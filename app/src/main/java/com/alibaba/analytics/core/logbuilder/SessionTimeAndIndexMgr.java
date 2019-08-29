package com.alibaba.analytics.core.logbuilder;

import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.appmonitor.delegate.BackgroundTrigger;
import com.alibaba.appmonitor.delegate.BackgroundTrigger.AppStatusChangeCallback;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionTimeAndIndexMgr implements AppStatusChangeCallback {
    private static long SESSION_TIMEOUT_AFTER_BACKGROUND = 300000;
    private static SessionTimeAndIndexMgr instance = new SessionTimeAndIndexMgr();
    /* access modifiers changed from: private */
    public final AtomicInteger m2202LogIndex;
    private ScheduledFuture mChangeSessionFuture;
    private Runnable mChangeSessionTask;
    /* access modifiers changed from: private */
    public final AtomicInteger mLogIndex;
    /* access modifiers changed from: private */
    public long mSessionTime;

    public static SessionTimeAndIndexMgr getInstance() {
        return instance;
    }

    public long getSessionTimestamp() {
        return this.mSessionTime;
    }

    private SessionTimeAndIndexMgr() {
        this.mSessionTime = System.currentTimeMillis();
        this.mChangeSessionFuture = null;
        this.mLogIndex = new AtomicInteger(0);
        this.m2202LogIndex = new AtomicInteger(0);
        this.mChangeSessionTask = new Runnable() {
            public void run() {
                SessionTimeAndIndexMgr.this.mSessionTime = System.currentTimeMillis();
                SessionTimeAndIndexMgr.this.mLogIndex.set(0);
                SessionTimeAndIndexMgr.this.m2202LogIndex.set(0);
            }
        };
        this.mSessionTime = System.currentTimeMillis();
        BackgroundTrigger.registerCallback(this);
    }

    public void onBackground() {
        Logger.d();
        this.mChangeSessionFuture = TaskExecutor.getInstance().schedule(this.mChangeSessionFuture, this.mChangeSessionTask, SESSION_TIMEOUT_AFTER_BACKGROUND);
    }

    public void onForeground() {
        if (this.mChangeSessionFuture != null && !this.mChangeSessionFuture.isDone()) {
            this.mChangeSessionFuture.cancel(true);
        }
    }

    public long logIndexIncrementAndGet() {
        return (long) this.mLogIndex.incrementAndGet();
    }

    public long m2202LogIndexIncrementAndGet() {
        return (long) this.m2202LogIndex.incrementAndGet();
    }
}
