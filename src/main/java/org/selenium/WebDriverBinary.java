package org.selenium;

import java.lang.annotation.Annotation;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.selenium.annotation.PhantomJsDriver;
import org.selenium.annotation.SeleniumChromeDriver;
import org.selenium.annotation.SeleniumFirefoxDriver;
import org.selenium.annotation.SeleniumJBrowserDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class WebDriverBinary {
    public WebDriverBinary() {
        // Util class has a private constructor
    }
    
    public static WebDriver createDriver(Annotation webDriverAnnotation) throws IllegalAccessException {
        WebDriver webDriver = null;
        if (webDriverAnnotation instanceof PhantomJsDriver) {
            webDriver = createPhantomJsDriver((PhantomJsDriver) webDriverAnnotation);
        } else if (webDriverAnnotation instanceof SeleniumJBrowserDriver) {
            webDriver = createJBrowserDriver();
        } else if (webDriverAnnotation instanceof SeleniumFirefoxDriver) {
            webDriver = createFirefoxDriver();
        } else if (webDriverAnnotation instanceof SeleniumChromeDriver) {
            webDriver = createChromeDriver((SeleniumChromeDriver) webDriverAnnotation);
        }
        return webDriver;
    }

    private static WebDriver createPhantomJsDriver(PhantomJsDriver annotation) throws IllegalAccessException {
        PhantomJsDriverManager.getInstance().setup();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        WebDriver webDriver = new PhantomJSDriver(capabilities);
        webDriver.manage().window().setSize(new Dimension(800, 600));
        return webDriver;
    }

    private static WebDriver createJBrowserDriver() throws IllegalAccessException {
        return new com.machinepublishers.jbrowserdriver.JBrowserDriver();
    }

    private static WebDriver createFirefoxDriver() throws IllegalAccessException {
        return new FirefoxDriver();
    }

    private static WebDriver createChromeDriver(SeleniumChromeDriver webDriverAnnotation)
            throws IllegalAccessException {
        ChromeDriverManager.getInstance().setup();
        WebDriver webDriver = new ChromeDriver();
        return webDriver;
    }
}
