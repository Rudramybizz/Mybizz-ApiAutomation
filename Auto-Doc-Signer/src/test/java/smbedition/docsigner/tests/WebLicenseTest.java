package smbedition.docsigner.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.ApiUtil;
import smbedition.docsigner.services.OrganizationService;
import smbedition.docsigner.services.WebLicenseService;

public class WebLicenseTest {

    @Test(priority = 1, description = "Add Digital Signature Sucess API")
    public void addDigital_Signature() {
        Response response = WebLicenseService.addDigitalSignatureSucess();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test(priority = 2, description = "Add Digital Sugnature API with wrong password")
    public void testAddDigitalSignature_WrongPassword() {
        Response response = WebLicenseService.addDigitalSignatureWithWrongPassword();
        Assert.assertEquals(response.getStatusCode(), 422);

    }
    @Test(priority = 3, description = "Add Digital Sugnature API with missing file")
    public void testAddDigitalSignature_MissingFile() {
        Response response = WebLicenseService.addDigitalSignatureWithMissingFile();
        Assert.assertEquals(response.getStatusCode(), 422);

    }

    @Test(priority = 4, description = "Add Digital Sugnature API with missing password")
    public void testAddDigitalSignature_MissingPassword() {
        Response response = WebLicenseService.addDigitalSignatureWithMissingPassword();
        Assert.assertEquals(response.getStatusCode(), 422);

    }



    @Test(priority = 5, description = "Get Digital Sugnature API")
    public void getDigital_Signature() {
        Response response = WebLicenseService.getDigitalSignature();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

//    @Test(priority = 6, description = "Delete Digital Sugnature API")
    public void deleteDigital_Signature() {
        Response response = WebLicenseService.deleteDigitalSignature();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        ApiUtil.waitForNextRequest();
        Response response1 = WebLicenseService.addDigitalSignatureSucess();
        System.out.println("New Signature Added After Deletion, Status Code: " + response1.getStatusCode());
    }


    @Test(priority = 7, description = "Add Signature API")
    public void addSignature(){
        Response response = WebLicenseService.addSignature();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

    @Test(priority = 8, description = "Get Signature Properties API")
    public void  getSignature_Properties(){
        Response response = WebLicenseService.getSignatureProperties();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

@Test(priority = 9, description = "Get Document Log API")
    public void getDocument_Log(){
        Response response = WebLicenseService.getDocumentLog();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

@Test(priority = 10, description = "Export Documents API")
    public void exportdocuments(){
        Response response = WebLicenseService.exportDocument();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

    @Test(priority = 11, description = "Get USB Token API")
    public void testgetUsb_Token(){
        Response response = WebLicenseService.getUsbToken();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

    @Test(priority = 12, description = "USB Certificates API")
    public void usb_Certificates(){
        Response response = WebLicenseService.usbCertificate();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }

    @Test(priority = 13, description = "Upload Document API")
   public void upload_Document(){
    Response response = WebLicenseService.uploadDocuments();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}


@Test(priority = 14, description = "Get Document Error API")
public void getDocument_Error(){
    Response response = WebLicenseService.getDocumentError();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}

@Test(priority = 15, description = "Get Document Pending API")
public void getDocument_Pending() {
    Response response = WebLicenseService.getDocumentPendng();
    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
}

@Test(priority = 16, description = "Get Document Signed API")
public void  getDocument_Signed() {
    Response response = WebLicenseService.getDocumentSigned();
    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
}

@Test(priority = 17, description = "Get Document List Of Request API")
public void getDocument_ListOf_Request(){
    Response response = WebLicenseService.getDocumentListOfRequest();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}

@Test(priority = 18, description = "Sign Document Sucess API")
public void signDocument_Sucess(){
    Response response = WebLicenseService.signDocumentSucess();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}

@Test(priority = 19, description = "Sign Document with USB API")
public void signDocument__USB(){
    Response response = WebLicenseService.signDocumnetwithUSB();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}

@Test(priority = 20, description = "Add Signatory API")
public void add_Signatory(){
    Response response = WebLicenseService.addSignatories();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}

@Test(priority = 21, description = "Get Assigned Signatory API")
public void getAssigned_Signatory(){
    Response response = WebLicenseService.getAssignedSignatory();
    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
}





//
//
//@Test(priority = 21, description = "Assign Workflow API")
//public void assign_Workflow(){
//    Response response = WebLicenseService.assignWorkflow();
//    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//}
//
//@Test(priority = 22, description = "Remove Document API")
//public void removeDocument(){
//    Response response = WebLicenseService.removeDocument();
//    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//}
//
//@Test(priority = 23, description = "Get Activity Report API")
//public void getActivity_Report(){
//    Response response = WebLicenseService.getActivityReport();
//    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//}
//
//@Test(priority = 24, description = "Get Notification API")
//public void get_Notification(){
//    Response response = WebLicenseService.getNotification();
//    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//}
//
//@Test(priority = 25, description = "Update Notification API")
//public void update_Notification() {
//    Response response = WebLicenseService.updateNotification();
//    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//}
//
//@Test(priority = 26, description = "View Version API")
//public void view_Version(){
//    Response response = WebLicenseService.viewVersion();
//    Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//}
//
//@Test(priority = 27, description = "Download Document API")
//public void download_Document() {
//    Response response = WebLicenseService.downloadDocument();
//    Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
//}





}