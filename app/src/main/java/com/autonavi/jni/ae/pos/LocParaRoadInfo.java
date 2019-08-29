package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocParaRoadInfo implements Serializable {
    public int nType;
    public long u64RoadId;
    public char u8FormWay;
    public char u8LinkType;

    public LocParaRoadInfo(long j, int i, char c, char c2) {
        this.u64RoadId = j;
        this.nType = i;
        this.u8FormWay = c;
        this.u8LinkType = c2;
    }

    public LocParaRoadInfo() {
    }
}
