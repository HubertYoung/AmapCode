package com.autonavi.ae.gmap.maploader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkState$1 extends BroadcastReceiver {
    final /* synthetic */ amt a;

    public NetworkState$1(amt amt) {
        this.a = amt;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.a.a != null) {
            this.a.a.a(context);
        }
    }
}
