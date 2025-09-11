package smbedition.docsigner.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.BaseTest;
import smbedition.docsigner.services.OrganizationService;

@Epic("Organization APIs")
@Feature("Check Organization functionality, plans")
public class OrganizationTest extends BaseTest {

    @Test(priority = 1)
    @Story("Get Organization User Functionality.")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Get organization functionality API")
  public void getOrg_User_Functionalities() {
        Response response = OrganizationService.getOrganizationUserFunctionalities();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Allure.addAttachment("Get organization user Functionality API Response", response.getBody().asPrettyString());
    }

@Test(priority = 2)
@Story("GetAvailable Plan Details.")
@Severity(SeverityLevel.CRITICAL)
@Description("Get plan details API")
public void getPlan_Details(){
        Response response = OrganizationService.getPlanDetails();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    Allure.addAttachment("Get Plan Details API Response", response.getBody().asPrettyString());
}

@Test(priority = 3)
@Story("Get Add On Details.")
@Severity(SeverityLevel.CRITICAL)
@Description("Get plan add on API")
public void getAdd_On(){
        Response response = OrganizationService.getPlanAddOn();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    Allure.addAttachment("Get Add On API Response", response.getBody().asPrettyString());

}



}