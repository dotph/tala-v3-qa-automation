package config;

import java.math.BigDecimal;

public enum WoocommercePlan {
    STARTUP_PLAN("Start-up Plan", new BigDecimal("5.00"),  new BigDecimal("2.50")),
    PRO_PLAN("Pro Plan",          new BigDecimal("8.00"),  new BigDecimal("4.00"));

    private final String label;
    private final BigDecimal originalMonthlyPrice;
    private final BigDecimal promoMonthlyPrice;

    WoocommercePlan(String label, BigDecimal originalMonthlyPrice, BigDecimal promoMonthlyPrice) {
        this.label = label;
        this.originalMonthlyPrice = originalMonthlyPrice;
        this.promoMonthlyPrice = promoMonthlyPrice;
    }

    public String getLabel() { return label; }

    public BigDecimal getOriginalMonthlyPrice() { return originalMonthlyPrice; }

    public BigDecimal getPromoMonthlyPrice() { return promoMonthlyPrice; }

    public static WoocommercePlan fromLabel(String label) {
        for (WoocommercePlan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown Woocommerce plan label: " + label);
    }
}
