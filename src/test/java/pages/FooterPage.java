package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class FooterPage {
    private static final Logger log = LogManager.getLogger(FooterPage.class);
    private Page page;

    private Locator footer() {
        return page.locator("footer");
    }

    public FooterPage(Page page) {
        this.page = page;
    }

    private String resolveExpectedUrl(String path) {
        return path.startsWith("http") ? path : EnvConfig.getUrl(path);
    }

    public void assertFooterSectionHeadingDisplays(String expectedHeading) {
        log.info("Asserting footer section heading displays: \"{}\"", expectedHeading);
        Locator heading = footer().getByText(expectedHeading, new Locator.GetByTextOptions().setExact(true)).first();
        PlaywrightAssertions.assertThat(heading).containsText(expectedHeading);
        log.info("PASSED: footer section heading displays \"{}\"", expectedHeading);
    }

    public void assertFooterForexRateDisplays(String expectedRate) {
        log.info("Asserting footer forex rate displays: \"{}\"", expectedRate);
        PlaywrightAssertions.assertThat(footer()).containsText(expectedRate);
        log.info("PASSED: footer forex rate displays \"{}\"", expectedRate);
    }

    public void assertFooterCopyrightDisplays(String expectedCopyright) {
        log.info("Asserting footer copyright displays: \"{}\"", expectedCopyright);
        PlaywrightAssertions.assertThat(footer()).containsText(expectedCopyright);
        log.info("PASSED: footer copyright displays \"{}\"", expectedCopyright);
    }

    public void assertFooterLinkDisplays(String expectedLinkText) {
        log.info("Asserting footer link displays: \"{}\"", expectedLinkText);
        Locator link = footer().getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(expectedLinkText));
        PlaywrightAssertions.assertThat(link).containsText(expectedLinkText);
        log.info("PASSED: footer link displays \"{}\"", expectedLinkText);
    }

    public void assertFooterLinkHref(String linkText, String expectedPath) {
        String expectedUrl = resolveExpectedUrl(expectedPath);
        log.info("Asserting footer link \"{}\" links to: \"{}\"", linkText, expectedUrl);
        Locator link = footer().getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(linkText));
        String actualHref = link.evaluate("el => el.href").toString();
        Assert.assertEquals(actualHref, expectedUrl, linkText + " href mismatch");
        log.info("PASSED: footer link \"{}\" links to \"{}\"", linkText, expectedUrl);
    }
}
