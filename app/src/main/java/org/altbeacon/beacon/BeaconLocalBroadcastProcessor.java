package org.altbeacon.beacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import org.altbeacon.beacon.b.d;

public class BeaconLocalBroadcastProcessor {
    static int a = 0;
    int b = 0;
    @NonNull
    private Context c;
    private BroadcastReceiver d = new f(this);

    private BeaconLocalBroadcastProcessor() {
    }

    public BeaconLocalBroadcastProcessor(Context context) {
        this.c = context;
    }

    public final void a() {
        a++;
        this.b++;
        d.a("BeaconLocalBroadcastProcessor", "Register calls: global=" + a + " instance=" + this.b, new Object[0]);
        b();
        LocalBroadcastManager.getInstance(this.c).registerReceiver(this.d, new IntentFilter("org.altbeacon.beacon.range_notification"));
        LocalBroadcastManager.getInstance(this.c).registerReceiver(this.d, new IntentFilter("org.altbeacon.beacon.monitor_notification"));
    }

    private void b() {
        LocalBroadcastManager.getInstance(this.c).unregisterReceiver(this.d);
    }
}
