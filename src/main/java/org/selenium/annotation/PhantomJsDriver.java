package org.selenium.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * arguments are command line arguments for phantomjs which are listed in
 * {@link http://phantomjs.org/api/command-line.html}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PhantomJsDriver {
    String phantomJsPathProperty() default "";

    String phantomJsPath() default "";

    String version() default "";

    /**
     * arguments are command line arguments for phantomjs which are listed in
     * {@link http://phantomjs.org/api/command-line.html}
     */
    String[] arguments() default {};
}