package com.alipay.mobile.beehive.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    public static final String THREAD_BACKGROUND = "background";
    public static final String THREAD_CURRENT = "current";
    public static final String THREAD_UI = "ui";

    String name() default "";

    String threadMode() default "current";

    String whiteListKey() default "";
}
