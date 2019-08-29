package com.amap.bundle.drive.common.basepage.control.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class EventBroadCast extends BroadcastReceiver {
    public a a;

    public enum BroadEvent {
        ;

        public abstract String action();
    }

    public interface a {
        void onReceive(BroadEvent broadEvent, Intent intent);
    }

    public void onReceive(Context context, Intent intent) {
        BroadEvent[] values;
        if (this.a != null) {
            String action = intent.getAction();
            for (BroadEvent broadEvent : BroadEvent.values()) {
                if (action.equals(broadEvent.action())) {
                    this.a.onReceive(broadEvent, intent);
                }
            }
        }
    }

    public static IntentFilter a() {
        IntentFilter intentFilter = new IntentFilter();
        for (BroadEvent action : BroadEvent.values()) {
            intentFilter.addAction(action.action());
        }
        return intentFilter;
    }
}
