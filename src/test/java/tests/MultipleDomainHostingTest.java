package tests;

import config.MultipleDomainHostingPlan;
import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.MultipleDomainHostingPage;

import java.math.BigDecimal;

public class MultipleDomainHostingTest {

    private final MultipleDomainHostingPage multipleDomainHostingPage;

    public MultipleDomainHostingTest(TestContext context) {
        this.multipleDomainHostingPage = new MultipleDomainHostingPage(context.page);
    }

    @Given("the user navigates to the Multiple Domain Hosting page")
    public void navigateToMultipleDomainHostingPage() {
        multipleDomainHostingPage.navigateToMultipleDomainHostingPage();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the MDH hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        multipleDomainHostingPage.assertHeroTitleText(expectedTitle);
    }

    @Then("the MDH hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        multipleDomainHostingPage.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the MDH See Pricing button displays {string}")
    public void assertSeePricingButtonDisplays(String expectedText) {
        multipleDomainHostingPage.assertSeePricingButtonDisplays(expectedText);
    }

    @Then("the MDH See Pricing button links to {string}")
    public void assertSeePricingButtonHref(String expectedHref) {
        multipleDomainHostingPage.assertSeePricingButtonHref(expectedHref);
    }

    @When("the user clicks the MDH See Pricing button")
    public void clickSeePricingButton() {
        multipleDomainHostingPage.clickSeePricingButton();
    }

    @Then("the MDH pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        multipleDomainHostingPage.assertPricingSectionInViewport();
    }

    // ==================== HOSTING PLANS ==================== //

    @Then("the MDH plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        multipleDomainHostingPage.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the MDH plan title displays {string}")
    public void assertPlanTitleDisplays(String expectedTitle) {
        multipleDomainHostingPage.assertPlanTitleDisplays(expectedTitle);
    }

    @Then("the MDH plan card at position {int} is {string}")
    public void assertPlanCardAtPosition(int position, String expectedPlanName) {
        multipleDomainHostingPage.assertPlanCardAtPosition(position, expectedPlanName);
    }

    @Then("the {string} MDH plan displays the monthly price")
    public void assertPlanMonthlyPrice(String planName) {
        BigDecimal price = MultipleDomainHostingPlan.fromLabel(planName).getMonthlyPrice();
        multipleDomainHostingPage.assertPlanPrice(planName, "$" + price.toPlainString());
    }

    @Then("the {string} MDH plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        multipleDomainHostingPage.assertPlanBillingPeriod(planName, expectedPeriod);
    }

    @Then("the {string} MDH plan displays the description {string}")
    public void assertPlanDescription(String planName, String expectedDescription) {
        multipleDomainHostingPage.assertPlanDescription(planName, expectedDescription);
    }

    @Then("the MDH default selected plan is {string}")
    public void assertDefaultSelectedPlan(String planName) {
        multipleDomainHostingPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} MDH plan is not selected by default")
    public void assertPlanNotSelectedByDefault(String planName) {
        multipleDomainHostingPage.assertPlanIsInUnselectedState(planName);
    }

    @When("the user selects the {string} MDH plan")
    public void selectPlan(String planName) {
        multipleDomainHostingPage.selectPlan(planName);
    }

    @Then("the {string} MDH plan is in the selected state")
    public void assertPlanIsInSelectedState(String planName) {
        multipleDomainHostingPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} MDH plan is in the unselected state")
    public void assertPlanIsInUnselectedState(String planName) {
        multipleDomainHostingPage.assertPlanIsInUnselectedState(planName);
    }

    @Then("the MDH CTA button reflects the {string} plan")
    public void assertCtaReflectsPlan(String planName) {
        multipleDomainHostingPage.assertCtaReflectsPlan(planName);
    }

    @Then("the {string} MDH CTA button links to {string}")
    public void assertCtaHref(String planName, String expectedHref) {
        multipleDomainHostingPage.assertCtaHrefForPlan(planName, expectedHref);
    }

    @Then("the MDH tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        multipleDomainHostingPage.assertTaxNoteDisplays(expectedText);
    }

    // ==================== APPLY TO DOMAIN FIELD ==================== //

    @Then("the MDH Apply to label displays {string}")
    public void assertApplyToLabel(String expectedLabel) {
        multipleDomainHostingPage.assertApplyToLabelDisplays(expectedLabel);
    }

    @Then("the MDH domain input field is visible")
    public void assertDomainFieldVisible() {
        multipleDomainHostingPage.assertDomainFieldVisible();
    }

    @Then("the MDH domain input placeholder displays {string}")
    public void assertDomainFieldPlaceholder(String expectedPlaceholder) {
        multipleDomainHostingPage.assertDomainFieldPlaceholder(expectedPlaceholder);
    }

    @When("the user fills the MDH domain input with {string}")
    public void fillDomainField(String value) {
        multipleDomainHostingPage.fillDomainField(value);
    }

    @Then("the MDH domain input value is {string}")
    public void assertDomainFieldValue(String expectedValue) {
        multipleDomainHostingPage.assertDomainFieldValue(expectedValue);
    }

    // ==================== PLAN INCLUSIONS ==================== //

    @Then("the {string} MDH plan includes {string}")
    public void assertPlanIncludesFeature(String planName, String feature) {
        multipleDomainHostingPage.assertPlanIncludesFeature(planName, feature);
    }

    @Then("the {string} MDH plan excludes {string}")
    public void assertPlanExcludesFeature(String planName, String feature) {
        multipleDomainHostingPage.assertPlanExcludesFeature(planName, feature);
    }
}
