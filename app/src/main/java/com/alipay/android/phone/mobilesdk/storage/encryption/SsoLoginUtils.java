package com.alipay.android.phone.mobilesdk.storage.encryption;

import android.content.Context;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent.IInitFinishListener;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class SsoLoginUtils {
    public static final String TAOBAO_SSO_MTOP_APP_KEY_ONLINE = "12501616";
    private static volatile boolean isFirst = true;

    public static String getAppKey(Context context) {
        return null;
    }

    public static void init(Context context) {
        final Context mcContext = context;
        if (isFirst) {
            try {
                SecurityGuardManager.getInitializer().registerInitFinishListener(new IInitFinishListener() {
                    public final void onSuccess() {
                        LoggerFactory.getTraceLogger().debug("xxxxxx", "so load Success!!!");
                    }

                    public final void onError() {
                        LoggerFactory.getTraceLogger().debug("xxxxxx", "so load faild!!!");
                    }
                });
                new Thread(new Runnable() {
                    public final void run() {
                        try {
                            SecurityGuardManager.getInstance(mcContext);
                        } catch (Throwable e) {
                            LoggerFactory.getTraceLogger().warn((String) "SsoLoginUtils", e);
                        }
                    }
                }).start();
                isFirst = false;
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().warn((String) "SsoLoginUtils", e);
                isFirst = true;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x010f A[SYNTHETIC, Splitter:B:26:0x010f] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0114 A[SYNTHETIC, Splitter:B:29:0x0114] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void copyLibrary(android.content.Context r8, java.lang.String r9) {
        /*
            java.io.File r3 = new java.io.File
            java.lang.String r5 = "plugins_lib"
            r6 = 0
            java.io.File r5 = r8.getDir(r5, r6)
            java.lang.String r5 = r5.getAbsolutePath()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "lib"
            r6.<init>(r7)
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.String r7 = ".so"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r3.<init>(r5, r6)
            boolean r5 = r3.exists()
            if (r5 == 0) goto L_0x002c
        L_0x002b:
            return
        L_0x002c:
            r0 = 0
            java.lang.Class r5 = r8.getClass()
            java.lang.ClassLoader r5 = r5.getClassLoader()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "lib"
            r6.<init>(r7)
            java.lang.String r7 = java.io.File.separator
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = android.os.Build.CPU_ABI
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = java.io.File.separator
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = "lib"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.String r7 = ".so"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            java.io.InputStream r4 = r5.getResourceAsStream(r6)
            if (r4 != 0) goto L_0x00a1
            java.lang.Class r5 = r8.getClass()     // Catch:{ Exception -> 0x0105 }
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "lib"
            r6.<init>(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = android.os.Build.CPU_ABI2     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "lib"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = r6.append(r9)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = ".so"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0105 }
            java.io.InputStream r4 = r5.getResourceAsStream(r6)     // Catch:{ Exception -> 0x0105 }
        L_0x00a1:
            if (r4 != 0) goto L_0x00dc
            java.lang.Class r5 = r8.getClass()     // Catch:{ Exception -> 0x0105 }
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "lib"
            r6.<init>(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "armeabi"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "lib"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r6 = r6.append(r9)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = ".so"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0105 }
            java.io.InputStream r4 = r5.getResourceAsStream(r6)     // Catch:{ Exception -> 0x0105 }
        L_0x00dc:
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0105 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x0105 }
            com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.copyFile(r1, r3)     // Catch:{ Exception -> 0x0131, all -> 0x012e }
            r1.close()     // Catch:{ IOException -> 0x00fa }
        L_0x00e7:
            if (r4 == 0) goto L_0x002b
            r4.close()     // Catch:{ IOException -> 0x00ee }
            goto L_0x002b
        L_0x00ee:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r6 = "SsoLoginUtils"
            r5.warn(r6, r2)
            goto L_0x002b
        L_0x00fa:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r6 = "SsoLoginUtils"
            r5.warn(r6, r2)
            goto L_0x00e7
        L_0x0105:
            r2 = move-exception
        L_0x0106:
            java.lang.RuntimeException r5 = new java.lang.RuntimeException     // Catch:{ all -> 0x010c }
            r5.<init>(r2)     // Catch:{ all -> 0x010c }
            throw r5     // Catch:{ all -> 0x010c }
        L_0x010c:
            r5 = move-exception
        L_0x010d:
            if (r0 == 0) goto L_0x0112
            r0.close()     // Catch:{ IOException -> 0x0118 }
        L_0x0112:
            if (r4 == 0) goto L_0x0117
            r4.close()     // Catch:{ IOException -> 0x0123 }
        L_0x0117:
            throw r5
        L_0x0118:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "SsoLoginUtils"
            r6.warn(r7, r2)
            goto L_0x0112
        L_0x0123:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "SsoLoginUtils"
            r6.warn(r7, r2)
            goto L_0x0117
        L_0x012e:
            r5 = move-exception
            r0 = r1
            goto L_0x010d
        L_0x0131:
            r2 = move-exception
            r0 = r1
            goto L_0x0106
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.storage.encryption.SsoLoginUtils.copyLibrary(android.content.Context, java.lang.String):void");
    }
}
