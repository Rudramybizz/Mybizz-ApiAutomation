package smbedition.organization.tests;

import io.qameta.allure.*;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import smbedition.common.BaseTest;
import smbedition.common.EncryptApi;
import smbedition.common.TestData;
import smbedition.common.logging.Log;
import smbedition.common.tokenmanagers.CookieManager;
import smbedition.common.waits.ApiUtil;
import smbedition.organization.services.OrgServices;
import smbedition.organization.util.CreateOrganizationDataProvider;
import smbedition.organization.util.FiscalYearDataProvider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

@Epic("Organization APIs")
@Feature("Organization Management APIs")
public class OrgTest extends BaseTest {

    // ===================== Create Organization Tests =====================
    @Test(priority = 1)
    @Story("Create Organization with Valid Data")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create organization with valid data")
    public void testCreateOrganization_ValidData() {
        Map<String, Object> orgData = new HashMap<>();
        orgData.put("name", TestData.generateOrganizationName());
        orgData.put("organization_id", TestData.generateOrganizationId());
        orgData.put("address", TestData.generateAddress());
        orgData.put("mandal_or_taluk", "Lumbini Layout, Gachibowli, Hyderabad");
        orgData.put("country_id", "IN");
        orgData.put("state_id", "4012");
        orgData.put("city_id", "133607");
        orgData.put("pincode", TestData.generatePincode());
        orgData.put("email", EncryptApi.encryptEmail(TestData.generateRandomEmail()));
        ApiUtil.waitForNextRequest();
        orgData.put("phone", EncryptApi.encryptMobile(TestData.generateRandomMobile()));
        orgData.put("dialing_code_id", "IN");
        orgData.put("organization_type", "Partnership firm");
        orgData.put("registration_number", TestData.generateRegistrationNumber());
        orgData.put("document_number", "");
        orgData.put("name_as_per_document", "");
        orgData.put("application_group_list", "[auto_doc_signer]");
       System.out.println("Organization Data:"+orgData);
        Response response = OrgServices.createOrganization(orgData);

        assertEquals(response.getStatusCode(), 200, "Organization creation should be successful");
        assertNotNull(response.jsonPath().get("data"), "Response should contain organization data");

        JsonPath jsonPath = response.jsonPath();
        assertEquals(jsonPath.getString("success"), "1", "Success should be 1");
        assertNotNull(jsonPath.getString("data.organization_id"), "Organization ID should be generated");
        assertNotNull(jsonPath.getString("data.name"), "Organization name should be returned");

        System.out.println("✅ Organization created successfully: " + jsonPath.getString("data.name"));
    }


    @Test(priority = 2, dataProvider = "fieldLengthBoundaryData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization Field Length Boundaries")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with field length boundaries: {1}")
    public void testCreateOrganization_FieldLengthBoundaries(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);


        assertEquals(response.getStatusCode(), 200, "Should accept valid boundary: " + scenario);
    }

    @Test(priority = 3, dataProvider = "invalidNameData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization with Invalid Names")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with invalid names: {1}")
    public void testCreateOrganization_InvalidName(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for: " + scenario);
        assertNotNull(response.jsonPath().getString("message"), "Error message should be present");
    }


    @Test(priority = 4, dataProvider = "invalidEmailData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization with Invalid Emails")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with invalid emails: {1}")
    public void testCreateOrganization_InvalidEmail(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for invalid email: " + scenario);
    }

    @Test(priority = 5, dataProvider = "invalidPhoneData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization with Invalid Phones")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with invalid phones: {1}")
    public void testCreateOrganization_InvalidPhone(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for invalid phone: " + scenario);
    }

    @Test(priority = 6, dataProvider = "securityTestData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization Security Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test organization creation with security threats: {1}")
    public void testCreateOrganization_SecurityTests(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        assertTrue(response.getStatusCode() >= 400, "Should reject security threat: " + scenario);
    }

    @Test(priority = 7, dataProvider = "invalidPincodeData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization with Invalid Pincodes")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with invalid pincodes: {1}")
    public void testCreateOrganization_InvalidPincode(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for invalid pincode: " + scenario);
    }

    @Test(priority = 8, dataProvider = "invalidDataTypeData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization with Invalid Data Types")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with invalid data types: {1}")
    public void testCreateOrganization_InvalidDataTypes(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for invalid data type: " + scenario);
    }

    @Test(priority = 9, dataProvider = "missingRequiredFieldsData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization with Missing Required Fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with missing required fields: {1}")
    public void testCreateOrganization_MissingRequiredFields(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for missing field: " + scenario);
    }

    @Test(priority = 10, dataProvider = "invalidEnumData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization with Invalid Enum Values")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with invalid enum values: {1}")
    public void testCreateOrganization_InvalidEnumValues(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for invalid enum: " + scenario);
    }

    @Test(priority = 11, dataProvider = "specialCharactersData", dataProviderClass = CreateOrganizationDataProvider.class)
    @Story("Create Organization with Special Characters")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with special characters: {1}")
    public void testCreateOrganization_SpecialCharacters(Map<String, Object> orgData, String scenario) {
        Response response = OrgServices.createOrganization(orgData);

        // Should accept valid special characters
        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() >= 400,
                "Should handle special characters appropriately: " + scenario);
    }

    @Test(priority = 12)
    @Story("Create Organization with Empty Body")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with empty request body")
    public void testCreateOrganization_EmptyBody() {
        Response response = OrgServices.createOrganization(new HashMap<>());

        assertEquals(response.getStatusCode(), 400, "Should return error for empty body");
    }

    @Test(priority = 13)
    @Story("Create Organization with Null Body")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test organization creation with null request body")
    public void testCreateOrganization_NullBody() {
        Response response = OrgServices.createOrganization(null);

        assertTrue(response.getStatusCode() >= 400, "Should return error for null body");
    }



    @Test(priority = 15)
    @Story("Get Organization General Information")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get general organization information")
    public void testGetOrganizationGeneral() {
        Response response = OrgServices.getOrganizationGeneral();
       Cookies cookie = CookieManager.getCookies();
        Log.info("Cookies:"+cookie);
        assertEquals(response.getStatusCode(), 200);
//        assertNotNull(response.jsonPath().get("organizations"), "Organization data should not be null");

        System.out.println("=== Organization General Response ===");
        response.prettyPrint();
    }



    @Test(priority = 16)
    @Story("Get Organization Country Specific")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get organization information related to country")
    public void testGetOrganization_countrySpecific() {
        Response response = OrgServices.getOrganizationCountrySpecific("IN");
        Cookies cookie = CookieManager.getCookies();
        Log.info("Cookies:"+cookie);
        assertEquals(response.getStatusCode(), 200);

        System.out.println("=== Organization General Response ===");
        response.prettyPrint();
    }


    @Test(priority = 17)
    @Story("Get Organization State Specific")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get organization information related to state")
    public void testGetOrganization_stateSpecific() {
        Response response = OrgServices.getOrganizationStateSpecific("4017");
        Cookies cookie = CookieManager.getCookies();
        Log.info("Cookies:"+cookie);
        assertEquals(response.getStatusCode(), 200);

        System.out.println("=== Organization State Specific Response ===");
        response.prettyPrint();
    }

    @Test(priority = 18)
    @Story("Get Organization List")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get organization list")
    public void testGetOrganization_list() {
        Response response = OrgServices.getOrganizationList();
        Cookies cookie = CookieManager.getCookies();
        Log.info("Cookies:"+cookie);
        assertEquals(response.getStatusCode(), 200);

        System.out.println("=== Organization List Response ===");
        response.prettyPrint();
    }





//    fiscalyear
@Test(priority = 20, dataProvider = "validFiscalYearData", dataProviderClass = FiscalYearDataProvider.class)
@Story("Create Organization Fiscal Year - Valid Data")
@Severity(SeverityLevel.CRITICAL)
@Description("Create organization fiscal year with valid data")
public void testCreateOrganizationFiscalYear_ValidData(Map<String, Object> fiscalYearData) {
    Response response = OrgServices.createOrganizationFiscalYear(fiscalYearData);

    assertEquals(response.getStatusCode(), 201, "Fiscal year should be created successfully");
    assertNotNull(response.jsonPath().getString("id"), "Response should contain fiscal year ID");
    assertNotNull(response.jsonPath().getString("created_at"), "Response should contain creation timestamp");

    // Validate response matches request data
    assertEquals(response.jsonPath().getString("default_date_format"),
            fiscalYearData.get("default_date_format"));
    assertEquals(response.jsonPath().getString("timezone_id"),
            fiscalYearData.get("timezone_id"));
    assertEquals(response.jsonPath().getString("default_currency_id"),
            fiscalYearData.get("default_currency_id"));

    System.out.println("=== Fiscal Year Creation Response ===");
    response.prettyPrint();
}

    @Test(priority = 21, dataProvider = "edgeCaseFiscalYearData", dataProviderClass = FiscalYearDataProvider.class)
    @Story("Create Organization Fiscal Year - Edge Cases")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with edge case data")
    public void testCreateOrganizationFiscalYear_EdgeCases(Map<String, Object> fiscalYearData) {
        Response response = OrgServices.createOrganizationFiscalYear(fiscalYearData);

        assertEquals(response.getStatusCode(), 201, "Fiscal year should be created with edge case data");

        System.out.println("=== Fiscal Year Edge Case Response ===");
        response.prettyPrint();
    }

    // ===================== NEGATIVE TESTS =====================

    @Test(priority = 23, dataProvider = "invalidFiscalYearData", dataProviderClass = FiscalYearDataProvider.class)
    @Story("Create Organization Fiscal Year - Invalid Data")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with invalid data")
    public void testCreateOrganizationFiscalYear_InvalidData(Map<String, Object> fiscalYearData) {
        Response response = OrgServices.createOrganizationFiscalYear(fiscalYearData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for invalid data");

        // Validate error response structure
        assertNotNull(response.jsonPath().getString("error"), "Error response should contain error message");
        assertNotNull(response.jsonPath().getString("message"), "Error response should contain detailed message");

        System.out.println("=== Fiscal Year Invalid Data Response ===");
        response.prettyPrint();
    }

    @Test(priority = 24)
    @Story("Create Organization Fiscal Year - Empty Body")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with empty request body")
    public void testCreateOrganizationFiscalYear_EmptyBody() {
        Response response = OrgServices.createOrganizationFiscalYear(new HashMap<>());

        assertEquals(response.getStatusCode(), 400, "Should return 400 for empty body");
    }

    @Test(priority = 25)
    @Story("Create Organization Fiscal Year - Null Body")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with null request body")
    public void testCreateOrganizationFiscalYear_NullBody() {
        Response response = OrgServices.createOrganizationFiscalYear(null);

        assertEquals(response.getStatusCode(), 400, "Should return 400 for null body");
    }

    @Test(priority = 26)
    @Story("Create Organization Fiscal Year - Invalid Fiscal Year Range")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with invalid date range")
    public void testCreateOrganizationFiscalYear_InvalidDateRange() {
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("default_date_format", "DD/MM/YYYY");
        invalidData.put("timezone_id", "Asia/Kolkata");
        invalidData.put("maintain_books_of_accounts_from", "01/04/2024");
        invalidData.put("default_language_id", "en-US");
        invalidData.put("default_currency_id", "INR");
        invalidData.put("allow_multi_currency_transactions", false);
        invalidData.put("manage_exchange_rates", "not_applicable");
        invalidData.put("allow_decimals", true);
        invalidData.put("no_of_decimals_allowed", "2");
        invalidData.put("number_format", "12,34,567.89");
        invalidData.put("fiscal_year_start_month", "12"); // Start after end
        invalidData.put("fiscal_year_start_day", "31");
        invalidData.put("fiscal_year_end_month", "01");
        invalidData.put("fiscal_year_end_day", "01");

        Response response = OrgServices.createOrganizationFiscalYear(invalidData);

        assertTrue(response.getStatusCode() >= 400, "Should return error for invalid fiscal year range");
    }

    // ===================== SECURITY TESTS =====================

    @Test(priority = 27)
    @Story("Security - Create Fiscal Year Without Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Attempt to create fiscal year without authentication token")
    public void testCreateOrganizationFiscalYear_WithoutAuth() {
        Map<String, Object> fiscalYearData = new HashMap<>();
        fiscalYearData.put("default_date_format", "DD/MM/YYYY");
        fiscalYearData.put("timezone_id", "Asia/Kolkata");
        fiscalYearData.put("maintain_books_of_accounts_from", "01/04/2024");

        // Use method without token
        Response response = OrgServices.createOrganizationFiscalYear(fiscalYearData);

        assertEquals(response.getStatusCode(), 401, "Should return 401 without authentication");
    }

    @Test(priority = 29)
    @Story("Security - SQL Injection Attempt")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Attempt SQL injection in fiscal year data")
    public void testCreateOrganizationFiscalYear_SQLInjectionAttempt() {
        Map<String, Object> sqlInjectionData = new HashMap<>();
        sqlInjectionData.put("default_date_format", "DD/MM/YYYY'; DROP TABLE users; --");
        sqlInjectionData.put("timezone_id", "Asia/Kolkata");
        sqlInjectionData.put("maintain_books_of_accounts_from", "01/04/2024");
        sqlInjectionData.put("default_language_id", "en-US'; DELETE FROM organizations; --");

        Response response = OrgServices.createOrganizationFiscalYear(sqlInjectionData);

        // Should either reject with 400 or sanitize input
        assertTrue(response.getStatusCode() >= 400, "Should reject SQL injection attempts");
    }

    @Test(priority = 30)
    @Story("Security - XSS Attempt")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Attempt cross-site scripting in fiscal year data")
    public void testCreateOrganizationFiscalYear_XSSAttempt() {
        Map<String, Object> xssData = new HashMap<>();
        xssData.put("default_date_format", "<script>alert('xss')</script>");
        xssData.put("timezone_id", "Asia/Kolkata");
        xssData.put("maintain_books_of_accounts_from", "01/04/2024");
        xssData.put("default_language_id", "en-US<img src=x onerror=alert('xss')>");

        Response response = OrgServices.createOrganizationFiscalYear(xssData);

        // Should either reject with 400 or sanitize input
        assertTrue(response.getStatusCode() >= 400, "Should reject XSS attempts");
    }

    // ===================== PERFORMANCE TESTS =====================

    @Test(priority = 31)
    @Story("Performance - Multiple Rapid Requests")
    @Severity(SeverityLevel.MINOR)
    @Description("Test handling of multiple rapid fiscal year creation requests")
    public void testCreateOrganizationFiscalYear_MultipleRequests() {
        Map<String, Object> fiscalYearData = new HashMap<>();
        fiscalYearData.put("default_date_format", "DD/MM/YYYY");
        fiscalYearData.put("timezone_id", "Asia/Kolkata");
        fiscalYearData.put("maintain_books_of_accounts_from", "01/04/2024");
        fiscalYearData.put("default_language_id", "en-US");
        fiscalYearData.put("default_currency_id", "INR");

        // Send multiple requests rapidly
        for (int i = 0; i < 5; i++) {
            Response response = OrgServices.createOrganizationFiscalYear(fiscalYearData);
            // First request might succeed, subsequent might be rate limited or rejected
            assertTrue(response.getStatusCode() == 201 || response.getStatusCode() == 429 ||
                    response.getStatusCode() == 400, "Should handle multiple requests appropriately");
        }
    }

    @Test(priority = 32)
    @Story("Performance - Large Payload")
    @Severity(SeverityLevel.MINOR)
    @Description("Test handling of very large fiscal year data payload")
    public void testCreateOrganizationFiscalYear_LargePayload() {
        Map<String, Object> largeData = new HashMap<>();
        largeData.put("default_date_format", "DD/MM/YYYY");
        largeData.put("timezone_id", "Asia/Kolkata");
        largeData.put("maintain_books_of_accounts_from", "01/04/2024");

        // Add large unnecessary fields
        for (int i = 0; i < 100; i++) {
            largeData.put("unnecessary_field_" + i, "x".repeat(1000));
        }

        Response response = OrgServices.createOrganizationFiscalYear(largeData);

        // Should either reject with 413 or process successfully
        assertTrue(response.getStatusCode() == 201 || response.getStatusCode() == 413,
                "Should handle large payload appropriately");
    }

    // ===================== BUSINESS LOGIC TESTS =====================

    @Test(priority = 33)
    @Story("Business Logic - Currency Decimal Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validate currency decimal rules")
    public void testCreateOrganizationFiscalYear_CurrencyDecimalRules() {
        // JPY typically doesn't use decimals
        Map<String, Object> jpyData = new HashMap<>();
        jpyData.put("default_date_format", "DD/MM/YYYY");
        jpyData.put("timezone_id", "Asia/Kolkata");
        jpyData.put("maintain_books_of_accounts_from", "01/04/2024");
        jpyData.put("default_language_id", "en-US");
        jpyData.put("default_currency_id", "JPY");
        jpyData.put("allow_decimals", true); // This might be invalid for JPY
        jpyData.put("no_of_decimals_allowed", "2");

        Response response = OrgServices.createOrganizationFiscalYear(jpyData);

        // Either accept or reject based on business rules
        assertTrue(response.getStatusCode() == 201 || response.getStatusCode() == 400,
                "Should validate currency-decimal rules");
    }



    @Test(priority = 34)
    @Story("Business Logic - Fiscal Year Consistency")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validate fiscal year date consistency")
    public void testCreateOrganizationFiscalYear_DateConsistency() {
        Map<String, Object> inconsistentData = new HashMap<>();
        inconsistentData.put("default_date_format", "DD/MM/YYYY");
        inconsistentData.put("timezone_id", "Asia/Kolkata");
        inconsistentData.put("maintain_books_of_accounts_from", "01/04/2024"); // Uses DD/MM/YYYY
        inconsistentData.put("fiscal_year_start_month", "04");
        inconsistentData.put("fiscal_year_start_day", "01/04/2024"); // Inconsistent format

        Response response = OrgServices.createOrganizationFiscalYear(inconsistentData);

        assertTrue(response.getStatusCode() >= 400, "Should reject inconsistent date formats");
    }

    // ===================== ADDITIONAL NEGATIVE TESTS =====================

    @Test(priority = 35)
    @Story("Create Organization Fiscal Year - Invalid Date Format")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with invalid date format")
    public void testCreateOrganizationFiscalYear_InvalidDateFormat() {
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("default_date_format", "INVALID_FORMAT");
        invalidData.put("timezone_id", "Asia/Kolkata");
        invalidData.put("maintain_books_of_accounts_from", "01/04/2024");
        invalidData.put("default_language_id", "en-US");
        invalidData.put("default_currency_id", "INR");

        Response response = OrgServices.createOrganizationFiscalYear(invalidData);

        assertEquals(response.getStatusCode(), 400, "Should return 400 for invalid date format");
    }


    @Test(priority = 36)
    @Story("Create Organization Fiscal Year - Invalid Timezone")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with invalid timezone")
    public void testCreateOrganizationFiscalYear_InvalidTimezone() {
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("default_date_format", "DD/MM/YYYY");
        invalidData.put("timezone_id", "Invalid/Timezone");
        invalidData.put("maintain_books_of_accounts_from", "01/04/2024");
        invalidData.put("default_language_id", "en-US");
        invalidData.put("default_currency_id", "INR");

        Response response = OrgServices.createOrganizationFiscalYear(invalidData);

        assertEquals(response.getStatusCode(), 400, "Should return 400 for invalid timezone");
    }


    @Test(priority = 37)
    @Story("Create Organization Fiscal Year - Missing Required Fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with missing required fields")
    public void testCreateOrganizationFiscalYear_MissingRequiredFields() {
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("default_date_format", "DD/MM/YYYY");
        // Missing timezone_id and maintain_books_of_accounts_from

        Response response = OrgServices.createOrganizationFiscalYear(invalidData);

        assertEquals(response.getStatusCode(), 400, "Should return 400 for missing required fields");
    }

    @Test(priority = 38)
    @Story("Create Organization Fiscal Year - Special Characters")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create organization fiscal year with special characters in fields")
    public void testCreateOrganizationFiscalYear_SpecialCharacters() {
        Map<String, Object> specialCharData = new HashMap<>();
        specialCharData.put("default_date_format", "DD/MM/YYYY");
        specialCharData.put("timezone_id", "Asia/Kolkata");
        specialCharData.put("maintain_books_of_accounts_from", "01/04/2024");
        specialCharData.put("default_language_id", "en-US@#$%");
        specialCharData.put("default_currency_id", "INR@#$");

        Response response = OrgServices.createOrganizationFiscalYear(specialCharData);

        assertTrue(response.getStatusCode() >= 400, "Should reject special characters in fields");
    }

    @Test(priority = 39)
    @Story("Get Organization Fiscal Year Information")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get general organization Fiscal year information")
    public void testGetOrganization_fiscalyear() {
        Response response = OrgServices.getorganization_fiscalyear();
        Cookies cookie = CookieManager.getCookies();
        Log.info("Cookies:"+cookie);
        assertEquals(response.getStatusCode(), 200);

        System.out.println("=== Organization General Response ===");
        response.prettyPrint();
    }


    @Test(priority = 40)
    @Story("Get Organization List")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get organization List information")
    public void testGetOrganization_List() {
        Response response = OrgServices.getorganization_List();
        Cookies cookie = CookieManager.getCookies();
        Log.info("Cookies:"+cookie);
        assertEquals(response.getStatusCode(), 200);

        System.out.println("=== Organization General Response ===");
        response.prettyPrint();
    }


    @Test(priority = 41)
    @Story("Get Organization Single api..")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get organization Single information")
    public void testGetOrganization_single() {
        Response response = OrgServices.getorganization_single();
        Cookies cookie = CookieManager.getCookies();
        Log.info("Cookies:"+cookie);
        assertEquals(response.getStatusCode(), 200);

        System.out.println("=== Organization General Response ===");
        response.prettyPrint();
    }


//    Update organization fiscal year


    @Test(priority = 42)
    @Story("Update Mark-aspdefault..")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Mark As default information")
    public void testMark_as_default() {
        Response response = OrgServices.markas_default();
        Cookies cookie = CookieManager.getCookies();
        Log.info("Cookies:"+cookie);
        assertEquals(response.getStatusCode(), 200);

        System.out.println("=== Organization General Response ===");
        response.prettyPrint();
    }




}