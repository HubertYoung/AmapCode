package defpackage;

import android.content.Context;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;

/* renamed from: dfe reason: default package */
/* compiled from: ErrorReportLineItem */
public final class dfe extends LineOverlayItem {
    private static final int a = agn.a((Context) AMapAppGlobal.getApplication(), 3.0f);
    private static final int b = agn.a((Context) AMapAppGlobal.getApplication(), 3.0f);
    private static final int c = R.drawable.map_alr_night;
    private static final int d = R.drawable.map_gray;

    private dfe(int i, GeoPoint[] geoPointArr, int i2, int i3, int i4) {
        super(i, geoPointArr, i2);
        setFillLineId(i3);
        setFillLineColor(i4);
    }

    public static dfe a(GeoPoint[] geoPointArr) {
        dfe dfe = new dfe(3, geoPointArr, a, c, -1);
        return dfe;
    }

    public static dfe b(GeoPoint[] geoPointArr) {
        dfe dfe = new dfe(5, geoPointArr, b, d, -9668748);
        return dfe;
    }
}
