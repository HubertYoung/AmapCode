package com.autonavi.bundle.searchcommon.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.base.overlay.PolygonOverlay;
import com.autonavi.minimap.base.overlay.PolygonOverlayItem;
import java.util.ArrayList;

public class SearchPolygonOverlay extends PolygonOverlay<PolygonOverlayItem> {
    public static final int AD_POLYGON_COLOR = 553685503;
    private POI currentPOI = null;
    public int mPolygonColor = 650440190;

    public void onPause() {
    }

    public void onResume() {
    }

    public SearchPolygonOverlay(bty bty) {
        super(bty);
    }

    public void drawPolygon(POI poi) {
        if (poi != null) {
            drawPolygon((poi.getPoiExtra() == null || poi.getPoiExtra().get("poi_polygon_bounds") == null) ? null : (ArrayList) poi.getPoiExtra().get("poi_polygon_bounds"));
            this.currentPOI = poi;
        }
    }

    public void drawPolygon(ArrayList<ArrayList<GeoPoint>> arrayList) {
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                addPolygon(arrayList.get(i), this.mPolygonColor);
            }
        }
    }

    public POI getCurrentPOI() {
        return this.currentPOI;
    }
}
