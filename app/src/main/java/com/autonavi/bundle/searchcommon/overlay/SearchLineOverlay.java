package com.autonavi.bundle.searchcommon.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import java.util.ArrayList;

public class SearchLineOverlay extends LineOverlay<LineOverlayItem> {
    private static final int DEFAULT_LINE_COLOR = -872377857;
    private static final int POLYGON_LINE_COLOR = -784291841;

    public void onPause() {
    }

    public void onResume() {
    }

    public SearchLineOverlay(bty bty) {
        super(bty);
    }

    public void showPolygonLine(POI poi, boolean z) {
        if (!(poi == null || poi.getPoiExtra() == null || poi.getPoiExtra().get("poi_polygon_bounds") == null)) {
            showPolygonLine((ArrayList) poi.getPoiExtra().get("poi_polygon_bounds"), z);
        }
    }

    public void showPolygonLine(ArrayList<ArrayList<GeoPoint>> arrayList, boolean z) {
        if (arrayList != null && arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                ArrayList arrayList2 = arrayList.get(i);
                int size = arrayList2.size();
                GeoPoint[] geoPointArr = new GeoPoint[size];
                for (int i2 = 0; i2 < size; i2++) {
                    geoPointArr[i2] = (GeoPoint) arrayList2.get(i2);
                }
                LineOverlayItem lineOverlayItem = new LineOverlayItem(!z ? 1 : 5, geoPointArr, 2);
                if (!z) {
                    lineOverlayItem.setFillLineId(R.drawable.map_lr);
                } else {
                    lineOverlayItem.setFillLineId(R.drawable.ic_cross_road_dash);
                }
                lineOverlayItem.setFillLineColor(POLYGON_LINE_COLOR);
                addItem(lineOverlayItem);
            }
        }
    }

    public void showRoadOverlay(POI poi) {
        if (!(poi == null || poi.getPoiExtra() == null || poi.getPoiExtra().get("poi_roadaoi_bounds") == null)) {
            ArrayList arrayList = (ArrayList) poi.getPoiExtra().get("poi_roadaoi_bounds");
            if (arrayList != null && arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    ArrayList arrayList2 = (ArrayList) arrayList.get(i);
                    int size = arrayList2.size();
                    GeoPoint[] geoPointArr = new GeoPoint[size];
                    for (int i2 = 0; i2 < size; i2++) {
                        geoPointArr[i2] = (GeoPoint) arrayList2.get(i2);
                    }
                    LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, 5);
                    lineOverlayItem.setFillLineId(R.drawable.map_lr);
                    lineOverlayItem.setFillLineColor(DEFAULT_LINE_COLOR);
                    addItem(lineOverlayItem);
                }
            }
        }
    }

    public void showStopLine(int[] iArr, int[] iArr2, int i, int i2) {
        if (iArr != null && iArr2 != null) {
            GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
            for (int i3 = 0; i3 < iArr.length; i3++) {
                geoPointArr[i3] = new GeoPoint();
                geoPointArr[i3].x = iArr[i3];
                geoPointArr[i3].y = iArr2[i3];
            }
            LineOverlayItem lineOverlayItem = new LineOverlayItem(5, geoPointArr, i);
            if (i2 == 0 || i2 != 1) {
                lineOverlayItem.setFillLineId(R.drawable.map_stop_exit_line);
            } else {
                lineOverlayItem.setFillLineId(R.drawable.map_stop_bg);
            }
            addItem(lineOverlayItem);
        }
    }
}
