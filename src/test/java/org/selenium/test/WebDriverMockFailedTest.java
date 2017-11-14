package org.selenium.test;

import lombok.Data;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.WebDriverMock;
import org.selenium.annotation.SeleniumWebDriver;

@Data
public class WebDriverMockFailedTest {
    @SeleniumWebDriver
    public WebDriver webDriver = new WebDriverMock();
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @Test
    public void failedTest() {
        Assert.fail();
    }
}