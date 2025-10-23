package smbedition.common;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import smbedition.common.tokenmanagers.CookieManager;
import smbedition.common.tokenmanagers.TokenManager;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;


public class EncryptApi {
  private static String orgId = TestData.getOrgId();
    public static final String ORG_HEADER = "organization";
    public static final String sso_enableencryption = "ENABLE-ENCRYPTION";
    public static final String sso_enabledecryption = "ENABLE-DECRYPTION";
    public static final String sso_status = "no";

    private static Cookies getCurrentCookies() {
        return CookieManager.getCookies();
    }
    // Method to get auth_token cookie
    public static Cookie cookieAuthtoken() {
        return CookieManager.cookieAuthtoken();
    }

        private static String encryptField (String fieldName, String fieldValue){
            Map<String, String> payload = new HashMap<>();
            payload.put(fieldName, fieldValue);
            Cookies cookies = getCurrentCookies();
            Cookie cookieauthtoken = cookieAuthtoken();
            Response response = given()

                    .header(sso_enableencryption, sso_status)
                    .header(sso_enabledecryption, sso_status)
                    .cookies(cookies)
                    .body(payload)
                    .when()
                    .post("http://10.1.0.10:8282/api/v1/encrypt")
                    .then()
                    .log().ifValidationFails()
                    .extract()
                    .response();

//            System.out.println("Encryption response status code: " + response.getStatusCode());
//            System.out.println("Encryption response body: " + response.asString());

            if (response.statusCode() != 200) {
//                System.out.println("Encryption API failed for field " + fieldName + " with status code " + response.statusCode());
//                System.out.println("Response body: " + response.asString());
                throw new RuntimeException("Encryption API failed for field " + fieldName +
                        " with status code " + response.statusCode());
            }

            String encryptedValue = response.jsonPath().getString(fieldName);
            if (encryptedValue == null || encryptedValue.isEmpty()) {
                throw new RuntimeException("Encrypted value for " + fieldName + " is null or empty");
            }

//            System.out.println("Encrypted " + fieldName + ": " + encryptedValue);
            return encryptedValue;
        }

        public static String encryptEmail (String email){
            return encryptField("data", email);
        }

        public static String encryptMobile (String mobile){
            return encryptField("data", mobile);
        }

        public static String encryptPassword (String password){
            return encryptField("data", password);
        }

        public static String encryptFirstName (String name){
            return encryptField("data", name);
        }

        public static String encryptLastName (String name){
            return encryptField("data", name);
        }


//    Encryption with bearer
//        public static String encryptFieldWithToken (String fieldName, String fieldValue){
//            Map<String, String> payload = new HashMap<>();
//            payload.put(fieldName, fieldValue);
//
//            String token = TokenManager.get();
//
//            if (token == null || token.isBlank()) {
//                throw new IllegalStateException("Auth token is required but was not found in EncryptApi class.");
//            }
////            System.out.println("token used in encypt: " + token);
////            System.out.println("Sending encryption request for field: " + fieldName + " (with Bearer token)");
////            System.out.println("Request JSON: " + payload);
//
//            Response response = given()
//                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer " + token)
//                    .body(payload)
//                    .log().all() // log request details
//                    .when()
//                    .post("http://10.1.0.10:8282/org/api/v1/encrypt") // or /org/api/v1/encrypt
//                    .then()
//                    .log().all() // log response details
//                    .extract()
//                    .response();
//
////            System.out.println("Encryption response status code: " + response.getStatusCode());
////            System.out.println("Encryption response body: " + response.asString());
//
//            if (response.statusCode() != 200) {
//                throw new RuntimeException("Encryption API failed for field " + fieldName +
//                        " with status code " + response.statusCode());
//            }
//            String encryptedValue = response.jsonPath().getString(fieldName);
//            if (encryptedValue == null || encryptedValue.isEmpty()) {
//                throw new RuntimeException("Encrypted value for " + fieldName + " is null or empty");
//            }
//
////            System.out.println("Encrypted " + fieldName + ": " + encryptedValue);
//            return encryptedValue;
//        }

//        public static String encryptEmailToken (String email){
//            return encryptFieldWithToken("data", email);
//        }
//
//        public static String encryptMobileToken (String mobile){
//            return encryptFieldWithToken("data", mobile);
//        }
//
//        public static String encryptPasswordToken (String password){
//            return encryptFieldWithToken("data", password);
//        }
//
//        public static String encryptFirstNameToken (String name){
//            return encryptFieldWithToken("data", name);
//        }
//
//        public static String encryptLastNameToken (String name){
//            return encryptFieldWithToken("data", name);
//        }




//    Encrypt field with authorization token & organization ID
        public static String encryptFieldWithTokenOrgId (String fieldName, String fieldValue){
            Map<String, String> payload = new HashMap<>();
            payload.put(fieldName, fieldValue);
            String token = TokenManager.get();

            if (token == null || token.isBlank()) {
                throw new IllegalStateException("Auth token is required but was not found in EncryptApi class.");
            }

            if (orgId == null || orgId.isBlank()) {
                System.out.println("Organization ID not found, fetching from OrgServices...");
//                OrgServices.get_Org_List(); // This should internally call TestData.setOrgId(...)
                orgId = TestData.getOrgId(); // refresh after fetching
            }
//        System.out.println("token used in encypt: "+token);
//        System.out.println("Sending encryption request for field: " + fieldName + " (with Bearer token)");
//        System.out.println("Request JSON: " + payload);

            Response response = given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)

                    .header("organization", orgId)
                    .body(payload)
                    .log().all() // log request details
                    .when()
                    .post("http://10.1.0.10:8282/org/api/v1/encrypt") // or /org/api/v1/encrypt
                    .then()
                    .log().all() // log response details
                    .extract()
                    .response();

//            System.out.println("orgId used in encrypt: " + orgId);

//        System.out.println("Encryption response status code: " + response.getStatusCode());
//        System.out.println("Encryption response body: " + response.asString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Encryption API failed for field " + fieldName +
                        " with status code " + response.statusCode());
            }
            String encryptedValue = response.jsonPath().getString(fieldName);
            if (encryptedValue == null || encryptedValue.isEmpty()) {
                throw new RuntimeException("Encrypted value for " + fieldName + " is null or empty");
            }

//            System.out.println("Encrypted " + fieldName + ": " + encryptedValue);
            return encryptedValue;
        }
        public static String encryptEmailTokenOrgId (String email){
            return encryptFieldWithTokenOrgId("data", email);   // fixed
        }

        public static String encryptMobileTokenOrgId (String mobile){
            return encryptFieldWithTokenOrgId("data", mobile);
        }

        public static String encryptPasswordTokenOrgId (String password){
            return encryptFieldWithTokenOrgId("data", password);
        }

        public static String encryptFirstNameTokenOrgId (String name){
            return encryptFieldWithTokenOrgId("data", name);
        }

        public static String encryptLastNameTokenOrgId (String name){
            return encryptFieldWithTokenOrgId("data", name);
        }




//        Decrypt por API Token
    public static String decryptField(String encryptedValue) {
        // Build details map
        Map<String, Object> details = new HashMap<>();
        details.put("user-agent", "PostmanRuntime/7.46.0");
        details.put("login-token", TokenManager.get());
        details.put("organization-id", TestData.getOrgId());
        details.put("organization-name", TestData.getOrgName());

        // Build main payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("data", encryptedValue);
        payload.put("details", details);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .when()
                .post("http://10.1.0.10:8282/api/v1/decrypt")
                .then()
                .log().all()
                .extract()
                .response();

        if (response.statusCode() != 200) {
            throw new RuntimeException("Decryption API failed with status code " + response.statusCode());
        }

        String decryptedValue = response.jsonPath().getString("data");
        if (decryptedValue == null || decryptedValue.isEmpty()) {
            throw new RuntimeException("Decrypted value is null or empty");
        }
        response.prettyPrint();
        return decryptedValue;
    }


}