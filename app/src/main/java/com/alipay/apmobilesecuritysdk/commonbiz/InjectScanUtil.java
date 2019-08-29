package com.alipay.apmobilesecuritysdk.commonbiz;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.commonbiz.ApplistUtil.AppListConfig;
import com.alipay.apmobilesecuritysdk.commonbiz.monitor.LogAgent;
import com.alipay.security.mobile.module.crypto.Base64Util;
import com.alipay.security.mobile.module.crypto.Hex;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class InjectScanUtil {
    private static TraceLogger a = LoggerFactory.f();
    private static String b = "/proc/";
    private static String c = "/maps";
    private static InjectScanResult d;

    public static class InjectScanResult {
        public String a;
        public String b;

        public InjectScanResult(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }

    public static InjectScanResult a(Context context) {
        Object obj;
        if (d == null) {
            long currentTimeMillis = System.currentTimeMillis();
            AppListConfig d2 = ApplistUtil.d(context);
            InjectScanResult injectScanResult = null;
            if (d2 == null) {
                LogAgent.b("inject_detect", "blacklist config is null.");
            } else {
                String str = d2.a;
                List<String> list = d2.c;
                if (str == null || str.length() == 0 || list == null || list.size() == 0) {
                    StringBuilder sb = new StringBuilder("blacklist version = ");
                    if (str == null) {
                        str = "null";
                    }
                    sb.append(str);
                    sb.append(", blackist = ");
                    if (list == null) {
                        obj = "null";
                    } else {
                        obj = Integer.valueOf(list.size());
                    }
                    sb.append(obj);
                    LogAgent.b("inject_detect", sb.toString());
                } else {
                    Set<String> b2 = b(context);
                    if (b2 == null) {
                        LogAgent.b("inject_detect", "Scan and get unexpected plugins error.");
                    } else {
                        byte[] bArr = new byte[((list.size() / 8) + 1)];
                        Arrays.fill(bArr, 0);
                        for (int i = 0; i < list.size(); i++) {
                            String str2 = list.get(i);
                            int i2 = i / 8;
                            byte b3 = bArr[i2];
                            if (b2.contains(str2)) {
                                b3 |= 128 >> (i % 8);
                                b2.remove(str2);
                            }
                            bArr[i2] = (byte) (b3 & 255);
                        }
                        if (b2.size() > 0) {
                            StringBuilder sb2 = new StringBuilder();
                            for (String append : b2) {
                                sb2.append(append);
                                sb2.append(",");
                            }
                            int length = sb2.length();
                            if (length > 0) {
                                int i3 = length - 1;
                                if (sb2.charAt(i3) == ',') {
                                    sb2.deleteCharAt(i3);
                                }
                            }
                            LogAgent.a((String) "inject_detect", sb2.toString());
                        }
                        injectScanResult = new InjectScanResult(str, new String(Hex.encode(bArr)));
                    }
                }
            }
            d = injectScanResult;
            if (injectScanResult != null) {
                LogAgent.d(d.a, d.b);
            }
            TraceLogger traceLogger = a;
            StringBuilder sb3 = new StringBuilder("scanAndGetInjectBitmaps cost ");
            sb3.append(System.currentTimeMillis() - currentTimeMillis);
            sb3.append(" ms. result = ");
            sb3.append(d.b);
            traceLogger.b((String) "ApplistUtil", sb3.toString());
        }
        return d;
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d7 A[Catch:{ Throwable -> 0x0100, all -> 0x00fe }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00dc A[Catch:{ Throwable -> 0x0100, all -> 0x00fe }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0106 A[SYNTHETIC, Splitter:B:57:0x0106] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x010c A[SYNTHETIC, Splitter:B:63:0x010c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.Set<java.lang.String> b(android.content.Context r10) {
        /*
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            r1 = 0
            if (r10 != 0) goto L_0x0010
            java.lang.String r10 = "inject_detect"
            java.lang.String r0 = "context is null."
            com.alipay.apmobilesecuritysdk.commonbiz.monitor.LogAgent.b(r10, r0)
            return r1
        L_0x0010:
            android.content.Context r10 = r10.getApplicationContext()
            java.lang.String r10 = r10.getPackageResourcePath()
            boolean r2 = android.text.TextUtils.isEmpty(r10)
            if (r2 == 0) goto L_0x0026
            java.lang.String r10 = "inject_detect"
            java.lang.String r0 = "get apk path returned null."
            com.alipay.apmobilesecuritysdk.commonbiz.monitor.LogAgent.b(r10, r0)
            return r1
        L_0x0026:
            java.lang.String r2 = "/"
            int r2 = r10.lastIndexOf(r2)
            if (r2 >= 0) goto L_0x0036
            java.lang.String r10 = "inject_detect"
            java.lang.String r0 = "not found slash in the apk path string."
            com.alipay.apmobilesecuritysdk.commonbiz.monitor.LogAgent.b(r10, r0)
            return r1
        L_0x0036:
            int r2 = r2 + 1
            r3 = 0
            java.lang.String r2 = r10.substring(r3, r2)
            int r4 = r2.length()
            int r5 = a()
            if (r5 >= 0) goto L_0x004f
            java.lang.String r10 = "inject_detect"
            java.lang.String r0 = "get process id error."
            com.alipay.apmobilesecuritysdk.commonbiz.monitor.LogAgent.b(r10, r0)
            return r1
        L_0x004f:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r6 = a
            java.lang.String r7 = "ApplistUtil"
            java.lang.String r8 = "search Key = "
            java.lang.String r9 = java.lang.String.valueOf(r2)
            java.lang.String r8 = r8.concat(r9)
            r6.b(r7, r8)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = b
            r6.append(r7)
            r6.append(r5)
            java.lang.String r5 = c
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            java.io.LineNumberReader r6 = new java.io.LineNumberReader     // Catch:{ Throwable -> 0x010a, all -> 0x0102 }
            java.io.InputStreamReader r7 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x010a, all -> 0x0102 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x010a, all -> 0x0102 }
            java.io.File r9 = new java.io.File     // Catch:{ Throwable -> 0x010a, all -> 0x0102 }
            r9.<init>(r5)     // Catch:{ Throwable -> 0x010a, all -> 0x0102 }
            r8.<init>(r9)     // Catch:{ Throwable -> 0x010a, all -> 0x0102 }
            r7.<init>(r8)     // Catch:{ Throwable -> 0x010a, all -> 0x0102 }
            r6.<init>(r7)     // Catch:{ Throwable -> 0x010a, all -> 0x0102 }
        L_0x008b:
            java.lang.String r5 = r6.readLine()     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r5 == 0) goto L_0x00fa
            if (r5 == 0) goto L_0x00b0
            java.lang.String r5 = r5.trim()     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r5 == 0) goto L_0x00b0
            int r7 = r5.length()     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r7 <= 0) goto L_0x00b0
            java.lang.String r7 = " "
            java.lang.String[] r5 = r5.split(r7)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r5 == 0) goto L_0x00b0
            int r7 = r5.length     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r7 <= 0) goto L_0x00b0
            int r7 = r5.length     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            int r7 = r7 + -1
            r5 = r5[r7]     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            goto L_0x00b1
        L_0x00b0:
            r5 = r1
        L_0x00b1:
            if (r5 == 0) goto L_0x008b
            boolean r7 = r5.startsWith(r2)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r7 == 0) goto L_0x008b
            boolean r7 = r10.equals(r5)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r7 != 0) goto L_0x008b
            int r7 = r5.length()     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            r8 = 4
            if (r7 <= r8) goto L_0x008b
            int r7 = r5.length()     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            java.lang.String r9 = ".apk"
            int r7 = r7 - r8
            java.lang.String r8 = r5.substring(r7)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            boolean r8 = r9.equals(r8)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r8 == 0) goto L_0x00dc
            java.lang.String r5 = r5.substring(r4, r7)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            goto L_0x00e0
        L_0x00dc:
            java.lang.String r5 = r5.substring(r4)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
        L_0x00e0:
            java.lang.String r7 = "-"
            java.lang.String[] r5 = r5.split(r7)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r5 == 0) goto L_0x008b
            int r7 = r5.length     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            if (r7 <= 0) goto L_0x008b
            r7 = r5[r3]     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            r0.add(r7)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r7 = a     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            java.lang.String r8 = "ApplistUtil"
            r5 = r5[r3]     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            r7.b(r8, r5)     // Catch:{ Throwable -> 0x0100, all -> 0x00fe }
            goto L_0x008b
        L_0x00fa:
            r6.close()     // Catch:{ Throwable -> 0x010f }
            goto L_0x010f
        L_0x00fe:
            r10 = move-exception
            goto L_0x0104
        L_0x0100:
            r1 = r6
            goto L_0x010a
        L_0x0102:
            r10 = move-exception
            r6 = r1
        L_0x0104:
            if (r6 == 0) goto L_0x0109
            r6.close()     // Catch:{ Throwable -> 0x0109 }
        L_0x0109:
            throw r10
        L_0x010a:
            if (r1 == 0) goto L_0x010f
            r1.close()     // Catch:{ Throwable -> 0x010f }
        L_0x010f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.commonbiz.InjectScanUtil.b(android.content.Context):java.util.Set");
    }

    private static int a() {
        try {
            return ((Integer) Class.forName(new String(Base64Util.decode("YW5kcm9pZC5vcy5Qcm9jZXNz"))).getDeclaredMethod(new String(Base64Util.decode("bXlQaWQ=")), new Class[0]).invoke(null, new Object[0])).intValue();
        } catch (Throwable unused) {
            return -1;
        }
    }
}
