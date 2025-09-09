package smbedition.organization.services;

import io.restassured.response.Response;
import smbedition.common.EncryptApi;
import smbedition.common.TestData;
import smbedition.common.TokenManager;
import smbedition.common.ApiClient;

import java.util.HashMap;
import java.util.Map;

import static smbedition.common.TokenManager.token;

public class OTPService {

    private static String otpemail = TestData.generateRandomEmail();
    private static String otpmobile = TestData.generateRandomMobile();
    private static String otpfirstName = TestData.generateRandomFirstName();
    private static String otplastName = TestData.generateRandomLastName();
    private static String  roleID = TestData.getRoleId();

    private static String generateOtpOrgId ;

    public static  String getgenerateOtpOrgId() {
        return generateOtpOrgId;
    }
    public static  void  setgenerateOtpOrgId(String id){
        generateOtpOrgId = id;
    }

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


//========================= OTP Services ========================

    public static Response generateOtpAddUser() {
        String orgId = TestData.getOrgId();

        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        //  Build Request Body
        Map<String, Object> body = new HashMap<>();
        body.put("purpose", "add_organization_user");
        body.put("email", EncryptApi.encryptEmailTokenOrgId(otpemail));
        System.out.println(otpemail);
        body.put("first_name",EncryptApi.encryptFirstNameTokenOrgId(otpfirstName));
        System.out.println(otpfirstName);
        body.put("last_name", EncryptApi.encryptLastNameTokenOrgId(otplastName));
        System.out.println(otplastName);
        body.put("phone", EncryptApi.encryptMobileTokenOrgId(otpmobile));
        body.put("country_id", "IN");
        body.put("dialing_code_id", "IN");
        body.put("role", TestData.getRoleId());
        body.put("employee_id", TestData.generateRandomEmpId());
        body.put("designation", TestData.generateDesignation());
        body.put("reporting_manager", null);
        body.put("department", TestData.generateDepartment());
        body.put("access_start_date", TestData.generateAccessStartDate());
        body.put("access_end_date", null);

        Response response = ApiClient.post(
                "org.generateotp_adduser",
                body,
                token,
                orgId
        );
        System.out.println("Get OTP Response: " + response.asPrettyString());
        String otpOrderId = response.jsonPath().getString("orderId");
//        OTPService.setgenerateOtpOrgId();
        TestData.setOtpOrderId(otpOrderId);

        return response;
// add otherizatio token & Organization Id while encrypting .
    }


// Update User  OTP
    public static Response generateOtpUpdateUser(){

        String orgId = TestData.getOrgId();

        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        //  Build Request Body
        Map<String, Object> body = new HashMap<>();
        body.put("purpose", "update_organization_user");
        body.put("email", EncryptApi.encryptEmailTokenOrgId(otpemail));
        System.out.println(otpemail);
        body.put("first_name",EncryptApi.encryptFirstNameTokenOrgId(otpfirstName));
        System.out.println(otpfirstName);
        body.put("last_name", EncryptApi.encryptLastNameTokenOrgId(otplastName));
        System.out.println(otplastName);
        body.put("phone", EncryptApi.encryptMobileTokenOrgId(otpmobile));
        System.out.println(otpmobile);
        body.put("country_id", "IN");
        body.put("dialing_code_id", "IN");
        body.put("role", TestData.getRoleId());
        System.out.println("Role Id is : "+TestData.getOrgId());
        body.put("employee_id", TestData.generateRandomEmpId());
        body.put("designation", TestData.generateDesignation());
        body.put("reporting_manager", null);
        body.put("department", TestData.generateDepartment());
        body.put("access_start_date", TestData.generateAccessStartDate());
        body.put("access_end_date", null);

        Response response = ApiClient.post(
                "org.generateotp_updateuser",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        String otpOrderIdUpdateuser = response.jsonPath().getString("orderId");
        TestData.setOtpOrderIdUpdateUser(otpOrderIdUpdateuser);
        System.out.println("Get OTP Response: " + response.asPrettyString());
        return response;
    }


// Remove User OTP
    public static Response generateOtpRemoveUser(){

        String orgId = TestData.getOrgId();

        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        Map<String, Object> body = new HashMap<>();
        body.put("purpose", "remove_organization_user");
        Response response = ApiClient.post(
                "org.generateotp_removeuser",
                body,
                token,
                orgId
        );
        System.out.println("Get OTP Response: " + response.asPrettyString());

        String otpOrderIdRemoveuser = response.jsonPath().getString("orderId");
        TestData.setOtpOrderIdRemoveUser(otpOrderIdRemoveuser);
        return response;
    }


}