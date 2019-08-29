package com.alibaba.baichuan.android.trade.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;

public class g {
    private static volatile g d;
    private Handler a = new Handler(Looper.getMainLooper());
    private HandlerThread b = new HandlerThread("SDK Looper Thread");
    private Handler c;

    private g() {
        this.b.start();
        while (this.b.getLooper() == null) {
            try {
                this.b.wait();
            } catch (InterruptedException e) {
                StringBuilder sb = new StringBuilder("创建handlerThread错误：");
                sb.append(e.getMessage());
                AlibcLogger.e("ExecutorServiceUtils", sb.toString());
            }
        }
        this.c = new h(this, this.b.getLooper());
    }

    public static g a() {
        if (d == null) {
            synchronized (g.class) {
                try {
                    if (d == null) {
                        d = new g();
                    }
                }
            }
        }
        return d;
    }

    public void a(Runnable runnable) {
        this.c.post(runnable);
    }

    public void a(Runnable runnable, long j) {
        this.c.postDelayed(runnable, j);
    }

    public void b(Runnable runnable) {
        this.a.post(runnable);
    }
}
