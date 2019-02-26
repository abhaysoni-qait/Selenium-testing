import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

public class Selenium {
	
	WebDriver driver = null;
	
  @Test
  public void testSearchBar() {
	  driver.get("https://www.google.com");
  }
  @BeforeClass
  public void beforeClass() {
	  driver = new ChromeDriver();
  }

  @AfterClass
  public void afterClass() {
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("Starting New test");
  }

}
