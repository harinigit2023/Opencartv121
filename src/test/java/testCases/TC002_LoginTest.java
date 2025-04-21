package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass
{
	@Test(groups={"sanity","Master"})
	public void  verify_login() 
	{
		logger.info("***** TC002_LoginTest  is started *****");
		try {
		//home page
		HomePage hp= new HomePage(driver);
		hp.ClickMyAccount();
		hp.ClickLogin();
		
		//login page
		LoginPage lp=new LoginPage(driver);
//		lp.setEmail(p.getProperty("email"));
//		lp.setPassword(p.getProperty("password"));
//		lp.clickLogin();
		
		  // Log the generated email and password
		loadCredentialsFromFile();
        logger.info("Using Email: " + BaseClass.generatedEmail);
        logger.info("Using Password: " + BaseClass.generatedPassword);

        // Use the generated email and password for login
        lp.setEmail(BaseClass.generatedEmail);
        lp.setPassword(BaseClass.generatedPassword);
        lp.clickLogin();
		
		//my account
		MyAccountPage macc=new MyAccountPage(driver);
		boolean targetPage=macc.isMyAccountPageExits();
		
		Assert.assertTrue(targetPage);//Assert.assertEquals(targetPage, true,"Login Failed");
		}catch(Exception e) {
			Assert.fail();
		}
		
		logger.info("***** TC002_LoginTest  is finished *****");
		
	}
	

}
