package com.autonavi.minimap.map;

import android.graphics.drawable.Drawable;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import java.util.ArrayList;

public class LocalReportOverlay extends PointOverlay<TrafficOverlayItem> {
    private static final long DURATION = 30000;
    private static final long serialVersionUID = -2380204574730198373L;
    private int mPoiFilterHeight;
    private int mPoiFilterWidth;

    public void resumeMarker() {
    }

    public void resumeMarker(Marker marker) {
    }

    public LocalReportOverlay(bty bty) {
        super(bty);
        Drawable drawable = bty.a().getDrawable(R.drawable.traffic_road_jam_local);
        this.mPoiFilterWidth = drawable.getIntrinsicWidth();
        this.mPoiFilterHeight = drawable.getIntrinsicHeight();
    }

    public void addItem(TrafficOverlayItem trafficOverlayItem) {
        GeoPoint geoPoint = trafficOverlayItem.getGeoPoint();
        if (!(geoPoint == null || trafficOverlayItem.getTopic() == null)) {
            this.mMapView.a(geoPoint.x, geoPoint.y, 2, (float) this.mPoiFilterWidth, (float) this.mPoiFilterHeight, String.valueOf(trafficOverlayItem.getTopic().getFilterKey()));
        }
        super.addItem(trafficOverlayItem);
    }

    public void removeItem(TrafficOverlayItem trafficOverlayItem) {
        if (trafficOverlayItem.getTopic() != null) {
            this.mMapView.a(String.valueOf(trafficOverlayItem.getTopic().getFilterKey()));
        }
        super.removeItem(trafficOverlayItem);
    }

    public void checkOverTime() {
        ArrayList arrayList = new ArrayList();
        if (getSize() > 0) {
            for (int i = 0; i < getSize(); i++) {
                TrafficOverlayItem trafficOverlayItem = (TrafficOverlayItem) getItem(i);
                if (trafficOverlayItem != null) {
                    if (Math.abs(System.currentTimeMillis() - trafficOverlayItem.getGeneratedTime()) >= 30000) {
                        arrayList.add(trafficOverlayItem);
                    }
                }
            }
        }
        removeAll(arrayList);
    }
}
