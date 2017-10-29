package org.selenium;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.selenium.annotation.PhantomJsDriver;
import org.selenium.annotation.ChromeDriver;
import org.selenium.annotation.JBrowserDriver;
import org.selenium.annotation.WebDriver;

public class AnnotationHelper {
    private AnnotationHelper() {
        // Util class has a private constructor
    }

    @SuppressWarnings({"rawtypes", "unchecked", "squid:S1751"})
    public static Annotation getWebDriverAnnotation(Object testCase) {
        Class[] annotations = {WebDriver.class, PhantomJsDriver.class, JBrowserDriver.class,
                ChromeDriver.class};
        for (Class<? extends Annotation> annotation : annotations) {
            for (Field field : FieldUtils.getFieldsWithAnnotation(testCase.getClass(), annotation)) {
                return field.getAnnotation(annotation);
            }
        }
        // No annotation was found, throw an exception
        throw new IllegalArgumentException(createErrorText());
    }

    static String createErrorText() {
        String annotationName = WebDriver.class.getSimpleName();
        return String.format("Annotate public attribute of type WebDriver with @%s annotation.", annotationName);
    }

    @SuppressWarnings("squid:S1751")
    public static Field getFieldWithAnnotation(Object testCase, Annotation annotation) {
        for (Field field : FieldUtils.getFieldsWithAnnotation(testCase.getClass(), annotation.annotationType())) {
            return field;
        }
        return null;
    }

    public static org.openqa.selenium.WebDriver getWebDriver(Object testCase, Field field) {
        try {
            Object object = FieldUtils.readField(field, testCase);
            if (object != null) {
                if (object instanceof org.openqa.selenium.WebDriver) {
                    return (org.openqa.selenium.WebDriver) object;
                } else {
                    throw new IllegalArgumentException(createErrorText());
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalArgumentException(createErrorText(), e);
        }
        return null;
    }

    public static void setWebDriverToTest(Object testCase, Field field, org.openqa.selenium.WebDriver webDriver) {
        try {
            FieldUtils.writeField(field, testCase, webDriver);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(createErrorText(), e);
        }
    }
}