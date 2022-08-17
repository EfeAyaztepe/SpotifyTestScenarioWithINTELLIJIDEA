import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.time.Duration;
import java.util.List;

public class BasePage extends BaseTest {

    FluentWait<WebDriver> wait;
    WebDriver driver;

    public BasePage(){
        this.driver=BaseTest.driver;

        wait = setFluentWait(15);
    }

    public FluentWait<WebDriver> setFluentWait(long timeout){

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);
    }
    public List<WebElement> findElementsWait(By by){

        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }
    public WebElement findElementWait(By by){

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }
    public WebElement findElement(By locater){
        return driver.findElement(locater);
    }

    public void click(By locater){
        findElementWait(locater).click();
    }

    public void sendKeys(By locater,String text){
        findElementWait(locater).sendKeys(text);

    }

    public void hoverElement(By locater){
        Actions actions = new Actions(driver);
        actions.moveToElement(findElementWait(locater)).build().perform();
    }

    public void scrollElementIntoView(By locater){
        JavascriptExecutor js =(JavascriptExecutor) driver;
        WebElement element= driver.findElement(locater);
        js.executeScript("arguments[0].scrollIntoView(true)",element);
    }



    public void waitByMilliSeconds(long seconds) throws InterruptedException {
        Thread.sleep(seconds+1000);
    }

    public List<WebElement> findAll(By locater){
        try {
            WebElement webElement = findElementWait(locater);
            //System.out.println("attribute: " + webElement.getAttribute("placeholder"));
        }catch (Exception e){
            System.out.println("hataaaaaa");
        }
        return findElementsWait(locater);
    }

    public String getText(By locater){
        return  findElementWait(locater).getText();
    }


    public void getSelection(String data){
        StringSelection stringSelection = new StringSelection(data);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection,null);
    }

    public void pasteSelection(){
        new Actions(driver).keyDown(Keys.LEFT_CONTROL).sendKeys("v").keyUp(Keys.LEFT_CONTROL).perform();
    }

    public void pressEnter(){
        Actions builder = new Actions(driver);
        builder.keyDown(Keys.RETURN).keyUp(Keys.RETURN).build().perform();

    }

    public boolean doesUrlEqual(String url, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.urlToBe(url));
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public String getAttribute(By by, String attribute){

        return findElementWait(by).getAttribute(attribute);
    }

    public boolean isElementVisible(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public boolean isElementClickable(By by, long timeout){

        try {
            setFluentWait(timeout).until(ExpectedConditions.elementToBeClickable(by));
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
