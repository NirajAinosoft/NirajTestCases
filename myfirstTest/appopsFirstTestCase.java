package myfirstTest;

import java.time.Duration;


import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;





@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class appopsFirstTestCase {

    static WebDriver driver;
    static WebDriverWait wait;
    static JavascriptExecutor js;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
    }

    @Test
    @Order(1)
    void login() {
        driver.get("https://devtest.appops.org/BuilderService/login.html");
        String firstWindow = driver.getWindowHandle();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='google_signin']"))).click();

        wait.until(d -> d.getWindowHandles().size() > 1);
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(firstWindow)) {
                driver.switchTo().window(win);
                break;
            }
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId"))).sendKeys("Niraj120205@gmail.com");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Next']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd"))).sendKeys("Niraj$123");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Next']"))).click();

        wait.until(d -> d.getWindowHandles().size() >= 2);
        for (String win : driver.getWindowHandles()) {
            driver.switchTo().window(win);
            if (driver.getCurrentUrl().contains("BuilderService")) {
                break;
            }
        }
    }
    @Test
    @Order(2)
    void openAppByScroll() {
        By appSelector = By.xpath("//div[@class='service-dashboard__service-app-item' and @data-app-id='72']");

        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(appSelector));

        // Scroll to the element
        js.executeScript("arguments[0].scrollIntoView();", element);

        // Click the element
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

//    @AfterAll
//    static void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}

