package config;

import java.math.BigDecimal;

public enum SslPlan {
    DOMAIN_VALIDATION      ("Domain Validation",       new BigDecimal("19.95")),
    ORGANIZATION_VALIDATION("Organization Validation", new BigDecimal("115.00")),
    WILDCARD_CERTIFICATE   ("Wildcard Certificate",    new BigDecimal("299.95"));

    private final String label;
    private final BigDecimal yearlyPrice;

    SslPlan(String label, BigDecimal yearlyPrice) {
        this.label = label;
        this.yearlyPrice = yearlyPrice;
    }

    public String getLabel() { return label; }

    public BigDecimal getYearlyPrice() { return yearlyPrice; }

    public static SslPlan fromLabel(String label) {
        for (SslPlan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown SSL plan label: " + label);
    }
}
