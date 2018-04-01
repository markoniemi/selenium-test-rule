package org.selenium.examples;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.WebDriverAnnotations;
import org.selenium.annotation.PhantomJsDriver;

import lombok.Data;

@Data
public class PhantomJsIT {
    @PhantomJsDriver(version = "2.1.1")
    public WebDriver webDriver;

    @Before
    public void setUp() {
        WebDriverAnnotations.initializeWebDriver(this);
    }

    @After
    public void tearDown() {
        webDriver.close();
        webDriver.quit();
    }

    @Test
    public void phantomJsTest() throws InterruptedException {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        Assert.assertNotNull(webDriver.findElement(By.id("test")));
    }
}