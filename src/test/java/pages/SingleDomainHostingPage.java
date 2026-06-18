package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
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
        Locator h2 = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).isInViewport();
        log.info("PASSED: pricing section is visible in viewport");
    }

    // ==================== HOSTING PLANS ==================== //

    public void assertPlansSectionTitleText(String expectedTitle) {
        log.info("Asserting plans section title contains: \"{}\"", expectedTitle);
        Locator h2 = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(2));
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

    private static final String SELECTED_LABEL = "✓ Selected";
    private static final String UNSELECTED_LABEL = "Select";

    private Locator getPlanStateLabel(String planName, String stateLabel) {
        return getPlanCard(planName).getByText(stateLabel, new Locator.GetByTextOptions().setExact(true)).first();
    }

    public void assertPlanIsInSelectedState(String planName) {
        log.info("Asserting plan is in selected state: \"{}\"", planName);
        PlaywrightAssertions.assertThat(getPlanStateLabel(planName, SELECTED_LABEL)).isVisible();
        log.info("PASSED: plan \"{}\" is in selected state (shows \"{}\" label)", planName, SELECTED_LABEL);
    }

    public void assertPlanIsInUnselectedState(String planName) {
        log.info("Asserting plan is in unselected state: \"{}\"", planName);
        PlaywrightAssertions.assertThat(getPlanStateLabel(planName, UNSELECTED_LABEL)).isVisible();
        log.info("PASSED: plan \"{}\" is in unselected state (shows \"{}\" label)", planName, UNSELECTED_LABEL);
    }

    public void selectPlan(String planName) {
        log.info("Selecting plan: \"{}\"", planName);
        getPlanStateLabel(planName, UNSELECTED_LABEL).click();
        log.info("Clicked \"{}\" on plan: \"{}\"", UNSELECTED_LABEL, planName);
    }

    public void assertCtaReflectsPlan(String planName) {
        String expectedCtaText = "Get " + planName;
        log.info("Asserting CTA reflects plan: \"{}\" (expecting \"{}\")", planName, expectedCtaText);
        Locator cta = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedCtaText));
        PlaywrightAssertions.assertThat(cta).isVisible();
        log.info("PASSED: CTA reflects plan \"{}\" (shows \"{}\")", planName, expectedCtaText);
    }

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        PlaywrightAssertions.assertThat(page.getByText(expectedText).first()).isVisible();
        log.info("PASSED: tax note displays \"{}\"", expectedText);
    }
}
