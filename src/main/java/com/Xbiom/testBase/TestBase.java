package com.Xbiom.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.Xbiom.Excelreader.Excel_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	public static final Logger logger = Logger.getLogger(TestBase.class.getName());
	public   WebDriver driver;
	public Properties OR;
	public File f1;
	public FileInputStream file;
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	Excel_Reader excelreader;
	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		 extent = new ExtentReports(System.getProperty("user.dir") + "/resources/testReports/ " + formater.format(calendar.getTime()) + ".html", false);
	}

	@AfterMethod()
	public void afterMethod(ITestResult result) throws IOException {
		getresult(result);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test Started");
	}
	
	@AfterClass(alwaysRun = true)
	public void endTest() {
		//driver.quit();
		extent.endTest(test);
		extent.flush();
	}
	public void getresult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.SUCCESS) {

			test.log(LogStatus.PASS, result.getName() + " test is pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getName() + " test is failed" + result.getThrowable());
			String screen = getScreenShot("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}

	public void getBrowser(String browser){
		if(System.getProperty("os.name").contains("Window")){
			if(browser.equalsIgnoreCase("firefox")){
				//https://github.com/mozilla/geckodriver/releases
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}
			else if(browser.equalsIgnoreCase("chrome")){
//				ChromeOptions options = new ChromeOptions();
//				options.addArguments("test-type");
//				options.addArguments("start-maximized");
//				options.addArguments("disable-infobars");
//				options.addArguments("--disable-extensions"); 
				//https://chromedriver.storage.googleapis.com/index.html
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				 driver = new ChromeDriver();
			}
			else if(browser.equalsIgnoreCase("ie")){
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		}
		else if(System.getProperty("os.name").contains("Mac")){
			System.out.println(System.getProperty("os.name"));
			if(browser.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver");
				driver = new FirefoxDriver();
			}
			else if(browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver = new ChromeDriver();
			}
			else if(browser.equalsIgnoreCase("ie")){
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		}
	}
	public void getUrl(String website)
	{
		driver.manage().window().maximize();
		driver.get(website);
		
	
	}	
	public void loadPropertiesFile() throws Exception
	{
		String log4jConfpath="log4j.properties";
		PropertyConfigurator.configure(log4jConfpath);
		OR = new Properties();
		f1 = new File(System.getProperty("user.dir")+"/resources/config.properties");
		file = new FileInputStream(f1);
		OR.load(file);
		
		f1 = new File(System.getProperty("user.dir")+"/log4j.properties");
		file = new FileInputStream(f1);
		OR.load(file);
		
		
	}
	public String getScreenShot(String imageName) throws IOException{
		
		if(imageName.equals("")){
			imageName = "blank";
		}
		File image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imagelocation = System.getProperty("user.dir")+"/src/main/java/com/Xbiom/screenshot/";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImageName = imagelocation+imageName+"_"+formater.format(calendar.getTime())+".png";
		File destFile = new File(actualImageName);
		FileUtils.copyFile(image,destFile);
		return actualImageName;
	}
	public WebElement waitForElement(WebDriver driver,long time,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	@SuppressWarnings("deprecation")
	public WebElement waitForElementWithPollingInterval(WebDriver driver,long time,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(StaleElementReferenceException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	public void implicitWait(){
		String imp=OR.getProperty("ImplicitWait");		
		driver.manage().timeouts().implicitlyWait(Long.parseLong(imp), TimeUnit.SECONDS);		
	}
	  
	public void mouseOvertheElement(WebDriver driver)
	{
		WebElement element1=driver.findElement(By.xpath("//a[@id='nav-link-yourAccount']"));
		Actions action=new Actions(driver);
		Action actions=action.moveToElement(element1).build();
		actions.perform();
		driver.close();
		
	}
	public String[][] getData(String excelName, String sheetName){
		System.out.println(System.getProperty("user.dir"));
		String excellocation = System.getProperty("user.dir")+"/resources/"+excelName;
		System.out.println(excellocation);
		 excelreader = new Excel_Reader();
		return excelreader.getExcelData(excellocation, sheetName);
	}
	
		public static void main(String[] args) throws Exception {
		TestBase base=new TestBase();
		base.loadPropertiesFile();
		base.getBrowser(base.OR.getProperty("Browser"));
		base.implicitWait();
		base.getUrl(base.OR.getProperty("Website"));
		logger.info("adajhjlashfashdklaj");
		base.getScreenShot("homepage");
	//	base.mouseOvertheElement(driver);
				
	
	}
}
