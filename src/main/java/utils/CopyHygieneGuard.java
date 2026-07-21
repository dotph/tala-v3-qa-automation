package utils;

import com.microsoft.playwright.Locator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Copy-quality guard: scans text nodes inside a scope and fails if any
 * user-visible text run carries a whitespace regression that Playwright's
 * whitespace-normalizing assertions ({@code hasText} / {@code containsText} /
 * {@code getByText}) would silently pass.
 *
 * Two failure modes flagged today:
 *
 * <ul>
 *   <li><b>double-space</b> — two or more literal ASCII spaces between two
 *       non-whitespace characters in a single text node (e.g. "Hello  world").
 *       Structural indentation between tags is skipped by requiring the run
 *       to be flanked by non-whitespace chars (\S _{2,}_ \S).</li>
 *   <li><b>nbsp</b> — U+00A0 anywhere in a visible text run. Sometimes used
 *       intentionally (e.g. "20 min" as "20 min" to prevent line
 *       breaks), so a legitimate hit is a design decision — if a page
 *       fails this check on real copy, whitelist that string on the caller
 *       side or scope the check narrower.</li>
 * </ul>
 *
 * QATEAM-1016 acceptance criterion asks the suite to "detect regressions in
 * copy quality such as typos, double spacing, and inconsistent formatting …
 * fail on any deviation" — this component covers the whitespace half.
 */
public final class CopyHygieneGuard {
    private static final Logger log = LogManager.getLogger(CopyHygieneGuard.class);

    private CopyHygieneGuard() {}

    private static final String SCAN_JS =
            "el => {" +
            "  const issues = [];" +
            // Filter out text nodes inside script/style/noscript/template — the
            // browser doesn't render them, but SHOW_TEXT walks them anyway.
            // Without this, an inline hydration payload with a "const x  = 1"
            // (double space) or a hidden template carrying an &nbsp; would
            // fail every landing page at once with a report that points at
            // markup, not copy.
            "  const walker = document.createTreeWalker(el, NodeFilter.SHOW_TEXT, {" +
            "    acceptNode: n => n.parentElement && n.parentElement.closest('script,style,noscript,template')" +
            "      ? NodeFilter.FILTER_REJECT : NodeFilter.FILTER_ACCEPT" +
            "  });" +
            "  while (walker.nextNode()) {" +
            "    const raw = walker.currentNode.textContent;" +
            "    if (!raw || !raw.trim()) continue;" +
            "    const preview = raw.trim().replace(/\\s+/g, ' ').slice(0, 100);" +
            "    if (/\\S {2,}\\S/.test(raw)) issues.push('[double-space] ' + preview);" +
            "    if (/\\u00a0/.test(raw)) issues.push('[nbsp] ' + preview);" +
            "  }" +
            "  return issues;" +
            "}";

    /**
     * Fail with a list of offending text runs if any are found within
     * {@code scope}. {@code scopeName} is used in the log and error message
     * only — pass whatever helps identify which page/section is under check.
     */
    @SuppressWarnings("unchecked")
    public static void assertNoWhitespaceRegressions(Locator scope, String scopeName) {
        log.info("Asserting copy hygiene on {} — no double-space or nbsp regressions", scopeName);
        Object result = scope.evaluate(SCAN_JS);
        List<Object> issues = (List<Object>) result;
        if (!issues.isEmpty()) {
            throw new AssertionError(
                    "Copy hygiene FAILED on " + scopeName + ": " + issues.size() +
                            " whitespace regression(s) found:\n  " +
                            String.join("\n  ", issues.stream().map(Object::toString).toList()));
        }
        log.info("PASSED: copy hygiene on {} (0 regressions)", scopeName);
    }
}
