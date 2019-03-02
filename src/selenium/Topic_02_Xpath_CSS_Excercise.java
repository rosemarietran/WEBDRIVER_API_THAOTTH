package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Xpath_CSS_Excercise {
	WebDriver driver;

	@BeforeClass
	public void beforeTest() {
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
	
	@AfterClass
	public void afterTest() {
		driver.quit();
	}

}
