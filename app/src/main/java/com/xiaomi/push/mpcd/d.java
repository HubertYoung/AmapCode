package com.xiaomi.push.mpcd;

import android.content.Context;
import android.content.IntentFilter;
import com.xiaomi.channel.commonutils.logger.b;

public class d {
    private static IntentFilter a() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        return intentFilter;
    }

    public static void a(Context context) {
        g.a(context).a();
        try {
            context.registerReceiver(new BroadcastActionsReceiver(), a());
        } catch (Throwable th) {
            b.a(th);
        }
    }
}
