package com.alipay.mobile.tinyappcustom.a;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;

/* compiled from: CollectPaymentGuider */
public final class a {
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a() {
        /*
            r12 = 0
            java.lang.String r11 = "CollectPaymentGuider_VolumeIssue_Enable"
            java.lang.String r13 = ""
            java.lang.String r2 = com.alipay.mobile.monitor.util.MonitorUtils.getConfigValueByKeyOnBrandOrSDK(r11, r13)     // Catch:{ Throwable -> 0x009b }
            java.lang.String r11 = "true"
            boolean r11 = r11.equals(r2)     // Catch:{ Throwable -> 0x009b }
            if (r11 != 0) goto L_0x002b
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x009b }
            java.lang.String r13 = "CollectPaymentGuider"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x009b }
            java.lang.String r15 = "CollectPaymentGuider_VolumeIssue_Enable: "
            r14.<init>(r15)     // Catch:{ Throwable -> 0x009b }
            java.lang.StringBuilder r14 = r14.append(r2)     // Catch:{ Throwable -> 0x009b }
            java.lang.String r14 = r14.toString()     // Catch:{ Throwable -> 0x009b }
            r11.info(r13, r14)     // Catch:{ Throwable -> 0x009b }
            r11 = r12
        L_0x002a:
            return r11
        L_0x002b:
            com.alipay.mobile.framework.LauncherApplicationAgent r11 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ Throwable -> 0x009b }
            com.alipay.mobile.framework.MicroApplicationContext r11 = r11.getMicroApplicationContext()     // Catch:{ Throwable -> 0x009b }
            java.lang.Class<com.alipay.mobile.base.config.ConfigService> r13 = com.alipay.mobile.base.config.ConfigService.class
            java.lang.String r13 = r13.getName()     // Catch:{ Throwable -> 0x009b }
            java.lang.Object r11 = r11.findServiceByInterface(r13)     // Catch:{ Throwable -> 0x009b }
            com.alipay.mobile.base.config.ConfigService r11 = (com.alipay.mobile.base.config.ConfigService) r11     // Catch:{ Throwable -> 0x009b }
            java.lang.String r13 = "CollectPaymentGuider_VolumeIssue_ValidSec"
            java.lang.String r10 = r11.getConfig(r13)     // Catch:{ Throwable -> 0x009b }
            r8 = 10800(0x2a30, double:5.336E-320)
            boolean r11 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x009b }
            if (r11 != 0) goto L_0x0051
            long r8 = java.lang.Long.parseLong(r10)     // Catch:{ Throwable -> 0x0090 }
        L_0x0051:
            java.util.concurrent.TimeUnit r11 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ Throwable -> 0x009b }
            r14 = 1
            long r14 = r11.toMillis(r14)     // Catch:{ Throwable -> 0x009b }
            long r6 = r8 * r14
            long r4 = b()     // Catch:{ Throwable -> 0x009b }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x009b }
            long r14 = r14 - r4
            long r0 = java.lang.Math.abs(r14)     // Catch:{ Throwable -> 0x009b }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x009b }
            java.lang.String r13 = "CollectPaymentGuider"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x009b }
            java.lang.String r15 = "isProblemUserByVolumeIssue, deltaMillis: "
            r14.<init>(r15)     // Catch:{ Throwable -> 0x009b }
            java.lang.StringBuilder r14 = r14.append(r0)     // Catch:{ Throwable -> 0x009b }
            java.lang.String r15 = ", validMillis: "
            java.lang.StringBuilder r14 = r14.append(r15)     // Catch:{ Throwable -> 0x009b }
            java.lang.StringBuilder r14 = r14.append(r6)     // Catch:{ Throwable -> 0x009b }
            java.lang.String r14 = r14.toString()     // Catch:{ Throwable -> 0x009b }
            r11.info(r13, r14)     // Catch:{ Throwable -> 0x009b }
            int r11 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r11 >= 0) goto L_0x00a7
            r11 = 1
            goto L_0x002a
        L_0x0090:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x009b }
            java.lang.String r13 = "CollectPaymentGuider"
            r11.error(r13, r3)     // Catch:{ Throwable -> 0x009b }
            goto L_0x0051
        L_0x009b:
            r3 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r13 = "CollectPaymentGuider"
            r11.error(r13, r3)
            r11 = r12
            goto L_0x002a
        L_0x00a7:
            r11 = r12
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcustom.a.a.a():boolean");
    }

    private static long b() {
        long updateMillis = 0;
        String updateTime = LoggerFactory.getLogContext().getApplicationContext().getSharedPreferences("VolumeSilentFlag", 4).getString("updateTime", "");
        if (!TextUtils.isEmpty(updateTime)) {
            try {
                updateMillis = Long.parseLong(updateTime);
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) "CollectPaymentGuider", t);
            }
        }
        LoggerFactory.getTraceLogger().info("CollectPaymentGuider", "getVolumeIssueUpdateTime: " + updateMillis);
        return updateMillis;
    }
}
