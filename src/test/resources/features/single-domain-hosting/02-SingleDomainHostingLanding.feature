Feature: Single Domain Hosting Landing Page
  Verify the copies, buttons, and links on the Single Domain Hosting landing page.

  Background:
    Given the user navigates to the Single Domain Hosting page

  # ==================== HERO SECTION ==================== #
  @single-domain-hosting @smoke @hero
  Scenario: Hero section displays correct copies
    Then the SDH hero title displays "Single Domain Hosting"
    And the SDH hero subtitle displays "Launching your first website? Get a hosting plan that helps you manage your site and provides the essentials. Our plans start with 30GB storage and 10 subdomains, with peak performance to make sure your site runs smoothly."
    And the SDH See Pricing button displays "See Pricing"
    And the SDH See Pricing button links to "#pricing"
    When the user clicks the SDH See Pricing button
    Then the URL hash is "#pricing"
    And the SDH pricing section is visible in the viewport

  # ==================== HOSTING PLANS ==================== #
  # NOTE (QATEAM-970 item #3): per-card "Get {plan}" link CTAs are not part of the
  # current SDH design (only cPanel has them). The page-level CTA is asserted via
  # `CTA button reflects the "X" plan` and `CTA button links to`. Each card's
  # Select / ✓ Selected button is covered by the default-selection and
  # plan-selection scenarios (aria-pressed + button text).

  @single-domain-hosting @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the SDH plans section title displays "Choose Your Plan"

  @single-domain-hosting @sanity @pricing @plan-order
  Scenario: Plan cards appear in the correct order
    Then the SDH plan card at position 1 is "Starter"
    And the SDH plan card at position 2 is "Professional"
    And the SDH plan card at position 3 is "Deluxe"

  @single-domain-hosting @smoke @pricing @default-selection
  Scenario: Professional plan is selected by default
    Then the SDH default selected plan is "Professional"
    And the "Starter" SDH plan is not selected by default
    And the "Deluxe" SDH plan is not selected by default
    And the SDH CTA button reflects the "Professional" plan
    And the "Professional" SDH CTA button links to "#"

  @single-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can select the Starter plan
    When the user selects the "Starter" SDH plan
    Then the "Starter" SDH plan is in the selected state
    And the "Professional" SDH plan is in the unselected state
    And the "Deluxe" SDH plan is in the unselected state
    And the SDH CTA button reflects the "Starter" plan
    And the "Starter" SDH CTA button links to "#"

  @single-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can select the Deluxe plan
    When the user selects the "Deluxe" SDH plan
    Then the "Deluxe" SDH plan is in the selected state
    And the "Starter" SDH plan is in the unselected state
    And the "Professional" SDH plan is in the unselected state
    And the SDH CTA button reflects the "Deluxe" plan
    And the "Deluxe" SDH CTA button links to "#"

  @single-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can re-select the Professional plan after picking another
    When the user selects the "Starter" SDH plan
    And the user selects the "Professional" SDH plan
    Then the "Professional" SDH plan is in the selected state
    And the "Starter" SDH plan is in the unselected state
    And the "Deluxe" SDH plan is in the unselected state
    And the SDH CTA button reflects the "Professional" plan
    And the "Professional" SDH CTA button links to "#"

  @single-domain-hosting @sanity @pricing @starter
  Scenario: Starter plan displays correct copies, pricing, and specs
    Then the SDH plan title displays "Starter"
    And the "Starter" SDH plan displays the monthly price
    And the "Starter" SDH plan displays the billing period "/ month"
    And the "Starter" SDH plan displays the description "Everything you need to get online"
    And the "Starter" SDH plan includes "1 domain"
    And the "Starter" SDH plan includes "30 GB disk space"
    And the "Starter" SDH plan includes "Up to 100 GB bandwidth"
    And the "Starter" SDH plan includes "10 subdomains"
    And the "Starter" SDH plan includes "50 email accounts"
    And the "Starter" SDH plan includes "10 mailing lists"
    And the "Starter" SDH plan excludes "Free SSL"

  @single-domain-hosting @sanity @pricing @professional
  Scenario: Professional plan displays correct copies, pricing, and specs
    Then the SDH plan title displays "Professional"
    And the "Professional" SDH plan displays the monthly price
    And the "Professional" SDH plan displays the billing period "/ month"
    And the "Professional" SDH plan displays the description "More power for growing sites"
    And the "Professional" SDH plan includes "1 domain"
    And the "Professional" SDH plan includes "60 GB disk space"
    And the "Professional" SDH plan includes "Up to 200 GB bandwidth"
    And the "Professional" SDH plan includes "15 subdomains"
    And the "Professional" SDH plan includes "100 email accounts"
    And the "Professional" SDH plan includes "15 mailing lists"
    And the "Professional" SDH plan excludes "Free SSL"

  @single-domain-hosting @sanity @pricing @deluxe
  Scenario: Deluxe plan displays correct copies, pricing, and specs
    Then the SDH plan title displays "Deluxe"
    And the "Deluxe" SDH plan displays the monthly price
    And the "Deluxe" SDH plan displays the billing period "/ month"
    And the "Deluxe" SDH plan displays the description "Maximum resources for demanding sites"
    And the "Deluxe" SDH plan includes "1 domain"
    And the "Deluxe" SDH plan includes "120 GB disk space"
    And the "Deluxe" SDH plan includes "Up to 500 GB bandwidth"
    And the "Deluxe" SDH plan includes "25 subdomains"
    And the "Deluxe" SDH plan includes "300 email accounts"
    And the "Deluxe" SDH plan includes "25 mailing lists"
    And the "Deluxe" SDH plan includes "Free SSL"

  @single-domain-hosting @sanity @pricing @apply-domain
  Scenario: Apply-to domain field accepts input
    Then the SDH Apply to label displays "Apply to:"
    And the SDH domain input field is visible
    And the SDH domain input placeholder displays "Find your domain name here"
    When the user fills the SDH domain input with "example.com"
    Then the SDH domain input value is "example.com"

  @single-domain-hosting @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the SDH tax note displays "* Applicable taxes may apply at checkout."
