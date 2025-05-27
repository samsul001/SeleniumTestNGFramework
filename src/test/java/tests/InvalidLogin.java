package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import pages.DashboardPage;
import pages.LoginPage;

public class InvalidLogin extends BaseTest{
	
	private LoginPage loginPg;
	private DashboardPage dashboardPg;
	
	@Test(description = "Failure login with invalid username and password")
	@Severity(SeverityLevel.CRITICAL)
	@Story("User logins with invalid login credentials")
	@Description("Ensure login is not working with incorrect username or password")
	public void loginTestFailure() {
		dashboardPg = loginPg.login(invalidUsername, invalidPassword);
		Assert.assertTrue(!dashboardPg.isDashboardTitleDisplayed(),"Login failing, So dashboard is not naviagated");
	}

}
