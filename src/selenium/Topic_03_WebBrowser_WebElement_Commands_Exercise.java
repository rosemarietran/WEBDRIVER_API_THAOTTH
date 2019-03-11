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

public class Topic_03_WebBrowser_WebElement_Commands_Exercise {
	WebDriver driver;
	
	By emailTextbox = By.xpath("//input[@id='mail']");
	By passwordTextbox = By.xpath("//input[@id='password']");
	By ageUnder18Radio = By.xpath("//input[@id='under_18']");
	By eduTextArea = By.xpath("//textarea[@id='edu']");
	By jobRole1Select = By.xpath("//select[@id='job1']");
	By jobRole2Select = By.xpath("//select[@id='job2']");
	By slide1Input = By.xpath("//input[@id='slider-1']");
	By slide2Input = By.xpath("//input[@id='slider-2']");
	By ageDisabledRadio = By.xpath("//input[@id='radio-disabled']");
	By enabledButton = By.xpath("//button[@id='button-enabled']");
	By disabledButton = By.xpath("//button[@id='button-disabled']");
	By InterestCheckbox = By.xpath("//select[@id='job1']");
	By interestDevelopmentCheckbox = By.xpath("//input[@id='development']");
	By interestDisabledCheckbox = By.xpath("//input[@id='check-disbaled']");
	By biographyTextArea = By.xpath("//textarea[@id='bio']");
	
	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver", "D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://daominhdam.github.io/basic-form/index.html");
	}

	@Test
	public void TC_01_CheckDisplayedElements() {
		if (isElementDisplayed(emailTextbox)) {
			driver.findElement(emailTextbox).sendKeys("Automation Testing");
		} 
			
		if (isElementDisplayed(eduTextArea)) {
			driver.findElement(eduTextArea).sendKeys("Automation Testing");
		} 
		
		if (isElementDisplayed(ageUnder18Radio)) {
			driver.findElement(ageUnder18Radio).click();
		} 
	}

	@Test
	public void TC_02_CheckEnabledDisabledElements() {
		//Check enabled elements
		System.out.println("Check enabled elements:");
		Assert.assertTrue(isElementEnabled(emailTextbox));
		Assert.assertTrue(isElementEnabled(ageUnder18Radio));
		Assert.assertTrue(isElementEnabled(eduTextArea));
		Assert.assertTrue(isElementEnabled(jobRole1Select));	
		Assert.assertTrue(isElementEnabled(interestDevelopmentCheckbox));
		Assert.assertTrue(isElementEnabled(slide1Input));
		Assert.assertTrue(isElementEnabled(enabledButton));
		
		//Check disabled elements
		System.out.println("Check disabled elements:");
		Assert.assertFalse(isElementEnabled(passwordTextbox));
		Assert.assertFalse(isElementEnabled(ageDisabledRadio));
		Assert.assertFalse(isElementEnabled(biographyTextArea));
		Assert.assertFalse(isElementEnabled(jobRole2Select));
		Assert.assertFalse(isElementEnabled(interestDisabledCheckbox));
		Assert.assertFalse(isElementEnabled(slide2Input));
		Assert.assertFalse(isElementEnabled(disabledButton));
	}
	
	@Test
	public void TC_03_CheckSelectedElements() {
		checkToElement(ageUnder18Radio);
		checkToElement(interestDevelopmentCheckbox);
		Assert.assertTrue(isElementSelected(ageUnder18Radio));
		Assert.assertTrue(isElementSelected(interestDevelopmentCheckbox));
		uncheckToCheckbox(interestDevelopmentCheckbox);
		Assert.assertFalse(isElementSelected(interestDevelopmentCheckbox));
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}
	
	//Check element displayed
	public boolean isElementDisplayed(By byValue) {
		if (driver.findElement(byValue).isDisplayed()) {
			System.out.println("Element [" + byValue + "] is displayed!");
			return true;
		} else {
			System.out.println("Element [" + byValue + "] is not displayed!");
			return false;
		}
	}

	//Check element enabled/ disabled
	public boolean isElementEnabled(By byValue) {
		if (driver.findElement(byValue).isEnabled()) {
			System.out.println("Element [" + byValue + "] is enabled!");
			return true;
		} else {
			System.out.println("Element [" + byValue + "] is disabled!");
			return false;
		}
	}
	
	//Check element selected
		public boolean isElementSelected(By byValue) {
			if (driver.findElement(byValue).isSelected()) {
				System.out.println("Element [" + byValue + "] is selected!");
				return true;
			} else {
				System.out.println("Element [" + byValue + "] is not selected!");
				return false;
			}
		}
	
	//Check element selected
	public void checkToElement(By byValue) {
		WebElement e = driver.findElement(byValue);
		if(!e.isSelected()) {
			e.click();
		} else {
			System.out.println("Element [" + byValue + "] is selected!");
		}
	}
	
	public void uncheckToCheckbox(By byValue) {
		WebElement e = driver.findElement(byValue);
		if(e.isSelected()) {
			e.click();
		} else {
			System.out.println("Element [" + byValue + "] is unselected!");
		}
	}
}
