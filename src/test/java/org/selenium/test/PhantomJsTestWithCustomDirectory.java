package org.selenium.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.SeleniumWebDriver;

import lombok.Data;

@Data
public class PhantomJsTestWithCustomDirectory {
    @SeleniumWebDriver
    public WebDriver webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule("target/custom/",true);

    @Before
    public void setUp() {
        String phantomJsPath = System.getProperty("phantomjs.binary");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsPath);
        webDriver = new PhantomJSDriver(capabilities);
        webDriver.manage().window().setSize(new Dimension(800, 600));
    }

    @Test
    public void phantomJsTest() {
        webDriver.get("https://www.google.fi");
        Assert.assertEquals("Google", webDriver.getTitle());
        webDriver.findElement(By.id("nonexistent"));
    }
}
