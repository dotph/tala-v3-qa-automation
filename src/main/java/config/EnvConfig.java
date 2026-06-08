package config;

public class EnvConfig {

    public enum TestEnvironment {
        MDOT("https://mdot.ph"),
        PRODUCTION("https://dot.ph");

        private final String baseUrl;

        TestEnvironment(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getBaseUrl() { return baseUrl; }
    }

    private static final TestEnvironment TEST_ENV = TestEnvironment.valueOf(
            System.getProperty("testEnv", "MDOT").toUpperCase());

    public static String getBaseUrl() {
        return TEST_ENV.getBaseUrl();
    }

    public static TestEnvironment getTestEnvironment() {
        return TEST_ENV;
    }

    public static String getCpanelHostingUrl() {
        return getBaseUrl() + "/shared-linux-hosting";
    }

    public static String getUrl(String path) {
        return getBaseUrl() + path;
    }
}
