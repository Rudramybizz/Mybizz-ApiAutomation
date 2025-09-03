package smbedition.authentication.services;

import io.restassured.response.Response;
import smbedition.common.TokenManager;
import smbedition.common.ApiClient;
import smbedition.common.ApiUtil;

import java.util.*;

public class DropdownsService {
    private static List<String> countryIds = new ArrayList<>();
    private static List<String> statesIds = new ArrayList<>();

    private static String getTokenOrLogin() {
        String token = TokenManager.get();

        if (token == null || token.trim().isEmpty()) {
            System.out.println("Token not found. Logging in to generate new token...");
             // performs login
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


    // ====================== Countries API ======================
    public static Response getCountries() {
        String token = getTokenOrLogin();
        ApiUtil.waitForNextRequest();
        System.out.println("===== Get Countries API =====");

        Response response = ApiClient.get("dropdown.countries", token);

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();
        countryIds = response.jsonPath().getList("data.id");
        System.out.println("Collected Country IDs: " + countryIds);

        return response;
    }



    // ====================== States API ======================
    public static List<Response> getStates() {
        String token = getTokenOrLogin();
        List<Response> responses = new ArrayList<>();

        if (countryIds == null || countryIds.isEmpty()) {
            throw new IllegalStateException("Countries not loaded. Run getCountries() first.");
        }

        System.out.println("===== Get States API =====");
        int limit = Math.min(3, countryIds.size());

        for (int i = 0; i < limit; i++) {
            String countryId = countryIds.get(i);

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("country_id", countryId);

            Response response = ApiClient.get("dropdown.states", token, queryParams);
            responses.add(response);

            System.out.println("Country ID: " + countryId);
            System.out.println("Response Status Code: " + response.getStatusCode());

            if (response.getStatusCode() == 200) {
                List<String> states = response.jsonPath().getList("data");
                if (states != null && !states.isEmpty()) {
                    statesIds = response.jsonPath().getList("data.id");
                    System.out.println("Collected States IDs: " + statesIds);
                } else {
                    System.out.println(" Country " + countryId + " does not have states.");
                }
            } else {
                System.out.println("Skipping parsing for Country " + countryId +
                        " because API returned " + response.getStatusCode());
                response.prettyPrint();
            }

            // 🔹 Delay before hitting the next country

        }

        return responses;
    }
    // ====================== Cities API ======================
    public static List<Response> getCities() {
        String token = getTokenOrLogin();
        ApiUtil.waitForNextRequest();
        List<Response> responses = new ArrayList<>();

        if (statesIds == null || statesIds.isEmpty()) {
            throw new IllegalStateException("States not loaded. Run getStates() first.");
        }

        System.out.println("============ Get Cities API ================");
        int limit = Math.min(3, statesIds.size());

        for (int i = 0; i < limit; i++) {
            String stateId = statesIds.get(i);

            Response response = ApiClient.get("dropdown.cities?state_id=" + stateId, token);
            responses.add(response);

            System.out.println("State ID: " + stateId);
            System.out.println("Response Status Code: " + response.getStatusCode());

            List<String> cities = response.jsonPath().getList("data");
            if (cities != null && !cities.isEmpty()) {
                System.out.println("Collected Cities IDs: " + response.jsonPath().getList("data.id"));
            } else {
                System.out.println(" State " + stateId + " does not have cities.");
            }

        }

        return responses;
    }


    public static Response getProfileSettings() {
        String token = getTokenOrLogin();
        ApiUtil.waitForNextRequest();
        System.out.println("===== Get ProfileSettings API =====");

        Response response = ApiClient.get("dropdown.profilesettings", token,true);

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }


    public static Response getLanguage(){
        String token = getTokenOrLogin();
        ApiUtil.waitForNextRequest();
        System.out.println("===============Get Language API ===========");
        Response response = ApiClient.get("dropdown.languages",token,true);
        System.out.println("Response Status Code :"+response.getStatusCode());
        response.prettyPrint();
        return response;
    }

    public static Response getDialingCode(){
        String token = getTokenOrLogin();
        ApiUtil.waitForNextRequest();
     System.out.println("===============Get Dialing Code API=============");
     Response response = ApiClient.get("dropdown.dialingcode",token,true);
     System.out.println("Response Status Code :"+response.getStatusCode());
     response.prettyPrint();
     return response;
    }

    public static Response getCurrency(){
        String token = getTokenOrLogin();
        System.out.println("===============Get Currency API ================");
        ApiUtil.waitForNextRequest();
        Response response = ApiClient.get("dropdown.currency",token,true);
        System.out.println("Response Status Code :"+response.getStatusCode());
        response.prettyPrint();
        return response;
    }


}