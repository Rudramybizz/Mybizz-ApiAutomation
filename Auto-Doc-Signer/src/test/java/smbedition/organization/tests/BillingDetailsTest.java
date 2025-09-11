package smbedition.organization.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.organization.services.BillingDetailsService;

public class BillingDetailsTest {

    @Test(priority = 1, description = "Add Billing Details API")
    public void add_billingdetails() {
        Response response = BillingDetailsService.addBillingDetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 2, description = "Get Billing Details API")
    public void get_billingdetails() {
        Response response = BillingDetailsService.getbillingDetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

//    @Test(priority = 3, description = "Update Billing Details API")
    public void update_billingdetails() {
        Response response = BillingDetailsService.updateBillingDetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }



    @Test(priority = 4, description = "License Payment Test API")
    public void license_Payment_Test(){
        Response response = BillingDetailsService.licensePaymentTest();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 5, description = "License Payment API Subscription")
    public void license_Payment_APISubscription(){
        Response response = BillingDetailsService.licensePaymentAPI();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }


}