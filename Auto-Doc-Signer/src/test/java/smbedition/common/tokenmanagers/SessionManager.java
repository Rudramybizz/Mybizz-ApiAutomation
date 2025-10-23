package smbedition.common.tokenmanagers;


import io.restassured.response.Response;

public final class SessionManager {

    private SessionManager() {}

    public static void initializeSession(Response response) {
        // Extract and set cookies from response
        CookieManager.setCookies(response.getDetailedCookies());

        // Extract and set API token if present in response
        String authToken = response.jsonPath().getString("data.authorization_token");
        if (authToken != null && !authToken.trim().isEmpty()) {
            TokenManager.setToken(authToken);
        }
    }

    public static void clearSession() {
        TokenManager.clear();
//        CookieManager.clearCookies();
    }

    public static boolean isAuthenticated() {
        return TokenManager.get() != null && !TokenManager.get().isBlank();
    }
}