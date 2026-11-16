package org.example;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class cdVyatka {
    WebDriver driver;
    final String URL = "https://m.cdsvyatka.com/ ";
    WebDriverWait wait;
    @Before
    public void setup(){

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void titleTest() {
        driver.get(URL);

        Assert.assertEquals(driver.getTitle(), "Мониторинг пассажирского транспорта в КИРОВЕ");
    }
    @Test
    public void FirstScenario() throws IOException {
        driver.get(URL);
        driver.manage().window().maximize();
        WebElement town = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_CitySelect")));
        Select townSelect = new Select(town);
        townSelect.selectByVisibleText("Киров");
        WebElement auto = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_MarshSelect")));
        Select autoSelect = new Select(auto);
        autoSelect.selectByVisibleText("Авт 1: Ипподром - Инфекционная больница");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_buttonMap")));
        btn.click();
        WebElement a = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'leaflet-marker-icon') and contains(@class, 'leaflet-clickable')]")));
        a.click();
        WebElement text = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'leaflet-popup-content')]")));
        List <WebElement> arr = text.findElements(By.tagName("br"));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshots/Bus.png"));
        Assert.assertFalse(arr.isEmpty());
    }
    @Test
    public void SecondScenario() throws IOException{
        driver.get(URL);
        driver.manage().window().maximize();
        WebElement town = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_CitySelect")));
        Select townSelect = new Select(town);
        townSelect.selectByVisibleText("Киров");
        WebElement auto = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_MarshSelect")));
        Select autoSelect = new Select(auto);
        autoSelect.selectByVisibleText("Авт 1: Ипподром - Инфекционная больница");
        WebElement station = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_StopSelect")));
        Select stationSelect = new Select(station);
        stationSelect.selectByVisibleText("Школа №42 (код 648)");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_buttonPrediction")));
        btn.click();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshots/Прогноз.png"));
        WebElement a = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'minutes')]")));
        Assert.assertEquals(a.getText(),"  1 мин.");
    }
}
