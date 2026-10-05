package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

	public static String capture(WebDriver driver, String testName) {
		try {
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
			String fileName = testName + "_" + timestamp + ".png";
			String destPath = "test-output/screenshots/" + fileName;

			Files.createDirectories(Paths.get("test-output/screenshots"));

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File dest = new File(destPath);
			Files.copy(src.toPath(), dest.toPath());

			return destPath;
		} catch (IOException e) {
			System.err.println("Failed to capture screenshot: " + e.getMessage());
			return null;
		}
	}
}
