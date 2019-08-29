package com.amap.location.sdk.a;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import com.amap.api.service.locationprovider.b;
import com.amap.location.security.Core;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AMapServiceUtils */
public class a {
    public static String a() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApY1XT6wOnohyD2nBBm4gri0WAbJv0HaF/S+gO3hpz2yKWKbkpWhzDpzMTQZNELHXCKkk/eZFov2PRyK/2kYQxfeg/6hxIEg1B2MTDwG6gSKMD0yQcIC8162O+tOr8hLK6hCKQOiHvgGykCp1nXs0yfqIPhjurauPI2ibnxlNvzlAFjBTXHbzXcgDFoDCEwSjvAF62z2ftMJ5c0dnVqawWzclkiVA9Ro8hfiEPgdaLCA40VCCIJMh66y6iaNYp/lTQ0brJ1ZNpsE22eDCmp6XgVeegm+SyIMzltZY6/z7gbUueToHRizn15jV5jlheLBIhw+0ENj5LZbSmEb5HY9j1wIDAQAB";
    }

    public static String b() {
        return "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwhGMHTt1dgDa4QKY6spO1/BR+QBGlpcufJD/SAwITKsLSG5nCa1CvaDvQH4dymZzpQ7Y4qMMu992KqS0Tu1gb3W1v3OA8/3d3cv5vuhzKsWmpPhkEER8P1T3SnImmd3iGaeDFjsiEMIhUkLz0wKPzWvIVeEoAWRwd5RhcNMrvgZhvod9niBOqeGPBAwp/hvss3pF/rWjK1k0CMrJXna8oGUhj79bpG3RQ5x/8WRCNHQq/aWU/gGUJd6PWtmjMrkaxzNKLqVyiomJevWPHyodU8ne+dqxei60ebjm/Wjn17dfIW/t2C+T/a0PmUWG8h2BWscwTwNoy6tClEPg5oO2IQIDAQAB";
    }

    public static String a(Context context, int i) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
        if (activityManager != null) {
            List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
                for (RunningAppProcessInfo next : runningAppProcesses) {
                    if (next != null && next.pid == i) {
                        return next.processName;
                    }
                }
            }
        }
        return null;
    }

    public static boolean a(Context context, com.amap.api.service.locationprovider.a aVar, String str, int i) {
        String str2;
        if (context == null || aVar == null || aVar.a() == null || str == null) {
            return false;
        }
        switch (i) {
            case 0:
                str2 = Core.getTag(context, str);
                break;
            case 1:
                str2 = Core.encode(context, str, 31);
                break;
            case 2:
                str2 = Core.transfer(context, str, 3.14159265d);
                break;
            default:
                try {
                    str2 = Core.load(context, str, 239641);
                    break;
                } catch (Exception unused) {
                    break;
                }
        }
        String trim = str2.trim();
        for (String next : aVar.a()) {
            if (next != null && next.trim().startsWith("android:apk-key-hash:")) {
                String substring = next.trim().substring(21);
                if (substring != null && substring.equals(trim)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String c() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append((char) ((int) ((Math.random() * 94.0d) + 33.0d)));
        }
        return sb.toString();
    }

    public static com.amap.api.service.locationprovider.a a(Context context) {
        String a = a(context, (String) "keyhash.dat");
        if (a == null) {
            a = b(context, "keyhash.dat");
            if (a == null) {
                return null;
            }
        }
        try {
            JSONArray jSONArray = new JSONObject(a).getJSONObject("data").getJSONArray("ids");
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add((String) jSONArray.get(i));
            }
            com.amap.api.service.locationprovider.a aVar = new com.amap.api.service.locationprovider.a();
            b bVar = new b();
            bVar.a(1);
            bVar.b(1);
            aVar.a(bVar);
            aVar.a((List<String>) arrayList);
            return aVar;
        } catch (JSONException unused) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0039 A[SYNTHETIC, Splitter:B:26:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x003e A[SYNTHETIC, Splitter:B:30:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0046 A[SYNTHETIC, Splitter:B:38:0x0046] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x004b A[SYNTHETIC, Splitter:B:42:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r4, java.lang.String r5) {
        /*
            r0 = 0
            java.io.FileInputStream r4 = r4.openFileInput(r5)     // Catch:{ Exception -> 0x0042, all -> 0x0035 }
            int r5 = r4.available()     // Catch:{ Exception -> 0x0033, all -> 0x0031 }
            byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x0033, all -> 0x0031 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0033, all -> 0x0031 }
            r1.<init>()     // Catch:{ Exception -> 0x0033, all -> 0x0031 }
        L_0x0010:
            int r2 = r4.read(r5)     // Catch:{ Exception -> 0x0044, all -> 0x002e }
            r3 = -1
            if (r2 == r3) goto L_0x001c
            r3 = 0
            r1.write(r5, r3, r2)     // Catch:{ Exception -> 0x0044, all -> 0x002e }
            goto L_0x0010
        L_0x001c:
            byte[] r5 = r1.toByteArray()     // Catch:{ Exception -> 0x0044, all -> 0x002e }
            java.lang.String r2 = new java.lang.String     // Catch:{ Exception -> 0x0044, all -> 0x002e }
            r2.<init>(r5)     // Catch:{ Exception -> 0x0044, all -> 0x002e }
            r1.close()     // Catch:{ Exception -> 0x0028 }
        L_0x0028:
            if (r4 == 0) goto L_0x002d
            r4.close()     // Catch:{ Exception -> 0x002d }
        L_0x002d:
            return r2
        L_0x002e:
            r5 = move-exception
            r0 = r1
            goto L_0x0037
        L_0x0031:
            r5 = move-exception
            goto L_0x0037
        L_0x0033:
            r1 = r0
            goto L_0x0044
        L_0x0035:
            r5 = move-exception
            r4 = r0
        L_0x0037:
            if (r0 == 0) goto L_0x003c
            r0.close()     // Catch:{ Exception -> 0x003c }
        L_0x003c:
            if (r4 == 0) goto L_0x0041
            r4.close()     // Catch:{ Exception -> 0x0041 }
        L_0x0041:
            throw r5
        L_0x0042:
            r4 = r0
            r1 = r4
        L_0x0044:
            if (r1 == 0) goto L_0x0049
            r1.close()     // Catch:{ Exception -> 0x0049 }
        L_0x0049:
            if (r4 == 0) goto L_0x004e
            r4.close()     // Catch:{ Exception -> 0x004e }
        L_0x004e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.a.a.a(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0038 A[SYNTHETIC, Splitter:B:18:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003f A[SYNTHETIC, Splitter:B:26:0x003f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(android.content.Context r4, java.lang.String r5) {
        /*
            r0 = 0
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch:{ IOException -> 0x003c, all -> 0x0034 }
            java.io.InputStream r4 = r4.open(r5)     // Catch:{ IOException -> 0x003c, all -> 0x0034 }
            int r5 = r4.available()     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            byte[] r5 = new byte[r5]     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            r1.<init>()     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
        L_0x0014:
            int r2 = r4.read(r5)     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            r3 = -1
            if (r2 == r3) goto L_0x0020
            r3 = 0
            r1.write(r5, r3, r2)     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            goto L_0x0014
        L_0x0020:
            byte[] r5 = r1.toByteArray()     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            r1.close()     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            java.lang.String r1 = new java.lang.String     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            r1.<init>(r5)     // Catch:{ IOException -> 0x003d, all -> 0x0032 }
            if (r4 == 0) goto L_0x0031
            r4.close()     // Catch:{ Exception -> 0x0031 }
        L_0x0031:
            return r1
        L_0x0032:
            r5 = move-exception
            goto L_0x0036
        L_0x0034:
            r5 = move-exception
            r4 = r0
        L_0x0036:
            if (r4 == 0) goto L_0x003b
            r4.close()     // Catch:{ Exception -> 0x003b }
        L_0x003b:
            throw r5
        L_0x003c:
            r4 = r0
        L_0x003d:
            if (r4 == 0) goto L_0x0042
            r4.close()     // Catch:{ Exception -> 0x0042 }
        L_0x0042:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.a.a.b(android.content.Context, java.lang.String):java.lang.String");
    }
}
