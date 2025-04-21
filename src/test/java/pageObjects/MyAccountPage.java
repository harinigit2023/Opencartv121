package pageObjects;

import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
		
	}
	
	@FindBy(xpath="//*[contains(text(),'My Account')]")
	WebElement msgHeading;
	@FindBy(xpath = "//a[@class='list-group-item' and contains(text(),'Logout')]")
	WebElement lnkLogout;
	
	public boolean isMyAccountPageExits() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.visibilityOf(msgHeading));
	        
	        
	        return msgHeading.isDisplayed();
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	public void clickLogout() 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.elementToBeClickable(lnkLogout));  // Ensure the logout is clickable
	    
	    // Scroll using Actions class
	    Actions actions = new Actions(driver);
	    actions.moveToElement(lnkLogout).perform();  // This will scroll the element into view
	    
	    // Now click the logout button
	    lnkLogout.click();
	}

}
