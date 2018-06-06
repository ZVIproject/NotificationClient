package com.studnnet.notification_system;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Test {

    private static WebDriver webDriver;
    private static final String GMAIL_AUTH_URL = "https://accounts.google.com/signin/v2/identifier?hl=uk&continue=https%3A%2F%2Fmail.google.com%2Fmail&service=mail&flowName=GlifWebSignIn&flowEntry=AddSession";

    @BeforeClass
    public static void initMethod(){
        System.setProperty("webdriver.chrome.driver", "/Users/vladzarovnyi/Downloads/chromedriver");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(GMAIL_AUTH_URL);
    }

    @org.junit.Test
    public void googleLoginTestFailedIfLoginWasNotCorrect() throws Exception {

        WebElement loginField = webDriver.findElement(By.id("identifierId"));
        WebElement loginButton = webDriver.findElement(By.id("identifierNext"));

        loginField.sendKeys("zarovni03");
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(webDriver, 10);

        wait.until((WebDriver webDriver1) ->  webDriver.findElement(By.id("profileIdentifier"))!= null);

        String username = webDriver.findElement(By.id("profileIdentifier")).getText();

        Assert.assertEquals(username, "zarovni03@gmail.com");
    }

    @AfterClass
    public static void after(){
        webDriver.quit();
    }

}
