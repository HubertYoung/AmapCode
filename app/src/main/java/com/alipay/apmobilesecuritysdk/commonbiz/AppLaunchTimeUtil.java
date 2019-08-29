package com.alipay.apmobilesecuritysdk.commonbiz;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.security.mobile.module.localstorage.SecurityStorageUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class AppLaunchTimeUtil {
    public static String a(Context context) {
        String readData = SecurityStorageUtils.readData(context, "wallet_times", LogItem.MM_C15_K4_TIME);
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray(readData);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = (JSONObject) jSONArray.get(i);
                String next = jSONObject2.keys().next();
                jSONObject.put(next, jSONObject2.get(next));
            }
        } catch (Exception unused) {
        }
        return jSONObject.toString();
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0040 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void b(android.content.Context r8) {
        /*
            com.alipay.security.mobile.module.deviceinfo.AppInfo r0 = com.alipay.security.mobile.module.deviceinfo.AppInfo.getInstance()     // Catch:{ Exception -> 0x00aa }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x00aa }
            r1.<init>()     // Catch:{ Exception -> 0x00aa }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00aa }
            r2.<init>()     // Catch:{ Exception -> 0x00aa }
            java.lang.String r3 = r8.getPackageName()     // Catch:{ Exception -> 0x00aa }
            r2.append(r3)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r3 = "_"
            r2.append(r3)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r0 = r0.getAppVersion(r8)     // Catch:{ Exception -> 0x00aa }
            r2.append(r0)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r0 = r2.toString()     // Catch:{ Exception -> 0x00aa }
            java.lang.String r2 = "wallet_times"
            java.lang.String r3 = "t"
            java.lang.String r2 = com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.readData(r8, r2, r3)     // Catch:{ Exception -> 0x00aa }
            org.json.JSONArray r3 = new org.json.JSONArray     // Catch:{ Exception -> 0x00aa }
            r3.<init>()     // Catch:{ Exception -> 0x00aa }
            boolean r4 = com.alipay.security.mobile.module.commonutils.CommonUtils.isNotBlank(r2)     // Catch:{ Exception -> 0x00aa }
            if (r4 == 0) goto L_0x0045
            org.json.JSONArray r3 = new org.json.JSONArray     // Catch:{ Exception -> 0x0040 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0040 }
            goto L_0x0045
        L_0x0040:
            org.json.JSONArray r3 = new org.json.JSONArray     // Catch:{ Exception -> 0x00aa }
            r3.<init>()     // Catch:{ Exception -> 0x00aa }
        L_0x0045:
            int r2 = r3.length()     // Catch:{ Exception -> 0x00aa }
            r4 = 0
            r5 = 0
        L_0x004b:
            if (r5 >= r2) goto L_0x005e
            org.json.JSONObject r6 = r3.getJSONObject(r5)     // Catch:{ Exception -> 0x00aa }
            boolean r7 = r6.has(r0)     // Catch:{ Exception -> 0x00aa }
            if (r7 == 0) goto L_0x0058
            return
        L_0x0058:
            r1.add(r6)     // Catch:{ Exception -> 0x00aa }
            int r5 = r5 + 1
            goto L_0x004b
        L_0x005e:
            int r2 = r1.size()     // Catch:{ Exception -> 0x00aa }
            r3 = 5
            if (r2 < r3) goto L_0x0068
            r1.remove(r4)     // Catch:{ Exception -> 0x00aa }
        L_0x0068:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x00aa }
            r2.<init>()     // Catch:{ Exception -> 0x00aa }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00aa }
            r3.<init>()     // Catch:{ Exception -> 0x00aa }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00aa }
            r3.append(r4)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00aa }
            r2.put(r0, r3)     // Catch:{ Exception -> 0x00aa }
            r1.add(r2)     // Catch:{ Exception -> 0x00aa }
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ Exception -> 0x00aa }
            r0.<init>()     // Catch:{ Exception -> 0x00aa }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Exception -> 0x00aa }
        L_0x008c:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x00aa }
            if (r2 == 0) goto L_0x009c
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x00aa }
            org.json.JSONObject r2 = (org.json.JSONObject) r2     // Catch:{ Exception -> 0x00aa }
            r0.put(r2)     // Catch:{ Exception -> 0x00aa }
            goto L_0x008c
        L_0x009c:
            java.lang.String r1 = "wallet_times"
            java.lang.String r2 = "t"
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00aa }
            com.alipay.security.mobile.module.localstorage.SecurityStorageUtils.writeData(r8, r1, r2, r0)     // Catch:{ Exception -> 0x00aa }
            return
        L_0x00aa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.commonbiz.AppLaunchTimeUtil.b(android.content.Context):void");
    }
}
