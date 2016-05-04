package org.selenium;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.WebDriver;
import org.selenium.annotation.PhantomJsDriver;
import org.selenium.annotation.SeleniumChromeDriver;
import org.selenium.annotation.SeleniumFirefoxDriver;
import org.selenium.annotation.SeleniumJBrowserDriver;
import org.selenium.annotation.SeleniumWebDriver;

public class AnnotationHelper {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Annotation getWebDriverAnnotation(Object testCase) {
        Class[] annotations = { SeleniumWebDriver.class, PhantomJsDriver.class, SeleniumJBrowserDriver.class,
                SeleniumFirefoxDriver.class, SeleniumChromeDriver.class };
        for (Class<? extends Annotation> annotation : annotations) {
            for (Field field : FieldUtils.getFieldsWithAnnotation(testCase.getClass(), annotation)) {
                // this.webDriver = (WebDriver) getFieldValue(testCase, field);
                // this.webDriverField = field;
                return field.getAnnotation(annotation);
            }
        }
        // No annotation was found, throw an exception
        throw new IllegalArgumentException(createErrorText());
    }

    private static String createErrorText() {
        String annotationName = org.selenium.annotation.SeleniumWebDriver.class.getSimpleName();
        return String.format("Annotate public attribute of type WebDriver with @%s annotation.", annotationName);
    }

    public static Field getFieldWithAnnotation(Object testCase, Annotation annotation) {
        for (Field field : FieldUtils.getFieldsWithAnnotation(testCase.getClass(), annotation.annotationType())) {
            return field;
        }
        return null;
    }

    public static WebDriver getWebDriver(Object testCase, Field field) {
        try {
            Object object = FieldUtils.readField(field, testCase);
            if (object != null) {
                if (object instanceof WebDriver) {
                    return (WebDriver) object;
                } else {
                    throw new IllegalArgumentException(createErrorText());
                }
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(createErrorText(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(createErrorText(), e);
        }
        return null;
    }

    public static void setWebDriverToTest(Object testCase, Field field, WebDriver webDriver) {
        try {
            FieldUtils.writeField(field, testCase, webDriver);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(createErrorText(), e);
        }
    }
}