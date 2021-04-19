package util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DeleteVisibleChatAndScrollUp {
	private static WebDriver driver;
	static String profilePath= "C:\\Users\\rites\\AppData\\Local\\Google\\Chrome\\User Data";
	static String chatURL = "https://teams.microsoft.com/_?culture=en-in&country=IN&lm=deeplink&lmsrc=NeutralHomePageWeb&cmpid=WebSignIn#/conversations/19:meeting_MjBmNjkxOGMtOGU2ZC00ZmMwLWFkMTYtNmIxZDg0M2FiZDNm@thread.v2?ctx=chat";
	static String username = "Ritesh Kawadkar";
	
	public static void main(String[] args) throws FindFailed, InterruptedException, AWTException {
		// TODO Auto-generated method stub
		checkLoginPageURL();
	}
	
	public static void checkLoginPageURL() throws InterruptedException, FindFailed, AWTException {
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();		
        
		options.addArguments("user-data-dir="+ profilePath);
		driver = new ChromeDriver(options);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to(chatURL);
		
		
		
		while(Util.isNewElementPresentFast(driver, "//*[@class='vr-item-placeholders']/div")==false) 
		{
			 Robot robot = new Robot();
			  robot.mouseWheel(-5);
		}
		
		int allLoadedMessageListCount= driver.findElements(By.xpath("//*[@class='vr-item-placeholders']/div")).size();
		for (int i = 0; i < allLoadedMessageListCount; i++) {
			
			//Scroll up one element at a time
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@data-scroll-pos=" + i + "]")));
			
			if(Util.isElementPresentFast(driver, "//div[@data-scroll-pos=" + i + "]//div[contains(text(), '" + username + "')]//parent::div//parent::div//parent::div[@id='messageBody']")) 
			{
				System.out.println("Element found at scroll position = " + i);
				deleteVisibleChat(i);	
			}
			
			
			
		}
		
		driver.quit();
		
		
		
		
	}
	
	


	private static void deleteVisibleChat(int pos) throws InterruptedException {
		// TODO Auto-generated method stub
		//get list of all messages
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				//WebElement chatBubble = driver.findElement(By.xpath("//div[@data-scroll-pos=" + pos + "]"));
				WebElement chatBubble = driver.findElement(By.xpath("//div[@data-scroll-pos=" + pos + "]//div[contains(text(), '" + username + "')]//parent::div//parent::div//parent::div[@id='messageBody']/div"));
				WebElement chatBubble1 = driver.findElement(By.xpath("//div[@data-scroll-pos=" + pos + "]//div[contains(text(), '" + username + "')]//parent::div//parent::div//parent::div[@id='messageBody']"));

				//list of id 
				WebElement id= driver.findElement(By.xpath("//div[@data-scroll-pos=" + pos + "]//div[contains(text(), '" + username + "')]//parent::div//parent::div//parent::div[@id='messageBody']/parent::div/parent::div"));

				String uid = id.getAttribute("id").replace("m", "");
				System.out.println(pos + " | ID of chat bubble found = " + uid);


				Util.highlightElement(driver, chatBubble);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chatBubble1);
				Thread.sleep(2000); 
				
				
			
				Actions action = new Actions(driver);
				chatBubble.click();
				Thread.sleep(100); 
				action.moveToElement(chatBubble1);
				action.build().perform();
				Thread.sleep(1000); 
				
				
				if(Util.isElementPresent(driver, "//*[@id='messageActions']/message-actions")) {
					driver.findElement(By.xpath("//*[@id='messageActions']/message-actions")).click();
					Thread.sleep(300);
				}
				
				if(Util.isElementPresent(driver, "//*[@id='messageActionDropdown']/ul/li[3]/button/span")) {
					driver.findElement(By.xpath("//*[@id='messageActionDropdown']/ul/li[3]/button/span")).click();
					Thread.sleep(1000);
				}
				
				
				try {
					//confirm deleteion 
					if(Util.isNewElementPresentFast(driver, "//*[starts-with(@id,'deleted-message-" + id + "')]")) {
						driver.findElement(By.xpath("//*[starts-with(@id,'deleted-message-" + id + "')]")).click();
						System.out.println(id + " - Deleted Succesfully");
					}
					
				} catch (Exception e) {
					System.out.println(pos + " - Not Deleted");
				}
					
				
					
					
				
	}
	
	
	

}

