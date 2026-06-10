package config;

import java.math.BigDecimal;

public enum SingleDomainHostingPlan {
    STARTER("Starter", new BigDecimal("6.50")),
    PROFESSIONAL("Professional", new BigDecimal("15.00")),
    DELUXE("Deluxe", new BigDecimal("35.00"));

    private final String label;
    private final BigDecimal monthlyPrice;

    SingleDomainHostingPlan(String label, BigDecimal monthlyPrice) {
        this.label = label;
        this.monthlyPrice = monthlyPrice;
    }

    public String getLabel() { return label; }

    public BigDecimal getMonthlyPrice() { return monthlyPrice; }

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
