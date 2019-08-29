package com.autonavi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VirtualApp {
    public static final int PRIORITY_HIGHT = 100;
    public static final int PRIORITY_LOW = 10000;
    public static final int PRIORITY_NOMAL = 1000;

    int priority() default 10000;
}
