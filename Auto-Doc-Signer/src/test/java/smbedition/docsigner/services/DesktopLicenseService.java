package smbedition.docsigner.services;

import io.restassured.response.Response;
import smbedition.common.ApiClient;
import smbedition.common.ApiUtil;
import smbedition.common.TestData;
import smbedition.common.TokenManager;

import java.util.HashMap;
import java.util.Map;

import static smbedition.common.TokenManager.token;

public class DesktopLicenseService {
    private static String orgId = TestData.getOrgId();

    private static String getTokenOrLogin() {
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
//

    public static Response activeLicense(){
        ApiUtil.waitForNextRequest();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);

        Map<String, String> body = new HashMap<>();
        body.put("license_id", "2505051");
        body.put("system_id","LINUX123");
        body.put("admin_email","gayathri.k@ddindia.biz");

        Response response = ApiClient.post(
                "doc.manageapicredentials",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }




}
