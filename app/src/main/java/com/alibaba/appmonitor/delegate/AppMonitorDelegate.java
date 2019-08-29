package com.alibaba.appmonitor.delegate;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.network.NetworkUtil;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorConfigMgr;
import com.alibaba.analytics.core.selfmonitor.exception.AppMonitorException;
import com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder;
import com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder.ExceptionType;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.appmonitor.event.EventRepo;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.model.Metric;
import com.alibaba.appmonitor.model.MetricRepo;
import com.alibaba.appmonitor.offline.TempAlarm;
import com.alibaba.appmonitor.offline.TempCounter;
import com.alibaba.appmonitor.offline.TempEvent;
import com.alibaba.appmonitor.offline.TempEventMgr;
import com.alibaba.appmonitor.sample.AMSamplingMgr;
import com.alibaba.mtl.appmonitor.Transaction;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.Measure;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import com.ut.mini.core.sign.UTBaseRequestAuthentication;
import com.ut.mini.core.sign.UTSecurityThridRequestAuthentication;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AppMonitorDelegate {
    public static boolean IS_DEBUG = false;
    private static final String TAG = "AppMonitorDelegate";
    private static Application application = null;
    private static Map<String, String> mGlobalArgsMap = new ConcurrentHashMap();
    static volatile boolean sdkInit = false;

    public static class Alarm {
        public static void setStatisticsInterval(int i) {
            EventType.ALARM.setStatisticsInterval(i);
            AppMonitorDelegate.setStatisticsInterval(EventType.ALARM, i);
        }

        public static void setSampling(int i) {
            AMSamplingMgr.getInstance().setEventTypeSampling(EventType.ALARM, i);
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            return AMSamplingMgr.getInstance().checkAlarmSampled(str, str2, Boolean.TRUE, null);
        }

        public static void commitSuccess(String str, String str2) {
            commitSuccess(str, str2, null);
        }

        public static void commitSuccess(String str, String str2, String str3) {
            String str4;
            try {
                if (!TextUtils.isEmpty(str)) {
                    if (!TextUtils.isEmpty(str2)) {
                        if (!AppMonitorDelegate.sdkInit || !Variables.isNotDisAM() || !EventType.ALARM.isOpen() || (!AppMonitorDelegate.IS_DEBUG && !AMSamplingMgr.getInstance().checkAlarmSampled(str, str2, Boolean.TRUE, null))) {
                            Logger.d((String) "log discard !", TempEvent.TAG_MODULE, str, "monitorPoint", str2, "arg", str3);
                            return;
                        }
                        Logger.d((String) "commitSuccess", TempEvent.TAG_MODULE, str, "monitorPoint", str2, "arg", str3);
                        if (AMSamplingMgr.getInstance().isOffline(EventType.ALARM, str, str2)) {
                            Context context = Variables.getInstance().getContext();
                            if (SelfMonitorConfigMgr.getInstance().isNeedMonitorForOffline(EventType.ALARM, str, str2)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(str);
                                sb.append("_abtest");
                                str4 = sb.toString();
                                EventRepo.getRepo().alarmEventSuccessIncr(EventType.ALARM.getEventId(), str, str2, str3);
                            } else {
                                str4 = str;
                            }
                            TempEventMgr instance = TempEventMgr.getInstance();
                            EventType eventType = EventType.ALARM;
                            TempAlarm tempAlarm = new TempAlarm(str4, str2, str3, null, null, true, NetworkUtil.getAccess(context), NetworkUtil.getAccsssSubType(context));
                            instance.add(eventType, tempAlarm);
                            return;
                        }
                        EventRepo.getRepo().alarmEventSuccessIncr(EventType.ALARM.getEventId(), str, str2, str3);
                        return;
                    }
                }
                Logger.w((String) AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                ExceptionEventBuilder.log(ExceptionType.AP, th);
            }
        }

        public static void commitFail(String str, String str2, String str3, String str4) {
            commitFail(str, str2, null, str3, str4);
        }

        public static void commitFail(String str, String str2, String str3, String str4, String str5) {
            String str6;
            String str7 = str;
            String str8 = str2;
            try {
                if (!TextUtils.isEmpty(str)) {
                    if (!TextUtils.isEmpty(str2)) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("_status", "0");
                        if (!AppMonitorDelegate.sdkInit || !Variables.isNotDisAM() || !EventType.ALARM.isOpen() || (!AppMonitorDelegate.IS_DEBUG && !AMSamplingMgr.getInstance().checkAlarmSampled(str7, str8, Boolean.FALSE, hashMap))) {
                            Logger.d((String) "log discard !", TempEvent.TAG_MODULE, str7, "monitorPoint", str8, "errorCode:", str4, "errorMsg:", str5);
                            return;
                        }
                        Logger.d((String) "commitFail ", TempEvent.TAG_MODULE, str7, "monitorPoint", str8, "errorCode:", str4, "errorMsg:", str5);
                        if (AMSamplingMgr.getInstance().isOffline(EventType.ALARM, str7, str8)) {
                            if (SelfMonitorConfigMgr.getInstance().isNeedMonitorForOffline(EventType.ALARM, str7, str8)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(str7);
                                sb.append("_abtest");
                                String sb2 = sb.toString();
                                EventRepo.getRepo().alarmEventFailIncr(EventType.ALARM.getEventId(), str7, str8, str3, str4, str5);
                                str6 = sb2;
                            } else {
                                str6 = str7;
                            }
                            Context context = Variables.getInstance().getContext();
                            TempEventMgr instance = TempEventMgr.getInstance();
                            EventType eventType = EventType.ALARM;
                            TempAlarm tempAlarm = new TempAlarm(str6, str8, str3, str4, str5, false, NetworkUtil.getAccess(context), NetworkUtil.getAccsssSubType(context));
                            instance.add(eventType, tempAlarm);
                            return;
                        }
                        EventRepo.getRepo().alarmEventFailIncr(EventType.ALARM.getEventId(), str7, str8, str3, str4, str5);
                        return;
                    }
                }
                Logger.w((String) AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                ExceptionEventBuilder.log(ExceptionType.AP, th);
            }
        }
    }

    public static class Counter {
        public static void setStatisticsInterval(int i) {
            EventType.COUNTER.setStatisticsInterval(i);
            AppMonitorDelegate.setStatisticsInterval(EventType.COUNTER, i);
        }

        public static void setSampling(int i) {
            AMSamplingMgr.getInstance().setEventTypeSampling(EventType.COUNTER, i);
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            return AMSamplingMgr.getInstance().checkSampled(EventType.COUNTER, str, str2);
        }

        public static void commit(String str, String str2, double d) {
            commit(str, str2, null, d);
        }

        public static void commit(String str, String str2, String str3, double d) {
            String str4;
            try {
                if (!TextUtils.isEmpty(str)) {
                    if (!TextUtils.isEmpty(str2)) {
                        if (!AppMonitorDelegate.sdkInit || !Variables.isNotDisAM() || !EventType.COUNTER.isOpen() || (!AppMonitorDelegate.IS_DEBUG && !AMSamplingMgr.getInstance().checkSampled(EventType.COUNTER, str, str2))) {
                            Logger.d((String) "log discard !", TempEvent.TAG_MODULE, str, "monitorPoint", str2, "args", str3, "value", Double.valueOf(d));
                            return;
                        }
                        Logger.d((String) "commitCount", TempEvent.TAG_MODULE, str, "monitorPoint", str2, "args", str3, "value", Double.valueOf(d));
                        if (AMSamplingMgr.getInstance().isOffline(EventType.COUNTER, str, str2)) {
                            Context context = Variables.getInstance().getContext();
                            if (SelfMonitorConfigMgr.getInstance().isNeedMonitorForOffline(EventType.COUNTER, str, str2)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(str);
                                sb.append("_abtest");
                                str4 = sb.toString();
                                EventRepo.getRepo().countEventCommit(EventType.COUNTER.getEventId(), str, str2, str3, d);
                            } else {
                                str4 = str;
                            }
                            TempEventMgr instance = TempEventMgr.getInstance();
                            EventType eventType = EventType.COUNTER;
                            TempCounter tempCounter = new TempCounter(str4, str2, str3, d, NetworkUtil.getAccess(context), NetworkUtil.getAccsssSubType(context));
                            instance.add(eventType, tempCounter);
                            return;
                        }
                        EventRepo.getRepo().countEventCommit(EventType.COUNTER.getEventId(), str, str2, str3, d);
                        return;
                    }
                }
                Logger.w((String) AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                ExceptionEventBuilder.log(ExceptionType.AP, th);
            }
        }
    }

    public static class OffLineCounter {
        public static void setStatisticsInterval(int i) {
            EventType.COUNTER.setStatisticsInterval(i);
            AppMonitorDelegate.setStatisticsInterval(EventType.COUNTER, i);
        }

        public static void setSampling(int i) {
            AMSamplingMgr.getInstance().setEventTypeSampling(EventType.COUNTER, i);
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            return AMSamplingMgr.getInstance().checkSampled(EventType.COUNTER, str, str2);
        }

        public static void commit(String str, String str2, double d) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    if (!TextUtils.isEmpty(str2)) {
                        if (!AppMonitorDelegate.sdkInit || !Variables.isNotDisAM() || !EventType.COUNTER.isOpen() || (!AppMonitorDelegate.IS_DEBUG && !AMSamplingMgr.getInstance().checkSampled(EventType.COUNTER, str, str2))) {
                            Logger.d((String) "log discard !", "");
                            return;
                        }
                        Logger.d((String) "commitOffLineCount", TempEvent.TAG_MODULE, str, "monitorPoint", str2, "value", Double.valueOf(d));
                        EventRepo.getRepo().countEventCommit(EventType.COUNTER.getEventId(), str, str2, null, d);
                        return;
                    }
                }
                Logger.w((String) AppMonitorDelegate.TAG, "module & monitorPoint must not null");
            } catch (Throwable th) {
                ExceptionEventBuilder.log(ExceptionType.AP, th);
            }
        }
    }

    public static class Stat {
        public static void setStatisticsInterval(int i) {
            EventType.STAT.setStatisticsInterval(i);
            AppMonitorDelegate.setStatisticsInterval(EventType.STAT, i);
        }

        public static void setSampling(int i) {
            AMSamplingMgr.getInstance().setEventTypeSampling(EventType.STAT, i);
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            return AMSamplingMgr.getInstance().checkSampled(EventType.STAT, str, str2);
        }

        public static void begin(String str, String str2, String str3) {
            try {
                if (!AppMonitorDelegate.sdkInit || !Variables.isNotDisAM() || !EventType.STAT.isOpen() || (!AppMonitorDelegate.IS_DEBUG && !AMSamplingMgr.getInstance().checkSampled(EventType.STAT, str, str2))) {
                    Logger.d((String) "log discard !", "");
                    return;
                }
                Logger.d((String) AppMonitorDelegate.TAG, "statEvent begin. module: ", str, " monitorPoint: ", str2, " measureName: ", str3);
                EventRepo.getRepo().beginStatEvent(Integer.valueOf(EventType.STAT.getEventId()), str, str2, str3);
            } catch (Throwable th) {
                ExceptionEventBuilder.log(ExceptionType.AP, th);
            }
        }

        public static void end(String str, String str2, String str3) {
            try {
                if (!AppMonitorDelegate.sdkInit || !Variables.isNotDisAM() || !EventType.STAT.isOpen() || (!AppMonitorDelegate.IS_DEBUG && !AMSamplingMgr.getInstance().checkSampled(EventType.STAT, str, str2))) {
                    Logger.d((String) "log discard !", " module ", str, "monitorPoint", str2, " measureName", str3);
                    return;
                }
                Logger.d((String) "statEvent end. module: ", str, " monitorPoint: ", str2, " measureName: ", str3);
                EventRepo.getRepo().endStatEvent(str, str2, str3);
            } catch (Throwable th) {
                ExceptionEventBuilder.log(ExceptionType.AP, th);
            }
        }

        public static Transaction createTransaction(String str, String str2) {
            return createTransaction(str, str2, null);
        }

        public static Transaction createTransaction(String str, String str2, DimensionValueSet dimensionValueSet) {
            return new Transaction(Integer.valueOf(EventType.STAT.getEventId()), str, str2, dimensionValueSet);
        }

        public static void commit(String str, String str2, double d) {
            commit(str, str2, (DimensionValueSet) null, d);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
            if (com.alibaba.appmonitor.sample.AMSamplingMgr.getInstance().checkSampled(com.alibaba.appmonitor.event.EventType.STAT, r6, r7, r8 != null ? r8.getMap() : null) != false) goto L_0x002c;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void commit(java.lang.String r6, java.lang.String r7, com.alibaba.mtl.appmonitor.model.DimensionValueSet r8, double r9) {
            /*
                boolean r0 = com.alibaba.appmonitor.delegate.AppMonitorDelegate.sdkInit     // Catch:{ Throwable -> 0x0087 }
                r1 = 1
                r2 = 0
                if (r0 == 0) goto L_0x007b
                boolean r0 = com.alibaba.analytics.core.Variables.isNotDisAM()     // Catch:{ Throwable -> 0x0087 }
                if (r0 == 0) goto L_0x007b
                com.alibaba.appmonitor.event.EventType r0 = com.alibaba.appmonitor.event.EventType.STAT     // Catch:{ Throwable -> 0x0087 }
                boolean r0 = r0.isOpen()     // Catch:{ Throwable -> 0x0087 }
                if (r0 == 0) goto L_0x007b
                boolean r0 = com.alibaba.appmonitor.delegate.AppMonitorDelegate.IS_DEBUG     // Catch:{ Throwable -> 0x0087 }
                if (r0 != 0) goto L_0x002c
                com.alibaba.appmonitor.sample.AMSamplingMgr r0 = com.alibaba.appmonitor.sample.AMSamplingMgr.getInstance()     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.appmonitor.event.EventType r3 = com.alibaba.appmonitor.event.EventType.STAT     // Catch:{ Throwable -> 0x0087 }
                if (r8 == 0) goto L_0x0025
                java.util.Map r4 = r8.getMap()     // Catch:{ Throwable -> 0x0087 }
                goto L_0x0026
            L_0x0025:
                r4 = 0
            L_0x0026:
                boolean r0 = r0.checkSampled(r3, r6, r7, r4)     // Catch:{ Throwable -> 0x0087 }
                if (r0 == 0) goto L_0x007b
            L_0x002c:
                java.lang.String r0 = "AppMonitorDelegate"
                r3 = 4
                java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0087 }
                java.lang.String r4 = "statEvent commit. module: "
                r3[r2] = r4     // Catch:{ Throwable -> 0x0087 }
                r3[r1] = r6     // Catch:{ Throwable -> 0x0087 }
                r4 = 2
                java.lang.String r5 = " monitorPoint: "
                r3[r4] = r5     // Catch:{ Throwable -> 0x0087 }
                r4 = 3
                r3[r4] = r7     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.analytics.utils.Logger.d(r0, r3)     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.appmonitor.model.MetricRepo r0 = com.alibaba.appmonitor.model.MetricRepo.getRepo()     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.appmonitor.model.Metric r0 = r0.getMetric(r6, r7)     // Catch:{ Throwable -> 0x0087 }
                if (r0 == 0) goto L_0x007a
                com.alibaba.mtl.appmonitor.model.MeasureSet r0 = r0.getMeasureSet()     // Catch:{ Throwable -> 0x0087 }
                java.util.List r0 = r0.getMeasures()     // Catch:{ Throwable -> 0x0087 }
                int r3 = r0.size()     // Catch:{ Throwable -> 0x0087 }
                if (r3 != r1) goto L_0x007a
                java.lang.Object r0 = r0.get(r2)     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.mtl.appmonitor.model.Measure r0 = (com.alibaba.mtl.appmonitor.model.Measure) r0     // Catch:{ Throwable -> 0x0087 }
                java.lang.String r0 = r0.getName()     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.appmonitor.pool.BalancedPool r1 = com.alibaba.appmonitor.pool.BalancedPool.getInstance()     // Catch:{ Throwable -> 0x0087 }
                java.lang.Class<com.alibaba.mtl.appmonitor.model.MeasureValueSet> r3 = com.alibaba.mtl.appmonitor.model.MeasureValueSet.class
                java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.appmonitor.pool.Reusable r1 = r1.poll(r3, r2)     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.mtl.appmonitor.model.MeasureValueSet r1 = (com.alibaba.mtl.appmonitor.model.MeasureValueSet) r1     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.mtl.appmonitor.model.MeasureValueSet r9 = r1.setValue(r0, r9)     // Catch:{ Throwable -> 0x0087 }
                commit(r6, r7, r8, r9)     // Catch:{ Throwable -> 0x0087 }
            L_0x007a:
                return
            L_0x007b:
                java.lang.String r6 = "log discard !"
                java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0087 }
                java.lang.String r8 = ""
                r7[r2] = r8     // Catch:{ Throwable -> 0x0087 }
                com.alibaba.analytics.utils.Logger.d(r6, r7)     // Catch:{ Throwable -> 0x0087 }
                return
            L_0x0087:
                r6 = move-exception
                com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder$ExceptionType r7 = com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder.ExceptionType.AP
                com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder.log(r7, r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.appmonitor.delegate.AppMonitorDelegate.Stat.commit(java.lang.String, java.lang.String, com.alibaba.mtl.appmonitor.model.DimensionValueSet, double):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x002d, code lost:
            if (com.alibaba.appmonitor.sample.AMSamplingMgr.getInstance().checkSampled(com.alibaba.appmonitor.event.EventType.STAT, r11, r12, r13 != null ? r13.getMap() : null) != false) goto L_0x002f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void commit(java.lang.String r11, java.lang.String r12, com.alibaba.mtl.appmonitor.model.DimensionValueSet r13, com.alibaba.mtl.appmonitor.model.MeasureValueSet r14) {
            /*
                boolean r0 = com.alibaba.appmonitor.delegate.AppMonitorDelegate.sdkInit     // Catch:{ Throwable -> 0x00c4 }
                r1 = 3
                r2 = 2
                r3 = 1
                r4 = 0
                r5 = 4
                if (r0 == 0) goto L_0x00b0
                boolean r0 = com.alibaba.analytics.core.Variables.isNotDisAM()     // Catch:{ Throwable -> 0x00c4 }
                if (r0 == 0) goto L_0x00b0
                com.alibaba.appmonitor.event.EventType r0 = com.alibaba.appmonitor.event.EventType.STAT     // Catch:{ Throwable -> 0x00c4 }
                boolean r0 = r0.isOpen()     // Catch:{ Throwable -> 0x00c4 }
                if (r0 == 0) goto L_0x00b0
                boolean r0 = com.alibaba.appmonitor.delegate.AppMonitorDelegate.IS_DEBUG     // Catch:{ Throwable -> 0x00c4 }
                if (r0 != 0) goto L_0x002f
                com.alibaba.appmonitor.sample.AMSamplingMgr r0 = com.alibaba.appmonitor.sample.AMSamplingMgr.getInstance()     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.appmonitor.event.EventType r6 = com.alibaba.appmonitor.event.EventType.STAT     // Catch:{ Throwable -> 0x00c4 }
                if (r13 == 0) goto L_0x0028
                java.util.Map r7 = r13.getMap()     // Catch:{ Throwable -> 0x00c4 }
                goto L_0x0029
            L_0x0028:
                r7 = 0
            L_0x0029:
                boolean r0 = r0.checkSampled(r6, r11, r12, r7)     // Catch:{ Throwable -> 0x00c4 }
                if (r0 == 0) goto L_0x00b0
            L_0x002f:
                java.lang.String r0 = "statEvent commit"
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x00c4 }
                java.lang.String r6 = "module"
                r5[r4] = r6     // Catch:{ Throwable -> 0x00c4 }
                r5[r3] = r11     // Catch:{ Throwable -> 0x00c4 }
                java.lang.String r3 = "monitorPoint"
                r5[r2] = r3     // Catch:{ Throwable -> 0x00c4 }
                r5[r1] = r12     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.analytics.utils.Logger.d(r0, r5)     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.appmonitor.sample.AMSamplingMgr r0 = com.alibaba.appmonitor.sample.AMSamplingMgr.getInstance()     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.appmonitor.event.EventType r1 = com.alibaba.appmonitor.event.EventType.STAT     // Catch:{ Throwable -> 0x00c4 }
                boolean r0 = r0.isOffline(r1, r11, r12)     // Catch:{ Throwable -> 0x00c4 }
                if (r0 == 0) goto L_0x0073
                com.alibaba.analytics.core.Variables r0 = com.alibaba.analytics.core.Variables.getInstance()     // Catch:{ Throwable -> 0x00c4 }
                android.content.Context r0 = r0.getContext()     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.appmonitor.offline.TempEventMgr r1 = com.alibaba.appmonitor.offline.TempEventMgr.getInstance()     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.appmonitor.event.EventType r2 = com.alibaba.appmonitor.event.EventType.STAT     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.appmonitor.offline.TempStat r10 = new com.alibaba.appmonitor.offline.TempStat     // Catch:{ Throwable -> 0x00c4 }
                java.lang.String r8 = com.alibaba.analytics.core.network.NetworkUtil.getAccess(r0)     // Catch:{ Throwable -> 0x00c4 }
                java.lang.String r9 = com.alibaba.analytics.core.network.NetworkUtil.getAccsssSubType(r0)     // Catch:{ Throwable -> 0x00c4 }
                r3 = r10
                r4 = r11
                r5 = r12
                r6 = r13
                r7 = r14
                r3.<init>(r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x00c4 }
                r1.add(r2, r10)     // Catch:{ Throwable -> 0x00c4 }
                return
            L_0x0073:
                com.alibaba.appmonitor.event.EventRepo r0 = com.alibaba.appmonitor.event.EventRepo.getRepo()     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.appmonitor.event.EventType r1 = com.alibaba.appmonitor.event.EventType.STAT     // Catch:{ Throwable -> 0x00c4 }
                int r1 = r1.getEventId()     // Catch:{ Throwable -> 0x00c4 }
                r2 = r11
                r3 = r12
                r4 = r14
                r5 = r13
                r0.commitStatEvent(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.analytics.core.selfmonitor.SelfMonitorConfigMgr r0 = com.alibaba.analytics.core.selfmonitor.SelfMonitorConfigMgr.getInstance()     // Catch:{ Throwable -> 0x00c4 }
                boolean r0 = r0.isNeedMonitorForBucket(r11, r12)     // Catch:{ Throwable -> 0x00c4 }
                if (r0 == 0) goto L_0x00c3
                com.alibaba.appmonitor.event.EventRepo r1 = com.alibaba.appmonitor.event.EventRepo.getRepo()     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.appmonitor.event.EventType r0 = com.alibaba.appmonitor.event.EventType.STAT     // Catch:{ Throwable -> 0x00c4 }
                int r2 = r0.getEventId()     // Catch:{ Throwable -> 0x00c4 }
                java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c4 }
                r0.<init>()     // Catch:{ Throwable -> 0x00c4 }
                r0.append(r11)     // Catch:{ Throwable -> 0x00c4 }
                java.lang.String r11 = "_abtest"
                r0.append(r11)     // Catch:{ Throwable -> 0x00c4 }
                java.lang.String r3 = r0.toString()     // Catch:{ Throwable -> 0x00c4 }
                r4 = r12
                r5 = r14
                r6 = r13
                r1.commitStatEvent(r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x00c4 }
                return
            L_0x00b0:
                java.lang.String r13 = "log discard !"
                java.lang.Object[] r14 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x00c4 }
                java.lang.String r0 = "module"
                r14[r4] = r0     // Catch:{ Throwable -> 0x00c4 }
                r14[r3] = r11     // Catch:{ Throwable -> 0x00c4 }
                java.lang.String r11 = "monitorPoint"
                r14[r2] = r11     // Catch:{ Throwable -> 0x00c4 }
                r14[r1] = r12     // Catch:{ Throwable -> 0x00c4 }
                com.alibaba.analytics.utils.Logger.d(r13, r14)     // Catch:{ Throwable -> 0x00c4 }
            L_0x00c3:
                return
            L_0x00c4:
                r11 = move-exception
                com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder$ExceptionType r12 = com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder.ExceptionType.AP
                com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder.log(r12, r11)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alibaba.appmonitor.delegate.AppMonitorDelegate.Stat.commit(java.lang.String, java.lang.String, com.alibaba.mtl.appmonitor.model.DimensionValueSet, com.alibaba.mtl.appmonitor.model.MeasureValueSet):void");
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:11|12|13|14) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0023, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0024 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void init(android.app.Application r6) {
        /*
            java.lang.Class<com.alibaba.appmonitor.delegate.AppMonitorDelegate> r0 = com.alibaba.appmonitor.delegate.AppMonitorDelegate.class
            monitor-enter(r0)
            java.lang.String r1 = "AppMonitorDelegate"
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x0029 }
            r4 = 0
            java.lang.String r5 = "start init"
            r3[r4] = r5     // Catch:{ all -> 0x0029 }
            com.alibaba.analytics.utils.Logger.d(r1, r3)     // Catch:{ all -> 0x0029 }
            boolean r1 = sdkInit     // Catch:{ Throwable -> 0x0024 }
            if (r1 != 0) goto L_0x0022
            application = r6     // Catch:{ Throwable -> 0x0024 }
            com.alibaba.appmonitor.delegate.CleanTask.init()     // Catch:{ Throwable -> 0x0024 }
            com.alibaba.appmonitor.delegate.CommitTask.init()     // Catch:{ Throwable -> 0x0024 }
            com.alibaba.appmonitor.delegate.BackgroundTrigger.init(r6)     // Catch:{ Throwable -> 0x0024 }
            sdkInit = r2     // Catch:{ Throwable -> 0x0024 }
        L_0x0022:
            monitor-exit(r0)
            return
        L_0x0024:
            destroy()     // Catch:{ all -> 0x0029 }
            monitor-exit(r0)
            return
        L_0x0029:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.appmonitor.delegate.AppMonitorDelegate.init(android.app.Application):void");
    }

    public static synchronized void destroy() {
        synchronized (AppMonitorDelegate.class) {
            try {
                Logger.d((String) TAG, "start destory");
                if (sdkInit) {
                    CommitTask.uploadAllEvent();
                    CommitTask.destroy();
                    CleanTask.destroy();
                    if (application != null) {
                        NetworkUtil.unRegister(application.getApplicationContext());
                    }
                    sdkInit = false;
                }
            } catch (Throwable th) {
                ExceptionEventBuilder.log(ExceptionType.AP, th);
            }
        }
    }

    public static synchronized void triggerUpload() {
        synchronized (AppMonitorDelegate.class) {
            try {
                Logger.d((String) TAG, "triggerUpload");
                if (sdkInit && Variables.isNotDisAM()) {
                    CommitTask.uploadAllEvent();
                }
            } catch (Throwable th) {
                ExceptionEventBuilder.log(ExceptionType.AP, th);
            }
        }
    }

    public static void setStatisticsInterval(int i) {
        EventType[] values;
        for (EventType eventType : EventType.values()) {
            eventType.setStatisticsInterval(i);
            setStatisticsInterval(eventType, i);
        }
    }

    public static void setSampling(int i) {
        EventType[] values;
        Logger.d((String) TAG, "[setSampling]");
        for (EventType eventType : EventType.values()) {
            eventType.setDefaultSampling(i);
            AMSamplingMgr.getInstance().setEventTypeSampling(eventType, i);
        }
    }

    public static void enableLog(boolean z) {
        Logger.d((String) TAG, "[enableLog]");
        Logger.setDebug(z);
    }

    public static void register(String str, String str2, MeasureSet measureSet) {
        register(str, str2, measureSet, (DimensionSet) null);
    }

    public static void register(String str, String str2, MeasureSet measureSet, boolean z) {
        register(str, str2, measureSet, null, z);
    }

    public static void register(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet) {
        register(str, str2, measureSet, dimensionSet, false);
    }

    public static void register(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) {
        try {
            if (sdkInit) {
                if (!StringUtils.isBlank(str)) {
                    if (!StringUtils.isBlank(str2)) {
                        Metric metric = new Metric(str, str2, measureSet, dimensionSet, z);
                        MetricRepo.getRepo().add(metric);
                        TempEventMgr.getInstance().add(metric);
                        MeasureSet measureSet2 = SelfMonitorConfigMgr.getInstance().getMeasureSet(str, str2);
                        if (measureSet2 != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append("_abtest");
                            Metric metric2 = new Metric(sb.toString(), str2, measureSet2, dimensionSet, false);
                            MetricRepo.getRepo().add(metric2);
                        }
                    }
                }
                Logger.d((String) TAG, "register stat event. module: ", str, " monitorPoint: ", str2);
                if (IS_DEBUG) {
                    throw new AppMonitorException((String) "register error. module and monitorPoint can't be null");
                }
            }
        } catch (Throwable th) {
            ExceptionEventBuilder.log(ExceptionType.AP, th);
        }
    }

    public static void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) {
        Logger.d((String) TAG, "[updateMeasure]");
        try {
            if (sdkInit && !StringUtils.isBlank(str)) {
                if (!StringUtils.isBlank(str2)) {
                    Metric metric = MetricRepo.getRepo().getMetric(str, str2);
                    if (!(metric == null || metric.getMeasureSet() == null)) {
                        metric.getMeasureSet().upateMeasure(new Measure(str3, Double.valueOf(d3), Double.valueOf(d), Double.valueOf(d2)));
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    public static void setStatisticsInterval(EventType eventType, int i) {
        try {
            if (sdkInit && eventType != null) {
                CommitTask.setStatisticsInterval(eventType.getEventId(), i);
                if (i > 0) {
                    eventType.setOpen(true);
                    return;
                }
                eventType.setOpen(false);
            }
        } catch (Throwable th) {
            ExceptionEventBuilder.log(ExceptionType.AP, th);
        }
    }

    public static void setRequestAuthInfo(boolean z, boolean z2, String str, String str2) {
        IUTRequestAuthentication iUTRequestAuthentication;
        if (z) {
            iUTRequestAuthentication = new UTSecurityThridRequestAuthentication(str, str2);
        } else {
            iUTRequestAuthentication = new UTBaseRequestAuthentication(str, str2, z2);
        }
        Variables.getInstance().setRequestAuthenticationInstance(iUTRequestAuthentication);
    }

    public static void setChannel(String str) {
        Variables.getInstance().setChannel(str);
    }

    public static void setGlobalProperty(String str, String str2) {
        if (!StringUtils.isEmpty(str) && str2 != null) {
            mGlobalArgsMap.put(str, str2);
        }
    }

    public static void removeGlobalProperty(String str) {
        mGlobalArgsMap.remove(str);
    }

    public static Map<String, String> getGlobalArgsMap() {
        return mGlobalArgsMap;
    }
}
