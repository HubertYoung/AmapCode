package com.vivo.push.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;

public class PushServiceReceiver extends BroadcastReceiver {
    private static HandlerThread a;
    private static Handler b;
    private static a c = new a();

    static class a implements Runnable {
        private Context a;
        private String b;

        a() {
        }

        public final void run() {
            boolean z;
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.a.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                z = activeNetworkInfo.isConnectedOrConnecting();
            } else {
                z = false;
            }
            if (!z) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.a.getPackageName());
                sb.append(": 无网络  by ");
                sb.append(this.b);
                fat.d("PushServiceReceiver", sb.toString());
                Context context = this.a;
                StringBuilder sb2 = new StringBuilder("触发静态广播:无网络(");
                sb2.append(this.b);
                sb2.append(",");
                sb2.append(this.a.getPackageName());
                sb2.append(")");
                fat.a(context, sb2.toString());
                return;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.a.getPackageName());
            sb3.append(": 执行开始出发动作: ");
            sb3.append(this.b);
            fat.d("PushServiceReceiver", sb3.toString());
            Context context2 = this.a;
            StringBuilder sb4 = new StringBuilder("触发静态广播(");
            sb4.append(this.b);
            sb4.append(",");
            sb4.append(this.a.getPackageName());
            sb4.append(")");
            fat.a(context2, sb4.toString());
            ezv.a().a(this.a);
            if (!ezj.a(this.a).c()) {
                eww.a(this.a);
                eww.a();
            }
        }

        static /* synthetic */ void a(a aVar, Context context, String str) {
            aVar.a = context.getApplicationContext();
            aVar.b = str;
        }
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action) || "android.intent.action.ACTION_POWER_CONNECTED".equals(action) || "android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
            if (a == null) {
                HandlerThread handlerThread = new HandlerThread("PushServiceReceiver");
                a = handlerThread;
                handlerThread.start();
                b = new Handler(a.getLooper());
            }
            StringBuilder sb = new StringBuilder();
            sb.append(context.getPackageName());
            sb.append(": start PushSerevice for by ");
            sb.append(action);
            sb.append("  ; handler : ");
            sb.append(b);
            fat.d("PushServiceReceiver", sb.toString());
            a.a(c, context, action);
            b.removeCallbacks(c);
            b.postDelayed(c, 2000);
        }
    }
}
