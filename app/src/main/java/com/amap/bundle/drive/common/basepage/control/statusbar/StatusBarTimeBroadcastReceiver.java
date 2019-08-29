package com.amap.bundle.drive.common.basepage.control.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class StatusBarTimeBroadcastReceiver extends BroadcastReceiver {
    public a a = null;

    public interface a {
        void onUpdate();
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ((TextUtils.equals(action, "android.intent.action.TIME_TICK") || TextUtils.equals(action, "android.intent.action.TIMEZONE_CHANGED") || TextUtils.equals(action, "android.intent.action.DATE_CHANGED") || TextUtils.equals(action, "android.intent.action.TIME_SET")) && this.a != null) {
            this.a.onUpdate();
        }
    }
}
