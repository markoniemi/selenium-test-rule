package org.selenium.test;

import lombok.Data;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.PhantomJsDriver;

@Data
public class PhantomJsNoWebDriverFieldIT {
    @PhantomJsDriver
    private String webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @Test
    public void phantomJsTest() {
        Assert.fail();
//        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
//        Assert.assertEquals("Test", webDriver.getTitle());
//        webDriver.findElement(By.id("nonexistent"));
    }
}
