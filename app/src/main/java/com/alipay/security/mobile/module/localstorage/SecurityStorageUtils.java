package com.alipay.security.mobile.module.localstorage;

import android.content.Context;
import com.alipay.security.mobile.module.commonutils.CommonUtils;

public class SecurityStorageUtils {
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0031, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String readFromSharedPreference(android.content.Context r3, java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.Class<com.alipay.security.mobile.module.localstorage.SecurityStorageUtils> r0 = com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.class
            monitor-enter(r0)
            r1 = 0
            if (r3 == 0) goto L_0x0030
            boolean r2 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r4)     // Catch:{ all -> 0x002d }
            if (r2 != 0) goto L_0x0030
            boolean r2 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r5)     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x0013
            goto L_0x0030
        L_0x0013:
            java.lang.String r2 = ""
            java.lang.String r3 = com.alipay.security.mobile.module.localstorage.SharePreferenceStorage.getDataFromSharePreference(r3, r4, r5, r2)     // Catch:{ Throwable -> 0x002a }
            boolean r4 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r3)     // Catch:{ Throwable -> 0x002a }
            if (r4 == 0) goto L_0x0021
            monitor-exit(r0)
            return r1
        L_0x0021:
            java.lang.String r4 = com.alipay.security.mobile.module.crypto.SecurityUtils.getSeed()     // Catch:{ Throwable -> 0x002a }
            java.lang.String r3 = com.alipay.security.mobile.module.crypto.SecurityUtils.decrypt(r4, r3)     // Catch:{ Throwable -> 0x002a }
            goto L_0x002b
        L_0x002a:
            r3 = r1
        L_0x002b:
            monitor-exit(r0)
            return r3
        L_0x002d:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        L_0x0030:
            monitor-exit(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.readFromSharedPreference(android.content.Context, java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void writeToSharedPreference(android.content.Context r2, java.lang.String r3, java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.Class<com.alipay.security.mobile.module.localstorage.SecurityStorageUtils> r0 = com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.class
            monitor-enter(r0)
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r3)     // Catch:{ all -> 0x002b }
            if (r1 != 0) goto L_0x0029
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r4)     // Catch:{ all -> 0x002b }
            if (r1 != 0) goto L_0x0029
            if (r2 != 0) goto L_0x0012
            goto L_0x0029
        L_0x0012:
            java.lang.String r1 = com.alipay.security.mobile.module.crypto.SecurityUtils.getSeed()     // Catch:{ Throwable -> 0x0027 }
            java.lang.String r5 = com.alipay.security.mobile.module.crypto.SecurityUtils.encrypt(r1, r5)     // Catch:{ Throwable -> 0x0027 }
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ Throwable -> 0x0027 }
            r1.<init>()     // Catch:{ Throwable -> 0x0027 }
            r1.put(r4, r5)     // Catch:{ Throwable -> 0x0027 }
            com.alipay.security.mobile.module.localstorage.SharePreferenceStorage.writeDataToSharePreference(r2, r3, r1)     // Catch:{ Throwable -> 0x0027 }
            monitor-exit(r0)
            return
        L_0x0027:
            monitor-exit(r0)
            return
        L_0x0029:
            monitor-exit(r0)
            return
        L_0x002b:
            r2 = move-exception
            monitor-exit(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.writeToSharedPreference(android.content.Context, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:10|11|12|13) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0041, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0025 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void writeToFile(java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            java.lang.Class<com.alipay.security.mobile.module.localstorage.SecurityStorageUtils> r0 = com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.class
            monitor-enter(r0)
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r4)     // Catch:{ all -> 0x0042 }
            if (r1 != 0) goto L_0x0040
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r5)     // Catch:{ all -> 0x0042 }
            if (r1 == 0) goto L_0x0010
            goto L_0x0040
        L_0x0010:
            java.lang.String r1 = com.alipay.security.mobile.module.localstorage.PublicStorage.readDataFromPublicArea(r4)     // Catch:{ Throwable -> 0x003e }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x003e }
            r2.<init>()     // Catch:{ Throwable -> 0x003e }
            boolean r3 = com.alipay.security.mobile.module.commonutils.CommonUtils.isNotBlank(r1)     // Catch:{ Throwable -> 0x003e }
            if (r3 == 0) goto L_0x002a
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0025 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0025 }
            goto L_0x002a
        L_0x0025:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x003e }
            r2.<init>()     // Catch:{ Throwable -> 0x003e }
        L_0x002a:
            java.lang.String r1 = com.alipay.security.mobile.module.crypto.SecurityUtils.getSeed()     // Catch:{ Throwable -> 0x003e }
            java.lang.String r6 = com.alipay.security.mobile.module.crypto.SecurityUtils.encrypt(r1, r6)     // Catch:{ Throwable -> 0x003e }
            r2.put(r5, r6)     // Catch:{ Throwable -> 0x003e }
            java.lang.String r5 = r2.toString()     // Catch:{ Throwable -> 0x003e }
            com.alipay.security.mobile.module.localstorage.PublicStorage.writeDataToPublicArea(r4, r5)     // Catch:{ Throwable -> 0x003e }
            monitor-exit(r0)
            return
        L_0x003e:
            monitor-exit(r0)
            return
        L_0x0040:
            monitor-exit(r0)
            return
        L_0x0042:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.writeToFile(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003b, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String readFromFile(java.lang.String r3, java.lang.String r4) {
        /*
            java.lang.Class<com.alipay.security.mobile.module.localstorage.SecurityStorageUtils> r0 = com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.class
            monitor-enter(r0)
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r3)     // Catch:{ all -> 0x003c }
            r2 = 0
            if (r1 != 0) goto L_0x003a
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r4)     // Catch:{ all -> 0x003c }
            if (r1 == 0) goto L_0x0011
            goto L_0x003a
        L_0x0011:
            java.lang.String r3 = com.alipay.security.mobile.module.localstorage.PublicStorage.readDataFromPublicArea(r3)     // Catch:{ Throwable -> 0x0038 }
            boolean r1 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r3)     // Catch:{ Throwable -> 0x0038 }
            if (r1 == 0) goto L_0x001d
            monitor-exit(r0)
            return r2
        L_0x001d:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0038 }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0038 }
            java.lang.String r3 = r1.getString(r4)     // Catch:{ Throwable -> 0x0038 }
            boolean r4 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r3)     // Catch:{ Throwable -> 0x0038 }
            if (r4 == 0) goto L_0x002e
            monitor-exit(r0)
            return r2
        L_0x002e:
            java.lang.String r4 = com.alipay.security.mobile.module.crypto.SecurityUtils.getSeed()     // Catch:{ Throwable -> 0x0038 }
            java.lang.String r3 = com.alipay.security.mobile.module.crypto.SecurityUtils.decrypt(r4, r3)     // Catch:{ Throwable -> 0x0038 }
            monitor-exit(r0)
            return r3
        L_0x0038:
            monitor-exit(r0)
            return r2
        L_0x003a:
            monitor-exit(r0)
            return r2
        L_0x003c:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.readFromFile(java.lang.String, java.lang.String):java.lang.String");
    }

    public static synchronized String readData(Context context, String str, String str2) {
        String readFromSharedPreference;
        synchronized (SecurityStorageUtils.class) {
            try {
                readFromSharedPreference = readFromSharedPreference(context, str, str2);
                if (CommonUtils.isBlank(readFromSharedPreference)) {
                    readFromSharedPreference = readFromFile(str, str2);
                }
            }
        }
        return readFromSharedPreference;
    }

    public static synchronized void writeData(Context context, String str, String str2, String str3) {
        synchronized (SecurityStorageUtils.class) {
            writeToFile(str, str2, str3);
            writeToSharedPreference(context, str, str2, str3);
        }
    }
}
