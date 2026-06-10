package tests;

import contexts.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.NavBarPage;

public class NavBarTest {

    private final NavBarPage navBarPage;

    public NavBarTest(TestContext context) {
        this.navBarPage = new NavBarPage(context.page);
    }

    // ==================== HEADER ==================== //

    @Then("the dotPH logo is visible")
    public void assertLogoVisible() {
        navBarPage.assertLogoIsVisible();
    }

    @Then("the dotPH logo links to {string}")
    public void assertLogoHref(String expectedHref) {
        navBarPage.assertLogoHref(expectedHref);
    }

    @Then("the nav item {string} is displayed")
    public void assertNavItemDisplays(String expectedText) {
        navBarPage.assertNavItemDisplays(expectedText);
    }

    @Then("the Login button displays {string}")
    public void assertLoginButtonDisplays(String expectedText) {
        navBarPage.assertLoginButtonDisplays(expectedText);
    }

    @Then("the Login button links to {string}")
    public void assertLoginButtonHref(String expectedHref) {
        navBarPage.assertLoginButtonHref(expectedHref);
    }

    @Then("the Cart button is visible")
    public void assertCartButtonVisible() {
        navBarPage.assertCartButtonVisible();
    }

    // ==================== DROPDOWN ACTIONS ==================== //

    @When("the user clicks the {string} nav item")
    public void clickNavItem(String itemName) {
        navBarPage.clickNavItem(itemName);
    }

    @When("the user closes the dropdown")
    public void closeDropdown() {
        navBarPage.closeDropdown();
    }

    // ==================== DROPDOWN LINKS ==================== //

    @Then("the nav link {string} is displayed")
    public void assertNavLinkDisplays(String expectedText) {
        navBarPage.assertNavLinkDisplays(expectedText);
    }

    @Then("the nav link {string} links to {string}")
    public void assertNavLinkHref(String linkText, String expectedHref) {
        navBarPage.assertNavLinkHref(linkText, expectedHref);
    }
}
