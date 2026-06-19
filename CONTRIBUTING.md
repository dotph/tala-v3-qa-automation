# Contributing

How we add to this suite. Read this once before you open your first PR.

## Code conventions

The full version lives in [docs/conventions.md](docs/conventions.md). The short list:

- **Role-based selectors first.** `getByRole(...)` over CSS / XPath / class-name selectors.
- **`containsText` for prose, `hasText` for exact labels, `hasAttribute` for state.** Never `.isVisible()` alone for copy assertions.
- **Config-driven values.** Pull URLs from `EnvConfig`, prices from the per-page enum (e.g. `SingleDomainHostingPlan`), forex from `EnvConfig.getExchangeRate()`. No hardcoded `$X.XX` in features.
- **Page-prefixed Cucumber steps.** Step phrases bind globally — prefix per page (e.g. `the SDH hero title displays`) to avoid `DuplicateStepDefinitionException`.
- **Two-phase logging.** Every assertion logs "Asserting …" then "PASSED: …" so traces read as a checklist.
- **`ThreadLocal` for any shared util.** TestNG runs `<test>` blocks in parallel.
- **No `page.waitForTimeout`.** Playwright assertions auto-retry.

## Adding a new page suite

Step-by-step checklist: [docs/adding-a-new-page-suite.md](docs/adding-a-new-page-suite.md). The most common mistake is forgetting to **register the new runner in `src/test/resources/features/testng.xml`** — without that, `mvn test` (the CI command) silently skips your suite.

## Branch / commit / PR-title format

Every branch, commit, and PR is tagged with the Jira ticket key and a type prefix so changes are traceable end-to-end.

| Artefact | Format | Example |
|---|---|---|
| Branch | `<type>/<TICKET-ID>-<short-kebab-summary>` | `test/QATEAM-123-single-domain-hosting` |
| Commit | `<TICKET-ID>: <type>(<scope>): <subject>` | `QATEAM-123: test(sdh): add Single Domain Hosting landing page automation` |
| PR title | `<type>(<scope>): <subject> [<TICKET-ID>]` | `test(sdh): add Single Domain Hosting landing page UI automation [QATEAM-123]` |

**Types:** `feat`, `fix`, `docs`, `style`, `refactor`, `perf`, `test`, `build`, `ci`, `chore`, `revert`.

**Scope** is usually the page key (`sdh`, `cpanel`, `vps`, …) or a cross-cutting label (`ci`, `docs`, `config`).

Existing branches created before this convention was introduced are grandfathered — the rule applies to **new branches** from the convention's adoption onward.

## PR description

Use the template in [`.github/PULL_REQUEST_TEMPLATE.md`](.github/PULL_REQUEST_TEMPLATE.md). Four required sections:

- **Summary** — what changed and why.
- **Closes** — a `Closes <TICKET-ID>` line so the Jira ticket closes automatically on merge.
- **Test plan** — the commands you ran and their result. Attach trace zips for any flake you investigated.
- **References** — Jira ticket link, related upstream PRs/issues, relevant docs.

## Before you push

1. `mvn test` (no `-Dtest` override) — both suites must pass via `testng.xml`. This is what CI runs.
2. `mvn test -Dheadless=true` — catches any headless-only regressions before CI.
3. Open `mvn allure:serve` and skim the new scenarios.
4. If you added a runner, double-check it's in `testng.xml`.

## Review

Code is auto-routed to QA via [`.github/CODEOWNERS`](.github/CODEOWNERS). Expect at least one approving review from a QA team member before merge.
