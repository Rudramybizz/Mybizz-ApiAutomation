//package smbedition.sso.tests;
//
//import io.restassured.response.Response;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import smbedition.common.waits.ApiUtil;
//import smbedition.sso.services.SSOService;
//
//public class SSoTest {
//
//    @Test(priority = 1, description = "SSO Registration  API")
//    public void sso_Register(){
//        Response response = SSOService.registerUser();
//        Assert.assertEquals(response.getStatusCode(), 308, "Expected status code 308");
//    }
//
//    @Test(priority = 2,description = "")
//    public void sso_GenerateOtp(){
//        Response response = SSOService.generateOTP();
//        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//        ApiUtil.waitForNextRequest();
//    }
//
////    ,dependsOnMethods = {"sso_Register"}
////    ,dependsOnMethods = {"sso_GenerateOtp"}
//    @Test(priority = 3,description = "", dependsOnMethods = {"sso_GenerateOtp"})
//    public void sso_Login(){
//        ApiUtil.waitForNextRequest();
//        Response response = SSOService.UserLogin();
//        Assert.assertEquals(response.getStatusCode(), 308, "Expected status code 308");
//    }
//
//
//    @Test(priority = 4,description = "")
//    public void sso_validateOtherizationToken(){
//        Response response =  SSOService.validateOtherizationToken();
//        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//    }
//
//    @Test(priority = 5,description = "")
//public void  sso_ResendOtp(){
//        Response response = SSOService.resendOTP();
//    Assert.assertEquals(response.getStatusCode(),308,"Expected status code 308");
//}
//    @Test(priority = 6,description = "")
//    public void  sso_list_Sessions(){
//        Response response = SSOService.list_Sessions();
//        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
//    }
//
//
//
//
//
//
//
//    @Test(priority = 8,description = "")
//    public void  sso_Logout(){
//        Response response = SSOService.sessionLogout();
//        Assert.assertEquals(response.getStatusCode(),308,"Expected status code 308");
//    }
//
//
//
//
//}