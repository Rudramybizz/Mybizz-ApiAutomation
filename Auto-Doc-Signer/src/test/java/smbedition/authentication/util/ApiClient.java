package smbedition.authentication.util;

import io.restassured.response.Response;
import smbedition.common.ConfigLoader;
import smbedition.common.RequestSpecFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static String getUrl(String endpointKey) {
        return ConfigLoader.getEndpoint(endpointKey);
    }


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

    // ========== POST METHODS ==========



    public static Response post(String endpointKey, Object body) {
        return given()
                .spec(RequestSpecFactory.get())
                .body(body)
                .when()
                .post(getUrl(endpointKey))
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




    public static Response patch(String endpointKey, Object body, String token) {
        return given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .patch(getUrl(endpointKey))
                .andReturn();
    }

    public static Response put(String endpointKey, Object body) {
        return given()
                .spec(RequestSpecFactory.get())
                .body(body)
                .when()
                .put(getUrl(endpointKey))
                .andReturn();
    }
}
