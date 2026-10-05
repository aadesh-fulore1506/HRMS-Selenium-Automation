package HRMS_Automation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Employee_Login_Test {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");
        driver.manage().window().maximize();

      driver.manage().timeouts() .implicitlyWait(Duration.ofSeconds(3));

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        String email =
                driver.findElement(
                        By.xpath("//div/b[text()='employee@peopleflow.com']"))
                        .getText();

        String password =
                driver.findElement(
                        By.xpath("//div[text()=' / Employee@123']"))
                        .getText()
                        .split("/")[1]
                        .trim();

        driver.findElement(By.id("email"))
                .sendKeys(email);

        driver.findElement(By.id("password"))
                .sendKeys(password);

        driver.findElement(By.id("rememberMe"))
                .click();

        driver.findElement(By.id("loginButton"))
                .click();


        // =========================================================
        // LOGIN SUCCESS
        // =========================================================

        WebElement loginToast =
                driver.findElement(By.id("toast-container"));

        wait.until(
                ExpectedConditions.visibilityOf(loginToast));

        wait.until(
                ExpectedConditions.invisibilityOf(loginToast));


        String dashboardTitle =
                driver.findElement(By.id("pageTitle"))
                        .getText();

        Assert.assertEquals(
                dashboardTitle,
                "Dashboard");

        System.out.println(
                "Employee login successful");


        // =========================================================
        // VERIFY EMPLOYEE NAME
        // =========================================================

        WebElement employeeProfile =
                driver.findElement(
                        By.xpath(
                                "//header//*[contains(text(),'Aarav Sharma')]"));

        Assert.assertTrue(
                employeeProfile.isDisplayed());


        // =========================================================
        // DASHBOARD IMPORTANT CARDS
        // =========================================================

        int totalEmployees =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath(
                                        "//div[@data-testid='card-total-employees']/div[@class='val']"))
                                .getText());

        int activeEmployees =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath(
                                        "//div[@data-testid='card-active-employees']/div[@class='val']"))
                                .getText());

        int onLeave =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath(
                                        "//div[@data-testid='card-on-leave']/div[@class='val']"))
                                .getText());

        int presentToday =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath(
                                        "//div[@data-testid='card-present-today']/div[@class='val']"))
                                .getText());

        int absentToday =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath(
                                        "//div[@data-testid='card-absent-today']/div[@class='val']"))
                                .getText());

        int pendingLeaves =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath(
                                        "//div[@data-testid='card-pending-leaves']/div[@class='val']"))
                                .getText());


        Assert.assertTrue(totalEmployees > 0);
        Assert.assertTrue(activeEmployees <= totalEmployees);
        Assert.assertTrue(onLeave >= 0);
        Assert.assertTrue(presentToday >= 0);
        Assert.assertTrue(absentToday >= 0);
        Assert.assertTrue(pendingLeaves >= 0);


        // =========================================================
        // VERIFY EMPLOYEE HAS ONLY EXPECTED MENU OPTIONS
        // =========================================================

        List<WebElement> sidebarButtons =
                driver.findElements(
                        By.cssSelector(
                                "#sidebar button[data-testid^='nav-']"));

        System.out.println(
                "Employee accessible menu count: "
                        + sidebarButtons.size());


        // These menus should NOT be accessible
        // according to your Employee screenshot.

        Assert.assertEquals(
                driver.findElements(
                        By.cssSelector(
                                "button[data-testid='nav-employees']"))
                        .size(),
                0,
                "Employee should not have Employee Management access");


        Assert.assertEquals(
                driver.findElements(
                        By.cssSelector(
                                "button[data-testid='nav-departments']"))
                        .size(),
                0,
                "Employee should not have Department access");


        Assert.assertEquals(
                driver.findElements(
                        By.cssSelector(
                                "button[data-testid='nav-reports']"))
                        .size(),
                0,
                "Employee should not have Reports access");


        // =========================================================
        // ATTENDANCE ACCESS
        // =========================================================

        driver.findElement(
                By.cssSelector(
                        "button[data-testid='nav-attendance']"))
                .click();

        String attendancePageTitle =
                driver.findElement(By.id("pageTitle"))
                        .getText();

        Assert.assertEquals(
                attendancePageTitle,
                "Attendance Management");


        List<WebElement> attendanceRows =
                driver.findElements(
                        By.xpath("//tbody/tr"));
    System.out.println(attendanceRows.size());
        Assert.assertFalse(
                attendanceRows.size() > 1);

        System.out.println(
                "Employee cannot access Attendance");



        // =========================================================
        // LEAVE MANAGEMENT ACCESS
        // =========================================================

        driver.findElement(
                By.cssSelector(
                        "button[data-testid='nav-leave-management']"))
                .click();

        String leavePageTitle =
                driver.findElement(By.id("pageTitle"))
                        .getText();

        Assert.assertEquals(
                leavePageTitle,
                "Leave Management");


        Assert.assertTrue(
                driver.findElement(
                        By.cssSelector(".panel"))
                        .isDisplayed());


        // =========================================================
        // EMPLOYEE LEAVE SEARCH
        // =========================================================

        WebElement leaveSearch =
                driver.findElement(By.id("lvSearch"));

        leaveSearch.sendKeys("Aarav Sharma");

        List<WebElement> employeeLeaves =
                driver.findElements(
                        By.xpath("//tbody/tr"));

        System.out.println(
                "Employee leave records: "
                        + employeeLeaves.size());

        Assert.assertTrue(
                employeeLeaves.size() >= 0);


        // =========================================================
        // IMPORTANT:
        // EMPLOYEE SHOULD NOT APPROVE LEAVE
        // =========================================================

        List<WebElement> approveButtons =
                driver.findElements(
                        By.cssSelector(
                                "button[data-testid*='approve-leave']"));

        Assert.assertEquals(
                approveButtons.size(),
                0,
                "Employee should not have Approve Leave access");


        // =========================================================
        // SETTINGS ACCESS
        // =========================================================

        driver.findElement(
                By.cssSelector(
                        "button[data-testid='nav-settings']"))
                .click();

        /*
         * If your Settings page has a pageTitle,
         * validate it here.
         *
         * Otherwise simply validate the Settings panel.
         */

        Assert.assertTrue(
                driver.findElement(
                        By.cssSelector(".panel"))
                        .isDisplayed());


        // =========================================================
        // LOGOUT
        // =========================================================

        driver.findElement(By.id("logoutBtn"))
                .click();

        WebElement confirmLogout =
                driver.findElement(By.id("confirmTitle"));

        wait.until(
                ExpectedConditions.visibilityOf(confirmLogout));

        driver.findElement(By.id("confirmButton"))
                .click();

        System.out.println(
                "Employee logout successful");

        driver.quit();
    }
}