package com.alipay.mobile.android.verify.bridge;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.logger.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: Utils */
public class h {
    private static final ExecutorService a = Executors.newCachedThreadPool();

    public static void a(Runnable runnable) {
        try {
            a.submit(runnable);
        } catch (Exception e) {
            Logger.t("Utils").e(e, "execute runnable got error", new Object[0]);
        }
    }

    public static JSONObject a(String str) {
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str)) {
            Logger.t("Utils").w("empty json string", new Object[0]);
            return null;
        }
        try {
            jSONObject = JSON.parseObject(str);
        } catch (Exception e) {
            Logger.t("Utils").e(e, "parse object error", new Object[0]);
            jSONObject = null;
        }
        return jSONObject;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0066 A[SYNTHETIC, Splitter:B:26:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007c A[SYNTHETIC, Splitter:B:33:0x007c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(android.content.Context r6) {
        /*
            r0 = 0
            r1 = 0
            android.content.res.AssetManager r6 = r6.getAssets()     // Catch:{ IOException -> 0x0051, all -> 0x004c }
            java.lang.String r2 = "bridge/bridge.js"
            java.io.InputStream r6 = r6.open(r2)     // Catch:{ IOException -> 0x0051, all -> 0x004c }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ IOException -> 0x004a }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x004a }
            r2.<init>(r6)     // Catch:{ IOException -> 0x004a }
            r1.<init>(r2)     // Catch:{ IOException -> 0x004a }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x004a }
            r2.<init>()     // Catch:{ IOException -> 0x004a }
        L_0x001b:
            java.lang.String r3 = r1.readLine()     // Catch:{ IOException -> 0x004a }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ IOException -> 0x004a }
            if (r4 != 0) goto L_0x0028
            r2.append(r3)     // Catch:{ IOException -> 0x004a }
        L_0x0028:
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ IOException -> 0x004a }
            if (r3 == 0) goto L_0x001b
            r1.close()     // Catch:{ IOException -> 0x004a }
            java.lang.String r1 = r2.toString()     // Catch:{ IOException -> 0x004a }
            if (r6 == 0) goto L_0x0049
            r6.close()     // Catch:{ IOException -> 0x003b }
            goto L_0x0049
        L_0x003b:
            r6 = move-exception
            java.lang.String r2 = "Utils"
            com.alipay.mobile.android.verify.logger.Printer r2 = com.alipay.mobile.android.verify.logger.Logger.t(r2)
            java.lang.String r3 = "close assets stream got error"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r2.e(r6, r3, r0)
        L_0x0049:
            return r1
        L_0x004a:
            r1 = move-exception
            goto L_0x0055
        L_0x004c:
            r6 = move-exception
            r5 = r1
            r1 = r6
            r6 = r5
            goto L_0x007a
        L_0x0051:
            r6 = move-exception
            r5 = r1
            r1 = r6
            r6 = r5
        L_0x0055:
            java.lang.String r2 = "Utils"
            com.alipay.mobile.android.verify.logger.Printer r2 = com.alipay.mobile.android.verify.logger.Logger.t(r2)     // Catch:{ all -> 0x0079 }
            java.lang.String r3 = "load bridge from assets got error"
            java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ all -> 0x0079 }
            r2.e(r1, r3, r4)     // Catch:{ all -> 0x0079 }
            java.lang.String r1 = ""
            if (r6 == 0) goto L_0x0078
            r6.close()     // Catch:{ IOException -> 0x006a }
            goto L_0x0078
        L_0x006a:
            r6 = move-exception
            java.lang.String r2 = "Utils"
            com.alipay.mobile.android.verify.logger.Printer r2 = com.alipay.mobile.android.verify.logger.Logger.t(r2)
            java.lang.String r3 = "close assets stream got error"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r2.e(r6, r3, r0)
        L_0x0078:
            return r1
        L_0x0079:
            r1 = move-exception
        L_0x007a:
            if (r6 == 0) goto L_0x008e
            r6.close()     // Catch:{ IOException -> 0x0080 }
            goto L_0x008e
        L_0x0080:
            r6 = move-exception
            java.lang.String r2 = "Utils"
            com.alipay.mobile.android.verify.logger.Printer r2 = com.alipay.mobile.android.verify.logger.Logger.t(r2)
            java.lang.String r3 = "close assets stream got error"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r2.e(r6, r3, r0)
        L_0x008e:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.android.verify.bridge.h.a(android.content.Context):java.lang.String");
    }
}
