package tests;

import config.SingleDomainHostingPlan;
import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.SingleDomainHostingPage;

import java.math.BigDecimal;

public class SingleDomainHostingTest {

    private final SingleDomainHostingPage singleDomainHostingPage;

    public SingleDomainHostingTest(TestContext context) {
        this.singleDomainHostingPage = new SingleDomainHostingPage(context.page);
    }

    @Given("the user navigates to the Single Domain Hosting page")
    public void navigateToSingleDomainHostingPage() {
        singleDomainHostingPage.navigateToSingleDomainHostingPage();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the SDH hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        singleDomainHostingPage.assertHeroTitleText(expectedTitle);
    }

    @Then("the SDH hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        singleDomainHostingPage.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the SDH See Pricing button displays {string}")
    public void assertSeePricingButtonDisplays(String expectedText) {
        singleDomainHostingPage.assertSeePricingButtonDisplays(expectedText);
    }

    @Then("the SDH See Pricing button links to {string}")
    public void assertSeePricingButtonHref(String expectedHref) {
        singleDomainHostingPage.assertSeePricingButtonHref(expectedHref);
    }

    @When("the user clicks the SDH See Pricing button")
    public void clickSeePricingButton() {
        singleDomainHostingPage.clickSeePricingButton();
    }

    @Then("the SDH pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        singleDomainHostingPage.assertPricingSectionInViewport();
    }

    // ==================== HOSTING PLANS ==================== //

    @Then("the SDH plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        singleDomainHostingPage.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the SDH plan title displays {string}")
    public void assertPlanTitleDisplays(String expectedTitle) {
        singleDomainHostingPage.assertPlanTitleDisplays(expectedTitle);
    }

    @Then("the {string} SDH plan displays the monthly price")
    public void assertPlanMonthlyPrice(String planName) {
        BigDecimal price = SingleDomainHostingPlan.fromLabel(planName).getMonthlyPrice();
        singleDomainHostingPage.assertPlanPrice(planName, "$" + price.toPlainString());
    }

    @Then("the {string} SDH plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        singleDomainHostingPage.assertPlanBillingPeriod(planName, expectedPeriod);
    }

    @Then("the {string} SDH plan displays the description {string}")
    public void assertPlanDescription(String planName, String expectedDescription) {
        singleDomainHostingPage.assertPlanDescription(planName, expectedDescription);
    }

    @Then("the SDH default selected plan is {string}")
    public void assertDefaultSelectedPlan(String planName) {
        singleDomainHostingPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} SDH plan is not selected by default")
    public void assertPlanNotSelectedByDefault(String planName) {
        singleDomainHostingPage.assertPlanIsInUnselectedState(planName);
    }

    @When("the user selects the {string} SDH plan")
    public void selectPlan(String planName) {
        singleDomainHostingPage.selectPlan(planName);
    }

    @Then("the {string} SDH plan is in the selected state")
    public void assertPlanIsInSelectedState(String planName) {
        singleDomainHostingPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the {string} SDH plan is in the unselected state")
    public void assertPlanIsInUnselectedState(String planName) {
        singleDomainHostingPage.assertPlanIsInUnselectedState(planName);
    }

    @Then("the SDH CTA button reflects the {string} plan")
    public void assertCtaReflectsPlan(String planName) {
        singleDomainHostingPage.assertCtaReflectsPlan(planName);
    }

    @Then("the {string} SDH plan includes its domain limit")
    public void assertPlanDomains(String planName) {
        String expected = SingleDomainHostingPlan.fromLabel(planName).getDomains();
        singleDomainHostingPage.assertPlanSpec(planName, expected);
    }

    @Then("the {string} SDH plan includes its disk space")
    public void assertPlanDiskSpace(String planName) {
        String expected = SingleDomainHostingPlan.fromLabel(planName).getDiskSpace();
        singleDomainHostingPage.assertPlanSpec(planName, expected);
    }

    @Then("the {string} SDH plan includes its bandwidth")
    public void assertPlanBandwidth(String planName) {
        String expected = SingleDomainHostingPlan.fromLabel(planName).getBandwidth();
        singleDomainHostingPage.assertPlanSpec(planName, expected);
    }

    @Then("the {string} SDH plan includes its subdomain limit")
    public void assertPlanSubdomains(String planName) {
        String expected = SingleDomainHostingPlan.fromLabel(planName).getSubdomains();
        singleDomainHostingPage.assertPlanSpec(planName, expected);
    }

    @Then("the {string} SDH plan includes its email account limit")
    public void assertPlanEmailAccounts(String planName) {
        String expected = SingleDomainHostingPlan.fromLabel(planName).getEmailAccounts();
        singleDomainHostingPage.assertPlanSpec(planName, expected);
    }

    @Then("the {string} SDH plan includes its mailing list limit")
    public void assertPlanMailingLists(String planName) {
        String expected = SingleDomainHostingPlan.fromLabel(planName).getMailingLists();
        singleDomainHostingPage.assertPlanSpec(planName, expected);
    }

    @Then("the {string} SDH plan includes its SSL coverage")
    public void assertPlanSslCoverage(String planName) {
        String expected = SingleDomainHostingPlan.fromLabel(planName).getSslCoverage();
        singleDomainHostingPage.assertPlanSpec(planName, expected);
    }

    @Then("the {string} SDH plan Free SSL indicator matches its plan")
    public void assertPlanFreeSslIndicator(String planName) {
        boolean included = SingleDomainHostingPlan.fromLabel(planName).isFreeSslIncluded();
        singleDomainHostingPage.assertPlanFreeSslIndicator(planName, included);
    }

    @Then("the SDH tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        singleDomainHostingPage.assertTaxNoteDisplays(expectedText);
    }
}
