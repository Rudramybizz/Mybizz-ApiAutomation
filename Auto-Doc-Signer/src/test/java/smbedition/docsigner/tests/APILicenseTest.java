//package smbedition.docsigner.tests;
//
//import io.qameta.allure.*;
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import smbedition.common.BaseTest;
//import smbedition.docsigner.services.ApiLicenseService;
//
//@Epic("API License APIs")
//@Feature("Get & Upload document in API.")
//public class APILicenseTest extends BaseTest {
//
//    @Test(priority = 1)
//    @Story(" Manage API Credential.")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Manage API Credentials.")
//    public void manageApi_Credentials() {
//        Response response = ApiLicenseService.manageApiCredentials();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//       Allure.addAttachment("Manage API Credentials Response", response.getBody().asPrettyString());
//
//     }
//
//    @Test(priority = 2)
//    @Story("Get API Credentials .")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Get API Credentials")
//    public void getApi_Credentials() {
//        Response response = ApiLicenseService.getApiCredentials();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Get API Credentials Response", response.getBody().asPrettyString());
//    }
//
//
//
//    @Test(priority = 3)
//    @Story("Generate API Token .")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Generate API Token")
//    public void  generateApi_Token(){
//        Response response = ApiLicenseService.generateApiToken();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Generate API token Response", response.getBody().asPrettyString());
//    }
//
//
//    @Test(priority = 4)
//    @Story(" Upload Digital Signature with API Key.")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description( "Upload Digital Signature with API Key")
//    public void uploadDigitalSignaturewithApiKey(){
//        Response response = ApiLicenseService.uploadDigitalSignatureApiKey();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Upload Digital Signature with API Key Response", response.getBody().asPrettyString());
//    }
//
//    @Test(priority = 5)
//    @Story("Get Digital Signature with API Key.")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Get Digital Signature with API Key")
//    public void get_DigitalSignature_ApiKey(){
//        Response response = ApiLicenseService.getDigitalSignatureApiKey();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Get Digital Signature with API Key Response", response.getBody().asPrettyString());
//    }
//
//    @Test(priority = 6)
//    @Story("Get API Log .")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Get API Log")
//    public void get_api_Log(){
//        Response response = ApiLicenseService.getapiLog();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Get API Log Response", response.getBody().asPrettyString());
//    }
//
//
//    @Test(priority = 7)
//    @Story(" Doc to Base API .")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Public API Docto Base")
//    public void publicApi_Docto_Base(){
//        Response response = ApiLicenseService.publicApiDoctoBase();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Public API Docto Base API Key Response", response.getBody().asPrettyString());
//    }
//
//
//    @Test(priority = 8)
//    @Story(" Sign Document API .")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Sign Document API")
//    public void signDocument(){
//        Response response = ApiLicenseService.signdocumentApiToken();
//        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//        Allure.addAttachment(" Sign on Document  Response", response.getBody().asPrettyString());
//    }
//
//
//    @Test(priority = 9)
//    @Story(" Base to Doc API .")
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Public API Base to Doc")
//    public void publicApi_Baseto_Doc(){
//        Response response = ApiLicenseService.publicApiBasetoDoc();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Allure.addAttachment("Public API Base to Doc API Key Response", response.getBody().asPrettyString());
//    }
//
//
//
//
//
//
//
//    // @Test(priority = 8)
////    @Story("Delete Digital Signature API Key.")
////    @Severity(SeverityLevel.CRITICAL)
////    @Description("Delete Digital Signature with API Key")
////    public void delete_DigitalSignature_ApiKey(){
////        Response response = ApiLicenseService.deleteDigitalSignatureApiKey();
////        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
////        Allure.addAttachment("Delete Digital Signature with API Key Response", response.getBody().asPrettyString());
////    }
////
////
//
//
//}