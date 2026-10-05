package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.HRMSBaseTest;
import pages.DashboardPage;
import pages.EmployeePage;

public class EmployeeTest extends HRMSBaseTest {

	private static final Logger log = LogManager.getLogger(EmployeeTest.class);
	private EmployeePage employeePage;

	// Expected values read from the dashboard, so nothing is hardcoded
	private int dashboardTotalEmployees;
	private int dashboardActiveEmployees;
	private int dashboardITCount;

	@BeforeMethod(alwaysRun = true)
	public void openEmployeePage() throws InterruptedException {
		// runs after setUp() and login() from the parent classes
		DashboardPage dashboardPage = new DashboardPage(getDriver());
		dashboardTotalEmployees = dashboardPage.getTotalEmployees();
		dashboardActiveEmployees = dashboardPage.getActiveEmployees();
		dashboardITCount = dashboardPage.getITCount();

		dashboardPage.gotoEmployeePage();
		employeePage = new EmployeePage(getDriver());
		wait.waitForEmployeePageTitleVisible();
	}

	@Test(description = "Employee Management page title")
	public void verifyEmployeePage() {
		Assert.assertEquals(employeePage.getPageTitle(), "Employee Management",
				"Employee Management page title is incorrect");
		log.info("EmpoyeePage Title is Displayed");
	}

	@Test(description = "Employee page counts match the dashboard")
	public void verifyEmployeeCounts() {
		int[] counts = employeePage.getEmployeeCountsAcrossPages();
		int total = counts[0];
		int active = counts[1];
		int inactive = counts[2];

		Assert.assertEquals(active + inactive, total, "Active + inactive should equal total employees");

		Assert.assertEquals(total, dashboardTotalEmployees, "Employee page total does not match dashboard total");

		Assert.assertEquals(active, dashboardActiveEmployees,
				"Employee page active count does not match dashboard active count");

		log.info("EmployeeCounts Are Successfully Verified");
	}

	@Test(description = "Inactive employee IDs")
	public void verifyInactiveEmployeeIds() {
		log.info("Printing All Inactive IDs");
		employeePage.getInactiveEmployeeIdsAcrossPages();

	}

	@Test(description = "Department filter count matches the dashboard")
	public void verifyEmployeeCountByDepartment() {

		log.info("Verify EmployeeCount As Per Department");
		Assert.assertEquals(employeePage.getEmployeeCountByDepartment("IT"), dashboardITCount,
				"IT count on Employee page does not match dashboard");
	}

	@Test(description = "Search employee by ID")
	public void verifyEmployeeSearchById() {

		String searchId = "EMP1025";

		Assert.assertEquals(employeePage.searchEmployeeById(searchId), searchId, "Searched employee ID was not found");
		log.info("Employee Found Successfully with " + searchId);
	}

	@Test(description = "Add a new employee")
	public void verifyAddNewEmployee() {
		employeePage.clearFilters();

		log.info("adding New Employee....");
		int totalBefore = employeePage.getEmployeeCountsAcrossPages()[0];
		String newEmployeeId = "EMP10" + (totalBefore + 1);

		employeePage.clickAddEmployee();
		employeePage.addEmployee(newEmployeeId, "Aadesh", "Fulore", "9372963242", "01-01-2006", "Male", "IT",
				"Software Engineer", "01-12-2006", "Full Time", "60000", "Kalyan, Maharashtra", "Mumbai", "Maharashtra",
				"421301", "9354236545");

		wait.waitForEmployeeCreatedMessage();

		Assert.assertEquals(employeePage.searchNewEmployee(newEmployeeId), newEmployeeId,
				"New employee was not found after adding");

		log.info("New Employee Added Sucessfully");

		employeePage.clearFilters();
		int totalAfter = employeePage.getEmployeeCountsAcrossPages()[0];
		Assert.assertEquals(totalAfter, totalBefore + 1, "Total employee count did not increase by 1");
		log.info("EmployeeCount Successfully Increased....");

	}
}