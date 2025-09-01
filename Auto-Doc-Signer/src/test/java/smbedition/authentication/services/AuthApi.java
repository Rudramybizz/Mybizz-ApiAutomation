package smbedition.authentication.services;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import smbedition.common.ApiUtil;
import smbedition.common.TokenManager;
import smbedition.authentication.util.ApiClient;
import smbedition.common.EncryptApi;
import smbedition.common.TestData;

import java.util.List;

import static io.restassured.RestAssured.given;

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

    // ===================== Helper Logging =====================
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

    // ===================== Registration APIs =====================
    public static Response generateOtpRegister() {
        email = TestData.generateRandomEmail();
        mobile = TestData.generateRandomMobile();
        password = TestData.generateRandomPassword();
        firstName = TestData.generateRandomFirstName();
        lastName = TestData.generateRandomLastName();

        String body = "{\n" +
                "  \"email\": \"" + EncryptApi.encryptEmail(email) + "\",\n" +
                "  \"password\": \"" + EncryptApi.encryptPassword(password) + "\",\n" +
                "  \"first_name\": \"" + EncryptApi.encryptFirstName(firstName) + "\",\n" +
                "  \"last_name\": \"" + EncryptApi.encryptLastName(lastName) + "\",\n" +
                "  \"phone\": \"" + EncryptApi.encryptMobile(mobile) + "\",\n" +
                "  \"country_id\": \"IN\",\n" +
                "  \"dialing_code_id\": \"IN\"\n" +
                "}";

        logRequest("Generate OTP Register", body);

        Response response = ApiClient.post("generate.otp.register", body);

        orderId = response.jsonPath().getString("order_id");

        logResponse("Generate OTP Register", response);
        return response;
    }



    public static Response Register() {
        if (orderId == null) throw new IllegalStateException("Order ID is not set. Run generateOtpRegister() first.");

        String body = "{\n" +
                "  \"email\": \"" + EncryptApi.encryptEmail(email) + "\",\n" +
                "  \"password\": \"" + EncryptApi.encryptPassword(password) + "\",\n" +
                "  \"first_name\": \"" + EncryptApi.encryptFirstName(firstName) + "\",\n" +
                "  \"last_name\": \"" + EncryptApi.encryptLastName(lastName) + "\",\n" +
                "  \"phone\": \"" + EncryptApi.encryptMobile(mobile) + "\",\n" +
                "  \"country_id\": \"IN\",\n" +
                "  \"dialing_code_id\": \"IN\",\n" +
                "  \"order_id\": \"" + orderId + "\",\n" +
                "  \"otp\": \"445566\"\n" +
                "}";

        logRequest("Register", body);
        Response response = ApiClient.post("register", body);
        logResponse("Register", response);
        return response;
    }

    // ===================== Login APIs =====================
    public static Response generateOtpLogin() {
        if (email == null || password == null)
            throw new IllegalStateException("User email/password not set. Register first.");

        String body = "{\n" +
                "  \"email\": \"" + EncryptApi.encryptEmail(email) + "\",\n" +
                "  \"password\": \"" + EncryptApi.encryptPassword(password) + "\"\n" +
                "}";
        logRequest("Generate OTP Login", body);

        Response response = ApiClient.post("generate.otp.login", body);
        loginOrderId = response.jsonPath().getString("order_id");

        logResponse("Generate OTP Login", response);
        return response;
    }


    public static Response loginWithOtp() {
        System.out.println(email);
        System.out.println(password);
        if (loginOrderId == null) throw new IllegalStateException("Login Order ID is not set. Run generateOtpLogin() first.");
        loginPasswordPlain = password;
        String body = "{\n" +
                "  \"email\": \"" + EncryptApi.encryptEmail(email) + "\",\n" +
                "  \"password\": \"" + EncryptApi.encryptPassword(password) + "\",\n" +
                "  \"order_id\": \"" + loginOrderId + "\",\n" +
                "  \"otp\": \"445566\"\n" +
                "}";

        logRequest("Login with OTP", body);
        Response response = ApiClient.post("login", body);
        token = response.jsonPath().getString("data.token");
         TokenManager.setToken(token);
        logResponse("Login with OTP", response);
        return response;
    }






    // ===================== Forgot Password APIs =====================

//    public static Response forgotPasswordOtp() {
//        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email must not be null or empty");
//
//        String body = "{ \"email\": \"" + EncryptApi.encryptEmail(email) + "\" }";
//        logRequest("Forgot Password OTP", body);
//
//        Response response = ApiClient.post("forgot.password.otp", body);
//        logResponse("Forgot Password OTP", response);
//        return response;
//    }


    public static Response forgetPassword() {
        if (token == null || token.isEmpty()) throw new IllegalArgumentException(" auth token is required");

        String body = "{ \"token\": \"" + token + "\" }";
        logRequest("Forget Password", body);

        Response response = ApiClient.post("forgetpassword", body);
        logResponse("Forget Password", response);
        return response;
    }




    // ===================== Profile APIs =====================
    public static Response getProfile() {
        if (token == null || token.trim().isEmpty()) throw new IllegalStateException("User must login first to get token.");

        Response response = ApiClient.get("getprofile.profile", token);
        logResponse("Get Profile", response);
        return response;
    }

    public static Response getLocalisationSettings() {
        if (token == null || token.trim().isEmpty()) throw new IllegalStateException("User must login first to get token--localization.");

        Response response = ApiClient.get("getprofile.localization", token);
        logResponse("Localisation Settings", response);
        return response;
    }


    public static Response getPrivacyAndSecuritySettings() {
        if (token == null || token.trim().isEmpty()) throw new IllegalStateException("User must login first to get token--privacy and security.");

        Response response = ApiClient.get("getprofile.privacy", token);
        logResponse("Privacy & Security Settings", response);
        return response;
    }

//    ====================== get Session =====================


    public static Response getSessions() {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalStateException("User must login first to get token.");
        }
        System.out.println("===== Get Sessions API =====");

        Response response = ApiClient.get("get.listofsessions", token);

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }



    // ===================== Update Profile =====================
    // ===================== Update Profile =====================
    public static Response updateProfile(
            String firstName,
            String lastName,
            String phone,
            String email,
            String countryId,
            String dialingCodeId
    ) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalStateException("User must login first to get token.");
        }

        // Fetch current profile to fill missing fields
        Response currentProfileResp = getProfile();
        JsonPath currentJson = currentProfileResp.jsonPath();

        String updatedFirstName   = firstName     != null ? firstName     : currentJson.getString("data.first_name");
        String updatedLastName    = lastName      != null ? lastName      : currentJson.getString("data.last_name");
        String updatedPhone       = phone         != null ? phone         : currentJson.getString("data.phone");
        String updatedCountry     = countryId     != null ? countryId     : currentJson.getString("data.country_id");
        String updatedDialingCode = dialingCodeId != null ? dialingCodeId : currentJson.getString("data.dialing_code_id");
        String updateEmail        = email         != null ? email         : currentJson.getString("data.email");

        // Log before encryption
        System.out.println("===== Update Profile Data (Before Encryption) =====");
        System.out.println("First Name: " + updatedFirstName);
        System.out.println("Last Name: " + updatedLastName);
        System.out.println("Phone: " + updatedPhone);
        System.out.println("Email: " + updateEmail);
        System.out.println("Country: " + updatedCountry);
        System.out.println("Dialing Code: " + updatedDialingCode);
        System.out.println("================================");

        // 🔐 Encrypt only if not null/empty
        String encryptedFirstName = (updatedFirstName != null && !updatedFirstName.isEmpty())
                ? EncryptApi.encryptFirstNameToken(updatedFirstName)
                : "";

        String encryptedLastName = (updatedLastName != null && !updatedLastName.isEmpty())
                ? EncryptApi.encryptLastNameToken(updatedLastName)
                : "";

        String encryptedPhone = (updatedPhone != null && !updatedPhone.isEmpty())
                ? EncryptApi.encryptMobileToken(updatedPhone)
                : "";

        String encryptedEmail = (updateEmail != null && !updateEmail.isEmpty())
                ? EncryptApi.encryptEmailToken(updateEmail)
                : "";

        System.out.println("===== Encrypted Values (Debug) =====");
        System.out.println("Encrypted First Name: " + encryptedFirstName);
        System.out.println("Encrypted Last Name: " + encryptedLastName);
        System.out.println("Encrypted Phone: " + encryptedPhone);
        System.out.println("Encrypted Email: " + encryptedEmail);
        System.out.println("====================================");

        // Build request body using StringBuilder
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("{\n")
                .append("  \"first_name\": \"").append(encryptedFirstName).append("\",\n")
                .append("  \"last_name\": \"").append(encryptedLastName).append("\",\n")
                .append("  \"phone\": \"").append(encryptedPhone).append("\",\n")
                .append("  \"email\": \"").append(encryptedEmail).append("\",\n")
                .append("  \"country_id\": \"").append(updatedCountry).append("\",\n")
                .append("  \"dialing_code_id\": \"").append(updatedDialingCode).append("\"\n")
                .append("}");

        String body = bodyBuilder.toString();

        // Log encrypted payload
        logRequest("Update Profile (Encrypted)", body);

        // Send PATCH request
        Response response = ApiClient.patch("updateprofile.profile", body, token);

        logResponse("Update Profile", response);
        return response;
    }






// ===================== Update Profile localisation_settings =====================

    public static Response updateProfileLocalisation_settings(
                String timezoneId,
                String languageId,
                List<String> communicationLanguages,
                String dateFormat
) {
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalStateException("User must login first to get token.");
            }

            // Validate required fields
            if (timezoneId == null || timezoneId.trim().isEmpty()) {
                throw new IllegalArgumentException("timezone_id must not be null or empty");
            }
            if (languageId == null || languageId.trim().isEmpty()) {
                throw new IllegalArgumentException("language_id must not be null or empty");
            }
            if (communicationLanguages == null || communicationLanguages.isEmpty()) {
                throw new IllegalArgumentException("communication_language_list must not be null or empty");
            }
            if (dateFormat == null || dateFormat.trim().isEmpty()) {
                throw new IllegalArgumentException("date_format must not be null or empty");
            }

            // Log input values
            System.out.println("===== Update Profile Localization Settings =====");
            System.out.println("Timezone ID: " + timezoneId);
            System.out.println("Language ID: " + languageId);
            System.out.println("Communication Languages: " + communicationLanguages);
            System.out.println("Date Format: " + dateFormat);
            System.out.println("=======================================");

            // Build JSON body
            String body = "{\n" +
                    "  \"timezone_id\": \"" + timezoneId + "\",\n" +
                    "  \"language_id\": \"" + languageId + "\",\n" +
                    "  \"communication_language_list\": " + communicationLanguages.toString() + ",\n" +
                    "  \"date_format\": \"" + dateFormat + "\"\n" +
                    "}";

            System.out.println("Request Body:\n" + body);

            // Send PATCH request
            Response response = ApiClient.patch("updateprofile.localization", body, token);

            // Log response
            System.out.println("Response Status Code: " + response.getStatusCode());
            response.prettyPrint();

            return response;
        }


    // ===================== Update Profile privacy_and_security_settings =====================

    public static Response updateProfilePrivacyAndSecuritySettings(Boolean enableTwoFactor) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalStateException("User must login first to get token.");
        }

        if (enableTwoFactor == null) {
            throw new IllegalArgumentException("enable_two_factor_authentication must not be null");
        }

        // Log input
        System.out.println("===== Update Privacy and Security Settings =====");
        System.out.println("Enable Two Factor Authentication: " + enableTwoFactor);
        System.out.println("===============================================");

        String body = "{\n" +
                "  \"enable_two_factor_authentication\": " + enableTwoFactor + "\n" +
                "}";

        System.out.println("Request Body:\n" + body);

        Response response = ApiClient.patch("updateprofile.privacy", body, token);

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }


    // ===================== Token Access Helpers =====================
    public static String getToken() {
        return token;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static void clearToken() {
        token = null;
    }


//   ====================== Resend OTP  FAIL/PASS=========================

    public static Response resendOtp() {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalStateException("Login Order ID is not set. Run generateOtpLogin() first.");
        }
        ApiUtil.waitForNextRequest();
        String endpoint = "resendOTP" ;


        logRequest("Resend OTP", "Endpoint: " + endpoint);
//                   resendOTP
        Response response = ApiClient.post(endpoint, null);
        logResponse("Resend OTP", response);

        try {
            String success = response.jsonPath().getString("success");
            String message = response.jsonPath().getString("message");
            if ("1".equals(success)) {
                System.out.println("OTP triggered successfully for Order ID: " + orderId);
            } else {
                System.out.println("OTP failed for Order ID: " + orderId + " | Message: " + message);
            }
        } catch (Exception e) {
            System.out.println("Could not parse OTP trigger status from response.");
        }

        return response;
    }






//======================change-password=======================

public static Response changePassword() {
//     final String PASSWORD_PATTERN = "^[A-Z][a-zA-Z]*\\s@[0-9]{3,}$";

    if (token == null || token.isEmpty()) {
        throw new IllegalStateException("User must login first to get token.");
    }
    if (loginPasswordPlain == null || loginPasswordPlain.isEmpty()) {
        throw new IllegalStateException("Old password not available. Run login first.");
    }


    System.out.println("old password"+loginPasswordPlain);
    String newPasswordPlain = TestData.generateRandomPassword();
    System.out.println("Generated new password (plain): " + newPasswordPlain);

    String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
    if (!newPasswordPlain.matches(passwordPattern)) {
        throw new IllegalArgumentException("Generated password does not meet security requirements: " + newPasswordPlain);
    }

        String oldPasswordEncrypted = EncryptApi.encryptPasswordToken(loginPasswordPlain);
        String newPasswordEncrypted = EncryptApi.encryptPasswordToken(newPasswordPlain);

        String body = "{\n" +
                "  \"old_password\": \"" + oldPasswordEncrypted + "\",\n" +
                "  \"new_password\": \"" + newPasswordEncrypted + "\"\n" +
                "}";

        logRequest("Change Password", body);

        Response response = ApiClient.post("changepassword", body, token);

        logResponse("Change Password", response);

        return response;
    }



    //======================Session Logout =======================
    public static Response sessionLogout() {
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("User must login first to logout the session.");
        }

        logRequest("Session Logout", "No body. Token: " + token);

        Response response = ApiClient.post("logout.session", null, token);

        logResponse("Session Logout", response);

        if (response.getStatusCode() == 200) {
            System.out.println("Session logout successful.");
        } else {
            System.err.println("Failed to logout session. Status: " + response.getStatusCode());
        }

        return response;
    }


}