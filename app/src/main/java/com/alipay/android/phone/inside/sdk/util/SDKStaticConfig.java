package com.alipay.android.phone.inside.sdk.util;

import android.content.Context;
import android.text.TextUtils;
import java.util.Map;

public class SDKStaticConfig {
    static final String CONFIG_FILE = "alipay_inside_channel.config";
    public static final String KEY_COLLECT_ENV_INFO_DEGRADE = "collectEnvInfoDegrade";
    static boolean initialized = false;
    static Map<String, String> staticConfig;

    public static boolean isCollectEnvInfoDegrade(Context context) {
        String config = getConfig(context, KEY_COLLECT_ENV_INFO_DEGRADE);
        if (TextUtils.isEmpty(config)) {
            return false;
        }
        return TextUtils.equals(config, "true");
    }

    public static String getConfig(Context context, String str) {
        return getStaticConfig(context).get(str);
    }

    public static Map<String, String> getStaticConfig(Context context) {
        initStaticConfig(context);
        return staticConfig;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0078 A[SYNTHETIC, Splitter:B:36:0x0078] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x008e A[SYNTHETIC, Splitter:B:49:0x008e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void initStaticConfig(android.content.Context r7) {
        /*
            java.lang.Class<com.alipay.android.phone.inside.sdk.util.SDKStaticConfig> r0 = com.alipay.android.phone.inside.sdk.util.SDKStaticConfig.class
            monitor-enter(r0)
            boolean r1 = initialized     // Catch:{ all -> 0x009d }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)
            return
        L_0x0009:
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x009d }
            r1.<init>()     // Catch:{ all -> 0x009d }
            staticConfig = r1     // Catch:{ all -> 0x009d }
            android.content.res.Resources r7 = r7.getResources()     // Catch:{ all -> 0x009d }
            r1 = 0
            android.content.res.AssetManager r7 = r7.getAssets()     // Catch:{ Throwable -> 0x0066, all -> 0x0061 }
            java.lang.String r2 = "alipay_inside_channel.config"
            java.io.InputStream r7 = r7.open(r2)     // Catch:{ Throwable -> 0x0066, all -> 0x0061 }
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Throwable -> 0x005f }
            r1.<init>()     // Catch:{ Throwable -> 0x005f }
            r1.load(r7)     // Catch:{ Throwable -> 0x005f }
            java.util.Set r2 = r1.keySet()     // Catch:{ Throwable -> 0x005f }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ Throwable -> 0x005f }
        L_0x002f:
            boolean r3 = r2.hasNext()     // Catch:{ Throwable -> 0x005f }
            if (r3 == 0) goto L_0x0049
            java.lang.Object r3 = r2.next()     // Catch:{ Throwable -> 0x005f }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x005f }
            java.util.Map<java.lang.String, java.lang.String> r4 = staticConfig     // Catch:{ Throwable -> 0x005f }
            java.lang.String r5 = ""
            java.lang.String r5 = r1.getProperty(r3, r5)     // Catch:{ Throwable -> 0x005f }
            r4.put(r3, r5)     // Catch:{ Throwable -> 0x005f }
            goto L_0x002f
        L_0x0049:
            r1 = 1
            initialized = r1     // Catch:{ Throwable -> 0x005f }
            if (r7 == 0) goto L_0x0089
            r7.close()     // Catch:{ Throwable -> 0x0053 }
            monitor-exit(r0)
            return
        L_0x0053:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x009d }
            java.lang.String r2 = "inside"
            r1.c(r2, r7)     // Catch:{ all -> 0x009d }
            monitor-exit(r0)
            return
        L_0x005f:
            r1 = move-exception
            goto L_0x006a
        L_0x0061:
            r7 = move-exception
            r6 = r1
            r1 = r7
            r7 = r6
            goto L_0x008c
        L_0x0066:
            r7 = move-exception
            r6 = r1
            r1 = r7
            r7 = r6
        L_0x006a:
            com.alipay.android.phone.inside.log.api.ex.ExceptionLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.e()     // Catch:{ all -> 0x008b }
            java.lang.String r3 = "sdk"
            java.lang.String r4 = "SDKLoadConfigFileEx"
            r2.a(r3, r4, r1)     // Catch:{ all -> 0x008b }
            if (r7 == 0) goto L_0x0089
            r7.close()     // Catch:{ Throwable -> 0x007d }
            monitor-exit(r0)
            return
        L_0x007d:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x009d }
            java.lang.String r2 = "inside"
            r1.c(r2, r7)     // Catch:{ all -> 0x009d }
            monitor-exit(r0)
            return
        L_0x0089:
            monitor-exit(r0)
            return
        L_0x008b:
            r1 = move-exception
        L_0x008c:
            if (r7 == 0) goto L_0x009c
            r7.close()     // Catch:{ Throwable -> 0x0092 }
            goto L_0x009c
        L_0x0092:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x009d }
            java.lang.String r3 = "inside"
            r2.c(r3, r7)     // Catch:{ all -> 0x009d }
        L_0x009c:
            throw r1     // Catch:{ all -> 0x009d }
        L_0x009d:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.sdk.util.SDKStaticConfig.initStaticConfig(android.content.Context):void");
    }
}
