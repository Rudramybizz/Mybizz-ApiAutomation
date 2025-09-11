package smbedition.common;


import io.qameta.allure.Allure;
import io.restassured.response.Response;

import java.io.ByteArrayInputStream;
import java.util.Objects;


public class ApiUtil {

    private static final long DELAY_BETWEEN_REQUESTS = 4000;

    private ApiUtil() {
        // prevent instantiation
    }

    public static void waitForNextRequest() {
        try {
            Thread.sleep(DELAY_BETWEEN_REQUESTS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during delay", e);
        }
    }


    public static void assertStatus(Response response, int expectedStatus) {
        Objects.requireNonNull(response, "Response is null");

        int actualStatus = response.getStatusCode();


        try {
            Allure.addAttachment("Response Body", new ByteArrayInputStream(response.getBody().asByteArray()));
        } catch (Exception e) {
            System.out.println("Failed to attach response to Allure: " + e.getMessage());
        }

        System.out.println("=== API RESPONSE ===");
        System.out.println("Status Code : " + actualStatus);
        System.out.println("Response Body:\n" + response.getBody().asPrettyString());
        System.out.println("===================");


        if (actualStatus != expectedStatus) {
            throw new AssertionError("Expected HTTP status: " + expectedStatus + " but got: " + actualStatus);
        }
    }


    public static void attachRequest(Object requestBody) {
        if (requestBody != null) {
            try {
                Allure.addAttachment("Request Body", new ByteArrayInputStream(requestBody.toString().getBytes()));
            } catch (Exception e) {
                System.out.println("Failed to attach request body to Allure: " + e.getMessage());
            }
        }
    }


    public static String cleanInstanceId(String instanceId) {
        if (instanceId == null) return "";
        return instanceId.replace("[", "").replace("]", "").trim();
    }
}
