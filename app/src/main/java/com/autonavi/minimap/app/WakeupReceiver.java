package com.autonavi.minimap.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.amap.bundle.logs.AMapLog;

public class WakeupReceiver extends BroadcastReceiver {
    private static volatile ckd a;

    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder("WakeupReceiver --- action = ");
        sb.append(intent.getAction());
        AMapLog.i("BroadcastCompat", sb.toString());
        try {
            ckd a2 = a();
            if (a2 != null) {
                a2.a();
            }
        } catch (Throwable unused) {
        }
    }

    private static ckd a() {
        if (a == null) {
            synchronized (WakeupReceiver.class) {
                if (a == null) {
                    a = new ckd();
                }
            }
        }
        return a;
    }
}
