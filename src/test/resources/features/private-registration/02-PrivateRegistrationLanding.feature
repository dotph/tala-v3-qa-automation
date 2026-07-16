Feature: Private Registration Landing Page
  Verify the copies, buttons, and links on the Private Registration landing page.

  Background:
    Given the user navigates to the Private Registration page

  # ==================== HERO SECTION ==================== #
  @private-registration @smoke @hero
  Scenario: Hero section displays correct copies
    Then the PR hero kicker displays "Security & Add-ons"
    And the PR hero title displays "Private Registration"
    And the PR hero subtitle displays "All domain registries require your accurate registrant information for every domain you register. By default, your contact information is publicly accessible via a simple WHOIS search. With Private Registration, your contact details remain secure."
    And the PR Buy Now button displays "Buy Now"
    And the PR Buy Now button links to "#pricing"
    When the user clicks the PR Buy Now button
    Then the URL hash is "#pricing"
    And the PR pricing section is visible in the viewport

  # ==================== CONFIDENTIAL SECTION ==================== #
  @private-registration @sanity @confidential
  Scenario: Confidential section displays correct copies for all three sub-features
    Then the PR Confidential section title displays "Keep your personal information confidential."
    And the PR Confidential sub-heading displays "Keeps Information Safe"
    And the PR Confidential sub-heading displays "Anti-Spam Protection"
    And the PR Confidential sub-heading displays "Live Support"
    And the "Keeps Information Safe" PR description includes "identity theft, data mining, and domain hijacking"
    And the "Anti-Spam Protection" PR description includes "newly-registered domains are easily vulnerable to scammers"
    And the "Live Support" PR description includes "call, chat, or email"

  # ==================== PLAN CARD ==================== #
  # NOTE: Private Registration ships a single tier — no plan-order or
  # plan-selection scenarios (there's nothing to toggle to). Price row is
  # "$10.00*" (currency + value + tax-footnote asterisk), no strikethrough
  # since there's no promo. The single inclusion set renders as ✓ Included,
  # so no `… plan excludes "X"` scenarios apply.

  @private-registration @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the PR plans section title displays "Private Registration"

  @private-registration @sanity @pricing @plan-count
  Scenario: Plans section renders exactly one plan card
    Then the PR plans section has 1 plan card

  @private-registration @sanity @pricing @inclusions-heading
  Scenario: The plan card shows the inclusions heading
    Then the "Private Registration" PR plan inclusions heading displays "What's included:"

  @private-registration @smoke @pricing @default-selection
  Scenario: Private Registration plan is selected by default
    Then the PR default selected plan is "Private Registration"

  @private-registration @sanity @pricing @plan-details
  Scenario: Private Registration plan displays correct copies, pricing, and specs
    Then the PR plan title displays "Private Registration"
    And the "Private Registration" PR plan displays the subtitle "Per domain, per year"
    And the "Private Registration" PR plan displays the yearly pricing
    And the "Private Registration" PR plan displays the billing period "/ yr"
    And the "Private Registration" PR plan includes "WHOIS information hidden"
    And the "Private Registration" PR plan includes "Protection from identity theft"
    And the "Private Registration" PR plan includes "Anti-spam protection"
    And the "Private Registration" PR plan includes "Secure forwarding of communications"

  @private-registration @sanity @pricing @apply-domain
  Scenario: Apply-to domain field accepts input
    Then the Apply to label displays "Apply to:"
    And the domain input field is visible
    And the domain input placeholder displays "Find your domain name here"
    When the user fills the domain input with "example.com"
    Then the domain input value is "example.com"

  @private-registration @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the PR tax note displays "* Applicable taxes may apply at checkout."

  @private-registration @smoke @pricing @cta
  Scenario: Add to Cart button displays correct copy and href
    Then the PR Add to Cart button displays "Add to Cart"
    And the PR Add to Cart button links to "#"
