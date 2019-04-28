package selenium;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.base.Function;

public class Topic_13_WebDriverWait2 {
	WebDriver driver;
	WebDriverWait waitExplicit;


	
	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver",
				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");
		driver = new FirefoxDriver();
		waitExplicit = new WebDriverWait(driver, 30);
	}
	
	public void TC_01_AjaxLoading_WithoutExplicitWait() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='contentWrapper']")).isDisplayed());
		
		String beforeDateSelected = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText();
		System.out.println("Ngay truoc khi chon: " + beforeDateSelected);
		
		driver.findElement(By.xpath("//a[text()='10']")).click();
		//Wait cho 1 element ke tiep duoc hien thi
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1' and contains(text(),'Wednesday, April 10, 2019')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='10']//parent::td[@class='rcSelected']")).isDisplayed());
	
		String afterDateSelected = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText().trim();
		System.out.println("Ngay sau khi chon: " + afterDateSelected);
		Assert.assertEquals(afterDateSelected,"Wednesday, April 10, 2019");
		
	}
	
	
	public void TC_02_AjaxLoading_WithExplicitWait() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		waitExplicit.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='contentWrapper']")));
		//Assert.assertTrue(driver.findElement(By.xpath("//div[@class='contentWrapper']")).isDisplayed());
		
		String beforeDateSelected = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText();
		System.out.println("Ngay truoc khi chon: " + beforeDateSelected);
		
		waitExplicit.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='10']")));
		driver.findElement(By.xpath("//a[text()='10']")).click();
		
		//Wait for Ajax loading icon bien mat
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='raDiv']")));
		
		//Wait cho 1 element ke tiep duoc hien thi
		//Assert.assertTrue(driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1' and contains(text(),'Wednesday, April 10, 2019')]")).isDisplayed());
		
		//Wait for selected element visible
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='10']//parent::td[@class='rcSelected']")));
	
		//Assert.assertTrue(driver.findElement(By.xpath("//a[text()='10']//parent::td[@class='rcSelected']")).isDisplayed());
		
		String afterDateSelected = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText().trim();
		System.out.println("Ngay sau khi chon: " + afterDateSelected);
		
		Assert.assertEquals(afterDateSelected,"Wednesday, April 10, 2019");		
	}
	
	@Test
	public void TC_03_FluentWait() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://daominhdam.github.io/fluent-wait/");
		WebElement countdount =  driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		waitExplicit.until(ExpectedConditions.visibilityOf(countdount));

		// Khởi tạo Fluent wait
		new FluentWait<WebElement>(countdount)
		           // Tổng time wait là 15s
		           .withTimeout(15, TimeUnit.SECONDS)
		            // Tần số mỗi 1s check 1 lần
		            .pollingEvery(500, TimeUnit.MILLISECONDS)
		           // Nếu gặp exception là find ko thấy element sẽ bỏ  qua
		            .ignoring(NoSuchElementException.class)
		            // Kiểm tra điều kiện
		            .until(new Function<WebElement, Boolean>() {
		                public Boolean apply(WebElement element) {
		                           // Kiểm tra điều kiện countdount = 00
		                           boolean flag =  element.getText().endsWith("00");
		                           System.out.println("Time = " +  element.getText());
		                           // return giá trị cho function apply
		                           return flag;
		                }
		           });
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
