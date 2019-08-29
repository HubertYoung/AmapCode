package com.alipay.security.mobile.module.commonutils;

import android.os.Process;
import java.util.LinkedList;

public class SingleThreadPool {
    private static SingleThreadPool instance = new SingleThreadPool();
    /* access modifiers changed from: private */
    public LinkedList<Runnable> mTaskQueue = new LinkedList<>();
    /* access modifiers changed from: private */
    public Thread workThread = null;

    public static SingleThreadPool getInstance() {
        return instance;
    }

    public synchronized void execute(Runnable runnable) {
        this.mTaskQueue.add(runnable);
        if (this.workThread == null) {
            this.workThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        Process.setThreadPriority(0);
                        while (!SingleThreadPool.this.mTaskQueue.isEmpty()) {
                            Runnable runnable = (Runnable) SingleThreadPool.this.mTaskQueue.pollFirst();
                            if (runnable != null) {
                                runnable.run();
                            }
                        }
                    } catch (Exception unused) {
                    } catch (Throwable th) {
                        SingleThreadPool.this.workThread = null;
                        throw th;
                    }
                    SingleThreadPool.this.workThread = null;
                }
            });
            this.workThread.start();
        }
    }
}
