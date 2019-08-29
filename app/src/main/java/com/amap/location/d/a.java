package com.amap.location.d;

import android.os.Handler;
import android.support.annotation.NonNull;

/* compiled from: AbstractIndoorTimeOutChecker */
public abstract class a implements Runnable {
    protected boolean a = false;
    private volatile long b = 10000;
    private Handler c;

    public a(@NonNull Handler handler) {
        this.c = handler;
    }

    public void a(long j) {
        if (j > 0) {
            this.b = j;
        }
    }

    public void a() {
        b();
        this.c.postDelayed(this, this.b);
        this.a = true;
    }

    public void b() {
        if (this.a) {
            this.c.removeCallbacks(this);
            this.a = false;
        }
    }
}
