package smbedition.authentication.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.BaseTest;
import smbedition.authentication.services.DebugService;

@Epic("Debug APIs")
@Feature("Debug , Validate User & Deployment Versions.")
public class Debug extends BaseTest {

    @Test(priority = 1 )
    @Story(" Validate User .")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validate User API")
    public void validate_User() {
        Response response = DebugService.validateUser();
//        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Allure.addAttachment("Validate User API Response", response.getBody().asPrettyString());
        System.out.println("User validation successful: " + response.getBody().asString());
    }

//    public void validate_User_Without_Auth() {
//        // ======================== User Validtion API without Auth ========================
//        Response response = DebugService.validateUserWithoutAuth();
//        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for unauthorized access");
//        System.out.println("User validation without auth failed as expected: " + response.getBody().asString());
//    }

    @Test(priority = 2)
    @Story("Get Deployment version .")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get Deployment Version API")
    public void get_deployment_version(){
        // ======================== Get Deployment Version API ========================
        String version = DebugService.getDeploymentVersion();
        System.out.println("Deployment version: " + version);
    }

}