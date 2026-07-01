package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultipleDomainHostingPage {
    private static final Logger log = LogManager.getLogger(MultipleDomainHostingPage.class);
    private Page page;

    public MultipleDomainHostingPage(Page page) {
        this.page = page;
    }

    public void navigateToMultipleDomainHostingPage() {
        String url = EnvConfig.getMultipleDomainHostingUrl();
        log.info("Navigating to Multiple Domain Hosting page: {}", url);
        page.navigate(url);
        log.info("Navigation complete");
    }

    // ==================== HERO SECTION ==================== //

    public void assertHeroTitleText(String expectedTitle) {
        log.info("Asserting hero title contains: \"{}\"", expectedTitle);
        Locator h1 = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));
        PlaywrightAssertions.assertThat(h1).containsText(expectedTitle);
        log.info("PASSED: hero title contains \"{}\"", expectedTitle);
    }

    public void assertHeroSubtitleText(String expectedSubtitle) {
        log.info("Asserting hero subtitle contains: \"{}\"", expectedSubtitle.substring(0, Math.min(60, expectedSubtitle.length())) + "...");
        Locator h1 = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));
        Locator subtitle = h1.locator(".. >> p").first();
        PlaywrightAssertions.assertThat(subtitle).containsText(expectedSubtitle);
        log.info("PASSED: hero subtitle matches expected copy");
    }

    public void assertSeePricingButtonDisplays(String expectedText) {
        log.info("Asserting See Pricing button displays: \"{}\"", expectedText);
        Locator seePricing = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("See Pricing"));
        PlaywrightAssertions.assertThat(seePricing).containsText(expectedText);
        log.info("PASSED: See Pricing button displays \"{}\"", expectedText);
    }

    public void assertSeePricingButtonHref(String expectedHref) {
        log.info("Asserting See Pricing button links to: \"{}\"", expectedHref);
        Locator seePricing = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("See Pricing"));
        PlaywrightAssertions.assertThat(seePricing).hasAttribute("href", expectedHref);
        log.info("PASSED: See Pricing button links to \"{}\"", expectedHref);
    }

    public void clickSeePricingButton() {
        log.info("Clicking See Pricing button");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("See Pricing")).click();
        log.info("Clicked See Pricing button");
    }

    public void assertPricingSectionInViewport() {
        log.info("Asserting pricing section is visible in viewport");
        Locator h2 = pricingSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).isInViewport();
        log.info("PASSED: pricing section is visible in viewport");
    }

    // ==================== HOSTING PLANS ==================== //

    public void assertPlansSectionTitleText(String expectedTitle) {
        log.info("Asserting plans section title contains: \"{}\"", expectedTitle);
        Locator h2 = pricingSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).containsText(expectedTitle);
        log.info("PASSED: plans section title contains \"{}\"", expectedTitle);
    }

    public void assertPlanTitleDisplays(String expectedTitle) {
        log.info("Asserting plan title displays: \"{}\"", expectedTitle);
        Locator planTitle = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(3).setName(expectedTitle));
        PlaywrightAssertions.assertThat(planTitle).containsText(expectedTitle);
        log.info("PASSED: plan title displays \"{}\"", expectedTitle);
    }

    public void assertPlanCardAtPosition(int position, String expectedPlanName) {
        log.info("Asserting plan card at position {}: \"{}\"", position, expectedPlanName);
        Locator planTitleAtPosition = pricingSection()
                .getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3))
                .nth(position - 1);
        PlaywrightAssertions.assertThat(planTitleAtPosition).hasText(expectedPlanName);
        log.info("PASSED: plan card at position {} is \"{}\"", position, expectedPlanName);
    }

    private Locator pricingSection() {
        return page.locator("#pricing");
    }

    private Locator getPlanCard(String planName) {
        return page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(3).setName(planName)).locator("..");
    }

    public void assertPlanPrice(String planName, String expectedPrice) {
        log.info("Asserting [{}] plan price: \"{}\"", planName, expectedPrice);
        PlaywrightAssertions.assertThat(getPlanCard(planName)).containsText(expectedPrice);
        log.info("PASSED: [{}] plan price \"{}\"", planName, expectedPrice);
    }

    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        log.info("Asserting [{}] plan billing period: \"{}\"", planName, expectedPeriod);
        PlaywrightAssertions.assertThat(getPlanCard(planName)).containsText(expectedPeriod);
        log.info("PASSED: [{}] plan billing period \"{}\"", planName, expectedPeriod);
    }

    public void assertPlanDescription(String planName, String expectedDescription) {
        log.info("Asserting [{}] plan description: \"{}\"", planName, expectedDescription.substring(0, Math.min(60, expectedDescription.length())) + "...");
        PlaywrightAssertions.assertThat(getPlanCard(planName)).containsText(expectedDescription);
        log.info("PASSED: [{}] plan description matches expected copy", planName);
    }

    private static final String SELECTED_BUTTON_TEXT   = "✓ Selected";
    private static final String UNSELECTED_BUTTON_TEXT = "Select";

    private Locator getPlanSelectButton(String planName) {
        return getPlanCard(planName).locator("button[aria-pressed]");
    }

    public void assertPlanIsInSelectedState(String planName) {
        log.info("Asserting plan is in selected state: \"{}\"", planName);
        Locator btn = getPlanSelectButton(planName);
        PlaywrightAssertions.assertThat(btn).hasAttribute("aria-pressed", "true");
        PlaywrightAssertions.assertThat(btn).hasText(SELECTED_BUTTON_TEXT);
        log.info("PASSED: plan \"{}\" is in selected state (aria-pressed=true, shows \"{}\")", planName, SELECTED_BUTTON_TEXT);
    }

    public void assertPlanIsInUnselectedState(String planName) {
        log.info("Asserting plan is in unselected state: \"{}\"", planName);
        Locator btn = getPlanSelectButton(planName);
        PlaywrightAssertions.assertThat(btn).hasAttribute("aria-pressed", "false");
        PlaywrightAssertions.assertThat(btn).hasText(UNSELECTED_BUTTON_TEXT);
        log.info("PASSED: plan \"{}\" is in unselected state (aria-pressed=false, shows \"{}\")", planName, UNSELECTED_BUTTON_TEXT);
    }

    public void selectPlan(String planName) {
        log.info("Selecting plan: \"{}\"", planName);
        getPlanSelectButton(planName).click();
        log.info("Clicked toggle button on plan: \"{}\"", planName);
    }

    public void assertCtaReflectsPlan(String planName) {
        String expectedCtaText = "Get " + planName;
        log.info("Asserting CTA reflects plan: \"{}\" (expecting \"{}\")", planName, expectedCtaText);
        Locator cta = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedCtaText));
        PlaywrightAssertions.assertThat(cta).isVisible();
        log.info("PASSED: CTA reflects plan \"{}\" (shows \"{}\")", planName, expectedCtaText);
    }

    public void assertCtaHrefForPlan(String planName, String expectedHref) {
        String expectedCtaText = "Get " + planName;
        log.info("Asserting [{}] CTA links to: \"{}\"", planName, expectedHref);
        Locator cta = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedCtaText));
        PlaywrightAssertions.assertThat(cta).hasAttribute("href", expectedHref);
        log.info("PASSED: [{}] CTA links to \"{}\"", planName, expectedHref);
    }

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        PlaywrightAssertions.assertThat(page.getByText(expectedText).first()).isVisible();
        log.info("PASSED: tax note displays \"{}\"", expectedText);
    }

    // ==================== APPLY TO DOMAIN FIELD ==================== //

    private Locator domainField() {
        return pricingSection().getByRole(AriaRole.SEARCHBOX);
    }

    private Locator applyToLabel() {
        return pricingSection().locator("p[class*='applyLabel']");
    }

    public void assertApplyToLabelDisplays(String expectedLabel) {
        log.info("Asserting Apply to label displays: \"{}\"", expectedLabel);
        PlaywrightAssertions.assertThat(applyToLabel()).containsText(expectedLabel);
        log.info("PASSED: Apply to label displays \"{}\"", expectedLabel);
    }

    public void assertDomainFieldVisible() {
        log.info("Asserting domain input field is visible");
        PlaywrightAssertions.assertThat(domainField()).isVisible();
        log.info("PASSED: domain input field is visible");
    }

    public void assertDomainFieldPlaceholder(String expectedPlaceholder) {
        log.info("Asserting domain input placeholder: \"{}\"", expectedPlaceholder);
        PlaywrightAssertions.assertThat(domainField()).hasAttribute("placeholder", expectedPlaceholder);
        log.info("PASSED: domain input placeholder \"{}\"", expectedPlaceholder);
    }

    public void fillDomainField(String value) {
        log.info("Filling domain input with: \"{}\"", value);
        domainField().fill(value);
        log.info("Filled domain input with \"{}\"", value);
    }

    public void assertDomainFieldValue(String expectedValue) {
        log.info("Asserting domain input value: \"{}\"", expectedValue);
        PlaywrightAssertions.assertThat(domainField()).hasValue(expectedValue);
        log.info("PASSED: domain input value \"{}\"", expectedValue);
    }

    // ==================== PLAN INCLUSIONS ==================== //

    // CSS module hash sits in the middle of the class name; the suffix is stable.
    private static final String INCLUDED_CLASS_SUFFIX = "inclusionIncluded";
    private static final String EXCLUDED_CLASS_SUFFIX = "inclusionExcluded";
    private static final String EXCLUDED_ICON_CLASS_SUFFIX = "xIcon";

    private Locator getPlanFeatureItem(String planName, String feature, String classSuffix) {
        return getPlanCard(planName)
                .locator("li[class*='" + classSuffix + "']")
                .filter(new Locator.FilterOptions().setHasText(feature));
    }

    public void assertPlanIncludesFeature(String planName, String feature) {
        log.info("Asserting [{}] plan includes feature: \"{}\"", planName, feature);
        Locator item = getPlanFeatureItem(planName, feature, INCLUDED_CLASS_SUFFIX);
        PlaywrightAssertions.assertThat(item).isVisible();
        // Exact-match guard: hasText normalizes whitespace and asserts equality,
        // preventing substring collisions like "40 GB" also matching "140 GB disk space".
        PlaywrightAssertions.assertThat(item).hasText(feature);
        log.info("PASSED: [{}] plan includes \"{}\"", planName, feature);
    }

    public void assertPlanExcludesFeature(String planName, String feature) {
        log.info("Asserting [{}] plan excludes feature: \"{}\" (expecting X icon)", planName, feature);
        Locator item = getPlanFeatureItem(planName, feature, EXCLUDED_CLASS_SUFFIX);
        PlaywrightAssertions.assertThat(item).isVisible();
        PlaywrightAssertions.assertThat(item).hasText(feature);
        PlaywrightAssertions.assertThat(item.locator("svg[class*='" + EXCLUDED_ICON_CLASS_SUFFIX + "']")).isVisible();
        log.info("PASSED: [{}] plan excludes \"{}\" (X icon present)", planName, feature);
    }
}
