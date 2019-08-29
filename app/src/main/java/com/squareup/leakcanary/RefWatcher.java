package com.squareup.leakcanary;

import com.squareup.leakcanary.ExcludedRefs.Builder;
import com.squareup.leakcanary.HeapDump.Listener;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public final class RefWatcher {
    public static final RefWatcher DISABLED;
    private final DebuggerControl debuggerControl;
    private final ExcludedRefs excludedRefs;
    private final GcTrigger gcTrigger;
    private final HeapDumper heapDumper;
    private final Listener heapdumpListener;
    private final ReferenceQueue<Object> queue = new ReferenceQueue<>();
    private final Set<String> retainedKeys = new CopyOnWriteArraySet();
    private final Executor watchExecutor;

    static {
        RefWatcher refWatcher = new RefWatcher(new Executor() {
            public final void execute(Runnable runnable) {
            }
        }, new DebuggerControl() {
            public final boolean isDebuggerAttached() {
                return true;
            }
        }, GcTrigger.DEFAULT, new HeapDumper() {
            public final File dumpHeap() {
                return null;
            }
        }, new Listener() {
            public final void analyze(HeapDump heapDump) {
            }
        }, new Builder().build());
        DISABLED = refWatcher;
    }

    public RefWatcher(Executor executor, DebuggerControl debuggerControl2, GcTrigger gcTrigger2, HeapDumper heapDumper2, Listener listener, ExcludedRefs excludedRefs2) {
        this.watchExecutor = (Executor) Preconditions.checkNotNull(executor, "watchExecutor");
        this.debuggerControl = (DebuggerControl) Preconditions.checkNotNull(debuggerControl2, "debuggerControl");
        this.gcTrigger = (GcTrigger) Preconditions.checkNotNull(gcTrigger2, "gcTrigger");
        this.heapDumper = (HeapDumper) Preconditions.checkNotNull(heapDumper2, "heapDumper");
        this.heapdumpListener = (Listener) Preconditions.checkNotNull(listener, "heapdumpListener");
        this.excludedRefs = (ExcludedRefs) Preconditions.checkNotNull(excludedRefs2, "excludedRefs");
    }

    public final void watch(Object obj) {
        watch(obj, "");
    }

    public final void watch(Object obj, String str) {
        Preconditions.checkNotNull(obj, "watchedReference");
        Preconditions.checkNotNull(str, "referenceName");
        if (!this.debuggerControl.isDebuggerAttached()) {
            final long nanoTime = System.nanoTime();
            String uuid = UUID.randomUUID().toString();
            this.retainedKeys.add(uuid);
            final KeyedWeakReference keyedWeakReference = new KeyedWeakReference(obj, uuid, str, this.queue);
            this.watchExecutor.execute(new Runnable() {
                public void run() {
                    RefWatcher.this.ensureGone(keyedWeakReference, nanoTime);
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public final void ensureGone(KeyedWeakReference keyedWeakReference, long j) {
        KeyedWeakReference keyedWeakReference2 = keyedWeakReference;
        long nanoTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(nanoTime - j);
        removeWeaklyReachableReferences();
        if (!gone(keyedWeakReference) && !this.debuggerControl.isDebuggerAttached()) {
            this.gcTrigger.runGc();
            removeWeaklyReachableReferences();
            if (!gone(keyedWeakReference)) {
                long nanoTime2 = System.nanoTime();
                long millis2 = TimeUnit.NANOSECONDS.toMillis(nanoTime2 - nanoTime);
                File dumpHeap = this.heapDumper.dumpHeap();
                if (dumpHeap != HeapDumper.NO_DUMP) {
                    long millis3 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime2);
                    Listener listener = this.heapdumpListener;
                    HeapDump heapDump = new HeapDump(dumpHeap, keyedWeakReference2.key, keyedWeakReference2.name, this.excludedRefs, millis, millis2, millis3);
                    listener.analyze(heapDump);
                }
            }
        }
    }

    private boolean gone(KeyedWeakReference keyedWeakReference) {
        return !this.retainedKeys.contains(keyedWeakReference.key);
    }

    private void removeWeaklyReachableReferences() {
        while (true) {
            KeyedWeakReference keyedWeakReference = (KeyedWeakReference) this.queue.poll();
            if (keyedWeakReference != null) {
                this.retainedKeys.remove(keyedWeakReference.key);
            } else {
                return;
            }
        }
    }
}
