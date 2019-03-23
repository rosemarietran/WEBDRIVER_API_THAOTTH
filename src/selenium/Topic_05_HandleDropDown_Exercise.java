package selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Topic_05_HandleDropDown_Exercise {
	WebDriver driver;
	WebDriverWait waitExplicit;
	JavascriptExecutor jsExecutor;

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver",
				"D:\\Data_THAO\\AUTOMATION_TEST\\PROJECTS\\01_WebDriverAPI\\geckodriver.exe");
		driver = new FirefoxDriver();
		waitExplicit = new WebDriverWait(driver, 30);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void TC_01_DefaultDropdown() {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		WebElement jobRoleDropdown = driver.findElement(By.xpath("//select[@id='job1']"));
		Select jobRoleSelect = new Select(jobRoleDropdown);
		// Get option da chon trong dropdown
		// jobRoleSelect.getFirstSelectedOption().getText();
		// Get so luong option trong dropdown
		Assert.assertEquals(jobRoleSelect.getOptions().size(), 5);

		// Kiem tra dropdown co ho tro multiple select khong
		Assert.assertTrue(!jobRoleSelect.isMultiple());

		// Chọn giá trị Automation Tester
		jobRoleSelect.selectByVisibleText("Automation Tester");
		Assert.assertEquals(jobRoleSelect.getFirstSelectedOption().getText(), "Automation Tester");

		// Chọn giá trị Manual Tester
		jobRoleSelect.selectByValue("manual");
		Assert.assertEquals(jobRoleSelect.getFirstSelectedOption().getText(), "Manual Tester");

		// Chọn giá trị Mobile Tester
		jobRoleSelect.selectByIndex(3);
		Assert.assertEquals(jobRoleSelect.getFirstSelectedOption().getText(), "Mobile Tester");

		// Kiểm tra dropdown có đủ 5 giá trị
		Assert.assertEquals(jobRoleSelect.getOptions().size(), 5);
	}

	public void TC_02_CustomJQueryDropdown() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		String parentXpath = "//span[@id='number-button']";
		String allItemsXpath = "//ul[@id='number-menu']//li[@class='ui-menu-item']/div";

		selectItemCustomDropdown(parentXpath, allItemsXpath, "3");
		Assert.assertTrue(
				isElementDisplayed("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text()='3']"));

		selectItemCustomDropdown(parentXpath, allItemsXpath, "1");
		Assert.assertTrue(
				isElementDisplayed("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text()='1']"));

		selectItemCustomDropdown(parentXpath, allItemsXpath, "13");
		Assert.assertTrue(
				isElementDisplayed("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text()='13']"));

		selectItemCustomDropdown(parentXpath, allItemsXpath, "2");
		Assert.assertTrue(
				isElementDisplayed("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text()='2']"));
	}

	public void TC_03_CustomAngularDropdown() throws Exception {
		driver.get("https://material.angular.io/components/select/examples");
		selectItemCustomDropdown("//mat-select[@placeholder='State']", "//mat-option/span", "Michigan");
		Assert.assertTrue(isElementDisplayed("//mat-select[@placeholder='State']//span[text()='Michigan']"));

		selectItemCustomDropdown("//mat-select[@placeholder='State']", "//mat-option/span", "Alaska");
		Assert.assertTrue(isElementDisplayed("//mat-select[@placeholder='State']//span[text()='Alaska']"));

		selectItemCustomDropdown("//mat-select[@placeholder='State']", "//mat-option/span", "Wyoming");
		Assert.assertTrue(isElementDisplayed("//mat-select[@placeholder='State']//span[text()='Wyoming']"));

		selectItemCustomDropdown("//mat-select[@placeholder='State']", "//mat-option/span", "New York");
		Assert.assertTrue(isElementDisplayed("//mat-select[@placeholder='State']//span[text()='New York']"));
	}

	public void TC_04_Telerik() throws Exception {
		driver.get("https://demos.telerik.com/kendo-ui/dropdownlist/index");
		selectItemCustomDropdown("//span[@aria-owns='color_listbox']", "//ul[@id='color_listbox']/li", "Orange");
		Assert.assertTrue(isElementDisplayed("//span[@aria-owns='color_listbox']//span[text()='Orange']"));

		selectItemCustomDropdown("//span[@aria-owns='color_listbox']", "//ul[@id='color_listbox']/li", "Grey");
		Assert.assertTrue(isElementDisplayed("//span[@aria-owns='color_listbox']//span[text()='Grey']"));
	}

	public void TC_05_VueJs() throws Exception {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "Third Option");
		Assert.assertTrue(isElementDisplayed("//li[@class='dropdown-toggle' and contains(text(),'Third Option')]"));

		selectItemCustomDropdown("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "Second Option");
		Assert.assertTrue(isElementDisplayed("//li[@class='dropdown-toggle' and contains(text(),'Second Option')]"));
	}

	public void TC_06_CustomMultipleItem() throws Exception {
		driver.get("http://multiple-select.wenzhixin.net.cn/examples/#basic.html");
		By contentIframeXpath = By.xpath("//div[@class='content']//iframe");

		String[] items = { "January", "April", "July" };
		String[] newItems = { "January", "April", "July", "October", "November" };

		String itemSelectedXpath = "//li[@class='selected']//input";

		WebElement contentIframe = driver.findElement(contentIframeXpath);
		driver.switchTo().frame(contentIframe);

		selectMultipleItemCustomInDropdown("//button[@class='ms-choice']", "//div[@class='ms-drop bottom']//span",
				items, itemSelectedXpath);
		Assert.assertTrue(checkItemSelected(items));

		driver.navigate().refresh();
		WebElement contentIframeRefresh = driver.findElement(contentIframeXpath);
		driver.switchTo().frame(contentIframeRefresh);

		selectMultipleItemCustomInDropdown("//button[@class='ms-choice']", "//div[@class='ms-drop bottom']//span",
				newItems, itemSelectedXpath);
		Assert.assertTrue(checkItemSelected(newItems));
	}

	@Test
	public void TC_07_SemanticUI() throws Exception {
		driver.get("https://semantic-ui.com/modules/dropdown.html#/definition");
		driver.findElement(By.xpath("//a[text()='Examples']")).click();

		String selectionParentDropdownXpath = "//div[@class='ui fluid normal dropdown selection multiple']";
		String allItemsInSelectionDropdownXpath = "//div[@class='menu transition visible']//div[@class='item']";
		String[] items1 = { "Graphic Design", "Javascript"};

		String searchSelectionParentDropdownXpath = "//div[@class='ui fluid multiple search normal selection dropdown']";
		String allItemsInSearchSelectionXpath = "//div[@class='menu transition visible']//div[@class='item']";
		String allItemsFilteredInSearchSelectionXpath = "//div[@class='menu transition visible']//div[text()='";
		String[] items2 = { "Antigua", "Benin" };
		String itemSelectedXpath1 = "//div[@class='ui fluid normal dropdown selection multiple']//a[@class='ui label transition visible']";
		String itemSelectedXpath2 = "//div[@class='ui fluid multiple search normal selection dropdown active visible']//a[@class='ui label transition visible']";

		selectMultipleItemCustomInDropdown(selectionParentDropdownXpath, allItemsInSelectionDropdownXpath, items1,
				itemSelectedXpath1);
//		driver.findElement(By.xpath(
//				"//div[@class='ui fluid multiple search normal selection dropdown']//input[@class='search']"))
//				.sendKeys("Vietnam");
		selectItemWithEditableDropdown(
				"//div[@class='ui fluid multiple search normal selection dropdown']//input[@class='search']",
				itemSelectedXpath2, allItemsFilteredInSearchSelectionXpath, "Vietnam");
		selectMultipleItemCustomInDropdown(searchSelectionParentDropdownXpath, allItemsInSearchSelectionXpath, items2,
				itemSelectedXpath2);
	}

	public void selectItemWithEditableDropdown(String parentXpath, String selectedItemsXpath,
			String itemFilteredInSearchSelectionXpath, String expectedItemValue) {
		// Scroll tới dropdown và click
		WebElement parentDropdown = driver.findElement(By.xpath(parentXpath));
		jsExecutor.executeScript("arguments[0].click()", parentDropdown);

		// Nhập text cần tìm kiếm vào textbox của dropdown
		parentDropdown.sendKeys(expectedItemValue);

		String expectedItemXpath = itemFilteredInSearchSelectionXpath + expectedItemValue + "']";

		// Chờ giá trị thỏa mãn text nhập vào được load ra thành công
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(expectedItemXpath)));

		// Click chọn item mong muốn
		WebElement expectedItem = driver.findElement(By.xpath(expectedItemXpath));
		if (expectedItem.isDisplayed()) {
			expectedItem.click();
		} else {
			System.out.println("Không tồn tại giá trị mong muốn trong dropdown!");
		}

		// Kiểm tra item đã được chọn chưa
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.xpath("//div[@class='ui sub header' and text() = 'Search Selection']")));
		WebElement selectedItem = driver.findElement(By.xpath(
				"//div[@class='ui sub header' and text() = 'Search Selection']/following-sibling::div/child::a"));
		System.out.println(expectedItemValue);
		System.out.println(selectedItem.getText());
		if (selectedItem.isDisplayed() && expectedItemValue.equals(selectedItem.getText())) {
			System.out.println("Chọn giá trị cần chọn thành công!");
		} else {
			System.out.println("Chưa chọn được giá trị cần chọn");
		}
	}

	public void selectItemCustomDropdown(String parentXpath, String allItemsXpath, String expectedItemValue)
			throws Exception {
		// Step1: Click vao dropdown de xo het gia tri ra
		WebElement parentDropdown = driver.findElement(By.xpath(parentXpath));
		jsExecutor.executeScript("arguments[0].click()", parentDropdown);
		// parentDropdown.click();

		// Step2: Cho tat ca cac gia tri trong dropdown duoc load ra thanh cong
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsXpath)));

		List<WebElement> allItems = driver.findElements(By.xpath(allItemsXpath));
		System.out.println("Tong so phan tu trong list dropdown la: " + allItems.size());

		// Duyet list all items cho den khi thoa man dieu kien
		for (WebElement childElement : allItems) {
			System.out.println("Text moi lan get = " + childElement.getText());
			if (expectedItemValue.equals(childElement.getText())) {
				System.out.println("Gia tri can chon = " + expectedItemValue);
				// Step3: Scroll den item can chon va click
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
				Thread.sleep(1500);

				// Click vao item can chon
				if (childElement.isDisplayed()) {
					childElement.click();
				} else {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
				}
				System.out.println("Chon gia tri can chon thanh cong!");
				Thread.sleep(1500);
				break;

			}
		}

	}

	public void selectMultipleItemCustomInDropdown(String parentXpath, String allItemsXpath, String[] expectedItemValue,
			String itemSelectedXpath) throws Exception {
		// Click vao dropdown de xo het gia tri ra
//		WebElement parentDropdown = driver.findElement(By.xpath(parentXpath));
//		jsExecutor.executeScript("arguments[0].click();", parentDropdown);
//
//		// Cho tat ca cac gia tri trong dropdown duoc load ra het
//		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsXpath)));

		List<WebElement> allItems = driver.findElements(By.xpath(allItemsXpath));

		// Duyet het cac phan tu den khi thoa man dieu kien
		for (WebElement childElement : allItems) {
			for (String item : expectedItemValue) {
				if (item.equals(childElement.getText())) {
					// scroll den item can chon
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
					Thread.sleep(1500);

					// click vao item can chon
					jsExecutor.executeScript("arguments[0].click();", childElement);
					Thread.sleep(1500);

					List<WebElement> itemSelected = driver.findElements(By.xpath(itemSelectedXpath));
					System.out.println("Total selected items = " + itemSelected.size());

					if (expectedItemValue.length == itemSelected.size()) {
						break;
					}
				}
			}
		}
	}

	public boolean checkItemSelected(String[] itemSelectedText) {
		List<WebElement> selectedItems = driver.findElements(By.xpath("//li[@class='selected']//input"));
		int selectedItemsNumber = selectedItems.size();

		String allItemsSelectedText = driver.findElement(By.xpath("//button[@class='ms-choice']/span")).getText();
		System.out.println("Text da chon: " + allItemsSelectedText);

		if (selectedItemsNumber <= 3 && selectedItemsNumber > 0) {
			for (String item : itemSelectedText) {
				if (allItemsSelectedText.contains(item)) {
					break;
				}
			}
			return true;
		} else {
			return driver
					.findElement(By.xpath(
							"//button[@class='ms-choice']/span[text()='" + selectedItemsNumber + " of 12 selected']"))
					.isDisplayed();
		}
	}

	public boolean isElementDisplayed(String xpathValue) {
		WebElement e = driver.findElement(By.xpath(xpathValue));
		if (e.isDisplayed()) {
			System.out.println("Element [" + xpathValue + "] is displayed!");
			return true;
		} else
			return false;
	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}
}
