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

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SeleniumTestRuleTest {
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
	public void succeeded() {
		Result result = runTest(WebDriverMockSuccessfulTest.class);
		Assert.assertTrue(result.wasSuccessful());
	}

	@Test(expected = AssertionError.class)
	public void failed() throws Throwable {
		Result result = runTest(WebDriverMockFailedTest.class);
		Assert.assertFalse(result.wasSuccessful());
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
