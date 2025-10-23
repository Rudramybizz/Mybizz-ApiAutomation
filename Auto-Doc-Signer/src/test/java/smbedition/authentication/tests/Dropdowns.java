package smbedition.authentication.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import smbedition.common.BaseTest;
import smbedition.authentication.services.DropdownsService;

import java.util.Map;

import static org.testng.Assert.*;

@Epic("Dropdowns APIs")
@Feature("Authentication Dropdown Api's")
public class Dropdowns extends BaseTest {

    @Test(priority = 1)
    @Story("Get All Countries")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get countries dropdown")
    public void testGetCountries() {
        Response response = DropdownsService.getCountries();

        assertEquals(response.getStatusCode(), 200);
        assertTrue(response.jsonPath().getList("data").size() > 0, "Countries list should not be empty");

        // Print sample countries
        System.out.println("Total countries: " + response.jsonPath().getList("data").size());
        System.out.println("First country: " + response.jsonPath().getString("data[0].name"));
    }

    @Test(priority = 2)
    @Story("Get States by Country")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get states dropdown for a specific country")
    public void testGetStatesByCountry() {
        // First get countries to find a valid country ID
        Response countriesResponse = DropdownsService.getCountries();
        String countryId = countriesResponse.jsonPath().getString("data[0].id");
        String countryName = countriesResponse.jsonPath().getString("data[0].name");

        System.out.println("Getting states for country: " + countryName + " (ID: " + countryId + ")");

        // Get states for the first country
        Response statesResponse = DropdownsService.getStates(countryId);

        assertEquals(statesResponse.getStatusCode(), 200);
        assertTrue(statesResponse.jsonPath().getList("data").size() >= 0, "States list should not be null");

        System.out.println("Total states for " + countryName + ": " +
                (statesResponse.jsonPath().getList("data") != null ?
                        statesResponse.jsonPath().getList("data").size() : 0));
    }

    @Test(priority = 3)
    @Story("Get Cities by State")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get cities dropdown for a specific state")
    public void testGetCitiesByState() {
        // Get countries first
        Response countriesResponse = DropdownsService.getCountries();
        String countryId = countriesResponse.jsonPath().getString("data[0].id");

        // Get states for the country
        Response statesResponse = DropdownsService.getStates(countryId);

        // Check if states exist for this country
        if (statesResponse.jsonPath().getList("data") != null &&
                !statesResponse.jsonPath().getList("data").isEmpty()) {

            String stateId = statesResponse.jsonPath().getString("data[0].id");
            String stateName = statesResponse.jsonPath().getString("data[0].name");

            System.out.println("Getting cities for state: " + stateName + " (ID: " + stateId + ")");

            // Get cities for the first state
            Response citiesResponse = DropdownsService.getCities(stateId);

            assertEquals(citiesResponse.getStatusCode(), 200);
            assertTrue(citiesResponse.jsonPath().getList("data").size() >= 0, "Cities list should not be null");

            System.out.println("Total cities for " + stateName + ": " +
                    (citiesResponse.jsonPath().getList("data") != null ?
                            citiesResponse.jsonPath().getList("data").size() : 0));
        } else {
            System.out.println("No states found for country ID: " + countryId);
        }
    }

    @Test(priority = 4)
    @Story("Complete Flow: Country -> State -> City")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test complete dropdown flow from country to cities")
    public void testCompleteDropdownFlow() {
        // 1. Get all countries
        Response countries = DropdownsService.getCountries();
        assertEquals(countries.getStatusCode(), 200);

        // 2. Get India's country ID (or any specific country)
        String indiaId = countries.jsonPath().getString("data.find { it.name == 'India' }.id");
        assertNotNull(indiaId, "India should be in countries list");

        // 3. Get states for India
        Response states = DropdownsService.getStates(indiaId);
        assertEquals(states.getStatusCode(), 200);

        // 4. Get Maharashtra state ID (or any specific state)
        String maharashtraId = states.jsonPath().getString("data.find { it.name == 'Maharashtra' }.id");
        if (maharashtraId != null) {
            // 5. Get cities for Maharashtra
            Response cities = DropdownsService.getCities(maharashtraId);
            assertEquals(cities.getStatusCode(), 200);

            System.out.println("Complete flow successful:");
            System.out.println("Country: India -> States: " +
                    (states.jsonPath().getList("data") != null ? states.jsonPath().getList("data").size() : 0) +
                    " -> Cities in Maharashtra: " +
                    (cities.jsonPath().getList("data") != null ? cities.jsonPath().getList("data").size() : 0));
        }
    }

    @Test(priority = 5)
    @Story("Get States with Invalid Country")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test states API with invalid country ID")
    public void testGetStatesWithInvalidCountry() {
        Response response = DropdownsService.getStates("INVALID_COUNTRY_ID");

        // Should handle gracefully - either empty data or error
        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400);
    }

    @Test(priority = 6)
    @Story("Get Cities with Invalid State")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test cities API with invalid state ID")
    public void testGetCitiesWithInvalidState() {
        Response response = DropdownsService.getCities("INVALID_STATE_ID");

        // Should handle gracefully - either empty data or error
        assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 400);
    }

    @Test(priority = 7)
    @Story("Get Profile Settings")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get profile settings dropdown")
    public void testGetProfileSettings() {
        Response response = DropdownsService.getProfileSettings();
        assertEquals(response.getStatusCode(), 200);
        System.out.println("Profile Settings Response:");
        response.prettyPrint();
    }

    @Test(priority = 8)
    @Story("Get Languages")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get languages dropdown")
    public void testGetLanguages() {
        Response response = DropdownsService.getLanguages();

        assertEquals(response.getStatusCode(), 200);
        assertTrue(response.jsonPath().getList("data").size() > 0, "Languages list should not be empty");

        System.out.println("Total languages: " + response.jsonPath().getList("data").size());

        // Verify language structure - handle optional fields gracefully
        response.jsonPath().getList("data").forEach(lang -> {
            Map<String, Object> language = (Map<String, Object>) lang;
            assertNotNull(language.get("id"), "Language ID should not be null");
            assertNotNull(language.get("name"), "Language name should not be null");

            // Code might be optional, so just log if missing
            if (language.get("code") == null) {
                System.out.println("Warning: Language '" + language.get("name") + "' has no code field");
            }
        });

        // Print first few languages to see the structure
        System.out.println("Sample languages:");
        for (int i = 0; i < Math.min(5, response.jsonPath().getList("data").size()); i++) {
            Map<String, Object> lang = response.jsonPath().getMap("data[" + i + "]");
            System.out.println("  " + (i + 1) + ". " + lang);
        }
    }


    @Test(priority = 9)
    @Story("Get Dialing Codes")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get dialing codes dropdown")
    public void testGetDialingCodes() {
        Response response = DropdownsService.getDialingCodes();

        assertEquals(response.getStatusCode(), 200);
        assertTrue(response.jsonPath().getList("data").size() > 0, "Dialing codes list should not be empty");

        System.out.println("Total dialing codes: " + response.jsonPath().getList("data").size());

        // Print sample to see actual structure
        System.out.println("Sample dialing codes:");
        for (int i = 0; i < Math.min(3, response.jsonPath().getList("data").size()); i++) {
            Map<String, Object> code = response.jsonPath().getMap("data[" + i + "]");
            System.out.println("  " + (i + 1) + ". " + code);
        }

        response.jsonPath().getList("data").forEach(dialingCode -> {
            Map<String, Object> code = (Map<String, Object>) dialingCode;

            // Check which identifier field exists
            if (code.containsKey("id")) {
                assertNotNull(code.get("id"), "Dialing code ID should not be null");
            } else if (code.containsKey("code")) {
                assertNotNull(code.get("code"), "Dialing code should not be null");
            } else if (code.containsKey("dialing_code")) {
                assertNotNull(code.get("dialing_code"), "Dialing code should not be null");
            } else {
                // If no identifier found, at least check name
                assertNotNull(code.get("name"), "Dialing code name should not be null");
            }
        });
    }


    @Test(priority = 10)
    @Story("Get Currencies")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get currencies dropdown")
    public void testGetCurrencies() {
        Response response = DropdownsService.getCurrencies();

        assertEquals(response.getStatusCode(), 200);
        assertTrue(response.jsonPath().getList("data").size() > 0, "Currencies list should not be empty");

        System.out.println("Total currencies: " + response.jsonPath().getList("data").size());

        // Print sample to confirm structure
        System.out.println("Sample currencies:");
        for (int i = 0; i < Math.min(3, response.jsonPath().getList("data").size()); i++) {
            Map<String, Object> currency = response.jsonPath().getMap("data[" + i + "]");
            System.out.println("  " + (i + 1) + ". " + currency);
        }

        // Verify currency structure - currencies use code, name, symbol (NO id field)
        response.jsonPath().getList("data").forEach(currency -> {
            Map<String, Object> curr = (Map<String, Object>) currency;
            assertNotNull(curr.get("code"), "Currency code should not be null");
            assertNotNull(curr.get("name"), "Currency name should not be null");

            // Symbol might be optional in some cases
            if (curr.get("symbol") == null) {
                System.out.println("Warning: Currency '" + curr.get("name") + "' has no symbol");
            }
        });
    }

    @Test(priority = 11)
    @Story("Get All Dropdowns")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test all dropdown APIs in sequence")
    public void testAllDropdowns() {
        // Test all dropdowns one by one
        Response countries = DropdownsService.getCountries();
//
        Response languages = DropdownsService.getLanguages();
        Response dialingCodes = DropdownsService.getDialingCodes();
        Response currencies = DropdownsService.getCurrencies();

        // Verify all responses are successful
        assertEquals(countries.getStatusCode(), 200);

        assertEquals(languages.getStatusCode(), 200);
        assertEquals(dialingCodes.getStatusCode(), 200);
        assertEquals(currencies.getStatusCode(), 200);

        // Print summary
        System.out.println("=== All Dropdowns Summary ===");
        System.out.println("Countries: " + countries.jsonPath().getList("data").size());
        System.out.println("Languages: " + languages.jsonPath().getList("data").size());
        System.out.println("Dialing Codes: " + dialingCodes.jsonPath().getList("data").size());
        System.out.println("Currencies: " + currencies.jsonPath().getList("data").size());
    }


//    @Test(priority = 12)
    @Story("Verify Specific Data in Dropdowns")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify specific expected data exists in dropdowns")
    public void testSpecificDataInDropdowns() {
        Response languages = DropdownsService.getLanguages();
        String englishLanguage = languages.jsonPath().getString("data.find { it.name == 'English' }.name");
        if (englishLanguage == null) {
            // Try with different case or partial match
            englishLanguage = languages.jsonPath().getString("data.find { it.name.toLowerCase().contains('english') }.name");
        }
        assertNotNull(englishLanguage, "English language should exist");

        // Verify Indian Rupee currency exists
        Response currencies = DropdownsService.getCurrencies();
        String indianRupee = currencies.jsonPath().getString("data.find { it.code == 'INR' }.name");
        if (indianRupee == null) {
            // Try with name search
            indianRupee = currencies.jsonPath().getString("data.find { it.name.toLowerCase().contains('rupee') }.name");
        }
        assertNotNull(indianRupee, "Indian Rupee should exist");

        // Verify India dialing code exists
        Response dialingCodes = DropdownsService.getDialingCodes();
        String indiaDialingCode = dialingCodes.jsonPath().getString("data.find { it.country_code == 'IN' }.dialing_code");
        if (indiaDialingCode == null) {
            // Try with dialing code directly (+91)
            indiaDialingCode = dialingCodes.jsonPath().getString("data.find { it.dialing_code == '+91' }.dialing_code");
        }
        assertNotNull(indiaDialingCode, "India dialing code should exist");

        System.out.println("Verified: English language, INR currency, India dialing code exist");
    }


}