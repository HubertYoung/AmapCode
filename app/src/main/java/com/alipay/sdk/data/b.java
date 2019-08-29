package com.alipay.sdk.data;

import android.content.Context;

final class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ a b;

    b(a aVar, Context context) {
        this.b = aVar;
        this.a = context;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x003d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
            com.alipay.sdk.packet.impl.b r0 = new com.alipay.sdk.packet.impl.b     // Catch:{ Throwable -> 0x0067 }
            r0.<init>()     // Catch:{ Throwable -> 0x0067 }
            android.content.Context r1 = r4.a     // Catch:{ Throwable -> 0x0067 }
            com.alipay.sdk.packet.b r0 = r0.a(r1)     // Catch:{ Throwable -> 0x0067 }
            if (r0 == 0) goto L_0x0066
            com.alipay.sdk.data.a r1 = r4.b     // Catch:{ Throwable -> 0x0067 }
            java.lang.String r0 = r0.b     // Catch:{ Throwable -> 0x0067 }
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0067 }
            if (r2 != 0) goto L_0x003d
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x003d }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x003d }
            java.lang.String r0 = "st_sdk_config"
            org.json.JSONObject r0 = r2.optJSONObject(r0)     // Catch:{ Throwable -> 0x003d }
            java.lang.String r2 = "timeout"
            r3 = 3500(0xdac, float:4.905E-42)
            int r2 = r0.optInt(r2, r3)     // Catch:{ Throwable -> 0x003d }
            r1.i = r2     // Catch:{ Throwable -> 0x003d }
            java.lang.String r2 = "tbreturl"
            java.lang.String r3 = "http://h5.m.taobao.com/trade/paySuccess.html?bizOrderId=$OrderId$&"
            java.lang.String r0 = r0.optString(r2, r3)     // Catch:{ Throwable -> 0x003d }
            java.lang.String r0 = r0.trim()     // Catch:{ Throwable -> 0x003d }
            r1.j = r0     // Catch:{ Throwable -> 0x003d }
        L_0x003d:
            com.alipay.sdk.data.a r0 = r4.b     // Catch:{ Throwable -> 0x0067 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0066 }
            r1.<init>()     // Catch:{ Exception -> 0x0066 }
            java.lang.String r2 = "timeout"
            int r3 = r0.a()     // Catch:{ Exception -> 0x0066 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x0066 }
            java.lang.String r2 = "tbreturl"
            java.lang.String r0 = r0.j     // Catch:{ Exception -> 0x0066 }
            r1.put(r2, r0)     // Catch:{ Exception -> 0x0066 }
            com.alipay.sdk.sys.b r0 = com.alipay.sdk.sys.b.a()     // Catch:{ Exception -> 0x0066 }
            android.content.Context r0 = r0.a     // Catch:{ Exception -> 0x0066 }
            java.lang.String r2 = "alipay_cashier_dynamic_config"
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0066 }
            com.alipay.sdk.util.i.a(r0, r2, r1)     // Catch:{ Exception -> 0x0066 }
            return
        L_0x0066:
            return
        L_0x0067:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.data.b.run():void");
    }
}
