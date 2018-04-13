package com.subramanian.karthick.com.automation.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.subramanian.karthick.com.automation.pageobject.AutomationErrorException.ErrorKind;


public class HomePage extends PageContext {
  static private By dirBtnLocator=By.id("searchbox-directions");

  public HomePage(WebDriver driver) {
    super(driver);
  }

  @Override
  public PageEnum getPage() {
    return PageEnum.HOME_PAGE;
  }
  
  public WebElement getDirectionsBtn()
    throws AutomationErrorException {
      return PageContext.waitForElementClickable(
        this.getDriver(), HomePage.dirBtnLocator, 
        30, "Missing 'Directions' button"
      );
    } 
  
  @Override
  public void checkContextElements() 
  throws AutomationErrorException {
    this.getDirectionsBtn();
  }
  
  public void navigateTo(PageEnum page) 
  throws AutomationErrorException {
 
    switch(page) {
      case HOME_PAGE:
        break; 
      case DIRECTIONS_PAGE:
        this.getDirectionsBtn().click();
        break;
      default:
        throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Unknown/unhandled destination page to navigate to");
    }
  }
}
