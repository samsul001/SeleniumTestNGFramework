package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class LoginPage extends BasePage{
	
	@FindBy(xpath = "//input[@name='username']")
	private WebElement txtUsername;
	
	@FindBy(xpath = "//input[@name='password']")
	private WebElement txtPassword;
	
	@FindBy(xpath = "//button[@type='submit']")
	private WebElement loginBtn;
	
	By loginBtnBy = By.xpath("//button[@type='submit']");
	
	//----------------------Action Methods----------------------------------------
	
	public void enterUsername(String userName) {
		txtUsername.clear();
		txtUsername.sendKeys(userName);
	}
	
	public void enterPwd(String pwd) {
		txtPassword.clear();
		txtPassword.sendKeys(pwd);
	}
	
	public void clickLoginBtn() {
		loginBtn.click();
	}
	
	public DashboardPage login(String username, String password) {
		txtUsername.clear();
		txtUsername.sendKeys(username);
		txtPassword.clear();
		txtPassword.sendKeys(password);
		loginBtn.click();
		return new DashboardPage();
	}

}
