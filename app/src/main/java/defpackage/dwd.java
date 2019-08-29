package defpackage;

import android.graphics.PointF;
import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

/* renamed from: dwd reason: default package */
/* compiled from: RealtimeNameOverlayItem */
public final class dwd extends PointOverlayItem {
    amc a;
    public Rect b = new Rect();

    dwd(GeoPoint geoPoint) {
        super(geoPoint);
    }

    public final void a(GLMapState gLMapState) {
        if (this.a != null) {
            PointF pointF = new PointF();
            gLMapState.p20ToScreenPoint(getGeoPoint().x, getGeoPoint().y, pointF);
            a(this.b, (int) pointF.x, (int) pointF.y, this.a, this.a.h);
        }
    }

    private static void a(Rect rect, int i, int i2, amc amc, int i3) {
        if (!(rect == null || amc == null)) {
            switch (i3) {
                case 0:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 1:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 2:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 3:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 4:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 5:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 6:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 7:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 8:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 9:
                    float f = (float) i;
                    rect.left = (int) (f - (((float) amc.d) * amc.f));
                    rect.top = i2 - amc.e;
                    rect.right = (int) (f + (((float) amc.d) * (1.0f - amc.f)));
                    rect.bottom = i2;
                    break;
            }
        }
    }
}
