package com.qa.google.base;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.qa.google.reports.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	public static WebDriver driver=null;
	public ExtentReports _startReport = ExtentManager.getInstance();
	public ExtentTest _report;
	Properties prop;
	String propertyPath = "src/main/java/com/qa/google/config/config.properties";
	String browserType ="" ;
    String appURL = "";
	
	@BeforeTest
	public void ConfigFileRead(){
		try{
			FileInputStream fis = new FileInputStream(propertyPath);
			prop = new Properties();
			prop.load(fis);
			fis.close();
		}
		catch(Exception e){}
	}
	
	@BeforeMethod
	public void NavigateToUrl(){
		System.out.println("BeforeMethod");
		appURL = prop.getProperty("url");
    	browserType = prop.getProperty("browser");
    	browserType = browserType.toLowerCase();
    	
    	if (browserType=="chrome")
    		driver = initChromeBrowser();
    	else if(browserType=="ff")
    		driver = initFirefoxBrowser();
    	else if(browserType=="ie")
    		driver = initIEBrowser();
    	else if(browserType=="edge")
    		driver = initEdgeBrowser();
    	else{
    	System.out.println("Browser value entered is invalid, Launching chrome  ");
    	driver = initChromeBrowser();
    	}
    	
    	System.out.println("Navigate to url");
    	driver.manage().window().maximize();
    	driver.manage().deleteAllCookies();
    	driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    	driver.get(appURL);
    	
    	}
    private WebDriver initChromeBrowser(){
    	System.out.println("Launching Chrome Browser ");
    	WebDriverManager.chromedriver().setup();
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("start-maximized");
		options.addArguments("-incognito");
		options.addArguments("chrome.switches","--disable-extensions");
    	driver = new ChromeDriver(options);
    	return driver;
    }
    
    private WebDriver initFirefoxBrowser(){
    	System.out.println("Launching Firefox browser ");
    	WebDriverManager.firefoxdriver().setup();
    	driver = new FirefoxDriver();
    	return driver;
    }
    
    private WebDriver initIEBrowser(){
    	System.out.println("Launching IE browser ");
    	WebDriverManager.iedriver().setup();
    	driver = new InternetExplorerDriver();
    	return driver;
    }
    
    private WebDriver initEdgeBrowser(){
    	System.out.println("Launching edge browser ");
    	WebDriverManager.edgedriver().setup();
    	driver = new EdgeDriver();
    	return driver;
    }
    
    @AfterMethod
    public void tearDown(ITestResult result){
    	if(result.getStatus()==ITestResult.FAILURE){
    		_report.log(LogStatus.FAIL, "Test case failed is : "+ result.getName());
//    		String screenShotPath = ZOOPLAPage.getScreenShot(driver,result.getName());
//    		_report.log(LogStatus.FAIL,_report.addScreenCapture(screenShotPath));
    	}
    	else if(result.getStatus()==ITestResult.SKIP)
    		_report.log(LogStatus.SKIP, "Test case skipped is : " +result.getName());
    	else if (result.getStatus()==ITestResult.SUCCESS)
    		_report.log(LogStatus.PASS, "Test case passed is : "+result.getName());
    	
    	_report.log(LogStatus.INFO,"Closing the browser");
    	if(driver!=null)
    		driver.close();
    	_startReport.flush();
    	_startReport.endTest(_report);
    }
	

}
