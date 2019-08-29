package com.ta.audid.store;

import com.ta.audid.Variables;
import com.ta.audid.collect.DeviceInfo2;
import com.ta.audid.upload.UtdidKeyFile;
import com.ta.audid.utils.JsonUtils;
import java.util.Map;

public class SdcardDeviceModle {
    private static final String MODULE_GSID = "gsid";
    private static final String MODULE_IMEI = "imei";
    private static final String MODULE_IMSI = "imsi";
    private static Map<String, String> mSdcardDeviceModle;

    public static String getModuleImei() {
        try {
            return getModule("imei");
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getModuleImsi() {
        try {
            return getModule("imsi");
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0076, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void writeSdcardDeviceModle(java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.Class<com.ta.audid.store.SdcardDeviceModle> r0 = com.ta.audid.store.SdcardDeviceModle.class
            monitor-enter(r0)
            com.ta.audid.Variables r1 = com.ta.audid.Variables.getInstance()     // Catch:{ all -> 0x0079 }
            android.content.Context r1 = r1.getContext()     // Catch:{ all -> 0x0079 }
            if (r1 != 0) goto L_0x000f
            monitor-exit(r0)
            return
        L_0x000f:
            boolean r2 = checkSdcardDeviceModle()     // Catch:{ Exception -> 0x0077 }
            if (r2 == 0) goto L_0x0031
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0077 }
            if (r1 != 0) goto L_0x0022
            java.util.Map<java.lang.String, java.lang.String> r1 = mSdcardDeviceModle     // Catch:{ Exception -> 0x0077 }
            java.lang.String r2 = "imei"
            r1.put(r2, r4)     // Catch:{ Exception -> 0x0077 }
        L_0x0022:
            boolean r4 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0077 }
            if (r4 != 0) goto L_0x0075
            java.util.Map<java.lang.String, java.lang.String> r4 = mSdcardDeviceModle     // Catch:{ Exception -> 0x0077 }
            java.lang.String r1 = "imsi"
            r4.put(r1, r5)     // Catch:{ Exception -> 0x0077 }
            monitor-exit(r0)
            return
        L_0x0031:
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ Exception -> 0x0077 }
            r2.<init>()     // Catch:{ Exception -> 0x0077 }
            mSdcardDeviceModle = r2     // Catch:{ Exception -> 0x0077 }
            boolean r2 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0077 }
            if (r2 != 0) goto L_0x0045
            java.util.Map<java.lang.String, java.lang.String> r2 = mSdcardDeviceModle     // Catch:{ Exception -> 0x0077 }
            java.lang.String r3 = "imei"
            r2.put(r3, r4)     // Catch:{ Exception -> 0x0077 }
        L_0x0045:
            boolean r4 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0077 }
            if (r4 != 0) goto L_0x0052
            java.util.Map<java.lang.String, java.lang.String> r4 = mSdcardDeviceModle     // Catch:{ Exception -> 0x0077 }
            java.lang.String r2 = "imsi"
            r4.put(r2, r5)     // Catch:{ Exception -> 0x0077 }
        L_0x0052:
            java.lang.String r4 = com.ta.audid.collect.DeviceInfo2.getAndroidID(r1)     // Catch:{ Exception -> 0x0077 }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0077 }
            if (r5 != 0) goto L_0x0063
            java.util.Map<java.lang.String, java.lang.String> r5 = mSdcardDeviceModle     // Catch:{ Exception -> 0x0077 }
            java.lang.String r1 = "gsid"
            r5.put(r1, r4)     // Catch:{ Exception -> 0x0077 }
        L_0x0063:
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0077 }
            java.util.Map<java.lang.String, java.lang.String> r5 = mSdcardDeviceModle     // Catch:{ Exception -> 0x0077 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0077 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0077 }
            java.lang.String r4 = com.ta.audid.store.UtdidContentUtil.getEncodedContent(r4)     // Catch:{ Exception -> 0x0077 }
            com.ta.audid.upload.UtdidKeyFile.writeSdcardDeviceModleFile(r4)     // Catch:{ Exception -> 0x0077 }
        L_0x0075:
            monitor-exit(r0)
            return
        L_0x0077:
            monitor-exit(r0)
            return
        L_0x0079:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.store.SdcardDeviceModle.writeSdcardDeviceModle(java.lang.String, java.lang.String):void");
    }

    private static synchronized String getModule(String str) {
        synchronized (SdcardDeviceModle.class) {
            if (Variables.getInstance().getContext() == null) {
                return "";
            }
            if (mSdcardDeviceModle != null) {
                String str2 = mSdcardDeviceModle.get(str);
                return str2;
            } else if (checkSdcardDeviceModle()) {
                String str3 = mSdcardDeviceModle.get(str);
                return str3;
            } else {
                clearSdcardDeviceModle();
                return "";
            }
        }
    }

    private static void clearSdcardDeviceModle() {
        try {
            mSdcardDeviceModle.clear();
            mSdcardDeviceModle = null;
            UtdidKeyFile.writeSdcardDeviceModleFile("");
        } catch (Exception unused) {
        }
    }

    private static boolean checkSdcardDeviceModle() {
        if (Variables.getInstance().getContext() == null) {
            return false;
        }
        try {
            Map<String, String> jsonToMap = JsonUtils.jsonToMap(UtdidContentUtil.getDecodedContent(UtdidKeyFile.readSdcardDeviceModleFile()));
            mSdcardDeviceModle = jsonToMap;
            if (DeviceInfo2.getAndroidID(Variables.getInstance().getContext()).equals(jsonToMap.get(MODULE_GSID))) {
                return true;
            }
            clearSdcardDeviceModle();
            return false;
        } catch (Exception unused) {
            clearSdcardDeviceModle();
            return false;
        }
    }
}
