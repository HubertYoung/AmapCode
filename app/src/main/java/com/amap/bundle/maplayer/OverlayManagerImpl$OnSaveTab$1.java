package com.amap.bundle.maplayer;

import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;

public class OverlayManagerImpl$OnSaveTab$1 implements Callback<Integer> {
    final /* synthetic */ FavoritePOI a;
    final /* synthetic */ b b;

    public void error(Throwable th, boolean z) {
    }

    public OverlayManagerImpl$OnSaveTab$1(b bVar, FavoritePOI favoritePOI) {
        this.b = bVar;
        this.a = favoritePOI;
    }

    public void callback(Integer num) {
        if (this.a.getPoiExtra().containsKey(IOverlayManager.POI_EXTRA_FROM_FAV) || (xn.this.m != null && xn.this.m.isFooterMapPointRequestOutter())) {
            xn.a(xn.this, (POI) this.a, num.intValue());
        }
    }
}
