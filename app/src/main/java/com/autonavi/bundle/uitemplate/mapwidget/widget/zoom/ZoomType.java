package com.autonavi.bundle.uitemplate.mapwidget.widget.zoom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface ZoomType {
    public static final int CITY = 11;
    public static final int COUNTRY = 4;
    public static final int PROVINCE = 8;
    public static final int STREET = 17;
}
