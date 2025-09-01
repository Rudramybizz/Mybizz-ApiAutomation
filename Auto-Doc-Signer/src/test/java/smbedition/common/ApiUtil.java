package smbedition.common;

public class ApiUtil {

    // Centralized delay time (in ms)
    private static final long DELAY_BETWEEN_REQUESTS = 4000;

    private ApiUtil() {
        // prevent instantiation
    }

    // Method to wait before next request
    public static void waitForNextRequest() {
        try {
            Thread.sleep(DELAY_BETWEEN_REQUESTS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during delay", e);
        }
    }
}
