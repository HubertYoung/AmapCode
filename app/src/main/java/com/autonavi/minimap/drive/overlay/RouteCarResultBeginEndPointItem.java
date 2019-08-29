package com.autonavi.minimap.drive.overlay;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;

public final class RouteCarResultBeginEndPointItem extends dhp {
    private TYPE h;

    public enum TYPE {
        BEGIN,
        END
    }

    public final Rect[] a() {
        return new Rect[]{b()};
    }

    public RouteCarResultBeginEndPointItem(int i, int i2, TYPE type) {
        super(new GeoPoint(i, i2));
        this.h = type;
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        int i;
        switch (this.h) {
            case BEGIN:
                i = R.drawable.bubble_start;
                break;
            case END:
                i = R.drawable.bubble_end;
                break;
            default:
                i = R.drawable.bubble_start;
                break;
        }
        this.mDefaultMarker = pointOverlay.createMarker(i, 5);
    }
}
