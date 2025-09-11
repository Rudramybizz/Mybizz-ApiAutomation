package smbedition.organization.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.organization.services.RoleService;

public class RoleTest  {

    // ================== Get Organization General ==================
    @Test(priority = 1, description = "Get Organization General API")
    public void testGetOrganizationGeneral() {
        Response response = RoleService.get_Organization_general();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertTrue(response.asString().contains("id"), "Response should contain organization id");
    }

    // ================== Create Roles ==================
    @Test(priority = 2)
    @Story("Create Account Manager ")
    @Severity(SeverityLevel.MINOR)
    @Description("Create Accounts Manager role - positive test")
    public void testCreateAccountsManagerRole() {
        Response response = RoleService.createMultipleRoles(); // internally tests multiple roles
//        Assert.assertTrue(
//                response.getStatusCode() == 200 || response.getStatusCode() == 201,
//                "Expected 200/201 for valid Accounts Manager role"
//        );
        Allure.addAttachment("Create Account Manager Role API Response", response.getBody().asPrettyString());

    }

    @Test(priority = 3, description = "Attempt to create role with empty name - negative test")
    public void testCreateRoleWithEmptyName() {
        Response response = RoleService.createMultipleRoles();
        Assert.assertTrue(
                response.getStatusCode() == 400 || response.getStatusCode() == 409,
                "Expected 400/409 for invalid role with empty name"
        );
    }

    @Test(priority = 4)
    @Story("Create Duplicate Role ")
    @Severity(SeverityLevel.MINOR)
    @Description("Attempt to create role with duplicate roleId - negative test")
    public void testCreateDuplicateRoleId() {
        Response response = RoleService.createMultipleRoles();
//        Assert.assertEquals(
//                response.getStatusCode(), 400 ,"Expected 400 for duplicate roleId"
//        );
        Allure.addAttachment("Create Duplicate Role API Response", response.getBody().asPrettyString());
    }

    // ================== Get Roles ==================
    @Test(priority = 5, description = "Get role list")
    public void testGetRoleList() {
        Response response = RoleService.getRole_List();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for role list");
        Assert.assertTrue(response.asString().contains("name"), "Role list should contain role names");
    }

   @Test(priority = 6, description = "Get single role details")
    public void testGetSingleRole() {
        Response response = RoleService.getRole_Single(); // fixed typo
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for single role");
        Assert.assertTrue(response.asString().contains("name"), "Single role response should contain role name");
    }


//    @Test(priority = 7, description = "Update role details")
    public void updateRole(){
        Response response = RoleService.update_Role();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for role update");
//        Assert.assertTrue(response.asString().contains("name"), "Updated role response should contain role name");
    }









}