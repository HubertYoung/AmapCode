package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.CookieManager;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.core.network.inter.response.InputStreamResponse;

/* renamed from: yq reason: default package */
/* compiled from: AmapNetworkService */
public class yq {
    private static volatile yq a;
    private static volatile boy b;

    private yq() {
    }

    public static yq a() {
        if (a == null) {
            synchronized (yq.class) {
                b();
                if (a == null) {
                    a = new yq();
                }
            }
        }
        return a;
    }

    public static void b() {
        Context a2 = aaf.a();
        if (ahs.a(a2)) {
            yv.b(aaf.a());
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            if (!mapSharePreference.getBooleanValue("accs_has_clear_cookies", false)) {
                try {
                    CookieManager.getInstance().removeAllCookies(null);
                    CookieManager.getInstance().flush();
                    mapSharePreference.putBooleanValue("accs_has_clear_cookies", true);
                } catch (Throwable unused) {
                }
            }
        } else if (ahs.a("com.autonavi.minimap:channel", a2)) {
            yv.a(aaf.a());
        }
        if (b == null) {
            synchronized (yq.class) {
                if (b == null) {
                    boy.a = new zb();
                    bpo.a = new zi();
                    iu.a = new zg();
                    iv.a = new aaj();
                    boy boy = new boy(new yu(aaf.a()));
                    b = boy;
                    boy.a(new int[]{6, 6, 6, 6, 6});
                    b.a((bpd) new yz());
                    bpv.a((a) new aar());
                    bpv.a();
                    box.a(b);
                    in.a((ir) new aab());
                }
            }
        }
    }

    public static <T extends AosResponse> T a(@NonNull AosRequest aosRequest, Class<T> cls) {
        in a2 = in.a();
        if (aosRequest == null) {
            return null;
        }
        InputStreamResponse b2 = a2.b(aosRequest);
        if (b2 == null) {
            return null;
        }
        return in.a(b2, cls, aosRequest);
    }

    public static <T extends AosResponse> void a(@NonNull AosRequest aosRequest, AosResponseCallback<T> aosResponseCallback) {
        in.a().a(aosRequest, aosResponseCallback);
    }

    public static <T extends bpk> T a(@NonNull bph bph, Class<T> cls) {
        box.a();
        return box.a(bph, cls);
    }

    public static <T extends bpk> void a(@NonNull bph bph, bpl<T> bpl) {
        box.a();
        box.a(bph, bpl);
    }

    public static void a(@NonNull bjg bjg, bjf bjf) {
        box.a();
        box.a(bjg, bjf);
    }

    public static void a(bph bph) {
        if (bph != null) {
            box.a();
            box.a(bph);
        }
    }

    public static void a(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public static void a(aae aae) {
        if (aaf.a != null) {
            throw new IllegalStateException("network context has initialized!");
        }
        aaf.a = aae;
    }
}
