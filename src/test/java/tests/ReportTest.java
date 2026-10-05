package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.HRMSBaseTest;
import pages.DashboardPage;
import pages.ReportPage;

public class ReportTest extends HRMSBaseTest {

	private ReportPage reportPage;
	private int dashboardTotalEmployees;

	@BeforeMethod(alwaysRun = true)
	public void openReportPage() {
		// runs after setUp() and login() from the parent classes
		DashboardPage dashboardPage = new DashboardPage(getDriver());
		dashboardPage.clickSidebarToggle();

		dashboardTotalEmployees = dashboardPage.getTotalEmployees();

		dashboardPage.gotoReportPage();
		reportPage = new ReportPage(getDriver());
	}

	@Test(description = "Reports page title")
	public void verifyReportPage() {
		Assert.assertEquals(reportPage.getPageTitle(), "Reports", "Reports page title is incorrect");
	}

	@Test(description = "Employee report matches the dashboard total")
	public void verifyEmployeeReport() {
		reportPage.clickEmployeeReport();

		int rows = reportPage.getReportRows().size();

		Assert.assertTrue(rows > 0, "Employee report is empty");

		Assert.assertEquals(rows, dashboardTotalEmployees,
				"Employee report row count does not match the dashboard total");
	}

	@Test(description = "Attendance report contains records")
	public void verifyAttendanceReport() {
		reportPage.clickAttendanceReport();

		Assert.assertTrue(reportPage.getReportRows().size() > 0, "Attendance report is empty");

	}

	@Test(description = "Leave report contains records")
	public void verifyLeaveReport() {
		reportPage.clickLeaveReport();

		Assert.assertTrue(reportPage.getReportRows().size() > 0, "Leave report is empty");

	}

	@Test(description = "Logout asks for confirmation")
	public void verifyLogoutConfirmation() {
		reportPage.clickLogout();

		Assert.assertTrue(reportPage.isLogoutConfirmationDisplayed(), "Logout confirmation is not displayed");

	}
}