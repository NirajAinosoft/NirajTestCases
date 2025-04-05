package myfirstTest;

import java.time.Duration;
import java.util.Set;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FirstTestCase {

    static WebDriver driver;
    static WebDriverWait wait;
    static JavascriptExecutor js;
    static String mainWindowHandle;

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
    void login() throws InterruptedException {
        driver.get("https://devtest.appops.org/BuilderService/login.html");
        mainWindowHandle = driver.getWindowHandle();

        WebElement googleSignIn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='google_signin']")));
        googleSignIn.click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("identifierId")));
        emailField.sendKeys("Niraj120205@gmail.com");

        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Next']")));
        nextButton.click();

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.name("Passwd")));
        passwordField.sendKeys("Niraj$123");

        WebElement nextButtonPass = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Next']")));
        nextButtonPass.click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(1));
        driver.switchTo().window(mainWindowHandle);

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(
            "https://devtest.appops.org/BuilderService/login.html")));
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
    }

    @Test
    @Order(2)
    void Openapp() throws InterruptedException {
        wait.until(ExpectedConditions.urlContains("BuilderService"));

        By appSelector = By.cssSelector("div.service-dashboard__service-app-item[data-app-id='72']");
        WebElement appElement = wait.until(ExpectedConditions.presenceOfElementLocated(appSelector));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", appElement);
        appElement = wait.until(ExpectedConditions.elementToBeClickable(appSelector));
        appElement.click();
        Thread.sleep(2000);
    }

    @Test
    @Order(3)
    void addTextBox() throws InterruptedException {
        WebElement sourceElement = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='tool-text']")));
        sourceElement.click();

        WebElement masterPage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[@id='master-page']")));

        Actions actions = new Actions(driver);
        actions.moveToElement(masterPage)
               .moveByOffset(100, -55)
               .clickAndHold()
               .pause(200)
               .moveByOffset(1, 1)
               .release()
               .build()
               .perform();

        Thread.sleep(2000);
    }

    @Test
    @Order(4)
    void addButtonclick() throws InterruptedException {
        WebElement sourceElement = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='tool-button']")));
        sourceElement.click();

        WebElement masterPage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[@id='master-page']")));

        Actions actions = new Actions(driver);
        actions.moveToElement(masterPage)
               .moveByOffset(150, -80)
               .clickAndHold()
               .pause(200)
               .moveByOffset(1, 1)
               .release()
               .build()
               .perform();

        Thread.sleep(2000);
    }

    @Test
    @Order(5)
    void eyeButton() throws InterruptedException {
        String currentTab = driver.getWindowHandle();

        WebElement eyeBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//img[@id='shortcut-preview']")));
        eyeBtn.click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> allTabs = driver.getWindowHandles();
        String previewTab = null;

        for (String tab : allTabs) {
            if (!tab.equals(currentTab)) {
                previewTab = tab;
                break;
            }
        }

        if (previewTab != null) {
            driver.switchTo().window(previewTab);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
            Thread.sleep(2000); // Optional - Preview tab loaded
            driver.close(); // Close preview tab
        }

        driver.switchTo().window(currentTab); // Switch back to main
        System.out.println("✅ Eye preview completed and returned to dashboard.");
    }

    @AfterAll
    static void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
        }
    }
}
