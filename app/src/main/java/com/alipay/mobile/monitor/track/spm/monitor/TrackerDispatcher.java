package com.alipay.mobile.monitor.track.spm.monitor;

import android.os.Process;
import com.alipay.mobile.monitor.track.spm.SpmLogCator;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.BaseTracker;
import java.util.concurrent.BlockingQueue;

public class TrackerDispatcher extends Thread {
    private final String a = TrackerDispatcher.class.getSimpleName();
    private final BlockingQueue<BaseTracker> b;
    private volatile boolean c = false;

    public TrackerDispatcher(BlockingQueue<BaseTracker> queue) {
        this.b = queue;
    }

    public void quit() {
        this.c = true;
    }

    public void run() {
        Process.setThreadPriority(10);
        SpmLogCator.debug(this.a, "run");
        while (true) {
            try {
                BaseTracker request = this.b.take();
                try {
                    SpmLogCator.debug(this.a, "run request:" + request);
                    request.commit();
                } catch (Exception e) {
                    SpmLogCator.error(this.a, (Throwable) e);
                }
            } catch (InterruptedException e2) {
                if (this.c) {
                    return;
                }
            }
        }
    }
}
