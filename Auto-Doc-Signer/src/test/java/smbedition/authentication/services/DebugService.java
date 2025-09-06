package smbedition.authentication.services;

import io.restassured.response.Response;
import smbedition.common.ApiUtil;
import smbedition.common.TokenManager;
import smbedition.common.ApiClient;

import java.util.HashMap;
import java.util.Map;


public class DebugService {
    private static String token = TokenManager.get();
    private static void logRequest(String apiName, String body) {
        System.out.println("=== " + apiName + " Request ===");
        System.out.println(body);
        System.out.println("========================");
    }

    private static void logResponse(String apiName, Response response) {
        System.out.println("=== " + apiName + " Response ===");
        System.out.println("Status Code: " + response.getStatusCode());
        response.prettyPrint();
        System.out.println("========================");
    }

    private static String getTokenOrLogin() {

        if (token == null || token.trim().isEmpty()) {
            System.out.println("Token not found. Logging in to generate new token...");
            token = TokenManager.get();

            if (token == null || token.trim().isEmpty()) {
                throw new IllegalStateException("Failed to generate token. Login process did not return a token.");
            }
        }
        System.out.println("Token successfully retrieved: " + token);
        return token;
    }


    public static Response validateUser() {
        String currentToken = getTokenOrLogin(); // ensure we have a valid token

        if (currentToken == null || currentToken.trim().isEmpty()) {
            throw new IllegalStateException("Token is null in debug service class.");
        }

        ApiUtil.waitForNextRequest();

        Map<String, String> body = new HashMap<>();
        body.put("token", currentToken);  // send token in body

        System.out.println("Validate User Request Body: " + body);

        Response response = ApiClient.post(
                "debug.validuser",
                body,
                currentToken
        );

        logResponse("Validate User", response);
        return response;
    }



    public static String getDeploymentVersion(){

        System.out.println("===== Get Deployment Version API =====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("debug.deploymentversion");

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response.jsonPath().getString("data.version");

    }
}