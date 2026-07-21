Feature: Registry Lock Landing Page
  Verify the copies, buttons, links, and imagery on the Registry Lock landing page.

  Background:
    Given the user navigates to the Registry Lock page

  # ==================== HERO SECTION ==================== #
  # NOTE: Registry Lock is the only landing page (so far) that renders a small
  # product tagline above the H1 ("Security & Add-ons"). Asserted here so a
  # silent removal or edit surfaces alongside the H1 checks.

  @registry-lock @smoke @hero
  Scenario: Hero section displays correct copies
    Then the RL hero tagline displays "Security & Add-ons"
    And the RL hero title displays "dotPH Registry Lock"
    And the RL hero subtitle displays "Protect your domains at the highest level — the Registry. With domain hijacking incidents being reported more frequently, it is highly important to add additional security for your domains."
    And the RL hero image src contains "p-rl-hero.jpg"
    And the RL Buy Now button displays "Buy Now"
    And the RL Buy Now button links to "#pricing"
    When the user clicks the RL Buy Now button
    Then the URL hash is "#pricing"
    And the RL pricing section is visible in the viewport

  # ==================== WHY REGISTRY LOCK SECTION ==================== #
  # NOTE: two-part "why" section — one H2 summary + a short intro <p>, followed
  # by two info blocks (H3 + paragraph + image). The image src assertions pin
  # rl-1.webp / rl-2.webp against Next.js image proxy URLs (width/quality
  # params drift with viewport, so the assertion matches on the underlying
  # media filename only).

  @registry-lock @sanity @why
  Scenario: Why section displays correct copies
    Then the RL why section title displays "With domain hijacking incidents being reported more frequently, it is highly important to add additional security for your domains."
    And the RL why intro includes "Registry lock offers the multiple levels of verification before any changes to your domain are applied."

  @registry-lock @sanity @why @info-blocks @info-block-count
  Scenario: Info-block section renders exactly two blocks
    Then the RL info-block section has 2 blocks

  @registry-lock @sanity @why @info-blocks
  Scenario: Info block 1 displays correct copy and image
    Then the RL info block 1 heading displays "dotPH Registry Lock is a premium service to prevent hijacking of your PH domains"
    And the RL info block 1 description includes "Domain hijacking is when an unauthorized action is done on your domain."
    And the RL info block 1 description includes "These changes would cause you to lose access to your domain and could endanger your customers to phishing attacks."
    And the RL info block 1 image src contains "rl-1.webp"

  @registry-lock @sanity @why @info-blocks
  Scenario: Info block 2 displays correct copy and image
    Then the RL info block 2 heading displays "With dotPH Registry Lock, any change to your domain will go through several layers of verification"
    And the RL info block 2 description includes "to ensure that the changes requested are valid and authorized by you."
    And the RL info block 2 description includes "A dotPH representative will contact you whenever a change is requested on your domain"
    And the RL info block 2 image src contains "rl-2.webp"

  # ==================== SUBSCRIBE STEPS SECTION ==================== #
  # NOTE: this section renders as an <ol> of 4 <li> entries — each item pairs a
  # numeric badge (1..4) with an H3 sub-heading and description paragraph. The
  # count scenario is the safety net for a 5th step being added — the per-step
  # outline already catches drops (its .nth() lookup fails when items go
  # missing), but an addition would slip past a 1..4 outline unnoticed.

  @registry-lock @sanity @subscribe @subscribe-count
  Scenario: Subscribe section renders exactly four steps
    Then the RL Subscribe section title displays "What Happens Once I Subscribe?"
    And the RL Subscribe section has 4 steps

  @registry-lock @sanity @subscribe
  Scenario Outline: Subscribe step <number> displays the correct badge, heading, and description
    Then the RL Subscribe step at position <number> displays the number "<number>"
    And the RL Subscribe step at position <number> heading displays "<heading>"
    And the RL Subscribe step at position <number> description includes "<fragment>"

    Examples:
      | number | heading                              | fragment                                                        |
      | 1      | Set Up Security Questions            | Provide security questions and supporting documentation         |
      | 2      | Authentication by dotPH              | A dotPH representative reviews and authenticates                |
      | 3      | Domain Locked at Registry            | Your domain is locked at the registry level                     |
      | 4      | Verification on Every Change Request | Any future change request triggers a direct call from our team  |

  # ==================== PRICING SECTION ==================== #
  # NOTE: Registry Lock ships a SINGLE plan card that is always in the selected
  # state (aria-pressed=true, "✓ Selected") — there is no unselected counterpart
  # to toggle against, so no plan-order / plan-selection scenarios here (the
  # plan-count guard below still pins the single-card invariant). No promo
  # label, no struck-through original price, no yearly-renewal-inclusion line —
  # the card carries a flat annual price ($100.00 / yr, sourced from the
  # RegistryLockPlan enum). Add to Cart href is the same "#" placeholder
  # pattern used on Woo / SDH / MDH.

  @registry-lock @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the RL plans section title displays "Registry Lock"

  @registry-lock @sanity @pricing @plan-count
  Scenario: Plans section renders exactly one plan card
    Then the RL plans section has 1 plan card

  @registry-lock @smoke @pricing @default-selection
  Scenario: The single Registry Lock card is selected by default
    Then the RL default selected plan is "Registry Lock"
    And the RL CTA button displays "Add to Cart"
    And the RL CTA button links to "#"

  @registry-lock @sanity @pricing @plan-details
  Scenario: Registry Lock plan displays correct copies, pricing, and inclusions
    Then the RL plan title displays "Registry Lock"
    And the "Registry Lock" RL plan supported TLDs paragraph displays "Supported: .PH, .COM.PH, .NET.PH, .ORG.PH"
    And the "Registry Lock" RL plan displays the yearly price
    And the "Registry Lock" RL plan displays the billing period "/ yr"
    And the "Registry Lock" RL plan inclusions heading displays "What's included:"
    And the "Registry Lock" RL plan includes "Registry-level domain lock"
    And the "Registry Lock" RL plan includes "Multi-layer verification"
    And the "Registry Lock" RL plan includes "Direct notification on change requests"
    And the "Registry Lock" RL plan includes "Identity verification requirement"
    And the "Registry Lock" RL plan includes "Protection against unauthorized transfers"

  @registry-lock @sanity @pricing @apply-domain
  Scenario: Apply-to domain field accepts input
    Then the Apply to label displays "Apply to:"
    And the domain input field is visible
    And the domain input placeholder displays "Find your domain name here"
    When the user fills the domain input with "example.com"
    Then the domain input value is "example.com"

  # ==================== COPY HYGIENE ==================== #
  # NOTE: Playwright's hasText / containsText / getByText all whitespace-
  # normalize before comparing, so a doubled space or stray &nbsp; on the
  # page would slip past every other scenario in this file. This scan walks
  # the DOM's text nodes and fails on any interior double-space or non-
  # breaking-space — meeting the QATEAM-1016 "detect regressions in copy
  # quality … fail on any deviation" criterion.

  @registry-lock @sanity @copy-hygiene
  Scenario: Page copy has no whitespace regressions
    Then the page copy has no whitespace regressions

  @registry-lock @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the RL tax note displays "* Applicable taxes may apply at checkout."
