package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavBarPage {
    private static final Logger log = LogManager.getLogger(NavBarPage.class);
    private Page page;

    public NavBarPage(Page page) {
        this.page = page;
    }

    /** The currently open desktop dropdown panel. */
    private Locator openDropdownPanel() {
        return page.locator("div[class*='panelWrapperOpen']");
    }

    // ==================== HEADER ==================== //

    public void assertLogoIsVisible() {
        log.info("Asserting dotPH logo is visible");
        PlaywrightAssertions.assertThat(
                page.locator("header").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("dotPH")))
                .isVisible();
        log.info("PASSED: dotPH logo is visible");
    }

    public void assertLogoHref(String expectedHref) {
        log.info("Asserting dotPH logo links to: \"{}\"", expectedHref);
        PlaywrightAssertions.assertThat(
                page.locator("header").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("dotPH")))
                .hasAttribute("href", expectedHref);
        log.info("PASSED: dotPH logo links to \"{}\"", expectedHref);
    }

    public void assertNavItemDisplays(String expectedText) {
        log.info("Asserting nav item displays: \"{}\"", expectedText);
        Locator navItem = page.getByRole(AriaRole.NAVIGATION, new Page.GetByRoleOptions().setName("Main navigation"))
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(expectedText));
        PlaywrightAssertions.assertThat(navItem).containsText(expectedText);
        log.info("PASSED: nav item displays \"{}\"", expectedText);
    }

    public void assertLoginButtonDisplays(String expectedText) {
        log.info("Asserting Login button displays: \"{}\"", expectedText);
        Locator loginLink = page.locator("header")
                .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Login"));
        PlaywrightAssertions.assertThat(loginLink).containsText(expectedText);
        log.info("PASSED: Login button displays \"{}\"", expectedText);
    }

    public void assertLoginButtonHref(String expectedHref) {
        log.info("Asserting Login button links to: \"{}\"", expectedHref);
        Locator loginLink = page.locator("header")
                .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Login"));
        PlaywrightAssertions.assertThat(loginLink).hasAttribute("href", expectedHref);
        log.info("PASSED: Login button links to \"{}\"", expectedHref);
    }

    public void assertCartButtonVisible() {
        log.info("Asserting Cart button is visible");
        PlaywrightAssertions.assertThat(
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cart")))
                .isVisible();
        log.info("PASSED: Cart button is visible");
    }

    // ==================== DROPDOWN ACTIONS ==================== //

    public void clickNavItem(String itemName) {
        log.info("Clicking nav item: \"{}\"", itemName);
        Locator navItem = page.getByRole(AriaRole.NAVIGATION, new Page.GetByRoleOptions().setName("Main navigation"))
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName(itemName));
        navItem.click();
        log.info("Clicked nav item: \"{}\"", itemName);
    }

    public void closeDropdown() {
        log.info("Closing open dropdown");
        page.keyboard().press("Escape");
        log.info("Dropdown closed");
    }

    // ==================== DROPDOWN LINKS ==================== //

    public void assertNavLinkDisplays(String expectedText) {
        log.info("Asserting nav link displays: \"{}\"", expectedText);
        Locator link = openDropdownPanel().getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(expectedText));
        PlaywrightAssertions.assertThat(link).containsText(expectedText);
        log.info("PASSED: nav link displays \"{}\"", expectedText);
    }

    public void assertNavLinkHref(String linkText, String expectedHref) {
        log.info("Asserting nav link \"{}\" links to: \"{}\"", linkText, expectedHref);
        Locator link = openDropdownPanel().getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(linkText));
        PlaywrightAssertions.assertThat(link).hasAttribute("href", expectedHref);
        log.info("PASSED: nav link \"{}\" links to \"{}\"", linkText, expectedHref);
    }
}
