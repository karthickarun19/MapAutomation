package Utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.openqa.selenium.WebDriver;
import com.subramanian.karthick.com.automation.pageobject.AutomationErrorException;
import com.subramanian.karthick.com.automation.pageobject.DirectionsPage;
import com.subramanian.karthick.com.automation.pageobject.DirectionsPage.RouteSummary;

public class CsvFileWriter {
	WebDriver driver;
	DirectionsPage directions = new DirectionsPage(driver);
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final Object[] FILE_HEADER = { "Title", "Distance", "Travel Time" };

	public void writeCsvFile(String fileName) throws IOException {
		
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;

		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		List<RouteSummary> routes = null;
		try {
			fileWriter = new FileWriter(fileName);
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			csvFilePrinter.printRecord(FILE_HEADER);

			routes = directions.readRoutesSimple();
			csvFilePrinter.printRecord(routes);

		} catch (AutomationErrorException e) {
			e.printStackTrace(System.err);
			System.exit(1);
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();

			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
				e.printStackTrace();

			}
		}

	}
}
