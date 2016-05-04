package org.selenium.test;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.WebDriverMock;
import org.selenium.annotation.SeleniumWebDriver;

import lombok.Data;

@Data
public class WebDriverMockSuccessfulTest {
    @SeleniumWebDriver
    public WebDriver webDriver = new WebDriverMock();
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

    @Test
    public void successfulTest() {
    }
}