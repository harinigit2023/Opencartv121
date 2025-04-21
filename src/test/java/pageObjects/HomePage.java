package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage{
	
	WebDriver driver;
	
	public HomePage(WebDriver driver) 
	{
		super(driver);
	}
@FindBy(xpath="//span[normalize-space()='My Account']")
WebElement lnkmyaccount;

@FindBy(xpath="//a[normalize-space()='Register']") //a[normalize-space()='Register']"
WebElement lnkRegister;

@FindBy(linkText="Login")
WebElement linkLogin;




public void ClickMyAccount()
{
	lnkmyaccount.click();
}

public void ClickRegister() {
	
	lnkRegister.click();
}
public void  ClickLogin()
{
	linkLogin.click();
	
}


}
