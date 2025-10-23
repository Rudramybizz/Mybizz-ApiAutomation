package smbedition.common;

import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.http.Cookies;
import smbedition.common.logging.Log;
import smbedition.common.tokenmanagers.CookieManager;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public static final String ORG_HEADER = "organization";
    public static final String sso_enableencryption = "ENABLE-ENCRYPTION";
    public static final String sso_enabledecryption = "ENABLE-DECRYPTION";
    public static final String sso_status = "no";

    private static String getUrl(String endpointKey) {
        String endpoint = ConfigLoader.getEndpoint(endpointKey);
        String baseUrl = ConfigLoader.getBaseUrlForEndpoint(endpointKey);
        System.out.println("Building URL for endpoint: " + endpointKey);
        System.out.println("Base URL: " + baseUrl);
        System.out.println("Endpoint: " + endpoint);
        if (baseUrl.endsWith("/") && endpoint.startsWith("/")) {
            endpoint = endpoint.substring(1);
        } else if (!baseUrl.endsWith("/") && !endpoint.startsWith("/")) {
            endpoint = "/" + endpoint;
        }
        String finalUrl = baseUrl + endpoint;
        System.out.println("Final URL: " + finalUrl);
        System.out.println("------------------------");
        return finalUrl;
    }

    // Helper method to get current cookies
    private static Cookies getCurrentCookies() {
        return CookieManager.getCookies();

    }
    // Method to get auth_token cookie
    public static Cookie cookieAuthtoken() {
        return CookieManager.cookieAuthtoken();
    }


    // 1. SSO GET Method
    public static Response ssoGet(String endpointKey) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        Response response = given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
        return response;
    }

    // Simple GET with token only
    public static Response get(String endpointKey, String token) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        return given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .header("Authorization", "Bearer " + token)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response get(String endpointKey) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        return given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response getWithQuery(String endpointKey, Map<String, Object> queryParams) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        return given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .queryParams(queryParams)
                .when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    public static Response getWithQuery(String endpointKey, Map<String, String> queryParams, String token, String orgId) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        var request = given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .header("Authorization", "Bearer " + token)
                .queryParams(queryParams);

        if (orgId != null && !orgId.isEmpty()) {
            request.header(ORG_HEADER, orgId);
        }

        return request.when()
                .get(getUrl(endpointKey))
                .andReturn();
    }

    // 2. SSO POST with redirect
    public static Response ssoPost(String endpointKey, Map<String, Object> body, String redirectUri) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("redirect_uri", redirectUri);
        Log.info("Current cookies being sent: " + cookies);
        Response response = given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .queryParams(queryParams)
                .body(body)
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
        return response;
    }

    // 3. SSO POST without redirect
    public static Response ssoPost(String endpointKey, Map<String, Object> body) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        Response response = given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .body(body)
                .when()
                .post(getUrl(endpointKey))
                .andReturn();
        return response;
    }

    // 4. PUT Method
    public static Response put(String endpointKey, Object body) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        Response response = given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .body(body)
                .when()
                .put(getUrl(endpointKey))
                .andReturn();

        return response;
    }

    // 5. PATCH with token
    public static Response patch(String endpointKey) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        Response response = given()
                .spec(RequestSpecFactory.get())
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .when()
                .patch(getUrl(endpointKey))
                .andReturn();

        return response;
    }

    // 6. PATCH with token and org
    public static Response patch(String endpointKey, Object body, String token, String orgId) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        Log.info("Current cookies being sent: " + cookies);
        Response response = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header(ORG_HEADER, orgId)
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies)
//                .cookie(cookieauthtoken)
                .body(body)
                .when()
                .patch(getUrl(endpointKey))
                .andReturn();

        return response;
    }

    // 7. PATCH with path param
    public static Response patch(String endpointKey, String pathParam, Object body,
                                 Map<String, String> queryParams, String token, String orgId) {
        Cookies cookies = getCurrentCookies();Cookie cookieauthtoken = cookieAuthtoken();
        String url = getUrl(endpointKey) + "/" + pathParam;
        Log.info("Current cookies being sent: " + cookies);
        var request = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies);

//                .cookie(cookieauthtoken);
        if (orgId != null && !orgId.isEmpty()) {
            request.header(ORG_HEADER, orgId);
        }

        if (queryParams != null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }

        if (body != null) {
            request.body(body);
        }

        Response response = request.when().patch(url).andReturn();

        return response;
    }

    // 8. DELETE Method
    public static Response delete(String endpointKey, String pathParam, String token, String orgId) {
        Cookies cookies = getCurrentCookies();
        Cookie cookieauthtoken = cookieAuthtoken();
        String url = getUrl(endpointKey);
        Log.info("Current cookies being sent: " + cookies);
        if (pathParam != null && !pathParam.isEmpty()) {
            url = url.replace("{id}", pathParam);
        }

        var request = given()
                .spec(RequestSpecFactory.get())
                .header("Authorization", "Bearer " + token)
                .header(sso_enableencryption, sso_status)
                .header(sso_enabledecryption, sso_status)
                .cookies(cookies);
//                .cookie(cookieauthtoken);
        if (orgId != null && !orgId.isEmpty()) {
            request.header(ORG_HEADER, orgId);
        }

        Response response = request.when().delete(url).andReturn();

        return response;
    }
}















