package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.io.File;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ejr reason: default package */
/* compiled from: StationFileUtil */
public class ejr {
    private static File h;
    Context a;
    File b;
    File c;
    File d;
    File e;
    private File f;
    private File g;
    private int i = 0;
    private String j;

    public ejr(int i2) {
        this.i = i2;
        this.a = AMapPageUtil.getAppContext();
        this.b = this.a.getFilesDir();
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getAbsolutePath());
        sb.append(File.separator);
        sb.append("station_list");
        this.c = new File(sb.toString());
        if (!this.c.exists()) {
            this.c.mkdirs();
        }
        this.j = i2 == 0 ? "train" : "coach";
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.j);
        sb2.append("_");
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.c);
        sb4.append(File.separator);
        sb4.append(sb3);
        sb4.append("station.json");
        this.f = new File(sb4.toString());
        StringBuilder sb5 = new StringBuilder();
        sb5.append(this.c);
        sb5.append(File.separator);
        sb5.append(sb3);
        sb5.append("station.tmp");
        this.d = new File(sb5.toString());
        StringBuilder sb6 = new StringBuilder();
        sb6.append(this.c);
        sb6.append(File.separator);
        sb6.append(sb3);
        sb6.append("hot_station.json");
        this.g = new File(sb6.toString());
        StringBuilder sb7 = new StringBuilder();
        sb7.append(this.c);
        sb7.append(File.separator);
        sb7.append(sb3);
        sb7.append("hot_station.tmp");
        this.e = new File(sb7.toString());
        StringBuilder sb8 = new StringBuilder();
        sb8.append(this.c);
        sb8.append(File.separator);
        sb8.append(sb3);
        sb8.append("hot_station.tmp");
        h = new File(sb8.toString());
    }

    public final boolean a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("staJson");
            JSONArray jSONArray2 = jSONObject.getJSONArray("hotCities");
            int optInt = jSONObject.optInt("preSellDate", -1);
            String optString = jSONObject.optString("md5");
            a(this.e);
            a(this.d);
            if (!this.e.exists() || !this.d.exists() || !a(jSONArray2, this.e) || !a(jSONArray, this.d)) {
                return false;
            }
            if (optInt != -1) {
                ejs.a().a(this.i, optInt);
            }
            if (!TextUtils.isEmpty(optString)) {
                ejs.a().a(this.i, optString);
            }
            synchronized (ejr.class) {
                try {
                    this.g.delete();
                    this.f.delete();
                    this.e.renameTo(this.g);
                    this.d.renameTo(this.f);
                }
            }
            return true;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static boolean a(File file) {
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    return true;
                }
            } catch (IOException unused) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0039 A[SYNTHETIC, Splitter:B:27:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0041 A[Catch:{ IOException -> 0x003d }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x004d A[SYNTHETIC, Splitter:B:38:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0055 A[Catch:{ IOException -> 0x0051 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(org.json.JSONArray r2, java.io.File r3) {
        /*
            r0 = 0
            if (r2 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r2 = r2.toString()
            boolean r1 = android.text.TextUtils.isEmpty(r2)
            if (r1 == 0) goto L_0x000f
            return r0
        L_0x000f:
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0032, all -> 0x002f }
            r1.<init>(r3)     // Catch:{ Exception -> 0x0032, all -> 0x002f }
            java.io.OutputStreamWriter r3 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x002d }
            r3.<init>(r1)     // Catch:{ Exception -> 0x002d }
            r3.write(r2)     // Catch:{ Exception -> 0x002a, all -> 0x0027 }
            r3.flush()     // Catch:{ Exception -> 0x002a, all -> 0x0027 }
            r3.close()     // Catch:{ IOException -> 0x003d }
            r1.close()     // Catch:{ IOException -> 0x003d }
            goto L_0x0048
        L_0x0027:
            r2 = move-exception
            r0 = r3
            goto L_0x004b
        L_0x002a:
            r2 = move-exception
            r0 = r3
            goto L_0x0034
        L_0x002d:
            r2 = move-exception
            goto L_0x0034
        L_0x002f:
            r2 = move-exception
            r1 = r0
            goto L_0x004b
        L_0x0032:
            r2 = move-exception
            r1 = r0
        L_0x0034:
            r2.printStackTrace()     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x003f
            r0.close()     // Catch:{ IOException -> 0x003d }
            goto L_0x003f
        L_0x003d:
            r2 = move-exception
            goto L_0x0045
        L_0x003f:
            if (r1 == 0) goto L_0x0048
            r1.close()     // Catch:{ IOException -> 0x003d }
            goto L_0x0048
        L_0x0045:
            r2.printStackTrace()
        L_0x0048:
            r2 = 1
            return r2
        L_0x004a:
            r2 = move-exception
        L_0x004b:
            if (r0 == 0) goto L_0x0053
            r0.close()     // Catch:{ IOException -> 0x0051 }
            goto L_0x0053
        L_0x0051:
            r3 = move-exception
            goto L_0x0059
        L_0x0053:
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ IOException -> 0x0051 }
            goto L_0x005c
        L_0x0059:
            r3.printStackTrace()
        L_0x005c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ejr.a(org.json.JSONArray, java.io.File):boolean");
    }

    public final byte[] a() {
        if (this.f == null) {
            return null;
        }
        synchronized (this.f) {
            try {
                if (this.f != null && this.f.exists()) {
                    if (this.f.length() != 0) {
                        byte[] b2 = b(this.f);
                        return b2;
                    }
                }
                Context appContext = AMapPageUtil.getAppContext();
                StringBuilder sb = new StringBuilder();
                sb.append(this.j);
                sb.append("/station_list.json");
                byte[] a2 = ahd.a(appContext, sb.toString());
                return a2;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final org.json.JSONArray b() {
        /*
            r5 = this;
            java.io.File r0 = r5.g
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            java.io.File r0 = r5.g
            monitor-enter(r0)
            java.io.File r2 = r5.g     // Catch:{ all -> 0x004d }
            boolean r2 = r2.exists()     // Catch:{ all -> 0x004d }
            if (r2 == 0) goto L_0x0018
            java.io.File r2 = r5.g     // Catch:{ all -> 0x004d }
            byte[] r2 = b(r2)     // Catch:{ all -> 0x004d }
            goto L_0x0033
        L_0x0018:
            android.content.Context r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()     // Catch:{ all -> 0x004d }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x004d }
            r3.<init>()     // Catch:{ all -> 0x004d }
            java.lang.String r4 = r5.j     // Catch:{ all -> 0x004d }
            r3.append(r4)     // Catch:{ all -> 0x004d }
            java.lang.String r4 = "/hot_station_list.json"
            r3.append(r4)     // Catch:{ all -> 0x004d }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x004d }
            byte[] r2 = defpackage.ahd.a(r2, r3)     // Catch:{ all -> 0x004d }
        L_0x0033:
            if (r2 == 0) goto L_0x004b
            int r3 = r2.length     // Catch:{ all -> 0x004d }
            if (r3 != 0) goto L_0x0039
            goto L_0x004b
        L_0x0039:
            java.lang.String r3 = new java.lang.String     // Catch:{ all -> 0x004d }
            r3.<init>(r2)     // Catch:{ all -> 0x004d }
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0045 }
            r2.<init>(r3)     // Catch:{ JSONException -> 0x0045 }
            r1 = r2
            goto L_0x0049
        L_0x0045:
            r2 = move-exception
            r2.printStackTrace()     // Catch:{ all -> 0x004d }
        L_0x0049:
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return r1
        L_0x004b:
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return r1
        L_0x004d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ejr.b():org.json.JSONArray");
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x002b */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x004b A[SYNTHETIC, Splitter:B:36:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0050 A[SYNTHETIC, Splitter:B:40:0x0050] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x005c A[SYNTHETIC, Splitter:B:49:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0061 A[SYNTHETIC, Splitter:B:53:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0068 A[SYNTHETIC, Splitter:B:59:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x006d A[SYNTHETIC, Splitter:B:63:0x006d] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:33:0x0046=Splitter:B:33:0x0046, B:46:0x0057=Splitter:B:46:0x0057} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] b(java.io.File r6) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x0071
            boolean r1 = r6.exists()
            if (r1 != 0) goto L_0x000b
            goto L_0x0071
        L_0x000b:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0054, OutOfMemoryError -> 0x0043, all -> 0x003e }
            r1.<init>(r6)     // Catch:{ IOException -> 0x0054, OutOfMemoryError -> 0x0043, all -> 0x003e }
            java.io.ByteArrayOutputStream r6 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x003b, OutOfMemoryError -> 0x0038, all -> 0x0033 }
            r6.<init>()     // Catch:{ IOException -> 0x003b, OutOfMemoryError -> 0x0038, all -> 0x0033 }
            r2 = 1024(0x400, float:1.435E-42)
            byte[] r2 = new byte[r2]     // Catch:{ IOException -> 0x0031, OutOfMemoryError -> 0x002f }
        L_0x0019:
            int r3 = r1.read(r2)     // Catch:{ IOException -> 0x0031, OutOfMemoryError -> 0x002f }
            if (r3 < 0) goto L_0x0024
            r4 = 0
            r6.write(r2, r4, r3)     // Catch:{ IOException -> 0x0031, OutOfMemoryError -> 0x002f }
            goto L_0x0019
        L_0x0024:
            byte[] r2 = r6.toByteArray()     // Catch:{ IOException -> 0x0031, OutOfMemoryError -> 0x002f }
            r6.close()     // Catch:{ IOException -> 0x002b }
        L_0x002b:
            r1.close()     // Catch:{ IOException -> 0x002e }
        L_0x002e:
            return r2
        L_0x002f:
            r2 = move-exception
            goto L_0x0046
        L_0x0031:
            r2 = move-exception
            goto L_0x0057
        L_0x0033:
            r6 = move-exception
            r5 = r0
            r0 = r6
            r6 = r5
            goto L_0x0066
        L_0x0038:
            r2 = move-exception
            r6 = r0
            goto L_0x0046
        L_0x003b:
            r2 = move-exception
            r6 = r0
            goto L_0x0057
        L_0x003e:
            r6 = move-exception
            r1 = r0
            r0 = r6
            r6 = r1
            goto L_0x0066
        L_0x0043:
            r2 = move-exception
            r6 = r0
            r1 = r6
        L_0x0046:
            defpackage.kf.a(r2)     // Catch:{ all -> 0x0065 }
            if (r6 == 0) goto L_0x004e
            r6.close()     // Catch:{ IOException -> 0x004e }
        L_0x004e:
            if (r1 == 0) goto L_0x0053
            r1.close()     // Catch:{ IOException -> 0x0053 }
        L_0x0053:
            return r0
        L_0x0054:
            r2 = move-exception
            r6 = r0
            r1 = r6
        L_0x0057:
            defpackage.kf.a(r2)     // Catch:{ all -> 0x0065 }
            if (r6 == 0) goto L_0x005f
            r6.close()     // Catch:{ IOException -> 0x005f }
        L_0x005f:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ IOException -> 0x0064 }
        L_0x0064:
            return r0
        L_0x0065:
            r0 = move-exception
        L_0x0066:
            if (r6 == 0) goto L_0x006b
            r6.close()     // Catch:{ IOException -> 0x006b }
        L_0x006b:
            if (r1 == 0) goto L_0x0070
            r1.close()     // Catch:{ IOException -> 0x0070 }
        L_0x0070:
            throw r0
        L_0x0071:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ejr.b(java.io.File):byte[]");
    }
}
