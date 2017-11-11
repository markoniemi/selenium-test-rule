package org.selenium.test;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.JBrowserDriver;

@Log4j2
@Data
public class JBrowserTest {
    @JBrowserDriver
    public WebDriver webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @Test
    public void jBrowserDriverTest() {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        WebElement webElement = webDriver.findElement(By.id("test"));
        webElement.click();
        log.debug(webDriver.getCurrentUrl());
    }
}