package smbedition.authentication.base;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import smbedition.common.ConfigLoader;
import smbedition.common.RequestSpecFactory;

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
