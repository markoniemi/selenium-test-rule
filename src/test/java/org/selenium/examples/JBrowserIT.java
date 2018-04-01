package org.selenium.examples;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.selenium.WebDriverAnnotations;
import org.selenium.annotation.JBrowserDriver;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class JBrowserIT {
    @JBrowserDriver
    public WebDriver webDriver;

    @Before
    public void setUp() {
        WebDriverAnnotations.initializeWebDriver(this);
    }

    @Test
    public void jBrowserDriverTest() {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        WebElement webElement = webDriver.findElement(By.id("test"));
        webElement.click();
        log.debug(webDriver.getCurrentUrl());
    }
}