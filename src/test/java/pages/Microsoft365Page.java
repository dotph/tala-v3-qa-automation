package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import config.PricingBlockClasses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Microsoft365Page {
    private static final Logger log = LogManager.getLogger(Microsoft365Page.class);
    private final Page page;

    public Microsoft365Page(Page page) {
        this.page = page;
    }

    public void navigateToMicrosoft365Page() {
        String url = EnvConfig.getMicrosoft365Url();
        log.info("Navigating to Microsoft 365 page: {}", url);
        page.navigate(url);
        log.info("Navigation complete");
    }

    // ==================== HERO SECTION ==================== //

    public void assertHeroEyebrowText(String expectedText) {
        log.info("Asserting hero eyebrow displays: \"{}\"", expectedText);
        // Same shape as SslPage.assertHeroEyebrowText: HeroBlock renders the
        // eyebrow as a <span class="...__eyebrow"> above the H1. Scoped to
        // HeroBlock so a future __eyebrow span in a lower section can't
        // collide; the class suffix survives per-build CSS-module hash
        // rotation.
        Locator eyebrow = page.locator("[class*='HeroBlock']").locator("span[class*='eyebrow']");
        PlaywrightAssertions.assertThat(eyebrow).isVisible();
        PlaywrightAssertions.assertThat(eyebrow).hasText(expectedText);
        log.info("PASSED: hero eyebrow displays \"{}\"", expectedText);
    }

    public void assertHeroTitleText(String expectedTitle) {
        log.info("Asserting hero title displays: \"{}\"", expectedTitle);
        Locator h1 = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));
        PlaywrightAssertions.assertThat(h1).hasText(expectedTitle);
        log.info("PASSED: hero title displays \"{}\"", expectedTitle);
    }

    public void assertHeroSubtitleText(String expectedSubtitle) {
        log.info("Asserting hero subtitle: \"{}\"", expectedSubtitle);
        // Same SSR + hydration duplicate as SSL/Woo/MDH heros — .first() picks
        // the deterministic one; isVisible + hasText together guard both a
        // DOM-only subtitle and any whitespace / copy drift.
        Locator subtitle = page.locator("p[class*='subheadline']").first();
        PlaywrightAssertions.assertThat(subtitle).isVisible();
        PlaywrightAssertions.assertThat(subtitle).hasText(expectedSubtitle);
        log.info("PASSED: hero subtitle matches expected copy");
    }

    public void assertSeePricingButtonDisplays(String expectedText) {
        log.info("Asserting See Pricing button displays: \"{}\"", expectedText);
        Locator seePricing = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedText));
        PlaywrightAssertions.assertThat(seePricing).isVisible();
        PlaywrightAssertions.assertThat(seePricing).hasText(expectedText);
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

    // ==================== PLAN INTROS STRIP ==================== //
    // The page ships TWO FeaturesBlock sections. The first has NO H2 and holds
    // three "text cards" whose H3s carry the full "Microsoft 365 <plan>" name
    // plus a "(formerly Office 365 <plan>)" paragraph. The second is the
    // "Microsoft 365 Features" grid (5 cards). All lookups here anchor on the
    // intro H3 text so the two sections can't collide by index.

    public void assertIntroCardCount(int expected) {
        log.info("Asserting intro strip has {} intro card(s)", expected);
        // Guards the introCard() locator's uniqueness assumption — a fourth
        // intro card (or any stray "Microsoft 365 …" H3 elsewhere on the
        // page) would silently shift introCard(position) matches without
        // this count. Deliberately counts the FILTERED H3s rather than
        // __textCard divs so a stray heading elsewhere fails here loudly
        // instead of quietly widening the pool.
        Locator introH3s = page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(3))
                .filter(new Locator.FilterOptions().setHasText("Microsoft 365 "));
        PlaywrightAssertions.assertThat(introH3s).hasCount(expected);
        log.info("PASSED: intro strip has {} intro card(s)", expected);
    }

    public void assertIntroCardHeading(int position, String expectedHeading) {
        log.info("Asserting intro card at position {} heading: \"{}\"", position, expectedHeading);
        Locator h3 = introCard(position)
                .getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(h3).hasText(expectedHeading);
        log.info("PASSED: intro card at position {} heading \"{}\"", position, expectedHeading);
    }

    public void assertIntroCardFormerName(int position, String expectedFormerName) {
        log.info("Asserting intro card at position {} former-name line: \"{}\"", position, expectedFormerName);
        // The "(formerly Office 365 …)" line is the FIRST paragraph inside the
        // text card, immediately after the H3. Description paragraph follows
        // and isn't asserted per feature-file scope decision (heading +
        // former-name is the load-bearing copy; the description is long
        // marketing prose we deliberately skip).
        Locator formerName = introCard(position).locator("p").first();
        PlaywrightAssertions.assertThat(formerName).hasText(expectedFormerName);
        log.info("PASSED: intro card at position {} former-name \"{}\"", position, expectedFormerName);
    }

    private Locator introCard(int position) {
        requirePositive(position);
        // Anchor on the intro-section H3 whose text starts with "Microsoft 365 "
        // (all three intro cards do; none of the feature-grid or plan-card H3s
        // do). .nth(position - 1) picks the intro-strip card at the requested
        // 1-indexed position, walking up to the nearest textCard ancestor
        // (FeaturesBlock's "__textCard" wrapper carries the H3 + former-name
        // <p> + description <p>). '__textCard ' with trailing space in the
        // XPath concat trick pins the whole-token match so a "__textCardFoo"
        // sibling can't be picked up by substring match.
        return page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(3))
                .filter(new Locator.FilterOptions().setHasText("Microsoft 365 "))
                .nth(position - 1)
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__textCard ')][1]");
    }

    // ==================== "MICROSOFT 365 FEATURES" SECTION ==================== //

    public void assertFeaturesSectionTitle(String expectedTitle) {
        log.info("Asserting features section title: \"{}\"", expectedTitle);
        Locator h2 = featuresSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).hasText(expectedTitle);
        log.info("PASSED: features section title \"{}\"", expectedTitle);
    }

    public void assertFeatureCount(int expected) {
        log.info("Asserting features section has {} feature card(s)", expected);
        // Guard against a silent extra/dropped feature. Scoped to
        // featuresSection() so the intro-strip H3s (which live in a sibling
        // FeaturesBlock section) can't inflate the count.
        Locator h3s = featuresSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(h3s).hasCount(expected);
        log.info("PASSED: features section has {} feature card(s)", expected);
    }

    public void assertFeatureHeading(int position, String expectedHeading) {
        log.info("Asserting feature at position {} heading: \"{}\"", position, expectedHeading);
        requirePositive(position);
        Locator h3 = featuresSection().getByRole(AriaRole.HEADING,
                        new Locator.GetByRoleOptions().setLevel(3))
                .nth(position - 1);
        PlaywrightAssertions.assertThat(h3).hasText(expectedHeading);
        log.info("PASSED: feature at position {} heading \"{}\"", position, expectedHeading);
    }

    public void assertFeatureDescriptionContains(int position, String fragment) {
        log.info("Asserting feature at position {} description contains: \"{}\"", position, fragment);
        requirePositive(position);
        // Description paragraph sits as the H3's immediate next sibling inside
        // the same textCard wrapper. following-sibling::p[1] pins it exactly
        // regardless of grid/wrapper class rename.
        Locator description = featuresSection().getByRole(AriaRole.HEADING,
                        new Locator.GetByRoleOptions().setLevel(3))
                .nth(position - 1)
                .locator("xpath=following-sibling::p[1]");
        PlaywrightAssertions.assertThat(description).containsText(fragment);
        log.info("PASSED: feature at position {} description contains \"{}\"", position, fragment);
    }

    private Locator featuresSection() {
        // Two FeaturesBlock sections ship on this page — the intros strip
        // (no H2) and the features grid (H2 "Microsoft 365 Features"). Filter
        // by has-H2 so the two never collide by index.
        return page.locator("section[class*='FeaturesBlock']")
                .filter(new Locator.FilterOptions().setHas(
                        page.getByRole(AriaRole.HEADING,
                                new Page.GetByRoleOptions().setLevel(2).setName("Microsoft 365 Features"))));
    }

    // ==================== MICROSOFT 365 PLANS ==================== //

    public void assertPlansSectionTitleText(String expectedTitle) {
        log.info("Asserting plans section title displays: \"{}\"", expectedTitle);
        Locator h2 = pricingSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).hasText(expectedTitle);
        log.info("PASSED: plans section title displays \"{}\"", expectedTitle);
    }

    public void assertPlanCardCount(int expected) {
        log.info("Asserting plans section has {} plan card(s)", expected);
        Locator cards = pricingSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(cards).hasCount(expected);
        log.info("PASSED: plans section has {} plan card(s)", expected);
    }

    public void assertPlanTitleDisplays(String expectedTitle) {
        log.info("Asserting plan title displays: \"{}\"", expectedTitle);
        // setExact(true) so the intro-strip H3s "Microsoft 365 Business Basic"
        // / "Microsoft 365 Business Standard" / "Microsoft 365 Apps for
        // Business" (which contain each pricing-card name as substring) can't
        // be picked up by role-lookup partial matching.
        Locator planTitle = pricingSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3).setName(expectedTitle).setExact(true));
        PlaywrightAssertions.assertThat(planTitle).isVisible();
        PlaywrightAssertions.assertThat(planTitle).hasText(expectedTitle);
        log.info("PASSED: plan title displays \"{}\"", expectedTitle);
    }

    public void assertPlanCardAtPosition(int position, String expectedPlanName) {
        log.info("Asserting plan card at position {}: \"{}\"", position, expectedPlanName);
        requirePositive(position);
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
        // Anchor to the pricing-section H3 with setExact(true) so intro-strip
        // H3s containing the plan name as substring can't be picked up. Then
        // walk up to the __card ancestor (same '__card ' whole-token XPath
        // trick as Woo/SSL/MDH).
        return pricingSection().getByRole(AriaRole.HEADING,
                        new Locator.GetByRoleOptions().setLevel(3).setName(planName).setExact(true))
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__card ')][1]");
    }

    public void assertPlanSubheadingText(String planName, String expectedSubheading) {
        log.info("Asserting [{}] plan sub-heading: \"{}\"", planName, expectedSubheading);
        // Sub-heading paragraph is the first <p> after the H3, filling the same
        // DOM slot SSL uses for its "Fast issuance…" / "Full business…" lines.
        // NOTE: Business Standard's sub-heading pins the em-dash character
        // (U+2014) — same staging-normalized parity risk SSL carries. A
        // `-DtestEnv=PRODUCTION` run will fail here if dot.ph renders an
        // en-dash (U+2013). Update dot.ph copy or duplicate this line into an
        // env-branched enum when the two stacks converge.
        Locator subheading = getPlanCard(planName).locator("xpath=.//h3/following-sibling::p[1]");
        PlaywrightAssertions.assertThat(subheading).hasText(expectedSubheading);
        log.info("PASSED: [{}] plan sub-heading \"{}\"", planName, expectedSubheading);
    }

    public void assertPlanPricing(String planName, String expectedYearly) {
        log.info("Asserting [{}] plan pricing: \"${}\"", planName, expectedYearly);
        // Same three-adjacent-inline-span composition as SSL: $, price,
        // footnote-asterisk render with no whitespace between spans, so
        // textContent reads "$150.00*". Asserting the full composed string in
        // one hasText call pins currency, amount, and asterisk together.
        Locator priceRow = getPlanCard(planName).locator("[class*='priceRow']");
        PlaywrightAssertions.assertThat(priceRow).hasText("$" + expectedYearly + "*");
        log.info("PASSED: [{}] plan pricing (${}*)", planName, expectedYearly);
    }

    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        log.info("Asserting [{}] plan billing period: \"{}\"", planName, expectedPeriod);
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

    private String getCtaText(String planName) {
        // M365 CTA formula is a clean `Get <plan-name>` across all three
        // tiers — verified live: "Get Business Basic", "Get Business
        // Standard", "Get Apps for Business". Unlike SSL there's no
        // Wildcard-style exception.
        return "Get " + planName;
    }

    public void assertCtaReflectsPlan(String planName) {
        String expectedCtaText = getCtaText(planName);
        log.info("Asserting CTA reflects plan: \"{}\" (expecting \"{}\")", planName, expectedCtaText);
        // Scoped to pricingSection() — verified live on mdot.ph that M365
        // renders a single re-labeling CTA inside the pricing section's
        // __applySection (sibling of the plan cards, NOT inside any __card
        // ancestor). Only one "Get <plan>" link exists at a time; the scope
        // guards against a stray "Get X" link in the footer / hero masking a
        // regression here. A per-card scoped lookup via getPlanCard would
        // break because the CTA lives outside every __card wrapper.
        Locator cta = pricingSection().getByRole(AriaRole.LINK,
                new Locator.GetByRoleOptions().setName(expectedCtaText));
        PlaywrightAssertions.assertThat(cta).isVisible();
        log.info("PASSED: CTA reflects plan \"{}\" (shows \"{}\")", planName, expectedCtaText);
    }

    public void assertCtaHrefForPlan(String planName, String expectedHref) {
        String expectedCtaText = getCtaText(planName);
        log.info("Asserting [{}] CTA links to: \"{}\"", planName, expectedHref);
        // Same pricingSection() scope as assertCtaReflectsPlan — see that
        // method for the single-re-labeling-CTA finding.
        Locator cta = pricingSection().getByRole(AriaRole.LINK,
                new Locator.GetByRoleOptions().setName(expectedCtaText));
        // Visibility gate first — mirrors SSL's assertCtaHrefForPlan and
        // prevents a hasAttribute-only assertion from passing on a hidden /
        // detached CTA (all plans currently link to "#", so the href check
        // alone is near-tautological without this gate).
        PlaywrightAssertions.assertThat(cta).isVisible();
        PlaywrightAssertions.assertThat(cta).hasAttribute("href", expectedHref);
        log.info("PASSED: [{}] CTA links to \"{}\"", planName, expectedHref);
    }

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        // Scoped to pricingSection() so a stray copy of the same string in
        // the footer / hero can't mask a real regression in the pricing note.
        // .first() disambiguates the SSR + hydration duplicate that shows up
        // under parallel load — same pattern as the hero subheadline; both
        // copies always render identical text. hasText (exact-match) rather
        // than isVisible on a substring locator so trailing copy drift like
        // "… See terms." would fail here instead of passing silently.
        Locator taxNote = pricingSection().getByText(expectedText).first();
        PlaywrightAssertions.assertThat(taxNote).hasText(expectedText);
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

    public void assertPlanInclusionRowCount(String planName, int expected) {
        log.info("Asserting [{}] plan has {} inclusion row(s)", planName, expected);
        // Guards against a silently added / duplicated / dropped inclusion row.
        // The per-string assertPlanIncludesFeature calls already pin each named
        // row, but they never assert the total — so a duplicate row (see
        // SSL/TALA-2858) or a stealth insertion of a new row stays green
        // without this count. Substring class match "inclusion" catches both
        // INCLUDED_SUFFIX ("inclusionIncluded") and EXCLUDED_SUFFIX
        // ("inclusionExcluded") in one locator, so the count covers the full
        // list regardless of the ✓/X mix per plan.
        Locator rows = getPlanCard(planName).locator("li[class*='inclusion']");
        PlaywrightAssertions.assertThat(rows).hasCount(expected);
        log.info("PASSED: [{}] plan has {} inclusion row(s)", planName, expected);
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

    public void assertPlanExcludesFeature(String planName, String feature) {
        log.info("Asserting [{}] plan excludes feature: \"{}\" (expecting X icon + dimmed row)", planName, feature);
        // Same three-signal contract as SDH's assertPlanExcludesFeature: the
        // <li> carries "inclusionExcluded" class, holds the exact feature
        // text, and renders an xIcon svg. The final opacity check pins the
        // 0.5-dimmed styling that visually differentiates excluded rows —
        // verified live on M365's Business Basic (Desktop apps) and Apps for
        // Business (email / Teams) rows.
        Locator item = getPlanFeatureItem(planName, feature, PricingBlockClasses.EXCLUDED_SUFFIX);
        PlaywrightAssertions.assertThat(item).isVisible();
        PlaywrightAssertions.assertThat(item).hasText(feature);
        PlaywrightAssertions.assertThat(item.locator("svg[class*='" + PricingBlockClasses.EXCLUDED_ICON + "']")).isVisible();
        PlaywrightAssertions.assertThat(item).hasCSS("opacity", "0.5");
        log.info("PASSED: [{}] plan excludes \"{}\" (X icon present, row dimmed)", planName, feature);
    }

    private static void requirePositive(int position) {
        // Playwright's .nth(-1) selects the LAST element, not the first —
        // silent bug if Gherkin ever passes position 0. Fail loudly here.
        if (position < 1) {
            throw new IllegalArgumentException(
                    "position must be 1-indexed (>= 1), got " + position);
        }
    }
}
