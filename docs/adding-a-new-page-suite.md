# Adding a new page suite

Checklist for landing a new page (e.g. `/multiple-domain-hosting`, `/vps`, `/woocommerce`) into the suite. Mirrors what we did for `cpanel-hosting` and `single-domain-hosting`.

## 1. URL in `EnvConfig`

Add a `getXxxHostingUrl()` next to the existing helpers in `src/main/java/config/EnvConfig.java`:

```java
public static String getVpsUrl() {
    return getBaseUrl() + "/vps";
}
```

## 2. Pricing enum (if the page lists prices)

One enum per priced page lives in `src/main/java/config/`. Pattern: label + `BigDecimal` monthly price, plus a `getStartingPrice()` helper if the page has a "from $X" tile.

```java
public enum VpsPlan {
    BASIC("Basic", new BigDecimal("15.00")),
    PRO  ("Pro",   new BigDecimal("35.00"));
    // …
}
```

## 3. Page Object

Create `src/test/java/pages/XxxPage.java`. Mirror the structure of `CpanelHostingPage` / `SingleDomainHostingPage`:

- `navigateToXxxPage()` using the new `EnvConfig` URL.
- Per-section methods (`assertHeroTitleText`, `assertPlanPrice`, etc.) that use **role-based locators** and `containsText` / `hasText` (see [conventions.md](conventions.md)).
- Private `getXxxCard(name)` helpers to scope to a card by its heading.
- Two-phase logging (`log.info("Asserting …")` → `log.info("PASSED: …")`).

Page Objects take a `Page` in the constructor and store it. They never own the browser lifecycle.

## 4. Step-def glue

Create `src/test/java/tests/XxxTest.java`. Constructor takes `TestContext` (picocontainer wires this), instantiates the Page Object.

**Prefix any step phrases** that could collide with another page's identically-shaped step (most hero / plans / tax steps qualify). The Given step (`the user navigates to the Xxx page`) is naturally unique and doesn't need a prefix. NavBar and Footer reuse the existing shared step-defs — don't redefine them.

```java
@Then("the {string} VPS plan displays the monthly price")
public void assertPlanMonthlyPrice(String planName) {
    BigDecimal price = VpsPlan.fromLabel(planName).getMonthlyPrice();
    vpsPage.assertPlanPrice(planName, "$" + price.toPlainString());
}
```

## 5. Runner

Create `src/test/java/runner/XxxRunner.java`. Identical to `CpanelHostingRunner` but pointing at the new feature folder:

```java
@CucumberOptions(
    features = "src/test/resources/features/vps",
    glue = {"tests"},
    plugin = {"pretty",
              "json:target/cucumber.json",
              "html:target/cucumber-report.html",
              "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
    monochrome = true
)
public class VpsRunner extends BaseRunner {}
```

## 6. Register in `testng.xml` ⚠️

**This step is easy to miss and causes the suite to silently skip in CI.**

Edit `src/test/resources/features/testng.xml` and add a `<test>` block alongside the existing ones:

```xml
<test name="VPS Landing Page">
    <classes>
        <class name="runner.VpsRunner"/>
    </classes>
</test>
```

Without this, `mvn test` (what CI runs) won't execute the new suite — only `mvn test -Dtest=VpsRunner` will, which CI doesn't use.

If the new runner pushes total parallel `<test>` blocks past two, also bump `thread-count` on the `<suite>` element to match (subject to host CPU/RAM).

## 7. Feature files

Create `src/test/resources/features/<x>/`:

- `01-NavBar.feature` — full nav coverage. Reuses shared `NavBarTest` glue. Only the Given step changes per page.
- `02-XxxLanding.feature` — page-specific scenarios (hero, plans, tax, interactions). Uses the prefixed steps from `XxxTest`.
- `03-Footer.feature` — full footer coverage. Reuses shared `FooterTest` glue. Only the Given step changes per page.

Tag each scenario at least `@<page-key>` plus one of `@smoke` / `@sanity` so `mvn test -Dcucumber.filter.tags="@smoke and @<page-key>"` is usable.

## 8. Verify

```bash
mvn -q test-compile                            # compile cleanly
mvn test                                       # all suites pass via testng.xml ← proves step 6 worked
mvn test -Dtest=XxxRunner                      # targeted re-run, sanity check
mvn test -Dheadless=true                       # CI-equivalent
```

Open the Allure report (`mvn allure:serve`) and confirm the new scenarios appear.

## 9. PR

Follow the conventions in [`../CONTRIBUTING.md`](../CONTRIBUTING.md) — branch name, commit prefix, PR title, PR template with `Closes <TICKET>`.
