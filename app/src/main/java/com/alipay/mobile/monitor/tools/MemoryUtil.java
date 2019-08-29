package com.alipay.mobile.monitor.tools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Debug.MemoryInfo;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUtil {
    private static final String TAG = "MemoryMonitor";
    private static final Map<String, MemoryInfo> sFastCacheMap = new ConcurrentHashMap();
    private static final Map<String, Long> sLastGetMemTimeMap = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static final Map<String, Map<String, MemoryInfo>> sProcessMap = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static final Map<String, String> sReportedTypes = new ConcurrentHashMap();
    private static final Map<String, SPRunnable> sSPRunnableMap = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static final Map<String, Long> sTotalMemoryMap = new ConcurrentHashMap();

    static class SPRunnable extends SafeRunnable {
        private String type;

        public SPRunnable(String str) {
            this.type = str;
        }

        public void safeRun() {
            long j;
            if (!TianyanLoggingStatus.isFrameworkBackground()) {
                TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                StringBuilder sb = new StringBuilder("updatingMemoryUsage for type but not in background anymore type:");
                sb.append(this.type);
                traceLogger.info(MemoryUtil.TAG, sb.toString());
                return;
            }
            TraceLogger traceLogger2 = LoggerFactory.getTraceLogger();
            StringBuilder sb2 = new StringBuilder("updatingMemoryUsage for type:");
            sb2.append(this.type);
            traceLogger2.info(MemoryUtil.TAG, sb2.toString());
            List<Pair> access$000 = MemoryUtil.getRunningProcesses();
            if (access$000 != null) {
                j = 0;
                for (Pair pair : access$000) {
                    MemoryInfo memoryInfo = MemoryUtil.getMemoryInfo(LoggerFactory.getLogContext().getApplicationContext(), ((Integer) pair.first).intValue(), (String) pair.second);
                    if (memoryInfo != null) {
                        j += (long) memoryInfo.getTotalPss();
                    }
                    TraceLogger traceLogger3 = LoggerFactory.getTraceLogger();
                    StringBuilder sb3 = new StringBuilder("got memoryInfo:");
                    sb3.append(memoryInfo == null ? null : String.valueOf(memoryInfo.getTotalPss()));
                    traceLogger3.info(MemoryUtil.TAG, sb3.toString());
                    SharedPreferences access$500 = MemoryUtil.getMonitorSPPrivate();
                    if (!(memoryInfo == null || access$500 == null)) {
                        String str = "";
                        if (!LoggerFactory.getLogContext().getApplicationContext().getPackageName().equals(pair.second)) {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append((String) pair.second);
                            sb4.append("_");
                            str = sb4.toString();
                        }
                        Editor edit = access$500.edit();
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(str);
                        sb5.append(this.type);
                        Editor putString = edit.putString(sb5.toString(), String.valueOf(memoryInfo.getTotalPss() / 1024));
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append(str);
                        sb6.append(this.type);
                        sb6.append("_dalvikPss");
                        Editor putString2 = putString.putString(sb6.toString(), String.valueOf(memoryInfo.dalvikPss / 1024));
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(str);
                        sb7.append(this.type);
                        sb7.append("_nativePss");
                        Editor putString3 = putString2.putString(sb7.toString(), String.valueOf(memoryInfo.nativePss / 1024));
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(str);
                        sb8.append(this.type);
                        sb8.append("_otherPss");
                        putString3.putString(sb8.toString(), String.valueOf(memoryInfo.otherPss / 1024)).apply();
                    }
                    MemoryUtil.updateMax((String) pair.second, memoryInfo);
                }
            } else {
                j = 0;
            }
            if (j > 0) {
                LoggerFactory.getTraceLogger().info(MemoryUtil.TAG, "got totalMemory:".concat(String.valueOf(j)));
                SharedPreferences access$5002 = MemoryUtil.getMonitorSPPrivate();
                if (access$5002 != null) {
                    Editor edit2 = access$5002.edit();
                    StringBuilder sb9 = new StringBuilder("total_");
                    sb9.append(this.type);
                    edit2.putString(sb9.toString(), String.valueOf(j / 1024)).apply();
                }
                MemoryUtil.updateTotalMax(j);
            }
        }
    }

    public static void updateMemoryUsage(final String str) {
        if (TextUtils.isEmpty(str)) {
            LoggerFactory.getTraceLogger().info(TAG, "type is empty");
            return;
        }
        LoggerFactory.getTraceLogger().info(TAG, "updateMemoryUsage for type:".concat(String.valueOf(str)));
        if (str.equalsIgnoreCase(LogContext.ENVENT_CLIENTLAUNCH) || str.equalsIgnoreCase(Subscribe.THREAD_BACKGROUND)) {
            long j = 0;
            if (LogContext.ENVENT_CLIENTLAUNCH.equals(str)) {
                if (sReportedTypes.containsKey(str)) {
                    LoggerFactory.getTraceLogger().info(TAG, "updateMemoryUsage but already reported, do not record again, type:".concat(String.valueOf(str)));
                    return;
                }
                j = 1000;
            }
            HandlerThreadFactory.getTimerThreadHandler().postDelayed(new SafeRunnable() {
                public final void safeRun() {
                    long j;
                    TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                    StringBuilder sb = new StringBuilder("updatingMemoryUsage for type:");
                    sb.append(str);
                    traceLogger.info(MemoryUtil.TAG, sb.toString());
                    List<Pair> access$000 = MemoryUtil.getRunningProcesses();
                    if (access$000 != null) {
                        j = 0;
                        for (Pair pair : access$000) {
                            Map map = (Map) MemoryUtil.sProcessMap.get(pair.second);
                            if (map == null) {
                                map = new ConcurrentHashMap();
                                MemoryUtil.sProcessMap.put(pair.second, map);
                            }
                            if (((MemoryInfo) map.get(str)) == null) {
                                MemoryInfo memoryInfo = MemoryUtil.getMemoryInfo(LoggerFactory.getLogContext().getApplicationContext(), ((Integer) pair.first).intValue(), (String) pair.second);
                                if (memoryInfo != null) {
                                    j += (long) memoryInfo.getTotalPss();
                                }
                                TraceLogger traceLogger2 = LoggerFactory.getTraceLogger();
                                StringBuilder sb2 = new StringBuilder("got memoryInfo:");
                                sb2.append(memoryInfo == null ? null : String.valueOf(memoryInfo.getTotalPss()));
                                traceLogger2.info(MemoryUtil.TAG, sb2.toString());
                                if (memoryInfo != null) {
                                    map.put(str, memoryInfo);
                                }
                                MemoryUtil.updateMax((String) pair.second, memoryInfo);
                            } else {
                                TraceLogger traceLogger3 = LoggerFactory.getTraceLogger();
                                StringBuilder sb3 = new StringBuilder("updatingMemoryUsage but already recorded for type:");
                                sb3.append(str);
                                traceLogger3.info(MemoryUtil.TAG, sb3.toString());
                            }
                        }
                    } else {
                        j = 0;
                    }
                    if (j > 0) {
                        Long l = (Long) MemoryUtil.sTotalMemoryMap.get(str);
                        if (l == null || l.longValue() <= 0) {
                            MemoryUtil.sTotalMemoryMap.put(str, Long.valueOf(j));
                            TraceLogger traceLogger4 = LoggerFactory.getTraceLogger();
                            StringBuilder sb4 = new StringBuilder("record totalMemory for type:");
                            sb4.append(str);
                            sb4.append(" size:");
                            sb4.append(j);
                            traceLogger4.info(MemoryUtil.TAG, sb4.toString());
                            MemoryUtil.updateTotalMax(j);
                        }
                    }
                }
            }, j);
        } else if (str.equalsIgnoreCase("max")) {
            HandlerThreadFactory.getTimerThreadHandler().post(new SafeRunnable() {
                public final void safeRun() {
                    long j;
                    TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                    StringBuilder sb = new StringBuilder("updatingMemoryUsage for type:");
                    sb.append(str);
                    traceLogger.info(MemoryUtil.TAG, sb.toString());
                    List<Pair> access$000 = MemoryUtil.getRunningProcesses();
                    if (access$000 != null) {
                        j = 0;
                        for (Pair pair : access$000) {
                            MemoryInfo memoryInfo = MemoryUtil.getMemoryInfo(LoggerFactory.getLogContext().getApplicationContext(), ((Integer) pair.first).intValue(), (String) pair.second);
                            if (memoryInfo != null) {
                                j += (long) memoryInfo.getTotalPss();
                            }
                            TraceLogger traceLogger2 = LoggerFactory.getTraceLogger();
                            StringBuilder sb2 = new StringBuilder("got memoryInfo:");
                            sb2.append(memoryInfo == null ? null : String.valueOf(memoryInfo.getTotalPss()));
                            traceLogger2.info(MemoryUtil.TAG, sb2.toString());
                            MemoryUtil.updateMax((String) pair.second, memoryInfo);
                        }
                    } else {
                        j = 0;
                    }
                    if (j > 0) {
                        MemoryUtil.updateTotalMax(j);
                    }
                }
            });
        } else {
            if (str.equalsIgnoreCase("background1") || str.equalsIgnoreCase("background2") || str.equalsIgnoreCase("background3") || str.equalsIgnoreCase("background9")) {
                SPRunnable sPRunnable = sSPRunnableMap.get(str);
                if (sPRunnable == null) {
                    sPRunnable = new SPRunnable(str);
                    sSPRunnableMap.put(str, sPRunnable);
                }
                HandlerThreadFactory.getTimerThreadHandler().postDelayed(sPRunnable, 15000);
            }
        }
    }

    public static void removePendingUpdates() {
        for (Entry<String, SPRunnable> value : sSPRunnableMap.entrySet()) {
            SPRunnable sPRunnable = (SPRunnable) value.getValue();
            if (sPRunnable != null) {
                HandlerThreadFactory.getTimerThreadHandler().removeCallbacks(sPRunnable);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void updateTotalMax(long j) {
        LoggerFactory.getTraceLogger().info(TAG, "updateMax for totalMemory.");
        Long l = sTotalMemoryMap.get("max");
        if (l == null || j > l.longValue()) {
            sTotalMemoryMap.put("max", Long.valueOf(j));
            LoggerFactory.getTraceLogger().info(TAG, "new max memoryInfo:".concat(String.valueOf(j)));
        }
    }

    /* access modifiers changed from: private */
    public static void updateMax(String str, MemoryInfo memoryInfo) {
        if (str == null) {
            str = LoggerFactory.getLogContext().getApplicationContext().getPackageName();
        }
        LoggerFactory.getTraceLogger().info(TAG, "updateMax for process:".concat(String.valueOf(str)));
        Map map = sProcessMap.get(str);
        if (map == null) {
            map = new ConcurrentHashMap();
            sProcessMap.put(str, map);
        }
        MemoryInfo memoryInfo2 = (MemoryInfo) map.get("max");
        if (memoryInfo == null || memoryInfo2 == null || memoryInfo.getTotalPss() >= memoryInfo2.getTotalPss()) {
            memoryInfo2 = memoryInfo;
        }
        if (memoryInfo2 != null) {
            map.put("max", memoryInfo2);
        }
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("new max memoryInfo:");
        sb.append(memoryInfo2 == null ? null : String.valueOf(memoryInfo2.getTotalPss()));
        traceLogger.info(TAG, sb.toString());
    }

    public static void reportMemoryUsages(final List<String> list) {
        if (list == null || list.size() <= 0) {
            LoggerFactory.getTraceLogger().info(TAG, "types is empty");
            return;
        }
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("reportMemoryUsages for types:");
        sb.append(list.size());
        traceLogger.info(TAG, sb.toString());
        HandlerThreadFactory.getTimerThreadHandler().post(new SafeRunnable() {
            public final void safeRun() {
                Editor editor;
                SharedPreferences access$500 = MemoryUtil.getMonitorSPPrivate();
                if (access$500 == null) {
                    editor = null;
                } else {
                    editor = access$500.edit();
                }
                for (Entry entry : MemoryUtil.sProcessMap.entrySet()) {
                    TraceLogger traceLogger = LoggerFactory.getTraceLogger();
                    StringBuilder sb = new StringBuilder("reportMemoryUsages for process:");
                    sb.append((String) entry.getKey());
                    traceLogger.info(MemoryUtil.TAG, sb.toString());
                    Performance performance = new Performance();
                    performance.setSubType(MemoryUtil.TAG);
                    performance.setParam1("occasion");
                    performance.setParam2((String) entry.getKey());
                    String str = "";
                    if (!LoggerFactory.getLogContext().getApplicationContext().getPackageName().equals(entry.getKey())) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append((String) entry.getKey());
                        sb2.append("_");
                        str = sb2.toString();
                    }
                    Map map = (Map) entry.getValue();
                    for (String str2 : list) {
                        LoggerFactory.getTraceLogger().info(MemoryUtil.TAG, "reportMemoryUsages for type:".concat(String.valueOf(str2)));
                        MemoryInfo memoryInfo = (MemoryInfo) map.get(str2);
                        if (memoryInfo != null) {
                            MemoryUtil.sReportedTypes.put(str2, str2);
                            performance.addExtParam(str2, String.valueOf(memoryInfo.getTotalPss() / 1024));
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str2);
                            sb3.append("_dalvikPss");
                            performance.addExtParam(sb3.toString(), String.valueOf(memoryInfo.dalvikPss / 1024));
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str2);
                            sb4.append("_nativePss");
                            performance.addExtParam(sb4.toString(), String.valueOf(memoryInfo.nativePss / 1024));
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str2);
                            sb5.append("_otherPss");
                            performance.addExtParam(sb5.toString(), String.valueOf(memoryInfo.otherPss / 1024));
                            map.remove(str2);
                        } else if (access$500 != null) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str);
                            sb6.append(str2);
                            if (access$500.contains(sb6.toString())) {
                                MemoryUtil.sReportedTypes.put(str2, str2);
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append(str);
                                sb7.append(str2);
                                performance.addExtParam(str2, access$500.getString(sb7.toString(), "-1"));
                            }
                            StringBuilder sb8 = new StringBuilder();
                            sb8.append(str);
                            sb8.append(str2);
                            sb8.append("_dalvikPss");
                            if (access$500.contains(sb8.toString())) {
                                StringBuilder sb9 = new StringBuilder();
                                sb9.append(str2);
                                sb9.append("_dalvikPss");
                                String sb10 = sb9.toString();
                                StringBuilder sb11 = new StringBuilder();
                                sb11.append(str);
                                sb11.append(str2);
                                sb11.append("_dalvikPss");
                                performance.addExtParam(sb10, access$500.getString(sb11.toString(), "-1"));
                            }
                            StringBuilder sb12 = new StringBuilder();
                            sb12.append(str);
                            sb12.append(str2);
                            sb12.append("_nativePss");
                            if (access$500.contains(sb12.toString())) {
                                StringBuilder sb13 = new StringBuilder();
                                sb13.append(str2);
                                sb13.append("_nativePss");
                                String sb14 = sb13.toString();
                                StringBuilder sb15 = new StringBuilder();
                                sb15.append(str);
                                sb15.append(str2);
                                sb15.append("_nativePss");
                                performance.addExtParam(sb14, access$500.getString(sb15.toString(), "-1"));
                            }
                            StringBuilder sb16 = new StringBuilder();
                            sb16.append(str);
                            sb16.append(str2);
                            sb16.append("_otherPss");
                            if (access$500.contains(sb16.toString())) {
                                StringBuilder sb17 = new StringBuilder();
                                sb17.append(str2);
                                sb17.append("_otherPss");
                                String sb18 = sb17.toString();
                                StringBuilder sb19 = new StringBuilder();
                                sb19.append(str);
                                sb19.append(str2);
                                sb19.append("_otherPss");
                                performance.addExtParam(sb18, access$500.getString(sb19.toString(), "-1"));
                            }
                        }
                        if (!(access$500 == null || editor == null)) {
                            StringBuilder sb20 = new StringBuilder();
                            sb20.append(str);
                            sb20.append(str2);
                            if (access$500.contains(sb20.toString())) {
                                StringBuilder sb21 = new StringBuilder();
                                sb21.append(str);
                                sb21.append(str2);
                                editor.remove(sb21.toString());
                            }
                            StringBuilder sb22 = new StringBuilder();
                            sb22.append(str);
                            sb22.append(str2);
                            sb22.append("_dalvikPss");
                            if (access$500.contains(sb22.toString())) {
                                StringBuilder sb23 = new StringBuilder();
                                sb23.append(str);
                                sb23.append(str2);
                                sb23.append("_dalvikPss");
                                editor.remove(sb23.toString());
                            }
                            StringBuilder sb24 = new StringBuilder();
                            sb24.append(str);
                            sb24.append(str2);
                            sb24.append("_nativePss");
                            if (access$500.contains(sb24.toString())) {
                                StringBuilder sb25 = new StringBuilder();
                                sb25.append(str);
                                sb25.append(str2);
                                sb25.append("_nativePss");
                                editor.remove(sb25.toString());
                            }
                            StringBuilder sb26 = new StringBuilder();
                            sb26.append(str);
                            sb26.append(str2);
                            sb26.append("_otherPss");
                            if (access$500.contains(sb26.toString())) {
                                StringBuilder sb27 = new StringBuilder();
                                sb27.append(str);
                                sb27.append(str2);
                                sb27.append("_otherPss");
                                editor.remove(sb27.toString());
                            }
                        }
                    }
                    if (performance.getExtPramas().size() > 0) {
                        LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_PERFORMANCE, performance);
                        LoggerFactory.getLogContext().flush(LogCategory.CATEGORY_PERFORMANCE, false);
                        LoggerFactory.getLogContext().uploadAfterSync(LogCategory.CATEGORY_PERFORMANCE);
                        TraceLogger traceLogger2 = LoggerFactory.getTraceLogger();
                        StringBuilder sb28 = new StringBuilder("reportMemoryUsages for type end:");
                        sb28.append(list.size());
                        traceLogger2.info(MemoryUtil.TAG, sb28.toString());
                    } else {
                        LoggerFactory.getTraceLogger().info(MemoryUtil.TAG, "reportMemoryUsages but nothing to report");
                    }
                }
                LoggerFactory.getTraceLogger().info(MemoryUtil.TAG, "reportMemoryUsages for totalMemory");
                Performance performance2 = new Performance();
                performance2.setSubType(MemoryUtil.TAG);
                performance2.setParam1("occasion");
                performance2.setParam2("total");
                for (String str3 : list) {
                    LoggerFactory.getTraceLogger().info(MemoryUtil.TAG, "reportMemoryUsages for type:".concat(String.valueOf(str3)));
                    Long l = (Long) MemoryUtil.sTotalMemoryMap.get(str3);
                    if (l != null) {
                        MemoryUtil.sReportedTypes.put(str3, str3);
                        performance2.addExtParam(str3, String.valueOf(l.longValue() / 1024));
                        MemoryUtil.sTotalMemoryMap.remove(str3);
                    } else if (access$500 != null) {
                        StringBuilder sb29 = new StringBuilder();
                        sb29.append("total_");
                        sb29.append(str3);
                        if (access$500.contains(sb29.toString())) {
                            MemoryUtil.sReportedTypes.put(str3, str3);
                            StringBuilder sb30 = new StringBuilder();
                            sb30.append("total_");
                            sb30.append(str3);
                            performance2.addExtParam(str3, access$500.getString(sb30.toString(), "-1"));
                        }
                    }
                    if (!(access$500 == null || editor == null)) {
                        StringBuilder sb31 = new StringBuilder();
                        sb31.append("total_");
                        sb31.append(str3);
                        if (access$500.contains(sb31.toString())) {
                            StringBuilder sb32 = new StringBuilder();
                            sb32.append("total_");
                            sb32.append(str3);
                            editor.remove(sb32.toString());
                        }
                    }
                }
                if (performance2.getExtPramas().size() > 0) {
                    LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_PERFORMANCE, performance2);
                    LoggerFactory.getLogContext().flush(LogCategory.CATEGORY_PERFORMANCE, false);
                    LoggerFactory.getLogContext().uploadAfterSync(LogCategory.CATEGORY_PERFORMANCE);
                    LoggerFactory.getTraceLogger().info(MemoryUtil.TAG, "reportMemoryUsages for totalMemory end");
                } else {
                    LoggerFactory.getTraceLogger().info(MemoryUtil.TAG, "reportMemoryUsages but nothing to report");
                }
                if (editor != null) {
                    editor.apply();
                }
            }
        });
    }

    public static synchronized MemoryInfo getMemoryInfo(Context context, int i, String str) {
        synchronized (MemoryUtil.class) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (str == null) {
                str = context.getPackageName();
            }
            MemoryInfo memoryInfo = sFastCacheMap.get(str);
            Long l = sLastGetMemTimeMap.get(str);
            if (memoryInfo != null && l != null && elapsedRealtime - l.longValue() < BalloonLayout.DEFAULT_DISPLAY_DURATION) {
                return memoryInfo;
            }
            try {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
                int[] iArr = new int[1];
                if (i < 0) {
                    i = Process.myPid();
                }
                iArr[0] = i;
                MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(iArr);
                if (processMemoryInfo.length > 0) {
                    MemoryInfo memoryInfo2 = processMemoryInfo[0];
                    sFastCacheMap.put(str, memoryInfo2);
                    sLastGetMemTimeMap.put(str, Long.valueOf(elapsedRealtime));
                    return memoryInfo2;
                }
                sFastCacheMap.remove(str);
                sLastGetMemTimeMap.put(str, Long.valueOf(elapsedRealtime));
                return null;
            } catch (Throwable th) {
                LoggerFactory.getTraceLogger().warn((String) TAG, th);
                sFastCacheMap.remove(str);
                sLastGetMemTimeMap.put(str, Long.valueOf(elapsedRealtime));
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    public static List<Pair<Integer, String>> getRunningProcesses() {
        try {
            ArrayList arrayList = new ArrayList();
            int i = LoggerFactory.getLogContext().getApplicationContext().getApplicationInfo().uid;
            for (RunningAppProcessInfo next : ((ActivityManager) LoggerFactory.getLogContext().getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (next != null && next.uid == i) {
                    arrayList.add(Pair.create(Integer.valueOf(next.pid), next.processName));
                }
            }
            return arrayList;
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "collectCpuTime", th);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static SharedPreferences getMonitorSPPrivate() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("MonitorPrivate_");
            sb.append(LoggerFactory.getProcessInfo().getProcessAlias());
            return LoggerFactory.getLogContext().getApplicationContext().getSharedPreferences(sb.toString(), 0);
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().warn((String) TAG, th);
            return null;
        }
    }
}
