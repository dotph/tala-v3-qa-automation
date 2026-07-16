package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import config.PricingBlockClasses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class RegistryLockPage {
    private static final Logger log = LogManager.getLogger(RegistryLockPage.class);
    private final Page page;

    public RegistryLockPage(Page page) {
        this.page = page;
    }

    public void navigateToRegistryLockPage() {
        String url = EnvConfig.getRegistryLockUrl();
        log.info("Navigating to Registry Lock page: {}", url);
        page.navigate(url);
        log.info("Navigation complete");
    }

    // ==================== HERO SECTION ==================== //

    public void assertHeroTagline(String expectedTagline) {
        log.info("Asserting hero tagline displays: \"{}\"", expectedTagline);
        // The tagline is a <span class="...__eyebrow"> emitted only by HeroBlock;
        // note "eyebrow" is the local suffix here (PricingBlock reuses "tagline"
        // for the supported-TLDs paragraph downstream, so class-suffix
        // "tagline" is NOT the hero eyebrow — do not conflate).
        // No .first() here: a live DOM probe on /registry-lock (and PR #16's
        // parallel probe on /private-registration) both found exactly one
        // eyebrow inside the hero section — Playwright's strict-mode locator
        // enforces uniqueness, so any future SSR duplicate surfaces as a
        // failure instead of silently getting masked.
        Locator eyebrow = heroSection().locator("span[class*='eyebrow']");
        PlaywrightAssertions.assertThat(eyebrow).isVisible();
        PlaywrightAssertions.assertThat(eyebrow).hasText(expectedTagline);
        log.info("PASSED: hero tagline displays \"{}\"", expectedTagline);
    }

    public void assertHeroTitleText(String expectedTitle) {
        log.info("Asserting hero title contains: \"{}\"", expectedTitle);
        Locator h1 = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(1));
        PlaywrightAssertions.assertThat(h1).containsText(expectedTitle);
        log.info("PASSED: hero title contains \"{}\"", expectedTitle);
    }

    public void assertHeroSubtitleText(String expectedSubtitle) {
        log.info("Asserting hero subtitle matches expected copy");
        // .first() covers the SSR + hydration duplicate that shows up under
        // Playwright's test browser (same pattern used across Woo/SDH/MDH heroes).
        Locator subtitle = heroSection().locator("p[class*='subheadline']").first();
        PlaywrightAssertions.assertThat(subtitle).isVisible();
        PlaywrightAssertions.assertThat(subtitle).hasText(expectedSubtitle);
        log.info("PASSED: hero subtitle matches expected copy");
    }

    public void assertHeroImageSrcContains(String fragment) {
        log.info("Asserting hero image src contains: \"{}\"", fragment);
        // Next.js emits the image as a proxy URL like
        // /_next/image?url=%2Fapi%2Fmedia%2Ffile%2Fp-rl-hero.jpg&w=3840&q=75 —
        // the w=/q= params drift with viewport / build tuning, so match on
        // the underlying media filename only.
        Locator heroImage = heroSection().locator("img[class*='image']").first();
        PlaywrightAssertions.assertThat(heroImage).hasAttribute("src", srcContainsPattern(fragment));
        log.info("PASSED: hero image src contains \"{}\"", fragment);
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

    private Locator heroSection() {
        return page.locator("section[class*='HeroBlock']");
    }

    // ==================== WHY SECTION ==================== //

    public void assertWhySectionTitle(String expectedTitle) {
        log.info("Asserting why section title: \"{}\"", expectedTitle.substring(0, Math.min(60, expectedTitle.length())) + "...");
        Locator h2 = whySection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).hasText(expectedTitle);
        log.info("PASSED: why section title matches");
    }

    public void assertWhyIntroContains(String fragment) {
        log.info("Asserting why intro contains: \"{}\"", fragment);
        // ContentBlock renders one intro paragraph under its H2 — but SSR +
        // hydration can duplicate the tree under Playwright's browser (same
        // pattern the hero subtitle / tax note hit). .first() disambiguates
        // without needing a following-sibling xpath walk.
        Locator intro = whySection().locator("p").first();
        PlaywrightAssertions.assertThat(intro).containsText(fragment);
        log.info("PASSED: why intro contains \"{}\"", fragment);
    }

    private Locator whySection() {
        return page.locator("section[class*='ContentBlock']");
    }

    // ==================== INFO BLOCKS (SplitFeature) ==================== //

    private Locator infoBlock(int position) {
        // Same shape as Woo's getPlanCard: anchor on the block's h3 (the
        // semantic content) and walk up to the __item ancestor. .nth() picks
        // the h3 at the requested position (1-indexed in Gherkin, 0-indexed
        // in Playwright).
        // '__item ' (trailing space only, matching Woo's '__card ' idiom):
        // classes here are per-build-hashed like "SplitFeatureBlock-module__
        // 8_jDQW__item" — "__item" is a suffix of a longer identifier, so it
        // never appears as a standalone whitespace-bounded token. Matching
        // "__item " (trailing space) via the concat trick pins the case where
        // "...__item" is followed by another class (block 2) or by the
        // concat's own trailing space (block 1, end of class attribute), and
        // rejects "...__itemList" because there's no space between "item" and
        // "List".
        requirePositive(position);
        return page.locator("section[class*='SplitFeatureBlock']")
                .getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3))
                .nth(position - 1)
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__item ')][1]");
    }

    public void assertInfoBlockCount(int expected) {
        log.info("Asserting info-block section has {} block(s)", expected);
        // Complementary to the per-block scenarios: an added 3rd block would
        // slip past those (they pin block 1 and block 2 by index), so pin the
        // count as an explicit invariant. Uses the same '__item ' xpath token
        // idiom as infoBlock() so it can't drift out of sync.
        Locator blocks = page.locator("section[class*='SplitFeatureBlock']")
                .locator("xpath=.//div[contains(concat(' ', @class, ' '), '__item ')]");
        PlaywrightAssertions.assertThat(blocks).hasCount(expected);
        log.info("PASSED: info-block section has {} block(s)", expected);
    }

    public void assertInfoBlockHeading(int position, String expectedHeading) {
        log.info("Asserting info block {} heading: \"{}\"", position, expectedHeading);
        Locator h3 = infoBlock(position).getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(h3).hasText(expectedHeading);
        log.info("PASSED: info block {} heading \"{}\"", position, expectedHeading);
    }

    public void assertInfoBlockDescriptionContains(int position, String fragment) {
        log.info("Asserting info block {} description contains: \"{}\"", position, fragment);
        // Each SplitFeatureBlock item renders a single description <p> next to
        // the h3, so a block-scoped p locator picks the right paragraph without
        // needing an xpath walk.
        Locator description = infoBlock(position).locator("p");
        PlaywrightAssertions.assertThat(description).containsText(fragment);
        log.info("PASSED: info block {} description contains \"{}\"", position, fragment);
    }

    public void assertInfoBlockImageSrcContains(int position, String fragment) {
        log.info("Asserting info block {} image src contains: \"{}\"", position, fragment);
        Locator img = infoBlock(position).locator("img");
        PlaywrightAssertions.assertThat(img).hasAttribute("src", srcContainsPattern(fragment));
        log.info("PASSED: info block {} image src contains \"{}\"", position, fragment);
    }

    private static Pattern srcContainsPattern(String fragment) {
        // Playwright serializes Java Patterns to the browser-side JS regex
        // engine, which does not understand Pattern.quote()'s \Q...\E literal-
        // block syntax. Escape metachars manually so the regex stays portable.
        // For the media filenames we assert against (e.g. "p-rl-hero.jpg"), the
        // only metachar in play is "." — escape it plus the other common ones
        // as a safety net so future callers don't get bitten.
        String escaped = fragment.replaceAll("[.\\\\+*?^$()\\[\\]{}|]", "\\\\$0");
        return Pattern.compile(".*" + escaped + ".*");
    }

    private static void requirePositive(int position) {
        // Playwright's .nth(-1) selects the LAST element, not the first — a
        // silent bug if Gherkin ever passes position 0. Fail loudly here.
        if (position < 1) {
            throw new IllegalArgumentException(
                    "position must be 1-indexed (>= 1), got " + position);
        }
    }

    // ==================== SUBSCRIBE STEPS (StepsBlock) ==================== //

    public void assertSubscribeSectionTitle(String expectedTitle) {
        log.info("Asserting Subscribe section title: \"{}\"", expectedTitle);
        Locator h2 = stepsSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).hasText(expectedTitle);
        log.info("PASSED: Subscribe section title \"{}\"", expectedTitle);
    }

    public void assertSubscribeStepCount(int expected) {
        log.info("Asserting Subscribe section has {} step(s)", expected);
        // Complementary to the Scenario Outline: additions (a hypothetical 5th
        // step) slip past an outline that only iterates 1..4. Drops surface
        // via the outline's .nth() lookup failing, but this count guard
        // documents the invariant and fails earlier / more cleanly on drops.
        PlaywrightAssertions.assertThat(subscribeSteps()).hasCount(expected);
        log.info("PASSED: Subscribe section has {} step(s)", expected);
    }

    public void assertSubscribeStepNumber(int position, String expectedNumber) {
        log.info("Asserting Subscribe step {} number badge: \"{}\"", position, expectedNumber);
        Locator number = subscribeStep(position).locator("[class*='__number']");
        PlaywrightAssertions.assertThat(number).hasText(expectedNumber);
        log.info("PASSED: Subscribe step {} number badge \"{}\"", position, expectedNumber);
    }

    public void assertSubscribeStepHeading(int position, String expectedHeading) {
        log.info("Asserting Subscribe step {} heading: \"{}\"", position, expectedHeading);
        Locator h3 = subscribeStep(position).getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(h3).hasText(expectedHeading);
        log.info("PASSED: Subscribe step {} heading \"{}\"", position, expectedHeading);
    }

    public void assertSubscribeStepDescriptionContains(int position, String fragment) {
        log.info("Asserting Subscribe step {} description contains: \"{}\"", position, fragment);
        Locator description = subscribeStep(position).locator("p");
        PlaywrightAssertions.assertThat(description).containsText(fragment);
        log.info("PASSED: Subscribe step {} description contains \"{}\"", position, fragment);
    }

    private Locator subscribeSteps() {
        // '__item ' xpath token match mirrors infoBlock() and Woo's getPlanCard,
        // so all three "match items in a section" locators read as one dialect.
        // Safe against a hypothetical <li class="...__itemFoo"> variant even
        // though StepsBlock's DOM currently has no such collision risk.
        return stepsSection()
                .locator("xpath=.//li[contains(concat(' ', @class, ' '), '__item ')]");
    }

    private Locator subscribeStep(int position) {
        requirePositive(position);
        return subscribeSteps().nth(position - 1);
    }

    private Locator stepsSection() {
        return page.locator("section[class*='StepsBlock']");
    }

    // ==================== PRICING SECTION ==================== //

    public void assertPlansSectionTitleText(String expectedTitle) {
        log.info("Asserting plans section title: \"{}\"", expectedTitle);
        Locator h2 = pricingSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).hasText(expectedTitle);
        log.info("PASSED: plans section title \"{}\"", expectedTitle);
    }

    public void assertPlanCardCount(int expected) {
        log.info("Asserting plans section has {} plan card(s)", expected);
        // Pins the single-card invariant that lets us skip plan-order and
        // plan-selection scenarios entirely. Woo's counterpart is the
        // assertPlanCardCount used in its plan-count scenario. Uses the same
        // '__card ' xpath token idiom as planCard() so a plan h3 added
        // outside a card wouldn't be miscounted.
        Locator cards = pricingSection().locator(
                "xpath=.//div[contains(concat(' ', @class, ' '), '__card ')]");
        PlaywrightAssertions.assertThat(cards).hasCount(expected);
        log.info("PASSED: plans section has {} plan card(s)", expected);
    }

    public void assertPlanTitle(String expectedTitle) {
        log.info("Asserting plan title: \"{}\"", expectedTitle);
        // Scoped to the pricing section AND matched with setExact(true) so the
        // info-block h3s ("dotPH Registry Lock is a premium service...") that
        // contain "Registry Lock" as substring don't accidentally satisfy this
        // assertion via role-lookup partial matching.
        Locator h3 = pricingSection().getByRole(AriaRole.HEADING,
                new Locator.GetByRoleOptions().setLevel(3).setName(expectedTitle).setExact(true));
        PlaywrightAssertions.assertThat(h3).isVisible();
        PlaywrightAssertions.assertThat(h3).hasText(expectedTitle);
        log.info("PASSED: plan title \"{}\"", expectedTitle);
    }

    public void assertPlanSupportedTldsDisplays(String planName, String expectedText) {
        log.info("Asserting [{}] plan supported TLDs paragraph: \"{}\"", planName, expectedText);
        // PricingBlock's "supported" line is emitted as <p class="...__tagline"> —
        // NOT the same as HeroBlock's __eyebrow tagline (see hero section note).
        Locator supported = planCard(planName).locator("p[class*='tagline']");
        PlaywrightAssertions.assertThat(supported).hasText(expectedText);
        log.info("PASSED: [{}] plan supported TLDs paragraph \"{}\"", planName, expectedText);
    }

    public void assertPlanPriceRowText(String planName, String expectedComposedText) {
        log.info("Asserting [{}] plan price row composed text: \"{}\"", planName, expectedComposedText);
        // Composed row text is textContent of three inline spans concatenated:
        //   <span priceDollar>$</span><span price>100.00</span><span priceStar>*</span>
        //   → "$100.00*"
        // Woo's precedent asserts the priceRow's full composed text in one call so
        // any drift (currency, amount, or the footnote-asterisk marker) surfaces
        // as a single failure. No strikethrough guard here — Registry Lock has no
        // promo/original-price element, so there's nothing to compare against.
        Locator priceRow = planCard(planName).locator("[class*='priceRow']");
        PlaywrightAssertions.assertThat(priceRow).hasText(expectedComposedText);
        log.info("PASSED: [{}] plan price row composed text \"{}\"", planName, expectedComposedText);
    }

    public void assertPlanBillingPeriod(String planName, String expectedPeriod) {
        log.info("Asserting [{}] plan billing period: \"{}\"", planName, expectedPeriod);
        Locator period = planCard(planName).locator("p[class*='period']");
        PlaywrightAssertions.assertThat(period).hasText(expectedPeriod);
        log.info("PASSED: [{}] plan billing period \"{}\"", planName, expectedPeriod);
    }

    private static final String SELECTED_BUTTON_TEXT = "✓ Selected";

    public void assertPlanIsInSelectedState(String planName) {
        log.info("Asserting plan is in selected state: \"{}\"", planName);
        // Two-signal check: the card wrapper carries aria-pressed=true, AND the
        // inner toggle button both reads "✓ Selected" and has aria-pressed=true.
        // The card-level aria-pressed catches a silent regression where the
        // inner button flips without the card modifier following, and the
        // inner-button text guards the visible label.
        Locator card = planCard(planName);
        PlaywrightAssertions.assertThat(card).hasAttribute("aria-pressed", "true");
        Locator innerBtn = card.locator("button[aria-pressed]");
        PlaywrightAssertions.assertThat(innerBtn).hasAttribute("aria-pressed", "true");
        PlaywrightAssertions.assertThat(innerBtn).hasText(SELECTED_BUTTON_TEXT);
        log.info("PASSED: plan \"{}\" is in selected state", planName);
    }

    public void assertPlanInclusionsHeading(String planName, String expectedHeading) {
        log.info("Asserting [{}] plan inclusions heading: \"{}\"", planName, expectedHeading);
        Locator heading = planCard(planName).locator("p[class*='" + PricingBlockClasses.INCLUSIONS_HEADING + "']");
        PlaywrightAssertions.assertThat(heading).hasText(expectedHeading);
        log.info("PASSED: [{}] plan inclusions heading \"{}\"", planName, expectedHeading);
    }

    public void assertPlanIncludes(String planName, String feature) {
        log.info("Asserting [{}] plan includes feature: \"{}\"", planName, feature);
        // Registry Lock's inclusion rows render as plain <li> with no svg check
        // icon per row (unlike Woo, which pairs each row with a ✓ icon). Only
        // asserting isVisible + exact hasText here — no checkIcon guard.
        Locator item = planCard(planName)
                .locator("li[class*='" + PricingBlockClasses.INCLUDED_SUFFIX + "']")
                .filter(new Locator.FilterOptions().setHasText(feature));
        PlaywrightAssertions.assertThat(item).isVisible();
        PlaywrightAssertions.assertThat(item).hasText(feature);
        log.info("PASSED: [{}] plan includes \"{}\"", planName, feature);
    }

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        // Scoped to pricingSection() so a stray copy of the same string in the
        // footer / hero couldn't mask a real regression in the pricing note.
        // .first() disambiguates the SSR + hydration duplicate — same pattern
        // used on every landing page's hero subtitle.
        PlaywrightAssertions.assertThat(pricingSection().getByText(expectedText).first()).isVisible();
        log.info("PASSED: tax note displays \"{}\"", expectedText);
    }

    public void assertCtaButtonDisplays(String expectedText) {
        log.info("Asserting CTA button displays: \"{}\"", expectedText);
        Locator cta = pricingSection().getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(expectedText));
        PlaywrightAssertions.assertThat(cta).isVisible();
        PlaywrightAssertions.assertThat(cta).hasText(expectedText);
        log.info("PASSED: CTA button displays \"{}\"", expectedText);
    }

    public void assertCtaButtonHref(String expectedHref) {
        log.info("Asserting CTA button links to: \"{}\"", expectedHref);
        Locator cta = pricingSection().getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Add to Cart"));
        PlaywrightAssertions.assertThat(cta).hasAttribute("href", expectedHref);
        log.info("PASSED: CTA button links to \"{}\"", expectedHref);
    }

    private Locator pricingSection() {
        return page.locator("#pricing");
    }

    private Locator planCard(String planName) {
        // Anchor via the plan h3 to survive class-hash rotation between builds
        // (same pattern used on Woo). setExact(true) so a future card whose
        // title contains "Registry Lock" (or the info-block h3s that already
        // contain it as substring) can't be picked up by role-lookup partial
        // matching — even though the __card ancestor filter would still
        // reject non-cards, exact matching removes the reliance.
        return page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(3).setName(planName).setExact(true))
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__card ')][1]");
    }
}
