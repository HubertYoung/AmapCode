package com.hmt.analytics.objects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: WakeMonitorReceiver */
public class o extends BroadcastReceiver {
    /* access modifiers changed from: private */
    public static final String a = "o";
    /* access modifiers changed from: private */
    public ewd b;

    public o(ewd ewd) {
        this.b = ewd;
    }

    public void onReceive(final Context context, final Intent intent) {
        euw.a((String) "WakeMonitorReceiver");
        ewq.b().execute(new Runnable() {
            public final void run() {
                if (o.this.b == null) {
                    o.a;
                    euw.a((String) "Collected:wake monitor task is null!!!!");
                    return;
                }
                String stringExtra = intent.getStringExtra("HMT_CODE");
                String stringExtra2 = intent.getStringExtra("HMT_CHANNEL");
                String stringExtra3 = intent.getStringExtra("HMT_MESSAGE");
                String stringExtra4 = intent.getStringExtra("HMT_SOURCE");
                String stringExtra5 = intent.getStringExtra("HMT_WAKEUP_WAY");
                ewe ewe = new ewe();
                ewe.a("hmt_code", stringExtra);
                ewe.a("hmt_channel", stringExtra2);
                ewe.a("hmt_message", stringExtra3);
                ewe.a("hmt_source", stringExtra4);
                ewe.a("hmt_wakeup_way", stringExtra5);
                ewe.a("wake_id", o.this.b.b);
                euv.a(context, (String) "wake_idmapping", ewe);
                o.this.b.a.add(stringExtra2);
            }
        });
    }
}
