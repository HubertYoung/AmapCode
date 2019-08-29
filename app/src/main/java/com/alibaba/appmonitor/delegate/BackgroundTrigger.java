package com.alibaba.appmonitor.delegate;

import android.annotation.TargetApi;
import android.app.Application;
import com.alibaba.analytics.utils.AppInfoUtil;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.sample.AMSamplingMgr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class BackgroundTrigger implements Runnable {
    private static List<AppStatusChangeCallback> callbacks = Collections.synchronizedList(new ArrayList());
    private static boolean init = false;
    private static ScheduledFuture mFuture;
    private Application application;
    private boolean isAppOnForeground = true;

    public interface AppStatusChangeCallback {
        void onBackground();

        void onForeground();
    }

    @TargetApi(14)
    public static void init(Application application2) {
        if (!init) {
            Logger.d((String) "init BackgroundTrigger", new Object[0]);
            mFuture = TaskExecutor.getInstance().scheduleAtFixedRate(mFuture, new BackgroundTrigger(application2), 60000);
            init = true;
        }
    }

    public static void registerCallback(AppStatusChangeCallback appStatusChangeCallback) {
        callbacks.add(appStatusChangeCallback);
    }

    public BackgroundTrigger(Application application2) {
        this.application = application2;
    }

    public void run() {
        EventType[] values;
        EventType[] values2;
        boolean isAppOnForeground2 = AppInfoUtil.isAppOnForeground(this.application.getApplicationContext());
        Logger.d((String) null, "forground", Boolean.valueOf(isAppOnForeground2));
        if (this.isAppOnForeground != isAppOnForeground2) {
            this.isAppOnForeground = isAppOnForeground2;
            if (isAppOnForeground2) {
                AMSamplingMgr.getInstance().updateSamplingSeed();
                for (EventType eventType : EventType.values()) {
                    AppMonitorDelegate.setStatisticsInterval(eventType, eventType.getForegroundStatisticsInterval());
                }
            } else {
                for (EventType eventType2 : EventType.values()) {
                    AppMonitorDelegate.setStatisticsInterval(eventType2, eventType2.getBackgroundStatisticsInterval());
                }
                AppMonitorDelegate.triggerUpload();
            }
            for (int i = 0; i < callbacks.size(); i++) {
                if (isAppOnForeground2) {
                    callbacks.get(i).onForeground();
                } else {
                    callbacks.get(i).onBackground();
                }
            }
        }
    }
}
