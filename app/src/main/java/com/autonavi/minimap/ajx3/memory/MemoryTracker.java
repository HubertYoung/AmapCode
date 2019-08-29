package com.autonavi.minimap.ajx3.memory;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryTracker {
    public static MemoryTracker INSTANCE = new MemoryTracker();
    private static final Object PRESENT = new Object();
    private static final String TAG = "MemoryTracker";
    /* access modifiers changed from: private */
    public ReferenceQueue<Object> mPhantomQueue = new ReferenceQueue<>();
    /* access modifiers changed from: private */
    public Map<Tracker, Object> mTrackers = new ConcurrentHashMap();
    private MemoryWatcher mWatcher = new MemoryWatcher();

    final class MemoryWatcher extends Thread {
        private volatile boolean isQuit = false;

        MemoryWatcher() {
            super(MemoryTracker.TAG);
            setDaemon(true);
        }

        /* access modifiers changed from: private */
        public void shutDown() {
            this.isQuit = true;
            interrupt();
        }

        public final void run() {
            while (!this.isQuit) {
                try {
                    Tracker tracker = (Tracker) MemoryTracker.this.mPhantomQueue.remove();
                    if (tracker != null) {
                        tracker.releasePtr();
                        tracker.clear();
                        MemoryTracker.this.mTrackers.remove(tracker);
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    static class Tracker extends PhantomReference<Object> {
        private final long nativePtr;
        private final int type;

        private Tracker(Object obj, long j, int i, ReferenceQueue<Object> referenceQueue) {
            super(obj, referenceQueue);
            this.nativePtr = j;
            this.type = i;
        }

        /* access modifiers changed from: private */
        public void releasePtr() {
            MemoryReleaseHelper.release(this.nativePtr, this.type);
        }
    }

    private MemoryTracker() {
        this.mWatcher.start();
    }

    public void track(Object obj, long j, int i) {
        if (obj == null) {
            throw new IllegalArgumentException("referent is null");
        } else if (j == 0) {
            throw new IllegalArgumentException("nativePtr is null");
        } else {
            Map<Tracker, Object> map = this.mTrackers;
            Tracker tracker = new Tracker(obj, j, i, this.mPhantomQueue);
            map.put(tracker, PRESENT);
        }
    }

    public void shutDown() {
        this.mWatcher.shutDown();
        try {
            this.mWatcher.join();
        } catch (InterruptedException unused) {
        }
    }
}
