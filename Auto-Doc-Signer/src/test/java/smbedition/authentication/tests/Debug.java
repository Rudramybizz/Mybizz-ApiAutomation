package smbedition.authentication.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.BaseTest;
import smbedition.authentication.services.DebugService;

public class Debug extends BaseTest {

    @Test(priority = 1, description = "Validate User API")
    public void validate_User() {
//        ======================== User Validtion API ========================
        Response response = DebugService.validateUser();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        System.out.println("User validation successful: " + response.getBody().asString());


    }

//    public void validate_User_Without_Auth() {
//        // ======================== User Validtion API without Auth ========================
//        Response response = DebugService.validateUserWithoutAuth();
//        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for unauthorized access");
//        System.out.println("User validation without auth failed as expected: " + response.getBody().asString());
//    }

    @Test(priority = 2, description = "Get Deployment Version API")
    public void get_deployment_version(){
        // ======================== Get Deployment Version API ========================
        String version = DebugService.getDeploymentVersion();
        System.out.println("Deployment version: " + version);
    }



}
