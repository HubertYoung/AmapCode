package com.alipay.mobile.nebula.util.batched;

import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BatchedScheduler<T> {
    /* access modifiers changed from: private */
    public final String TAG = ("Batched@" + getClass().getSimpleName());
    private final List<T> batchedList;
    private AtomicBoolean isPaused = new AtomicBoolean(false);
    private final Executor mainExecutor;
    private Handler scheduleHandler;
    private HandlerThread scheduleHandlerThread;
    private ScheduleRunnable scheduleRunnable = new ScheduleRunnable<>();

    private class ScheduleRunnable implements Runnable {
        private volatile boolean isCancelled;
        /* access modifiers changed from: private */
        public volatile boolean isScheduled;

        private ScheduleRunnable() {
            this.isScheduled = false;
            this.isCancelled = false;
        }

        /* access modifiers changed from: 0000 */
        public void start() {
            H5Log.d(BatchedScheduler.this.TAG, "start schedule");
            this.isCancelled = false;
            this.isScheduled = true;
        }

        /* access modifiers changed from: 0000 */
        public void cancel() {
            H5Log.d(BatchedScheduler.this.TAG, "cancel schedule");
            this.isCancelled = true;
            this.isScheduled = false;
        }

        public void run() {
            if (!this.isCancelled) {
                BatchedScheduler.this.cloneAndExecute();
                this.isScheduled = false;
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void onSchedule(List<T> list);

    public BatchedScheduler(Executor executor) {
        this.mainExecutor = executor;
        this.batchedList = new LinkedList();
        this.scheduleHandlerThread = new HandlerThread(this.TAG);
        this.scheduleHandlerThread.start();
        this.scheduleHandler = new Handler(this.scheduleHandlerThread.getLooper());
    }

    public void pause() {
        H5Log.d(this.TAG, "pause!");
        this.isPaused.set(true);
    }

    public void resume() {
        if (!this.isPaused.get()) {
            H5Log.d(this.TAG, "not resume because not paused");
            return;
        }
        H5Log.d(this.TAG, "resume!");
        this.isPaused.set(false);
        synchronized (this.batchedList) {
            H5Log.d(this.TAG, "=== resume schedule batched tasks begin====");
            int size = this.batchedList.size();
            int count = size / thresholdSize();
            for (int i = 0; i <= count; i++) {
                int start = i * thresholdSize();
                int end = (i + 1) * thresholdSize();
                if (end > size) {
                    end = size;
                }
                doExecute(new ArrayList(this.batchedList.subList(start, end)));
            }
            this.batchedList.clear();
            H5Log.d(this.TAG, "=== resume schedule batched tasks over====");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007e, code lost:
        if (com.alipay.mobile.nebula.util.batched.BatchedScheduler.ScheduleRunnable.access$100(r4.scheduleRunnable) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0080, code lost:
        r4.scheduleRunnable.start();
        r4.scheduleHandler.postDelayed(r4.scheduleRunnable, thresholdInterval());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void post(T r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x0003
        L_0x0002:
            return
        L_0x0003:
            android.os.HandlerThread r0 = r4.scheduleHandlerThread
            if (r0 != 0) goto L_0x000f
            java.lang.String r0 = r4.TAG
            java.lang.String r1 = "cannot post task because BatchedScheduler is shut down!"
            com.alipay.mobile.nebula.util.H5Log.d(r0, r1)
            goto L_0x0002
        L_0x000f:
            java.util.List<T> r1 = r4.batchedList
            monitor-enter(r1)
            java.util.List<T> r0 = r4.batchedList     // Catch:{ all -> 0x0035 }
            r0.add(r5)     // Catch:{ all -> 0x0035 }
            java.util.concurrent.atomic.AtomicBoolean r0 = r4.isPaused     // Catch:{ all -> 0x0035 }
            boolean r0 = r0.get()     // Catch:{ all -> 0x0035 }
            if (r0 == 0) goto L_0x0038
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r0 = r4.scheduleRunnable     // Catch:{ all -> 0x0035 }
            boolean r0 = r0.isScheduled     // Catch:{ all -> 0x0035 }
            if (r0 == 0) goto L_0x0033
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r0 = r4.scheduleRunnable     // Catch:{ all -> 0x0035 }
            r0.cancel()     // Catch:{ all -> 0x0035 }
            android.os.Handler r0 = r4.scheduleHandler     // Catch:{ all -> 0x0035 }
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r2 = r4.scheduleRunnable     // Catch:{ all -> 0x0035 }
            r0.removeCallbacks(r2)     // Catch:{ all -> 0x0035 }
        L_0x0033:
            monitor-exit(r1)     // Catch:{ all -> 0x0035 }
            goto L_0x0002
        L_0x0035:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0035 }
            throw r0
        L_0x0038:
            java.util.List<T> r0 = r4.batchedList     // Catch:{ all -> 0x0035 }
            int r0 = r0.size()     // Catch:{ all -> 0x0035 }
            int r2 = r4.thresholdSize()     // Catch:{ all -> 0x0035 }
            if (r0 <= r2) goto L_0x0077
            java.lang.String r0 = r4.TAG     // Catch:{ all -> 0x0035 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0035 }
            java.lang.String r3 = "schedule now! size: "
            r2.<init>(r3)     // Catch:{ all -> 0x0035 }
            java.util.List<T> r3 = r4.batchedList     // Catch:{ all -> 0x0035 }
            int r3 = r3.size()     // Catch:{ all -> 0x0035 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0035 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0035 }
            com.alipay.mobile.nebula.util.H5Log.d(r0, r2)     // Catch:{ all -> 0x0035 }
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r0 = r4.scheduleRunnable     // Catch:{ all -> 0x0035 }
            boolean r0 = r0.isScheduled     // Catch:{ all -> 0x0035 }
            if (r0 == 0) goto L_0x0072
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r0 = r4.scheduleRunnable     // Catch:{ all -> 0x0035 }
            r0.cancel()     // Catch:{ all -> 0x0035 }
            android.os.Handler r0 = r4.scheduleHandler     // Catch:{ all -> 0x0035 }
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r2 = r4.scheduleRunnable     // Catch:{ all -> 0x0035 }
            r0.removeCallbacks(r2)     // Catch:{ all -> 0x0035 }
        L_0x0072:
            r4.cloneAndExecute()     // Catch:{ all -> 0x0035 }
            monitor-exit(r1)     // Catch:{ all -> 0x0035 }
            goto L_0x0002
        L_0x0077:
            monitor-exit(r1)     // Catch:{ all -> 0x0035 }
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r0 = r4.scheduleRunnable
            boolean r0 = r0.isScheduled
            if (r0 != 0) goto L_0x0002
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r0 = r4.scheduleRunnable
            r0.start()
            android.os.Handler r0 = r4.scheduleHandler
            com.alipay.mobile.nebula.util.batched.BatchedScheduler$ScheduleRunnable<> r1 = r4.scheduleRunnable
            long r2 = r4.thresholdInterval()
            r0.postDelayed(r1, r2)
            goto L_0x0002
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.batched.BatchedScheduler.post(java.lang.Object):void");
    }

    public void shutdown() {
        H5Log.d(this.TAG, "shutdown");
        if (this.scheduleHandlerThread != null) {
            this.scheduleHandler.removeCallbacks(this.scheduleRunnable);
            this.scheduleHandler = null;
            cloneAndExecute();
            this.scheduleHandlerThread.quit();
            this.scheduleHandlerThread = null;
        }
    }

    /* access modifiers changed from: private */
    public void cloneAndExecute() {
        H5Log.d(this.TAG, "cloneAndExecute");
        synchronized (this.batchedList) {
            if (this.batchedList.size() != 0) {
                List tasks = new ArrayList(this.batchedList);
                this.batchedList.clear();
                doExecute(tasks);
            }
        }
    }

    private void doExecute(final List<T> tasks) {
        H5Log.d(this.TAG, "doExecute " + tasks.size() + " items");
        this.mainExecutor.execute(new Runnable() {
            public void run() {
                BatchedScheduler.this.onSchedule(tasks);
            }
        });
    }

    /* access modifiers changed from: protected */
    public int thresholdSize() {
        return 16;
    }

    /* access modifiers changed from: protected */
    public long thresholdInterval() {
        return 500;
    }
}
