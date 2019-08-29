package com.yunos.carkitservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.os.Parcelable;

public class WifiAdmin$1 extends BroadcastReceiver {
    final /* synthetic */ fcs a;

    public WifiAdmin$1(fcs fcs) {
        this.a = fcs;
    }

    public void onReceive(Context context, Intent intent) {
        State state;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        State state2 = State.DISCONNECTED;
        Parcelable parcelableExtra = intent.getParcelableExtra("networkInfo");
        if (parcelableExtra != null) {
            state = ((NetworkInfo) parcelableExtra).getState();
        } else {
            state = connectivityManager.getNetworkInfo(1).getState();
        }
        "state=".concat(String.valueOf(state));
        if (state == State.CONNECTED) {
            WifiInfo connectionInfo = this.a.a.getConnectionInfo();
            this.a.c.obtainMessage(4102, fcs.a(connectionInfo.getSSID())).sendToTarget();
            new StringBuilder("connected to wifi ").append(connectionInfo.getSSID());
            return;
        }
        if (state == State.DISCONNECTED) {
            this.a.c.obtainMessage(4113).sendToTarget();
        }
    }
}
