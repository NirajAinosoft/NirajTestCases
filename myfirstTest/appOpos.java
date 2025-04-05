package myfirstTest;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class appOpos {
	WebDriver driver;
	
	
@BeforeEach
void setup() {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("https://devtest.appops.org/BuilderService/");

}

@Test
void clickTest() throws InterruptedException {
//	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    driver.findElement(By.xpath("//a[@routerlink='/guides/what-is-appops']")).click();
//    Thread.sleep(2000);
//    
    
}

@AfterEach
void tearDown() {
//	driver.close();
}

}
