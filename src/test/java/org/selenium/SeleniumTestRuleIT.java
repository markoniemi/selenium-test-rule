package org.selenium;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.selenium.test.ChromeDriverTest;
import org.selenium.test.JBrowserDriverTest;
import org.selenium.test.PhantomJsTest;
import org.selenium.test.PhantomJsTestWithAutomaticDriverCreate;
import org.selenium.test.PhantomJsTestWithClose;
import org.selenium.test.PhantomJsTestWithCustomDirectory;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SeleniumTestRuleIT {
    @Test(expected = NoSuchElementException.class)
    public void phantomJsTest() throws Throwable {
        Result result = runTest(PhantomJsTest.class);
        Assert.assertFalse(result.wasSuccessful());
        throw result.getFailures().get(0).getException();
        // Throwable exception = result.getFailures().get(0).getException();
        // Assert.assertTrue(exception.toString(), exception instanceof
        // NoSuchElementException);
    }

    @Test(expected = NoSuchElementException.class)
    public void phantomJsTestWithAutomaticDriverCreate() throws Throwable {
        // set system property phantomjs.binary before running this test
        Result result = runTest(PhantomJsTestWithAutomaticDriverCreate.class);
        Assert.assertFalse(result.wasSuccessful());
        throw result.getFailures().get(0).getException();
    }

    @Test(expected = NoSuchWindowException.class)
    public void phantomJsTestWithClose() throws Throwable {
        Result result = runTest(PhantomJsTestWithClose.class);
        throw result.getFailures().get(0).getException();
    }

    @Test(expected = NoSuchElementException.class)
    public void phantomJsTestWithCustomDirectory() throws Throwable {
        Result result = runTest(PhantomJsTestWithCustomDirectory.class);
        throw result.getFailures().get(0).getException();
    }
    
    @Test(expected = NoSuchElementException.class)
    @Ignore("Jenkins causes JavaFX detected no fonts error with jbrowserdriver")
    public void jBrowserDriverTest() throws Throwable {
        Result result = runTest(JBrowserDriverTest.class);
        throw result.getFailures().get(0).getException();
    }

    @Test(expected = NoSuchElementException.class)
    @Ignore("Jenkins does not have xfvb or chrome")
    public void chromeDriverTest() throws Throwable {
        Result result = runTest(ChromeDriverTest.class);
        throw result.getFailures().get(0).getException();
    }

    private Result runTest(Class<?> test) {
        Result result = JUnitCore.runClasses(test);
        for (Failure failure : result.getFailures()) {
            log.debug(failure.getMessage());
        }
        return result;
    }
}
