package pages;

import java.util.ArrayList;
import java.util.List;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import utils.WaitUtils;

public class DashboardPage {

    private WebDriver driver;
     WaitUtils wait ;
    public DashboardPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WaitUtils(driver);
        
    }


    // PAGE TITLE

    @FindBy(id = "pageTitle")
    private WebElement pageTitle;

  // SIDE TOGGLE 
    @FindBy(id = "sidebarToggle")
    private WebElement sidebarToggle;
    
    // DASHBOARD CARDS

    @FindBy(xpath = "//div[@data-testid='card-total-employees']/div[@class='val']")
    private WebElement totalEmployees;

    @FindBy(xpath = "//div[@data-testid='card-active-employees']/div[@class='val']")
    private WebElement activeEmployees;

    @FindBy(xpath = "//div[@data-testid='card-on-leave']/div[@class='val']")
    private WebElement onLeave;

    @FindBy(xpath = "//div[@data-testid='card-present-today']/div[@class='val']")
    private WebElement presentToday;

    @FindBy(xpath = "//div[@data-testid='card-absent-today']/div[@class='val']")
    private WebElement absentToday;

    @FindBy(xpath = "//div[@data-testid='card-pending-leaves']/div[@class='val']")
    private WebElement pendingLeaves;


    // DEPARTMENT DISTRIBUTION

    @FindBy(xpath = "//div[@data-testid='employee-distribution']//div[@class='bl']")
    private List<WebElement> departments;

    @FindBy(xpath = "//div[@data-testid='employee-distribution']//div[@class='bv']")
    private List<WebElement> departmentEmployeeCounts;


    // INDIVIDUAL DEPARTMENT COUNTS

    @FindBy(xpath = "//div[@data-testid='employee-distribution']/div[1]/div[3]")
    private WebElement itCount;

    @FindBy(xpath = "//div[@data-testid='employee-distribution']/div[2]/div[3]")
    private WebElement humanResourcesCount;

    @FindBy(xpath = "//div[@data-testid='employee-distribution']/div[3]/div[3]")
    private WebElement financeCount;

    @FindBy(xpath = "//div[@data-testid='employee-distribution']/div[4]/div[3]")
    private WebElement salesCount;

    @FindBy(xpath = "//div[@data-testid='employee-distribution']/div[5]/div[3]")
    private WebElement marketingCount;

    @FindBy(xpath = "//div[@data-testid='employee-distribution']/div[6]/div[3]")
    private WebElement operationsCount;


    // ATTENDANCE OVERVIEW

    @FindBy(xpath = "//div[@data-testid='attendance-overview']//div[text()='Present']/following-sibling::div[2]")
    private WebElement present;

    @FindBy(xpath = "//div[@data-testid='attendance-overview']//div[text()='Absent']/following-sibling::div[2]")
    private WebElement absent;

    @FindBy(xpath = "//div[@data-testid='attendance-overview']//div[text()='Late']/following-sibling::div[2]")
    private WebElement late;

    @FindBy(xpath = "//div[@data-testid='attendance-overview']//div[text()='On Leave']/following-sibling::div[2]")
    private WebElement attendanceOnLeave;


    // RECENT SECTIONS

    @FindBy(xpath = "//h3[text()='Recent Employees']/ancestor::div[2]")
    private WebElement recentEmployeesSection;

    @FindBy(xpath = "//h3[text()='Recent Leave Requests']/ancestor::div[2]")
    private WebElement recentLeaveRequests;


    // NAVIGATION

    @FindBy(css = "button[data-testid='nav-employees']")
    private WebElement employeesNavigation;

    @FindBy(css = "button[data-testid='nav-departments']")
    private WebElement departmentsNavigation;

    @FindBy(css = "button[data-testid='nav-attendance']")
    private WebElement attendanceNavigation;

    @FindBy(css = "button[data-testid='nav-leave-management']")
    private WebElement leaveNavigation;

    @FindBy(css = "button[data-testid='nav-reports']")
    private WebElement reportsNavigation;

    @FindBy(css = "button[data-testid='nav-users']")
    private WebElement usersNavigation;


    // PAGE TITLE

    public String getPageTitle() {

        return pageTitle.getText();
    }


    // DASHBOARD CARDS

    public void clickSidebarToggle() {
        sidebarToggle.click();
    }
    
    public int getTotalEmployees() {

        return Integer.parseInt(totalEmployees.getText());
    }


    public int getActiveEmployees() {

        return Integer.parseInt(activeEmployees.getText());
    }


    public int getOnLeave() {

        return Integer.parseInt(onLeave.getText());
    }


    public int getPresentToday() {

        return Integer.parseInt(presentToday.getText());
    }


    public int getAbsentToday() {

        return Integer.parseInt(absentToday.getText());
    }


    public int getPendingLeaves() {

        return Integer.parseInt(pendingLeaves.getText());
    }


    // DEPARTMENT COUNTS

    public int getITCount() {

        return Integer.parseInt(itCount.getText());
    }


    public int getHumanResourcesCount() {

        return Integer.parseInt(humanResourcesCount.getText());
    }


    public int getFinanceCount() {

        return Integer.parseInt(financeCount.getText());
    }


    public int getSalesCount() {

        return Integer.parseInt(salesCount.getText());
    }


    public int getMarketingCount() {

        return Integer.parseInt(marketingCount.getText());
    }


    public int getOperationsCount() {

        return Integer.parseInt(operationsCount.getText());
    }


     
    // DEPARTMENT DISTRIBUTION
     

    public List<String> getDepartmentNames() {

        List<String> names = new ArrayList<>();

        for (WebElement department : departments) {

            names.add(department.getText());
        }

        return names;
    }


    public List<Integer> getDepartmentEmployeeCounts() {

        List<Integer> counts = new ArrayList<>();

        for (WebElement count : departmentEmployeeCounts) {

            counts.add(Integer.parseInt(count.getText()));
        }

        return counts;
    }


    public int getDepartmentEmployeeTotal() {

        int total = 0;

        for (WebElement count : departmentEmployeeCounts) {

            total += Integer.parseInt(count.getText());
        }

        return total;
    }


    public void printDepartmentDistribution() {

        System.out.println(" Department Distribution ");

        for (int i = 0; i < departments.size(); i++) {

            String department =
                    departments.get(i).getText();

            String count =
                    departmentEmployeeCounts.get(i).getText();

            System.out.println(
                    department + " : " + count
            );
        }
    }


    // ATTENDANCE

    public int getAttendancePresent() {

        return Integer.parseInt(present.getText());
    }


    public int getAttendanceAbsent() {

        return Integer.parseInt(absent.getText());
    }


    public int getAttendanceLate() {

        return Integer.parseInt(late.getText());
    }


    public int getAttendanceOnLeave() {

        return Integer.parseInt(attendanceOnLeave.getText());
    }


    public int getTotalPresentCount() {

        return getAttendancePresent()
                + getAttendanceLate();
    }


    // RECENT SECTIONS

    public boolean isRecentEmployeesDisplayed() {

        return recentEmployeesSection.isDisplayed();
    }


    public boolean isRecentLeaveRequestsDisplayed() {

        return recentLeaveRequests.isDisplayed();
    }

    public void gotoEmployeePage() {
        employeesNavigation.click();
      
    }

    public void gotoDepartmentPage() {
        departmentsNavigation.click();
    }

    public void gotoAttendancePage() {
        attendanceNavigation.click();
    }

    public void gotoLeavePage() {
        leaveNavigation.click();
    }

    public void gotoReportPage() {
        reportsNavigation.click();
    }

    public void gotoUserRolePage() {
        usersNavigation.click();
    }

}