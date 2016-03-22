package org.selenium;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.selenium.test.PhantomJsTest;
import org.selenium.test.PhantomJsTestWithAutomaticDriverCreate;
import org.selenium.test.PhantomJsTestWithClose;
import org.selenium.test.WebDriverMockFailedTest;
import org.selenium.test.WebDriverMockSuccessfulTest;

public class SeleniumTestRuleTest {
    @Test
    public void getWebDriver() {
        WebDriverTest webDriverTest = new WebDriverTest();
        webDriverTest.webDriver = new WebDriverMock();
        SeleniumTestRule seleniumTestRule = new SeleniumTestRule();
        WebDriver webDriver = seleniumTestRule.getWebDriver(webDriverTest);
        Assert.assertNotNull(webDriver);
    }

    @Test
    @Ignore
    public void getWebDriverFromAnnotation() {
        try {
            WebDriverTest webDriverTest = new WebDriverTest();
            SeleniumTestRule seleniumTestRule = new SeleniumTestRule();
            WebDriver webDriver = seleniumTestRule.getWebDriver(webDriverTest);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void succeeded() {
        Result result = runTest(WebDriverMockSuccessfulTest.class);
        Assert.assertTrue(result.wasSuccessful());
    }

    @Test
    public void failed() {
        Result result = runTest(WebDriverMockFailedTest.class);
        Assert.assertFalse(result.wasSuccessful());
    }

    @Test
    public void phantomJsTest() {
        Result result = runTest(PhantomJsTest.class);
        Assert.assertFalse(result.wasSuccessful());
        Assert.assertTrue(result.getFailures().get(0).getException() instanceof NoSuchElementException);
    }

    @Test
    public void phantomJsTestWithAutomaticDriverCreate() {
        Result result = runTest(PhantomJsTestWithAutomaticDriverCreate.class);
        Assert.assertFalse(result.wasSuccessful());
//        Assert.assertTrue(result.getFailures().get(0).getException() instanceof NoSuchElementException);
    }
    
    @Test
    public void phantomJsTestWithClose() {
        Result result = runTest(PhantomJsTestWithClose.class);
        Assert.assertTrue(result.getFailures().get(0).getException() instanceof NoSuchWindowException);
    }

    private static Result runTest(Class<?> test) {
        JUnitCore junitCore = new JUnitCore();
        return junitCore.run(Request.aClass(test).getRunner());
    }
}
