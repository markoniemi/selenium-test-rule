package org.selenium.test;

import junit.framework.TestCase;
import lombok.Data;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.WebDriverInitializer;
import org.selenium.annotation.PhantomJsDriver;

public class PhantomJsTestWithoutAnnotation extends TestCase {
    public WebDriver webDriver;

    protected void setUp() throws Exception {
        WebDriverInitializer.initializeWebDriver(this);
    }

    public void testPhantomJsTest() {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        webDriver.findElement(By.id("nonexistent"));
    }
}
