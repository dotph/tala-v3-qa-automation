# Architecture

A snapshot of how the suite is wired so a new contributor (or future-you) can land a change without reverse-engineering the codebase.

## Stack

- **Playwright Java 1.45** drives Chromium.
- **TestNG 7** is the test engine that Maven Surefire invokes.
- **Cucumber 7 (cucumber-testng)** lets us write scenarios in Gherkin and binds them to Java step definitions.
- **picocontainer** is the Cucumber object factory — it constructs step-def classes per scenario and injects shared dependencies (currently just `contexts.TestContext`).
- **Log4j2 + SLF4J** for logging.
- **Allure Cucumber7 JVM** for reports.

## Layered layout

```
Cucumber feature (.feature)
        │
        ▼
Step-def glue   (src/test/java/tests/*Test.java)
        │  uses
        ▼
Page Object     (src/test/java/pages/*Page.java)
        │  uses
        ▼
Playwright API  (com.microsoft.playwright.*) + helpers
        │  via
        ▼
PlaywrightUtils (src/main/java/utils/PlaywrightUtils.java) — ThreadLocal Playwright/Browser/Context/Page
```

A Cucumber Gherkin line like `Then the "Starter" SDH plan displays the monthly price` is bound by `@Then` on a method in `SingleDomainHostingTest`, which calls into `SingleDomainHostingPage`, which uses Playwright `Locator`s on the `Page` handed to it via `TestContext`.

## Browser lifecycle (parallel-safe)

`PlaywrightUtils` keeps `Playwright`, `Browser`, `BrowserContext`, and `Page` in `ThreadLocal` fields. `TestContext` constructs a fresh `TestContext` for each Cucumber scenario, which calls `PlaywrightUtils.init()` and grabs `getPage()` for the duration of the scenario. `Hooks.tearDown` calls `PlaywrightUtils.close()`.

Because every reference is `ThreadLocal`, two TestNG `<test>` blocks running in parallel (see below) each get their own browser. There is no shared mutable state between threads.

## Test execution

`pom.xml` configures Maven Surefire to read `src/test/resources/features/testng.xml`. That XML lists one `<test>` block per runner class:

```xml
<suite name="TALA V3 Suite" parallel="tests" thread-count="2">
    <test name="cPanel Hosting Landing Page">
        <classes><class name="runner.CpanelHostingRunner"/></classes>
    </test>
    <test name="Single Domain Hosting Landing Page">
        <classes><class name="runner.SingleDomainHostingRunner"/></classes>
    </test>
</suite>
```

- `parallel="tests"` runs each `<test>` block on its own thread.
- `thread-count="2"` caps concurrency at two — bump this when you add a third runner if the host has the CPU/memory.
- Each runner is a thin `@CucumberOptions`-annotated class extending `BaseRunner` (which extends Cucumber's `AbstractTestNGCucumberTests` with `@DataProvider(parallel = true)`), so **scenarios within a runner also run in parallel** up to the same cap.

`mvn test` (no `-Dtest`) is the canonical CI command — it exercises everything registered in `testng.xml`. `mvn test -Dtest=<Runner>` is a developer shortcut to focus on a single suite.

## Reporting / artefacts

- Each runner emits Cucumber JSON to `target/cucumber.json`, HTML to `target/cucumber-report.html`, and Allure result files to `target/allure-results/` via the `io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm` plugin (configured in each runner's `@CucumberOptions(plugin = …)`).
- `Hooks.setUp` starts a Playwright trace on the scenario's browser context; `Hooks.tearDown` stops it, writes the zip to `traces/<MM-dd-yyyy>/`, and attaches it to the Allure scenario.
- On scenario failure, `Hooks.tearDown` also writes a full-page screenshot to `screenshots/<MM-dd-yyyy>/` and attaches it.

## Config

- **URLs and environment switch** — `config.EnvConfig`. `getBaseUrl()` reads the `testEnv` system property (default `MDOT`). Add a new page URL by exposing a `getXxxUrl()` helper next to the existing two.
- **Pricing** — one enum per priced page lives under `src/main/java/config/`. Two examples today: `SingleDomainHostingPlan` (Starter/Professional/Deluxe) and `MultipleDomainHostingPlan` (MD1–MD5). Step defs pull prices from the enum so a price change is a single-line edit.

## Why Cucumber+TestNG (not JUnit)

- Cucumber gives PMs / QAs a readable scenario format and tag-based filtering.
- TestNG's parallel `@DataProvider` plays cleanly with Cucumber's `AbstractTestNGCucumberTests`, giving us scenario-level parallelism with minimal config — JUnit 5's parallel support exists but the Cucumber bridge is less mature.
- Surefire's `<suiteXmlFiles>` model is a clean place to gate which runners CI exercises.
