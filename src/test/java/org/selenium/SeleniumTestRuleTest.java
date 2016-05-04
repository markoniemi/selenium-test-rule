package org.selenium;

import java.lang.annotation.Annotation;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.selenium.test.ChromeDriverTest;
import org.selenium.test.FirefoxDriverTest;
import org.selenium.test.JBrowserDriverTest;
import org.selenium.test.PhantomJsTest;
import org.selenium.test.PhantomJsTestWithAutomaticDriverCreate;
import org.selenium.test.PhantomJsTestWithClose;
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

	@Test(expected = NullPointerException.class)
	@Ignore
	public void jBrowserDriverTest() throws Throwable {
		Result result = runTest(JBrowserDriverTest.class);
		throw result.getFailures().get(0).getException();
	}

	@Test(expected = NullPointerException.class)
	@Ignore("driver installation not yet done")
	public void firefoxDriverTest() throws Throwable {
		Result result = runTest(FirefoxDriverTest.class);
		throw result.getFailures().get(0).getException();
	}
	@Test(expected = NullPointerException.class)
	@Ignore("driver installation not yet done")
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
