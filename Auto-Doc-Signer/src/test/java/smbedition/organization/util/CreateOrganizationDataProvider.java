package smbedition.organization.util;

import org.testng.annotations.DataProvider;
import smbedition.common.EncryptApi;
import smbedition.common.TestData;

import java.util.HashMap;
import java.util.Map;

public class CreateOrganizationDataProvider {


    // POSITIVE Boundaries - Valid values
    @DataProvider(name = "positiveBoundaries")
    public static Object[][] positiveBoundaries() {
        return new Object[][]{
                {createOrgWithName("Test"), "Name min (4 chars)"},
                {createOrgWithName("A".repeat(75)), "Name max (75 chars)"},
                {createOrgWithFieldLength("address", 10), "Address min (10 chars)"},
                {createOrgWithFieldLength("address", 100), "Address max (100 chars)"},
                {createOrgWithFieldLength("mandal_or_taluk", 1), "Mandal min (1 char)"},
                {createOrgWithFieldLength("mandal_or_taluk", 50), "Mandal max (50 chars)"}
        };
    }
    // NEGATIVE Boundaries - Invalid values
    @DataProvider(name = "negativeBoundaries")
    public static Object[][] negativeBoundaries() {
        return new Object[][]{
                {createOrgWithName("Te"), "Name too short (2 chars)"},
                {createOrgWithName("A".repeat(150)), "Name too long (76 chars)"},
                {createOrgWithFieldLength("ad", 2), "Address too short (2 chars)"},
                {createOrgWithFieldLength("address", 150), "Address too long (101 chars)"},
                {createOrgWithFieldLength("mandal_or_taluk", 0), "Mandal empty (0 chars)"},
                {createOrgWithFieldLength("mandal_or_taluk", 150), "Mandal too long (51 chars)"}
        };
    }


    // Field Length Boundaries - UPDATED with correct API limits
    @DataProvider(name = "fieldLengthBoundaryData")
    public static Object[][] fieldLengthBoundaryData() {
        return new Object[][]{
                {createOrgWithFieldLength("name", 4), "Name min length (4 chars)"},
                {createOrgWithFieldLength("name", 75), "Name max length (75 chars)"},
                {createOrgWithFieldLength("address", 10), "Address min length (10 chars)"},
                {createOrgWithFieldLength("address", 100), "Address max length (100 chars)"},
                {createOrgWithFieldLength("mandal_or_taluk", 1), "Mandal/Taluk min length (1 char)"},
                {createOrgWithFieldLength("mandal_or_taluk", 50), "Mandal/Taluk max length (50 chars)"}
        };
    }


    @DataProvider(name = "invalidFieldLengthBoundaryData")
    public static Object[][] invalidFieldLengthBoundaryData() {
        return new Object[][]{
                {createOrgWithFieldLength("name", 3), "Name below min length (3 chars)"},
                {createOrgWithFieldLength("name", 76), "Name above max length (76 chars)"},
                {createOrgWithFieldLength("address", 9), "Address below min length (9 chars)"},
                {createOrgWithFieldLength("address", 101), "Address above max length (101 chars)"},
                {createOrgWithFieldLength("mandal_or_taluk", 0), "Mandal/Taluk below min length (0 char)"},
                {createOrgWithFieldLength("mandal_or_taluk", 51), "Mandal/Taluk above max length (51 chars)"}
        };
    }

    // Invalid organization names - UPDATED with correct limits
    @DataProvider(name = "invalidNameData")
    public static Object[][] invalidNameData() {
        return new Object[][]{
                {createOrgWithName(""), "Empty organization name"},
                {createOrgWithName("A"), "Name too short (1 char)"},
                {createOrgWithName(generateLongString(76)), "Name too long (76 chars)"}, // FIXED: 76 chars (over 75 limit)
                {createOrgWithName(null), "Null organization name"}
        };
    }

    // Invalid email formats
    @DataProvider(name = "invalidEmailData")
    public static Object[][] invalidEmailData() {
        return new Object[][]{
                {createOrgWithEmail("invalid"), "Invalid email format"},
                {createOrgWithEmail("invalid@"), "Invalid email format"},
                {createOrgWithEmail("@domain.com"), "Invalid email format"},
                {createOrgWithEmail(""), "Empty email"},
                {createOrgWithEmail(null), "Null email"}
        };
    }

    // Invalid phone numbers
    @DataProvider(name = "invalidPhoneData")
    public static Object[][] invalidPhoneData() {
        return new Object[][]{
                {createOrgWithPhone("123"), "Phone too short"},
                {createOrgWithPhone("1234567890123456"), "Phone too long"},
                {createOrgWithPhone("abc"), "Non-numeric phone"},
                {createOrgWithPhone(""), "Empty phone"},
                {createOrgWithPhone(null), "Null phone"}
        };
    }

    // XSS and SQL injection attempts
    @DataProvider(name = "securityTestData")
    public static Object[][] securityTestData() {
        return new Object[][]{
                {createOrgWithXSS(), "XSS attempt"},
                {createOrgWithSQLInjection(), "SQL injection attempt"},
                {createOrgWithSpecialChars(), "Special characters"}
        };
    }

    // Boundary values for pincode
    @DataProvider(name = "invalidPincodeData")
    public static Object[][] invalidPincodeData() {
        return new Object[][]{
                {createOrgWithPincode("123"), "Pincode too short"},
                {createOrgWithPincode("1234567"), "Pincode too long"},
                {createOrgWithPincode("abc"), "Non-numeric pincode"},
                {createOrgWithPincode(""), "Empty pincode"}
        };
    }

    // Data Type Validation
    @DataProvider(name = "invalidDataTypeData")
    public static Object[][] invalidDataTypeData() {
        return new Object[][]{
                {createOrgWithFieldValue("country_id", "abc"), "Non-numeric country_id"},
                {createOrgWithFieldValue("state_id", "xyz"), "Non-numeric state_id"},
                {createOrgWithFieldValue("city_id", "invalid"), "Non-numeric city_id"},
                {createOrgWithFieldValue("dialing_code_id", "text"), "Non-numeric dialing_code_id"},
                {createOrgWithFieldValue("pincode", "56a001"), "Alphanumeric pincode"},
                {createOrgWithFieldValue("phone", "98765a4321"), "Alphanumeric phone"}
        };
    }

    // Required Fields Validation
    @DataProvider(name = "missingRequiredFieldsData")
    public static Object[][] missingRequiredFieldsData() {
        return new Object[][]{
                {createOrgWithoutField("name"), "Missing name field"},
                {createOrgWithoutField("country_id"), "Missing country_id field"},
                {createOrgWithoutField("state_id"), "Missing state_id field"},
                {createOrgWithoutField("city_id"), "Missing city_id field"},
                {createOrgWithoutField("email"), "Missing email field"},
                {createOrgWithoutField("phone"), "Missing phone field"}
        };
    }

    // Enum/List Value Validation
    @DataProvider(name = "invalidEnumData")
    public static Object[][] invalidEnumData() {
        return new Object[][]{
                {createOrgWithFieldValue("organization_type", "invalid_type"), "Invalid organization_type"},
                {createOrgWithFieldValue("application_group_list", "[\"invalid_app\"]"), "Invalid application_group"},
                {createOrgWithFieldValue("dialing_code_id", "9999"), "Invalid dialing_code_id"}
        };
    }

    // Duplicate Data Validation
    @DataProvider(name = "duplicateData")
    public static Object[][] duplicateData() {
        String duplicateName = "Duplicate Org ABCDE";
        String duplicateEmail = TestData.generateRandomEmail();
        String duplicatePhone = TestData.generateRandomMobile();

        return new Object[][]{
                {createOrgWithName(duplicateName), "Duplicate organization name"},
                {createOrgWithEmail(duplicateEmail), "Duplicate email"},
                {createOrgWithPhone(duplicatePhone), "Duplicate phone"}
        };
    }

    // Special Character Handling
    @DataProvider(name = "specialCharactersData")
    public static Object[][] specialCharactersData() {
        return new Object[][]{
                {createOrgWithFieldValue("name", "O'Reilly & Sons"), "Name with apostrophe and ampersand"},
                {createOrgWithFieldValue("name", "Tech-Solutions Inc"), "Name with hyphen"},
                {createOrgWithFieldValue("address", "123, Main St. #Apt-4B"), "Address with special chars"},
                {createOrgWithFieldValue("name", "Company (Pvt) Ltd"), "Name with parentheses"}
        };
    }

    // Helper methods - UPDATED with correct field lengths
    private static Map<String, Object> createValidOrganizationData(String orgName) {
        Map<String, Object> orgData = new HashMap<>();
        orgData.put("name", orgName);
        orgData.put("organization_id", TestData.generateOrganizationId());
        orgData.put("address", "123, Main Street, Test Area");
        orgData.put("mandal_or_taluk", "Test Taluk");
        orgData.put("country_id", "IN");
        orgData.put("state_id", "4012");
        orgData.put("city_id", "133607");
        orgData.put("pincode", "500032");
        orgData.put("email", EncryptApi.encryptEmail(TestData.generateRandomEmail()));
        orgData.put("phone", EncryptApi.encryptMobile(TestData.generateRandomMobile()));
        orgData.put("dialing_code_id", "IN");
        orgData.put("organization_type", "5");
        orgData.put("registration_number", "36AAICD2619K1ZI");
        orgData.put("document_number", "AAICD2619K");
        orgData.put("name_as_per_document", orgName);
        orgData.put("application_group_list", "[\"auto_doc_signer\"]");
        return orgData;
    }



    private static Map<String, Object> createOrgWithName(String name) {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put("name", name);
        return orgData;
    }

    private static Map<String, Object> createOrgWithEmail(String email) {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put("email", email);
        return orgData;
    }

    private static Map<String, Object> createOrgWithPhone(String phone) {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put("phone", phone);
        return orgData;
    }

    private static Map<String, Object> createOrgWithPincode(String pincode) {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put("pincode", pincode);
        return orgData;
    }

    private static Map<String, Object> createOrgWithFieldLength(String field, int length) {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put(field, generateLongString(length));
        return orgData;
    }

    private static Map<String, Object> createOrgWithFieldValue(String field, String value) {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put(field, value);
        return orgData;
    }

    private static Map<String, Object> createOrgWithoutField(String field) {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.remove(field);
        return orgData;
    }

    private static Map<String, Object> createOrgWithXSS() {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put("name", "<script>alert('xss')</script>");
        orgData.put("address", "<img src=x onerror=alert('xss')>");
        return orgData;
    }

    private static Map<String, Object> createOrgWithSQLInjection() {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put("name", "'; DROP TABLE organizations; --");
        orgData.put("email", "test' OR '1'='1");
        return orgData;
    }

    private static Map<String, Object> createOrgWithSpecialChars() {
        Map<String, Object> orgData = createValidOrganizationData("Valid Org");
        orgData.put("name", "Org!@#$%^&*()");
        orgData.put("address", "Address with \n newline and \t tab");
        return orgData;
    }

    private static String generateLongString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("a");
        }
        return sb.toString();
    }
}