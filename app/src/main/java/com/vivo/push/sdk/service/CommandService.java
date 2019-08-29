package com.vivo.push.sdk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

public class CommandService extends Service {
    public void onCreate() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" -- oncreate ");
        sb.append(getPackageName());
        fat.c((String) "CommandService", sb.toString());
        super.onCreate();
        ezz.a().a(getApplicationContext());
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            stopSelf();
            return 2;
        } else if (!a(intent.getAction())) {
            StringBuilder sb = new StringBuilder();
            sb.append(getPackageName());
            sb.append(" receive invalid action ");
            sb.append(intent.getAction());
            fat.a((String) "CommandService", sb.toString());
            stopSelf();
            return 2;
        } else {
            try {
                String stringExtra = intent.getStringExtra("command_type");
                if (!TextUtils.isEmpty(stringExtra) && stringExtra.equals("reflect_receiver")) {
                    ezz.a().a(intent);
                }
            } catch (Exception e) {
                fat.a("CommandService", "onStartCommand -- error", e);
            }
            stopSelf();
            return 2;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(String str) {
        return "com.vivo.pushservice.action.RECEIVE".equals(str);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        fat.c((String) "CommandService", (String) "onBind initSuc: ");
        return null;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
