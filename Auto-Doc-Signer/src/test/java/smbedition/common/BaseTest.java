package smbedition.common;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected RequestSpecification spec;
    private String baseUrl;

    @BeforeSuite(alwaysRun = true)
    public void setup() {
        // Load environment configuration
        ConfigLoader.load();

        baseUrl = ConfigLoader.get("base.url");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("base.url is not defined. Provide -Denv=staging or -Dbase.url=...");
        }

        // Initialize spec without token; token will be set dynamically in AuthApi
        RequestSpecFactory.init(baseUrl);
    }

    @AfterSuite(alwaysRun = true)
    public void teardown() {
        RequestSpecFactory.remove();
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
