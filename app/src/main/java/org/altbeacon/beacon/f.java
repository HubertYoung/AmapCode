package org.altbeacon.beacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: BeaconLocalBroadcastProcessor */
final class f extends BroadcastReceiver {
    final /* synthetic */ BeaconLocalBroadcastProcessor a;

    f(BeaconLocalBroadcastProcessor this$0) {
        this.a = this$0;
    }

    public final void onReceive(Context context, Intent intent) {
        new m();
        m.a(context, intent);
    }
}
