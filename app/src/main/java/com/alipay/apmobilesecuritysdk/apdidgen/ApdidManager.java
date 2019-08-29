package com.alipay.apmobilesecuritysdk.apdidgen;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.commonbiz.external.UmidSdkWrapper;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.apmobilesecuritysdk.storage.SettingsStorage;
import com.alipay.apmobilesecuritysdk.storage.TokenStorage;

public class ApdidManager {
    public static int a;
    private static TraceLogger b = LoggerFactory.f();

    /* JADX WARNING: Can't wrap try/catch for region: R(12:1|2|3|4|5|(5:8|(1:12)|13|14|6)|15|16|(3:19|(1:26)|17)|27|21|22) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x004b */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x007a A[Catch:{ Exception -> 0x009c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(android.content.Context r6, java.util.Map<java.lang.String, java.lang.Object> r7, int r8) {
        /*
            a = r8
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r8 = b     // Catch:{ Exception -> 0x009c }
            java.lang.String r0 = "APSecuritySdk"
            java.lang.String r1 = "on generateApdid"
            r8.b(r0, r1)     // Catch:{ Exception -> 0x009c }
            java.lang.String r8 = "device_feature_file_name"
            java.lang.String r0 = "wallet_times"
            java.lang.String r1 = "wxcasxx_v3"
            java.lang.String r2 = "wxcasxx_v4"
            java.lang.String r3 = "wxxzyy_v1"
            java.lang.String[] r8 = new java.lang.String[]{r8, r0, r1, r2, r3}     // Catch:{ Throwable -> 0x004b }
            r0 = 0
        L_0x001e:
            r1 = 5
            if (r0 >= r1) goto L_0x004b
            r1 = r8[r0]     // Catch:{ Throwable -> 0x004b }
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x004b }
            java.io.File r3 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Throwable -> 0x004b }
            java.lang.String r4 = ".SystemConfig/"
            java.lang.String r5 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x004b }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ Throwable -> 0x004b }
            r2.<init>(r3, r4)     // Catch:{ Throwable -> 0x004b }
            boolean r3 = r2.exists()     // Catch:{ Throwable -> 0x004b }
            if (r3 == 0) goto L_0x0045
            boolean r3 = r2.canWrite()     // Catch:{ Throwable -> 0x004b }
            if (r3 == 0) goto L_0x0045
            r2.delete()     // Catch:{ Throwable -> 0x004b }
        L_0x0045:
            java.lang.System.clearProperty(r1)     // Catch:{ Throwable -> 0x004b }
            int r0 = r0 + 1
            goto L_0x001e
        L_0x004b:
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ Exception -> 0x009c }
            r8.<init>()     // Catch:{ Exception -> 0x009c }
            com.alipay.apmobilesecuritysdk.apdidgen.ApdidInitializeProcessor r0 = new com.alipay.apmobilesecuritysdk.apdidgen.ApdidInitializeProcessor     // Catch:{ Exception -> 0x009c }
            r0.<init>()     // Catch:{ Exception -> 0x009c }
            r8.add(r0)     // Catch:{ Exception -> 0x009c }
            com.alipay.apmobilesecuritysdk.apdidgen.ApdidValidCheckProcessor r0 = new com.alipay.apmobilesecuritysdk.apdidgen.ApdidValidCheckProcessor     // Catch:{ Exception -> 0x009c }
            r0.<init>()     // Catch:{ Exception -> 0x009c }
            r8.add(r0)     // Catch:{ Exception -> 0x009c }
            com.alipay.apmobilesecuritysdk.apdidgen.ApdidRequestDataProcessor r0 = new com.alipay.apmobilesecuritysdk.apdidgen.ApdidRequestDataProcessor     // Catch:{ Exception -> 0x009c }
            r0.<init>()     // Catch:{ Exception -> 0x009c }
            r8.add(r0)     // Catch:{ Exception -> 0x009c }
            com.alipay.apmobilesecuritysdk.apdidgen.ApdidRequestServerProcessor r0 = new com.alipay.apmobilesecuritysdk.apdidgen.ApdidRequestServerProcessor     // Catch:{ Exception -> 0x009c }
            r0.<init>()     // Catch:{ Exception -> 0x009c }
            r8.add(r0)     // Catch:{ Exception -> 0x009c }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ Exception -> 0x009c }
        L_0x0074:
            boolean r0 = r8.hasNext()     // Catch:{ Exception -> 0x009c }
            if (r0 == 0) goto L_0x0086
            java.lang.Object r0 = r8.next()     // Catch:{ Exception -> 0x009c }
            com.alipay.apmobilesecuritysdk.apdidgen.ApdidProcessor r0 = (com.alipay.apmobilesecuritysdk.apdidgen.ApdidProcessor) r0     // Catch:{ Exception -> 0x009c }
            boolean r0 = r0.a(r6, r7)     // Catch:{ Exception -> 0x009c }
            if (r0 != 0) goto L_0x0074
        L_0x0086:
            com.alipay.apmobilesecuritysdk.apdidgen.ApdidFinalizeProcessor r8 = new com.alipay.apmobilesecuritysdk.apdidgen.ApdidFinalizeProcessor     // Catch:{ Exception -> 0x009c }
            r8.<init>()     // Catch:{ Exception -> 0x009c }
            r8.a(r6, r7)     // Catch:{ Exception -> 0x009c }
            java.lang.String r6 = "resultcode"
            java.lang.Object r6 = r7.get(r6)     // Catch:{ Exception -> 0x009c }
            java.lang.Integer r6 = (java.lang.Integer) r6     // Catch:{ Exception -> 0x009c }
            int r6 = r6.intValue()     // Catch:{ Exception -> 0x009c }
            return r6
        L_0x009c:
            r6 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r7 = b
            java.lang.String r8 = "APSecuritySdk"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "generateApdid(), 未知的Exception : "
            r0.<init>(r1)
            java.lang.String r6 = com.alipay.security.mobile.module.commonutils.CommonUtils.getStackString(r6)
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            r7.d(r8, r6)
            r6 = 4
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.apdidgen.ApdidManager.a(android.content.Context, java.util.Map, int):int");
    }

    public static TokenResult a(Context context) {
        TokenResult tokenResult = new TokenResult();
        try {
            tokenResult.apdidToken = TokenStorage.b(context, "");
            tokenResult.clientKey = SettingsStorage.h(context);
            tokenResult.apdid = TokenStorage.b(context);
            tokenResult.umidToken = UmidSdkWrapper.getLocalUmidToken(context);
        } catch (Throwable unused) {
        }
        return tokenResult;
    }
}
