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
    Then the SDH pricing section is visible in the viewport

  # ==================== HOSTING PLANS ==================== #
  @single-domain-hosting @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the SDH plans section title displays "Choose Your Plan"

  @single-domain-hosting @smoke @pricing @default-selection
  Scenario: Professional plan is selected by default
    Then the SDH default selected plan is "Professional"
    And the "Starter" SDH plan is not selected by default
    And the "Deluxe" SDH plan is not selected by default
    And the SDH CTA button reflects the "Professional" plan

  @single-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can select the Starter plan
    When the user selects the "Starter" SDH plan
    Then the "Starter" SDH plan is in the selected state
    And the "Professional" SDH plan is in the unselected state
    And the "Deluxe" SDH plan is in the unselected state
    And the SDH CTA button reflects the "Starter" plan

  @single-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can select the Deluxe plan
    When the user selects the "Deluxe" SDH plan
    Then the "Deluxe" SDH plan is in the selected state
    And the "Starter" SDH plan is in the unselected state
    And the "Professional" SDH plan is in the unselected state
    And the SDH CTA button reflects the "Deluxe" plan

  @single-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can re-select the Professional plan after picking another
    When the user selects the "Starter" SDH plan
    And the user selects the "Professional" SDH plan
    Then the "Professional" SDH plan is in the selected state
    And the "Starter" SDH plan is in the unselected state
    And the "Deluxe" SDH plan is in the unselected state
    And the SDH CTA button reflects the "Professional" plan

  @single-domain-hosting @sanity @pricing @starter
  Scenario: Starter plan displays correct copies, pricing, and specs
    Then the SDH plan title displays "Starter"
    And the "Starter" SDH plan displays the monthly price
    And the "Starter" SDH plan displays the billing period "/ month"
    And the "Starter" SDH plan displays the description "Everything you need to get online"
    And the "Starter" SDH plan includes its domain limit
    And the "Starter" SDH plan includes its disk space
    And the "Starter" SDH plan includes its bandwidth
    And the "Starter" SDH plan includes its subdomain limit
    And the "Starter" SDH plan includes its email account limit
    And the "Starter" SDH plan includes its mailing list limit
    And the "Starter" SDH plan includes its SSL coverage
    And the "Starter" SDH plan Free SSL indicator matches its plan

  @single-domain-hosting @sanity @pricing @professional
  Scenario: Professional plan displays correct copies, pricing, and specs
    Then the SDH plan title displays "Professional"
    And the "Professional" SDH plan displays the monthly price
    And the "Professional" SDH plan displays the billing period "/ month"
    And the "Professional" SDH plan displays the description "More power for growing sites"
    And the "Professional" SDH plan includes its domain limit
    And the "Professional" SDH plan includes its disk space
    And the "Professional" SDH plan includes its bandwidth
    And the "Professional" SDH plan includes its subdomain limit
    And the "Professional" SDH plan includes its email account limit
    And the "Professional" SDH plan includes its mailing list limit
    And the "Professional" SDH plan includes its SSL coverage
    And the "Professional" SDH plan Free SSL indicator matches its plan

  @single-domain-hosting @sanity @pricing @deluxe
  Scenario: Deluxe plan displays correct copies, pricing, and specs
    Then the SDH plan title displays "Deluxe"
    And the "Deluxe" SDH plan displays the monthly price
    And the "Deluxe" SDH plan displays the billing period "/ month"
    And the "Deluxe" SDH plan displays the description "Maximum resources for demanding sites"
    And the "Deluxe" SDH plan includes its domain limit
    And the "Deluxe" SDH plan includes its disk space
    And the "Deluxe" SDH plan includes its bandwidth
    And the "Deluxe" SDH plan includes its subdomain limit
    And the "Deluxe" SDH plan includes its email account limit
    And the "Deluxe" SDH plan includes its mailing list limit
    And the "Deluxe" SDH plan includes its SSL coverage
    And the "Deluxe" SDH plan Free SSL indicator matches its plan

  @single-domain-hosting @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the SDH tax note displays "* Applicable taxes may apply at checkout."
