package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	
	private static Properties pro;
	private static String path;
		
	public ReadConfig() {
		try {
			File src = new File("./src/test/resources/config.properties");
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);
		}catch (Exception e) {
			System.out.println("Exception is: "+e.getMessage());
		}
	}
	
	public static String getUrl() {
		String url = pro.getProperty("baseUrl");
		return url;
	}
	
	public static String getUsername() {
		String userName = pro.getProperty("username");
		return userName;
	}
	
	public static String getPassword() {
		String password = pro.getProperty("password");
		System.out.println(CommUtils.encodingText(password));
		return password;
	}
	
	public static String getLoginDataPath() {
		path = System.getProperty("user.dir");
		String filePath = path.concat(pro.getProperty("loginData"));		
		return filePath;
	}
	
	public static String getInvalidUsername() {
		String invalidusername = pro.getProperty("invalidUsername");
		return invalidusername;
	}
	
	public static String getInvalidPassword() {
		String invalidPassword = pro.getProperty("invalidPassword");
		return invalidPassword;
	}

}
