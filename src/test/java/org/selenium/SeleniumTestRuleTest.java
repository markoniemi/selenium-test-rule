package org.selenium;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.WebDriver;
import org.selenium.test.WebDriverMockFailedTest;
import org.selenium.test.WebDriverMockSuccessfulTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeleniumTestRuleTest {
    @Ignore
    @Test
    public void getWebDriver() {
        WebDriverTest webDriverTest = new WebDriverTest();
        webDriverTest.webDriver = new WebDriverMock();
        SeleniumTestRule seleniumTestRule = new SeleniumTestRule();
        WebDriver webDriver = seleniumTestRule.setWebDriverToTest(webDriverTest);
        Assert.assertNotNull(webDriver);
    }

    @Test
    @Ignore
    public void getWebDriverFromAnnotation() {
        try {
            WebDriverTest webDriverTest = new WebDriverTest();
            SeleniumTestRule seleniumTestRule = new SeleniumTestRule();
            WebDriver webDriver = seleniumTestRule.setWebDriverToTest(webDriverTest);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            // TODO re-write this test
        }
    }

    @Test
    public void webDriverMockSuccessfulTest() throws Throwable {
        runTest(WebDriverMockSuccessfulTest.class);
    }

    @Test(expected = AssertionError.class)
    public void webDriverMockFailedTest() throws Throwable {
        runTest(WebDriverMockFailedTest.class);
    }

    public static Result runTest(Class<?> test) throws Throwable {
        Result result = JUnitCore.runClasses(test);
        for (Failure failure : result.getFailures()) {
            log.debug(failure.getMessage());
        }
        if (result.wasSuccessful()) {
            return result;
        } else {
            throw result.getFailures().get(0).getException();
        }
    }
}
