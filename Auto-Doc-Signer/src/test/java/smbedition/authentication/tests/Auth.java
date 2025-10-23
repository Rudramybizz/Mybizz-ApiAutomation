package smbedition.authentication.tests;

import io.qameta.allure.*;
import smbedition.authentication.util.LoginDataProvider;
import smbedition.authentication.util.RegistrationDataProvider;
import smbedition.common.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.authentication.services.AuthApi;
import smbedition.common.logging.Log;
import smbedition.common.tokenmanagers.CookieManager;
import smbedition.common.tokenmanagers.TokenManager;
import smbedition.common.waits.ApiUtil;
import java.util.*;

@Epic("Authentication APIs")
@Feature("User Onboarding & Security")
public class Auth extends BaseTest {
    private static List<Map<String, Object>> registeredUsers = new ArrayList<>();

    String mail = TestData.generateRandomEmail();
    String mobile= TestData.generateRandomMobile();
    String firstname = TestData.generateRandomFirstName();
    String lastname = TestData.generateRandomLastName();
    String password = TestData.generateRandomPassword();
    String dialing_code ="IN";

    // ========== POSITIVE TESTS ==========
    @Test(priority = 1)
    @Story("Successful SSO Registration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Register user successfully via SSO with valid credentials")
    public void testSSORegistration_ValidCredentials() {
        Map<String, Object> validData = createValidRegistrationData();
        Response response = AuthApi.ssoRegister(validData);
        registeredUsers.add(validData);
        Allure.addAttachment("Registration Response", response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 308, "Registration should be successful");

        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.getString("message");
        Assert.assertNotNull(message, "Success message should be present");
    }
    // Helper method to create valid registration data using TestData
    private static Map<String, Object> createValidRegistrationData() {
        Map<String, Object> body = new HashMap<>();
        body.put("first_name", (TestData.generateRandomFirstName()));
        body.put("last_name", (TestData.generateRandomLastName()));
        body.put("email", (TestData.generateRandomEmail()));
        body.put("password",(TestData.generateRandomPassword()));
        body.put("dialing_code", "IN");
        body.put("phone",(TestData.generateRandomMobile()));
        body.put("country_id", "IN");

        return body;
    }

    // ========== NEGATIVE TESTS WITH DATA PROVIDER ==========
    @Test(priority = 2, dataProvider = "invalidRegistrationData", dataProviderClass = RegistrationDataProvider.class)
    @Story("SSO Registration with Invalid Data")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test registration with various invalid data scenarios: {1}")
    public void testSSORegistration_InvalidData(Map<String, Object> registrationData, String scenario) {
        Allure.parameter("Scenario", scenario);
        Allure.parameter("Registration Data", registrationData.toString());

        Response response = AuthApi.ssoRegister(registrationData);

        Allure.addAttachment("Registration Response - " + scenario, response.getBody().asPrettyString());

        // Should return 4xx status code for invalid data
        Assert.assertTrue(response.getStatusCode() >= 400 && response.getStatusCode() < 500,
                "Should return client error for: " + scenario);

        // Validate error response structure
        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.getString("message");
        Assert.assertNotNull(errorMessage, "Error message should be present for: " + scenario);
    }


    @Test(priority = 3, dataProvider = "boundaryValueData", dataProviderClass = RegistrationDataProvider.class)
    @Story("SSO Registration Boundary Values")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test registration with boundary values: {1}")
    public void testSSORegistration_BoundaryValues(Map<String, Object> registrationData, String scenario) {
        Allure.parameter("Scenario", scenario);
        ApiUtil.waitForNextRequest();
        Response response = AuthApi.ssoRegister(registrationData);
        Allure.addAttachment("Boundary Test Response - " + scenario, response.getBody().asPrettyString());
        // Log the response for analysis
        System.out.println("Boundary Test - " + scenario + ": Status=" + response.getStatusCode());
        // Boundary tests might pass or fail based on validation rules
        // We just verify we get a proper response
        Assert.assertTrue(response.getStatusCode() >= 200 && response.getStatusCode() < 500,
                "Should return valid status code for boundary test: " + scenario);
    }

    @Test(priority = 4, dataProvider = "countryDialingCodeData", dataProviderClass = RegistrationDataProvider.class)
    @Story("SSO Registration with Different Countries")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test registration with different country/dialing code combinations: {2}")
    public void testSSORegistration_DifferentCountries(String countryId, String dialingCode, String description) {
        Map<String, Object> registrationData = createRegistrationDataForCountry(countryId, dialingCode);

        Allure.parameter("Country ID", countryId);
        Allure.parameter("Dialing Code", dialingCode);
        Allure.parameter("Description", description);

        Response response = AuthApi.ssoRegister(registrationData);

        Allure.addAttachment("Country Test Response - " + countryId, response.getBody().asPrettyString());

        // Country-specific tests might have different success criteria
        if (response.getStatusCode() == 308) {
            // Successful registration
            JsonPath jsonPath = response.jsonPath();
            Assert.assertNotNull(jsonPath.getString("message"), "Success message should be present");
        } else {
            // Might be unsupported country
            Assert.assertTrue(response.getStatusCode() >= 400, "Should indicate issue with country configuration");
        }
    }

    // ========== SECURITY TESTS ==========
    @Test(priority = 5)
    @Story("SSO Registration Security - SQL Injection")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test SQL injection prevention in registration")
    public void testSSORegistration_SQLInjectionPrevention() {
        Map<String, Object> maliciousData = RegistrationDataProvider.createSqlInjectionData();

        Response response = AuthApi.ssoRegister(maliciousData);

        Allure.addAttachment("SQL Injection Test Response", response.getBody().asPrettyString());

        // Should reject SQL injection attempts
        Assert.assertEquals(response.getStatusCode(), 400, "Should reject SQL injection attempts");

        JsonPath jsonPath = response.jsonPath();
        String errorMessage = jsonPath.getString("message");
        Assert.assertNotNull(errorMessage, "Should return error message for SQL injection attempt");
    }

    @Test(priority = 6)
    @Story("SSO Registration Security - XSS Prevention")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test XSS prevention in registration")
    public void testSSORegistration_XSSPrevention() {
        Map<String, Object> xssData = RegistrationDataProvider.createXSSData();

        Response response = AuthApi.ssoRegister(xssData);

        Allure.addAttachment("XSS Test Response", response.getBody().asPrettyString());

        // Should reject or sanitize XSS attempts
        Assert.assertTrue(response.getStatusCode() >= 400, "Should handle XSS attempts properly");
    }

    // ========== PERFORMANCE TESTS ==========
    @Test(priority = 7)
    @Story("SSO Registration Performance")
    @Severity(SeverityLevel.MINOR)
    @Description("Test registration response time")
    public void testSSORegistration_ResponseTime() {
        long startTime = System.currentTimeMillis();
        Map<String, Object> validData = createValidRegistrationData();
        Response response = AuthApi.ssoRegister(validData);


        long responseTime = System.currentTimeMillis() - startTime;

        Allure.addAttachment("Response Time", "Response time: " + responseTime + "ms");
        Allure.addAttachment("Performance Response", response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 308, "Registration should be successful");

        // Response time should be reasonable (adjust threshold as needed)
        long maxAcceptableTime = 1000;
        Assert.assertTrue(responseTime < maxAcceptableTime,
                "Response time should be under " + maxAcceptableTime + "ms. Actual: " + responseTime + "ms");
    }

    // ========== HELPER METHODS ==========
    private Map<String, Object> createRegistrationDataForCountry(String countryId, String dialingCode) {
        Map<String, Object> body = new HashMap<>();
        body.put("first_name", TestData.generateRandomFirstName());
        body.put("last_name", TestData.generateRandomLastName());
        body.put("email", TestData.generateRandomEmail());
        body.put("password", TestData.generateRandomPassword());
        body.put("dialing_code", dialingCode);
        body.put("phone", TestData.generateRandomMobile());
        body.put("country_id", countryId);

        return body;
    }


    // =================== SSo Generate OTP API ===================
    @Test(priority = 10)
    @Story("Generate OTP with Valid Credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Generate OTP for user login with valid credentials")
    public void testGenerateOtp_ValidCredentials() {

        Map<String, Object> registrationData = new HashMap<>();
        registrationData.put("first_name", firstname);
        registrationData.put("last_name", lastname);
        registrationData.put("email", mail);
        registrationData.put("password", password);
        registrationData.put("dialing_code", dialing_code);
        registrationData.put("phone", mobile);
        registrationData.put("country_id", "IN");

        // Register the user first
        AuthApi.ssoRegister(registrationData);
        ApiUtil.waitForNextRequest();
        Log.info("mail:"+mail);
        Log.info("password:"+password);
        Response response = AuthApi.generateOtpLogin(mail,password);

        Allure.addAttachment("Generate OTP Response", response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "OTP generation should be successful");

        JsonPath jsonPath = response.jsonPath();
        String success = jsonPath.getString("success");
        String message = jsonPath.getString("message");

        Assert.assertEquals(success, "1", "Success should be 1");
        Assert.assertEquals(message, "OTP sent successfully", "OTP should be sent successfully");

        System.out.println("✅ OTP Generated Successfully");
    }

    //==================== SSO Login with OTP API ===================
    @Test(priority = 11)
    @Story("Login with Valid OTP")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with valid OTP after OTP generation")
    public void testLoginWithValidOtp() {

        AuthApi.generateOtpLogin(mail, password);
        ApiUtil.waitForNextRequest();

        Response response = AuthApi.loginWithOtp(mail, password, "667788");

        Allure.addAttachment("Login Response", response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 308, "Login should be successful");

        JsonPath jsonPath = response.jsonPath();
        String success = jsonPath.getString("success");
        String message = jsonPath.getString("message");
        String authToken = jsonPath.getString("data.authorization_token");

        Assert.assertEquals(success, "1", "Success should be 1");
        Assert.assertEquals(message, "Login successful", "Login should be successful");
        Assert.assertNotNull(authToken, "Authorization token should be present");

        // Check if token is stored in TokenManager
        String storedToken = TokenManager.get();
        Assert.assertNotNull(storedToken, "Token should be stored in TokenManager");

        System.out.println("✅ Login Successful - Token: " + authToken);
    }


    @Test(priority = 12, dataProvider = "invalidOtpData", dataProviderClass = LoginDataProvider.class)
    @Story("Generate OTP with Invalid Data")
    @Severity(SeverityLevel.NORMAL)
    @Description("Generate OTP with invalid data: {1}")
    public void testGenerateOtp_InvalidData(Map<String, Object> otpData, String scenario) {
        Allure.parameter("Scenario", scenario);

        Response response = AuthApi.generateOtpLogin(
                (String) otpData.get("email"),
                (String) otpData.get("password")
        );

        Allure.addAttachment("Invalid OTP Response - " + scenario, response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 400, "Should return error for: " + scenario);
    }

    @Test(priority = 13, dataProvider = "invalidLoginData", dataProviderClass = LoginDataProvider.class)
    @Story("Login with Invalid Data")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login with invalid data: {1}")
    public void testLogin_InvalidData(Map<String, Object> loginData, String scenario) {
        Allure.parameter("Scenario", scenario);

        // First generate valid OTP
        AuthApi.generateOtpLogin("manpret343@gmail.com", "Indian@123");

        Response response = AuthApi.loginWithOtp(
                (String) loginData.get("email"),
                (String) loginData.get("password"),
                (String) loginData.get("otp")
        );

        Allure.addAttachment("Invalid Login Response - " + scenario, response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 400, "Should return error for: " + scenario);
    }

    @Test(priority = 14)
    @Story("Complete Login Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Complete login flow: Generate OTP + Login with OTP")
    public void testCompleteLoginFlow() {
        Response response = AuthApi.completeLoginFlow(mail, password, "667788");

        Allure.addAttachment("Complete Login Flow Response", response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 308, "Complete login flow should be successful");

        String authToken = TokenManager.get();
        Assert.assertNotNull(authToken, "Token should be stored after complete login flow");

        System.out.println("✅ Complete Login Flow Successful");
    }

//  ============Token Exchange API ===================
    @Test(priority = 50)
    @Story("Token Exchange with Valid Token")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Exchange authorization token and capture cookies")
    public void testTokenExchange_ValidToken() {


        Response loginResponse = AuthApi.completeLoginFlow("rudra2@gmail.com","Test@123", "667788");
        Assert.assertEquals(loginResponse.getStatusCode(), 308, "Login should be successful");

        // Then exchange token
        Response response = AuthApi.exchangeToken();

        Allure.addAttachment("Token Exchange Response", response.getBody().asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200, "Token exchange should be successful");

        JsonPath jsonPath = response.jsonPath();
        String success = jsonPath.getString("success");
        String message = jsonPath.getString("message");

        Assert.assertEquals(success, "1", "Success should be 1");
        Assert.assertEquals(message, "Token fetched successfully", "Token should be fetched successfully");
        // Verify cookies are stored
        Assert.assertFalse(!CookieManager.getCookies().exist(), "Cookies should be stored after token exchange");

        // Log cookies for verification
        System.out.println("=== Cookies After Token Exchange ===");
        CookieManager.getCookies().forEach(cookie -> {
            System.out.println("Cookie: " + cookie.getName() + " = " + cookie.getValue());
            Allure.addAttachment("Cookie: " + cookie.getName(), cookie.getValue());
        });
        System.out.println("✅ Token Exchange Successful - Cookies Captured");
    }


    @Test(priority = 51)
    @Story("Token Exchange without Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Try token exchange without logging in first")
    public void testTokenExchange_WithoutLogin() {
        // Clear any existing token
        TokenManager.clear();

        try {
            AuthApi.exchangeToken();
            Assert.fail("Should throw exception when no token is available");
        } catch (IllegalStateException e) {
            System.out.println("✅ Correctly caught exception: " + e.getMessage());
            Assert.assertTrue(e.getMessage().contains("No authorization token available"));
        }
    }

    // =================== Get Profile API ===================

        @Test(priority = 61)
        @Story("Get User Profile After Login")
        @Severity(SeverityLevel.CRITICAL)
        @Description("Get user profile information after successful authentication")
        public void testGetProfile_AfterLogin() {
            // Complete authentication flow first
            AuthApi.generateOtpLogin("rudra2@gmail.com", "Test@123");
            AuthApi.loginWithOtp("rudra2@gmail.com", "Test@123", "667788");
            ApiUtil.waitForNextRequest();
            AuthApi.exchangeToken();

            // Get profile
            Response response = AuthApi.getProfile();

            Allure.addAttachment("Profile Response", response.getBody().asPrettyString());

            Assert.assertEquals(response.getStatusCode(), 200, "Get profile should be successful");

            JsonPath jsonPath = response.jsonPath();

            // Validate profile structure
            Assert.assertNotNull(jsonPath.get("data"), "Profile data should be present");
            Assert.assertNotNull(jsonPath.getString("data.email"), "Email should be present in profile");
            Assert.assertNotNull(jsonPath.getString("data.first_name"), "First name should be present in profile");
            Assert.assertNotNull(jsonPath.getString("data.last_name"), "Last name should be present in profile");

            System.out.println("✅ Get Profile Successful");
        }

        @Test(priority = 62)
        @Story("Get Profile Without Authentication")
        @Severity(SeverityLevel.NORMAL)
        @Description("Try to get profile without logging in")
        public void testGetProfile_WithoutAuth() {
            // Clear token
            TokenManager.clear();

            try {
                AuthApi.getProfile();
                Assert.fail("Should throw exception when no token is available");
            } catch (IllegalStateException e) {
                System.out.println("✅ Correctly caught exception: " + e.getMessage());
                Assert.assertTrue(e.getMessage().contains("No authorization token available"));
            }
        }


}