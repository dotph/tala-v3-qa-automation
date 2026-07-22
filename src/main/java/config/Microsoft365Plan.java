package config;

import java.math.BigDecimal;

public enum Microsoft365Plan {
    BUSINESS_BASIC   ("Business Basic",    new BigDecimal("72.00")),
    BUSINESS_STANDARD("Business Standard", new BigDecimal("150.00")),
    APPS_FOR_BUSINESS("Apps for Business", new BigDecimal("99.00"));

    private final String label;
    private final BigDecimal yearlyPrice;

    Microsoft365Plan(String label, BigDecimal yearlyPrice) {
        this.label = label;
        this.yearlyPrice = yearlyPrice;
    }

    public String getLabel() { return label; }

    public BigDecimal getYearlyPrice() { return yearlyPrice; }

    public static Microsoft365Plan fromLabel(String label) {
        for (Microsoft365Plan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown Microsoft 365 plan label: " + label);
    }
}
