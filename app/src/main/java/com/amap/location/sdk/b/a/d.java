package com.amap.location.sdk.b.a;

import com.amap.location.e.b.c;
import com.amap.location.uptunnel.UpTunnel;
import org.json.JSONObject;

/* compiled from: UpTunnelWrappper */
public class d {
    private static a a;
    private static b b;
    private static b c;
    private static b d;
    private static b e;
    private static boolean f;

    /* compiled from: UpTunnelWrappper */
    public static class a implements c {
        public void a(int i, int i2) {
        }

        public void a(String str) {
        }

        public void a(byte[] bArr) {
            d.b(1, bArr);
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0036 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x00b8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00f8 */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x007e A[SYNTHETIC, Splitter:B:18:0x007e] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b2 A[Catch:{ Throwable -> 0x00b8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00e0 A[Catch:{ Throwable -> 0x00f8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r9, boolean r10) {
        /*
            java.lang.Class<com.amap.location.sdk.b.a.d> r0 = com.amap.location.sdk.b.a.d.class
            monitor-enter(r0)
            f = r10     // Catch:{ all -> 0x0120 }
            com.amap.location.uptunnel.ConfigContainer r1 = new com.amap.location.uptunnel.ConfigContainer     // Catch:{ all -> 0x0120 }
            r1.<init>()     // Catch:{ all -> 0x0120 }
            com.amap.location.sdk.b.a.a r2 = new com.amap.location.sdk.b.a.a     // Catch:{ all -> 0x0120 }
            r3 = 0
            r2.<init>(r3)     // Catch:{ all -> 0x0120 }
            a = r2     // Catch:{ all -> 0x0120 }
            r1.mCountTunnelConfig = r2     // Catch:{ all -> 0x0120 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ all -> 0x0120 }
            r2.<init>()     // Catch:{ all -> 0x0120 }
            r4 = 1000000(0xf4240, float:1.401298E-39)
            r5 = 1
            r6 = 0
            java.lang.String r7 = "enableUploadWifi"
            r2.put(r7, r5)     // Catch:{ Throwable -> 0x0036 }
            java.lang.String r7 = "enableUploadMobile"
            r2.put(r7, r6)     // Catch:{ Throwable -> 0x0036 }
            java.lang.String r7 = "maxSizeInDB"
            if (r10 == 0) goto L_0x0030
            r8 = 1000000(0xf4240, float:1.401298E-39)
            goto L_0x0033
        L_0x0030:
            r8 = 500000(0x7a120, float:7.00649E-40)
        L_0x0033:
            r2.put(r7, r8)     // Catch:{ Throwable -> 0x0036 }
        L_0x0036:
            com.amap.location.sdk.b.a.b r7 = new com.amap.location.sdk.b.a.b     // Catch:{ all -> 0x0120 }
            r7.<init>(r2)     // Catch:{ all -> 0x0120 }
            b = r7     // Catch:{ all -> 0x0120 }
            r1.mEventTunnelConfig = r7     // Catch:{ all -> 0x0120 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ all -> 0x0120 }
            r2.<init>()     // Catch:{ all -> 0x0120 }
            java.lang.String r7 = "enableUploadWifi"
            r2.put(r7, r5)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r7 = "enableUploadMobile"
            r2.put(r7, r6)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r7 = "maxSizeInDB"
            r8 = 10000000(0x989680, float:1.4012985E-38)
            r2.put(r7, r8)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r7 = "bufferSize"
            r8 = 1000(0x3e8, float:1.401E-42)
            r2.put(r7, r8)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r7 = "blockCount"
            r2.put(r7, r5)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r5 = "storePeriod"
            r7 = 20000(0x4e20, float:2.8026E-41)
            r2.put(r5, r7)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r5 = "sizeOfPerRequestWifi"
            r7 = 100000(0x186a0, float:1.4013E-40)
            r2.put(r5, r7)     // Catch:{ Throwable -> 0x007c }
            java.lang.String r5 = "uploadTimeout"
            r7 = 300000(0x493e0, float:4.2039E-40)
            r2.put(r5, r7)     // Catch:{ Throwable -> 0x007c }
        L_0x007c:
            if (r10 == 0) goto L_0x0083
            com.amap.location.sdk.b.a.b r3 = new com.amap.location.sdk.b.a.b     // Catch:{ all -> 0x0120 }
            r3.<init>(r2)     // Catch:{ all -> 0x0120 }
        L_0x0083:
            c = r3     // Catch:{ all -> 0x0120 }
            r1.mDataBlockTunnelConfig = r3     // Catch:{ all -> 0x0120 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ all -> 0x0120 }
            r2.<init>()     // Catch:{ all -> 0x0120 }
            r3 = 2500000(0x2625a0, float:3.503246E-39)
            java.lang.String r5 = "SP_TRACE_CLOUD"
            android.content.SharedPreferences r5 = r9.getSharedPreferences(r5, r6)     // Catch:{ Throwable -> 0x00b8 }
            java.lang.String r7 = "is_key_log_switch"
            boolean r5 = r5.getBoolean(r7, r6)     // Catch:{ Throwable -> 0x00b8 }
            java.lang.String r7 = "enableUploadWifi"
            r2.put(r7, r5)     // Catch:{ Throwable -> 0x00b8 }
            java.lang.String r5 = "enableUploadMobile"
            r2.put(r5, r6)     // Catch:{ Throwable -> 0x00b8 }
            java.lang.String r5 = "timeGapOfAutoUpload"
            r7 = 1800000(0x1b7740, float:2.522337E-39)
            r2.put(r5, r7)     // Catch:{ Throwable -> 0x00b8 }
            java.lang.String r5 = "maxSizeInDB"
            if (r10 == 0) goto L_0x00b5
            r4 = 2500000(0x2625a0, float:3.503246E-39)
        L_0x00b5:
            r2.put(r5, r4)     // Catch:{ Throwable -> 0x00b8 }
        L_0x00b8:
            com.amap.location.sdk.b.a.b r4 = new com.amap.location.sdk.b.a.b     // Catch:{ all -> 0x0120 }
            r4.<init>(r2)     // Catch:{ all -> 0x0120 }
            d = r4     // Catch:{ all -> 0x0120 }
            r1.mKeyLogConfig = r4     // Catch:{ all -> 0x0120 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ all -> 0x0120 }
            r2.<init>()     // Catch:{ all -> 0x0120 }
            java.lang.String r4 = "SP_TRACE_CLOUD"
            android.content.SharedPreferences r4 = r9.getSharedPreferences(r4, r6)     // Catch:{ Throwable -> 0x00f8 }
            java.lang.String r5 = "is_trace_open"
            boolean r4 = r4.getBoolean(r5, r6)     // Catch:{ Throwable -> 0x00f8 }
            java.lang.String r5 = "enableUploadWifi"
            r2.put(r5, r4)     // Catch:{ Throwable -> 0x00f8 }
            java.lang.String r4 = "enableUploadMobile"
            r2.put(r4, r6)     // Catch:{ Throwable -> 0x00f8 }
            java.lang.String r4 = "maxSizeInDB"
            if (r10 == 0) goto L_0x00e3
            r3 = 1500000(0x16e360, float:2.101948E-39)
        L_0x00e3:
            r2.put(r4, r3)     // Catch:{ Throwable -> 0x00f8 }
            java.lang.String r10 = "timeGapOfAutoUpload"
            r3 = 3600000(0x36ee80, float:5.044674E-39)
            r2.put(r10, r3)     // Catch:{ Throwable -> 0x00f8 }
            java.lang.String r10 = "validityTimeInDB"
            r3 = 432000000(0x19bfcc00, float:1.9831332E-23)
            r2.put(r10, r3)     // Catch:{ Throwable -> 0x00f8 }
        L_0x00f8:
            com.amap.location.sdk.b.a.b r10 = new com.amap.location.sdk.b.a.b     // Catch:{ all -> 0x0120 }
            r10.<init>(r2)     // Catch:{ all -> 0x0120 }
            e = r10     // Catch:{ all -> 0x0120 }
            r1.mLogConfig = r10     // Catch:{ all -> 0x0120 }
            com.amap.location.sdk.b.d r10 = com.amap.location.sdk.b.d.a()     // Catch:{ all -> 0x0120 }
            r1.mHttpClient = r10     // Catch:{ all -> 0x0120 }
            com.amap.location.uptunnel.UpTunnel.init(r9, r1)     // Catch:{ all -> 0x0120 }
            com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r9 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.getInstance()     // Catch:{ all -> 0x0120 }
            com.amap.location.sdk.b.a.d$1 r10 = new com.amap.location.sdk.b.a.d$1     // Catch:{ all -> 0x0120 }
            r10.<init>()     // Catch:{ all -> 0x0120 }
            r9.setIndoorFeedback(r10)     // Catch:{ all -> 0x0120 }
            com.amap.location.sdk.b.a.d$a r9 = new com.amap.location.sdk.b.a.d$a     // Catch:{ all -> 0x0120 }
            r9.<init>()     // Catch:{ all -> 0x0120 }
            com.amap.location.e.b.d.a(r9)     // Catch:{ all -> 0x0120 }
            monitor-exit(r0)
            return
        L_0x0120:
            r9 = move-exception
            monitor-exit(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.b.a.d.a(android.content.Context, boolean):void");
    }

    public static synchronized void a() {
        synchronized (d.class) {
            UpTunnel.destroy();
            a = null;
            b = null;
            d = null;
            e = null;
            com.amap.location.e.b.d.a((c) null);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:5|6|7|8|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0011 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(boolean r3) {
        /*
            java.lang.Class<com.amap.location.sdk.b.a.d> r0 = com.amap.location.sdk.b.a.d.class
            monitor-enter(r0)
            com.amap.location.sdk.b.a.b r1 = e     // Catch:{ all -> 0x0018 }
            if (r1 == 0) goto L_0x0016
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ all -> 0x0018 }
            r1.<init>()     // Catch:{ all -> 0x0018 }
            java.lang.String r2 = "enableUploadWifi"
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0011 }
        L_0x0011:
            com.amap.location.sdk.b.a.b r3 = e     // Catch:{ all -> 0x0018 }
            r3.a(r1)     // Catch:{ all -> 0x0018 }
        L_0x0016:
            monitor-exit(r0)
            return
        L_0x0018:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.b.a.d.a(boolean):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:5|6|7|8|9|10) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0011 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void b(boolean r3) {
        /*
            java.lang.Class<com.amap.location.sdk.b.a.d> r0 = com.amap.location.sdk.b.a.d.class
            monitor-enter(r0)
            com.amap.location.sdk.b.a.b r1 = d     // Catch:{ all -> 0x0018 }
            if (r1 == 0) goto L_0x0016
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ all -> 0x0018 }
            r1.<init>()     // Catch:{ all -> 0x0018 }
            java.lang.String r2 = "enableUploadWifi"
            r1.put(r2, r3)     // Catch:{ JSONException -> 0x0011 }
        L_0x0011:
            com.amap.location.sdk.b.a.b r3 = d     // Catch:{ all -> 0x0018 }
            r3.a(r1)     // Catch:{ all -> 0x0018 }
        L_0x0016:
            monitor-exit(r0)
            return
        L_0x0018:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.b.a.d.b(boolean):void");
    }

    public static boolean b() {
        return f;
    }

    public static void a(int i) {
        UpTunnel.addCount(i);
    }

    public static void a(int i, byte[] bArr) {
        UpTunnel.reportEvent(i, bArr);
    }

    public static void a(int i, String str) {
        UpTunnel.reportLog(i, str);
    }

    public static void b(int i, byte[] bArr) {
        UpTunnel.reportKeyLog(i, bArr);
    }

    public static void a(JSONObject jSONObject) {
        UpTunnel.execCMD(jSONObject);
    }
}
