package com.alibaba.appmonitor.event;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.core.network.NetworkUtil;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.appmonitor.model.Metric;
import com.alibaba.appmonitor.model.MetricRepo;
import com.alibaba.appmonitor.model.MetricValueSet;
import com.alibaba.appmonitor.model.UTDimensionValueSet;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.util.UTUtil;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EventRepo {
    private static final String TAG = "EventRepo";
    private static final String TAG_COMMIT_DAY = "commitDay";
    private static EventRepo eventRepo;
    private Map<String, DurationEvent> durationEventMap = new ConcurrentHashMap();
    private Map<UTDimensionValueSet, MetricValueSet> eventMap = new ConcurrentHashMap();
    private AtomicInteger mAlarmCounter = new AtomicInteger(0);
    private AtomicInteger mCountCounter = new AtomicInteger(0);
    private AtomicInteger mSTATCounter = new AtomicInteger(0);
    private SimpleDateFormat mSdf = new SimpleDateFormat("yyyy-MM-dd");

    public static synchronized EventRepo getRepo() {
        EventRepo eventRepo2;
        synchronized (EventRepo.class) {
            try {
                if (eventRepo == null) {
                    eventRepo = new EventRepo();
                }
                eventRepo2 = eventRepo;
            }
        }
        return eventRepo2;
    }

    private EventRepo() {
    }

    private UTDimensionValueSet fetchUTDimensionValues(int i, Long l, String str, String str2) {
        UTDimensionValueSet uTDimensionValueSet = (UTDimensionValueSet) BalancedPool.getInstance().poll(UTDimensionValueSet.class, new Object[0]);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            uTDimensionValueSet.setValue(LogField.ACCESS.toString(), NetworkUtil.getAccess(Variables.getInstance().getContext()));
            uTDimensionValueSet.setValue(LogField.ACCESS_SUBTYPE.toString(), NetworkUtil.getAccsssSubType(Variables.getInstance().getContext()));
        } else {
            uTDimensionValueSet.setValue(LogField.ACCESS.toString(), str);
            uTDimensionValueSet.setValue(LogField.ACCESS_SUBTYPE.toString(), str2);
        }
        uTDimensionValueSet.setValue(LogField.USERID.toString(), Variables.getInstance().getUserid());
        uTDimensionValueSet.setValue(LogField.USERNICK.toString(), Variables.getInstance().getUsernick());
        uTDimensionValueSet.setValue(LogField.EVENTID.toString(), String.valueOf(i));
        if (l == null) {
            l = Long.valueOf(System.currentTimeMillis() / 1000);
        }
        uTDimensionValueSet.setValue(TAG_COMMIT_DAY, this.mSdf.format(new Date(l.longValue() * 1000)));
        return uTDimensionValueSet;
    }

    public void alarmEventSuccessIncr(int i, String str, String str2, String str3) {
        alarmEventSuccessIncr(i, str, str2, str3, null, null, null);
    }

    public void alarmEventSuccessIncr(int i, String str, String str2, String str3, Long l, String str4, String str5) {
        UTDimensionValueSet fetchUTDimensionValues = fetchUTDimensionValues(i, l, str4, str5);
        AlarmEvent alarmEvent = (AlarmEvent) getEvent(fetchUTDimensionValues, str, str2, str3, AlarmEvent.class);
        if (alarmEvent != null) {
            alarmEvent.incrSuccess(l);
        }
        if (Variables.getInstance().isApRealTimeDebugging()) {
            AlarmEvent alarmEvent2 = (AlarmEvent) BalancedPool.getInstance().poll(AlarmEvent.class, Integer.valueOf(i), str, str2, str3);
            alarmEvent2.incrSuccess(l);
            UTUtil.sendRealDebugEvent(fetchUTDimensionValues, alarmEvent2);
        }
        checkUploadEvent(EventType.getEventType(i), this.mAlarmCounter);
    }

    public void alarmEventFailIncr(int i, String str, String str2, String str3, String str4, String str5) {
        alarmEventFailIncr(i, str, str2, str3, str4, str5, null, null, null);
    }

    public void alarmEventFailIncr(int i, String str, String str2, String str3, String str4, String str5, Long l, String str6, String str7) {
        UTDimensionValueSet fetchUTDimensionValues = fetchUTDimensionValues(i, l, str6, str7);
        AlarmEvent alarmEvent = (AlarmEvent) getEvent(fetchUTDimensionValues, str, str2, str3, AlarmEvent.class);
        if (alarmEvent != null) {
            alarmEvent.incrFail(l);
            alarmEvent.addError(str4, str5);
        }
        if (Variables.getInstance().isApRealTimeDebugging()) {
            AlarmEvent alarmEvent2 = (AlarmEvent) BalancedPool.getInstance().poll(AlarmEvent.class, Integer.valueOf(i), str, str2, str3);
            alarmEvent2.incrFail(l);
            alarmEvent2.addError(str4, str5);
            UTUtil.sendRealDebugEvent(fetchUTDimensionValues, alarmEvent2);
        }
        checkUploadEvent(EventType.getEventType(i), this.mAlarmCounter);
    }

    public void countEventCommit(int i, String str, String str2, String str3, double d) {
        countEventCommit(i, str, str2, str3, d, null, null, null);
    }

    public void countEventCommit(int i, String str, String str2, String str3, double d, Long l, String str4, String str5) {
        UTDimensionValueSet fetchUTDimensionValues = fetchUTDimensionValues(i, l, str4, str5);
        CountEvent countEvent = (CountEvent) getEvent(fetchUTDimensionValues, str, str2, str3, CountEvent.class);
        if (countEvent != null) {
            countEvent.addValue(d, l);
        }
        if (Variables.getInstance().isApRealTimeDebugging()) {
            CountEvent countEvent2 = (CountEvent) BalancedPool.getInstance().poll(CountEvent.class, Integer.valueOf(i), str, str2, str3);
            countEvent2.addValue(d, l);
            UTUtil.sendRealDebugEvent(fetchUTDimensionValues, countEvent2);
        }
        checkUploadEvent(EventType.getEventType(i), this.mCountCounter);
    }

    public void commitStatEvent(int i, String str, String str2, MeasureValueSet measureValueSet, DimensionValueSet dimensionValueSet) {
        commitStatEvent(i, str, str2, measureValueSet, dimensionValueSet, null, null, null);
    }

    public void commitStatEvent(int i, String str, String str2, MeasureValueSet measureValueSet, DimensionValueSet dimensionValueSet, Long l, String str3, String str4) {
        String str5 = str;
        String str6 = str2;
        MeasureValueSet measureValueSet2 = measureValueSet;
        DimensionValueSet dimensionValueSet2 = dimensionValueSet;
        Metric metric = MetricRepo.getRepo().getMetric(str5, str6);
        if (metric != null) {
            if (metric.getDimensionSet() != null) {
                metric.getDimensionSet().setConstantValue(dimensionValueSet2);
            }
            if (metric.getMeasureSet() != null) {
                metric.getMeasureSet().setConstantValue(measureValueSet2);
            }
            int i2 = i;
            UTDimensionValueSet fetchUTDimensionValues = fetchUTDimensionValues(i2, l, str3, str4);
            StatEvent statEvent = (StatEvent) getEvent(fetchUTDimensionValues, str5, str6, null, StatEvent.class);
            if (statEvent != null) {
                statEvent.commit(dimensionValueSet2, measureValueSet2);
            }
            if (Variables.getInstance().isApRealTimeDebugging()) {
                StatEvent statEvent2 = (StatEvent) BalancedPool.getInstance().poll(StatEvent.class, Integer.valueOf(i2), str5, str6);
                statEvent2.commit(dimensionValueSet2, measureValueSet2);
                UTUtil.sendRealDebugEvent(fetchUTDimensionValues, statEvent2);
            }
            checkUploadEvent(EventType.getEventType(i2), this.mSTATCounter);
            return;
        }
        Logger.e((String) "metric is null,stat commit failed,please call AppMonitor.register() once before AppMonitor.STAT.commit()", new Object[0]);
    }

    public void beginStatEvent(Integer num, String str, String str2, String str3) {
        String transactionId = getTransactionId(str, str2);
        if (transactionId != null) {
            beginStatEvent(transactionId, num, str, str2, str3);
        }
    }

    public void beginStatEvent(String str, Integer num, String str2, String str3, String str4) {
        DurationEvent durationEvent;
        Metric metric = MetricRepo.getRepo().getMetric(str2, str3);
        if (metric == null || metric.getMeasureSet() == null) {
            Logger.e((String) "log discard!,metric is null,please call AppMonitor.register() once before Transaction.begin(measure)", new Object[0]);
            return;
        }
        if (metric.getMeasureSet().getMeasure(str4) != null) {
            synchronized (DurationEvent.class) {
                durationEvent = this.durationEventMap.get(str);
                if (durationEvent == null) {
                    durationEvent = (DurationEvent) BalancedPool.getInstance().poll(DurationEvent.class, num, str2, str3);
                    this.durationEventMap.put(str, durationEvent);
                }
            }
            durationEvent.start(str4);
        }
    }

    public void endStatEvent(String str, String str2, String str3) {
        String transactionId = getTransactionId(str, str2);
        if (transactionId != null) {
            endStatEvent(transactionId, str3, true);
        }
    }

    public void endStatEvent(String str, String str2, boolean z) {
        DurationEvent durationEvent = this.durationEventMap.get(str);
        if (durationEvent != null && durationEvent.end(str2)) {
            this.durationEventMap.remove(str);
            if (z) {
                resetTransactionId(durationEvent.module, durationEvent.monitorPoint);
            }
            commitStatEvent(durationEvent.eventId, durationEvent.module, durationEvent.monitorPoint, durationEvent.getMeasureValues(), durationEvent.getDimensionValues());
            BalancedPool.getInstance().offer(durationEvent);
        }
    }

    public void commitElapseEventDimensionValue(String str, Integer num, String str2, String str3, DimensionValueSet dimensionValueSet) {
        DurationEvent durationEvent;
        synchronized (DurationEvent.class) {
            durationEvent = this.durationEventMap.get(str);
            if (durationEvent == null) {
                durationEvent = (DurationEvent) BalancedPool.getInstance().poll(DurationEvent.class, num, str2, str3);
                this.durationEventMap.put(str, durationEvent);
            }
        }
        durationEvent.commitDimensionValue(dimensionValueSet);
    }

    private String getTransactionId(String str, String str2) {
        Metric metric = MetricRepo.getRepo().getMetric(str, str2);
        if (metric != null) {
            return metric.getTransactionId();
        }
        return null;
    }

    private void resetTransactionId(String str, String str2) {
        Metric metric = MetricRepo.getRepo().getMetric(str, str2);
        if (metric != null) {
            metric.resetTransactionId();
        }
    }

    private Event getEvent(UTDimensionValueSet uTDimensionValueSet, String str, String str2, String str3, Class<? extends Event> cls) {
        MetricValueSet metricValueSet;
        if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(str2)) {
            Integer eventId = uTDimensionValueSet.getEventId();
            if (eventId != null) {
                synchronized (this.eventMap) {
                    metricValueSet = this.eventMap.get(uTDimensionValueSet);
                    if (metricValueSet == null) {
                        metricValueSet = (MetricValueSet) BalancedPool.getInstance().poll(MetricValueSet.class, new Object[0]);
                        this.eventMap.put(uTDimensionValueSet, metricValueSet);
                        Logger.d((String) TAG, "put in Map utDimensionValues", uTDimensionValueSet);
                    }
                }
                return metricValueSet.getEvent(eventId, str, str2, str3, cls);
            }
        }
        return null;
    }

    private void checkUploadEvent(EventType eventType, AtomicInteger atomicInteger) {
        int incrementAndGet = atomicInteger.incrementAndGet();
        Logger.i(eventType.toString(), " EVENT size:", String.valueOf(incrementAndGet));
        if (incrementAndGet >= eventType.getTriggerCount()) {
            Logger.d((String) TAG, eventType.toString(), " event size exceed trigger count.");
            uploadEvent(eventType.getEventId());
        }
    }

    public Map<UTDimensionValueSet, List<Event>> getUploadEvent(int i) {
        HashMap hashMap = new HashMap();
        synchronized (this.eventMap) {
            Iterator<Entry<UTDimensionValueSet, MetricValueSet>> it = this.eventMap.entrySet().iterator();
            while (it.hasNext()) {
                Entry next = it.next();
                UTDimensionValueSet uTDimensionValueSet = (UTDimensionValueSet) next.getKey();
                MetricValueSet metricValueSet = (MetricValueSet) next.getValue();
                if (uTDimensionValueSet.getEventId().intValue() == i) {
                    if (metricValueSet != null) {
                        hashMap.put(uTDimensionValueSet, metricValueSet.getEvents());
                    } else {
                        Logger.d((String) "error", "utDimensionValues", uTDimensionValueSet);
                    }
                    it.remove();
                }
            }
        }
        getCounter(i).set(0);
        return hashMap;
    }

    public void cleanExpiredEvent() {
        ArrayList arrayList = new ArrayList(this.durationEventMap.keySet());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            String str = (String) arrayList.get(i);
            DurationEvent durationEvent = this.durationEventMap.get(str);
            if (durationEvent != null && durationEvent.isExpired()) {
                this.durationEventMap.remove(str);
            }
        }
    }

    public void uploadEvent(int i) {
        final Map<UTDimensionValueSet, List<Event>> uploadEvent = getUploadEvent(i);
        TaskExecutor.getInstance().submit(new Runnable() {
            public void run() {
                UTUtil.uploadEvent(uploadEvent);
            }
        });
    }

    private AtomicInteger getCounter(int i) {
        if (65501 == i) {
            return this.mAlarmCounter;
        }
        if (65502 == i) {
            return this.mCountCounter;
        }
        if (65503 == i) {
            return this.mSTATCounter;
        }
        return null;
    }
}
