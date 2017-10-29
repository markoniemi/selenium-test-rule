package org.selenium.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.selenium.SeleniumTestRule;
import org.selenium.WebDriverMock;
import org.selenium.annotation.WebDriver;

import lombok.Data;

@Data
public class WebDriverMockFailedTest {
    @WebDriver
    public org.openqa.selenium.WebDriver webDriver = new WebDriverMock();
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @Test
    public void failedTest() {
        Assert.fail();
    }
}