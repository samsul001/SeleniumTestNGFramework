package utils;

import java.util.Set;

import factory.DriverFactory;

public class WindowUtils {
	
	//Switch to windows using index
	
	public static String getParentWindowHandle() {
		return DriverFactory.getDriver().getWindowHandle();
	}
	
	public static Set<String> getAllWindowHandles(){
		return DriverFactory.getDriver().getWindowHandles();
	}
	
	public static void switchToParentWindow(String parentWindow) {
		DriverFactory.getDriver().switchTo().window(parentWindow);
	}
	
	public static void switchToChildWindow() {
		String parentWindow = DriverFactory.getDriver().getWindowHandle();
		
		for(String window: DriverFactory.getDriver().getWindowHandles()) {
			if(!window.equals(parentWindow)) {
				DriverFactory.getDriver().switchTo().window(window);
				break;
			}
		}
	}
	
	public static void switchToWindowByIndex(int index) {
		Set<String> windowHandles = DriverFactory.getDriver().getWindowHandles();
		if(index==0 || index > windowHandles.size()) {
			throw new IllegalArgumentException();
		}
		String[] handles =windowHandles.toArray(new String[0]);
		DriverFactory.getDriver().switchTo().window(handles[index]);
	}
	
	public static void switchToWindowByTitle(String expectedTitle) {
		for(String window: DriverFactory.getDriver().getWindowHandles()) {
			DriverFactory.getDriver().switchTo().window(window);
			if(DriverFactory.getDriver().getTitle().equals(expectedTitle)) {
				return;
			}
		}
		throw new RuntimeException("No window is found: "+expectedTitle);
	}
	
	public static void closeExceptParent() {
		String parentWindow = DriverFactory.getDriver().getWindowHandle();
		
		for(String handle: DriverFactory.getDriver().getWindowHandles()) {
			if(!handle.equals(parentWindow)) {
				DriverFactory.getDriver().switchTo().window(handle).close();
			}
		}
		
		DriverFactory.getDriver().switchTo().window(parentWindow);
	}

}
