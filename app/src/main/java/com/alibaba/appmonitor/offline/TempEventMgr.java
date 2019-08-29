package com.alibaba.appmonitor.offline;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.config.SystemConfigMgr.IKVChangeListener;
import com.alibaba.analytics.core.db.DBMgr;
import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.selfmonitor.CrashDispatcher;
import com.alibaba.analytics.core.selfmonitor.CrashListener;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.appmonitor.delegate.BackgroundTrigger;
import com.alibaba.appmonitor.delegate.BackgroundTrigger.AppStatusChangeCallback;
import com.alibaba.appmonitor.event.EventRepo;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.model.Metric;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class TempEventMgr implements IKVChangeListener, CrashListener, AppStatusChangeCallback {
    private static final int DB_MAX_COUNT = 50000;
    private static final int MAX_SIZE = 100;
    private static final String OFFLINE_DURATION = "offline_duration";
    private static final int ONE_HOUR_SEC = 3600;
    private static final int ONE_SECOND = 1000;
    private static TempEventMgr instance = new TempEventMgr();
    private Runnable commitTask = new Runnable() {
        public void run() {
            TempEventMgr.this.commitEventsToComputer();
        }
    };
    private List<TempEvent> mAlarmEventLists = Collections.synchronizedList(new ArrayList());
    private ScheduledFuture mCommitFuture = null;
    private List<TempEvent> mCounterEventLists = Collections.synchronizedList(new ArrayList());
    private long mCurrentDuration = -2;
    private List<Metric> mMetricLists = Collections.synchronizedList(new ArrayList());
    private List<TempEvent> mStatEventLists = Collections.synchronizedList(new ArrayList());
    private ScheduledFuture mStoreFuture = null;
    private Runnable storeTask = new Runnable() {
        public void run() {
            TempEventMgr.this.store();
        }
    };

    class CleanTableTask implements Runnable {
        private CleanTableTask() {
        }

        public void run() {
            TempEventMgr.this.clearTempAlarmTable();
            TempEventMgr.this.clearTempCounterTable();
            TempEventMgr.this.clearTempStatTable();
        }
    }

    public void onForeground() {
    }

    private TempEventMgr() {
        BackgroundTrigger.registerCallback(this);
        CrashDispatcher.getInstance().addCrashListener(this);
        SystemConfigMgr.getInstance().register(OFFLINE_DURATION, this);
        TaskExecutor.getInstance().submit(new CleanTableTask());
        startCommitTask();
    }

    public static TempEventMgr getInstance() {
        return instance;
    }

    public void add(EventType eventType, TempEvent tempEvent) {
        Logger.d();
        if (EventType.ALARM == eventType) {
            this.mAlarmEventLists.add(tempEvent);
        } else if (EventType.COUNTER == eventType) {
            this.mCounterEventLists.add(tempEvent);
        } else if (EventType.STAT == eventType) {
            this.mStatEventLists.add(tempEvent);
        }
        if (this.mAlarmEventLists.size() >= 100 || this.mCounterEventLists.size() >= 100 || this.mStatEventLists.size() >= 100) {
            this.mStoreFuture = TaskExecutor.getInstance().schedule(null, this.storeTask, 0);
            return;
        }
        if (this.mStoreFuture == null || (this.mStoreFuture != null && this.mStoreFuture.isDone())) {
            this.mStoreFuture = TaskExecutor.getInstance().schedule(this.mStoreFuture, this.storeTask, StatisticConfig.MIN_UPLOAD_INTERVAL);
        }
    }

    public void add(Metric metric) {
        Logger.d();
        if (metric != null) {
            this.mMetricLists.add(metric);
        }
        if (this.mMetricLists.size() >= 100) {
            this.mStoreFuture = TaskExecutor.getInstance().schedule(null, this.storeTask, 0);
        } else {
            this.mStoreFuture = TaskExecutor.getInstance().schedule(this.mStoreFuture, this.storeTask, StatisticConfig.MIN_UPLOAD_INTERVAL);
        }
    }

    public Metric getMetric(String str, String str2) {
        StringBuilder sb = new StringBuilder("module=\"");
        sb.append(str);
        sb.append("\" and monitor_point=\"");
        sb.append(str2);
        sb.append("\"");
        List<? extends Entity> find = Variables.getInstance().getDbMgr().find(Metric.class, sb.toString(), null, 1);
        if (find == null || find.size() <= 0) {
            return null;
        }
        return (Metric) find.get(0);
    }

    public void store() {
        Logger.d();
        clearAndStore(this.mAlarmEventLists);
        clearAndStore(this.mCounterEventLists);
        clearAndStore(this.mStatEventLists);
        clearAndSyncMetric(this.mMetricLists);
    }

    private void clearAndStore(List<?> list) {
        ArrayList arrayList;
        if (list != null && list.size() > 0) {
            synchronized (list) {
                arrayList = new ArrayList(list);
                list.clear();
            }
            Variables.getInstance().getDbMgr().insert((List<? extends Entity>) arrayList);
        }
    }

    private void clearAndSyncMetric(List<Metric> list) {
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            synchronized (list) {
                for (int i = 0; i < list.size(); i++) {
                    Metric metric = list.get(i);
                    Metric metric2 = getMetric(metric.getModule(), metric.getMonitorPoint());
                    if (metric2 != null) {
                        metric._id = metric2._id;
                        arrayList.add(metric);
                    } else {
                        arrayList2.add(metric);
                    }
                }
                list.clear();
            }
            if (arrayList.size() > 0) {
                Variables.getInstance().getDbMgr().update((List<? extends Entity>) arrayList);
            }
            if (arrayList2.size() > 0) {
                Variables.getInstance().getDbMgr().insert((List<? extends Entity>) arrayList2);
            }
        }
    }

    public List<? extends TempEvent> getExpireEvents(EventType eventType, int i) {
        return Variables.getInstance().getDbMgr().find(getCls(eventType), "commit_time<".concat(String.valueOf((System.currentTimeMillis() / 1000) - (getDuration() / 1000))), "access,sub_access,module,monitor_point", i);
    }

    public List<? extends TempEvent> get(EventType eventType, int i) {
        return Variables.getInstance().getDbMgr().find(getCls(eventType), null, null, i);
    }

    private Class<? extends Entity> getCls(EventType eventType) {
        if (EventType.ALARM == eventType) {
            return TempAlarm.class;
        }
        if (EventType.COUNTER == eventType) {
            return TempCounter.class;
        }
        if (EventType.STAT == eventType) {
            return TempStat.class;
        }
        return TempEvent.class;
    }

    public void onBackground() {
        Logger.d();
        this.mStoreFuture = TaskExecutor.getInstance().schedule(null, this.storeTask, 0);
    }

    /* access modifiers changed from: private */
    public void commitEventsToComputer() {
        int i;
        Logger.d();
        EventType[] values = EventType.values();
        int length = values.length;
        char c = 0;
        int i2 = 0;
        while (i2 < length) {
            EventType eventType = values[i2];
            while (true) {
                List<? extends TempEvent> expireEvents = getExpireEvents(eventType, 500);
                Object[] objArr = new Object[4];
                objArr[c] = "type";
                objArr[1] = eventType;
                objArr[2] = "events.size()";
                objArr[3] = Integer.valueOf(expireEvents.size());
                Logger.d((String) null, objArr);
                if (expireEvents.size() != 0) {
                    int i3 = 0;
                    while (i3 < expireEvents.size()) {
                        switch (eventType) {
                            case ALARM:
                                i = i2;
                                TempAlarm tempAlarm = (TempAlarm) expireEvents.get(i3);
                                if (!tempAlarm.isSuccessEvent()) {
                                    EventRepo.getRepo().alarmEventFailIncr(eventType.getEventId(), tempAlarm.module, tempAlarm.monitorPoint, tempAlarm.arg, tempAlarm.errCode, tempAlarm.errMsg, Long.valueOf(tempAlarm.commitTime), tempAlarm.access, tempAlarm.accessSubType);
                                    break;
                                } else {
                                    EventRepo.getRepo().alarmEventSuccessIncr(eventType.getEventId(), tempAlarm.module, tempAlarm.monitorPoint, tempAlarm.arg, Long.valueOf(tempAlarm.commitTime), tempAlarm.access, tempAlarm.accessSubType);
                                    break;
                                }
                            case COUNTER:
                                TempCounter tempCounter = (TempCounter) expireEvents.get(i3);
                                i = i2;
                                EventRepo.getRepo().countEventCommit(eventType.getEventId(), tempCounter.module, tempCounter.monitorPoint, tempCounter.arg, tempCounter.value, Long.valueOf(tempCounter.commitTime), tempCounter.access, tempCounter.accessSubType);
                                break;
                            case STAT:
                                TempStat tempStat = (TempStat) expireEvents.get(i3);
                                EventRepo.getRepo().commitStatEvent(eventType.getEventId(), tempStat.module, tempStat.monitorPoint, tempStat.getMeasureVauleSet(), tempStat.getDimensionValue());
                                break;
                        }
                        i = i2;
                        i3++;
                        i2 = i;
                    }
                    int i4 = i2;
                    delete(expireEvents);
                    c = 0;
                } else {
                    i2++;
                    c = 0;
                }
            }
        }
    }

    private void delete(List<? extends TempEvent> list) {
        Variables.getInstance().getDbMgr().delete(list);
    }

    public void onCrash(Thread thread, Throwable th) {
        Logger.d();
        store();
    }

    public void clear() {
        Variables.getInstance().getDbMgr().clear(TempAlarm.class);
        Variables.getInstance().getDbMgr().clear(TempCounter.class);
        Variables.getInstance().getDbMgr().clear(TempStat.class);
    }

    public void onChange(String str, String str2) {
        if (OFFLINE_DURATION.equalsIgnoreCase(str)) {
            startCommitTask();
        }
    }

    private void startCommitTask() {
        long duration = getDuration();
        if (this.mCurrentDuration != duration) {
            this.mCurrentDuration = duration;
            this.mCommitFuture = TaskExecutor.getInstance().scheduleAtFixedRate(this.mCommitFuture, this.commitTask, this.mCurrentDuration);
        }
    }

    private long getDuration() {
        int i = SystemConfigMgr.getInstance().getInt(OFFLINE_DURATION);
        int i2 = i <= 0 ? DiskExpUtils.CHECK_TIME_INTERVAL : i <= ONE_HOUR_SEC ? 3600000 : i * 1000;
        return (long) i2;
    }

    /* access modifiers changed from: private */
    public void clearTempCounterTable() {
        clearEvent(TempCounter.class);
    }

    /* access modifiers changed from: private */
    public void clearTempAlarmTable() {
        clearEvent(TempAlarm.class);
    }

    /* access modifiers changed from: private */
    public void clearTempStatTable() {
        clearEvent(TempStat.class);
    }

    private void clearEvent(Class<? extends Entity> cls) {
        clearExpiredEvent(cls);
        if (Variables.getInstance().getDbMgr().count(cls) > 50000) {
            clearEventByCount(cls, 10000);
        }
    }

    private long clearEventByCount(Class<? extends Entity> cls, int i) {
        String tablename = Variables.getInstance().getDbMgr().getTablename(cls);
        DBMgr dbMgr = Variables.getInstance().getDbMgr();
        StringBuilder sb = new StringBuilder(" _id in ( select _id from ");
        sb.append(tablename);
        sb.append("  ORDER BY  _id ASC LIMIT ");
        sb.append(i);
        sb.append(" )");
        return (long) dbMgr.delete(cls, sb.toString(), null);
    }

    private int clearExpiredEvent(Class<? extends Entity> cls) {
        Calendar instance2 = Calendar.getInstance();
        instance2.add(5, -7);
        return Variables.getInstance().getDbMgr().delete(cls, "commit_time< ".concat(String.valueOf(instance2.getTimeInMillis() / 1000)), null);
    }
}
