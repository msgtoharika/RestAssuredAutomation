package com.test.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcelData {

	static Workbook workbook;

	static Sheet sheet;

	public static String TESTUSERDATA_PATH = "C:\\Users\\haa\\OneDrive - Adobe\\Documents\\API Stuff\\bookdetails.xlsx";

	public static Object[][] getData(String sheetName) {
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(TESTUSERDATA_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			workbook = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = workbook.getSheet(sheetName);

		// create data array
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		// fetch data
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				if(sheet.getRow(i + 1).getCell(j)!=null) {
					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}else
				{
					data[i][j] = "";
				}
				
				
			}
		}
		return data;
	}

}
