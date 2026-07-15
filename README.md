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

## Jira automation hooks (optional, macOS)

`.githooks/` contains git hooks that auto-manage a Jira Story alongside each branch. Everything is per-clone opt-in — nothing runs unless you enable it.

| Trigger | Effect |
|---|---|
| `git checkout -b <name>` | Creates Story under the configured epic, assigns to you, transitions to *Work in progress*. If `<name>` is shaped `<type>/<slug>` without a key, renames it to `<type>/<KEY>-<slug>` after the ticket is created. |
| `git commit` | Warns (never blocks) if the commit subject does not start with a Jira key. Per CONTRIBUTING.md every commit subject should be `<TICKET-ID> <type>(<scope>): <subject>` — e.g. `QATEAM-1007 chore(hooks): …`. Body lines and merge/revert/fixup/squash/amend commits are left alone. |
| `git push origin <name>` | Transitions ticket to *Code Review* |
| `git checkout` away from a branch whose PR is merged (including `git checkout main`) | Transitions that ticket to *Done* (uses `gh` CLI to verify the PR is merged first) |
| `git jira-plan` (alias) | Interactively stages a summary + Claude-enhanced description for the next `checkout -b` |

First-time setup on a fresh clone:

```bash
# 1. Point git at the tracked hooks directory
git config --local core.hooksPath .githooks

# 2. Jira config (values differ per user/team)
git config --local jira.baseUrl https://dotph.atlassian.net
git config --local jira.projectKey QATEAM
git config --local jira.userEmail your.name@dot.ph
git config --local jira.epicKey QATEAM-970
git config --local jira.assigneeAccountId <your-atlassian-account-id>

# 3. Register the git jira-plan alias
git config --local alias.jira-plan '!bash "$(git rev-parse --show-toplevel)/.githooks/jira-plan"'

# 4. Store an Atlassian API token in macOS Keychain
#    Generate at https://id.atlassian.com/manage-profile/security/api-tokens.
#    `security` prompts for the token interactively so it never lands on
#    another process's argv (visible to `ps`) or in shell history.
security add-generic-password -a "your.name@dot.ph" -s "jira-api-token" -U -w
```

After first use, macOS will prompt for Keychain access on every checkout/push. Click **Always Allow** on the prompt (or open **Keychain Access → jira-api-token → Access Control → Always allow access by these applications: security, curl**) to avoid the recurring popup.

`git jira-plan` pipes the description you type through `claude -p` for a two-sentence rewrite before staging it. That text leaves your machine on its way to Anthropic's API — if the description is anything sensitive, cancel out of the prompt or answer with a placeholder and edit the ticket afterwards.

Hooks never block git — they always `exit 0`. Missing config, an invalid token, or network failure just causes the ticket action to skip silently (a `[jira] WARNING:` line is printed). To disable, run `git config --local --unset core.hooksPath`.

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
