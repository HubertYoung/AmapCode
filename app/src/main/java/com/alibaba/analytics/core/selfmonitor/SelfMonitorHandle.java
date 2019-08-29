package com.alibaba.analytics.core.selfmonitor;

import com.alibaba.analytics.core.config.UTOrangeConfMgr;
import com.alibaba.analytics.core.store.LogStoreMgr;
import com.alibaba.analytics.core.sync.BizRequest;
import com.alibaba.analytics.core.sync.TnetUtil;
import com.alibaba.analytics.core.sync.UploadLogFromCache;
import com.alibaba.analytics.core.sync.UploadLogFromDB;
import com.alibaba.analytics.core.sync.UrlWrapper;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate.Counter;
import com.alibaba.appmonitor.delegate.AppMonitorDelegate.Stat;
import com.alibaba.appmonitor.event.EventType;
import com.ut.mini.core.UTLogTransferMain;

public class SelfMonitorHandle implements SelfMonitorEventListener {
    private static SelfMonitorHandle instance = new SelfMonitorHandle();

    @Deprecated
    public void handleEvent(SelfMonitorEvent selfMonitorEvent) {
    }

    public static SelfMonitorHandle getInstance() {
        return instance;
    }

    public void init() {
        try {
            UploadLogFromDB.getInstance().mMonitor.regiserListener(this);
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
        try {
            UploadLogFromCache.getInstance().mMonitor.regiserListener(this);
        } catch (Throwable th2) {
            Logger.e(null, th2, new Object[0]);
        }
        try {
            UrlWrapper.mMonitor.regiserListener(this);
        } catch (Throwable th3) {
            Logger.e(null, th3, new Object[0]);
        }
        try {
            BizRequest.mMonitor.regiserListener(this);
        } catch (Throwable th4) {
            Logger.e(null, th4, new Object[0]);
        }
        try {
            LogStoreMgr.mMonitor.regiserListener(this);
        } catch (Throwable th5) {
            Logger.e(null, th5, new Object[0]);
        }
        try {
            UTLogTransferMain.getInstance().mMonitor.regiserListener(this);
        } catch (Throwable th6) {
            Logger.e(null, th6, new Object[0]);
        }
        try {
            UTOrangeConfMgr.mMonitor.regiserListener(this);
        } catch (Throwable th7) {
            Logger.e(null, th7, new Object[0]);
        }
        try {
            TnetUtil.mMonitor.regiserListener(this);
        } catch (Throwable th8) {
            Logger.e(null, th8, new Object[0]);
        }
        try {
            ConfigArrivedMonitor.mMonitor.regiserListener(this);
        } catch (Throwable th9) {
            Logger.e(null, th9, new Object[0]);
        }
    }

    public void onEvent(SelfMonitorEvent selfMonitorEvent) {
        if (selfMonitorEvent.type == EventType.COUNTER) {
            Counter.commit(SelfMonitorEvent.module, selfMonitorEvent.monitorPoint, selfMonitorEvent.arg, selfMonitorEvent.value.doubleValue());
            return;
        }
        if (selfMonitorEvent.type == EventType.STAT) {
            Stat.commit((String) SelfMonitorEvent.module, selfMonitorEvent.monitorPoint, selfMonitorEvent.dvs, selfMonitorEvent.mvs);
        }
    }
}
