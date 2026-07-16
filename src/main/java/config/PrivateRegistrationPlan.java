package config;

import java.math.BigDecimal;

public enum PrivateRegistrationPlan {
    PRIVATE_REGISTRATION("Private Registration", new BigDecimal("10.00"));

    private final String label;
    private final BigDecimal yearlyPrice;

    PrivateRegistrationPlan(String label, BigDecimal yearlyPrice) {
        this.label = label;
        this.yearlyPrice = yearlyPrice;
    }

    public String getLabel() { return label; }

    public BigDecimal getYearlyPrice() { return yearlyPrice; }

    public static PrivateRegistrationPlan fromLabel(String label) {
        for (PrivateRegistrationPlan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown Private Registration plan label: " + label);
    }
}
