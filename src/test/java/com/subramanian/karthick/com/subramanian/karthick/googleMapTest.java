package com.subramanian.karthick.com.subramanian.karthick;

import java.io.IOException;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.subramanian.karthick.com.automation.pageobject.DirectionsPage.TravelMode;
import com.subramanian.karthick.com.automation.pageobject.DirectionsPage;
import com.subramanian.karthick.com.automation.pageobject.HomePage;
import com.subramanian.karthick.com.automation.pageobject.PageContext;
import com.subramanian.karthick.com.automation.pageobject.PageEnum;
import com.subramanian.karthick.com.automation.pageobject.Screenshot;
import com.subramanian.karthick.com.automation.pageobject.TestBase;

import Utilities.CsvFileWriter;
import Utilities.PropertyReader;

public class googleMapTest extends TestBase {
	PropertyReader pr = new PropertyReader();
	Screenshot sc = new Screenshot();
	WebDriver driver;
	CsvFileWriter csv = new CsvFileWriter();

	@Test
	public void Destination1() throws IOException {

		PageContext page = new HomePage(driver);
		page.checkContextElements();
		page.navigateTo(PageEnum.DIRECTIONS_PAGE);

		DirectionsPage directions = new DirectionsPage(driver);
		directions.checkContextElements();

		// click the travel mode by foot
		directions.setTravelMode(TravelMode.BY_FOOT);

		// Read the property files and supplies the starting and endpoint
		Set<Object> keys = pr.getAllKeys();
		for (Object k : keys) {
			String startingPoint = (String) k;
			directions.setStartingPoint(startingPoint);
			directions.setDestination(pr.getPropertyValue(startingPoint));
			// Helper method to take the screenshot
			sc.snap(driver, "C:\\Users\\Karthick\\eclipse-workspace\\com.subramanian.karthick\\Screenshot");

		}
		csv.writeCsvFile("C:\\Users\\Karthick\\eclipse-workspace\\com.subramanian.karthick\\directions_data.csv");
	}
}