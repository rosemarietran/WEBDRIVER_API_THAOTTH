package selenium;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class Topic_11_UploadFile {
	WebDriver driver;
	JavascriptExecutor jsExcutor;
	String rootFolder = System.getProperty("user.home");

	String fileName01 = "image01.png";
	String fileName02 = "image01.png";
	String fileName03 = "image01.png";

	String filePath01 = rootFolder + "\\uploadFiles\\" + fileName01;
	String filePath02 = rootFolder + "\\uploadFiles\\" + fileName02;
	String filePath03 = rootFolder + "\\uploadFiles\\" + fileName03;

	String firefoxPath = rootFolder + "\\uploadFiles\\" + "firefox.exe";
	String chromePath = rootFolder + "\\uploadFiles\\" + "chrome.exe";
	String iePath = rootFolder + "\\uploadFiles\\" + "ie.exe";

	@BeforeTest
	public void beforeTest() {
//		System.setProperty("webdriver.gecko.driver",
//				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");

// 		driver = new FirefoxDriver();

		// Chrome driver
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// IE
//	    System.setProperty("webdriver.ie.driver",".\\lib\\IEDriverServer.exe");
//	    WebDriver driver = new InternetExplorerDriver();

		System.out.println("Driver name = " + driver.toString());
	}

	public void TC_01_Upload_MultipleFiles_BySendkeys_Queue() {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		String[] uploadFilePaths = { filePath01, filePath02, filePath03 };
		for (String file : uploadFilePaths) {
			WebElement addFileInput = driver.findElement(By.xpath("//input[@type='file']"));
			addFileInput.sendKeys(file);
		}

		WebElement startUploadBtn = driver.findElement(By.xpath(""));
		startUploadBtn.click();

		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName01 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName02 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName03 + "']")).isDisplayed());
	}

	public void TC_02_Upload_MultipleFiles_A_Time() {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
		uploadFile.sendKeys(filePath01 + "\n" + filePath02 + "\n" + filePath03);
		List<WebElement> startButtons = driver.findElements(By.cssSelector("td>button[class*='btn-primary']"));
		for (WebElement startButton : startButtons) {
			if (driver.toString().contains("internet  explorer")) {
				clickToElementByJS(startButton);
				System.out.println("Click by  JS!");
			} else {
				startButton.click();
				System.out.println("Click by  Selenium Built-in!");
			}
		}
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName01 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName02 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName03 + "']")).isDisplayed());
	}

	public void TC_03_Upload_By_AutoIT() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		Thread.sleep(3000);
		if (driver.toString().contains("firefox")) {
			System.out.println("Go to Firefox");
			WebElement uploadFile = driver.findElement(By.cssSelector(".fileinput-button"));
			uploadFile.click();
			Thread.sleep(1000);
			Runtime.getRuntime().exec(new String[] { firefoxPath, filePath01 });
		} else if (driver.toString().contains("chrome")) {
			System.out.println("Go to Chrome");
			WebElement uploadFile = driver.findElement(By.cssSelector(".fileinput-button"));
			uploadFile.click();
			Thread.sleep(1000);
			Runtime.getRuntime().exec(new String[] { chromePath, filePath01 });
		} else {
			WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
			clickToElementByJS(uploadFile);
			Thread.sleep(1000);
			Runtime.getRuntime().exec(new String[] { iePath, filePath01 });
		}
		Thread.sleep(4000);
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName01 + "']")).isDisplayed());
	}

	public void TC_04_Upload_By_Robot() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		// Specify the file location with extension
		StringSelection select = new StringSelection(filePath01);

		// Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);

		if (driver.toString().contains("chrome") || driver.toString().contains("firefox")) {
			WebElement uploadFile = driver.findElement(By.cssSelector(".fileinput-button"));
			uploadFile.click();
			Thread.sleep(1000);
		} else {
			System.out.println("Go to IE");
			WebElement uploadFile = driver.findElement(By.xpath("//input[@type='file']"));
			clickToElementByJS(uploadFile);
			Thread.sleep(1000);
		}

		Robot robot = new Robot();
		Thread.sleep(1000);

		// Nhan phim Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		// Nhan xuong Ctrl - V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		// Nha Ctrl - V
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);

		// Nhan Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(3000);
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + fileName01 + "']")).isDisplayed());
	}

	public void TC_05_Upload_File() throws Exception {
		driver.get("https://encodable.com/uploaddemo/");
		// Step 02 - Choose Files to Upload (Ex: UploadFile.jpg)
		WebElement chooseFileBtn = driver.findElement(By.xpath("//input[@id='uploadname1']"));
		chooseFileBtn.sendKeys(filePath01);

		// Step 03 - Select dropdown (Upload to: /uploaddemo/files/)
		Select uploadToDropdown = new Select(driver.findElement(By.xpath("//select[@name='subdir1']")));
		uploadToDropdown.selectByVisibleText("/uploaddemo/files/");

		// Step 04 - Input random folder to 'New subfolder? Name:) textbox (Ex:
		// dam1254353)
		driver.findElement(By.xpath("//input[@id='newsubdir1']")).sendKeys("thao" + randomNumber());

		// Step 05 - Input email and firstname (dam@gmail.com/ DAM DAO)
		driver.findElement(By.xpath("//input[@name='email_address']")).sendKeys("thao@gmail.com");
		driver.findElement(By.xpath("//input[@name='first_name']")).sendKeys("thao");

		// Step 06 - Click Begin Upload (Note: Wait for page load successfully)
		driver.findElement(By.xpath("//input[@id='uploadbutton']")).click();
		Thread.sleep(5000);

		// Step 07: Verify information
		Assert.assertTrue(driver.findElement(By.xpath("//dl/dd[contains(text(),'Email Address: thao@gmail.com')]"))
				.isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//dl/dd[contains(text(),'First Name: thao')]")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//dl/dt[contains(text(),'File 1 of 1:')]/a")).getText(),
				"image01.png");

		// Step 08 - Click 'View Uploaded Files' link
		driver.findElement(By.xpath("//a[text()='View Uploaded Files']")).click();

		// Step 09 - Click to random folder (Ex: dam1254353)
		driver.findElement(By.xpath("//table//a[contains(text(),'thao')]")).click();

		// Step 10 - Verify file name exist in folder (UploadFile.jpg)

		Assert.assertTrue(driver.findElement(By.xpath("//td[@class='fname thumb']/a[contains(text(),'image01.png')]"))
				.isDisplayed());
	}

	public int randomNumber() {
		int random = (int) (Math.random() * 50 + 1);
		return random;
	}

	public Object clickToElementByJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
