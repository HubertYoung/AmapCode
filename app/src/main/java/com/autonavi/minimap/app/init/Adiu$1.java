package com.autonavi.minimap.app.init;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;

public class Adiu$1 extends BroadcastReceiver {
    final /* synthetic */ Application a;
    final /* synthetic */ ckj b;

    public Adiu$1(ckj ckj, Application application) {
        this.b = ckj;
        this.a = application;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getIntExtra("grantResult", -1) == 0) {
            ik a2 = ik.a();
            Application application = this.a;
            if (application != null) {
                if (!TextUtils.isEmpty(a2.d)) {
                    chw.a(application, ik.a, a2.d);
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder("flushAdiuToFile:");
                        sb.append(a2.d);
                        AMapLog.info("paas.adiu", "AdiuService", sb.toString());
                    }
                } else if (a2.c != null) {
                    a2.c.a(new Runnable() {
                        public final void run() {
                            ik.this.a(true);
                        }
                    });
                }
            }
            if (bno.a) {
                AMapLog.debug("paas.main", "Adiu", "syncAdiu");
            }
        }
    }
}
