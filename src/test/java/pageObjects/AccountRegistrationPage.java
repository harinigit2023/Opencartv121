package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage {
	//WebDriver driver;
	
	
	public AccountRegistrationPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtFirstname;
	
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastname;
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@id='input-newsletter']")
	WebElement nlCheckbox;
	
	@FindBy(xpath="//input[@name='agree']")
	WebElement chkdPolicy;
	
	@FindBy(xpath="//button[normalize-space()='Continue']")
	public
	WebElement btnContinue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	public void setFirstName(String fname) {
		txtFirstname.sendKeys(fname);
		
	}
	public void setLastName(String lname) {
		txtLastname.sendKeys(lname);
		
	}
	
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
		
	}
	
	
	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
		
	}
	public void setNewsletterSubscription() {
		try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        
	        // Wait until it's present and visible
	        wait.until(ExpectedConditions.visibilityOf(nlCheckbox));
	        wait.until(ExpectedConditions.elementToBeClickable(nlCheckbox));
	        
	        // Scroll into view
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nlCheckbox);

	        // Small wait after scroll (optional but helpful)
	        Thread.sleep(500);

	        // Try clicking via JavaScript
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nlCheckbox);

	    } catch (Exception e) {
	        System.out.println("Newsletter checkbox could not be clicked: " + e.getMessage());
	    }
	}
	
	
	public void setPrivacyPolicy() {
		chkdPolicy.click();
	
	}
	public void clickContinue() {
		btnContinue.click();
	}
	

	public String getConfirmationMsg() {
		try {
			return(msgConfirmation.getText());
		}catch(Exception e) {
			return(e.getMessage());
		}
	}
	

}
