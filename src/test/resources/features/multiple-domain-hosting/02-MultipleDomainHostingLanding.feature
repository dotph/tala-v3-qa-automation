Feature: Multiple Domain Hosting Landing Page
  Verify the copies, buttons, and links on the Multiple Domain Hosting landing page.

  Background:
    Given the user navigates to the Multiple Domain Hosting page

  # ==================== HERO SECTION ==================== #
  @multiple-domain-hosting @smoke @hero
  Scenario: Hero section displays correct copies
    Then the MDH hero title displays "Multiple Domain Hosting"
    And the MDH hero subtitle displays "Manage multiple sites easily with the industry-standard cPanel interface. Our plans start at 10 domains and 200 email accounts, with guaranteed quality customer service and an easy-to-use interface."
    And the MDH See Pricing button displays "See Pricing"
    And the MDH See Pricing button links to "#pricing"
    When the user clicks the MDH See Pricing button
    Then the MDH pricing section is visible in the viewport

  # ==================== HOSTING PLANS ==================== #
  @multiple-domain-hosting @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the MDH plans section title displays "Choose Your Plan"

  @multiple-domain-hosting @smoke @pricing @default-selection
  Scenario: MD3 plan is selected by default
    Then the MDH default selected plan is "MD3"
    And the "MD1" MDH plan is not selected by default
    And the "MD2" MDH plan is not selected by default
    And the "MD4" MDH plan is not selected by default
    And the "MD5" MDH plan is not selected by default
    And the MDH CTA button reflects the "MD3" plan
    And the "MD3" MDH CTA button links to "#"

  @multiple-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can select the MD1 plan
    When the user selects the "MD1" MDH plan
    Then the "MD1" MDH plan is in the selected state
    And the "MD2" MDH plan is in the unselected state
    And the "MD3" MDH plan is in the unselected state
    And the "MD4" MDH plan is in the unselected state
    And the "MD5" MDH plan is in the unselected state
    And the MDH CTA button reflects the "MD1" plan
    And the "MD1" MDH CTA button links to "#"

  @multiple-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can select the MD2 plan
    When the user selects the "MD2" MDH plan
    Then the "MD2" MDH plan is in the selected state
    And the "MD1" MDH plan is in the unselected state
    And the "MD3" MDH plan is in the unselected state
    And the "MD4" MDH plan is in the unselected state
    And the "MD5" MDH plan is in the unselected state
    And the MDH CTA button reflects the "MD2" plan
    And the "MD2" MDH CTA button links to "#"

  @multiple-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can select the MD4 plan
    When the user selects the "MD4" MDH plan
    Then the "MD4" MDH plan is in the selected state
    And the "MD1" MDH plan is in the unselected state
    And the "MD2" MDH plan is in the unselected state
    And the "MD3" MDH plan is in the unselected state
    And the "MD5" MDH plan is in the unselected state
    And the MDH CTA button reflects the "MD4" plan
    And the "MD4" MDH CTA button links to "#"

  @multiple-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can select the MD5 plan
    When the user selects the "MD5" MDH plan
    Then the "MD5" MDH plan is in the selected state
    And the "MD1" MDH plan is in the unselected state
    And the "MD2" MDH plan is in the unselected state
    And the "MD3" MDH plan is in the unselected state
    And the "MD4" MDH plan is in the unselected state
    And the MDH CTA button reflects the "MD5" plan
    And the "MD5" MDH CTA button links to "#"

  @multiple-domain-hosting @sanity @pricing @plan-selection
  Scenario: User can re-select the MD3 plan after picking another
    When the user selects the "MD1" MDH plan
    And the user selects the "MD3" MDH plan
    Then the "MD3" MDH plan is in the selected state
    And the "MD1" MDH plan is in the unselected state
    And the "MD2" MDH plan is in the unselected state
    And the "MD4" MDH plan is in the unselected state
    And the "MD5" MDH plan is in the unselected state
    And the MDH CTA button reflects the "MD3" plan
    And the "MD3" MDH CTA button links to "#"

  @multiple-domain-hosting @sanity @pricing @md1
  Scenario: MD1 plan displays correct copies, pricing, and specs
    Then the MDH plan title displays "MD1"
    And the "MD1" MDH plan displays the monthly price
    And the "MD1" MDH plan displays the billing period "/ month"
    And the "MD1" MDH plan displays the description "Start managing multiple sites"
    And the "MD1" MDH plan includes "10 domains"
    And the "MD1" MDH plan includes "40 GB disk space"
    And the "MD1" MDH plan includes "100 GB bandwidth"
    And the "MD1" MDH plan includes "200 email accounts"
    And the "MD1" MDH plan excludes "Free SSL"

  @multiple-domain-hosting @sanity @pricing @md2
  Scenario: MD2 plan displays correct copies, pricing, and specs
    Then the MDH plan title displays "MD2"
    And the "MD2" MDH plan displays the monthly price
    And the "MD2" MDH plan displays the billing period "/ month"
    And the "MD2" MDH plan displays the description "More domains, more power"
    And the "MD2" MDH plan includes "20 domains"
    And the "MD2" MDH plan includes "60 GB disk space"
    And the "MD2" MDH plan includes "200 GB bandwidth"
    And the "MD2" MDH plan includes "400 email accounts"
    And the "MD2" MDH plan excludes "Free SSL"

  @multiple-domain-hosting @sanity @pricing @md3
  Scenario: MD3 plan displays correct copies, pricing, and specs
    Then the MDH plan title displays "MD3"
    And the "MD3" MDH plan displays the monthly price
    And the "MD3" MDH plan displays the billing period "/ month"
    And the "MD3" MDH plan displays the description "Growing businesses"
    And the "MD3" MDH plan includes "40 domains"
    And the "MD3" MDH plan includes "100 GB disk space"
    And the "MD3" MDH plan includes "400 GB bandwidth"
    And the "MD3" MDH plan includes "500 email accounts"
    And the "MD3" MDH plan includes "Free SSL"

  @multiple-domain-hosting @sanity @pricing @md4
  Scenario: MD4 plan displays correct copies, pricing, and specs
    Then the MDH plan title displays "MD4"
    And the "MD4" MDH plan displays the monthly price
    And the "MD4" MDH plan displays the billing period "/ month"
    And the "MD4" MDH plan displays the description "High-volume operations"
    And the "MD4" MDH plan includes "50 domains"
    And the "MD4" MDH plan includes "120 GB disk space"
    And the "MD4" MDH plan includes "500 GB bandwidth"
    And the "MD4" MDH plan includes "600 email accounts"
    And the "MD4" MDH plan includes "Free SSL"

  @multiple-domain-hosting @sanity @pricing @md5
  Scenario: MD5 plan displays correct copies, pricing, and specs
    Then the MDH plan title displays "MD5"
    And the "MD5" MDH plan displays the monthly price
    And the "MD5" MDH plan displays the billing period "/ month"
    And the "MD5" MDH plan displays the description "Maximum scale"
    And the "MD5" MDH plan includes "60 domains"
    And the "MD5" MDH plan includes "150 GB disk space"
    And the "MD5" MDH plan includes "600 GB bandwidth"
    And the "MD5" MDH plan includes "800 email accounts"
    And the "MD5" MDH plan includes "Free SSL"

  @multiple-domain-hosting @sanity @pricing @apply-domain
  Scenario: Apply-to domain field accepts input
    Then the MDH Apply to label displays "Apply to:"
    And the MDH domain input field is visible
    And the MDH domain input placeholder displays "Find your domain name here"
    When the user fills the MDH domain input with "example.com"
    Then the MDH domain input value is "example.com"

  @multiple-domain-hosting @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the MDH tax note displays "* Applicable taxes may apply at checkout."
