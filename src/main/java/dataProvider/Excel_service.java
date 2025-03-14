package dataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Excel_service {

	public static String[][] readExcelData(String filePath, String sheetName) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(new File(filePath));
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheet(sheetName);

		if (sheet == null) {
			throw new IOException("Sheet with name '" + sheetName + "' not found in the Excel file.");
		}

		int rowCount = sheet.getPhysicalNumberOfRows();
		if (rowCount == 0) {
			throw new IOException("No rows found in the sheet '" + sheetName + "'.");
		}

		Row firstRow = sheet.getRow(0);
		int columnCount = (firstRow != null) ? firstRow.getPhysicalNumberOfCells() : 0;

		String[][] data = new String[rowCount][columnCount];

		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}

			for (int j = 0; j < columnCount; j++) {
				Cell cell = row.getCell(j);
				if (cell != null) {

					switch (cell.getCellType()) {
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							data[i][j] = cell.getDateCellValue().toString();
						} else {
							double numericValue = cell.getNumericCellValue();
							if (numericValue == Math.floor(numericValue)) {
								data[i][j] = String.valueOf((int) numericValue);
							} else {
								data[i][j] = String.valueOf(numericValue);
							}
						}
						break;
					case STRING:
						data[i][j] = cell.getStringCellValue();
						break;
					case BOOLEAN:
						data[i][j] = String.valueOf(cell.getBooleanCellValue());
						break;
					case FORMULA:
						data[i][j] = cell.getCellFormula();
						break;
					default:
						data[i][j] = "";
						break;
					}
				} else {
					data[i][j] = "";
				}
			}
		}

		workbook.close();
		fileInputStream.close();

		return data;
	}
}
