package com.amap.bundle.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AmapBatteryManager$3 extends BroadcastReceiver {
    final /* synthetic */ afq a;

    public AmapBatteryManager$3(afq afq) {
        this.a = afq;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                char c = 65535;
                int hashCode = action.hashCode();
                if (hashCode != -1980154005) {
                    if (hashCode != -1538406691) {
                        if (hashCode == 490310653 && action.equals("android.intent.action.BATTERY_LOW")) {
                            c = 1;
                        }
                    } else if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                        c = 0;
                    }
                } else if (action.equals("android.intent.action.BATTERY_OKAY")) {
                    c = 2;
                }
                switch (c) {
                    case 0:
                        afq.c(this.a);
                        return;
                    case 1:
                        afq.c(this.a);
                        return;
                    case 2:
                        afq.c(this.a);
                        break;
                }
            }
        }
    }
}
