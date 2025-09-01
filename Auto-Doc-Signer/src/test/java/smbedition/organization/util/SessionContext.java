package smbedition.organization.util;

import smbedition.common.TokenManager;

public class SessionContext {

    private static String token;

    public static String getToken() {
        if (token == null || token.isEmpty()) {
            token = TokenManager.get();
        }
        return token;
    }


}

