package org.example;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class OtzovikTest {
    WebDriver driver;
    final String URL = "https://otzovik.com";
    WebDriverWait wait;
    @Before
    public void setup(){

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void titleTest() {
        driver.get(URL);

        Assert.assertEquals(driver.getTitle(), "Отзовик - отзывы реальных покупателей, владельцев, сотрудников, пациентов, туристов и рейтинги лучших предложений!");
    }
    @Test
    public void FirstScenario() throws IOException {
        driver.get(URL);
        driver.manage().window().maximize();
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header-search-input")));
        search.sendKeys("яндекс");
        search.sendKeys(Keys.ENTER);
        WebElement firstLi = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Yandex Metro - программа для Android")));
        firstLi.click();
        WebElement parentDiv = driver.findElement(By.className("otz_product_reviews_left"));
        List <WebElement> a = parentDiv.findElements(By.tagName("div"));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshots/Яндекс.png"));
        Assert.assertFalse(a.isEmpty());
    }
    @Test
    public void SecondScenario() throws IOException{
        driver.get(URL);
        driver.manage().window().maximize();
        WebElement firstLi = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Написать отзыв")));
        firstLi.click();
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tproduct")));
        search.sendKeys("Ботанический сад ВятГУ (Россия, Киров)");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("noproduct")));
        btn.click();
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content_title")));
        text.sendKeys("dsada");
        WebElement btn2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("previewbtn")));
        btn2.click();
        WebElement parentDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("diError")));
        List <WebElement> a = parentDiv.findElements(By.tagName("div"));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshots/Отзыв.png"));
        Assert.assertFalse(a.isEmpty());
    }



    @After
    public void quit(){

    }
}
