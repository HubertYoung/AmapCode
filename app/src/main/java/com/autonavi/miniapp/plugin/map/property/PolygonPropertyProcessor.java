package com.autonavi.miniapp.plugin.map.property;

import android.content.Context;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.MapJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

public class PolygonPropertyProcessor extends BasePropertyProcessor {

    public static class Polygon implements Serializable {
        public String color;
        public String fillColor;
        public List<Point> points;
        public double width;
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
    }

    public PolygonPropertyProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super(weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(MapJsonObj mapJsonObj) {
        if (this.mRealView != null && mapJsonObj.polygon != null) {
            doClear();
            this.mRealView.getPolygonOverlay().addMiniAppPolygon(mapJsonObj.polygon);
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "setPolygons");
        }
    }

    /* access modifiers changed from: protected */
    public void doClear() {
        this.mRealView.getPolygonOverlay().clearPolygon();
    }
}
