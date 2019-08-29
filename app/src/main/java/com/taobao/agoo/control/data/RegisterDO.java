package com.taobao.agoo.control.data;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.JsonUtility.JsonObjectBuilder;

public class RegisterDO extends BaseDO {
    public static final String JSON_CMD_REGISTER = "register";
    private static final String TAG = "RegisterDO";
    public String appKey;
    public String appVersion;
    public String c0;
    public String c1;
    public String c2;
    public String c3;
    public String c4;
    public String c5;
    public String c6;
    public String notifyEnable;
    public String packageName;
    public String romInfo;
    public String sdkVersion = "221";
    public String ttid;
    public String utdid;

    public byte[] buildData() {
        try {
            String jSONObject = new JsonObjectBuilder().put((String) BaseDO.JSON_CMD, this.cmd).put((String) "appKey", this.appKey).put((String) "utdid", this.utdid).put((String) "appVersion", this.appVersion).put((String) Constants.KEY_SDK_VERSION, this.sdkVersion).put((String) "ttid", this.ttid).put((String) "packageName", this.packageName).put((String) "notifyEnable", this.notifyEnable).put((String) "romInfo", this.romInfo).put((String) "c0", this.c0).put((String) "c1", this.c1).put((String) "c2", this.c2).put((String) "c3", this.c3).put((String) "c4", this.c4).put((String) "c5", this.c5).put((String) "c6", this.c6).build().toString();
            ALog.i(TAG, "buildData", "data", jSONObject);
            return jSONObject.getBytes("utf-8");
        } catch (Throwable th) {
            ALog.e(TAG, "buildData", th, new Object[0]);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a8, code lost:
        if (r6 != null) goto L_0x00aa;
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] buildRegister(android.content.Context r8, java.lang.String r9, java.lang.String r10) {
        /*
            r0 = 1
            r1 = 0
            r2 = 0
            java.lang.String r3 = com.taobao.accs.utl.UtilityImpl.getDeviceId(r8)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            java.lang.String r4 = r8.getPackageName()     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            com.taobao.accs.client.GlobalClientInfo r5 = com.taobao.accs.client.GlobalClientInfo.getInstance(r8)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            android.content.pm.PackageInfo r5 = r5.getPackageInfo()     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            java.lang.String r5 = r5.versionName     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            boolean r6 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            if (r6 != 0) goto L_0x0072
            boolean r6 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            if (r6 != 0) goto L_0x0072
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            if (r6 == 0) goto L_0x0028
            goto L_0x0072
        L_0x0028:
            com.taobao.agoo.control.data.RegisterDO r6 = new com.taobao.agoo.control.data.RegisterDO     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r6.<init>()     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            java.lang.String r7 = "register"
            r6.cmd = r7     // Catch:{ Throwable -> 0x0070 }
            r6.appKey = r9     // Catch:{ Throwable -> 0x0070 }
            r6.utdid = r3     // Catch:{ Throwable -> 0x0070 }
            r6.appVersion = r5     // Catch:{ Throwable -> 0x0070 }
            r6.ttid = r10     // Catch:{ Throwable -> 0x0070 }
            r6.packageName = r4     // Catch:{ Throwable -> 0x0070 }
            java.lang.String r9 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x0070 }
            r6.c0 = r9     // Catch:{ Throwable -> 0x0070 }
            java.lang.String r9 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x0070 }
            r6.c1 = r9     // Catch:{ Throwable -> 0x0070 }
            java.lang.String r9 = com.taobao.accs.utl.AdapterUtilityImpl.isNotificationEnabled(r8)     // Catch:{ Throwable -> 0x0070 }
            r6.notifyEnable = r9     // Catch:{ Throwable -> 0x0070 }
            com.taobao.accs.utl.RomInfoCollecter r9 = com.taobao.accs.utl.RomInfoCollecter.getCollecter()     // Catch:{ Throwable -> 0x0070 }
            java.lang.String r9 = r9.collect()     // Catch:{ Throwable -> 0x0070 }
            r6.romInfo = r9     // Catch:{ Throwable -> 0x0070 }
            java.lang.String r9 = "phone"
            java.lang.Object r8 = r8.getSystemService(r9)     // Catch:{ Throwable -> 0x0070 }
            android.telephony.TelephonyManager r8 = (android.telephony.TelephonyManager) r8     // Catch:{ Throwable -> 0x0070 }
            if (r8 == 0) goto L_0x0062
            java.lang.String r9 = r8.getDeviceId()     // Catch:{ Throwable -> 0x0070 }
            goto L_0x0063
        L_0x0062:
            r9 = r2
        L_0x0063:
            r6.c2 = r9     // Catch:{ Throwable -> 0x0070 }
            if (r8 == 0) goto L_0x006c
            java.lang.String r8 = r8.getSubscriberId()     // Catch:{ Throwable -> 0x0070 }
            goto L_0x006d
        L_0x006c:
            r8 = r2
        L_0x006d:
            r6.c3 = r8     // Catch:{ Throwable -> 0x0070 }
            goto L_0x00aa
        L_0x0070:
            r8 = move-exception
            goto L_0x0099
        L_0x0072:
            java.lang.String r8 = "RegisterDO"
            java.lang.String r10 = "buildRegister param null"
            r4 = 6
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            java.lang.String r6 = "appKey"
            r4[r1] = r6     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r4[r0] = r9     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r9 = 2
            java.lang.String r6 = "utdid"
            r4[r9] = r6     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r9 = 3
            r4[r9] = r3     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r9 = 4
            java.lang.String r3 = "appVersion"
            r4[r9] = r3     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            r9 = 5
            r4[r9] = r5     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            com.taobao.accs.utl.ALog.e(r8, r10, r4)     // Catch:{ Throwable -> 0x0097, all -> 0x0094 }
            return r2
        L_0x0094:
            r8 = move-exception
            r6 = r2
            goto L_0x00b0
        L_0x0097:
            r8 = move-exception
            r6 = r2
        L_0x0099:
            java.lang.String r9 = "RegisterDO"
            java.lang.String r10 = "buildRegister"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x00af }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x00af }
            r0[r1] = r8     // Catch:{ all -> 0x00af }
            com.taobao.accs.utl.ALog.w(r9, r10, r0)     // Catch:{ all -> 0x00af }
            if (r6 == 0) goto L_0x00ae
        L_0x00aa:
            byte[] r2 = r6.buildData()
        L_0x00ae:
            return r2
        L_0x00af:
            r8 = move-exception
        L_0x00b0:
            if (r6 == 0) goto L_0x00b5
            r6.buildData()
        L_0x00b5:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.control.data.RegisterDO.buildRegister(android.content.Context, java.lang.String, java.lang.String):byte[]");
    }
}
