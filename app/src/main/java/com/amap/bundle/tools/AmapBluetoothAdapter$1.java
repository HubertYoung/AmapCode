package com.amap.bundle.tools;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.amap.bundle.logs.AMapLog;

public class AmapBluetoothAdapter$1 extends BroadcastReceiver {
    final /* synthetic */ afr a;

    public AmapBluetoothAdapter$1(afr afr) {
        this.a = afr;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED" == intent.getAction()) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
                if (bno.a) {
                    AMapLog.debug("paas.tools", "AmapBluetoothAdapter", "changedState:".concat(String.valueOf(intExtra)));
                }
                if (10 == intExtra) {
                    afr.a(this.a, false);
                }
                return;
            }
            if ("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED" == intent.getAction()) {
                int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", Integer.MIN_VALUE);
                if (bno.a) {
                    AMapLog.debug("paas.tools", "AmapBluetoothAdapter", "connectionState:".concat(String.valueOf(intExtra2)));
                }
                if (2 == intExtra2) {
                    this.a.b = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    afr.a(this.a, true);
                } else if (intExtra2 == 0) {
                    afr.a(this.a, false);
                }
            }
        }
    }
}
