package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
public class BaseClass {
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	public static String generatedEmail;
    public static String generatedPassword;
	
	@BeforeClass(groups={"sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException 
	{
		//loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities  capabilities=new DesiredCapabilities();
			///os
			if(os.equalsIgnoreCase("windows")){
			capabilities.setPlatform(Platform.WIN11);
			}
			else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}
			else if (os.equalsIgnoreCase("Linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("No matching os");
				return;
			}
			//browser
			
			switch(br.toLowerCase()) 
			{
			case "chrome":capabilities.setBrowserName("chrome");break;
			case "edge":capabilities.setBrowserName("MicrosoftEdge");break;
			case "firefox":capabilities.setBrowserName("firefox");break;
			default:System.out.println("no matching browser");return;
			}
			
		URI uri = URI.create("http://192.168.1.3:4444/wd/hub");
			driver = new RemoteWebDriver(uri.toURL(), capabilities);
			
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			
			
			switch (br.toLowerCase())
			{
			case "chrome":driver=new ChromeDriver();break;
			case "edge":driver=new EdgeDriver();break;
			case "firefox":driver=new FirefoxDriver();break;
			default:System.out.println("Invalid Browser"); return;
			}
			
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));//reading url from properties file
		driver.manage().window().maximize();
	}
	@AfterClass(groups={"sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}
	public String randomString()
	{
		String generatedString=UUID.randomUUID().toString().substring(0, 5);
		return generatedString;
	}
	public String randomAlphaNumeric()
	{
		String generateString = UUID.randomUUID().toString().replace("-", "").substring(0, 5);

        // Generate 5-digit number
        int generateNumber = ThreadLocalRandom.current().nextInt(10000, 100000);
        String generateNumberStr = String.valueOf(generateNumber);

        // Combine and return
        return( generateString + generateNumberStr);
	}
	public void generateAndStoreCredentials() {
        // Generate and store random email and password
        generatedEmail = randomString() + "@gmail.com";
        generatedPassword = randomAlphaNumeric();
        saveCredentialsToFile();
	}
	public void saveCredentialsToFile() {
        try {
            Properties props = new Properties();
            props.setProperty("email", generatedEmail);
            props.setProperty("password", generatedPassword);
            FileWriter writer = new FileWriter("credentials.properties");
            props.store(writer, "Test user credentials");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCredentialsFromFile() {
        try {
            Properties props = new Properties();
            FileReader reader = new FileReader("credentials.properties");
            props.load(reader);
            generatedEmail = props.getProperty("email");
            generatedPassword = props.getProperty("password");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
        public String captureScreen(String tname) throws IOException {
        	 String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        	 TakesScreenshot takesscreenshot=(TakesScreenshot) driver;
        	 File sourceFile=takesscreenshot.getScreenshotAs(OutputType.FILE);
        	 
        	 String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timestamp + ".png";
        	 File targetFile=new File(targetFilePath);
        	 
        	 sourceFile.renameTo(targetFile);
        	 
        	 return targetFilePath;
        
        
    }
}
