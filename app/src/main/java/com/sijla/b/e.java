package com.sijla.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class e {
    private Context a;
    private a b = new a();
    /* access modifiers changed from: private */
    public b c;

    class a extends BroadcastReceiver {
        /* access modifiers changed from: private */
        public String b;

        private a() {
            this.b = null;
        }

        public void onReceive(Context context, final Intent intent) {
            try {
                com.sijla.a.a.a(new Runnable() {
                    public void run() {
                        a.this.b = intent.getAction();
                        if ("android.intent.action.SCREEN_OFF".equals(a.this.b)) {
                            e.this.c.h();
                        } else if ("android.intent.action.USER_PRESENT".equals(a.this.b)) {
                            e.this.c.g();
                        } else if ("android.intent.action.ACTION_POWER_CONNECTED".equals(a.this.b)) {
                            e.this.c.e();
                        } else if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(a.this.b)) {
                            e.this.c.f();
                        } else {
                            if ("android.intent.action.BATTERY_CHANGED".equals(a.this.b)) {
                                e.this.c.a(intent);
                            }
                        }
                    }
                });
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public interface b {
        void a(Intent intent);

        void e();

        void f();

        void g();

        void h();
    }

    public e(Context context) {
        this.a = context;
    }

    public void a(b bVar) {
        this.c = bVar;
        a();
    }

    private void a() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
            this.a.registerReceiver(this.b, intentFilter);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
