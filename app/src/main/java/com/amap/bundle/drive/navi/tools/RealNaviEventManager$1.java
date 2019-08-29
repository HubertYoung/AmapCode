package com.amap.bundle.drive.navi.tools;

import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import java.util.ArrayList;

public class RealNaviEventManager$1 implements Callback<ArrayList<GeoPoint>> {
    final /* synthetic */ long a;
    final /* synthetic */ oe b;

    public RealNaviEventManager$1(oe oeVar, long j) {
        this.b = oeVar;
        this.a = j;
    }

    public void callback(ArrayList<GeoPoint> arrayList) {
        this.b.f.setOrgRouteLine(arrayList);
        NaviManager.releasePathResult(this.a);
    }

    public void error(Throwable th, boolean z) {
        th.printStackTrace();
        NaviManager.releasePathResult(this.a);
    }
}
