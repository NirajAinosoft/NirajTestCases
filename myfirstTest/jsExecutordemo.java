package myfirstTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class jsExecutordemo {
   
    WebDriver driver;
    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://testautomationpractice.blogspot.com/"); 
    }
    
    @Test
    public void sendName() throws InterruptedException {
       
        WebElement InputBox = driver.findElement(By.xpath("//input[@id='name']"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].setAttribute('value','john')",InputBox);
        
      //for scrolling till pixel 
//        js.executeScript("window.scrollBy(0,500)", "");
        
//        //for till viewing elelment
//        WebElement tillE=driver.findElement(By.xpath("//h2[@class='title']"));
//        js.executeScript("arguments[0].scrollIntoView();",tillE);
       
       
        //for scrolling last of page
        
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        
        //for getting position of scroll bar
        System.out.println(js.executeScript("return window.pageYOffset"));
        Thread.sleep(5000);
        //for back to scroll up top position use - sysmbol
        js.executeScript("window.scrollBy(0,-document.body.scrollHeight)");
        System.out.println(js.executeScript("return window.pageYOffset"));
        
        WebElement RBtn= driver.findElement(By.xpath("//input[@id='male']"));
        js.executeScript("arguments[0].click()",RBtn);
        
        
    }
    
//    @AfterEach
//    void tearDown() {
//            driver.quit(); // Use quit() instead of close() to end session properly

    
}