package org.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.selenium.annotation.ChromeDriver;
import org.selenium.annotation.JBrowserDriver;
import org.selenium.annotation.PhantomJsDriver;

import java.lang.annotation.Annotation;

@Log4j2
public class WebDriverBinary {
    private WebDriverBinary() {
        // Util class has a private constructor
    }

    public static WebDriver createDriver(Annotation webDriverAnnotation) {
        WebDriver webDriver = null;
        if (webDriverAnnotation instanceof PhantomJsDriver) {
            webDriver = createPhantomJsDriver(((PhantomJsDriver) webDriverAnnotation).version());
        } else if (webDriverAnnotation instanceof JBrowserDriver) {
            webDriver = createJBrowserDriver();
        } else if (webDriverAnnotation instanceof ChromeDriver) {
            webDriver = createChromeDriver();
        }
        return webDriver;
    }

    private static WebDriver createPhantomJsDriver(String version) {
        PhantomJsDriverManager.getInstance().version(version).setup();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        WebDriver webDriver = new PhantomJSDriver(capabilities);
        webDriver.manage().window().setSize(new Dimension(800, 600));
        return webDriver;
    }

    private static WebDriver createJBrowserDriver() {
        return new com.machinepublishers.jbrowserdriver.JBrowserDriver();
    }

    // TODO add headless parameter
    private static WebDriver createChromeDriver() {
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        return new org.openqa.selenium.chrome.ChromeDriver(options);
    }
}
