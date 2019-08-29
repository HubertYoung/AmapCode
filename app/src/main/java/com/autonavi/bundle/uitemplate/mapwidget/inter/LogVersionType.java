package com.autonavi.bundle.uitemplate.mapwidget.inter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.LOCAL_VARIABLE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface LogVersionType {
    public static final String REDESIGN = "redesign";
    public static final String UN_REDESIGN = "非redesign";
}
