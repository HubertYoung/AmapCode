package com.autonavi.minimap.base.overlay;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;

public class PolygonOverlayItem {
    public int color;
    public GeoPoint[] points;

    public PolygonOverlayItem(GeoPoint[] geoPointArr, int i) {
        this.points = geoPointArr;
        this.color = i;
    }

    public Rect getBound() {
        if (this.points.length == 0) {
            return null;
        }
        int i = 999999999;
        int i2 = 999999999;
        int i3 = -999999999;
        int i4 = -999999999;
        for (int i5 = 0; i5 < this.points.length; i5++) {
            i = Math.min(i, this.points[i5].x);
            i2 = Math.min(i2, this.points[i5].y);
            i3 = Math.max(i3, this.points[i5].x);
            i4 = Math.max(i4, this.points[i5].y);
        }
        Rect rect = new Rect();
        rect.set(i, i2, i3, i4);
        return rect;
    }
}
