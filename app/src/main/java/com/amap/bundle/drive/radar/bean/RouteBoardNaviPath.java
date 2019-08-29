package com.amap.bundle.drive.radar.bean;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class RouteBoardNaviPath {
    public float angleComp;
    public float angleGps;
    public int angleType;
    public float direction;
    public RouteBoardPOI[] endPOIs;
    public float fittingCredit;
    public float fittingDir;
    public int formWay;
    public float gpsCredit;
    public int linkType;
    public float matchingDir;
    public float precision;
    public float radius;
    public float reliability;
    public int sigType;
    public float speed;
    public RouteBoardPOI[] startPOIs;
    public RouteBoardPOI[] viaPOIs;
}
