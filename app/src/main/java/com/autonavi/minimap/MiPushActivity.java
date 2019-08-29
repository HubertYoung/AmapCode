package com.autonavi.minimap;

import android.content.Intent;
import com.autonavi.common.utils.Constant;
import com.taobao.agoo.BaseNotifyClickActivity;

public class MiPushActivity extends BaseNotifyClickActivity {
    private static String a = "MiPushActivity-->";

    /* JADX WARNING: Removed duplicated region for block: B:23:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00df  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMessage(android.content.Intent r9) {
        /*
            r8 = this;
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r1 = "push_state"
            r0.<init>(r1)
            java.lang.String r1 = "push_setting"
            r2 = 1
            boolean r0 = r0.getBooleanValue(r1, r2)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            java.lang.String r1 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "pushSetting = "
            r3.<init>(r4)
            r3.append(r0)
            java.lang.String r4 = " ,push intent = "
            r3.append(r4)
            r3.append(r9)
            java.lang.String r3 = r3.toString()
            com.amap.bundle.logs.AMapLog.i(r1, r3)
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x0035
            if (r9 != 0) goto L_0x0038
        L_0x0035:
            r8.a()
        L_0x0038:
            java.lang.String r0 = "body"
            java.lang.String r9 = r9.getStringExtra(r0)
            java.lang.String r0 = a
            java.lang.String r1 = " push message = "
            java.lang.String r3 = java.lang.String.valueOf(r9)
            java.lang.String r1 = r1.concat(r3)
            com.amap.bundle.logs.AMapLog.i(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 == 0) goto L_0x0056
            r8.a()
        L_0x0056:
            r0 = 0
            r1 = 0
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0091 }
            r3.<init>(r9)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r9 = "exts"
            org.json.JSONObject r9 = r3.getJSONObject(r9)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r4 = "mtype"
            int r9 = r9.getInt(r4)     // Catch:{ Exception -> 0x0091 }
            java.lang.String r0 = "url"
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x008c }
            java.lang.String r1 = "basemap.notification"
            java.lang.String r4 = "MiPushActivity.onMessage"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008a }
            java.lang.String r6 = "message:"
            r5.<init>(r6)     // Catch:{ Exception -> 0x008a }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x008a }
            r5.append(r3)     // Catch:{ Exception -> 0x008a }
            java.lang.String r3 = r5.toString()     // Catch:{ Exception -> 0x008a }
            com.amap.bundle.logs.AMapLog.info(r1, r4, r3)     // Catch:{ Exception -> 0x008a }
            goto L_0x009b
        L_0x008a:
            r1 = move-exception
            goto L_0x0095
        L_0x008c:
            r0 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x0095
        L_0x0091:
            r9 = move-exception
            r0 = r1
            r1 = r9
            r9 = 0
        L_0x0095:
            r1.printStackTrace()
            r8.a()
        L_0x009b:
            if (r9 != r2) goto L_0x00df
            android.content.Intent r9 = new android.content.Intent
            r9.<init>()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x00d8
            java.lang.String r1 = "androidamap"
            boolean r1 = r0.startsWith(r1)
            if (r1 != 0) goto L_0x00b8
            java.lang.String r1 = "amapuri"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x00d8
        L_0x00b8:
            java.lang.String r1 = "com.autonavi.map.activity.NewMapActivity"
            r9.setClassName(r8, r1)
            r1 = 268435456(0x10000000, float:2.5243549E-29)
            r9.setFlags(r1)
            java.lang.String r1 = "owner"
            java.lang.String r2 = "umengPush"
            r9.putExtra(r1, r2)
            java.lang.String r1 = "com.autonavi.bundle.notification.INTENT.KEY"
            java.lang.String r2 = "INTENT.FROM.PUSH"
            r9.putExtra(r1, r2)
            android.net.Uri r0 = android.net.Uri.parse(r0)
            r9.setData(r0)
        L_0x00d8:
            r8.startActivity(r9)
            r8.finish()
            return
        L_0x00df:
            r8.a()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.MiPushActivity.onMessage(android.content.Intent):void");
    }

    private void a() {
        Intent intent = new Intent();
        intent.setClassName(this, Constant.LAUNCHER_ACTIVITY_NAME);
        intent.setFlags(268435456);
        intent.putExtra("com.autonavi.bundle.notification.INTENT.KEY", "INTENT.FROM.PUSH");
        startActivity(intent);
        finish();
    }
}
