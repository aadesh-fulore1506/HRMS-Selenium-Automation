package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeePage {

	private WebDriver driver;

	public EmployeePage(WebDriver driver) {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGE

	@FindBy(id = "pageTitle")
	private WebElement pageTitle;

	// EMPLOYEE TABLE

	@FindBy(xpath = "//tbody/tr")
	private List<WebElement> employeeRows;

	@FindBy(css = ".badge.badge-green")
	private List<WebElement> activeEmployees;

	@FindBy(css = ".badge.badge-red")
	private List<WebElement> inactiveEmployees;

	// PAGINATION

	@FindBy(id = "nextPage")
	private WebElement nextPage;

	// FILTERS

	@FindBy(id = "clearFilters")
	private WebElement clearFilters;

	@FindBy(id = "empSearch")
	private WebElement employeeSearch;

	@FindBy(id = "empDeptFilter")
	private WebElement departmentFilter;

	// ADD EMPLOYEE

	@FindBy(id = "addEmployeeBtn")
	private WebElement addEmployeeButton;

	@FindBy(id = "f_id")
	private WebElement employeeId;

	@FindBy(id = "f_status")
	private WebElement employeeStatus;

	@FindBy(id = "f_fn")
	private WebElement firstName;

	@FindBy(id = "f_ln")
	private WebElement lastName;

	@FindBy(id = "f_email")
	private WebElement email;

	@FindBy(id = "f_phone")
	private WebElement phone;

	@FindBy(id = "f_dob")
	private WebElement dateOfBirth;

	@FindBy(id = "f_gender")
	private WebElement gender;

	@FindBy(id = "f_dept")
	private WebElement department;

	@FindBy(id = "f_desig")
	private WebElement designation;

	@FindBy(id = "f_join")
	private WebElement joiningDate;

	@FindBy(id = "f_empType")
	private WebElement employeeType;

	@FindBy(id = "f_salary")
	private WebElement salary;

	@FindBy(id = "f_addr")
	private WebElement address;

	@FindBy(id = "f_city")
	private WebElement city;

	@FindBy(id = "f_state")
	private WebElement state;

	@FindBy(id = "f_postal")
	private WebElement postalCode;

	@FindBy(id = "f_emerg")
	private WebElement emergencyContact;

	@FindBy(id = "saveEmpBtn")
	private WebElement saveEmployeeButton;

	@FindBy(css = ".badge.badge-red")
	private List<WebElement> inActive;

	// NAVIGATION

	@FindBy(css = "button[data-testid='nav-departments']")
	private WebElement departmentsNavigation;

	private final By rows = By.xpath("ancestor::tr");
	private final By empId = By.xpath("./td[1]");
	private final By empStatus = By.xpath("./td[9]");

	// METHODS

	public String getPageTitle() {

		return pageTitle.getText();
	}

	public int getActiveEmployeeCount() {

		return activeEmployees.size();
	}

	public int getInactiveEmployeeCount() {

		return inactiveEmployees.size();
	}

	public void searchEmployee(String employeeId) {

		employeeSearch.clear();

		employeeSearch.sendKeys(employeeId);
	}

	public String getFirstEmployeeId() {

		return employeeRows.get(0).findElement(By.xpath("./td[1]")).getText();
	}

	public boolean isEmployeeDisplayed(String employeeId) {

		for (WebElement row : employeeRows) {

			String id = row.findElement(By.xpath("./td[1]")).getText();

			if (id.equals(employeeId)) {

				return true;
			}
		}

		return false;
	}

	public void clearFilters() {

		clearFilters.click();
	}

	public void gotoDepartmentPage() {

		departmentsNavigation.click();
	}

	public int[] getEmployeeCountsAcrossPages() {

		int employeeCount = 0;
		int activeCount = 0;
		int inactiveCount = 0;

		while (true) {

			employeeCount += employeeRows.size();
			activeCount += activeEmployees.size();
			inactiveCount += inActive.size();

			if (!nextPage.isEnabled()) {
				break;
			}

			nextPage.click();
		}

		return new int[] { employeeCount, activeCount, inactiveCount };
	}

	public ArrayList<String> getInactiveEmployeeIdsAcrossPages() {
		ArrayList<String> inactiveEmployees = new ArrayList<>();
		while (true) {
			List<WebElement> inactiveList = inActive;

			for (WebElement inactiveStatus : inactiveList) {

				WebElement row = inactiveStatus.findElement(rows);

				String employeeId = row.findElement(empId).getText();
				inactiveEmployees.add(employeeId);
				String status = row.findElement(empStatus).getText();

				System.out.println("Employee Id: " + employeeId + " | Employee Status: " + status);
			}

			if (!nextPage.isEnabled()) {
				break;
			}

			nextPage.click();

		}
		return inactiveEmployees;
	}

	public int getEmployeeCountByDepartment(String departmentName) {

		Select departmentSelect = new Select(departmentFilter);

		departmentSelect.selectByVisibleText(departmentName);

		return employeeRows.size();
	}

	public void addEmployee(String employeeId, String firstName, String lastName, String phone, String dob,
			String gender, String department, String designation, String dateOfJoining, String employmentType,
			String salary, String address, String city, String state, String postalCode, String emergencyContact) {

		driver.findElement(By.id("f_id")).sendKeys(employeeId);

		Select statusSelect = new Select(driver.findElement(By.id("f_status")));
		statusSelect.selectByVisibleText("Active");

		driver.findElement(By.id("f_fn")).sendKeys(firstName);
		driver.findElement(By.id("f_ln")).sendKeys(lastName);

		String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + employeeId.substring(5)
				+ "@peopleflow.com";

		driver.findElement(By.id("f_email")).sendKeys(email);
		driver.findElement(By.id("f_phone")).sendKeys(phone);
		driver.findElement(By.id("f_dob")).sendKeys(dob);

		Select genderSelect = new Select(driver.findElement(By.id("f_gender")));
		genderSelect.selectByVisibleText(gender);

		Select departmentSelect = new Select(driver.findElement(By.id("f_dept")));
		departmentSelect.selectByVisibleText(department);

		driver.findElement(By.id("f_desig")).sendKeys(designation);
		driver.findElement(By.id("f_join")).sendKeys(dateOfJoining);

		Select employmentTypeSelect = new Select(driver.findElement(By.id("f_empType")));
		employmentTypeSelect.selectByVisibleText(employmentType);

		driver.findElement(By.id("f_salary")).sendKeys(salary);
		driver.findElement(By.id("f_addr")).sendKeys(address);
		driver.findElement(By.id("f_city")).sendKeys(city);
		driver.findElement(By.id("f_state")).sendKeys(state);
		driver.findElement(By.id("f_postal")).sendKeys(postalCode);
		driver.findElement(By.id("f_emerg")).sendKeys(emergencyContact);

		driver.findElement(By.id("saveEmpBtn")).click();
	}

	public void clickAddEmployee() {
		driver.findElement(By.id("addEmployeeBtn")).click();
	}

	WebDriverWait wait;

	public String searchEmployeeById(String searchId) {

		employeeSearch.clear();
		employeeSearch.sendKeys(searchId);
		WebElement employeeRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//tbody/tr/td[1][normalize-space()='" + searchId + "']")));

		String displayId = employeeRow.findElement(By.xpath("//td[1]")).getText();

		System.out.println("Expected ID : " + searchId);
		System.out.println("Displayed ID: " + displayId);
		return displayId;

	}

	public String searchNewEmployee(String id) {
		employeeSearch.clear();
		employeeSearch.sendKeys(id);

		WebElement newEmployeeRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//tbody//tr[td[1][normalize-space()='" + id + "']]")));

		String displayNewEmployeeId = newEmployeeRow.findElement(By.xpath("./td[1]")).getText();
		System.out.println("Expected ID : " + id);
		System.out.println("Displayed ID: " + displayNewEmployeeId);

		return displayNewEmployeeId;
	}

}