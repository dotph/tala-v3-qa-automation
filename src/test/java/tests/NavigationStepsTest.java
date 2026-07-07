package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import contexts.TestContext;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class NavigationStepsTest {
    private static final Logger log = LogManager.getLogger(NavigationStepsTest.class);
    private final Page page;

    public NavigationStepsTest(TestContext context) {
        this.page = context.page;
    }

    @Then("the URL hash is {string}")
    public void assertUrlHashIs(String expectedHash) {
        log.info("Asserting URL ends with hash: \"{}\"", expectedHash);
        // Anchored regex: URL must end with the exact hash. Catches broken hrefs,
        // missing anchor targets, and JS-intercepted navigation that skips the hash.
        // Note: Playwright Java's hasURL(Pattern) sends the regex source to the JS
        // engine, which doesn't understand Java's \Q...\E quote syntax — so we
        // interpolate the hash directly. Safe as long as the hash has no regex
        // special chars (# is not one).
        PlaywrightAssertions.assertThat(page)
                .hasURL(Pattern.compile(".*" + expectedHash + "$"));
        log.info("PASSED: URL ends with \"{}\"", expectedHash);
    }
}
