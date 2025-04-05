package myfirstTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class amazonS {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://www.amazon.in");
		driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("dell");
		driver.findElement(By.id("nav-search-submit-button")).click();
		
//		//another way by name 
//		driver.findElement(By.className("nav-input nav-progressive-attribute")).sendKeys("dell laptop");
//		driver.findElement(By.className("nav-input nav-progressive-attribute")).click();
		
		
	}

}
