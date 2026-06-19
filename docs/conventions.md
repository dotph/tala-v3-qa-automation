# Conventions

Patterns the suite already follows. Stick to them so the tests keep reading the same way as we grow.

## Selectors — role-based first

Reach for `getByRole(...)` before any CSS / XPath. Role-based locators are resilient to styling changes and surface a11y regressions for free.

```java
// ✅ Good — semantic, won't break on a CSS refactor
page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("See Pricing"))

// ❌ Avoid — brittle, ties the test to current class names
page.locator(".btn-primary.see-pricing")
```

When a role-based name match isn't unique (e.g. two `Get X` links exist), narrow by an ancestor scope rather than by class:

```java
getPlanCard(planName).getByRole(AriaRole.LINK, …)
```

CSS attribute selectors like `button[aria-pressed]` are also acceptable — they target the ARIA contract, which is far more stable than utility-class names.

## Assertions — `containsText` for prose, `hasText` for exact labels

Both come from `com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat(...)`. Both auto-retry until the Playwright timeout — never wrap them in a manual `waitForTimeout`.

| Use | When | Example |
|---|---|---|
| `.containsText(s)` | Block of copy (hero subtitle, plan description) where surrounding markup may exist | `assertThat(card).containsText("Built for growing businesses…")` |
| `.hasText(s)` | Whole-element text equality (button labels, toggle states) | `assertThat(btn).hasText("Select")` |
| `.hasAttribute(k, v)` | A11y state, hrefs, data-attrs | `assertThat(btn).hasAttribute("aria-pressed", "true")` |
| `.isVisible()` | Last resort — the element's presence is the assertion (not its copy) | rare; prefer the above |

`hasText` is exact match (after whitespace normalisation), so `hasText("Select")` rejects `Selecte`, `Selected`, `✓ Selected`, and `select`. `containsText("Select")` would accept all of them and is wrong for labels.

## Config-driven values

Never hardcode prices, URLs, or exchange rates in Page Objects or features.

- URLs → `EnvConfig.getXxxUrl()`.
- Prices → per-page enum under `src/main/java/config/` (e.g. `SingleDomainHostingPlan.STARTER.getMonthlyPrice()`).
- Forex → `EnvConfig.getExchangeRate()`.

```gherkin
# ✅ — pulled from the enum at step-def time
Then the "Starter" SDH plan displays the monthly price

# ❌ — feature file owns the literal price; updating it means churn in every feature
Then the "Starter" SDH plan displays the monthly price "$6.50"
```

## Cucumber step phrases — page-prefixed

Cucumber binds step text globally, across every step-def class on the classpath. Two `@Then` methods with the same regex collide and Cucumber throws a `DuplicateStepDefinitionException` at runtime.

Two pages with similar behaviour (e.g. "the plan title displays X") **must** prefix their page-specific steps:

```java
// CpanelHostingTest
@Then("the plan title displays {string}")             // global, OK — cpanel is the only consumer today
public void assertPlanTitleDisplays(...)

// SingleDomainHostingTest
@Then("the SDH plan title displays {string}")         // SDH-prefixed — no clash
public void assertPlanTitleDisplays(...)
```

The Given step (`the user navigates to the X page`) is naturally unique per page so it doesn't need a prefix.

Shared concerns (NavBar, Footer) live in **one** step-def class (`NavBarTest`, `FooterTest`) and are reused by every page's features. They don't need a prefix because there's only one binding.

## Logging

Each Page Object method logs in two phases — entry and pass — so a trace reads like a checklist:

```java
log.info("Asserting hero title contains: \"{}\"", expectedTitle);
PlaywrightAssertions.assertThat(h1).containsText(expectedTitle);
log.info("PASSED: hero title contains \"{}\"", expectedTitle);
```

This pays off in CI when scanning a 200-line scenario log — every "PASSED:" line is a confirmed assertion.

## ThreadLocal everywhere shared

Any util that holds Playwright / browser / page state must be `ThreadLocal` (see `PlaywrightUtils`). The TestNG suite runs `<test>` blocks in parallel; a non-`ThreadLocal` shared `Page` would cross threads and produce surreal flakes.

## What we don't do

- No `page.waitForTimeout(...)` — every Playwright assertion already auto-retries until its timeout. If you find yourself needing one, the locator is probably wrong.
- No CSS-color or hex pixel snapshots for state changes — assert the semantic affordance (label flip, `aria-pressed`, class) instead. Visual regression, if it's wanted, belongs in a dedicated suite with baseline images.
- No first()/nth() without a comment explaining which match is being picked and why.
