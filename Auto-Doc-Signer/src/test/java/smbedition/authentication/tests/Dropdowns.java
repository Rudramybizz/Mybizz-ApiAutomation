package smbedition.authentication.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import smbedition.common.BaseTest;
import smbedition.authentication.services.DropdownsService;

import java.util.List;
import java.util.Map;

public class Dropdowns extends BaseTest {

    //   ====================== Countries Api ======================

    @Test(priority = 1, description = "Get Countries Dropdown")
    public void get_Countries() {
        Response response = DropdownsService.getCountries();
        Assert.assertEquals(response.getStatusCode(), 200, "Get Countries dropdown API failed.");
        // Optionally validate the response structure
        JsonPath json = response.jsonPath();
        Assert.assertNotNull(json.getList("data"), "Countries data is missing in response");
        System.out.println("Total Sessions: " + json.getList("data").size());
    }




//    @Test(priority = 2, dependsOnMethods = "get_Countries", description = "Get States Dropdown for first 3 countries")
    public void get_States() {
        List<Response> responses = DropdownsService.getStates();

        for (Response response : responses) {
            Assert.assertEquals(response.getStatusCode(), 200, "Get States API failed.");
            Assert.assertNotNull(response.jsonPath().getList("data"), "States data missing in response");
        }
    }

//    @Test(priority = 3, dependsOnMethods = "get_States", description = "Get Cities Dropdown for first 3 states")
    public void get_Cities() {
        List<Response> responses = DropdownsService.getCities();

        for (Response response : responses) {
            Assert.assertEquals(response.getStatusCode(), 200, "Get Cities API failed.");
            Assert.assertNotNull(response.jsonPath().getList("data"), "Cities data missing in response");
        }
    }

    @Test(priority = 4, description = "Get profile Settings Dropdown.")
    public void get_profileSettings() {
        Response response = DropdownsService.getProfileSettings();
        Assert.assertEquals(response.getStatusCode(), 200, "Get profile Settings API failed.");
        // Optionally validate the response structure
        JsonPath json = response.jsonPath();
        Object data = json.get("data");

        if (data instanceof List) {
            List<?> dataList = (List<?>) data;
            Assert.assertFalse(dataList.isEmpty(), "Profile Settings data is missing in response");
            System.out.println("Total Sessions: " + dataList.size());
        } else if (data instanceof Map) {
            Map<?, ?> dataMap = (Map<?, ?>) data;
            Assert.assertFalse(dataMap.isEmpty(), "Profile Settings data is missing in response");
            System.out.println("Profile Settings returned a single object: " + dataMap);
        } else {
            Assert.fail("Unexpected response format for 'data'");
        }
    }


   @Test(priority =5, description = "Get Language Api.")
  public void get_Language(){
        Response response = DropdownsService.getLanguage();
        Assert.assertEquals(response.getStatusCode(),200,"Get Language API Failed.");
        JsonPath  json = response.jsonPath();
        Assert.assertNotNull(json.getList("data"),"Language data is missing in response");
        System.out.println("Total Sessions:"+json.getList("data").size());
  }

  @Test(priority = 6, description = "Get Dialing Code API.")
  public void get_DialingCode(){
        Response response = DropdownsService.getDialingCode();
        Assert.assertEquals(response.getStatusCode(),200,"Get Dialing Code API.");
        JsonPath json = response.jsonPath();
        Assert.assertNotNull(json.getList("data"), "Dialing code  data is missing in response");
        System.out.println("Total Sessions: "+json.getList("data").size());
  }

  @Test(priority = 7, description = "Get Currency API.")
  public void get_Currency(){
        Response response = DropdownsService.getCurrency();
        Assert.assertEquals(response.getStatusCode(),200,"Get Currency API." );
        JsonPath json = response.jsonPath();
        Assert.assertNotNull(json.getList("data"),"Currency  code data is missing in response.");
        System.out.println("Total Sessions :"+json.getList("data").size());
  }




}