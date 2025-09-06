package smbedition.organization.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.organization.services.OTPService;

public class OTPTest {

    @Test(priority = 1, description = "Generate OTp add user API")
    public void testGenerateOtpAddUser() {
        Response response = OTPService.generateOtpAddUser();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

    }

    @Test(priority = 2, description = "Generate OTP update user API")
    public void testGenerateOtp_UpdateUser(){
        Response response = OTPService.generateOtpUpdateUser();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }


    @Test(priority = 3, description = "Generate OTP remove user API")
    public void testGenerateOtp_RemoveUser(){
        Response response = OTPService.generateOtpRemoveUser();
        Assert.assertEquals(response.getStatusCode(),200,"Expected status code 200");
    }






}