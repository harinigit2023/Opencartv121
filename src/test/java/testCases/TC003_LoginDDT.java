package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
     @Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="DataDriven")
	public void verify_LoginDDT(String email,String pwd,String exp) {
    	 logger.info("***** Starting DDT login test for:  *****");
    	 try {
    	 HomePage hp = new HomePage(driver);
         hp.ClickMyAccount();
         hp.ClickLogin();
         
         // Login page
         LoginPage lp = new LoginPage(driver);
         lp.setEmail(email);        
         lp.setPassword(pwd);       
         lp.clickLogin();
         
         // My Account page
         MyAccountPage macc = new MyAccountPage(driver);
         boolean targetPage = macc.isMyAccountPageExits();
         
         if (exp.equalsIgnoreCase("Valid")) {
             if (targetPage==true) {
                  
                Assert.assertTrue(true);
                macc.clickLogout();
             } else {
                 Assert.assertTrue(false);
             }
	}
         if (exp.equalsIgnoreCase("Invalid")) {
             if (targetPage==true) {
            	 Assert.assertTrue(false);
            	 macc.clickLogout();
            	
             } else 
             {
            	 
                 Assert.assertTrue(true);
             }
         }
    	 }catch (Exception e) {
             Assert.fail();
         }
         
         logger.info("***** Finished DDT login test for *****");
}
}