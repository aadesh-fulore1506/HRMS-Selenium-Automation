package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AttendancePage {

	private WebDriver driver;

	public AttendancePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGE

	@FindBy(id = "pageTitle")
	private WebElement pageTitle;

	// ATTENDANCE TABLE

	@FindBy(xpath = "//tbody/tr")
	private List<WebElement> attendanceRows;

	// FILTERS

	@FindBy(id = "attSearch")
	private WebElement attendanceSearch;

	@FindBy(id = "attDeptFilter")
	private WebElement departmentFilter;

	@FindBy(id = "attDateFilter")
	private WebElement attendanceDateFilter;

	@FindBy(id = "attClearFilters")
	private WebElement clearFilters;

	// PAGINATION

	@FindBy(id = "nextPage")
	private WebElement nextPage;

	// STATUS

	@FindBy(xpath = "//td/span")
	private List<WebElement> employeeStatuses;

	// MARK ATTENDANCE

	@FindBy(id = "markAttBtn")
	private WebElement markAttendanceButton;

	@FindBy(id = "a_date")
	private WebElement attendanceDate;

	@FindBy(id = "a_emp")
	private WebElement employeeDropdown;

	@FindBy(id = "a_in")
	private WebElement inTime;

	@FindBy(id = "a_out")
	private WebElement outTime;

	@FindBy(id = "a_status")
	private WebElement attendanceStatusDropdown;

	@FindBy(id = "saveAttBtn")
	private WebElement saveAttendanceButton;

	// SUCCESS MESSAGE

	@FindBy(css = ".toast-message.success")
	private WebElement attendanceSuccessMessage;

	// NAVIGATION

	@FindBy(css = "button[data-testid='nav-leave-management']")
	private WebElement leaveNavigation;

	private final By rows = By.xpath("./ancestor::tr");
	private final By employeeId = By.xpath("./td[1]");
	private final By employeeName = By.xpath("./td[2]");
	private final By employeeDepartment = By.xpath("./td[3]");
	private final By date = By.xpath("./td[4]");

	// METHODS

	public String getPageTitle() {

		return pageTitle.getText();
	}

	public int getAttendanceRecordCount() {

		return attendanceRows.size();
	}

	public void searchEmployee(String employeeId) {

		attendanceSearch.clear();

		attendanceSearch.sendKeys(employeeId);
	}

	public void selectDepartment(String department) {

		Select select = new Select(departmentFilter);

		select.selectByVisibleText(department);
	}

	public void filterByDate(String date) {

		attendanceDateFilter.clear();

		attendanceDateFilter.sendKeys(date);
	}

	public void clearFilters() {

		clearFilters.click();
	}

	public int getAttendanceRecordsByStatus(String status) {

		int count = 0;

		for (WebElement row : attendanceRows) {

			String rowStatus = row.findElement(By.xpath("./td[6]")).getText();

			if (rowStatus.equalsIgnoreCase(status)) {

				count++;
			}
		}

		return count;
	}

	// MARK ATTENDANCE

	public String markEmployeeAttendance(String empId, String in, String out, String status) {

		clickMarkAttendance();

		String date = attendanceDate.getAttribute("value");
		String formattedDate = date.substring(8, 10) + date.substring(5, 7) + date.substring(0, 4);

		selectEmployee(empId);

		enterInTime(in);

		enterOutTime(out);

		selectAttendanceStatus(status);

		saveAttendance();
		return formattedDate;
	}

	public boolean verifyAttendanceMarked(String empId, String date) {
		attendanceSearch.sendKeys(empId);
		filterByDate(date);
		return attendanceRows.getFirst().isDisplayed();
	}

	public void clickMarkAttendance() {

		markAttendanceButton.click();
	}

	public void selectEmployee(String employeeId) {

		Select select = new Select(employeeDropdown);

		select.selectByValue(employeeId);
	}

	public void enterInTime(String time) {

		inTime.clear();

		inTime.sendKeys(time);
	}

	public void enterOutTime(String time) {

		outTime.clear();

		outTime.sendKeys(time);
	}

	public void selectAttendanceStatus(String status) {

		Select select = new Select(attendanceStatusDropdown);

		select.selectByVisibleText(status);
	}

	public void saveAttendance() {

		saveAttendanceButton.click();
	}

	public String getAttendanceSuccessMessage() {

		return attendanceSuccessMessage.getText();
	}

	// NAVIGATION

	public void gotoLeavePage() {

		leaveNavigation.click();
	}

	public void getEmployeeDetails(String empId, String dep) {
		attendanceSearch.sendKeys(empId);
		selectDepartment(dep);

		ArrayList<String> presentList = new ArrayList<>();
		ArrayList<String> lateList = new ArrayList<>();
		ArrayList<String> halfDayList = new ArrayList<>();
		ArrayList<String> leaveList = new ArrayList<>();

		for (WebElement status : employeeStatuses) {
			WebElement row = status.findElement(rows);
			String id = row.findElement(employeeId).getText();

			String name = row.findElement(employeeName).getText();

			String department = row.findElement(employeeDepartment).getText();

			String attendanceDate = row.findElement(date).getText();
			String empStatus = status.getText();
			String data = id + " | " + name + " | " + department + " | " + date + " | " + empStatus;

			if (empStatus.contains("Present")) {

				presentList.add(data);

			} else if (empStatus.contains("Late")) {

				lateList.add(data);

			} else if (empStatus.contains("Half Day")) {

				halfDayList.add(data);

			} else if (empStatus.contains("Leave")) {

				leaveList.add(data);
			}
			if (nextPage.isEnabled()) {
				nextPage.click();
			}
		}
		System.out.println("===== PRESENT =====");

		for (String data : presentList) {
			System.out.println(data);
		}

		System.out.println("Total Present: " + presentList.size());

		System.out.println("\n===== LATE =====");

		for (String data : lateList) {
			System.out.println(data);
		}

		System.out.println("Total Late: " + lateList.size());

		System.out.println("\n===== HALF DAY =====");

		for (String data : halfDayList) {
			System.out.println(data);
		}

		System.out.println("Total Half Day: " + halfDayList.size());

		System.out.println("\n===== LEAVE =====");

		for (String data : leaveList) {
			System.out.println(data);
		}

		System.out.println("Total Leave: " + leaveList.size());
	}

}
