package defpackage;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.taobao.agoo.control.data.BaseDO;
import java.util.ArrayList;
import java.util.List;

/* renamed from: emy reason: default package */
/* compiled from: PerformanceCmdHandler */
public final class emy {
    private static List<String> a;

    public static void a(Intent intent) {
        Uri data = intent != null ? intent.getData() : null;
        if (data != null && data.isHierarchical() && !TextUtils.isEmpty(data.getHost()) && BaseDO.JSON_CMD.equalsIgnoreCase(data.getHost())) {
            List<String> pathSegments = data.getPathSegments();
            if (pathSegments == null || pathSegments.isEmpty()) {
                if (a == null) {
                    ArrayList arrayList = new ArrayList();
                    a = arrayList;
                    arrayList.add("map_render_switch");
                    a.add("accs_switch");
                }
                for (int i = 0; i < a.size(); i++) {
                    String str = a.get(i);
                    String queryParameter = data.getQueryParameter(str);
                    if (!TextUtils.isEmpty(queryParameter)) {
                        if ("map_render_switch".equalsIgnoreCase(str)) {
                            a(queryParameter);
                        } else if ("accs_switch".equalsIgnoreCase(str)) {
                            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("accs_switch_off", "1".equalsIgnoreCase(queryParameter));
                        }
                        intent.setData(null);
                        intent.setAction("");
                    }
                }
            } else if ("render".equalsIgnoreCase(pathSegments.get(0))) {
                String queryParameter2 = data.getQueryParameter("value");
                if (!TextUtils.isEmpty(queryParameter2)) {
                    a(queryParameter2);
                    intent.setData(null);
                    intent.setAction("");
                }
            }
        }
    }

    private static void a(String str) {
        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
        edit.putBoolean("cmd_render", !"1".equalsIgnoreCase(str));
        edit.apply();
    }
}
