package org.selenium;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.selenium.annotation.PhantomJsDriver;
import org.selenium.examples.PhantomJsIT;
import org.selenium.test.PhantomJsNoAnnotationsIT;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationHelperTest {

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getFieldWithAnnotation() {
        PhantomJsIT testCase = new PhantomJsIT();
        PhantomJsDriver annotation = Mockito.mock(PhantomJsDriver.class);
        Class clazz = PhantomJsDriver.class;
        Mockito.when(annotation.annotationType()).thenReturn(clazz);
        Field field = AnnotationHelper.getFieldWithAnnotation(testCase, annotation);
        Assert.assertNotNull(field);
    }
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getFieldWithAnnotationReturnsNull() {
        PhantomJsNoAnnotationsIT testCase = new PhantomJsNoAnnotationsIT();
        PhantomJsDriver annotation = Mockito.mock(PhantomJsDriver.class);
        Class clazz = PhantomJsDriver.class;
        Mockito.when(annotation.annotationType()).thenReturn(clazz);
        Field field = AnnotationHelper.getFieldWithAnnotation(testCase, annotation);
        Assert.assertNull(field);
    }
}
