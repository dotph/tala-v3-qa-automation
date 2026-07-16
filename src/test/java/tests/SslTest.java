package tests;

import config.SslPlan;
import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.SslPage;

public class SslTest {

    private final SslPage sslPage;

    public SslTest(TestContext context) {
        this.sslPage = new SslPage(context.page);
    }

    @Given("the user navigates to the SSL Certificates page")
    public void navigateToSslPage() {
        sslPage.navigateToSslPage();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the SSL hero eyebrow displays {string}")
    public void assertHeroEyebrow(String expectedText) {
        sslPage.assertHeroEyebrowText(expectedText);
    }

    @Then("the SSL hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        sslPage.assertHeroTitleText(expectedTitle);
    }

    @Then("the SSL hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        sslPage.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the SSL Buy Now button displays {string}")
    public void assertBuyNowButtonDisplays(String expectedText) {
        sslPage.assertBuyNowButtonDisplays(expectedText);
    }

    @Then("the SSL Buy Now button links to {string}")
    public void assertBuyNowButtonHref(String expectedHref) {
        sslPage.assertBuyNowButtonHref(expectedHref);
    }

    @When("the user clicks the SSL Buy Now button")
    public void clickBuyNowButton() {
        sslPage.clickBuyNowButton();
    }

    @Then("the SSL pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        sslPage.assertPricingSectionInViewport();
    }

    // ==================== "SECURE YOUR WEBSITE" SECTION ==================== //

    @Then("the SSL Secure Your Website section title displays {string}")
    public void assertSecureYourWebsiteTitle(String expectedTitle) {
        sslPage.assertSecureYourWebsiteTitle(expectedTitle);
    }

    @Then("the SSL Secure Your Website paragraph includes {string}")
    public void assertSecureYourWebsiteParagraphIncludes(String fragment) {
        sslPage.assertSecureYourWebsiteParagraphContains(fragment);
    }

    // ==================== SSL CERTIFICATE PLANS ==================== //

    @Then("the SSL plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        sslPage.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the SSL plans section has {int} plan cards")
    public void assertPlanCardCount(int expected) {
        sslPage.assertPlanCardCount(expected);
    }

    @Then("the SSL plan title displays {string}")
    public void assertPlanTitleDisplays(String expectedTitle) {
        sslPage.assertPlanTitleDisplays(expectedTitle);
    }

    @Then("the SSL plan card at position {int} is {string}")
    public void assertPlanCardAtPosition(int position, String expectedPlanName) {
        sslPage.assertPlanCardAtPosition(position, expectedPlanName);
    }

    @Then("the {string} SSL plan sub-heading displays {string}")
    public void assertPlanSubheading(String planName, String expectedSubheading) {
        sslPage.assertPlanSubheadingText(planName, expectedSubheading);
    }

    @Then("the {string} SSL plan displays the yearly pricing")
    public void assertPlanYearlyPricing(String planName) {
        // The SslPlan enum owns the yearly price; the page assertion pins the
        // priceRow's full "$YY.YY*" text in one call (currency + amount +
        // footnote-asterisk together).
        SslPlan plan = SslPlan.fromLabel(planName);
        sslPage.assertPlanPricing(planName, plan.getYearlyPrice().toPlainString());
    }

    @Then("the {string} SSL plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        sslPage.assertPlanBillingPeriod(planName, expectedPeriod);
    }

    @Then("the SSL default selected plan is {string}")
    public void assertDefaultSelectedPlan(String planName) {
        sslPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} SSL plan is not selected by default")
    public void assertPlanNotSelectedByDefault(String planName) {
        sslPage.assertPlanIsInUnselectedState(planName);
    }

    @When("the user selects the {string} SSL plan")
    public void selectPlan(String planName) {
        sslPage.selectPlan(planName);
    }

    @Then("the {string} SSL plan is in the selected state")
    public void assertPlanIsInSelectedState(String planName) {
        sslPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} SSL plan is in the unselected state")
    public void assertPlanIsInUnselectedState(String planName) {
        sslPage.assertPlanIsInUnselectedState(planName);
    }

    @Then("the SSL CTA button reflects the {string} plan")
    public void assertCtaReflectsPlan(String planName) {
        sslPage.assertCtaReflectsPlan(planName);
    }

    @Then("the {string} SSL CTA button links to {string}")
    public void assertCtaHref(String planName, String expectedHref) {
        sslPage.assertCtaHrefForPlan(planName, expectedHref);
    }

    @Then("the SSL tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        sslPage.assertTaxNoteDisplays(expectedText);
    }

    // ==================== PLAN INCLUSIONS ==================== //

    @Then("the {string} SSL plan inclusions heading displays {string}")
    public void assertPlanInclusionsHeading(String planName, String expectedHeading) {
        sslPage.assertPlanInclusionsHeading(planName, expectedHeading);
    }

    @Then("the {string} SSL plan includes {string}")
    public void assertPlanIncludesFeature(String planName, String feature) {
        sslPage.assertPlanIncludesFeature(planName, feature);
    }
}
