package smbedition.common.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.util.Properties;

public class Log {

    private static Logger logger = Logger.getLogger(Log.class.getName());
    private static boolean initialized = false;

    // Initialize logger with configuration
    public static void initialize() {
        if (!initialized) {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream("src/main/resources/log4j.properties"));
                PropertyConfigurator.configure(props);
                initialized = true;
                info("Logger initialized successfully");
            } catch (Exception e) {
                // Fallback to basic configuration
                System.out.println("WARN: Could not load log4j.properties, using default configuration");
                initialized = true;
            }
        }
    }

    // Info Level Logs
    public static void info(String message) {
        logger.info(message);
        System.out.println("[INFO] " + getTimestamp() + " - " + message);
    }

    public static void info(String message, Object... args) {
        String formattedMessage = String.format(message, args);
        logger.info(formattedMessage);
        System.out.println("[INFO] " + getTimestamp() + " - " + formattedMessage);
    }

    // Debug Level Logs
    public static void debug(String message) {
        logger.debug(message);
        System.out.println("[DEBUG] " + getTimestamp() + " - " + message);
    }

    public static void debug(String message, Object... args) {
        String formattedMessage = String.format(message, args);
        logger.debug(formattedMessage);
        System.out.println("[DEBUG] " + getTimestamp() + " - " + formattedMessage);
    }

    // Warning Level Logs
    public static void warn(String message) {
        logger.warn(message);
        System.out.println("[WARN] " + getTimestamp() + " - " + message);
    }

    public static void warn(String message, Object... args) {
        String formattedMessage = String.format(message, args);
        logger.warn(formattedMessage);
        System.out.println("[WARN] " + getTimestamp() + " - " + formattedMessage);
    }

    public static void warn(String message, Throwable throwable) {
        logger.warn(message, throwable);
        System.out.println("[WARN] " + getTimestamp() + " - " + message);
        throwable.printStackTrace();
    }

    // Error Level Logs
    public static void error(String message) {
        logger.error(message);
        System.err.println("[ERROR] " + getTimestamp() + " - " + message);
    }

    public static void error(String message, Object... args) {
        String formattedMessage = String.format(message, args);
        logger.error(formattedMessage);
        System.err.println("[ERROR] " + getTimestamp() + " - " + formattedMessage);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
        System.err.println("[ERROR] " + getTimestamp() + " - " + message);
        throwable.printStackTrace();
    }

    // API Specific Logs
    public static void apiRequest(String method, String url, String body) {
        String logMessage = String.format(
                "API REQUEST:\nMethod: %s\nURL: %s\nBody: %s",
                method, url, body
        );
        debug(logMessage);
    }

    public static void apiResponse(String method, String url, int statusCode, String responseBody, long responseTime) {
        String logMessage = String.format(
                "API RESPONSE:\nMethod: %s\nURL: %s\nStatus: %d\nResponse Time: %dms\nBody: %s",
                method, url, statusCode, responseTime, responseBody
        );
        debug(logMessage);
    }

    public static void apiError(String method, String url, int statusCode, String errorMessage) {
        String logMessage = String.format(
                "API ERROR:\nMethod: %s\nURL: %s\nStatus: %d\nError: %s",
                method, url, statusCode, errorMessage
        );
        error(logMessage);
    }

    // Test Execution Logs
    public static void testStart(String testName) {
        info("🚀 STARTING TEST: " + testName);
    }

    public static void testPass(String testName) {
        info("✅ TEST PASSED: " + testName);
    }

    public static void testFail(String testName, String errorMessage) {
        error("❌ TEST FAILED: " + testName + " - " + errorMessage);
    }

    public static void testSkip(String testName) {
        warn("⏸️ TEST SKIPPED: " + testName);
    }

    // Step Logs
    public static void step(String stepDescription) {
        info("📝 STEP: " + stepDescription);
    }

    public static void step(String stepDescription, Object... args) {
        String formattedStep = String.format(stepDescription, args);
        info("📝 STEP: " + formattedStep);
    }

    // Verification Logs
    public static void verification(String verificationDescription) {
        debug("✓ VERIFICATION: " + verificationDescription);
    }

    public static void verification(String verificationDescription, Object... args) {
        String formattedVerification = String.format(verificationDescription, args);
        debug("✓ VERIFICATION: " + formattedVerification);
    }

    // Data Logs
    public static void data(String dataDescription, Object data) {
        debug("📊 DATA: " + dataDescription + " = " + data);
    }

    // Configuration Logs
    public static void config(String configDescription) {
        info("⚙️ CONFIG: " + configDescription);
    }

    // Helper method for timestamp
    private static String getTimestamp() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    // Cleanup
    public static void shutdown() {
        info("Shutting down logger...");
    }
}
