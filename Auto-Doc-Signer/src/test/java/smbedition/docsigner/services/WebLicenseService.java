package smbedition.docsigner.services;

import io.restassured.response.Response;
import smbedition.common.*;


import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class WebLicenseService {

    private static String orgId = TestData.getOrgId();
    private static String token = TokenManager.get();
    private static String signatureId ;

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

//    ================ Add Digital Signature ==================
    public static Response addDigitalSignatureSucess() {
        ApiUtil.waitForNextRequest();

        File signatureFile = new File("src/test/resources/cartlines.pfx");

        if (!signatureFile.exists()) {
            throw new RuntimeException("Signature file not found: " + signatureFile.getAbsolutePath());
        }

        Response response = ApiClient.postMultipart(
                "doc.adddigitalsignatureSucess",
                signatureFile,
                "Cart@999",
                token,
                orgId
        );

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }


    public static Response addDigitalSignatureWithWrongPassword() {
        ApiUtil.waitForNextRequest();
        File signatureFile = new File("src/test/resources/cartlines.pfx");

        Map<String, Object> body = new HashMap<>();
        body.put("signature_file", signatureFile);
        body.put("password", "WrongPass");

        return ApiClient.post("doc.adddigitalsignatureSucess", body, token, orgId);
    }
    public static Response addDigitalSignatureWithMissingFile() {
        ApiUtil.waitForNextRequest();

        Map<String, Object> body = new HashMap<>();
        body.put("password", "Cart@999");

        return ApiClient.post("doc.adddigitalsignatureSucess", body, token, orgId);
    }


    public static Response addDigitalSignatureWithMissingPassword() {
        ApiUtil.waitForNextRequest();
        File signatureFile = new File("src/test/resources/cartlines.pfx");

        Map<String, Object> body = new HashMap<>();
        body.put("signature_file", signatureFile);

        return ApiClient.post("doc.adddigitalsignatureSucess", body, token, orgId);
    }




//    ===============Digital Signature =============

    public static Response getDigitalSignature() {
        System.out.println("Get Digital Signature");
        System.out.println("token:"+token);
        System.out.println("Org Id:"+orgId);

        Response response = ApiClient.get
                (
                        "doc.getdigitalsignature",
                        orgId,
                        token
                );

        response.prettyPrint();
        signatureId = response.jsonPath().getString("data.id");
        return response;
    }





    public static Response deleteDigitalSignature() {
        ApiUtil.waitForNextRequest();
        if (signatureId==null|| signatureId .isEmpty()){
         System.out.println("Signature ID is null or empty. Fetching Digital Signature to get a valid ID.");
        }

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("purpose", "delete");

        Response response = ApiClient.patch(
                "doc.deletedigitalsignature",
                signatureId,
                null,
                queryParams,
                token,
                orgId
        );
        logResponse("Delete Digital Signature", response);
        response.prettyPrint();

        return response;
    }



//    ==================Signature Properties ==================

public static Response addSignature(){
    ApiUtil.waitForNextRequest();

    Map<String, String> body = new HashMap<>();
     body.put("signature_position", "570,70,370,150");
     body.put("default_page_number", "first");
     body.put("custom_page_number", null);
     body.put( "purpose_or_reason", "document");
     body.put("default_location", "Hyderabad");


    Response response = ApiClient.post(
            "doc.adddigitalsignature",
            signatureId,
            body,
            token,
            orgId
    );

    response.prettyPrint();
     return response;
}


public static  Response getSignatureProperties() {
    String token = getTokenOrLogin();
    System.out.println("Signature Id:"+signatureId);
    System.out.println("OrgId:"+orgId);
    System.out.println("Get Signature Properties");
    Response response = ApiClient.get(
            "doc.getsignatureproperties",
                       signatureId,
                       orgId,
                       token
                       );
    response.prettyPrint();

    return response;
}



}