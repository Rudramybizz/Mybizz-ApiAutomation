package smbedition.docsigner.services;

import io.restassured.response.Response;
import smbedition.common.ApiClient;
import smbedition.common.ApiUtil;
import smbedition.common.TestData;
import smbedition.common.TokenManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static smbedition.common.TokenManager.token;

public class ApiLicenseService {
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


//    ==================Manage Api Credentials =============
    public static Response manageApiCredentials(){
        ApiUtil.waitForNextRequest();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);

        Map<String, String> body = new HashMap<>();
         body.put("enable_api_access", "true");
         body.put("regenerate","false");

        Response response = ApiClient.post(
                "doc.manageapicredentials",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }


    public static Response getApiCredentials(){
        ApiUtil.waitForNextRequest();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);

        Response response = ApiClient.get(
                "doc.getapicredentials",
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }


   public static Response getapiLog(){
       ApiUtil.waitForNextRequest();
       System.out.println("token:"+token);
       System.out.println("OrgId:"+orgId);
       Response response = ApiClient.get(
               "doc.getapilog",
               token,
               orgId
       );
       response.prettyPrint();
       return response;
   }


   public static Response uploadDigitalSignatureApiKey(){
       ApiUtil.waitForNextRequest();
       System.out.println("token:"+token);
       System.out.println("OrgId:"+orgId);

       File signatureFile = new File("src/test/resources/cartlines.pfx");
       if (!signatureFile.exists()) {
           throw new RuntimeException("Signature file not found: " + signatureFile.getAbsolutePath());
       }
       Response response = ApiClient.postMultipart(
               "doc.uploadDigitalSignatureApiKey",
               signatureFile,
               "Cart@999",
               token,
               orgId
       );

       System.out.println("Response Status Code: " + response.getStatusCode());
       response.prettyPrint();

       return response;
   }

   public static Response getDigitalSignatureApiKey(){
       ApiUtil.waitForNextRequest();
       System.out.println("token:"+token);
       System.out.println("OrgId:"+orgId);

       Response response = ApiClient.get(
               "doc.getDigitalSignatureApiKey",
               token,
               orgId
       );
       response.prettyPrint();
       return response;
   }


public static Response deleteDigitalSignatureApiKey(){
    ApiUtil.waitForNextRequest();
    System.out.println("token:"+token);
    System.out.println("OrgId:"+orgId);

    if (token == null || token.isEmpty()) {
        token = getTokenOrLogin();
    }

    Response response = ApiClient.delete(
            "doc.deleteDigitalSignatureApiKey",   // endpoint key from endpoints.properties
            "signatureId",             // path param
            token,
            orgId
    );
    System.out.println("Response Status Code: " + response.getStatusCode());
    response.prettyPrint();

    return response;
}

public static Response publicApiDoctoBase(){
    ApiUtil.waitForNextRequest();
    System.out.println("token:"+token);
    System.out.println("OrgId:"+orgId);

    File signatureFile = new File("src/test/resources/dumy.pdf");
    if (!signatureFile.exists()) {
        throw new RuntimeException("Signature file not found: " + signatureFile.getAbsolutePath());
    }
    Response response = ApiClient.postMultipart(
            "doc.publicapidoctobase",
            signatureFile,
            token,
            orgId
    );

    System.out.println("Response Status Code: " + response.getStatusCode());
    response.prettyPrint();

    return response;
    }


    public static Response publicApiBasetoDoc(){
        ApiUtil.waitForNextRequest();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);

        Map<String, String> body = new HashMap<>();
        body.put("base64", "");
        body.put("file_name","sales_quotation.pdf");

        Response response = ApiClient.post(
                "doc.publicapibasetodoc",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }

    public static Response generateApiToken(){
        ApiUtil.waitForNextRequest();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);

        Response response = ApiClient.get(
                "doc.generateapitoken",
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }

//    public static Response signdocumentApiToken(){
//
//    }







}
