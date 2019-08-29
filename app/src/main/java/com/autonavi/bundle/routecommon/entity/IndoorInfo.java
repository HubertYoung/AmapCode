package com.autonavi.bundle.routecommon.entity;

import java.io.Serializable;

public class IndoorInfo implements Serializable {
    private static final long serialVersionUID = -5612357979464070145L;
    public String building;
    public String buildingId;
    public String dsp;
    public int floor;
    public String floorName;
    public byte indoorPathEndAction;
    public byte indoorPathStartAction;
    public double[] mLats;
    public double[] mLons;
    public int[] mXs;
    public int[] mYs;
    public int nextFloor;
    public String nextFloorName;
    public String time;
}
