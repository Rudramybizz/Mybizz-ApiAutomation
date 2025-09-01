package smbedition.common;

public final class TokenManager {
    public static volatile String token;

    public TokenManager() {}

    public static void setToken(String value) {
        System.out.println(">>> TokenManager.setToken called with: " + value);
        token = value;
    }

    public static String get() {
        System.out.println(">>> TokenManager.get returning: " + token);
        return token;
    }

    public static String require() {
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Bearer token is not available. Run login first.");
        }
        return token;
    }

    public static void clear() {
        token = null;
    }




}
