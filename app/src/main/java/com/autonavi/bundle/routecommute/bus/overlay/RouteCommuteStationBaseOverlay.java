package com.autonavi.bundle.routecommute.bus.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.base.overlay.PoiFilterPointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RouteCommuteStationBaseOverlay extends PoiFilterPointOverlay {
    protected int mChooseIndex = 0;
    private Set<GeoPoint> mShowedPoints = new HashSet();

    public RouteCommuteStationBaseOverlay(bty bty) {
        super(bty);
        setPoiFilterType(-1);
    }

    public void clearShowedPoints() {
        if (this.mShowedPoints != null) {
            this.mShowedPoints.clear();
        }
    }

    public void addShowedPoints(GeoPoint geoPoint) {
        if (this.mShowedPoints == null) {
            this.mShowedPoints = new HashSet();
        }
        this.mShowedPoints.add(geoPoint);
    }

    public void setFocus(int i, boolean z) {
        this.mChooseIndex = i;
        int findRealIndex = findRealIndex();
        super.setFocus(findRealIndex, z);
        StringBuilder sb = new StringBuilder("deng---setFocus mChooseIndex:");
        sb.append(this.mChooseIndex);
        sb.append(",realIndex:");
        sb.append(findRealIndex);
        azb.a(null, sb.toString());
    }

    private int findRealIndex() {
        try {
            List items = getItems();
            if (items != null && items.size() > 0) {
                for (int i = 0; i < items.size(); i++) {
                    if (((Integer) ((PointOverlayItem) items.get(i)).Tag).intValue() == this.mChooseIndex) {
                        return i;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mChooseIndex;
    }
}
