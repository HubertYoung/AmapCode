package com.autonavi.minimap.route.bus.localbus.overlay;

import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

public class RecommonPopOverlay extends PointOverlay<PointOverlayItem> {
    public RecommonPopOverlay(bty bty) {
        super(bty);
    }

    public void addMarker(int i, GeoPoint geoPoint, View view, MapViewLayoutParams mapViewLayoutParams) {
        if (geoPoint != null && view != null && mapViewLayoutParams != null && this.mMapView != null) {
            this.mMapView.a(view, (LayoutParams) mapViewLayoutParams);
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.mDefaultMarker = createMarker(i, view, 5, 0.0f, 0.0f, false);
            this.mMapView.a(view);
            addItem(pointOverlayItem);
        }
    }
}
