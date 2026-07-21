package tests;

import contexts.TestContext;
import io.cucumber.java.en.Then;
import utils.CopyHygieneGuard;

/**
 * Step defs for the copy-hygiene guard. The Background on each landing-page
 * feature file already navigates to the page, so the shared step here just
 * scans the current DOM's {@code <body>} for whitespace regressions
 * (double-spaces and non-breaking spaces) that Playwright's default
 * assertions would silently pass. QATEAM-1016 acceptance criterion.
 */
public class CopyHygieneStepsTest {
    private final TestContext context;

    public CopyHygieneStepsTest(TestContext context) {
        this.context = context;
    }

    @Then("the page copy has no whitespace regressions")
    public void assertNoWhitespaceRegressions() {
        CopyHygieneGuard.assertNoWhitespaceRegressions(
                context.page.locator("body"),
                context.page.url());
    }
}
