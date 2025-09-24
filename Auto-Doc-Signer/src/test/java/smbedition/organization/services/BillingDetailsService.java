package smbedition.organization.services;

import io.restassured.response.Response;
import smbedition.common.*;
import java.util.HashMap;
import java.util.Map;
import static smbedition.common.TokenManager.token;


public class BillingDetailsService {

    private static String orgId = TestData.getOrgId();
    private static String billingdetailsid;

    private static String getTokenOrLogin() {
        String token = TokenManager.get();
        if (token == null || token.trim().isEmpty()) {
            System.out.println("Token not found. Logging in to generate new token...");
        }
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


//    ==================  Add Billing Details ==================

public static Response addBillingDetails() {
    System.out.println("=====Add Billing Details API=====");
    ApiUtil.waitForNextRequest();
    System.out.println("organization Id:"+orgId);

    if (token == null || token.isEmpty()) {
        token = getTokenOrLogin();
    }
    System.out.println("Token:"+token);
    System.out.println("Organization Id:"+orgId);

    String email = EncryptApi.encryptEmailTokenOrgId(TestData.generateRandomEmail());
    String phone = EncryptApi.encryptMobileTokenOrgId(TestData.generateRandomMobile());
    System.out.println("Email:"+email);
    System.out.println("Phone:"+phone);
//    TestData.generateAddress()
    Map<String, Object> body = new HashMap<>();
    body.put("organization_name", "DDI-2");
    body.put("sales_tax_number", "TAX112");
    body.put("address_line_1", "Plot No. 77, Road No. 36");
    body.put("address_line_2", "Lumbini Layout, Gachibowli, Hyderabad");
    body.put("country_id", "IN");
    body.put("state_id", "4012");
    body.put("city_id", "133607");
    body.put("pincode", "500032");
    body.put("email", email);
    body.put("phone", phone);
    body.put("dialing_code_id", "IN");
    body.put("contact_person_name", "KGP");

    Response response = ApiClient.post(
            "org.addbillingdetails",
                        body,
                        token,
                        orgId
                        );
    System.out.println("Response Status Code: " + response.getStatusCode());
    response.prettyPrint();
      return response;
}

    public  static Response getbillingDetails(){
        System.out.println("=====get Billing Details API=====");
//    ApiUtil.waitForNextRequest();
        System.out.println("Organization Id:"+orgId);
        System.out.println("Auth Token"+token);
        Response response = ApiClient.get("org.getbillingdetails",orgId,token);
        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();
        billingdetailsid = response.jsonPath().getString("data[0].id");
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
    body.put("email", EncryptApi.encryptMobileTokenOrgId(TestData.generateRandomEmail()));
    body.put("phone", EncryptApi.encryptMobileTokenOrgId(TestData.generateRandomMobile()));
    body.put("dialing_code_id", "IN");
    body.put("contact_person_name", "KGH");

    Response response = ApiClient.post(
            "org.updatebillingdetails",
            billingdetailsid,
            body,
            token,
            orgId
    );
    System.out.println("Response Status Code: " + response.getStatusCode());
    response.prettyPrint();

    return response;
}


    public static Response licensePaymentTest(){
        System.out.println("=====License Payment Test  API=====");
        ApiUtil.waitForNextRequest();
        System.out.println("organization Id:"+orgId);

        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        System.out.println("Token:"+token);
        System.out.println("Organization Id:"+orgId);
        System.out.println("Billing Details Id:"+billingdetailsid);

//    TestData.generateAddress()
        Map<String, Object> body = new HashMap<>();
        body.put("billing_details_id", billingdetailsid);
//        body.put("request_plan", "new");
//        body.put("region", "india");
//        body.put("desktop_type", "web");
//        body.put("plan_type", "basic");
//        body.put("license_period", 12);
//        body.put("add_ons_quantity", 1);

        body.put("request_plan", "new");
        body.put("region", "india");
        body.put("desktop_type","web");
        body.put("plan_type","unlimited");
        body.put("license_period",12);
        body.put("add_ons_quantity",0);


        Response response = ApiClient.post(
                "doc.licensepaymenttest",
                body,
                token,
                orgId
        );
        System.out.println("Response Status Code: " + response.getStatusCode());

        response.prettyPrint();
        return response;
    }


    public static Response licensePaymentAPI(){
        System.out.println("=====License Payment Test  API=====");
        ApiUtil.waitForNextRequest();
        System.out.println("organization Id:"+orgId);

        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        System.out.println("Token:"+token);
        System.out.println("Organization Id:"+orgId);
        System.out.println("Billing Details Id:"+billingdetailsid);

//    TestData.generateAddress()
        Map<String, Object> body = new HashMap<>();
        body.put("billing_details_id", billingdetailsid);
//        body.put("request_plan", "new");
//        body.put("region", "india");
//        body.put("desktop_type", "web");
//        body.put("plan_type", "basic");
//        body.put("license_period", 12);
//        body.put("add_ons_quantity", 1);

        body.put("request_plan", "new");
        body.put("region", "india");
        body.put("desktop_type","api");
        body.put("plan_type","unlimited"); // basic/premium/unlimited
        body.put("license_period",12);
        body.put("add_ons_quantity",0);

        Response response = ApiClient.post(
                "doc.licensepaymenttest",
                body,
                token,
                orgId
        );
        System.out.println("Response Status Code: " + response.getStatusCode());

        response.prettyPrint();
        return response;
    }



    public static Response licensePaymentWindows(){
        System.out.println("=====License Payment Test Windows =====");
        ApiUtil.waitForNextRequest();
        System.out.println("organization Id:"+orgId);

        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }
        System.out.println("Token:"+token);
        System.out.println("Organization Id:"+orgId);
        System.out.println("Billing Details Id:"+billingdetailsid);

//    TestData.generateAddress()
        Map<String, Object> body = new HashMap<>();
        body.put("billing_details_id", billingdetailsid);
//        body.put("request_plan", "new");
//        body.put("region", "india");
//        body.put("desktop_type", "web");
//        body.put("plan_type", "basic");
//        body.put("license_period", 12);
//        body.put("add_ons_quantity", 1);

        body.put("request_plan", "new");
        body.put("region", "india");
        body.put("desktop_type","windows");// windows/linux/mac/web/api
        body.put("plan_type","Unlimited"); // basic/premium/unlimited
        body.put("license_period",12);
        body.put("add_ons_quantity",0);

        Response response = ApiClient.post(
                "doc.licensepaymenttest",
                body,
                token,
                orgId
        );
        System.out.println("Response Status Code: " + response.getStatusCode());

        response.prettyPrint();
        return response;
    }

}