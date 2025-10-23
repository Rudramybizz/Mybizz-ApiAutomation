package smbedition.common.waits;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ApiUtil {

    private static final long DELAY_BETWEEN_REQUESTS = 4000;
    private static final long DEFAULT_TIMEOUT = 30;

    private ApiUtil() {
        // prevent instantiation
    }

    // ==================== WAIT METHODS ====================

    public static void waitForNextRequest() {
        try {
            Thread.sleep(DELAY_BETWEEN_REQUESTS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during delay", e);
        }
    }

    public static void waitSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during wait", e);
        }
    }

    public static void waitMilliseconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during wait", e);
        }
    }

    // ==================== ASSERTION METHODS ====================

    public static void assertStatus(Response response, int expectedStatus) {
        Objects.requireNonNull(response, "Response is null");

        int actualStatus = response.getStatusCode();

        // Attach to Allure
        attachResponseToAllure(response);

        // Log to console
        logResponse(response);

        if (actualStatus != expectedStatus) {
            throw new AssertionError(
                    String.format("Expected HTTP status: %d but got: %d\nResponse: %s",
                            expectedStatus, actualStatus, response.getBody().asString())
            );
        }
    }

    public static void assertStatusWithMessage(Response response, int expectedStatus, String message) {
        Objects.requireNonNull(response, "Response is null");

        int actualStatus = response.getStatusCode();
        attachResponseToAllure(response);
        logResponse(response);

        if (actualStatus != expectedStatus) {
            throw new AssertionError(
                    String.format("%s\nExpected: %d, Actual: %d\nResponse: %s",
                            message, expectedStatus, actualStatus, response.getBody().asString())
            );
        }
    }

    public static void assertSuccessStatus(Response response) {
        assertStatus(response, 200);
    }

    public static void assertCreatedStatus(Response response) {
        assertStatus(response, 201);
    }

    // ==================== ALLURE ATTACHMENTS ====================

    private static void attachResponseToAllure(Response response) {
        try {
            Allure.addAttachment("Response Status", "text/plain",
                    String.valueOf(response.getStatusCode()));
            Allure.addAttachment("Response Headers", "text/plain",
                    response.getHeaders().toString());
            Allure.addAttachment("Response Body", new ByteArrayInputStream(
                    response.getBody().asByteArray()));
        } catch (Exception e) {
            System.out.println("Failed to attach response to Allure: " + e.getMessage());
        }
    }

    public static void attachRequest(Object requestBody) {
        if (requestBody != null) {
            try {
                Allure.addAttachment("Request Body", "application/json",
                        String.valueOf(new ByteArrayInputStream(requestBody.toString().getBytes())));
            } catch (Exception e) {
                System.out.println("Failed to attach request body to Allure: " + e.getMessage());
            }
        }
    }

    public static void attachRequestAndResponse(Object requestBody, Response response) {
        attachRequest(requestBody);
        attachResponseToAllure(response);
    }

    // ==================== LOGGING ====================

    private static void logResponse(Response response) {
        System.out.println("=== API RESPONSE ===");
        System.out.println("Status Code : " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asPrettyString());
        System.out.println("===================");
    }

    public static void logRequest(String method, String url, Object body) {
        System.out.println("=== API REQUEST ===");
        System.out.println("Method : " + method);
        System.out.println("URL : " + url);
        if (body != null) {
            System.out.println("Body:\n" + body);
        }
        System.out.println("===================");
    }

    // ==================== UTILITY METHODS ====================

    public static String cleanInstanceId(String instanceId) {
        if (instanceId == null) return "";
        return instanceId.replace("[", "").replace("]", "").trim();
    }

    public static String extractValue(Response response, String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }

    public static boolean isSuccessfulStatus(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    public static void validateResponseTime(Response response, long maxTimeInMillis) {
        long responseTime = response.getTime();
        if (responseTime > maxTimeInMillis) {
            throw new AssertionError(
                    String.format("Response time exceeded! Expected: <%dms, Actual: %dms",
                            maxTimeInMillis, responseTime)
            );
        }
    }
}