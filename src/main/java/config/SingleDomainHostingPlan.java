package config;

import java.math.BigDecimal;

public enum SingleDomainHostingPlan {
    STARTER("Starter", new BigDecimal("6.50"),
            "1 domain",
            "30 GB disk space",
            "Up to 100 GB bandwidth",
            "10 subdomains",
            "50 email accounts",
            "10 mailing lists",
            "Free SSL", false),
    PROFESSIONAL("Professional", new BigDecimal("15.00"),
            "1 domain",
            "60 GB disk space",
            "Up to 200 GB bandwidth",
            "15 subdomains",
            "100 email accounts",
            "15 mailing lists",
            "Free SSL", false),
    DELUXE("Deluxe", new BigDecimal("35.00"),
            "1 domain",
            "120 GB disk space",
            "Up to 500 GB bandwidth",
            "25 subdomains",
            "300 email accounts",
            "25 mailing lists",
            "Free SSL", true);

    private final String label;
    private final BigDecimal monthlyPrice;
    private final String domains;
    private final String diskSpace;
    private final String bandwidth;
    private final String subdomains;
    private final String emailAccounts;
    private final String mailingLists;
    private final String sslCoverage;
    // Whether the Free SSL row's icon indicates inclusion (✓) vs exclusion (✗) for this plan.
    // The text "Free SSL" appears on every card; the icon is what actually conveys availability.
    private final boolean freeSslIncluded;

    SingleDomainHostingPlan(String label, BigDecimal monthlyPrice,
                            String domains, String diskSpace, String bandwidth, String subdomains,
                            String emailAccounts, String mailingLists, String sslCoverage,
                            boolean freeSslIncluded) {
        this.label = label;
        this.monthlyPrice = monthlyPrice;
        this.domains = domains;
        this.diskSpace = diskSpace;
        this.bandwidth = bandwidth;
        this.subdomains = subdomains;
        this.emailAccounts = emailAccounts;
        this.mailingLists = mailingLists;
        this.sslCoverage = sslCoverage;
        this.freeSslIncluded = freeSslIncluded;
    }

    public String getLabel() { return label; }

    public BigDecimal getMonthlyPrice() { return monthlyPrice; }

    public String getDomains() { return domains; }

    public String getDiskSpace() { return diskSpace; }

    public String getBandwidth() { return bandwidth; }

    public String getSubdomains() { return subdomains; }

    public String getEmailAccounts() { return emailAccounts; }

    public String getMailingLists() { return mailingLists; }

    public String getSslCoverage() { return sslCoverage; }

    public boolean isFreeSslIncluded() { return freeSslIncluded; }

    /** Returns the lowest monthly price across all plans (used on the landing page). */
    public static BigDecimal getStartingPrice() {
        BigDecimal lowest = null;
        for (SingleDomainHostingPlan plan : values()) {
            if (lowest == null || plan.monthlyPrice.compareTo(lowest) < 0) {
                lowest = plan.monthlyPrice;
            }
        }
        return lowest;
    }

    public static SingleDomainHostingPlan fromLabel(String label) {
        for (SingleDomainHostingPlan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown SDH plan label: " + label);
    }
}
