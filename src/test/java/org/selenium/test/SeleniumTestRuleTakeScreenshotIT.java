
package org.selenium.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.PhantomJsDriver;

import lombok.Data;

@Data
public class SeleniumTestRuleTakeScreenshotIT {
    @PhantomJsDriver(version = "2.1.1")
    public WebDriver webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @Test
    public void phantomJsTest() throws InterruptedException {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
        webDriver.findElement(By.id("nonexistent"));
    }
}
