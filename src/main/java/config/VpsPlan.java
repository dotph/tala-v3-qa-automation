package config;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public enum VpsPlan {
    MIDDLEWEIGHT("middleweight"),
    CRUISERWEIGHT("cruiserweight"),
    HEAVYWEIGHT("heavyweight");

    // Loaded once at class initialisation from /config/vps-plans.properties.
    // Prices live in the config file (not hardcoded here) so a stakeholder
    // reviewing the test spec can see the expected amounts without opening
    // the enum — flagged in QATEAM-1001's review as opaque otherwise.
    private static final Properties CONFIG = loadConfig();

    private final String key;

    VpsPlan(String key) {
        this.key = key;
    }

    public String getLabel() {
        return require("plan." + key + ".label");
    }

    public BigDecimal getMonthlyPrice() {
        return new BigDecimal(require("plan." + key + ".monthlyPrice"));
    }

    public static VpsPlan fromLabel(String label) {
        for (VpsPlan p : values()) {
            if (p.getLabel().equals(label)) return p;
        }
        throw new IllegalArgumentException("Unknown VPS plan label: " + label);
    }

    private static String require(String propertyKey) {
        String value = CONFIG.getProperty(propertyKey);
        if (value == null) {
            throw new IllegalStateException(
                    "Missing property \"" + propertyKey + "\" in /config/vps-plans.properties");
        }
        return value;
    }

    private static Properties loadConfig() {
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
        return props;
    }
}
