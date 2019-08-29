package com.autonavi.bundle.routecommute.drive.bean;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class DriveCommuteNaviPath {
    public float angleComp;
    public float angleGps;
    public int angleType;
    public float direction;
    public DriveCommutePOI[] endPOIs;
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
    public DriveCommutePOI[] startPOIs;
    public DriveCommutePOI[] viaPOIs;
}
