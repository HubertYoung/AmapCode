package defpackage;

import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.map.DPoint;

/* renamed from: bcz reason: default package */
/* compiled from: SearchServerUtil */
public final class bcz {
    public static String a(Rect rect) {
        StringBuffer stringBuffer = new StringBuffer();
        if (rect != null) {
            DPoint a = a((long) rect.left, (long) rect.top);
            DPoint a2 = a((long) rect.right, (long) rect.bottom);
            stringBuffer.append(a.x);
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(a.y);
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(a2.x);
            stringBuffer.append(MergeUtil.SEPARATOR_KV);
            stringBuffer.append(a2.y);
        }
        return stringBuffer.toString();
    }

    private static double a(double d, double d2, double d3) {
        return Math.min(Math.max(d, d2), d3);
    }

    public static Point a(double d, double d2) {
        Point point = new Point();
        double sin = Math.sin((a(d, -85.0511287798d, 85.0511287798d) * 3.141592653589793d) / 180.0d);
        point.x = (int) a((((((a(d2, -180.0d, 180.0d) * 3.141592653589793d) / 180.0d) * 6378137.0d) + 2.0037508342789244E7d) / 0.14929107086948487d) + 0.5d, 0.0d, 2.68435455E8d);
        point.y = (int) a((((double) ((long) (2.0037508342789244E7d - (Math.log((sin + 1.0d) / (1.0d - sin)) * 3189068.0d)))) / 0.14929107086948487d) + 0.5d, 0.0d, 2.68435455E8d);
        return point;
    }

    private static DPoint a(long j, long j2) {
        DPoint dPoint = new DPoint();
        dPoint.y = 1.5707963267948966d - (Math.atan(Math.exp((-(2.0037508342789244E7d - (((double) j2) * 0.14929107086948487d))) / 6378137.0d)) * 2.0d);
        dPoint.y *= 57.29577951308232d;
        dPoint.x = ((((double) j) * 0.14929107086948487d) - 2.0037508342789244E7d) / 6378137.0d;
        dPoint.x *= 57.29577951308232d;
        return dPoint;
    }

    public static float a(GeoPoint geoPoint, GeoPoint geoPoint2) {
        if (geoPoint == null || geoPoint2 == null) {
            return -1.0f;
        }
        DPoint a = a((long) geoPoint.x, (long) geoPoint.y);
        DPoint a2 = a((long) geoPoint2.x, (long) geoPoint2.y);
        return a(a.y, a.x, a2.y, a2.x);
    }

    private static float a(double d, double d2, double d3, double d4) {
        if (d <= 0.0d || d2 <= 0.0d || d3 <= 0.0d || d4 <= 0.0d) {
            return -1.0f;
        }
        float[] fArr = new float[1];
        Location.distanceBetween(d, d2, d3, d4, fArr);
        return fArr[0];
    }

    public static boolean a(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }
}
