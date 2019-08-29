package com.autonavi.bundle.routecommon.model;

import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.ajx3.util.Constants;

public enum RouteType {
    DEFAULT("0", -1, Constants.ANIMATOR_NONE),
    CAR("驾车", 0, "car"),
    BUS("公交", 1, ShowRouteActionProcessor.SEARCH_TYPE_BUS),
    ONFOOT("走", 2, "foot"),
    RIDE("骑行", 3, ShowRouteActionProcessor.SEARCH_TYPE_RIDE),
    TRAIN("火车票", 4, "train"),
    COACH("客车票", 5, "coach"),
    TAXI("打车", 6, FunctionSupportConfiger.TAXI_TAG),
    TRUCK("货车", 7, DriveUtil.NAVI_TYPE_TRUCK),
    ETRIP("E行", 8, "etrip"),
    FREERIDE("顺风车", 9, "freeride"),
    AIRTICKET("飞机票", 10, "airticket"),
    MOTOR("摩托车", 11, "motor");
    
    private String keyName;
    private String name;
    private int value;

    private RouteType(String str, int i, String str2) {
        this.value = i;
        this.name = str;
        this.keyName = str2;
    }

    public static RouteType getType(int i) {
        RouteType[] values;
        for (RouteType routeType : values()) {
            if (routeType.getValue() == i) {
                return routeType;
            }
        }
        return CAR;
    }

    public static RouteType getType(String str) {
        RouteType[] values;
        for (RouteType routeType : values()) {
            if (routeType.getKeyName().equals(str)) {
                return routeType;
            }
        }
        return DEFAULT;
    }

    public final String getName() {
        return this.name;
    }

    public final int getValue() {
        return this.value;
    }

    public final String getKeyName() {
        return this.keyName;
    }
}
