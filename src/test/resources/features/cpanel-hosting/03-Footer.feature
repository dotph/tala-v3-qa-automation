Feature: Footer
  Verify the copies and links on the footer.

  @footer @smoke
  Scenario: Footer displays correct copies and links
    Given the user navigates to the cPanel Hosting page

    # ==================== DOMAINS COLUMN ==================== #
    Then the footer section heading displays "Domains"
    And the footer link displays "Register"
    And the footer link "Register" links to "/register"
    And the footer link displays "Renew"
    And the footer link "Renew" links to "/renew"
    And the footer link displays "Social Media Domains"
    And the footer link "Social Media Domains" links to "/social-media-domains"
    And the footer link displays "My Domains"
    And the footer link "My Domains" links to "/my-domains"
    And the footer link displays "FAQs"
    And the footer link "FAQs" links to "/faqs"
    And the footer link displays "WHOIS"
    And the footer link "WHOIS" links to "/whois"

    # ==================== HOSTING COLUMN ==================== #
    And the footer section heading displays "Hosting"
    And the footer link displays "cPanel Hosting"
    And the footer link "cPanel Hosting" links to "/shared-linux-hosting"
    And the footer link displays "Single Domain Hosting"
    And the footer link "Single Domain Hosting" links to "/single-domain-hosting"
    And the footer link displays "Multiple Domain Hosting"
    And the footer link "Multiple Domain Hosting" links to "/multiple-domain-hosting"
    And the footer link displays "Virtual Private Server"
    And the footer link "Virtual Private Server" links to "/vps"
    And the footer link displays "WooCommerce"
    And the footer link "WooCommerce" links to "/woocommerce"

    # ==================== OTHER PRODUCTS COLUMN ==================== #
    And the footer section heading displays "Other Products"
    And the footer link displays "dotPH Registry Lock"
    And the footer link "dotPH Registry Lock" links to "/registry-lock"
    And the footer link displays "Private Registration"
    And the footer link "Private Registration" links to "/private-registration"
    And the footer link displays "SSL Certificate"
    And the footer link "SSL Certificate" links to "/ssl"
    And the footer link displays "Microsoft 365"
    And the footer link "Microsoft 365" links to "/microsoft-365"
    And the footer link displays "Google Workspace"
    And the footer link "Google Workspace" links to "/google-workspace"

    # ==================== FOREX ==================== #
    And the footer section heading displays "dotPH Forex"
    And the footer forex rate displays "$ 1.00 = Php 60.50"

    # ==================== BOTTOM BAR ==================== #
    And the footer copyright displays "dotPH Domains Inc. Copyright 2026"
    And the footer link displays "Privacy Policy Statement"
    And the footer link "Privacy Policy Statement" links to "/privacy-policy"
    And the footer link displays "Policies"
    And the footer link "Policies" links to "/policies"
