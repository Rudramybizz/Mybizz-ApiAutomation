package smbedition.docsigner.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.docsigner.services.OrganizationService;

public class PaymentgetwayTest {

    @Test(priority = 1, description = "")
    public void getOrg_User_Functionalities() {
        Response response = OrganizationService.getOrganizationUserFunctionalities();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }



}
