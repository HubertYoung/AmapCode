package com.alibaba.analytics.core.store;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.model.Log;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.SystemUtils;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.appmonitor.delegate.BackgroundTrigger;
import com.alibaba.appmonitor.delegate.BackgroundTrigger.AppStatusChangeCallback;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;

public class LogStoreMgr implements AppStatusChangeCallback {
    private static final int DELETE = 2;
    private static final int INSERT = 1;
    private static final int LOG_COUNT_CHECK = 5000;
    private static final Object Lock_Object = new Object();
    private static final int MAX_LOG_COUNT = 9000;
    private static final int MAX_LOG_SIZE = 45;
    private static final long STORE_INTERVAL = 5000;
    private static final String TAG = "LogStoreMgr";
    private static int logCount;
    private static LogStoreMgr mInstance = new LogStoreMgr();
    public static SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();
    private List<ILogChangeListener> mLogChangeListeners = Collections.synchronizedList(new ArrayList());
    private List<Log> mLogs = new CopyOnWriteArrayList();
    private ScheduledFuture mOneMinDBMonitorFuture = null;
    /* access modifiers changed from: private */
    public ILogStore mStore = new LogSqliteStore(Variables.getInstance().getContext());
    private ScheduledFuture mStoreFuture = null;
    private Runnable mStoreTask = new Runnable() {
        public void run() {
            LogStoreMgr.this.store();
        }
    };
    private ScheduledFuture mThrityMinDBMonitorFuture = null;

    class CleanDbTask implements Runnable {
        CleanDbTask() {
        }

        public void run() {
            Logger.d();
            int access$000 = LogStoreMgr.this.clearOldLogByTime();
            if (access$000 > 0) {
                LogStoreMgr.mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.CLEAN_DB, "time_ex", Double.valueOf((double) access$000)));
            }
            int count = LogStoreMgr.this.mStore.count();
            if (count > LogStoreMgr.MAX_LOG_COUNT) {
                int access$200 = LogStoreMgr.this.clearOldLogByCount(count);
                if (access$200 > 0) {
                    LogStoreMgr.mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.CLEAN_DB, "count_ex", Double.valueOf((double) access$200)));
                }
            }
        }
    }

    class CleanLogTask implements Runnable {
        CleanLogTask() {
        }

        public void run() {
            Logger.d((String) LogStoreMgr.TAG, "CleanLogTask");
            int count = LogStoreMgr.this.mStore.count();
            if (count > LogStoreMgr.MAX_LOG_COUNT) {
                LogStoreMgr.this.clearOldLogByCount(count);
            }
        }
    }

    class MonitorDBTask implements Runnable {
        private int min = 0;

        MonitorDBTask() {
        }

        public MonitorDBTask setMin(int i) {
            this.min = i;
            return this;
        }

        public void run() {
            try {
                int count = LogStoreMgr.this.mStore.count();
                double dbFileSize = LogStoreMgr.this.mStore.getDbFileSize();
                double systemFreeSize = SystemUtils.getSystemFreeSize();
                HashMap hashMap = new HashMap();
                hashMap.put("min", Integer.valueOf(this.min));
                hashMap.put("dbLeft", Integer.valueOf(count));
                hashMap.put("dbFileSize", Double.valueOf(dbFileSize));
                hashMap.put("freeSize", Double.valueOf(systemFreeSize));
                LogStoreMgr.mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.DB_MONITOR, JSON.toJSONString(hashMap), Double.valueOf(1.0d)));
            } catch (Throwable unused) {
            }
        }
    }

    public void onForeground() {
    }

    private LogStoreMgr() {
        TaskExecutor.getInstance().submit(new CleanDbTask());
        BackgroundTrigger.registerCallback(this);
    }

    public static LogStoreMgr getInstance() {
        return mInstance;
    }

    public void add(Log log) {
        if (Logger.isDebug()) {
            Logger.i((String) TAG, "Log", log.getContent());
        }
        this.mLogs.add(log);
        if (this.mLogs.size() >= 45 || Variables.getInstance().isRealTimeDebug()) {
            this.mStoreFuture = TaskExecutor.getInstance().schedule(null, this.mStoreTask, 0);
        } else if (this.mStoreFuture == null || this.mStoreFuture.isDone()) {
            this.mStoreFuture = TaskExecutor.getInstance().schedule(this.mStoreFuture, this.mStoreTask, STORE_INTERVAL);
        }
        synchronized (Lock_Object) {
            int i = logCount + 1;
            logCount = i;
            if (i > 5000) {
                logCount = 0;
                TaskExecutor.getInstance().submit(new CleanLogTask());
            }
        }
    }

    public void addLogAndSave(Log log) {
        add(log);
        store();
    }

    public int delete(List<Log> list) {
        return this.mStore.delete(list);
    }

    public void update(List<Log> list) {
        this.mStore.update(list);
    }

    public void updateLogPriority(List<Log> list) {
        this.mStore.updateLogPriority(list);
    }

    public List<Log> get(int i) {
        return this.mStore.get(i);
    }

    public synchronized void store() {
        Logger.d();
        ArrayList arrayList = null;
        try {
            synchronized (this.mLogs) {
                if (this.mLogs.size() > 0) {
                    arrayList = new ArrayList(this.mLogs);
                    this.mLogs.clear();
                }
            }
            if (arrayList != null) {
                if (arrayList.size() > 0) {
                    this.mStore.insert(arrayList);
                    dispatcherLogChangeEvent(1, arrayList.size());
                }
            }
        } catch (Throwable unused) {
        }
    }

    public void clear() {
        Logger.d((String) TAG, "[clear]");
        this.mStore.clear();
        this.mLogs.clear();
    }

    public long count() {
        Logger.d((String) TAG, "[count] memory count:", Integer.valueOf(this.mLogs.size()), " db count:", Integer.valueOf(this.mStore.count()));
        return (long) (this.mStore.count() + this.mLogs.size());
    }

    @Deprecated
    public long memoryCount() {
        return (long) this.mLogs.size();
    }

    public long dbCount() {
        return (long) this.mStore.count();
    }

    public void registerLogChangeListener(ILogChangeListener iLogChangeListener) {
        this.mLogChangeListeners.add(iLogChangeListener);
    }

    public void unRegisterChangeListener(ILogChangeListener iLogChangeListener) {
        this.mLogChangeListeners.remove(iLogChangeListener);
    }

    private void dispatcherLogChangeEvent(int i, int i2) {
        Logger.d();
        for (int i3 = 0; i3 < this.mLogChangeListeners.size(); i3++) {
            ILogChangeListener iLogChangeListener = this.mLogChangeListeners.get(i3);
            if (iLogChangeListener != null) {
                switch (i) {
                    case 1:
                        iLogChangeListener.onInsert((long) i2, dbCount());
                        break;
                    case 2:
                        iLogChangeListener.onDelete((long) i2, dbCount());
                        break;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public int clearOldLogByTime() {
        Logger.d();
        Calendar instance = Calendar.getInstance();
        instance.add(5, -3);
        return this.mStore.clearOldLogByField("time", String.valueOf(instance.getTimeInMillis()));
    }

    /* access modifiers changed from: private */
    public int clearOldLogByCount(int i) {
        int i2;
        if (i > MAX_LOG_COUNT) {
            i2 = this.mStore.clearOldLogByCount((i - 9000) + 1000);
        } else {
            i2 = 0;
        }
        Logger.d((String) TAG, "clearOldLogByCount", Integer.valueOf(i2));
        return i;
    }

    public void onBackground() {
        this.mStoreFuture = TaskExecutor.getInstance().schedule(null, this.mStoreTask, 0);
        this.mOneMinDBMonitorFuture = TaskExecutor.getInstance().schedule(this.mOneMinDBMonitorFuture, new MonitorDBTask().setMin(1), 60000);
        this.mThrityMinDBMonitorFuture = TaskExecutor.getInstance().schedule(this.mThrityMinDBMonitorFuture, new MonitorDBTask().setMin(30), TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL);
    }
}
