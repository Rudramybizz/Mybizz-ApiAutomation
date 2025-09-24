package smbedition.docsigner.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.ApiUtil;
import smbedition.common.BaseTest;
import smbedition.docsigner.services.OrganizationService;
import smbedition.docsigner.services.WebLicenseService;


@Epic("Web License Apis")
@Feature("Web License Api, Upload & Sign Document ")
public class WebLicenseTest extends BaseTest {

    @Test(priority = 1)
    @Story("Add Digital Signature with Correct Credentials.")
    @Severity(SeverityLevel.MINOR)
    @Description("Add Digital Signature Sucess API")
    public void addDigital_Signature() {
        Response response = WebLicenseService.addDigitalSignatureSucess();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Allure.addAttachment("Add Digital Signature API Response", response.getBody().asPrettyString());
    }



    @Test(priority = 2)
    @Story("Add Digital Signature with wrong password API.")
    @Severity(SeverityLevel.MINOR)
    @Description("Add Digital Sugnature API with wrong password")
    public void testAddDigitalSignature_WrongPassword() {
        Response response = WebLicenseService.addDigitalSignatureWithWrongPassword();
        Assert.assertEquals(response.getStatusCode(), 422);
        Allure.addAttachment("Add Digital Signature with Wrong Password API Response", response.getBody().asPrettyString());
    }


    @Test(priority = 3)
    @Story("Add Digital Signature with missing file API.")
    @Severity(SeverityLevel.MINOR)
    @Description("Add Digital Sugnature API with missing file")
    public void testAddDigitalSignature_MissingFile() {
        Response response = WebLicenseService.addDigitalSignatureWithMissingFile();
        Assert.assertEquals(response.getStatusCode(), 422);
        Allure.addAttachment("Add Digital Signature with missing file_API Response", response.getBody().asPrettyString());
    }

    @Test(priority = 4)
    @Story("Add Digital Signature with missing Password API.")
    @Severity(SeverityLevel.MINOR)
    @Description("Add Digital Sugnature API with missing password")
    public void testAddDigitalSignature_MissingPassword() {
        Response response = WebLicenseService.addDigitalSignatureWithMissingPassword();
        Assert.assertEquals(response.getStatusCode(), 422);
        Allure.addAttachment("Add Digital Signature with missing Password API Response", response.getBody().asPrettyString());
    }

    @Test(priority = 5)
    @Story("Get Digital Signature .")
    @Severity(SeverityLevel.MINOR)
    @Description( "Get Digital Signature API")
    public void getDigital_Signature() {
        Response response = WebLicenseService.getDigitalSignature();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Allure.addAttachment("Get Digital Signature API Response", response.getBody().asPrettyString());
    }


    @Test(priority = 7)
    @Story("Add Signature .")
    @Severity(SeverityLevel.MINOR)
    @Description("Add Signature API")
    public void addSignature(){
        Response response = WebLicenseService.addSignature();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
        Allure.addAttachment("Add Digital Signature API Response", response.getBody().asPrettyString());
    }

    @Test(priority = 8 )
    @Story("Get Signature Property .")
    @Severity(SeverityLevel.MINOR)
    @Description("Get Signature Properties API")
    public void  getSignature_Properties(){
        Response response = WebLicenseService.getSignatureProperties();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
        Allure.addAttachment("get Digital Signature API Response", response.getBody().asPrettyString());
    }

    @Test(priority = 9)
    @Story("Get Document Log .")
    @Severity(SeverityLevel.MINOR)
    @Description("Get Document Log API")
    public void getDocument_Log(){
        Response response = WebLicenseService.getDocumentLog();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
        Allure.addAttachment("get Document Log API Response", response.getBody().asPrettyString());
    }

    @Test(priority = 10)
    @Story("Export Documents.")
    @Severity(SeverityLevel.MINOR)
    @Description( "Export Documents API")
    public void exportdocuments(){
        Response response = WebLicenseService.exportDocument();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
     Allure.addAttachment("Export Document API Response", response.getBody().asPrettyString());
}

//    @Test(priority = 11, description = "Get USB Token API Old API Not Usable.")
    public void testgetUsb_Token(){
        Response response = WebLicenseService.getUsbToken();
//        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

//    @Test(priority = 12, description = "USB Certificates API Old API Not Usable.")
    public void usb_Certificates(){
        Response response = WebLicenseService.usbCertificate();
//        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

    @Test(priority = 13, description = "Upload Document API")
    @Story("Upload Documents.")
    @Severity(SeverityLevel.MINOR)
    @Description( "Upload Documents API")
   public void upload_Document(){
    Response response = WebLicenseService.uploadDocuments();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    Allure.addAttachment("Upload Document API Response", response.getBody().asPrettyString());
    }

@Test(priority = 14, description = "Get Document Error API")
@Story("Get Document All.")
@Severity(SeverityLevel.MINOR)
@Description( "Get All Documents API")
public void getDocument_All(){
    Response response = WebLicenseService.getAllDocument();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    Allure.addAttachment("Get All Document API Response", response.getBody().asPrettyString());

}

@Test(priority = 15)
@Story("Get Document List of Request.")
@Severity(SeverityLevel.MINOR)
@Description("Get Document List Of Request API")
public void getDocument_ListOf_Request(){
    Response response = WebLicenseService.getDocumentListOfRequest();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    Allure.addAttachment("Get Document List of Request API Response", response.getBody().asPrettyString());
}

    @Test(priority = 16)
    @Story("Add Signatory.")
    @Severity(SeverityLevel.MINOR)
    @Description("Add Signatory API")
    public void add_Signatory(){
        Response response = WebLicenseService.addSignatories();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
        Allure.addAttachment("Add Signatory API Response", response.getBody().asPrettyString());
    }

    @Test(priority = 17)
    @Story("Get Assigned Signatory.")
    @Severity(SeverityLevel.MINOR)
    @Description("Get Assigned Signatory API")
    public void getAssigned_Signatory(){
        Response response = WebLicenseService.getAssignedSignatory();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//        Assert.fail(response.getStatusCode(),"");
        Allure.addAttachment("Get Assigned Signatory API Response", response.getBody().asPrettyString());
    }

    @Test(priority = 18)
    @Story("Add Workflow.")
    @Severity(SeverityLevel.MINOR)
    @Description("Add Workflow API")
    public void add_WorkFlow(){
        Response response = WebLicenseService.addWorkFlow();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
        Allure.addAttachment("Add workflow API Response", response.getBody().asPrettyString());
    }

//    @Test(priority = 19, description = "Update Workflow API")
//    Public void update_Workflow(){
//
//    }

  @Test(priority = 20 )
  @Story("Get Workflow.")
  @Severity(SeverityLevel.MINOR)
  @Description("Get WorkFlow API.")
    public void get_Workflow(){
        Response response = WebLicenseService.getWorkflow();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
      Allure.addAttachment("Get workflow API Response", response.getBody().asPrettyString());
  }

    @Test(priority = 21)
    @Story("Assigned Workflow.")
    @Severity(SeverityLevel.MINOR)
    @Description("Assign Workflow API")
    public void assign_Workflow(){
        Response response = WebLicenseService.assignWorkflow();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
        Allure.addAttachment("Get workflow API Response", response.getBody().asPrettyString());
    }

    @Test(priority = 22)
    @Story("Document Pending Api.")
    @Severity(SeverityLevel.MINOR)
    @Description("Get Document Pending API")
    public void getDocument_Pending() {
        Response response = WebLicenseService.getDocumentPendng();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Allure.addAttachment("Get Document Pending API Response", response.getBody().asPrettyString());
    }

@Test(priority = 23)
@Story("Sign Document Sucess.")
@Severity(SeverityLevel.MINOR)
@Description("Sign Document Sucess API")
public void signDocument_Sucess(){
    Response response = WebLicenseService.signDocumentSucess();
//    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    Allure.addAttachment("Sign Document Sucess API Response", response.getBody().asPrettyString());
}

    @Test(priority = 24)
    @Story("Get Document Signed.")
    @Severity(SeverityLevel.MINOR)
    @Description("Get Document Signed API")
    public void  getDocument_Signed() {
        Response response = WebLicenseService.getDocumentSigned();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Allure.addAttachment("Get Document Signed API Response", response.getBody().asPrettyString());
    }

//Under Development
//@Test(priority = 25, description = "Sign Document with USB API")
public void signDocument__USB(){
    Response response = WebLicenseService.signDocumnetwithUSB();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}

@Test(priority = 26)
@Story("Get User Activity Report")
@Severity(SeverityLevel.MINOR)
@Description("Get Activity Report API")
public void getActivity_Report(){
    Response response = WebLicenseService.getActivityReport();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    Allure.addAttachment("Get Activity Report API Response", response.getBody().asPrettyString());
}

@Test(priority = 27)
@Story("Get Notification ")
@Severity(SeverityLevel.MINOR)
@Description("Get Notification API")
public void get_Notification(){
    Response response = WebLicenseService.getNotification();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    Allure.addAttachment("Get Notification API Response", response.getBody().asPrettyString());
}

@Test(priority = 28)
@Story("Update Notification ")
@Severity(SeverityLevel.MINOR)
@Description("Update Notification API")
public void update_Notification() {
    Response response = WebLicenseService.updateNotification();
//    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    Allure.addAttachment("Update Notification API Response", response.getBody().asPrettyString());
}

//@Test(priority = 29, description = "View Version API")
public void view_Version(){
    Response response = WebLicenseService.viewVersion();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}

@Test(priority = 30)
@Story("Download Document")
@Severity(SeverityLevel.MINOR)
@Description("Download Document API")
public void download_Document() {
    Response response = WebLicenseService.downloadDocument();
    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    Allure.addAttachment("Download Document API Response", response.getBody().asPrettyString());
}

@Test(priority = 31)
@Story("Remove Document")
@Severity(SeverityLevel.MINOR)
@Description("Remove Document API")
    public void removeDocument(){
        Response response = WebLicenseService.removeDocument();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    Allure.addAttachment("Remove Document API Response", response.getBody().asPrettyString());
}
     @Test(priority = 32, description = "Delete Digital Sugnature API")
    @Story("Delete Digital Signature .")
    @Severity(SeverityLevel.MINOR)
    @Description( "Delete Digital Signature API")
    public void deleteDigital_Signature() {
        Response response = WebLicenseService.deleteDigitalSignature();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        ApiUtil.waitForNextRequest();
//        Response response1 = WebLicenseService.addDigitalSignatureSucess();
//        System.out.println("New Signature Added After Deletion, Status Code: " + response1.getStatusCode());
        Allure.addAttachment("Delete Digital Signature API Response", response.getBody().asPrettyString());
    }




}