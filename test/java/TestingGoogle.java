import org.testng.annotations.Test;

import com.webapp.automation.HandlePropFile;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestingGoogle {

	private WebDriver driver;
	private String testingOnWebsite = HandlePropFile.getProperty("testingOnWebsite");
	private String websiteName = HandlePropFile.getProperty("websiteName");
	private String searchQuery = HandlePropFile.getProperty("searchQuery");
	private String titleConstant = HandlePropFile.getProperty("titleConstant");
	private String expectedTopUrl = HandlePropFile.getProperty("expectedTopUrl");
	private String searchBarCssSelector = HandlePropFile.getProperty("searchBarCssSelector");
	private String primarySearchButton = HandlePropFile.getProperty("primarySearchButton");
	private String secondarySearchButton = HandlePropFile.getProperty("secondarySearchButton");
	private String topUrlXpath = HandlePropFile.getProperty("topUrlXpath");
	private WebElement searchBar;
	private ArrayList<WebElement> searchButtons;

	
	@BeforeClass
	public void setup() {
		// setting path of drivers
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		// opening browser
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(testingOnWebsite);
	}

	
	@AfterClass
	public void teardown() {
		// closing browser
		driver.close();
	}

	
	@Test
	public void testingTitle() {
		String actualTitle = driver.getTitle();
//		matching the title of the page with expected title
		assertEquals(actualTitle, websiteName);
	}

	
//	@Test
//	without dependsOnMethods annotation we would need to driver.get in this method also
	@Test(dependsOnMethods = { "testingTitle" })
	public void isSearchBarPresent() {
		boolean isSearchBarPresent = false;
		try {
			searchBar = driver.findElement(By.cssSelector(searchBarCssSelector));
			isSearchBarPresent = true;
		} catch (NoSuchElementException e) {
			isSearchBarPresent = false;
//				either search bar did not loaded within the given amount of time
//				or it is not there in page
		}
		assertEquals(isSearchBarPresent, true);
	}

	
	@Test(dependsOnMethods = { "isSearchButtonPresent" })
	public void isSearchBoxTypeable() {
		searchBar.sendKeys(searchQuery);
		// getting value of an html input element
		// matching, sended value to value right now in search bar
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(searchBar.getAttribute("value"), searchQuery);
	}

	
	@Test(dependsOnMethods = { "isSearchBarPresent" })
	public void isSearchButtonPresent() {
		boolean isSearchButtonPresent = false;
		searchButtons = new ArrayList<WebElement>();
	
		try {
			System.out.println(secondarySearchButton);
			searchButtons
			.add(driver.findElement(By.cssSelector(primarySearchButton)));
			searchButtons
			.add(driver.findElement(By.cssSelector(secondarySearchButton)));
			isSearchButtonPresent = true;
		} catch (NoSuchElementException e) {
			System.out.println("Search Button Not found");
			System.out.println(e.getMessage());
//			either search button did not loaded within the given amount of time
//			or it is not there in page
		} catch (ElementNotInteractableException e) {
			System.out.println("Search Button Found but in a state that user can not Interact with");
		} catch(IllegalMonitorStateException e) {
			System.out.println(e.getMessage());
		}
		assertEquals(isSearchButtonPresent, true);
	}

	
	@Test(dependsOnMethods = { "isSearchBoxTypeable" })
	public void isSearchButton1NotClickable() {
		boolean status = false;
		try {
				searchButtons.get(0).click();
				
		} catch (Exception e) {
			status = true;
			System.out.println(e.getMessage());
		}
		assertEquals(status, true);
	}

	
	@Test(dependsOnMethods = { "isSearchButton1NotClickable" })
	public void isSearchButton2Clickable() {
		boolean status = false;
		try {
			// checking if element is visible and clickable
			if (ExpectedConditions.elementToBeClickable(searchButtons.get(0)) != null) {
				searchButtons.get(1).click();
				status = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		assertEquals(status, true);
	}
	
	@Test(dependsOnMethods = { "isSearchButton2Clickable" })
	public void checkingTitleAfterSearch() {
		String actualTitle = driver.getTitle();
//		matching the title of the page with expected title
		assertEquals(actualTitle, searchQuery+titleConstant);
	}
	
	@Test(dependsOnMethods = {"checkingTitleAfterSearch"}, alwaysRun=true)
//	this methods checks if the top url is what we expect or not
	public void checkingTopResultOfPage() {
		String topUrl = driver.findElement(By.xpath(topUrlXpath)).getText();
		assertEquals(topUrl, expectedTopUrl);
	}

}
