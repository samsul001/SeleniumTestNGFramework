package dataproviders;

import java.io.FileInputStream;
import java.io.IOException;

import org.testng.annotations.DataProvider;

import utils.ReadConfig;
import utils.XLUtils;

public class LoginTestData {
	
	@DataProvider(name = "loginTestData")
	public static Object[][] loginData() throws IOException{
		
		FileInputStream fis = new FileInputStream(ReadConfig.getLoginDataPath());
		int rowNum = XLUtils.getRowCount(ReadConfig.getLoginDataPath(), "Sheet1");
		int colNum = XLUtils.getColCount(ReadConfig.getLoginDataPath(), "Sheet1", 1);
		
		Object data[][] = new Object[rowNum][colNum];
		
		for(int i=1; i<=rowNum; i++) {
			for(int j=0; j<colNum; j++) {
				data[i-1][j] = XLUtils.getCellData(ReadConfig.getLoginDataPath(), "Sheet1", i, j);
			}
		}
		return data;
	}

}
