package smbedition.docsigner.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.docsigner.services.LicenseService;
import smbedition.docsigner.services.OrganizationService;

public class LicenseTest {
    @Test(priority = 1, description = "Generate License Info API")
    public void getLicense_Info() {
        Response response = LicenseService.getLicenseInfo();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 2, description = "Generate License Invoice API")
    public void getLicense_Invoice(){
        Response response = LicenseService.getLicenseInvoice();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }


}
