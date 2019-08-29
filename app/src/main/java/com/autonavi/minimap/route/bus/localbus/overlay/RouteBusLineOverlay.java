package com.autonavi.minimap.route.bus.localbus.overlay;

import android.content.Context;
import android.util.DisplayMetrics;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.LineOverlayItem;

public class RouteBusLineOverlay extends LineOverlay {
    public void onPause() {
    }

    public void onResume() {
    }

    public RouteBusLineOverlay(bty bty) {
        super(bty);
    }

    public int createAndAddBackgroundLineItem(GeoPoint[] geoPointArr, int i) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, 4.0f));
        lineOverlayItem.setFillLineColor(i);
        lineOverlayItem.setFillLineId(R.drawable.map_frontlr);
        lineOverlayItem.setBackgroundColor(-1);
        lineOverlayItem.setBackgrondId(R.drawable.map_lr);
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public int createAndAddBackgroundLineItemRT(GeoPoint[] geoPointArr, int i, int i2, int i3, int i4) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, i3);
        lineOverlayItem.setFillLineId(R.drawable.map_frontlr);
        lineOverlayItem.setFillLineColor(i);
        lineOverlayItem.setBackgrondId(R.drawable.map_lr);
        lineOverlayItem.setBackgroundColor(i2);
        if (i4 > 0) {
            lineOverlayItem.setBorderLineWidth(agn.a((Context) AMapAppGlobal.getApplication(), (float) i4));
        }
        DisplayMetrics displayMetrics = AMapAppGlobal.getApplication().getResources().getDisplayMetrics();
        if (displayMetrics != null && displayMetrics.densityDpi <= 240) {
            lineOverlayItem.mLineProperty.q = 32.0f;
        }
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public int createAndAddLinkPathItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(4, geoPointArr, agn.a(this.mContext, 3.0f));
        lineOverlayItem.setFillLineId(R.drawable.map_lr_bus_foot_dott);
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public void createBusTrackItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem;
        if (geoPointArr != null && geoPointArr.length != 0) {
            if (ags.a(this.mMapView.d()).width() <= 480) {
                lineOverlayItem = new LineOverlayItem(5, geoPointArr, agn.a(this.mContext, 4.0f));
            } else {
                lineOverlayItem = new LineOverlayItem(5, geoPointArr, agn.a(this.mContext, 2.6f));
            }
            lineOverlayItem.setFillLineId(R.drawable.map_lr_realtime_bus);
            lineOverlayItem.setbTexPreMulAlpha(true);
            addItem(lineOverlayItem);
        }
    }

    public int createAndAddArrowLineItem(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(6, geoPointArr, agn.a(this.mContext, 4.0f));
        lineOverlayItem.setFillLineId(R.drawable.bus_map_aolr);
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public int createAndAddArrowLineItemRT(GeoPoint[] geoPointArr) {
        LineOverlayItem lineOverlayItem = new LineOverlayItem(6, geoPointArr, agn.a(this.mContext, 4.0f));
        lineOverlayItem.setFillLineId(R.drawable.route_map_aolr);
        addItem(lineOverlayItem);
        return getSize() - 1;
    }
}
