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

public class Topic_07_UserInteraction_Excersise {
	WebDriver driver;
	Actions action;

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver",
				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");
		driver = new FirefoxDriver();
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void TC_01_HoverToElement() throws Exception {
		driver.get("https://www.myntra.com/s");
		// Hover to profileIcon
		WebElement profileIcon = driver.findElement(By.xpath("//span[contains(@class,'desktop-iconUser')]"));
		action.moveToElement(profileIcon).perform();
		Thread.sleep(3000);

		// Click Login button
		WebElement loginBtn = driver.findElement(By.xpath("//a[@class='desktop-linkButton' and text() = 'log in']"));
		action.click(loginBtn).perform();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='login-box']")).isDisplayed());
	}

	public void TC_02_ClickAndHoldElement() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");

		List<WebElement> listElement = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		System.out.println("Total items trước khi chọn: " + listElement.size());

		// Click and hold element
		action.clickAndHold(listElement.get(0)).moveToElement(listElement.get(3)).release().perform();
		Thread.sleep(3000);

		// Các item đã select
		List<WebElement> numberItemSelected = driver
				.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"));
		System.out.println("Total items sau khi chon = " + numberItemSelected.size());
		Assert.assertEquals(numberItemSelected.size(), 4);

	}

	public void TC_03_ClickAndHoldElement_Random() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");

		List<WebElement> listElement = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		// Giữ phím Ctrl
		action.keyDown(Keys.CONTROL).perform();
		// Click chọn các element
		action.click(listElement.get(0));
		action.click(listElement.get(2));
		action.click(listElement.get(4));

		// Nhả phím Ctrl
		action.keyUp(Keys.CONTROL).perform();
		Thread.sleep(3000);
	}

	public void TC_04_DoubleClick() throws Exception {
		driver.get("http://www.seleniumlearn.com/double-click");
		WebElement doubleClickMeBtn = driver.findElement(By.xpath("//button[text()='Double-Click Me!']"));
		action.doubleClick(doubleClickMeBtn).perform();
		Thread.sleep(3000);

		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "The Button was double-clicked.");
		alert.accept();
	}

	public void TC_05_RightClickToElement() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		// Right click to Right click me Button
		WebElement rightClickMeBtn = driver.findElement(By.xpath("//span[text()='right click me']"));
		action.contextClick(rightClickMeBtn).perform();

		WebElement quitContext = driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit')]"));
		action.moveToElement(quitContext).perform();
		Assert.assertTrue(driver.findElement(By.xpath(
				"//li[contains(@class,'context-menu-icon-quit') and contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]"))
				.isDisplayed());
		quitContext.click();

		Alert quitAlert = driver.switchTo().alert();
		Assert.assertEquals(quitAlert.getText(), "clicked: quit");
		quitAlert.accept();

		Assert.assertFalse(quitContext.isDisplayed());
	}

	@Test
	public void TC_06_DragAndDropElement() {
		driver.get("http://demos.telerik.com/kendo-ui/dragdrop/angular");
		WebElement dropTarget = driver.findElement(By.xpath("//div[@id='droptarget']"));
		WebElement draggable = driver.findElement(By.xpath("//div[@id='draggable']"));
		action.dragAndDrop(draggable, dropTarget).perform();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@id='droptarget' and text() = 'You did great!']")).isDisplayed());
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
