Feature: Microsoft 365 Landing Page
  Verify the copies, buttons, and links on the Microsoft 365 landing page.

  Background:
    Given the user navigates to the Microsoft 365 page

  # ==================== HERO SECTION ==================== #
  @m365 @smoke @hero
  Scenario: Hero section displays correct copies
    Then the M365 hero eyebrow displays "Email & Productivity"
    And the M365 hero title displays "Microsoft 365"
    And the M365 hero subtitle displays "Optimal Cloud Solutions for Your Growing Business."
    And the M365 See Pricing button displays "See Pricing"
    And the M365 See Pricing button links to "#pricing"
    When the user clicks the M365 See Pricing button
    Then the URL hash is "#pricing"
    And the M365 pricing section is visible in the viewport

  # ==================== PLAN INTROS STRIP ==================== #
  # NOTE: Above the features grid the page renders three narrative "text
  # cards" — one per plan — carrying the full "Microsoft 365 <plan>" heading
  # plus a "(formerly Office 365 …)" line. Descriptions on each card are long
  # marketing prose and deliberately not asserted; the heading + former-name
  # is the load-bearing copy this section verifies.
  @m365 @sanity @plan-intros
  Scenario: Business Basic intro card displays correct heading and former name
    Then the M365 intro card at position 1 displays the heading "Microsoft 365 Business Basic"
    And the M365 intro card at position 1 shows the former name "(formerly Office 365 Business Essentials)"

  @m365 @sanity @plan-intros
  Scenario: Business Standard intro card displays correct heading and former name
    Then the M365 intro card at position 2 displays the heading "Microsoft 365 Business Standard"
    And the M365 intro card at position 2 shows the former name "(formerly Office 365 Business Premium)"

  @m365 @sanity @plan-intros
  Scenario: Apps for Business intro card displays correct heading and former name
    Then the M365 intro card at position 3 displays the heading "Microsoft 365 Apps for Business"
    And the M365 intro card at position 3 shows the former name "(formerly Office 365 Business)"

  # ==================== MICROSOFT 365 FEATURES ==================== #
  @m365 @smoke @features
  Scenario: Features section title displays correct copy
    Then the M365 features section title displays "Microsoft 365 Features"

  @m365 @sanity @features @feature-count
  Scenario: Features section renders exactly five feature cards
    Then the M365 features section has 5 feature cards

  @m365 @sanity @features
  Scenario: Industry Standard Microsoft Applications feature displays correct copy
    Then the M365 feature at position 1 displays the heading "Industry Standard Microsoft Applications"
    And the M365 feature at position 1 description includes "Get to work with the classic Microsoft Apps Word, Excel, Powerpoint and Onenote."

  @m365 @sanity @features
  Scenario: Flexible Email with Microsoft Exchange feature displays correct copy
    Then the M365 feature at position 2 displays the heading "Flexible Email with Microsoft Exchange"
    And the M365 feature at position 2 description includes "With Exchange, you get a business class email server with an interface adaptable to how you work."

  @m365 @sanity @features
  Scenario: Secure Storage and Sharing feature displays correct copy
    Then the M365 feature at position 3 displays the heading "Secure Storage and Sharing"
    And the M365 feature at position 3 description includes "Keep your files safe up on the cloud with OneDrive (up to 1TB capacity)."

  @m365 @sanity @features
  Scenario: Seamless Collaboration feature displays correct copy
    Then the M365 feature at position 4 displays the heading "Seamless Collaboration"
    And the M365 feature at position 4 description includes "Communicate and share ideas with your team no matter where you are with apps such as Yammer, Sharepoint, Planner, and Microsoft Teams."

  @m365 @sanity @features
  Scenario: Enhanced Productivity feature displays correct copy
    Then the M365 feature at position 5 displays the heading "Enhanced Productivity"
    And the M365 feature at position 5 description includes "You'll also get Tools to assist you in your day-to-day routine. Sway, Flow, Outlook, Forms and Visio."

  # ==================== MICROSOFT 365 PLANS ==================== #
  # NOTE: The page ships three tiers — Business Basic, Business Standard,
  # Apps for Business. Each card carries a plan sub-heading <p> immediately
  # after the h3 (functional descriptor, not a promo tagline), a single
  # yearly price with a "/ user / yr" billing period, and the "What's
  # included:" list. Business Standard is selected by default; the CTA below
  # the plan row follows the "Get <plan-name>" formula.

  @m365 @smoke @pricing
  Scenario: Plans section title displays correct copy
    Then the M365 plans section title displays "Choose Your Microsoft 365 Plan"

  @m365 @sanity @pricing @plan-count
  Scenario: Plans section renders exactly three plan cards
    Then the M365 plans section has 3 plan cards

  @m365 @sanity @pricing @plan-order
  Scenario: Plan cards appear in the correct order
    Then the M365 plan card at position 1 is "Business Basic"
    And the M365 plan card at position 2 is "Business Standard"
    And the M365 plan card at position 3 is "Apps for Business"

  @m365 @sanity @pricing @inclusions-heading
  Scenario: Every plan card shows the inclusions heading
    Then the "Business Basic" M365 plan inclusions heading displays "What's included:"
    And the "Business Standard" M365 plan inclusions heading displays "What's included:"
    And the "Apps for Business" M365 plan inclusions heading displays "What's included:"

  @m365 @smoke @pricing @default-selection
  Scenario: Business Standard is selected by default
    Then the M365 default selected plan is "Business Standard"
    And the "Business Basic" M365 plan is not selected by default
    And the "Apps for Business" M365 plan is not selected by default
    And the M365 CTA button reflects the "Business Standard" plan
    And the "Business Standard" M365 CTA button links to "#"

  @m365 @sanity @pricing @plan-selection
  Scenario: User can select the Business Basic plan
    When the user selects the "Business Basic" M365 plan
    Then the "Business Basic" M365 plan is in the selected state
    And the "Business Standard" M365 plan is in the unselected state
    And the "Apps for Business" M365 plan is in the unselected state
    And the M365 CTA button reflects the "Business Basic" plan
    And the "Business Basic" M365 CTA button links to "#"

  @m365 @sanity @pricing @plan-selection
  Scenario: User can select the Apps for Business plan
    When the user selects the "Apps for Business" M365 plan
    Then the "Apps for Business" M365 plan is in the selected state
    And the "Business Basic" M365 plan is in the unselected state
    And the "Business Standard" M365 plan is in the unselected state
    And the M365 CTA button reflects the "Apps for Business" plan
    And the "Apps for Business" M365 CTA button links to "#"

  @m365 @sanity @pricing @plan-selection
  Scenario: User can re-select Business Standard after picking Apps for Business
    When the user selects the "Apps for Business" M365 plan
    And the user selects the "Business Standard" M365 plan
    Then the "Business Standard" M365 plan is in the selected state
    And the "Business Basic" M365 plan is in the unselected state
    And the "Apps for Business" M365 plan is in the unselected state
    And the M365 CTA button reflects the "Business Standard" plan
    And the "Business Standard" M365 CTA button links to "#"

  @m365 @sanity @pricing @business-basic
  Scenario: Business Basic plan displays correct copies, pricing, and specs
    Then the M365 plan title displays "Business Basic"
    And the "Business Basic" M365 plan sub-heading displays "Cloud-based tools for new businesses"
    And the "Business Basic" M365 plan displays the yearly pricing
    And the "Business Basic" M365 plan displays the billing period "/ user / yr"
    And the "Business Basic" M365 plan includes "Business email (Exchange)"
    And the "Business Basic" M365 plan includes "Microsoft Teams"
    And the "Business Basic" M365 plan includes "OneDrive (1 TB)"
    And the "Business Basic" M365 plan includes "SharePoint & Yammer"
    And the "Business Basic" M365 plan includes "Web & mobile Office apps"
    And the "Business Basic" M365 plan excludes "Desktop Office apps (Word, Excel, etc.)"

  # NOTE: Business Standard's sub-heading pins the em-dash character (U+2014)
  # emitted by mdot.ph. A `-DtestEnv=PRODUCTION` run will fail here if dot.ph
  # renders an en-dash (U+2013) — same staging-normalized policy the SSL
  # em-dash lines follow.
  @m365 @sanity @pricing @business-standard
  Scenario: Business Standard plan displays correct copies, pricing, and specs
    Then the M365 plan title displays "Business Standard"
    And the "Business Standard" M365 plan sub-heading displays "The full package — email, tools, and apps"
    And the "Business Standard" M365 plan displays the yearly pricing
    And the "Business Standard" M365 plan displays the billing period "/ user / yr"
    And the "Business Standard" M365 plan includes "Business email (Exchange)"
    And the "Business Standard" M365 plan includes "Microsoft Teams"
    And the "Business Standard" M365 plan includes "OneDrive (1 TB)"
    And the "Business Standard" M365 plan includes "SharePoint & Yammer"
    And the "Business Standard" M365 plan includes "Web & mobile Office apps"
    And the "Business Standard" M365 plan includes "Desktop Office apps (Word, Excel, PowerPoint, etc.)"

  @m365 @sanity @pricing @apps-for-business
  Scenario: Apps for Business plan displays correct copies, pricing, and specs
    Then the M365 plan title displays "Apps for Business"
    And the "Apps for Business" M365 plan sub-heading displays "Desktop Office apps without the email"
    And the "Apps for Business" M365 plan displays the yearly pricing
    And the "Apps for Business" M365 plan displays the billing period "/ user / yr"
    And the "Apps for Business" M365 plan includes "Desktop Office apps (Word, Excel, PowerPoint, etc.)"
    And the "Apps for Business" M365 plan includes "OneDrive (1 TB)"
    And the "Apps for Business" M365 plan includes "Web & mobile Office apps"
    And the "Apps for Business" M365 plan excludes "Business email (Exchange)"
    And the "Apps for Business" M365 plan excludes "Microsoft Teams"

  @m365 @sanity @pricing @apply-domain
  Scenario: Apply-to domain field accepts input
    Then the Apply to label displays "Apply to:"
    And the domain input field is visible
    And the domain input placeholder displays "Find your domain name here"
    When the user fills the domain input with "example.com"
    Then the domain input value is "example.com"

  @m365 @sanity @copy-hygiene
  Scenario: Page copy has no whitespace regressions
    Then the page copy has no whitespace regressions

  @m365 @smoke @pricing
  Scenario: Tax disclaimer displays correct copy
    Then the M365 tax note displays "* Applicable taxes may apply at checkout."
