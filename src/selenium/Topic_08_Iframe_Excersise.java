package selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_08_Iframe_Excersise {
	WebDriver driver;
	JavascriptExecutor jsExcutor;

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver",
				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");
		driver = new FirefoxDriver();
		jsExcutor = (JavascriptExecutor) (driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_CloseFrame() throws Exception {
		driver.get("https://www.hdfcbank.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Khai báo notification Iframe
		List<WebElement> notificationIframe = driver.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		int notificationFrameSize = notificationIframe.size();
		System.out.println("Notification frame displayed " + notificationFrameSize);

		// Size > 0 ~ Frame có xuất hiện
		if (notificationFrameSize > 0) {
			// Switch qua iframe
			driver.switchTo().frame(notificationIframe.get(0));

			// Verify image trong pop up được hiển thị
			Assert.assertTrue(driver.findElement(By.xpath("//div[@id='container-div']/img")).isDisplayed());

			// Close popup đó
			// driver.findElement(By.xpath("//div[@id='div-close']")).click();
			jsExcutor.executeScript("arguments[0].click()", driver.findElement(By.xpath("//div[@id='div-close']")));

			System.out.println("Close pop up successfully");
			driver.switchTo().defaultContent();
		}

		System.out.println("Pass handle pop up successfully");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		// Switch to iframe chứa text
		WebElement textLookingForIframe = driver.findElement(By.xpath("//div[@class='flipBannerWrap']//iframe"));
		driver.switchTo().frame(textLookingForIframe);
		// Verify text hiển thị
		Assert.assertTrue(
				driver.findElement(By.xpath("//span[@id='messageText' and text() = 'What are you looking for?']"))
						.isDisplayed());
		// Switch to default window
		driver.switchTo().defaultContent();

		// Switch to iframe chứa sliding banner images
		WebElement slidingBannerIframe = driver.findElement(By.xpath("//div[@class='slidingbanners']//iframe"));
		driver.switchTo().frame(slidingBannerIframe);

		// Verify sliding banner images
		List<WebElement> slidingBannerImages = driver.findElements(By.xpath("//img[@class='bannerimage']"));
		System.out.println("Banner image size: " + slidingBannerImages.size());
		Assert.assertEquals(slidingBannerImages.size(), 6);

		// Check every images are loaded successfully
		for (int i = 0; i < slidingBannerImages.size(); i++) {
			if (isImageLoadSuccess(slidingBannerImages.get(i))) {
				System.out.println("Sliding image [" + i + "] is loaded successfully");
			}
		}

		// Switch to default window
		driver.switchTo().defaultContent();
		// Find flipBannerImages
		List<WebElement> flipBannerImages = driver
				.findElements(By.xpath("//div[@class='flipBanner']//img[@class='front icon']"));

		// Verify flipBannerImages
		System.out.println("Banner image size: " + flipBannerImages.size());
		Assert.assertEquals(flipBannerImages.size(), 8);

		// Check every images are loaded and displayed successfully
		for (int i = 0; i < flipBannerImages.size(); i++) {
			if (isImageLoadSuccess(flipBannerImages.get(i)) && flipBannerImages.get(i).isDisplayed()) {
				System.out.println("Flip banner image [" + i + "] is loaded and displayed successfully");
			}
		}

	}

	public boolean isImageLoadSuccess(WebElement e) {
		return (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				e);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
