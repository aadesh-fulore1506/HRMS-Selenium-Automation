package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class UserRolePage {

	private WebDriver driver;

	public UserRolePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGE

	@FindBy(id = "pageTitle")
	private WebElement pageTitle;

	@FindBy(id = "confirmButton")
	private WebElement deleteUser;

	// USER TABLE

	@FindBy(xpath = "//tbody/tr")
	private List<WebElement> userRows;

	// ADD USER

	@FindBy(xpath = "//button[contains(text(),'Add User')]")
	private WebElement addUserButton;

	@FindBy(id = "u_name")
	private WebElement userName;

	@FindBy(id = "u_email")
	private WebElement userEmail;

	@FindBy(id = "u_role")
	private WebElement userRole;

	@FindBy(id = "u_status")
	private WebElement userStatus;

	@FindBy(xpath = "//button[contains(text(),'Save User')]")
	private WebElement saveUserButton;

	// TOAST

	@FindBy(css = ".toast-message.success")
	private WebElement successMessage;

	// NAVIGATION

	@FindBy(css = "button[data-testid='nav-reports']")
	private WebElement reportsNavigation;

	private final By userNameCell = By.xpath("./td[1]");

	private final By usernameEmailCell = By.xpath("./td[2]");

	private final By userRoleCell = By.xpath("./td[3]");

	private final By userStatusCell = By.xpath("./td[4]");

	// METHODS

	public String getPageTitle() {

		return pageTitle.getText();
	}

	public List<WebElement> getUserRows() {

		return userRows;
	}

	public String getUserName(WebElement row) {

		return row.findElement(By.xpath("./td[1]")).getText();
	}

	public String getUserRole(WebElement row) {

		return row.findElement(By.xpath("./td[3]")).getText();
	}

	public String getUsernameEmail(WebElement row) {

		return row.findElement(By.xpath("./td[2]")).getText();
	}

	public String getStatus(WebElement row) {

		return row.findElement(By.xpath("./td[4]")).getText();
	}

	public boolean isUserPresent(String name, String role) {

		for (WebElement row : userRows) {

			if (getUserName(row).equalsIgnoreCase(name) && getUserRole(row).equalsIgnoreCase(role)) {

				return true;
			}
		}

		return false;
	}

	public void clickAddUser() {

		addUserButton.click();
	}

	public void addNewUser(String name, String email, String role, String status) {

		clickAddUser();
		userName.sendKeys(name);

		userEmail.sendKeys(email);

		userRole.sendKeys(role);

		userStatus.sendKeys(status);

		saveUserButton.click();
	}

	public String getSuccessMessage() {

		return successMessage.getText();
	}

	public void deleteUser(String name, String role) {

		for (WebElement row : userRows) {

			if (getUserName(row).equalsIgnoreCase(name) && getUserRole(row).equalsIgnoreCase(role)) {

				row.findElement(By.xpath(".//button[2]")).click();
				deleteUser.click();

				break;
			}
		}
	}

	public void gotoReportPage() {

		reportsNavigation.click();
	}

	public boolean[] verifyDefaultUsers() {
		boolean adminFound = false;
		boolean hrFound = false;
		boolean employeeFound = false;
		List<WebElement> users = userRows;
		for (WebElement user : users) {

			String name = getUserName(user);

			String role = getUserRole(user);

			String status = getStatus(user);

			if (name.equals("System Administrator")) {
				adminFound = true;
				Assert.assertEquals(role, "Admin");
				Assert.assertEquals(status, "Active");
				System.out.println("Admin Found.");
			}

			if (name.equals("Priya Sharma")) {
				hrFound = true;
				Assert.assertEquals(role, "HR");
				Assert.assertEquals(status, "Active");
				System.out.println("HR Found.");

			}

			if (name.equals("Aarav Sharma")) {
				employeeFound = true;
				Assert.assertEquals(role, "Employee");
				Assert.assertEquals(status, "Active");
				System.out.println("Employee Found.");

			}

		}
		return new boolean[] { adminFound, hrFound, employeeFound };
	}

	public boolean isNewUserAdded(String name, String email, String role, String status) {

		for (WebElement userRow : userRows) {

			String userName = getUserName(userRow);
			String usernameEmail = getUsernameEmail(userRow);
			String roleName = getUserRole(userRow);
			String statusName = getStatus(userRow);

			if (userName.equals(name) && usernameEmail.equals(email) && roleName.equals(role)
					&& statusName.equals(status)) {

				return true;
			}
		}

		return false;
	}
}
