package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import config.PricingBlockClasses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SslPage {
    private static final Logger log = LogManager.getLogger(SslPage.class);
    private Page page;

    public SslPage(Page page) {
        this.page = page;
    }

    public void navigateToSslPage() {
        String url = EnvConfig.getSslUrl();
        log.info("Navigating to SSL Certificates page: {}", url);
        page.navigate(url);
        log.info("Navigation complete");
    }

    // ==================== HERO SECTION ==================== //

    public void assertHeroEyebrowText(String expectedText) {
        log.info("Asserting hero eyebrow displays: \"{}\"", expectedText);
        // The eyebrow ("Security & Add-ons") is emitted by HeroBlock as a
        // <span class="...__eyebrow"> above the H1 (same DOM shape as the
        // Private Registration hero — see PrivateRegistrationPage.
        // assertHeroKickerText). Scoped to HeroBlock so a future __eyebrow
        // span in a lower section can't collide; the class suffix survives
        // the per-build CSS-module hash rotation. No .first() — strict mode
        // guards uniqueness within the hero.
        Locator eyebrow = page.locator("[class*='HeroBlock']").locator("span[class*='eyebrow']");
        PlaywrightAssertions.assertThat(eyebrow).isVisible();
        PlaywrightAssertions.assertThat(eyebrow).hasText(expectedText);
        log.info("PASSED: hero eyebrow displays \"{}\"", expectedText);
    }

    public void assertHeroTitleText(String expectedTitle) {
        log.info("Asserting hero title displays: \"{}\"", expectedTitle);
        // hasText (exact after whitespace normalization) per conventions.md —
        // the H1 is a short label with no nested inline markup on the live
        // page (verified: <h1>SSL Certificates</h1>). containsText would
        // accept "SSL Certificates and More" as passing; hasText won't.
        Locator h1 = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));
        PlaywrightAssertions.assertThat(h1).hasText(expectedTitle);
        log.info("PASSED: hero title displays \"{}\"", expectedTitle);
    }

    public void assertHeroSubtitleText(String expectedSubtitle) {
        log.info("Asserting hero subtitle contains: \"{}\"", expectedSubtitle.substring(0, Math.min(60, expectedSubtitle.length())) + "...");
        // Same SSR + hydration duplicate the WooCommerce / MDH pages hit —
        // .first() picks the deterministic one; isVisible + hasText together
        // guard both a DOM-only subtitle and any whitespace / copy drift.
        Locator subtitle = page.locator("p[class*='subheadline']").first();
        PlaywrightAssertions.assertThat(subtitle).isVisible();
        PlaywrightAssertions.assertThat(subtitle).hasText(expectedSubtitle);
        log.info("PASSED: hero subtitle matches expected copy");
    }

    public void assertBuyNowButtonDisplays(String expectedText) {
        log.info("Asserting Buy Now button displays: \"{}\"", expectedText);
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

    // ==================== "SECURE YOUR WEBSITE" SECTION ==================== //

    public void assertSecureYourWebsiteTitle(String expectedTitle) {
        log.info("Asserting Secure Your Website section title: \"{}\"", expectedTitle);
        // hasText per conventions.md — the H2 is a short exact label with no
        // nested markup on the live page. `getByRole(HEADING).setName(...)`
        // does a substring match on the accessible name, so an appended word
        // ("Secure Your Website Today") would pass isVisible alone; hasText
        // asserts equality after whitespace normalization.
        Locator h2 = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(2).setName(expectedTitle));
        PlaywrightAssertions.assertThat(h2).isVisible();
        PlaywrightAssertions.assertThat(h2).hasText(expectedTitle);
        log.info("PASSED: Secure Your Website section title \"{}\"", expectedTitle);
    }

    public void assertSecureYourWebsiteParagraphContains(String fragment) {
        log.info("Asserting Secure Your Website paragraph contains: \"{}\"", fragment);
        // The h2 doesn't share a parent as a direct sibling with the paragraph
        // — ContentBlock wraps the paragraph in a `__body` div (verified live:
        // "<h2>...</h2><div class='...__body'><div><p>...</p></div></div>"),
        // so plain `following-sibling::p[1]` returns nothing. Step into the
        // h2's immediate next sibling div, then descend to the <p>. This is
        // tighter than `parent::*//p[1]` (which would silently pick up any
        // decorative <p> added elsewhere in the section container) and doesn't
        // hard-code the `__body` class suffix so a CSS-module rename doesn't
        // break it.
        Locator paragraph = page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(2).setName("Secure Your Website"))
                .locator("xpath=following-sibling::div[1]//p[1]");
        PlaywrightAssertions.assertThat(paragraph).containsText(fragment);
        log.info("PASSED: Secure Your Website paragraph contains \"{}\"", fragment);
    }

    // ==================== SSL CERTIFICATE PLANS ==================== //

    public void assertPlansSectionTitleText(String expectedTitle) {
        log.info("Asserting plans section title displays: \"{}\"", expectedTitle);
        // hasText per conventions.md — the plans H2 is a short exact heading
        // ("Choose Your SSL Certificate") with no nested inline markup on the
        // live page. containsText would accept accidental appends silently.
        Locator h2 = pricingSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).hasText(expectedTitle);
        log.info("PASSED: plans section title displays \"{}\"", expectedTitle);
    }

    public void assertPlanCardCount(int expected) {
        log.info("Asserting plans section has {} plan card(s)", expected);
        // Guard against a silent extra/dropped tier. Scoped to pricingSection
        // so navbar / footer h3s can't inflate the count.
        Locator cards = pricingSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(cards).hasCount(expected);
        log.info("PASSED: plans section has {} plan card(s)", expected);
    }

    public void assertPlanTitleDisplays(String expectedTitle) {
        log.info("Asserting plan title displays: \"{}\"", expectedTitle);
        Locator planTitle = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(3).setName(expectedTitle));
        PlaywrightAssertions.assertThat(planTitle).isVisible();
        PlaywrightAssertions.assertThat(planTitle).hasText(expectedTitle);
        log.info("PASSED: plan title displays \"{}\"", expectedTitle);
    }

    public void assertPlanCardAtPosition(int position, String expectedPlanName) {
        log.info("Asserting plan card at position {}: \"{}\"", position, expectedPlanName);
        // .nth(position - 1): feature file passes 1-indexed positions, Playwright's
        // nth() is 0-indexed. Scoped to pricingSection() so navbar / footer h3s
        // can't shift the index.
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
        // whole token (same trick WoocommercePage uses to avoid colliding with
        // future "__cardHeader"-style wrappers).
        return page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(3).setName(planName))
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__card ')][1]");
    }

    public void assertPlanSubheadingText(String planName, String expectedSubheading) {
        log.info("Asserting [{}] plan sub-heading: \"{}\"", planName, expectedSubheading);
        // The plan sub-heading paragraph ("Fast issuance for any website",
        // "Full business authentication", "Cover all your subdomains") sits
        // in the same DOM slot WooCommerce fills with the "50% OFF" promo
        // tagline — the first <p> after the h3. Using a positional XPath
        // rather than a class substring so an eventual `tagline` → `subheading`
        // frontend rename doesn't break this.
        Locator subheading = getPlanCard(planName).locator("xpath=.//h3/following-sibling::p[1]");
        PlaywrightAssertions.assertThat(subheading).hasText(expectedSubheading);
        log.info("PASSED: [{}] plan sub-heading \"{}\"", planName, expectedSubheading);
    }

    public void assertPlanPricing(String planName, String expectedYearly) {
        log.info("Asserting [{}] plan pricing: \"${}\"", planName, expectedYearly);
        // The SSL priceRow renders three adjacent inline spans with no
        // whitespace between them:
        //   <span priceDollar>$</span>
        //   <span price>19.95</span>
        //   <span priceStar>*</span>
        // Playwright's hasText reads textContent (not innerText), so adjacent
        // inline children concatenate without whitespace and the row's full
        // text is e.g. "$19.95*". Asserting the composed string in a single
        // hasText call pins the currency symbol, the yearly price, and the
        // footnote-asterisk marker together — one assertion, one failure
        // surface. If any of those three spans drifts (or gets reordered
        // / dropped), this fails.
        Locator priceRow = getPlanCard(planName).locator("[class*='priceRow']");
        PlaywrightAssertions.assertThat(priceRow).hasText("$" + expectedYearly + "*");
        log.info("PASSED: [{}] plan pricing ($" + expectedYearly + "*)", planName);
    }

    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        log.info("Asserting [{}] plan billing period: \"{}\"", planName, expectedPeriod);
        // Scope to the __period <p> specifically (rather than card-level
        // containsText) so unrelated text on the card can't accidentally
        // satisfy a substring assertion.
        Locator period = getPlanCard(planName).locator("p[class*='period']");
        PlaywrightAssertions.assertThat(period).hasText(expectedPeriod);
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

    private static final String WILDCARD_PLAN_LABEL = "Wildcard Certificate";

    private String getCtaText(String planName) {
        // SSL CTA formula is `Get <plan-name> SSL` for Domain Validation and
        // Organization Validation. Wildcard Certificate is the one exception:
        // the live CTA renders as "Get Wildcard SSL" (drops the redundant
        // " Certificate" word to keep the button label short).
        if (WILDCARD_PLAN_LABEL.equals(planName)) return "Get Wildcard SSL";
        return "Get " + planName + " SSL";
    }

    public void assertCtaReflectsPlan(String planName) {
        String expectedCtaText = getCtaText(planName);
        log.info("Asserting CTA reflects plan: \"{}\" (expecting \"{}\")", planName, expectedCtaText);
        Locator cta = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedCtaText));
        PlaywrightAssertions.assertThat(cta).isVisible();
        log.info("PASSED: CTA reflects plan \"{}\" (shows \"{}\")", planName, expectedCtaText);
    }

    public void assertCtaHrefForPlan(String planName, String expectedHref) {
        String expectedCtaText = getCtaText(planName);
        log.info("Asserting [{}] CTA links to: \"{}\"", planName, expectedHref);
        Locator cta = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedCtaText));
        // Visibility gate first — mirrors assertCtaReflectsPlan and prevents
        // a hasAttribute-only assertion from passing on a hidden/detached CTA
        // (all plans currently link to "#", so the href check alone is
        // near-tautological without this gate).
        PlaywrightAssertions.assertThat(cta).isVisible();
        PlaywrightAssertions.assertThat(cta).hasAttribute("href", expectedHref);
        log.info("PASSED: [{}] CTA links to \"{}\"", planName, expectedHref);
    }

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        // Scoped to pricingSection() so a stray copy of the same string in
        // the footer / hero can't mask a real regression in the pricing note
        // (matches the WoocommercePage sibling). .first() disambiguates the
        // SSR + hydration duplicate that shows up under parallel load — same
        // pattern as the hero subheadline; both copies always render
        // identical text.
        PlaywrightAssertions.assertThat(pricingSection().getByText(expectedText).first()).isVisible();
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
        PlaywrightAssertions.assertThat(item).hasText(feature);
        PlaywrightAssertions.assertThat(item.locator("svg[class*='" + PricingBlockClasses.CHECK_ICON + "']")).isVisible();
        log.info("PASSED: [{}] plan includes \"{}\" (✓ icon present)", planName, feature);
    }

    public void assertPlanIncludesFeatureExactly(String planName, String feature, int expectedCount) {
        log.info("Asserting [{}] plan includes feature \"{}\" exactly {} time(s)", planName, feature, expectedCount);
        // Explicit count oracle — vs. the implicit strict-mode duplicate
        // guard inside assertPlanIncludesFeature. Callers use this when the
        // exact number of matches is the assertion itself (e.g. the
        // @known-bug scenario asserting a known duplicate has been fixed to
        // exactly one occurrence). Makes the exactly-once contract
        // survivable if the sibling assertion is ever softened with .first().
        Locator items = getPlanCard(planName)
                .locator("li[class*='" + PricingBlockClasses.INCLUDED_SUFFIX + "']")
                .filter(new Locator.FilterOptions().setHasText(feature));
        PlaywrightAssertions.assertThat(items).hasCount(expectedCount);
        log.info("PASSED: [{}] plan includes feature \"{}\" exactly {} time(s)", planName, feature, expectedCount);
    }
}
