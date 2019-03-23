package selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_06_Button_Radio_Checkbox_Alert_Exercise {
	WebDriver driver;
	JavascriptExecutor jsExcutor;

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver",
				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");
		driver = new FirefoxDriver();
		jsExcutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void TC_01_Button_Click() {
		driver.get("http://live.guru99.com/");
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		myAccountLink.click();

		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/login/");

		WebElement createAnAccountLink = driver.findElement(By.xpath("//a[@title='Create an Account']"));
		createAnAccountLink.click();

		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/create/");

	}

	// Sử dụng JS khi thực hiện click trên IE11/chuyển page/ chuyển link/ element bị
	// che => nen dung JS

	public void TC_02_ButtonJSClick() throws Exception {
		driver.get("http://live.guru99.com/");
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		jsExcutor.executeScript("arguments[0].click();", myAccountLink);
		Thread.sleep(1500);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/login/");

		WebElement createAnAccountLink = driver.findElement(By.xpath("//a[@title='Create an Account']"));
		jsExcutor.executeScript("arguments[0].click();", createAnAccountLink);
		Thread.sleep(1500);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/create/");

	}

	// Chỉ dùng JS khi không thể click thông thường. Có thể kiểm tra nếu element is
	// not displayed -> mới dùng JS để click

	public void TC_03_CustomCheckbox_JSClick() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
		// Click
		WebElement dualzoneCheckbox = driver
				.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		checkToCheckBoxOrRadioButton(dualzoneCheckbox);
		// Verify
		Assert.assertTrue(dualzoneCheckbox.isSelected());
		// Uncheck
		uncheckToCheckBox(dualzoneCheckbox);
		// Verify
		Assert.assertFalse(dualzoneCheckbox.isSelected());
	}

	public void TC_03_CustomRadio_JSClick() {
		driver.get("https://demos.telerik.com/kendo-ui/styling/radios");
		// Click
		WebElement petrol147 = driver
				.findElement(By.xpath("//label[text() = '2.0 Petrol, 147kW']/preceding-sibling::input"));
		checkToCheckBoxOrRadioButton(petrol147);
		// Verify
		if (!petrol147.isSelected()) {
			checkToCheckBoxOrRadioButton(petrol147);
		}
		Assert.assertTrue(petrol147.isSelected());

	}

	public void TC_04_Accept_JS_Confirm_Prompt_Alert() {
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		// JS Alert accept
		WebElement JSAlertBtn = driver.findElement(By.xpath("//button[text()='Click for JS Alert']"));
		JSAlertBtn.click();

		Alert acceptAlert = driver.switchTo().alert();
		Assert.assertEquals(acceptAlert.getText(), "I am a JS Alert");

		acceptAlert.accept();
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@id='result' and text()='You clicked an alert successfully ']"))
						.isDisplayed());

		// JS Confirm accept
		driver.navigate().refresh();
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		Alert confirmAlert = driver.switchTo().alert();
		Assert.assertEquals(confirmAlert.getText(), "I am a JS Confirm");
		confirmAlert.dismiss();
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@id='result' and text()='You clicked: Cancel']")).isDisplayed());

		// JS Prompt accept
		driver.navigate().refresh();
		String inputText = "Tran Thi Huong Thao";
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		Alert promptAlert = driver.switchTo().alert();
		Assert.assertEquals(promptAlert.getText(), "I am a JS prompt");
		promptAlert.sendKeys(inputText);
		promptAlert.accept();
		Assert.assertTrue(driver.findElement(By.xpath("//p[@id='result' and text()='You entered: " + inputText + "']"))
				.isDisplayed());

	}

	@Test
	public void TC_05_AuthencationAlert() {
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		Assert.assertTrue(driver
				.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]"))
				.isDisplayed());
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	public void checkToCheckBoxOrRadioButton(WebElement e) {
		if (e.isDisplayed()) {
			System.out.println("Check by Selenium");
			e.click();
		} else {
			System.out.println("Check by JS");
			jsExcutor.executeScript("arguments[0].click()", e);
		}
	}

	public void uncheckToCheckBox(WebElement e) {
		if (e.isSelected() && e.isDisplayed()) {
			System.out.println("Uncheck by Selenium");
			e.click();
		} else if (e.isSelected() && !e.isDisplayed()) {
			System.out.println("Uncheck by JS");
			jsExcutor.executeScript("arguments[0].click()", e);
		} else if (!e.isSelected()) {
			System.out.println("Element is already un-checked");
		}
	}
}
