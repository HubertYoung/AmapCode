package com.amap.bundle.maplayer;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.minimap.R;

public class OverlayManagerImpl$2 implements Callback<Integer> {
    final /* synthetic */ GeoPoint a;
    final /* synthetic */ xn b;

    public void error(Throwable th, boolean z) {
    }

    public OverlayManagerImpl$2(xn xnVar, GeoPoint geoPoint) {
        this.b = xnVar;
        this.a = geoPoint;
    }

    public void callback(Integer num) {
        a e = this.b.n;
        GeoPoint geoPoint = this.a;
        int intValue = num.intValue();
        e.e = 0;
        e.b.clear();
        if (e.a != null) {
            e.a.cancel();
        }
        e.a();
        e.f = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
        e.g = (GeocodePOI) POIFactory.createPOI(e.f, geoPoint).as(GeocodePOI.class);
        IMapRequestManager iMapRequestManager = (IMapRequestManager) ank.a(IMapRequestManager.class);
        if (iMapRequestManager != null) {
            e.a = iMapRequestManager.getReverseGeocodeResult(geoPoint, new OverlayManagerImpl$GeoCodeChecker$1(e, intValue, geoPoint));
        }
    }
}
