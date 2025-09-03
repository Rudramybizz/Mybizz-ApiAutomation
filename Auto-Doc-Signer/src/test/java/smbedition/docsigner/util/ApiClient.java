package smbedition.docsigner.util;

import io.restassured.response.Response;
import smbedition.common.ConfigLoader;
import smbedition.common.RequestSpecFactory;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {


    public static final String ORG_HEADER = "organization";

    private static String getUrl(String endpointKey) {
        String endpoint = ConfigLoader.getEndpoint(endpointKey);
        String baseUrl = ConfigLoader.get("base.url");  // unified base URL
        return baseUrl + endpoint;
    }


    public static Response get(String endpointKey) {
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .when()
                .get(getUrl(endpointKey))

                .andReturn();

    }


    public static Response get(String endpointKey, String token) {
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response get(String endpointKey, String orgId,String token ){
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response get(String endpointKey, String token, Map<String, String> queryParams) {
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .queryParams(queryParams) // add query params here
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }



    public static Response get(String endpointKey, String token, String organizationId, boolean addOrgHeader) {
        var request = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token);

        if (addOrgHeader && organizationId != null && !organizationId.isEmpty()) {
            request.header(ORG_HEADER, organizationId);  //  FIXED
        }

        return request.when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response get(String endpointKey, String pathParam, String token, String orgId) {
        String url = getUrl(endpointKey) + "/" + pathParam;

        var request = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token);

        if (orgId != null && !orgId.isEmpty()) {
            request.header(ORG_HEADER, orgId);  //  add organization header
        }

        return request.when()
                .get(url)
                .andReturn();
    }









    // ========== POST METHODS ==========
    public static Response post(String endpointKey, Object body) {
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .body(body)
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }



    public static Response post(String endpointKey, Object body, String token) {
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }

    public static Response post(String endpointKey, Object body, String token, String orgId) {
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .body(body)
                .when()
                .post(getUrl(endpointKey))
                .andReturn();

    }

    // ========== POST with Path Param ==========
    public static Response post(String endpointKey, String pathParam, Object body, String token, String orgId) {
        String url = getUrl(endpointKey) + "/" + pathParam;

        return given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .body(body)
                .when()
                .post(url)
                .andReturn();
    }




    public static Response postMultipart(String endpointKey, File file, String password, String token, String orgId) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .multiPart("signature_file", file, "application/x-pkcs12") // explicit MIME type for .pfx
                .multiPart("password", password)      // text field
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }





    // ========== PATCH METHODS ==========
    public static Response patch(String endpointKey, Object body, String token) {
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .patch(getUrl(endpointKey))
                .andReturn();
    }

    public static Response patch(String endpointKey, Object body, String token,String orgId) {
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .body(body)
                .when()
                .patch(getUrl(endpointKey))
                .andReturn();
    }

    public static Response patch(String endpointKey, String pathParam, Object body,
                                 Map<String, String> queryParams, String token, String orgId) {
        String url = getUrl(endpointKey) + "/" + pathParam;

        var request = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token);

        if (orgId != null && !orgId.isEmpty()) {
            request.header(ORG_HEADER, orgId);
        }

        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }

        if (body != null) {
            request.body(body);
        }

        return request.when()
                .patch(url)
                .andReturn();
    }



}

