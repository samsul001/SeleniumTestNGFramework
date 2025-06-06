package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import factory.DriverFactory;
import utils.AssertUtils;
import utils.CommUtils;
import utils.ReadConfig;

public abstract class BaseTest {
	
	
	//protected Properties pro;
	
	//static ReadConfig readConfig = new ReadConfig();	
	public String baseUrl = ReadConfig.getUrl();
	public String userName = ReadConfig.getUsername();
	public String pwd = ReadConfig.getPassword();
	public String invalidUsername = ReadConfig.getInvalidUsername();
	public String invalidPassword = ReadConfig.getInvalidPassword();
	
	@Parameters("browser")
	@BeforeClass
	public void setup(@Optional("chrome") String browser) throws InterruptedException {
		WebDriver driver;
		driver = DriverFactory.initDriver(browser);
		driver.get(baseUrl);
		//Thread.sleep(8000);
		CommUtils.waitForPageLoad(20);
		AssertUtils.initSoftAssert();
	}
	
	@AfterClass
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}
