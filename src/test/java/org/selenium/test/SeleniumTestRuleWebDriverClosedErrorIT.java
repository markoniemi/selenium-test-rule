package org.selenium.test;

import lombok.Data;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.SeleniumWebDriver;

@Data
public class SeleniumTestRuleWebDriverClosedErrorIT {
    @SeleniumWebDriver
    public WebDriver webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @After
    public void tearDown() {
        webDriver.close();
    }

    @Test
    public void phantomJsTest() {
        webDriver.get("https://www.google.fi");
        Assert.assertEquals("Google", webDriver.getTitle());
        webDriver.findElement(By.id("nonexistent"));
    }
}
