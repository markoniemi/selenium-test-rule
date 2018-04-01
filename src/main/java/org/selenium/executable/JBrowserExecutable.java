package org.selenium.executable;

import org.openqa.selenium.WebDriver;
import org.selenium.annotation.JBrowserDriver;

public class JBrowserExecutable {

    public static WebDriver createDriver(JBrowserDriver annotation) {
        return new com.machinepublishers.jbrowserdriver.JBrowserDriver();
    }
}
