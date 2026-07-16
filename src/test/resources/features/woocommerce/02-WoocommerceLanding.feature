Feature: WooCommerce Landing Page
  Verify the copies, buttons, and links on the WooCommerce landing page.

  Background:
    Given the user navigates to the WooCommerce page

  # ==================== HERO SECTION ==================== #
  @woocommerce @smoke @hero
  Scenario: Hero section displays correct copies
    Then the Woo hero title displays "The most trusted ecommerce platform for building success."
    And the Woo hero subtitle displays "WooCommerce helps users accelerate business growth, whether selling online or creating stores for others."
    And the Woo See Pricing button displays "See Pricing"
    And the Woo See Pricing button links to "#pricing"
    When the user clicks the Woo See Pricing button
    Then the URL hash is "#pricing"
    And the Woo pricing section is visible in the viewport

  # ==================== START GROWING SECTION ==================== #
  @woocommerce @sanity @start-growing
  Scenario: Start growing section displays correct copies for all three sub-features
    Then the Woo Start growing section title displays "Start growing your business with WooCommerce."
    And the Woo Start growing intro includes "WooCommerce assists you in realizing your goals for success"
    And the Woo Start growing sub-heading displays "Create your perfect store"
    And the Woo Start growing sub-heading displays "Explore free WooCommerce extensions"
    And the Woo Start growing sub-heading displays "Boost your store's potential"
    And the "Create your perfect store" Woo description includes "leading open-source ecommerce solution globally"
    And the "Explore free WooCommerce extensions" Woo description includes "premium extensions"
    And the "Boost your store's potential" Woo description includes "hundreds of extensions and themes"

  # ==================== HOSTING PLANS ==================== #
  # NOTE: WooCommerce ships two plans (vs. SDH/MDH/VPS's 3–5), both under a
  # "50% OFF" promo. Every card renders a struck-through original price above
  # the promoted monthly (asserted together in one priceRow hasText, plus a
  # text-decoration guard on the originalPrice span so a silent strikethrough
  # removal doesn't slip past). All inclusions render as ✓ Included today —
  # no dimmed/excluded rows, so no `… plan excludes "X"` scenarios here.

  @woocommerce @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the Woo plans section title displays "Choose Your WooCommerce Plan"

  @woocommerce @sanity @pricing @plan-count
  Scenario: Plans section renders exactly two plan cards
    Then the Woo plans section has 2 plan cards

  @woocommerce @sanity @pricing @plan-order
  Scenario: Plan cards appear in the correct order
    Then the Woo plan card at position 1 is "Start-up Plan"
    And the Woo plan card at position 2 is "Pro Plan"

  @woocommerce @sanity @pricing @inclusions-heading
  Scenario: Every plan card shows the inclusions heading
    Then the "Start-up Plan" Woo plan inclusions heading displays "What's included:"
    And the "Pro Plan" Woo plan inclusions heading displays "What's included:"

  @woocommerce @smoke @pricing @default-selection
  Scenario: Pro Plan is selected by default
    Then the Woo default selected plan is "Pro Plan"
    And the "Start-up Plan" Woo plan is not selected by default
    And the Woo CTA button reflects the "Pro Plan" plan
    And the "Pro Plan" Woo CTA button links to "#"

  @woocommerce @sanity @pricing @plan-selection
  Scenario: User can select the Start-up Plan
    When the user selects the "Start-up Plan" Woo plan
    Then the "Start-up Plan" Woo plan is in the selected state
    And the "Pro Plan" Woo plan is in the unselected state
    And the Woo CTA button reflects the "Start-up Plan" plan
    And the "Start-up Plan" Woo CTA button links to "#"

  @woocommerce @sanity @pricing @plan-selection
  Scenario: User can re-select the Pro Plan after picking the Start-up Plan
    When the user selects the "Start-up Plan" Woo plan
    And the user selects the "Pro Plan" Woo plan
    Then the "Pro Plan" Woo plan is in the selected state
    And the "Start-up Plan" Woo plan is in the unselected state
    And the Woo CTA button reflects the "Pro Plan" plan
    And the "Pro Plan" Woo CTA button links to "#"

  @woocommerce @sanity @pricing @start-up
  Scenario: Start-up Plan displays correct copies, pricing, and specs
    Then the Woo plan title displays "Start-up Plan"
    And the "Start-up Plan" Woo plan promo label displays "50% OFF"
    And the "Start-up Plan" Woo plan displays the monthly pricing
    And the "Start-up Plan" Woo plan displays the billing period "/ month"
    And the "Start-up Plan" Woo plan includes "25 GB storage"
    And the "Start-up Plan" Woo plan includes "1 online store"
    And the "Start-up Plan" Woo plan includes "1 free business email (10 GB)"
    And the "Start-up Plan" Woo plan includes "Free SSL certificate"
    And the "Start-up Plan" Woo plan displays the yearly renewal price

  @woocommerce @sanity @pricing @pro
  Scenario: Pro Plan displays correct copies, pricing, and specs
    Then the Woo plan title displays "Pro Plan"
    And the "Pro Plan" Woo plan promo label displays "50% OFF"
    And the "Pro Plan" Woo plan displays the monthly pricing
    And the "Pro Plan" Woo plan displays the billing period "/ month"
    And the "Pro Plan" Woo plan includes "50 GB storage"
    And the "Pro Plan" Woo plan includes "1 online store"
    And the "Pro Plan" Woo plan includes "1 free business email (10 GB)"
    And the "Pro Plan" Woo plan includes "Free SSL certificate"
    And the "Pro Plan" Woo plan displays the yearly renewal price

  @woocommerce @sanity @pricing @apply-domain
  Scenario: Apply-to domain field accepts input
    Then the Apply to label displays "Apply to:"
    And the domain input field is visible
    And the domain input placeholder displays "Find your domain name here"
    When the user fills the domain input with "example.com"
    Then the domain input value is "example.com"

  @woocommerce @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the Woo tax note displays "* Applicable taxes may apply at checkout."

  # ==================== FAQ SECTION ==================== #
  # NOTE: The FAQ accordion keeps every panel present in the DOM even when
  # collapsed (CSS grid 0fr→1fr animation), so panel text is always readable
  # via textContent. User-observable state tracks bounding-box height, so:
  #   - collapsed: assertFaqQuestionCollapsed → aria-expanded=false + not visible
  #   - expanded : expandFaqQuestion clicks + waits for aria-expanded=true
  #   - answer   : assertFaqAnswerContains gates on the panel's isVisible check
  # The count scenario is the safety net for a 7th question quietly appearing.

  @woocommerce @sanity @faq @faq-count
  Scenario: FAQ section renders exactly six questions
    Then the Woo FAQ section has 6 questions

  @woocommerce @sanity @faq
  Scenario Outline: FAQ question "<question>" starts collapsed and expands to the expected answer
    Then the Woo FAQ question "<question>" is visible
    And the Woo FAQ question "<question>" is collapsed by default
    When the user expands the Woo FAQ question "<question>"
    Then the Woo FAQ answer for "<question>" includes "<fragment>"

    Examples:
      | question                              | fragment                                                |
      | What is WooCommerce?                  | most popular ecommerce plugin for WordPress             |
      | Do I need hosting for WooCommerce?    | Our plans come pre-configured for WooCommerce           |
      | Does WooCommerce charge sales fees?   | Utilizing WooCommerce by itself is free of charge       |
      | How secure is my WooCommerce store?   | free SSL certificate, daily backups                     |
      | Do I get a business email account?    | one free 10 GB business email account                   |
      | Can I manage my store from my phone?  | WooCommerce mobile app is available on iOS and Android  |

  # ==================== COPY HYGIENE ==================== #
  # Playwright's hasText / containsText / getByText all whitespace-normalize,
  # so a doubled space or stray &nbsp; on this page would slip past every
  # other scenario in this file. See tests.CopyHygieneStepsTest.

  @woocommerce @sanity @copy-hygiene
  Scenario: Page copy has no whitespace regressions
    Then the page copy has no whitespace regressions
