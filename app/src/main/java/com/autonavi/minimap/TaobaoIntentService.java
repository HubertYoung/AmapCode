package com.autonavi.minimap;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.controller.IPushAidl;
import com.autonavi.minimap.controller.IPushAidl.Stub;
import com.taobao.agoo.TaobaoBaseIntentService;
import org.json.JSONException;
import org.json.JSONObject;

public class TaobaoIntentService extends TaobaoBaseIntentService {
    /* access modifiers changed from: private */
    public IPushAidl a;
    private boolean b = false;
    private ServiceConnection c = new ServiceConnection() {
        public final void onServiceDisconnected(ComponentName componentName) {
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TaobaoIntentService.this.a = Stub.asInterface(iBinder);
        }
    };

    public void onRegistered(Context context, String str) {
    }

    public void onUnregistered(Context context, String str) {
    }

    public void onCreate() {
        super.onCreate();
        StringBuilder sb = new StringBuilder("onCreate => isBindService=");
        sb.append(this.b);
        AMapLog.i("TaobaoIntentService", sb.toString());
        Intent intent = new Intent();
        intent.setAction("com.autonavi.minimap.controller.PushAidlService");
        intent.setClassName("com.autonavi.minimap", "com.autonavi.minimap.controller.PushAidlService");
        this.b = bindService(intent, this.c, 1);
        StringBuilder sb2 = new StringBuilder("bindAidlService => isBindService=");
        sb2.append(this.b);
        AMapLog.i("TaobaoIntentService", sb2.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x007d, code lost:
        if (r9.b != false) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x007f, code lost:
        unbindService(r9.c);
        r9.b = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0086, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00c1, code lost:
        if (r9.b == false) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00c4, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMessage(android.content.Context r10, android.content.Intent r11) {
        /*
            r9 = this;
            java.lang.String r0 = "task_id"
            java.lang.String r0 = r11.getStringExtra(r0)
            java.lang.String r1 = "id"
            java.lang.String r1 = r11.getStringExtra(r1)
            java.lang.String r2 = "body"
            java.lang.String r11 = r11.getStringExtra(r2)
            java.lang.String r2 = "basemap.notification"
            java.lang.String r3 = "TaobaoIntentService.onMessage"
            java.lang.String r4 = "message:"
            java.lang.String r5 = java.lang.String.valueOf(r11)
            java.lang.String r4 = r4.concat(r5)
            com.amap.bundle.logs.AMapLog.info(r2, r3, r4)
            java.lang.String r2 = "pushMessage--->"
            java.lang.String r3 = "TaobaoIntentService"
            com.amap.bundle.logs.AMapLog.i(r3, r2)
            com.autonavi.minimap.controller.IPushAidl r2 = r9.a
            if (r2 != 0) goto L_0x0033
            defpackage.dbr.a(r10, r0, r1, r11)
            return
        L_0x0033:
            r2 = 0
            com.autonavi.minimap.controller.IPushAidl r3 = r9.a     // Catch:{ Exception -> 0x0089 }
            boolean r3 = r3.pushIsShow()     // Catch:{ Exception -> 0x0089 }
            com.autonavi.minimap.controller.IPushAidl r4 = r9.a     // Catch:{ Exception -> 0x0089 }
            int r4 = r4.getMiniProgromNewComingCount()     // Catch:{ Exception -> 0x0089 }
            java.lang.String r5 = "notificationIsShow = "
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r5 = r5.concat(r6)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r6 = "TaobaoIntentService"
            com.amap.bundle.logs.AMapLog.i(r6, r5)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r5 = "basemap.notification"
            java.lang.String r6 = "TaobaoIntentService.pushMessage"
            java.lang.String r7 = "notificationIsShow:"
            java.lang.String r8 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r7 = r7.concat(r8)     // Catch:{ Exception -> 0x0089 }
            com.amap.bundle.logs.AMapLog.info(r5, r6, r7)     // Catch:{ Exception -> 0x0089 }
            if (r3 == 0) goto L_0x0065
            defpackage.dbr.a(r10, r0, r1, r11, r4)     // Catch:{ Exception -> 0x0089 }
        L_0x0065:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "unbindService，"
            r10.<init>(r11)
            boolean r11 = r9.b
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            java.lang.String r11 = "TaobaoIntentService"
            com.amap.bundle.logs.AMapLog.i(r11, r10)
            boolean r10 = r9.b
            if (r10 == 0) goto L_0x00c4
        L_0x007f:
            android.content.ServiceConnection r10 = r9.c
            r9.unbindService(r10)
            r9.b = r2
            return
        L_0x0087:
            r10 = move-exception
            goto L_0x00c5
        L_0x0089:
            r3 = move-exception
            java.lang.String r4 = "basemap.notification"
            java.lang.String r5 = "TaobaoIntentService.pushMessage"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0087 }
            java.lang.String r7 = "Exception:"
            r6.<init>(r7)     // Catch:{ all -> 0x0087 }
            java.lang.String r7 = r3.toString()     // Catch:{ all -> 0x0087 }
            r6.append(r7)     // Catch:{ all -> 0x0087 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0087 }
            com.amap.bundle.logs.AMapLog.info(r4, r5, r6)     // Catch:{ all -> 0x0087 }
            defpackage.dbr.a(r10, r0, r1, r11)     // Catch:{ all -> 0x0087 }
            r3.printStackTrace()     // Catch:{ all -> 0x0087 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "unbindService，"
            r10.<init>(r11)
            boolean r11 = r9.b
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            java.lang.String r11 = "TaobaoIntentService"
            com.amap.bundle.logs.AMapLog.i(r11, r10)
            boolean r10 = r9.b
            if (r10 == 0) goto L_0x00c4
            goto L_0x007f
        L_0x00c4:
            return
        L_0x00c5:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "unbindService，"
            r11.<init>(r0)
            boolean r0 = r9.b
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            java.lang.String r0 = "TaobaoIntentService"
            com.amap.bundle.logs.AMapLog.i(r0, r11)
            boolean r11 = r9.b
            if (r11 == 0) goto L_0x00e6
            android.content.ServiceConnection r11 = r9.c
            r9.unbindService(r11)
            r9.b = r2
        L_0x00e6:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.TaobaoIntentService.onMessage(android.content.Context, android.content.Intent):void");
    }

    public void onError(Context context, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00065", "B002", jSONObject);
    }

    public void onDestroy() {
        super.onDestroy();
        StringBuilder sb = new StringBuilder("onDestroy--->isBindService = ");
        sb.append(this.b);
        AMapLog.i("TaobaoIntentService", sb.toString());
        if (this.b) {
            unbindService(this.c);
            this.b = false;
        }
    }
}
