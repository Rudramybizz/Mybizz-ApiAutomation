package smbedition.organization.services;

import io.restassured.response.Response;
import smbedition.common.ApiUtil;
import smbedition.common.EncryptApi;
import smbedition.common.TestData;
import smbedition.common.TokenManager;
import smbedition.common.ApiClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static smbedition.common.TokenManager.token;

public class OrgUserService {
    private static String otpEmail = TestData.generateRandomEmail();
    private static String otpFirstName = TestData.generateRandomFirstName();
    private static String otpLastName = TestData.generateRandomLastName();
    private static String otpMobile = TestData.generateRandomMobile();
    private static String orgId = TestData.getOrgId();
    public static String orgUserUpdate;
    private static String userId;


    private static String getTokenOrLogin() {

        if(orgId == null || orgId.isEmpty()){
            System.out.println("OrgId not found....");
//            orgId = TestData.getOrgId();
        }
        System.out.println("organization Id:"+orgId);

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


// ==================== Add User ====================
//    public static Response organizationAddUser(){
//        if (token == null || token.isEmpty()) {
//            token = getTokenOrLogin();
//        }
//        String empId = TestData.generateRandomEmpId();
//        String designation = TestData.generateDesignation();
//        String department = TestData.generateDepartment();
//        String accessStartDate = TestData.generateAccessStartDate();
//        String orderId = TestData.getOtpOrderId();
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("purpose", "add_organization_user");
//        body.put("email", EncryptApi.encryptEmailTokenOrgId(otpEmail));
//        body.put("first_name", EncryptApi.encryptFirstNameTokenOrgId(otpFirstName));
//        body.put("last_name", EncryptApi.encryptLastNameTokenOrgId(otpLastName));
//        body.put("phone", EncryptApi.encryptMobileTokenOrgId(otpMobile));
//        body.put("country_id", "IN");
//        body.put("dialing_code_id", "IN");
//        body.put("role", TestData.getRoleId());
//        body.put("employee_id", empId);
//        body.put("designation", designation);
//        body.put("reporting_manager", null);
//        body.put("department", department);
//        body.put("access_start_date", accessStartDate);
//        body.put("access_end_date", null);
//        body.put("otp", "445566");
//        body.put("order_id", orderId);
//        System.out.println("Order Id"+orderId);
//
//        System.out.println("======= Add User Request Body =======");
//        body.forEach((k, v) -> System.out.println(k + " : " + v));
////        System.out.println("Organization Id: " + orgId);
//        System.out.println("=====================================");
//
////        System.out.println("organization Id:"+orgId);
//        Response response = ApiClient.post(
//                "org.adduser",
//                body,
//                token,
//                orgId
//        );
//
//          response.prettyPrint();
//        return response;
//    }

public static Response organizationAddUser(){
    if (token == null || token.isEmpty()) {
        token = getTokenOrLogin();
    }

    String empId = TestData.generateRandomEmpId();
    String designation = TestData.generateDesignation();
    String department = TestData.generateDepartment();
    String accessStartDate = TestData.generateAccessStartDate();
    String orderId = TestData.getOtpOrderId();
    System.out.println("Order Id for add user:"+orderId);

    Map<String, Object> body = new HashMap<>();
    body.put("purpose", "add_organization_user");
    body.put("email", EncryptApi.encryptEmailTokenOrgId(otpEmail));
    body.put("first_name", EncryptApi.encryptFirstNameTokenOrgId(otpFirstName));
    body.put("last_name", EncryptApi.encryptLastNameTokenOrgId(otpLastName));
    body.put("phone", EncryptApi.encryptMobileTokenOrgId(otpMobile));
    body.put("country_id", "IN");
    body.put("dialing_code_id", "IN");
    body.put("role", TestData.getRoleId());
    body.put("employee_id", empId);
    body.put("designation", designation);
    body.put("reporting_manager", null);
    body.put("department", department);
    body.put("access_start_date", accessStartDate);
    body.put("access_end_date", null);
    body.put("otp","445566");          // dynamic OTP
    body.put("order_id", orderId); // dynamic Order ID
     System.out.println(body);
//    System.out.println("Order Id: " + orderId + ", OTP: " + otp);

    Response response = ApiClient.post(
            "org.adduser",
            body,
            token,
            orgId
    );

    response.prettyPrint();
    return response;
}


    public  static Response getorgUser_dropdown(){
        System.out.println("=====get organization User dropdown API=====");
        ApiUtil.waitForNextRequest();
        System.out.println("Organization Id:"+orgId);
        System.out.println("Auth Token"+token);
        Response response = ApiClient.get("org.getorgUser.dropdown",orgId,token);
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();
        return response;
    }

    public static Response getorgUser_List(){
        System.out.println("=====get organization User List API=====");
        ApiUtil.waitForNextRequest();
        System.out.println("Organization Id:"+orgId);
        System.out.println("Auth Token"+token);
        Response response = ApiClient.get("org.getorgUser.list",orgId,token);
        System.out.println("Response Status Code: " + response.getStatusCode());
        userId = response.jsonPath().getString("data[0].id");
        TestData.setSignatoryId(userId);
        TestData.setUseridForSignatory(response.jsonPath().getString("data[0].user_details.id"));
        response.prettyPrint();
        return response;
    }


    public static Response getorgUser_Single(){

        String orgId = TestData.getOrgId();
        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        Response response = ApiClient.get("org.getorgUser.single", userId, token, orgId);
        logResponse("GetRoleSingle", response);
        return response;
    }


    // ==================== Update User ====================
    public static Response organizationUpdateUser() {
        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        String empId = TestData.generateRandomEmpId();
        String designation = TestData.generateDesignation();
        String department = TestData.generateDepartment();
        String accessStartDate = TestData.generateAccessStartDate();
        String orderId = TestData.getOtpOrderIdUpdateUser();
        String roleId = TestData.getRoleId();
         System.out.println("Role Id:"+roleId);

        Map<String, Object> body = new HashMap<>();
        body.put("purpose", "update_organization_user");
        body.put("email", EncryptApi.encryptEmailTokenOrgId(otpEmail));
        body.put("first_name", EncryptApi.encryptFirstNameTokenOrgId(otpFirstName));
        body.put("last_name", EncryptApi.encryptLastNameTokenOrgId(otpLastName));
        body.put("phone", EncryptApi.encryptMobileTokenOrgId(otpMobile));
        body.put("country_id", "IN");
        body.put("dialing_code_id", "IN");
        body.put("role", TestData.getRoleId());
        body.put("employee_id", empId);
        body.put("designation", designation);
        body.put("reporting_manager", null);
        body.put("department", department);
        body.put("access_start_date", accessStartDate);
        body.put("access_end_date", null);
        body.put("otp","445566");
        body.put("order_id", orderId);

        System.out.println("======= Update User Request Body =======");
        body.forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("Order Id: " + orderId);
        System.out.println("User Id: " + userId);
        System.out.println("========================================");

        Response response = ApiClient.post(
                "org.updateuser",
                userId,
                body,
                token,
                orgId
        );

        response.prettyPrint();
        return response;
    }


    public static Response orgUserRemoveUser(){
        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        Map<String, Object> body = new HashMap<>();
        body.put("instance_ids", Arrays.asList(userId));
        body.put("otp", "445566");
        body.put("order_id", TestData.getOtpOrderIdRemoveUser());

        System.out.println("Remove User Body: " + body);
        Response response = ApiClient.patch(
                "org.removeuser",
                body,
                token,
                orgId
        );

        response.prettyPrint();
        return response;
    }


    public static Response orgUserMarkInactive(){
        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        Map<String, Object> body = new HashMap<>();
        body.put("instance_ids", Arrays.asList(userId));
        System.out.println("Body: " + body);
        Response response = ApiClient.patch(
                "org.markasinactive",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }



    public static Response orgUserMarkActive(){
        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        Map<String, Object> body = new HashMap<>();
        body.put("instance_ids", Arrays.asList(userId));
        System.out.println("Body: " + body);
        Response response = ApiClient.patch(
                "org.markasactive",
                body,
                token,
                orgId
        );

        response.prettyPrint();
        return response;
    }

}