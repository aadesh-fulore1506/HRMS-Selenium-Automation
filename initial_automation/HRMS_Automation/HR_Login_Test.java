package HRMS_Automation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HR_Login_Test {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");
        driver.manage().window().maximize();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String email =
                driver.findElement(
                        By.xpath("//div/b[text()='hr@peopleflow.com']"))
                        .getText();

        String password =
                driver.findElement(
                        By.xpath("//div[text()=' / Hr@123']"))
                        .getText()
                        .split("/")[1]
                        .trim();

        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.id("rememberMe")).click();
        driver.findElement(By.id("loginButton")).click();

        WebElement loginToast =
                driver.findElement(By.id("toast-container"));

        wait.until(ExpectedConditions.visibilityOf(loginToast));
        wait.until(ExpectedConditions.invisibilityOf(loginToast));


        // =========================================================
        // VERIFY HR DASHBOARD
        // =========================================================

        String dashboardTitle =
                driver.findElement(By.id("pageTitle")).getText();

        Assert.assertEquals(dashboardTitle, "Dashboard");


        // =========================================================
        // VERIFY HR USER NAME / ROLE
        // =========================================================

        WebElement profile =
                driver.findElement(By.xpath("//header//*[contains(text(),'Priya Sharma')]"));

        Assert.assertTrue(profile.isDisplayed());

        System.out.println("HR login successful");


        // =========================================================
        // DASHBOARD IMPORTANT VALIDATIONS
        // =========================================================

        int totalEmployees =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='card-total-employees']/div[@class='val']"))
                                .getText());

        int activeEmployees =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='card-active-employees']/div[@class='val']"))
                                .getText());

        int onLeave =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='card-on-leave']/div[@class='val']"))
                                .getText());

        int presentToday =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='card-present-today']/div[@class='val']"))
                                .getText());

        int absentToday =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='card-absent-today']/div[@class='val']"))
                                .getText());

        int pendingLeaves =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='card-pending-leaves']/div[@class='val']"))
                                .getText());


        Assert.assertTrue(totalEmployees > 0);
        Assert.assertTrue(activeEmployees <= totalEmployees);
        Assert.assertTrue(onLeave >= 0);
        Assert.assertTrue(presentToday >= 0);
        Assert.assertTrue(absentToday >= 0);
        Assert.assertTrue(pendingLeaves >= 0);


        // =========================================================
        // EMPLOYEE DISTRIBUTION
        // =========================================================

        List<WebElement> departmentNames =
                driver.findElements(
                        By.xpath("//div[@data-testid='employee-distribution']//div[@class='bl']"));

        List<WebElement> departmentCounts =
                driver.findElements(
                        By.xpath("//div[@data-testid='employee-distribution']//div[@class='bv']"));

        int distributionTotal = 0;

        for (int i = 0; i < departmentCounts.size(); i++) {

            String department = departmentNames.get(i).getText();

            int count =
                    Integer.parseInt(departmentCounts.get(i).getText());

            System.out.println(department + " : " + count);

            distributionTotal += count;
        }

        Assert.assertEquals(distributionTotal, totalEmployees);


        // =========================================================
        // ATTENDANCE OVERVIEW
        // =========================================================

        int present =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='attendance-overview']//div[text()='Present']/following-sibling::div[2]"))
                                .getText());

        int absent =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='attendance-overview']//div[text()='Absent']/following-sibling::div[2]"))
                                .getText());

        int late =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='attendance-overview']//div[text()='Late']/following-sibling::div[2]"))
                                .getText());

        int attendanceOnLeave =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//div[@data-testid='attendance-overview']//div[text()='On Leave']/following-sibling::div[2]"))
                                .getText());


        Assert.assertEquals(present + late, presentToday);
        Assert.assertEquals(absent, absentToday);
        Assert.assertEquals(attendanceOnLeave, onLeave);


        // =========================================================
        // HR EMPLOYEE PAGE ACCESS
        // =========================================================

        driver.findElement(
                By.cssSelector("button[data-testid='nav-employees']"))
                .click();

        String employeePageTitle =
                driver.findElement(By.id("pageTitle")).getText();

        Assert.assertEquals(employeePageTitle, "Employee Management");

        List<WebElement> employeeRows =
                driver.findElements(By.xpath("//tbody/tr"));

        Assert.assertTrue(employeeRows.size() > 0);

        System.out.println("HR can access Employee Management");


        // =========================================================
        // HR EMPLOYEE SEARCH
        // =========================================================

        String employeeId = "EMP1025";

        WebElement searchBox =
                driver.findElement(By.id("empSearch"));

        searchBox.sendKeys(employeeId);

        WebElement employeeRow =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//tbody//tr[td[1][normalize-space()='"
                                        + employeeId + "']]")));

        String displayedEmployeeId =
                employeeRow.findElement(By.xpath("./td[1]")).getText();

        Assert.assertEquals(displayedEmployeeId, employeeId);

        driver.findElement(By.id("clearFilters")).click();


        // =========================================================
        // HR DEPARTMENT ACCESS
        // =========================================================

        driver.findElement(
                By.cssSelector("button[data-testid='nav-departments']"))
                .click();

        String departmentPageTitle =
                driver.findElement(By.id("pageTitle")).getText();

        Assert.assertEquals(
                departmentPageTitle,
                "Department Management");

        List<WebElement> departments =
                driver.findElements(
                        By.xpath("//tbody[@id='deptTableBody']/tr"));

        Assert.assertTrue(departments.size() > 0);

        System.out.println("HR can access Departments");


        // =========================================================
        // HR ATTENDANCE ACCESS
        // =========================================================

        driver.findElement(
                By.cssSelector("button[data-testid='nav-attendance']"))
                .click();

        String attendancePageTitle =
                driver.findElement(By.id("pageTitle")).getText();

        Assert.assertEquals(
                attendancePageTitle,
                "Attendance Management");

        Assert.assertTrue(
                driver.findElements(By.xpath("//tbody/tr")).size() > 0);

        System.out.println("HR can access Attendance");


        // =========================================================
        // HR LEAVE MANAGEMENT
        // =========================================================

        driver.findElement(
                By.cssSelector("button[data-testid='nav-leave-management']"))
                .click();

        String leavePageTitle =
                driver.findElement(By.id("pageTitle")).getText();

        Assert.assertEquals(
                leavePageTitle,
                "Leave Management");


        Select leaveStatus =
                new Select(
                        driver.findElement(By.id("lvStatusFilter")));

        leaveStatus.selectByVisibleText("Pending");

        List<WebElement> leavesPending =
                driver.findElements(By.xpath("//tbody/tr"));

        System.out.println(
                "Pending leaves visible to HR: "
                        + leavesPending.size());


        // =========================================================
        // IMPORTANT HR FUNCTION:
        // APPROVE ONE PENDING LEAVE
        // =========================================================

        driver.findElement(By.id("lvClearFilters")).click();

        driver.findElement(By.id("lvSearch"))
                .sendKeys("Ishaan Iyer");

        WebElement leavePanel =
                driver.findElement(By.cssSelector(".panel"));

        Assert.assertTrue(leavePanel.isDisplayed());

        String statusBeforeApproval =
                driver.findElement(By.cssSelector(".badge-amber"))
                        .getText();

        int leaveDays =
                Integer.parseInt(
                        driver.findElement(
                                By.xpath("//tbody/tr/td[7]"))
                                .getText());

        if (leaveDays <= 3) {

            driver.findElement(
                    By.cssSelector(
                            "button[data-testid*='approve-leave']"))
                    .click();

            WebElement confirmation =
                    driver.findElement(By.id("confirmTitle"));

            wait.until(
                    ExpectedConditions.visibilityOf(confirmation));

            driver.findElement(By.id("confirmButton")).click();

            WebElement approvalToast =
                    driver.findElement(By.id("toast-container"));

            wait.until(
                    ExpectedConditions.visibilityOf(approvalToast));

            wait.until(
                    ExpectedConditions.invisibilityOf(approvalToast));

            String statusAfterApproval =
                    driver.findElement(By.cssSelector(".badge-green"))
                            .getText();

            Assert.assertEquals(
                    statusAfterApproval,
                    "Approved");

            Assert.assertNotEquals(
                    statusBeforeApproval,
                    statusAfterApproval);
        }


        // =========================================================
        // HR REPORT ACCESS
        // =========================================================

        driver.findElement(
                By.cssSelector("button[data-testid='nav-reports']"))
                .click();

        String reportPageTitle =
                driver.findElement(By.id("pageTitle")).getText();

        Assert.assertEquals(reportPageTitle, "Reports");

        driver.findElement(
                By.xpath("//button[contains(text(),'Employee Report')]"))
                .click();

        Assert.assertTrue(
                driver.findElements(
                        By.xpath("//table[@id='reportTable']//tbody/tr"))
                        .size() > 0);

        driver.findElement(
                By.xpath("//button[contains(text(),'Attendance Report')]"))
                .click();

        Assert.assertTrue(
                driver.findElements(
                        By.xpath("//table[@id='reportTable']//tbody/tr"))
                        .size() > 0);

        driver.findElement(
                By.xpath("//button[contains(text(),'Leave Report')]"))
                .click();

        Assert.assertTrue(
                driver.findElements(
                        By.xpath("//table[@id='reportTable']//tbody/tr"))
                        .size() > 0);


        // =========================================================
        // LOGOUT
        // =========================================================

        driver.findElement(By.id("logoutBtn")).click();

        WebElement confirmLogout =
                driver.findElement(By.id("confirmTitle"));

        wait.until(
                ExpectedConditions.visibilityOf(confirmLogout));

        driver.findElement(By.id("confirmButton")).click();

        System.out.println("HR logout successful");

        driver.quit();
    }
}