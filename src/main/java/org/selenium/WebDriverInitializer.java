package org.selenium;

import org.openqa.selenium.WebDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class WebDriverInitializer {
    public static void initializeWebDriver(Object testCase) {
        Annotation webDriverAnnotation = AnnotationHelper.getWebDriverAnnotation(testCase);
        Field webDriverField = AnnotationHelper.getFieldWithAnnotation(testCase, webDriverAnnotation);
        WebDriver webDriver = AnnotationHelper.getWebDriver(testCase, webDriverField);
        AnnotationHelper.setWebDriverToTest(testCase, webDriverField, webDriver);
    }
}
