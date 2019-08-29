package com.autonavi.miniapp.monitor.biz.apm.util;

import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.TimerTask;

public abstract class APMTimerJob extends TimerTask {
    private String mName = getClass().getSimpleName();

    /* access modifiers changed from: protected */
    public abstract void doJob();

    public void setName(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mName = str;
        }
    }

    public String getName() {
        return this.mName;
    }

    public void run() {
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            doJob();
        } finally {
            long uptimeMillis2 = SystemClock.uptimeMillis() - uptimeMillis;
            if (uptimeMillis2 > 5000) {
                r2 = "doJob: spend ";
                LoggerFactory.getTraceLogger().error(getName(), r2.concat(String.valueOf(uptimeMillis2)));
            }
        }
    }
}
