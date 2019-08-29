package com.ut.mini.module.appstatus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.alibaba.analytics.utils.TaskExecutor;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@TargetApi(14)
public class UTAppStatusMonitor implements ActivityLifecycleCallbacks {
    private static UTAppStatusMonitor s_instance = new UTAppStatusMonitor();
    private int mActivitiesActive = 0;
    /* access modifiers changed from: private */
    public List<UTAppStatusCallbacks> mAppStatusCallbacksList = new LinkedList();
    /* access modifiers changed from: private */
    public final Object mAppStatusCallbacksLockObj = new Object();
    private final Object mApplicationStatusLockObj = new Object();
    private ScheduledFuture<?> mApplicationStatusScheduledFuture = null;
    /* access modifiers changed from: private */
    public boolean mIsInForeground = false;

    class NotInForegroundRunnable implements Runnable {
        private NotInForegroundRunnable() {
        }

        public void run() {
            UTAppStatusMonitor.this.mIsInForeground = false;
            synchronized (UTAppStatusMonitor.this.mAppStatusCallbacksLockObj) {
                for (int i = 0; i < UTAppStatusMonitor.this.mAppStatusCallbacksList.size(); i++) {
                    UTAppStatusCallbacks uTAppStatusCallbacks = (UTAppStatusCallbacks) UTAppStatusMonitor.this.mAppStatusCallbacksList.get(i);
                    if (uTAppStatusCallbacks instanceof UTAppStatusDelayCallbacks) {
                        ((UTAppStatusDelayCallbacks) uTAppStatusCallbacks).onSwitchBackgroundDelay();
                    }
                }
            }
        }
    }

    private UTAppStatusMonitor() {
    }

    public static UTAppStatusMonitor getInstance() {
        return s_instance;
    }

    public void registerAppStatusCallbacks(UTAppStatusCallbacks uTAppStatusCallbacks) {
        if (uTAppStatusCallbacks != null) {
            synchronized (this.mAppStatusCallbacksLockObj) {
                this.mAppStatusCallbacksList.add(uTAppStatusCallbacks);
            }
        }
    }

    public void unregisterAppStatusCallbacks(UTAppStatusCallbacks uTAppStatusCallbacks) {
        if (uTAppStatusCallbacks != null) {
            synchronized (this.mAppStatusCallbacksLockObj) {
                this.mAppStatusCallbacksList.remove(uTAppStatusCallbacks);
            }
        }
    }

    private void _clearApplicationStatusCheckExistingTimer() {
        synchronized (this.mApplicationStatusLockObj) {
            if (this.mApplicationStatusScheduledFuture != null) {
                this.mApplicationStatusScheduledFuture.cancel(true);
            }
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (int i = 0; i < this.mAppStatusCallbacksList.size(); i++) {
                this.mAppStatusCallbacksList.get(i).onActivityCreated(activity, bundle);
            }
        }
    }

    public void onActivityDestroyed(Activity activity) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (int i = 0; i < this.mAppStatusCallbacksList.size(); i++) {
                this.mAppStatusCallbacksList.get(i).onActivityDestroyed(activity);
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (int i = 0; i < this.mAppStatusCallbacksList.size(); i++) {
                this.mAppStatusCallbacksList.get(i).onActivityPaused(activity);
            }
        }
    }

    public void onActivityResumed(Activity activity) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (int i = 0; i < this.mAppStatusCallbacksList.size(); i++) {
                this.mAppStatusCallbacksList.get(i).onActivityResumed(activity);
            }
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        synchronized (this.mAppStatusCallbacksLockObj) {
            for (int i = 0; i < this.mAppStatusCallbacksList.size(); i++) {
                this.mAppStatusCallbacksList.get(i).onActivitySaveInstanceState(activity, bundle);
            }
        }
    }

    public void onActivityStarted(Activity activity) {
        _clearApplicationStatusCheckExistingTimer();
        this.mActivitiesActive++;
        if (!this.mIsInForeground) {
            synchronized (this.mAppStatusCallbacksLockObj) {
                for (int i = 0; i < this.mAppStatusCallbacksList.size(); i++) {
                    this.mAppStatusCallbacksList.get(i).onSwitchForeground();
                }
            }
        }
        this.mIsInForeground = true;
    }

    public void onActivityStopped(Activity activity) {
        this.mActivitiesActive--;
        if (this.mActivitiesActive == 0) {
            synchronized (this.mAppStatusCallbacksLockObj) {
                for (int i = 0; i < this.mAppStatusCallbacksList.size(); i++) {
                    this.mAppStatusCallbacksList.get(i).onSwitchBackground();
                }
            }
            _clearApplicationStatusCheckExistingTimer();
            this.mApplicationStatusScheduledFuture = TaskExecutor.getInstance().schedule(null, new NotInForegroundRunnable(), 1000);
        }
    }

    public boolean isInForeground() {
        return this.mIsInForeground;
    }
}
