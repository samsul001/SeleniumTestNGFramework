package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.DashboardPage;
import pages.LoginPage;

@Epic("Authentication")
@Feature("Login Feature")
public class ValidLogin extends BaseTest{
	
	private LoginPage loginPg;
	private DashboardPage dashboardPg;
	
	@Test(description = "Valid login with username and password")
	@Severity(SeverityLevel.CRITICAL)
	@Story("User logins with login credentials")
	@Description("Ensure login works with correct username and password")
	public void loginTest() {
		loginPg = new LoginPage();
//		System.out.println(">>> Inside loginTest <<<");
//		lp.enterUsername(userName);
//		lp.enterPwd(pwd);
//		lp.clickLoginBtn();
		
		dashboardPg = loginPg.login(userName, pwd);				
		Assert.assertTrue(dashboardPg.isDashboardTitleDisplayed(), "Dashboard page is not displayed after login");		
	}
	
	
}
