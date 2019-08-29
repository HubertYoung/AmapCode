package defpackage;

import android.text.TextUtils;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.mapstorage.MapSharePreference;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: atg reason: default package */
/* compiled from: CarLogoCacheUtils */
public final class atg {
    private static JSONObject b(atj atj) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("car_logo_id", atj.a);
            jSONObject.put("car_logo_type", atj.b);
            jSONObject.put("car_logo_uid", atj.c);
            jSONObject.put("car_logo_used", atj.d);
            jSONObject.put("car_logo_download_finished", atj.e);
            jSONObject.put("car_weak_signal_logo_path", atj.f);
            jSONObject.put("car_normal_signal_logo_path", atj.g);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static void a(atj atj) {
        ArrayList<atj> a = a(a());
        Iterator<atj> it = a.iterator();
        boolean z = false;
        while (it.hasNext()) {
            atj next = it.next();
            if (next.c.equals(atj.c) && next.a.equals(atj.a)) {
                if ("normalType".equals(atj.b)) {
                    String str = next.g;
                    if (str == null || !str.equals(atj.g)) {
                        next.g = atj.g;
                    }
                } else {
                    String str2 = next.f;
                    if (str2 == null || !str2.equals(atj.f)) {
                        next.f = atj.f;
                    }
                }
                z = true;
            }
        }
        if (!z) {
            a.add(atj);
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<atj> it2 = a.iterator();
        while (it2.hasNext()) {
            jSONArray.put(b(it2.next()));
        }
        b(jSONArray.toString());
    }

    public static ArrayList<atj> a(String str) {
        ArrayList<atj> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    atj atj = new atj();
                    atj.a = optJSONObject.optString("car_logo_id");
                    atj.b = optJSONObject.optString("car_logo_type");
                    atj.c = optJSONObject.optString("car_logo_uid");
                    atj.d = optJSONObject.optBoolean("car_logo_used");
                    atj.e = optJSONObject.optBoolean("car_logo_download_finished");
                    atj.g = optJSONObject.optString("car_normal_signal_logo_path");
                    atj.f = optJSONObject.optString("car_weak_signal_logo_path");
                    arrayList.add(atj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static boolean a(String str, String str2) {
        Iterator<atj> it = a(a()).iterator();
        while (it.hasNext()) {
            atj next = it.next();
            if ("0".equals(str)) {
                return true;
            }
            if (new File(str2).exists() && next.e) {
                return true;
            }
        }
        return false;
    }

    public static void b(String str, String str2) {
        ArrayList<atj> a = a(a());
        Iterator<atj> it = a.iterator();
        while (it.hasNext()) {
            atj next = it.next();
            String str3 = next.c;
            String str4 = next.a;
            if (str3.equals(str)) {
                if (str4.equals(str2)) {
                    next.d = true;
                } else if (next.d) {
                    next.d = false;
                }
            }
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<atj> it2 = a.iterator();
        while (it2.hasNext()) {
            jSONArray.put(b(it2.next()));
        }
        b(jSONArray.toString());
    }

    private static void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).putStringValue("car_logo_file_cache", str);
        }
    }

    public static String a() {
        return new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).getStringValue("car_logo_file_cache", "");
    }
}
