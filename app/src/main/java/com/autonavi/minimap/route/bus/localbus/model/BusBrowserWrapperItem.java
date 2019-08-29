package com.autonavi.minimap.route.bus.localbus.model;

import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.IndoorInfo;
import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;

public class BusBrowserWrapperItem implements Serializable {
    private static final long serialVersionUID = -7354073014074267241L;
    public GeoPoint endPoint;
    public int index;
    public IndoorInfo indoorInfo;
    public byte indoorPathEndAction;
    public byte indoorPathStartAction;
    public boolean isIndoor;
    public int mCurSegment;
    public String mFootNaviEndName;
    public String mSectionColor;
    public BusPathSection mSectionData;
    public int mSectionType;
    public int mType;
    public CharSequence mainDes;
    public int nextFloor;
    public String nextFloorName;
    public GeoPoint startPoint;
    public String subDes;
}
