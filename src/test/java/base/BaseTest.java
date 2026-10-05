
package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import server.StartServer;
import utils.ConfigReader;

public class BaseTest {

	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return driver.get();
	}

	@BeforeSuite(alwaysRun = true)
	public void startServer() throws Exception {
		StartServer server = new StartServer();
		server.startServer();
	}

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		String browser = ConfigReader.get("browser");
		boolean headless = ConfigReader.getBoolean("headless");

		WebDriver localDriver;

		switch (browser.toLowerCase()) {
		case "firefox":
			FirefoxOptions ffOptions = new FirefoxOptions();
			if (headless) {
				ffOptions.addArguments("-headless");
			}
			localDriver = new FirefoxDriver(ffOptions);
			break;

		case "chrome":
		default:
			ChromeOptions chromeOptions = new ChromeOptions();
			if (headless) {
				chromeOptions.addArguments("--headless=new");
			}
			chromeOptions.addArguments("--remote-allow-origins=*");
			chromeOptions.addArguments("--window-size=1920,1080");
			localDriver = new ChromeDriver(chromeOptions);
			break;
		}

		if (!headless) {
			localDriver.manage().window().maximize();
		}
		localDriver.manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait.seconds")));
		localDriver.manage().timeouts()
				.pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout.seconds")));

		driver.set(localDriver);
		getDriver().get(ConfigReader.get("base.url"));
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (getDriver() != null) {
			getDriver().quit();
			driver.remove();
		}
	}
}
