package com.autonavi.common.tool;

import android.app.Application;
import android.text.TextUtils;
import com.alibaba.appmonitor.offline.TempEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

public class ModuleCollector {
    public static final String CONTROLLER_PROXY_PREFIX = "com.autonavi.plugin.core.controller.ControllerProxy";

    public static String deleteModuleIfControllerProxyError(String str, Application application) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File dir = application.getDir(TempEvent.TAG_MODULE, 0);
        if (dir == null || !dir.exists()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                } else if (readLine.contains(CONTROLLER_PROXY_PREFIX)) {
                    String pluginInfoCollectAndDeletePlugins = pluginInfoCollectAndDeletePlugins(dir);
                    if (!TextUtils.isEmpty(pluginInfoCollectAndDeletePlugins)) {
                        sb.append(pluginInfoCollectAndDeletePlugins);
                    }
                }
            }
            return sb.toString();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static String deleteModuleIfError(String str, Application application) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        CrashLogUtil.getControler();
        return null;
    }

    public static String getInfo(Throwable th, Application application) {
        StringBuilder sb = new StringBuilder();
        try {
            application.getDir(TempEvent.TAG_MODULE, 0);
            CrashLogUtil.getControler();
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x0087 */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0051 A[SYNTHETIC, Splitter:B:22:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x005c A[SYNTHETIC, Splitter:B:29:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x006e A[Catch:{ Throwable -> 0x0087 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String pluginInfoCollectAndDeletePlugins(java.io.File r6) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x00a3
            boolean r1 = r6.exists()
            if (r1 != 0) goto L_0x000b
            goto L_0x00a3
        L_0x000b:
            bmt r1 = com.autonavi.common.tool.CrashLogUtil.getControler()
            java.util.List r1 = r1.A()
            if (r1 == 0) goto L_0x00a2
            int r2 = r1.size()
            if (r2 != 0) goto L_0x001d
            goto L_0x00a2
        L_0x001d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "\nModuleInfo:\n"
            r2.append(r3)
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x005a, all -> 0x004d }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x005a, all -> 0x004d }
            java.lang.String r5 = "version"
            r4.<init>(r6, r5)     // Catch:{ Throwable -> 0x005a, all -> 0x004d }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x005a, all -> 0x004d }
            java.lang.String r0 = "\tversionMark:"
            r2.append(r0)     // Catch:{ Throwable -> 0x004b, all -> 0x0049 }
            java.lang.String r0 = defpackage.bmu.a(r3)     // Catch:{ Throwable -> 0x004b, all -> 0x0049 }
            r2.append(r0)     // Catch:{ Throwable -> 0x004b, all -> 0x0049 }
            java.lang.String r0 = "\n"
            r2.append(r0)     // Catch:{ Throwable -> 0x004b, all -> 0x0049 }
            r3.close()     // Catch:{ IOException -> 0x0060 }
            goto L_0x0064
        L_0x0049:
            r6 = move-exception
            goto L_0x004f
        L_0x004b:
            r0 = r3
            goto L_0x005a
        L_0x004d:
            r6 = move-exception
            r3 = r0
        L_0x004f:
            if (r3 == 0) goto L_0x0059
            r3.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0059
        L_0x0055:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0059:
            throw r6
        L_0x005a:
            if (r0 == 0) goto L_0x0064
            r0.close()     // Catch:{ IOException -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0064:
            java.util.Iterator r0 = r1.iterator()     // Catch:{ Throwable -> 0x0087 }
        L_0x0068:
            boolean r1 = r0.hasNext()     // Catch:{ Throwable -> 0x0087 }
            if (r1 == 0) goto L_0x0087
            java.lang.Object r1 = r0.next()     // Catch:{ Throwable -> 0x0087 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x0087 }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0087 }
            r3.<init>(r6, r1)     // Catch:{ Throwable -> 0x0087 }
            java.lang.String r1 = getModuleFileInfo(r3)     // Catch:{ Throwable -> 0x0087 }
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0087 }
            if (r3 != 0) goto L_0x0068
            r2.append(r1)     // Catch:{ Throwable -> 0x0087 }
            goto L_0x0068
        L_0x0087:
            defpackage.bmx.a(r6)     // Catch:{ Throwable -> 0x008a }
        L_0x008a:
            java.lang.String r0 = "\t deleteDir result:"
            r2.append(r0)
            boolean r6 = r6.exists()
            r6 = r6 ^ 1
            r2.append(r6)
            java.lang.String r6 = "\n"
            r2.append(r6)
            java.lang.String r6 = r2.toString()
            return r6
        L_0x00a2:
            return r0
        L_0x00a3:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.ModuleCollector.pluginInfoCollectAndDeletePlugins(java.io.File):java.lang.String");
    }

    public static String getModuleFileInfo(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (File file2 : listFiles) {
            sb.append("\tpath:");
            sb.append(file2.getPath());
            sb.append(" lastModified:");
            sb.append(file2.lastModified());
            sb.append(" size:");
            sb.append(file2.length());
            if (file2.getPath().endsWith(".apk")) {
                sb.append(" md5:");
                sb.append(bmq.a(file2));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
