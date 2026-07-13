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
    And the "Start-up Plan" Woo plan includes "$60.00/year on renewal"

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
    And the "Pro Plan" Woo plan includes "$96.00/year on renewal"

  @woocommerce @sanity @pricing @apply-domain
  Scenario: Apply-to domain field displays correct copies
    Then the Apply to label displays "Apply to:"
    And the domain input field is visible
    And the domain input placeholder displays "Find your domain name here"

  @woocommerce @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the Woo tax note displays "* Applicable taxes may apply at checkout."

  # ==================== FAQ SECTION ==================== #
  # NOTE: The FAQ accordion keeps every panel present in the DOM even when
  # collapsed (CSS grid 0fr→1fr animation), so panel text is always readable
  # via textContent. To match user-observable behaviour we still click each
  # trigger before asserting the copy — assertFaqAnswerContains gates on the
  # panel's isVisible check, which waits for the accordion to open.

  @woocommerce @sanity @faq
  Scenario: FAQ section renders all questions
    Then the Woo FAQ question "What is WooCommerce?" is visible
    And the Woo FAQ question "Do I need hosting for WooCommerce?" is visible
    And the Woo FAQ question "Does WooCommerce charge sales fees?" is visible
    And the Woo FAQ question "How secure is my WooCommerce store?" is visible
    And the Woo FAQ question "Do I get a business email account?" is visible
    And the Woo FAQ question "Can I manage my store from my phone?" is visible

  @woocommerce @sanity @faq
  Scenario: What is WooCommerce? expands to the expected answer
    When the user expands the Woo FAQ question "What is WooCommerce?"
    Then the Woo FAQ answer for "What is WooCommerce?" includes "most popular ecommerce plugin for WordPress"

  @woocommerce @sanity @faq
  Scenario: Do I need hosting for WooCommerce? expands to the expected answer
    When the user expands the Woo FAQ question "Do I need hosting for WooCommerce?"
    Then the Woo FAQ answer for "Do I need hosting for WooCommerce?" includes "Our plans come pre-configured for WooCommerce"

  @woocommerce @sanity @faq
  Scenario: Does WooCommerce charge sales fees? expands to the expected answer
    When the user expands the Woo FAQ question "Does WooCommerce charge sales fees?"
    Then the Woo FAQ answer for "Does WooCommerce charge sales fees?" includes "Utilizing WooCommerce by itself is free of charge"

  @woocommerce @sanity @faq
  Scenario: How secure is my WooCommerce store? expands to the expected answer
    When the user expands the Woo FAQ question "How secure is my WooCommerce store?"
    Then the Woo FAQ answer for "How secure is my WooCommerce store?" includes "free SSL certificate, daily backups"

  @woocommerce @sanity @faq
  Scenario: Do I get a business email account? expands to the expected answer
    When the user expands the Woo FAQ question "Do I get a business email account?"
    Then the Woo FAQ answer for "Do I get a business email account?" includes "one free 10 GB business email account"

  @woocommerce @sanity @faq
  Scenario: Can I manage my store from my phone? expands to the expected answer
    When the user expands the Woo FAQ question "Can I manage my store from my phone?"
    Then the Woo FAQ answer for "Can I manage my store from my phone?" includes "WooCommerce mobile app is available on iOS and Android"
