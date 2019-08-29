package com.autonavi.minimap.bundle.maphome.tipsview;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.ae.search.model.GPoiBean;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.minimap.R;
import java.util.Timer;

public class GeoCodeCheckerImpl$1 implements Callback<awg> {
    final /* synthetic */ Callback a;
    final /* synthetic */ GeoPoint b;
    final /* synthetic */ dao c;

    public GeoCodeCheckerImpl$1(dao dao, Callback callback, GeoPoint geoPoint) {
        this.c = dao;
        this.a = callback;
        this.b = geoPoint;
    }

    public void callback(awg awg) {
        this.c.a = null;
        if (!TextUtils.isEmpty(awg.c)) {
            this.c.f = awg.c;
            this.c.g.setName(this.c.f);
        }
        this.c.b.clear();
        this.c.b.addAll(awg.b);
        String string = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
        if (this.c.b.size() > 0) {
            string = ((POI) this.c.b.get(0)).getName();
        }
        dao.a(this.c, string, this.a);
        if (this.c.b.size() > 0) {
            this.c.d = new a(this.a);
            this.c.c = new Timer("OverlayManager.Timer");
            this.c.c.scheduleAtFixedRate(this.c.d, 20000, 20000);
        }
    }

    public void error(Throwable th, boolean z) {
        this.c.a = null;
        this.c.g = (GeocodePOI) POIFactory.createPOI(this.c.f, this.b).as(GeocodePOI.class);
        adz adz = (adz) a.a.a(adz.class);
        if (adz != null) {
            ady b2 = adz.b();
            if (b2 != null) {
                b2.a((float) this.b.getLongitude(), (float) this.b.getLatitude(), (OnSearchResultListener) new OnSearchResultListener() {
                    public final void onGetSearchResult(int i, final GPoiResult gPoiResult) {
                        aho.a(new Runnable() {
                            public final void run() {
                                if (gPoiResult == null || gPoiResult.getPoiList() == null || gPoiResult.getPoiList().size() <= 0 || TextUtils.isEmpty(gPoiResult.getPoiList().get(0).getName())) {
                                    dao.a(GeoCodeCheckerImpl$1.this.c, GeoCodeCheckerImpl$1.this.c.f, GeoCodeCheckerImpl$1.this.a);
                                    return;
                                }
                                GPoiBase gPoiBase = gPoiResult.getPoiList().get(0);
                                if (gPoiBase instanceof GPoiBean) {
                                    GPoiBean gPoiBean = (GPoiBean) gPoiBase;
                                    if (!TextUtils.isEmpty(gPoiBean.getAddr())) {
                                        GeoCodeCheckerImpl$1.this.c.f = gPoiBean.getAddr();
                                    }
                                    dao.a(GeoCodeCheckerImpl$1.this.c, gPoiBean.getName(), GeoCodeCheckerImpl$1.this.a);
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
