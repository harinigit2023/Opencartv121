package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationtest extends BaseClass {
		@Test	(groups={"Regression","Master"})
		public void verify_account_registartion()  	
		{
			logger.info("****starting TC001_AccountRegistrationtest ***** ");
		
	try {
    HomePage hp = new HomePage(driver);
    hp.ClickMyAccount();
    logger.info("Clicked on MyAccount Link");
    hp.ClickRegister();
    logger.info("Clicked on Register Link");

    AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
    logger.info("Providing customer details");

    generateAndStoreCredentials(); // Generate & store email/pass

    regpage.setFirstName(randomString().toUpperCase());
    regpage.setLastName(randomString().toUpperCase());
    regpage.setEmail(generatedEmail);           // using saved email
    regpage.setPassword(generatedPassword);     // using saved password

    regpage.setNewsletterSubscription();
    regpage.setPrivacyPolicy();
    regpage.clickContinue();

    logger.info("Validating expected message...");
    String confmsg = regpage.getConfirmationMsg();

    Assert.assertEquals(confmsg, "Your Account Has Been Created!");
} catch (Exception e) {
    logger.error("Registration test failed", e);
    Assert.fail();
}

logger.info("**** Finished TC001_AccountRegistrationtest *****");
}
	
	

}
