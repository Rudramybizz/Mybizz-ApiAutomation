package smbedition.organization.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.authentication.base.BaseTest;
import smbedition.organization.services.OTPService;
import smbedition.organization.services.OrgUserService;

public class OrgUserTest  {

    @Test(priority = 1, description = "Generate OTp add user API")
    public void organization_AddUser() {
        Response response = OrgUserService.organizationAddUser();
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");

    }

    @Test(priority = 2, description = "Generate OTp update user API")
    public void organization_UpdateUser() {
        Response response = OrgUserService.organizationUpdateUser();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

    }

    @Test(priority = 3,description = "get organiztion dropdown")
    public void organization_getdropdown(){
        Response response = OrgUserService.getorgUser_dropdown();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

    @Test(priority = 4,description = "get organiztion user list")
    public void organization_getUserList(){
        Response response = OrgUserService.getorgUser_List();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }




}
