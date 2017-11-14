package org.selenium;

import org.junit.Test;
import org.selenium.annotation.SeleniumWebDriver;

public class WebDriverTest {
    @SeleniumWebDriver
    public org.openqa.selenium.WebDriver webDriver;

    @Test
    public void dummy() {
        // empty test
    }
}
