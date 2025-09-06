package smbedition.docsigner.services;

import io.restassured.response.Response;
import smbedition.common.*;


import java.io.File;
import java.nio.file.Files;
import java.util.*;

import static io.restassured.RestAssured.given;

public class WebLicenseService {

    private static String orgId = TestData.getOrgId();
    private static String token = TokenManager.get();
    private static String signatureId ;
    private static String instanceId ;

    private static String getTokenOrLogin() {
        if (token == null || token.trim().isEmpty()) {
            System.out.println("Token not found. Logging in to generate new token...");
            token = TokenManager.get();
        }
        System.out.println("Token successfully retrieved: " + token);
        return token;
    }
    private static void logRequest(String apiName, String body) {
        System.out.println("=== " + apiName + " Request ===");
        System.out.println(body);
        System.out.println("========================");
    }
    private static void logResponse(String apiName, Response response) {
        System.out.println("=== " + apiName + " Response ===");
        System.out.println("Status Code: " + response.getStatusCode());
        response.prettyPrint();
        System.out.println("========================");
    }



//    ================ Add Digital Signature ==================
    public static Response addDigitalSignatureSucess() {
        ApiUtil.waitForNextRequest();

        File signatureFile = new File("src/test/resources/cartlines.pfx");

        if (!signatureFile.exists()) {
            throw new RuntimeException("Signature file not found: " + signatureFile.getAbsolutePath());
        }

        Response response = ApiClient.postMultipart(
                "doc.adddigitalsignatureSucess",
                signatureFile,
                "Cart@999",
                token,
                orgId
        );

        System.out.println("Response Status Code: " + response.getStatusCode());
        response.prettyPrint();

        return response;
    }


    public static Response addDigitalSignatureWithWrongPassword() {
        ApiUtil.waitForNextRequest();
        File signatureFile = new File("src/test/resources/cartlines.pfx");

        Map<String, Object> body = new HashMap<>();
        body.put("signature_file", signatureFile);
        body.put("password", "WrongPass");

        return ApiClient.post("doc.adddigitalsignatureSucess", body, token, orgId);
    }
    public static Response addDigitalSignatureWithMissingFile() {
        ApiUtil.waitForNextRequest();

        Map<String, Object> body = new HashMap<>();
        body.put("password", "Cart@999");

        return ApiClient.post("doc.adddigitalsignatureSucess", body, token, orgId);
    }


    public static Response addDigitalSignatureWithMissingPassword() {
        ApiUtil.waitForNextRequest();
        File signatureFile = new File("src/test/resources/cartlines.pfx");

        Map<String, Object> body = new HashMap<>();
        body.put("signature_file", signatureFile);

        return ApiClient.post("doc.adddigitalsignatureSucess", body, token, orgId);
    }




//    ===============Digital Signature =============

    public static Response getDigitalSignature() {
        System.out.println("Get Digital Signature");
        System.out.println("token:"+token);
        System.out.println("Org Id:"+orgId);

        Response response = ApiClient.get
                (
                        "doc.getdigitalsignature",
                        orgId,
                        token
                );

        response.prettyPrint();
        signatureId = response.jsonPath().getString("data.id");
        return response;
    }





    public static Response deleteDigitalSignature() {
        ApiUtil.waitForNextRequest();
        if (signatureId==null|| signatureId .isEmpty()){
         System.out.println("Signature ID is null or empty. Fetching Digital Signature to get a valid ID.");
        }

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("purpose", "delete");

        Response response = ApiClient.patch(
                "doc.deletedigitalsignature",
                signatureId,
                null,
                queryParams,
                token,
                orgId
        );
        logResponse("Delete Digital Signature", response);
        response.prettyPrint();

        return response;
    }



//    ==================Signature Properties ==================

public static Response addSignature(){
    ApiUtil.waitForNextRequest();

    Map<String, String> body = new HashMap<>();
     body.put("signature_position", "570,70,370,150");
     body.put("default_page_number", "first");
     body.put("custom_page_number", null);
     body.put( "purpose_or_reason", "document");
     body.put("default_location", "Hyderabad");


    Response response = ApiClient.post(
            "doc.adddigitalsignature",
            signatureId,
            body,
            token,
            orgId
    );

    response.prettyPrint();
     return response;
}


public static  Response getSignatureProperties() {
    String token = getTokenOrLogin();
    System.out.println("Signature Id:"+signatureId);
    System.out.println("OrgId:"+orgId);
    System.out.println("Get Signature Properties");
    Response response = ApiClient.get(
            "doc.getsignatureproperties",
                       signatureId,
                        token,
                       orgId

                       );
    response.prettyPrint();

    return response;
}


// ==========   Document Log ===========
    public static Response getDocumentLog(){
        String token = getTokenOrLogin();
        System.out.println("OrgId:"+orgId);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("from_date", "01/04/2025");
        queryParams.put("to_date", "28/04/2025");
        Response response = ApiClient.getWithQuery(
                "doc.getdocumentlog",
                queryParams,
                token,
                orgId

        );

        response.prettyPrint();
        return response;
    }


    public static Response exportDocument(){
        String token = getTokenOrLogin();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);

        Map<String, String> body = new HashMap<>();
           body.put("report_name","document_log");
           body.put("file_name", "document");
           body.put("from_date", "01/04/2025");
           body.put("to_date", "31/03/2026");
           body.put("send_mail", "true");
  Response response = ApiClient.post(
          "doc.exportdocumentlog",
          body,
            token,
            orgId
  );
        response.prettyPrint();
        return response;
    }


//    =======Usb Token=========
  public static  Response getUsbToken(){
      String token = getTokenOrLogin();
      System.out.println("OrgId:"+orgId);

      Response response = ApiClient.get(
              "doc.getusbtoken",
              orgId,
              token
      );

      response.prettyPrint();
      return response;
  }


  public static Response usbCertificate(){
      String token = getTokenOrLogin();
      System.out.println("token:"+token);
      System.out.println("OrgId:"+orgId);

      Map<String, String> body = new HashMap<>();
      body.put("label", "FT ePass2003Auto");
      body.put("serial", "3B9F958131FE9F006646530532022571DF000006000010");
      body.put("pin", "GVsiva@6623");


      Response response = ApiClient.post(
              "doc.usbCertificate",
              body,
              token,
              orgId
      );
      response.prettyPrint();
      return response;
  }


  public static Response uploadDocuments(){
      ApiUtil.waitForNextRequest();

      File signatureFile = new File("src/test/resources/dumy.pdf");

      if (!signatureFile.exists()) {
          throw new RuntimeException("Signature file not found: " + signatureFile.getAbsolutePath());
      }

      Response response = ApiClient.postMultipart(
              "doc.uploadDocuments",
              signatureFile,
              token,
              orgId
      );

      System.out.println("Response Status Code: " + response.getStatusCode());
      response.prettyPrint();

      return response;
  }



  public static  Response getDocumentError(){
      String token = getTokenOrLogin();
      System.out.println("OrgId:"+orgId);
      Response response = ApiClient.get(
              "doc.getdocumentError",
              orgId,
              token
      );

      response.prettyPrint();
      return response;


  }


public static Response getDocumentPendng(){
    String token = getTokenOrLogin();
      System.out.println("OrgId:"+orgId);
    Response response = ApiClient.get(
            "doc.getdocumentPendng",
            orgId,
            token
    );

      response.prettyPrint();

      instanceId = response.jsonPath().getString("data.id");
      return response;
}
    public static Response getDocumentSigned(){
        String token = getTokenOrLogin();
        System.out.println("OrgId:"+orgId);
        Response response = ApiClient.get(
                "doc.getdocumentsigned",
                orgId,
                token
        );

        response.prettyPrint();
        return response;
    }


    public static Response getDocumentListOfRequest() {
        String token = getTokenOrLogin();
        System.out.println("OrgId:" + orgId);
        System.out.println("Instance Id:"+instanceId);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("instance_ids", Arrays.asList(instanceId));

        Response response = ApiClient.get(
                "doc.getdocumentlistofRequest",
                queryParams,
                orgId,
                token
        );

        response.prettyPrint();
        return response;
    }

// ===========
    public static Response signDocumentSucess(){
        String token = getTokenOrLogin();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);
        System.out.println("Instance Id:"+instanceId);

        Map<String, Object> body = new HashMap<>();
        body.put("instance_ids", Arrays.asList(instanceId));
        body.put("sign_mode", "pfx");

        Map<String, Object> docProps = new HashMap<>();
        docProps.put("signature_position", "570,70,370,150");
        docProps.put("default_page_number", "first");
        docProps.put("custom_page_number", null);
        docProps.put("purpose_or_reason", "document");
        docProps.put("default_location", "Hyderabad");

        body.put("document_properties", docProps);

        Response response = ApiClient.post(
                "doc.signdocumentSucess",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }


    public static Response signDocumnetwithUSB() {
        String token = getTokenOrLogin();
        System.out.println("token:" + token);
        System.out.println("OrgId:" + orgId);
        System.out.println("Instance Id:" + instanceId);

        Map<String, Object> body = new HashMap<>();
        body.put("instance_ids", Arrays.asList(instanceId));
        body.put("sign_mode", "usb");

        Map<String, Object> docProps = new HashMap<>();
        docProps.put("libPath", "C:\\WINDOWS\\system32\\eps2003csp11v2.dll");
        docProps.put("certAlias", "1d98740a-8748-47e9-9eda-08e9f6d0fde8");
        docProps.put("x", "400");
        docProps.put("y", "20");
        docProps.put("width", "175");
        docProps.put("height", "90");
        docProps.put("defaultPage", "First");
        docProps.put("customPage", "");
        docProps.put("reason", "usb test");
        docProps.put("location", "test location");
        docProps.put("lockForSigning", false);
        docProps.put("addEncryption", false);
        docProps.put("encryptionPin", "");
        docProps.put("reEncryptionAllowed", false);

        body.put("document_properties", docProps);


        Response response = ApiClient.post(
                "doc.signdocumentUSB",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }


    public static Response  addSignatories(){
        String token = getTokenOrLogin();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);
        System.out.println("Instance Id:"+instanceId);

        Map<String, Object> body = new HashMap<>();
        body.put("instance_ids", Arrays.asList(instanceId));
        body.put("purpose", "add_signatory");

        List<Map<String, Object>> signatoryList = new ArrayList<>();

        Map<String, Object> signatory1 = new HashMap<>();
        signatory1.put("serial_number", "1");
        signatory1.put("signatory_id", "43951d69-0ec6-4b0a-adad-d1f2e8c9aa7b");
        signatory1.put("pages_to_sign", Arrays.asList("1"));
        signatory1.put("instruction_notes", "Please sign on page number 1");

        Map<String, Object> signatory2 = new HashMap<>();
        signatory2.put("serial_number", "2");
        signatory2.put("signatory_id", "43951d69-0ec6-4b0a-adad-d1f2e8c9aa7b");
        signatory2.put("pages_to_sign", Arrays.asList("1"));
        signatory2.put("instruction_notes", "Please sign on page number 1");

        signatoryList.add(signatory1);
        signatoryList.add(signatory2);

        Response response = ApiClient.patch(
                "doc.addSignatories",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;

    }

    public static Response getAssignedSignatory(){
        String token = getTokenOrLogin();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);
        System.out.println("Instance Id:"+instanceId);

        Response response = ApiClient.get(
                "doc.getassignedSignatory",
                instanceId + "/view_signatories",
                token,
                orgId
        );

        response.prettyPrint();
        return response;
    }













//  Need to pass work flow id.
    public static Response assignWorkflow(){
        String token = getTokenOrLogin();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);
        System.out.println("Instance Id:"+instanceId);

        Map<String, Object> body = new HashMap<>();
        body.put("instance_ids", Arrays.asList(instanceId));
        body.put("workflow_id", "f7c1ca11-68ad-412");
        body.put("purpose", "assign_workflow");

        Response response = ApiClient.patch(
                "doc.assignWorkflow",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }


    public static Response removeDocument(){
        String token = getTokenOrLogin();
        System.out.println("token:"+token);
        System.out.println("OrgId:"+orgId);
        System.out.println("Instance Id:"+instanceId);

        Map<String, Object> body = new HashMap<>();
        body.put("instance_ids", Arrays.asList(instanceId));
        body.put( "purpose", "remove");

        Response response = ApiClient.patch(
                "doc.removeDocument",
                body,
                token,
                orgId
        );
        response.prettyPrint();
        return response;
    }

// Need to pass approval ids
public static Response documentApproval(){
    String token = getTokenOrLogin();
    System.out.println("token:" + token);
    System.out.println("OrgId:" + orgId);
    System.out.println("Instance Id:" + instanceId);

    Map<String, Object> body = new HashMap<>();
//    body.put("approval_ids", Arrays.asList(approvalIds));
      body.put("remarks", "Signed");
      body.put("status","sign");

    Response response = ApiClient.post(
            "doc.documentApproval",
            body,
            token,
            orgId
    );
    response.prettyPrint();
    return response;
}

public static Response getActivityReport(){

        String token = getTokenOrLogin();
        System.out.println("Token: " + token);
        System.out.println("OrgId: " + orgId);
        System.out.println("InstanceId: " + instanceId);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("instance_id", instanceId);
        queryParams.put("purpose","document");

        Response response = ApiClient.get(
                "doc.getActivityRepost",
                queryParams,
                orgId,
                token
        );

        response.prettyPrint();
        return response;
    }


public static Response getNotification(){
    String token = getTokenOrLogin();
    System.out.println("Token: " + token);
    System.out.println("OrgId: " + orgId);
    Response response = ApiClient.get(
            "doc.getNotification",
            orgId,
            token
    );

    response.prettyPrint();
    return response;
}

public static Response updateNotification(){
    String token = getTokenOrLogin();
    System.out.println("token:"+token);
    System.out.println("OrgId:"+orgId);
    System.out.println("Instance Id:"+instanceId);

    Map<String, Object> body = new HashMap<>();
    body.put("purpose","mark_as_read");
    body.put("instance_ids", Arrays.asList(1));

    Response response = ApiClient.patch(
            "doc.updateNotification",
            body,
            token,
            orgId
    );
    response.prettyPrint();
    return response;
}


public static Response viewVersion(){
    String token = getTokenOrLogin();
    System.out.println("Token: " + token);
    System.out.println("OrgId: " + orgId);

    Map<String, Object> queryParams = new HashMap<>();
    queryParams.put("instance_id", orgId);
    queryParams.put("purpose","document");

    Response response = ApiClient.getWithPath(
            "doc.viewversions",
            instanceId + "/view_versions",
            token,
            orgId
    );
    response.prettyPrint();
    return response;
}

public static Response downloadDocument(){
    String token = getTokenOrLogin();
    System.out.println("token:" + token);
    System.out.println("OrgId:" + orgId);
    System.out.println("Instance Id:" + instanceId);

    Map<String, Object> body = new HashMap<>();
    body.put("instance_ids", Arrays.asList(instanceId));

    Response response = ApiClient.post(
            "doc.downloadDocument",
            body,
            token,
            orgId
    );
    response.prettyPrint();
    return response;
}



}