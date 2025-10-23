package smbedition.common.logging;

import jdk.jfr.Description;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestLogger {

    public static void testStarted(Method method) {
        Log.testStart(getTestName(method));
        Log.info("📖 Test Description: " + getTestDescription(method));
        Log.info("🎯 Test Priority: " + getTestPriority(method));
    }

    public static void testFinished(Method method, ITestResult result) {
        long executionTime = result.getEndMillis() - result.getStartMillis();

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                Log.testPass(getTestName(method));
                Log.info("⏱️ Execution Time: " + executionTime + "ms");
                break;
            case ITestResult.FAILURE:
                Log.testFail(getTestName(method), result.getThrowable().getMessage());
                Log.error("Stack Trace:", result.getThrowable());
                break;
            case ITestResult.SKIP:
                Log.testSkip(getTestName(method));
                break;
        }

        Log.info("📊 Test Status: " + getStatusText(result.getStatus()));
    }

    public static void testData(String dataName, Object dataValue) {
        Log.data(dataName, dataValue);
    }

    public static void assertion(String assertionDescription, boolean condition) {
        if (condition) {
            Log.verification("✓ " + assertionDescription);
        } else {
            Log.error("✗ Assertion Failed: " + assertionDescription);
        }
    }

    private static String getTestName(Method method) {
        return method.getDeclaringClass().getSimpleName() + "." + method.getName();
    }

    private static String getTestDescription(Method method) {
        Description description = method.getAnnotation(Description.class);
        return description != null ? description.value() : "No description provided";
    }

    private static String getTestPriority(Method method) {
        Test test = method.getAnnotation(Test.class);
        return test != null ? String.valueOf(test.priority()) : "Not specified";
    }

    private static String getStatusText(int status) {
        switch (status) {
            case ITestResult.SUCCESS: return "PASSED";
            case ITestResult.FAILURE: return "FAILED";
            case ITestResult.SKIP: return "SKIPPED";
            default: return "UNKNOWN";
        }
    }
}