package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import config.PricingBlockClasses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VpsPage {
    private static final Logger log = LogManager.getLogger(VpsPage.class);
    private Page page;

    public VpsPage(Page page) {
        this.page = page;
    }

    public void navigateToVpsPage() {
        String url = EnvConfig.getVpsUrl();
        log.info("Navigating to VPS page: {}", url);
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
        // Under Playwright's test browser this locator resolves to 2 identical
        // subheadline <p>s (SSR + hydration duplicate — the pattern is stable,
        // both copies always match). .first() gives us one element to assert on
        // without strict-mode noise; hasText requires an exact whitespace-
        // normalized match so trailing/leading characters can't slip through.
        Locator subtitle = page.locator("p[class*='subheadline']").first();
        PlaywrightAssertions.assertThat(subtitle).hasText(expectedSubtitle);
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

    // ==================== HOSTING FEATURES SECTION ==================== //

    public void assertHostingFeaturesSectionTitle(String expectedTitle) {
        log.info("Asserting Hosting Features section title: \"{}\"", expectedTitle);
        Locator h2 = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(2).setName(expectedTitle));
        PlaywrightAssertions.assertThat(h2).isVisible();
        log.info("PASSED: Hosting Features section title \"{}\"", expectedTitle);
    }

    public void assertHostingFeaturesCopyContains(String fragment) {
        log.info("Asserting Hosting Features copy contains: \"{}\"", fragment);
        // Climb to the section container (parent of the h2) so the assertion
        // scopes to the surrounding paragraph without matching unrelated copy.
        Locator section = page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(2).setName("Hosting Features"))
                .locator("xpath=..");
        PlaywrightAssertions.assertThat(section).containsText(fragment);
        log.info("PASSED: Hosting Features copy contains \"{}\"", fragment);
    }

    // ==================== GET FULL CONTROL SECTION ==================== //

    public void assertGetFullControlSectionTitle(String expectedTitle) {
        log.info("Asserting Get full control section title: \"{}\"", expectedTitle);
        Locator h2 = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(2).setName(expectedTitle));
        PlaywrightAssertions.assertThat(h2).isVisible();
        log.info("PASSED: Get full control section title \"{}\"", expectedTitle);
    }

    public void assertGetFullControlSubHeadingDisplays(String subHeading) {
        log.info("Asserting Get full control sub-heading: \"{}\"", subHeading);
        // Scope to the section rooted at the h2's parent so we don't collide
        // with h3s outside "Get full control..." (e.g. footer / nav / plans).
        Locator sectionRoot = page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(2).setName("Get full control of your site"))
                .locator("xpath=..");
        Locator h3 = sectionRoot.getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3).setName(subHeading));
        PlaywrightAssertions.assertThat(h3).isVisible();
        log.info("PASSED: Get full control sub-heading \"{}\"", subHeading);
    }

    public void assertGetFullControlSubDescriptionContains(String subHeading, String fragment) {
        log.info("Asserting [{}] Get full control description contains: \"{}\"", subHeading, fragment);
        // The description <p> is a sibling of its h3, so climb one level to
        // scope containsText to the individual sub-feature block.
        Locator container = page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(3).setName(subHeading))
                .locator("xpath=..");
        PlaywrightAssertions.assertThat(container).containsText(fragment);
        log.info("PASSED: [{}] description contains \"{}\"", subHeading, fragment);
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
        // Climb to the nearest ancestor whose class list carries "__card" as a
        // whole token. Substring-matching "__card" alone would collide with
        // future wrapper classes like "__cardHeader"; the concat-with-spaces
        // trick emulates a token match in XPath 1.0.
        return page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(3).setName(planName))
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__card ')][1]");
    }

    public void assertPlanPrice(String planName, String expectedPrice) {
        log.info("Asserting [{}] plan price: \"{}\"", planName, expectedPrice);
        // The card DOM renders "$", the numeric portion, and a footnote "*" in
        // three separate spans, which Playwright normalizes to "$ 175.00 *".
        // A single containsText("$175.00") would fail on that whitespace, so
        // callers pass the numeric portion only (e.g. "175.00") and we verify
        // the "$" currency marker on the same card in the same step.
        Locator card = getPlanCard(planName);
        PlaywrightAssertions.assertThat(card).containsText(expectedPrice);
        PlaywrightAssertions.assertThat(card).containsText("$");
        log.info("PASSED: [{}] plan price \"{}\"", planName, expectedPrice);
    }

    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        log.info("Asserting [{}] plan billing period: \"{}\"", planName, expectedPeriod);
        PlaywrightAssertions.assertThat(getPlanCard(planName)).containsText(expectedPeriod);
        log.info("PASSED: [{}] plan billing period \"{}\"", planName, expectedPeriod);
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

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        PlaywrightAssertions.assertThat(page.getByText(expectedText).first()).isVisible();
        log.info("PASSED: tax note displays \"{}\"", expectedText);
    }

    // ==================== PLAN INCLUSIONS ==================== //

    public void assertPlanInclusionsHeading(String planName, String expectedHeading) {
        log.info("Asserting [{}] plan inclusions heading: \"{}\"", planName, expectedHeading);
        Locator heading = getPlanCard(planName)
                .locator("p[class*='" + PricingBlockClasses.INCLUSIONS_HEADING + "']");
        PlaywrightAssertions.assertThat(heading).hasText(expectedHeading);
        log.info("PASSED: [{}] plan inclusions heading \"{}\"", planName, expectedHeading);
    }

    private Locator getPlanFeatureItem(String planName, String feature, String classSuffix) {
        return getPlanCard(planName)
                .locator("li[class*='" + classSuffix + "']")
                .filter(new Locator.FilterOptions().setHasText(feature));
    }

    public void assertPlanIncludesFeature(String planName, String feature) {
        log.info("Asserting [{}] plan includes feature: \"{}\" (expecting ✓ icon)", planName, feature);
        Locator item = getPlanFeatureItem(planName, feature, PricingBlockClasses.INCLUDED_SUFFIX);
        PlaywrightAssertions.assertThat(item).isVisible();
        // Exact-match guard: hasText normalizes whitespace and asserts equality,
        // preventing substring collisions like "2GB RAM" also matching a longer row.
        PlaywrightAssertions.assertThat(item).hasText(feature);
        // Visual guard: a missing/broken checkIcon svg would slip past a class +
        // text check alone; assert the ✓ icon actually renders on the row.
        PlaywrightAssertions.assertThat(item.locator("svg[class*='" + PricingBlockClasses.CHECK_ICON + "']")).isVisible();
        log.info("PASSED: [{}] plan includes \"{}\" (✓ icon present)", planName, feature);
    }

    // ==================== INQUIRY FORM ==================== //
    // NOTE: The inquiry form (a shared FormBlock component) is rendered twice
    // by SSR + hydration — same pattern the hero subheadline exhibits.
    // Every locator here uses `.first()` to satisfy Playwright's strict mode.
    // Both copies always render identical content, so asserting on the first
    // proves the copy on the visible instance too.

    public void assertInquiryIntroDisplays(String expectedText) {
        log.info("Asserting inquiry intro displays: \"{}\"", expectedText);
        PlaywrightAssertions.assertThat(page.getByText(expectedText).first()).isVisible();
        log.info("PASSED: inquiry intro displays");
    }

    public void assertInquiryFieldLabelDisplays(String labelText) {
        log.info("Asserting inquiry field label displays: \"{}\"", labelText);
        // Exact match so "Name of Contact Person" doesn't collide with "5. Name of Contact Person".
        PlaywrightAssertions.assertThat(
                page.getByText(labelText, new Page.GetByTextOptions().setExact(true)).first()
        ).isVisible();
        log.info("PASSED: inquiry field label \"{}\"", labelText);
    }

    public void assertInquiryFieldPlaceholder(String labelText, String placeholder) {
        log.info("Asserting inquiry field [{}] placeholder: \"{}\"", labelText, placeholder);
        Locator field = page.getByLabel(labelText).first();
        PlaywrightAssertions.assertThat(field).hasAttribute("placeholder", placeholder);
        log.info("PASSED: inquiry field [{}] placeholder \"{}\"", labelText, placeholder);
    }

    public void assertInquirySubmitButtonVisible() {
        log.info("Asserting inquiry Submit button is visible");
        Locator submit = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).first();
        PlaywrightAssertions.assertThat(submit).isVisible();
        log.info("PASSED: inquiry Submit button is visible");
    }
}
