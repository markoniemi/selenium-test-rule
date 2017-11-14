package org.selenium.examples;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.selenium.WebDriverAnnotations;
import org.selenium.annotation.PhantomJsDriver;

@Log4j2
@Data
public class PhantomJsIT {
    @PhantomJsDriver(version = "2.1.1")
    public org.openqa.selenium.WebDriver webDriver;

    @Before
    public void setUp() {
        WebDriverAnnotations.initializeWebDriver(this);
    }

    @After
    public void tearDown() {
        webDriver.close();
    }

    @Test
    public void phantomJsTest() throws InterruptedException {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        Assert.assertNotNull(webDriver.findElement(By.id("test")));
    }
}