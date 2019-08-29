package defpackage;

import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: chc reason: default package */
/* compiled from: Filter */
public final class chc {
    public static chi a() {
        JSONObject jSONObject;
        String str;
        String str2;
        String str3;
        try {
            jSONObject = new JSONObject(chh.a());
        } catch (Throwable th) {
            th.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            emd.a((String) "", (String) "", (String) null, (String) "nolist", (String) null, (String) null);
            return null;
        }
        String optString = jSONObject.optString("session_id");
        ArrayList<chi> a = chg.a(jSONObject);
        if (a.isEmpty()) {
            emd.a(optString, (String) "", (String) null, (String) "nolist", (String) null, (String) null);
            return null;
        }
        ArrayList<chi> a2 = a(a);
        if (a2.isEmpty()) {
            emd.a(optString, (String) "", (String) null, (String) "novalid", (String) null, (String) null);
            return null;
        }
        Iterator<chi> it = a2.iterator();
        chi chi = null;
        while (it.hasNext()) {
            chi = it.next();
            String str4 = chi.f;
            if ("video".equals(str4) || "dynamic_image".equals(str4)) {
                if ("video".equals(str4)) {
                    str3 = chi.g;
                    str2 = "v_";
                } else if ("dynamic_image".equals(str4)) {
                    str3 = chi.i;
                    str2 = "g_";
                } else {
                    str3 = null;
                    str2 = null;
                }
                String a3 = a(str3, str2);
                if (!TextUtils.isEmpty(a3)) {
                    chi.w = str4;
                    chi.x = a3;
                    return chi;
                }
                String a4 = a(chi);
                if (!TextUtils.isEmpty(a4)) {
                    chi.w = "static_image";
                    chi.x = a4;
                    return chi;
                }
            } else if ("static_image".equals(str4)) {
                String a5 = a(chi);
                if (!TextUtils.isEmpty(a5)) {
                    chi.w = str4;
                    chi.x = a5;
                    return chi;
                }
            } else {
                continue;
            }
        }
        if (chi != null) {
            str = chi.e;
        } else {
            str = "";
        }
        emd.a(optString, "afp".concat(String.valueOf(str)), (String) null, (String) "nomaterial", (String) null, (String) null);
        return null;
    }

    private static ArrayList<chi> a(ArrayList<chi> arrayList) {
        ArrayList<chi> arrayList2 = new ArrayList<>();
        Iterator<chi> it = arrayList.iterator();
        while (it.hasNext()) {
            chi next = it.next();
            long j = (long) next.b;
            if (j != 0) {
                long j2 = (long) next.c;
                if (j2 != 0) {
                    long currentTimeMillis = (long) ((int) (System.currentTimeMillis() / 1000));
                    if (currentTimeMillis >= j && currentTimeMillis <= j2) {
                        arrayList2.add(next);
                    }
                }
            }
        }
        return arrayList2;
    }

    private static String a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String absolutePath = AMapAppGlobal.getApplication().getExternalFilesDir(BaseIntentDispatcher.INTENT_CALL_SPLASH).getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(agy.a(str));
        File file = new File(absolutePath, sb.toString());
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        return null;
    }

    private static String a(chi chi) {
        return a(chi.j, "i_");
    }
}
