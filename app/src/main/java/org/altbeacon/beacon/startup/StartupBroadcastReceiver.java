package org.altbeacon.beacon.startup;

import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import java.util.List;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.service.r;

public class StartupBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        d.a("StartupBroadcastReceiver", "onReceive called in startup broadcast receiver", new Object[0]);
        if (VERSION.SDK_INT < 18) {
            d.c("StartupBroadcastReceiver", "Not starting up beacon service because we do not have API version 18 (Android 4.3).  We have: %s", Integer.valueOf(VERSION.SDK_INT));
            return;
        }
        g beaconManager = g.a(context.getApplicationContext());
        if (beaconManager.e() || beaconManager.f()) {
            int bleCallbackType = intent.getIntExtra("android.bluetooth.le.extra.CALLBACK_TYPE", -1);
            if (bleCallbackType != -1) {
                d.a("StartupBroadcastReceiver", "Passive background scan callback type: " + bleCallbackType, new Object[0]);
                d.a("StartupBroadcastReceiver", "got Android O background scan via intent", new Object[0]);
                int errorCode = intent.getIntExtra("android.bluetooth.le.extra.ERROR_CODE", -1);
                if (errorCode != -1) {
                    d.c("StartupBroadcastReceiver", "Passive background scan failed.  Code; " + errorCode, new Object[0]);
                }
                r.a().a(context, (List<ScanResult>) intent.getParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT"));
            } else if (intent.getBooleanExtra("wakeup", false)) {
                d.a("StartupBroadcastReceiver", "got wake up intent", new Object[0]);
            } else {
                d.a("StartupBroadcastReceiver", "Already started.  Ignoring intent: %s of type: %s", intent, intent.getStringExtra("wakeup"));
            }
        } else {
            d.a("StartupBroadcastReceiver", "No consumers are bound.  Ignoring broadcast receiver.", new Object[0]);
        }
    }
}
