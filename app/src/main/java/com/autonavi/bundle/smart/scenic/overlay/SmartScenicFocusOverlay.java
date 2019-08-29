package com.autonavi.bundle.smart.scenic.overlay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.sina.weibo.sdk.utils.UIUtils;

public class SmartScenicFocusOverlay extends PointOverlay {
    private String focusKey = "";
    private GeoPointHD lastPoint;
    private Context mContext;

    public SmartScenicFocusOverlay(bty bty) {
        super(bty);
    }

    public void addSmartScenicFoucsPoint(Context context, @NonNull GeoPointHD geoPointHD, String str) {
        clear();
        this.lastPoint = geoPointHD;
        this.focusKey = str;
        this.mContext = context;
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPointHD);
        pointOverlayItem.mDefaultMarker = createMarker(bby.a("b_poi_hl", this.mContext), 9, 0.5f, 0.87f);
        addFoliter();
        addItem(pointOverlayItem);
        setClickable(false);
        if (this.mMapView != null) {
            this.mMapView.a((GLGeoPoint) geoPointHD);
        }
    }

    public boolean clear() {
        if (this.mMapView != null && !TextUtils.isEmpty(this.focusKey)) {
            this.mMapView.a(this.focusKey);
            this.lastPoint = null;
        }
        return super.clear();
    }

    public void addFoliter() {
        int dip2px = UIUtils.dip2px(5, this.mContext);
        if (this.mMapView != null && !TextUtils.isEmpty(this.focusKey) && this.lastPoint != null) {
            float f = (float) dip2px;
            this.mMapView.a(this.lastPoint.x, this.lastPoint.y, 2, f, f, this.focusKey);
        }
    }
}
