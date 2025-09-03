package smbedition.docsigner.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.docsigner.services.OrganizationService;
import smbedition.docsigner.services.PaymentgetwayService;

public class PaymentgetwayTest {

    @Test(priority = 1, description = "Add billing details API")
    public void addBilling_details() {
        Response response = PaymentgetwayService.addBillingdetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 2, description = "Get billing details API")
    public void getBilling_details() {
        Response response = PaymentgetwayService.getBillingdetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 3, description = "Update billing details API")
    public  void updateBilling_details() {
        Response response = PaymentgetwayService.updateBillingdetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }



}
