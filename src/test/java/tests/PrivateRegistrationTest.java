package tests;

import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.PrivateRegistrationPage;

public class PrivateRegistrationTest {

    private final PrivateRegistrationPage privateRegistrationPage;

    public PrivateRegistrationTest(TestContext context) {
        this.privateRegistrationPage = new PrivateRegistrationPage(context.page);
    }

    @Given("the user navigates to the Private Registration page")
    public void navigateToPrivateRegistrationPage() {
        privateRegistrationPage.navigateToPrivateRegistrationPage();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the PR hero kicker displays {string}")
    public void assertHeroKicker(String expectedText) {
        privateRegistrationPage.assertHeroKickerText(expectedText);
    }

    @Then("the PR hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        privateRegistrationPage.assertHeroTitleText(expectedTitle);
    }

    @Then("the PR hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        privateRegistrationPage.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the PR Buy Now button displays {string}")
    public void assertBuyNowButtonDisplays(String expectedText) {
        privateRegistrationPage.assertBuyNowButtonDisplays(expectedText);
    }

    @Then("the PR Buy Now button links to {string}")
    public void assertBuyNowButtonHref(String expectedHref) {
        privateRegistrationPage.assertBuyNowButtonHref(expectedHref);
    }

    @When("the user clicks the PR Buy Now button")
    public void clickBuyNowButton() {
        privateRegistrationPage.clickBuyNowButton();
    }

    @Then("the PR pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        privateRegistrationPage.assertPricingSectionInViewport();
    }

    // ==================== "KEEP YOUR PERSONAL INFORMATION CONFIDENTIAL" SECTION ==================== //

    @Then("the PR Confidential section title displays {string}")
    public void assertConfidentialSectionTitle(String expectedTitle) {
        privateRegistrationPage.assertConfidentialSectionTitle(expectedTitle);
    }

    @Then("the PR Confidential sub-heading displays {string}")
    public void assertConfidentialSubHeadingDisplays(String subHeading) {
        privateRegistrationPage.assertConfidentialSubHeadingDisplays(subHeading);
    }

    @Then("the {string} PR description includes {string}")
    public void assertConfidentialSubDescription(String subHeading, String fragment) {
        privateRegistrationPage.assertConfidentialSubDescriptionContains(subHeading, fragment);
    }

    // ==================== HOSTING PLANS ==================== //

    @Then("the PR plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        privateRegistrationPage.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the PR plans section has {int} plan card")
    public void assertPlanCardCount(int expected) {
        privateRegistrationPage.assertPlanCardCount(expected);
    }

    @Then("the PR plan title displays {string}")
    public void assertPlanTitleDisplays(String expectedTitle) {
        privateRegistrationPage.assertPlanTitleDisplays(expectedTitle);
    }

    @Then("the {string} PR plan displays the subtitle {string}")
    public void assertPlanSubtitle(String planName, String expectedSubtitle) {
        privateRegistrationPage.assertPlanSubtitle(planName, expectedSubtitle);
    }

    @Then("the {string} PR plan displays the yearly pricing")
    public void assertPlanYearlyPricing(String planName) {
        // The PrivateRegistrationPlan enum owns the yearly price; the page
        // assertion pins the priceRow's full "$X.YY*" text in one call. No
        // strikethrough/original-price guard here — unlike Woo, Private
        // Registration doesn't run a promo.
        String price = config.PrivateRegistrationPlan.fromLabel(planName).getYearlyPrice().toPlainString();
        privateRegistrationPage.assertPlanPrice(planName, price);
    }

    @Then("the {string} PR plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        privateRegistrationPage.assertPlanBillingPeriod(planName, expectedPeriod);
    }

    @Then("the PR default selected plan is {string}")
    public void assertDefaultSelectedPlan(String planName) {
        privateRegistrationPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the PR Add to Cart button displays {string}")
    public void assertAddToCartButtonDisplays(String expectedText) {
        privateRegistrationPage.assertAddToCartButtonDisplays(expectedText);
    }

    @Then("the PR Add to Cart button links to {string}")
    public void assertAddToCartButtonHref(String expectedHref) {
        privateRegistrationPage.assertAddToCartButtonHref(expectedHref);
    }

    @Then("the PR tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        privateRegistrationPage.assertTaxNoteDisplays(expectedText);
    }

    // ==================== PLAN INCLUSIONS ==================== //

    @Then("the {string} PR plan inclusions heading displays {string}")
    public void assertPlanInclusionsHeading(String planName, String expectedHeading) {
        privateRegistrationPage.assertPlanInclusionsHeading(planName, expectedHeading);
    }

    @Then("the {string} PR plan includes {string}")
    public void assertPlanIncludesFeature(String planName, String feature) {
        privateRegistrationPage.assertPlanIncludesFeature(planName, feature);
    }
}
