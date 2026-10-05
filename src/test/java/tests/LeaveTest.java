package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.HRMSBaseTest;
import pages.DashboardPage;
import pages.LeavePage;

public class LeaveTest extends HRMSBaseTest {

	// Needs a PENDING request of at most MAX_DAYS days. Once approved, it cannot be
	// approved again.
	private static final String employeeName = "Ishaan Iyer";
	private static final int maxDays = 3;

	private LeavePage leavePage;

	@BeforeMethod(alwaysRun = true)
	public void openLeavePage() {
		// runs after setUp() and login() from the parent classes
		DashboardPage dashboardPage = new DashboardPage(getDriver());
		dashboardPage.clickSidebarToggle();
		dashboardPage.gotoLeavePage();

		leavePage = new LeavePage(getDriver());
	}

	@Test(description = "Leave Management page title")
	public void verifyLeaveManagementPage() {
		Assert.assertEquals(leavePage.getPageTitle(), "Leave Management", "Leave Management page title is incorrect");
	}

	@Test(description = "Leave Management panel is displayed")
	public void verifyLeaveManagementPanel() {
		Assert.assertTrue(leavePage.isLeaveManagementPanelDisplayed(), "Leave Management panel is not displayed");
	}

	@Test(enabled = false, description = "Pending leave requests")
	public void verifyPendingLeaveRequests() {
		leavePage.printDetailsByLeaveType("Pending");
	}

	@Test(description = "Approve a leave request within the day limit")
	public void verifyApproveLeaveRequest() {
		boolean approved = leavePage.approveLeaveIfDaysWithinLimit(employeeName, maxDays);

		Assert.assertTrue(approved,
				"Leave was not approved for " + employeeName
						+ ". Either no pending request exists, or it is longer than " + maxDays
						+ " days. Check the test data.");

		wait.waitForApproveLeaveRequest();

	}
}