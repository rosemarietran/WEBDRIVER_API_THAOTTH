package selenium;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class Topic_12_WebDriverWait {
	WebDriver driver;
	WebDriverWait waitExplicit;
	By startButton = By.xpath("//button[text()='Start']");
	By loadingIcon = By.xpath("//div[@id='loading']/img");
	By helloText = By.xpath("//div[@id='finish']/h4[text()='Hello World!']");
	
	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver",
				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");
		driver = new FirefoxDriver();
		
	}

	public void TC_01_ImplicitWait_2s() {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.findElement(startButton).click();
		Assert.assertTrue(driver.findElement(helloText).isDisplayed());
		
	}
	
	public void TC_02_ImplicitWait_5s() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.findElement(startButton).click();
		Assert.assertTrue(driver.findElement(helloText).isDisplayed());
		
	}
	
	public void TC_03_LoadingIconInvisible_Failed() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		waitExplicit = new WebDriverWait(driver, 2);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.findElement(startButton).click();
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		Assert.assertTrue(driver.findElement(helloText).isDisplayed());
	}
	
	public void TC_04_HelloWorldTextVisible() {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		waitExplicit = new WebDriverWait(driver, 2);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.findElement(startButton).click();
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(helloText));
		Assert.assertTrue(driver.findElement(helloText).isDisplayed());
	}
	
	
	
	public void TC_05_HelloWorldText_LoadingIcon_NoLongerInDOM() {
		driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
		waitExplicit = new WebDriverWait(driver, 5);
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		
	}
	
	

	public Date getDateTimeSecond() {
		java.util.Date date = (Date) new java.util.Date();
		date = new Timestamp(date.getTime());
		return (Date) date;
	}
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
