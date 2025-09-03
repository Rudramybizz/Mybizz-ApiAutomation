package smbedition.docsigner.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.ApiUtil;
import smbedition.docsigner.services.OrganizationService;
import smbedition.docsigner.services.WebLicenseService;

public class WebLicenseTest {

    @Test(priority = 1, description = "Add Digital Signature Sucess API")
    public void addDigital_Signature() {
        Response response = WebLicenseService.addDigitalSignatureSucess();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 2, description = "Add Digital Sugnature API with wrong password")
    public void testAddDigitalSignature_WrongPassword() {
        Response response = WebLicenseService.addDigitalSignatureWithWrongPassword();
        Assert.assertEquals(response.getStatusCode(), 422);

    }
    @Test(priority = 3, description = "Add Digital Sugnature API with missing file")
    public void testAddDigitalSignature_MissingFile() {
        Response response = WebLicenseService.addDigitalSignatureWithMissingFile();
        Assert.assertEquals(response.getStatusCode(), 422);

    }

    @Test(priority = 4, description = "Add Digital Sugnature API with missing password")
    public void testAddDigitalSignature_MissingPassword() {
        Response response = WebLicenseService.addDigitalSignatureWithMissingPassword();
        Assert.assertEquals(response.getStatusCode(), 422);

    }



    @Test(priority = 5, description = "Get Digital Sugnature API")
    public void getDigital_Signature() {
        Response response = WebLicenseService.getDigitalSignature();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }



    @Test(priority = 6, description = "Delete Digital Sugnature API")
    public void deleteDigital_Signature() {
        Response response = WebLicenseService.deleteDigitalSignature();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        ApiUtil.waitForNextRequest();
        Response response1 = WebLicenseService.addDigitalSignatureSucess();
        System.out.println("New Signature Added After Deletion, Status Code: " + response1.getStatusCode());
    }


    @Test(priority = 7, description = "Add Signature API")
    public void addSignature(){
        Response response = WebLicenseService.addSignature();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

    @Test(priority = 8, description = "Get Signature Properties API")
    public void  getSignature_Properties(){
        Response response = WebLicenseService.getSignatureProperties();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }













}
