package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import factory.DriverFactory;
import io.qameta.allure.Allure;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("🔵 Suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🟢 Suite finished: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🚀 Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("❌ Test failed: " + testName);
        byte[] screenshot = CommUtils.captureScreenshot();

        // Attach to Allure
        Allure.addAttachment(testName + " - Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");

        // Save to local folder
        try {
            CommUtils.captureScreen(DriverFactory.getDriver(), testName);
        } catch (IOException e) {
            System.err.println("⚠️ Failed to save screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏩ Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("⚠️ Test failed but within success percentage: " + result.getMethod().getMethodName());
    }
}
