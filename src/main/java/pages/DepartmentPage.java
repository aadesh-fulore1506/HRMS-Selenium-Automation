package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class DepartmentPage {

	private WebDriver driver;

	public DepartmentPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGE

	@FindBy(id = "pageTitle")
	private WebElement pageTitle;

	// DEPARTMENT TABLE

	@FindBy(css = ".panel")
	private WebElement departmentPanel;
	@FindBy(xpath = "//tbody[@id='deptTableBody']/tr")
	private List<WebElement> departmentRows;

	@FindBy(xpath = "//tbody[@id='deptTableBody']/tr/td[2]")
	private List<WebElement> departmentNames;

	@FindBy(xpath = "//tbody[@id='deptTableBody']/tr/td[3]")
	private List<WebElement> departmentHeads;

	@FindBy(xpath = "//tbody[@id='deptTableBody']/tr/td[5]")
	private List<WebElement> departmentStatuses;

	@FindBy(id = "saveDeptBtn")
	private WebElement saveButton;

	@FindBy(css = "button[data-testid='cancel-department-form']")
	private WebElement cancelEdit;

	// FILTERS

	@FindBy(id = "deptSearch")
	private WebElement departmentSearch;

	@FindBy(id = "deptClearFilters")
	private WebElement clearFilters;

	// ADD DEPARTMENT

	@FindBy(id = "addDeptBtn")
	private WebElement addDepartmentButton;

	@FindBy(id = "d_id")
	private WebElement departmentId;

	@FindBy(id = "d_name")
	private WebElement departmentName;

	@FindBy(id = "d_head")
	private WebElement departmentHead;

	@FindBy(id = "d_desc")
	private WebElement departmentDescription;

	@FindBy(id = "d_status")
	private WebElement departmentStatus;

	@FindBy(id = "saveDeptBtn")
	private WebElement saveDepartmentButton;

	@FindBy(id = "nextPage")
	private WebElement nextPageButton;

	// DELETE CONFIRMATION

	@FindBy(id = "confirmButton")
	private WebElement confirmDeleteTitle;

	@FindBy(id = "confirmButton")
	private WebElement confirmDeleteButton;

	// TOAST

	@FindBy(id = "toast-container")
	private WebElement toastMessage;

	// NAVIGATION

	@FindBy(css = "button[data-testid='nav-attendance']")
	private WebElement attendanceNavigation;

	// METHODS

	public String getPageTitle() {

		return pageTitle.getText();
	}

	public int getDepartmentCount() {

		return departmentRows.size();
	}

	public void clearFilter() {
		departmentSearch.click();
	}

	public List<String> getDepartmentNames() {

		List<String> names = new ArrayList<>();

		for (WebElement element : departmentNames) {

			String name = element.getText().replaceAll("\\s+\\d+\\s+emp", "").trim();

			names.add(name);
		}

		return names;
	}

	public List<String> getDepartmentHeadNames() {

		List<String> heads = new ArrayList<>();

		for (WebElement element : departmentHeads) {

			heads.add(element.getText());
		}

		return heads;
	}

	public List<WebElement> getDepartmentStatuses() {

		return departmentStatuses;

	}

	public String getDepartmentHead(String departmentName) {

		WebElement row = getDepartmentRow(departmentName);

		return row.findElement(By.xpath("./td[3]")).getText();
	}

	public String getDepartmentStatus(String departmentName) {

		WebElement row = getDepartmentRow(departmentName);

		return row.findElement(By.xpath("./td[5]")).getText();
	}

	private WebElement getDepartmentRow(String departmentName) {

		return driver
				.findElement(By.xpath("//tbody[@id='deptTableBody']/tr[td[2][contains(normalize-space(.),'IT')]]"));
	}

	public WebElement searchDepartment(String departmentName) {

		departmentSearch.clear();

		departmentSearch.sendKeys(departmentName);
		return departmentRows.getFirst();
	}

	public void clearFilters() {

		clearFilters.click();
	}

	public boolean isDepartmentDisplayed(String departmentName) {

		for (WebElement row : departmentRows) {

			String name = row.findElement(By.xpath("./td[2]")).getText().replaceAll("\\s+\\d+\\s+emp", "").trim();

			if (name.equals(departmentName)) {

				return true;
			}
		}

		return false;
	}

	// EDIT

	public void clickEditDepartment(String departmentName) {

		WebElement row = getDepartmentRow(departmentName);

		row.findElement(By.cssSelector("button[data-testid*='edit-department']")).click();
	}

	// DELETE

	public void clickDeleteDepartment(String departmentName) {

		WebElement row = getDepartmentRow(departmentName);

		row.findElement(By.xpath(".//button[contains(text(),'Delete')]")).click();
	}

	public boolean isDeleteConfirmationDisplayed() {

		return confirmDeleteTitle.isDisplayed();
	}

	public void confirmDeleteDepartment() {

		confirmDeleteButton.click();
	}

	public String getToastMessage() {

		return toastMessage.getText();
	}

	public void gotoAttendancePage() {

		attendanceNavigation.click();
	}

	public void editDepartmentHead(String department, String newHead) {

		clickEditDepartment(department);
		departmentHead.clear();
		departmentHead.sendKeys(newHead);
		saveButton.click();
	}

	public void cancelDepartmentHeadEdit(String department, String editHead) {
		clickEditDepartment(department);
		departmentHead.clear();
		departmentHead.sendKeys(editHead);
		cancelEdit.click();
	}

	public String addNewDepartment(String newDepartmentName, String head, String description, String status) {
		addDepartmentButton.click();
		String addDepartmentId = driver.findElement(By.id("d_id")).getAttribute("value");
		departmentName.sendKeys(newDepartmentName);
		departmentHead.sendKeys(head);
		departmentDescription.sendKeys(description);
		Select selectSatus = new Select(departmentStatus);
		selectSatus.selectByVisibleText(status);
		saveButton.click();
		return addDepartmentId;
	}

	public int getUpdatedDepartmentCount() {
		while (true) {
			departmentNames.size();
			if (!nextPageButton.isEnabled()) {
				break;
			}
			nextPageButton.click();
		}
		return departmentNames.size();
	}

	public boolean searchAddedDepartment(String dep) {
		departmentSearch.sendKeys(dep);
		return departmentPanel.isDisplayed();
	}

	public void deleteDepartment(String depId) {
		By deleteDepartment = By.cssSelector("button[data-testid='delete-department-" + depId + "']");
		driver.findElement(deleteDepartment).click();
		Assert.assertTrue(confirmDeleteTitle.isDisplayed());
		confirmDeleteTitle.click();
		clearFilters();
	}

	public List<WebElement> getDepartmentRows() {
		return departmentNames;
	}
}
