//package smbedition.docsigner.tests;
//
//import io.qameta.allure.*;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import smbedition.docsigner.services.LicenseService;
//import smbedition.docsigner.services.OrganizationService;
//
//@Epic("License APIs")
//@Feature("Buy License .")
//public class LicenseTest {
//
//    @Test(priority = 1)
//    @Story("Get License Information.")
//    @Severity(SeverityLevel.MINOR)
//    @Description("Generate License Info API")
//    public void getLicense_Info() {
//        Response response = LicenseService.getLicenseInfo();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Generate License Info API Response", response.getBody().asPrettyString());
//    }
//
//    @Test(priority = 2)
//    @Story("Get License Invoice.")
//    @Severity(SeverityLevel.MINOR)
//    @Description("Generate License Invoice API")
//    public void getLicense_Invoice(){
//        Response response = LicenseService.getLicenseInvoice();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment(" Generate License Invoice API Response", response.getBody().asPrettyString());
//    }
//
//
//}
