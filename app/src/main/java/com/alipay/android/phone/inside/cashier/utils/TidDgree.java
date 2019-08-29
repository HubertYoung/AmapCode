package com.alipay.android.phone.inside.cashier.utils;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.cashier.enc.Des;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class TidDgree {
    public static final String KEY_CLIENTKEY = "client_key";
    public static final String KEY_TID = "tid";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_VIMEI = "vimei";
    public static final String KEY_VIMSI = "vimsi";
    public static final String PREF_TID_FILE = "alipay_tid_storage";
    public static final String PREF_TID_INFO = "tidinfo";

    public static class LocalPreference {
        private static String generateOldDesKey() {
            return "!@#23457";
        }

        public static String getLocalPreferences(Context context, String str, String str2) {
            return getLocalPreferences(context, str, str2, true);
        }

        public static String getLocalPreferences(Context context, String str, String str2, boolean z) {
            String string = context.getSharedPreferences(str, 0).getString(str2, null);
            if (!TextUtils.isEmpty(string) && z) {
                String decrypt = Des.decrypt(string, generateDesKey(context));
                if (TextUtils.isEmpty(decrypt)) {
                    decrypt = Des.decrypt(string, generateOldDesKey());
                }
                string = decrypt;
                if (TextUtils.isEmpty(string)) {
                    LoggerFactory.e().a((String) "insTid", (String) "InsDegreeTidEmpty", "");
                }
            }
            return string;
        }

        private static String generateDesKey(Context context) {
            String str;
            try {
                str = context.getApplicationContext().getPackageName();
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "inside", (String) "GenerateDesKeyEx", th);
                str = "";
            }
            LoggerFactory.f().b((String) "inside", "TidStorage.generateDesKey > packageName:".concat(String.valueOf(str)));
            if (TextUtils.isEmpty(str)) {
                str = "unknow";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("00000000");
            return sb.toString().substring(0, 8);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00e8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized android.os.Bundle loadTid(android.content.Context r14) throws java.lang.Exception {
        /*
            java.lang.Class<com.alipay.android.phone.inside.cashier.utils.TidDgree> r0 = com.alipay.android.phone.inside.cashier.utils.TidDgree.class
            monitor-enter(r0)
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x011a }
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ all -> 0x011a }
            r2 = 0
            java.lang.String r3 = "alipay_tid_storage"
            java.lang.String r4 = "tidinfo"
            r5 = 1
            java.lang.String r14 = com.alipay.android.phone.inside.cashier.utils.TidDgree.LocalPreference.getLocalPreferences(r14, r3, r4, r5)     // Catch:{ Exception -> 0x006a }
            boolean r3 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Exception -> 0x006a }
            if (r3 != 0) goto L_0x0066
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x006a }
            r3.<init>(r14)     // Catch:{ Exception -> 0x006a }
            java.lang.String r14 = "tid"
            java.lang.String r4 = ""
            java.lang.String r14 = r3.optString(r14, r4)     // Catch:{ Exception -> 0x006a }
            java.lang.String r4 = "client_key"
            java.lang.String r5 = ""
            java.lang.String r4 = r3.optString(r4, r5)     // Catch:{ Exception -> 0x0063 }
            java.lang.String r5 = "timestamp"
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0060 }
            long r5 = r3.optLong(r5, r6)     // Catch:{ Exception -> 0x0060 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ Exception -> 0x0060 }
            java.lang.String r1 = "vimei"
            java.lang.String r6 = ""
            java.lang.String r1 = r3.optString(r1, r6)     // Catch:{ Exception -> 0x005d }
            java.lang.String r6 = "vimsi"
            java.lang.String r7 = ""
            java.lang.String r3 = r3.optString(r6, r7)     // Catch:{ Exception -> 0x0058 }
            r2 = r4
            r4 = r3
            r3 = r1
            r1 = r5
            goto L_0x007d
        L_0x0058:
            r3 = move-exception
            r13 = r5
            r5 = r1
            r1 = r13
            goto L_0x006e
        L_0x005d:
            r3 = move-exception
            r1 = r5
            goto L_0x0061
        L_0x0060:
            r3 = move-exception
        L_0x0061:
            r5 = r2
            goto L_0x006e
        L_0x0063:
            r3 = move-exception
            r4 = r2
            goto L_0x006d
        L_0x0066:
            r14 = r2
            r3 = r14
            r4 = r3
            goto L_0x007d
        L_0x006a:
            r3 = move-exception
            r14 = r2
            r4 = r14
        L_0x006d:
            r5 = r4
        L_0x006e:
            com.alipay.android.phone.inside.log.api.ex.ExceptionLogger r6 = com.alipay.android.phone.inside.log.api.LoggerFactory.e()     // Catch:{ all -> 0x011a }
            java.lang.String r7 = "insTid"
            java.lang.String r8 = "TidDgreeLoadEx"
            r6.a(r7, r8, r3)     // Catch:{ all -> 0x011a }
            r3 = r5
            r13 = r4
            r4 = r2
            r2 = r13
        L_0x007d:
            java.lang.String r5 = ""
            java.lang.String r6 = ""
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r7 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ all -> 0x011a }
            java.lang.String r8 = "inside"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r9 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ all -> 0x011a }
            java.lang.String r10 = "InsTidDgreeBe"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x011a }
            java.lang.String r12 = "TidDgree::loadTid > "
            r11.<init>(r12)     // Catch:{ all -> 0x011a }
            r11.append(r14)     // Catch:{ all -> 0x011a }
            java.lang.String r12 = ","
            r11.append(r12)     // Catch:{ all -> 0x011a }
            r11.append(r2)     // Catch:{ all -> 0x011a }
            java.lang.String r12 = ","
            r11.append(r12)     // Catch:{ all -> 0x011a }
            r11.append(r1)     // Catch:{ all -> 0x011a }
            java.lang.String r1 = ","
            r11.append(r1)     // Catch:{ all -> 0x011a }
            r11.append(r3)     // Catch:{ all -> 0x011a }
            java.lang.String r1 = ","
            r11.append(r1)     // Catch:{ all -> 0x011a }
            r11.append(r4)     // Catch:{ all -> 0x011a }
            java.lang.String r1 = ","
            r11.append(r1)     // Catch:{ all -> 0x011a }
            r11.append(r5)     // Catch:{ all -> 0x011a }
            java.lang.String r1 = ","
            r11.append(r1)     // Catch:{ all -> 0x011a }
            r11.append(r6)     // Catch:{ all -> 0x011a }
            java.lang.String r1 = r11.toString()     // Catch:{ all -> 0x011a }
            r7.a(r8, r9, r10, r1)     // Catch:{ all -> 0x011a }
            boolean r1 = isIllegal(r14, r2, r3, r4)     // Catch:{ all -> 0x011a }
            if (r1 == 0) goto L_0x00e8
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r14 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ all -> 0x011a }
            java.lang.String r1 = "inside"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r2 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ all -> 0x011a }
            java.lang.String r3 = "TidDgreeIllegal"
            r14.b(r1, r2, r3)     // Catch:{ all -> 0x011a }
            java.lang.Exception r14 = new java.lang.Exception     // Catch:{ all -> 0x011a }
            java.lang.String r1 = "tid isIllegal"
            r14.<init>(r1)     // Catch:{ all -> 0x011a }
            throw r14     // Catch:{ all -> 0x011a }
        L_0x00e8:
            com.alipay.android.phone.inside.log.api.behavior.BehaviorLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.d()     // Catch:{ all -> 0x011a }
            java.lang.String r7 = "inside"
            com.alipay.android.phone.inside.log.api.behavior.BehaviorType r8 = com.alipay.android.phone.inside.log.api.behavior.BehaviorType.EVENT     // Catch:{ all -> 0x011a }
            java.lang.String r9 = "TidDgreeSuccess"
            r1.b(r7, r8, r9)     // Catch:{ all -> 0x011a }
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ all -> 0x011a }
            r1.<init>()     // Catch:{ all -> 0x011a }
            java.lang.String r7 = "Tid"
            r1.putString(r7, r14)     // Catch:{ all -> 0x011a }
            java.lang.String r14 = "TidSeed"
            r1.putString(r14, r2)     // Catch:{ all -> 0x011a }
            java.lang.String r14 = "IMEI"
            r1.putString(r14, r5)     // Catch:{ all -> 0x011a }
            java.lang.String r14 = "IMSI"
            r1.putString(r14, r6)     // Catch:{ all -> 0x011a }
            java.lang.String r14 = "VirtualImei"
            r1.putString(r14, r3)     // Catch:{ all -> 0x011a }
            java.lang.String r14 = "VirtualImsi"
            r1.putString(r14, r4)     // Catch:{ all -> 0x011a }
            monitor-exit(r0)
            return r1
        L_0x011a:
            r14 = move-exception
            monitor-exit(r0)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.cashier.utils.TidDgree.loadTid(android.content.Context):android.os.Bundle");
    }

    private static boolean isIllegal(String str, String str2, String str3, String str4) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4);
    }
}
