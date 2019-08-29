package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocGPSHistory implements Serializable {
    public LocGPSHistoryInfo[] buffer;
    public String naviID;
    public int naviType;
    public boolean nomove;
    public int pageType;
    public int routeSource;
    public long timestampNavi;
    public long timestampRoute;

    public LocGPSHistory() {
    }

    public LocGPSHistory(LocGPSHistoryInfo[] locGPSHistoryInfoArr, boolean z, int i, int i2, String str, int i3, long j, long j2) {
        this.buffer = locGPSHistoryInfoArr;
        this.nomove = z;
        this.pageType = i;
        this.naviType = i2;
        this.naviID = str;
        this.routeSource = i3;
        this.timestampNavi = j;
        this.timestampRoute = j2;
    }
}
