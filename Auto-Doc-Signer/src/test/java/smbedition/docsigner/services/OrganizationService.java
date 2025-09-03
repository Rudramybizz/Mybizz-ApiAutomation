package smbedition.docsigner.services;

import io.restassured.response.Response;
import smbedition.common.ApiUtil;
import smbedition.common.TestData;
import smbedition.common.TokenManager;
import smbedition.organization.util.ApiClient;

import static smbedition.common.TokenManager.token;

public class OrganizationService {
    private static String orgId = TestData.getOrgId();
    private static String getTokenOrLogin() {

        String token = TokenManager.get();

        if (token == null || token.trim().isEmpty()) {
            System.out.println("Token not found. Logging in to generate new token...");
            token = TokenManager.get();

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



//=============================================================
    public static Response getOrganizationUserFunctionalities() {
        if (token == null || token.isEmpty()) {
            System.out.println("Token is null or empty. Attempting to log in...");
            token = getTokenOrLogin();
        }
        System.out.println("===== Get Organization User Functionalities API =====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get(
                "doc.orguserfunctionalities",
                orgId,
                token
        );

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }

    public static Response getPlanDetails(){
        if (token == null || token.isEmpty()) {
            System.out.println("Token is null or empty. Attempting to log in...");
            token = getTokenOrLogin();
        }

        System.out.println("===== Get Plan Details API =====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get(
                "doc.plandetails",
                orgId,
                token
        );
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }

    public static Response getPlanAddOn(){
        if (token == null || token.isEmpty()) {
            System.out.println("Token is null or empty. Attempting to log in...");
            token = getTokenOrLogin();
        }
        System.out.println("===== Get Plan Add On Details API =====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get(
                "doc.planAddon",
                orgId,
                token
        );
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }








}