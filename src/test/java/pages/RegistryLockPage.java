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
    private static final String PLAN_NAME = "Registry Lock";

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
        // ContentBlock renders exactly one paragraph under its H2, so a
        // section-scoped p locator is unambiguous without needing to walk
        // via a following-sibling xpath.
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
        return page.locator("section[class*='SplitFeatureBlock']")
                .getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3))
                .nth(position - 1)
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__item ')][1]");
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

    // ==================== SUBSCRIBE STEPS (StepsBlock) ==================== //

    public void assertSubscribeSectionTitle(String expectedTitle) {
        log.info("Asserting Subscribe section title: \"{}\"", expectedTitle);
        Locator h2 = stepsSection().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(2));
        PlaywrightAssertions.assertThat(h2).hasText(expectedTitle);
        log.info("PASSED: Subscribe section title \"{}\"", expectedTitle);
    }

    public void assertSubscribeStepCount(int expected) {
        log.info("Asserting Subscribe section has {} step(s)", expected);
        // Complementary to the Scenario Outline that pins each known step by
        // copy: without this, a 5th step added to StepsBlock (or a dropped one)
        // would pass unnoticed since the outline only iterates 1..4.
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

    public void assertPlanTitle(String expectedTitle) {
        log.info("Asserting plan title: \"{}\"", expectedTitle);
        Locator h3 = planCard().getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3));
        PlaywrightAssertions.assertThat(h3).hasText(expectedTitle);
        log.info("PASSED: plan title \"{}\"", expectedTitle);
    }

    public void assertPlanSupportedTldsDisplays(String expectedText) {
        log.info("Asserting plan supported TLDs paragraph: \"{}\"", expectedText);
        // PricingBlock's "supported" line is emitted as <p class="...__tagline"> —
        // NOT the same as HeroBlock's __eyebrow tagline (see hero section note).
        Locator supported = planCard().locator("p[class*='tagline']");
        PlaywrightAssertions.assertThat(supported).hasText(expectedText);
        log.info("PASSED: plan supported TLDs paragraph \"{}\"", expectedText);
    }

    public void assertPlanPriceRowText(String expectedComposedText) {
        log.info("Asserting plan price row composed text: \"{}\"", expectedComposedText);
        // Composed row text is textContent of three inline spans concatenated:
        //   <span priceDollar>$</span><span price>100.00</span><span priceStar>*</span>
        //   → "$100.00*"
        // Woo's precedent asserts the priceRow's full composed text in one call so
        // any drift (currency, amount, or the footnote-asterisk marker) surfaces
        // as a single failure. No strikethrough guard here — Registry Lock has no
        // promo/original-price element, so there's nothing to compare against.
        Locator priceRow = planCard().locator("[class*='priceRow']");
        PlaywrightAssertions.assertThat(priceRow).hasText(expectedComposedText);
        log.info("PASSED: plan price row composed text \"{}\"", expectedComposedText);
    }

    public void assertPlanBillingPeriod(String expectedPeriod) {
        log.info("Asserting plan billing period: \"{}\"", expectedPeriod);
        Locator period = planCard().locator("p[class*='period']");
        PlaywrightAssertions.assertThat(period).hasText(expectedPeriod);
        log.info("PASSED: plan billing period \"{}\"", expectedPeriod);
    }

    private static final String SELECTED_BUTTON_TEXT = "✓ Selected";

    public void assertPlanIsInSelectedState(String planName) {
        log.info("Asserting plan is in selected state: \"{}\"", planName);
        // Two-signal check: the card wrapper carries aria-pressed=true, AND the
        // inner toggle button both reads "✓ Selected" and has aria-pressed=true.
        // The card-level aria-pressed catches a silent regression where the
        // inner button flips without the card modifier following, and the
        // inner-button text guards the visible label.
        Locator card = planCard();
        PlaywrightAssertions.assertThat(card).hasAttribute("aria-pressed", "true");
        Locator innerBtn = card.locator("button[aria-pressed]");
        PlaywrightAssertions.assertThat(innerBtn).hasAttribute("aria-pressed", "true");
        PlaywrightAssertions.assertThat(innerBtn).hasText(SELECTED_BUTTON_TEXT);
        log.info("PASSED: plan \"{}\" is in selected state", planName);
    }

    public void assertPlanInclusionsHeading(String expectedHeading) {
        log.info("Asserting plan inclusions heading: \"{}\"", expectedHeading);
        Locator heading = planCard().locator("p[class*='" + PricingBlockClasses.INCLUSIONS_HEADING + "']");
        PlaywrightAssertions.assertThat(heading).hasText(expectedHeading);
        log.info("PASSED: plan inclusions heading \"{}\"", expectedHeading);
    }

    public void assertPlanIncludes(String feature) {
        log.info("Asserting plan includes feature: \"{}\"", feature);
        // Registry Lock's inclusion rows render as plain <li> with no svg check
        // icon per row (unlike Woo, which pairs each row with a ✓ icon). Only
        // asserting isVisible + exact hasText here — no checkIcon guard.
        Locator item = planCard()
                .locator("li[class*='" + PricingBlockClasses.INCLUDED_SUFFIX + "']")
                .filter(new Locator.FilterOptions().setHasText(feature));
        PlaywrightAssertions.assertThat(item).isVisible();
        PlaywrightAssertions.assertThat(item).hasText(feature);
        log.info("PASSED: plan includes \"{}\"", feature);
    }

    public void assertTaxNoteDisplays(String expectedText) {
        log.info("Asserting tax note displays: \"{}\"", expectedText);
        // .first() disambiguates the SSR + hydration duplicate — same pattern
        // used on Woo's tax note and every landing page's hero subtitle.
        PlaywrightAssertions.assertThat(page.getByText(expectedText).first()).isVisible();
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

    private Locator planCard() {
        // Anchor via the plan h3 to survive class-hash rotation between builds
        // (same pattern used on Woo). Registry Lock ships a single card, but
        // going through the h3 keeps the locator strategy consistent with the
        // multi-card landing pages.
        return page.getByRole(AriaRole.HEADING,
                        new Page.GetByRoleOptions().setLevel(3).setName(PLAN_NAME))
                .locator("xpath=ancestor::div[contains(concat(' ', @class, ' '), '__card ')][1]");
    }
}
