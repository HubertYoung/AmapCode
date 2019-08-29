package com.ut.mini.core.sign;

import com.alibaba.analytics.utils.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class UTSecurityThridRequestAuthentication implements IUTRequestAuthentication {
    private static final String TAG = "UTSecurityThridRequestAuthentication";
    private String authcode;
    private String mAppkey = null;
    private boolean mBInitSecurityCheck = false;
    private int s_secureIndex = 3;
    private Object s_secureSignatureCompObj = null;
    private Object s_securityGuardManagerObj = null;
    private Class s_securityGuardParamContextClz = null;
    private Field s_securityGuardParamContext_appKey = null;
    private Field s_securityGuardParamContext_paramMap = null;
    private Field s_securityGuardParamContext_requestType = null;
    private Method s_signRequestMethod = null;

    public String getAppkey() {
        return this.mAppkey;
    }

    public String getAuthcode() {
        return this.authcode;
    }

    public UTSecurityThridRequestAuthentication(String str, String str2) {
        this.mAppkey = str;
        this.authcode = str2;
    }

    public String getSign(String str) {
        String str2;
        Logger.d((String) "", "toBeSignedStr", str);
        if (!this.mBInitSecurityCheck) {
            initSecurityCheck();
        }
        if (this.mAppkey == null) {
            Logger.d((String) TAG, "There is no appkey,please check it!");
            return null;
        } else if (str == null) {
            return null;
        } else {
            if (this.s_securityGuardManagerObj == null || this.s_securityGuardParamContextClz == null || this.s_securityGuardParamContext_appKey == null || this.s_securityGuardParamContext_paramMap == null || this.s_securityGuardParamContext_requestType == null || this.s_signRequestMethod == null || this.s_secureSignatureCompObj == null) {
                Logger.w((String) "UTSecurityThridRequestAuthentication.getSign", "s_securityGuardManagerObj", this.s_securityGuardManagerObj, "s_securityGuardParamContextClz", this.s_securityGuardParamContextClz, "s_securityGuardParamContext_appKey", this.s_securityGuardParamContext_appKey, "s_securityGuardParamContext_paramMap", this.s_securityGuardParamContext_paramMap, "s_securityGuardParamContext_requestType", this.s_securityGuardParamContext_requestType, "s_signRequestMethod", this.s_signRequestMethod);
            } else {
                try {
                    Object newInstance = this.s_securityGuardParamContextClz.newInstance();
                    this.s_securityGuardParamContext_appKey.set(newInstance, this.mAppkey);
                    ((Map) this.s_securityGuardParamContext_paramMap.get(newInstance)).put("INPUT", str);
                    this.s_securityGuardParamContext_requestType.set(newInstance, Integer.valueOf(this.s_secureIndex));
                    str2 = (String) this.s_signRequestMethod.invoke(this.s_secureSignatureCompObj, new Object[]{newInstance, this.authcode});
                } catch (Exception e) {
                    Logger.e(null, e, new Object[0]);
                }
                Logger.d((String) "", "lSignedStr", str2);
                return str2;
            }
            str2 = null;
            Logger.d((String) "", "lSignedStr", str2);
            return str2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void initSecurityCheck() {
        /*
            r9 = this;
            monitor-enter(r9)
            boolean r0 = r9.mBInitSecurityCheck     // Catch:{ all -> 0x00a8 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r9)
            return
        L_0x0007:
            r0 = 0
            r1 = 2
            r2 = 1
            r3 = 0
            java.lang.String r4 = "com.alibaba.wireless.security.open.SecurityGuardManager"
            java.lang.Class r4 = java.lang.Class.forName(r4)     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r5 = "getInstance"
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x0042 }
            java.lang.Class<android.content.Context> r7 = android.content.Context.class
            r6[r3] = r7     // Catch:{ Throwable -> 0x0042 }
            java.lang.reflect.Method r5 = r4.getMethod(r5, r6)     // Catch:{ Throwable -> 0x0042 }
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0042 }
            com.alibaba.analytics.core.Variables r7 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Throwable -> 0x0042 }
            android.content.Context r7 = r7.getContext()     // Catch:{ Throwable -> 0x0042 }
            r6[r3] = r7     // Catch:{ Throwable -> 0x0042 }
            java.lang.Object r0 = r5.invoke(r0, r6)     // Catch:{ Throwable -> 0x0042 }
            r9.s_securityGuardManagerObj = r0     // Catch:{ Throwable -> 0x0042 }
            java.lang.String r0 = "getSecureSignatureComp"
            java.lang.Class[] r5 = new java.lang.Class[r3]     // Catch:{ Throwable -> 0x0042 }
            java.lang.reflect.Method r0 = r4.getMethod(r0, r5)     // Catch:{ Throwable -> 0x0042 }
            java.lang.Object r5 = r9.s_securityGuardManagerObj     // Catch:{ Throwable -> 0x0042 }
            java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0042 }
            java.lang.Object r0 = r0.invoke(r5, r6)     // Catch:{ Throwable -> 0x0042 }
            r9.s_secureSignatureCompObj = r0     // Catch:{ Throwable -> 0x0042 }
            goto L_0x0055
        L_0x0042:
            r0 = move-exception
            goto L_0x0048
        L_0x0044:
            r4 = move-exception
            r8 = r4
            r4 = r0
            r0 = r8
        L_0x0048:
            java.lang.String r5 = "UTSecurityThridRequestAuthentication"
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ all -> 0x00a8 }
            java.lang.String r7 = "initSecurityCheck"
            r6[r3] = r7     // Catch:{ all -> 0x00a8 }
            r6[r2] = r0     // Catch:{ all -> 0x00a8 }
            com.alibaba.analytics.utils.Logger.w(r5, r6)     // Catch:{ all -> 0x00a8 }
        L_0x0055:
            if (r4 == 0) goto L_0x00a4
            java.lang.String r0 = "com.alibaba.wireless.security.open.SecurityGuardParamContext"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ Throwable -> 0x0096 }
            r9.s_securityGuardParamContextClz = r0     // Catch:{ Throwable -> 0x0096 }
            java.lang.Class r0 = r9.s_securityGuardParamContextClz     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r4 = "appKey"
            java.lang.reflect.Field r0 = r0.getDeclaredField(r4)     // Catch:{ Throwable -> 0x0096 }
            r9.s_securityGuardParamContext_appKey = r0     // Catch:{ Throwable -> 0x0096 }
            java.lang.Class r0 = r9.s_securityGuardParamContextClz     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r4 = "paramMap"
            java.lang.reflect.Field r0 = r0.getDeclaredField(r4)     // Catch:{ Throwable -> 0x0096 }
            r9.s_securityGuardParamContext_paramMap = r0     // Catch:{ Throwable -> 0x0096 }
            java.lang.Class r0 = r9.s_securityGuardParamContextClz     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r4 = "requestType"
            java.lang.reflect.Field r0 = r0.getDeclaredField(r4)     // Catch:{ Throwable -> 0x0096 }
            r9.s_securityGuardParamContext_requestType = r0     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r0 = "com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r4 = "signRequest"
            java.lang.Class[] r5 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x0096 }
            java.lang.Class r6 = r9.s_securityGuardParamContextClz     // Catch:{ Throwable -> 0x0096 }
            r5[r3] = r6     // Catch:{ Throwable -> 0x0096 }
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r2] = r6     // Catch:{ Throwable -> 0x0096 }
            java.lang.reflect.Method r0 = r0.getMethod(r4, r5)     // Catch:{ Throwable -> 0x0096 }
            r9.s_signRequestMethod = r0     // Catch:{ Throwable -> 0x0096 }
            goto L_0x00a4
        L_0x0096:
            r0 = move-exception
            java.lang.String r4 = "UTSecurityThridRequestAuthentication"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00a8 }
            java.lang.String r5 = "initSecurityCheck"
            r1[r3] = r5     // Catch:{ all -> 0x00a8 }
            r1[r2] = r0     // Catch:{ all -> 0x00a8 }
            com.alibaba.analytics.utils.Logger.w(r4, r1)     // Catch:{ all -> 0x00a8 }
        L_0x00a4:
            r9.mBInitSecurityCheck = r2     // Catch:{ all -> 0x00a8 }
            monitor-exit(r9)
            return
        L_0x00a8:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.core.sign.UTSecurityThridRequestAuthentication.initSecurityCheck():void");
    }
}
