package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import pages.LoginPage;
import utils.WaitUtils;

public class HRMSBaseTest extends BaseTest {

	protected WaitUtils wait;

	private static final Logger log = LogManager.getLogger(HRMSBaseTest.class);

	@BeforeMethod(alwaysRun = true)
	public void login() {

		log.info("Starting HRMS login setup");

		wait = new WaitUtils(getDriver());

		log.info("WaitUtils initialized");

		new LoginPage(getDriver()).loginAsAdmin();

		log.info("Admin login successful");
	}
}