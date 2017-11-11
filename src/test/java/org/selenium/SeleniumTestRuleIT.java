package org.selenium;

import lombok.extern.log4j.Log4j2;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.selenium.test.*;

import static org.selenium.SeleniumTestRuleTest.runTest;

@Log4j2
public class SeleniumTestRuleIT {
    @Test
    public void phantomJsTest() throws Throwable {
        runTest(PhantomJsTest.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void phantomJsFailingTest() throws Throwable {
        runTest(PhantomJsFailingTest.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void phantomJsTestWithAutomaticDriverCreate() throws Throwable {
        // set system property phantomjs.binary before running this test
        runTest(PhantomJsTestWithAutomaticDriverCreate.class);
    }

    @Test(expected = NoSuchWindowException.class)
    public void phantomJsTestWithClose() throws Throwable {
        runTest(PhantomJsTestWithClose.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void phantomJsTestWithCustomDirectory() throws Throwable {
        runTest(PhantomJsTestWithCustomDirectory.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void phantomJsTestWithPrivateField() throws Throwable {
        runTest(PhantomJsTestWithFinalField.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void phantomJsTestWithNonWebDriverField() throws Throwable {
        runTest(PhantomJsTestWithNonWebDriverField.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void phantomJsTestWithoutAnnotation() throws Throwable {
        runTest(PhantomJsTestWithoutAnnotation.class);
    }

    @Test
    public void jBrowserTest() throws Throwable {
        runTest(JBrowserTest.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void jBrowserFailingTest() throws Throwable {
        runTest(JBrowserFailingTest.class);
    }

    @Test
    public void chromeDriverTest() throws Throwable {
        runTest(ChromeDriverTest.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void chromeDriverFailingTest() throws Throwable {
        runTest(ChromeDriverFailingTest.class);
    }
}
