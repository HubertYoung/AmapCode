package com.autonavi.minimap.qrcode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

final class InactivityTimer {
    private static final String a = "InactivityTimer";
    /* access modifiers changed from: private */
    public final Activity b;
    private AsyncTask<Object, Object, Object> c;

    final class PowerStatusReceiver extends BroadcastReceiver {
        final /* synthetic */ InactivityTimer a;

        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                if (intent.getIntExtra("plugged", -1) <= 0) {
                    this.a.a();
                    return;
                }
                this.a.b();
            }
        }
    }

    final class a extends AsyncTask<Object, Object, Object> {
        private a() {
        }

        /* synthetic */ a(InactivityTimer inactivityTimer, byte b) {
            this();
        }

        /* access modifiers changed from: protected */
        public final Object doInBackground(Object... objArr) {
            try {
                Thread.sleep(300000);
                InactivityTimer.this.b.finish();
            } catch (InterruptedException unused) {
            }
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a() {
        b();
        this.c = new a(this, 0);
        this.c.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }

    /* access modifiers changed from: private */
    public synchronized void b() {
        AsyncTask<Object, Object, Object> asyncTask = this.c;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.c = null;
        }
    }
}
