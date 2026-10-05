package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {

	private final WebDriverWait wait;

	public WaitUtils(WebDriver driver) {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	private final By toastMessageLocator = By.id("toast-container");

	private final By employeeCreatedSuccessMsgLocator = By.id("toast-container");

	private final By departmentCreatedMessageLocator = By.id("toast-container");

	private final By departmentDeleteMessageLocator = By.id("toast-container");

	private final By attendanceMarked = By.cssSelector(".toast-message.success");

	private final By leaveApprovedMsgLocator = By.id("toast-container");

	private final By userAddedSuccessMessageLocator = By.cssSelector(".toast-message.success");

	private final By userDeletedSuccessMessageLocator = By.cssSelector(".toast-message.success");

	private final By confirmLogoutLocator = By.id("confirmTitle");

	private final By employeePageTitle = By.cssSelector("div[data-testid*='page-title']");

	public WebElement waitForVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForVisible(WebElement findby) {
		return wait.until(ExpectedConditions.visibilityOf(findby));
	}

	public WebElement waitForInvisible(WebElement findby) {
		return wait.until(ExpectedConditions.visibilityOf(findby));
	}

	public WebElement waitForClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public boolean waitForInvisible(By locator) {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public boolean waitForUrlContains(String fraction) {
		return wait.until(ExpectedConditions.urlContains(fraction));
	}

	public void waitForLoginToastMessage() {
		waitForVisible(toastMessageLocator);
		waitForInvisible(toastMessageLocator);
	}

	public void waitForEmployeeCreatedMessage() {
		waitForVisible(employeeCreatedSuccessMsgLocator);
		waitForInvisible(employeeCreatedSuccessMsgLocator);
	}

	public void waitForDepartmentEditSuccessMessage() {
		waitForVisible(departmentCreatedMessageLocator);
		waitForInvisible(departmentCreatedMessageLocator);
	}

	public void waitForDepartmentAddSSuccessMessage() {
		waitForVisible(departmentCreatedMessageLocator);
		waitForInvisible(departmentCreatedMessageLocator);
	}

	public void waitForDepartmentDeleteMessage() {
		waitForVisible(departmentDeleteMessageLocator);
		waitForInvisible(departmentDeleteMessageLocator);
	}

	public void waitForAttendanceMarkedSuccessMessage() {
		waitForVisible(attendanceMarked);
		waitForInvisible(attendanceMarked);
	}

	public void waitForUserAddedSuccessMessage() {
		waitForVisible(userAddedSuccessMessageLocator);
		waitForInvisible(userAddedSuccessMessageLocator);
	}

	public void waitForUserDeletedSuccessMessage() {
		waitForVisible(userDeletedSuccessMessageLocator);
		waitForInvisible(userDeletedSuccessMessageLocator);
	}

	public void waitForApproveLeaveRequest() {
		waitForVisible(leaveApprovedMsgLocator);
		waitForInvisible(leaveApprovedMsgLocator);
	}

	public void waitForEmployeePageTitleVisible() {
		waitForVisible(employeePageTitle);

	}

}
