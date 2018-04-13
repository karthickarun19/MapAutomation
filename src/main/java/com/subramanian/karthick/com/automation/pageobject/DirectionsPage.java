package com.subramanian.karthick.com.automation.pageobject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.subramanian.karthick.com.automation.pageobject.AutomationErrorException.ErrorKind;
//This class maintains elements of a page
public class DirectionsPage extends PageContext {
   private static By byCarBtnLocator=By.cssSelector(".directions-travel-mode-icon.directions-drive-icon");
   private static By byTramBtnLocator=By.cssSelector(".directions-travel-mode-icon.directions-transit-icon");
   private static By byFootBtnLocator=By.cssSelector(".directions-travel-mode-icon.directions-walk-icon");
   private static By srcInputLocator=By.xpath("//div[@id='directions-searchbox-0']//input[@class='tactile-searchbox-input']");
   private static By destInputLocator=By.xpath("//div[@id='directions-searchbox-1']//input[@class='tactile-searchbox-input']");
   private static By closeDirectionsLocator=By.xpath("//div[@class='widget-directions-travel-mode-switcher-container']//button[@class='widget-directions-close']");
   private static By routesSimpleDetailsListLocator=By.xpath("//div[@class='widget-pane-section-directions-trip-description']/div[@style!='display:none']");
   private static By routeViaLocator=By.xpath(".//h1[@class='widget-pane-section-directions-trip-title']//span");
   private static By routeDurationLocator=By.xpath(".//div[contains(@class, 'widget-pane-section-directions-trip-duration')]/span[not(@style)]");
   private static By routeDistanceLocator=By.xpath(".//div[contains(@class, 'widget-pane-section-directions-trip-secondary-text')]/div"); 
   private static By routeConfusingWaypoint=By.xpath("//div[@class='widget-directions-suggest-container']//div[@class='widget-directions-waypoint-status' and not(@style)]");
 
  public enum TravelMode {
    BY_CAR,
    BY_PUBTRNS,
    BY_FOOT;
    
    public String toString() {
      String toRet="'Travel by ";
      switch(this) {
        case BY_CAR:
          toRet+="car'";
          break;
        case BY_PUBTRNS:
          toRet+="public transport'";
          break;
        case BY_FOOT:
          toRet+="foot'";
          break;
        default: 
          toRet+=" ???'";
          break;
      }
      return toRet;
    }
  }
  

   public static class RouteSummary {
    protected String title;
    protected String travelTime;
    protected String distance;

    public RouteSummary(String title, String travelTime, String distance) {
      super();
      this.title = title;
      this.travelTime = travelTime;
      this.distance = distance;
    }

    public String getVia() {
      return this.title;
    }

    protected void setVia(String title) {
      this.title = title;
    }

    public String getTravelTime() {
      return this.travelTime;
    }

    protected void setTravelTime(String travelTime) {
      this.travelTime = travelTime;
    }

    public String getDistance() {
      return this.distance;
    }

    protected void setDistance(String distance) {
      this.distance = distance;
    }
  }
  
  public DirectionsPage(WebDriver driver) {
    super(driver);
  }

  @Override
  public PageEnum getPage() {
    return PageEnum.DIRECTIONS_PAGE;
  }

  @Override
  public void checkContextElements() 
  throws AutomationErrorException {
    this.getDestinationInput();
    this.getStartingPointInput();
    this.getTravelModeBtn(TravelMode.BY_CAR);
    this.getTravelModeBtn(TravelMode.BY_PUBTRNS);
    this.getTravelModeBtn(TravelMode.BY_FOOT);
    this.getCloseDirectionsBtn();
  }
  
  protected WebElement getTravelModeBtn(TravelMode mode)
  throws AutomationErrorException {
    if(null==mode) {
      mode=TravelMode.BY_CAR; 
    }
    String msg="For "+mode+" button";
    By locator=null;
    switch(mode) {
      case BY_CAR:
        locator=DirectionsPage.byCarBtnLocator;
        break;
      case BY_PUBTRNS:
        locator=DirectionsPage.byTramBtnLocator;
        break;
      case BY_FOOT:
        locator=DirectionsPage.byFootBtnLocator;
        break;
      default:
        throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Unhadled transporation mode: "+mode);
    }
    return this.waitForElementClickable(
      locator, 30, msg
    );
  }
  
  protected WebElement getStartingPointInput()
  throws AutomationErrorException {
    return this.waitForElementClickable(
      DirectionsPage.srcInputLocator, 
      30, "For 'Starting point' input field"
    );
  }
  
  protected WebElement getDestinationInput() 
  throws AutomationErrorException  {
    return this.waitForElementClickable(
      DirectionsPage.destInputLocator, 
      30, "For 'Destination point' input field"
    );
  }
  
  protected WebElement getCloseDirectionsBtn() {
    return this.waitForElementClickable(
      DirectionsPage.closeDirectionsLocator, 
      30, "For 'Close directions' button"
    );
  }
  
  public void navigateTo(PageEnum page) {
    switch(page) {
      case HOME_PAGE:
        this.getCloseDirectionsBtn().click();
      case DIRECTIONS_PAGE:  
        break;
      default:
        throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Unknown/unhandled destination page to navigate to");
    }
  }

  public void setStartingPoint(String startingPoint)
  throws AutomationErrorException {
    WebElement input=this.getStartingPointInput();
    input.clear();
    if(null!=startingPoint) {
      input.sendKeys(startingPoint);
    }
  }

  public void setDestination(String destination)
  throws AutomationErrorException {
    WebElement input=this.getDestinationInput();
    input.clear();
    if(null!=destination) {
      input.sendKeys(destination);
    }
  }
  
  public void setTravelMode(TravelMode travelMode)
  throws AutomationErrorException {
    WebElement travelModeBtn=this.getTravelModeBtn(travelMode);
    travelModeBtn.click();
  }
  
  public List<RouteSummary> readRoutesSimple() {
    List<WebElement> routeSummaryList=this.waitForElementListVisible(
      DirectionsPage.routesSimpleDetailsListLocator,
      -1, "List of route summaries"
    );
   
    List<RouteSummary> toRet=null;
    if(null!=routeSummaryList) {
      toRet=routeSummaryList.stream().map(
        e-> {
          try {
            String via=e.findElement(DirectionsPage.routeViaLocator).getText();
            String distance=e.findElement(DirectionsPage.routeDistanceLocator).getText();
            String duration=e.findElement(DirectionsPage.routeDurationLocator).getText();
            return new RouteSummary(via, duration, distance);
          }
          catch(NoSuchElementException ex) {
            throw new AutomationErrorException(ErrorKind.AUTOMATION_LOGIC, "Element not found", ex);
          }
        }
      ).collect(Collectors.toList());
    }
    if(null==toRet) {
      toRet=Collections.emptyList();
    }
    return toRet;
  }
  

  public String readConfusingLocationsMessage() {
    List<WebElement> confusingWaypointList=this.waitForElementListVisible(
      DirectionsPage.routeConfusingWaypoint,
      -1, "List of confusing waypoints"
    );
    final StringBuilder ret=new StringBuilder("");
    confusingWaypointList.stream().forEach(
      e->{ ret.append(e.getText()); }
    );
    return ret.toString();
  }
}
