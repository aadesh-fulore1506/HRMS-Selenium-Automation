package pages;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.WaitUtils;

public class LoginPage {
	WaitUtils wait;
	private WebDriver driver;

	public LoginPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WaitUtils(driver);
	}

	// COMMON LOGIN ELEMENTS
	@FindBy(id = "email")
	private WebElement emailField;

	@FindBy(id = "password")
	private WebElement passwordField;

	@FindBy(id = "rememberMe")
	private WebElement rememberMe;

	@FindBy(id = "loginButton")
	private WebElement loginButton;

	@FindBy(id = "toast-container")
	private WebElement loginToast;

	// SYSTEM ADMINISTRATOR CREDENTIALS

	@FindBy(xpath = "//div/b[text()='admin@peopleflow.com']")
	private WebElement adminEmail;

	@FindBy(xpath = "//div[text()=' / Admin@123']")
	private WebElement adminPassword;

	// HR CREDENTIALS

	@FindBy(xpath = "//div/b[text()='hr@peopleflow.com']")
	private WebElement hrEmail;

	@FindBy(xpath = "//div[text()=' / Hr@123']")
	private WebElement hrPassword;

	// EMPLOYEE CREDENTIALS

	@FindBy(xpath = "//div/b[text()='employee@peopleflow.com']")
	private WebElement employeeEmail;

	@FindBy(xpath = "//div[text()=' / Employee@123']")
	private WebElement employeePassword;

	@FindBy(id = "pageTitle")
	private WebElement pageTitle;
	// COMMON LOGIN

	private void login(String email, String password) {

		emailField.clear();
		emailField.sendKeys(email);

		passwordField.clear();
		passwordField.sendKeys(password);

		if (!rememberMe.isSelected()) {
			rememberMe.click();
		}

		loginButton.click();
	}

	// ADMIN LOGIN

	public void loginAsAdmin() {

		String email = adminEmail.getText();

		String password = adminPassword.getText().split("/")[1].trim();

		login(email, password);
		wait.waitForLoginToastMessage();
	}

	// HR LOGIN

	public void loginAsHR() {

		String email = hrEmail.getText();

		String password = hrPassword.getText().split("/")[1].trim();

		login(email, password);
		wait.waitForLoginToastMessage();
	}

	// EMPLOYEE LOGIN
	public void loginAsEmployee() {

		String email = employeeEmail.getText();

		String password = employeePassword.getText().split("/")[1].trim();

		login(email, password);
		wait.waitForLoginToastMessage();
	}

	public boolean isLoginSuccessful() {

		wait.waitForVisible(pageTitle);

		return pageTitle.isDisplayed();
	}

}