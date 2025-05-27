package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import factory.DriverFactory;
import utils.CommUtils;
import utils.ReadConfig;

public abstract class BaseTest {
	
	
	//protected Properties pro;
	
	ReadConfig readConfig = new ReadConfig();	
	public String baseUrl = readConfig.getUrl();
	public String userName = readConfig.getUsername();
	public String pwd = readConfig.getPassword();
	public String invalidUsername = readConfig.getInvalidUsername();
	public String invalidPassword = readConfig.getInvalidPassword();
	
	@Parameters("browser")
	@BeforeClass
	public void setup(@Optional("chrome") String browser) throws InterruptedException {
		WebDriver driver;
		driver = DriverFactory.initDriver(browser);
		driver.get(baseUrl);
		//Thread.sleep(8000);
		CommUtils.waitForPageLoad(20);
	}
	
	@AfterClass
	public void tearDown() {
		DriverFactory.quitDriver();
	}

}
