package config;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

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

    // The values above are the source of truth (kept visible in the enum
    // like SDH/MDH). /config/vps-plans.properties mirrors them so
    // stakeholders can review pricing without reading Java. The static
    // block below asserts the two are in sync at class init — if a price
    // changes and only one file is updated, the next `mvn test` fails
    // loudly with a message naming the specific plan and field.
    static {
        Properties props = new Properties();
        try (InputStream in = VpsPlan.class.getResourceAsStream("/config/vps-plans.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                        "/config/vps-plans.properties not found on the classpath");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load /config/vps-plans.properties", e);
        }
        for (VpsPlan p : values()) {
            String key = p.name().toLowerCase();
            String expectedLabel = props.getProperty("plan." + key + ".label");
            String expectedPriceStr = props.getProperty("plan." + key + ".monthlyPrice");
            if (expectedLabel == null || expectedPriceStr == null) {
                throw new IllegalStateException(
                        "vps-plans.properties is missing label or monthlyPrice for '" + key + "'");
            }
            if (!p.label.equals(expectedLabel)) {
                throw new IllegalStateException(
                        "Label drift for VpsPlan." + p.name() + ": enum=\"" + p.label
                                + "\", vps-plans.properties=\"" + expectedLabel + "\" — sync them");
            }
            // compareTo (not equals) so trailing-zero differences like 175 vs 175.00 still match.
            if (p.monthlyPrice.compareTo(new BigDecimal(expectedPriceStr)) != 0) {
                throw new IllegalStateException(
                        "Price drift for VpsPlan." + p.name() + ": enum=" + p.monthlyPrice.toPlainString()
                                + ", vps-plans.properties=" + expectedPriceStr + " — sync them");
            }
        }
    }
}
