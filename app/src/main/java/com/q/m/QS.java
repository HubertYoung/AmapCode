package com.q.m;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.sijla.b.d;

public class QS extends Service {
    private static d a;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    public void onCreate() {
        super.onCreate();
        if (a == null) {
            a = new d(getApplication());
        }
        a.a();
    }
}
