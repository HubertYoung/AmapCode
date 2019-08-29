package com.autonavi.minimap.drive.errorreport.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

public final class ErrorReportRoutePointItem extends PointOverlayItem {
    private RoutePointType a;
    private int b;

    public enum RoutePointType {
        START(R.drawable.bubble_start, 5),
        END(R.drawable.bubble_end, 5),
        MID(R.drawable.bubble_midd, 5),
        OFF_ROUTE(R.drawable.route_line_error_off_route_point, 5),
        MULTI_MID,
        NONE;
        
        /* access modifiers changed from: private */
        public int anchor;
        /* access modifiers changed from: private */
        public int resID;

        private RoutePointType(int i, int i2) {
            this.resID = i;
            this.anchor = i2;
        }
    }

    private ErrorReportRoutePointItem(GeoPoint geoPoint) {
        super(geoPoint);
        this.a = RoutePointType.NONE;
        this.b = 0;
    }

    private ErrorReportRoutePointItem(RoutePointType routePointType, GeoPoint geoPoint, int i) {
        this(geoPoint);
        this.b = i;
        this.a = routePointType;
    }

    public static ErrorReportRoutePointItem a(RoutePointType routePointType, GeoPoint geoPoint) {
        return new ErrorReportRoutePointItem(routePointType, geoPoint, -1);
    }

    public static ErrorReportRoutePointItem a(GeoPoint geoPoint, int i) {
        return new ErrorReportRoutePointItem(RoutePointType.MULTI_MID, geoPoint, i);
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        super.onPrepareAddItem(pointOverlay);
        if (this.a != RoutePointType.NONE) {
            if (this.a != RoutePointType.MULTI_MID) {
                this.mDefaultMarker = pointOverlay.createMarker(this.a.resID, this.a.anchor);
                return;
            }
            int i = R.drawable.bubble_midd1;
            switch (this.b) {
                case 0:
                    i = R.drawable.bubble_midd1;
                    break;
                case 1:
                    i = R.drawable.bubble_midd2;
                    break;
                case 2:
                    i = R.drawable.bubble_midd3;
                    break;
                case 3:
                    i = R.drawable.bubble_midd4;
                    break;
                case 4:
                    i = R.drawable.bubble_midd5;
                    break;
            }
            this.mDefaultMarker = pointOverlay.createMarker(i, 5);
        }
    }
}
