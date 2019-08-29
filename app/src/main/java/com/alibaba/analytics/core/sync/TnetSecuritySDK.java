package com.alibaba.analytics.core.sync;

import com.alibaba.analytics.utils.Logger;
import java.lang.reflect.Method;

public class TnetSecuritySDK {
    private static volatile TnetSecuritySDK mTnetSecuritySDK;
    private String authcode = "";
    private Method getByteArrayMethod = null;
    private Object mDynamicDataStoreCompObj = null;
    private boolean mInitSecurityCheck = false;
    private Object mSecurityGuardManagerObj = null;
    private Object mStaticDataEncryptCompObj = null;
    private Method putByteArrayMethod = null;
    private Method staticBinarySafeDecryptNoB64Method = null;

    private TnetSecuritySDK() {
    }

    public static TnetSecuritySDK getInstance() {
        TnetSecuritySDK tnetSecuritySDK;
        if (mTnetSecuritySDK != null) {
            return mTnetSecuritySDK;
        }
        synchronized (TnetSecuritySDK.class) {
            try {
                if (mTnetSecuritySDK == null) {
                    TnetSecuritySDK tnetSecuritySDK2 = new TnetSecuritySDK();
                    mTnetSecuritySDK = tnetSecuritySDK2;
                    tnetSecuritySDK2.initSecurityCheck();
                }
                tnetSecuritySDK = mTnetSecuritySDK;
            }
        }
        return tnetSecuritySDK;
    }

    public boolean getInitSecurityCheck() {
        Logger.d((String) "", "mInitSecurityCheck", Boolean.valueOf(this.mInitSecurityCheck));
        return this.mInitSecurityCheck;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00c2, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void initSecurityCheck() {
        /*
            r12 = this;
            monitor-enter(r12)
            com.alibaba.analytics.utils.Logger.d()     // Catch:{ all -> 0x00e1 }
            r0 = 3
            r1 = 4
            r2 = 2
            r3 = 1
            r4 = 0
            com.alibaba.analytics.core.Variables r5 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Throwable -> 0x00c3 }
            com.ut.mini.core.sign.IUTRequestAuthentication r5 = r5.getRequestAuthenticationInstance()     // Catch:{ Throwable -> 0x00c3 }
            boolean r6 = r5 instanceof com.ut.mini.core.sign.UTBaseRequestAuthentication     // Catch:{ Throwable -> 0x00c3 }
            if (r6 == 0) goto L_0x0017
            r12.mInitSecurityCheck = r4     // Catch:{ Throwable -> 0x00c3 }
        L_0x0017:
            if (r5 == 0) goto L_0x00c1
            java.lang.String r6 = "com.alibaba.wireless.security.open.SecurityGuardManager"
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r7 = "com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent"
            java.lang.Class r7 = java.lang.Class.forName(r7)     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r8 = "com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent"
            java.lang.Class r8 = java.lang.Class.forName(r8)     // Catch:{ Throwable -> 0x00c3 }
            boolean r9 = r5 instanceof com.ut.mini.core.sign.UTSecurityThridRequestAuthentication     // Catch:{ Throwable -> 0x00c3 }
            if (r9 == 0) goto L_0x0037
            com.ut.mini.core.sign.UTSecurityThridRequestAuthentication r5 = (com.ut.mini.core.sign.UTSecurityThridRequestAuthentication) r5     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r5 = r5.getAuthcode()     // Catch:{ Throwable -> 0x00c3 }
            r12.authcode = r5     // Catch:{ Throwable -> 0x00c3 }
        L_0x0037:
            if (r6 == 0) goto L_0x00bf
            if (r7 == 0) goto L_0x00bf
            if (r8 == 0) goto L_0x00bf
            java.lang.String r5 = "getInstance"
            java.lang.Class[] r9 = new java.lang.Class[r3]     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Class<android.content.Context> r10 = android.content.Context.class
            r9[r4] = r10     // Catch:{ Throwable -> 0x00c3 }
            java.lang.reflect.Method r5 = r6.getMethod(r5, r9)     // Catch:{ Throwable -> 0x00c3 }
            r9 = 0
            java.lang.Object[] r10 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00c3 }
            com.alibaba.analytics.core.Variables r11 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Throwable -> 0x00c3 }
            android.content.Context r11 = r11.getContext()     // Catch:{ Throwable -> 0x00c3 }
            r10[r4] = r11     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Object r5 = r5.invoke(r9, r10)     // Catch:{ Throwable -> 0x00c3 }
            r12.mSecurityGuardManagerObj = r5     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r5 = "getStaticDataEncryptComp"
            java.lang.Class[] r9 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x00c3 }
            java.lang.reflect.Method r5 = r6.getMethod(r5, r9)     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Object r9 = r12.mSecurityGuardManagerObj     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Object r5 = r5.invoke(r9, r10)     // Catch:{ Throwable -> 0x00c3 }
            r12.mStaticDataEncryptCompObj = r5     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r5 = "getDynamicDataStoreComp"
            java.lang.Class[] r9 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x00c3 }
            java.lang.reflect.Method r5 = r6.getMethod(r5, r9)     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Object r6 = r12.mSecurityGuardManagerObj     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Object[] r9 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Object r5 = r5.invoke(r6, r9)     // Catch:{ Throwable -> 0x00c3 }
            r12.mDynamicDataStoreCompObj = r5     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r5 = "staticBinarySafeDecryptNoB64"
            java.lang.Class[] r6 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Class r9 = java.lang.Integer.TYPE     // Catch:{ Throwable -> 0x00c3 }
            r6[r4] = r9     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            r6[r3] = r9     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Class<byte[]> r9 = byte[].class
            r6[r2] = r9     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            r6[r0] = r9     // Catch:{ Throwable -> 0x00c3 }
            java.lang.reflect.Method r5 = r7.getMethod(r5, r6)     // Catch:{ Throwable -> 0x00c3 }
            r12.staticBinarySafeDecryptNoB64Method = r5     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r5 = "putByteArray"
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r6[r4] = r7     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Class<byte[]> r7 = byte[].class
            r6[r3] = r7     // Catch:{ Throwable -> 0x00c3 }
            java.lang.reflect.Method r5 = r8.getMethod(r5, r6)     // Catch:{ Throwable -> 0x00c3 }
            r12.putByteArrayMethod = r5     // Catch:{ Throwable -> 0x00c3 }
            java.lang.String r5 = "getByteArray"
            java.lang.Class[] r6 = new java.lang.Class[r3]     // Catch:{ Throwable -> 0x00c3 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r6[r4] = r7     // Catch:{ Throwable -> 0x00c3 }
            java.lang.reflect.Method r5 = r8.getMethod(r5, r6)     // Catch:{ Throwable -> 0x00c3 }
            r12.getByteArrayMethod = r5     // Catch:{ Throwable -> 0x00c3 }
            r12.mInitSecurityCheck = r3     // Catch:{ Throwable -> 0x00c3 }
            monitor-exit(r12)
            return
        L_0x00bf:
            r12.mInitSecurityCheck = r4     // Catch:{ Throwable -> 0x00c3 }
        L_0x00c1:
            monitor-exit(r12)
            return
        L_0x00c3:
            r5 = move-exception
            r12.mInitSecurityCheck = r4     // Catch:{ all -> 0x00e1 }
            java.lang.String r6 = "initSecurityCheck"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00e1 }
            java.lang.String r7 = "e.getCode"
            r1[r4] = r7     // Catch:{ all -> 0x00e1 }
            java.lang.Throwable r4 = r5.getCause()     // Catch:{ all -> 0x00e1 }
            r1[r3] = r4     // Catch:{ all -> 0x00e1 }
            r1[r2] = r5     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = r5.getMessage()     // Catch:{ all -> 0x00e1 }
            r1[r0] = r2     // Catch:{ all -> 0x00e1 }
            com.alibaba.analytics.utils.Logger.e(r6, r1)     // Catch:{ all -> 0x00e1 }
            monitor-exit(r12)
            return
        L_0x00e1:
            r0 = move-exception
            monitor-exit(r12)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.sync.TnetSecuritySDK.initSecurityCheck():void");
    }

    public byte[] staticBinarySafeDecryptNoB64(int i, String str, byte[] bArr) {
        if (this.staticBinarySafeDecryptNoB64Method == null || this.mStaticDataEncryptCompObj == null) {
            return null;
        }
        try {
            Object invoke = this.staticBinarySafeDecryptNoB64Method.invoke(this.mStaticDataEncryptCompObj, new Object[]{Integer.valueOf(i), str, bArr, this.authcode});
            Logger.i((String) "", "mStaticDataEncryptCompObj", this.mStaticDataEncryptCompObj, "i", Integer.valueOf(i), "str", str, "bArr", bArr, "authcode", this.authcode, "obj", invoke);
            if (invoke == null) {
                return null;
            }
            return (byte[]) invoke;
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
            return null;
        }
    }

    public int putByteArray(String str, byte[] bArr) {
        if (this.putByteArrayMethod == null || this.mDynamicDataStoreCompObj == null) {
            return 0;
        }
        try {
            Object invoke = this.putByteArrayMethod.invoke(this.mDynamicDataStoreCompObj, new Object[]{str, bArr});
            if (invoke == null) {
                return 0;
            }
            int intValue = ((Integer) invoke).intValue();
            Logger.d((String) "", "ret", Integer.valueOf(intValue));
            return intValue;
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
            return -1;
        }
    }

    public byte[] getByteArray(String str) {
        if (this.getByteArrayMethod == null || this.mDynamicDataStoreCompObj == null) {
            return null;
        }
        try {
            Object invoke = this.getByteArrayMethod.invoke(this.mDynamicDataStoreCompObj, new Object[]{str});
            if (invoke == null) {
                return null;
            }
            return (byte[]) invoke;
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
            return null;
        }
    }
}
