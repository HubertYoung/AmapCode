package com.hmt.analytics.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class KeepReceiver extends BroadcastReceiver {
    private static final String a = "KeepReceiver";

    public void onReceive(Context context, Intent intent) {
        if (intent != null && context != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                char c = 65535;
                switch (action.hashCode()) {
                    case -1886648615:
                        if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                            c = 4;
                            break;
                        }
                        break;
                    case -1514214344:
                        if (action.equals("android.intent.action.MEDIA_MOUNTED")) {
                            c = 2;
                            break;
                        }
                        break;
                    case -1172645946:
                        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 798292259:
                        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 823795052:
                        if (action.equals("android.intent.action.USER_PRESENT")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 1019184907:
                        if (action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        String str = (String) ewp.b(context.getApplicationContext(), "sopath", "path", "");
                        if (!TextUtils.isEmpty(str)) {
                            ewk.a(context.getApplicationContext(), str);
                            break;
                        }
                        break;
                }
            }
        }
    }
}
