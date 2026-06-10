package tests;

import contexts.TestContext;
import io.cucumber.java.en.Then;
import pages.FooterPage;

public class FooterTest {

    private final FooterPage footerPage;

    public FooterTest(TestContext context) {
        this.footerPage = new FooterPage(context.page);
    }

    @Then("the footer section heading displays {string}")
    public void assertFooterSectionHeading(String expectedHeading) {
        footerPage.assertFooterSectionHeadingDisplays(expectedHeading);
    }

    @Then("the footer forex rate is displayed")
    public void assertFooterForexRate() {
        footerPage.assertFooterForexRateDisplays();
    }

    @Then("the footer copyright displays the current year")
    public void assertFooterCopyright() {
        footerPage.assertFooterCopyrightDisplaysCurrentYear();
    }

    @Then("the footer link displays {string}")
    public void assertFooterLinkDisplays(String expectedLinkText) {
        footerPage.assertFooterLinkDisplays(expectedLinkText);
    }

    @Then("the footer link {string} links to {string}")
    public void assertFooterLinkHref(String linkText, String expectedHref) {
        footerPage.assertFooterLinkHref(linkText, expectedHref);
    }
}
