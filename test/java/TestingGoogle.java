import org.testng.annotations.Test;
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
	private String testingOnWebsite = "http://www.google.com";
	private String websiteName = "Google";
	private String searchQuery = "QA infotech";
	private String titleConstant = " - Google Search";
	private String expectedTopUrl = "https://qainfotech.com/";
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
			searchBar = driver.findElement(By.cssSelector("input[name=\"q\"]"));
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
//		String searchQuery = null;
		searchBar.sendKeys(searchQuery);
		// getting value of an html input element
//		searchQuery = searchBar.getAttribute("value");
		// matching, sended value to value right now in search bar
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(searchBar.getAttribute("value"), searchQuery);
	}

	
	@Test(dependsOnMethods = { "isSearchBarPresent" })
	public void isSearchButtonPresent() {
		boolean isSearchButtonPresent = false;
		searchButtons = new ArrayList<WebElement>();
		
		try {
			
			searchButtons
			.add(driver.findElement(By.cssSelector(".FPdoLc.VlcLAe input[type='submit'][name='btnK']")));
			searchButtons
			.add(driver.findElement(By.cssSelector(".aajZCb input[type='submit'][name='btnK']")));
//			System.out.println(searchButtons.size());
//			System.out.println(searchButtons);
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
//		catch(InterruptedException e) {
//			System.out.println(e.getMessage());
//		}
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
//			status = false;
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
	
	//*[@id="rso"]/div/div/div[1]/div/div/div[1]/a/div/cite
	
	@Test(dependsOnMethods = {"checkingTitleAfterSearch"})
//	this methods checks if the top url is what we expect or not
	public void checkingTopResultOfPage() {
		String topUrl = driver.findElement(By.xpath("//*[@id=\"rso\"]/div/div/div[1]/div/div/div[1]/a/div/cite")).getText();
		assertEquals(topUrl, expectedTopUrl);
	}

}
