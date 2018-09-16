package org.selenium;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.WebDriver;
import org.selenium.annotation.ChromeDriver;
import org.selenium.annotation.PhantomJsDriver;
import org.selenium.annotation.SeleniumWebDriver;

public class AnnotationHelper {
    private AnnotationHelper() {
        // Util class has a private constructor
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "squid:S1751" })
    public static Annotation getWebDriverAnnotation(Object testCase) {
        Class[] annotations = { SeleniumWebDriver.class, PhantomJsDriver.class, ChromeDriver.class };
        for (Class<? extends Annotation> annotation : annotations) {
            for (Field field : FieldUtils.getFieldsWithAnnotation(testCase.getClass(), annotation)) {
                return field.getAnnotation(annotation);
            }
        }
        // No annotation was found, throw an exception
        throw new IllegalArgumentException();
    }

    @SuppressWarnings("squid:S1751")
    public static Field getFieldWithAnnotation(Object testCase, Annotation annotation) {
        for (Field field : FieldUtils.getFieldsWithAnnotation(testCase.getClass(), annotation.annotationType())) {
            return field;
        }
        return null;
    }

    public static WebDriver getWebDriver(Object testCase) throws IllegalAccessException {
        Annotation webDriverAnnotation = AnnotationHelper.getWebDriverAnnotation(testCase);
        Field webDriverField = AnnotationHelper.getFieldWithAnnotation(testCase, webDriverAnnotation);
        return AnnotationHelper.getWebDriver(testCase, webDriverField);
    }

    public static WebDriver getWebDriver(Object testCase, Field field) throws IllegalAccessException {
        return (WebDriver) FieldUtils.readField(field, testCase);
    }

    public static void setWebDriverToField(Object testCase, Field field, WebDriver webDriver)
            throws IllegalAccessException {
        FieldUtils.writeField(field, testCase, webDriver);
    }
}