package org.selenium;

import org.junit.Test;
import org.selenium.annotation.WebDriver;

public class WebDriverTest {
    @WebDriver
    public org.openqa.selenium.WebDriver webDriver;

    @Test
    public void dummy() {
        // empty test
    }
}
