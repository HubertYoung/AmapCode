package com.autonavi.minimap.ajx3.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@Target({ElementType.TYPE})
@Keep
@Retention(RetentionPolicy.RUNTIME)
public @interface AjxModule {
    boolean isInUiThread() default true;

    String value() default "";
}
