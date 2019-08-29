package com.autonavi.io.monitor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessPath {
    boolean isFile() default false;

    AccessMode[] mode() default {};

    String value() default "";
}
