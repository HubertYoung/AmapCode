package com.alipay.deviceid.module.x;

import java.io.File;
import org.json.JSONObject;

public final class x {
    private File a = null;
    private r b = null;

    public x(String str, r rVar) {
        this.a = new File(str);
        this.b = rVar;
    }

    private static String a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "id");
            jSONObject.put("error", str);
            return jSONObject.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00be, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a() {
        /*
            r7 = this;
            monitor-enter(r7)
            java.io.File r0 = r7.a     // Catch:{ all -> 0x00bf }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r7)
            return
        L_0x0007:
            java.io.File r0 = r7.a     // Catch:{ all -> 0x00bf }
            boolean r0 = r0.exists()     // Catch:{ all -> 0x00bf }
            if (r0 == 0) goto L_0x00bd
            java.io.File r0 = r7.a     // Catch:{ all -> 0x00bf }
            boolean r0 = r0.isDirectory()     // Catch:{ all -> 0x00bf }
            if (r0 == 0) goto L_0x00bd
            java.io.File r0 = r7.a     // Catch:{ all -> 0x00bf }
            java.lang.String[] r0 = r0.list()     // Catch:{ all -> 0x00bf }
            int r0 = r0.length     // Catch:{ all -> 0x00bf }
            if (r0 != 0) goto L_0x0022
            goto L_0x00bd
        L_0x0022:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x00bf }
            r0.<init>()     // Catch:{ all -> 0x00bf }
            java.io.File r1 = r7.a     // Catch:{ all -> 0x00bf }
            java.lang.String[] r1 = r1.list()     // Catch:{ all -> 0x00bf }
            int r2 = r1.length     // Catch:{ all -> 0x00bf }
            r3 = 0
            r4 = 0
        L_0x0030:
            if (r4 >= r2) goto L_0x003a
            r5 = r1[r4]     // Catch:{ all -> 0x00bf }
            r0.add(r5)     // Catch:{ all -> 0x00bf }
            int r4 = r4 + 1
            goto L_0x0030
        L_0x003a:
            java.util.Collections.sort(r0)     // Catch:{ all -> 0x00bf }
            int r1 = r0.size()     // Catch:{ all -> 0x00bf }
            int r1 = r1 + -1
            java.lang.Object r1 = r0.get(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x00bf }
            int r2 = r0.size()     // Catch:{ all -> 0x00bf }
            java.util.Calendar r4 = java.util.Calendar.getInstance()     // Catch:{ all -> 0x00bf }
            java.util.Date r4 = r4.getTime()     // Catch:{ all -> 0x00bf }
            java.text.SimpleDateFormat r5 = new java.text.SimpleDateFormat     // Catch:{ all -> 0x00bf }
            java.lang.String r6 = "yyyyMMdd"
            r5.<init>(r6)     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = r5.format(r4)     // Catch:{ all -> 0x00bf }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bf }
            r5.<init>()     // Catch:{ all -> 0x00bf }
            r5.append(r4)     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = ".log"
            r5.append(r4)     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x00bf }
            boolean r4 = r1.equals(r4)     // Catch:{ all -> 0x00bf }
            if (r4 == 0) goto L_0x008e
            int r1 = r0.size()     // Catch:{ all -> 0x00bf }
            r4 = 2
            if (r1 >= r4) goto L_0x0081
            monitor-exit(r7)
            return
        L_0x0081:
            int r1 = r0.size()     // Catch:{ all -> 0x00bf }
            int r1 = r1 - r4
            java.lang.Object r1 = r0.get(r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x00bf }
            int r2 = r2 + -1
        L_0x008e:
            java.io.File r4 = r7.a     // Catch:{ all -> 0x00bf }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = com.alipay.deviceid.module.x.f.a(r4, r1)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = a(r1)     // Catch:{ all -> 0x00bf }
            com.alipay.deviceid.module.x.r r4 = r7.b     // Catch:{ all -> 0x00bf }
            boolean r1 = r4.a(r1)     // Catch:{ all -> 0x00bf }
            if (r1 != 0) goto L_0x00a6
            int r2 = r2 + -1
        L_0x00a6:
            if (r3 >= r2) goto L_0x00bb
            java.lang.Object r1 = r0.get(r3)     // Catch:{ all -> 0x00bf }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x00bf }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x00bf }
            java.io.File r5 = r7.a     // Catch:{ all -> 0x00bf }
            r4.<init>(r5, r1)     // Catch:{ all -> 0x00bf }
            r4.delete()     // Catch:{ all -> 0x00bf }
            int r3 = r3 + 1
            goto L_0x00a6
        L_0x00bb:
            monitor-exit(r7)
            return
        L_0x00bd:
            monitor-exit(r7)
            return
        L_0x00bf:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.x.x.a():void");
    }
}
