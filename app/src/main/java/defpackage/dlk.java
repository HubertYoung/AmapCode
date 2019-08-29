package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.maphome.data.PoiList;
import com.autonavi.minimap.intent.BaseIntent;
import com.autonavi.minimap.intent.BaseIntent.a;
import com.autonavi.minimap.intent.BaseIntent.b;
import java.util.StringTokenizer;

/* renamed from: dlk reason: default package */
/* compiled from: InternalIntent */
public class dlk extends BaseIntent {
    static final /* synthetic */ boolean A = true;
    Uri s;
    int t = 13;
    double u = 0.0d;
    double v = 0.0d;
    boolean w = false;
    String x = null;
    String y = null;
    String z = null;

    public final void a(String str) {
    }

    public dlk(Activity activity, Intent intent) {
        super(activity, intent);
    }

    public final boolean c() {
        int i = 0;
        if (this.d == null || !this.d.equals("com.autonavi.minimap.ACTION") || this.e == null) {
            return false;
        }
        this.s = Uri.parse(this.e);
        String[] split = this.e.split("\\:|\\?");
        if (split == null) {
            dlj.a(this.a);
        } else {
            if (split.length > 0) {
                this.x = split[0];
            }
            if (split.length >= 2) {
                this.y = split[1];
            }
            if (split.length >= 3) {
                this.x = split[0];
                this.y = split[1];
                this.z = split[2];
            }
        }
        String scheme = this.s.getScheme();
        if (scheme.equals(BioDetector.EXT_KEY_GEO)) {
            if (!g()) {
                dlj.a(this.a);
                return false;
            } else if (this.y == null || this.x == null) {
                dlj.a(this.a);
                return false;
            } else {
                POI a = dlj.a(this.y, this.w);
                if (a == null) {
                    dlj.a(this.a);
                    return false;
                }
                this.q = new a();
                this.q.a = a;
                this.q.b = this.t;
                this.f = true;
                return true;
            }
        } else if (!scheme.equals("pois")) {
            return false;
        } else {
            if (!g()) {
                dlj.a(this.a);
                return false;
            }
            String a2 = dlj.a(this.z, (String) "f");
            int parseInt = a2 != null ? Integer.parseInt(a2) : 0;
            if (this.y == null) {
                return false;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(this.y, "|#|");
            PoiList poiList = new PoiList();
            while (stringTokenizer.hasMoreTokens()) {
                POI a3 = dlj.a(stringTokenizer.nextToken(), this.w);
                if (a3 != null) {
                    if (A || a3 != null) {
                        a3.setId("null");
                        if (i == parseInt) {
                            poiList.mFocusPoiIndex = i;
                        }
                        i++;
                        poiList.pois.add(a3);
                    } else {
                        throw new AssertionError();
                    }
                }
            }
            this.r = new b();
            this.r.a = poiList;
            this.f = true;
            return true;
        }
    }

    private boolean g() {
        if (this.z == null) {
            return false;
        }
        String a = dlj.a(this.z, (String) "z");
        String a2 = dlj.a(this.z, (String) "offset");
        String a3 = dlj.a(this.z, (String) "cx");
        String a4 = dlj.a(this.z, (String) "cy");
        if (a != null) {
            this.t = Integer.parseInt(a);
        }
        if (a2 != null && Integer.parseInt(a2) == 1) {
            this.w = true;
        }
        if (a3 != null) {
            this.u = Double.parseDouble(a3);
        }
        if (a4 != null) {
            this.v = Double.parseDouble(a4);
        }
        int i = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getInt("Z", -1);
        if (i > 0) {
            this.t = i;
        }
        return true;
    }
}
