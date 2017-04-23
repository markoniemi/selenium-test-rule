package org.selenium.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PhantomJsDriver {
    public String phantomJsPathProperty() default "";
    public String phantomJsPath() default "";
    public String version() default "";
}