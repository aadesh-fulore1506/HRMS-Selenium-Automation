package listeners;

import base.BaseTest;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import utils.ExtentReportManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		ExtentReportManager.startTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReportManager.getTest().log(Status.PASS, "Test Pass");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		String screenshotPath = ScreenshotUtil.capture(BaseTest.getDriver(), testName);

		ExtentReportManager.getTest().log(Status.FAIL, "Test Fail" + result.getThrowable());
		if (screenshotPath != null) {
			try {
				ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
			} catch (Exception e) {
				ExtentReportManager.getTest().log(Status.WARNING, "Coult not attached screenshot" + e.getMessage());
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped" + result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.flush();
	}
}