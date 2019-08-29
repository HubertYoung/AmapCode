package com.alipay.android.phone.inside.commonbiz.report;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.security.encryption.TaobaoSecurityEncryptor;

public final class ReportConfig {
    private static ReportConfig a;
    private final Context b;

    public static ReportConfig a(Context context) {
        synchronized (ReportConfig.class) {
            if (a == null) {
                a = new ReportConfig(context);
            }
        }
        return a;
    }

    private ReportConfig(Context context) {
        this.b = context;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:12|13|14) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        com.alipay.android.phone.inside.log.api.LoggerFactory.f().c((java.lang.String) "ReportConfig", (java.lang.String) "set device info config exception");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00c7, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x00bb */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r9, java.lang.String r10, java.lang.String r11) {
        /*
            r8 = this;
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r1 = "ReportConfig"
            java.lang.String r2 = "clientReportConfig："
            java.lang.String r3 = java.lang.String.valueOf(r11)
            java.lang.String r2 = r2.concat(r3)
            r0.a(r1, r2)
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x00d5
            java.lang.String r0 = ":"
            boolean r0 = r11.contains(r0)
            if (r0 == 0) goto L_0x00d5
            java.lang.String r0 = "0:0"
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x002b
            goto L_0x00d5
        L_0x002b:
            java.lang.String r0 = ":"
            java.lang.String[] r11 = r11.split(r0)
            r0 = 0
            r1 = r11[r0]     // Catch:{ Exception -> 0x00c8 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x00c8 }
            int r1 = r1.intValue()     // Catch:{ Exception -> 0x00c8 }
            r2 = 1
            r11 = r11[r2]     // Catch:{ Exception -> 0x00c8 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x00c8 }
            int r11 = r11.intValue()     // Catch:{ Exception -> 0x00c8 }
            java.util.Calendar r2 = java.util.Calendar.getInstance()     // Catch:{ Exception -> 0x00c8 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00c8 }
            r2.setTimeInMillis(r3)     // Catch:{ Exception -> 0x00c8 }
            r3 = 12
            r2.add(r3, r1)     // Catch:{ Exception -> 0x00c8 }
            long r4 = r2.getTimeInMillis()     // Catch:{ Exception -> 0x00c8 }
            r2.add(r3, r11)     // Catch:{ Exception -> 0x00c8 }
            long r1 = r2.getTimeInMillis()     // Catch:{ Exception -> 0x00c8 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r11 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Exception -> 0x00c8 }
            java.lang.String r3 = "ReportConfig"
            java.lang.String r6 = "server disallow report alive time start："
            java.lang.String r7 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x00c8 }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ Exception -> 0x00c8 }
            r11.a(r3, r6)     // Catch:{ Exception -> 0x00c8 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r11 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Exception -> 0x00c8 }
            java.lang.String r3 = "ReportConfig"
            java.lang.String r6 = "erver disallow report alive time end："
            java.lang.String r7 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x00c8 }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ Exception -> 0x00c8 }
            r11.a(r3, r6)     // Catch:{ Exception -> 0x00c8 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bb }
            r11.<init>(r10)     // Catch:{ Exception -> 0x00bb }
            r11.append(r4)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r10 = ","
            r11.append(r10)     // Catch:{ Exception -> 0x00bb }
            r11.append(r1)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r10 = r11.toString()     // Catch:{ Exception -> 0x00bb }
            android.content.ContextWrapper r11 = new android.content.ContextWrapper     // Catch:{ Exception -> 0x00bb }
            android.content.Context r1 = r8.b     // Catch:{ Exception -> 0x00bb }
            r11.<init>(r1)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r10 = com.alipay.android.phone.inside.security.encryption.TaobaoSecurityEncryptor.a(r11, r10)     // Catch:{ Exception -> 0x00bb }
            android.content.Context r11 = r8.b     // Catch:{ Exception -> 0x00bb }
            java.io.FileOutputStream r9 = r11.openFileOutput(r9, r0)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r11 = "UTF-8"
            byte[] r10 = r10.getBytes(r11)     // Catch:{ Exception -> 0x00bb }
            r9.write(r10)     // Catch:{ Exception -> 0x00bb }
            r9.flush()     // Catch:{ Exception -> 0x00bb }
            return
        L_0x00bb:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r9 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Exception -> 0x00c8 }
            java.lang.String r10 = "ReportConfig"
            java.lang.String r11 = "set device info config exception"
            r9.c(r10, r11)     // Catch:{ Exception -> 0x00c8 }
            return
        L_0x00c8:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r9 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r10 = "ReportConfig"
            java.lang.String r11 = "report time format invalid"
            r9.c(r10, r11)
            return
        L_0x00d5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.commonbiz.report.ReportConfig.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public final boolean a(String str, String str2) {
        try {
            String a2 = a(str);
            if (TextUtils.isEmpty(a2) || !a2.startsWith(str2) || a2.indexOf(44) <= 0) {
                return true;
            }
            String[] split = a2.substring(str2.length()).split(",");
            long longValue = Long.valueOf(split[0]).longValue();
            long longValue2 = Long.valueOf(split[1]).longValue();
            long currentTimeMillis = System.currentTimeMillis();
            LoggerFactory.f().a((String) "ReportConfig", "disallow report alive start time：".concat(String.valueOf(longValue)));
            LoggerFactory.f().a((String) "ReportConfig", "disallow report alive end time：".concat(String.valueOf(longValue2)));
            LoggerFactory.f().a((String) "ReportConfig", "now：".concat(String.valueOf(currentTimeMillis)));
            if (currentTimeMillis >= longValue || currentTimeMillis <= longValue2) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            LoggerFactory.f().c((String) "ReportConfig", "no report config of ".concat(String.valueOf(str2)));
            return true;
        }
    }

    private String a(String str) {
        try {
            byte[] bArr = new byte[256];
            return TaobaoSecurityEncryptor.b(new ContextWrapper(this.b), new String(bArr, 0, this.b.openFileInput(str).read(bArr), "UTF-8"));
        } catch (Exception unused) {
            LoggerFactory.f().c((String) "ReportConfig", "can not find config in file:".concat(String.valueOf(str)));
            return null;
        }
    }
}
