package com.subramanian.karthick.com.automation.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import Utilities.CsvFileWriter;

public class TestBase {
	
	WebDriver driver;
	
	
	@BeforeMethod
	public void setUp() {
		
    	System.setProperty("webdriver.chrome.driver", "C:\\Users\\Karthick\\eclipse-workspace\\com.subramanian.karthick"
    			+ "\\Driver\\chromedriver.exe");
    	 
    	   driver = new ChromeDriver();
    	  driver.get("https://www.google.com/maps");
    	  
    	  driver.manage().window().maximize();
	
	}
	
	@AfterTest 
	public void tearDown() {
		driver.quit();
	}
}


