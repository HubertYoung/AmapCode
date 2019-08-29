package com.amap.api.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.amap.location.common.b;
import com.amap.location.sdk.BuildConfig;
import com.amap.location.sdk.e.d;

public class AMapService extends Service {
    a a;
    private Context b;

    public void onCreate() {
        super.onCreate();
        this.b = getApplicationContext();
        if (this.b == null) {
            this.b = this;
        }
        b.a(this.b);
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.getPackageName());
        sb.append("_remote");
        b.d(sb.toString());
        b.b("ABKLWEH8H9LH09NLB5CCAGHK78BYZ89");
        if ("amap_auto".equalsIgnoreCase(b.c())) {
            b.a(3);
        } else {
            b.a(0);
            d.a(this.b);
        }
        b.a((String) BuildConfig.VERSION_NAME);
        this.a = a.a(this.b);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        this.a.a(intent, i, i2);
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return this.a.a(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        this.a.a();
    }
}
