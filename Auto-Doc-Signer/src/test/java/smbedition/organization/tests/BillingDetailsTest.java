package smbedition.organization.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.organization.services.BillingDetailsService;
import smbedition.organization.services.OrgUserService;

public class BillingDetailsTest {

    @Test(priority = 1, description = "Get Billing Details API")
    public void get_billingdetails() {
        Response response = BillingDetailsService.getbillingDetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 2, description = "Add Billing Details API")
    public void add_billingdetails() {
        Response response = BillingDetailsService.addBillingDetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 3, description = "Update Billing Details API")
    public void update_billingdetails() {
        Response response = BillingDetailsService.updateBillingDetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }






}
