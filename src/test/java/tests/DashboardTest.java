package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.HRMSBaseTest;
import pages.DashboardPage;

public class DashboardTest extends HRMSBaseTest {

	private DashboardPage dashboardPage;

	@BeforeMethod(alwaysRun = true)
	public void openDashboard() {
		// runs after setUp() and login() from the parent classes
		dashboardPage = new DashboardPage(getDriver());
		dashboardPage.clickSidebarToggle();
	}

	@Test(description = "Department counts on dashboard add up to total employees")
	public void verifyDepartmentDistribution() {
		int totalEmployees = dashboardPage.getTotalEmployees();

		List<String> departmentNames = dashboardPage.getDepartmentNames();
		Assert.assertFalse(departmentNames.isEmpty(), "No departments shown on dashboard");

		Assert.assertEquals(dashboardPage.getDepartmentEmployeeTotal(), totalEmployees,
				"Department employee total does not match total employees");

		Assert.assertEquals(dashboardPage.getITCount(), 5, "IT count is incorrect");
		Assert.assertEquals(dashboardPage.getHumanResourcesCount(), 5, "HR count is incorrect");
		Assert.assertEquals(dashboardPage.getFinanceCount(), 5, "Finance count is incorrect");
		Assert.assertEquals(dashboardPage.getSalesCount(), 5, "Sales count is incorrect");
		Assert.assertEquals(dashboardPage.getMarketingCount(), 4, "Marketing count is incorrect");
		Assert.assertEquals(dashboardPage.getOperationsCount(), 4, "Operations count is incorrect");
	}

	@Test(description = "Summary cards match the Attendance Overview section")
	public void verifyAttendanceOverviewMatchesSummary() {
		Assert.assertEquals(dashboardPage.getTotalPresentCount(), dashboardPage.getPresentToday(),
				"Present count does not match summary card");

		Assert.assertEquals(dashboardPage.getAttendanceAbsent(), dashboardPage.getAbsentToday(),
				"Absent count does not match summary card");

		Assert.assertEquals(dashboardPage.getAttendanceOnLeave(), dashboardPage.getOnLeave(),
				"On-leave count does not match summary card");
	}

	@Test(description = "Employee summary numbers are consistent")
	public void verifyEmployeeSummary() {
		int total = dashboardPage.getTotalEmployees();
		int active = dashboardPage.getActiveEmployees();

		Assert.assertTrue(total > 0, "Total employees should be greater than 0");
		Assert.assertTrue(active <= total, "Active employees cannot exceed total employees");
		Assert.assertTrue(dashboardPage.getPendingLeaves() >= 0, "Pending leaves cannot be negative");
	}

	@Test(description = "Recent sections are visible")
	public void verifyRecentSectionsDisplayed() {
		Assert.assertTrue(dashboardPage.isRecentEmployeesDisplayed(), "Recent Employees section is not displayed");

		Assert.assertTrue(dashboardPage.isRecentLeaveRequestsDisplayed(),
				"Recent Leave Requests section is not displayed");
	}
}