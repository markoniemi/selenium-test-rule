package org.selenium.test;

import org.junit.Assert;
import org.junit.Ignore;
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
@Ignore
public class JBrowserDriverTest {
	@SeleniumJBrowserDriver
	public WebDriver webDriver;
	@Rule
	public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();

	@Test
	public void jBrowserDriverTest() {
        webDriver.get("file://localhost/" + System.getProperty("user.dir") + "/src/test/resources/test.html");
        Assert.assertEquals("Test", webDriver.getTitle());
		webDriver.findElement(By.id("nonexistent")).click();
		log.debug(webDriver.getCurrentUrl());
	}
}