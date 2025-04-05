package myfirstTest;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class fisrtTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test
    void testLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Enter Username
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='username']")));
        username.sendKeys("Admin");

        // Enter Password
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='password']")));
        password.sendKeys("admin123");

        // Click Login Button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        loginButton.click();

        System.out.println("Login Successful!");

        //dashboard visibilityy
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h6")));
        String actualRes = element.getText();
        String expectedRes = "Dashboard";

        assertEquals(expectedRes, actualRes, "Dashboard text does not match!");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}

