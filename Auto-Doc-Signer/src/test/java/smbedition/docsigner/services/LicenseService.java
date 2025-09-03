package smbedition.docsigner.services;

import io.restassured.response.Response;
import smbedition.common.ApiUtil;
import smbedition.common.TestData;
import smbedition.common.TokenManager;
import smbedition.common.ApiClient;

import static smbedition.common.TokenManager.token;

public class LicenseService {

    private static String orgId = TestData.getOrgId();
    private static String getTokenOrLogin() {

        String token = TokenManager.get();

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


//==============================================================

    public static Response getLicenseInfo(){
        if (token == null || token.isEmpty()) {
            System.out.println("Token is null or empty. Attempting to log in...");
            token = getTokenOrLogin();
        }
        System.out.println("===== Get License Info API =====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get(
                "doc.getlicense",
                orgId,
                token
        );
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;

    }


    public static Response getLicenseInvoice(){
        if (token == null || token.isEmpty()) {
            System.out.println("Token is null or empty. Attempting to log in...");
            token = getTokenOrLogin();
        }
        System.out.println("===== Get License Info API =====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get(
                "doc.getlicenseInvoice",
                orgId,
                token
        );
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;

    }







}
