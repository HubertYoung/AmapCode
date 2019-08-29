package defpackage;

import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

/* renamed from: dff reason: default package */
/* compiled from: ErrorReportPointItem */
public final class dff extends PointOverlayItem {
    public int a = -1;

    private dff(djm djm) {
        super(djm.d);
    }

    public static dff a(djm djm) {
        if (djm != null) {
            return new dff(djm);
        }
        throw new IllegalArgumentException("errorInfo should'n be null");
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        super.onPrepareAddItem(pointOverlay);
        this.mDefaultMarker = pointOverlay.createMarker(R.drawable.car_error_iv_center_draw_map, 5);
    }
}
