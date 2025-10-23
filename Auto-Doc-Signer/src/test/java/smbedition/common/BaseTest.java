package smbedition.common;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setup() {
        // Load configuration first
        ConfigLoader.load();

        // Initialize request specification
        RequestSpecFactory.init();

        logTestSetup();
    }

    private void logTestSetup() {
        System.out.println("\n🚀 TEST SETUP COMPLETE");
        System.out.println("Environment: " + ConfigLoader.getCurrentEnvironment().toUpperCase());
        System.out.println("Base URL: " + ConfigLoader.get("base.url"));
        System.out.println("SSO URL: " + ConfigLoader.get("sso.base.url"));
        System.out.println("========================\n");
    }

    @AfterSuite(alwaysRun = true)
    public void teardown() {
        RequestSpecFactory.remove();
        System.out.println("🏁 Test execution completed for environment: " +
                ConfigLoader.getCurrentEnvironment().toUpperCase());
    }
}