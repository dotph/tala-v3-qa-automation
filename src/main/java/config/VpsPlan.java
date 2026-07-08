package config;

import java.math.BigDecimal;

public enum VpsPlan {
    MIDDLEWEIGHT("MiddleWeight VPS", new BigDecimal("175.00")),
    CRUISERWEIGHT("CruiserWeight VPS", new BigDecimal("325.00")),
    HEAVYWEIGHT("HeavyWeight VPS", new BigDecimal("650.00"));

    private final String label;
    private final BigDecimal monthlyPrice;

    VpsPlan(String label, BigDecimal monthlyPrice) {
        this.label = label;
        this.monthlyPrice = monthlyPrice;
    }

    public String getLabel() { return label; }

    public BigDecimal getMonthlyPrice() { return monthlyPrice; }

    public static VpsPlan fromLabel(String label) {
        for (VpsPlan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown VPS plan label: " + label);
    }
}
