package org.selenium.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumChromeDriver;
import org.selenium.SeleniumTestRule;

import lombok.Data;

@Data
public class ChromeDriverTest {
	@SeleniumChromeDriver
	public WebDriver webDriver;
	@Rule
	public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

	@Test
	public void chromeDriverTest() {
		webDriver.get("https://www.google.fi");
		Assert.assertEquals("Google", webDriver.getTitle());
		// jBrowserDriver does not throw NoSuchElementException when element is
		webDriver.findElement(By.id("nonexistent")).click();
	}
}