package tests;

import config.Microsoft365Plan;
import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.Microsoft365Page;

public class Microsoft365Test {

    private final Microsoft365Page microsoft365Page;

    public Microsoft365Test(TestContext context) {
        this.microsoft365Page = new Microsoft365Page(context.page);
    }

    @Given("the user navigates to the Microsoft 365 page")
    public void navigateToMicrosoft365Page() {
        microsoft365Page.navigateToMicrosoft365Page();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the M365 hero eyebrow displays {string}")
    public void assertHeroEyebrow(String expectedText) {
        microsoft365Page.assertHeroEyebrowText(expectedText);
    }

    @Then("the M365 hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        microsoft365Page.assertHeroTitleText(expectedTitle);
    }

    @Then("the M365 hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        microsoft365Page.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the M365 See Pricing button displays {string}")
    public void assertSeePricingButtonDisplays(String expectedText) {
        microsoft365Page.assertSeePricingButtonDisplays(expectedText);
    }

    @Then("the M365 See Pricing button links to {string}")
    public void assertSeePricingButtonHref(String expectedHref) {
        microsoft365Page.assertSeePricingButtonHref(expectedHref);
    }

    @When("the user clicks the M365 See Pricing button")
    public void clickSeePricingButton() {
        microsoft365Page.clickSeePricingButton();
    }

    @Then("the M365 pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        microsoft365Page.assertPricingSectionInViewport();
    }

    // ==================== PLAN INTROS STRIP ==================== //

    @Then("the M365 intro strip has {int} intro cards")
    public void assertIntroCardCount(int expected) {
        microsoft365Page.assertIntroCardCount(expected);
    }

    @Then("the M365 intro card at position {int} displays the heading {string}")
    public void assertIntroCardHeading(int position, String expectedHeading) {
        microsoft365Page.assertIntroCardHeading(position, expectedHeading);
    }

    @Then("the M365 intro card at position {int} shows the former name {string}")
    public void assertIntroCardFormerName(int position, String expectedFormerName) {
        microsoft365Page.assertIntroCardFormerName(position, expectedFormerName);
    }

    // ==================== "MICROSOFT 365 FEATURES" SECTION ==================== //

    @Then("the M365 features section title displays {string}")
    public void assertFeaturesSectionTitle(String expectedTitle) {
        microsoft365Page.assertFeaturesSectionTitle(expectedTitle);
    }

    @Then("the M365 features section has {int} feature cards")
    public void assertFeatureCount(int expected) {
        microsoft365Page.assertFeatureCount(expected);
    }

    @Then("the M365 feature at position {int} displays the heading {string}")
    public void assertFeatureHeading(int position, String expectedHeading) {
        microsoft365Page.assertFeatureHeading(position, expectedHeading);
    }

    @Then("the M365 feature at position {int} description includes {string}")
    public void assertFeatureDescriptionContains(int position, String fragment) {
        microsoft365Page.assertFeatureDescriptionContains(position, fragment);
    }

    // ==================== MICROSOFT 365 PLANS ==================== //

    @Then("the M365 plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        microsoft365Page.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the M365 plans section has {int} plan cards")
    public void assertPlanCardCount(int expected) {
        microsoft365Page.assertPlanCardCount(expected);
    }

    @Then("the M365 plan title displays {string}")
    public void assertPlanTitleDisplays(String expectedTitle) {
        microsoft365Page.assertPlanTitleDisplays(expectedTitle);
    }

    @Then("the M365 plan card at position {int} is {string}")
    public void assertPlanCardAtPosition(int position, String expectedPlanName) {
        microsoft365Page.assertPlanCardAtPosition(position, expectedPlanName);
    }

    @Then("the {string} M365 plan sub-heading displays {string}")
    public void assertPlanSubheading(String planName, String expectedSubheading) {
        microsoft365Page.assertPlanSubheadingText(planName, expectedSubheading);
    }

    @Then("the {string} M365 plan displays the yearly pricing")
    public void assertPlanYearlyPricing(String planName) {
        Microsoft365Plan plan = Microsoft365Plan.fromLabel(planName);
        microsoft365Page.assertPlanPricing(planName, plan.getYearlyPrice().toPlainString());
    }

    @Then("the {string} M365 plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        microsoft365Page.assertPlanBillingPeriod(planName, expectedPeriod);
    }

    @Then("the M365 default selected plan is {string}")
    public void assertDefaultSelectedPlan(String planName) {
        microsoft365Page.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} M365 plan is not selected by default")
    public void assertPlanNotSelectedByDefault(String planName) {
        microsoft365Page.assertPlanIsInUnselectedState(planName);
    }

    @When("the user selects the {string} M365 plan")
    public void selectPlan(String planName) {
        microsoft365Page.selectPlan(planName);
    }

    @Then("the {string} M365 plan is in the selected state")
    public void assertPlanIsInSelectedState(String planName) {
        microsoft365Page.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} M365 plan is in the unselected state")
    public void assertPlanIsInUnselectedState(String planName) {
        microsoft365Page.assertPlanIsInUnselectedState(planName);
    }

    @Then("the M365 CTA button reflects the {string} plan")
    public void assertCtaReflectsPlan(String planName) {
        microsoft365Page.assertCtaReflectsPlan(planName);
    }

    @Then("the {string} M365 CTA button links to {string}")
    public void assertCtaHref(String planName, String expectedHref) {
        microsoft365Page.assertCtaHrefForPlan(planName, expectedHref);
    }

    @Then("the M365 tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        microsoft365Page.assertTaxNoteDisplays(expectedText);
    }

    // ==================== PLAN INCLUSIONS ==================== //

    @Then("the {string} M365 plan inclusions heading displays {string}")
    public void assertPlanInclusionsHeading(String planName, String expectedHeading) {
        microsoft365Page.assertPlanInclusionsHeading(planName, expectedHeading);
    }

    @Then("the {string} M365 plan has {int} inclusion row(s)")
    public void assertPlanInclusionRowCount(String planName, int expected) {
        microsoft365Page.assertPlanInclusionRowCount(planName, expected);
    }

    @Then("the {string} M365 plan includes {string}")
    public void assertPlanIncludesFeature(String planName, String feature) {
        microsoft365Page.assertPlanIncludesFeature(planName, feature);
    }

    @Then("the {string} M365 plan excludes {string}")
    public void assertPlanExcludesFeature(String planName, String feature) {
        microsoft365Page.assertPlanExcludesFeature(planName, feature);
    }
}
