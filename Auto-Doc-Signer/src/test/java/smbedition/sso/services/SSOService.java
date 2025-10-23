//package smbedition.sso.services;
//
//import io.restassured.response.Response;
//import smbedition.common.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SSOService {
//
//    private static String firstName =  TestData.generateRandomFirstName();
//    private static String lastName  =  TestData.generateRandomLastName();
//    private static String email     =  TestData.generateRandomEmail();
//    private static String password  =  TestData.generateRandomPassword();
//    private static String mobile    = TestData.generateRandomMobile();
//
//    private static  String token ;
//    private static  String loginResponse;
//
//    // =================== SSO Registration ===================
//    public static Response registerUser() {
//        ConfigLoader.load();
//        String baseUrl = ConfigLoader.get("base.url");
//        RequestSpecFactory.init(baseUrl);
//        Map<String, Object> body = new HashMap<>();
//        body.put("first_name",firstName);
//        body.put("last_name", lastName);
//        body.put("email", email);
//        body.put("password", password);
//        body.put("dialing_code", "IN");
//        body.put("phone", mobile);
//        System.out.println("=== SSO Register Request ===");
//        System.out.println(body);
//        System.out.println("===========================");
//
//        Response response = ApiClient.ssoPost(
//                "sso.register",
//                                body,
//                "http://10.1.0.10:8181"
//        );
//        System.out.println("=== SSO Register Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("=============================");
//
//        return response;
//    }
//
//
//    // =================== SSO Generate OTP ===============
//    public static Response generateOTP() {
//        ConfigLoader.load();
//        String baseUrl = ConfigLoader.get("base.url");
//        RequestSpecFactory.init(baseUrl);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("email","manpret343@gmail.com");
//        body.put("password", "Indian@123");
//
//        System.out.println(body);
//        Response response = ApiClient.ssoPost(
//                "sso.generateotp",
//                body,
//                "http://10.1.0.10:8181"
//        );
//        System.out.println("=== SSO Generate OTP Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("=============================");
//
//        return response;
//    }
//
//    // =================== SSO Login ===================
//    public static Response UserLogin() {
//        ConfigLoader.load();
//        String baseUrl = ConfigLoader.get("base.url");
//        RequestSpecFactory.init(baseUrl);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("email", "manpret343@gmail.com");
//        body.put("password", "Indian@123");
//        body.put("otp", "667788");   // static OTP
//
//        System.out.println("=== SSO Login Request ===");
//        System.out.println(body);
//
//        Response response = ApiClient.ssoPost(
//                "sso.userlogin",
//                body,
//                "http://10.1.0.10:8181"
//        );
//
//        System.out.println("=== SSO Login Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("=============================");
//
//        String responsecode = response.jsonPath().getString("code");
//
//        // Safely read the error code (may be null)
//        String code = null;
//        try {
//            code = response.jsonPath().getString("code");
//        } catch (Exception ignored) {}
//
//        if ("OTP_INVALIDATED".equalsIgnoreCase(code) || "OTP_EXPIRED".equalsIgnoreCase(code)) {
//
//            System.out.println("Login failed due to OTP issue: " + code + ". Do NOT auto-regenerate OTP here.");
//
//            return response;
//        }
//
//        try {
//            if (response.jsonPath().get("data") != null) {
//                String token = response.jsonPath().getString("data.authorization_token");
//                if (token != null && !token.isEmpty()) {
//                    TokenManager.setToken(token);
//                }
//            }
//        } catch (Exception e) {
//
//        }
//
//        return response;
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//    // =================== SSO Validate Otherization Token ===================
//    public static Response validateOtherizationToken() {
//        ConfigLoader.load();
//        String baseUrl = ConfigLoader.get("base.url");
//        RequestSpecFactory.init(baseUrl);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("authorization_token",token);
//
//        System.out.println(body);
//        Response response = ApiClient.ssoPost(
//                "sso.validateOtherizationToken",
//                body,
//                "http://10.1.0.10:8181"
//        );
//        System.out.println("=== SSO Validate Otherization Token Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("=============================");
//
//        return response;
//    }
//
//
//
//    // =================== SSO Resend OTP ===================
//    public static Response resendOTP() {
//        ConfigLoader.load();
//        String baseUrl = ConfigLoader.get("base.url");
//        RequestSpecFactory.init(baseUrl);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("email","manpret343@gmail.com");
//
//        System.out.println(body);
//        Response response = ApiClient.ssoPost(
//                "sso.resendOTP",
//                body,
//                "http://10.1.0.10:8181"
//        );
//        System.out.println("=== SSO Validate Otherization Token Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("=============================");
//
//        return response;
//    }
//
//
//    // =================== SSO List of sessions ===================
//    public static Response list_Sessions() {
//        ConfigLoader.load();
//        String baseUrl = ConfigLoader.get("base.url");
//        RequestSpecFactory.init(baseUrl);
//
//        Response response = ApiClient.ssoGet(
//                "sso.listSession"
//        );
//        System.out.println("=== SSO Get List of Session Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("=============================");
//
//        return response;
//    }
//
//
//    // =================== SSO Regenerate Authorization Token ===================
//    public static Response list() {
//            ConfigLoader.load();
//            String baseUrl = ConfigLoader.get("base.url");
//            RequestSpecFactory.init(baseUrl);
//
//            Map<String, Object> body = new HashMap<>();
//            body.put("email","manpret343@gmail.com");
//
//            System.out.println(body);
//            Response response = ApiClient.ssoPost(
//                    "sso.resendOTP",
//                    body,
//                    "http://10.1.0.10:8181"
//            );
//            System.out.println("=== SSO Regenerate Authorization token Response ===");
//            System.out.println("Status Code: " + response.getStatusCode());
//            response.prettyPrint();
//            System.out.println("=============================");
//
//            return response;
//        }
//
//
//
//        // =================== SSO Logout ===================
//    public static Response sessionLogout() {
//        ConfigLoader.load();
//        String baseUrl = ConfigLoader.get("base.url");
//        RequestSpecFactory.init(baseUrl);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("auth_token",token);
//
//        System.out.println(body);
//        Response response = ApiClient.ssoPost(
//                "sso.logout",
//                body,
//                "http://10.1.0.10:8181"
//        );
//        System.out.println("=== SSO Logout Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("=============================");
//
//        return response;
//    }
//
//}