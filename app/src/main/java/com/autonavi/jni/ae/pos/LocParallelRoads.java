package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocParallelRoads implements Serializable {
    public int nCount;
    public int nFlag;
    public int nStatus;
    public LocParaRoadInfo[] paraRoads;

    public LocParallelRoads(int i, int i2, int i3, LocParaRoadInfo[] locParaRoadInfoArr) {
        this.nStatus = i;
        this.nFlag = i2;
        this.nCount = i3;
        this.paraRoads = locParaRoadInfoArr;
    }

    public LocParallelRoads() {
    }
}
