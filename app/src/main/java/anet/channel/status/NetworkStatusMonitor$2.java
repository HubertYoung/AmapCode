package anet.channel.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStatusMonitor$2 extends BroadcastReceiver {
    public final void onReceive(Context context, Intent intent) {
        if (cl.a(1)) {
            StringBuilder sb = new StringBuilder("receiver:");
            sb.append(intent.getAction());
            cl.a("awcn.NetworkStatusMonitor", sb.toString(), null, new Object[0]);
        }
        ck.a(new Runnable() {
            public final void run() {
                bl.c();
            }
        });
    }
}
