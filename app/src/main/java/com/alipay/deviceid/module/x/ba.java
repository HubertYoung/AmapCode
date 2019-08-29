package com.alipay.deviceid.module.x;

import java.util.LinkedList;

public final class ba {
    private static ba a = new ba();
    /* access modifiers changed from: private */
    public Thread b = null;
    /* access modifiers changed from: private */
    public LinkedList<Runnable> c = new LinkedList<>();

    public static ba a() {
        return a;
    }

    public final synchronized void a(Runnable runnable) {
        this.c.add(runnable);
        if (this.b == null) {
            this.b = new Thread(new bb(this));
            this.b.start();
        }
    }
}
