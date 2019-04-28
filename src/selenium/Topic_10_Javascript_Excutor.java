package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_10_Javascript_Excutor {
	WebDriver driver;
	JavascriptExecutor jsExcutor;
	// Declare variables Information for login
	String username = "mngr191182";
	String password = "bagAryr";

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
	By dobTxtboxBy = By.xpath("//input[@id='dob']");
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

	@BeforeTest
	public void beforeTest() {
//		System.setProperty("webdriver.gecko.driver",
//				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");

// 		driver = new FirefoxDriver();

		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

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

	public void TC_01_JavascriptExcecutor() throws Exception {
		navigateToUrlByJS("http://live.guru99.com/");
		String domainName = executeForBrowser("return document.domain");
		Assert.assertEquals(domainName, "live.guru99.com");

		String urlName = executeForBrowser("return document.URL");
		Assert.assertEquals(urlName, "http://live.guru99.com/");

		WebElement mobileLink = driver.findElement(By.xpath("//a[text()='Mobile']"));
		highlightElement(mobileLink);
		clickToElementByJS(mobileLink);

		WebElement samsungAddToCartBtn = driver.findElement(
				By.xpath("//a[text()='Samsung Galaxy']//parent::h2/following-sibling::div[@class='actions']/button"));
		highlightElement(samsungAddToCartBtn);
		clickToElementByJS(samsungAddToCartBtn);

		String innerText = executeForBrowser("return document.documentElement.innerText");
		Assert.assertTrue(innerText.contains("Samsung Galaxy was added to your shopping cart."));

		WebElement privacyPolicyLink = driver.findElement(By.xpath("//a[text()='Privacy Policy']"));
		highlightElement(privacyPolicyLink);
		clickToElementByJS(privacyPolicyLink);
		String privacyPolicyPageTitle = executeForBrowser("return document.title");
		Assert.assertEquals(privacyPolicyPageTitle, "Privacy Policy");

		scrollToBottomPage();

		WebElement wishListRow = driver.findElement(By.xpath(
				"//th[text()='WISHLIST_CNT']/following-sibling::td[text()='The number of items in your Wishlist.']"));
		highlightElement(wishListRow);
		Assert.assertTrue(wishListRow.isDisplayed());

		navigateToUrlByJS("http://demo.guru99.com/v4/");
		String demoDomainName = executeForBrowser("return document.domain");
		Assert.assertEquals(demoDomainName, "demo.guru99.com");
	}

	public void TC_02_RemoveAttribute() throws Exception {
		navigateToUrlByJS("http://demo.guru99.com/v4/");
		login(username, password);
		// Click Add new customer menu function
		driver.findElement(addNewCustLinkTxt).click();
		Assert.assertTrue(isElementDisplayed(goToAddNewCustPageSuccessTxt));
		addNewCustomer(custName, gender, dob, addr, city, state, pin, phone, email, pwd);
	}

	@Test
	public void TC_03_CreateAnAccount() throws Exception {
		navigateToUrlByJS("http://live.guru99.com/");
		WebElement myAccountLink = driver
				.findElement(By.xpath("//div[@class='footer']//child::div//a[@title='My Account']"));
		clickToElementByJS(myAccountLink);
		clickToElementByJS(driver.findElement(By.xpath("//span[text()='Create an Account']")));
		sendkeyToElementByJS(driver.findElement(By.xpath("//input[@id='firstname']")), "Thao");
		sendkeyToElementByJS(driver.findElement(By.xpath("//input[@id='middlename']")), "Huong");
		sendkeyToElementByJS(driver.findElement(By.xpath("//input[@id='lastname']")), "Tran");
		sendkeyToElementByJS(driver.findElement(By.xpath("//input[@id='email_address']")),
				"Thao" + randomNumber() + "@gmail.com");
		sendkeyToElementByJS(driver.findElement(By.xpath("//input[@id='password']")), "12345678");
		sendkeyToElementByJS(driver.findElement(By.xpath("//input[@id='confirmation']")), "12345678");
		clickToElementByJS(driver.findElement(By.xpath("//button[@title='Register']")));
		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(),
				"Thank you for registering with Main Website Store.");
		clickToElementByJS(
				driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")));
		clickToElementByJS(driver.findElement(By.xpath("//a[@title='Log Out']")));
		Thread.sleep(5000);
		Assert.assertTrue(isElementDisplayed(By.xpath("//h2[contains(text(),'This is demo site for')]")));
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
			String pin, String phone, String email, String pwd) throws Exception {
		driver.findElement(custNameTxtbox).sendKeys(custName);
		driver.findElement(genderFemaleRadio).click();

		WebElement dobTxtbox = driver.findElement(dobTxtboxBy);
		removeAttributeInDOM(dobTxtbox, "type");
		dobTxtbox.sendKeys(dob);

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
		// Assert.assertEquals(driver.findElement(cusBirthdateLabel).getText(), dob);
		Assert.assertEquals(driver.findElement(cusAddrLabel).getText(), addr);
		Assert.assertEquals(driver.findElement(cusCityLabel).getText(), city);
		Assert.assertEquals(driver.findElement(cusStateLabel).getText(), state);
		Assert.assertEquals(driver.findElement(cusPinLabel).getText(), pin);
		Assert.assertEquals(driver.findElement(cusPhoneLabel).getText(), phone);
		Assert.assertEquals(driver.findElement(cusEmailLabel).getText(), email);
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

	public void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='6px groove red'", element);
	}

	public String executeForBrowser(String javaSript) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (String) js.executeScript(javaSript);
	}

	public Object clickToElementByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);
	}

	public Object sendkeyToElementByJS(WebElement element, String value) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public void removeAttributeInDOM(WebElement element, String attribute) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].removeAttribute('" + attribute + "');", element);
		Thread.sleep(3000);
	}

	public Object scrollToBottomPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public Object navigateToUrlByJS(String url) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.location = '" + url + "'");
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
