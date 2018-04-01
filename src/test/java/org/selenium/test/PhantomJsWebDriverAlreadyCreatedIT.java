package org.selenium.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.SeleniumWebDriver;

import io.github.bonigarcia.wdm.PhantomJsDriverManager;

/**
 * Test case where WebDriver is created by the test case. SeleniumTestRule
 * should not overwrite webDriver.
 */
public class PhantomJsWebDriverAlreadyCreatedIT {
    @SeleniumWebDriver
    public WebDriver webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule("target/custom/", true);

    @Before
    public void setUp() {
        PhantomJsDriverManager.getInstance().setup();
        webDriver = new PhantomJSDriver();
    }

    @Test
    public void phantomJsTest() {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        webDriver.findElement(By.id("nonexistent"));
    }
}
