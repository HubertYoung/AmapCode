package defpackage;

import com.autonavi.common.model.POI;

/* renamed from: ehu reason: default package */
/* compiled from: ShareRidingStatus */
public final class ehu {
    private static POI a;
    private static int b;

    public static synchronized void a() {
        synchronized (ehu.class) {
            eao.f("ShareRidingStatus", "setCacheTime 1");
            b = 1;
        }
    }

    public static synchronized void a(POI poi) {
        synchronized (ehu.class) {
            StringBuilder sb = new StringBuilder("setSelectedPoi ");
            sb.append(poi);
            sb.append(" mCacheTimes --->");
            sb.append(b);
            eao.f("ShareRidingStatus", sb.toString());
            if (poi != null || b <= 0) {
                b = 0;
                a = poi;
                return;
            }
            b--;
        }
    }

    public static synchronized POI b() {
        POI poi;
        synchronized (ehu.class) {
            try {
                StringBuilder sb = new StringBuilder("getSelectedPoi  mCacheTimes --->");
                sb.append(b);
                eao.f("ShareRidingStatus", sb.toString());
                poi = a;
            }
        }
        return poi;
    }
}
