package org.selenium.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumTestRule;
import org.selenium.WebDriverBinary;
import org.selenium.annotation.PhantomJsDriver;

public class PhantomJsTestWithBinaryPathProperty {
	@PhantomJsDriver(phantomJsPathProperty = WebDriverBinary.PHANTOMJS_BINARY)
	public WebDriver webDriver;
	@Rule
	public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

	@Test
	public void phantomJsTest() {
		webDriver.get("https://www.google.fi");
		Assert.assertEquals("Google", webDriver.getTitle());
		webDriver.findElement(By.id("nonexistent"));
	}
}
