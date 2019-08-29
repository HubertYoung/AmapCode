package com.amap.bundle.drive.radar.bean;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class RouteBoardPOI {
    public String angle;
    public String buildid;
    public String extendInfoFlag;
    public int floor;
    public String floorName;
    public String name;
    public double[] naviPos;
    public boolean overhead;
    public String parentID;
    public String parentName;
    public String parentRel;
    public String parentSimpleName;
    public String poiID;
    public int pointCnt;
    public double[] realPos;
    public long roadId;
    public float sigshelter;
    public int type;
    public String typeCode;
}
