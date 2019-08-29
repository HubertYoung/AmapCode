package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Location;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;
import java.math.BigDecimal;
import java.util.List;

/* renamed from: cfe reason: default package */
/* compiled from: MapUtil */
public final class cfe {
    public static String a(int i) {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (i >= 1000) {
            float round = ((float) Math.round((((float) i) / 1000.0f) * 10.0f)) / 10.0f;
            if ((round * 10.0f) % 10.0f == 0.0f) {
                StringBuilder sb = new StringBuilder();
                sb.append((int) round);
                sb.append(resources.getString(R.string.km));
                return sb.toString();
            }
            try {
                round = new BigDecimal((double) round).setScale(2, 4).floatValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(round);
            sb2.append(resources.getString(R.string.km));
            return sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(String.valueOf(i));
        sb3.append(resources.getString(R.string.meter));
        return sb3.toString();
    }

    public static float a(GeoPoint geoPoint, GeoPoint geoPoint2) {
        if (geoPoint == null || geoPoint2 == null) {
            return -1.0f;
        }
        DPoint a = cfg.a((long) geoPoint.x, (long) geoPoint.y);
        DPoint a2 = cfg.a((long) geoPoint2.x, (long) geoPoint2.y);
        return a(a.y, a.x, a2.y, a2.x);
    }

    public static float a(double d, double d2, double d3, double d4) {
        float[] fArr = new float[1];
        Location.distanceBetween(d, d2, d3, d4, fArr);
        return fArr[0];
    }

    public static int b(GeoPoint geoPoint, GeoPoint geoPoint2) {
        float[] fArr = new float[1];
        Location.distanceBetween(geoPoint.getLatitude(), geoPoint.getLongitude(), geoPoint2.getLatitude(), geoPoint2.getLongitude(), fArr);
        return Math.max((int) fArr[0], 0);
    }

    public static int a(GeoPoint geoPoint, GeoPoint[] geoPointArr) {
        int b = b(geoPoint, geoPointArr[0]);
        for (int i = 1; i < geoPointArr.length; i++) {
            int b2 = b(geoPoint, geoPointArr[i]);
            if (b2 < b) {
                b = b2;
            }
        }
        return b;
    }

    public static int a(GeoPoint geoPoint, int i) {
        GeoPoint geoPoint2 = new GeoPoint();
        geoPoint2.x = geoPoint.x + 1000;
        geoPoint2.y = geoPoint.y;
        DPoint a = cfg.a((long) geoPoint.x, (long) geoPoint.y);
        DPoint a2 = cfg.a((long) geoPoint2.x, (long) geoPoint2.y);
        float[] fArr = new float[1];
        Location.distanceBetween(a.y, a.x, a2.y, a2.x, fArr);
        return (int) (((float) (i * 1000)) / fArr[0]);
    }

    public static float a(List<GeoPoint> list, MapManager mapManager, Rect rect, float f) {
        if (list.size() == 0) {
            return f;
        }
        int i = list.get(0).x;
        int i2 = list.get(0).y;
        int i3 = i2;
        int i4 = i;
        for (GeoPoint next : list) {
            i = Math.min(i, next.x);
            i2 = Math.min(i2, next.y);
            i4 = Math.max(i4, next.x);
            i3 = Math.max(i3, next.y);
        }
        rect.left = i;
        rect.top = i2;
        rect.right = i4;
        rect.bottom = i3;
        return mapManager.getMapView().a(i, i2, i4, i3);
    }

    public static boolean a(bid bid, GeoPoint geoPoint, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean z = false;
        if (bid == null) {
            return false;
        }
        Context context = bid.getContext();
        if (context == null) {
            i6 = 0;
            i5 = 0;
        } else if (context.getResources().getConfiguration().orientation == 2) {
            i5 = ags.a(context).height();
            i6 = ags.a(context).width();
        } else {
            i5 = ags.a(context).width();
            i6 = ags.a(context).height();
        }
        Rect rect = new Rect();
        bid.getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int i7 = rect.top;
        int i8 = i + i7;
        int i9 = i2 + i7;
        Point point = new Point();
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView != null) {
            mapView.a((GLGeoPoint) geoPoint, point);
        }
        int dimensionPixelOffset = DoNotUseTool.getContext().getResources().getDimensionPixelOffset(R.dimen.search_result_vision_padding);
        int i10 = i3 / 2;
        if (point.x - i10 > dimensionPixelOffset && point.x + i10 < i5 - dimensionPixelOffset && point.y - i4 > i8 && point.y < i6 - i9) {
            z = true;
        }
        return z;
    }

    public static void a(IMapPage iMapPage) {
        if (iMapPage != null) {
            Context context = iMapPage.getContext();
            if (context != null) {
                bty mapView = iMapPage.getMapView();
                if (mapView != null) {
                    mapView.b(ags.a(context).width() / 2, ags.a(context).height() / 2);
                }
            }
        }
    }

    public static boolean b(IMapPage iMapPage) {
        if (iMapPage == null) {
            return false;
        }
        bty mapView = iMapPage.getMapView();
        if (mapView == null) {
            return false;
        }
        mapView.X();
        return true;
    }

    public static String a(Rect rect) {
        StringBuffer stringBuffer = new StringBuffer();
        if (rect != null) {
            DPoint a = cfg.a((long) rect.left, (long) rect.top);
            DPoint a2 = cfg.a((long) rect.right, (long) rect.bottom);
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
}
