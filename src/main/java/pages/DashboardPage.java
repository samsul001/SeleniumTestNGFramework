package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage{
	
	@FindBy(xpath = "//h6[text()='Dashboard']")
	private WebElement dashboardTitle;
	
	
	//--------------------Actions methods-------------------------------------------------------------------
	//--------------------Author: Samsul Alam --------------------------------------------------------------
	
	public boolean isDashboardTitleDisplayed() {
		return dashboardTitle.isDisplayed();
	}

}
