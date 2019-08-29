package com.autonavi.minimap.dexfilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DexReceiverProxy extends BroadcastReceiver {
    static {
        drx.a("--DexReceiverProxy.static initializer :");
    }

    public DexReceiverProxy() {
        drx.a("--DexReceiverProxy.DexReceiverProxy :");
    }

    public void onReceive(Context context, Intent intent) {
        drx.a("--DexReceiverProxy.onReceive :");
    }
}
