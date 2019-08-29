package defpackage;

import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import org.json.JSONObject;

/* renamed from: dqw reason: default package */
/* compiled from: SketchScenicStoreHelper */
public final class dqw {
    public MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);

    dqw() {
    }

    private static boolean b() {
        try {
            if (new JSONObject(lo.a().a((String) "hand_drawing_switch")).optInt(FunctionSupportConfiger.SWITCH_TAG, 1) == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public final boolean a() {
        if (this.a.contains("SKETCH_SCENIC_ALLOW_TO_SHOW")) {
            return this.a.getBooleanValue("SKETCH_SCENIC_ALLOW_TO_SHOW", true);
        }
        return b();
    }
}
