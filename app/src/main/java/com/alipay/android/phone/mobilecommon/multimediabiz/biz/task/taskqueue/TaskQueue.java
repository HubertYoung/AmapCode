package com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.taskqueue;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask;
import java.util.Observable;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TaskQueue extends Observable {
    private BlockingDeque<MMTask> a = new LinkedBlockingDeque();
    private BlockingDeque<MMTask> b = new LinkedBlockingDeque();
    private BlockingDeque<MMTask> c = new LinkedBlockingDeque();

    public void addTask(MMTask task) {
        if (task != null) {
            switch (task.getPriority()) {
                case 1:
                    a(task, this.c);
                    break;
                case 5:
                    a(task, this.b);
                    break;
                case 10:
                    a(task, this.a);
                    break;
                default:
                    a(task, this.b);
                    break;
            }
            setChanged();
            notifyObservers();
        }
    }

    private static void a(MMTask task, BlockingDeque<MMTask> queue) {
        synchronized (queue) {
            if (task.isLIFO()) {
                queue.offerFirst(task);
            } else {
                queue.offer(task);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001f, code lost:
        if (r2.b.isEmpty() != false) goto L_0x0031;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        r0 = r2.b.poll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0032, code lost:
        r1 = r2.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0034, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x003b, code lost:
        if (r2.c.isEmpty() != false) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x003d, code lost:
        r0 = r2.c.poll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0045, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0016, code lost:
        r1 = r2.b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
        monitor-enter(r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask getTask() {
        /*
            r2 = this;
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r1 = r2.a
            monitor-enter(r1)
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r0 = r2.a     // Catch:{ all -> 0x002e }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x0015
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r0 = r2.a     // Catch:{ all -> 0x002e }
            java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x002e }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask r0 = (com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask) r0     // Catch:{ all -> 0x002e }
            monitor-exit(r1)     // Catch:{ all -> 0x002e }
        L_0x0014:
            return r0
        L_0x0015:
            monitor-exit(r1)     // Catch:{ all -> 0x002e }
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r1 = r2.b
            monitor-enter(r1)
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r0 = r2.b     // Catch:{ all -> 0x002b }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x002b }
            if (r0 != 0) goto L_0x0031
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r0 = r2.b     // Catch:{ all -> 0x002b }
            java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x002b }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask r0 = (com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask) r0     // Catch:{ all -> 0x002b }
            monitor-exit(r1)     // Catch:{ all -> 0x002b }
            goto L_0x0014
        L_0x002b:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002b }
            throw r0
        L_0x002e:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002e }
            throw r0
        L_0x0031:
            monitor-exit(r1)     // Catch:{ all -> 0x002b }
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r1 = r2.c
            monitor-enter(r1)
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r0 = r2.c     // Catch:{ all -> 0x0047 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0047 }
            if (r0 != 0) goto L_0x004a
            java.util.concurrent.BlockingDeque<com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask> r0 = r2.c     // Catch:{ all -> 0x0047 }
            java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x0047 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask r0 = (com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask) r0     // Catch:{ all -> 0x0047 }
            monitor-exit(r1)     // Catch:{ all -> 0x0047 }
            goto L_0x0014
        L_0x0047:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0047 }
            throw r0
        L_0x004a:
            monitor-exit(r1)     // Catch:{ all -> 0x0047 }
            r0 = 0
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.taskqueue.TaskQueue.getTask():com.alipay.android.phone.mobilecommon.multimediabiz.biz.task.MMTask");
    }

    public void removeTask(MMTask task) {
        if (task != null) {
            switch (task.getPriority()) {
                case 1:
                    synchronized (this.c) {
                        this.c.remove(task);
                    }
                    return;
                case 5:
                    synchronized (this.b) {
                        this.b.remove(task);
                    }
                    return;
                case 10:
                    synchronized (this.a) {
                        this.a.remove(task);
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public boolean isTaskInQueue(MMTask task) {
        boolean ret = false;
        if (task != null) {
            switch (task.getPriority()) {
                case 1:
                    synchronized (this.c) {
                        try {
                            ret = this.c.contains(task);
                        }
                    }
                    break;
                case 5:
                    synchronized (this.b) {
                        try {
                            ret = this.b.contains(task);
                        }
                    }
                    break;
                case 10:
                    synchronized (this.a) {
                        try {
                            ret = this.a.contains(task);
                        }
                    }
                    break;
                default:
                    synchronized (this.b) {
                        try {
                            ret = this.b.contains(task);
                            break;
                        }
                    }
            }
        }
        return ret;
    }

    public String toString() {
        return "TaskQueue{highSize=" + this.a.size() + ", midSize=" + this.b.size() + ", lowSize=" + this.c.size() + '}';
    }
}
