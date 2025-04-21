package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager  implements ITestListener, ISuiteListener  {
    
    public ExtentSparkReporter sparkreporter;
    public ExtentReports extent;
    public ExtentTest test;
    
    String repName;  // Declare repName at class level
    
    public void onStart(ITestContext testContext) {
        // Create a time stamp for the report name
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timestamp + ".html";  // Assign value to the class variable
        
        sparkreporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reports\\" + repName);
        sparkreporter.config().setDocumentTitle("Opencart Automation Report");
        sparkreporter.config().setReportName("Opencart Functional Testing");
        sparkreporter.config().setTheme(Theme.DARK);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkreporter);
        
        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);
        
        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);
        
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }
    
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName() + " is successfully Executed");
    }
    
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        
        test.log(Status.FAIL, result.getName() + " is failed");
        test.log(Status.INFO, result.getThrowable().getMessage());
        
        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getClass().getName());
        
        test.log(Status.SKIP, "Test case skipped is: " + result.getName());
        test.assignCategory(result.getMethod().getGroups());
        
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }
    
    public void onFinish(ITestContext testContext) {
        extent.flush();
        
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);
        
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
//        try {
//            // Create a file URL using Paths and File.separator
//            URL url = new File(System.getProperty("user.dir"), "reports" + File.separator + repName).toURI().toURL();
//            
//            // Create ImageHtmlEmail object and configure email
//            ImageHtmlEmail email = new ImageHtmlEmail();
//            email.setHostName("smtp.googlemail.com");
//            email.setSmtpPort(465);
//            email.setAuthenticator(new DefaultAuthenticator("hariniaduri2001@gmail.com", "password"));
//            email.setSSLOnConnect(true);
//            email.setFrom("hariniaduri2001@gmail.com");  // Use a valid sender email address
//            email.setSubject("Test Results");
//            email.setMsg("Please Find Attached Reports");
//            email.addTo("radhaduri@gmail.com");  // Use a valid recipient email address
//            email.attach(url,"extent report","please check report");
//            
//            // Send the email
//            email.send();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

