package com.xiaomi.metoknlp.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

class e extends BroadcastReceiver {
    final /* synthetic */ c a;

    private e(c cVar) {
        this.a = cVar;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null && action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                if (networkInfo != null) {
                    if (networkInfo.isConnected()) {
                        this.a.c.obtainMessage(200, networkInfo).sendToTarget();
                    } else if (networkInfo.getState() == State.DISCONNECTED) {
                        this.a.c.obtainMessage(201, networkInfo).sendToTarget();
                    }
                }
            }
        }
    }
}
