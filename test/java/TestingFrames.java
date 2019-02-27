
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestingFrames {

	private WebDriver driver;
	private String testingOnWebsite = "http://10.0.14.57:9292/";
	private String path = "nested_frames";
	private WebElement frame;

	@BeforeClass
	public void setup() {
		// setting path of drivers
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		// opening browser
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(testingOnWebsite + path);
	}

	@AfterClass
	public void teardown() {
		// closing browser
		driver.close();
	}

	@BeforeMethod
	public void resetUrl() {
		driver.navigate().to(testingOnWebsite + path);
	}

	@DataProvider(name = "list")
	public Object[][] getDataFromDataprovider() {
		return new Object[][] { { 0, "frame-left", "LEFT" }, { 0, "frame-middle", "MIDDLE" },
				{ 0, "frame-right", "RIGHT" }, };

	}

	@Test(dataProvider = "list")
	public void testingFrames(int parentFrameIndex, String childFrameName, String expectedText) {
		driver.switchTo().frame(parentFrameIndex);
		driver.switchTo().frame(childFrameName);
		assertEquals(driver.findElement(By.cssSelector("body")).getText(), expectedText);
	}

	@Test
	public void testingFrames() {
		driver.switchTo().frame(1);
		assertEquals(driver.findElement(By.cssSelector("body")).getText(), "BOTTOM");
	}

}
