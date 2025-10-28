package smbedition.common.tokenmanagers;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;

public final class CookieManager {
    private static volatile Cookies currentCookies = new Cookies();
    private static volatile Cookies orgId = new Cookies();
    private static volatile String csrfToken;

    private CookieManager() {
    }

    // Cookie Management
    public static void setCookies(Cookies cookies) {
        if (cookies != null) {
            currentCookies = cookies;
            extractCsrfToken(cookies);
        }
    }

    public static Cookies getCookies() {
        return currentCookies;
    }

//    public static void clearCookies() {
//        currentCookies = new Cookies();
//        csrfToken = null;
//    }

    // CSRF Token Management
    private static void extractCsrfToken(Cookies cookies) {
        cookies.forEach(cookie -> {
            if (cookie.getName().toLowerCase().contains("csrf") ||
                    cookie.getName().toLowerCase().contains("xsrf")) {
                csrfToken = cookie.getValue();
                System.out.println("Extracted CSRF Token: " + csrfToken);
            }
        });
    }

    public static String getCsrfToken() {
        return csrfToken;
    }

    public static void setCsrfToken(String token) {
        csrfToken = token;
    }

    // Method to get auth_token from cookies

    public static Cookie cookieAuthtoken() {
        for (Cookie cookie : currentCookies) {
            if ("auth_token".equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }


//    Irganization Id
private static volatile String organizationId;

    public static void setOrgId(String orgId) {
        if (orgId != null && !orgId.isEmpty()) {
            organizationId = orgId;
            System.out.println("Organization ID set: " + organizationId);
        }
    }

    public static String getOrgId() {
        return organizationId;
    }

}