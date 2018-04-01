package org.selenium.executable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.selenium.annotation.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class ChromeExecutable {

    private ChromeExecutable() {
        // Util class has a private constructor
    }

    public static WebDriver createDriver(ChromeDriver annotation) {
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        if (annotation.headless()) {
            options.addArguments("headless");
            options.addArguments("window-size=1200x600");
            options.addArguments("no-sandbox");
        }
        options.addArguments(annotation.arguments());
        return new org.openqa.selenium.chrome.ChromeDriver(options);
    }
}
