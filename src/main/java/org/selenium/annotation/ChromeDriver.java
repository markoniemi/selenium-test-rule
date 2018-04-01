package org.selenium.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ChromeDriver {
    String BINARY_PATH_PROPERTY = "webdriver.chrome.driver";

    String binaryPathProperty() default "";

    String binaryPath() default "";

    boolean headless() default true;

    String[] arguments() default {};
}
