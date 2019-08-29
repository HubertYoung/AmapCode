package defpackage;

import android.graphics.PointF;
import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

/* renamed from: bbr reason: default package */
/* compiled from: SearchPointOverlayItem */
public final class bbr extends PointOverlayItem {
    public String a = "";
    private POI b;

    public bbr(POI poi) {
        super(poi.getPoint());
        this.b = poi;
    }

    public bbr(POI poi, GeoPoint geoPoint) {
        super(geoPoint);
        this.b = poi;
    }

    public final Rect a(bty bty) {
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        new PointF();
        PointF f = bty.f(this.mGeoPoint.x, this.mGeoPoint.y);
        if (this.mDefaultMarker != null) {
            a(rect2, (int) f.x, (int) f.y, this.mDefaultMarker, this.mDefaultMarker.mAnchor);
            rect.set(rect2);
        }
        if (this.mBgMarker != null) {
            a(rect3, (int) f.x, (int) f.y, this.mBgMarker, this.mBgMarker.mAnchor);
            rect.union(rect3);
        }
        return rect;
    }

    private static void a(Rect rect, int i, int i2, Marker marker, int i3) {
        if (marker != null) {
            switch (i3) {
                case 0:
                    rect.left = i;
                    rect.right = i + marker.mWidth;
                    rect.top = i2;
                    rect.bottom = i2 + marker.mHeight;
                    return;
                case 1:
                    rect.left = i - marker.mWidth;
                    rect.right = i;
                    rect.top = i2;
                    rect.bottom = i2 + marker.mHeight;
                    return;
                case 2:
                    rect.left = i;
                    rect.right = i + marker.mWidth;
                    rect.top = i2 - marker.mHeight;
                    rect.bottom = i2;
                    return;
                case 3:
                    rect.left = i - marker.mWidth;
                    rect.right = i;
                    rect.top = i2 - marker.mHeight;
                    rect.bottom = i2;
                    return;
                case 4:
                    rect.left = i - (marker.mWidth / 2);
                    rect.right = i + (marker.mWidth / 2);
                    rect.top = i2 - (marker.mHeight / 2);
                    rect.bottom = i2 + (marker.mHeight / 2);
                    return;
                case 5:
                    rect.left = i - (marker.mWidth / 2);
                    rect.right = i + (marker.mWidth / 2);
                    rect.top = i2 - marker.mHeight;
                    rect.bottom = i2;
                    return;
                case 6:
                    rect.left = i - (marker.mWidth / 2);
                    rect.right = i + (marker.mWidth / 2);
                    rect.top = i2;
                    rect.bottom = i2 + marker.mHeight;
                    return;
                case 7:
                    rect.left = i;
                    rect.right = i + marker.mWidth;
                    rect.top = i2 - (marker.mHeight / 2);
                    rect.bottom = i2 + (marker.mHeight / 2);
                    return;
                case 8:
                    rect.left = i - marker.mWidth;
                    rect.right = i;
                    rect.top = i2 - (marker.mHeight / 2);
                    rect.bottom = i2 + (marker.mHeight / 2);
                    break;
            }
        }
    }
}
