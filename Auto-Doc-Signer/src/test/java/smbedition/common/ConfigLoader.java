package smbedition.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();
    private static String currentEnvironment;

    public static void load() {
        // Determine environment
        String env = System.getProperty("env", "staging").trim().toLowerCase();
        currentEnvironment = env;

        System.out.println("🔧 Loading configuration for environment: " + env.toUpperCase());

        // Load environment-specific properties file
        loadPropertiesFile(env + ".properties");

        // System properties override everything
        overrideWithSystemProperties();

        logConfiguration();
    }

    private static void loadPropertiesFile(String filename) {
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(filename)) {
            if (in == null) {
                throw new RuntimeException("Config file not found on classpath: " + filename);
            }
            props.load(in);
            System.out.println("✅ Loaded config from: " + filename);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment properties: " + filename, e);
        }
    }

    private static void overrideWithSystemProperties() {
        // Override base URLs
        overrideProperty("base.url");
        overrideProperty("sso.base.url");

        // You can add more overridable properties here
        overrideProperty("db.url");
        overrideProperty("api.timeout");
    }

    private static void overrideProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            props.setProperty(key, systemValue);
            System.out.println("⚡ Overridden from system property: " + key + "=" + systemValue);
        }
    }

    private static void logConfiguration() {
        System.out.println("\n📋 CURRENT CONFIGURATION:");
        System.out.println("Environment: " + currentEnvironment.toUpperCase());
        System.out.println("Base URL: " + get("base.url"));
        System.out.println("SSO Base URL: " + get("sso.base.url"));
        System.out.println("========================\n");
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in environment: " + currentEnvironment);
        }
        return value.trim();
    }

    public static String getEndpoint(String endpointKey) {
        String endpoint = get("endpoint." + endpointKey);
        return endpoint.startsWith("/") ? endpoint : "/" + endpoint;
    }

    public static String getBaseUrlForEndpoint(String endpointKey) {
        if (endpointKey.startsWith("sso.")) {
            return get("sso.base.url");
        } else {
            return get("base.url");
        }
    }

    public static String getCurrentEnvironment() {
        return currentEnvironment;
    }

    // Helper method to check if we're in production
    public static boolean isProduction() {
        return "production".equalsIgnoreCase(currentEnvironment);
    }

    // Helper method to check if we're in staging
    public static boolean isStaging() {
        return "staging".equalsIgnoreCase(currentEnvironment);
    }
}