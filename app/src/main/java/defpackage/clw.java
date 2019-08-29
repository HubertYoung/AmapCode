package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.server.aos.serverkey;
import java.util.HashMap;
import java.util.Map;

/* renamed from: clw reason: default package */
/* compiled from: ABTestConfigProvider */
public class clw implements a {
    final lp a = new lp() {
        public final void onConfigCallBack(int i) {
        }

        public final void onConfigResultCallBack(int i, String str) {
            StringBuilder sb = new StringBuilder("onConfigResultCallBack = ");
            sb.append(i);
            sb.append(",result = ");
            sb.append(str);
            AMapLog.i("network.ABTest", sb.toString());
            clw.a(clw.this, i, str);
        }
    };
    private Context b;
    private Map<String, Object> c = new HashMap();
    private volatile boolean d;

    public clw(Context context) {
        this.b = context;
    }

    private void a(String str) {
        if (str != null) {
            Editor edit = this.b.getSharedPreferences("ABtestPrefsFile", 0).edit();
            if (str.length() > 0) {
                edit.putString("abtest", serverkey.amapEncodeV2(str));
            } else {
                edit.putString("abtest", str);
            }
            edit.apply();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a0, code lost:
        if (r2 == null) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a8, code lost:
        if (r2.startsWith("/") == false) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00aa, code lost:
        r2 = r2.substring(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b5, code lost:
        if (r2.endsWith("/") == false) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b7, code lost:
        r2 = r2.substring(0, r2.length() - 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c1, code lost:
        com.amap.bundle.logs.AMapLog.d("network.ABTest", "key = ".concat(java.lang.String.valueOf(r2)));
        r12.put(r2, r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.lang.String r12) {
        /*
            r11 = this;
            if (r12 != 0) goto L_0x0003
            return
        L_0x0003:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x00e7 }
            r0.<init>(r12)     // Catch:{ Exception -> 0x00e7 }
            java.util.HashMap r12 = new java.util.HashMap     // Catch:{ Exception -> 0x00e7 }
            r12.<init>()     // Catch:{ Exception -> 0x00e7 }
            java.util.Iterator r1 = r0.keys()     // Catch:{ Exception -> 0x00e7 }
            if (r1 != 0) goto L_0x0014
            return
        L_0x0014:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x00e7 }
            if (r2 == 0) goto L_0x00d5
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00e7 }
            org.json.JSONObject r3 = r0.getJSONObject(r2)     // Catch:{ Exception -> 0x00e7 }
            if (r3 != 0) goto L_0x0029
            return
        L_0x0029:
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ Exception -> 0x00e7 }
            r4.<init>()     // Catch:{ Exception -> 0x00e7 }
            java.util.Iterator r5 = r3.keys()     // Catch:{ Exception -> 0x00e7 }
            if (r5 != 0) goto L_0x0035
            return
        L_0x0035:
            boolean r6 = r5.hasNext()     // Catch:{ Exception -> 0x00e7 }
            r7 = 0
            if (r6 == 0) goto L_0x009f
            java.lang.Object r6 = r5.next()     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00e7 }
            org.json.JSONArray r8 = r3.getJSONArray(r6)     // Catch:{ Exception -> 0x00e7 }
            if (r8 != 0) goto L_0x004b
            return
        L_0x004b:
            java.lang.String r9 = ""
        L_0x004d:
            int r10 = r8.length()     // Catch:{ Exception -> 0x00e7 }
            if (r7 >= r10) goto L_0x0080
            int r10 = r9.length()     // Catch:{ Exception -> 0x00e7 }
            if (r10 <= 0) goto L_0x006a
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e7 }
            r10.<init>()     // Catch:{ Exception -> 0x00e7 }
            r10.append(r9)     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r9 = "|"
            r10.append(r9)     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r9 = r10.toString()     // Catch:{ Exception -> 0x00e7 }
        L_0x006a:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e7 }
            r10.<init>()     // Catch:{ Exception -> 0x00e7 }
            r10.append(r9)     // Catch:{ Exception -> 0x00e7 }
            java.lang.Object r9 = r8.get(r7)     // Catch:{ Exception -> 0x00e7 }
            r10.append(r9)     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r9 = r10.toString()     // Catch:{ Exception -> 0x00e7 }
            int r7 = r7 + 1
            goto L_0x004d
        L_0x0080:
            java.lang.String r7 = "network.ABTest"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r10 = "param key = "
            r8.<init>(r10)     // Catch:{ Exception -> 0x00e7 }
            r8.append(r6)     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r10 = ", param value = "
            r8.append(r10)     // Catch:{ Exception -> 0x00e7 }
            r8.append(r9)     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x00e7 }
            com.amap.bundle.logs.AMapLog.d(r7, r8)     // Catch:{ Exception -> 0x00e7 }
            r4.put(r6, r9)     // Catch:{ Exception -> 0x00e7 }
            goto L_0x0035
        L_0x009f:
            r3 = 1
            if (r2 == 0) goto L_0x00af
            java.lang.String r5 = "/"
            boolean r5 = r2.startsWith(r5)     // Catch:{ Exception -> 0x00e7 }
            if (r5 == 0) goto L_0x00af
            java.lang.String r2 = r2.substring(r3)     // Catch:{ Exception -> 0x00e7 }
            goto L_0x009f
        L_0x00af:
            java.lang.String r5 = "/"
            boolean r5 = r2.endsWith(r5)     // Catch:{ Exception -> 0x00e7 }
            if (r5 == 0) goto L_0x00c1
            int r5 = r2.length()     // Catch:{ Exception -> 0x00e7 }
            int r5 = r5 - r3
            java.lang.String r2 = r2.substring(r7, r5)     // Catch:{ Exception -> 0x00e7 }
            goto L_0x00af
        L_0x00c1:
            java.lang.String r3 = "network.ABTest"
            java.lang.String r5 = "key = "
            java.lang.String r6 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x00e7 }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ Exception -> 0x00e7 }
            com.amap.bundle.logs.AMapLog.d(r3, r5)     // Catch:{ Exception -> 0x00e7 }
            r12.put(r2, r4)     // Catch:{ Exception -> 0x00e7 }
            goto L_0x0014
        L_0x00d5:
            java.util.Map<java.lang.String, java.lang.Object> r0 = r11.c     // Catch:{ Exception -> 0x00e7 }
            monitor-enter(r0)     // Catch:{ Exception -> 0x00e7 }
            java.util.Map<java.lang.String, java.lang.Object> r1 = r11.c     // Catch:{ all -> 0x00e4 }
            r1.clear()     // Catch:{ all -> 0x00e4 }
            java.util.Map<java.lang.String, java.lang.Object> r1 = r11.c     // Catch:{ all -> 0x00e4 }
            r1.putAll(r12)     // Catch:{ all -> 0x00e4 }
            monitor-exit(r0)     // Catch:{ all -> 0x00e4 }
            return
        L_0x00e4:
            r12 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00e4 }
            throw r12     // Catch:{ Exception -> 0x00e7 }
        L_0x00e7:
            r12 = move-exception
            java.lang.String r0 = "network.ABTest"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "parse error:"
            r1.<init>(r2)
            java.lang.String r12 = r12.getLocalizedMessage()
            r1.append(r12)
            java.lang.String r12 = r1.toString()
            com.amap.bundle.logs.AMapLog.e(r0, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.clw.b(java.lang.String):void");
    }

    public final Map<String, Object> a() {
        if (!this.d) {
            synchronized (this) {
                if (!this.d) {
                    this.d = true;
                    String string = this.b.getSharedPreferences("ABtestPrefsFile", 0).getString("abtest", null);
                    if (string != null && string.length() > 0) {
                        string = serverkey.amapDecodeV2(string);
                    }
                    AMapLog.d("network.ABTest", "data from sp = ".concat(String.valueOf(string)));
                    b(string);
                }
            }
        }
        HashMap hashMap = new HashMap();
        synchronized (this.c) {
            hashMap.putAll(this.c);
        }
        return hashMap;
    }

    static /* synthetic */ void a(clw clw, int i, String str) {
        switch (i) {
            case 0:
            case 1:
            case 4:
                clw.a(str);
                clw.b(str);
                break;
            case 3:
                clw.a("");
                synchronized (clw.c) {
                    clw.c.clear();
                }
                break;
        }
        synchronized (clw) {
            clw.d = true;
        }
    }
}
