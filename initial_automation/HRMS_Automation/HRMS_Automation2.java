package HRMS_Automation;

import java.time.Duration;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import pages.AttendancePage;
import pages.DashboardPage;
import pages.DepartmentPage;
import pages.EmployeePage;
import pages.LeavePage;
import pages.LoginPage;
import pages.ReportPage;
import pages.UserRolePage;
import utils.WaitUtils;

public class HRMS_Automation2 {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WaitUtils wait = new WaitUtils(driver);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		wait.waitForLoginToastMessage();

	
		// Dashboard page
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickSidebarToggle();

		int totalEmployees = dashboardPage.getTotalEmployees();
		int activeEmployee = dashboardPage.getActiveEmployees();

		int onLeave = dashboardPage.getOnLeave();

		int presentToday = dashboardPage.getPresentToday();

		int absentToday = dashboardPage.getAbsentToday();

		int pendingLeaveRequest = dashboardPage.getPendingLeaves();

		// Employee Distribution
		int ITCount = dashboardPage.getITCount();
		Assert.assertEquals(dashboardPage.getITCount(), 5);
		Assert.assertEquals(dashboardPage.getHumanResourcesCount(), 5);
		Assert.assertEquals(dashboardPage.getFinanceCount(), 5);
		Assert.assertEquals(dashboardPage.getSalesCount(), 5);
		Assert.assertEquals(dashboardPage.getMarketingCount(), 4);
		Assert.assertEquals(dashboardPage.getOperationsCount(), 4);

		List<String> departmentNames = dashboardPage.getDepartmentNames();
		System.out.println(departmentNames);
		dashboardPage.printDepartmentDistribution();
		Assert.assertEquals(dashboardPage.getDepartmentEmployeeTotal(), totalEmployees);

		// Attendance Overview (Today

		int present = dashboardPage.getAttendancePresent();

		int absent = dashboardPage.getAttendanceAbsent();

		int late = dashboardPage.getAttendanceLate();

		int onLeaveCount = dashboardPage.getAttendanceOnLeave();

		int presentCount = dashboardPage.getTotalPresentCount();
		;

		Assert.assertEquals(presentCount, presentToday);
		Assert.assertEquals(absent, absentToday);

		Assert.assertEquals(onLeaveCount, onLeave);
		boolean recentEmployeesSection = dashboardPage.isRecentEmployeesDisplayed();
		Assert.assertTrue(recentEmployeesSection);

		boolean recentLeaveRequest = dashboardPage.isRecentLeaveRequestsDisplayed();
		Assert.assertTrue(recentLeaveRequest);

		dashboardPage.gotoEmployeePage();

	
		
		// Employee ManagementPage
		EmployeePage employeePage = new EmployeePage(driver);

		String EmployeePageTitle = employeePage.getPageTitle();
		Assert.assertEquals(EmployeePageTitle, "Employee Management");

		int[] employeeCounts = employeePage.getEmployeeCountsAcrossPages();

		int totalEmployeesCount = employeeCounts[0];
		int totalActiveEmployeesCount = employeeCounts[1];
		int totalInactiveEmployeesCount = employeeCounts[2];

		System.out.println("Total Employees: " + totalEmployeesCount);
		System.out.println("Total Active Employees: " + totalActiveEmployeesCount);
		System.out.println("Total Inavtive Employees: " + totalInactiveEmployeesCount);

		Assert.assertEquals(totalActiveEmployeesCount, activeEmployee);
		Assert.assertEquals(totalEmployeesCount, totalEmployees);
		employeePage.clearFilters();
		employeePage.getInactiveEmployeeIdsAcrossPages();
		employeePage.clearFilters();

		// department filter
		int actualITCount = employeePage.getEmployeeCountByDepartment("IT");

		Assert.assertEquals(actualITCount, ITCount);
		employeePage.clearFilters();

		String searchId = "EMP1025";

		String displayId = employeePage.searchEmployeeById(searchId);
		Assert.assertEquals(displayId, searchId);
		employeePage.clearFilters();

		employeePage.clickAddEmployee();
		employeePage.addEmployee("EMP10" + (totalEmployees + 1), "Aadesh", "Fulore", "9372963242", "01-01-2006", "Male",
				"IT", "Software Engineer", "01-12-2006", "Full Time", "60000", "Kalyan,Maharastra", "Mumbai",
				"Maharastra", "421301", "9354236545");

		wait.waitForEmployeeCreatedMessage();

		String newEmployeeId = "EMP10" + (totalEmployees + 1);
		String displayedNewEmployeeId = employeePage.searchNewEmployee(newEmployeeId);

		// Validate
		Assert.assertEquals(displayedNewEmployeeId, newEmployeeId);
		employeePage.clearFilters();

		employeePage.gotoDepartmentPage();

		// Department Page

		DepartmentPage departmentPage = new DepartmentPage(driver);
		String departmentPageTitle = departmentPage.getPageTitle();
		Assert.assertEquals(departmentPageTitle, "Department Management");

		List<String> presentDepartments = departmentPage.getDepartmentNames();

		System.out.println(presentDepartments);
		Assert.assertEquals(presentDepartments, departmentNames);

		List<String> departmentHeads = departmentPage.getDepartmentHeadNames();
		System.out.println(departmentHeads);

		List<WebElement> departmentStatuses = departmentPage.getDepartmentStatuses();
		for (WebElement departmentStatus : departmentStatuses) {
			Assert.assertEquals(departmentStatus.getText(), "Active");
		}
		String departmentHead = departmentPage.getDepartmentHead("IT");

		System.out.println(departmentHead);
		String departmentStatus = departmentPage.getDepartmentStatus("IT");
		System.out.println(departmentStatus);

		String beforeEditDepartmentHead = departmentPage.getDepartmentHead("IT");
		;
		departmentPage.editDepartmentHead("IT", "Manal Mhatre");

		wait.waitForDepartmentEditSuccessMessage();

		String afterEditDepartmentHead = departmentPage.getDepartmentHead("IT");
		;
		System.out.println(afterEditDepartmentHead);
		Assert.assertNotEquals(beforeEditDepartmentHead, afterEditDepartmentHead);
		departmentPage.cancelDepartmentHeadEdit("IT", "Shubham Sutar");

		String afterEditCancelDepartmentHead = departmentPage.getDepartmentHead("IT");

		Assert.assertEquals(afterEditCancelDepartmentHead, afterEditDepartmentHead);

		// Search Department
		String searchDepartment = "Human Resources";
		WebElement isDepartmentDisplayed = departmentPage.searchDepartment(searchDepartment);
		Assert.assertTrue(isDepartmentDisplayed.isDisplayed());
		departmentPage.clearFilter();

		// Add new Department
		String addedDepartmentID = departmentPage.addNewDepartment("Product Management", "Shubham Sutar",
				"Handles product strategy, development, planning, and lifecycle management.", "Active");
		System.out.println(addedDepartmentID);
		wait.waitForDepartmentAddSSuccessMessage();

		Assert.assertNotEquals(departmentPage.getUpdatedDepartmentCount(), presentDepartments.size());

		String searchNewDepartment = "Product Management";
		boolean isAddedDipartmentDisplyed = departmentPage.searchAddedDepartment(searchNewDepartment);
		Assert.assertTrue(isAddedDipartmentDisplyed);
		departmentPage.clearFilter();

		departmentPage.deleteDepartment(addedDepartmentID);
		wait.waitForDepartmentDeleteMessage();

		List<WebElement> departmentsAfterNewDepartmentDeleted = departmentPage.getDepartmentRows();
		Assert.assertEquals(departmentsAfterNewDepartmentDeleted.size(), presentDepartments.size());

		departmentPage.gotoAttendancePage();

		// Attendance Page

		AttendancePage attendancePage = new AttendancePage(driver);

		String attendancePageTitle = attendancePage.getPageTitle();
		Assert.assertEquals(attendancePageTitle, "Attendance Management");

		String employeeId = "EMP1002";
		String employeeDepartment = "Human Resources";

		attendancePage.getEmployeeDetails(employeeId, employeeDepartment);

		// mark attendance

		String idToSearch = "EMP1026";

		String markedAttendanceDate = attendancePage.markEmployeeAttendance("EMP1026", "9000", "1800", "Present"); // toast-message.success
		wait.waitForAttendanceMarkedSuccessMessage();

		boolean isAttendanceMarked = attendancePage.verifyAttendanceMarked(idToSearch, markedAttendanceDate);
		Assert.assertTrue(isAttendanceMarked);
		attendancePage.clearFilters();
		attendancePage.gotoLeavePage();

		// Leave Management

		LeavePage leavePage = new LeavePage(driver);
		String leaveManagementPageTitle = leavePage.getPageTitle();
		Assert.assertEquals(leaveManagementPageTitle, "Leave Management");

		Assert.assertTrue(leavePage.isLeaveManagementPanelDisplayed());
		leavePage.printDetailsByLeaveType("Pending");
		leavePage.clearFilters();

		String employeeName = "Ishaan Iyer";

		leavePage.approveLeaveIfDaysWithinLimit(employeeName, 3);
		wait.waitForApproveLeaveRequest();

		leavePage.gotoUersRolePage();

		// User and Role
		UserRolePage userPage = new UserRolePage(driver);
		String userPageTitle = userPage.getPageTitle();
		Assert.assertEquals(userPageTitle, "Users & Roles");

		boolean verifyUsers[] = userPage.verifyDefaultUsers();
		Assert.assertTrue(verifyUsers[0], "Admin user not found");
		Assert.assertTrue(verifyUsers[1], "HR user not found");
		Assert.assertTrue(verifyUsers[2], "Employee user not found");

		// Add new user
		String userName = "Rahul Patil";
		String email = "RahulPatil@peopleflow.com";
		String role = "HR";
		String status = "Active";
		userPage.addNewUser(userName, email, role, status);

		wait.waitForUserAddedSuccessMessage();

		boolean isUserAdded = userPage.isNewUserAdded(userName, email, role, status);

		Assert.assertTrue(isUserAdded, "New user was not added with expected details");

		userPage.deleteUser(userName, role);

		wait.waitForUserDeletedSuccessMessage();

		boolean deletedUserFound = userPage.isUserPresent(userName, role);

		Assert.assertFalse(deletedUserFound);

		System.out.println("User deleted successfully");

		userPage.gotoReportPage();

		// ReportPage

		ReportPage reportPage = new ReportPage(driver);
		String reportPageTitle = reportPage.getPageTitle();
		Assert.assertEquals(reportPageTitle, "Reports");

		// Employee report
		reportPage.clickEmployeeReport();

		Assert.assertTrue(reportPage.getReportRows().size() > 0, "Employee report is  empty");

		System.out.println("Employee report records: " + reportPage.getReportRecordCount());

		// Attendance Report
		reportPage.clickAttendanceReport();

		Assert.assertTrue(reportPage.getReportRows().size() > 0, "Attendance report is empty");

		System.out.println("Attendance report records: " + reportPage.getReportRecordCount());

		// Leave Report

		reportPage.clickLeaveReport();

		Assert.assertTrue(reportPage.getReportRows().size() > 0, "Leave report is empty");

		System.out.println("Leave report records: " + reportPage.getReportRecordCount());

		reportPage.clickLogout();
		Assert.assertTrue(reportPage.isLogoutConfirmationDisplayed());
		reportPage.confirmLogout();

		System.out.println("ALL TESTS PASSED SUCCESSFULLY");
		driver.quit();

	}
}
