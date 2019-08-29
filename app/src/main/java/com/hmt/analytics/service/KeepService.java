package com.hmt.analytics.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.text.TextUtils;
import com.hmt.analytics.task.WaTask;

public class KeepService extends Service {
    private static final String a = "KeepService";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        euw.a((String) "KeepService is Created");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(ewc.b);
        sb.append("-9");
        ewc.b = sb.toString();
        euw.a((String) "onStartCommand");
        if (VERSION.SDK_INT < 18) {
            startForeground(1001, new Notification());
        } else if (VERSION.SDK_INT > 18) {
            int i3 = VERSION.SDK_INT;
        }
        if (intent != null) {
            String stringExtra = intent.getStringExtra("path");
            if (!TextUtils.isEmpty(stringExtra)) {
                ewp.a(getApplicationContext(), "sopath", "path", stringExtra);
                WaTask.a(getApplicationContext(), stringExtra);
            }
        } else {
            String str = (String) ewp.b(getApplicationContext(), "sopath", "");
            if (!TextUtils.isEmpty(str)) {
                WaTask.a(getApplicationContext(), str);
            }
        }
        return 1;
    }
}
