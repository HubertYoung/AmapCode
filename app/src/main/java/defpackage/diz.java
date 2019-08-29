package defpackage;

import android.content.Context;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.base.overlay.LineOverlayItem;

/* renamed from: diz reason: default package */
/* compiled from: StickersLineOverlayItem */
public final class diz extends LineOverlayItem {
    private static final int a = agn.a((Context) AMapAppGlobal.getApplication(), 4.0f);

    private diz(GeoPoint[] geoPointArr, int i, int i2) {
        super(2, geoPointArr, i2);
        setFillLineId(i);
    }

    public static diz a(GeoPoint[] geoPointArr, int i) {
        return new diz(geoPointArr, i, a);
    }
}
