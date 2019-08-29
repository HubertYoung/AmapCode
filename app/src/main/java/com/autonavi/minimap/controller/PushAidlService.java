package com.autonavi.minimap.controller;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.minimap.controller.IPushAidl.Stub;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;

public class PushAidlService extends Service {
    private Stub a = new Stub() {
        public int getMiniProgromNewComingCount() {
            try {
                return Integer.parseInt(new MapSharePreference((String) "namespace_message_big_pear").getStringValue("key_message_big_pear_newcoming", "0"));
            } catch (NumberFormatException unused) {
                return 0;
            }
        }

        public boolean pushIsShow() {
            boolean z;
            boolean z2 = true;
            try {
                boolean booleanValue = new MapSharePreference((String) KEY_TYPE.PUSHSTATE).getBooleanValue("push_setting", true);
                dfm dfm = (dfm) ank.a(dfm.class);
                boolean b = dfm != null ? dfm.b() : false;
                PushAidlService.a("----->isStartingNavi=".concat(String.valueOf(b)));
                try {
                    bax bax = (bax) a.a.a(bax.class);
                    if (bax != null) {
                        z = bax.d();
                        PushAidlService.a("----->isRidingOrWalking=".concat(String.valueOf(z)));
                        if (booleanValue || b || z) {
                            z2 = false;
                        }
                        PushAidlService.a("----->pushShow=".concat(String.valueOf(z2)));
                        return z2;
                    }
                } catch (Exception unused) {
                }
                z = false;
                PushAidlService.a("----->isRidingOrWalking=".concat(String.valueOf(z)));
                if (booleanValue) {
                }
                z2 = false;
                PushAidlService.a("----->pushShow=".concat(String.valueOf(z2)));
                return z2;
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable unused2) {
            }
            return true;
        }
    };

    public void onDestroy() {
        super.onDestroy();
        AMapLog.i("----xing-->AidlService", "onDestroy");
    }

    public static void a(String str) {
        AMapLog.i("----xing-->AidlService", str);
    }

    public void onCreate() {
        AMapLog.i("----xing-->AidlService", "onCreate");
        super.onCreate();
    }

    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        AMapLog.i("----xing-->AidlService", "bindService");
        return super.bindService(intent, serviceConnection, i);
    }

    public void unbindService(ServiceConnection serviceConnection) {
        AMapLog.i("----xing-->AidlService", "unbindService");
        super.unbindService(serviceConnection);
    }

    public IBinder onBind(Intent intent) {
        AMapLog.i("----xing-->AidlService", "onBind");
        return this.a;
    }
}
