package com.autonavi.bundle.routecommute.bus.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;

public class RouteCommuteStationOverlay extends RouteCommuteStationBaseOverlay {
    public RouteCommuteStationOverlay(bty bty) {
        super(bty);
    }

    public void addStationOverlay(ArrayList<BusPath> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            clear();
            clearShowedPoints();
            int i = 0;
            while (i < arrayList.size() && i < 3) {
                BusPath busPath = arrayList.get(i);
                if (busPath != null) {
                    drawOverlay(i, ayt.c(busPath), busPath);
                }
                i++;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawOverlay(int i, GeoPoint geoPoint, BusPath busPath) {
        if (geoPoint != null) {
            addShowedPoints(geoPoint);
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.Tag = Integer.valueOf(i);
            int i2 = R.drawable.drive_bus_station;
            if (ayt.b(busPath)) {
                i2 = R.drawable.drive_subway_default;
            }
            pointOverlayItem.mDefaultMarker = createMarker(i2, 5);
            getGLOverlay().setOverlayItemPriority(3 - i);
            addItem(pointOverlayItem);
        }
    }
}
