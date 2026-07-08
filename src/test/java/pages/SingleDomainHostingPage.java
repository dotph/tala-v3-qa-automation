package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import config.PricingBlockClasses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingleDomainHostingPage {
    private static final Logger log = LogManager.getLogger(SingleDomainHostingPage.class);
    private Page page;

    public SingleDomainHostingPage(Page page) {
        this.page = page;
    }

    public void navigateToSingleDomainHostingPage() {
        String url = EnvConfig.getSingleDomainHostingUrl();
        log.info("Navigating to Single Domain Hosting page: {}", url);
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
        // preventing substring collisions like "40 GB" also matching "140 GB disk space".
        PlaywrightAssertions.assertThat(item).hasText(feature);
        // Complementary visual signal (symmetric with assertPlanExcludesFeature): the
        // included row must carry the ✓ checkIcon svg. Without this, a broken/wrong
        // icon on an "included" row would pass on class + text alone.
        PlaywrightAssertions.assertThat(item.locator("svg[class*='" + PricingBlockClasses.CHECK_ICON + "']")).isVisible();
        log.info("PASSED: [{}] plan includes \"{}\" (✓ icon present)", planName, feature);
    }

    public void assertPlanExcludesFeature(String planName, String feature) {
        log.info("Asserting [{}] plan excludes feature: \"{}\" (expecting X icon + dimmed row)", planName, feature);
        Locator item = getPlanFeatureItem(planName, feature, PricingBlockClasses.EXCLUDED_SUFFIX);
        PlaywrightAssertions.assertThat(item).isVisible();
        PlaywrightAssertions.assertThat(item).hasText(feature);
        PlaywrightAssertions.assertThat(item.locator("svg[class*='" + PricingBlockClasses.EXCLUDED_ICON + "']")).isVisible();
        // Complementary visual signal: excluded rows render at opacity 0.5 so
        // they read as "greyed out". If the styling ever drops to opacity 1,
        // the row looks included even though the class/icon still say excluded.
        // Note: hasCSS reads getComputedStyle on the <li> itself. If the design
        // ever moves the dim styling to a wrapper (leaving the <li> at opacity 1),
        // this fails on an otherwise-correct page — update the target element then.
        PlaywrightAssertions.assertThat(item).hasCSS("opacity", "0.5");
        log.info("PASSED: [{}] plan excludes \"{}\" (X icon present, row dimmed)", planName, feature);
    }
}
