Feature: Navigation Bar (shared across landing pages)
  Verify the copies and links on the navigation bar are consistent
  regardless of which landing page the user is on. The nav is a global
  component; the only per-page difference is the Background URL.

  @navigation @smoke
  Scenario Outline: Navigation bar displays correct copies and links on <page>
    Given the user navigates to <page>

    # ==================== HEADER ==================== #
    Then the dotPH logo is visible
    And the dotPH logo links to "/"
    And the nav item "Domains" is displayed
    And the nav item "Web Hosting" is displayed
    And the nav item "Products" is displayed
    And the nav item "About" is displayed
    And the Login button displays "Login"
    And the Login button links to "/login"
    And the Cart button is visible

    # ==================== DOMAINS DROPDOWN ==================== #
    When the user clicks the "Domains" nav item
    Then the nav link "Social Media Domains" is displayed
    And the nav link "Social Media Domains" links to "/social-media-domains"
    And the nav link "Domain Swap" is displayed
    And the nav link "Domain Swap" links to "/domain-swap"
    And the nav link "My Domains" is displayed
    And the nav link "My Domains" links to "/my-domains"
    And the nav link "FAQs" is displayed
    And the nav link "FAQs" links to "/faqs"
    And the nav link "WHOIS" is displayed
    And the nav link "WHOIS" links to "/whois"

    # ==================== WEB HOSTING DROPDOWN ==================== #
    When the user clicks the "Web Hosting" nav item
    Then the nav link "cPanel Hosting" is displayed
    And the nav link "cPanel Hosting" links to "/shared-linux-hosting"
    And the nav link "Single Domain Hosting" is displayed
    And the nav link "Single Domain Hosting" links to "/single-domain-hosting"
    And the nav link "Multiple Domain Hosting" is displayed
    And the nav link "Multiple Domain Hosting" links to "/multiple-domain-hosting"
    And the nav link "Virtual Private Server" is displayed
    And the nav link "Virtual Private Server" links to "/vps"
    And the nav link "WooCommerce" is displayed
    And the nav link "WooCommerce" links to "/woocommerce"
    And the nav link "My Hosting Products" is displayed
    And the nav link "My Hosting Products" links to "/my-hosting"

    # ==================== PRODUCTS DROPDOWN ==================== #
    When the user clicks the "Products" nav item
    Then the nav link "Browse All" is displayed
    And the nav link "Browse All" links to "/products"
    And the nav link "dotPH Registry Lock" is displayed
    And the nav link "dotPH Registry Lock" links to "/registry-lock"
    And the nav link "Private Registration" is displayed
    And the nav link "Private Registration" links to "/private-registration"
    And the nav link "SSL Certificates" is displayed
    And the nav link "SSL Certificates" links to "/ssl"
    And the nav link "Microsoft 365" is displayed
    And the nav link "Microsoft 365" links to "/microsoft-365"
    And the nav link "Google Workspace" is displayed
    And the nav link "Google Workspace" links to "/google-workspace"
    And the nav link "My Services" is displayed
    And the nav link "My Services" links to "/my-services"

    # ==================== ABOUT DROPDOWN ==================== #
    When the user clicks the "About" nav item
    Then the nav link "Send Us A Message" is displayed
    And the nav link "Send Us A Message" links to "/contact"
    And the nav link "About dotPH" is displayed
    And the nav link "About dotPH" links to "/about"
    And the nav link "Become a partner" is displayed
    And the nav link "Become a partner" links to "https://partner.dot.ph/"
    And the nav link "Policies, Agreements and UDRP" is displayed
    And the nav link "Policies, Agreements and UDRP" links to "/policies"
    And the nav link "Newsroom" is displayed
    And the nav link "Newsroom" links to "https://newsroom.dot.ph/"
    And the nav link "Careers" is displayed
    And the nav link "Careers" links to "/careers"
    When the user closes the dropdown

    Examples:
      | page                             |
      | the cPanel Hosting page          |
      | the Single Domain Hosting page   |
      | the Multiple Domain Hosting page |
      | the Virtual Private Server page  |
      | the WooCommerce page             |
      | the Private Registration page    |
