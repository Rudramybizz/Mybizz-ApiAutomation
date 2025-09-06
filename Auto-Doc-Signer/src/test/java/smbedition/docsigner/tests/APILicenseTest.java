package smbedition.docsigner.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.docsigner.services.ApiLicenseService;

public class APILicenseTest {

    @Test(priority = 1, description = "Manage API Credentials")
    public void manageApi_Credentials() {
        Response response = ApiLicenseService.manageApiCredentials();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 2, description = "Get API Credentials")
    public void getApi_Credentials() {
        Response response = ApiLicenseService.getApiCredentials();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 3, description = "Get API Log")
    public void get_api_Log(){
        Response response = ApiLicenseService.getapiLog();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 4, description = "Upload Digital Signature with API Key")
    public void uploadDigitalSignaturewithApiKey(){
        Response response = ApiLicenseService.uploadDigitalSignatureApiKey();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 5, description = "Get Digital Signature with API Key")
    public void get_DigitalSignature_ApiKey(){
        Response response = ApiLicenseService.getDigitalSignatureApiKey();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 6, description = "Delete Digital Signature with API Key")
    public void delete_DigitalSignature_ApiKey(){
        Response response = ApiLicenseService.deleteDigitalSignatureApiKey();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 7, description = "Public API Docto Base")
    public void publicApi_Docto_Base(){
        Response response = ApiLicenseService.publicApiDoctoBase();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 8, description = "Public API Base to Doc")
    public void publicApi_Baseto_Doc(){
        Response response = ApiLicenseService.publicApiDoctoBase();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 9, description = "Generate API Token")
    public void  generateApi_Token(){
        Response response = ApiLicenseService.generateApiToken();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }


}