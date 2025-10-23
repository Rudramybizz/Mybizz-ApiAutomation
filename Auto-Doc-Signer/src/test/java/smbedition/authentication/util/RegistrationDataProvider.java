package smbedition.authentication.util;

import org.testng.annotations.DataProvider;
//import smbedition.common.EncryptApi;

import java.util.HashMap;
import java.util.Map;

public class RegistrationDataProvider {

        // DataProvider for invalid registration scenarios
        @DataProvider(name = "invalidRegistrationData")
        public static Object[][] invalidRegistrationData() {
            return new Object[][] {
                    // Empty first name
                    {
                            createRegistrationBody("", "Singh", "manpret343@gmail.com", "Indian@123", "IN", "7011721818", "IN"),
                            "Empty first name"
                    },
                    // Empty last name
                    {
                            createRegistrationBody("Manpreet", "", "manpret343@gmail.com", "Indian@123", "IN", "7011721818", "IN"),
                            "Empty last name"
                    },
                    // Invalid email format
                    {
                            createRegistrationBody("Manpreet", "Singh", "invalid-email", "Indian@123", "IN", "7011721818", "IN"),
                            "Invalid email format"
                    },
                    // Weak password
                    {
                            createRegistrationBody("Manpreet", "Singh", "manpret343@gmail.com", "weak", "IN", "7011721818", "IN"),
                            "Weak password"
                    },
                    // Invalid phone number
                    {
                            createRegistrationBody("Manpreet", "Singh", "manpret343@gmail.com", "Indian@123", "IN", "123", "IN"),
                            "Invalid phone number"
                    },
                    // Empty country
                    {
                            createRegistrationBody("Manpreet", "Singh", "manpret343@gmail.com", "Indian@123", "IN", "7011721818", ""),
                            "Empty country"
                    },
                    // Null values
                    {
                            createRegistrationBody(null, "Singh", "manpret343@gmail.com", "Indian@123", "IN", "7011721818", "IN"),
                            "Null first name"
                    },
                    // SQL injection attempt
                    {
                            createRegistrationBody("Manpreet", "Singh", "test'; DROP TABLE users;--", "Indian@123", "IN", "7011721818", "IN"),
                            "SQL injection in email"
                    },
                    // XSS attempt
                    {
                            createRegistrationBody("<script>alert('xss')</script>", "Singh", "manpret343@gmail.com", "Indian@123", "IN", "7011721818", "IN"),
                            "XSS in first name"
                    },
                    // Very long inputs
                    {
                            createRegistrationBody("A".repeat(1000), "Singh", "manpret343@gmail.com", "Indian@123", "IN", "7011721818", "IN"),
                            "Very long first name"
                    }
            };
        }

        // DataProvider for boundary value testing
        @DataProvider(name = "boundaryValueData")
        public static Object[][] boundaryValueData() {
            return new Object[][] {
                    // Minimum length names
                    {
                            createRegistrationBody("A", "B", "min@test.com", "Indian@123", "IN", "7011721818", "IN"),
                            "Minimum length names"
                    },
                    // Maximum length names (assuming 255 chars)
                    {
                            createRegistrationBody("A".repeat(255), "B".repeat(255), "max@test.com", "Indian@123", "IN", "7011721818", "IN"),
                            "Maximum length names"
                    },
                    // Phone number boundary
                    {
                            createRegistrationBody("Manpreet", "Singh", "boundary@test.com", "Indian@123", "IN", "1".repeat(5), "IN"),
                            "Minimum phone length"
                    },
                    {
                            createRegistrationBody("Manpreet", "Singh", "boundary@test.com", "Indian@123", "IN", "1".repeat(20), "IN"),
                            "Maximum phone length"
                    }
            };
        }

        // DataProvider for different country/dialing code combinations
        @DataProvider(name = "countryDialingCodeData")
        public static Object[][] countryDialingCodeData() {
            return new Object[][] {
                    {"US", "US", "US country with US dialing code"},
                    {"IN", "IN", "IN country with IN dialing code"},
                    {"GB", "GB", "GB country with GB dialing code"},
                    {"CA", "CA", "CA country with CA dialing code"},
                    {"AU", "AU", "AU country with AU dialing code"}
            };
        }

        // DataProvider for duplicate registration attempts
        @DataProvider(name = "duplicateRegistrationData")
        public static Object[][] duplicateRegistrationData() {
            String email = "duplicate@test.com";
            String phone = "7011721818";

            return new Object[][] {
                    {
                            createRegistrationBody("User1", "Test1", email, "Password@123", "IN", phone, "IN"),
                            "Duplicate email and phone"
                    },
                    {
                            createRegistrationBody("User2", "Test2", email, "Different@123", "IN", "9999999999", "IN"),
                            "Duplicate email only"
                    },
                    {
                            createRegistrationBody("User3", "Test3", "new@test.com", "Password@123", "IN", phone, "IN"),
                            "Duplicate phone only"
                    }
            };
        }

        // Helper method to create registration body
        private static Map<String, Object> createRegistrationBody(String firstName, String lastName, String email,
                                                                  String password, String dialingCode, String phone,
                                                                  String countryId) {
            Map<String, Object> body = new HashMap<>();
            if (firstName != null) body.put("first_name", firstName);
            if (lastName != null) body.put("last_name", lastName);
            if (email != null) body.put("email", email);
            if (password != null) body.put("password", password);
            body.put("dialing_code", dialingCode);
            body.put("phone", phone);
            body.put("country_id", countryId);

            return body;
        }


    // Security test data
    @DataProvider(name = "securityTestData")
    public static Object[][] securityTestData() {
        return new Object[][] {
                {
                        createRegistrationBody("'; DROP TABLE users;--", "Test", "test@test.com", "Password@123", "IN", "7011721818", "IN"),
                        "SQL injection in first name"
                },
                {
                        createRegistrationBody("<script>alert('xss')</script>", "Test", "test@test.com", "Password@123", "IN", "7011721818", "IN"),
                        "XSS in first name"
                },
                {
                        createRegistrationBody("Test", "Test", "../../etc/passwd", "Password@123", "IN", "7011721818", "IN"),
                        "Path traversal in email"
                }
        };
    }

    // Helper methods for security tests
    public static Map<String, Object> createSqlInjectionData() {
        return createRegistrationBody("'; DROP TABLE users;--", "Test", "test'; DELETE FROM accounts;--", "Password@123", "IN", "7011721818", "IN");
    }

    public static Map<String, Object> createXSSData() {
        return createRegistrationBody("<script>alert('xss')</script>", "<img src=x onerror=alert(1)>", "test@test.com", "Password@123", "IN", "7011721818", "IN");
    }

    }

