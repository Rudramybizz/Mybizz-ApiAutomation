package smbedition.authentication.tests;

import io.qameta.allure.*;
import smbedition.common.ApiUtil;
import smbedition.common.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.TestData;
import smbedition.common.TokenManager;
import smbedition.authentication.services.AuthApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Epic("Authentication APIs")
@Feature("User Onboarding  & Security")
public class Auth extends BaseTest {


    String NewFirstName = TestData.generateRandomFirstName();
    String NewLastName = TestData.generateRandomLastName();  ;
    String NewPhone = TestData.generateRandomMobile();
    String NewEmail = TestData.generateRandomEmail();
    String cointryId = "IN";
    String dialingCodeId = "IN";



    @Test(priority = 1)
    @Story("User Registration Generate OTP")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Generate OTP for Registration")
    public void testGenerate_Otp_Register() {
        Response response = AuthApi.generateOtpRegister(); Allure.step("Calling Generate OTP API for registration");
//        Response response = AuthApi.generateOtpRegister();

        Allure.addAttachment("Response", response.getBody().asPrettyString());

        Allure.step("Validating status code");
        Assert.assertEquals(response.getStatusCode(), 200);

        String order_id = response.jsonPath().getString("order_id");
        String message = response.jsonPath().getString("message");

        Allure.step("Asserting order_id and message are not null");
        Assert.assertNotNull(order_id);
        Assert.assertNotNull(message);

        Allure.step("OTP Generated: " + order_id);
    }


    @Test(priority = 2)
    @Story("User Registration With OTP")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Register with OTP & order ID")
    public void user_register() {
        Allure.step("Ensure OTP is generated first");
        AuthApi.generateOtpRegister(); // Ensure OTP is generated

        Allure.step("Calling Register API");
        Response regResponse = AuthApi.Register();
        Allure.addAttachment("Register Response", regResponse.getBody().asPrettyString());

        Assert.assertEquals(regResponse.getStatusCode(), 201, "Register API failed");
    }


    @Test(priority = 3)
    @Story("User Login Generate OTP")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Generate OTP for user login.")
    public void test_GenerateOtp_login() {
        Response response = AuthApi.generateOtpLogin();
        Allure.addAttachment("Login OTP Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Expected HTTP 200 for OTP login");

        JsonPath jsonPath = response.jsonPath();
        String orderId = jsonPath.getString("order_id");
        String message = jsonPath.getString("message");

        Assert.assertNotNull(orderId, "'order_id' missing");
        Assert.assertTrue(orderId.length() > 10, "'order_id' too short");
        Assert.assertNotNull(message, "'message' missing");

        System.out.println("Login OTP Order ID: " + orderId);
    }


    @Test(priority = 4)
    @Story("User Login With OTP")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Login with OTP.")
    public void user_Login() {
        Allure.step("Generate OTP for login");
        AuthApi.generateOtpLogin();

        Allure.step("Call Login with OTP");
        Response loginResponse = AuthApi.loginWithOtp();
        Allure.addAttachment("Login Response", loginResponse.getBody().asPrettyString());
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

    @Test(priority = 5)
    @Story("Forgot Password OTP")
    @Severity(SeverityLevel.NORMAL)
    @Description("Forgot Password OTP")
    public void testForgotPasswordOtp() {
        Response response = AuthApi.forgetPassword();
        Allure.addAttachment("Forgot Password Response", response.getBody().asPrettyString());
        Allure.step("Manual verification required: email OTP link sent.");
        System.out.println( "Forgot Password link should shared to mail address , need to check manually");
    }


    @Test(priority = 7)
    @Story("Get User Profile")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get Profile API")
    public void getProfile() {
        Response response = AuthApi.getProfile();
        Allure.addAttachment("Profile Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Get Profile failed");
    }


    @Test(priority = 8)
    @Story("Get Localization.")
    @Severity(SeverityLevel.NORMAL)
    @Description("Get localization settings API.")
    public void getLocalization() {
        Response response = AuthApi.getLocalisationSettings();
        Allure.addAttachment("Localization Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Get localization settings failed");
    }

    @Test(priority = 9 )
    @Story("Get Privacy Policy And Security. ")
    @Severity(SeverityLevel.NORMAL)
    @Description("Get privacy and security settings API")
    public void getPrivacyAndSecurity() {
        Response response = AuthApi.getPrivacyAndSecuritySettings();
        Allure.addAttachment("Privacy & Security Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Get privacy and security settings failed");
    }


    // =================== Update Profile API ===================
    //    ==============Update profile with All Fields==============
    @Test(priority = 10)
    @Story("Update Profile With All Correct Fields.")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Update all profile fields successfully.")
    public void updateProfile_AllFields() {
        Response response = AuthApi.updateProfile(NewFirstName, NewLastName, NewPhone,NewEmail,"IN", "IN");
        Allure.addAttachment("Update Profile Response", response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "Profile update failed");
    }


    @Test(priority = 11)
    @Story("Update Profile With Only First Name & Last Name .")
    @Severity(SeverityLevel.NORMAL)
    @Description("Update only first name and last name")
    public void updateProfile_PartialFields() {
        Response response = AuthApi.updateProfile(NewFirstName, NewLastName, null,null, null, null);
        Allure.addAttachment("Partial Update Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 400, "Please ensure that all the filled details are correct");
    }

    @Test(priority = 12)
    @Story("Update Profile With empty credentials.")
    @Severity(SeverityLevel.NORMAL)
    @Description("Send empty required fields")
    public void updateProfile_EmptyRequiredFields() {
        Response response = AuthApi.updateProfile("", "","", "", "", "");
        Allure.addAttachment("Empty Update Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 400, "API should return 400 for empty fields");
    }



    @Test(priority = 13 )
    @Story("Update Profile With Invalid Phone number.")
    @Severity(SeverityLevel.NORMAL)
    @Description("Send invalid phone number")
    public void updateProfile_InvalidPhone() {
        Response response = AuthApi.updateProfile(NewFirstName, NewLastName, "123", NewEmail,"IN", "IN");
        Allure.addAttachment("Invalid Phone Update Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 400, "API should return 400 for invalid phone");
    }

//    =============Update Profile Api localization ===================

    @Test(priority = 14 )
    @Story("Localization Update with correct credentials.")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Update Localization Settings - All valid data")
    public void updateLocalization_AllFields() {
        List<String> languages = Arrays.asList("en-US", "te");
        Response response = AuthApi.updateProfileLocalisation_settings(
                "Asia/Kolkata",
                "en-US",
                languages,
                "%d/%m/%Y"
        );
        Allure.addAttachment("Localization Update Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Localization update failed");
    }


    @Test(priority = 15 )
    @Story("Update Localization Settings with missing timezone.")
    @Severity(SeverityLevel.NORMAL)
    @Description("Update Localization - Missing timezone")
    public void updateLocalization_MissingTimezone() {
        List<String> languages = Arrays.asList("en-US", "te");
        try {
            AuthApi.updateProfileLocalisation_settings(
                    null,
                    "en-US",
                    languages,
                    "%d/%m/%Y"
            );
            Assert.fail("Expected IllegalArgumentException for missing timezone");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }

    @Test(priority = 16 )
    @Story("Update Localization Settings Without entering the Language.")
    @Severity(SeverityLevel.NORMAL)
    @Description("Update Localization - Empty communication languages")
    public void updateLocalization_EmptyLanguages() {
        try {
            AuthApi.updateProfileLocalisation_settings(
                    "Asia/Kolkata",
                    "en-US",
                    new ArrayList<>(),
                    "%d/%m/%Y"
            );
            Assert.fail("Expected IllegalArgumentException for empty communication_language_list");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }

    @Test(priority = 17)
    @Story("Update Localization Settings with invalid Date Format. ")
    @Severity(SeverityLevel.NORMAL)
    @Description( "Update Localization - Invalid date format")
    public void updateLocalization_InvalidDateFormat() {
        List<String> languages = Arrays.asList("en-US", "te");
        Response response = AuthApi.updateProfileLocalisation_settings(
                "Asia/Kolkata",
                "en-US",
                languages,
                "%d/%Y"
        );
        Allure.addAttachment("Invalid Date Format Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 for invalid date format");
    }


// ===================== Update Profile privacy_and_security_settings =====================

   @Test(priority = 18)
   @Story("Privacy Policy update, Desable two factor Authentication.")
   @Severity(SeverityLevel.CRITICAL)
   @Description( "Disable Two Factor Authentication")
    public void updatePrivacy_DisableTwoFactor() {
        Response response = AuthApi.updateProfilePrivacyAndSecuritySettings(false);
       Allure.addAttachment("Disable 2FA Response", response.getBody().asPrettyString());
       Assert.assertEquals(response.getStatusCode(), 200, "Failed to disable two-factor authentication");
    }


    @Test(priority = 19)
    @Story("Privacy Enable Two Factor Authentication.")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Enable Two Factor Authentication")
    public void updatePrivacy_EnableTwoFactor() {
        Response response = AuthApi.updateProfilePrivacyAndSecuritySettings(true);
        Allure.addAttachment("Enable 2FA Response", response.getBody().asPrettyString());
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to enable two-factor authentication");
    }


    @Test(priority = 20)
    @Story("Update Privacy with Null Value.")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Update Privacy with Null Value.")
    public void updatePrivacy_NullValue() {
        try {
            AuthApi.updateProfilePrivacyAndSecuritySettings(null);
            Assert.fail("API should not accept null value");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }


    // ===================== get Sessions =====================
    @Test(priority = 21)
    @Story("Get Active Sessions")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get all active user sessions")
    public void getUserSessions_Positive() {
        Response response = AuthApi.getSessions();
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to fetch user sessions");
        Allure.addAttachment("Sessions Response", response.getBody().asPrettyString());
        // Optionally validate the response structure
        JsonPath json = response.jsonPath();
        Assert.assertNotNull(json.getList("data"), "Sessions data is missing in response");
        System.out.println("Total Sessions: " + json.getList("data").size());
    }

    @Test(priority = 22)
    @Story("Get Sessions without Token.")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get sessions without login token")
    public void getUserSessions_NoToken() {
        // Temporarily clear token
        String currentToken = null;
        try {
            currentToken = AuthApi.getToken(); // If getter exists, else handle via reflection
            AuthApi.clearToken(); // hypothetical method to clear token
            AuthApi.getSessions();
            Assert.fail("Expected IllegalStateException due to missing token");
        } catch (IllegalStateException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        } finally {
            // Restore token
            AuthApi.setToken(currentToken); // hypothetical setter
        }
    }


//    ========= Resend OTP Sucess /Fail=============

   @Test(priority = 23 )
   @Story("Resend OTP")
   @Severity(SeverityLevel.NORMAL)
   @Description("Resend OTP for existing order ID")
    public void testResendOtp() {
       ApiUtil.waitForNextRequest();
        Response response = AuthApi.resendOtp();
       Allure.addAttachment("Resend OTP Response", response.getBody().asPrettyString());
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for resend OTP");

        // JSON structure validation
        JsonPath jsonPath = response.jsonPath();
//        String message = jsonPath.getString("message");
//        Assert.assertNotNull(message, "'message' is missing");
//        Assert.assertFalse(message.trim().isEmpty(), "'message' is empty");

//        System.out.println("Resend OTP Response: " + message);
    }

//    ========================== Change Password ============================

//  @Test(priority = 24)
    @Story("Change Password.")
    @Severity(SeverityLevel.NORMAL)
    @Description("Change password API")
    public void changePassword() {

        Response response = AuthApi.changePassword();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for change password OTP");
        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.getString("message");
        Assert.assertNotNull(message, "'message' is missing");
        Assert.assertFalse(message.trim().isEmpty(), "'message' is empty");
        Allure.addAttachment("Change Password Response", response.getBody().asPrettyString());
        System.out.println("Change Password Response: " + message);
    }


//    =================Session-Logout ========================

 //  @Test(priority = 25 )
     @Story("Logout From All Session.")
     @Severity(SeverityLevel.NORMAL)
     @Description("Logout from  sessions")
    public void logoutFromAllSessions() {
        Response response = AuthApi.sessionLogout();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for logout from all sessions");

        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.getString("message");
        Assert.assertNotNull(message, "'message' is missing");
        Assert.assertFalse(message.trim().isEmpty(), "'message' is empty");
        Allure.addAttachment("Logout From All Session Response", response.getBody().asPrettyString());

         System.out.println("Logout Response: " + message);
    }





}