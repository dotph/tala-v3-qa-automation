package config;

import java.math.BigDecimal;

public enum RegistryLockPlan {
    REGISTRY_LOCK("Registry Lock", new BigDecimal("100.00"));

    private final String label;
    private final BigDecimal yearlyPrice;

    RegistryLockPlan(String label, BigDecimal yearlyPrice) {
        this.label = label;
        this.yearlyPrice = yearlyPrice;
    }

    public String getLabel() { return label; }

    public BigDecimal getYearlyPrice() { return yearlyPrice; }

    public static RegistryLockPlan fromLabel(String label) {
        for (RegistryLockPlan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown Registry Lock plan label: " + label);
    }
}
