//package smbedition.docsigner.tests;
//
//import io.qameta.allure.*;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import smbedition.common.BaseTest;
//import smbedition.docsigner.services.OrganizationService;
//import smbedition.docsigner.services.PaymentgetwayService;
//
//@Epic("Payment Getway Apis")
//@Feature("Make Payment, Buy License .")
//public class PaymentgetwayTest extends BaseTest {
//
//    @Test(priority = 1)
//    @Story("Create new Billing details.")
//    @Severity(SeverityLevel.MINOR)
//    @Description("Add billing details API")
//    public void addBilling_details() {
//        Response response = PaymentgetwayService.addBillingdetails();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Add Billing Details API Response", response.getBody().asPrettyString());
//    }
//
//    @Test(priority = 2)
//    @Story("Get Billing details.")
//    @Severity(SeverityLevel.MINOR)
//    @Description("Get billing details API")
//    public void getBilling_details() {
//        Response response = PaymentgetwayService.getBillingdetails();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Get Billing Details API Response", response.getBody().asPrettyString());
//    }
//
////    @Test(priority = 3, description = "Update billing details API")
//@Story("Update Billing details.")
//@Severity(SeverityLevel.MINOR)
//@Description("Update billing details API")
//    public  void updateBilling_details() {
//        Response response = PaymentgetwayService.updateBillingdetails();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//    Allure.addAttachment("Update Billing Details API Response", response.getBody().asPrettyString());
//
//}
//
//
//
//}
