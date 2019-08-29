package org.altbeacon.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import org.altbeacon.beacon.b.d;

/* compiled from: BluetoothCrashResolver */
final class b extends BroadcastReceiver {
    final /* synthetic */ BluetoothCrashResolver a;

    b(BluetoothCrashResolver this$0) {
        this.a = this$0;
    }

    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.bluetooth.adapter.action.DISCOVERY_FINISHED")) {
            if (this.a.a) {
                d.a("BluetoothCrashResolver", "Bluetooth discovery finished", new Object[0]);
                this.a.g();
            } else {
                d.a("BluetoothCrashResolver", "Bluetooth discovery finished (external)", new Object[0]);
            }
        }
        if (action.equals("android.bluetooth.adapter.action.DISCOVERY_STARTED")) {
            if (this.a.a) {
                this.a.b = true;
                d.a("BluetoothCrashResolver", "Bluetooth discovery started", new Object[0]);
            } else {
                d.a("BluetoothCrashResolver", "Bluetooth discovery started (external)", new Object[0]);
            }
        }
        if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
            switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE)) {
                case Integer.MIN_VALUE:
                    d.a("BluetoothCrashResolver", "Bluetooth state is ERROR", new Object[0]);
                    return;
                case 10:
                    d.a("BluetoothCrashResolver", "Bluetooth state is OFF", new Object[0]);
                    this.a.c = SystemClock.elapsedRealtime();
                    return;
                case 11:
                    this.a.d = SystemClock.elapsedRealtime();
                    d.a("BluetoothCrashResolver", "Bluetooth state is TURNING_ON", new Object[0]);
                    return;
                case 12:
                    d.a("BluetoothCrashResolver", "Bluetooth state is ON", new Object[0]);
                    d.a("BluetoothCrashResolver", "Bluetooth was turned off for %s milliseconds", Long.valueOf(this.a.d - this.a.c));
                    if (this.a.d - this.a.c < 600) {
                        this.a.c();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
