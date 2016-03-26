package org.selenium.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumJBrowserDriver;
import org.selenium.SeleniumTestRule;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class JBrowserDriverTest {
	@SeleniumJBrowserDriver
	public WebDriver webDriver;
	@Rule
	public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

	@Test
	public void jBrowserDriverTest() {
		webDriver.get("https://www.google.fi");
		Assert.assertEquals("Google", webDriver.getTitle());
		// jBrowserDriver does not throw NoSuchElementException when element is
		webDriver.findElement(By.id("nonexistent")).click();
		log.debug(webDriver.getCurrentUrl());
	}
}