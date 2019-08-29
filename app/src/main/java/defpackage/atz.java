package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.MiningUserInfo;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.xidea.el.json.JSONDecoder;

/* renamed from: atz reason: default package */
/* compiled from: CommuteServiceImpl */
public class atz implements brm {
    private static int b = -1;
    private static int c = -1;
    private static long d = -1;
    private ddu a;

    public final boolean a() {
        if (!lt.a().d()) {
            return false;
        }
        List<String> m = m();
        if (m.size() == 0) {
            return false;
        }
        String k = k();
        if (k == null || k.length() == 0) {
            return false;
        }
        for (String equals : m) {
            if (equals.equals(k)) {
                return true;
            }
        }
        return false;
    }

    public final boolean a(bty bty) {
        String k = k();
        GLGeoPoint n = bty.n();
        return TextUtils.equals(k, String.valueOf(li.a().a(n.x, n.y)));
    }

    public final boolean b(bty bty) {
        if (!lt.a().d()) {
            return false;
        }
        GLGeoPoint n = bty.n();
        String valueOf = String.valueOf(li.a().a(n.x, n.y));
        if (valueOf == null || valueOf.length() == 0) {
            return false;
        }
        List<String> m = m();
        if (m.size() == 0) {
            return false;
        }
        for (String equals : m) {
            if (equals.equals(valueOf)) {
                return true;
            }
        }
        return false;
    }

    public final void b() {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("commute_bubble_has_shown", true);
    }

    public final void c() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            djk djk = (djk) ank.a(djk.class);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("bundle_key_from_page", "commute_map");
            if (djk == null || !djk.i()) {
                pageContext.startPage((String) "amap.drive.action.commute.helper", pageBundle);
            } else {
                pageContext.startPage((String) "amap.drive.action.commute", pageBundle);
            }
        }
    }

    private static String l() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue("commute_last_citycode", "");
    }

    private static void b(String str) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue("commute_last_citycode", str);
    }

    private static List<String> m() {
        ArrayList arrayList = new ArrayList();
        String[] split = lt.a().a((String) "").split(",");
        int i = 0;
        while (split != null && i < split.length) {
            arrayList.add(split[i]);
            i++;
        }
        return arrayList;
    }

    public final boolean d() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("indoor_guide_bubble_has_shown", false);
    }

    public final void e() {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("indoor_guide_bubble_has_shown", true);
    }

    public final String f() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue("commute_mining_info", "");
    }

    public final ArrayList<MiningUserInfo> g() {
        String str;
        ArrayList<MiningUserInfo> arrayList = new ArrayList<>();
        try {
            brm brm = (brm) ank.a(brm.class);
            if (brm != null) {
                str = brm.f();
            } else {
                str = null;
            }
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject("home");
            JSONObject optJSONObject2 = jSONObject.optJSONObject("company");
            arrayList.add(0, (MiningUserInfo) JSONDecoder.decode(optJSONObject.toString(), MiningUserInfo.class));
            arrayList.add(1, (MiningUserInfo) JSONDecoder.decode(optJSONObject2.toString(), MiningUserInfo.class));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    public final void a(int i, int i2) {
        b = i;
        c = i2;
        d = System.currentTimeMillis();
    }

    public final void a(cde cde, MapManager mapManager) {
        this.a = new ddu(cde, mapManager);
    }

    public final void h() {
        ddu.c();
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.a(str);
        }
    }

    public final void i() {
        if (this.a != null) {
            this.a.b();
        }
    }

    public final void j() {
        if (this.a != null) {
            this.a.a((String) "");
        }
    }

    private static String k() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition == null) {
            String l = l();
            if (l == null || l.length() == 0) {
                return null;
            }
            return l;
        }
        li a2 = li.a();
        if (a2 != null) {
            lj b2 = a2.b(latestPosition.x, latestPosition.y);
            if (b2 != null) {
                String valueOf = String.valueOf(b2.j);
                if (valueOf == null || valueOf.length() == 0) {
                    return null;
                }
                b(valueOf);
                return valueOf;
            }
        }
        return null;
    }
}
