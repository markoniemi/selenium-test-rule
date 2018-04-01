package org.selenium.executable;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.selenium.annotation.PhantomJsDriver;

import io.github.bonigarcia.wdm.PhantomJsDriverManager;

public class PhantomJsExecutable {
    private PhantomJsExecutable() {
        // Util class has a private constructor
    }

    public static WebDriver createDriver(PhantomJsDriver annotation) {
        downloadExecutable(annotation.version());
        WebDriver webDriver = createWebDriver(createArgumentMap(annotation.arguments()));
        webDriver.manage().window().setSize(new Dimension(1200, 800));
        return webDriver;
    }

    private static void downloadExecutable(String version) {
        PhantomJsDriverManager.getInstance().version(version).setup();
    }

    private static Map<String, String[]> createArgumentMap(String[] arguments) {
        Map<String, String[]> argumentMap = new HashMap<>();
        argumentMap.put(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, arguments);
        return argumentMap;
    }

    private static WebDriver createWebDriver(Map<String, String[]> argumentMap) {
        Capabilities serviceCapabilities = new DesiredCapabilities(argumentMap);
        PhantomJSDriverService service = PhantomJSDriverService.createDefaultService(serviceCapabilities);
        DesiredCapabilities driverCapabilities = DesiredCapabilities.phantomjs();
        return new PhantomJSDriver(service, driverCapabilities);
    }
}
