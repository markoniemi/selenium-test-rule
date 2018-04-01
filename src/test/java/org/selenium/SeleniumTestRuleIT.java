package org.selenium;

import static org.selenium.SeleniumTestRuleTest.runTest;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.selenium.examples.ChromeDriverIT;
import org.selenium.examples.JBrowserIT;
import org.selenium.examples.PhantomJsIT;
import org.selenium.test.ChromeDriverFailingIT;
import org.selenium.test.JBrowserFailingIT;
import org.selenium.test.PhantomJsFailingIT;
import org.selenium.test.PhantomJsFinalFieldIT;
import org.selenium.test.PhantomJsNoAnnotationsIT;
import org.selenium.test.PhantomJsNoWebDriverFieldIT;
import org.selenium.test.SeleniumTestRuleCustomDirectoryIT;
import org.selenium.test.SeleniumTestRuleWebDriverClosedErrorIT;

public class SeleniumTestRuleIT {
    @Test
    public void phantomJsIT() throws Throwable {
        runTest(PhantomJsIT.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void phantomJsFailingIT() throws Throwable {
        runTest(PhantomJsFailingIT.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void seleniumTestRuleWebDriverClosedErrorIT() throws Throwable {
        runTest(SeleniumTestRuleWebDriverClosedErrorIT.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void seleniumTestRuleCustomDirectoryIT() throws Throwable {
        runTest(SeleniumTestRuleCustomDirectoryIT.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void phantomJsFinalFieldIT() throws Throwable {
        runTest(PhantomJsFinalFieldIT.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void phantomJsNoWebDriverFieldIT() throws Throwable {
        runTest(PhantomJsNoWebDriverFieldIT.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void phantomJsNoAnnotationsIT() throws Throwable {
        runTest(PhantomJsNoAnnotationsIT.class);
    }

    @Test
    public void jBrowserIT() throws Throwable {
        runTest(JBrowserIT.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void jBrowserFailingIT() throws Throwable {
        runTest(JBrowserFailingIT.class);
    }

    @Test
    public void chromeDriverIT() throws Throwable {
        runTest(ChromeDriverIT.class);
    }

    @Test(expected = NoSuchElementException.class)
    public void chromeDriverFailingIT() throws Throwable {
        runTest(ChromeDriverFailingIT.class);
    }
}
