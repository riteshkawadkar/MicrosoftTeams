package util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Util {
	
	public static boolean isElementPresent(WebDriver driver, String elementXpath){
	    boolean foundElement = false;
	    WebDriverWait wait = new WebDriverWait(driver, 5 /*timeout in seconds*/);
	    try {
	        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
	        foundElement = true;
	    } catch (Exception eTO) {
	    	foundElement = false;
	    }
	    
		return foundElement;
	   
	}
	
	
	
	public static boolean isElementPresent(WebDriver driver, WebElement element){
	    boolean foundElement = false;
	    WebDriverWait wait = new WebDriverWait(driver, 5 /*timeout in seconds*/);
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
	
	



	public static boolean isElementPresentFast(WebDriver driver, String locator) {
		 boolean foundElement = false;
		 driver.manage().timeouts().implicitlyWait(50, TimeUnit.MILLISECONDS );
		try 
		{
			return driver.findElement(By.xpath(locator)).isDisplayed();
	        
		} 
		
		catch (Exception eTO)
		{
	    	foundElement = false;
	    }
		return foundElement;
	}
	
	
	public static boolean isNewElementPresentFast(WebDriver driver, String locator) {
		 boolean foundElement = false;
		 driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS );
		try 
		{
			return driver.findElement(By.xpath(locator)).isDisplayed();
	        
		} 
		
		catch (Exception eTO)
		{
	    	foundElement = false;
	    }
		return foundElement;
	}
	
}
