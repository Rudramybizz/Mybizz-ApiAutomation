package smbedition.authentication.services;


import io.restassured.response.Response;
import smbedition.common.ApiClient;

import java.util.Map;

public class DropdownsService {

    public static Response getCountries() {
        Response response = ApiClient.get("doc.getcountriesdropdown");
        return response;
    }
    // ===================== States API =====================
    public static Response getStates(String countryId) {
        Response response = ApiClient.getWithQuery("doc.getStatesdropdowns",
                Map.of("country_id", countryId));
        return response;
    }


    // ===================== Cities API =====================
    public static Response getCities(String stateId) {
        Response response = ApiClient.getWithQuery("doc.getCitiesdropdowns",
                Map.of("state_id", stateId));
        return response;
    }


    // ===================== Utility Methods =====================
    public static String getCountryIdByName(Response countriesResponse, String countryName) {
        return countriesResponse.jsonPath().getString("data.find { it.name == '" + countryName + "' }.id");
    }

    public static String getStateIdByName(Response statesResponse, String stateName) {
        return statesResponse.jsonPath().getString("data.find { it.name == '" + stateName + "' }.id");
    }


    // ===================== Profile Settings API =====================
    public static Response getProfileSettings() {
        Response response = ApiClient.get("doc.getProfileSettings");
        return response;
    }

    // ===================== Languages API =====================
    public static Response getLanguages() {
        Response response = ApiClient.get("doc.languagedropdown");
        return response;
    }

    // ===================== Dialing Codes API =====================
    public static Response getDialingCodes() {
        Response response = ApiClient.get("doc.dialingcodedropdown");
        return response;
    }

    // ===================== Currencies API =====================
    public static Response getCurrencies() {
        Response response = ApiClient.get("doc.currencydropdown");
        return response;
    }




}