package com.autonavi.annotation.helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface JsActionLogger {
    String[] actions() default {};

    String[] jsActions() default {};

    String module() default "";
}
