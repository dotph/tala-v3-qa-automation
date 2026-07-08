Feature: VPS Landing Page
  Verify the copies, buttons, and links on the Virtual Private Server landing page.

  Background:
    Given the user navigates to the Virtual Private Server page

  # ==================== HERO SECTION ==================== #
  @vps @smoke @hero
  Scenario: Hero section displays correct copies
    Then the VPS hero title displays "Virtual Private Server"
    And the VPS hero subtitle displays "We'll make sure you get the resources you need to keep growing your business."
    And the VPS See Pricing button displays "See Pricing"
    And the VPS See Pricing button links to "#pricing"
    When the user clicks the VPS See Pricing button
    Then the URL hash is "#pricing"
    And the VPS pricing section is visible in the viewport

  # ==================== HOSTING FEATURES SECTION ==================== #
  @vps @sanity @hosting-features
  Scenario: Hosting Features section displays correct copies
    Then the VPS Hosting Features section title displays "Hosting Features"
    And the VPS Hosting Features copy includes "Static, public IP"
    And the VPS Hosting Features copy includes "CentOS linux OS pre-installation"
    And the VPS Hosting Features copy includes "Remote server reboot with web-based control panel"
    And the VPS Hosting Features copy includes "Shared bandwidth, with direct peering with local broadband providers"
    And the VPS Hosting Features copy includes "Local support: voice, email and chat"

  # ==================== GET FULL CONTROL SECTION ==================== #
  @vps @sanity @full-control
  Scenario: Get full control section displays correct copies for all four sub-features
    Then the VPS Get full control section title displays "Get full control of your site"
    And the VPS Get full control sub-heading displays "Security Check"
    And the VPS Get full control sub-heading displays "Less Downtime = Less Loss"
    And the VPS Get full control sub-heading displays "We Keep It Cool"
    And the VPS Get full control sub-heading displays "One Stop Shop"
    And the "Security Check" VPS description includes "Keycard protocols"
    And the "Less Downtime = Less Loss" VPS description includes "same data center"
    And the "We Keep It Cool" VPS description includes "ventilation and cooling systems"
    And the "One Stop Shop" VPS description includes "wide array of payment options"

  # ==================== HOSTING PLANS ==================== #
  # NOTE: Three per-plan behaviours SDH / MDH assert are intentionally absent
  # here because /vps genuinely doesn't render them (verified live on mdot.ph):
  #   1. No per-card "Get X" CTA link. Selection is asserted directly on the
  #      card's Select / ✓ Selected toggle via aria-pressed + button text.
  #      (SDH / MDH additionally assert the page-level CTA reflects the
  #      selected plan — no equivalent CTA exists on /vps.)
  #   2. No plan description copy between the plan title and the price row.
  #      SDH cards carry lines like "Everything you need to get online"; VPS
  #      cards go straight from h3 → price. Nothing to assert.
  #   3. No excluded / dimmed inclusion rows. All rows across the three plans
  #      render as ✓ Included, so there are no `… plan excludes "X"`
  #      assertions here — that's the safety net that caught TALA3-71 on MDH
  #      (MD2 Free SSL drift), not applicable to VPS today.
  # Re-evaluate any of the three if the /vps design changes.

  @vps @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the VPS plans section title displays "Virtual Private Server and Dedicated Hosting Plans"

  @vps @sanity @pricing @plan-order
  Scenario: Plan cards appear in the correct order
    Then the VPS plan card at position 1 is "MiddleWeight VPS"
    And the VPS plan card at position 2 is "CruiserWeight VPS"
    And the VPS plan card at position 3 is "HeavyWeight VPS"

  @vps @sanity @pricing @inclusions-heading
  Scenario: Every plan card shows the inclusions heading
    Then the "MiddleWeight VPS" VPS plan inclusions heading displays "What's included:"
    And the "CruiserWeight VPS" VPS plan inclusions heading displays "What's included:"
    And the "HeavyWeight VPS" VPS plan inclusions heading displays "What's included:"

  @vps @smoke @pricing @default-selection
  Scenario: MiddleWeight VPS is selected by default
    Then the VPS default selected plan is "MiddleWeight VPS"
    And the "CruiserWeight VPS" VPS plan is not selected by default
    And the "HeavyWeight VPS" VPS plan is not selected by default

  @vps @sanity @pricing @plan-selection
  Scenario: User can select the CruiserWeight VPS plan
    When the user selects the "CruiserWeight VPS" VPS plan
    Then the "CruiserWeight VPS" VPS plan is in the selected state
    And the "MiddleWeight VPS" VPS plan is in the unselected state
    And the "HeavyWeight VPS" VPS plan is in the unselected state

  @vps @sanity @pricing @plan-selection
  Scenario: User can select the HeavyWeight VPS plan
    When the user selects the "HeavyWeight VPS" VPS plan
    Then the "HeavyWeight VPS" VPS plan is in the selected state
    And the "MiddleWeight VPS" VPS plan is in the unselected state
    And the "CruiserWeight VPS" VPS plan is in the unselected state

  @vps @sanity @pricing @plan-selection
  Scenario: User can re-select the MiddleWeight VPS plan after picking another
    When the user selects the "HeavyWeight VPS" VPS plan
    And the user selects the "MiddleWeight VPS" VPS plan
    Then the "MiddleWeight VPS" VPS plan is in the selected state
    And the "CruiserWeight VPS" VPS plan is in the unselected state
    And the "HeavyWeight VPS" VPS plan is in the unselected state

  @vps @sanity @pricing @middleweight
  Scenario: MiddleWeight VPS displays correct pricing and specs
    Then the VPS plan title displays "MiddleWeight VPS"
    And the "MiddleWeight VPS" VPS plan displays the monthly price
    And the "MiddleWeight VPS" VPS plan displays the billing period "/ month"
    And the "MiddleWeight VPS" VPS plan includes "Equivalent to Dual Core 32-bit Intel AMD/CPU"
    And the "MiddleWeight VPS" VPS plan includes "2GB RAM"
    And the "MiddleWeight VPS" VPS plan includes "200GB Redundant (RAID) Disk Space"
    And the "MiddleWeight VPS" VPS plan includes "1 Static IP Address"
    And the "MiddleWeight VPS" VPS plan includes "VPS type server"

  @vps @sanity @pricing @cruiserweight
  Scenario: CruiserWeight VPS displays correct pricing and specs
    Then the VPS plan title displays "CruiserWeight VPS"
    And the "CruiserWeight VPS" VPS plan displays the monthly price
    And the "CruiserWeight VPS" VPS plan displays the billing period "/ month"
    And the "CruiserWeight VPS" VPS plan includes "Equivalent to Dual Core 32-bit Intel AMD/CPU"
    And the "CruiserWeight VPS" VPS plan includes "4GB RAM"
    And the "CruiserWeight VPS" VPS plan includes "500GB Redundant (RAID) Disk Space"
    And the "CruiserWeight VPS" VPS plan includes "1 Static IP Address"
    And the "CruiserWeight VPS" VPS plan includes "VPS type server"

  @vps @sanity @pricing @heavyweight
  Scenario: HeavyWeight VPS displays correct pricing and specs
    Then the VPS plan title displays "HeavyWeight VPS"
    And the "HeavyWeight VPS" VPS plan displays the monthly price
    And the "HeavyWeight VPS" VPS plan displays the billing period "/ month"
    And the "HeavyWeight VPS" VPS plan includes "Equivalent to Quad Core 64-bit Intel/AMD CPU"
    And the "HeavyWeight VPS" VPS plan includes "8GB RAM"
    And the "HeavyWeight VPS" VPS plan includes "1TB Redundant (RAID) Disk Space"
    And the "HeavyWeight VPS" VPS plan includes "1 Static IP Address"
    And the "HeavyWeight VPS" VPS plan includes "Full Machine with KVM Access"

  @vps @smoke @pricing
  Scenario: Tax and legal disclaimers display correct copies
    Then the VPS tax note displays "*Applicable taxes may apply at checkout."
    And the VPS tax note displays "**12 months Lock-in-period."
    And the VPS tax note displays "***SSH is disabled by default for security reasons."
    And the VPS tax note displays "****Additional IP will be charged accordingly. Billing Period is Quarterly and Annually."

  # ==================== INQUIRY FORM ==================== #
  # NOTE: Fields 1–5 use the generic "Your Answer Here" placeholder; the last
  # two have contextual placeholders ("you@company.com" for email, "Message"
  # for the comment box). Asserting each per-field pins placeholder copy so a
  # frontend rewrite (e.g. per-field prompts) is caught.
  @vps @sanity @inquiry-form
  Scenario: Inquiry form displays intro copy, all field labels, placeholders, and Submit button
    Then the VPS inquiry intro displays "We have a few questions regarding your inquiry on VPS/Dedicated hosting services:"
    And the VPS inquiry field label displays "1. Who is your target market for the website? Philippines or USA?"
    And the VPS inquiry field label displays "2. What kind of site do you plan to put up?"
    And the VPS inquiry field label displays "3. What is your bandwidth estimate for your website?"
    And the VPS inquiry field label displays "4. Estimated traffic of your server/site?"
    And the VPS inquiry field label displays "5. Name of Contact Person"
    And the VPS inquiry field label displays "Email Address of Contact Person"
    And the VPS inquiry field label displays "Additional Comments"
    And the VPS inquiry field "1. Who is your target market for the website? Philippines or USA?" placeholder is "Your Answer Here"
    And the VPS inquiry field "2. What kind of site do you plan to put up?" placeholder is "Your Answer Here"
    And the VPS inquiry field "3. What is your bandwidth estimate for your website?" placeholder is "Your Answer Here"
    And the VPS inquiry field "4. Estimated traffic of your server/site?" placeholder is "Your Answer Here"
    And the VPS inquiry field "5. Name of Contact Person" placeholder is "Your Answer Here"
    And the VPS inquiry field "Email Address of Contact Person" placeholder is "you@company.com"
    And the VPS inquiry field "Additional Comments" placeholder is "Message"
    And the VPS inquiry Submit button is visible
