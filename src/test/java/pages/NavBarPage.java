package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import config.EnvConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class NavBarPage {
    private static final Logger log = LogManager.getLogger(NavBarPage.class);
    private Page page;

    public NavBarPage(Page page) {
        this.page = page;
    }

    private String resolveExpectedUrl(String path) {
        return path.startsWith("http") ? path : EnvConfig.getUrl(path);
    }

    // ==================== HEADER ==================== //

    public void assertLogoIsVisible() {
        log.info("Asserting dotPH logo is visible");
        PlaywrightAssertions.assertThat(
                page.locator("header").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("dotPH")))
                .isVisible();
        log.info("PASSED: dotPH logo is visible");
    }

    public void assertLogoHref(String expectedPath) {
        String expectedUrl = resolveExpectedUrl(expectedPath);
        log.info("Asserting dotPH logo links to: \"{}\"", expectedUrl);
        Locator logo = page.locator("header").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("dotPH"));
        String actualHref = logo.evaluate("el => el.href").toString();
        Assert.assertEquals(actualHref, expectedUrl, "Logo href mismatch");
        log.info("PASSED: dotPH logo links to \"{}\"", expectedUrl);
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

    public void assertLoginButtonHref(String expectedPath) {
        String expectedUrl = resolveExpectedUrl(expectedPath);
        log.info("Asserting Login button links to: \"{}\"", expectedUrl);
        Locator loginLink = page.locator("header")
                .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Login"));
        String actualHref = loginLink.evaluate("el => el.href").toString();
        Assert.assertEquals(actualHref, expectedUrl, "Login href mismatch");
        log.info("PASSED: Login button links to \"{}\"", expectedUrl);
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
        Locator link = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(expectedText)).first();
        PlaywrightAssertions.assertThat(link).containsText(expectedText);
        log.info("PASSED: nav link displays \"{}\"", expectedText);
    }

    public void assertNavLinkHref(String linkText, String expectedPath) {
        String expectedUrl = resolveExpectedUrl(expectedPath);
        log.info("Asserting nav link \"{}\" links to: \"{}\"", linkText, expectedUrl);
        Locator link = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(linkText)).first();
        String actualHref = link.evaluate("el => el.href").toString();
        Assert.assertEquals(actualHref, expectedUrl, linkText + " href mismatch");
        log.info("PASSED: nav link \"{}\" links to \"{}\"", linkText, expectedUrl);
    }
}
