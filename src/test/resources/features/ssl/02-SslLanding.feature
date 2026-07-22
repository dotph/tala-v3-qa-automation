Feature: SSL Certificates Landing Page
  Verify the copies, buttons, and links on the SSL Certificates landing page.

  Background:
    Given the user navigates to the SSL Certificates page

  # ==================== HERO SECTION ==================== #
  @ssl @smoke @hero
  Scenario: Hero section displays correct copies
    Then the SSL hero eyebrow displays "Security & Add-ons"
    And the SSL hero title displays "SSL Certificates"
    And the SSL hero subtitle displays "Protect your customers' information online. More and more online users are being targeted by phishing attacks and imposter sites. With SSL certificates, you're letting your customers know they're on the right site."
    And the SSL Buy Now button displays "Buy Now"
    And the SSL Buy Now button links to "#pricing"
    When the user clicks the SSL Buy Now button
    Then the URL hash is "#pricing"
    And the SSL pricing section is visible in the viewport

  # ==================== SECURE YOUR WEBSITE SECTION ==================== #
  @ssl @sanity @secure-your-website
  Scenario: Secure Your Website section displays correct copies
    Then the SSL Secure Your Website section title displays "Secure Your Website"
    And the SSL Secure Your Website paragraph includes "SSL certificates provide security to the business owners' website"
    And the SSL Secure Your Website paragraph includes "little green padlock with HTTPS"
    And the SSL Secure Your Website paragraph includes "Safer sites mean higher customer trust and loyalty."

  # ==================== SSL CERTIFICATE PLANS ==================== #
  # NOTE: The SSL page ships three tiers — Domain Validation, Organization
  # Validation, and Wildcard Certificate. Each card carries a plan sub-heading
  # <p> immediately after the h3 (functional descriptor, not a promo tagline),
  # a single yearly price with no strikethrough, and a "/ yr" billing period.
  # Organization Validation is selected by default; the CTA below the plan row
  # follows the "Get <plan-name> SSL" formula.

  @ssl @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the SSL plans section title displays "Choose Your SSL Certificate"

  @ssl @sanity @pricing @plan-count
  Scenario: Plans section renders exactly three plan cards
    Then the SSL plans section has 3 plan cards

  @ssl @sanity @pricing @plan-order
  Scenario: Plan cards appear in the correct order
    Then the SSL plan card at position 1 is "Domain Validation"
    And the SSL plan card at position 2 is "Organization Validation"
    And the SSL plan card at position 3 is "Wildcard Certificate"

  @ssl @sanity @pricing @inclusions-heading
  Scenario: Every plan card shows the inclusions heading
    Then the "Domain Validation" SSL plan inclusions heading displays "What's included:"
    And the "Organization Validation" SSL plan inclusions heading displays "What's included:"
    And the "Wildcard Certificate" SSL plan inclusions heading displays "What's included:"

  @ssl @smoke @pricing @default-selection
  Scenario: Organization Validation is selected by default
    Then the SSL default selected plan is "Organization Validation"
    And the "Domain Validation" SSL plan is not selected by default
    And the "Wildcard Certificate" SSL plan is not selected by default
    And the SSL CTA button reflects the "Organization Validation" plan
    And the "Organization Validation" SSL CTA button links to "#"

  @ssl @sanity @pricing @plan-selection
  Scenario: User can select the Domain Validation plan
    When the user selects the "Domain Validation" SSL plan
    Then the "Domain Validation" SSL plan is in the selected state
    And the "Organization Validation" SSL plan is in the unselected state
    And the "Wildcard Certificate" SSL plan is in the unselected state
    And the SSL CTA button reflects the "Domain Validation" plan
    And the "Domain Validation" SSL CTA button links to "#"

  @ssl @sanity @pricing @plan-selection
  Scenario: User can select the Wildcard Certificate plan
    When the user selects the "Wildcard Certificate" SSL plan
    Then the "Wildcard Certificate" SSL plan is in the selected state
    And the "Domain Validation" SSL plan is in the unselected state
    And the "Organization Validation" SSL plan is in the unselected state
    And the SSL CTA button reflects the "Wildcard Certificate" plan
    And the "Wildcard Certificate" SSL CTA button links to "#"

  @ssl @sanity @pricing @plan-selection
  Scenario: User can re-select Organization Validation after picking Wildcard
    When the user selects the "Wildcard Certificate" SSL plan
    And the user selects the "Organization Validation" SSL plan
    Then the "Organization Validation" SSL plan is in the selected state
    And the "Domain Validation" SSL plan is in the unselected state
    And the "Wildcard Certificate" SSL plan is in the unselected state
    And the SSL CTA button reflects the "Organization Validation" plan
    And the "Organization Validation" SSL CTA button links to "#"

  @ssl @sanity @pricing @domain-validation
  Scenario: Domain Validation plan displays correct copies, pricing, and specs
    Then the SSL plan title displays "Domain Validation"
    And the "Domain Validation" SSL plan sub-heading displays "Fast issuance for any website"
    And the "Domain Validation" SSL plan displays the yearly pricing
    And the "Domain Validation" SSL plan displays the billing period "/ yr"
    And the "Domain Validation" SSL plan includes "Single Domain Name"
    And the "Domain Validation" SSL plan includes "Full Domain Validation method"
    And the "Domain Validation" SSL plan includes "Issued within 3 - 10 business days"
    And the "Domain Validation" SSL plan includes "256 bit SSL and 2048 bit CSR encryption"
    And the "Domain Validation" SSL plan includes "Secure www and non-www domains"
    And the "Domain Validation" SSL plan includes "99% All Web Browsers & Mobile Devices Support"
    And the "Domain Validation" SSL plan includes "99% Client OS Compatibility"
    # NOTE: em-dash pin below is staging-normalized. mdot.ph renders the row
    # with "—", dot.ph (production) renders it with "–", so a
    # `-DtestEnv=PRODUCTION` run fails here until the two stacks converge.
    And the "Domain Validation" SSL plan includes "Automated online validation — no paperwork"
    And the "Domain Validation" SSL plan includes "Unlimited Server License"
    And the "Domain Validation" SSL plan includes "Unlimited Server Reissues"
    And the "Domain Validation" SSL plan includes "$ 50,000 Warranty"
    And the "Domain Validation" SSL plan includes "Domain Name Shown On Certificate Only"
    And the "Domain Validation" SSL plan includes "Secure Trust Seal"

  @ssl @sanity @pricing @organization-validation
  Scenario: Organization Validation plan displays correct copies, pricing, and specs
    Then the SSL plan title displays "Organization Validation"
    And the "Organization Validation" SSL plan sub-heading displays "Full business authentication"
    And the "Organization Validation" SSL plan displays the yearly pricing
    And the "Organization Validation" SSL plan displays the billing period "/ yr"
    And the "Organization Validation" SSL plan includes "Single Domain Name"
    And the "Organization Validation" SSL plan includes "Full Organization Authentication"
    And the "Organization Validation" SSL plan includes "Issued within 3 - 10 business days"
    And the "Organization Validation" SSL plan includes "256 bit SSL and 2048 bit CSR encryption"
    And the "Organization Validation" SSL plan includes "Secure www and non-www domains"
    And the "Organization Validation" SSL plan includes "99% All Web Browsers & Mobile Devices Support"
    And the "Organization Validation" SSL plan includes "99% Client OS Compatibility"
    And the "Organization Validation" SSL plan includes "Requires documents and manual validation"
    And the "Organization Validation" SSL plan includes "Unlimited Server License"
    And the "Organization Validation" SSL plan includes "Unlimited Server Reissues"
    And the "Organization Validation" SSL plan includes "$ 50,000 Warranty"
    And the "Organization Validation" SSL plan includes "Validated company details in the SSL certificate and your Site Seal profile"
    And the "Organization Validation" SSL plan includes "Secure Trust Seal"

  @ssl @sanity @pricing @wildcard
  Scenario: Wildcard Certificate plan displays correct copies, pricing, and specs
    Then the SSL plan title displays "Wildcard Certificate"
    And the "Wildcard Certificate" SSL plan sub-heading displays "Cover all your subdomains"
    And the "Wildcard Certificate" SSL plan displays the yearly pricing
    And the "Wildcard Certificate" SSL plan displays the billing period "/ yr"
    And the "Wildcard Certificate" SSL plan includes "Single Domain Name, Multiple Sub-Domains"
    And the "Wildcard Certificate" SSL plan includes "Issued within 3 - 10 business days"
    And the "Wildcard Certificate" SSL plan includes "256 bit SSL and 2048 bit CSR encryption"
    And the "Wildcard Certificate" SSL plan includes "Secure unlimited sub-domain"
    And the "Wildcard Certificate" SSL plan includes "99% All Web Browsers & Mobile Devices Support"
    And the "Wildcard Certificate" SSL plan includes "99% Client OS Compatibility"
    # NOTE: em-dash pin below is staging-normalized — same reason as the
    # matching Domain Validation line above (mdot em-dash / dot.ph en-dash).
    And the "Wildcard Certificate" SSL plan includes "Automated online validation — no paperwork"
    And the "Wildcard Certificate" SSL plan includes "Unlimited Server License"
    And the "Wildcard Certificate" SSL plan includes "Unlimited Sub-domain Certs"
    And the "Wildcard Certificate" SSL plan includes "$ 50,000 Warranty"
    And the "Wildcard Certificate" SSL plan includes "Domain Name Shown On Certificate Only"
    And the "Wildcard Certificate" SSL plan includes "Secure Trust Seal"

  # KNOWN BUG (observed 2026-07-16, tracked in TALA-2858): live mdot.ph and
  # dot.ph Wildcard Certificate card renders "Unlimited Sub-domain Security"
  # twice in its inclusion list (rows #2 and #11 of 14). Isolated into its
  # own scenario without @sanity so the failure doesn't drag down smoke runs.
  # When the UI is corrected, this scenario starts passing — at that point
  # merge the step back into the Wildcard scenario above and delete this one.
  # https://dotph.atlassian.net/browse/TALA-2858
  @ssl @pricing @wildcard @known-bug
  Scenario: Wildcard Certificate lists Unlimited Sub-domain Security exactly once (currently failing per TALA-2858)
    Then the "Wildcard Certificate" SSL plan includes "Unlimited Sub-domain Security" exactly 1 time

  @ssl @sanity @pricing @apply-domain
  Scenario: Apply-to domain field accepts input
    Then the Apply to label displays "Apply to:"
    And the domain input field is visible
    And the domain input placeholder displays "Find your domain name here"
    When the user fills the domain input with "example.com"
    Then the domain input value is "example.com"

  @ssl @sanity @copy-hygiene
  Scenario: Page copy has no whitespace regressions
    Then the page copy has no whitespace regressions

  @ssl @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the SSL tax note displays "* Applicable taxes may apply at checkout."
