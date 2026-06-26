package config;

import java.math.BigDecimal;

public enum MultipleDomainHostingPlan {
    MD1("MD1", new BigDecimal("22.00")),
    MD2("MD2", new BigDecimal("41.80")),
    MD3("MD3", new BigDecimal("65.00")),
    MD4("MD4", new BigDecimal("75.00")),
    MD5("MD5", new BigDecimal("85.00"));

    private final String label;
    private final BigDecimal monthlyPrice;

    MultipleDomainHostingPlan(String label, BigDecimal monthlyPrice) {
        this.label = label;
        this.monthlyPrice = monthlyPrice;
    }

    public String getLabel() { return label; }

    public BigDecimal getMonthlyPrice() { return monthlyPrice; }

    /** Returns the lowest monthly price across all plans (used on the landing page). */
    public static BigDecimal getStartingPrice() {
        BigDecimal lowest = null;
        for (MultipleDomainHostingPlan plan : values()) {
            if (lowest == null || plan.monthlyPrice.compareTo(lowest) < 0) {
                lowest = plan.monthlyPrice;
            }
        }
        return lowest;
    }

    public static MultipleDomainHostingPlan fromLabel(String label) {
        for (MultipleDomainHostingPlan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown MDH plan label: " + label);
    }
}
