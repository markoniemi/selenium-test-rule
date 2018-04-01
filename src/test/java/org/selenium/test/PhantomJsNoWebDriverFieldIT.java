package org.selenium.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.PhantomJsDriver;

import lombok.Data;

@Data
public class PhantomJsNoWebDriverFieldIT {
    @PhantomJsDriver
    private String webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @Test
    public void phantomJsTest() {
        Assert.fail();
    }
}
