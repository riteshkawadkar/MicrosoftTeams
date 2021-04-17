package util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DeleteChat {
	private static WebDriver driver;
	

	public static void main(String[] args) throws FindFailed, InterruptedException {
		// TODO Auto-generated method stub
		checkLoginPageURL();
	}
	
	public static void checkLoginPageURL() throws InterruptedException, FindFailed {
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();		
        String dir= "C:\\Users\\rites\\AppData\\Local\\Google\\Chrome\\User Data";
		options.addArguments("user-data-dir="+dir);
		driver = new ChromeDriver(options);
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("https://teams.microsoft.com/_?culture=en-in&country=IN&lm=deeplink&lmsrc=NeutralHomePageWeb&cmpid=WebSignIn#/conversations/19:meeting_NjAwYzNjMjYtZWIwYy00ODczLTg0YzMtNjEyNzI3YzdlZmRi@thread.v2?ctx=chat");
		
		//get list of messages
		List<WebElement> chatOriginsList = driver.findElements(By.xpath("//*[starts-with(@data-scroll-id,'8:orgid')]/div/thread/div//*[@id='messageBody']//*[@data-tid='messageBodyContainer']"));
		
		int i=1;
		//list of id 
		List<WebElement> ids= driver.findElements(By.xpath("//*[starts-with(@data-scroll-id,'8:orgid')]/div/thread/div//*[@id='messageBody']//*[@data-tid='messageBodyContainer']//ancestor::div[@class='clearfix']"));
		for (WebElement cids : ids) {
			String id = cids.getAttribute("id").replace("t", "");
			System.out.println(i + " = " + id);
			i++;
		}
		
		WebElement chatId; 
		WebElement deletedChatBubble;
		Actions action = new Actions(driver);
		
		for (WebElement chatBubble : chatOriginsList) {
			
			
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chatBubble);
			Thread.sleep(2000); 
			Util.highlightElement(driver, chatBubble);
			
			//get id
			chatId = driver.findElement(By.xpath("//*[starts-with(@data-scroll-id,'8:orgid')]/div/thread/div//*[@id='messageBody']//*[@data-tid='messageBodyContainer']//ancestor::div[@class='clearfix']"));

			
			String id = chatId.getAttribute("id").replace("t", "");
			System.out.println(id);
			
			chatBubble.click();
			action.moveToElement(chatBubble);
			action.build().perform();
			Thread.sleep(2000); 
			
			
			if(Util.isElementPresent(driver, "//*[@id='messageActions']/message-actions")) {
				driver.findElement(By.xpath("//*[@id='messageActions']/message-actions")).click();
				Thread.sleep(1000);
			}
			
			if(Util.isElementPresent(driver, "//*[@id='messageActionDropdown']/ul/li[3]/button/span")) {
				driver.findElement(By.xpath("//*[@id='messageActionDropdown']/ul/li[3]/button/span")).click();
				//Thread.sleep(1000);
			}
			
			
			try {
				//confirm deleteion 
				if(Util.isElementPresent(driver, "//*[starts-with(@id,'deleted-message-" + id + "')]")) {
					System.out.println(id + " - Deleted Succesfully");
					
					
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(id + " - Not Deleted");
				//driver.quit();
			}
			
			
			
			
		}
		
		driver.quit();
		
		
		
		
	}

}
