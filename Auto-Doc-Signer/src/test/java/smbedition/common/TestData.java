package smbedition.common;

import smbedition.organization.services.OrgServices;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TestData {
    private static Random random = new Random();

    private static String orgId;
    private static String roleId;
    private static String otpOrderId;
    private static String otpOrderIdUpdateUser;
    private static String otpOrderIdRemoveUser;

    private static  String signatoryId;
    private static  String signatoryId2;
    private static  String useridForSignatory;
    private static  String  useridForSignatory2;

    public static String generateRandomEmail() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String email = "";
        for (int i = 0; i < 8; i++) {
            email += chars.charAt(random.nextInt(chars.length()));
        }
        return email + "@mybizz.com";
    }

    public static String generateRandomMobile() {
        String digits = "0123456789";
        String mobile = "";
        for (int i = 0; i < 10; i++) {
            mobile += digits.charAt(random.nextInt(digits.length()));
        }
        return mobile;
    }

    public static String generateRandomPassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "@#$%";
        String all = upper + lower + digits + special;

        StringBuilder password = new StringBuilder();

        // Ensure at least one of each required type
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));

        // Fill remaining with random characters
        for (int i = 4; i < 12; i++) {
            password.append(all.charAt(random.nextInt(all.length())));
        }

        // Shuffle to avoid predictable positions
        List<Character> passwordChars = password.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(passwordChars);

        StringBuilder finalPassword = new StringBuilder();
        for (char c : passwordChars) {
            finalPassword.append(c);
        }

        return finalPassword.toString();
    }

    public static String generateRandomFirstName(){
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder userName = new StringBuilder();
        userName.append(Character.toUpperCase(chars.charAt(random.nextInt(chars.length()))));

        for(int i =0;i<6;i++){
            userName.append((chars.charAt(random.nextInt(chars.length()))));

        }
        return userName.toString();
}



    public static String generateRandomLastName(){
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder userName = new StringBuilder();
        userName.append(Character.toUpperCase(chars.charAt(random.nextInt(chars.length()))));
        for(int i =0;i<5;i++){
            userName.append((chars.charAt(random.nextInt(chars.length()))));

        }
        return userName.toString();
    }





//   ===========================

    public static String generateOrganizationName() {
        String[] prefixes = {"DRAG AND DROP", "SKYLINE", "INFINITY", "VISION", "QUANTUM", "GLOBAL", "FUSION"};
        String[] suffixes = {"INDIA", "TECH", "SOFTWARE", "CONSULTING", "DIGITAL", "SYSTEMS"};
        return prefixes[random.nextInt(prefixes.length)] + " " +
                suffixes[random.nextInt(suffixes.length)] + " PRIVATE LIMITED";
    }

    // Generate 10-digit Organization ID like 0420250002
    public static String generateOrganizationId() {
        int year = 2025; // or use LocalDate.now().getYear()
        int serial = 1000 + random.nextInt(9000);
        return String.format("04%d%04d", year, serial);
    }

    // Generate random address
    public static String generateAddress() {
        String[] buildings = {"Spaces & More 2", "Akshara Grand", "Tech Park One", "Cyber Heights", "Sky Towers"};
        int plotNo = 90 + random.nextInt(50);
        return String.format("4A, 4th Floor, %s, Plot No %d & %d",
                buildings[random.nextInt(buildings.length)], plotNo, plotNo + 1);
    }

    // Mandal / Taluk
    public static String generateMandalOrTaluk() {
        String[] areas = {"Lumbini Layout, Gachibowli, Hyderabad", "Madhapur, Hyderabad",
                "Whitefield, Bangalore", "Hinjewadi, Pune", "Salt Lake, Kolkata"};
        return areas[random.nextInt(areas.length)];
    }

    // 6-digit pincode
    public static String generatePincode() {
        int pin = 100000 + random.nextInt(900000);
        return String.valueOf(pin);
    }

    // Organization type (random number as string)
    public static String generateOrganizationType() {
        int type = 1 + random.nextInt(9);
        return String.valueOf(type);
    }

    // GST-style Registration Number (15 chars)
    public static String generateRegistrationNumber() {
        String pan = generateDocumentNumber();
        String stateCode = "36"; // Telangana example
        return stateCode + pan + "1Z" + (char) (65 + random.nextInt(26));
    }


    // PAN-style Document Number (10 chars: 5 letters + 4 digits + 1 letter)
    public static String generateDocumentNumber() {
        String letters = randomLetters(5);
        String digits = String.format("%04d", random.nextInt(10000));
        char last = (char) ('A' + random.nextInt(26));
        return letters + digits + last;
    }

    // Helper to generate random uppercase letters
    private static String randomLetters(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) ('A' + random.nextInt(26));
            sb.append(c);
        }
        return sb.toString();
    }



    public static String getRandomStateId() {
        List<String> states = OrgServices.getStateIds(); // replace with actual class holding get_Organization_countrySpecific
        if (states == null || states.isEmpty()) {
            throw new IllegalStateException("No state IDs available. Make sure get_Organization_countrySpecific() is called first.");
        }
        Random random = new Random();
        return states.get(random.nextInt(states.size()));
    }

    public static  String getRandomCityId() {
        List<String> cities = OrgServices.getCityIds();
        if (cities == null || cities.isEmpty()) {
            throw new IllegalStateException("No city IDs available. Make sure get_Organization_countrySpecific() is called first.");
        }
        Random random = new Random();
        return cities.get(random.nextInt(cities.size()));
    }


    // ===== ORG ID =====
    public static String getOrgId() {
        return orgId;
    }

    public static void setOrgId(String id) {
        orgId = id;
    }

//    =======Role ID =======
    public static String getRoleId() {
        return roleId;
    }

    public static void setRoleId(String id) {
        roleId = id;
    }

//    ============Otp order Id ===========
    public static  String getOtpOrderId() {
        return otpOrderId;
    }
    public static  void  setOtpOrderId(String id){
        otpOrderId = id;
    }
    public static  String getOtpOrderIdUpdateUser() {
        return otpOrderIdUpdateUser;
    }
    public static  void  setOtpOrderIdUpdateUser(String id){
        otpOrderIdUpdateUser = id;
    }
    public static  String getOtpOrderIdRemoveUser() {
        return otpOrderIdRemoveUser;
    }
    public static  void  setOtpOrderIdRemoveUser(String id){
        otpOrderIdRemoveUser = id;
    }


    public static String generateRandomEmpId(){
        int empId = 1000 + random.nextInt(9000);
        return String.valueOf(empId);
    }
    public static String generateDesignation() {
        String[] designations = {
                "Software Developer", "Senior Developer", "QA Engineer",
                "Business Analyst", "Tech Lead", "Manager", "DevOps Engineer"
        };
        return designations[new Random().nextInt(designations.length)];
    }


    public static String generateDepartment() {
        String[] departments = {
                "Technical", "Finance", "HR", "Operations", "Support", "Sales", "Marketing"
        };
        return departments[new Random().nextInt(departments.length)];
    }


    // Random Access Start Date (any date in 2025)
    public static String generateAccessStartDate() {
        int day = 1 + new Random().nextInt(28);  // valid days
        int month = 1 + new Random().nextInt(12);
        return String.format("%02d/%02d/2025", day, month);
    }


    public static String getSignatoryId() {
        return signatoryId;
    }
    public static void setSignatoryId (String id){
        signatoryId = id;
    }


    public static String getSignatoryId2() {
        return signatoryId2;
    }
    public static void setSignatoryId2 (String id){
        signatoryId2= id;
    }


    public static String getUseridForSignatory () {
        return useridForSignatory;
    }
    public static void setUseridForSignatory (String id){
        useridForSignatory =id;
    }

    public static String getUseridForSignatory2 () {
        return useridForSignatory2;
    }
    public static void setUseridForSignatory2 (String id){
        useridForSignatory2 =id;
    }


}