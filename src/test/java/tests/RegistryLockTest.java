package tests;

import contexts.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.RegistryLockPage;

public class RegistryLockTest {

    private final RegistryLockPage registryLockPage;

    public RegistryLockTest(TestContext context) {
        this.registryLockPage = new RegistryLockPage(context.page);
    }

    @Given("the user navigates to the Registry Lock page")
    public void navigateToRegistryLockPage() {
        registryLockPage.navigateToRegistryLockPage();
    }

    // ==================== HERO SECTION ==================== //

    @Then("the RL hero tagline displays {string}")
    public void assertHeroTagline(String expectedTagline) {
        registryLockPage.assertHeroTagline(expectedTagline);
    }

    @Then("the RL hero title displays {string}")
    public void assertHeroTitle(String expectedTitle) {
        registryLockPage.assertHeroTitleText(expectedTitle);
    }

    @Then("the RL hero subtitle displays {string}")
    public void assertHeroSubtitle(String expectedSubtitle) {
        registryLockPage.assertHeroSubtitleText(expectedSubtitle);
    }

    @Then("the RL hero image src contains {string}")
    public void assertHeroImageSrc(String fragment) {
        registryLockPage.assertHeroImageSrcContains(fragment);
    }

    @Then("the RL Buy Now button displays {string}")
    public void assertBuyNowButtonDisplays(String expectedText) {
        registryLockPage.assertBuyNowButtonDisplays(expectedText);
    }

    @Then("the RL Buy Now button links to {string}")
    public void assertBuyNowButtonHref(String expectedHref) {
        registryLockPage.assertBuyNowButtonHref(expectedHref);
    }

    @When("the user clicks the RL Buy Now button")
    public void clickBuyNowButton() {
        registryLockPage.clickBuyNowButton();
    }

    @Then("the RL pricing section is visible in the viewport")
    public void assertPricingSectionInViewport() {
        registryLockPage.assertPricingSectionInViewport();
    }

    // ==================== WHY SECTION ==================== //

    @Then("the RL why section title displays {string}")
    public void assertWhySectionTitle(String expectedTitle) {
        registryLockPage.assertWhySectionTitle(expectedTitle);
    }

    @Then("the RL why intro includes {string}")
    public void assertWhyIntroIncludes(String fragment) {
        registryLockPage.assertWhyIntroContains(fragment);
    }

    // ==================== INFO BLOCKS ==================== //

    @Then("the RL info block {int} heading displays {string}")
    public void assertInfoBlockHeading(int position, String expectedHeading) {
        registryLockPage.assertInfoBlockHeading(position, expectedHeading);
    }

    @Then("the RL info block {int} description includes {string}")
    public void assertInfoBlockDescription(int position, String fragment) {
        registryLockPage.assertInfoBlockDescriptionContains(position, fragment);
    }

    @Then("the RL info block {int} image src contains {string}")
    public void assertInfoBlockImageSrc(int position, String fragment) {
        registryLockPage.assertInfoBlockImageSrcContains(position, fragment);
    }

    // ==================== SUBSCRIBE STEPS ==================== //

    @Then("the RL Subscribe section title displays {string}")
    public void assertSubscribeSectionTitle(String expectedTitle) {
        registryLockPage.assertSubscribeSectionTitle(expectedTitle);
    }

    @Then("the RL Subscribe section has {int} steps")
    public void assertSubscribeStepCount(int expected) {
        registryLockPage.assertSubscribeStepCount(expected);
    }

    @Then("the RL Subscribe step at position {int} shows the number {string}")
    public void assertSubscribeStepNumber(int position, String expectedNumber) {
        registryLockPage.assertSubscribeStepNumber(position, expectedNumber);
    }

    @Then("the RL Subscribe step at position {int} heading displays {string}")
    public void assertSubscribeStepHeading(int position, String expectedHeading) {
        registryLockPage.assertSubscribeStepHeading(position, expectedHeading);
    }

    @Then("the RL Subscribe step at position {int} description includes {string}")
    public void assertSubscribeStepDescription(int position, String fragment) {
        registryLockPage.assertSubscribeStepDescriptionContains(position, fragment);
    }

    // ==================== PRICING SECTION ==================== //

    @Then("the RL plans section title displays {string}")
    public void assertPlansSectionTitle(String expectedTitle) {
        registryLockPage.assertPlansSectionTitleText(expectedTitle);
    }

    @Then("the RL default selected plan is {string}")
    public void assertDefaultSelectedPlan(String planName) {
        registryLockPage.assertPlanIsInSelectedState(planName);
    }

    @Then("the RL CTA button displays {string}")
    public void assertCtaButtonDisplays(String expectedText) {
        registryLockPage.assertCtaButtonDisplays(expectedText);
    }

    @Then("the RL CTA button links to {string}")
    public void assertCtaButtonHref(String expectedHref) {
        registryLockPage.assertCtaButtonHref(expectedHref);
    }

    @Then("the RL plan title displays {string}")
    public void assertPlanTitle(String expectedTitle) {
        registryLockPage.assertPlanTitle(expectedTitle);
    }

    @Then("the RL plan supported TLDs paragraph displays {string}")
    public void assertPlanSupportedTlds(String expectedText) {
        registryLockPage.assertPlanSupportedTldsDisplays(expectedText);
    }

    @Then("the RL plan displays the price {string}")
    public void assertPlanPrice(String expectedComposedText) {
        registryLockPage.assertPlanPriceRowText(expectedComposedText);
    }

    @Then("the RL plan displays the billing period {string}")
    public void assertPlanBillingPeriod(String expectedPeriod) {
        registryLockPage.assertPlanBillingPeriod(expectedPeriod);
    }

    @Then("the RL plan inclusions heading displays {string}")
    public void assertPlanInclusionsHeading(String expectedHeading) {
        registryLockPage.assertPlanInclusionsHeading(expectedHeading);
    }

    @Then("the RL plan includes {string}")
    public void assertPlanIncludes(String feature) {
        registryLockPage.assertPlanIncludes(feature);
    }

    @Then("the RL tax note displays {string}")
    public void assertTaxNote(String expectedText) {
        registryLockPage.assertTaxNoteDisplays(expectedText);
    }
}
