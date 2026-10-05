package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class LeavePage {

	private WebDriver driver;

	public LeavePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGE

	@FindBy(id = "pageTitle")
	private WebElement pageTitle;

	@FindBy(css = ".panel")
	private WebElement leaveManagementPanel;

	@FindBy(xpath = "//tbody/tr/td[7]")
	List<WebElement> leaveDays;

	@FindBy(css = "button[data-testid*='approve-leave']")
	private WebElement approveLeave;

	// FILTERS

	@FindBy(id = "lvStatusFilter")
	private WebElement leaveStatusFilter;

	@FindBy(id = "lvClearFilters")
	private WebElement clearFilters;

	@FindBy(id = "lvSearch")
	private WebElement leaveSearch;

	// TABLE

	@FindBy(xpath = "//tbody/tr")
	private List<WebElement> leaveRows;

	@FindBy(id = "nextPage")
	private WebElement nextPage;

	// STATUS

	@FindBy(css = ".badge-amber")
	private WebElement pendingStatus;

	@FindBy(css = ".badge-green")
	private WebElement approvedStatus;

	// CONFIRMATION

	@FindBy(id = "confirmTitle")
	private WebElement confirmTitle;

	@FindBy(id = "confirmButton")
	private WebElement confirmButton;

	// NAVIGATION

	@FindBy(css = "button[data-testid='nav-users']")
	private WebElement usersNavigation;

	private final By leaveIdCell = By.xpath("./td[1]");

	private final By employeeNameCell = By.xpath("./td[2]");

	private final By departmentCell = By.xpath("./td[3]");

	private final By leaveTypeCell = By.xpath("./td[4]");

	// METHODS

	public String getPageTitle() {

		return pageTitle.getText();
	}

	public boolean isLeaveManagementPanelDisplayed() {

		return leaveManagementPanel.isDisplayed();
	}

	public void selectLeaveStatus(String status) {

		Select select = new Select(leaveStatusFilter);

		select.selectByVisibleText(status);
	}

	public int getLeaveRowCount() {

		return leaveRows.size();
	}

	public void clearFilters() {

		clearFilters.click();
	}

	public void searchEmployee(String employeeName) {

		leaveSearch.clear();

		leaveSearch.sendKeys(employeeName);
	}

	public void printDetailsByLeaveType(String leaveType) {
		selectLeaveStatus(leaveType);
		while (true) {
			List<WebElement> leaves = leaveRows;

			for (WebElement leave : leaves) {
				String leaveId = leave.findElement(leaveIdCell).getText();

				String employeeName = leave.findElement(employeeNameCell).getText();

				String department = leave.findElement(departmentCell).getText();

				String typeOfLeave = leave.findElement(leaveTypeCell).getText();

				System.out.println("Leave ID: " + leaveId);
				System.out.println("Employee Name: " + employeeName);
				System.out.println("Department: " + department);
				System.out.println("Leave Type: " + typeOfLeave);
				System.out.println("-------------------------");
			}
			if (!nextPage.isEnabled()) {
				break;
			}
			nextPage.click();
		}
	}

	// APPROVE LEAVE

	public boolean approveLeaveIfDaysWithinLimit(String empName, int days) {
		leaveSearch.sendKeys(empName);
		Assert.assertTrue(leaveManagementPanel.isDisplayed());
		int numberOfLeaveDays = Integer.parseInt(leaveDays.getFirst().getText());
		if (numberOfLeaveDays <= days) {
			clickApproveLeave();
			Assert.assertTrue(isApprovalConfirmationDisplayed());
			confirmLeaveApproval();
			Assert.assertTrue(isLeaveApproved());

			System.out.println("Leave Approved ");
			clearFilters();

		}
		return isLeaveApproved();

	}

	public void clickApproveLeave() {

		approveLeave.click();
	}

	public boolean isApprovalConfirmationDisplayed() {

		return confirmTitle.isDisplayed();
	}

	public void confirmLeaveApproval() {

		confirmButton.click();
	}

	public boolean isLeaveApproved() {

		return approvedStatus.isDisplayed();
	}

	public String getApprovedStatus() {

		return approvedStatus.getText();
	}

	// NAVIGATION

	public void gotoUersRolePage() {

		usersNavigation.click();
	}
}