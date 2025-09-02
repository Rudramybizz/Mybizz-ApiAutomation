package smbedition.docsigner.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.docsigner.services.OrganizationService;


public class OrganizationTest {

    @Test(priority = 1, description = "Generate OTp add user API")
public void getOrg_User_Functionalities() {
        Response response = OrganizationService.getOrganizationUserFunctionalities();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
}

@Test(priority = 2, description = "Get plan details API")
public void getPlan_Details(){
        Response response = OrganizationService.getPlanDetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
}

@Test(priority = 3, description = "Get plan add on API")
public void getAdd_On(){
        Response response = OrganizationService.getPlanAddOn();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }



}