package selenium;

import java.util.List;
import java.util.Set;
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

public class Topic_09_Window_Tab_Excersise {
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

	public void TC_01_SwitchTabs() throws Exception {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		// Lấy ID của cửa sổ đang active
		String parentId = driver.getWindowHandle();
		System.out.println("Parent ID: " + parentId);
		String parentTitle = driver.getTitle();

		// Step 02 - Click "Opening a new window: Click Here" link -> Switch qua tab mới
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		Thread.sleep(2000);
		switchToChildWindow(parentId);

		// Step 03 - Kiểm tra title của window mới = Google
		String googleTitle = driver.getTitle();
		System.out.println("Child Window Title: " + googleTitle);
		Assert.assertEquals(googleTitle, "Google");

		// Step 04 - Close window mới
		// Step 05 - Switch về parent window
		Assert.assertTrue(closeAllWithoutParentWindows(parentId));
		Thread.sleep(2000);

		// Step 06 - Kiểm tra đã quay về parent window thành công (title/ url)
		Assert.assertEquals(driver.getTitle(), parentTitle);

	}

	@Test
	public void TC_02_SwitchWindow() throws Exception {
		// Step 01 - Truy cập vào trang: http://www.hdfcbank.com/
		driver.get("http://www.hdfcbank.com/");
		String parentID = driver.getWindowHandle();

		// Step 02 - Kiểm tra và close quảng cáo nếu có xuất hiện
		List<WebElement> popupCloseBtn = driver.findElements(By.xpath("//img[@class='popupCloseButton']"));
		int popupCloseBtnSize = popupCloseBtn.size();
		if (popupCloseBtnSize > 0) {
			driver.findElement(By.xpath("//img[@class='popupCloseButton']")).click();
		}

		// Step 03 - Click Angri link -> Mở ra tab/window mới -> Switch qua tab mới
		driver.findElement(By.xpath("//a[text()='Agri']")).click();
		switchToWindowByTitle("HDFC Bank Kisan Dhan Vikas e-Kendra");
		Thread.sleep(2000);

		// Step 04 - Click Account Details link -> Mở ra tab/window mới -> Switch qua
		// tab mới
		driver.findElement(By.xpath("//p[text()='Account Details']")).click();
		switchToWindowByTitle("Welcome to HDFC Bank NetBanking");
		Thread.sleep(2000);

		// Step 05- Click Privacy Policy link (nằm trong frame) -> Mở ra tab/window mới
		// -> Switch qua tab mới
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='footer']")));
		driver.findElement(By.xpath("//a[text()='Privacy Policy']")).click();
		switchToWindowByTitle(
				"HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");

		// Step 06- Click CSR link on Privacy Policy page
		driver.findElement(By.xpath("//a[text()='CSR']")).click();

		// Step 07 - Close tất cả windows/ tabs khác - chỉ giữ lại parent window
		// (http://www.hdfcbank.com/)
		Assert.assertTrue(closeAllWithoutParentWindows(parentID));
	}

	
	public void TC_03() throws Exception {
		//Step 01 - Truy cập vào trang: http://live.guru99.com/index.php/

		driver.get("http://live.guru99.com/index.php/");
		String parentID = driver.getWindowHandle();
		//Step 02 - Click vào Mobile tab
		driver.findElement(By.xpath("//a[text()='Mobile']")).click();

		//Step 03 - Add sản phẩm Sony Xperia vào để Compare (Add to Compare)
		driver.findElement(By.xpath(
				"//a[text()='Sony Xperia']//parent::h2//following-sibling::div[@class='actions']//ul[@class='add-to-links']//a[text()='Add to Compare']"))
				.click();

		//Step 04 - Add sản phẩm Samsung Galaxy vào để Compare (Add to Compare)
		driver.findElement(By.xpath(
				"//a[text()='Samsung Galaxy']//parent::h2//following-sibling::div[@class='actions']//ul[@class='add-to-links']//a[text()='Add to Compare']"))
				.click();

		//Step 05 - Click to Compare button
		driver.findElement(By.xpath("//span[text()='Compare']")).click();

		//Step 06 - Switch qa cửa sổ mới (chứa 2 sản phẩm đã được Add vào để Compare)
		switchToWindowByTitle("Products Comparison List - Magento Commerce");
		Thread.sleep(2000);

//				Step 07 - Verify title của cửa sổ bằng: Products Comparison List - Magento Commerce

		Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");

//				Step 08 - Close tab và chuyển về Parent Window
		closeAllWithoutParentWindows(parentID);
		driver.navigate().back();
	}

	// Switch to child Windows (only 2 windows)
	public void switchToChildWindow(String parent) {
		// Lấy ra ID của tất cả các cửa sổ đang có
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng for-each duyệt qua từng cửa sổ và kiểm tra nếu ID của cửa sổ nào mà khác
		// ID của parent window thì switch qua cửa sổ đó
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parent)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	// Switch to child Windows (greater than 2 windows and title of the pages are
	// unique)
	public void switchToWindowByTitle(String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			System.out.println(currentWin);
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	// Close all windows without parent window
	public boolean closeAllWithoutParentWindows(String parentWindow) {
		// Lấy ra ID của tất cả cửa sổ đang có
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng for-each duyệt qua từng cửa sổ.
		for (String runWindows : allWindows) {
			// Kiểm tra cửa sổ nào có ID khác với parent ID thì switch qua và close.
			if (!runWindows.equals(parentWindow)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

}
