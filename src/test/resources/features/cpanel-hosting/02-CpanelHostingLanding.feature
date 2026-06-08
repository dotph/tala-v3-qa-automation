Feature: cPanel Hosting Landing Page
  Verify the copies, buttons, and links on the cPanel Hosting landing page.

  Background:
    Given the user navigates to the cPanel Hosting page

  # ==================== HERO SECTION ==================== #
  @cpanel-hosting @smoke @hero
  Scenario: Hero section displays correct copies
    Then the hero title displays "cPanel Hosting"
    And the hero subtitle displays "Keep your websites in check with a web hosting service. Hosting services provide you the essentials to set up a well-run site: site upgrades, security, storage, bandwidth, and technical support."
    And the See Pricing button displays "See Pricing"
    And the See Pricing button links to "/shared-linux-hosting#pricing"
    When the user clicks the See Pricing button
    Then the pricing section is visible in the viewport

  # ==================== HOSTING PLANS ==================== #
  @cpanel-hosting @smoke @pricing
  Scenario: Hosting plans section title displays correct copy
    Then the plans section title displays "Choose Your Hosting Plan"

  @cpanel-hosting @sanity @pricing @single-domain
  Scenario: Single Domain Hosting plan displays correct copies
    Then the plan title displays "Single Domain Hosting"
    And the "Single Domain Hosting" plan displays the pricing label "For as low as"
    And the "Single Domain Hosting" plan displays the price "$6.50"
    And the "Single Domain Hosting" plan displays the billing period "/ month"
    And the "Single Domain Hosting" plan displays the description "If you own a business that needs one domain to handle your operations online, our single domain plans are the ideal option for you."
    And the "Single Domain Hosting" plan CTA displays "Get Single Domain Hosting"
    And the "Single Domain Hosting" plan CTA links to "/single-domain-hosting"

  @cpanel-hosting @sanity @pricing @multiple-domain
  Scenario: Multiple Domain Hosting plan displays correct copies
    Then the plan title displays "Multiple Domain Hosting"
    And the "Multiple Domain Hosting" plan displays the pricing label "For as low as"
    And the "Multiple Domain Hosting" plan displays the price "$22.00"
    And the "Multiple Domain Hosting" plan displays the billing period "/ month"
    And the "Multiple Domain Hosting" plan displays the description "If you are simultaneously managing several ventures, we also have hosting plans for multiple domains available. An easy control panel that fits your needs."
    And the "Multiple Domain Hosting" plan CTA displays "Get Multiple Domain Hosting"
    And the "Multiple Domain Hosting" plan CTA links to "/multiple-domain-hosting"

  @cpanel-hosting @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the tax note displays "* Applicable taxes may apply at checkout."
