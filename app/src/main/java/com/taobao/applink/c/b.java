package com.taobao.applink.c;

import com.taobao.applink.TBAppLinkSDK;
import com.taobao.applink.util.TBAppLinkUtil;
import com.taobao.applink.util.e;
import java.util.HashMap;
import java.util.Map;

public class b {
    private static volatile b b;
    private String a = TBAppLinkSDK.getInstance().sOpenParam.mAppkey;
    private Map c = new HashMap();
    private a d = c();

    public interface a {
        void a(a aVar);
    }

    private b() {
    }

    public static synchronized b a() {
        b bVar;
        synchronized (b.class) {
            try {
                if (b == null) {
                    b = new b();
                }
                bVar = b;
            }
        }
        return bVar;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(4:68|69|(1:71)|(3:73|74|75)) */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        r12 = new java.lang.StringBuffer();
        r12.append("isSuccess=0&configExist=0");
        b(com.taobao.applink.util.TBAppLinkUtil.getConfigUrl(), null, r12.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x019a, code lost:
        if (r0 != null) goto L_0x019c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x019c, code lost:
        r0.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x019f, code lost:
        if (r2 != null) goto L_0x01a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01a4, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01a5, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01a6, code lost:
        r12.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01aa, code lost:
        r12 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:68:0x0185 */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01ad  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01b2 A[SYNTHETIC, Splitter:B:83:0x01b2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(com.taobao.applink.c.b.a r12) {
        /*
            r11 = this;
            java.lang.String r0 = "https://nbsdk-baichuan.alicdn.com/2.0.0/applink.htm?plat=android&appKey=%s"
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = r11.a
            r3 = 0
            r1[r3] = r2
            java.lang.String r0 = java.lang.String.format(r0, r1)
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ Exception -> 0x0183, all -> 0x017f }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0183, all -> 0x017f }
            java.net.URLConnection r0 = r2.openConnection()     // Catch:{ Exception -> 0x0183, all -> 0x017f }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x0183, all -> 0x017f }
            r2 = 5000(0x1388, float:7.006E-42)
            r0.setConnectTimeout(r2)     // Catch:{ Exception -> 0x017d, all -> 0x017a }
            r0.setReadTimeout(r2)     // Catch:{ Exception -> 0x017d, all -> 0x017a }
            int r2 = r0.getResponseCode()     // Catch:{ Exception -> 0x017d, all -> 0x017a }
            r3 = 200(0xc8, float:2.8E-43)
            if (r2 != r3) goto L_0x016a
            java.io.InputStream r2 = r0.getInputStream()     // Catch:{ Exception -> 0x017d, all -> 0x017a }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0185 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0185 }
            java.lang.String r5 = "UTF-8"
            r4.<init>(r2, r5)     // Catch:{ Exception -> 0x0185 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0185 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0185 }
            r4.<init>()     // Catch:{ Exception -> 0x0185 }
        L_0x003f:
            java.lang.String r5 = r3.readLine()     // Catch:{ Exception -> 0x0185 }
            if (r5 == 0) goto L_0x0049
            r4.append(r5)     // Catch:{ Exception -> 0x0185 }
            goto L_0x003f
        L_0x0049:
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0185 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0185 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r4 = r11.d     // Catch:{ Exception -> 0x0185 }
            if (r4 != 0) goto L_0x005d
            com.taobao.applink.c.a r4 = new com.taobao.applink.c.a     // Catch:{ Exception -> 0x0185 }
            r4.<init>()     // Catch:{ Exception -> 0x0185 }
            r11.d = r4     // Catch:{ Exception -> 0x0185 }
        L_0x005d:
            com.taobao.applink.c.a r4 = r11.d     // Catch:{ Exception -> 0x0185 }
            r4.a(r3)     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r4 = r11.d     // Catch:{ Exception -> 0x0185 }
            boolean r4 = r4.a()     // Catch:{ Exception -> 0x0185 }
            if (r4 != 0) goto L_0x008f
            java.lang.StringBuffer r12 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0185 }
            r12.<init>()     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = "isSuccess=1&configExist=0"
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = com.taobao.applink.util.TBAppLinkUtil.getConfigUrl()     // Catch:{ Exception -> 0x0185 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0185 }
            r11.b(r3, r1, r12)     // Catch:{ Exception -> 0x0185 }
            if (r0 == 0) goto L_0x0084
            r0.disconnect()
        L_0x0084:
            if (r2 == 0) goto L_0x008e
            r2.close()     // Catch:{ IOException -> 0x008a }
            return
        L_0x008a:
            r12 = move-exception
            r12.printStackTrace()
        L_0x008e:
            return
        L_0x008f:
            java.lang.String r4 = "appLinkConfig"
            org.json.JSONObject r3 = r3.optJSONObject(r4)     // Catch:{ Exception -> 0x0185 }
            if (r3 != 0) goto L_0x00bc
            java.lang.StringBuffer r12 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0185 }
            r12.<init>()     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = "isSuccess=0&configExist=0"
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = com.taobao.applink.util.TBAppLinkUtil.getConfigUrl()     // Catch:{ Exception -> 0x0185 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0185 }
            r11.b(r3, r1, r12)     // Catch:{ Exception -> 0x0185 }
            if (r0 == 0) goto L_0x00b1
            r0.disconnect()
        L_0x00b1:
            if (r2 == 0) goto L_0x00bb
            r2.close()     // Catch:{ IOException -> 0x00b7 }
            return
        L_0x00b7:
            r12 = move-exception
            r12.printStackTrace()
        L_0x00bb:
            return
        L_0x00bc:
            com.taobao.applink.c.a r4 = r11.d     // Catch:{ Exception -> 0x0185 }
            java.lang.String r5 = "exp"
            long r5 = r3.optLong(r5)     // Catch:{ Exception -> 0x0185 }
            r4.d = r5     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r4 = r11.d     // Catch:{ Exception -> 0x0185 }
            java.lang.String r5 = "taobao_scheme"
            java.lang.String r5 = r3.optString(r5)     // Catch:{ Exception -> 0x0185 }
            r4.b = r5     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r4 = r11.d     // Catch:{ Exception -> 0x0185 }
            java.lang.String r5 = "tmall_scheme"
            java.lang.String r5 = r3.optString(r5)     // Catch:{ Exception -> 0x0185 }
            r4.c = r5     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r4 = r11.d     // Catch:{ Exception -> 0x0185 }
            java.lang.String r5 = "sign"
            java.lang.String r3 = r3.optString(r5)     // Catch:{ Exception -> 0x0185 }
            r4.f = r3     // Catch:{ Exception -> 0x0185 }
            java.util.Map r3 = r11.c     // Catch:{ Exception -> 0x0185 }
            if (r3 != 0) goto L_0x00ef
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ Exception -> 0x0185 }
            r3.<init>()     // Catch:{ Exception -> 0x0185 }
            r11.c = r3     // Catch:{ Exception -> 0x0185 }
        L_0x00ef:
            java.util.Map r3 = r11.c     // Catch:{ Exception -> 0x0185 }
            java.lang.String r4 = r11.a     // Catch:{ Exception -> 0x0185 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0185 }
            r3.remove(r4)     // Catch:{ Exception -> 0x0185 }
            java.util.Map r3 = r11.c     // Catch:{ Exception -> 0x0185 }
            java.lang.String r4 = r11.a     // Catch:{ Exception -> 0x0185 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0185 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r7 = r11.d     // Catch:{ Exception -> 0x0185 }
            long r7 = r7.d     // Catch:{ Exception -> 0x0185 }
            r9 = 1000(0x3e8, double:4.94E-321)
            long r7 = r7 * r9
            long r5 = r5 + r7
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ Exception -> 0x0185 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0185 }
            if (r12 == 0) goto L_0x011d
            com.taobao.applink.c.a r3 = r11.d     // Catch:{ Exception -> 0x0185 }
            r12.a(r3)     // Catch:{ Exception -> 0x0185 }
        L_0x011d:
            java.lang.StringBuffer r12 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x0185 }
            r12.<init>()     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = "isSuccess=1&configExist=1&appkeyExist=1&taobao_scheme="
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r3 = r11.d     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = r3.b     // Catch:{ Exception -> 0x0185 }
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = "&tmall_scheme="
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r3 = r11.d     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = r3.c     // Catch:{ Exception -> 0x0185 }
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = "&sug="
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r3 = r11.d     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = r3.e     // Catch:{ Exception -> 0x0185 }
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = "&sign="
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r3 = r11.d     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = r3.f     // Catch:{ Exception -> 0x0185 }
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = "&exp="
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            com.taobao.applink.c.a r3 = r11.d     // Catch:{ Exception -> 0x0185 }
            long r3 = r3.d     // Catch:{ Exception -> 0x0185 }
            r12.append(r3)     // Catch:{ Exception -> 0x0185 }
            java.lang.String r3 = com.taobao.applink.util.TBAppLinkUtil.getConfigUrl()     // Catch:{ Exception -> 0x0185 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x0185 }
            r11.b(r3, r1, r12)     // Catch:{ Exception -> 0x0185 }
            r1 = r2
        L_0x016a:
            if (r0 == 0) goto L_0x016f
            r0.disconnect()
        L_0x016f:
            if (r1 == 0) goto L_0x01a9
            r1.close()     // Catch:{ IOException -> 0x0175 }
            return
        L_0x0175:
            r12 = move-exception
            r12.printStackTrace()
            return
        L_0x017a:
            r12 = move-exception
            r2 = r1
            goto L_0x01ab
        L_0x017d:
            r2 = r1
            goto L_0x0185
        L_0x017f:
            r12 = move-exception
            r0 = r1
            r2 = r0
            goto L_0x01ab
        L_0x0183:
            r0 = r1
            r2 = r0
        L_0x0185:
            java.lang.StringBuffer r12 = new java.lang.StringBuffer     // Catch:{ all -> 0x01aa }
            r12.<init>()     // Catch:{ all -> 0x01aa }
            java.lang.String r3 = "isSuccess=0&configExist=0"
            r12.append(r3)     // Catch:{ all -> 0x01aa }
            java.lang.String r3 = com.taobao.applink.util.TBAppLinkUtil.getConfigUrl()     // Catch:{ all -> 0x01aa }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x01aa }
            r11.b(r3, r1, r12)     // Catch:{ all -> 0x01aa }
            if (r0 == 0) goto L_0x019f
            r0.disconnect()
        L_0x019f:
            if (r2 == 0) goto L_0x01a9
            r2.close()     // Catch:{ IOException -> 0x01a5 }
            return
        L_0x01a5:
            r12 = move-exception
            r12.printStackTrace()
        L_0x01a9:
            return
        L_0x01aa:
            r12 = move-exception
        L_0x01ab:
            if (r0 == 0) goto L_0x01b0
            r0.disconnect()
        L_0x01b0:
            if (r2 == 0) goto L_0x01ba
            r2.close()     // Catch:{ IOException -> 0x01b6 }
            goto L_0x01ba
        L_0x01b6:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01ba:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.applink.c.b.b(com.taobao.applink.c.b$a):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x010b A[SYNTHETIC, Splitter:B:43:0x010b] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x011f A[SYNTHETIC, Splitter:B:53:0x011f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r4 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            java.lang.System.currentTimeMillis()
            java.lang.String r1 = "logtype=2&wappkey="
            r0.append(r1)
            java.lang.String r1 = r4.a
            r0.append(r1)
            java.lang.String r1 = "&packagename="
            r0.append(r1)
            android.app.Application r1 = com.taobao.applink.util.TBAppLinkUtil.getApplication()
            java.lang.String r1 = com.taobao.applink.b.a.a(r1)
            r0.append(r1)
            java.lang.String r1 = "&os=android&&t=&sdkversion=2.0.0"
            r0.append(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r7)
            if (r1 != 0) goto L_0x0035
            java.lang.String r1 = "&"
            r0.append(r1)
            r0.append(r7)
        L_0x0035:
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x0043
            java.lang.String r7 = "&type="
            r0.append(r7)
            r0.append(r6)
        L_0x0043:
            com.taobao.applink.TBAppLinkSDK r6 = com.taobao.applink.TBAppLinkSDK.getInstance()
            com.taobao.applink.TBAppLinkParam r6 = r6.sOpenParam
            java.lang.String r6 = r6.mTtid
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x0061
            java.lang.String r6 = "&ttid="
            r0.append(r6)
            com.taobao.applink.TBAppLinkSDK r6 = com.taobao.applink.TBAppLinkSDK.getInstance()
            com.taobao.applink.TBAppLinkParam r6 = r6.sOpenParam
            java.lang.String r6 = r6.mTtid
            r0.append(r6)
        L_0x0061:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r5)
            java.lang.String r5 = "?"
            r6.append(r5)
            java.lang.String r5 = r0.toString()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            r6 = 0
            java.net.URL r7 = new java.net.URL     // Catch:{ Throwable -> 0x00fd, all -> 0x00f8 }
            r7.<init>(r5)     // Catch:{ Throwable -> 0x00fd, all -> 0x00f8 }
            java.net.URLConnection r5 = r7.openConnection()     // Catch:{ Throwable -> 0x00fd, all -> 0x00f8 }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ Throwable -> 0x00fd, all -> 0x00f8 }
            java.lang.String r7 = "POST"
            r5.setRequestMethod(r7)     // Catch:{ Throwable -> 0x00f3, all -> 0x00ee }
            r7 = 1
            r5.setDoOutput(r7)     // Catch:{ Throwable -> 0x00f3, all -> 0x00ee }
            r5.setDoInput(r7)     // Catch:{ Throwable -> 0x00f3, all -> 0x00ee }
            r7 = 5000(0x1388, float:7.006E-42)
            r5.setConnectTimeout(r7)     // Catch:{ Throwable -> 0x00f3, all -> 0x00ee }
            r5.setReadTimeout(r7)     // Catch:{ Throwable -> 0x00f3, all -> 0x00ee }
            java.io.OutputStream r7 = r5.getOutputStream()     // Catch:{ Throwable -> 0x00f3, all -> 0x00ee }
            java.lang.String r6 = r0.toString()     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r0 = "UTF-8"
            java.lang.String r6 = java.net.URLEncoder.encode(r6, r0)     // Catch:{ Throwable -> 0x00ec }
            byte[] r6 = r6.getBytes()     // Catch:{ Throwable -> 0x00ec }
            r7.write(r6)     // Catch:{ Throwable -> 0x00ec }
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00ec }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x00ec }
            java.io.InputStream r1 = r5.getInputStream()     // Catch:{ Throwable -> 0x00ec }
            r0.<init>(r1)     // Catch:{ Throwable -> 0x00ec }
            r6.<init>(r0)     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r0 = ""
        L_0x00be:
            java.lang.String r1 = r6.readLine()     // Catch:{ Throwable -> 0x00ec }
            if (r1 == 0) goto L_0x00d9
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ec }
            r2.<init>()     // Catch:{ Throwable -> 0x00ec }
            r2.append(r0)     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r0 = "\n"
            r2.append(r0)     // Catch:{ Throwable -> 0x00ec }
            r2.append(r1)     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r0 = r2.toString()     // Catch:{ Throwable -> 0x00ec }
            goto L_0x00be
        L_0x00d9:
            if (r5 == 0) goto L_0x00de
            r5.disconnect()
        L_0x00de:
            if (r7 == 0) goto L_0x0116
            r7.flush()     // Catch:{ IOException -> 0x00e7 }
            r7.close()     // Catch:{ IOException -> 0x00e7 }
            return
        L_0x00e7:
            r5 = move-exception
            r5.printStackTrace()
            return
        L_0x00ec:
            r6 = move-exception
            goto L_0x0101
        L_0x00ee:
            r7 = move-exception
            r3 = r7
            r7 = r6
            r6 = r3
            goto L_0x0118
        L_0x00f3:
            r7 = move-exception
            r3 = r7
            r7 = r6
            r6 = r3
            goto L_0x0101
        L_0x00f8:
            r5 = move-exception
            r7 = r6
            r6 = r5
            r5 = r7
            goto L_0x0118
        L_0x00fd:
            r5 = move-exception
            r7 = r6
            r6 = r5
            r5 = r7
        L_0x0101:
            r6.getStackTrace()     // Catch:{ all -> 0x0117 }
            if (r5 == 0) goto L_0x0109
            r5.disconnect()
        L_0x0109:
            if (r7 == 0) goto L_0x0116
            r7.flush()     // Catch:{ IOException -> 0x0112 }
            r7.close()     // Catch:{ IOException -> 0x0112 }
            return
        L_0x0112:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0116:
            return
        L_0x0117:
            r6 = move-exception
        L_0x0118:
            if (r5 == 0) goto L_0x011d
            r5.disconnect()
        L_0x011d:
            if (r7 == 0) goto L_0x012a
            r7.flush()     // Catch:{ IOException -> 0x0126 }
            r7.close()     // Catch:{ IOException -> 0x0126 }
            goto L_0x012a
        L_0x0126:
            r5 = move-exception
            r5.printStackTrace()
        L_0x012a:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.applink.c.b.b(java.lang.String, java.lang.String, java.lang.String):void");
    }

    private a c() {
        a aVar = new a();
        aVar.b = TBAppLinkUtil.BASE_URL;
        aVar.c = TBAppLinkUtil.TMALL_BASE_SCHEME_URL;
        return aVar;
    }

    public void a(a aVar) {
        try {
            new c(this, aVar).execute(new Void[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(String str, String str2, String str3) {
        if (!e.a(str)) {
            synchronized (this) {
                try {
                    new d(this, str, str2, str3).execute(new Void[0]);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }

    public boolean a(String str) {
        return !this.c.containsKey(str) || ((Long) this.c.get(str)).longValue() - System.currentTimeMillis() < 1000;
    }

    public a b() {
        if (a(this.a)) {
            a((a) null);
        }
        return this.d;
    }
}
