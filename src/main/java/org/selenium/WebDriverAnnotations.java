package org.selenium;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.selenium.executable.WebDriverFactory;

public class WebDriverAnnotations {
    private WebDriverAnnotations() {
        // util class has a private constructor
    }

    /**
     * Initialize webDriver to test case. When using initializeWebDriver method, you
     * must call webDriver.quit() in @After or @AfterClass method.
     * 
     * @param testCase
     *            pass 'this' from test case
     * @return the WebDriver that was created.
     */
    public static WebDriver initializeWebDriver(Object testCase) {
        WebDriver webDriver = null;
        try {
            Annotation webDriverAnnotation = AnnotationHelper.getWebDriverAnnotation(testCase);
            Field webDriverField = AnnotationHelper.getFieldWithAnnotation(testCase, webDriverAnnotation);
            webDriver = AnnotationHelper.getWebDriver(testCase, webDriverField);
            if (webDriver == null) {
                // test case did not initialize webDriver, initialize it
                webDriver = WebDriverFactory.createDriver(webDriverAnnotation);
                AnnotationHelper.setWebDriverToField(testCase, webDriverField, webDriver);
            }
        } catch (Exception e) {
            if (webDriver != null) {
                webDriver.quit();
            }
            throw e;
        }
        return webDriver;
    }
}
