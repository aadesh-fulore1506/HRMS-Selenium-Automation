package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReportPage {

	private WebDriver driver;

	public ReportPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGE

	@FindBy(id = "pageTitle")
	private WebElement pageTitle;

	// REPORT BUTTONS

	@FindBy(xpath = "//button[contains(text(),'Employee Report')]")
	private WebElement employeeReportButton;

	@FindBy(xpath = "//button[contains(text(),'Attendance Report')]")
	private WebElement attendanceReportButton;

	@FindBy(xpath = "//button[contains(text(),'Leave Report')]")
	private WebElement leaveReportButton;

	// REPORT TABLE

	@FindBy(xpath = "//table[@id='reportTable']//thead//th")
	private List<WebElement> reportHeaders;

	@FindBy(xpath = "//table[@id='reportTable']//tbody/tr")
	private List<WebElement> reportRows;

	// LOGOUT

	@FindBy(id = "logoutBtn")
	private WebElement logoutButton;

	@FindBy(id = "confirmTitle")
	private WebElement confirmLogout;

	@FindBy(id = "confirmButton")
	private WebElement confirmButton;

	// METHODS

	public String getPageTitle() {

		return pageTitle.getText();
	}

	public void clickEmployeeReport() {

		employeeReportButton.click();
	}

	public void clickAttendanceReport() {

		attendanceReportButton.click();
	}

	public void clickLeaveReport() {

		leaveReportButton.click();
	}

	public int getReportRecordCount() {

		return reportRows.size();
	}

	public List<WebElement> getReportRows() {

		return reportRows;
	}

	// LOGOUT

	public void clickLogout() {

		logoutButton.click();
	}

	public boolean isLogoutConfirmationDisplayed() {

		return confirmLogout.isDisplayed();
	}

	public void confirmLogout() {

		confirmButton.click();
	}
}