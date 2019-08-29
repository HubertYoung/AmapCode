package com.alipay.mobile.monitor.track.spm.monitor;

import com.alipay.mobile.monitor.track.spm.monitor.tracker.BaseTracker;
import java.util.concurrent.PriorityBlockingQueue;

public class TrackerQueue {
    private final PriorityBlockingQueue<BaseTracker> a;
    private TrackerDispatcher[] b;

    public TrackerQueue() {
        this(5);
    }

    public TrackerQueue(int threadPoolSize) {
        this.a = new PriorityBlockingQueue<>();
        this.b = new TrackerDispatcher[(threadPoolSize <= 0 ? 5 : threadPoolSize)];
    }

    public void start() {
        stop();
        for (int i = 0; i < this.b.length; i++) {
            TrackerDispatcher networkDispatcher = new TrackerDispatcher(this.a);
            this.b[i] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    public void stop() {
        for (int i = 0; i < this.b.length; i++) {
            if (this.b[i] != null) {
                this.b[i].quit();
            }
        }
    }

    public BaseTracker add(BaseTracker request) {
        this.a.add(request);
        return request;
    }
}
