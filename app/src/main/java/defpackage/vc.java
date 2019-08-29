package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: vc reason: default package */
/* compiled from: EyrieAbImpl */
public class vc implements lp, vf {
    public static String a = "";
    private static String b = "";
    private static String c = "";
    private static volatile vc d;
    private static MapSharePreference e = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public static vc a() {
        if (d == null) {
            synchronized (vc.class) {
                try {
                    if (d == null) {
                        d = new vc();
                        vg vgVar = (vg) ank.a(vg.class);
                        if (vgVar != null) {
                            vgVar.a(d);
                        }
                        g();
                    }
                }
            }
        }
        return d;
    }

    public final String b() {
        return a;
    }

    private static String c() {
        if (TextUtils.isEmpty(b) || TextUtils.isEmpty(c)) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            sb.append(c);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(b);
        sb2.append(MergeUtil.SEPARATOR_KV);
        sb2.append(c);
        return sb2.toString();
    }

    private static void d() {
        try {
            String a2 = lo.a().a((String) "eyrie_foot");
            if (a2 == null || a2.trim().equals("")) {
                e.putStringValue("eyrie_foot_key", "");
                return;
            }
            e.putStringValue("eyrie_foot_key", new JSONObject(a2).optString("eyrie_foot_key", "ef_a"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void onConfigResultCallBack(int i, String str) {
        d();
        f();
        g();
    }

    public void onConfigCallBack(int i) {
        d();
        f();
        g();
    }

    private static String e() {
        String a2 = lo.a().a((String) "engine_pos");
        if (TextUtils.isEmpty(a2)) {
            return null;
        }
        try {
            a2 = new JSONObject(a2).toString();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return a2;
    }

    private static void f() {
        String str = "";
        String str2 = "";
        String str3 = "";
        try {
            JSONObject jSONObject = new JSONObject(e());
            if (jSONObject.has("acc_location")) {
                int optInt = jSONObject.optInt("acc_location");
                if (optInt == 0) {
                    str2 = "ac_a";
                } else if (optInt == 3) {
                    str2 = "ac_b";
                }
            }
            if (!TextUtils.isEmpty(str2)) {
                str = str.concat(str.length() > 0 ? MergeUtil.SEPARATOR_KV : "").concat(str2);
            }
            if (jSONObject.has("fyypdr_location")) {
                int optInt2 = jSONObject.optInt("fyypdr_location");
                if (optInt2 == 0) {
                    str3 = "fy_a";
                } else if (optInt2 == 3) {
                    str3 = "fy_b";
                }
            }
            if (!TextUtils.isEmpty(str3)) {
                str = str.concat(str.length() > 0 ? MergeUtil.SEPARATOR_KV : "").concat(str3);
            }
            String str4 = "";
            if (jSONObject.has("dir_merge")) {
                int optInt3 = jSONObject.optInt("dir_merge");
                if (optInt3 == 0) {
                    str4 = "dir_a";
                } else if (optInt3 == 3) {
                    str4 = "dir_b";
                }
            }
            if (!TextUtils.isEmpty(str4)) {
                str = str.concat(str.length() > 0 ? MergeUtil.SEPARATOR_KV : "").concat(str4);
            }
            String str5 = "";
            if (jSONObject.has("opposite_direction")) {
                int optInt4 = jSONObject.optInt("opposite_direction");
                if (optInt4 == 0) {
                    str5 = "od_a";
                } else if (optInt4 == 3) {
                    str5 = "od_b";
                }
            }
            if (!TextUtils.isEmpty(str5)) {
                str = str.concat(str.length() > 0 ? MergeUtil.SEPARATOR_KV : "").concat(str5);
            }
            String str6 = "";
            if (jSONObject.has("wifi_navigation")) {
                int optInt5 = jSONObject.optInt("wifi_navigation");
                if (optInt5 == 0) {
                    str6 = "wifin_a";
                } else if (optInt5 == 3) {
                    str6 = "wifin_b";
                }
            }
            if (!TextUtils.isEmpty(str6)) {
                str = str.concat(str.length() > 0 ? MergeUtil.SEPARATOR_KV : "").concat(str6);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        e.putStringValue("pdr_foot_ar_key", str);
    }

    private static void g() {
        b = e.getStringValue("eyrie_foot_key", "");
        c = e.getStringValue("pdr_foot_ar_key", "");
        a = c();
    }
}
