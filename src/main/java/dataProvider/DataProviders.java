package dataProvider;

import java.io.IOException;
import java.util.Arrays;

import org.testng.annotations.DataProvider;

public class DataProviders {

	static String path = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\OCR_API\\Book 18(1).xlsx";
	static String sheetName = "ocrdata";

	@DataProvider(name = "ocr_data_provider", parallel = true)
	public static String[][] provider() throws IOException {

		try {
			return Excel_service.readExcelData(path, sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
