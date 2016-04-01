package org.selenium.test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.SeleniumFirefoxDriver;
import org.selenium.SeleniumTestRule;

import lombok.Data;

@Data
@Ignore
public class FirefoxDriverTest {
	@SeleniumFirefoxDriver
	public WebDriver webDriver;
	@Rule
	public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

	@Test
	public void firefoxDriverTest() {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
		// jBrowserDriver does not throw NoSuchElementException when element is
		webDriver.findElement(By.id("nonexistent")).click();
	}
}