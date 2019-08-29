package com.amap.bundle.maplayer;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.ae.search.model.GPoiBean;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.minimap.R;
import java.util.Timer;

public class OverlayManagerImpl$GeoCodeChecker$1 implements Callback<awg> {
    final /* synthetic */ int a;
    final /* synthetic */ GeoPoint b;
    final /* synthetic */ a c;

    OverlayManagerImpl$GeoCodeChecker$1(a aVar, int i, GeoPoint geoPoint) {
        this.c = aVar;
        this.a = i;
        this.b = geoPoint;
    }

    public void callback(awg awg) {
        this.c.a = null;
        if ((xn.this.m != null && xn.this.m.isPoiDetailPageEnabled()) || (xn.this.getGeoCodeOverlay().getItem(0) != null && xn.this.m != null && xn.this.m.isTokenAvailable(this.a))) {
            if (!TextUtils.isEmpty(awg.c)) {
                this.c.f = awg.c;
                this.c.g.setName(this.c.f);
            }
            this.c.g.setAdCode(awg.f);
            this.c.g.setCityCode(awg.a);
            this.c.b.clear();
            this.c.b.addAll(awg.b);
            String string = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
            if (this.c.b.size() > 0) {
                string = this.c.b.get(0).getName();
            }
            a.a(this.c, string, this.a);
            if (this.c.b.size() > 0) {
                this.c.d = new C0108a(this.c, 0);
                this.c.d.a = this.a;
                this.c.c = new Timer("OverlayManager.Timer");
                this.c.c.scheduleAtFixedRate(this.c.d, 20000, 20000);
            }
        }
    }

    public void error(Throwable th, boolean z) {
        ady ady = null;
        this.c.a = null;
        if (xn.this.getGeoCodeOverlay().getItem(0) != null || xn.this.m != null) {
            this.c.g = (GeocodePOI) POIFactory.createPOI(this.c.f, this.b).as(GeocodePOI.class);
            adz adz = (adz) a.a.a(adz.class);
            if (adz != null) {
                ady = adz.b();
            }
            if (ady != null) {
                ady.a((float) this.b.getLongitude(), (float) this.b.getLatitude(), (OnSearchResultListener) new OnSearchResultListener() {
                    public final void onGetSearchResult(int i, final GPoiResult gPoiResult) {
                        aho.a(new Runnable() {
                            public final void run() {
                                if (gPoiResult == null || gPoiResult.getPoiList() == null || gPoiResult.getPoiList().size() <= 0 || TextUtils.isEmpty(gPoiResult.getPoiList().get(0).getName())) {
                                    a.a(OverlayManagerImpl$GeoCodeChecker$1.this.c, OverlayManagerImpl$GeoCodeChecker$1.this.c.f, OverlayManagerImpl$GeoCodeChecker$1.this.a);
                                    return;
                                }
                                GPoiBase gPoiBase = gPoiResult.getPoiList().get(0);
                                if (gPoiBase instanceof GPoiBean) {
                                    GPoiBean gPoiBean = (GPoiBean) gPoiBase;
                                    if (!TextUtils.isEmpty(gPoiBean.getAddr())) {
                                        OverlayManagerImpl$GeoCodeChecker$1.this.c.f = gPoiBean.getAddr();
                                        if (OverlayManagerImpl$GeoCodeChecker$1.this.c.g != null) {
                                            OverlayManagerImpl$GeoCodeChecker$1.this.c.g.setName(OverlayManagerImpl$GeoCodeChecker$1.this.c.f);
                                        }
                                    }
                                    a.a(OverlayManagerImpl$GeoCodeChecker$1.this.c, gPoiBean.getName(), OverlayManagerImpl$GeoCodeChecker$1.this.a);
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
