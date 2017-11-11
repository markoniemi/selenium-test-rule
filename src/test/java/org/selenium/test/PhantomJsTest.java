package org.selenium.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.PhantomJsDriver;
import org.selenium.annotation.WebDriver;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class PhantomJsTest {
    @PhantomJsDriver(version = "2.1.1")
    public org.openqa.selenium.WebDriver webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @Test
    public void phantomJsTest() throws InterruptedException {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        Assert.assertNotNull(webDriver.findElement(By.id("test")));
    }
}