package com.autonavi.minimap.drive.errorreport.overlay;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.Iterator;

public class ErrorReportRoutePointOverlay extends PointOverlay<PointOverlayItem> {
    private Rect mAllPointBoundRect;

    public ErrorReportRoutePointOverlay(bty bty) {
        super(bty);
    }

    @NonNull
    public Rect acquireBounds() {
        if (this.mAllPointBoundRect == null) {
            this.mAllPointBoundRect = calcBounds();
        }
        return this.mAllPointBoundRect;
    }

    @NonNull
    private Rect calcBounds() {
        Rect rect = new Rect();
        if (this.mItemList.size() == 0) {
            return rect;
        }
        Iterator it = this.mItemList.iterator();
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MIN_VALUE;
        int i4 = Integer.MIN_VALUE;
        while (it.hasNext()) {
            GeoPoint geoPoint = ((PointOverlayItem) it.next()).getGeoPoint();
            i = Math.min(i, geoPoint.x);
            i2 = Math.min(i2, geoPoint.y);
            i3 = Math.max(i3, geoPoint.x);
            i4 = Math.max(i4, geoPoint.y);
        }
        return new Rect(i, i2, i3, i4);
    }
}
