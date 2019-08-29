package com.amap.api.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.amap.location.common.d.a;

public class BootEventReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            try {
                context.startService(new Intent(context, AMapService.class));
            } catch (Exception e) {
                a.a((Throwable) e);
            }
        }
    }
}
