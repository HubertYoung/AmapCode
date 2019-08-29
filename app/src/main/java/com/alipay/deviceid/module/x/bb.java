package com.alipay.deviceid.module.x;

import android.os.Process;

final class bb implements Runnable {
    final /* synthetic */ ba a;

    bb(ba baVar) {
        this.a = baVar;
    }

    public final void run() {
        try {
            Process.setThreadPriority(0);
            new StringBuilder("[*] thread priority: ").append(Process.getThreadPriority(Process.myTid()));
            while (!this.a.c.isEmpty()) {
                Runnable runnable = (Runnable) this.a.c.pollFirst();
                if (runnable != null) {
                    runnable.run();
                }
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            this.a.b = null;
            throw th;
        }
        this.a.b = null;
    }
}
