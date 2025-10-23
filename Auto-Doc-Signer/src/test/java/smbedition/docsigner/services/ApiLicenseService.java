//package smbedition.docsigner.services;
//
//import io.restassured.response.Response;
//import smbedition.common.*;
//import smbedition.common.waits.ApiUtil;
//
//import java.io.File;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import static smbedition.common.TokenManager.token;
//
//public class ApiLicenseService {
//    private static String orgId = TestData.getOrgId();
//    private static String apiKey;
//    private static String apiSecret;
//    private static String apiToken;
//    private static String base64;
//    private static String filename;
//
//    private static String getTokenOrLogin() {
//        if (token == null || token.trim().isEmpty()) {
//            System.out.println("Token not found. Logging in to generate new token...");
//            token = TokenManager.get();
//        }
//        System.out.println("Token successfully retrieved: " + token);
//        return token;
//    }
//    private static void logRequest(String apiName, String body) {
//        System.out.println("=== " + apiName + " Request ===");
//        System.out.println(body);
//        System.out.println("========================");
//    }
//    private static void logResponse(String apiName, Response response) {
//        System.out.println("=== " + apiName + " Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("========================");
//    }
//
//
////    ==================Manage Api Credentials =============
//    public static Response manageApiCredentials(){
//
//        ApiUtil.waitForNextRequest();
//        System.out.println("token:"+token);
//        System.out.println("OrgId:"+orgId);
//
//        Map<String, Object> body = new HashMap<>();
//         body.put( "app_name", "APP1");
////         body.put("regenerate",false);
//
//        Response response = ApiClient.post(
//                "doc.manageapicredentials",
//                body,
//                token,
//                orgId
//        );
//        response.prettyPrint();
//        return response;
//    }
//
//    public static Response getApiCredentials(){
//        ApiUtil.waitForNextRequest();
//        System.out.println("token:"+token);
//        System.out.println("OrgId:"+orgId);
//
//        Response response = ApiClient.get(
//                "doc.getapicredentials",
//                orgId,
//                token
//        );
//        response.prettyPrint();
//        apiKey = response.jsonPath().getString("data.api_key");
//        apiSecret = response.jsonPath().getString("data.api_secret");
//        return response;
//    }
//
//
//
//    public static Response generateApiToken(){
//        String decryptApiKey = EncryptApi.decryptField(apiKey);
//        ApiUtil.waitForNextRequest();
//        String decryptApiSecret = EncryptApi.decryptField(apiSecret);
//        ApiUtil.waitForNextRequest();
//        System.out.println("token:"+token);
//        System.out.println("OrgId:"+orgId);
//        System.out.println("ApiKey:"+decryptApiKey);
//        System.out.println("Api Secret:"+decryptApiSecret);
//
//        String licenseId = TestData.getLicenseId();
//        System.out.println("LicenseId:" +licenseId );
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put("API-Key", decryptApiKey );
//        headers.put("API-Secret",decryptApiSecret);
//        headers.put("License-ID", licenseId);
//        headers.put("Authorization", "Bearer " + token);
//        headers.put(ApiClient.ORG_HEADER, orgId);
//
//        Response response = ApiClient.postWithHeaders(
//                "doc.generateapitoken",
//                headers
//        );
//        response.prettyPrint();
//        apiToken = response.jsonPath().getString("token");
//        return response;
//    }
//
//
//
//
////
////   public static Response uploadDigitalSignatureApiKey(){
////       ApiUtil.waitForNextRequest();
////       System.out.println("token:"+token);
////       System.out.println("OrgId:"+orgId);
////       System.out.println("API Token:"+apiToken);
////
////       File signatureFile = new File("src/test/resources/cartlines.pfx");
////       if (!signatureFile.exists()) {
////           throw new RuntimeException("Signature file not found: " + signatureFile.getAbsolutePath());
////       }
////       Response response = ApiClient.postMultipart(
////               "doc.uploadDigitalSignatureApiCred",
////               signatureFile,
////               "Cart@999",
////               token,
////               orgId
////       );
////
////       System.out.println("Response Status Code: " + response.getStatusCode());
////       response.prettyPrint();
////
////       return response;
////   }
//
//    public static Response uploadDigitalSignatureApiKey(){
//        ApiUtil.waitForNextRequest();
//        System.out.println("token: " + token);
//        System.out.println("OrgId: " + orgId);
//        System.out.println("API Token: " + apiToken);
//
//        File signatureFile = new File("src/test/resources/cartlines.pfx");
//        if (!signatureFile.exists()) {
//            throw new RuntimeException("Signature file not found: " + signatureFile.getAbsolutePath());
//        }
//        Map<String, String> headers = new HashMap<>();
//        headers.put("organization", orgId);
//        headers.put("Authorization", "Bearer " + token);
//        headers.put("Auth", "Bearer " + apiToken);
//
//        Response response = ApiClient.postMultipartWithHeaders(
//                "doc.uploadDigitalSignatureApiCred",
//                signatureFile,
//                "Cart@999",
//                headers
//        );
//
//        System.out.println("Response Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//
//        return response;
//    }
//
//
//    public static Response getDigitalSignatureApiKey(){
//       ApiUtil.waitForNextRequest();
//       System.out.println("token:"+token);
//       System.out.println("OrgId:"+orgId);
//       System.out.println("API Token: " + apiToken);
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put("organization", orgId);
//        headers.put("Authorization", "Bearer " + token);
//        headers.put("Auth", "Bearer " + apiToken);
//
//        Response response = ApiClient.getWithHeaders(
//                "doc.getDigitalSignatureApiKey",
//                headers
//        );
//
//        System.out.println("Response Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//
//        return response;
//   }
//
//    public static Response getapiLog(){
//        ApiUtil.waitForNextRequest();
//        System.out.println("token:"+token);
//        System.out.println("OrgId:"+orgId);
//        Response response = ApiClient.get(
//                "doc.getapilog",
//                orgId,
//                token
//        );
//        response.prettyPrint();
//        return response;
//    }
//
//
//public static Response publicApiDoctoBase(){
//    ApiUtil.waitForNextRequest();
//    System.out.println("token:"+token);
//    System.out.println("OrgId:"+orgId);
//
//    File signatureFile = new File("src/test/resources/dumy.pdf");
//    if (!signatureFile.exists()) {
//        throw new RuntimeException("Signature file not found: " + signatureFile.getAbsolutePath());
//    }
//    Response response = ApiClient.postdoc(
//            "doc.publicapidoctobase",
//            signatureFile,
//            token,
//            orgId
//    );
//    System.out.println("Response Status Code: " + response.getStatusCode());
//    response.prettyPrint();
//     base64 = response.jsonPath().getString("data[0].base64");
//     filename = response.jsonPath().getString("data[0].file_name");
//    return response;
//    }
//
//
//    public static Response signdocumentApiToken(){
//        ApiUtil.waitForNextRequest();
//        System.out.println("token:"+token);
//        System.out.println("OrgId:"+orgId);
//        System.out.println("Api Token :"+apiToken);
//        System.out.println("Base64:"+base64);
//        System.out.println("File Name:"+filename);
//        Map<String, Object> documentData = new HashMap<>();
//        documentData.put("base64", base64);
//        documentData.put("file_name", filename);
//
//        Map<String, Object> position = new HashMap<>();
//        position.put("lowerLeftX", 400);
//        position.put("lowerLeftY", 25);
//        position.put("width", 175);
//        position.put("height", 90);
//
//        Map<String, Object> docProperties = new HashMap<>();
//        docProperties.put("addEncryption", false);
//        docProperties.put("defaultPage", "first");
//        docProperties.put("encryptionPin", "");
//        docProperties.put("lockForSigning", false);
//        docProperties.put("position", position);
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("document_data", Collections.singletonList(documentData));
//        requestBody.put("sign_mode", "pfx");
//        requestBody.put("document_properties", docProperties);
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Authorization", "Bearer " + token);
//        headers.put("Auth", "Bearer " + apiToken);
//        headers.put("organization", orgId);
//
//        Response response = ApiClient.postWithBodyAndHeaders(
//                "doc.signdocumentApiToken",
//                requestBody,
//                headers
//        );
//        response.prettyPrint();
//        return response;
//    }
//
//
//
//
//
//    public static Response publicApiBasetoDoc(){
//        ApiUtil.waitForNextRequest();
//        System.out.println("token:"+token);
//        System.out.println("OrgId:"+orgId);
//
//        Map<String, String> body = new HashMap<>();
//        body.put("base64", base64);
//        body.put("file_name",filename);
//
//        Response response = ApiClient.post(
//                "doc.publicapibasetodoc",
//                body,
//                token,
//                orgId
//        );
//        response.prettyPrint();
//        return response;
//    }
//
//
//    public static Response deleteDigitalSignatureApiKey(){
//        ApiUtil.waitForNextRequest();
//        System.out.println("token:"+token);
//        System.out.println("OrgId:"+orgId);
//
//        if (token == null || token.isEmpty()) {
//            token = getTokenOrLogin();
//        }
//
//        Response response = ApiClient.delete(
//                "doc.deleteDigitalSignatureApiKey",
//                "signatureId",
//                token,
//                orgId
//        );
//        System.out.println("Response Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//
//        return response;
//    }
//
//
//
//}