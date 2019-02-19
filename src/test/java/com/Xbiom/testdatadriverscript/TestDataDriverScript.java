package com.Xbiom.testdatadriverscript;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Xbiom.testBase.TestBase;

public class TestDataDriverScript extends TestBase {
	JavascriptExecutor exe = (JavascriptExecutor) driver;
@DataProvider(name="testData")
public Object[][] dataSource()
{
	return getData("testData.xlsx","LoginTestData");
}
@Test(dataProvider="testData")
public void testLogin(String username,String password,String runscript,String url) throws Exception
{
	
	
	loadPropertiesFile();
	logger.info("loading properties file");
	getBrowser(OR.getProperty("Browser"));
	getUrl(OR.getProperty("Website"));
	implicitWait();
WebElement usernamegmail=driver.findElement(By.xpath("//input[@type='email']"));
	//exe.executeScript("arguments[0].value='username';",usernamegmail);
	//sendusername(username);
	usernamegmail.sendKeys(username);
	WebElement usernameSubmit=driver.findElement(By.xpath("//span[text()='Next']"));
	usernameSubmit.click();
		WebElement passwordgmail=driver.findElement(By.xpath("//input[@name='password']"));
		//waitForElementWithPollingInterval(driver, 20, passwordgmail);
		passwordgmail.sendKeys(password);
	WebElement submitPassword=driver.findElement(By.xpath("//span[contains(text(),'Next')]"));
	submitPassword.click();
	Thread.sleep(3000);
	driver.close();
	
	
}
}
