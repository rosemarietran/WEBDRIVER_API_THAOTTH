package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_04_HandleTextbox_TextArea_Exercise {
	WebDriver driver;

	// Declare variables Information for login
	String username = "mngr181358";
	String password = "berydUp";

	// Declare variables Information for adding new customer
	String custName, gender, dob, addr, city, state, pin, phone, email, pwd;
	// Declare variable for edit customer information
	String custID;

	// Locate Elements in login page
	By usernameTxtbox = By.xpath("//input[@name='uid']");
	By pwdTxtbox = By.xpath("//input[@name='password']");
	By loginBtn = By.xpath("//input[@name='btnLogin']");
	By welcomeTxt = By.xpath("//marquee[text()=\"Welcome To Manager's Page of Guru99 Bank\"]");

	// Locate Elements in Add new customer page
	By addNewCustLinkTxt = By.xpath("//a[text()='New Customer']");
	By goToAddNewCustPageSuccessTxt = By.xpath("//p[@class='heading3' and text() = 'Add New Customer']");
	By custNameTxtbox = By.xpath("//input[@name='name']");
	By genderFemaleRadio = By.xpath("//input[@name='rad1' and @value='f']");
	By dobTxtbox = By.xpath("//input[@id='dob']");
	By addressTxtbox = By.xpath("//textarea[@name='addr']");
	By cityTxtbox = By.xpath("//input[@name='city']");
	By stateTxtbox = By.xpath("//input[@name='state']");
	By pinoTxtbox = By.xpath("//input[@name='pinno']");
	By phoneTxtbox = By.xpath("//input[@name='telephoneno']");
	By emailTxtbox = By.xpath("//input[@name='emailid']");
	By passwordTxtbox = By.xpath("//input[@name='password']");
	By submitBtn = By.xpath("//input[@name='sub']");

	// Locate Elements in page after add new customer successfully
	By addNewCustSuccessTxt = By.xpath("//p[@class='heading3' and text() = 'Customer Registered Successfully!!!']");
	By cusIDLabel = By.xpath("//td[text()='Customer ID']/following-sibling::td");
	By cusNameLabel = By.xpath("//td[text()='Customer Name']/following-sibling::td");
	By cusGenderLabel = By.xpath("//td[text()='Gender']/following-sibling::td");
	By cusBirthdateLabel = By.xpath("//td[text()='Birthdate']/following-sibling::td");
	By cusAddrLabel = By.xpath("//td[text()='Address']/following-sibling::td");
	By cusCityLabel = By.xpath("//td[text()='City']/following-sibling::td");
	By cusStateLabel = By.xpath("//td[text()='State']/following-sibling::td");
	By cusPinLabel = By.xpath("//td[text()='Pin']/following-sibling::td");
	By cusPhoneLabel = By.xpath("//td[text()='Mobile No.']/following-sibling::td");
	By cusEmailLabel = By.xpath("//td[text()='Email']/following-sibling::td");

	// Elements in Edit customer information page
	By editCustLinkTxt = By.xpath("//a[text()='Edit Customer']");
	By goToEditCustPageSuccessTxt = By.xpath("//p[@class='heading3' and text() = 'Edit Customer Form']");
	By custIdTextbox = By.xpath("//input[@name='cusid']");
	By submitToEditCustBtn = By.xpath("//input[@name='AccSubmit']");
	By editCustSuccessTxt = By.xpath("//p[@class='heading3' and text() = 'Customer details updated Successfully!!!']");

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver",
				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4");
		login(username, password);

		// Map data test for adding new customer
		custName = "Selenium online";
		gender = "female";
		dob = "1991-01-01";
		addr = "123 To Huu";
		city = "Hanoi";
		state = "Ha Dong";
		pin = "600000";
		phone = "0987654321";
		email = "automationtesting" + randomNumber() + "@gmail.com";
		pwd = "123456";

	}

	@Test
	public void TC_01_CreateNewCustomer() {
		// Click Add new customer menu function
		driver.findElement(addNewCustLinkTxt).click();
		Assert.assertTrue(isElementDisplayed(goToAddNewCustPageSuccessTxt));
		addNewCustomer(custName, gender, dob, addr, city, state, pin, phone, email, pwd);
	}

	@Test
	public void TC_02_EditCustomer() {
		// Click Edit customer menu function
		driver.findElement(editCustLinkTxt).click();
		Assert.assertTrue(isElementDisplayed(goToEditCustPageSuccessTxt));

		// Submit customer ID
		driver.findElement(custIdTextbox).sendKeys(custID);
		driver.findElement(submitToEditCustBtn).click();

		// Verify customer name and address
		verifyCustInforInEditPage(custName, addr);

		// Map data test for editing customer information
		addr = "239 Xuan Thuy";
		city = "Saigon";
		state = "Cau Giay";
		pin = "123456";
		phone = "1234567890";
		email = "seleniumtest" + randomNumber() + "@gmail.com";
		pwd = "654321";
		
		editCustomer(addr, city, state, pin, phone, email);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	// Login
	public void login(String username, String password) {
		driver.findElement(usernameTxtbox).sendKeys(username);
		driver.findElement(pwdTxtbox).sendKeys(password);
		driver.findElement(loginBtn).click();
		Assert.assertTrue(isElementDisplayed(welcomeTxt));
	}

	// Add new customer
	public void addNewCustomer(String custName, String gender, String dob, String addr, String city, String state,
			String pin, String phone, String email, String pwd) {
		driver.findElement(custNameTxtbox).sendKeys(custName);
		driver.findElement(genderFemaleRadio).click();
		driver.findElement(dobTxtbox).sendKeys(dob);
		driver.findElement(addressTxtbox).sendKeys(addr);
		driver.findElement(cityTxtbox).sendKeys(city);
		driver.findElement(stateTxtbox).sendKeys(state);
		driver.findElement(pinoTxtbox).sendKeys(pin);
		driver.findElement(phoneTxtbox).sendKeys(phone);
		driver.findElement(emailTxtbox).sendKeys(email);
		driver.findElement(passwordTxtbox).sendKeys(pwd);
		driver.findElement(submitBtn).click();

		// Verify new customer's information
		Assert.assertTrue(isElementDisplayed(addNewCustSuccessTxt));
		custID = driver.findElement(cusIDLabel).getText();
		System.out.println("New Customer ID: " + custID);
		Assert.assertEquals(driver.findElement(cusNameLabel).getText(), custName);
		Assert.assertEquals(driver.findElement(cusGenderLabel).getText(), gender);
		Assert.assertEquals(driver.findElement(cusBirthdateLabel).getText(), dob);
		Assert.assertEquals(driver.findElement(cusAddrLabel).getText(), addr);
		Assert.assertEquals(driver.findElement(cusCityLabel).getText(), city);
		Assert.assertEquals(driver.findElement(cusStateLabel).getText(), state);
		Assert.assertEquals(driver.findElement(cusPinLabel).getText(), pin);
		Assert.assertEquals(driver.findElement(cusPhoneLabel).getText(), phone);
		Assert.assertEquals(driver.findElement(cusEmailLabel).getText(), email);
	}

	// Edit customer
	public void editCustomer(String addr, String city, String state, String pin, String phone, String email) {
		driver.findElement(addressTxtbox).clear();
		driver.findElement(cityTxtbox).clear();
		driver.findElement(stateTxtbox).clear();
		driver.findElement(pinoTxtbox).clear();
		driver.findElement(phoneTxtbox).clear();
		driver.findElement(emailTxtbox).clear();
		
		driver.findElement(addressTxtbox).sendKeys(addr);
		driver.findElement(cityTxtbox).sendKeys(city);
		driver.findElement(stateTxtbox).sendKeys(state);
		driver.findElement(pinoTxtbox).sendKeys(pin);
		driver.findElement(phoneTxtbox).sendKeys(phone);
		driver.findElement(emailTxtbox).sendKeys(email);
		driver.findElement(submitBtn).click();

		// Verify after edit customer's information
		Assert.assertTrue(isElementDisplayed(editCustSuccessTxt));
		Assert.assertEquals(driver.findElement(cusAddrLabel).getText(), addr);
		Assert.assertEquals(driver.findElement(cusCityLabel).getText(), city);
		Assert.assertEquals(driver.findElement(cusStateLabel).getText(), state);
		Assert.assertEquals(driver.findElement(cusPinLabel).getText(), pin);
		Assert.assertEquals(driver.findElement(cusPhoneLabel).getText(), phone);
		Assert.assertEquals(driver.findElement(cusEmailLabel).getText(), email);
	}

	// Verify customer information in edit page
	public void verifyCustInforInEditPage(String customerName, String customerAddress) {
		Assert.assertEquals(driver.findElement(custNameTxtbox).getAttribute("value"), customerName);
		Assert.assertEquals(driver.findElement(addressTxtbox).getText(), customerAddress);
	}

	// Check element displayed
	public boolean isElementDisplayed(By byValue) {
		if (driver.findElement(byValue).isDisplayed()) {
			System.out.println("Element [" + byValue + "] is displayed!");
			return true;
		} else {
			System.out.println("Element [" + byValue + "] is not displayed!");
			return false;
		}
	}

	public int randomNumber() {
		int random = (int) (Math.random() * 50 + 1);
		return random;
	}
}
