package com.autonavi.minimap.route.bus.busline.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BusLineStationPointOverlay extends PointOverlay<PointOverlayItem> {
    public void resumeMarker() {
    }

    public BusLineStationPointOverlay(bty bty) {
        super(bty);
        showReversed(true);
        setHideIconWhenCovered(false);
        setBubbleAnimator(2);
    }

    public void createOverlayItem(GeoPoint geoPoint, int i) {
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        pointOverlayItem.mDefaultMarker = createMarker(getMarkerIdFromIndex(i), 5);
        pointOverlayItem.mBubbleMarker = createMarker(getFocusMarkerIdFromIndex(i), 5);
        addItem(pointOverlayItem);
    }

    public List<GeoPoint> getItemsGeoPoint() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mItemList.iterator();
        while (it.hasNext()) {
            arrayList.add(((PointOverlayItem) it.next()).getGeoPoint());
        }
        return arrayList;
    }

    private int getMarkerIdFromIndex(int i) {
        switch (i) {
            case 0:
                return R.drawable.b_poi_1;
            case 1:
                return R.drawable.b_poi_2;
            case 2:
                return R.drawable.b_poi_3;
            case 3:
                return R.drawable.b_poi_4;
            case 4:
                return R.drawable.b_poi_5;
            case 5:
                return R.drawable.b_poi_6;
            case 6:
                return R.drawable.b_poi_7;
            case 7:
                return R.drawable.b_poi_8;
            case 8:
                return R.drawable.b_poi_9;
            case 9:
                return R.drawable.b_poi_10;
            default:
                return R.drawable.b_poi;
        }
    }

    public int getFocusMarkerIdFromIndex(int i) {
        switch (i) {
            case 0:
                return R.drawable.b_poi_1_hl;
            case 1:
                return R.drawable.b_poi_2_hl;
            case 2:
                return R.drawable.b_poi_3_hl;
            case 3:
                return R.drawable.b_poi_4_hl;
            case 4:
                return R.drawable.b_poi_5_hl;
            case 5:
                return R.drawable.b_poi_6_hl;
            case 6:
                return R.drawable.b_poi_7_hl;
            case 7:
                return R.drawable.b_poi_8_hl;
            case 8:
                return R.drawable.b_poi_9_hl;
            case 9:
                return R.drawable.b_poi_10_hl;
            default:
                return R.drawable.b_poi_hl;
        }
    }
}
