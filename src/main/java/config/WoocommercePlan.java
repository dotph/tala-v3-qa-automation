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

    /**
     * The "$X/year on renewal" copy on each plan card is the pre-promo monthly
     * price × 12 (verified live: Start-up $5.00 × 12 = $60.00, Pro $8.00 × 12
     * = $96.00). Deriving it here keeps the source of truth on the enum
     * instead of scattering the yearly literal across the feature file.
     */
    public BigDecimal getYearlyRenewalPrice() {
        return originalMonthlyPrice.multiply(new BigDecimal("12"));
    }

    public static WoocommercePlan fromLabel(String label) {
        for (WoocommercePlan p : values()) {
            if (p.label.equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown Woocommerce plan label: " + label);
    }
}
