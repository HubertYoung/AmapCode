package com.amap.bundle.drive.ajx.inter;

public interface JsCommandCallback {
    public static final int COMMAND_AJX_BACK_PRESS = 6;
    public static final int COMMAND_OPEN_TAXI = 7;
    public static final int COMMAND_UNLOCK_GPS_BUTTON = 1;
    public static final int DAY_NIGHT_STATE_CHANGED = 3;
    public static final int NAVI_END_COMPENSATE_CLICK = 4;
    public static final int REAL_CITY_STATE_CHANGED = 2;

    boolean callback(int i, String... strArr);
}
