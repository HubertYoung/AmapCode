package com.autonavi.bundle.uitemplate.mapwidget.inter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface AlignType {
    public static final int CENTER_BOTTOM = 8;
    public static final int CENTER_TOP = 7;
    public static final int FOOTER = 10;
    public static final int HEADER = 9;
    public static final int LEFT_BOTTOM = 3;
    public static final int LEFT_CENTER = 2;
    public static final int LEFT_TOP = 1;
    public static final int RIGHT_BOTTOM = 6;
    public static final int RIGHT_CENTER = 5;
    public static final int RIGHT_TOP = 4;
}
