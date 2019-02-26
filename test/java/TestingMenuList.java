
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestingMenuList {
	private WebDriver driver;
	private int totalMenuItem = 39;
	private String testingOnWebsite = "http://10.0.14.57:9292/";
	private ArrayList<WebElement> menuList;

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

	@DataProvider(name = "itemList")
	public Object[][] getDataFromDataprovider() {
		
		String itemList[] = { "A/B Testing", "Basic Auth", "Broken Images", "Challenging DOM", "Checkboxes",
				"Context Menu", "Disappearing Elements", "Drag and Drop", "Dropdown", "Dynamic Content",
				"Dynamic Controls", "Dynamic Loading", "Exit Intent", "File Download", "File Upload", "Floating Menu",
				"Forgot Password", "Form Authentication", "Frames", "Geolocation", "Horizontal Slider", "Hovers",
				"Infinite Scroll", "JQuery UI Menus", "JavaScript Alerts", "JavaScript onload event error",
				"Key Presses", "Large & Deep DOM", "Multiple Windows", "Nested Frames", "Notification Messages",
				"Redirect Link", "Secure File Download", "Shifting Content", "Slow Resources", "Sortable Data Tables",
				"Status Codes", "Typos", "WYSIWYG Editor" };

		return new Object[][][] { { itemList } };

	}

	@Test
	public void checkTotalMenuItem() {
		menuList = (ArrayList<WebElement>) driver.findElements(By.cssSelector("#content > ul > li"));
		assertEquals(menuList.size(), totalMenuItem);
	}
	

	@Test(dependsOnMethods = { "checkTotalMenuItem" }, dataProvider = "itemList")
	public void verifyItemList(String itemList[]) {
		boolean status = true;
		menuList = 
		(ArrayList<WebElement>) driver.findElements(By.cssSelector("#content > ul > li > a"));
		
		for (int i=0; i<totalMenuItem; i++) {
			if(!itemList[i].equalsIgnoreCase(menuList.get(i).getText())) {
				status = false;
			}
		}
		
		assertEquals(status, true);
	}

}
