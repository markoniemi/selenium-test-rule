package org.selenium.test;

import junit.framework.TestCase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.WebDriverAnnotations;

public class PhantomJsNoAnnotationsIT extends TestCase {
    public WebDriver webDriver;

    protected void setUp() throws Exception {
        WebDriverAnnotations.initializeWebDriver(this);
    }

    public void testPhantomJsTest() {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        webDriver.findElement(By.id("nonexistent"));
    }
}
