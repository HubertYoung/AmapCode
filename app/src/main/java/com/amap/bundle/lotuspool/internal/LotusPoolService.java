package com.amap.bundle.lotuspool.internal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.amap.bundle.logs.AMapLog;
import org.jetbrains.annotations.Nullable;

public class LotusPoolService extends Service {
    private static final String a = "LotusPoolService";
    private wt b;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.b = new wt(this);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        int i3 = 3;
        if (intent != null) {
            i3 = intent.getIntExtra("launch_type", 3);
        }
        if (i3 <= 0 || i3 > 4) {
            AMapLog.logFatalNative(AMapLog.GROUP_COMMON, a, "onStartCommand", "launchType:".concat(String.valueOf(i3)));
            return 1;
        }
        if (this.b != null) {
            wt wtVar = this.b;
            if (bno.a) {
                AMapLog.d(wt.a, "start launchType:".concat(String.valueOf(i3)), true);
            }
            if (wtVar.b == null) {
                wtVar.b = new a("LotusPool Thread", wtVar.c);
                wtVar.b.start();
            }
            a aVar = wtVar.b;
            synchronized (aVar.b) {
                if (aVar.a == null) {
                    aVar.b.add(Integer.valueOf(i3));
                } else {
                    aVar.a.a(i3);
                }
            }
        }
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
        wt wtVar = this.b;
        if (wtVar.b != null) {
            a aVar = wtVar.b;
            synchronized (aVar.b) {
                if (aVar.a == null) {
                    aVar.b.add(Integer.valueOf(-1));
                } else {
                    aVar.a.post(new Runnable() {
                        public final void run() {
                            a.this.a();
                        }
                    });
                }
            }
            wtVar.b = null;
        }
        this.b = null;
        if (bno.a) {
            AMapLog.i(a, "onDestroy()", true);
        }
    }
}
