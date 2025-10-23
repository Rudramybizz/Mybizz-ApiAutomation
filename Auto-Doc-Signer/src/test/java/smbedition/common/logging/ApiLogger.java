package smbedition.common.logging;

import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;

public class ApiLogger {

    public static void logRequest(FilterableRequestSpecification request, String endpoint) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n" + "=".repeat(80) + "\n");
        logMessage.append("📤 API REQUEST\n");
        logMessage.append("=".repeat(80) + "\n");
        logMessage.append("Endpoint: ").append(endpoint).append("\n");
        logMessage.append("Method: ").append(request.getMethod()).append("\n");
        logMessage.append("URL: ").append(request.getURI()).append("\n");

        if (request.getHeaders() != null) {
            logMessage.append("Headers:\n");
            request.getHeaders().forEach(header ->
                    logMessage.append("  ").append(header.getName()).append(": ").append(header.getValue()).append("\n")
            );
        }

        if (request.getBody() != null) {
            logMessage.append("Body:\n").append(request.getBody().toString()).append("\n");
        }

        if (request.getQueryParams() != null && !request.getQueryParams().isEmpty()) {
            logMessage.append("Query Params: ").append(request.getQueryParams()).append("\n");
        }

        logMessage.append("=".repeat(80));
        Log.debug(logMessage.toString());
    }

    public static void logResponse(Response response, long executionTime) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n" + "=".repeat(80) + "\n");
        logMessage.append("📥 API RESPONSE\n");
        logMessage.append("=".repeat(80) + "\n");
        logMessage.append("Status Code: ").append(response.getStatusCode()).append("\n");
        logMessage.append("Status Line: ").append(response.getStatusLine()).append("\n");
        logMessage.append("Response Time: ").append(executionTime).append("ms\n");

        if (response.getHeaders() != null) {
            logMessage.append("Headers:\n");
            response.getHeaders().forEach(header ->
                    logMessage.append("  ").append(header.getName()).append(": ").append(header.getValue()).append("\n")
            );
        }

        try {
            String responseBody = response.getBody().asPrettyString();
            logMessage.append("Body:\n").append(responseBody).append("\n");
        } catch (Exception e) {
            logMessage.append("Body: [Unable to parse response body]\n");
        }

        logMessage.append("=".repeat(80));

        if (response.getStatusCode() >= 400) {
            Log.error(logMessage.toString());
        } else {
            Log.debug(logMessage.toString());
        }
    }

    public static void logApiCall(String method, String url, int statusCode, long responseTime) {
        String icon = statusCode >= 400 ? "❌" : "✅";
        Log.info("%s %s %s - Status: %d, Time: %dms", icon, method, url, statusCode, responseTime);
    }
}