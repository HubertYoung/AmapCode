package com.autonavi.common.tool.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStatusOldApiMonitor extends BroadcastReceiver {
    private final bmt a;

    public void onReceive(Context context, Intent intent) {
        int d = this.a.d();
        bmp.b();
        bmp.a((String) "NetworkType", d);
    }
}
