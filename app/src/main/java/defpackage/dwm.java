package defpackage;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;

/* renamed from: dwm reason: default package */
/* compiled from: TipStruct */
public final class dwm {
    public GeoPoint a;
    public String b;
    public String c;
    public String d;
    public int e;
    public int f;
    public int g;
    public String h;
    public int i;
    public boolean j;

    public static int a(int i2) {
        if (i2 == 0) {
            return 2;
        }
        if (i2 == 1) {
            return 3;
        }
        if (i2 == 2) {
            return 0;
        }
        if (i2 == 3) {
            return 1;
        }
        return i2;
    }

    public static int a(int i2, int i3, int i4, int i5) {
        int i6 = i2 - i4;
        int i7 = i3 - i5;
        if (i6 > 0 && i7 >= 0) {
            return 0;
        }
        if (i6 < 0 && i7 >= 0) {
            return 1;
        }
        if (i6 >= 0 || i7 > 0) {
            return (i6 <= 0 || i7 > 0) ? 0 : 3;
        }
        return 2;
    }

    public static void a(dwm dwm, dwm dwm2) {
        int a2 = a(dwm.a.x, dwm.a.y, dwm2.a.x, dwm2.a.y);
        dwm.e = a2;
        dwm2.e = a(a2);
    }

    public static int a(Rect rect, GeoPoint geoPoint) {
        GeoPoint geoPoint2 = new GeoPoint();
        geoPoint2.x = (rect.left + rect.right) / 2;
        geoPoint2.y = (rect.top + rect.bottom) / 2;
        return a(geoPoint.x, geoPoint.y, geoPoint2.x, geoPoint2.y);
    }

    public static void b(Rect rect, GeoPoint geoPoint) {
        if (rect.left > geoPoint.x) {
            rect.left = geoPoint.x;
        }
        if (rect.right < geoPoint.x) {
            rect.right = geoPoint.x;
        }
        if (rect.top > geoPoint.y) {
            rect.top = geoPoint.y;
        }
        if (rect.bottom < geoPoint.y) {
            rect.bottom = geoPoint.y;
        }
    }

    public static int a(GeoPoint geoPoint, GeoPoint geoPoint2, int i2) {
        if (Math.abs(geoPoint2.y - geoPoint.y) > Math.abs(geoPoint2.x - geoPoint.x)) {
            switch (i2) {
                case 0:
                    return 1;
                case 1:
                    return 0;
                case 2:
                    return 3;
                case 3:
                    return 2;
            }
        } else {
            switch (i2) {
                case 0:
                    return 3;
                case 1:
                    return 2;
                case 2:
                    return 1;
                case 3:
                    return 0;
            }
        }
        return 0;
    }
}
