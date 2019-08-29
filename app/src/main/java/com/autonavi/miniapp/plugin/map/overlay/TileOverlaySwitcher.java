package com.autonavi.miniapp.plugin.map.overlay;

import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

public class TileOverlaySwitcher {
    private static alt mOpenLayerGetInfo;

    public static void hideOpenLayer(int i) {
        if (mOpenLayerGetInfo != null) {
            alv alv = new alv();
            alv.a = i;
            alv.b = mOpenLayerGetInfo.a;
            alv.c = mOpenLayerGetInfo.b;
            alv.d = 1;
            getMapView().a(alv);
        }
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c(i);
        }
    }

    private static bty getMapView() {
        return DoNotUseTool.getMapView().e();
    }

    public static void showOpenLayer(int i) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.b(i);
        }
        alu alu = new alu();
        alu.a = i;
        mOpenLayerGetInfo = null;
        mOpenLayerGetInfo = getMapView().a(alu);
        alv alv = new alv();
        alv.a = i;
        alv.b = 20;
        alv.c = 16;
        alv.d = 1;
        getMapView().a(alv);
    }
}
