package com.alipay.mobile.security.bio.thread;

import com.alipay.mobile.security.bio.utils.BioLog;

public abstract class WatchThread extends Thread {
    public static final ThreadGroup tg = new ThreadGroup("watch-thread");
    private boolean a = true;
    private String b = null;

    public abstract void task();

    public WatchThread(String str) {
        super(tg, str);
        setDaemon(true);
    }

    public void kill() {
        this.a = false;
    }

    public void run() {
        while (this.a) {
            try {
                task();
            } catch (Exception e) {
                BioLog.e((Throwable) e);
            } catch (Throwable th) {
                BioLog.e(th);
            }
        }
    }

    public String getStatus() {
        return this.b;
    }
}
