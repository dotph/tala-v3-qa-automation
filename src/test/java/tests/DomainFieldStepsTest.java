package tests;

import contexts.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.components.DomainField;

/**
 * Page-agnostic step definitions for the shared "Apply to:" domain field.
 * The current page context (set by the Background's navigate step) determines
 * which landing page's field is under test, so the Gherkin steps here carry
 * no SDH/MDH prefix.
 */
public class DomainFieldStepsTest {
    private final DomainField domainField;

    public DomainFieldStepsTest(TestContext context) {
        this.domainField = new DomainField(context.page.locator("#pricing"));
    }

    @Then("the Apply to label displays {string}")
    public void assertApplyToLabel(String expectedLabel) {
        domainField.assertLabelDisplays(expectedLabel);
    }

    @Then("the domain input field is visible")
    public void assertDomainFieldVisible() {
        domainField.assertVisible();
    }

    @Then("the domain input placeholder displays {string}")
    public void assertDomainFieldPlaceholder(String expectedPlaceholder) {
        domainField.assertPlaceholder(expectedPlaceholder);
    }

    @When("the user fills the domain input with {string}")
    public void fillDomainField(String value) {
        domainField.fill(value);
    }

    @Then("the domain input value is {string}")
    public void assertDomainFieldValue(String expectedValue) {
        domainField.assertValue(expectedValue);
    }
}
