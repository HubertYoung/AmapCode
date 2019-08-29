package com.alibaba.mtl.appmonitor;

import android.app.Application;
import android.os.RemoteException;
import android.text.TextUtils;
import com.alibaba.analytics.AnalyticsMgr;
import com.alibaba.analytics.AnalyticsMgr.Entity;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.offline.TempEvent;
import com.alibaba.mtl.appmonitor.model.DimensionSet;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.appmonitor.model.MeasureSet;
import com.alibaba.mtl.appmonitor.model.MeasureValueSet;
import java.util.Map;

public final class AppMonitor {
    private static final String TAG = "AppMonitor";

    public static class Alarm {
        public static void setStatisticsInterval(final int i) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.alarm_setStatisticsInterval(i);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void setSampling(final int i) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.alarm_setSampling(i);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            boolean z;
            if (AnalyticsMgr.iAnalytics == null) {
                return false;
            }
            try {
                z = AnalyticsMgr.iAnalytics.alarm_checkSampled(str, str2);
            } catch (RemoteException unused) {
                z = false;
            }
            return z;
        }

        public static void commitSuccess(final String str, final String str2) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.alarm_commitSuccess1(str, str2);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void commitSuccess(final String str, final String str2, final String str3) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.alarm_commitSuccess2(str, str2, str3);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void commitFail(final String str, final String str2, final String str3, final String str4) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.alarm_commitFail1(str, str2, str3, str4);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void commitFail(String str, String str2, String str3, String str4, String str5) {
            if (AppMonitor.checkInit()) {
                final String str6 = str;
                final String str7 = str2;
                final String str8 = str3;
                final String str9 = str4;
                final String str10 = str5;
                AnonymousClass6 r1 = new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.alarm_commitFail2(str6, str7, str8, str9, str10);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                };
                AnalyticsMgr.handler.postWatingTask(r1);
            }
        }
    }

    public static class Counter {
        public static void setStatisticsInterval(final int i) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.counter_setStatisticsInterval(i);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void setSampling(final int i) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.counter_setSampling(i);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        @Deprecated
        public static boolean checkSampled(String str, String str2) {
            boolean z;
            if (AnalyticsMgr.iAnalytics == null) {
                return false;
            }
            try {
                z = AnalyticsMgr.iAnalytics.counter_checkSampled(str, str2);
            } catch (RemoteException unused) {
                z = false;
            }
            return z;
        }

        public static void commit(final String str, final String str2, final double d) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.counter_commit1(str, str2, d);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void commit(String str, String str2, String str3, double d) {
            if (AppMonitor.checkInit()) {
                final String str4 = str;
                final String str5 = str2;
                final String str6 = str3;
                final double d2 = d;
                AnonymousClass4 r1 = new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.counter_commit2(str4, str5, str6, d2);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                };
                AnalyticsMgr.handler.postWatingTask(r1);
            }
        }
    }

    @Deprecated
    public static class OffLineCounter {
        public static boolean checkSampled(String str, String str2) {
            return false;
        }

        public static void setSampling(int i) {
        }

        public static void setStatisticsInterval(int i) {
        }

        public static void commit(String str, String str2, double d) {
            Counter.commit(str, str2, d);
        }
    }

    public static class Stat {
        public static void setStatisticsInterval(final int i) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.stat_setStatisticsInterval(i);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void setSampling(final int i) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.stat_setSampling(i);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static boolean checkSampled(String str, String str2) {
            boolean z;
            if (AnalyticsMgr.iAnalytics == null) {
                return false;
            }
            try {
                z = AnalyticsMgr.iAnalytics.stat_checkSampled(str, str2);
            } catch (RemoteException unused) {
                z = false;
            }
            return z;
        }

        public static void begin(final String str, final String str2, final String str3) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.stat_begin(str, str2, str3);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void end(final String str, final String str2, final String str3) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.stat_end(str, str2, str3);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
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

        public static void commit(String str, String str2, DimensionValueSet dimensionValueSet, double d) {
            if (AppMonitor.checkInit()) {
                final String str3 = str;
                final String str4 = str2;
                final DimensionValueSet dimensionValueSet2 = dimensionValueSet;
                final double d2 = d;
                AnonymousClass5 r1 = new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.stat_commit2(str3, str4, dimensionValueSet2, d2);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                };
                AnalyticsMgr.handler.postWatingTask(r1);
            }
        }

        public static void commit(final String str, final String str2, final DimensionValueSet dimensionValueSet, final MeasureValueSet measureValueSet) {
            if (AppMonitor.checkInit()) {
                AnalyticsMgr.handler.postWatingTask(new Runnable() {
                    public final void run() {
                        try {
                            AnalyticsMgr.iAnalytics.stat_commit3(str, str2, dimensionValueSet, measureValueSet);
                        } catch (RemoteException e) {
                            AnalyticsMgr.handleRemoteException(e);
                        }
                    }
                });
            }
        }

        public static void commit(String str, String str2, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4) {
            DimensionValueSet dimensionValueSet;
            Logger.d((String) "[commit from jni]", new Object[0]);
            MeasureValueSet measureValueSet = null;
            if (strArr == null || strArr2 == null || strArr.length != strArr2.length) {
                dimensionValueSet = null;
            } else {
                dimensionValueSet = DimensionValueSet.create();
                for (int i = 0; i < strArr2.length; i++) {
                    dimensionValueSet.setValue(strArr[i], strArr2[i]);
                }
            }
            if (strArr3 == null || strArr4 == null || strArr3.length != strArr4.length) {
                Logger.d((String) "measure is null ,or lenght not match", new Object[0]);
            } else {
                measureValueSet = MeasureValueSet.create();
                for (int i2 = 0; i2 < strArr4.length; i2++) {
                    double d = 0.0d;
                    if (!TextUtils.isEmpty(strArr4[i2])) {
                        try {
                            d = Double.valueOf(strArr4[i2]).doubleValue();
                        } catch (Exception unused) {
                            StringBuilder sb = new StringBuilder("measure's value cannot convert to double. measurevalue:");
                            sb.append(strArr4[i2]);
                            Logger.d(sb.toString(), new Object[0]);
                        }
                    }
                    measureValueSet.setValue(strArr3[i2], d);
                }
            }
            commit(str, str2, dimensionValueSet, measureValueSet);
        }
    }

    @Deprecated
    public static synchronized void init(Application application) {
        synchronized (AppMonitor.class) {
            AnalyticsMgr.init(application);
        }
    }

    @Deprecated
    public static void setRequestAuthInfo(boolean z, String str, String str2) {
        throw new RuntimeException("this interface is deprecated after sdk version 6.3.0，please call Analytics.getInstance().setAppApplicationInstance(Application application,IUTApplication utcallback) ");
    }

    @Deprecated
    public static void setChannel(String str) {
        AnalyticsMgr.setChanel(str);
    }

    @Deprecated
    public static void turnOnRealTimeDebug(Map<String, String> map) {
        AnalyticsMgr.turnOnRealTimeDebug(map);
    }

    @Deprecated
    public static void turnOffRealTimeDebug() {
        AnalyticsMgr.turnOffRealTimeDebug();
    }

    @Deprecated
    public static synchronized void destroy() {
        synchronized (AppMonitor.class) {
        }
    }

    @Deprecated
    public static synchronized void triggerUpload() {
        synchronized (AppMonitor.class) {
        }
    }

    public static void setStatisticsInterval(final int i) {
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public final void run() {
                    try {
                        AnalyticsMgr.iAnalytics.setStatisticsInterval1(i);
                    } catch (RemoteException e) {
                        AnalyticsMgr.handleRemoteException(e);
                    }
                }
            });
        }
    }

    public static void setSampling(final int i) {
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public final void run() {
                    try {
                        AnalyticsMgr.iAnalytics.setSampling(i);
                    } catch (RemoteException e) {
                        AnalyticsMgr.handleRemoteException(e);
                    }
                }
            });
        }
    }

    public static void enableLog(final boolean z) {
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public final void run() {
                    try {
                        AnalyticsMgr.iAnalytics.enableLog(z);
                    } catch (RemoteException e) {
                        AnalyticsMgr.handleRemoteException(e);
                    }
                }
            });
        }
    }

    public static void register(final String str, final String str2, final MeasureSet measureSet) {
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public final void run() {
                    try {
                        AnalyticsMgr.iAnalytics.register1(str, str2, measureSet);
                    } catch (RemoteException e) {
                        AnalyticsMgr.handleRemoteException(e);
                    }
                }
            });
            addRegisterEntity(str, str2, measureSet, null, false);
        }
    }

    public static void register(final String str, final String str2, final MeasureSet measureSet, final boolean z) {
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public final void run() {
                    try {
                        AnalyticsMgr.iAnalytics.register2(str, str2, measureSet, z);
                    } catch (RemoteException e) {
                        AnalyticsMgr.handleRemoteException(e);
                    }
                }
            });
            addRegisterEntity(str, str2, measureSet, null, z);
        }
    }

    public static void register(final String str, final String str2, final MeasureSet measureSet, final DimensionSet dimensionSet) {
        Logger.d((String) "外注册任务被业务方调用", TempEvent.TAG_MODULE, str, "monitorPoint", str2);
        if (checkInit()) {
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public final void run() {
                    try {
                        Logger.d((String) "外注册任务开始执行", TempEvent.TAG_MODULE, str, "monitorPoint", str2);
                        AnalyticsMgr.iAnalytics.register3(str, str2, measureSet, dimensionSet);
                    } catch (RemoteException e) {
                        AnalyticsMgr.handleRemoteException(e);
                    }
                }
            });
            addRegisterEntity(str, str2, measureSet, dimensionSet, false);
        }
    }

    public static void register(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) {
        if (checkInit()) {
            registerInternal(str, str2, measureSet, dimensionSet, z, false);
        }
    }

    private static void registerInternal(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z, boolean z2) {
        if (checkInit()) {
            Logger.d((String) "AppMonitor", "[registerInternal] : module:", str, "monitorPoint:", str2, "measures:", measureSet, "dimensions:", dimensionSet, "isCommitDetail:", Boolean.valueOf(z), "isInternal:", Boolean.valueOf(z2));
            if (!z2) {
                addRegisterEntity(str, str2, measureSet, dimensionSet, z);
            }
            AnalyticsMgr.handler.postWatingTask(createRegisterTask(str, str2, measureSet, dimensionSet, z));
        }
    }

    private static void addRegisterEntity(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) {
        try {
            Entity entity = new Entity();
            entity.module = str;
            entity.monitorPoint = str2;
            entity.measureSet = measureSet;
            entity.dimensionSet = dimensionSet;
            entity.isCommitDetail = z;
            AnalyticsMgr.mRegisterList.add(entity);
        } catch (Throwable unused) {
        }
    }

    public static void register(String str, String str2, String[] strArr, String[] strArr2, boolean z) {
        Logger.d((String) "AppMonitor", "[c interface] module", str, "monitorPoint", str2);
        if (strArr != null) {
            MeasureSet create = MeasureSet.create();
            for (String addMeasure : strArr) {
                create.addMeasure(addMeasure);
            }
            DimensionSet dimensionSet = null;
            if (strArr2 != null) {
                dimensionSet = DimensionSet.create();
                for (String addDimension : strArr2) {
                    dimensionSet.addDimension(addDimension);
                }
            }
            register(str, str2, create, dimensionSet, z);
            return;
        }
        Logger.d((String) "AppMonitor", "register failed:no measure");
    }

    public static void updateMeasure(String str, String str2, String str3, double d, double d2, double d3) {
        Logger.d((String) "AppMonitor", "[updateMeasure]");
        if (checkInit()) {
            final String str4 = str;
            final String str5 = str2;
            final String str6 = str3;
            final double d4 = d;
            final double d5 = d2;
            final double d6 = d3;
            AnonymousClass7 r1 = new Runnable() {
                public final void run() {
                    try {
                        AnalyticsMgr.iAnalytics.updateMeasure(str4, str5, str6, d4, d5, d6);
                    } catch (RemoteException e) {
                        AnalyticsMgr.handleRemoteException(e);
                    }
                }
            };
            AnalyticsMgr.handler.post(r1);
        }
    }

    public static void setStatisticsInterval(EventType eventType, final int i) {
        if (checkInit()) {
            final int event = getEvent(eventType);
            AnalyticsMgr.handler.postWatingTask(new Runnable() {
                public final void run() {
                    try {
                        AnalyticsMgr.iAnalytics.setStatisticsInterval2(event, i);
                    } catch (RemoteException e) {
                        AnalyticsMgr.handleRemoteException(e);
                    }
                }
            });
        }
    }

    public static void setGlobalProperty(String str, String str2) {
        AnalyticsMgr.setGlobalProperty(str, str2);
    }

    public static void removeGlobalProperty(String str) {
        AnalyticsMgr.removeGlobalProperty(str);
    }

    public static String getGlobalProperty(String str) {
        return AnalyticsMgr.getGlobalProperty(str);
    }

    private static int getEvent(EventType eventType) {
        return eventType.getEventId();
    }

    /* access modifiers changed from: private */
    public static boolean checkInit() {
        if (!AnalyticsMgr.isInit) {
            Logger.d((String) "AppMonitor", "Please call init() before call other method");
        }
        return AnalyticsMgr.isInit;
    }

    private static Runnable createRegisterTask(String str, String str2, MeasureSet measureSet, DimensionSet dimensionSet, boolean z) {
        final String str3 = str;
        final String str4 = str2;
        final MeasureSet measureSet2 = measureSet;
        final DimensionSet dimensionSet2 = dimensionSet;
        final boolean z2 = z;
        AnonymousClass9 r0 = new Runnable() {
            public final void run() {
                try {
                    Logger.d((String) "AppMonitor", "register stat event. module: ", str3, " monitorPoint: ", str4);
                    AnalyticsMgr.iAnalytics.register4(str3, str4, measureSet2, dimensionSet2, z2);
                } catch (RemoteException e) {
                    AnalyticsMgr.handleRemoteException(e);
                }
            }
        };
        return r0;
    }
}
