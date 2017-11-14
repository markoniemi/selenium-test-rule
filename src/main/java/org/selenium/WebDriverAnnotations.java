package org.selenium;

import org.openqa.selenium.WebDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class WebDriverAnnotations {
    public static WebDriver initializeWebDriver(Object testCase) {
        Annotation webDriverAnnotation = AnnotationHelper.getWebDriverAnnotation(testCase);
        Field webDriverField = AnnotationHelper.getFieldWithAnnotation(testCase, webDriverAnnotation);
        WebDriver webDriver = AnnotationHelper.getWebDriver(testCase, webDriverField);
        if (webDriver == null) {
            // test case did not initialize webDriver, initialize it
            webDriver = WebDriverBinary.createDriver(webDriverAnnotation);
            AnnotationHelper.setWebDriverToField(testCase, webDriverField, webDriver);
        }
        return webDriver;
    }
}
