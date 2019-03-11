package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_02_Xpath_CSS_Excercise {
	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver", "D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.guru99.com/");
	}

	@Test
	public void TC_01_EmailAndPasswordEmpty() throws Exception {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.name("login[password]")).sendKeys("");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		String emailRequiredMsg = driver.findElement(By.id("advice-required-entry-email")).getText();
		Assert.assertEquals(emailRequiredMsg, "This is a required field.");
		String passwordRequiredMsg = driver.findElement(By.id("advice-required-entry-pass")).getText();
		Assert.assertEquals(passwordRequiredMsg, "This is a required field.");
	}

	@Test
	public void TC_02_LoginWithInvalidEmail() throws Exception {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("123434234@12312.123123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		String invalidEmailMsg = driver.findElement(By.id("advice-validate-email-email")).getText();
		Assert.assertEquals(invalidEmailMsg, "Please enter a valid email address. For example johndoe@domain.com.");
	}
	
	@Test
	public void TC_03_LoginWithPasswordHasLessThanSixCharacters() throws Exception {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		String invalidPassMsg = driver.findElement(By.id("advice-validate-password-pass")).getText();
		Assert.assertEquals(invalidPassMsg, "Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void TC_04_LoginWithIncorrectPassword() throws Exception {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123123123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		String incorrectPassMsg = driver.findElement(By.xpath("//span[text()='Invalid login or password.']")).getText();
		Assert.assertEquals(incorrectPassMsg, "Invalid login or password.");
	}
	
	@Test
	public void TC_05_CreateAnAccount() throws Exception {
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys("Thao");
		driver.findElement(By.id("middlename")).sendKeys("Huong");
		driver.findElement(By.id("lastname")).sendKeys("Tran");
		driver.findElement(By.id("email_address")).sendKeys("thaotran" + randomNumber() + "@gmail.com");
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.id("confirmation")).sendKeys("123456");
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		
		//Check Register successfully
		String successMsg = driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText();
		Assert.assertEquals(successMsg, "Thank you for registering with Main Website Store.");
		
		//Log out
		driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();	
		
		//Check navigate home page
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='page-title']//img[contains(@src,'logo.png')]")).isDisplayed());
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}
	
	public int randomNumber() {
		int random = (int )(Math.random() * 50 + 1);
		return random;
	}
	

}
