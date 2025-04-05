package myfirstTest;

import java.sql.Driver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class shadowDom {
	WebDriver driver;
	@BeforeEach
	void setup() {
		WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();
	    driver.manage().window().maximize();
	    driver.get("https://testautomationpractice.blogspot.com/");
	    
	}
	
	@Test
	void findUsingShadow() throws InterruptedException{
		
		
		SearchContext shadow = driver.findElement(By.cssSelector("#shadow_host")).getShadowRoot();
		Thread.sleep(1000);
		 WebElement Ele = shadow.findElement(By.cssSelector("input[type='text']"));
//		 JavascriptExecutor js = (JavascriptExecutor)driver;
//		 WebElement tillE=(WebElement) driver.findElement(By.cssSelector("#shadow_host")).getShadowRoot();
//			shadow.findElement(By.cssSelector("input[type='text']"));
//			js.executeScript("arguments[0].scrollIntoView();",tillE);
		Ele.click();
		Ele.sendKeys("niraj");
		
	}

}
