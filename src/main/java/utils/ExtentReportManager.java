package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

	private static ExtentReports extent;

	private static final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

	public static ExtentReports getInstance() {

		if (extent == null) {

			ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");

			spark.config().setDocumentTitle("HRMS Automation Report");

			spark.config().setReportName("HRMS Automation Regression Suite");

			extent = new ExtentReports();

			extent.attachReporter(spark);

			extent.setSystemInfo("Environment", ConfigReader.get("base.url"));

			extent.setSystemInfo("Browser", ConfigReader.get("browser"));
		}

		return extent;
	}

	public static void startTest(String testName) {

		ExtentTest test = getInstance().createTest(testName);

		currentTest.set(test);
	}

	public static ExtentTest getTest() {

		return currentTest.get();
	}

	public static void flush() {

		getInstance().flush();
	}

	public static void removeTest() {

		currentTest.remove();
	}
}