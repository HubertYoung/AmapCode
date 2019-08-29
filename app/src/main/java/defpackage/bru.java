package defpackage;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.map.core.MapManager;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bru reason: default package */
/* compiled from: MapLayerSettingModule */
public final class bru {
    a a;
    private final MapSharePreference b = new MapSharePreference(SharePreferenceName.SharedPreferences);

    /* renamed from: bru$a */
    /* compiled from: MapLayerSettingModule */
    public interface a {
        Context a();

        bty b();

        MapManager c();
    }

    public bru(a aVar) {
        this.a = aVar;
    }

    public final boolean a(boolean z, boolean z2, boolean z3) {
        boolean z4;
        bqx bqx = (bqx) ank.a(bqx.class);
        boolean z5 = false;
        if (bqx != null) {
            if (this.a.b().s() != z) {
                z5 = true;
            }
            z4 = bqx.a();
            bqx.a(z, z2, this.a.c(), this.a.a());
        } else {
            z4 = false;
        }
        if (z && z5) {
            this.a.b().t();
        }
        if (z3) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", z4 ? "0" : "1");
                jSONObject.put("status", bnv.d());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B014", jSONObject);
        }
        return z;
    }

    public static Boolean a() {
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx == null || !eme.b()) {
            return null;
        }
        return Boolean.valueOf(bqx.a());
    }
}
