package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.util.PerformanceUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eve reason: default package */
/* compiled from: HMTTool */
public class eve {
    /* access modifiers changed from: private */
    public static final String a = "eve";

    public static String a() {
        return "";
    }

    public static void b() {
    }

    public static void a(Context context) {
        if (context != null) {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                if (applicationInfo != null) {
                    String string = applicationInfo.metaData.getString("HMT_TRACKEDURL");
                    if (string != null) {
                        evd.n = string.split(",");
                        return;
                    }
                    euw.a((String) "Could not read HMT_TRACKEDURL meta-data from AndroidManifest.xml.");
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e.getMessage());
                euw.a(sb.toString());
            }
        }
    }

    public static void a(Context context, JSONObject jSONObject, String str, String str2) {
        StringBuilder sb = new StringBuilder("Trigger send ");
        sb.append(str);
        sb.append(" type of data!");
        euw.a(sb.toString());
        JSONObject a2 = a(context, jSONObject, str);
        JSONArray jSONArray = new JSONArray();
        try {
            jSONArray.put(0, a2);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(str, jSONArray);
            evn.a(context, jSONObject2, str2);
        } catch (JSONException e) {
            StringBuilder sb2 = new StringBuilder("Collected:");
            sb2.append(e.getMessage());
            euw.a(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(PerformanceUtil.END_AFTER_FIX);
        if (1 == euw.h(context) && euw.c(context)) {
            euw.a((String) "Policy mode one!");
            ewq.a().execute(new evb(context));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00d7 A[Catch:{ JSONException -> 0x00f4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject a(android.content.Context r3, org.json.JSONObject r4, java.lang.String r5) {
        /*
            java.lang.String r0 = ""
            int r1 = r5.hashCode()
            r2 = -1665500117(0xffffffff9cba7c2b, float:-1.2340544E-21)
            if (r1 == r2) goto L_0x002a
            r2 = -1036384306(0xffffffffc23a07ce, float:-46.50762)
            if (r1 == r2) goto L_0x0020
            r2 = 959291551(0x392da09f, float:1.6558402E-4)
            if (r1 == r2) goto L_0x0016
            goto L_0x0034
        L_0x0016:
            java.lang.String r1 = "client_data_list"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 2
            goto L_0x0035
        L_0x0020:
            java.lang.String r1 = "activity_list"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 0
            goto L_0x0035
        L_0x002a:
            java.lang.String r1 = "act_list"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 1
            goto L_0x0035
        L_0x0034:
            r1 = -1
        L_0x0035:
            switch(r1) {
                case 0: goto L_0x0065;
                case 1: goto L_0x004f;
                case 2: goto L_0x0039;
                default: goto L_0x0038;
            }
        L_0x0038:
            return r4
        L_0x0039:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "package_name"
            java.lang.String r0 = r4.optString(r0)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x007a
        L_0x004f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "act_name"
            java.lang.String r0 = r4.optString(r0)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x007a
        L_0x0065:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "_activity"
            java.lang.String r0 = r4.optString(r0)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
        L_0x007a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "ts"
            java.lang.String r0 = r4.optString(r0)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = "hmt_osk"
            java.lang.String r2 = ""
            java.lang.Object r1 = defpackage.ewp.b(r3, r1, r2)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = ""
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x00a5
            java.lang.String r1 = ""
            goto L_0x00a9
        L_0x00a5:
            java.lang.String r1 = defpackage.ewn.a(r1)
        L_0x00a9:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            r2.append(r1)
            java.lang.String r0 = "os_version"
            java.lang.String r0 = r4.optString(r0)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            java.lang.String r0 = defpackage.ewn.a(r0)
            java.lang.String r2 = "mac2"
            r4.put(r2, r1)     // Catch:{ JSONException -> 0x00f4 }
            java.lang.String r1 = "mac3"
            r4.put(r1, r0)     // Catch:{ JSONException -> 0x00f4 }
            java.lang.String r0 = "client_data_list"
            boolean r5 = r5.equals(r0)     // Catch:{ JSONException -> 0x00f4 }
            if (r5 == 0) goto L_0x010a
            java.lang.String r5 = "temp_key_from_so"
            java.lang.String r0 = ""
            java.lang.Object r5 = defpackage.ewp.b(r3, r5, r0)     // Catch:{ JSONException -> 0x00f4 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ JSONException -> 0x00f4 }
            boolean r0 = android.text.TextUtils.isEmpty(r5)     // Catch:{ JSONException -> 0x00f4 }
            if (r0 != 0) goto L_0x010a
            java.lang.String r0 = "mac4"
            r4.put(r0, r5)     // Catch:{ JSONException -> 0x00f4 }
            java.lang.String r5 = "temp_key_from_so"
            java.lang.String r0 = ""
            defpackage.ewp.a(r3, r5, r0)     // Catch:{ JSONException -> 0x00f4 }
            goto L_0x010a
        L_0x00f4:
            r3 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "Collected:"
            r5.<init>(r0)
            java.lang.String r3 = r3.getMessage()
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            defpackage.euw.a(r3)
        L_0x010a:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eve.a(android.content.Context, org.json.JSONObject, java.lang.String):org.json.JSONObject");
    }

    public static void a(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject != null && jSONObject2 != null) {
            Iterator<String> keys = jSONObject2.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                try {
                    jSONObject.put(next, jSONObject2.optString(next));
                } catch (JSONException e) {
                    StringBuilder sb = new StringBuilder("Collected:");
                    sb.append(e.getMessage());
                    euw.a(sb.toString());
                }
            }
        }
    }

    public static void a(String str) {
        if (evd.x != null && !TextUtils.isEmpty(str)) {
            evp evp = evd.x;
        }
    }

    public static void b(String str) {
        if (evd.x != null && !TextUtils.isEmpty(str)) {
            evd.x.a();
        }
    }

    public static void c(String str) {
        if (evd.x != null && !TextUtils.isEmpty(str)) {
            evd.x.b();
        }
    }

    private static String b(Context context, String str) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(euw.w(context));
        sb.append(euw.y(context));
        sb.append(euw.a(context));
        sb.append(str);
        sb.append(random.nextInt(100000000));
        String a2 = ewn.a(sb.toString());
        int i = 0;
        for (char c : a2.toCharArray()) {
            i += c;
        }
        String hexString = Integer.toHexString(i % 256);
        if (hexString.length() < 2) {
            hexString = "0".concat(String.valueOf(hexString));
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(hexString);
        sb2.append(a2);
        String sb3 = sb2.toString();
        ewp.a(context, "hmt_irsuid", "irsuid_id", sb3);
        ewp.a(context, "hmt_irsuid", "irsuid_save_time", str);
        return sb3;
    }

    public static String b(Context context) {
        return (String) ewp.b(context, "hmt_irsuid", "irsuid_id", "");
    }

    public static synchronized void c(Context context) {
        synchronized (eve.class) {
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String str = (String) ewp.b(context, "hmt_irsuid", "irsuid_save_time", "");
            if (!format.equals(str) || str.equals("")) {
                b(context, format);
            }
        }
    }

    public static synchronized boolean d(Context context) {
        synchronized (eve.class) {
            try {
                String str = (String) ewp.b(context, "hmt_irsuid", "irsuid_send_time", "");
                if (!new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(str) || str.equals("")) {
                    return true;
                }
                return false;
            }
        }
    }

    public static boolean a(long j, long j2) {
        long j3 = j - j2;
        return j3 < 86400000 && j3 > -86400000 && a(j) == a(j2);
    }

    private static long a(long j) {
        return (j + ((long) TimeZone.getDefault().getOffset(j))) / 86400000;
    }

    public static boolean e(Context context) {
        return !a(System.currentTimeMillis(), ((Long) ewp.b(context, "hmt_init_savetime", "upload_save_time", Long.valueOf(0))).longValue());
    }

    public static boolean a(Long l) {
        return System.currentTimeMillis() - l.longValue() > 259200000;
    }

    public static String a(Context context, String str) {
        String d = euw.d(context);
        String str2 = (String) ewp.b(context, "hmt_send_url", "");
        if (!TextUtils.isEmpty(str2)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("/hmt?_z=m&jsonp=hmt&_ua=");
            sb.append(d);
            sb.append(evd.A);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("&_ua=");
        sb2.append(d);
        return sb2.toString();
    }

    public static String d(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length() / 2;
        String str2 = str;
        for (int i = 0; i < length; i++) {
            for (char append : Character.toChars(Integer.parseInt(str2.substring(0, 2), 16))) {
                sb.append(append);
            }
            if (str2.length() > 2) {
                str2 = str2.substring(2, str2.length());
            }
        }
        return sb.toString();
    }

    public static void a(final Intent intent, final Context context) {
        if (intent != null && context != null) {
            ewq.b().execute(new Runnable() {
                public final void run() {
                    Uri data = intent.getData();
                    if (data != null) {
                        ewe ewe = new ewe();
                        String uri = data.toString();
                        if (!TextUtils.isEmpty(uri)) {
                            int indexOf = uri.indexOf("?");
                            if (indexOf > 0) {
                                uri = uri.substring(0, indexOf);
                            }
                            if (!TextUtils.isEmpty(uri)) {
                                ewe.a("sys_dl_path", uri);
                                for (String next : data.getQueryParameterNames()) {
                                    String queryParameter = data.getQueryParameter(next);
                                    if (TextUtils.isEmpty(queryParameter)) {
                                        queryParameter = "";
                                    }
                                    ewe.a(next, queryParameter);
                                }
                                eve.a;
                                euw.a((String) "Send app-links action!");
                                euv.a(context, (String) "sys_dl", ewe);
                            }
                        }
                    }
                }
            });
        }
    }

    public static JSONObject a(JSONObject jSONObject, ewe ewe) {
        if (ewe != null) {
            JSONObject jSONObject2 = ewe.a;
            Iterator<String> keys = jSONObject2.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                try {
                    Object obj = jSONObject2.get(next);
                    if (obj instanceof JSONArray) {
                        jSONObject.put("p_".concat(String.valueOf(next)), obj);
                    } else {
                        JSONArray jSONArray = new JSONArray();
                        jSONArray.put(obj);
                        jSONObject.put("p_".concat(String.valueOf(next)), jSONArray);
                    }
                } catch (JSONException e) {
                    StringBuilder sb = new StringBuilder("Collected:");
                    sb.append(e.getMessage());
                    euw.a(sb.toString());
                }
            }
        }
        return jSONObject;
    }
}
