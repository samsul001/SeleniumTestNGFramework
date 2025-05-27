package factory;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class DriverFactory {
	
	private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public static WebDriver initDriver(String browser) {
		WebDriver driver;
		
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else {
			throw new RuntimeException("Unsupported browser: "+browser);
		}
		
		tlDriver.set(driver);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return getDriver();
	}
	
	public static void quitDriver() {
		getDriver().quit();
		tlDriver.remove();
	}

	

}
