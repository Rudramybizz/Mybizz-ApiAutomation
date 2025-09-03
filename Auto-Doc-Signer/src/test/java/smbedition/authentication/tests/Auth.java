package smbedition.authentication.tests;

import smbedition.common.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.TestData;
import smbedition.common.TokenManager;
import smbedition.authentication.services.AuthApi;

public class Auth extends BaseTest {


    String NewFirstName = TestData.generateRandomFirstName();
    String NewLastName = TestData.generateRandomLastName();  ;
    String NewPhone = TestData.generateRandomMobile();
    String NewEmail = TestData.generateRandomEmail();
    String cointryId = "IN";
    String dialingCodeId = "IN";



    @Test(priority = 1, description = "Generate OTP for Registration")
    public void testGenerate_Otp_Register() {
        Response response = AuthApi.generateOtpRegister();

        // Validate response
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        JsonPath jsonPath = response.jsonPath();
        String order_id = jsonPath.getString("order_id");
        String message = jsonPath.getString("message");

        Assert.assertNotNull(order_id, "'order_id' is missing");
        Assert.assertTrue(order_id.length() > 10, "'order_id' seems too short");
        Assert.assertNotNull(message, "'message' is missing");

        System.out.println("Generated OTP Token: " + order_id);
    }

    @Test(priority = 2, description = "Register with OTP & order ID")
    public void user_register() {
        AuthApi.generateOtpRegister(); // Ensure OTP is generated
        Response regResponse = AuthApi.Register();
        Assert.assertEquals(regResponse.getStatusCode(), 201, "Register API failed");
    }

    @Test(priority = 3, description = "Generate OTP for Login")
    public void test_GenerateOtp_login() {
        Response response = AuthApi.generateOtpLogin();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP 200 for OTP login");

        JsonPath jsonPath = response.jsonPath();
        String orderId = jsonPath.getString("order_id");
        String message = jsonPath.getString("message");

        Assert.assertNotNull(orderId, "'order_id' missing");
        Assert.assertTrue(orderId.length() > 10, "'order_id' too short");
        Assert.assertNotNull(message, "'message' missing");

        System.out.println("Login OTP Order ID: " + orderId);
    }

    @Test(priority = 4, description = "Login with OTP")
    public void user_Login() {
        AuthApi.generateOtpLogin(); // Generate login OTP
        Response loginResponse = AuthApi.loginWithOtp();

        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login failed");

        JsonPath loginJson = loginResponse.jsonPath();
        String token = loginJson.getString("data.token");
        TokenManager.setToken(token);
        String message = loginJson.getString("message");

        Assert.assertNotNull(token, "Login token missing");
        Assert.assertFalse(token.trim().isEmpty(), "Login token empty");
        Assert.assertNotNull(message, "'message' missing");
        Assert.assertFalse(message.trim().isEmpty(), "'message' empty");

        System.out.println("Login Token: " + token);
    }

    @Test(priority = 5, description = "Forgot Password OTP")
    public void testForgotPasswordOtp() {
        Response response = AuthApi.forgetPassword();
        Assert.assertEquals(response.getStatusCode(), "Forgot Password link should shared to mail address , need to check manually");
    }



    @Test(priority = 7, description = "Get Profile API")
    public void getProfile() {
        Response response = AuthApi.getProfile();
        Assert.assertEquals(response.getStatusCode(), 200, "Get Profile failed");
    }

//    @Test(priority = 8, description = "Get localization settings API")
//    public void getLocalization() {
//        Response response = AuthApi.getLocalisationSettings();
//        Assert.assertEquals(response.getStatusCode(), 200, "Get localization settings failed");
//    }
//
//    @Test(priority = 9, description = "Get privacy and security settings API")
//    public void getPrivacyAndSecurity() {
//        Response response = AuthApi.getPrivacyAndSecuritySettings();
//        Assert.assertEquals(response.getStatusCode(), 200, "Get privacy and security settings failed");
//    }
//
//
//    // =================== Update Profile API ===================
//    //    ==============Update profile with All Fields==============
//    @Test(priority = 10, description = "Update all profile fields successfully")
//    public void updateProfile_AllFields() {
//        Response response = AuthApi.updateProfile(NewFirstName, NewLastName, NewPhone,NewEmail,"IN", "IN");
//        Assert.assertEquals(response.getStatusCode(), 200, "Profile update failed");
//    }
//
//
//    @Test(priority = 11, description = "Update only first name and last name")
//    public void updateProfile_PartialFields() {
//        Response response = AuthApi.updateProfile(NewFirstName, NewLastName, null,null, null, null);
//        Assert.assertEquals(response.getStatusCode(), 400, "Please ensure that all the filled details are correct");
//    }
//
//    @Test(priority = 12, description = "Send empty required fields")
//    public void updateProfile_EmptyRequiredFields() {
//        Response response = AuthApi.updateProfile("", "","", "", "", "");
//        Assert.assertEquals(response.getStatusCode(), 400, "API should return 400 for empty fields");
//    }
//
//    @Test(priority = 13, description = "Send invalid phone number")
//    public void updateProfile_InvalidPhone() {
//        Response response = AuthApi.updateProfile(NewFirstName, NewLastName, "123", NewEmail,"IN", "IN");
//        Assert.assertEquals(response.getStatusCode(), 400, "API should return 400 for invalid phone");
//    }
//
////    =============Update Profile Api localization ===================
//
//    @Test(priority = 14, description = "Update Localization Settings - All valid data")
//    public void updateLocalization_AllFields() {
//        List<String> languages = Arrays.asList("en-US", "te");
//        Response response = AuthApi.updateProfileLocalisation_settings(
//                "Asia/Kolkata",
//                "en-US",
//                languages,
//                "%d/%m/%Y"
//        );
//        Assert.assertEquals(response.getStatusCode(), 200, "Localization update failed");
//    }
//
//    @Test(priority = 15, description = "Update Localization - Missing timezone")
//    public void updateLocalization_MissingTimezone() {
//        List<String> languages = Arrays.asList("en-US", "te");
//        try {
//            AuthApi.updateProfileLocalisation_settings(
//                    null,
//                    "en-US",
//                    languages,
//                    "%d/%m/%Y"
//            );
//            Assert.fail("Expected IllegalArgumentException for missing timezone");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Caught expected exception: " + e.getMessage());
//        }
//    }
//
//    @Test(priority = 16, description = "Update Localization - Empty communication languages")
//    public void updateLocalization_EmptyLanguages() {
//        try {
//            AuthApi.updateProfileLocalisation_settings(
//                    "Asia/Kolkata",
//                    "en-US",
//                    new ArrayList<>(),
//                    "%d/%m/%Y"
//            );
//            Assert.fail("Expected IllegalArgumentException for empty communication_language_list");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Caught expected exception: " + e.getMessage());
//        }
//    }
//
//    @Test(priority = 17, description = "Update Localization - Invalid date format")
//    public void updateLocalization_InvalidDateFormat() {
//        List<String> languages = Arrays.asList("en-US", "te");
//        Response response = AuthApi.updateProfileLocalisation_settings(
//                "Asia/Kolkata",
//                "en-US",
//                languages,
//                "%d/%Y"
//        );
//        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 for invalid date format");
//    }
//
//
//// ===================== Update Profile privacy_and_security_settings =====================
//
//   @Test(priority = 18, description = "Disable Two Factor Authentication")
//    public void updatePrivacy_DisableTwoFactor() {
//        Response response = AuthApi.updateProfilePrivacyAndSecuritySettings(false);
//        Assert.assertEquals(response.getStatusCode(), 200, "Failed to disable two-factor authentication");
//    }
//
//    @Test(priority = 19, description = "Enable Two Factor Authentication")
//    public void updatePrivacy_EnableTwoFactor() {
//        Response response = AuthApi.updateProfilePrivacyAndSecuritySettings(true);
//        Assert.assertEquals(response.getStatusCode(), 200, "Failed to enable two-factor authentication");
//    }
//
//    @Test(priority = 20, description = "Send null value for two-factor")
//    public void updatePrivacy_NullValue() {
//        try {
//            AuthApi.updateProfilePrivacyAndSecuritySettings(null);
//            Assert.fail("API should not accept null value");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Caught expected exception: " + e.getMessage());
//        }
//    }
//
//
//    // ===================== get Sessions =====================
//    @Test(priority = 21, description = "Get all active user sessions")
//    public void getUserSessions_Positive() {
//        Response response = AuthApi.getSessions();
//        Assert.assertEquals(response.getStatusCode(), 200, "Failed to fetch user sessions");
//
//        // Optionally validate the response structure
//        JsonPath json = response.jsonPath();
//        Assert.assertNotNull(json.getList("data"), "Sessions data is missing in response");
//        System.out.println("Total Sessions: " + json.getList("data").size());
//    }
//
//    @Test(priority = 22, description = "Get sessions without login token")
//    public void getUserSessions_NoToken() {
//        // Temporarily clear token
//        String currentToken = null;
//        try {
//            currentToken = AuthApi.getToken(); // If getter exists, else handle via reflection
//            AuthApi.clearToken(); // hypothetical method to clear token
//            AuthApi.getSessions();
//            Assert.fail("Expected IllegalStateException due to missing token");
//        } catch (IllegalStateException e) {
//            System.out.println("Caught expected exception: " + e.getMessage());
//        } finally {
//            // Restore token
//            AuthApi.setToken(currentToken); // hypothetical setter
//        }
//    }
//
//
////    ========= Resend OTP Sucess /Fail=============
//
//   @Test(priority = 23, description = "Resend OTP for existing order ID")
//    public void testResendOtp() {
//        Response response = AuthApi.resendOtp();
//
//        // Basic HTTP validation
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for resend OTP");
//
//        // JSON structure validation
//        JsonPath jsonPath = response.jsonPath();
//        String message = jsonPath.getString("message");
//        Assert.assertNotNull(message, "'message' is missing");
//        Assert.assertFalse(message.trim().isEmpty(), "'message' is empty");
//
//        System.out.println("Resend OTP Response: " + message);
//    }
//
////    ========================== Change Password ============================
//
////    @Test(priority = 24, description = "Change password API")
//    public void changePassword() {
//
//        Response response = AuthApi.changePassword();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for change password OTP");
//        JsonPath jsonPath = response.jsonPath();
//        String message = jsonPath.getString("message");
//        Assert.assertNotNull(message, "'message' is missing");
//        Assert.assertFalse(message.trim().isEmpty(), "'message' is empty");
//
//        System.out.println("Change Password Response: " + message);
//    }
//
//
//
////    =================Session-Logout ========================
//
// //  @Test(priority = 25, description = "Logout from  sessions")
//    public void logoutFromAllSessions() {
//        Response response = AuthApi.sessionLogout();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for logout from all sessions");
//
//        JsonPath jsonPath = response.jsonPath();
//        String message = jsonPath.getString("message");
//        Assert.assertNotNull(message, "'message' is missing");
//        Assert.assertFalse(message.trim().isEmpty(), "'message' is empty");
//
//        System.out.println("Logout Response: " + message);
//    }




}