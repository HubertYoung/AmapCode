package org.altbeacon.beacon.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import java.io.Serializable;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.e;
import org.altbeacon.beacon.g;

/* compiled from: Callback */
public final class b implements Serializable {
    private e a = new e();

    public static boolean a(Context context, String dataName, Bundle data) {
        String action;
        if (g.a(context).f()) {
            if ("rangingData".equals(dataName)) {
                action = "org.altbeacon.beacon.range_notification";
            } else {
                action = "org.altbeacon.beacon.monitor_notification";
            }
            Intent intent = new Intent(action);
            intent.putExtra(dataName, data);
            d.a("Callback", "attempting callback via local broadcast intent: %s", action);
            return LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
        Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName(context.getPackageName(), "org.altbeacon.beacon.BeaconIntentProcessor"));
        intent2.putExtra(dataName, data);
        d.a("Callback", "attempting callback via global broadcast intent: %s", intent2.getComponent());
        try {
            e.a(context, intent2);
            return true;
        } catch (Exception e) {
            d.d("Callback", "Failed attempting to start service: " + intent2.getComponent().flattenToString(), e);
            return false;
        }
    }
}
