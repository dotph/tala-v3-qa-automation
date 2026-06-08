package tests;

import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CpanelHostingPage;

public class CpanelHostingTest {

    private final CpanelHostingPage cpanelHostingPage;

    public CpanelHostingTest(TestContext context) {
        this.cpanelHostingPage = new CpanelHostingPage(context.page);
    }

    @Given("the user navigates to the cPanel Hosting page")
    public void navigateToCpanelHostingPage() {
        cpanelHostingPage.navigateToCpanelHostingPage();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        cpanelHostingPage.assertHeroTitleText(expectedTitle);
    }

    @Then("the hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        cpanelHostingPage.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the See Pricing button displays {string}")
    public void assertSeePricingButtonDisplays(String expectedText) {
        cpanelHostingPage.assertSeePricingButtonDisplays(expectedText);
    }

    @Then("the See Pricing button links to {string}")
    public void assertSeePricingButtonHref(String expectedHref) {
        cpanelHostingPage.assertSeePricingButtonHref(expectedHref);
    }

    @When("the user clicks the See Pricing button")
    public void clickSeePricingButton() {
        cpanelHostingPage.clickSeePricingButton();
    }

    @Then("the pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        cpanelHostingPage.assertPricingSectionInViewport();
    }

    // ==================== HOSTING PLANS ==================== //

    @Then("the plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        cpanelHostingPage.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the plan title displays {string}")
    public void assertPlanTitleDisplays(String expectedTitle) {
        cpanelHostingPage.assertPlanTitleDisplays(expectedTitle);
    }

    @Then("the {string} plan displays the pricing label {string}")
    public void assertPlanPricingLabel(String planName, String expectedLabel) {
        cpanelHostingPage.assertPlanPricingLabel(planName, expectedLabel);
    }

    @Then("the {string} plan displays the price {string}")
    public void assertPlanPrice(String planName, String expectedPrice) {
        cpanelHostingPage.assertPlanPrice(planName, expectedPrice);
    }

    @Then("the {string} plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        cpanelHostingPage.assertPlanBillingPeriod(planName, expectedPeriod);
    }

    @Then("the {string} plan displays the description {string}")
    public void assertPlanDescription(String planName, String expectedDescription) {
        cpanelHostingPage.assertPlanDescription(planName, expectedDescription);
    }

    @Then("the {string} plan CTA displays {string}")
    public void assertPlanCtaDisplays(String planName, String expectedCtaText) {
        cpanelHostingPage.assertPlanCtaDisplays(planName, expectedCtaText);
    }

    @Then("the {string} plan CTA links to {string}")
    public void assertPlanCtaHref(String planName, String expectedHref) {
        cpanelHostingPage.assertPlanCtaHref(planName, expectedHref);
    }

    @Then("the tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        cpanelHostingPage.assertTaxNoteDisplays(expectedText);
    }
}
