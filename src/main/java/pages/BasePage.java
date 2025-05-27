package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import factory.DriverFactory;

public class BasePage {
	protected WebDriver driver;

	public BasePage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
	}
	
	
}
