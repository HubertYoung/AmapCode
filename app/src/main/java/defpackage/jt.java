package defpackage;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import org.json.JSONObject;

/* renamed from: jt reason: default package */
/* compiled from: UpgradeDataHelper */
public final class jt {
    public static String a() {
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("appUpdateInfo", 0);
        return sharedPreferences != null ? sharedPreferences.getString("appInfo", "") : "";
    }

    public static ji b() {
        SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("appUpdateInfo", 0);
        if (sharedPreferences == null) {
            return null;
        }
        ji jiVar = new ji();
        String string = sharedPreferences.getString("appInfo", "");
        if (!TextUtils.isEmpty(string)) {
            try {
                jiVar.a(new JSONObject(string));
            } catch (Exception e) {
                sharedPreferences.edit().putString("appInfo", "").apply();
                e.printStackTrace();
            }
        }
        return jiVar;
    }
}
