package com.autonavi.bundle.uitemplate.mapwidget.inter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface WidgetPriority {
    public static final int ACTIVITY = 35;
    public static final int AUTO_REMOTE = 40;
    public static final int COMPASS = 25;
    public static final int DIY = 80;
    public static final int FLOOR = 45;
    public static final int GPS = 85;
    public static final int HOME_CORP = 75;
    public static final int INDOOR_GUIDE = 55;
    public static final int LAYER = 60;
    public static final int MINI_SEARCH = 90;
    public static final int MSG_BOX = 30;
    public static final int NEARBY_SEARCH = 95;
    public static final int RESTRICT = 70;
    public static final int ROUTE_LINE = 100;
    public static final int SCALE = 72;
    public static final int SCENIC_AREA = 50;
    public static final int TRAFFIC = 82;
    public static final int ZOOM_IN_OUT = 20;
}
