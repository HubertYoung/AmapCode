package com.autonavi.miniapp.plugin.map.property;

import android.content.Context;
import com.alipay.mobile.h5container.api.H5Page;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.MapJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import java.io.Serializable;
import java.lang.ref.WeakReference;

public class CirclesPropertyProcessor extends BasePropertyProcessor {

    public static class Circle implements Serializable {
        public String color;
        public String fillColor;
        public double latitude;
        public double longitude;
        public double radius;
        public double strokeWidth;
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
    }

    public CirclesPropertyProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super(weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(MapJsonObj mapJsonObj) {
        if (this.mRealView != null && mapJsonObj.circles != null) {
            doClear();
            this.mRealView.getPolygonOverlay().addMiniAppCircle(mapJsonObj.circles);
        }
    }

    /* access modifiers changed from: protected */
    public void doClear() {
        this.mRealView.getPolygonOverlay().clearCircle();
    }
}
