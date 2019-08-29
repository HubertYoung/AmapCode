package com.autonavi.minimap.route.foot.model;

import com.autonavi.bundle.routecommon.entity.IndoorInfo;
import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;
import java.util.ArrayList;

public class RouteFootListItemData implements Serializable {
    public static final int ONFOOT_DEFUALT_DES = -1;
    public static final int ONFOOT_DEVICE_DES = 2;
    public static final int ONFOOT_END_DES = 4;
    public static final int ONFOOT_INDOOR_DES = 3;
    public static final int ONFOOT_PROCESS_DES = 1;
    public static final int ONFOOT_START_DES = 0;
    private static final long serialVersionUID = -1827685171141404519L;
    public byte action;
    public ArrayList<CharSequence> actionDes = new ArrayList<>();
    public int actionIcon = -1;
    public String actionStr;
    public String assiActionNavi;
    public int desType;
    public String distanceDes = "";
    public byte indoorPathEndAction;
    public byte indoorPathStartAction;
    public boolean isIndoor;
    public IndoorInfo mIndoorInfo;
    public String mainActionNavi;
    public int nextFloor;
    public String nextFloorName;
    public String nextStreetName;
    public String onFootDistance;
    public GeoPoint[] pointArray;
    public String previousRoadName;
    public String realStreetName;
    public int startDirection;
    public String streetName;
    public String tmp1;
    public String tmp2;
    public String tmp3;
    public int viewIndex;
    public int walkType;
}
