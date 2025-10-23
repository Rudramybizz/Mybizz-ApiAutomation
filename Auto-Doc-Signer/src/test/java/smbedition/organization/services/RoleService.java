//package smbedition.organization.services;
//
//import io.restassured.response.Response;
//import smbedition.common.waits.ApiUtil;
//import smbedition.common.TestData;
//import smbedition.common.TokenManager;
//import smbedition.common.ApiClient;
//
//import java.util.*;
//
//import static smbedition.common.TokenManager.token;
//
//public class RoleService {
//
//    private static String roleId;
//
//    // ================== Token Handling ==================
//    private static String getTokenOrLogin() {
//        String token = TokenManager.get();
//
//        if (token == null || token.trim().isEmpty()) {
//            System.out.println("Token not found. Logging in to generate new token...");
//
//            token = TokenManager.get();
//
//            if (token == null || token.trim().isEmpty()) {
//                throw new IllegalStateException("Failed to generate token. Login process did not return a token.");
//            }
//        }
//        System.out.println("Token successfully retrieved: " + token);
//        return token;
//    }
//
//    private static void logRequest(String apiName, String body) {
//        System.out.println("=== " + apiName + " Request ===");
//        System.out.println(body);
//        System.out.println("========================");
//    }
//
//    private static void logResponse(String apiName, Response response) {
//        System.out.println("=== " + apiName + " Response ===");
//        System.out.println("Status Code: " + response.getStatusCode());
//        response.prettyPrint();
//        System.out.println("========================");
//    }
//
//    // ================== Get Organization General ==================
//    public static Response get_Organization_general() {
//        String orgId = TestData.getOrgId();
//        if (token == null || token.isEmpty()) {
//            token = getTokenOrLogin();
//        }
//        System.out.println("===== Get Organization General API =====");
//        ApiUtil.waitForNextRequest();
//
//        Response response = ApiClient.get("org.getorganization.general", token, orgId, true);
//        logResponse("GetOrganizationGeneral", response);
//
//        return response;
//    }
//
//
//    // ================== Create Multiple Roles ==================
////    public static Response createMultipleRoles() {
////        String orgId = TestData.getOrgId();
////
////        if (token == null || token.isEmpty()) {
////            token = getTokenOrLogin();
////        }
////
////        // List of roles to test (positive + negative scenarios)
////        String[][] roles = {
////                {"Accounts Manager", "ACM-001", "Deals with accounts"},
////                {"HR Manager", "HR-002", "Handles HR operations"},
////                {"Admin", "ADM-003", "System administrator"},
////                {"", "INV-001", "Missing role name"},
////                {"Duplicate Role", "ACM-001", "Duplicate roleId"}
////        };
////
////        Response lastResponse = null;
////
////        for (String[] role : roles) {
////            String name = role[0];
////            String roleId = role[1];
////            String description = role[2];
////
////            String requestBody = "{\n" +
////                    "    \"name\": \"" + name + "\",\n" +
////                    "    \"role_id\": \"" + roleId + "\",\n" +
////                    "    \"description\": \"" + description + "\",\n" +
////                    "    \"functionality_groups\": [\n" +
////                    "        {\n" +
////                    "            \"code\": \"auto_doc_signer\",\n" +
////                    "            \"name\": \"Auto Doc Signer (Digital Signatures)\",\n" +
////                    "            \"functionalities\": [\n" +
////                    "                {\n" +
////                    "                    \"code\": \"documents\",\n" +
////                    "                    \"name\": \"Documents\",\n" +
////                    "                    \"permission\": \"full_rights\"\n" +
////                    "                },\n" +
////                    "                {\n" +
////                    "                    \"code\": \"license_info\",\n" +
////                    "                    \"name\": \"License Info\",\n" +
////                    "                    \"permission\": \"full_rights\"\n" +
////                    "                },\n" +
////                    "                {\n" +
////                    "                    \"code\": \"settings\",\n" +
////                    "                    \"name\": \"Settings\",\n" +
////                    "                    \"permission\": \"full_rights\"\n" +
////                    "                }\n" +
////                    "            ]\n" +
////                    "        }\n" +
////                    "    ]\n" +
////                    "}";
////
////            logRequest("CreateRole", requestBody);
////            ApiUtil.waitForNextRequest();
////
////            lastResponse = ApiClient.post("org.create_role", requestBody, token, orgId);
////
////            logResponse("CreateRole", lastResponse);
////
////            // Business validation inside service
////            if (name.isEmpty() || "ACM-001".equals(roleId)) {
////                // Expected failure
////                if (lastResponse.getStatusCode() == 400 || lastResponse.getStatusCode() == 409) {
////                    System.out.println(" Negative test passed for roleId: " + roleId);
////                } else {
////                    throw new AssertionError(" Negative test FAILED for roleId: " + roleId
////                            + ". Status: " + lastResponse.getStatusCode());
////                }
////            } else {
////                // Expected success
////                if (lastResponse.getStatusCode() == 200 || lastResponse.getStatusCode() == 201) {
////                    System.out.println(" Role created successfully: " + name + " (" + roleId + ")");
////                } else {
////                    throw new AssertionError(" Role creation FAILED for: " + name + " (" + roleId + ")"
////                            + ". Status: " + lastResponse.getStatusCode());
////                }
////            }
////        }
////
////        return lastResponse;
////    }
//
//
//    // ============== Create Single Role ==============
//    public static Response createRole(String name, String roleId, String description) {
//        String orgId = TestData.getOrgId();
//
//        if (token == null || token.isEmpty()) {
//            token = getTokenOrLogin();
//        }
//
//        String requestBody = "{\n" +
//                "    \"name\": \"" + name + "\",\n" +
//                "    \"role_id\": \"" + roleId + "\",\n" +
//                "    \"description\": \"" + description + "\",\n" +
//                "    \"functionality_groups\": [\n" +
//                "        {\n" +
//                "            \"code\": \"auto_doc_signer\",\n" +
//                "            \"name\": \"Auto Doc Signer (Digital Signatures)\",\n" +
//                "            \"functionalities\": [\n" +
//                "                {\"code\": \"documents\", \"name\": \"Documents\", \"permission\": \"full_rights\"},\n" +
//                "                {\"code\": \"license_info\", \"name\": \"License Info\", \"permission\": \"full_rights\"},\n" +
//                "                {\"code\": \"settings\", \"name\": \"Settings\", \"permission\": \"full_rights\"}\n" +
//                "            ]\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//
//        logRequest("CreateRole", requestBody);
//        ApiUtil.waitForNextRequest();
//
//        Response response = ApiClient.post("org.create_role", requestBody, token, orgId);
//
//        logResponse("CreateRole", response);
//
//        return response;
//    }
//
//
//
//
//
//    // ================== Get Roles ==================
//    public static Response getRole_List() {
//        System.out.println("================= get Role List API ===================");
//        String orgId = TestData.getOrgId();
//        if (token == null || token.isEmpty()) {
//            token = getTokenOrLogin();
//        }
//        Response response = ApiClient.get("org.getrole.List", token, orgId, true);
//        roleId = response.jsonPath().getString("data[0].id");
//        TestData.setRoleId(roleId);
//        logResponse("GetRoleList", response);
//        return response;
//    }
//
//
//
//    public static Response getRole_Single() {
//        String orgId = TestData.getOrgId();
//        if (token == null || token.isEmpty()) {
//            token = getTokenOrLogin();
//        }
//        Response response = ApiClient.get("org.getroleSingle", roleId, token, orgId);
//        logResponse("GetRoleSingle", response);
//        return response;
//    }
//
//
//    // ================== Update Role ==================
//    public static Response update_Role() {
//        String orgId = TestData.getOrgId();
//        if (token == null || token.isEmpty()) {
//            token = getTokenOrLogin();
//        }
//        Map<String, Object> body = new HashMap<>();
//        body.put("name", "Accounts Manager");
//        body.put("role_id", "ACM-001");
//        body.put("description", "Deal with accounts");
//
//        Map<String, Object> functionality1 = new HashMap<>();
//        functionality1.put("code", "documents");
//        functionality1.put("name", "Documents");
//        functionality1.put("permission", "restricted_edit");
//
//        Map<String, Object> functionality2 = new HashMap<>();
//        functionality2.put("code", "license_info");
//        functionality2.put("name", "License Info");
//        functionality2.put("permission", "full_rights");
//
//        Map<String, Object> functionality3 = new HashMap<>();
//        functionality3.put("code", "settings");
//        functionality3.put("name", "Settings");
//        functionality3.put("permission", "full_rights");
//
//        List<Map<String, Object>> functionalities = new ArrayList<>();
//        functionalities.add(functionality1);
//        functionalities.add(functionality2);
//        functionalities.add(functionality3);
//
//        Map<String, Object> functionalityGroup = new HashMap<>();
//        functionalityGroup.put("code", "auto_doc_signer");
//        functionalityGroup.put("name", "Auto Doc Signer (Digital Signatures)");
//        functionalityGroup.put("functionalities", functionalities);
//
//        List<Map<String, Object>> functionalityGroups = new ArrayList<>();
//        functionalityGroups.add(functionalityGroup);
//
//        body.put("functionality_groups", functionalityGroups);
//        Response response = ApiClient.post(
//                "org.update_role",
//                roleId,
//                body,
//                token,
//                orgId
//        );
//
//        logResponse("UpdateRole", response);
//        return response;
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
////package smbedition.organization.services;
////
////import io.restassured.response.Response;
////import smbedition.authentication.services.AuthApi;
////import smbedition.common.waits.ApiUtil;
////import smbedition.common.TestData;
////import smbedition.common.TokenManager;
////import smbedition.organization.util.ApiClient;
////
////import static smbedition.common.TokenManager.token;
////
////public class RoleService {
////
////    private static String getTokenOrLogin() {
////        String token = TokenManager.get();
////
////        if (token == null || token.trim().isEmpty()) {
////            System.out.println("Token not found. Logging in to generate new token...");
////
////            token = TokenManager.get();
////
////            if (token == null || token.trim().isEmpty()) {
////                throw new IllegalStateException("Failed to generate token. Login process did not return a token.");
////            }
////        }
////        System.out.println("Token successfully retrieved: " + token);
////        return token;
////    }
////
////    private static void logRequest(String apiName, String body) {
////        System.out.println("=== " + apiName + " Request ===");
////        System.out.println(body);
////        System.out.println("========================");
////    }
////    private static void logResponse(String apiName, Response response) {
////        System.out.println("=== " + apiName + " Response ===");
////        System.out.println("Status Code: " + response.getStatusCode());
////        response.prettyPrint();
////        System.out.println("========================");
////    }
////
//////    ============================Get Role==================
////    public static Response get_Organization_general() {
////         String orgId = TestData.getOrgId();
////        if(token == null || token.isEmpty()) {
////            System.out.println("Token is null or empty. Attempting to log in...");
////            token = getTokenOrLogin();
////        }
////        System.out.println(token);
////        System.out.println("===== Get Organization General API =====");
////        ApiUtil.waitForNextRequest();
////
////        Response response = ApiClient.get("org.getorganization.general",token,orgId, true);
////
////        System.out.println("Response Status Code: " + response.getStatusCode());
////        response.prettyPrint();
////
////        return response;
////    }
////
////
//////     Create Roles APIS
////
////
////    // ============================ Create Multiple Roles ===================
////    public static Response create_Roles() {
////        String orgId = TestData.getOrgId();
////
////        if (token == null || token.isEmpty()) {
////            System.out.println("Token is null or empty. Attempting to log in...");
////            token = getTokenOrLogin();
////        }
////
////        // List of roles to test (positive + negative scenarios)
////        String[][] roles = {
////                {"Accounts Manager", "ACM-001", "Deals with accounts"},
////                {"HR Manager", "HR-002", "Handles HR operations"},
////                {"Admin", "ADM-003", "System administrator"},
////                {"", "INV-001", "Missing role name"},
////                {"Duplicate Role", "ACM-001", "Duplicate roleId"}
////        };
////
////        for (String[] role : roles) {
////            String name = role[0];
////            String roleId = role[1];
////            String description = role[2];
////
////            String requestBody = "{\n" +
////                    "    \"name\": \"" + name + "\",\n" +
////                    "    \"role_id\": \"" + roleId + "\",\n" +
////                    "    \"description\": \"" + description + "\",\n" +
////                    "    \"functionality_groups\": [\n" +
////                    "        {\n" +
////                    "            \"code\": \"auto_doc_signer\",\n" +
////                    "            \"name\": \"Auto Doc Signer (Digital Signatures)\",\n" +
////                    "            \"functionalities\": [\n" +
////                    "                {\n" +
////                    "                    \"code\": \"documents\",\n" +
////                    "                    \"name\": \"Documents\",\n" +
////                    "                    \"permission\": \"full_rights\"\n" +
////                    "                },\n" +
////                    "                {\n" +
////                    "                    \"code\": \"license_info\",\n" +
////                    "                    \"name\": \"License Info\",\n" +
////                    "                    \"permission\": \"full_rights\"\n" +
////                    "                },\n" +
////                    "                {\n" +
////                    "                    \"code\": \"settings\",\n" +
////                    "                    \"name\": \"Settings\",\n" +
////                    "                    \"permission\": \"full_rights\"\n" +
////                    "                }\n" +
////                    "            ]\n" +
////                    "        }\n" +
////                    "    ]\n" +
////                    "}";
////
////            logRequest("CreateRole", requestBody);
////            ApiUtil.waitForNextRequest();
////
////            Response response = ApiClient.post("org.create_role", requestBody, token, orgId);
////
////            logResponse("CreateRole", response);
////
////            // Business validation inside service
////            if (name.isEmpty() || "ACM-001".equals(roleId)) {
////                // Expected failure
////                if (response.getStatusCode() == 400 || response.getStatusCode() == 409) {
////                    System.out.println(" Negative test passed for roleId: " + roleId);
////                } else {
////                    throw new AssertionError(" Negative test FAILED for roleId: " + roleId
////                            + ". Status: " + response.getStatusCode());
////                }
////            } else {
////                // Expected success
////                if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
////                    System.out.println(" Role created successfully: " + name + " (" + roleId + ")");
////                } else {
////                    throw new AssertionError(" Role creation FAILED for: " + name + " (" + roleId + ")"
////                            + ". Status: " + response.getStatusCode());
////                }
////            }
////        }
////
////
////        //                  Get Roles APIS
////        public static Response getRole_List () {
////            System.out.println("================= get Role List API ===================");
////            String orgId = TestData.getOrgId();
////            System.out.println("organization Id:" + orgId);
////            if (token == null || token.isEmpty()) {
////                System.out.println("Token is null or empty. Attempting to log in...");
////                token = getTokenOrLogin();
////            }
////            Response response = ApiClient.get("org.getrole.List", token, orgId, true);
////            response.prettyPrint();
////            return response;
////        }
////
////
////        public static Response gerRole_Single () {
////            String orgId = TestData.getOrgId();
////            if (token == null || token.isEmpty()) {
////                System.out.println("Token is null or empty. Attempting to log in...");
////                token = getTokenOrLogin();
////            }
////
////            Response response = ApiClient.get("org.getroleSingle", orgId, token, true);
////            response.prettyPrint();
////            return response;
////        }
////
////
////
////}