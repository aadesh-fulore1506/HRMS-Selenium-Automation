package HRMS_Automation;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HRMS_Automation {

	public static void main(String[] args) {
	
		  WebDriver driver = new ChromeDriver();
		  driver.get("http://localhost:8080");
		  driver.manage().window().maximize();
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		  
		  String email = 
				  driver.findElement(By.xpath("//div/b[text()='admin@peopleflow.com']")).getText();
		  
          String password =
        		  driver.findElement(By.xpath("//div[text()=' / Admin@123']")).getText().split("/")[1].trim();
          
		  driver.findElement(By.id("email")).sendKeys(email);
		  driver.findElement(By.id("password")).sendKeys(password);
		  
		  driver.findElement(By.id("rememberMe")).click();
		  driver.findElement(By.id("loginButton")).click();
		  
		  WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		  WebElement toastMessage = driver.findElement(By.id("toast-container"));
		  wait.until(ExpectedConditions.visibilityOf(toastMessage));
		  wait.until(ExpectedConditions.invisibilityOf(toastMessage));
		  
		  //Dashboard page 
		  driver.findElement(By.id("sidebarToggle")).click();
		  int totalEmployees = Integer.parseInt(driver.findElement
				  (By.xpath("//div[@data-testid='card-total-employees']/div[@class='val']")).getText());
		  
		  int activeEmployee =Integer.parseInt( driver.findElement
				  (By.xpath("//div[@data-testid='card-active-employees']/div[@class='val']")).getText());
	
		  int onLeave =Integer.parseInt( driver.findElement
				  (By.xpath("//div[@data-testid='card-on-leave']/div[@class='val']")).getText());
		  
		  int presentToday =Integer.parseInt( driver.findElement
				  (By.xpath("//div[@data-testid='card-present-today']/div[@class='val']")).getText());
		  
		  int absentToday =Integer.parseInt( driver.findElement
				  (By.xpath("//div[@data-testid='card-absent-today']/div[@class='val']")).getText());
		  
		  int pendingLeaveRequest =Integer.parseInt( driver.findElement
				  (By.xpath("//div[@data-testid='card-pending-leaves']/div[@class='val']")).getText());
	
	
		  //Employee Distribution 
		  int expectedITCount = Integer.parseInt(driver.findElement(By.xpath("//div[@data-testid='employee-distribution']/div[1]/div[3]")).getText());
		  int expectedHumanResourcesCount=Integer.parseInt(driver.findElement(By.xpath("//div[@data-testid='employee-distribution']/div[2]/div[3]")).getText());
		  int expectedFinanceCount=Integer.parseInt(driver.findElement(By.xpath("//div[@data-testid='employee-distribution']/div[3]/div[3]")).getText());
		  int expectedSalesCount = Integer.parseInt(driver.findElement(By.xpath("//div[@data-testid='employee-distribution']/div[4]/div[3]")).getText());
		  int expectedMarketingCount=Integer.parseInt(driver.findElement(By.xpath("//div[@data-testid='employee-distribution']/div[5]/div[3]")).getText());
		  int expectedOperationsCount=Integer.parseInt(driver.findElement(By.xpath("//div[@data-testid='employee-distribution']/div[6]/div[3]")).getText());
		  
//		  public int getExpectedDepartmentCount(String department) {
//
//			    String xpath = "//div[@data-testid='employee-distribution']//div[.//text()[normalize-space()='" 
//			                 + department + "']]//div[3]";
//
//			    return Integer.parseInt(
//			            driver.findElement(By.xpath(xpath)).getText()
//			    );
//			}
//		  int expectedITCount = getExpectedDepartmentCount("IT");
//		  int expectedHumanResourcesCount = getExpectedDepartmentCount("Human Resources");
//		  int expectedFinanceCount = getExpectedDepartmentCount("Finance");
//		  int expectedSalesCount = getExpectedDepartmentCount("Sales");
//		  int expectedMarketingCount = getExpectedDepartmentCount("Marketing");
//		  int expectedOperationsCount = getExpectedDepartmentCount("Operations");
		  
		  List<WebElement> departments = driver.findElements
				  (By.xpath("//div[@data-testid='employee-distribution']//div[@class='bl']"));
		  ArrayList<String> departmentNames = new ArrayList<>();
		  for (WebElement department : departments ) {
			  departmentNames.add(department.getText());
			   }
		  System.out.println(departmentNames);
		  
		  List<WebElement> depEmployee =driver.findElements
				  (By.xpath("//div[@data-testid='employee-distribution']//div[@class='bv']"));
		  
		  int total = 0 ;
		  for(int i=0;i<depEmployee.size();i++) {
			  String department = departments.get(i).getText();
			  int count = Integer.parseInt(depEmployee.get(i).getText());
			  System.out.println(department +": "+count);
			   total += count ;
		  }
		  
		  Assert.assertEquals(total, totalEmployees);	
		  
		  
		 //Attendance Overview (Today

		  int present = Integer.parseInt(driver.findElement
				  (By.xpath("//div[@data-testid='attendance-overview']//div[text()='Present']/following-sibling::div[2]")).getText());
		  
		  int absent = Integer.parseInt(driver.findElement
				  (By.xpath("//div[@data-testid='attendance-overview']//div[text()='Absent']/following-sibling::div[2]")).getText());
		  
		  int late = Integer.parseInt(driver.findElement
				  (By.xpath("//div[@data-testid='attendance-overview']//div[text()='Late']/following-sibling::div[2]")).getText());
		  
		  int onLeaveCount = Integer.parseInt(driver.findElement
				  (By.xpath("//div[@data-testid='attendance-overview']//div[text()='On Leave']/following-sibling::div[2]")).getText());
		  
		  int presentCount = present + late ;
		  
		  Assert.assertEquals(presentCount,presentToday );
		  Assert.assertEquals(absent,absentToday );

		  Assert.assertEquals(onLeaveCount,onLeave);
		  
		  JavascriptExecutor js = (JavascriptExecutor) driver ;
		  js.executeScript("window.scrollBy(0,300)");
		 boolean recentEmployeesSection = driver.findElement
				  (By.xpath("//h3[text()='Recent Employees']/ancestor::div[2]")).isDisplayed();
		  Assert.assertTrue(recentEmployeesSection);
		  
		  js.executeScript("window.scrollBy(0,500)");
		  boolean recentLeaveRequest = driver.findElement
				  (By.xpath("//h3[text()='Recent Leave Requests']/ancestor::div[2]")).isDisplayed();
		  Assert.assertTrue(recentLeaveRequest);
		  
		  driver.findElement(By.cssSelector("button[data-testid='nav-employees']")).click();
		  
		  //Employee Management
          boolean title = driver.findElement(By.id("pageTitle")).isDisplayed();
          Assert.assertTrue(title);
   
          int employeeCount = 0;
          int activeCount = 0 ;
          int inactiveCount = 0;
          List<String> inactiveEmployeeId;
         while (true) {
        	 
             List<WebElement> activeList = driver.findElements(By.cssSelector(".badge.badge-green"));
             List<WebElement> inactiveList = driver.findElements(By.cssSelector(".badge.badge-red"));
             List<WebElement> employeeRows = driver.findElements(By.xpath("//tbody/tr"));
             
             employeeCount += employeeRows.size();
             activeCount += activeList.size();
             inactiveCount += inactiveList.size();
             
              WebElement nextButton =
                      driver.findElement(By.cssSelector("#nextPage"));
              
              if (!nextButton.isEnabled()) {
                  break;
             }

             nextButton.click();
        }

       System.out.println("Total Employees: " + employeeCount);
       System.out.println("Total Active Employees: " + activeCount);
       System.out.println("Total Inavtive Employees: " + inactiveCount);
       
       Assert.assertEquals(activeCount, activeEmployee);
       Assert.assertEquals(employeeCount, totalEmployees);
       driver.findElement(By.id("clearFilters")).click();
       while (true) {

    	    List<WebElement> inactiveList =
    	            driver.findElements(By.cssSelector(".badge.badge-red"));

    	    for (WebElement inactiveStatus : inactiveList) {

    	        WebElement row = inactiveStatus.findElement(By.xpath("ancestor::tr"));

    	        String employeeId = row.findElement(By.xpath("./td[1]")).getText();
    	        String status = row.findElement(By.xpath("./td[9]")).getText();

    	        System.out.println(
    	                "Employee Id: " + employeeId +
    	                " | Employee Status: " + status
    	        );
    	    }

    	    WebElement nextButton =
    	            driver.findElement(By.cssSelector("#nextPage"));

    	    if (!nextButton.isEnabled()) {
    	        break;
    	    }

    	    nextButton.click();

    	
    	}
    
       driver.findElement(By.id("clearFilters")).click();
	   
	   //department filter 
	   WebElement departmentDropdown = driver.findElement(By.id("empDeptFilter"));
	   Select departmentSelect = new Select(departmentDropdown);
	   departmentSelect.selectByVisibleText("IT");
	   List<WebElement> itEmployeeList = driver.findElements(By.xpath("//tbody/tr"));
	   int actualITCount = itEmployeeList.size();
	   Assert.assertEquals(actualITCount ,expectedITCount );
	   driver.findElement(By.id("clearFilters")).click();
	   
	   //Important method which we can use in POM
//	   public void verifyDepartmentCount(String department, int expectedCount) {
//
//		    Select departmentSelect = new Select(
//		            driver.findElement(By.id("empDeptFilter")));
//
//		    departmentSelect.selectByValue(department);
//
//		    List<WebElement> employeeList =
//		            driver.findElements(By.xpath("//tbody/tr"));
//
//		    int actualCount = employeeList.size();
//
//		    Assert.assertEquals(actualCount, expectedCount,
//		            department + " employee count mismatch");
//		}
//    
//	   verifyDepartmentCount("IT", expectedITCount);
//	   verifyDepartmentCount("HR", expectedHumanResourcesCount);
//	   verifyDepartmentCount("Finance", expectedFinanceCount);
//	   verifyDepartmentCount("Sales", expectedSalesCount);
//	   verifyDepartmentCount("Marketing", expectedMarketingCount);
//	   verifyDepartmentCount("Operations", expectedOperationsCount);
       
		String searchId = "EMP1025";

		// Enter employee ID in search box
		WebElement searchBox = driver.findElement(By.id("empSearch"));
		searchBox.clear();
		searchBox.sendKeys(searchId);

		// Wait until the row containing searched ID is visible
		WebElement employeeRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//tbody//tr[td[1][normalize-space()='" + searchId + "']]")));

		// Get employee ID from the first column
		String displayId = employeeRow.findElement(By.xpath("./td[1]")).getText();

		System.out.println("Expected ID : " + searchId);
		System.out.println("Displayed ID: " + displayId);

		// Validate
		Assert.assertEquals(displayId, searchId);
		   driver.findElement(By.id("clearFilters")).click();
		
       // When Writinf POM method create same for name and gmailId also.........
		   
		   driver.findElement(By.id("addEmployeeBtn")).click();
		   
		   driver.findElement(By.id("f_id")).sendKeys("EMP10"+(totalEmployees+1));
		  WebElement statusDropdown = driver.findElement(By.id("f_status"));
		  Select selectStatus = new Select(statusDropdown);
		  selectStatus.selectByVisibleText("Active");
		  
		  String firstName = "Aadesh";
		  String lastName = "Fulore" ;
		  String number = "9372963242" ;
		  String dob = "01-01-2006";
		  String designation = "Software Engineer";
		  String dateOfJoining = "01-12-2006";
		  String salary = "60000";
		  String address = "Kalyan,Maharastra";
		  String city = "Mumbai";
		  String state = "Maharastra";
		  String postalCode = "421301";
		  String emergencyContact = "9354236545";
		  driver.findElement(By.id("f_fn")).sendKeys(firstName);
		  driver.findElement(By.id("f_ln")).sendKeys(lastName);
		  driver.findElement(By.id("f_email")).sendKeys(firstName.toLowerCase()+"."+lastName.toLowerCase()+(totalEmployees+1)+"@peopleflow.com");
		  driver.findElement(By.id("f_phone")).sendKeys(number);
		  driver.findElement(By.id("f_dob")).sendKeys(dob);
		  WebElement genderDropdown = driver.findElement(By.id("f_gender"));
		  Select selectGender = new Select(genderDropdown);
		  selectGender.selectByVisibleText("Male");
		  WebElement depDropdown = driver.findElement(By.id("f_dept"));
		  Select selectDepartment = new Select(depDropdown);
		  selectDepartment.selectByVisibleText("IT");
		  driver.findElement(By.id("f_desig")).sendKeys(designation);
		  driver.findElement(By.id("f_join")).sendKeys(dateOfJoining);
		  WebElement EmploymentTypeDropdown = driver.findElement(By.id("f_empType"));
		  Select selectEmploymentType = new Select(EmploymentTypeDropdown);
		  selectEmploymentType.selectByVisibleText("Full Time");
		  driver.findElement(By.id("f_salary")).sendKeys(salary);
		  driver.findElement(By.id("f_addr")).sendKeys(address);
		  driver.findElement(By.id("f_city")).sendKeys(city);
		  driver.findElement(By.id("f_state")).sendKeys(state);
		  driver.findElement(By.id("f_postal")).sendKeys(postalCode);
		  driver.findElement(By.id("f_emerg")).sendKeys(emergencyContact);
		  driver.findElement(By.id("saveEmpBtn")).click();
		  
          
		 WebElement successMsg = driver.findElement(By.id("toast-container"));
		 wait.until(ExpectedConditions.visibilityOf(successMsg));
		 wait.until(ExpectedConditions.invisibilityOf(successMsg));

	        searchBox = driver.findElement(By.id("empSearch"));
			searchBox.clear();
			String newEmployeeId = "EMP10"+(totalEmployees+1);
			searchBox.sendKeys(newEmployeeId);

			// Wait until the row containing searched ID is visible
			WebElement newEmployeeRow = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//tbody//tr[td[1][normalize-space()='" + newEmployeeId + "']]")));

			// Get employee ID from the first column
			String displayNewEmployeeId = newEmployeeRow.findElement(By.xpath("./td[1]")).getText();

			System.out.println("Expected ID : " + newEmployeeId);
			System.out.println("Displayed ID: " + displayNewEmployeeId);

			// Validate
			Assert.assertEquals(displayNewEmployeeId, newEmployeeId);
			   driver.findElement(By.id("clearFilters")).click();
		  
		  
		   driver.findElement(By.cssSelector("button[data-testid='nav-departments']")).click();
  
	
		   
		   //Department Page   
	
		   String departmentPageTitle =  driver.findElement(By.id("pageTitle")).getText();
			 Assert.assertEquals(departmentPageTitle, "Department Management");
			 
					
					ArrayList<String> presentDepartmentNames = new ArrayList<>();
					List<WebElement> presentDepartments = driver.findElements(By.xpath("//tbody[@id='deptTableBody']/tr/td[2]"));
					for(WebElement department : presentDepartments) {
						presentDepartmentNames.add(department.getText().replaceAll("\\s+\\d+\\s+emp", "")
					            .trim());
					}
					System.out.println(presentDepartmentNames);
					Assert.assertEquals(presentDepartmentNames, departmentNames);
					//Put Assertion 
					
					ArrayList<String> departmentHeadNames = new ArrayList<>();
					List<WebElement> departmentHeads = driver.findElements(By.xpath("//tr/td[3]"));
					for(WebElement departmentHead : departmentHeads) {
						departmentHeadNames.add(departmentHead.getText());
					}
					System.out.println(departmentHeadNames);
					//Put Assertion 
					//Add methods same for status when working on POM 
					String beforeEditDepartmentHead  = driver.findElement(By.xpath("//td[text()='IT ']/following-sibling::td[1]")).getText();
				WebElement editButton =	driver.findElement(By.xpath("//td[text()='IT ']/following-sibling::td[4]/div/button"));
				editButton.click();
					WebElement editHeadName = driver.findElement(By.id("d_head"));
					editHeadName.clear();
					editHeadName.sendKeys("Manal Mhatre");
					driver.findElement(By.id("saveDeptBtn")).click();
					
					WebElement toastmsg = driver.findElement(By.id("toast-container"));
					wait.until(ExpectedConditions.visibilityOf(toastmsg));
					wait.until(ExpectedConditions.invisibilityOf(toastmsg));
				   String afterEditDepartmentHead = driver.findElement(By.xpath("//td[text()='IT ']/following-sibling::td[1]")).getText();
                   System.out.println(afterEditDepartmentHead);
				   Assert.assertNotEquals(afterEditDepartmentHead,beforeEditDepartmentHead);
				  
				   editButton =	driver.findElement(By.xpath("//td[text()='IT ']/following-sibling::td[4]/div/button"));
				   editButton.click();
				   editHeadName = driver.findElement(By.id("d_head"));
					editHeadName.clear();
					editHeadName.sendKeys("Raj Mhatre");
					driver.findElement(By.cssSelector("button[data-testid='cancel-department-form']")).click();
					  String afterEditCancelDepartmentHead = driver.findElement(By.xpath("//td[text()='IT ']/following-sibling::td[1]")).getText();

					   Assert.assertEquals(afterEditCancelDepartmentHead,afterEditDepartmentHead);

					//Search Department    
					   String searchDepartment = "Human Resources";
				   driver.findElement(By.id("deptSearch")).sendKeys(searchDepartment);
				   WebElement departmentPanel = driver.findElement(By.cssSelector(".panel"));
				   Assert.assertTrue(departmentPanel.isDisplayed());
				   
				 String displayedDepartmentAfterSearch =  driver.findElement(By.xpath("//tbody/tr/td[2]")).getText().replaceAll("\\s+\\d+\\s+emp", "")
		            .trim();
				 Assert.assertEquals(displayedDepartmentAfterSearch, searchDepartment);
				 driver.findElement(By.id("deptClearFilters")).click();
				 
				 // Add new Department 
				
				 
				 driver.findElement(By.id("addDeptBtn")).click();
				 String newDepartmentId = driver.findElement(By.id("d_id")).getAttribute("value");	 
				driver.findElement(By.id("d_name")).sendKeys("Product Management");
				 driver.findElement(By.id("d_head")).sendKeys("Shubham Sutar");
				 driver.findElement(By.id("d_desc")).sendKeys("Handles product strategy, development, planning, and lifecycle management.");
			     WebElement selectStatusDropdown = driver.findElement(By.id("d_status"));
			     Select selectSatus = new Select(selectStatusDropdown);
			     selectSatus.selectByVisibleText("Active");
			     driver.findElement(By.id("saveDeptBtn")).click();
			     WebElement successMessage = driver.findElement(By.id("toast-container"));
			     wait.until(ExpectedConditions.visibilityOf(successMessage));
			     wait.until(ExpectedConditions.invisibilityOf(successMessage));

			     
					List<WebElement> departmentsAfterNewDepartmentAdded = driver.findElements(By.xpath("//tbody[@id='deptTableBody']/tr/td[2]"));
					Assert.assertNotEquals(departmentsAfterNewDepartmentAdded.size(),presentDepartments.size() );

					   String searchNewDepartment = "Product Management";
					   driver.findElement(By.id("deptSearch")).sendKeys(searchNewDepartment);
					   departmentPanel = driver.findElement(By.cssSelector(".panel"));
					   Assert.assertTrue(departmentPanel.isDisplayed());
				    driver.findElement(By.id("deptClearFilters")).click();
				    
				    driver.findElement(By.cssSelector("button[data-testid='delete-department-" +newDepartmentId+"']")).click();
				    
				   WebElement deleteDepartmentPage = driver.findElement(By.id("confirmTitle"));
				   Assert.assertTrue(deleteDepartmentPage.isDisplayed());		
				   driver.findElement(By.id("confirmButton")).click();
				     WebElement deleteMessage = driver.findElement(By.id("toast-container"));
				     wait.until(ExpectedConditions.visibilityOf(deleteMessage));
				     wait.until(ExpectedConditions.invisibilityOf(deleteMessage));

				     List<WebElement> departmentsAfterNewDepartmentDeleted = driver.findElements(By.xpath("//tbody[@id='deptTableBody']/tr/td[2]"));
						Assert.assertEquals(departmentsAfterNewDepartmentDeleted.size(),presentDepartments.size() ); 
					
						driver.findElement(By.cssSelector("button[data-testid='nav-attendance']")).click();  
						
		         // Attendance Page 
						
						
						   String attendancePageTitle =  driver.findElement(By.id("pageTitle")).getText();
							 Assert.assertEquals(attendancePageTitle, "Attendance Management");
							 
							 String employeeId = "EMP1002";
						
							 driver.findElement(By.id("attSearch")).sendKeys(employeeId);
							 WebElement selectDepartmentDropDown= driver.findElement(By.id("attDeptFilter"));
                             Select selectDepartmentOfEmployee = new Select(selectDepartmentDropDown);
                             selectDepartmentOfEmployee.selectByContainsVisibleText("Human Resources");
							 
				List<WebElement> employeeStatus =  driver.findElements(By.xpath("//td/span"));
				WebElement nextButton = driver.findElement(By.id("nextPage"));
				
								
				// print by present half day leave in that format 
				
				ArrayList<String> presentList = new ArrayList<>();
				ArrayList<String> lateList = new ArrayList<>();
				ArrayList<String> halfDayList = new ArrayList<>();
				ArrayList<String> leaveList = new ArrayList<>();

				for (WebElement status : employeeStatus) {

				    WebElement row = status.findElement(By.xpath("./ancestor::tr"));

				    String id = row.findElement(By.xpath("./td[1]")).getText();
				    String name = row.findElement(By.xpath("./td[2]")).getText();
				    String department = row.findElement(By.xpath("./td[3]")).getText();
				    String date = row.findElement(By.xpath("./td[4]")).getText();
				    String empStatus = status.getText();

				    String data = id + " | " + name + " | " + department + " | "
				                  + date + " | " + empStatus;

				    if (empStatus.contains("Present")) {

				        presentList.add(data);

				    } else if (empStatus.contains("Late")) {

				        lateList.add(data);

				    } else if (empStatus.contains("Half Day")) {

				        halfDayList.add(data);

				    } else if (empStatus.contains("Leave")) {

				        leaveList.add(data);
				    }
				    if(nextButton.isEnabled()) {
				    	nextButton.click();
				    }
				}
			
				System.out.println("===== PRESENT =====");

				for (String data : presentList) {
				    System.out.println(data);
				}

				System.out.println("Total Present: " + presentList.size());


				System.out.println("\n===== LATE =====");

				for (String data : lateList) {
				    System.out.println(data);
				}

				System.out.println("Total Late: " + lateList.size());


				System.out.println("\n===== HALF DAY =====");

				for (String data : halfDayList) {
				    System.out.println(data);
				}

				System.out.println("Total Half Day: " + halfDayList.size());


				System.out.println("\n===== LEAVE =====");

				for (String data : leaveList) {
				    System.out.println(data);
				}

				System.out.println("Total Leave: " + leaveList.size());
				
				//mark attendance
				
				String idToSearch = "EMP1026";
				driver.findElement(By.id("markAttBtn")).click();
				String date = driver.findElement(By.id("a_date")).getAttribute("value");

				String formattedDate = date.substring(8, 10)
				                     + date.substring(5, 7)
				                     + date.substring(0, 4);

				System.out.println(formattedDate);
			WebElement employeeDropdown =	driver.findElement(By.id("a_emp"));
			Select selectEmployee = new Select(employeeDropdown);
			selectEmployee.selectByValue(idToSearch);
			driver.findElement(By.id("a_in")).sendKeys("0900");
			driver.findElement(By.id("a_out")).sendKeys("1800");
			WebElement attendanceStatusDropdown = driver.findElement(By.id("a_status"));
			Select attendanceStatus = new Select(attendanceStatusDropdown);
			attendanceStatus.selectByVisibleText("Present");
			driver.findElement(By.id("saveAttBtn")).click();
			//toast-message.success
			WebElement attendanceSuccessMessage = driver.findElement(By.cssSelector(".toast-message.success"));
			wait.until(ExpectedConditions.visibilityOf(attendanceSuccessMessage));
			wait.until(ExpectedConditions.invisibilityOf(attendanceSuccessMessage));

			
			
			   driver.findElement(By.id("attSearch")).sendKeys(idToSearch);
	            driver.findElement(By.id("attDateFilter")).sendKeys(formattedDate);
	            WebElement isTodayAttendanceDisplayed = driver.findElement(By.xpath("//tbody/tr"));
	            Assert.assertTrue(isTodayAttendanceDisplayed.isDisplayed());
	            
	            
	            driver.findElement(By.id("attClearFilters")).click();
				driver.findElement(By.cssSelector("button[data-testid='nav-leave-management']")).click();  
 
				//Leave Management 
				 String leaveManagementPageTitle =  driver.findElement(By.id("pageTitle")).getText();
				 Assert.assertEquals(leaveManagementPageTitle, "Leave Management");
				 WebElement leaveManagementPanel = driver.findElement(By.cssSelector(".panel"));
				 Assert.assertTrue(leaveManagementPanel.isDisplayed());
				 
				 WebElement leaveStatus = driver.findElement(By.id("lvStatusFilter"));
				 Select leaveStatusDropdown = new Select(leaveStatus);
				 leaveStatusDropdown.selectByVisibleText("Pending");
				
				 System.out.println("========== PENDING LEAVES ==========");

				 while (true) {

				     List<WebElement> pendingLeaves = driver.findElements(
				             By.xpath("//tbody/tr"));

				     for (WebElement leave : pendingLeaves) {

				         String leaveId = leave.findElement(By.xpath("./td[1]")).getText();
				         String employeeName = leave.findElement(By.xpath("./td[2]")).getText();
				         String department = leave.findElement(By.xpath("./td[3]")).getText();
				         String leaveType = leave.findElement(By.xpath("./td[4]")).getText();

				         System.out.println("Leave ID: " + leaveId);
				         System.out.println("Employee Name: " + employeeName);
				         System.out.println("Department: " + department);
				         System.out.println("Leave Type: " + leaveType);
				         System.out.println("-------------------------");
				     }

				    
				     WebElement nextPageButton = driver.findElement(By.id("nextPage"));

				     
				     if (!nextPageButton.isEnabled()) {
				         break;
				     }
				     nextPageButton.click();
				     wait.until(ExpectedConditions.stalenessOf(pendingLeaves.get(0)));
				 }
				 
				 driver.findElement(By.id("lvClearFilters")).click();
				 driver.findElement(By.id("lvSearch")).sendKeys("Ishaan Iyer");
				 Assert.assertTrue(driver.findElement(By.cssSelector(".panel")).isDisplayed());
				 String statusBeforeApproval= driver.findElement(By.cssSelector(".badge-amber")).getText();
				 int leaveDays =Integer.parseInt( driver.findElement(By.xpath("//tbody/tr/td[7]")).getText());
				 if(leaveDays <= 3 ) {
					 driver.findElement(By.cssSelector("button[data-testid*='approve-leave']")).click();
					 WebElement approveLeaveRequest = driver.findElement(By.id("confirmTitle"));
					 wait.until(ExpectedConditions.visibilityOf(approveLeaveRequest));
					 driver.findElement(By.id("confirmButton")).click();
					 }
				 
				  	WebElement leaveApproveMsg = driver.findElement(By.id("toast-container"));
					 wait.until(ExpectedConditions.visibilityOf(leaveApproveMsg));
					 wait.until(ExpectedConditions.invisibilityOf(leaveApproveMsg));
					String statusAfterApproval = driver.findElement(By.cssSelector(".badge-green")).getText();
					Assert.assertNotEquals(statusBeforeApproval, statusAfterApproval);
					Assert.assertEquals(statusAfterApproval, "Approved");
					driver.findElement(By.id("lvClearFilters")).click();
					
					driver.findElement(By.cssSelector("button[data-testid='nav-users']")).click();  
					
					
					
					//User and Role 
					 String userPageTitle =  driver.findElement(By.id("pageTitle")).getText();
					 Assert.assertEquals(userPageTitle, "Users & Roles");
					 
					 boolean adminFound = false;
					 boolean hrFound = false;
					 boolean employeeFound = false;

					 List<WebElement> users = driver.findElements(By.xpath("//tbody/tr"));

					 for (WebElement user : users) {

					     String name = user.findElement(By.xpath("./td[1]")).getText();
					     String role = user.findElement(By.xpath("./td[3]")).getText();
					     String status = user.findElement(By.xpath("./td[4]")).getText();

					     if (name.equals("System Administrator")) {
					         adminFound = true;
					         Assert.assertEquals(role, "Admin");
					         Assert.assertEquals(status, "Active");
					     }

					     if (name.equals("Priya Sharma")) {
					         hrFound = true;
					         Assert.assertEquals(role, "HR");
					         Assert.assertEquals(status, "Active");
					     }

					     if (name.equals("Aarav Sharma")) {
					         employeeFound = true;
					         Assert.assertEquals(role, "Employee");
					         Assert.assertEquals(status, "Active");
					     }
					 }

					 Assert.assertTrue(adminFound, "Admin user not found");
					 Assert.assertTrue(hrFound, "HR user not found");
					 Assert.assertTrue(employeeFound, "Employee user not found");
					 
					// Click Add User
					 driver.findElement(By.xpath("//button[contains(text(),'Add User')]")).click();

					 // Enter Name
					 driver.findElement(By.id("u_name")).sendKeys("Rahul Patil");

					 // Enter Username / Email
					 driver.findElement(By.id("u_email")).sendKeys("rahul@peopleflow.com");

					 // Select Role
					 Select role = new Select(driver.findElement(By.id("u_role")));
					 role.selectByVisibleText("HR");

					 // Select Status
					 Select status = new Select(driver.findElement(By.id("u_status")));
					 status.selectByVisibleText("Active");

					 // Save User
					 driver.findElement(By.xpath("//button[contains(text(),'Save User')]")).click();
					 
						WebElement userAddedSuccessMessage = driver.findElement(By.cssSelector(".toast-message.success"));
						wait.until(ExpectedConditions.visibilityOf(userAddedSuccessMessage));
						wait.until(ExpectedConditions.invisibilityOf(userAddedSuccessMessage));
					 List<WebElement> systemUsers = driver.findElements(By.xpath("//tbody/tr"));

					 boolean addedUserFound = false;

					 for (WebElement user : systemUsers) {

					     String name = user.findElement(By.xpath("./td[1]")).getText();
					     String usernameEmail = user.findElement(By.xpath("./td[2]")).getText();
					     String roleName = user.findElement(By.xpath("./td[3]")).getText();
					     String statusName = user.findElement(By.xpath("./td[4]")).getText();

					     if (name.equals("Rahul Patil")) {

					    	 addedUserFound = true;

					         Assert.assertEquals(usernameEmail, "rahul@peopleflow.com");
					         Assert.assertEquals(roleName, "HR");
					         Assert.assertEquals(statusName, "Active");

					         System.out.println("New user added successfully");
					     }
					 }

					 Assert.assertTrue(addedUserFound, "Rahul Patil was not found");

				 
					  users = driver.findElements(By.xpath("//tbody/tr"));

					 for (WebElement user : users) {

					     String name = user.findElement(By.xpath("./td[1]")).getText();

					     if (name.equals("Rahul Patil")) {

					         // Click delete button in the same row
					         user.findElement(By.xpath(".//td[5]//button[2]")).click();

					         break;
					     }
					 }
					 
					 driver.findElement(By.xpath("//button[contains(text(),'Delete')]")).click();
					 WebElement userDeletedSuccessMessage = driver.findElement(By.cssSelector(".toast-message.success"));
						wait.until(ExpectedConditions.visibilityOf(userDeletedSuccessMessage));
						wait.until(ExpectedConditions.invisibilityOf(userDeletedSuccessMessage));
					 
					 boolean deletedUserFound = false;

					 List<WebElement> usersAfterDelete =
					         driver.findElements(By.xpath("//tbody/tr"));

					 for (WebElement user : usersAfterDelete) {

					     String name = user.findElement(By.xpath("./td[1]")).getText();

					     if (name.equals("Rahul Patil")) {
					    	 deletedUserFound = true;
					         break;
					     }
					 }

					 Assert.assertFalse(deletedUserFound, "User was not deleted");

					 System.out.println("Rahul Patil deleted successfully");
				 
					 driver.findElement(By.cssSelector("button[data-testid='nav-reports']")).click();  
						
						
						
						//ReportPage 
						 String reportPageTitle =  driver.findElement(By.id("pageTitle")).getText();
						 Assert.assertEquals(reportPageTitle, "Reports");

						 // Employee report
						 driver.findElement(By.xpath("//button[contains(text(),'Employee Report')]")).click();


						 List<WebElement> employeeRows =
						         driver.findElements(By.xpath("//table[@id='reportTable']//tbody/tr"));

						 Assert.assertTrue(employeeRows.size() > 0,
						         "Employee report is empty");

						 System.out.println("Employee report records: " + employeeRows.size());

						 // Attendance Report

            	 driver.findElement(By.xpath("//button[contains(text(),'Attendance Report')]")).click();

						 List<WebElement> attendanceHeaders =
						         driver.findElements(By.xpath("//table[@id='reportTable']//thead//th"));


						 List<WebElement> attendanceRows =
						         driver.findElements(By.xpath("//table[@id='reportTable']//tbody/tr"));

						 Assert.assertTrue(attendanceRows.size() > 0,
						         "Attendance report is empty");

						 System.out.println("Attendance report records: " + attendanceRows.size());

						 // Leave Report
					

						 driver.findElement(By.xpath("//button[contains(text(),'Leave Report')]")).click();

						 List<WebElement> leaveRows =
						         driver.findElements(By.xpath("//table[@id='reportTable']//tbody/tr"));

						 Assert.assertTrue(leaveRows.size() > 0,
						         "Leave report is empty");

						 System.out.println("Leave report records: " + leaveRows.size());

driver.findElement(By.id("logoutBtn")).click();
WebElement confirmLogout = driver.findElement(By.id("confirmTitle"));
wait.until(ExpectedConditions.visibilityOf(confirmLogout));
driver.findElement(By.id("confirmButton")).click();
	}
}
