package defpackage;

import com.autonavi.ae.gmap.AMapSurface;
import java.util.HashMap;
import java.util.Map;

/* renamed from: brx reason: default package */
/* compiled from: MapViewManager */
public final class brx {
    public static int d;
    public Map<Integer, bty> a = new HashMap();
    public akq b;
    public AMapSurface c;

    public brx(akq akq) {
        this.b = akq;
    }

    public final bty a(int i) {
        return this.a.get(Integer.valueOf(i));
    }
}
