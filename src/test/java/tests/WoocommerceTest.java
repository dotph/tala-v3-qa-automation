package tests;

import config.WoocommercePlan;
import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.WoocommercePage;

public class WoocommerceTest {

    private final WoocommercePage woocommercePage;

    public WoocommerceTest(TestContext context) {
        this.woocommercePage = new WoocommercePage(context.page);
    }

    @Given("the user navigates to the WooCommerce page")
    public void navigateToWoocommercePage() {
        woocommercePage.navigateToWoocommercePage();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the Woo hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        woocommercePage.assertHeroTitleText(expectedTitle);
    }

    @Then("the Woo hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        woocommercePage.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the Woo See Pricing button displays {string}")
    public void assertSeePricingButtonDisplays(String expectedText) {
        woocommercePage.assertSeePricingButtonDisplays(expectedText);
    }

    @Then("the Woo See Pricing button links to {string}")
    public void assertSeePricingButtonHref(String expectedHref) {
        woocommercePage.assertSeePricingButtonHref(expectedHref);
    }

    @When("the user clicks the Woo See Pricing button")
    public void clickSeePricingButton() {
        woocommercePage.clickSeePricingButton();
    }

    @Then("the Woo pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        woocommercePage.assertPricingSectionInViewport();
    }

    // ==================== "START GROWING YOUR BUSINESS" SECTION ==================== //

    @Then("the Woo Start growing section title displays {string}")
    public void assertGrowingSectionTitle(String expectedTitle) {
        woocommercePage.assertGrowingSectionTitle(expectedTitle);
    }

    @Then("the Woo Start growing intro includes {string}")
    public void assertGrowingIntroIncludes(String fragment) {
        woocommercePage.assertGrowingIntroContains(fragment);
    }

    @Then("the Woo Start growing sub-heading displays {string}")
    public void assertGrowingSubHeadingDisplays(String subHeading) {
        woocommercePage.assertGrowingSubHeadingDisplays(subHeading);
    }

    @Then("the {string} Woo description includes {string}")
    public void assertGrowingSubDescription(String subHeading, String fragment) {
        woocommercePage.assertGrowingSubDescriptionContains(subHeading, fragment);
    }

    // ==================== HOSTING PLANS ==================== //

    @Then("the Woo plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        woocommercePage.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the Woo plan title displays {string}")
    public void assertPlanTitleDisplays(String expectedTitle) {
        woocommercePage.assertPlanTitleDisplays(expectedTitle);
    }

    @Then("the Woo plan card at position {int} is {string}")
    public void assertPlanCardAtPosition(int position, String expectedPlanName) {
        woocommercePage.assertPlanCardAtPosition(position, expectedPlanName);
    }

    @Then("the {string} Woo plan promo label displays {string}")
    public void assertPlanPromoLabel(String planName, String expectedLabel) {
        woocommercePage.assertPlanPromoLabel(planName, expectedLabel);
    }

    @Then("the {string} Woo plan displays the monthly pricing")
    public void assertPlanMonthlyPricing(String planName) {
        // The WoocommercePlan enum owns both the struck-through original and
        // the promoted monthly price; the page assertion pins the priceRow's
        // full "$orig$promo*" text plus the strikethrough visual in one call.
        WoocommercePlan plan = WoocommercePlan.fromLabel(planName);
        woocommercePage.assertPlanPricing(planName,
                plan.getOriginalMonthlyPrice().toPlainString(),
                plan.getPromoMonthlyPrice().toPlainString());
    }

    @Then("the {string} Woo plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        woocommercePage.assertPlanBillingPeriod(planName, expectedPeriod);
    }

    @Then("the Woo default selected plan is {string}")
    public void assertDefaultSelectedPlan(String planName) {
        woocommercePage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} Woo plan is not selected by default")
    public void assertPlanNotSelectedByDefault(String planName) {
        woocommercePage.assertPlanIsInUnselectedState(planName);
    }

    @When("the user selects the {string} Woo plan")
    public void selectPlan(String planName) {
        woocommercePage.selectPlan(planName);
    }

    @Then("the {string} Woo plan is in the selected state")
    public void assertPlanIsInSelectedState(String planName) {
        woocommercePage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} Woo plan is in the unselected state")
    public void assertPlanIsInUnselectedState(String planName) {
        woocommercePage.assertPlanIsInUnselectedState(planName);
    }

    @Then("the Woo CTA button reflects the {string} plan")
    public void assertCtaReflectsPlan(String planName) {
        woocommercePage.assertCtaReflectsPlan(planName);
    }

    @Then("the {string} Woo CTA button links to {string}")
    public void assertCtaHref(String planName, String expectedHref) {
        woocommercePage.assertCtaHrefForPlan(planName, expectedHref);
    }

    @Then("the Woo tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        woocommercePage.assertTaxNoteDisplays(expectedText);
    }

    // ==================== PLAN INCLUSIONS ==================== //

    @Then("the {string} Woo plan inclusions heading displays {string}")
    public void assertPlanInclusionsHeading(String planName, String expectedHeading) {
        woocommercePage.assertPlanInclusionsHeading(planName, expectedHeading);
    }

    @Then("the {string} Woo plan includes {string}")
    public void assertPlanIncludesFeature(String planName, String feature) {
        woocommercePage.assertPlanIncludesFeature(planName, feature);
    }

    // ==================== FAQ SECTION ==================== //

    @Then("the Woo FAQ question {string} is visible")
    public void assertFaqQuestionVisible(String question) {
        woocommercePage.assertFaqQuestionVisible(question);
    }

    @When("the user expands the Woo FAQ question {string}")
    public void expandFaqQuestion(String question) {
        woocommercePage.expandFaqQuestion(question);
    }

    @Then("the Woo FAQ answer for {string} includes {string}")
    public void assertFaqAnswerIncludes(String question, String fragment) {
        woocommercePage.assertFaqAnswerContains(question, fragment);
    }
}
