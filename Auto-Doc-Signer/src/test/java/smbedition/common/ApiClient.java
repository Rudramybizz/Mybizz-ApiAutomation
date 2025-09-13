package smbedition.common;

import io.restassured.response.Response;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {

//    private static String getUrl(String endpointKey) {
//        return ConfigLoader.getEndpoint(endpointKey);
//    }

    public static final String ORG_HEADER = "organization";

    private static String getUrl(String endpointKey) {
        String endpoint = ConfigLoader.getEndpoint(endpointKey);
        String baseUrl = ConfigLoader.get("base.url");
        return baseUrl + endpoint;
    }


//       === Get Methods ===

    public static Response get(String endpointKey) {
        return given()
                .spec(RequestSpecFactory.get())
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }
    public static Response get(String endpointKey, String token) {
        return given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response get(String endpointKey, String token, Map<String, String> queryParams) {
        return given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .queryParams(queryParams) // add query params here
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response get(String endpointKey, String token, boolean internal) {
        var request = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token);


        if (internal) {
            request.header("X-Internal-Request", "true");
        }

        return request
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }


    public static Response get(String endpointKey, String orgId,String token ){
        return given()
                .spec(smbedition.common.RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header("user-agent", "PostmanRuntime/7.46.0")
                .header(ORG_HEADER, orgId)
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


    public static Response getWithQuery(String endpointKey, Map<String, String> queryParams, String token, String orgId) {
        return given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .queryParams(queryParams)   // add query params here
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }
    public static Response getWithPath(String endpointKey, String pathParam, String token, String orgId) {
        String url = getUrl(endpointKey) + "/" + pathParam; // build full URL

        var request = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token);

        if (orgId != null && !orgId.isEmpty()) {
            request.header(ORG_HEADER, orgId); // add org header if present
        }
        return request.when()
                .get(url)
                .andReturn();
    }

    public static Response get(String endpointKey, Map<String, Object> queryParams, String orgId, String token) {
        return given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header("organization", orgId)
                .queryParams(queryParams)
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response getWithHeaders(String endpointKey, Map<String, String> headers) {
        var request = given().spec(RequestSpecFactory.get());

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(request::header);
        }

        return request
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }



    // ========== POST METHODS ==========

    public static Response post(String endpointKey, Object body) {
        return given()
                .spec(RequestSpecFactory.get())
                .body(body)
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }
    public static Response postparam(String endpointKey, String pathParam) {
        String url = getUrl(endpointKey) + "/" + pathParam;  // append path param
        return given()
                .spec(RequestSpecFactory.get())
                .when()
                .post(url)
                .andReturn();
    }

    public static Response post(String endpointKey, Object body, String token) {
        return given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }
//    public static Response post(String endpointKey, String pathParam, String token) {
//        String url = getUrl(endpointKey) + pathParam;
//        return given()
//                .spec(RequestSpecFactory.get())
//                .header("Authorization", "Bearer " + token)
////                .body(body)
//                .when()
//                .post(url)
//                .andReturn();
//    }

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

//    public static Response postMultipart(String endpointKey, File file, String token, String orgId) {
//        return given()
//                .header("Authorization", "Bearer " + token)
//                .header(ORG_HEADER, orgId)
//                .multiPart("signature_file", file, "application/pdf") // explicit MIME type for .pfx
////
//                .when()
//                .post(getUrl(endpointKey))
//                .andReturn();
//    }

    public static Response postMultipart(String endpointKey, File file, String token, String orgId) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .multiPart("uploaded_attachments", file, "application/pdf")
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }

    public static Response postdoc(String endpointKey, File file, String token, String orgId) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .multiPart("uploaded_documents", file, "application/pdf")
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }


    public static Response postWithHeaders(String endpointKey, Map<String, String> headers) {
        var request = given().spec(RequestSpecFactory.get());

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }
        return request
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }




    public static Response postMultipartWithHeaders(String endpointKey, File file, String password, Map<String, String> headers) {
//        var request = given().spec(RequestSpecFactory.get());
        var request = given();
        if (headers != null && !headers.isEmpty()) {
            headers.forEach(request::header);
        }
        request.multiPart("signature_file", file, "application/x-pkcs12");
        request.multiPart("password", password);

        return request
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }







    public static Response postWithBodyAndHeaders(String endpointKey, Object body, Map<String, String> headers) {
        var request = given().spec(RequestSpecFactory.get());

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(request::header);
        }

        return request
                .body(body)
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
    }

    //    =============== PUT METHODS =============
public static Response put(String endpointKey, Object body) {
    return given()
            .spec(RequestSpecFactory.get())
            .body(body)
            .when()
            .put(getUrl(endpointKey))
            .andReturn();
}
    // ========== PATCH METHODS ==========

    public static Response patch(String endpointKey, Object body, String token) {
        return given()
                .spec(RequestSpecFactory.get())
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



    // ========== DELETE METHODS ==========
    public static Response delete(String endpointKey, String pathParam, String token, String orgId) {
        String url = getUrl(endpointKey); // builds baseUrl + endpoint

        if (pathParam != null && !pathParam.isEmpty()) {
            url = url.replace("{id}", pathParam);  // replace placeholder
        }

        var request = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token);

        if (orgId != null && !orgId.isEmpty()) {
            request.header(ORG_HEADER, orgId);
        }

        return request.when()
                .delete(url)
                .andReturn();
    }

}