package com.autonavi.minimap.ajx3.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@Target({ElementType.METHOD})
@Keep
@Retention(RetentionPolicy.RUNTIME)
public @interface AjxMethod {
    String invokeMode() default "async";

    String value() default "";
}
