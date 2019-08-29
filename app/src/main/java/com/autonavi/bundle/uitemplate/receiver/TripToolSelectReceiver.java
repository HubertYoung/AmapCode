package com.autonavi.bundle.uitemplate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

public class TripToolSelectReceiver extends BroadcastReceiver {
    private static a a;
    private static TripToolSelectReceiver b;

    public interface a {
        void a();
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (((action.hashCode() == 212433707 && action.equals("com.autonavi.minimap.action.TRIP_TOOL_SELECTED")) ? (char) 0 : 65535) == 0 && a != null) {
            a.a();
        }
    }

    public static void a(a aVar) {
        a = aVar;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.autonavi.minimap.action.TRIP_TOOL_SELECTED");
        if (b == null) {
            b = new TripToolSelectReceiver();
        }
        Context context = DoNotUseTool.getContext();
        if (context != null) {
            context.registerReceiver(b, intentFilter);
        }
    }

    public static void a() {
        Context context = DoNotUseTool.getContext();
        if (context != null && b != null) {
            context.unregisterReceiver(b);
        }
    }
}
