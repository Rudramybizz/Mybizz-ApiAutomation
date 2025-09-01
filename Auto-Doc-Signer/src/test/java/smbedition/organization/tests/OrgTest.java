package smbedition.organization.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.organization.base.BaseTest;
import smbedition.organization.services.OrgServices;
import java.io.File;

public class OrgTest  {

    @Test(priority = 1, description = "Verify Get Organization General API")
    public void getOrganizationGeneral (){
        Response response = OrgServices.get_Organization_general();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Assert.assertTrue(response.getBody().asString().contains("organizationName"), "Response should contain organizationName");
    }

    @Test(priority = 2, description = "Verify Get Organization State Specific API")
    public void getOrganizationCountrySpecific() {
        Response response = OrgServices.get_Organization_countrySpecific();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Assert.assertTrue(response.getBody().asString().contains("country"), "Response should contain country information");
    }

    @Test(priority = 3, description = "Verify Get Organization State Specific API")
    public void getOrganizationStateSpecific() {
        Response response = OrgServices.get_Organization_stateSpecific();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Assert.assertTrue(response.getBody().asString().contains("state"), "Response should contain state information");
    }

    @Test(priority = 6, description = "Verify Get Organization List API")
    public void getOrgList() {
        Response response = OrgServices.get_Org_List();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//        Assert.assertTrue(response.getBody().asString().contains("orgList"), "Response should contain orgList");
    }

    @Test(priority = 5, description = "Verify Get Fiscal Year API")
    public void getFiscalYear(){
        Response response = OrgServices.get_fiscalYear();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

    }

    @Test(priority = 4, description = "Create Organization API")
    public void create_Organization() {
        Response response = OrgServices.createOrganization();
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201");

    }


//    @Test(priority = 6, description = "Create Organization API")
//    public void createOrgWithoutLogo() {
//        Response response = OrgServices.createOrganization(null);
//        Assert.assertEquals(response.getStatusCode(), 201);
//    }
//
//    @Test(priority = 7, description = "Create Organization API")
//    public void createOrgWithLogo() {
//        File logo = new File("src/test/resources/testlogo.png");
//        Response response = OrgServices.createOrganization(logo);
//        Assert.assertEquals(response.getStatusCode(), 201);
//    }




    @Test(priority = 7, description = "Get Organization List API")
    public void getOrganizationList() {
        Response response = OrgServices.getorganization_List();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        // Additional assertions can be added here to verify the content of the response

    }
    @Test(priority = 8, description = "Get Organization single API")
    public void getOrganizationSingle() {
        Response response = OrgServices.getorganization_Single();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        // Additional assertions can be added here to verify the content of the response
    }


    @Test(priority = 9, description = "Organization Fiscal Year Success API")
    public void createOrganizationFiscalYear() {
        OrgServices.get_fiscalYear();
        String defaultDateFormat = OrgServices.dateFormats.values().iterator().next();
        String defaultNumberFormat = OrgServices.numberFormats.values().iterator().next();

        System.out.println("Creating Fiscal Year with DateFormat: " + defaultDateFormat +
                " & NumberFormat: " + defaultNumberFormat);

        Response response = OrgServices.orgfiscalyearSucess_create(defaultDateFormat, defaultNumberFormat);

        Assert.assertEquals(response.getStatusCode(), 200,
                "Fiscal Year creation failed for DateFormat: " + defaultDateFormat +
                        " & NumberFormat: " + defaultNumberFormat);
    }




//   @Test(priority = 10, description = "Test Fiscal Year with all Date and Number Formats")
    public void createFiscalYear_Combinations() {
        // Step 1: Fetch latest formats
        OrgServices.get_fiscalYear();

        // Step 2: Loop through all date & number formats
        for (String dateFormat : OrgServices.dateFormats.values()) {
            for (String numberFormat : OrgServices.numberFormats.values()) {
                System.out.println("Testing with Date Format: " + dateFormat + ", Number Format: " + numberFormat);

                Response response = OrgServices.orgfiscalyearSucess_create(dateFormat, numberFormat);

                Assert.assertEquals(response.getStatusCode(), 200,
                        "Failed for DateFormat: " + dateFormat + " & NumberFormat: " + numberFormat);
            }
        }
    }



    @Test(priority = 11, description = "Get organization Fiscal Year Settings API")
    public void get_fiscalyear_settings(){
        Response response = OrgServices.getfiscalyearSettings();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        // Additional assertions can be added here to verify the content of the response
    }


    @Test(priority = 12, description = "Organization fiscal year failure API")
     public void update_FiscalYear() {
        Response response = OrgServices.updateFiscalYear();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        // Additional assertions can be added here to verify the content of the response

    }



//    @Test(priority = 13,description = "Mark as default API")
    public void markas_default(){
        Response response = OrgServices.markOrganizationAsDefault("");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

    }




}