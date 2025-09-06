package smbedition.common;

import io.restassured.response.Response;
import smbedition.organization.services.OrgServices;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static smbedition.common.TestData.getOrgId;

public class EncryptApi {
  private static String orgId = TestData.getOrgId();

        private static String encryptField (String fieldName, String fieldValue){
            Map<String, String> payload = new HashMap<>();
            payload.put(fieldName, fieldValue);

//            System.out.println("Sending encryption request for field: " + fieldName);
//            System.out.println("Request JSON: " + payload);

            Response response = given()
                    .header("Content-Type", "application/json")
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
        public static String encryptFieldWithToken (String fieldName, String fieldValue){
            Map<String, String> payload = new HashMap<>();
            payload.put(fieldName, fieldValue);

            String token = TokenManager.get();

            if (token == null || token.isBlank()) {
                throw new IllegalStateException("Auth token is required but was not found in EncryptApi class.");
            }
//            System.out.println("token used in encypt: " + token);
//            System.out.println("Sending encryption request for field: " + fieldName + " (with Bearer token)");
//            System.out.println("Request JSON: " + payload);

            Response response = given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(payload)
                    .log().all() // log request details
                    .when()
                    .post("http://10.1.0.10:8282/org/api/v1/encrypt") // or /org/api/v1/encrypt
                    .then()
                    .log().all() // log response details
                    .extract()
                    .response();

//            System.out.println("Encryption response status code: " + response.getStatusCode());
//            System.out.println("Encryption response body: " + response.asString());

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

        public static String encryptEmailToken (String email){
            return encryptFieldWithToken("data", email);
        }

        public static String encryptMobileToken (String mobile){
            return encryptFieldWithToken("data", mobile);
        }

        public static String encryptPasswordToken (String password){
            return encryptFieldWithToken("data", password);
        }

        public static String encryptFirstNameToken (String name){
            return encryptFieldWithToken("data", name);
        }

        public static String encryptLastNameToken (String name){
            return encryptFieldWithToken("data", name);
        }




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
                OrgServices.get_Org_List(); // This should internally call TestData.setOrgId(...)
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




}