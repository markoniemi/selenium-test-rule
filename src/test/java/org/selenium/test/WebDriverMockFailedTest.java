package org.selenium.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.SeleniumWebDriver;
import org.selenium.WebDriverMock;

import lombok.Data;

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