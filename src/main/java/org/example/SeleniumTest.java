package org.example;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class SeleniumTest {
    WebDriver driver;
    final String URL = "https://old.vyatsu.ru";
    WebDriverWait wait;
    @Before
    public void setup(){

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void titleTest() {
        driver.get(URL);
        Assert.assertEquals(driver.getTitle(), "Официальный сайт ВятГУ");
    }
    @Test
    public void FirstScenario() throws IOException {
        driver.get(URL);
        WebElement firstLi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"jsddm\"]/li[1]")));
        Actions action = new Actions(driver);
        action.moveToElement(firstLi).perform();
        WebElement targetMenuItem = wait.until(ExpectedConditions.elementToBeClickable( By.xpath("//*[@id=\"jsddm\"]/li[1]//ul/li[contains(., \"Образовательные стандарты\")]") ));
        targetMenuItem.click();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshots/студенту.png"));
        Assert.assertEquals(driver.getTitle(), "Сведения об ОО Образовательные стандарты и требования");
    }

    @Test
    public void SecondScenario() throws IOException{
        driver.get(URL);
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("query")));
        search.sendKeys("отпуск");
        search.sendKeys(Keys.ENTER);
        WebElement parentDiv = driver.findElement(By.className("column-center"));
        List <WebElement> a = parentDiv.findElements(By.tagName("div"));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshots/отпуск.png"));
        Assert.assertFalse(a.isEmpty());
    }

    @Test
    public void ThirdScenarioNegative() throws IOException{
        driver.get(URL);
        WebElement firstLi = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("СТУДЕНТУ")));
        firstLi.click();
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        search.sendKeys("sadsru");
        search.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert =driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText,"\nНеправильно введен e-mail");

    }

    @After
    public void quit(){

    }



}
