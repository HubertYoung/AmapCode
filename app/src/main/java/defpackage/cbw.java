package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cbw reason: default package */
/* compiled from: AddNewPoiHelper */
public final class cbw {

    /* renamed from: cbw$a */
    /* compiled from: AddNewPoiHelper */
    static class a implements lp {
        public final void onConfigCallBack(int i) {
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final void onConfigResultCallBack(int i, String str) {
            if (i == 3) {
                ahm.a(new Runnable() {
                    public final void run() {
                        new MapSharePreference((String) "sp_key_feedback_config").edit().clear().apply();
                    }
                });
            } else {
                cbw.b(str);
            }
        }
    }

    static {
        lo.a().a((String) "feedback", (lp) new a(0));
        b(lo.a().a((String) "feedback"));
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("poi")) {
                    new MapSharePreference((String) "sp_key_feedback_config").putIntValue("feedback_config_key_poi", jSONObject.optInt("poi"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean a() {
        if (new MapSharePreference((String) "sp_key_feedback_config").getIntValue("feedback_config_key_poi", 0) == 1) {
            return true;
        }
        return false;
    }
}
