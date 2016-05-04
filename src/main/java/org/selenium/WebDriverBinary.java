package org.selenium;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.selenium.annotation.PhantomJsDriver;
import org.selenium.annotation.SeleniumChromeDriver;
import org.selenium.annotation.SeleniumFirefoxDriver;
import org.selenium.annotation.SeleniumJBrowserDriver;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class WebDriverBinary {
    /** system property which is compatible with phantomjs-maven-plugin */
    public static final String PHANTOMJS_BINARY = "phantomjs.binary";

    public static WebDriver createDriver(Annotation webDriverAnnotation) throws IllegalAccessException {
        WebDriver webDriver = null;
        if (webDriverAnnotation instanceof PhantomJsDriver) {
            webDriver = createPhantomJsDriver((PhantomJsDriver) webDriverAnnotation);
        } else if (webDriverAnnotation instanceof SeleniumJBrowserDriver) {
            webDriver = createJBrowserDriver();
        } else if (webDriverAnnotation instanceof SeleniumFirefoxDriver) {
            webDriver = createFirefoxDriver();
        } else if (webDriverAnnotation instanceof SeleniumChromeDriver) {
            webDriver = createChromeDriver((SeleniumChromeDriver)webDriverAnnotation);
        }
        return webDriver;
    }

    private static WebDriver createPhantomJsDriver(PhantomJsDriver annotation) throws IllegalAccessException {
        String phantomJsPath = getPhantomJsBinaryPath(annotation);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsPath);
        WebDriver webDriver = new PhantomJSDriver(capabilities);
        webDriver.manage().window().setSize(new Dimension(800, 600));
        return webDriver;
    }

    protected static String getPhantomJsBinaryPath(PhantomJsDriver annotation) {
        String phantomJsPath = annotation.phantomJsPath();
        if (StringUtils.isEmpty(phantomJsPath) && !StringUtils.isEmpty(annotation.phantomJsPathProperty())) {
            phantomJsPath = System.getProperty(annotation.phantomJsPathProperty());
        }
        if (StringUtils.isEmpty(phantomJsPath)) {
            phantomJsPath = System.getProperty(PHANTOMJS_BINARY);
        }
        if (StringUtils.isEmpty(phantomJsPath)) {
            log.error(
                    "PhantomJsDriver requires system property name that contains path to PhantomJs binary or system property "
                            + PHANTOMJS_BINARY);
            throw new IllegalArgumentException(
                    "PhantomJsDriver requires either path to PhantomJs binary or system property " + PHANTOMJS_BINARY);
        }
        return phantomJsPath;
    }
    
    protected String getChromeBinaryPath(SeleniumChromeDriver annotation) {
        String binaryPath = annotation.binaryPath();
        if (StringUtils.isEmpty(binaryPath) && !StringUtils.isEmpty(annotation.binaryPathProperty())) {
            binaryPath = System.getProperty(annotation.binaryPathProperty());
        }
        if (StringUtils.isEmpty(binaryPath)) {
            binaryPath = System.getProperty(SeleniumChromeDriver.BINARY_PATH_PROPERTY);
        }
        if (StringUtils.isEmpty(binaryPath)) {
            log.error(
                    "PhantomJsDriver requires system property name that contains path to PhantomJs binary or system property "
                            + PHANTOMJS_BINARY);
            throw new IllegalArgumentException(
                    "PhantomJsDriver requires either path to PhantomJs binary or system property " + PHANTOMJS_BINARY);
        }
        return binaryPath;
    }

    private static WebDriver createJBrowserDriver() throws IllegalAccessException {
        WebDriver webDriver = new com.machinepublishers.jbrowserdriver.JBrowserDriver();
        return webDriver;
    }

    private static WebDriver createFirefoxDriver() throws IllegalAccessException {
        WebDriver webDriver = new FirefoxDriver();
        return webDriver;
    }
    private static WebDriver createChromeDriver(SeleniumChromeDriver webDriverAnnotation) throws IllegalAccessException {
        WebDriver webDriver = new ChromeDriver();
        return webDriver;
    }
}
