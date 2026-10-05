package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.HRMSBaseTest;
import pages.AttendancePage;
import pages.DashboardPage;
import pages.DepartmentPage;

public class AttendanceTest extends HRMSBaseTest {

	private static final String employeeID = "EMP1026";
	private static final String checkIn = "9000";
	private static final String checkOut = "1800";
	private static final String status = "Present";

	private AttendancePage attendancePage;

	@BeforeMethod(alwaysRun = true)
	public void openAttendancePage() {
		// runs after setUp() and login() from the parent classes
		DashboardPage dashboardPage = new DashboardPage(getDriver());
		dashboardPage.clickSidebarToggle();
		dashboardPage.gotoAttendancePage();

		attendancePage = new AttendancePage(getDriver());
	}

	@Test(description = "Attendance Management page title")
	public void verifyAttendancePage() {
		Assert.assertEquals(attendancePage.getPageTitle(), "Attendance Management",
				"Attendance Management page title is incorrect");
	}

	@Test(description = "Employee attendance details")
	public void verifyEmployeeDetails() {
		attendancePage.getEmployeeDetails(employeeID, "Human Resources");
	}

	@Test(description = "Mark attendance for an employee")
	public void verifyMarkEmployeeAttendance() {
		String markedDate = attendancePage.markEmployeeAttendance(employeeID, checkIn, checkOut, status);

		wait.waitForAttendanceMarkedSuccessMessage();

		Assert.assertTrue(attendancePage.verifyAttendanceMarked(employeeID, markedDate),
				"Attendance was not marked for " + employeeID + " on " + markedDate);

	}
}