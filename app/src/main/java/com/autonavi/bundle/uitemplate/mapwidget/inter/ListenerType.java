package com.autonavi.bundle.uitemplate.mapwidget.inter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface ListenerType {
    public static final int CLICK = 0;
    public static final int LONG_CLICK = 10;
    public static final int TOUCH = 1;
    public static final int UN_DISPATCH = -1;
}
