package smbedition.organization.services;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import smbedition.common.ApiClient;

import java.util.HashMap;
import java.util.Map;

public class OrgServices {


//    ====================Create Organization ====================

    public static Response createOrganization(Map<String, Object> body) {
        Response response = ApiClient.ssoPost("org.createorganization", body);
        response.prettyPrint();
        return response;
    }


    // ===================== Organization General API =====================
    public static Response getOrganizationGeneral() {
        Response response = ApiClient.get("org.getorganization.general");
        response.prettyPrint();
        return response;
    }

    // ===================== Organization Country Specific API =====================
    public static Response getOrganizationCountrySpecific(String countryId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("country_id", countryId);
        queryParams.put("purpose", "country_specific");

        Response response = ApiClient.getWithQuery("org.getorganizationCountrySpecific", queryParams);
        response.prettyPrint();
        return response;
    }


    // ===================== Organization State Specific API =====================
    public static Response getOrganizationStateSpecific(String stateId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("state_id", stateId);
        queryParams.put("purpose", "state_specific");

        Response response = ApiClient.getWithQuery("org.getorganizationStateSpecific", queryParams);
        response.prettyPrint();
        return response;
    }
    // ===================== Organization List API =====================
    public static Response getOrganizationList() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("purpose", "list");

        Response response = ApiClient.getWithQuery("org.getorganizationList", queryParams);
        response.prettyPrint();
        return response;
    }

    public static Response createOrganizationFiscalYear(Map<String, Object> fiscalYearData) {
        Response response = ApiClient.ssoPostOrgId("org.createorganizationfiscalyear", fiscalYearData);
        response.prettyPrint();
        return response;
    }

    public static Response getorganization_fiscalyear(){
        Response response = ApiClient.get("org.getorganizationfiscalyear");
        response.prettyPrint();
        return response;
    }

    public static Response getorganization_List(){
        Response response = ApiClient.get("org.getorganization_List");
        response.prettyPrint();
        return  response;
    }

    public static  Response getorganization_single(){
        Response response = ApiClient.get("org.getorganization_single");
        response.prettyPrint();
        return response;
   }


    public static Response updateOrganizationFiscalYear(Map<String, Object> fiscalYearData) {
        Response response = ApiClient.ssoPost("org.updateorganizationfiscalyear", fiscalYearData);
        response.prettyPrint();
        return response;
    }

public static Response markas_default(){
        Response response = ApiClient.patch("org.markas_default");
        response.prettyPrint();
        return  response;
}




}