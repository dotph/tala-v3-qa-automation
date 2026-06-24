# TALA v3 QA Automation

Automated UI test coverage for TALA v3 (the rewrite of the TALA codebase, aligning the Ruby version to the latest while preserving existing behaviour).

Built with **Playwright Java 1.45**, **TestNG 7**, **Cucumber 7**, and **Allure 2** on **Java 21**.

## Prerequisites

- **Java 21** (Temurin or equivalent).
- **Maven 3.9+**.
- **Playwright browsers** — installed once into `~/.cache/pw-browsers-java` (where `PlaywrightUtils` looks):

  ```bash
  PLAYWRIGHT_BROWSERS_PATH=$HOME/.cache/pw-browsers-java \
    mvn exec:java -e \
      -Dexec.mainClass="com.microsoft.playwright.CLI" \
      -Dexec.args="install chromium"
  ```

## Running tests

| Goal | Command |
|---|---|
| All registered suites (CI default) | `mvn test` |
| One specific suite | `mvn test -Dtest=SingleDomainHostingRunner` |
| Filter by Cucumber tags | `mvn test -Dcucumber.filter.tags="@smoke and @pricing"` |
| Headless (CI default) | `mvn test -Dheadless=true` |

`mvn test` is driven by [`src/test/resources/features/testng.xml`](src/test/resources/features/testng.xml). To add a new suite to the default run, register its runner there — see [docs/adding-a-new-page-suite.md](docs/adding-a-new-page-suite.md).

## Runtime switches

All are passed as `-D` system properties on the Maven command line. See [`.env.example`](.env.example) for the contract.

| Property | Default | Source | Notes |
|---|---|---|---|
| `testEnv` | `MDOT` | `EnvConfig.java` | `MDOT` (staging) or `PRODUCTION` (`dot.ph`). Drives `getBaseUrl()`. |
| `exchangeRate` | `60.50` | `EnvConfig.java` | USD → PHP rate used in the footer forex assertion. |
| `headless` | `false` | `PlaywrightUtils.java` | Run Chromium headless. CI sets `true`. |

## Reports and artifacts

| What | Where | Open with |
|---|---|---|
| Allure results | `target/allure-results/` | `mvn allure:serve` |
| Per-scenario Playwright traces | `traces/<MM-dd-yyyy>/*.zip` | `npx playwright show-trace <file>` |
| Failure screenshots | `screenshots/<MM-dd-yyyy>/*.png` | any image viewer |
| Log4j2 logs | `logs/` | tail / grep |

All four directories are gitignored.

## Project layout

```
src/
├── main/java/
│   ├── config/                  Env + per-page pricing enums (e.g. SingleDomainHostingPlan)
│   └── utils/PlaywrightUtils    ThreadLocal browser lifecycle + tracing helpers
└── test/
    ├── java/
    │   ├── contexts/TestContext Cucumber-scoped Page handle
    │   ├── pages/               Page Objects (CpanelHostingPage, SingleDomainHostingPage, …)
    │   ├── tests/               Cucumber step-def glue (incl. shared Hooks, NavBarTest, FooterTest)
    │   └── runner/              One <X>Runner per landing page, all extending BaseRunner
    └── resources/
        └── features/
            ├── testng.xml       Suite registry — every new runner must be added here
            └── <page>/          NavBar / Landing / Footer feature files per page
```

See [docs/architecture.md](docs/architecture.md) for the wiring details and [docs/conventions.md](docs/conventions.md) for selector / assertion / step-phrase rules.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for code conventions, naming rules, and the PR workflow.
