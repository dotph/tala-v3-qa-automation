package tests;

import config.VpsPlan;
import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.VpsPage;

public class VpsTest {

    private final VpsPage vpsPage;

    public VpsTest(TestContext context) {
        this.vpsPage = new VpsPage(context.page);
    }

    @Given("the user navigates to the Virtual Private Server page")
    public void navigateToVpsPage() {
        vpsPage.navigateToVpsPage();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the VPS hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        vpsPage.assertHeroTitleText(expectedTitle);
    }

    @Then("the VPS hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        vpsPage.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the VPS See Pricing button displays {string}")
    public void assertSeePricingButtonDisplays(String expectedText) {
        vpsPage.assertSeePricingButtonDisplays(expectedText);
    }

    @Then("the VPS See Pricing button links to {string}")
    public void assertSeePricingButtonHref(String expectedHref) {
        vpsPage.assertSeePricingButtonHref(expectedHref);
    }

    @When("the user clicks the VPS See Pricing button")
    public void clickSeePricingButton() {
        vpsPage.clickSeePricingButton();
    }

    @Then("the VPS pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        vpsPage.assertPricingSectionInViewport();
    }

    // ==================== HOSTING FEATURES SECTION ==================== //

    @Then("the VPS Hosting Features section title displays {string}")
    public void assertHostingFeaturesSectionTitle(String expectedTitle) {
        vpsPage.assertHostingFeaturesSectionTitle(expectedTitle);
    }

    @Then("the VPS Hosting Features copy includes {string}")
    public void assertHostingFeaturesCopyIncludes(String fragment) {
        vpsPage.assertHostingFeaturesCopyContains(fragment);
    }

    // ==================== GET FULL CONTROL SECTION ==================== //

    @Then("the VPS Get full control section title displays {string}")
    public void assertGetFullControlSectionTitle(String expectedTitle) {
        vpsPage.assertGetFullControlSectionTitle(expectedTitle);
    }

    @Then("the VPS Get full control sub-heading displays {string}")
    public void assertGetFullControlSubHeadingDisplays(String subHeading) {
        vpsPage.assertGetFullControlSubHeadingDisplays(subHeading);
    }

    @Then("the {string} VPS description includes {string}")
    public void assertGetFullControlSubDescription(String subHeading, String fragment) {
        vpsPage.assertGetFullControlSubDescriptionContains(subHeading, fragment);
    }

    // ==================== HOSTING PLANS ==================== //

    @Then("the VPS plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        vpsPage.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the VPS plan title displays {string}")
    public void assertPlanTitleDisplays(String expectedTitle) {
        vpsPage.assertPlanTitleDisplays(expectedTitle);
    }

    @Then("the VPS plan card at position {int} is {string}")
    public void assertPlanCardAtPosition(int position, String expectedPlanName) {
        vpsPage.assertPlanCardAtPosition(position, expectedPlanName);
    }

    @Then("the {string} VPS plan displays the monthly price")
    public void assertPlanMonthlyPrice(String planName) {
        // Numeric portion only ("175.00"); assertPlanPrice checks "$" separately
        // because the card DOM splits currency, price, and footnote across spans.
        String price = VpsPlan.fromLabel(planName).getMonthlyPrice().toPlainString();
        vpsPage.assertPlanPrice(planName, price);
    }

    @Then("the {string} VPS plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        vpsPage.assertPlanBillingPeriod(planName, expectedPeriod);
    }

    @Then("the VPS default selected plan is {string}")
    public void assertDefaultSelectedPlan(String planName) {
        vpsPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} VPS plan is not selected by default")
    public void assertPlanNotSelectedByDefault(String planName) {
        vpsPage.assertPlanIsInUnselectedState(planName);
    }

    @When("the user selects the {string} VPS plan")
    public void selectPlan(String planName) {
        vpsPage.selectPlan(planName);
    }

    @Then("the {string} VPS plan is in the selected state")
    public void assertPlanIsInSelectedState(String planName) {
        vpsPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} VPS plan is in the unselected state")
    public void assertPlanIsInUnselectedState(String planName) {
        vpsPage.assertPlanIsInUnselectedState(planName);
    }

    @Then("the VPS tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        vpsPage.assertTaxNoteDisplays(expectedText);
    }

    // ==================== PLAN INCLUSIONS ==================== //

    @Then("the {string} VPS plan inclusions heading displays {string}")
    public void assertPlanInclusionsHeading(String planName, String expectedHeading) {
        vpsPage.assertPlanInclusionsHeading(planName, expectedHeading);
    }

    @Then("the {string} VPS plan includes {string}")
    public void assertPlanIncludesFeature(String planName, String feature) {
        vpsPage.assertPlanIncludesFeature(planName, feature);
    }

    // ==================== INQUIRY FORM ==================== //

    @Then("the VPS inquiry intro displays {string}")
    public void assertInquiryIntro(String expectedText) {
        vpsPage.assertInquiryIntroDisplays(expectedText);
    }

    @Then("the VPS inquiry field label displays {string}")
    public void assertInquiryFieldLabel(String labelText) {
        vpsPage.assertInquiryFieldLabelDisplays(labelText);
    }

    @Then("the VPS inquiry field {string} placeholder is {string}")
    public void assertInquiryFieldPlaceholder(String labelText, String placeholder) {
        vpsPage.assertInquiryFieldPlaceholder(labelText, placeholder);
    }

    @Then("the VPS inquiry Submit button is visible")
    public void assertInquirySubmitButtonVisible() {
        vpsPage.assertInquirySubmitButtonVisible();
    }
}
