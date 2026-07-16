package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import config.PricingBlockClasses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WoocommercePage {
    private static final Logger log = LogManager.getLogger(WoocommercePage.class);
    private Page page;

    public WoocommercePage(Page page) {
        this.page = page;
    }

    public void navigateToWoocommercePage() {
        String url = EnvConfig.getWoocommerceUrl();
        log.info("Navigating to WooCommerce page: {}", url);
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
        // isVisible gate mirrors the tax-note assertion below — a DOM-only
        // subtitle (display:none or 0-height) would otherwise silently pass
        // the copy check.
        PlaywrightAssertions.assertThat(subtitle).isVisible();
        PlaywrightAssertions.assertThat(subtitle).hasText(expectedSubtitle);
        log.info("PASSED: hero subtitle matches expected copy");
    }

    public void assertSeePricingButtonDisplays(String expectedText) {
        log.info("Asserting See Pricing button displays: \"{}\"", expectedText);
        // Accessible-name locator + exact hasText: the locator finds the
        // button by its rendered text, so a text-drift causes a clear
        // element-not-found timeout; the explicit isVisible + hasText
        // pair also catches display-hidden state and whitespace / casing
        // regressions that containsText would let slide.
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

    // ==================== "START GROWING YOUR BUSINESS" SECTION ==================== //

    public void assertGrowingSectionTitle(String expectedTitle) {
        log.info("Asserting Start growing section title: \"{}\"", expectedTitle);
        Locator h2 = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setLevel(2).setName(expectedTitle));
        PlaywrightAssertions.assertThat(h2).isVisible();
        log.info("PASSED: Start growing section title \"{}\"", expectedTitle);
    }

    public void assertGrowingIntroContains(String fragment) {
        log.info("Asserting Start growing intro contains: \"{}\"", fragment);
        // Anchor to the intro <p> immediately following the section h2 via
        // following-sibling::p[1] (same pattern used for the sub-feature
        // description assertions below). Scoping to the h2's parent
        // container would let a fragment from any child element — including
        // the three sub-feature paragraphs — silently satisfy the assertion.
        Locator intro = page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(2).setName("Start growing your business with WooCommerce."))
                .locator("xpath=following-sibling::p[1]");
        PlaywrightAssertions.assertThat(intro).containsText(fragment);
        log.info("PASSED: Start growing intro contains \"{}\"", fragment);
    }

    public void assertGrowingSubHeadingDisplays(String subHeading) {
        log.info("Asserting Start growing sub-heading: \"{}\"", subHeading);
        Locator h3 = growingSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3).setName(subHeading));
        PlaywrightAssertions.assertThat(h3).isVisible();
        log.info("PASSED: Start growing sub-heading \"{}\"", subHeading);
    }

    public void assertGrowingSubDescriptionContains(String subHeading, String fragment) {
        log.info("Asserting [{}] Start growing description contains: \"{}\"", subHeading, fragment);
        // Anchor to the description <p> that follows the sub-heading h3 via
        // following-sibling::p[1] (same pattern used on the VPS "Get full
        // control" section). This pins the assertion to the exact
        // description belonging to this h3 regardless of grid/wrapper shape,
        // so a fragment from a sibling sub-feature can't silently satisfy
        // the assertion.
        Locator description = growingSection()
                .getByRole(AriaRole.HEADING,
                        new Locator.GetByRoleOptions().setLevel(3).setName(subHeading))
                .locator("xpath=following-sibling::p[1]");
        PlaywrightAssertions.assertThat(description).containsText(fragment);
        log.info("PASSED: [{}] description contains \"{}\"", subHeading, fragment);
    }

    private Locator growingSection() {
        // Climb to the section's shared parent so h3 lookups don't collide
        // with h3s outside "Start growing..." (e.g. footer / nav / plan cards).
        return page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(2).setName("Start growing your business with WooCommerce."))
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
        // Guard against a silent extra card / dropped card: the per-plan
        // scenarios pin the two known cards but wouldn't catch a hypothetical
        // 3rd tier (or a card disappearing entirely). Scoped to pricingSection
        // so navbar / footer h3s can't inflate the count.
        Locator cards = pricingSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(cards).hasCount(expected);
        log.info("PASSED: plans section has {} plan card(s)", expected);
    }

    public void assertPlanTitleDisplays(String expectedTitle) {
        log.info("Asserting plan title displays: \"{}\"", expectedTitle);
        // Same tightening as assertSeePricingButtonDisplays: locate by
        // accessible name (any text drift surfaces as element-not-found),
        // then gate on isVisible and pin the exact rendered text with
        // hasText so hidden or whitespace-drifted h3s don't slip past.
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
        // can't shift the index (per conventions.md "no first()/nth() without a
        // comment explaining the match").
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

    public void assertPlanPromoLabel(String planName, String expectedLabel) {
        log.info("Asserting [{}] plan promo label: \"{}\"", planName, expectedLabel);
        // The "50% OFF" tagline is emitted as a <p class="...__tagline"> between
        // the h3 and the priceRow. hasText normalizes whitespace and asserts
        // equality so the promo copy is pinned exactly.
        Locator tagline = getPlanCard(planName).locator("p[class*='tagline']");
        PlaywrightAssertions.assertThat(tagline).hasText(expectedLabel);
        log.info("PASSED: [{}] plan promo label \"{}\"", planName, expectedLabel);
    }

    public void assertPlanPricing(String planName, String expectedOriginal, String expectedPromo) {
        log.info("Asserting [{}] plan pricing: original \"${}\" + promo \"${}\"", planName, expectedOriginal, expectedPromo);
        // The priceRow renders four adjacent inline spans with no whitespace
        // between them:
        //   <span originalPrice>$8.00</span>   ← struck-through anchor
        //   <span priceDollar>$</span>
        //   <span price>4.00</span>
        //   <span priceStar>*</span>
        // Playwright's hasText reads textContent (not innerText), so adjacent
        // inline children concatenate without whitespace and the row's full
        // text is e.g. "$8.00$4.00*". Asserting the composed string in a
        // single hasText call pins ALL of: original price, promo price, the
        // currency symbol, and the footnote-asterisk marker — one assertion,
        // one failure surface. If any of those four spans drifts (or gets
        // reordered / dropped), this fails; a per-span hasText would need
        // four calls to cover the same ground.
        Locator priceRow = getPlanCard(planName).locator("[class*='priceRow']");
        PlaywrightAssertions.assertThat(priceRow).hasText("$" + expectedOriginal + "$" + expectedPromo + "*");
        // Visual guard: without checking text-decoration, the price row would
        // still pass this assertion even if the original-price strikethrough
        // was silently removed by a CSS rewrite — the copy would look like
        // a normal number rather than a "was" price. Assert on the exact
        // element that carries the strikethrough style.
        Locator originalPriceSpan = getPlanCard(planName).locator("span[class*='originalPrice']");
        PlaywrightAssertions.assertThat(originalPriceSpan).hasCSS("text-decoration-line", "line-through");
        log.info("PASSED: [{}] plan pricing (original struck-through)", planName);
    }

    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        log.info("Asserting [{}] plan billing period: \"{}\"", planName, expectedPeriod);
        // Scope to the __period <p> specifically (rather than card-level
        // containsText) so unrelated text on the card — like "$60.00/year on
        // renewal" in the inclusions list — can't accidentally satisfy a
        // substring assertion on a shorter expected value.
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
        // Scoped to pricingSection() so a stray copy of the same string in the
        // footer / hero can't mask a real regression in the pricing note.
        // .first() disambiguates the SSR + hydration duplicate — same pattern
        // the hero subheadline hits under parallel load. Both copies always
        // render identical text, so asserting on the first proves the visible
        // instance too (per conventions.md "no first()/nth() without a comment").
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
        // Exact-match guard: hasText normalizes whitespace and asserts equality,
        // preventing substring collisions like "25 GB" also matching a longer row.
        PlaywrightAssertions.assertThat(item).hasText(feature);
        // Visual guard: a missing/broken checkIcon svg would slip past a class +
        // text check alone; assert the ✓ icon actually renders on the row.
        PlaywrightAssertions.assertThat(item.locator("svg[class*='" + PricingBlockClasses.CHECK_ICON + "']")).isVisible();
        log.info("PASSED: [{}] plan includes \"{}\" (✓ icon present)", planName, feature);
    }

    // ==================== FAQ SECTION ==================== //
    // Accordion structure emitted by FAQBlock:
    //   <div class="...__item">
    //     <button class="...__trigger" aria-expanded="false" aria-controls="X">
    //       {question text}
    //     </button>
    //     <div class="...__panel" id="X">{answer paragraph}</div>
    //   </div>
    // The panel is present in the DOM even when collapsed (CSS grid 0fr → 1fr
    // animation trick), so text is always readable. Real user-visible state
    // tracks aria-expanded on the trigger + panel bounding-box height.

    private Locator getFaqTrigger(String question) {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(question));
    }

    private Locator getFaqPanel(String question) {
        // The panel is the sibling immediately after the trigger button; scope
        // by class suffix "panel" to survive per-build CSS-module hash rotation.
        return getFaqTrigger(question).locator("xpath=following-sibling::div[contains(@class, 'panel')][1]");
    }

    private Locator faqTriggers() {
        // Scoped to the FAQBlock container so a future accordion elsewhere
        // on the page (e.g. nav / footer) can't inflate the count.
        return page.locator("[class*='FAQBlock']").locator("button[class*='trigger']");
    }

    public void assertFaqQuestionCount(int expected) {
        log.info("Asserting FAQ section has {} question(s)", expected);
        // Complementary to the Scenario Outline that pins each known
        // question by copy: without this, a 7th question added to the
        // component (or a dropped one) would pass unnoticed.
        PlaywrightAssertions.assertThat(faqTriggers()).hasCount(expected);
        log.info("PASSED: FAQ section has {} question(s)", expected);
    }

    public void assertFaqQuestionVisible(String question) {
        log.info("Asserting FAQ question is visible: \"{}\"", question);
        PlaywrightAssertions.assertThat(getFaqTrigger(question)).isVisible();
        log.info("PASSED: FAQ question visible \"{}\"", question);
    }

    public void assertFaqQuestionCollapsed(String question) {
        log.info("Asserting FAQ question is collapsed by default: \"{}\"", question);
        // Two signals for the same state: (a) aria-expanded=false on the
        // trigger — the accessibility contract, and (b) the panel is not
        // user-visible (CSS grid 0fr collapses bounding-box height to 0,
        // isVisible returns false). Asserting both prevents an "always-open"
        // accordion regression from slipping past — expand + answer checks
        // alone would still pass on a stuck-open panel.
        Locator trigger = getFaqTrigger(question);
        PlaywrightAssertions.assertThat(trigger).hasAttribute("aria-expanded", "false");
        PlaywrightAssertions.assertThat(getFaqPanel(question)).not().isVisible();
        log.info("PASSED: FAQ question collapsed by default \"{}\"", question);
    }

    public void expandFaqQuestion(String question) {
        log.info("Expanding FAQ question: \"{}\"", question);
        Locator trigger = getFaqTrigger(question);
        trigger.click();
        // Assert aria-expanded flipped to true so we know the click landed
        // before downstream assertions read the panel — otherwise a race with
        // the accordion animation could read stale (collapsed) height.
        PlaywrightAssertions.assertThat(trigger).hasAttribute("aria-expanded", "true");
        log.info("Expanded FAQ question \"{}\"", question);
    }

    public void assertFaqAnswerContains(String question, String fragment) {
        log.info("Asserting [{}] FAQ answer contains: \"{}\"", question, fragment);
        Locator panel = getFaqPanel(question);
        // Panel visibility depends on the accordion animation completing;
        // Playwright's auto-wait on isVisible bridges the 0fr→1fr transition
        // without needing an explicit sleep.
        PlaywrightAssertions.assertThat(panel).isVisible();
        PlaywrightAssertions.assertThat(panel).containsText(fragment);
        log.info("PASSED: [{}] FAQ answer contains \"{}\"", question, fragment);
    }
}
