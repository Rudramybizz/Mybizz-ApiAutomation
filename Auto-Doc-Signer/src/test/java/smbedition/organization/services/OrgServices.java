package smbedition.organization.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import smbedition.authentication.services.AuthApi;
import smbedition.common.*;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static smbedition.common.TokenManager.token;


public class OrgServices {

    private static List<String> statesList = new ArrayList<>();
    private static List<String> citiesList = new ArrayList<>();

    public static Map<String, String> dateFormats = new HashMap<>();
    public static Map<String, String> numberFormats = new HashMap<>();
    private static Map<String, String> exchangeRates;

    private static String physicalYearId;
    private static String organizationId;
    private static String orgId;

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



//===============Get Organization API=======================
    public static Response get_Organization_general() {

        if(token == null || token.isEmpty()) {
            System.out.println("Token is null or empty. Attempting to log in...");
            token = getTokenOrLogin();
        }
        System.out.println(token);
        System.out.println("===== Get Organization General API =====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("org.getorganization.general",token);

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }


    public static Response get_Organization_countrySpecific(){
        System.out.println("====Get Organization CountrySpecific API=======");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("org.getorganization.country_specific",token);
        statesList = response.jsonPath().getList("states.id");
        System.out.println("Response Status Code :"+response.getStatusCode());
        response.prettyPrint();

        return  response;

    }

//    === helper method to get state IDs
public static List<String> getStateIds() {
    return statesList;
}


    public static Response get_Organization_stateSpecific()
    {
        System.out.println("====Get Organization  State Specific API=====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("org.getorganization.state_specific",token);

        System.out.println("Response Status Code: "+ response.getStatusCode());
        citiesList= response.jsonPath().getList("cities.id");
        response.prettyPrint();
        return response;
    }

//    === helper method to get city IDs
public static List<String> getCityIds() {
    return citiesList;
}


    public static Response get_Org_List(){
        System.out.println("====Get Organization List API=====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("org.getorganization.OrgList",token);
        System.out.println("Response Status Code: " + response.getStatusCode());
        orgId = response.jsonPath().getString("data[0].id");
        ApiUtil.waitForNextRequest();
        System.out.println(orgId);
        TestData.setOrgId(orgId);
        TestData.setOrgName(response.jsonPath().getString("data[0].name"));
        response.prettyPrint();

        return response;
    }


    public static Response get_fiscalYear(){
        System.out.println("=====get fiscal year API=====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("org.getorganization.fiscalyear",token);
        System.out.println("Response Status Code: " + response.getStatusCode());
        if (response.getStatusCode() == 200) {
            dateFormats = response.jsonPath().getMap("data.date_formats");
            numberFormats = response.jsonPath().getMap("data.number_formats");
        }
        exchangeRates = response.jsonPath().getMap("data.exchange_rates");

        System.out.println("Stored Fiscal Year Config in variables:");
        System.out.println("Date Formats: " + dateFormats);
        System.out.println("Number Formats: " + numberFormats);
        System.out.println("Exchange Rates: " + exchangeRates);

        response.prettyPrint();
        return response;
    }


    //=============== Create Organization API ==================
public static Response createOrganization() {

    String token = TokenManager.get();
    System.out.println("token used in createOrg: " + TokenManager.get());
    System.out.println("===== Create Organization API =====");
    String randomemail = TestData.generateRandomEmail();
    String randomphoneno = TestData.generateRandomMobile();
    System.out.println("Random Email:"+randomemail);
    System.out.println("Random Phone Number :"+randomphoneno);
    ApiUtil.waitForNextRequest();

    // Auto-generated TestData values
    String name = TestData.generateOrganizationName();
    System.out.println("Organization Name:"+name);
    String organizationId = TestData.generateOrganizationId();
    System.out.println("Organization ID: " + organizationId);
    String address = TestData.generateAddress();
    System.out.println("Address: " + address);
    String mandalOrTaluk = TestData.generateMandalOrTaluk();
    System.out.println("Mandal or Taluk: " + mandalOrTaluk);
    String countryId = "IN";
    System.out.println("Country ID: " + countryId);
    String stateId = "4012";
//            TestData.getRandomStateId();
    System.out.println("State ID: " + stateId);
    String cityId = "133607";
//            TestData.getRandomCityId();
    System.out.println("City ID: " + cityId);
    String pincode = TestData.generatePincode();
    System.out.println("Pincode: " + pincode);
    String email = EncryptApi.encryptEmailToken(randomemail);
    String phone =EncryptApi.encryptMobileToken(randomphoneno);
    String dialingCodeId = "IN";
    System.out.println("Dialing Code ID: " + dialingCodeId);
    String organizationType = TestData.generateOrganizationType();
    System.out.println("Organization Type: " + organizationType);
    String registrationNumber = TestData.generateRegistrationNumber();
    System.out.println("Registration Number: " + registrationNumber);
    String documentNumber = TestData.generateDocumentNumber();
    System.out.println("Document Number: " + documentNumber);
    String nameAsPerDocument = TestData.generateOrganizationName();
    System.out.println("Name as per Document: " + nameAsPerDocument);
    String application_group_list = "[\"auto_doc_signer\"]";

    // Build JSON Body
    StringBuilder bodyBuilder = new StringBuilder();
    bodyBuilder.append("{\n")
            .append("  \"name\": \"").append(name).append("\",\n")
            .append("  \"organization_id\": \"").append(organizationId).append("\",\n")
            .append("  \"address\": \"").append(address).append("\",\n")
            .append("  \"mandal_or_taluk\": \"").append(mandalOrTaluk).append("\",\n")
            .append("  \"country_id\": \"").append(countryId).append("\",\n")
            .append("  \"state_id\": \"").append(stateId).append("\",\n")
            .append("  \"city_id\": \"").append(cityId).append("\",\n")
            .append("  \"pincode\": \"").append(pincode).append("\",\n")
            .append("  \"email\": \"").append(email).append("\",\n")
            .append("  \"phone\": \"").append(phone).append("\",\n")
            .append("  \"dialing_code_id\": \"").append(dialingCodeId).append("\",\n")
            .append("  \"organization_type\": \"").append(organizationType).append("\",\n")
            .append("  \"registration_number\": \"").append(registrationNumber).append("\",\n")
            .append("  \"document_number\": \"").append(documentNumber).append("\",\n")
            .append("  \"name_as_per_document\": \"").append(nameAsPerDocument).append("\",\n")
            .append("  \"application_group_list\": \"[\\\"auto_doc_signer\\\"]\"\n")
            .append("}");
    String body = bodyBuilder.toString();

    Response response = ApiClient.post("org.createorganization", body, token);

    System.out.println("=== Request Body ===");
    System.out.println(body);
    System.out.println("====================");
    System.out.println("Response Status Code: " + response.getStatusCode());
    response.prettyPrint();

    return response;
}



    //Get Organization List API
    public static Response getorganization_List(){
        System.out.println("=====Get Organization List API=====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("org.getorganization_Sucess_List", token);
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();
        return response;
    }

    public static Response getorganization_Single(){
        System.out.println("=====Get Organization Single API=====");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("org.getorganizationSucess_Single", token);
        organizationId = response.jsonPath().getString("data.id");
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();
        return response;
    }


    public static Response orgfiscalyearSucess_create(String defaultDateFormat, String numberFormat) {
        System.out.println("===== Create Fiscal Year API =====");
        ApiUtil.waitForNextRequest();

        String timezoneId = "Asia/Kolkata";
        String maintainBooksFrom = "01/04/2024";
        String defaultLanguageId = "en-US";
        String defaultCurrencyId = "INR";
        boolean allowMultiCurrency = false;
        String manageExchangeRates = "not_applicable";
        boolean allowDecimals = true;
        String noOfDecimalsAllowed = "2";
        String fiscalYearStartMonth = "01";
        String fiscalYearStartDay = "01";
        String fiscalYearEndMonth = "12";
        String fiscalYearEndDay = "31";

        String body = "{\n" +
                "  \"default_date_format\": \"" + defaultDateFormat + "\",\n" +
                "  \"timezone_id\": \"" + timezoneId + "\",\n" +
                "  \"maintain_books_of_accounts_from\": \"" + maintainBooksFrom + "\",\n" +
                "  \"default_language_id\": \"" + defaultLanguageId + "\",\n" +
                "  \"default_currency_id\": \"" + defaultCurrencyId + "\",\n" +
                "  \"allow_multi_currency_transactions\": " + allowMultiCurrency + ",\n" +
                "  \"manage_exchange_rates\": \"" + manageExchangeRates + "\",\n" +
                "  \"allow_decimals\": " + allowDecimals + ",\n" +
                "  \"no_of_decimals_allowed\": \"" + noOfDecimalsAllowed + "\",\n" +
                "  \"number_format\": \"" + numberFormat + "\",\n" +
                "  \"fiscal_year_start_month\": \"" + fiscalYearStartMonth + "\",\n" +
                "  \"fiscal_year_start_day\": \"" + fiscalYearStartDay + "\",\n" +
                "  \"fiscal_year_end_month\": \"" + fiscalYearEndMonth + "\",\n" +
                "  \"fiscal_year_end_day\": \"" + fiscalYearEndDay + "\"\n" +
                "}";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        headers.put(ApiClient.ORG_HEADER, orgId);
//        headers.put("Content-Type", "application/json");



        System.out.println("Organization id:"+orgId);
        System.out.println(token);

        Response response = ApiClient.post("org.fiscalyearsettings_create", body, token, orgId);


        System.out.println("=== Request Body ===");
        System.out.println(body);
        System.out.println("====================");
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }


//
//        //=============== Create Organization Fiscal Year API ==================
//        public static Response orgfiscalyearSucess_create() {
//            System.out.println("===== Create Fiscal Year API =====");
//
//            ApiUtil.waitForNextRequest();
//
//            String defaultDateFormat = "DD/MM/YYYY";
//            String timezoneId = "Asia/Kolkata";
//            String maintainBooksFrom = "01/04/2024";
//            String defaultLanguageId = "en-US";
//            String defaultCurrencyId = "INR";
//            boolean allowMultiCurrency = false;
//            String manageExchangeRates = "not_applicable";
//            boolean allowDecimals = true;
//            String noOfDecimalsAllowed = "2";
//            String numberFormat = "12,34,567.89";
//            String fiscalYearStartMonth = "04";
//            String fiscalYearStartDay = "01";
//            String fiscalYearEndMonth = "03";
//            String fiscalYearEndDay = "31";
//
//            String body = "{\n" +
//                    "  \"default_date_format\": \"" + defaultDateFormat + "\",\n" +
//                    "  \"timezone_id\": \"" + timezoneId + "\",\n" +
//                    "  \"maintain_books_of_accounts_from\": \"" + maintainBooksFrom + "\",\n" +
//                    "  \"default_language_id\": \"" + defaultLanguageId + "\",\n" +
//                    "  \"default_currency_id\": \"" + defaultCurrencyId + "\",\n" +
//                    "  \"allow_multi_currency_transactions\": " + allowMultiCurrency + ",\n" +
//                    "  \"manage_exchange_rates\": \"" + manageExchangeRates + "\",\n" +
//                    "  \"allow_decimals\": " + allowDecimals + ",\n" +
//                    "  \"no_of_decimals_allowed\": \"" + noOfDecimalsAllowed + "\",\n" +
//                    "  \"number_format\": \"" + numberFormat + "\",\n" +
//                    "  \"fiscal_year_start_month\": \"" + fiscalYearStartMonth + "\",\n" +
//                    "  \"fiscal_year_start_day\": \"" + fiscalYearStartDay + "\",\n" +
//                    "  \"fiscal_year_end_month\": \"" + fiscalYearEndMonth + "\",\n" +
//                    "  \"fiscal_year_end_day\": \"" + fiscalYearEndDay + "\"\n" +
//                    "}";
//
//            Map<String, String> headers = new HashMap<>();
//            headers.put("Authorization", "Bearer " + token);
//            headers.put("organization:", orgId);
//
//            Response response = ApiClient.post("org.fiscalyearsettings_create", headers, body);
//
//            System.out.println("=== Request Body ===");
//            System.out.println(body);
//            System.out.println("====================");
//            System.out.println("Response Status Code: " + response.getStatusCode());
//            response.prettyPrint();
//
//            return response;
//
//
//        }



    public static Response getfiscalyearSettings(){
        System.out.println("=====Get Fiscal Year Settings API=====");
        ApiUtil.waitForNextRequest();
        System.out.println("this is token inside getfiscalyearSettings method: " + token);
        System.out.println("Organization Id :"+orgId);
        System.out.println("First create the organization & organizartion fiscal year before calling this API.");

        Response response = ApiClient.get("org.fiscalyearsettings_get", token,orgId,true);

        physicalYearId = response.jsonPath().getString("data.id");
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();
        return response;
    }



    //=============== Update Organization Fiscal Year API ==================
    public static Response updateFiscalYear() {
        System.out.println("===== Update Fiscal Year API =====");

        ApiUtil.waitForNextRequest();

        String defaultDateFormat = "DD/MM/YYYY";
        String timezoneId = "Asia/Kolkata";
        String maintainBooksFrom = "01/04/2024";
        String defaultLanguageId = "en-US";
        String defaultCurrencyId = "INR";
        boolean allowMultiCurrency = false;
        String manageExchangeRates = "not_applicable";
        boolean allowDecimals = true;
        int noOfDecimalsAllowed = 3;
        String numberFormat = "12,34,567.89";
        int fiscalYearStartMonth = 4;
        int fiscalYearStartDay = 1;
        int fiscalYearEndMonth = 3;
        int fiscalYearEndDay = 31;

        String body = "{\n" +
                "  \"default_date_format\": \"" + defaultDateFormat + "\",\n" +
                "  \"timezone_id\": \"" + timezoneId + "\",\n" +
                "  \"maintain_books_of_accounts_from\": \"" + maintainBooksFrom + "\",\n" +
                "  \"default_language_id\": \"" + defaultLanguageId + "\",\n" +
                "  \"default_currency_id\": \"" + defaultCurrencyId + "\",\n" +
                "  \"allow_multi_currency_transactions\": " + allowMultiCurrency + ",\n" +
                "  \"manage_exchange_rates\": \"" + manageExchangeRates + "\",\n" +
                "  \"allow_decimals\": " + allowDecimals + ",\n" +
                "  \"no_of_decimals_allowed\": " + noOfDecimalsAllowed + ",\n" +
                "  \"number_format\": \"" + numberFormat + "\",\n" +
                "  \"fiscal_year_start_month\": " + fiscalYearStartMonth + ",\n" +
                "  \"fiscal_year_start_day\": " + fiscalYearStartDay + ",\n" +
                "  \"fiscal_year_end_month\": " + fiscalYearEndMonth + ",\n" +
                "  \"fiscal_year_end_day\": " + fiscalYearEndDay + "\n" +
                "}";

        String endpointKey = "org.fiscalyearsettings_update";
        String endpoint = ConfigLoader.getEndpoint(endpointKey) + physicalYearId;

//        String endpoint = ConfigLoader.getEndpoint("org.fiscalyearsettings_update") + physicalYearId;
        Response response = ApiClient.post("org.fiscalyearsettings_update", physicalYearId, body, token, orgId);
//        Response response = ApiClient.post(endpoint, body, token, orgId);

        System.out.println("Organization ID: " + orgId);
        System.out.println("Physical Year ID: " + physicalYearId);
        System.out.println("=== Request Body ===");
        System.out.println(body);
        System.out.println("====================");
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }


    //=============== Mark Organization as Default API ==================
    public static Response markOrganizationAsDefault(String instanceId) {
        System.out.println("===== Mark Organization as Default API =====");

        ApiUtil.waitForNextRequest();

        String body = "{\n" +
                "  \"instance_id\": \"" + instanceId + "\"\n" +
                "}";


        String endpoint = "org.markasdefault";

        Response response = ApiClient.patch(endpoint, token, body);

        System.out.println("=== Request Body ===");
        System.out.println(body);
        System.out.println("====================");
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }





}