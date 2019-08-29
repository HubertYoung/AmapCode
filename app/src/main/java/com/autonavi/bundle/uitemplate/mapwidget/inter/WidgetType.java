package com.autonavi.bundle.uitemplate.mapwidget.inter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface WidgetType {
    public static final String ACTIVITY = "activity";
    public static final String AUTO_REMOTE = "auto_remote";
    public static final String CENTRAL_CARD = "central_card";
    public static final String COMBINE = "combine";
    public static final String COMPASS = "compass";
    public static final String DIY = "diy";
    public static final String FLOOR = "floor";
    public static final String GPS = "gps";
    public static final String HOME_CORP = "home_corp";
    public static final String INDOOR_GUIDE = "indoor_guide";
    public static final String LAYER = "layer";
    public static final String MINI_GPS = "mini_gps";
    public static final String MINI_SEARCH = "mini_search";
    public static final String MSG_BOX = "msg_box";
    public static final String NEARBY_SEARCH = "nearby_search";
    public static final String PATHPREFERENCEANDSCALE = "pathPreferenceAndScale";
    public static final String PATHTIPSENTERVIEW = "pathTipsEnterView";
    public static final String ROUTE_LINE = "route_line";
    public static final String SCALE = "scale";
    public static final String SCENIC_AREA = "scenic_area";
    public static final String TRAFFIC = "traffic";
    public static final String USER_CENTER = "user_icon";
    public static final String WEATHER_RESTRICT = "weather_restrict";
    public static final String ZOOM_IN_OUT = "zoom_in_out";
}
