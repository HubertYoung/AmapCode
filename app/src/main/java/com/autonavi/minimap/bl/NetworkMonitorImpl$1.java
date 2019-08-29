package com.autonavi.minimap.bl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.autonavi.minimap.bl.net.INetworkMonitorObserver;

public class NetworkMonitorImpl$1 extends BroadcastReceiver {
    final /* synthetic */ ctc a;

    public NetworkMonitorImpl$1(ctc ctc) {
        this.a = ctc;
    }

    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            int currentStatus = this.a.getCurrentStatus();
            if (currentStatus != this.a.c) {
                for (INetworkMonitorObserver onNetworkStatusChanged : this.a.d) {
                    onNetworkStatusChanged.onNetworkStatusChanged(this.a.c, currentStatus);
                }
                this.a.c = currentStatus;
            }
        }
    }
}
