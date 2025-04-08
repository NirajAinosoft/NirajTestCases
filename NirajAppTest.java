package myfirstTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.Set;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NirajAppTest {

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    @Test
    @Order(1)
    void login() {
        driver.get("https://devtest.appops.org/BuilderService/login.html");
        mainWindowHandle = driver.getWindowHandle();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='google_signin']"))).click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId"))).sendKeys("Niraj120205@gmail.com");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Next']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd"))).sendKeys("Niraj$123");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Next']"))).click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(1));
        driver.switchTo().window(mainWindowHandle);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://devtest.appops.org/BuilderService/login.html")));
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
    }
    @Test
    @Order(2)
    void openApp() {
        wait.until(ExpectedConditions.urlContains("BuilderService"));

        // Wait until dashboard loads
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.cssSelector("div.service-dashboard__service-app-item")));

        By appSelector = By.cssSelector("div.service-dashboard__service-app-item[data-app-id='71']");
        WebElement appElement = wait.until(ExpectedConditions.elementToBeClickable(appSelector));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", appElement);
        js.executeScript("arguments[0].click();", appElement);

        System.out.println("Clicked app card, waiting for canvas...");

        // Wait for master page to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("master-page")));
        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        // Assert that we're no longer on dashboard
        boolean stillOnDashboard = driver.findElements(By.cssSelector("div.service-dashboard__service-app-item")).size() > 0;
        assertFalse("App did not load properly, still on dashboard!", stillOnDashboard);

        // Confirm stable canvas UI
        assertTrue("Canvas not loaded properly!",
            driver.findElements(By.id("master-page")).size() > 0);
    }
    
    @Test
    @Order(3)
    public void createBlackContainerOnly() throws InterruptedException {
        
    	wait.until(ExpectedConditions.presenceOfElementLocated(By.id("master-page")));

    	// Click on Container Icon
    	clickElement(By.xpath("//*[@id='toolbar__container-element-list']/div[1]"), "Container icon not visible");
      Thread.sleep(1000);
    	// Click on Design Area to Place Container
    	clickDesignArea(0, 65);

    	//  Wait for container to be placed
    	wait.until(ExpectedConditions.presenceOfElementLocated(
    	    By.xpath("//div[starts-with(@id,'aoe-container')]")));
    	

    	// Click on the container to activate sidebar
    	WebElement container = wait.until(ExpectedConditions.elementToBeClickable(
    	    By.cssSelector("div[id^='aoe-container']")));
    	js.executeScript("arguments[0].scrollIntoView(true);", container);
    	js.executeScript("arguments[0].click();", container);
    	

    	// Click on Appearance Tab
    	WebElement appearanceTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("pe-tab_appearance")));
    	appearanceTab.click();
    	System.out.println("Appearance tab clicked.");

    	//  Set size,backgrdColour,padding,radius
    	setElementSize(100, "%", 60, "px");
    	setBackgroundColor("#000000");
    	setPadding("padding-right", "16");
    	setBorderRadius(driver, "20");
        System.out.println("Container setup done ✅");
        
    }

 
   

    
    
    
    
  // ------------>>>> utilss for usess ..........
    
    public void setBorderRadius(WebDriver driver, String radiusValue) {
        // Step 1: Expand <ao-border>
        WebElement aoBorder = driver.findElement(By.cssSelector("ao-border"));
        SearchContext shadowRoot1 = aoBorder.getShadowRoot();

        // Step 2: Inside first shadow DOM, find <ao-dimension data-attribute='border-radius'>
        WebElement aoDimension = shadowRoot1.findElement(By.cssSelector("ao-dimension[data-attribute='border-radius']"));
        SearchContext shadowRoot2 = aoDimension.getShadowRoot();

        // Step 3: Find input and set value
        WebElement input = shadowRoot2.findElement(By.cssSelector("input.magnitude"));
        input.clear();
        input.sendKeys(radiusValue);

        // Optional: trigger update
        input.sendKeys(Keys.TAB);
    }


    private void clickElement(By locator, String errorMessage) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        assertTrue(errorMessage, element.isDisplayed());
        element.click();
    }

	private void clickDesignArea(int x, int y) {
		WebElement designArea = driver.findElement(By.id("master-page"));
		assertTrue("Design area not visible", designArea.isDisplayed());
		int adjustedX = x - (designArea.getSize().getWidth() / 2);
		int adjustedY = y - (designArea.getSize().getHeight() / 2);
		new Actions(driver).moveToElement(designArea, adjustedX, adjustedY).click().perform();
	}

    private void setElementSize(int width, String widthUnit, int height, String heightUnit) {
        WebElement sizeHost = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pe-panel_appearance\"]/ao-size")));
        setDimension(sizeHost, "nth-child(1)", width, widthUnit); // Width
        setDimension(sizeHost, "nth-child(2)", height, heightUnit); // Height
    }

    private void setDimension(WebElement sizeHost, String childSelector, int value, String unit) {
        WebElement dimension = (WebElement) ((JavascriptExecutor) driver).executeScript(
            "return arguments[0].shadowRoot.querySelector('details > p > ao-dimension:" + childSelector + "')",
            sizeHost);
        WebElement input = getShadowElement(dimension, "p > input");
        input.clear();
        input.sendKeys(String.valueOf(value));
        WebElement unitDropdown = getShadowElement(dimension, "p > select");
        unitDropdown.sendKeys(unit);
    }

    private WebElement getShadowElement(WebElement host, String selector) {
        return (WebElement) js.executeScript("return arguments[0].shadowRoot.querySelector(arguments[1])", host, selector);
    }


    private void setBackgroundColor(String color) {
        // 1. Get the host shadow root element
        WebElement bgHost = driver.findElement(By.xpath("//*[@id=\"pe-panel_appearance\"]/ao-background"));

        // 2. Navigate inside first shadow DOM: ao-background → ao-color-picker
        WebElement colorPicker = getShadowElement(bgHost, "details > p > ao-color-picker");

        // 3. Navigate inside second shadow DOM: ao-color-picker → input.color
        WebElement colorInput = getShadowElement(colorPicker, "input.color");

        // 4. Inject value + dispatchEvent with bubbles so UI reacts
        executeScript(
            "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            colorInput, color);
    }

	private void executeScript(String script, WebElement element, String value) {
		((JavascriptExecutor) driver).executeScript(script, element, value);
	}
	



	private void setPadding(String paddingType, String value) {
		WebElement spacingHost = driver.findElement(By.xpath("//*[@id=\"pe-panel_appearance\"]/ao-spacing"));
		WebElement padding = getShadowElement(spacingHost, "details > p > ao-number-input." + paddingType);
		WebElement input = getShadowElement(padding, "p > input[type=number]");
		input.sendKeys(value);
	}


     


   @Test
   @Order(4)
    void addButtonANDEyeButton() throws InterruptedException {
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
        
        //EYEBTN 
        
        masterPage.wait(2000);
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
    }}

   
//    @AfterAll
//    static void tearDown() throws InterruptedException {
//        Thread.sleep(3000);
//        if (driver != null) {
//            driver.quit();
//        }
//   }

