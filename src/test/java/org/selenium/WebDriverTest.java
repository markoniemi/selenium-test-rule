package org.selenium;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.selenium.annotation.SeleniumWebDriver;

public class WebDriverTest {
    @SeleniumWebDriver
    public WebDriver webDriver;

    @Test
    public void dummy() {
        // empty test
    }
}
