package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

	private static final Logger log = LogManager.getLogger(LoginPage.class);

	@Test
	public void verifyAdminLogin() {

		LoginPage loginPage = new LoginPage(getDriver());

		loginPage.loginAsAdmin();
		log.info("Successfully Login As Admin.");
		System.out.println("URL after login: " + getDriver().getCurrentUrl());
		System.out.println("Title: " + getDriver().getTitle());

		Assert.assertTrue(loginPage.isLoginSuccessful(), "Admin login was not successful");
	}

	@Test
	public void verifyHRLogin() {

		LoginPage loginPage = new LoginPage(getDriver());

		loginPage.loginAsHR();
		log.info("Successfully Login As HR.");

		Assert.assertTrue(loginPage.isLoginSuccessful(), "HR login was not successful");
	}

	@Test
	public void verifyEmployeeLogin() {

		LoginPage loginPage = new LoginPage(getDriver());

		loginPage.loginAsEmployee();
		log.info("Successfully Login As Employee.");

		Assert.assertTrue(loginPage.isLoginSuccessful(), "Employee login was not successful");
	}

}