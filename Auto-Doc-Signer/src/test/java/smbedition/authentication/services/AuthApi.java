package smbedition.authentication.services;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import smbedition.common.ApiClient;
import smbedition.common.TestData;
import smbedition.common.logging.Log;
import smbedition.common.tokenmanagers.CookieManager;
import smbedition.common.tokenmanagers.TokenManager;
import smbedition.common.waits.ApiUtil;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class AuthApi {

    private static String email;
    private static String mobile;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static String orderId;
    private static String loginOrderId;
    private static String token;
    private static String loginPasswordPlain;
   private static  String redirectUri = "http://10.1.0.10:8181";

    // ===================== Registration APIs =====================
    public static Response ssoRegister(Map<String, Object> registrationData) {


        System.out.println("=== SSO Registration Request ===");
        System.out.println("Registration Data: " + registrationData);

        Response response = ApiClient.ssoPost("sso.register", registrationData, redirectUri);

        System.out.println("=== SSO Registration Response ===");
        System.out.println("Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }

    // Method for valid registration (using TestData from common package)
//    public static Response ssoRegisterValid() {
//        Map<String, Object> validData = createValidRegistrationData();
//        return ssoRegister(validData);
//    }


    // ===================== Generate OTP API =====================
    public static Response generateOtpLogin(String email, String password) {

        String completeuri= redirectUri+"/login";
        String redirectUri = completeuri;
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        System.out.println("=== Generate OTP Login Request ===");
        System.out.println("Email: " + email);
        System.out.println("Redirect URI: " + redirectUri);

        Response response = ApiClient.ssoPost("sso.generate.otp.login", body, redirectUri);

        System.out.println("=== Generate OTP Response ===");
        System.out.println("Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }

    // ===================== Login with OTP API =====================
    public static Response loginWithOtp(String email, String password, String otp) {

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        body.put("otp", otp);

        System.out.println("=== Login with OTP Request ===");
        System.out.println("Email: " + email);
        System.out.println("OTP: " + otp);
        System.out.println("Redirect URI: " + redirectUri);

        Response response = ApiClient.ssoPost("sso.login", body, redirectUri);

        System.out.println("=== Login Response ===");
        System.out.println("Status Code: " + response.getStatusCode());
        response.prettyPrint();

        // If login successful, store token and cookies
        if (response.getStatusCode() == 308) {
            String authToken = response.jsonPath().getString("data.authorization_token");
            if (authToken != null && !authToken.isEmpty()) {
                TokenManager.setToken(authToken);
                System.out.println("✅ Login Successful - Token Stored: " + authToken);
            }

            System.out.println("✅ Cookies Stored");
        }

        return response;
    }

    // ===================== Complete Login Flow =====================
    public static Response completeLoginFlow(String email, String password, String otp) {
        System.out.println("🚀 Starting Complete Login Flow");

        // Step 1: Generate OTP
        Response otpResponse = generateOtpLogin(email, password);
        if (otpResponse.getStatusCode() != 200) {
            System.out.println("❌ OTP Generation Failed");
            return otpResponse;
        }

        // Step 2: Login with OTP
        Response loginResponse = loginWithOtp(email, password, otp);
        return loginResponse;
    }

    // ===================== Token Exchange API =====================
    public static Response exchangeToken() {
        String authToken = TokenManager.get();
        Log.info("authorization_token: " + authToken);

        if (authToken == null || authToken.isEmpty()) {
            throw new IllegalStateException("No authorization token available. Please login first.");
        }

        Map<String, Object> body = new HashMap<>();
        body.put("authorization_token", authToken);

        System.out.println("=== Token Exchange Request ===");
        System.out.println("Authorization Token: " + authToken);

        Response response = ApiClient.ssoPost("doc.tokenexchange", body);

        System.out.println("=== Token Exchange Response ===");
        System.out.println("Status Code: " + response.getStatusCode());
        response.prettyPrint();

        // Capture cookies from response and store in CookieManager
        if (response.getStatusCode() == 200) {
            Cookies detailedCookies = response.getDetailedCookies();

            detailedCookies.asList().forEach(cookie -> {
                if (cookie.getExpiryDate() != null) {
                    ZonedDateTime utcTime = cookie.getExpiryDate()
                            .toInstant()
                            .atZone(ZoneOffset.UTC);
                    String formatted = utcTime.format(DateTimeFormatter.RFC_1123_DATE_TIME);
                    System.out.println("Converted Expiry for " + cookie.getName() + ": " + formatted);
                }
            });

            CookieManager.setCookies(response.getDetailedCookies());
            System.out.println("✅ Cookies stored from token exchange");
          Log.info("stored cookie: "+CookieManager.getCookies());
            // Log the cookies for debugging
            System.out.println("=== Stored Cookies ===");
            CookieManager.getCookies().forEach(cookie -> {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            });
        }


        return response;
    }


    // ===================== Get Profile API =====================
public static Response getProfile() {
    String token = TokenManager.get();

    if (token == null || token.isEmpty()) {
        throw new IllegalStateException("No authorization token available. Please login first.");
    }

    System.out.println("=== Get Profile Request ===");
    System.out.println("Using Token: " + token);

    Response response = ApiClient.get("doc.getprofile");

    System.out.println("=== Get Profile Response ===");
    System.out.println("Status Code: " + response.getStatusCode());
    response.prettyPrint();

    return response;
}


// ============ Helper method =================
    public static  void generateCookies_Login(){
        String email = TestData.generateRandomEmail();
        String phone = TestData.generateRandomMobile();
        String firstname = TestData.generateRandomFirstName();
        String lastname = TestData.generateRandomLastName();
        String password = TestData.generateRandomPassword();
        String dialing_code= "IN";

            Map<String, Object> body = new HashMap<>();
            body.put("first_name", firstname);
            body.put("last_name", lastname);
            body.put("email", email);
            body.put("password",password);
            body.put("dialing_code", dialing_code);
            body.put("phone",phone);
            body.put("country_id", dialing_code);
         AuthApi authApi = new AuthApi();
            authApi.ssoRegister(body);

//            Login
        ApiUtil.waitForNextRequest();
             generateOtpLogin(email,password);
             ApiUtil.waitForNextRequest();
             loginWithOtp(email,password,"667788");
             exchangeToken();
    }



}