package com.hmt.analytics.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* compiled from: HomeWatcher */
public class j {
    public static final String a = "j";
    public Context b;
    public a c;
    /* access modifiers changed from: private */
    public b d;

    /* compiled from: HomeWatcher */
    class a extends BroadcastReceiver {
        final /* synthetic */ j a;

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                String stringExtra = intent.getStringExtra("reason");
                if (!(stringExtra == null || this.a.d == null)) {
                    if (stringExtra.equals("homekey")) {
                        this.a.d;
                    } else if (stringExtra.equals("recentapps")) {
                        this.a.d;
                    }
                }
            }
        }
    }

    /* compiled from: HomeWatcher */
    public interface b {
    }
}
