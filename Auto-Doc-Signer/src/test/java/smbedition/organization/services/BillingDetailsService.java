package smbedition.organization.services;

import io.restassured.response.Response;
import smbedition.common.ApiUtil;
import smbedition.common.TestData;
import smbedition.common.TokenManager;
import smbedition.common.ApiClient;

import java.util.HashMap;
import java.util.Map;

import static smbedition.common.TokenManager.token;

public class BillingDetailsService {

    private static String orgId = TestData.getOrgId();


    private static String getTokenOrLogin() {

        if(orgId == null || orgId.isEmpty()){
            System.out.println("OrgId not found....");
//            orgId = TestData.getOrgId();
        }
        System.out.println("organization Id:"+orgId);

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


//    ==================  Get Billing Details ==================

public  static Response getbillingDetails(){
    System.out.println("=====get Billing Details API=====");
//    ApiUtil.waitForNextRequest();
    System.out.println("Organization Id:"+orgId);
    System.out.println("Auth Token"+token);
    Response response = ApiClient.get("org.getbillingdetails",orgId,token);
    System.out.println("Response Status Code: " + response.getStatusCode());
    response.prettyPrint();
    return response;
}



public static Response addBillingDetails() {
    System.out.println("=====Add Billing Details API=====");
//    TestData.generateAddress()
    if (token == null || token.isEmpty()) {
        token = getTokenOrLogin();
    }
    String organization_name = TestData.generateOrganizationName();
    Map<String, Object> body = new HashMap<>();
    body.put("organization_name","DDI-2");
    body.put("sales_tax_number","TAX112");
    body.put("address_line_1","Plot No. 77, Road No. 36");
    body.put("address_line_2", "Lumbini Layout, Gachibowli, Hyderabad");
    body.put("country_id","IN");
    body.put("state_id", "4012");
    body.put("city_id","133607");
    body.put("pincode", "500032");
    body.put( "email", "gvs6623@gmail.com");
    body.put("phone", "9912135936");
    body.put("dialing_code_id", "IN");
    body.put("contact_person_name","KGP");

    Response response = ApiClient.post("org.addbillingdetails", body, orgId, token);
    System.out.println("Response Status Code: " + response.getStatusCode());
    response.prettyPrint();
      return response;
}



public static Response updateBillingDetails() {
    System.out.println("=====Update Billing Details API=====");
    if (token == null || token.isEmpty()) {
        token = getTokenOrLogin();
    }
    String organization_name = TestData.generateOrganizationName();
    Map<String, Object> body = new HashMap<>();
    body.put("organization_name", "DDI-2");
    body.put("sales_tax_number", "TAX112");
    body.put("address_line_1", TestData.generateAddress());
    body.put("address_line_2", "Lumbini Layout, Gachibowli, Hyderabad");
    body.put("country_id", "IN");
    body.put("state_id", "4012");
    body.put("city_id", "133607");
    body.put("pincode", "500032");
    body.put("email", TestData.generateRandomEmail());
    body.put("phone", TestData.generateRandomMobile());
    body.put("dialing_code_id", "IN");
    body.put("contact_person_name", "KGH");

    Response response = ApiClient.post("org.updatebillingdetails", body, orgId, token);
    System.out.println("Response Status Code: " + response.getStatusCode());
    response.prettyPrint();

    return response;
}

//
//public static Response billingDetails(){
//
//}





}