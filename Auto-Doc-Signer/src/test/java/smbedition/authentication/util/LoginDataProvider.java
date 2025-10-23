package smbedition.authentication.util;
import org.testng.annotations.DataProvider;
import java.util.HashMap;
import java.util.Map;

public class LoginDataProvider {

    // DataProvider for invalid OTP generation
    @DataProvider(name = "invalidOtpData")
    public static Object[][] invalidOtpData() {
        return new Object[][] {
                // Empty email
                {
                        createOtpBody("", "Indian@123"),
                        "Empty email"
                },
                // Empty password
                {
                        createOtpBody("manpret343@gmail.com", ""),
                        "Empty password"
                },
                // Invalid email format
                {
                        createOtpBody("invalid-email", "Indian@123"),
                        "Invalid email format"
                },
                // Wrong password
                {
                        createOtpBody("manpret343@gmail.com", "WrongPassword"),
                        "Wrong password"
                },
                // SQL injection in email
                {
                        createOtpBody("test'; DROP TABLE users;--", "Indian@123"),
                        "SQL injection in email"
                },
                // XSS in email
                {
                        createOtpBody("<script>alert('xss')</script>", "Indian@123"),
                        "XSS in email"
                }
        };
    }

    // DataProvider for invalid login attempts
    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() {
        return new Object[][] {
                // Empty email
                {
                        createLoginBody("", "Indian@123", "667788"),
                        "Empty email"
                },
                // Empty password
                {
                        createLoginBody("manpret343@gmail.com", "", "667788"),
                        "Empty password"
                },
                // Empty OTP
                {
                        createLoginBody("manpret343@gmail.com", "Indian@123", ""),
                        "Empty OTP"
                },
                // Invalid OTP
                {
                        createLoginBody("manpret343@gmail.com", "Indian@123", "000000"),
                        "Invalid OTP"
                },
                // Wrong credentials
                {
                        createLoginBody("wrong@email.com", "Indian@123", "667788"),
                        "Wrong email"
                },
                // SQL injection in OTP
                {
                        createLoginBody("manpret343@gmail.com", "Indian@123", "'; DROP TABLE users;--"),
                        "SQL injection in OTP"
                }
        };
    }

    // Helper method for OTP body
    private static Map<String, Object> createOtpBody(String email, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        return body;
    }

    // Helper method for Login body
    private static Map<String, Object> createLoginBody(String email, String password, String otp) {
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        body.put("otp", otp);
        return body;
    }
}