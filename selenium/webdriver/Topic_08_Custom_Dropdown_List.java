package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_Dropdown_List {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecute;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		jsExecute = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 15);
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	@Test
	public void TC_01_JQuery_01() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		
		selectItem("//span[@id='number-button']", "//ul[@id='number-menu']/li/div", "10");
		selectItem("//span[@id='number-button']", "//ul[@id='number-menu']/li/div", "19");
	}
	
	@Test
	public void TC_02_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		
		selectItem("//i[@class='dropdown icon']", "//div[@class='item']/span", "Elliot Fu");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Elliot Fu");
		
		
		selectItem("//i[@class='dropdown icon']", "//div[@class='item']/span", "Justen Kitsune");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Justen Kitsune");
		
		
		selectItem("//i[@class='dropdown icon']", "//div[@class='item']/span", "Christian");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Christian");
	}
	
	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		
		selectItem("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "First Option");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), "First Option");
		
		
		selectItem("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "Second Option");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), "Second Option");
		
		
		selectItem("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//a", "Third Option");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), "Third Option");
	}
	
	@Test
	public void TC_04_Default_Dropdown() {
		driver.get("https://demo.nopcommerce.com/register");
		
		selectItem("//select[@name='DateOfBirthDay']", "//select[@name='DateOfBirthDay']/option", "10");
		Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text()='10']")).isSelected());
		
		
		selectItem("//select[@name='DateOfBirthDay']", "//select[@name='DateOfBirthDay']/option", "30");
		Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text()='30']")).isSelected());
		
		
		selectItem("//select[@name='DateOfBirthDay']", "//select[@name='DateOfBirthDay']/option", "21");
		Assert.assertTrue(driver.findElement(By.xpath("//select[@name='DateOfBirthDay']/option[text()='21']")).isSelected());
	}
	
	@Test
	public void TC_05_Editable_Dropdown() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		
		selectItem("//input", "//div[@class='item']//span", "Aland Islands");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Aland Islands");
		
		
		selectItem("//input", "//div[@class='item']//span", "Benin");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Benin");
		
		
		selectItem("//input", "//div[@class='item']//span", "Angola");
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']")).getText(), "Angola");
	}

	
	public void selectItem (String parentLocator, String childLocator, String expectedItem) {
		// Click v??o Dropdown ????? x??? h???t t???t c??? Item ra
		driver.findElement(By.xpath(parentLocator)).click();
		sleepInsecond(1);
		
		// Ch??? cho t???t c??? Item b??n trong load h???t
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childLocator)));
		
		// L???y h???t t???t c??? Item ra, l??u v??o 1 List WebElement
		List<WebElement> allDropdownItems = driver.findElements(By.xpath(childLocator));
		
		// Duy???t qua t???t c??? Items b???n v??ng l???p FOR
		for (WebElement item : allDropdownItems) {
			String itemText = item.getText();
			System.out.println(itemText);
			
			// Duy???t qua t???ng gi?? tr???, getText() t???ng c??i, ?????n khi t???i gi?? tr??? mong mu???n th?? d???ng l???i
			if(itemText.equals(expectedItem)) {
				jsExecute.executeScript("arguments[0].scrollIntoView(true)", item);
				sleepInsecond(1);
				
				item.click();
				sleepInsecond(1);
				break;
			}
		}
		
	}
	
	public void enterItem (String editableLocator, String childLocator, String expectedItem) {
		// Click v??o Dropdown ????? x??? h???t t???t c??? Item ra
		driver.findElement(By.xpath(editableLocator)).sendKeys(expectedItem);
		sleepInsecond(1);
				
		// Ch??? cho t???t c??? Item b??n trong load h???t
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childLocator)));
				
		// L???y h???t t???t c??? Item ra, l??u v??o 1 List WebElement
		List<WebElement> allDropdownItems = driver.findElements(By.xpath(childLocator));
				
		// Duy???t qua t???t c??? Items b???n v??ng l???p FOR
		for (WebElement item : allDropdownItems) {
			String itemText = item.getText();
			System.out.println(itemText);
			
			// Duy???t qua t???ng gi?? tr???, getText() t???ng c??i, ?????n khi t???i gi?? tr??? mong mu???n th?? d???ng l???i
			if(itemText.equals(expectedItem)) {
				jsExecute.executeScript("arguments[0].scrollIntoView(true)", item);
				sleepInsecond(1);
				
				item.click();
				sleepInsecond(1);
			break;
			}
		}
				
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public int RandomEmail() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}

	public void sleepInsecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}