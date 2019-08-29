package com.autonavi.minimap.drive.overlay;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;

public final class RouteCarResultLinePointItem extends dhp {
    private TYPE h;

    public enum TYPE {
        BEGIN,
        END_MAIN,
        END_BAK
    }

    public final Rect[] a() {
        return new Rect[]{b()};
    }

    public RouteCarResultLinePointItem(int i, int i2, TYPE type) {
        super(new GeoPoint(i, i2));
        this.h = type;
    }

    public RouteCarResultLinePointItem(GeoPoint geoPoint, TYPE type) {
        super(geoPoint);
        this.h = type;
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        int i;
        switch (this.h) {
            case BEGIN:
                i = R.drawable.drive_navi_route_start;
                break;
            case END_MAIN:
                i = R.drawable.route_result_end_icon;
                break;
            case END_BAK:
                i = R.drawable.route_result_end_bak_icon;
                break;
            default:
                i = 0;
                break;
        }
        this.mDefaultMarker = pointOverlay.createMarker(i, 4);
    }
}
