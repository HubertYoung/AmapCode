package com.alipay.apmobilesecuritysdk.apdidgen;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.commonbiz.RushTimeUtil;
import com.alipay.apmobilesecuritysdk.constant.Constant;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.apmobilesecuritysdk.storage.TokenStorage;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.deviceinfo.DeviceInfo;
import java.util.Map;

public class ApdidValidCheckProcessor implements ApdidProcessor {
    private TraceLogger a = LoggerFactory.f();

    public final boolean a(Context context, Map<String, Object> map) {
        boolean z;
        long currentTimeMillis = System.currentTimeMillis();
        this.a.b((String) CONST.LOG_TAG, "ApdidValidCheckProcessor() start:".concat(String.valueOf(currentTimeMillis)));
        String stringFromMap = CommonUtils.getStringFromMap(map, "appName", "");
        if (RushTimeUtil.a()) {
            z = true;
            if (CommonUtils.isBlank(TokenStorage.b(context, stringFromMap))) {
                this.a.b((String) CONST.LOG_TAG, (String) "[*] rush hour, but apdidToken is empty, prepare to upload data to server.");
                map.put("why_update", Constant.a);
            } else if (CommonUtils.isBlank(TokenStorage.a(context))) {
                this.a.b((String) CONST.LOG_TAG, (String) "[*] rush hour, but apdid is empty, prepare to upload data to server.");
                map.put("why_update", Constant.b);
            } else {
                if (Math.abs(System.currentTimeMillis() - SettingsStorage.g(context, stringFromMap)) > 3888000000L) {
                    map.put("why_update", Constant.d);
                } else {
                    String stringFromMap2 = CommonUtils.getStringFromMap(map, "tid", "");
                    if (!CommonUtils.isNotBlank(stringFromMap2) || CommonUtils.equals(stringFromMap2, TokenStorage.c())) {
                        z = false;
                    } else {
                        map.put("why_update", Constant.c);
                    }
                }
            }
        } else {
            z = a(context, map, stringFromMap);
        }
        SettingsStorage.b(context, String.valueOf(DeviceInfo.getInstance().getSystemBootTime()));
        TraceLogger traceLogger = this.a;
        StringBuilder sb = new StringBuilder("ApdidValidCheckProcessor() cost ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append(" ms.");
        traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00f3 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.content.Context r12, java.util.Map<java.lang.String, java.lang.Object> r13, java.lang.String r14) {
        /*
            r11 = this;
            java.lang.String r0 = com.alipay.apmobilesecuritysdk.storage.TokenStorage.b(r12, r14)
            boolean r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r0)
            r1 = 1
            if (r0 == 0) goto L_0x001d
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r12 = r11.a
            java.lang.String r14 = "APSecuritySdk"
            java.lang.String r0 = "[*] apdidToken is empty, prepare to upload data to server."
            r12.b(r14, r0)
            java.lang.String r12 = "why_update"
            java.lang.Integer r14 = com.alipay.apmobilesecuritysdk.constant.Constant.a
            r13.put(r12, r14)
            return r1
        L_0x001d:
            java.lang.String r0 = com.alipay.apmobilesecuritysdk.storage.TokenStorage.a(r12)
            boolean r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r0)
            if (r0 == 0) goto L_0x0039
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r12 = r11.a
            java.lang.String r14 = "APSecuritySdk"
            java.lang.String r0 = "[*] apdid is empty, prepare to upload data to server."
            r12.b(r14, r0)
            java.lang.String r12 = "why_update"
            java.lang.Integer r14 = com.alipay.apmobilesecuritysdk.constant.Constant.b
            r13.put(r12, r14)
            return r1
        L_0x0039:
            com.alipay.apmobilesecuritysdk.model.DeviceInfoManager r0 = com.alipay.apmobilesecuritysdk.model.DeviceInfoManager.a()
            java.lang.String r0 = r0.a(r12)
            java.lang.String r2 = "devicehash"
            r13.put(r2, r0)
            java.lang.String r2 = com.alipay.apmobilesecuritysdk.storage.TokenStorage.b()
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = r11.a
            java.lang.String r4 = "APSecuritySdk"
            java.lang.String r5 = "last hash:"
            java.lang.String r6 = java.lang.String.valueOf(r2)
            java.lang.String r5 = r5.concat(r6)
            r3.b(r4, r5)
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = r11.a
            java.lang.String r4 = "APSecuritySdk"
            java.lang.String r5 = "new  hash:"
            java.lang.String r6 = java.lang.String.valueOf(r0)
            java.lang.String r5 = r5.concat(r6)
            r3.b(r4, r5)
            boolean r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.equals(r0, r2)
            r0 = r0 ^ r1
            if (r0 == 0) goto L_0x0085
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r12 = r11.a
            java.lang.String r14 = "APSecuritySdk"
            java.lang.String r0 = "[*] device static info changed, prepare to upload data to server."
            r12.b(r14, r0)
            java.lang.String r12 = "why_update"
            java.lang.Integer r14 = com.alipay.apmobilesecuritysdk.constant.Constant.c
            r13.put(r12, r14)
            return r1
        L_0x0085:
            java.lang.String r0 = com.alipay.apmobilesecuritysdk.storage.SettingsStorage.b(r12)
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = r11.a
            java.lang.String r3 = "APSecuritySdk"
            java.lang.String r4 = "last machine boot time    : "
            java.lang.String r5 = java.lang.String.valueOf(r0)
            java.lang.String r4 = r4.concat(r5)
            r2.b(r3, r4)
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = r11.a
            java.lang.String r3 = "APSecuritySdk"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "current machine boot time : "
            r4.<init>(r5)
            com.alipay.security.mobile.module.deviceinfo.DeviceInfo r5 = com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getInstance()
            long r5 = r5.getSystemBootTime()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r2.b(r3, r4)
            r2 = 0
            r3 = 0
            long r5 = java.lang.Long.parseLong(r0)     // Catch:{ Throwable -> 0x00cb }
            com.alipay.security.mobile.module.deviceinfo.DeviceInfo r0 = com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getInstance()     // Catch:{ Throwable -> 0x00c9 }
            long r7 = r0.getSystemBootTime()     // Catch:{ Throwable -> 0x00c9 }
            r3 = r7
            r0 = 0
            goto L_0x00e7
        L_0x00c9:
            r0 = move-exception
            goto L_0x00cd
        L_0x00cb:
            r0 = move-exception
            r5 = r3
        L_0x00cd:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r7 = r11.a
            java.lang.String r8 = "APSecuritySdk"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "[*] read boot time parse error, exception = ."
            r9.<init>(r10)
            java.lang.String r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.getStackString(r0)
            r9.append(r0)
            java.lang.String r0 = r9.toString()
            r7.b(r8, r0)
            r0 = 1
        L_0x00e7:
            r7 = 0
            long r3 = r3 - r5
            long r3 = java.lang.Math.abs(r3)
            r5 = 3000(0xbb8, double:1.482E-320)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 > 0) goto L_0x016e
            if (r0 == 0) goto L_0x00f6
            goto L_0x016e
        L_0x00f6:
            java.lang.String r0 = "tid"
            java.lang.String r3 = ""
            java.lang.String r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.getStringFromMap(r13, r0, r3)
            java.lang.String r3 = "utdid"
            java.lang.String r4 = ""
            java.lang.String r3 = com.alipay.security.mobile.module.commonutils.CommonUtils.getStringFromMap(r13, r3, r4)
            boolean r4 = com.alipay.security.mobile.module.commonutils.CommonUtils.isNotBlank(r0)
            if (r4 == 0) goto L_0x012a
            java.lang.String r4 = com.alipay.apmobilesecuritysdk.storage.TokenStorage.c()
            boolean r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.equals(r0, r4)
            if (r0 != 0) goto L_0x012a
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r12 = r11.a
            java.lang.String r14 = "APSecuritySdk"
            java.lang.String r0 = "[*] tid changed, prepare to upload data to server."
            r12.b(r14, r0)
            java.lang.String r12 = "why_update"
            java.lang.Integer r14 = com.alipay.apmobilesecuritysdk.constant.Constant.c
            r13.put(r12, r14)
            return r1
        L_0x012a:
            boolean r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.isNotBlank(r3)
            if (r0 == 0) goto L_0x014c
            java.lang.String r0 = com.alipay.apmobilesecuritysdk.storage.TokenStorage.d()
            boolean r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.equals(r3, r0)
            if (r0 != 0) goto L_0x014c
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r12 = r11.a
            java.lang.String r14 = "APSecuritySdk"
            java.lang.String r0 = "[*] UTDID changed, prepare to upload data to server."
            r12.b(r14, r0)
            java.lang.String r12 = "why_update"
            java.lang.Integer r14 = com.alipay.apmobilesecuritysdk.constant.Constant.c
            r13.put(r12, r14)
            return r1
        L_0x014c:
            boolean r12 = com.alipay.apmobilesecuritysdk.storage.TokenStorage.a(r12, r14)
            if (r12 != 0) goto L_0x0164
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r12 = r11.a
            java.lang.String r14 = "APSecuritySdk"
            java.lang.String r0 = "[*] local apaid and apdidToken is out of date, prepare to upload data to server."
            r12.b(r14, r0)
            java.lang.String r12 = "why_update"
            java.lang.Integer r14 = com.alipay.apmobilesecuritysdk.constant.Constant.d
            r13.put(r12, r14)
            return r1
        L_0x0164:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r12 = r11.a
            java.lang.String r13 = "APSecuritySdk"
            java.lang.String r14 = "[*] finished data verify, DONT need to upload data to server."
            r12.b(r13, r14)
            return r2
        L_0x016e:
            java.lang.String r12 = "why_update"
            java.lang.Integer r14 = com.alipay.apmobilesecuritysdk.constant.Constant.c
            r13.put(r12, r14)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.apdidgen.ApdidValidCheckProcessor.a(android.content.Context, java.util.Map, java.lang.String):boolean");
    }
}
