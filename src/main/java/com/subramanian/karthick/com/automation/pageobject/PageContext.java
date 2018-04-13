package com.subramanian.karthick.com.automation.pageobject;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.subramanian.karthick.com.automation.pageobject.AutomationErrorException.ErrorKind;

abstract public class PageContext {
  static private final int maxWaitSecs=30;
  
  static protected WebDriver defaultWebDriver=null;
  
  static public WebDriver getDefaultWebDriver() {
    return PageContext.defaultWebDriver;
  }
  
  static public void setDefaultWebDriver(WebDriver driver) {
    PageContext.defaultWebDriver=driver;
  }

  protected WebDriver driver;
  
  protected PageContext(WebDriver driver) {
    this.driver=driver;
  }
  
  protected WebDriver getDriver() throws AutomationErrorException {
    return PageContext.driver(this.driver);
  }
  
  static protected WebDriver driver(WebDriver driver) {
    WebDriver toRet=driver;
    if(null==toRet) {
      toRet=PageContext.getDefaultWebDriver();
    }
    if(null==driver) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "No WebDriver available");
    }
    return toRet;
  }
  
 
 abstract public PageEnum getPage();
  
  
  abstract public void checkContextElements()
  throws AutomationErrorException;
  

  abstract public void navigateTo(PageEnum page);


  protected WebElement waitForElementClickable(
    By condition,
    long secsToWait, String msgIfTimeout
  ) 
  throws AutomationErrorException {
    return PageContext.waitForElementClickable(
      this.getDriver(), condition, 
      secsToWait, msgIfTimeout+" (in the context of a "+this.getPage()+" page)"
    );
  }
 
  static protected WebElement waitForElementClickable(WebDriver driver, By condition,long secsToWait, String msgIfTimeout) throws AutomationErrorException {
    driver=PageContext.driver(driver);
    if(null==condition) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "No condition specified to get a clickable Web element");
    }
    if(secsToWait<=0) {
      secsToWait=PageContext.maxWaitSecs;
    }
    WebElement toRet=null;
    WebDriverWait wait= new WebDriverWait(driver, secsToWait);
    wait.pollingEvery(1, TimeUnit.SECONDS);
    try {
      toRet=wait.until(ExpectedConditions.elementToBeClickable(condition));
    }
    catch(NoSuchElementException e) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, msgIfTimeout, e);
    }
    return toRet;
  }

  
  protected List<WebElement> waitForElementListVisible(
    By condition,
    long secsToWait, String msgIfTimeout
  ) {
    return PageContext.waitForElementListVisible(
      this.getDriver(), 
      condition, secsToWait,
      msgIfTimeout+" (in the context of a "+this.getPage()+" page)"
    );
  }
  

  static protected List<WebElement> waitForElementListVisible( WebDriver driver, By condition,long secsToWait, String msgIfTimeout) 
  throws AutomationErrorException {
    driver=PageContext.driver(driver);
    if(null==condition) {
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "No condition specified to get a clickable Web element");
    }
    if(secsToWait<=0) {
      secsToWait=PageContext.maxWaitSecs; 
    }
    List<WebElement> toRet=null;
    WebDriverWait wait= new WebDriverWait(driver, secsToWait);
    wait.pollingEvery(1, TimeUnit.SECONDS);
    try {
      toRet=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(condition));
    }
    catch(NoSuchElementException e) {
    
      throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, msgIfTimeout, e);
    }
    return toRet;
  }
}
