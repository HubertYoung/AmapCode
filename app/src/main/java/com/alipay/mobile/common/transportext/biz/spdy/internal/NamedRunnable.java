package com.alipay.mobile.common.transportext.biz.spdy.internal;

public abstract class NamedRunnable implements Runnable {
    private final String name;

    /* access modifiers changed from: protected */
    public abstract void execute();

    public NamedRunnable(String format, Object... args) {
        this.name = String.format(format, args);
    }

    public final void run() {
        String oldName = Thread.currentThread().getName();
        Thread.currentThread().setName(this.name);
        try {
            execute();
        } finally {
            Thread.currentThread().setName(oldName);
        }
    }
}
