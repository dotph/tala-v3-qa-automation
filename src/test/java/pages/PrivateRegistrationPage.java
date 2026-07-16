package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import config.PricingBlockClasses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrivateRegistrationPage {
    private static final Logger log = LogManager.getLogger(PrivateRegistrationPage.class);
    private Page page;

    public PrivateRegistrationPage(Page page) {
        this.page = page;
    }

    public void navigateToPrivateRegistrationPage() {
        String url = EnvConfig.getPrivateRegistrationUrl();
        log.info("Navigating to Private Registration page: {}", url);
        page.navigate(url);
        log.info("Navigation complete");
    }

    // ==================== HERO SECTION ==================== //

    public void assertHeroKickerText(String expectedText) {
        log.info("Asserting hero kicker displays: \"{}\"", expectedText);
        // The eyebrow label ("Security & Add-ons") is emitted by HeroBlock as
        // a <span class="...__eyebrow"> above the H1. It's a distinct copy
        // element from the H1/subtitle, so a typo/capitalization drift there
        // wouldn't surface via any hero-title/subtitle assertion — hence its
        // own step. Anchored to HeroBlock so a future __eyebrow span in a
        // lower section can't collide, and the class suffix survives the
        // per-build CSS-module hash rotation. No .first() — strict mode
        // guards uniqueness within the hero.
        Locator eyebrow = page.locator("[class*='HeroBlock']").locator("span[class*='eyebrow']");
        PlaywrightAssertions.assertThat(eyebrow).isVisible();
        PlaywrightAssertions.assertThat(eyebrow).hasText(expectedText);
        log.info("PASSED: hero kicker displays \"{}\"", expectedText);
    }

    public void assertHeroTitleText(String expectedTitle) {
        log.info("Asserting hero title contains: \"{}\"", expectedTitle);
        Locator h1 = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));
        PlaywrightAssertions.assertThat(h1).containsText(expectedTitle);
        log.info("PASSED: hero title contains \"{}\"", expectedTitle);
    }

    public void assertHeroSubtitleText(String expectedSubtitle) {
        log.info("Asserting hero subtitle contains: \"{}\"", expectedSubtitle.substring(0, Math.min(60, expectedSubtitle.length())) + "...");
        // Under Playwright's test browser this locator resolves to 2 identical
        // subheadline <p>s (SSR + hydration duplicate — same pattern as Woo/SDH,
        // both copies always match). .first() gives us one element to assert on
        // without strict-mode noise; hasText requires an exact whitespace-
        // normalized match so trailing/leading characters can't slip through.
        Locator subtitle = page.locator("p[class*='subheadline']").first();
        // isVisible gate mirrors the tax-note assertion — a DOM-only subtitle
        // (display:none or 0-height) would otherwise silently pass the copy
        // check.
        PlaywrightAssertions.assertThat(subtitle).isVisible();
        PlaywrightAssertions.assertThat(subtitle).hasText(expectedSubtitle);
        log.info("PASSED: hero subtitle matches expected copy");
    }

    public void assertBuyNowButtonDisplays(String expectedText) {
        log.info("Asserting Buy Now button displays: \"{}\"", expectedText);
        // Accessible-name locator + exact hasText: text drift causes a clear
        // element-not-found timeout; the isVisible + hasText pair catches
        // display-hidden state and whitespace / casing regressions that
        // containsText would let slide.
        Locator buyNow = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedText));
        PlaywrightAssertions.assertThat(buyNow).isVisible();
        PlaywrightAssertions.assertThat(buyNow).hasText(expectedText);
        log.info("PASSED: Buy Now button displays \"{}\"", expectedText);
    }

    public void assertBuyNowButtonHref(String expectedHref) {
        log.info("Asserting Buy Now button links to: \"{}\"", expectedHref);
        Locator buyNow = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Buy Now"));
        PlaywrightAssertions.assertThat(buyNow).hasAttribute("href", expectedHref);
        log.info("PASSED: Buy Now button links to \"{}\"", expectedHref);
    }

    public void clickBuyNowButton() {
        log.info("Clicking Buy Now button");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Buy Now")).click();
        log.info("Clicked Buy Now button");
    }

    public void assertPricingSectionInViewport() {
        log.info("Asserting pricing section is visible in viewport");
        Locator h2 = pricingSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).isInViewport();
        log.info("PASSED: pricing section is visible in viewport");
    }

    // ==================== "KEEP YOUR PERSONAL INFORMATION CONFIDENTIAL" SECTION ==================== //

    public void assertConfidentialSectionTitle(String expectedTitle) {
        log.info("Asserting Confidential section title: \"{}\"", expectedTitle);
        Locator h2 = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(2).setName(expectedTitle));
        PlaywrightAssertions.assertThat(h2).isVisible();
        log.info("PASSED: Confidential section title \"{}\"", expectedTitle);
    }

    public void assertConfidentialSubHeadingDisplays(String subHeading) {
        log.info("Asserting Confidential sub-heading: \"{}\"", subHeading);
        Locator h3 = confidentialSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3).setName(subHeading));
        PlaywrightAssertions.assertThat(h3).isVisible();
        log.info("PASSED: Confidential sub-heading \"{}\"", subHeading);
    }

    public void assertConfidentialSubDescriptionContains(String subHeading, String fragment) {
        log.info("Asserting [{}] Confidential description contains: \"{}\"", subHeading, fragment);
        // Anchor to the description <p> following the sub-heading h3 via
        // following-sibling::p[1]. Pins the assertion to the exact description
        // belonging to this h3 regardless of grid/wrapper shape, so a fragment
        // from a sibling sub-feature can't silently satisfy the assertion.
        Locator description = confidentialSection()
                .getByRole(AriaRole.HEADING,
                        new Locator.GetByRoleOptions().setLevel(3).setName(subHeading))
                .locator("xpath=following-sibling::p[1]");
        PlaywrightAssertions.assertThat(description).containsText(fragment);
        log.info("PASSED: [{}] description contains \"{}\"", subHeading, fragment);
    }

    private Locator confidentialSection() {
        // Climb to the section's shared parent so h3 lookups don't collide
        // with h3s outside "Keep your personal information..." (footer / nav).
        return page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(2).setName("Keep your personal information confidential."))
                .locator("xpath=..");
    }

    // ==================== HOSTING PLANS ==================== //

    public void assertPlansSectionTitleText(String expectedTitle) {
        log.info("Asserting plans section title contains: \"{}\"", expectedTitle);
        Locator h2 = pricingSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).containsText(expectedTitle);
        log.info("PASSED: plans section title contains \"{}\"", expectedTitle);
    }

    public void assertPlanCardCount(int expected) {
        log.info("Asserting plans section has {} plan card(s)", expected);
        // Future-proof guard: Private Registration ships one plan today, so
        // the per-plan scenarios pin that single card. A silent second tier
        // (or the card disappearing entirely) would still pass every other
        // assertion — hence this count check. Scoped to pricingSection so
        // navbar / footer h3s can't inflate the count.
        Locator cards = pricingSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(cards).hasCount(expected);
        log.info("PASSED: plans section has {} plan card(s)", expected);
    }

    public void assertPlanTitleDisplays(String expectedTitle) {
        log.info("Asserting plan title displays: \"{}\"", expectedTitle);
        // Same tightening as assertBuyNowButtonDisplays: locate by accessible
        // name (any text drift surfaces as element-not-found), then gate on
        // isVisible and pin the exact rendered text with hasText so hidden or
        // whitespace-drifted h3s don't slip past. setLevel(3) disambiguates
        // from the H1 hero and H2 pricing-section headings which share the
        // literal "Private Registration" text on this page.
        Locator planTitle = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(3).setName(expectedTitle));
        PlaywrightAssertions.assertThat(planTitle).isVisible();
        PlaywrightAssertions.assertThat(planTitle).hasText(expectedTitle);
        log.info("PASSED: plan title displays \"{}\"", expectedTitle);
    }

    private Locator pricingSection() {
        return page.locator("#pricing");
    }

    private Locator getPlanCard(String planName) {
        // Climb to the nearest ancestor whose class list carries "__card" as
        // a whole token. Substring-matching "__card" alone would collide with
        // future wrapper classes like "__cardHeader"; the concat-with-spaces
        // trick emulates a token match in XPath 1.0. Same helper shape as
        // WoocommercePage.
        return page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(3).setName(planName))
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__card ')][1]");
    }

    public void assertPlanSubtitle(String planName, String expectedSubtitle) {
        log.info("Asserting [{}] plan subtitle: \"{}\"", planName, expectedSubtitle);
        // The __tagline <p> renders between the h3 and the priceRow — on this
        // page it carries the per-billing-unit descriptor ("Per domain, per
        // year"). hasText normalizes whitespace and asserts equality so the
        // copy is pinned exactly.
        Locator tagline = getPlanCard(planName).locator("p[class*='tagline']");
        PlaywrightAssertions.assertThat(tagline).hasText(expectedSubtitle);
        log.info("PASSED: [{}] plan subtitle \"{}\"", planName, expectedSubtitle);
    }

    public void assertPlanPrice(String planName, String expectedPrice) {
        log.info("Asserting [{}] plan price: \"${}\"", planName, expectedPrice);
        // The priceRow renders three adjacent inline spans with no whitespace
        // between them:
        //   <span priceDollar>$</span>
        //   <span price>10.00</span>
        //   <span priceStar>*</span>
        // Playwright's hasText reads textContent (not innerText), so adjacent
        // inline children concatenate without whitespace and the row's full
        // text is "$10.00*". Asserting the composed string pins ALL of:
        // currency symbol, price value, and the footnote-asterisk marker in
        // one call. Unlike Woo there's no promo strikethrough here, so no
        // originalPrice / text-decoration guard.
        Locator priceRow = getPlanCard(planName).locator("[class*='priceRow']");
        PlaywrightAssertions.assertThat(priceRow).hasText("$" + expectedPrice + "*");
        log.info("PASSED: [{}] plan price \"${}\"", planName, expectedPrice);
    }

    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        log.info("Asserting [{}] plan billing period: \"{}\"", planName, expectedPeriod);
        // Scope to the __period <p> specifically (rather than card-level
        // containsText) so unrelated text on the card can't accidentally
        // satisfy a substring assertion on a shorter expected value.
        Locator period = getPlanCard(planName).locator("p[class*='period']");
        PlaywrightAssertions.assertThat(period).hasText(expectedPeriod);
        log.info("PASSED: [{}] plan billing period \"{}\"", planName, expectedPeriod);
    }

    private static final String SELECTED_BUTTON_TEXT = "✓ Selected";

    public void assertPlanIsInSelectedState(String planName) {
        log.info("Asserting plan is in selected state: \"{}\"", planName);
        // Only one plan on this page, and it renders permanently selected;
        // there's no un-selected sibling to toggle to. Still assert both the
        // aria-pressed contract and the button label so a stealth state flip
        // (e.g. aria-pressed removed but label unchanged) can't slip past.
        Locator btn = getPlanCard(planName).locator("button[aria-pressed]");
        PlaywrightAssertions.assertThat(btn).hasAttribute("aria-pressed", "true");
        PlaywrightAssertions.assertThat(btn).hasText(SELECTED_BUTTON_TEXT);
        log.info("PASSED: plan \"{}\" is in selected state (aria-pressed=true, shows \"{}\")", planName, SELECTED_BUTTON_TEXT);
    }

    public void assertAddToCartButtonDisplays(String expectedText) {
        log.info("Asserting Add to Cart button displays: \"{}\"", expectedText);
        Locator cta = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedText));
        PlaywrightAssertions.assertThat(cta).isVisible();
        PlaywrightAssertions.assertThat(cta).hasText(expectedText);
        log.info("PASSED: Add to Cart button displays \"{}\"", expectedText);
    }

    public void assertAddToCartButtonHref(String expectedHref) {
        log.info("Asserting Add to Cart button links to: \"{}\"", expectedHref);
        Locator cta = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to Cart"));
        PlaywrightAssertions.assertThat(cta).hasAttribute("href", expectedHref);
        log.info("PASSED: Add to Cart button links to \"{}\"", expectedHref);
    }

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        // .first() disambiguates the SSR + hydration duplicate — same pattern
        // Woo's tax-note assertion hits under parallel load. Both copies
        // always render identical text, so asserting on the first proves the
        // visible instance too.
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
        // Exact-match guard: hasText normalizes whitespace and asserts
        // equality, preventing substring collisions like "identity theft"
        // also matching a longer row.
        PlaywrightAssertions.assertThat(item).hasText(feature);
        // Visual guard: a missing/broken checkIcon svg would slip past a class
        // + text check alone; assert the ✓ icon actually renders on the row.
        PlaywrightAssertions.assertThat(item.locator("svg[class*='" + PricingBlockClasses.CHECK_ICON + "']")).isVisible();
        log.info("PASSED: [{}] plan includes \"{}\" (✓ icon present)", planName, feature);
    }
}
