package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Year;

public class FooterPage {
    private static final Logger log = LogManager.getLogger(FooterPage.class);
    private Page page;

    private Locator footer() {
        return page.locator("footer");
    }

    public FooterPage(Page page) {
        this.page = page;
    }

    public void assertFooterSectionHeadingDisplays(String expectedHeading) {
        log.info("Asserting footer section heading displays: \"{}\"", expectedHeading);
        Locator heading = footer().getByText(expectedHeading, new Locator.GetByTextOptions().setExact(true)).first();
        PlaywrightAssertions.assertThat(heading).containsText(expectedHeading);
        log.info("PASSED: footer section heading displays \"{}\"", expectedHeading);
    }

    public void assertFooterForexRateDisplays() {
        log.info("Asserting footer forex rate is displayed");
        PlaywrightAssertions.assertThat(footer()).containsText("$ 1.00 = Php");
        log.info("PASSED: footer forex rate is displayed");
    }

    public void assertFooterCopyrightDisplaysCurrentYear() {
        int currentYear = Year.now().getValue();
        String expectedCopyright = "dotPH Domains Inc. Copyright " + currentYear;
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

    public void assertFooterLinkHref(String linkText, String expectedHref) {
        log.info("Asserting footer link \"{}\" links to: \"{}\"", linkText, expectedHref);
        Locator link = footer().getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(linkText));
        PlaywrightAssertions.assertThat(link).hasAttribute("href", expectedHref);
        log.info("PASSED: footer link \"{}\" links to \"{}\"", linkText, expectedHref);
    }
}
