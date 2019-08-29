package com.alipay.sdk.auth;

import android.app.Activity;

final class i implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ StringBuilder b;
    final /* synthetic */ APAuthInfo c;

    i(Activity activity, StringBuilder sb, APAuthInfo aPAuthInfo) {
        this.a = activity;
        this.b = sb;
        this.c = aPAuthInfo;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00f1, code lost:
        if (com.alipay.sdk.auth.h.c != null) goto L_0x00f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00f3, code lost:
        com.alipay.sdk.auth.h.c.b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00fa, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x010e, code lost:
        if (com.alipay.sdk.auth.h.c == null) goto L_0x0111;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0111, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r5 = this;
            com.alipay.sdk.packet.impl.a r0 = new com.alipay.sdk.packet.impl.a     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r0.<init>()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r1 = 0
            android.app.Activity r2 = r5.a     // Catch:{ Throwable -> 0x0013 }
            java.lang.StringBuilder r3 = r5.b     // Catch:{ Throwable -> 0x0013 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0013 }
            com.alipay.sdk.packet.b r0 = r0.a(r2, r3)     // Catch:{ Throwable -> 0x0013 }
            goto L_0x0014
        L_0x0013:
            r0 = r1
        L_0x0014:
            com.alipay.sdk.widget.a r1 = com.alipay.sdk.auth.h.c     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            if (r1 == 0) goto L_0x0024
            com.alipay.sdk.widget.a r1 = com.alipay.sdk.auth.h.c     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r1.b()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.auth.h.c = null     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
        L_0x0024:
            if (r0 != 0) goto L_0x0057
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r0.<init>()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.auth.APAuthInfo r1 = r5.c     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = r1.getRedirectUri()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r0.append(r1)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = "?resultCode=202"
            r0.append(r1)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.auth.h.d = r0     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            android.app.Activity r0 = r5.a     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = com.alipay.sdk.auth.h.d     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.auth.h.a(r0, r1)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.widget.a r0 = com.alipay.sdk.auth.h.c
            if (r0 == 0) goto L_0x0056
            com.alipay.sdk.widget.a r0 = com.alipay.sdk.auth.h.c
            r0.b()
        L_0x0056:
            return
        L_0x0057:
            org.json.JSONObject r0 = r0.a()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = "form"
            org.json.JSONObject r0 = r0.optJSONObject(r1)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = "onload"
            org.json.JSONObject r0 = r0.optJSONObject(r1)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.util.List r0 = com.alipay.sdk.protocol.b.a(r0)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r1 = 0
            r2 = 0
        L_0x006d:
            int r3 = r0.size()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            if (r2 >= r3) goto L_0x0090
            java.lang.Object r3 = r0.get(r2)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.protocol.b r3 = (com.alipay.sdk.protocol.b) r3     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.protocol.a r3 = r3.a     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.protocol.a r4 = com.alipay.sdk.protocol.a.WapPay     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            if (r3 != r4) goto L_0x008d
            java.lang.Object r0 = r0.get(r2)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.protocol.b r0 = (com.alipay.sdk.protocol.b) r0     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String[] r0 = r0.b     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r0 = r0[r1]     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.auth.h.d = r0     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            goto L_0x0090
        L_0x008d:
            int r2 = r2 + 1
            goto L_0x006d
        L_0x0090:
            java.lang.String r0 = com.alipay.sdk.auth.h.d     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            if (r0 == 0) goto L_0x00cb
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r0.<init>()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.auth.APAuthInfo r1 = r5.c     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = r1.getRedirectUri()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r0.append(r1)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = "?resultCode=202"
            r0.append(r1)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.auth.h.d = r0     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            android.app.Activity r0 = r5.a     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = com.alipay.sdk.auth.h.d     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.auth.h.a(r0, r1)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.widget.a r0 = com.alipay.sdk.auth.h.c
            if (r0 == 0) goto L_0x00ca
            com.alipay.sdk.widget.a r0 = com.alipay.sdk.auth.h.c
            r0.b()
        L_0x00ca:
            return
        L_0x00cb:
            android.content.Intent r0 = new android.content.Intent     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            android.app.Activity r1 = r5.a     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.Class<com.alipay.sdk.auth.AuthActivity> r2 = com.alipay.sdk.auth.AuthActivity.class
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = "params"
            java.lang.String r2 = com.alipay.sdk.auth.h.d     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r0.putExtra(r1, r2)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r1 = "redirectUri"
            com.alipay.sdk.auth.APAuthInfo r2 = r5.c     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            java.lang.String r2 = r2.getRedirectUri()     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r0.putExtra(r1, r2)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            android.app.Activity r1 = r5.a     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            r1.startActivity(r0)     // Catch:{ Exception -> 0x010a, all -> 0x00fb }
            com.alipay.sdk.widget.a r0 = com.alipay.sdk.auth.h.c
            if (r0 == 0) goto L_0x0111
        L_0x00f3:
            com.alipay.sdk.widget.a r0 = com.alipay.sdk.auth.h.c
            r0.b()
            return
        L_0x00fb:
            r0 = move-exception
            com.alipay.sdk.widget.a r1 = com.alipay.sdk.auth.h.c
            if (r1 == 0) goto L_0x0109
            com.alipay.sdk.widget.a r1 = com.alipay.sdk.auth.h.c
            r1.b()
        L_0x0109:
            throw r0
        L_0x010a:
            com.alipay.sdk.widget.a r0 = com.alipay.sdk.auth.h.c
            if (r0 == 0) goto L_0x0111
            goto L_0x00f3
        L_0x0111:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.auth.i.run():void");
    }
}
