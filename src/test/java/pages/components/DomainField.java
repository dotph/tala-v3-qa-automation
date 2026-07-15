package pages.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * "Apply to:" domain search field that appears in the PricingBlock component
 * on the SDH, MDH, and WooCommerce landing pages. Same DOM shape across all
 * three, so the page objects share this component instance and Gherkin
 * routes through a single {@code DomainFieldStepsTest} class instead of
 * page-prefixed step defs on each landing page's test class.
 */
public class DomainField {
    private static final Logger log = LogManager.getLogger(DomainField.class);
    private final Locator pricingSection;

    public DomainField(Locator pricingSection) {
        this.pricingSection = pricingSection;
    }

    private Locator input() {
        // .first() for the same reason as the hero subtitle in each landing page:
        // Playwright's test browser occasionally renders the pricing block twice
        // (SSR + hydration duplicate) under parallel load. Both copies carry the
        // same DOM shape, so picking the first is deterministic and safe.
        return pricingSection.getByRole(AriaRole.SEARCHBOX).first();
    }

    private Locator label() {
        return pricingSection.locator("p[class*='applyLabel']").first();
    }

    public void assertLabelDisplays(String expectedLabel) {
        log.info("Asserting Apply to label displays: \"{}\"", expectedLabel);
        PlaywrightAssertions.assertThat(label()).containsText(expectedLabel);
        log.info("PASSED: Apply to label displays \"{}\"", expectedLabel);
    }

    public void assertVisible() {
        log.info("Asserting domain input field is visible");
        PlaywrightAssertions.assertThat(input()).isVisible();
        log.info("PASSED: domain input field is visible");
    }

    public void assertPlaceholder(String expectedPlaceholder) {
        log.info("Asserting domain input placeholder: \"{}\"", expectedPlaceholder);
        PlaywrightAssertions.assertThat(input()).hasAttribute("placeholder", expectedPlaceholder);
        log.info("PASSED: domain input placeholder \"{}\"", expectedPlaceholder);
    }

    public void fill(String value) {
        log.info("Filling domain input with: \"{}\"", value);
        input().fill(value);
        log.info("Filled domain input with \"{}\"", value);
    }

    public void assertValue(String expectedValue) {
        log.info("Asserting domain input value: \"{}\"", expectedValue);
        PlaywrightAssertions.assertThat(input()).hasValue(expectedValue);
        log.info("PASSED: domain input value \"{}\"", expectedValue);
    }
}
