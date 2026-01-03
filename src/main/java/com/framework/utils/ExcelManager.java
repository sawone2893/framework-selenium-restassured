package com.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManager {

	public static Map<String, String> getTestCaseData(String tcId) {

		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		File file = null;
		FileInputStream fis = null;
		int tcIdRowIndex = 0;
		Map<String, String> testCaseData = new HashMap<>();
		try {
			String filePath = System.getProperty("user.dir")
					+ EnvConfig.get("TESTDATA_FILE_LOCATION");
			file = new File(filePath);
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(EnvConfig.get("TESTDATA_FILE_SHEET_NAME"));
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			XSSFRow headerRow = sheet.getRow(0);
			int colCount = headerRow.getPhysicalNumberOfCells();

			for (int j = 1; j <= rowCount; j++) {
				if (tcId.equalsIgnoreCase(sheet.getRow(j).getCell(0).getStringCellValue())) {
					tcIdRowIndex = j;
					break;
				}

			}

			for (int i = 0; i < colCount; i++) {
				testCaseData.put(headerRow.getCell(i).getStringCellValue(),
						sheet.getRow(tcIdRowIndex).getCell(i).getStringCellValue());

			}
			workbook.close();
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return testCaseData;
	}

}
