package org.selenium.executable;

import org.openqa.selenium.WebDriver;
import org.selenium.annotation.JBrowserDriver;

public class JBrowserExecutable {
    private JBrowserExecutable() {
        // Util class has a private constructor
    }

    @SuppressWarnings({ "squid:S1172", "unused" })
    public static WebDriver createDriver(JBrowserDriver annotation) {
        return new com.machinepublishers.jbrowserdriver.JBrowserDriver();
    }
}
