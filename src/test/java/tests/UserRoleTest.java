package tests;

import java.util.concurrent.ThreadLocalRandom;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.HRMSBaseTest;
import pages.DashboardPage;
import pages.UserRolePage;

public class UserRoleTest extends HRMSBaseTest {

	private static final String role = "HR";
	private static final String status = "Active";

	private UserRolePage userPage;

	// Set when a test creates a user; removed in cleanUp() even if the test fails
	private String createdUserName;

	@BeforeMethod(alwaysRun = true)
	public void openUserRolePage() {
		// runs after setUp() and login() from the parent classes
		DashboardPage dashboardPage = new DashboardPage(getDriver());
		dashboardPage.clickSidebarToggle();
		dashboardPage.gotoUserRolePage();

		userPage = new UserRolePage(getDriver());
	}

	@AfterMethod(alwaysRun = true)
	public void cleanUp() {
		// Child @AfterMethod runs before BaseTest.tearDown(), so the driver is still
		// open
		if (createdUserName != null) {
			userPage.deleteUser(createdUserName, role);
			wait.waitForUserDeletedSuccessMessage();
			createdUserName = null;
		}
	}

	// Letters only, in case the app rejects digits in names
	private static String randomLetters(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append((char) ('a' + ThreadLocalRandom.current().nextInt(26)));
		}
		return sb.toString();
	}

	@Test(description = "Users & Roles page title")
	public void verifyUserRolePage() {
		Assert.assertEquals(userPage.getPageTitle(), "Users & Roles", "Users & Roles page title is incorrect");
	}

	@Test(description = "Default users exist")
	public void verifyDefaultUsers() {
		boolean[] users = userPage.verifyDefaultUsers();

		Assert.assertTrue(users[0], "Admin user not found");
		Assert.assertTrue(users[1], "HR user not found");
		Assert.assertTrue(users[2], "Employee user not found");
	}

	@Test(description = "Add a new user")
	public void verifyAddNewUser() {
		String suffix = randomLetters(6);
		String name = "Auto " + suffix;
		String email = suffix + "@peopleflow.com";

		userPage.addNewUser(name, email, role, status);
		createdUserName = name; // deleted in cleanUp()

		wait.waitForUserAddedSuccessMessage();

		Assert.assertTrue(userPage.isNewUserAdded(name, email, role, status),
				"New user was not added with expected details");
	}

	@Test(description = "Delete a user")
	public void verifyDeleteUser() {
		String suffix = randomLetters(6);
		String name = "Auto " + suffix;
		String email = suffix + "@peopleflow.com";

		userPage.addNewUser(name, email, role, status);
		createdUserName = name; // safety net if anything below fails

		wait.waitForUserAddedSuccessMessage();

		Assert.assertTrue(userPage.isNewUserAdded(name, email, role, status), "Test user was not created");

		userPage.deleteUser(name, role);
		createdUserName = null; // already deleted, so cleanUp() has nothing to do
		wait.waitForUserDeletedSuccessMessage();

		Assert.assertFalse(userPage.isUserPresent(name, role), "User was not deleted successfully");
	}
}