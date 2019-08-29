package com.googlecode.androidannotations.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Trace {
    public static final String DEFAULT_TAG = "NO_TAG";

    int level() default 4;

    String tag() default "NO_TAG";
}
