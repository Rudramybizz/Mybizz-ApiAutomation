package smbedition.docsigner.services;

import io.restassured.response.Response;
import smbedition.common.TestData;
import smbedition.common.TokenManager;
import smbedition.common.ApiClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static smbedition.common.TokenManager.token;

public class PaymentgetwayService {

    private static String orgId = TestData.getOrgId();
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


//    =============================================================

    public  static Response addBillingdetails(){
            if (token == null || token.isEmpty()) {
                token = getTokenOrLogin();
            }

            Map<String, Object> body = new HashMap<>();
            body.put("organization_name","DDI-2");
            body.put( "sales_tax_number", "TAX112");
            body.put("address_line_1", "4A, 4th Floor, Spaces & More 2, Plot No 98 & 99, Akshara Grand");
            body.put("address_line_2" , "Lumbini Layout, Gachibowli, Hyderabad");
            body.put("country_id", "IN");
            body.put("state_id", "4012");
            body.put("city_id", "133607");
            body.put("pincode", "500032");
            body.put( "email", "gvs6623@gmail.com");
            body.put( "phone", "9912135936");
            body.put("dialing_code_id", "IN");
            body.put("contact_person_name", "KGP");


            System.out.println("Remove User Body: " + body);
            Response response = ApiClient.patch(
                    "doc.addbillingdetails",
                    body,
                    token,
                    orgId
            );


            response.prettyPrint();
            return response;

    }


    public static Response getBillingdetails() {

        String orgId = TestData.getOrgId();
            if (token == null || token.isEmpty()) {
                token = getTokenOrLogin();
            }
            Response response = ApiClient.get
                    (
                            "doc.getbilldetails",
                            token,
                            orgId
                    );
            logResponse("GetRoleSingle", response);
            return response;
        }




    public  static Response updateBillingdetails(){
        if (token == null || token.isEmpty()) {
            token = getTokenOrLogin();
        }

        Map<String, Object> body = new HashMap<>();
        body.put("organization_name","DDI-2");
        body.put( "sales_tax_number", "TAX112");
        body.put("address_line_1", "4A, 4th Floor, Spaces & More 2, Plot No 98 & 99, Akshara Grand");
        body.put("address_line_2" , "Lumbini Layout, Gachibowli, Hyderabad");
        body.put("country_id", "IN");
        body.put("state_id", "4012");
        body.put("city_id", "133607");
        body.put("pincode", "500032");
        body.put( "email", "gvs6623@gmail.com");
        body.put( "phone", "9912135936");
        body.put("dialing_code_id", "IN");
        body.put("contact_person_name", "KGP");


        System.out.println("Remove User Body: " + body);
        Response response = ApiClient.patch(
                "doc.updatebillingdetails",
                body,
                token,
                orgId
        );


        response.prettyPrint();
        return response;

    }










}
