package com.amap.bundle.drivecommon.model;

import com.amap.bundle.drivecommon.overlay.RouteCarResultRouteItem;
import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NavigationSection implements Serializable {
    private static final long serialVersionUID = 1179033518813552170L;
    public boolean isRightPassArea = true;
    public int mChargeLength;
    public int mDataLength;
    public GeoPoint[] mGeoPoints;
    public int mIndex;
    public ArrayList<RouteCarResultRouteItem> mLineBgOverlayList;
    public ArrayList<RouteCarResultRouteItem> mLineOverlayList;
    public byte mNaviAssiAction;
    public byte mNavigtionAction;
    public int mPathlength;
    public int mPointNum;
    public transient List<RouteCarResultRouteItem> mRestrictedLineItemList;
    public String mStreetName;
    public int mTollCost;
    public String mTollPathName;
    public int mTrafficLights;
    public String midPoiName;
}
