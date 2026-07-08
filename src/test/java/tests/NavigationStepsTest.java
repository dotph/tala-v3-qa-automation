package tests;

import com.microsoft.playwright.Page;
import contexts.TestContext;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationStepsTest {
    private static final Logger log = LogManager.getLogger(NavigationStepsTest.class);
    private final Page page;

    public NavigationStepsTest(TestContext context) {
        this.page = context.page;
    }

    @Then("the URL hash is {string}")
    public void assertUrlHashIs(String expectedHash) {
        log.info("Asserting URL ends with hash: \"{}\"", expectedHash);
        // Use a Predicate<String> rather than hasURL(Pattern) — Playwright Java
        // sends regex sources to the underlying JS engine, which doesn't
        // understand Java's \Q...\E quote syntax, so Pattern.quote() breaks
        // and a raw string containing regex metachars (future hashes like
        // "#foo.bar") would match too broadly. String.endsWith is exact,
        // predicate-based, and inherits waitForURL's auto-retry / timeout.
        page.waitForURL(url -> url.endsWith(expectedHash));
        log.info("PASSED: URL ends with \"{}\"", expectedHash);
    }
}
