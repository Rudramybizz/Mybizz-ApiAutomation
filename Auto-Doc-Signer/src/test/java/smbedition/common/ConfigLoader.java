package smbedition.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    /**
     * Load environment config.
     * Priority:
     * 1) If system property 'base.url' is provided -> use that (no file needed)
     * 2) Else use -Denv=staging/preprod/prod (default staging) -> loads env.properties from classpath
     */
    public static void load() {
        String baseUrlOverride = System.getProperty("base.url");
        if (baseUrlOverride != null && !baseUrlOverride.isBlank()) {
            props.setProperty("base.url", baseUrlOverride);
            System.out.println("Loaded config from system properties: base.url=" + baseUrlOverride);
            return;
        }

        String env = System.getProperty("env", "staging").trim();
        String filename = env + ".properties";
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(filename)) {
            if (in == null) {
                throw new RuntimeException("Config file not found on classpath: " + filename);
            }
            props.load(in);
            System.out.println("Loaded config from file: " + filename);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load environment properties: " + filename, e);
        }
    }

    /**
     * Get a property value
     */
    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    /**
     * Get full API endpoint URL
     * Combines base.url + endpoint from properties
     * Example: getEndpoint("generate.otp.register")
     */

    public static String getEndpoint(String endpointKey) {
        String endpoint = get("endpoint." + endpointKey);

        // Always ensure it starts with /
        if (!endpoint.startsWith("/")) {
            endpoint = "/" + endpoint;
        }

        // 🚨 Do NOT add base.url here
        return endpoint;
    }

//    public static String getEndpoint(String endpointKey) {
//        String baseUrl = get("base.url");
//        String endpoint = get("endpoint." + endpointKey);
//        if (!baseUrl.endsWith("/") && !endpoint.startsWith("/")) {
//            endpoint = "/" + endpoint;
//        }
//        return baseUrl + endpoint;
//    }
    /**
     * Optional: get token stored in properties or system property
     */


}
