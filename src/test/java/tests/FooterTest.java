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

    @Then("the footer forex rate displays {string}")
    public void assertFooterForexRate(String expectedRate) {
        footerPage.assertFooterForexRateDisplays(expectedRate);
    }

    @Then("the footer copyright displays {string}")
    public void assertFooterCopyright(String expectedCopyright) {
        footerPage.assertFooterCopyrightDisplays(expectedCopyright);
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
