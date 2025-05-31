package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import factory.DriverFactory;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class CommUtils {
	
//	allure generate target/allure-results --clean -o allure-report
//	allure open allure-report
	
	//---------------------------WebElement actions------------------------------------------------------------------------
	//-----------------------------Author: Samsul -------------------------------------------------------------------------
		
	public static void safeClick(WebElement ele, int timeoutSeconds) {
		 CommUtils.waitForElementClickable(ele, timeoutSeconds).click();
	 }
	 
	 public static void saftType(WebElement ele, int timeoutSeconds, String text) {
		 ele.clear();
		 CommUtils.waitForElementVisible(ele, timeoutSeconds).sendKeys(text);
	 }
	 
	 public static String getText(WebElement ele, int timeoutSeconds) {
		 return CommUtils.waitForElementVisible(ele, timeoutSeconds).getText();
	 }
	 
	 public static void clearText(WebElement ele, int timeoutSeconds) {
		 CommUtils.waitForElementVisible(ele, timeoutSeconds).clear();
	 }
	 
	 public static String getAttribute(WebElement ele, int timeoutSeconds, String attribute) {
		 return CommUtils.waitForElementVisible(ele, timeoutSeconds).getAttribute(attribute);
	 }
	 
	 public static boolean isDisplayed(WebElement ele, int timeoutSeconds) {
		 return CommUtils.waitForElementVisible(ele, timeoutSeconds).isDisplayed();
	 }
	 
	 public static boolean isEnabled(WebElement ele, int timeoutSeconds) {
		 return CommUtils.waitForElementClickable(ele, timeoutSeconds).isEnabled();
	 }
	 
	 public static boolean isSelected(WebElement ele, int timeoutSeconds) {
		 return CommUtils.waitForElementClickable(ele, timeoutSeconds).isSelected();
	 }
	 
	 //-----------------------------Link related utilities------------------------------------------------------------------
	 //-----------------------------Author: Samsul Alam -------------------------------------------------------------------
	 
	 public static WebElement waitForLinkTobeClickable(WebElement link, int timeoutSeconds) {
		 WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
		 return wait.until(ExpectedConditions.elementToBeClickable(link));
	 }
	 
	 public static void clickLink(WebElement ele, int timeoutSeconds) {
		 try {
			 waitForElementClickable(ele,timeoutSeconds).click(); 
		 }catch(Exception e) {
			 clickElementByJS(ele);
		 }		 
	 }
	 
	 public static boolean isLinkActive(WebElement link) {
		 String href = getAttribute(link, 10, "href");
		 
		 if(href == null || href.isBlank()) return false;
		 
		 try {
			 HttpURLConnection connection = (HttpURLConnection) new URL(href).openConnection();
			 connection.setConnectTimeout(5000);
			 connection.connect();
			 return connection.getResponseCode() < 400;
		 }catch(Exception e) {
			 return false;
		 }
	 }
	 
	 public static boolean isLinkBroken(WebElement ele) {
		 return !isLinkActive(ele);
	 }
	 
	 public static boolean areAllLinkBroken(List<WebElement> links) {
		 for(WebElement link: links) {
			 if(isLinkActive(link)) {
				 return false;
			 }
		 }
		 return true;
	 }
	 
	 public static List<WebElement> getBrokenLinks(List<WebElement> links){
		 List<WebElement> brokenLink = new ArrayList<WebElement>();
		 
		 for(WebElement link: links) {
			 if(isLinkActive(link)) {				 
				 brokenLink.add(link);
			 }
		 }
		 return brokenLink;
	 }
	 
	 public static List<WebElement> findBrokenLinksFromPage(){
		 List<WebElement> links = DriverFactory.getDriver().findElements(By.tagName("a"));
		 return getBrokenLinks(links);
	 }
	 
	
	//-------------------------Encoding Decoding texts-------------------------------------------------------
	//-------------------------Author: Samsul ---------------------------------------------------------------
	
	public static String encodingText(String value) {
		byte[] encoding = Base64.encodeBase64(value.getBytes());
		return (new String(encoding));
	}
	
	public static String decodingText(String value) {
		byte[] decoding = Base64.decodeBase64(value.getBytes());
		return (new String(decoding));
	}
	
	//--------------------------Wait Utils-------------------------------------------------------------------
	//-------------------------Author : Samsul---------------------------------------------------------------
	
	public static WebElement waitForElementVisible(WebElement ele, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
		return wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public static WebElement waitForElementClickable(WebElement ele, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
		return wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	
	public static boolean waitForUrlContains(String ele, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
		return wait.until(ExpectedConditions.urlContains(ele));
	}
	
	public static boolean waitForTitleContains(String title, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
		return wait.until(ExpectedConditions.titleContains(title));
	}
	
	 public static boolean waitForElementToDisappear(WebElement element, int timeoutSeconds) {
	    WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
	    return wait.until(ExpectedConditions.invisibilityOf(element));
	 }
	 
	 public static boolean waitForTextTobePressent(WebElement ele, String text, int timeoutSeconds) {
		 WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
		 return wait.until(ExpectedConditions.textToBePresentInElement(ele, text));
	 }
	 
	 public static WebElement fluentWaitForElements(By locator, int timeoutSeconds, int pollingSeconds) {
		 FluentWait<WebDriver> wait = new FluentWait<>(DriverFactory.getDriver())
				 .withTimeout(Duration.ofSeconds(timeoutSeconds))
				 .pollingEvery(Duration.ofSeconds(pollingSeconds))
				 .ignoring(NoSuchElementException.class);
		 
		 return wait.until(new Function<WebDriver, WebElement>() {
			 public WebElement apply(WebDriver driver) {
				 WebElement element = driver.findElement(locator);
				 if(element.isDisplayed()) {
					 return element;
				 }
				 return null;
			 }
		});
	 }
	 
	 public static void waitForPageLoad(int timeoutSeconds) {
		 WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
		 wait.until(webDriver -> ((JavascriptExecutor)webDriver).executeScript("return document.readyState").equals("complete"));
	 }
	
	public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
	
	//------------------------------JavascriptExecutor utils----------------------------------------------------------------
	//----------------------------Author: Samsul-----------------------------------------------------------------------------
	
	public static void clickElementByJS(WebElement element) {
		((JavascriptExecutor)DriverFactory.getDriver()).executeScript("arguments[0].click();", element);
	}
	
	public static void refreshPageByJS() {
		((JavascriptExecutor)DriverFactory.getDriver()).executeScript("history.go[0]");
	}
	
	public static void scrollTillElement(WebElement element) {
		((JavascriptExecutor)DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true)", element);
	}
	
	public static void scrollTillPageEnd() {
		((JavascriptExecutor)DriverFactory.getDriver()).executeScript("window.scrollTo(0,document.body.scrollHeight)");	
	}
	
	public static void scrollToTop() {
		((JavascriptExecutor)DriverFactory.getDriver()).executeScript("window.scrollTo(0,0");
	}
	
	public static String getTitleByJS() {
		return ((JavascriptExecutor)DriverFactory.getDriver()).executeScript("return document.title").toString();
	}
	
	//-------------------------Screenshot Utils------------------------------------------------------------------------
	//-------------------------Author: Samsul -------------------------------------------------------------------------
	
	public static byte[] captureScreenshot() {
	    return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
	}

	// For local file storage
	public static void captureScreen(WebDriver driver, String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
        String screenShotName = tname + "-" + timeStamp;
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File target = new File(System.getProperty("user.dir") + "\\Screenshots\\" + screenShotName + ".png");
        FileUtils.copyFile(source, target);
    }
	
	//----------------------------Actions class utils---------------------------------------------------------------------
	//---------------------------Author: Samsul------------------------------------------------------------------------------
	
	public static void hoverElement(WebElement ele) {
		new Actions(DriverFactory.getDriver())
		.moveToElement(ele)
		.perform();
	}
	
	public static void actionsClick(WebElement ele) {
		new Actions(DriverFactory.getDriver())
		.moveToElement(ele)
		.click()
		.perform();
	}
	
	public static void doubleClick(WebElement ele) {
		new Actions(DriverFactory.getDriver())
		.moveToElement(ele)
		.doubleClick()
		.perform();
	}
	
	public static void rightClick(WebElement ele) {
		new Actions(DriverFactory.getDriver())
		.moveToElement(ele)
		.contextClick(ele)
		.perform();		
	}
	
	public static void dragAndDrop(WebElement source, WebElement target) {
		new Actions(DriverFactory.getDriver())
		.dragAndDrop(source, target)
		.perform();
	}
	
	public static void dragAndDropManually(WebElement source, WebElement target) {
		new Actions(DriverFactory.getDriver())
		.clickAndHold(source)
		.moveToElement(target)
		.release()
		.perform();
	}
	
	public static void clickAndHold(WebElement ele) {
		new Actions(DriverFactory.getDriver())
		.clickAndHold(ele)
		.perform();
	}
	
	public static void moveByOffset(int xOffset, int yOffset) {
		new Actions(DriverFactory.getDriver())
		.moveByOffset(xOffset, yOffset)
		.perform();
	}
	
	public static void releaseElement(WebElement ele) {
		new Actions(DriverFactory.getDriver())
		.release(ele)
		.perform();
	}
	
	public static void sendKeys(WebElement ele, String keys) {
		new Actions(DriverFactory.getDriver())
		.moveToElement(ele)
		.click()
		.sendKeys(keys)
		.perform();
	}
	
	//---------------------------Actions class for Keyboard actions-----------------------------------------------
	//--------------------------Author : Samsul ------------------------------------------------------------------
	
	public static void pressKey(Keys key) {
		new Actions(DriverFactory.getDriver())
		.sendKeys(key)
		.perform();
	}
	
	public static void pressKeyCombination(Keys holdKey, Keys key) {
		new Actions(DriverFactory.getDriver())
		.keyDown(holdKey)
		.sendKeys(key)
		.keyUp(holdKey)
		.perform();
	}
	
	public static void keyDown(Keys key) {
		new Actions(DriverFactory.getDriver())
		.keyDown(key)
		.perform();
	}
	
	public static void keyUp(Keys key) {
		new Actions(DriverFactory.getDriver())
		.keyUp(key)
		.perform();
	}
	
	public static void pressMultipleKeys(Keys...keys) {
		new Actions(DriverFactory.getDriver())
		.sendKeys(keys)
		.perform();
	}
	
	public static void pressTab() {
		CommUtils.pressKey(Keys.TAB);
	}
	
	public static void pressEnter(WebElement ele) {
		ele.sendKeys(Keys.ENTER);
	}
	
	public static void pressEscape(WebElement ele) {
		ele.sendKeys(Keys.ESCAPE);
	}
	
	public static void pressBackSpace(WebElement ele) {
		ele.sendKeys(Keys.BACK_SPACE);
	}
	
	public static void pressDelete(WebElement ele) {
		ele.sendKeys(Keys.DELETE);
	}
	
	public static void selectAll(WebElement ele) {
		ele.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	}
	
	public static void copy(WebElement ele) {
		ele.sendKeys(Keys.chord(Keys.CONTROL, "c"));
	}
	
	public static void paste(WebElement ele) {
		ele.sendKeys(Keys.chord(Keys.CONTROL, "v"));
	}
	
	public static void cut(WebElement ele) {
		ele.sendKeys(Keys.chord(Keys.CONTROL, "x"));
	}
	
	public static void undo(WebElement ele) {
		ele.sendKeys(Keys.chord(Keys.CONTROL, "z"));
	}
	
	public static void redo(WebElement ele) {
		ele.sendKeys(Keys.chord(Keys.CONTROL, "y"));
	}
	
	//---------------------------Alert popup handling--------------------------------------------------------------------
	//---------------------------Author : Samsul ------------------------------------------------------------------------
	
	public static void acceptAlert() {
		try {
			Alert alert = DriverFactory.getDriver().switchTo().alert();
			System.out.println("Alert Text: "+alert.getText());
			alert.accept();
			System.out.println("Alert Accepted");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void dismissAlert() {
		try {
			Alert alert = DriverFactory.getDriver().switchTo().alert();
			System.out.println("Alert Text: "+alert.getText());
			alert.dismiss();
			System.out.println("Alert dismissed");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static String getAlertText() {
		try {
			Alert alert = DriverFactory.getDriver().switchTo().alert();
			return alert.getText();
		}catch(Exception e) {
			return null;
		}
	}
	
	public static void sendTextToAlert(String text) {
		try {
			Alert alert = DriverFactory.getDriver().switchTo().alert();
			alert.sendKeys(text);
			alert.accept();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//-----------------------------Dropdown util classes---------------------------------------------------------------------
	//-----------------------------Author: Samsul ---------------------------------------------------------------------------
	
	public static void selectByVisibleText(WebElement dropdown, String visibleText) {
		new Select(dropdown).selectByVisibleText(visibleText);
	}
	
	public static void selectByValue(WebElement dropdown, String value) {
		new Select(dropdown).selectByValue(value);
	}
	
	public static void selectByIndex(WebElement dropdown, int index) {
		new Select(dropdown).selectByIndex(index);
	}
	
	public static List<String> getAllOptions(WebElement dropdown) {
		List<WebElement> options = new Select(dropdown).getOptions();
		List<String> texts = new ArrayList<String>();
		for(WebElement ele: options) {
			texts.add(ele.getText().trim());
		}
		return texts;
	}
	
	public static String getFirstSelectedOptionText(WebElement dropdown) {
		return new Select(dropdown).getFirstSelectedOption().getText().trim();
	}
	
	
	public static boolean isOptionPresent(WebElement dropdown, String text) {
		List<String> texts = getAllOptions(dropdown);
		return texts.contains(text.trim());
	}
	
	public static void selectIfPresent(WebElement dropdown, String visibleText) {
		if(isOptionPresent(dropdown, visibleText)) {
			selectByVisibleText(dropdown, visibleText);
		}else{
			throw new IllegalArgumentException("option "+visibleText+" is not present in the dropdown");
		}
	}
	
	public static boolean isMultiple(WebElement droopdown) {
		return new Select(droopdown).isMultiple();
	}
	
	public static void selectAl1Options(WebElement dropdown) {
		List<String> options =getAllOptions(dropdown);
		if(isMultiple(dropdown)) {
			for(String option: options) {
				selectByVisibleText(dropdown, option);
			}
		}
	}
	
	public static void deselectAllOptions(WebElement dropdown) {
		Select select = new Select(dropdown);
		
		if(isMultiple(dropdown)) {
			select.deselectAll();
		}else {
			throw new UnsupportedOperationException("dropdown is not multi-select");
		}
	}
	
	public static List<String> getSelectedOptions(WebElement dropdown) {
		List<WebElement> selectedOptions = new Select(dropdown).getAllSelectedOptions();
		List<String> selectedTexts = new ArrayList<String>();
		for(WebElement selectedOption: selectedOptions) {
			selectedTexts.add(selectedOption.getText().trim());
		}
		return selectedTexts;
	}
	
	public static boolean areAllOptionsSelected(WebElement dropdown) {
		if(!isMultiple(dropdown)) {
			throw new UnsupportedOperationException("Cannot verify all options in a single-select dropdown");
		}
		
		List<String> allOptions = getAllOptions(dropdown);
		List<String> selectedOptions = getSelectedOptions(dropdown);
		
		return allOptions == selectedOptions;
	}
	
	//------------------------Upload using Robot class------------------------------------------------------------------------
	//------------------------Author: Samsul ---------------------------------------------------------------------------------
	
	public static void uploadFile(String filePath) throws AWTException {
		Robot robot = new Robot();
		robot.setAutoDelay(3000);
		
		//CONTROL+C
		StringSelection fPath = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fPath, null);
		
		//CONTROL+V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.setAutoDelay(3000);
	}
	
	//--------------------Frames handling------------------------------------------------------------------------------------
	//--------------------Author : Samsul -----------------------------------------------------------------------------------
	
	public static void switchToFramebyIndex(int index, int timeoutSeconds) {
		new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds))
		.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}
	
	public static void switchToFrameByNameOrId(String nameOrId, int timeoutSeconds) {
		new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds))
		.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
	}
	
	public static void switchToFrameByElement(WebElement element, int timeoutSeconds) {
		new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds))
		.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}
	
	public static void switchtoFrameByLocator(By locator, int timeoutSeconds) {
		new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds))
		.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	//------------------------Comparing images -------------------------------------------------------------------------------
	//------------------------Author: Samsul ---------------------------------------------------------------------------------
	
	public static boolean compareImages(WebElement element, String filePath) throws IOException {
		BufferedImage actualImage = ImageIO.read(new File(filePath));		
		Screenshot screenshot = new AShot().takeScreenshot(DriverFactory.getDriver(), element);		
		BufferedImage expectedImage = screenshot.getImage();
		
		ImageDiffer imgDiff = new ImageDiffer();
		ImageDiff diff =imgDiff.makeDiff(expectedImage, actualImage);
		
		return !diff.hasDiff();		
		
	}
	
	//---------------------Cookie Handling -----------------------------------------------------------------------------------
	//---------------------Author: Samsul ------------------------------------------------------------------------------------
	public static Set<Cookie> getAllCookies(){
		return DriverFactory.getDriver().manage().getCookies();
	}
	
	public static Set<String> getNameOfAllCookies(){
		Set<Cookie> cookies = DriverFactory.getDriver().manage().getCookies();
		Set<String> cookie = new HashSet<String>();
		
		for(Cookie c: cookies) {
			cookie.add(c.getName());
		}
		return cookie;
	}
	
	public static Set<String> getValueOfAllCookies(){
		Set<Cookie> cookies = DriverFactory.getDriver().manage().getCookies();
		Set<String> cookieVal = new HashSet<String>();
		
		for(Cookie c: cookies) {
			cookieVal.add(c.getValue());
		}
		return cookieVal;
	}
	
	public static Cookie getCookies(String cookieName) {
		return DriverFactory.getDriver().manage().getCookieNamed(cookieName);
	}
	
	public static void addCookie(String cookieName, String cookieValue) {
		Cookie cObj = new Cookie(cookieName, cookieValue);
		DriverFactory.getDriver().manage().addCookie(cObj);
	}
	
	public static void deleteAllCookies() {
		DriverFactory.getDriver().manage().deleteAllCookies();
	}
	
	public static void deleteCookie(Cookie cookie) {
		DriverFactory.getDriver().manage().deleteCookie(cookie);
	}
	
	public static void deleteCookie(String cookie) {
		DriverFactory.getDriver().manage().deleteCookieNamed(cookie);
	}
	
	public static int getSizeOfCookie() {
		return DriverFactory.getDriver().manage().getCookies().size();
	}
	
	//------------------------Get text from Barcode and QR Code --------------------------------------------------------
	//------------------------Author: Samsul ---------------------------------------------------------------------------
	
	public static String getTextOfScannableCode(WebElement ele, int timeoutSeconds) throws IOException, NotFoundException {
		String url = CommUtils.getAttribute(ele,timeoutSeconds,"src");
		
		URL urlink = new URL(url);
		BufferedImage img = ImageIO.read(urlink);
		LuminanceSource luminous = new BufferedImageLuminanceSource(img);
		BinaryBitmap bitMap = new BinaryBitmap(new HybridBinarizer(luminous));
		Result result = new MultiFormatReader().decode(bitMap);
		return result.getText();
	}
	
	
	
}
