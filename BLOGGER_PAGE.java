package myfirstTest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.font.TextHitInfo;
import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import io.github.bonigarcia.wdm.WebDriverManager;
public class BLOGGER_PAGE {

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
    void fullFlowTest() throws InterruptedException {
        login();
        openApp();

        // 1st container
        clickContainerButton();
        clickMasterPage();
        setXYPosition(driver,"0", "0");
        placeAndStyleContainer("#000000", "100", "%", "60", "px");
        setXYPosition(driver,"0", "0");
       
       

        // 2nd container
        clickContainerButton(); // again
        clickMasterPage();      // again
        placeAndStyleContainer("#FFFFFF", "1183", "px", "729", "px");
        setXYPosition(driver,"10", "70");

        //btn
        dragToolButtonToContainer(driver);
        setXYPosition(driver,"1006", "-56");
        updateButtonText("Create");
        
        dragRepeatingListToContainer(driver);
        placeAndStyleRep("590", "px", "273", "px");
        setXYPosition(driver,"11.5", "6");
        selRelativeInPos();
       
     
        
        dragTextButtonToContainer(driver);
        updateButtonText("Title:");
        setXYPosition(driver,"46", "52");
        
        dragTextButtonToContainer(driver);
        updateButtonText("Author:");
        setXYPosition(driver,"42", "139");
        
        
        dragImageBoxToContainer(driver);
        setXYPosition(driver,"265", "18");
      
        
        //createNewPage();
        


        // Drag & Drop Text Element to 2nd Container
        
       // dragButtonToMasterPage();
       
    }
    
   

    void login() {
        driver.get("https://devtest.appops.org/BuilderService/login.html");
        mainWindowHandle = driver.getWindowHandle();

        wait.until(ExpectedConditions.elementToBeClickable(By.className("google_signin"))).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId"))).sendKeys("Niraj120205@gmail.com");
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd"))).sendKeys("Niraj$123");
        driver.findElement(By.xpath("//span[text()='Next']")).click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(1));
        driver.switchTo().window(mainWindowHandle);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://devtest.appops.org/BuilderService/login.html")));
    }

    void openApp() {
        wait.until(ExpectedConditions.urlContains("BuilderService"));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.service-dashboard__service-app-item")));
        WebElement app = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.service-dashboard__service-app-item[data-app-id='74']")));
        js.executeScript("arguments[0].scrollIntoView(true);", app);
        app.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("master-page")));
    }
    
    public void setXYPosition(WebDriver driver, String xValue, String yValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Set X coordinate
        WebElement inputX = (WebElement) js.executeScript(
            "return document.querySelector('ao-position')" +
            ".shadowRoot.querySelector('ao-dimension[data-attribute=\"left\"]')" +
            ".shadowRoot.querySelector('input.magnitude')"
        );
        inputX.clear();
        inputX.sendKeys(xValue);

        // Set Y coordinate
        WebElement inputY = (WebElement) js.executeScript(
            "return document.querySelector('ao-position')" +
            ".shadowRoot.querySelector('ao-dimension[data-attribute=\"top\"]')" +
            ".shadowRoot.querySelector('input.magnitude')"
        );
        inputY.clear();
        inputY.sendKeys(yValue);
    }


    public void dragToolButtonToContainer(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // Wait and locate the draggable tool button from the visual element list
        WebElement toolButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//details[@id='toolbar__visual-element-list']//div[@class='tool-button' and @draggable='true']")
        ));

        // Wait and locate the active target container
        WebElement targetContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@class='wrapper mutation-ignore active']//div[contains(@class, 'aose-container-default') and contains(@class, 'mutation-ignore')]")
        ));

        // Perform drag and drop
        actions.moveToElement(toolButton)
               .clickAndHold()
               .pause(Duration.ofMillis(500))  // optional pause for stability
               .moveToElement(targetContainer)
               .pause(Duration.ofMillis(500))
               .release()
               .build()
               .perform();
    }
    
    public void dragTextButtonToContainer(WebDriver driver) {
    	
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // Wait and locate the draggable tool button from the visual element list
        WebElement TextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tool-text']")));

        // Wait and locate the active target container
        WebElement targetRepList= wait.until(ExpectedConditions.visibilityOfElementLocated(
        		By.xpath("//div[contains(@id, 'repeating-list-container')]")));

        // Perform drag and drop
        actions.moveToElement(TextButton)
               .clickAndHold()
               .pause(Duration.ofMillis(500))  // optional pause for stability
               .moveToElement(targetRepList)
               .pause(Duration.ofMillis(500))
               .release()
               .build()
               .perform();
    }
    public void dragRepeatingListToContainer(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
       
            // First, locate the Repeating List element from the toolbox
            // Using the class attribute which appears more stable
            WebElement repeatingList = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'tool-repeating_list') or contains(text(), 'Repeating List')]")));
          
            
            // Find the target container without relying on specific IDs
            // Using attributes that appear in your HTML structure
            WebElement targetContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[@data-type='container' and contains(@class, 'aose-container-default')])[2]")));
                // Second approach: more controlled movement
                actions.clickAndHold(repeatingList)
                       .pause(Duration.ofMillis(500))
                       .moveToElement(targetContainer)
                       .pause(Duration.ofMillis(500))
                       .release()
                       .build()
                       .perform();}
 public void dragImageBoxToContainer(WebDriver driver) {
    	
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        // Wait and locate the draggable tool button from the visual element list
        WebElement TextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tool-image']")));

        // Wait and locate the active target container
        WebElement targetRepList= wait.until(ExpectedConditions.visibilityOfElementLocated(
        		By.xpath("(//div[@data-type='container' and contains(@class, 'aose-container-default')])[2]")));

        // Perform drag and drop
        actions.moveToElement(TextButton)
               .clickAndHold()
               .pause(Duration.ofMillis(500))  // optional pause for stability
               .moveToElement(targetRepList)
               .pause(Duration.ofMillis(500))
               .release()
               .build()
               .perform();
    }
    public void updateCellHeight(WebDriver driver, String newCellHeight) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Locate the textarea element containing the JSON configuration
            WebElement jsonTextarea = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("textarea.config-textarea")
            ));

            // Scroll into view to ensure visibility
            js.executeScript("arguments[0].scrollIntoView(true);", jsonTextarea);

            // Get the current JSON text
            String currentJson = jsonTextarea.getAttribute("value");

            // Parse and update the JSON using string manipulation or a JSON library
            if (currentJson.contains("\"cellHeight\": \"100%\"")) {
                String updatedJson = currentJson.replace("\"cellHeight\": \"100%\"", "\"cellHeight\": \"" + newCellHeight + "\"");

                // Clear the textarea and input the updated JSON
                jsonTextarea.clear();
                jsonTextarea.sendKeys(updatedJson);

                System.out.println("Updated JSON successfully.");
            } else {
                System.out.println("JSON does not contain 'cellHeight' with value '100%'.");
            }
        } catch (Exception e) {
            System.out.println("Failed to update JSON: " + e.getMessage());
        }
    }





    void clickContainerButton() throws InterruptedException {
        WebElement containerIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='toolbar__container-element-list']/div[1]")));
        containerIcon.click();
        Thread.sleep(1000);
    }

    void clickMasterPage() {
        WebElement masterPage = wait.until(ExpectedConditions.elementToBeClickable(By.id("master-page")));
        js.executeScript("arguments[0].scrollIntoView(true);", masterPage);
        masterPage.click();
    }
    public void selRelativeInPos() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        String script = "return document.querySelector('ao-position')" +
                       ".shadowRoot.querySelector('ao-dropdown[data-attribute=\"position\"]')" +
                       ".shadowRoot.querySelector('select')";
        
        WebElement selectElement = (WebElement) ((JavascriptExecutor) driver).executeScript(script);
        wait.until(ExpectedConditions.elementToBeClickable(selectElement));
        
        Select dropdown = new Select(selectElement);
        
        // Try multiple ways to select in case one fails
        try {
            dropdown.selectByVisibleText("Relative");
        } catch (Exception e1) {
            try {
                dropdown.selectByValue("relative");
            } catch (Exception e2) {
                try {
                    dropdown.selectByIndex(2); // Assuming Relative is 3rd option (0-based index 2)
                } catch (Exception e3) {
                    throw new RuntimeException("All selection methods failed for Relative option");
                }
            }
        }
    }
    void placeAndStyleContainer(String bgColor, String width, String widthUnit, String height, String heightUnit) throws InterruptedException {
        WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id^='aoe-container']")));
        js.executeScript("arguments[0].click();", container);

        // Open appearance tab
        wait.until(ExpectedConditions.elementToBeClickable(By.id("pe-tab_appearance"))).click();
        

        // Set width
        WebElement sizeHost = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ao-size")));
        setDimension(sizeHost, 1, width, widthUnit);   // width
        setDimension(sizeHost, 2, height, heightUnit); // height

        // Set position to relative
        setPositionToRelative();

        // Set background color
        WebElement bgHost = driver.findElement(By.cssSelector("ao-background"));
        WebElement colorPicker = getShadowElement(bgHost, "details > p > ao-color-picker");
        WebElement colorInput = getShadowElement(colorPicker, "input.color");

        js.executeScript(
            "arguments[0].value = arguments[1]; " +
            "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
            colorInput, bgColor);

        // Border Radius
        setBorderRadius("20");
    }
    void placeAndStyleRep(String width, String widthUnit, String height, String heightUnit) throws InterruptedException {
        WebElement RepetingList = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.aose-container-default[data-type='container'] ao-repeating-list")));
        js.executeScript("arguments[0].click();", RepetingList);

        // Open appearance tab
        wait.until(ExpectedConditions.elementToBeClickable(By.id("pe-tab_appearance"))).click();
        Thread.sleep(500);

        // Set width
        WebElement sizeHost = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ao-size")));
        setDimension(sizeHost, 1, width, widthUnit);   // width
        setDimension(sizeHost, 2, height, heightUnit); // height

        // Set position to relative
        setPositionToRelative();
        // Border Radius
        setBorderRadius("20");
    }

    void setDimension(WebElement sizeHost, int childIndex, String value, String unit) {
        WebElement dimension = (WebElement) js.executeScript(
            "return arguments[0].shadowRoot.querySelector('details > p > ao-dimension:nth-child(" + childIndex + ")')",
            sizeHost);
        WebElement input = getShadowElement(dimension, "p > input");
        input.clear();
        input.sendKeys(value);
        WebElement unitDropdown = getShadowElement(dimension, "p > select");
        unitDropdown.sendKeys(unit);
    }

    void setPositionToRelative() {
        WebElement aoPosition = (WebElement) js.executeScript(
            "return document.querySelector('ao-position').shadowRoot.querySelector('ao-dropdown[data-attribute=\"position\"]')");
        WebElement selectElement = (WebElement) js.executeScript("return arguments[0].shadowRoot.querySelector('select')", aoPosition);
        new Select(selectElement).selectByValue("relative");
    }

    void setBorderRadius(String radiusValue) {
        WebElement aoBorder = driver.findElement(By.cssSelector("ao-border"));
        SearchContext shadowRoot1 = aoBorder.getShadowRoot();
        WebElement aoDimension = shadowRoot1.findElement(By.cssSelector("ao-dimension[data-attribute='border-radius']"));
        SearchContext shadowRoot2 = aoDimension.getShadowRoot();
        WebElement input = shadowRoot2.findElement(By.cssSelector("input.magnitude"));
        input.clear();
        input.sendKeys(radiusValue);
        input.sendKeys(Keys.TAB);
    }
    
  //value of btn box :   
    static void updateButtonText(String newText) {
            WebElement aoConfig = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ao-config")));
            SearchContext shadowRoot = aoConfig.getShadowRoot();

            // Step 1: Set the new text in textarea
            WebElement jsonTextarea = shadowRoot.findElement(By.cssSelector("textarea.config-textarea"));
            String json = "{\n  \"text\": \"" + newText + "\"\n}";
            jsonTextarea.clear();
            jsonTextarea.sendKeys(json);

            // Step 2: Click on the "Config" summary AFTER setting the value
            WebElement configSummary = shadowRoot.findElement(By.cssSelector("summary"));
            configSummary.click();
        }
    
    public void createNewPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Click the "Pages" tab (if not already open)
        WebElement pagesTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='pages' and contains(text(),'Pages')]")));
        pagesTab.click();

        // 2. Enter "createBlog" in the "New Page Name" input
        WebElement newPageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@placeholder='New Page Name']")
        ));
        newPageInput.sendKeys("createBlog");

        // 3. Click on the "Add Page" button
        WebElement addPageButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(),'Add Page')]")
        ));
        addPageButton.click();
    }



    WebElement getShadowElement(WebElement host, String selector) {
        return (WebElement) js.executeScript("return arguments[0].shadowRoot.querySelector(arguments[1])", host, selector);
    
    }
    }
