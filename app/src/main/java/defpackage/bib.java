package defpackage;

import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: bib reason: default package */
/* compiled from: AMapLocationSDK */
public final class bib {
    public static a a(Callback<GeoPoint> callback, int i) {
        return LocationInstrument.getInstance().getPosition(callback, i);
    }
}
