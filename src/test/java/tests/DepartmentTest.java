package tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.HRMSBaseTest;
import pages.DashboardPage;
import pages.DepartmentPage;
import pages.EmployeePage;

public class DepartmentTest extends HRMSBaseTest {

	private static final String DESCRIPTION = "Handles product strategy, development, planning, and lifecycle management.";

	private DepartmentPage departmentPage;
	private List<String> dashboardDepartmentNames;

	// Set by tests that create a department; removed in cleanUp() even if the test
	// fails
	private String createdDepartmentId;

	@BeforeMethod(alwaysRun = true)
	public void openDepartmentPage() {
		// runs after setUp() and login() from the parent classes
		DashboardPage dashboardPage = new DashboardPage(getDriver());
		dashboardPage.clickSidebarToggle();
		dashboardDepartmentNames = dashboardPage.getDepartmentNames();
		dashboardPage.gotoEmployeePage();
		new EmployeePage(getDriver()).gotoDepartmentPage();

		departmentPage = new DepartmentPage(getDriver());
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {
		// Child @AfterMethod runs before BaseTest.tearDown(), so the driver is still
		// open
		if (createdDepartmentId != null) {
			departmentPage.clearFilter();
			departmentPage.deleteDepartment(createdDepartmentId);
			wait.waitForDepartmentDeleteMessage();
			createdDepartmentId = null;
		}
	}

	private String uniqueDepartmentName() {
		return "QA Dept " + (System.currentTimeMillis() % 100000);
	}

	@Test(description = "Department Management page title")
	public void verifyDepartmentPage() {
		Assert.assertEquals(departmentPage.getPageTitle(), "Department Management",
				"Department Management page title is incorrect");
	}

	@Test(description = "Department names match the dashboard")
	public void verifyDepartmentNamesMatchDashboard() {
		List<String> departments = departmentPage.getDepartmentNames();

		Assert.assertFalse(departments.isEmpty(), "No departments are displayed");
		Assert.assertEquals(departments, dashboardDepartmentNames, "Department names differ from the dashboard");
	}

	@Test(description = "All department statuses are Active")
	public void verifyDepartmentStatuses() {
		List<WebElement> statuses = departmentPage.getDepartmentStatuses();

		Assert.assertFalse(statuses.isEmpty(), "No department statuses are displayed");

		for (WebElement status : statuses) {
			Assert.assertEquals(status.getText().trim(), "Active", "Department status is not Active");
		}
	}

	@Test(description = "IT department head and status")
	public void verifyITDepartment() {
		Assert.assertFalse(departmentPage.getDepartmentHead("IT").isEmpty(), "IT department head is not displayed");

		Assert.assertEquals(departmentPage.getDepartmentStatus("IT").trim(), "Active",
				"IT department status is incorrect");
	}

	@Test(description = "Edit department head and restore it")
	public void verifyEditDepartmentHead() {
		String original = departmentPage.getDepartmentHead("IT");
		String newHead = original.equals("Manal Mhatre") ? "Shubham Sutar" : "Manal Mhatre";

		try {
			departmentPage.editDepartmentHead("IT", newHead);
			wait.waitForDepartmentEditSuccessMessage();

			Assert.assertEquals(departmentPage.getDepartmentHead("IT"), newHead,
					"Department head was not updated correctly");
		} finally {
			departmentPage.editDepartmentHead("IT", original);
			wait.waitForDepartmentEditSuccessMessage();
		}
	}

	@Test(description = "Cancelling an edit leaves the head unchanged")
	public void verifyCancelDepartmentHeadEdit() {
		String original = departmentPage.getDepartmentHead("IT");
		String other = original.equals("Shubham Sutar") ? "Manal Mhatre" : "Shubham Sutar";

		departmentPage.cancelDepartmentHeadEdit("IT", other);

		Assert.assertEquals(departmentPage.getDepartmentHead("IT"), original, "Cancel changed the department head");
	}

	@Test(description = "Search department")
	public void verifyDepartmentSearch() {
		WebElement department = departmentPage.searchDepartment("Human Resources");

		Assert.assertTrue(department.isDisplayed(), "Human Resources department was not found");

		departmentPage.clearFilter();
	}

	@Test(description = "Add a new department")
	public void verifyAddNewDepartment() {
		String name = uniqueDepartmentName();
		int countBefore = departmentPage.getDepartmentNames().size();

		createdDepartmentId = departmentPage.addNewDepartment(name, "Shubham Sutar", DESCRIPTION, "Active");
		wait.waitForDepartmentAddSSuccessMessage();

		Assert.assertEquals(departmentPage.getUpdatedDepartmentCount(), countBefore + 1,
				"Department count did not increase after adding");

		Assert.assertTrue(departmentPage.searchAddedDepartment(name), name + " was not found after adding");
	}

	@Test(description = "Delete a department")
	public void verifyDeleteDepartment() {
		createdDepartmentId = departmentPage.addNewDepartment(uniqueDepartmentName(), "Shubham Sutar", DESCRIPTION,
				"Active");
		wait.waitForDepartmentAddSSuccessMessage();

		int countBefore = departmentPage.getUpdatedDepartmentCount();

		departmentPage.deleteDepartment(createdDepartmentId);
		createdDepartmentId = null;
		wait.waitForDepartmentDeleteMessage();

		Assert.assertEquals(departmentPage.getUpdatedDepartmentCount(), countBefore - 1,
				"Department count did not decrease after deletion");
	}
}