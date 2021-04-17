package util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Util {
	
	public static boolean isElementPresent(WebDriver driver, String elementXpath){
	    boolean foundElement = false;
	    WebDriverWait wait = new WebDriverWait(driver, 90 /*timeout in seconds*/);
	    try {
	        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
	        foundElement = true;
	    } catch (TimeoutException eTO) {
	    	foundElement = false;
	    }
	    
		return foundElement;
	   
	}
	
	public static boolean isElementPresent(WebDriver driver, WebElement element){
	    boolean foundElement = false;
	    WebDriverWait wait = new WebDriverWait(driver, 90 /*timeout in seconds*/);
	    try {
	        wait.until(ExpectedConditions.visibilityOf(element));
	        foundElement = true;
	    } catch (TimeoutException eTO) {
	    	foundElement = false;
	    }
	    
		return foundElement;
	   
	}
	
	
	public static void highlightElement(WebDriver driver, WebElement element) {
	   
		JavascriptExecutor js = (JavascriptExecutor) driver;
	        //use executeScript() method and pass the arguments 
	        //Here i pass values based on css style. Yellow background color with solid red color border. 
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
	}
	
	
	
}
