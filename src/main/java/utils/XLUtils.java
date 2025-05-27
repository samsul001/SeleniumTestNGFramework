package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtils {
	public static FileInputStream fi;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;
	
	public static int getRowCount(String filepath, String sheetName) throws IOException {
		fi = new FileInputStream(filepath);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheetName);
		int rowCount = ws.getLastRowNum();
		fi.close();
		wb.close();
		return rowCount;
	}
	
	public static int getColCount(String filepath, String sheetName, int rowNum) throws IOException {
		fi = new FileInputStream(filepath);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheetName);
		row = ws.getRow(rowNum);
		int colCount = row.getLastCellNum();
		fi.close();
		wb.close();
		return colCount;
	}
	
	public static String getCellData(String filepath, String sheetName, int rowNum, int colNum) {
		String data = " ";
		
		try(
			FileInputStream fi = new FileInputStream(filepath);
			XSSFWorkbook wb = new XSSFWorkbook(fi)	
		){
			ws = wb.getSheet(sheetName);
			row = ws.getRow(rowNum);
			cell = row.getCell(colNum);
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return data;
	}

	
}
