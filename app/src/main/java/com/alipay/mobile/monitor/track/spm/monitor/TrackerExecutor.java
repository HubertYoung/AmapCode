package com.alipay.mobile.monitor.track.spm.monitor;

import com.alipay.mobile.monitor.track.spm.monitor.tracker.BaseTracker;

public class TrackerExecutor {
    private final int a = 1;
    private TrackerQueue b = new TrackerQueue(1);

    public TrackerExecutor() {
        this.b.start();
    }

    public void commitTracker(BaseTracker tracker) {
        if (this.b != null && tracker != null) {
            this.b.add(tracker);
        }
    }

    public void stop() {
        if (this.b != null) {
            this.b.stop();
            this.b = null;
        }
    }
}
