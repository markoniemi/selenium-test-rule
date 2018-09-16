package org.selenium.executable;

import java.lang.annotation.Annotation;

import org.openqa.selenium.WebDriver;
import org.selenium.annotation.ChromeDriver;
import org.selenium.annotation.PhantomJsDriver;

public class WebDriverFactory {
    private WebDriverFactory() {
        // Util class has a private constructor
    }

    public static WebDriver createDriver(Annotation webDriverAnnotation) {
        WebDriver webDriver = null;
        if (webDriverAnnotation instanceof PhantomJsDriver) {
            webDriver = PhantomJsExecutable.createDriver((PhantomJsDriver) webDriverAnnotation);
        } else if (webDriverAnnotation instanceof ChromeDriver) {
            webDriver = ChromeExecutable.createDriver((ChromeDriver) webDriverAnnotation);
        }
        return webDriver;
    }
}
