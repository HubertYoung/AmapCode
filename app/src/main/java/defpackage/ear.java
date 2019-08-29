package defpackage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.server.aos.serverkey;
import java.util.HashMap;

/* renamed from: ear reason: default package */
/* compiled from: ClassifiedSp */
public final class ear {
    private static final HashMap a = new HashMap();

    private static SharedPreferences a() {
        return AMapAppGlobal.getApplication().getSharedPreferences("route_cl_sp", 0);
    }

    public static void a(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str)) {
                a.put(str, str2);
                Editor edit = a().edit();
                if (str2 != null) {
                    str2 = serverkey.amapEncode(str2);
                }
                edit.putString(str, str2);
                edit.apply();
            }
        } catch (Exception unused) {
        }
    }

    public static String b(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str)) {
                String str3 = (String) a.get(str);
                if (!TextUtils.isEmpty(str3)) {
                    return str3;
                }
                String string = a().getString(str, null);
                if (string != null) {
                    try {
                        str2 = serverkey.amapDecode(string);
                    } catch (Exception unused) {
                        str2 = string;
                    }
                }
                return str2;
            }
        } catch (Exception unused2) {
        }
        str2 = null;
        return str2;
    }
}
