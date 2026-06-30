package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

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

    public void assertPlanSpec(String planName, String expectedSpec) {
        log.info("Asserting [{}] plan includes spec: \"{}\"", planName, expectedSpec);
        PlaywrightAssertions.assertThat(getPlanCard(planName)).containsText(expectedSpec);
        log.info("PASSED: [{}] plan includes \"{}\"", planName, expectedSpec);
    }

    /**
     * Asserts the Free SSL row's icon matches the plan's actual coverage —
     * checkIcon (✓) when included, xIcon (✗) when not. The text "Free SSL"
     * appears on every card, so the icon is the real signal of inclusion.
     */
    public void assertPlanFreeSslIndicator(String planName, boolean included) {
        log.info("Asserting [{}] plan Free SSL indicator: {}", planName, included ? "included (✓)" : "not included (✗)");
        Locator freeSslRow = getPlanCard(planName).locator("li")
                .filter(new Locator.FilterOptions().setHasText("Free SSL"));
        String expectedIconClass = included ? "checkIcon" : "xIcon";
        PlaywrightAssertions.assertThat(freeSslRow.locator("svg"))
                .hasAttribute("class", Pattern.compile(expectedIconClass));
        log.info("PASSED: [{}] plan Free SSL row uses {} icon", planName, expectedIconClass);
    }

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        PlaywrightAssertions.assertThat(page.getByText(expectedText).first()).isVisible();
        log.info("PASSED: tax note displays \"{}\"", expectedText);
    }
}
