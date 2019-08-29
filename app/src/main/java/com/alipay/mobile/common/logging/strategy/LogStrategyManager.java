package com.alipay.mobile.common.logging.strategy;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.ContextInfo;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogEvent.Level;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.common.logging.util.NetUtil;
import com.alipay.mobile.common.logging.util.network.NetWorkProvider;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.ajx3.util.Constants;
import com.taobao.accs.utl.UtilityImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

public class LogStrategyManager {
    public static final String ACTION_TYPE_BOOT = "boot";
    public static final String ACTION_TYPE_FEEDBACK = "feedback";
    public static final String ACTION_TYPE_LEAVEHINT = "leavehint";
    public static final String ACTION_TYPE_LOGIN = "login";
    public static final String ACTION_TYPE_MDAPUPLOAD = "mdapupload";
    public static final String ACTION_TYPE_TIMEOUT = "timeout";
    private static long CURRENT_REQUEST_TIME_SPAN = DEFAULT_REQUEST_TIME_SPAN;
    private static final long DEFAULT_REQUEST_TIME_SPAN = TimeUnit.MINUTES.toMillis(30);
    private static final int DISABLE_TOOLS_PROCESS_NO = 1;
    private static final int DISABLE_TOOLS_PROCESS_YES = 2;
    private static final int ENABLE_NOLOCK_LOG_NO = 2;
    private static final int ENABLE_NOLOCK_LOG_YES = 1;
    private static final int ENABLE_TRAFFIC_LIMIT_NO = 2;
    private static final int ENABLE_TRAFFIC_LIMIT_YES = 1;
    private static LogStrategyManager INSTANCE = null;
    private static final String JSON_DATA_CONTENT = "content";
    private static final String JSON_DATA_DIAGNOSE = "diagnose";
    private static final String KEY_BACKGROUND_TIMESTAMP = "backgroundTimestamp";
    private static final String KEY_CURRENT_REQUEST_TIMESPAN = "CurrentRequestTimeSpan";
    private static final String KEY_CUR_CRASH_HOUR = "curCrashHour";
    private static final String KEY_CUR_CRASH_HOUR_COUNT = "curCrashHourCount";
    private static final String KEY_CUR_CRASH_MINUTE = "curCrashMinute";
    private static final String KEY_CUR_CRASH_MINUTE_COUNT = "curCrashMinuteCount";
    private static final String KEY_CUR_KEYBIZ_DAY = "curKeyBizDay";
    private static final String KEY_CUR_KEYBIZ_DAY_COUNT = "curKeyBizDayCount";
    private static final String KEY_DISABLE_TOOLS_PROCESS = "DisableToolsProcess";
    private static final String KEY_ENABLE_NOLOCK_LOG = "Disable_NoLock_Log";
    private static final String KEY_ENABLE_TRAFFIC_LIMIT = "EnableTrafficLimit";
    private static final String KEY_POSITIVE_DIAGNOSE = "PositiveDiagnose";
    private static final String KEY_PREVIOUS_REQUEST_TIME = "PreviousRequestTime";
    private static final String KEY_STRATEG_CONFIG_CONTENT = "StrategConfigContent2nd";
    private static final String KEY_ZIP_AND_SEVENZIP = "ZipAndSevenZip";
    private static final long MAXIMAL_REQUEST_TIME_SPAN = TimeUnit.HOURS.toMillis(1);
    private static final int MAX_CRASH_HOUR_COUNT = 50;
    private static final int MAX_CRASH_MINUTE_COUNT = 2;
    private static final int MAX_KEYBIZ_DAY_COUNT = 200;
    public static final long MINIMUM_REQUEST_TIME_SPAN = TimeUnit.MINUTES.toMillis(3);
    private static final int POSITIVE_DIAGNOSE_ALL = 3;
    private static final int POSITIVE_DIAGNOSE_NO = 1;
    private static final int POSITIVE_DIAGNOSE_WIFI = 2;
    private static final long REQUEST_TWICE_SPAN = TimeUnit.SECONDS.toMillis(5);
    private static final String REQUEST_URL_SUFFIX = "/loggw/logConfig.do";
    private static final String SP_NAME_CRASHCOUNT_INFO = "CrashCountInfo";
    private static final String SP_NAME_KEYBIZ_INFO = "KeyBizInfo";
    private static final String SP_NAME_LOGSTRATEGY_CONFIG = "LogStrategyConfig";
    public static final String SP_STRATEGY_KEY_NETWORK = "Network";
    public static final String SP_STRATEGY_KEY_THRESHOLD = "Threshold";
    public static final String SP_STRATEGY_KEY_TRIGGER = "Trigger";
    private static final String TAG = "LogStrategyManager";
    private static final int ZIP_AND_SEVENZIP_NO = 1;
    private static final int ZIP_AND_SEVENZIP_YES = 2;
    private Context context;
    private ContextInfo contextInfo;
    private DataChangeBroadCastReceiver dataChangeBroadCastReceiver;
    private int disableNoLockLog;
    private int disableToolsProcessTag;
    private int enableTrafficLimitTag;
    private Map<String, String> intervalEventMap = new ConcurrentHashMap();
    private boolean isReadAndParseStrategy;
    private int positiveDiagnoseTag;
    private long previousRequestTime;
    private RealTimeConfig realTimeConfig = new RealTimeConfig();
    private Map<String, LogStrategyInfo> strategyDataMap = new ConcurrentHashMap();
    private Map<String, Long> uploadTimeMap = new ConcurrentHashMap();
    private int zipAndSevenZipTag;

    public static synchronized LogStrategyManager createInstance(Context context2, ContextInfo contextInfo2) {
        LogStrategyManager logStrategyManager;
        synchronized (LogStrategyManager.class) {
            if (INSTANCE == null) {
                INSTANCE = new LogStrategyManager(context2, contextInfo2);
            }
            logStrategyManager = INSTANCE;
        }
        return logStrategyManager;
    }

    public static LogStrategyManager getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        throw new IllegalStateException("need createInstance before use");
    }

    private LogStrategyManager(Context context2, ContextInfo contextInfo2) {
        this.context = context2;
        this.contextInfo = contextInfo2;
        registerDateChangeReceiver();
    }

    private void registerDateChangeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.DATE_CHANGED");
        this.dataChangeBroadCastReceiver = new DataChangeBroadCastReceiver();
        if (this.context != null) {
            this.context.registerReceiver(this.dataChangeBroadCastReceiver, filter);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0069, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().info(TAG, " readAndParseStrategy END. spend: " + (android.os.SystemClock.uptimeMillis() - r4));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readAndParseStrategy() {
        /*
            r10 = this;
            boolean r6 = r10.isReadAndParseStrategy
            if (r6 == 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            long r4 = android.os.SystemClock.uptimeMillis()
            monitor-enter(r10)
            boolean r6 = r10.isReadAndParseStrategy     // Catch:{ all -> 0x0010 }
            if (r6 == 0) goto L_0x0013
            monitor-exit(r10)     // Catch:{ all -> 0x0010 }
            goto L_0x0004
        L_0x0010:
            r6 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0010 }
            throw r6
        L_0x0013:
            r3 = -1
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0010 }
            android.os.Looper r7 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0010 }
            java.lang.Thread r7 = r7.getThread()     // Catch:{ all -> 0x0010 }
            if (r6 == r7) goto L_0x003d
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0010 }
            int r3 = r6.getPriority()     // Catch:{ all -> 0x0010 }
            android.os.Looper r6 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0010 }
            java.lang.Thread r6 = r6.getThread()     // Catch:{ all -> 0x0010 }
            int r0 = r6.getPriority()     // Catch:{ all -> 0x0010 }
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0010 }
            r6.setPriority(r0)     // Catch:{ all -> 0x0010 }
        L_0x003d:
            android.content.Context r6 = r10.context     // Catch:{ Throwable -> 0x0089 }
            java.lang.String r7 = "LogStrategyConfig"
            r8 = 4
            android.content.SharedPreferences r6 = r6.getSharedPreferences(r7, r8)     // Catch:{ Throwable -> 0x0089 }
            java.lang.String r7 = "StrategConfigContent2nd"
            r8 = 0
            java.lang.String r1 = r6.getString(r7, r8)     // Catch:{ Throwable -> 0x0089 }
            r10.parseLogStrategy(r1)     // Catch:{ Throwable -> 0x0089 }
        L_0x0050:
            r6 = 1
            r10.isReadAndParseStrategy = r6     // Catch:{ all -> 0x0010 }
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0010 }
            android.os.Looper r7 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0010 }
            java.lang.Thread r7 = r7.getThread()     // Catch:{ all -> 0x0010 }
            if (r6 == r7) goto L_0x0068
            java.lang.Thread r6 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0010 }
            r6.setPriority(r3)     // Catch:{ all -> 0x0010 }
        L_0x0068:
            monitor-exit(r10)     // Catch:{ all -> 0x0010 }
            long r6 = android.os.SystemClock.uptimeMillis()
            long r4 = r6 - r4
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r7 = "LogStrategyManager"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = " readAndParseStrategy END. spend: "
            r8.<init>(r9)
            java.lang.StringBuilder r8 = r8.append(r4)
            java.lang.String r8 = r8.toString()
            r6.info(r7, r8)
            goto L_0x0004
        L_0x0089:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0010 }
            java.lang.String r7 = "LogStrategyManager"
            java.lang.String r8 = "readAndParseStrategy"
            r6.error(r7, r8, r2)     // Catch:{ all -> 0x0010 }
            goto L_0x0050
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.strategy.LogStrategyManager.readAndParseStrategy():void");
    }

    public boolean isLogWrite(String logCategory, Level level) {
        readAndParseStrategy();
        if (logCategory == null) {
            return false;
        }
        LogStrategyInfo strategyInfo = this.strategyDataMap.get(logCategory);
        if (strategyInfo != null) {
            if (!strategyInfo.isWrite) {
                return false;
            }
            if (!isHitTest(strategyInfo, level)) {
                return false;
            }
            if (strategyInfo.level == -1) {
                return true;
            }
            if (level == null) {
                return true;
            }
            if (strategyInfo.level < level.loggerLevel) {
                return false;
            }
            return true;
        } else if ("crash".equalsIgnoreCase(logCategory)) {
            long now = System.currentTimeMillis();
            SharedPreferences sp = this.context.getSharedPreferences("CrashCountInfo", 4);
            long curHour = now / TimeUnit.HOURS.toMillis(1);
            long lastCrashHour = sp.getLong(KEY_CUR_CRASH_HOUR, 0);
            LoggerFactory.getTraceLogger().info(TAG, "isLogWrite, curHour:" + curHour + " lastCrashHour:" + lastCrashHour);
            if (curHour != lastCrashHour) {
                Editor edit = sp.edit();
                edit.putLong(KEY_CUR_CRASH_HOUR, curHour);
                edit.putInt(KEY_CUR_CRASH_HOUR_COUNT, 1);
                LoggerFactory.getTraceLogger().info(TAG, "isLogWrite, hourCommitResult:" + edit.commit());
            } else {
                int curCrashHourCout = sp.getInt(KEY_CUR_CRASH_HOUR_COUNT, 0) + 1;
                LoggerFactory.getTraceLogger().info(TAG, "isLogWrite, curCrashHourCout:" + curCrashHourCout);
                if (curCrashHourCout > 50) {
                    LoggerFactory.getTraceLogger().error((String) TAG, "crash count beyound hour limit:" + curCrashHourCout);
                    return false;
                }
                LoggerFactory.getTraceLogger().info(TAG, "isLogWrite, curCrashHourCoutCommitResult:" + sp.edit().putInt(KEY_CUR_CRASH_HOUR_COUNT, curCrashHourCout).commit());
            }
            long curMinute = now / TimeUnit.MINUTES.toMillis(1);
            long lastCrashMinute = sp.getLong(KEY_CUR_CRASH_MINUTE, 0);
            LoggerFactory.getTraceLogger().info(TAG, "isLogWrite, curMinute:" + curMinute + " lastCrashMinute:" + lastCrashMinute);
            if (curMinute != lastCrashMinute) {
                Editor edit2 = sp.edit();
                edit2.putLong(KEY_CUR_CRASH_MINUTE, curMinute);
                edit2.putInt(KEY_CUR_CRASH_MINUTE_COUNT, 1);
                LoggerFactory.getTraceLogger().info(TAG, "isLogWrite, minuteCommitResult:" + edit2.commit());
            } else {
                int curCrashMinuteCout = sp.getInt(KEY_CUR_CRASH_MINUTE_COUNT, 0) + 1;
                LoggerFactory.getTraceLogger().info(TAG, "isLogWrite, curCrashMinuteCout:" + curCrashMinuteCout);
                if (curCrashMinuteCout > 2) {
                    LoggerFactory.getTraceLogger().error((String) TAG, "crash count beyound minute limit:" + curCrashMinuteCout);
                    return false;
                }
                LoggerFactory.getTraceLogger().info(TAG, "isLogWrite, curCrashMinuteCoutCommitResult:" + sp.edit().putInt(KEY_CUR_CRASH_MINUTE_COUNT, curCrashMinuteCout).commit());
            }
            return true;
        } else if (LogCategory.CATEGORY_KEYBIZTRACE.equalsIgnoreCase(logCategory)) {
            long now2 = System.currentTimeMillis();
            SharedPreferences sp2 = this.context.getSharedPreferences(SP_NAME_KEYBIZ_INFO, 4);
            long curDay = now2 / TimeUnit.DAYS.toMillis(1);
            if (curDay != sp2.getLong(KEY_CUR_KEYBIZ_DAY, 0)) {
                Editor edit3 = sp2.edit();
                edit3.putLong(KEY_CUR_KEYBIZ_DAY, curDay);
                edit3.putInt(KEY_CUR_KEYBIZ_DAY_COUNT, 1);
                edit3.commit();
            } else {
                int curBizDayCout = sp2.getInt(KEY_CUR_KEYBIZ_DAY_COUNT, 0) + 1;
                if (curBizDayCout > 200) {
                    LoggerFactory.getTraceLogger().error((String) TAG, "key biz trace count beyound day limit:" + curBizDayCout);
                    return false;
                }
                sp2.edit().putInt(KEY_CUR_KEYBIZ_DAY_COUNT, curBizDayCout).commit();
            }
            return true;
        } else if (LogCategory.CATEGORY_SDKMONITOR.equalsIgnoreCase(logCategory)) {
            return false;
        } else {
            if (LogCategory.CATEGORY_ROMESYNC.equalsIgnoreCase(logCategory)) {
                return false;
            }
            return true;
        }
    }

    public boolean needEncrypt(String logCategory) {
        if (TextUtils.isEmpty(logCategory)) {
            return false;
        }
        LogStrategyInfo strategyInfo = this.strategyDataMap.get(logCategory);
        if (strategyInfo != null) {
            return strategyInfo.isEncrypt;
        }
        return false;
    }

    public boolean isLogSend(String fileName) {
        readAndParseStrategy();
        String[] loginfo = fileName.split("_");
        if (loginfo.length < 3) {
            return false;
        }
        String logCategory = loginfo[2];
        String networkType = NetWorkProvider.getInstance().getCurrentNetworkType2Str();
        if ("unknown".equals(networkType)) {
            return true;
        }
        LogStrategyInfo strategyInfo = this.strategyDataMap.get(logCategory);
        if (strategyInfo != null) {
            if (!strategyInfo.hasSendCondition) {
                return true;
            }
            if (UtilityImpl.NET_TYPE_4G.equals(networkType) && !strategyInfo.sendCondition.contains(UtilityImpl.NET_TYPE_4G)) {
                return false;
            }
            if (UtilityImpl.NET_TYPE_3G.equals(networkType) && !strategyInfo.sendCondition.contains(UtilityImpl.NET_TYPE_3G)) {
                return false;
            }
            if (UtilityImpl.NET_TYPE_2G.equals(networkType) && !strategyInfo.sendCondition.contains(UtilityImpl.NET_TYPE_2G)) {
                return false;
            }
            if ("wifi".equals(networkType) && !strategyInfo.sendCondition.contains("wifi")) {
                return false;
            }
        }
        return true;
    }

    public String isLogSend(String fileName, String logCategoryFilter) {
        readAndParseStrategy();
        String[] loginfo = fileName.split("_");
        if (loginfo.length < 3) {
            return null;
        }
        String logCategory = loginfo[2];
        if (logCategoryFilter != null && !logCategoryFilter.equals(logCategory)) {
            return null;
        }
        String networkType = NetWorkProvider.getInstance().getCurrentNetworkType2Str();
        if ("unknown".equals(networkType)) {
            return logCategory;
        }
        LogStrategyInfo strategyInfo = this.strategyDataMap.get(logCategory);
        if (strategyInfo == null || !strategyInfo.hasSendCondition) {
            return logCategory;
        }
        if (UtilityImpl.NET_TYPE_4G.equals(networkType) && !strategyInfo.sendCondition.contains(UtilityImpl.NET_TYPE_4G)) {
            return null;
        }
        if (UtilityImpl.NET_TYPE_3G.equals(networkType) && !strategyInfo.sendCondition.contains(UtilityImpl.NET_TYPE_3G)) {
            return null;
        }
        if (UtilityImpl.NET_TYPE_2G.equals(networkType) && !strategyInfo.sendCondition.contains(UtilityImpl.NET_TYPE_2G)) {
            return null;
        }
        if (!"wifi".equals(networkType) || strategyInfo.sendCondition.contains("wifi")) {
            return logCategory;
        }
        return null;
    }

    public boolean isLogUpload(String logCategory, String eventType) {
        readAndParseStrategy();
        List eventFilters = null;
        LogStrategyInfo strategyInfo = this.strategyDataMap.get(logCategory);
        if (strategyInfo != null) {
            eventFilters = strategyInfo.uploadEvents;
        }
        if (eventFilters == null) {
            eventFilters = new ArrayList();
        }
        if (eventFilters.isEmpty()) {
            if (LogCategory.CATEGORY_ALIVEREPORT.equals(logCategory) || LogCategory.CATEGORY_PERFORMANCE.equals(logCategory) || LogCategory.CATEGORY_USERBEHAVOR.equals(logCategory) || LogCategory.CATEGORY_AUTOUSERBEHAVOR.equals(logCategory)) {
                eventFilters.add(LogContext.ENVENT_GOTOBACKGROUND);
            } else if ("crash".equals(logCategory) || LogCategory.CATEGORY_APM.equals(logCategory) || LogCategory.CATEGORY_DATAFLOW.equals(logCategory) || LogCategory.CATEGORY_BATTERY.equals(logCategory)) {
                eventFilters.add(LogContext.ENVENT_GOTOBACKGROUND);
                eventFilters.add(LogContext.CLIENT_ENVENT_CLIENTLAUNCH);
            } else {
                eventFilters.add(LogContext.CLIENT_ENVENT_PERIODCHECK);
                eventFilters.add(LogContext.ENVENT_GOTOBACKGROUND);
            }
        } else if (LogCategory.CATEGORY_LOGMONITOR.equals(logCategory) && !eventFilters.contains(LogContext.CLIENT_ENVENT_PERIODCHECK)) {
            eventFilters.add(LogContext.CLIENT_ENVENT_PERIODCHECK);
        }
        return eventFilters.contains(eventType);
    }

    public boolean isLogUploadByInterval(String logCategory, String eventType) {
        if (TextUtils.isEmpty(logCategory) || !this.intervalEventMap.containsKey(eventType)) {
            return true;
        }
        Long lastTime = this.uploadTimeMap.get(logCategory);
        if (lastTime == null || lastTime.longValue() <= 0) {
            this.uploadTimeMap.put(logCategory, Long.valueOf(System.currentTimeMillis()));
            return true;
        }
        LogStrategyInfo info = this.strategyDataMap.get(logCategory);
        int interval = 60000;
        if (info != null && info.uploadInterval >= 0) {
            interval = info.uploadInterval * 1000;
        }
        long now = System.currentTimeMillis();
        if (Math.abs(now - lastTime.longValue()) <= ((long) interval)) {
            return false;
        }
        this.uploadTimeMap.put(logCategory, Long.valueOf(now));
        return true;
    }

    public boolean isLogUpload(String logCategory, int msgCount, LogContext context2) {
        readAndParseStrategy();
        int defalutThreshold = 100;
        if (LogCategory.CATEGORY_DATAFLOW.equals(logCategory) || LogCategory.CATEGORY_BATTERY.equals(logCategory)) {
            defalutThreshold = 100;
        } else if (LogCategory.CATEGORY_USERBEHAVOR.equals(logCategory) || LogCategory.CATEGORY_AUTOUSERBEHAVOR.equals(logCategory) || LogCategory.CATEGORY_EXCEPTION.equals(logCategory) || LogCategory.CATEGORY_NETWORK.equals(logCategory)) {
            defalutThreshold = 50;
        } else if (LogCategory.CATEGORY_ALIVEREPORT.equals(logCategory)) {
            defalutThreshold = 10;
        } else if ("crash".equals(logCategory) || LogCategory.CATEGORY_APM.equals(logCategory) || LogCategory.CATEGORY_KEYBIZTRACE.equals(logCategory)) {
            defalutThreshold = 1;
        }
        int threshold = defalutThreshold;
        LogStrategyInfo strategyInfo = this.strategyDataMap.get(logCategory);
        if (strategyInfo != null && strategyInfo.threshold > 0) {
            threshold = strategyInfo.threshold;
        }
        String uploadSize = context2.getContextParam(new StringBuilder(LogContext.STORAGE_LOGCATEGORY_UPLOAD_PERFIX).append(logCategory).toString());
        if (!TextUtils.isEmpty(uploadSize)) {
            try {
                threshold = Integer.parseInt(uploadSize);
                Log.i(TAG, "threshold = " + threshold);
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) TAG, e);
            }
        }
        if (msgCount >= threshold) {
            return true;
        }
        return false;
    }

    public boolean isRealTimeLogCategory(String logCategory) {
        if (TextUtils.isEmpty(logCategory)) {
            return false;
        }
        readAndParseStrategy();
        LogStrategyInfo strategyInfo = this.strategyDataMap.get(logCategory);
        if (strategyInfo == null || !this.realTimeConfig.isEnable()) {
            return false;
        }
        return strategyInfo.realtime;
    }

    public LogStrategyInfo getLogStrategyInfo(String logCategory) {
        if (this.strategyDataMap == null || TextUtils.isEmpty(logCategory)) {
            return null;
        }
        return this.strategyDataMap.get(logCategory);
    }

    public Map<String, LogStrategyInfo> getLogStrategyInfos() {
        return this.strategyDataMap;
    }

    public void adjustRequestSpanByReceived() {
        readAndParseStrategy();
    }

    public void adjustRequestSpanByNetNotMatch() {
        readAndParseStrategy();
    }

    public void adjustRequestSpanByZipFail() {
        readAndParseStrategy();
    }

    public void adjustRequestSpanByUploadFail() {
        readAndParseStrategy();
    }

    public void revertRequestSpanToNormal() {
        readAndParseStrategy();
        LoggerFactory.getTraceLogger().info(TAG, "revertRequestSpanToNormal: " + CURRENT_REQUEST_TIME_SPAN);
        this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).edit().putLong(KEY_CURRENT_REQUEST_TIMESPAN, CURRENT_REQUEST_TIME_SPAN).apply();
    }

    public void queryStrategy(String actionType, boolean isForceRequest) {
        SharedPreferences sp;
        readAndParseStrategy();
        if (isForceRequest) {
            asyncRequestLogConfig(actionType, isForceRequest);
            return;
        }
        if (LoggerFactory.getProcessInfo().isMainProcess()) {
            sp = this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 0);
        } else {
            sp = this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4);
        }
        long nowTime = System.currentTimeMillis();
        long prevTime = sp.getLong(KEY_PREVIOUS_REQUEST_TIME, 0);
        if (Math.abs(nowTime - prevTime) < sp.getLong(KEY_CURRENT_REQUEST_TIMESPAN, CURRENT_REQUEST_TIME_SPAN)) {
            Log.i(TAG, "queryStrategy returned by span: " + actionType);
        } else if (Math.abs(nowTime - this.previousRequestTime) < REQUEST_TWICE_SPAN) {
            LoggerFactory.getTraceLogger().error((String) TAG, "queryStrategy returned by twice: " + actionType);
        } else {
            this.previousRequestTime = nowTime;
            if (NetUtil.isNetworkConnected(this.context)) {
                saveRequestTimeAndRevertRequestSpanToNormal();
            }
            asyncRequestLogConfig(actionType, isForceRequest);
        }
    }

    private void asyncRequestLogConfig(String actionType, boolean isForceRequest) {
        new Thread(new a(this, actionType, isForceRequest), "LogStrategyManager.request").start();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void syncRequestLogConfig(java.lang.String r22, boolean r23) {
        /*
            r21 = this;
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "LogStrategyManager"
            java.lang.StringBuilder r19 = new java.lang.StringBuilder
            java.lang.String r20 = "syncRequestLogConfig: "
            r19.<init>(r20)
            r0 = r19
            r1 = r22
            java.lang.StringBuilder r19 = r0.append(r1)
            java.lang.String r20 = ", isForceRequest: "
            java.lang.StringBuilder r19 = r19.append(r20)
            r0 = r19
            r1 = r23
            java.lang.StringBuilder r19 = r0.append(r1)
            java.lang.String r19 = r19.toString()
            r0 = r19
            r2.warn(r8, r0)
            com.alipay.mobile.common.logging.api.ProcessInfo r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Throwable -> 0x00a5 }
            boolean r2 = r2.isMainProcess()     // Catch:{ Throwable -> 0x00a5 }
            if (r2 != 0) goto L_0x0044
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r8 = "LogStrategyManager"
            java.lang.String r19 = "syncRequestLogConfig: is not main process"
            r0 = r19
            r2.error(r8, r0)     // Catch:{ Throwable -> 0x00a5 }
        L_0x0043:
            return
        L_0x0044:
            boolean r2 = com.alipay.tianyan.mobilesdk.TianyanLoggingStatus.isStrictBackground()     // Catch:{ Throwable -> 0x00a5 }
            if (r2 == 0) goto L_0x007a
            java.lang.String r2 = "LogStrategy_request_in_bg_disable"
            java.lang.String r8 = ""
            java.lang.String r18 = com.alipay.tianyan.mobilesdk.TianyanLoggingStatus.getConfigValueByKey(r2, r8)     // Catch:{ Throwable -> 0x00a5 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r8 = "LogStrategyManager"
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r20 = "syncRequestLogConfig: background and disable "
            r19.<init>(r20)     // Catch:{ Throwable -> 0x00a5 }
            r0 = r19
            r1 = r18
            java.lang.StringBuilder r19 = r0.append(r1)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r19 = r19.toString()     // Catch:{ Throwable -> 0x00a5 }
            r0 = r19
            r2.error(r8, r0)     // Catch:{ Throwable -> 0x00a5 }
            java.lang.String r2 = "true"
            r0 = r18
            boolean r2 = r2.equals(r0)     // Catch:{ Throwable -> 0x00a5 }
            if (r2 != 0) goto L_0x0043
        L_0x007a:
            com.alipay.mobile.common.logging.api.LogContext r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r13 = r2.getLogHost()     // Catch:{ Throwable -> 0x0096 }
            boolean r2 = android.text.TextUtils.isEmpty(r13)     // Catch:{ Throwable -> 0x0096 }
            if (r2 == 0) goto L_0x00b2
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = "LogStrategyManager"
            java.lang.String r19 = "syncRequestLogConfig: host is none"
            r0 = r19
            r2.error(r8, r0)     // Catch:{ Throwable -> 0x0096 }
            goto L_0x0043
        L_0x0096:
            r9 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "LogStrategyManager"
            java.lang.String r19 = "syncRequestLogConfig"
            r0 = r19
            r2.error(r8, r0, r9)
            goto L_0x0043
        L_0x00a5:
            r17 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "LogStrategyManager"
            r0 = r17
            r2.error(r8, r0)
            goto L_0x007a
        L_0x00b2:
            java.util.HashMap r14 = new java.util.HashMap     // Catch:{ Throwable -> 0x0096 }
            r14.<init>()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r2 = "actionType"
            r0 = r22
            r14.put(r2, r0)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r2 = "userId"
            r0 = r21
            com.alipay.mobile.common.logging.ContextInfo r8 = r0.contextInfo     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = r8.g()     // Catch:{ Throwable -> 0x0096 }
            r14.put(r2, r8)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r2 = "productId"
            r0 = r21
            com.alipay.mobile.common.logging.ContextInfo r8 = r0.contextInfo     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = r8.e()     // Catch:{ Throwable -> 0x0096 }
            r14.put(r2, r8)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r2 = "productVersion"
            r0 = r21
            com.alipay.mobile.common.logging.ContextInfo r8 = r0.contextInfo     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = r8.f()     // Catch:{ Throwable -> 0x0096 }
            r14.put(r2, r8)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r2 = "processName"
            com.alipay.mobile.common.logging.api.ProcessInfo r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = r8.getProcessAlias()     // Catch:{ Throwable -> 0x0096 }
            r14.put(r2, r8)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r2 = "utdId"
            r0 = r21
            com.alipay.mobile.common.logging.ContextInfo r8 = r0.contextInfo     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = r8.i()     // Catch:{ Throwable -> 0x0096 }
            r14.put(r2, r8)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r2 = "templateId"
            java.lang.String r8 = "2.0"
            r14.put(r2, r8)     // Catch:{ Throwable -> 0x0096 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0096 }
            r2.<init>()     // Catch:{ Throwable -> 0x0096 }
            java.lang.StringBuilder r2 = r2.append(r13)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = "/loggw/logConfig.do"
            java.lang.StringBuilder r2 = r2.append(r8)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r3 = r2.toString()     // Catch:{ Throwable -> 0x0096 }
            com.alipay.mobile.common.logging.http.HttpClient r11 = new com.alipay.mobile.common.logging.http.HttpClient     // Catch:{ Throwable -> 0x0096 }
            r0 = r21
            android.content.Context r2 = r0.context     // Catch:{ Throwable -> 0x0096 }
            r11.<init>(r3, r2)     // Catch:{ Throwable -> 0x0096 }
            r12 = 0
            java.lang.String r10 = ""
            org.apache.http.HttpResponse r12 = r11.synchronousRequestByGET(r14)     // Catch:{ Throwable -> 0x014c }
        L_0x0129:
            if (r12 != 0) goto L_0x0152
            r11.closeStreamForNextExecute()     // Catch:{ Throwable -> 0x0096 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = "LogStrategyManager"
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r20 = "syncRequestLogConfig: response is NULL, network error: "
            r19.<init>(r20)     // Catch:{ Throwable -> 0x0096 }
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r10)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r19 = r19.toString()     // Catch:{ Throwable -> 0x0096 }
            r0 = r19
            r2.error(r8, r0)     // Catch:{ Throwable -> 0x0096 }
            goto L_0x0043
        L_0x014c:
            r2 = move-exception
            java.lang.String r10 = r2.toString()     // Catch:{ Throwable -> 0x0096 }
            goto L_0x0129
        L_0x0152:
            r21.saveRequestTimeAndRevertRequestSpanToNormal()     // Catch:{ Throwable -> 0x0096 }
            int r15 = r11.getResponseCode()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r16 = r11.getResponseContent()     // Catch:{ Throwable -> 0x0096 }
            long r4 = r11.getRequestLength()     // Catch:{ Throwable -> 0x0096 }
            long r6 = r11.getResponseLength()     // Catch:{ Throwable -> 0x0096 }
            com.alipay.mobile.common.logging.api.monitor.DataflowID r2 = com.alipay.mobile.common.logging.api.monitor.DataflowID.MDAP_LOG     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = "strategy"
            com.alipay.mobile.common.logging.api.monitor.DataflowModel r2 = com.alipay.mobile.common.logging.api.monitor.DataflowModel.obtain(r2, r3, r4, r6, r8)     // Catch:{ Throwable -> 0x0096 }
            r2.report()     // Catch:{ Throwable -> 0x0096 }
            r11.closeStreamForNextExecute()     // Catch:{ Throwable -> 0x0096 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r15 != r2) goto L_0x017d
            boolean r2 = android.text.TextUtils.isEmpty(r16)     // Catch:{ Throwable -> 0x0096 }
            if (r2 == 0) goto L_0x019b
        L_0x017d:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = "LogStrategyManager"
            java.lang.StringBuilder r19 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r20 = "syncRequestLogConfig: response is none, or responseCode is "
            r19.<init>(r20)     // Catch:{ Throwable -> 0x0096 }
            r0 = r19
            java.lang.StringBuilder r19 = r0.append(r15)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r19 = r19.toString()     // Catch:{ Throwable -> 0x0096 }
            r0 = r19
            r2.error(r8, r0)     // Catch:{ Throwable -> 0x0096 }
            goto L_0x0043
        L_0x019b:
            r0 = r21
            r1 = r16
            r0.syncLogConfig(r1)     // Catch:{ Throwable -> 0x0096 }
            goto L_0x0043
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.strategy.LogStrategyManager.syncRequestLogConfig(java.lang.String, boolean):void");
    }

    public void syncLogConfig(String responseContent) {
        try {
            JSONObject strategyJson = new JSONObject(responseContent);
            if (strategyJson.getInt("code") != 200) {
                LoggerFactory.getTraceLogger().error((String) TAG, "syncRequestLogConfig: serverLogicCode is not 200, " + responseContent);
                return;
            }
            try {
                if (strategyJson.has("diagnose")) {
                    LoggerFactory.getTraceLogger().info(TAG, "syncRequestLogConfig: has diagnose tasks");
                    JSONArray diagnoseJson = strategyJson.getJSONArray("diagnose");
                    if (diagnoseJson != null) {
                        Intent intent = new Intent();
                        intent.setClassName(this.context, LogContext.AMAP_LOG_SERVICE_CLASS_NAME);
                        intent.setAction(this.context.getPackageName() + ".push.action.MONITOR_RECEIVED");
                        intent.putExtra("config_msg_tasks", diagnoseJson.toString());
                        intent.putExtra("config_msg_userid", this.contextInfo.g());
                        try {
                            intent.setPackage(this.context.getPackageName());
                        } catch (Throwable th) {
                        }
                        if (this.context.startService(intent) == null) {
                            LoggerFactory.getTraceLogger().error((String) TAG, (String) "syncRequestLogConfig: start service for diagnose occured error");
                        }
                    }
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error(TAG, "syncRequestLogConfig", e);
            }
            if (strategyJson.has("content")) {
                LoggerFactory.getTraceLogger().info(TAG, "syncRequestLogConfig: has configs");
                String data = strategyJson.getString("content");
                updateLogStrategy(data);
                if (LoggerFactory.getProcessInfo().isMainProcess()) {
                    notifyOtherProcessToUpdateLogStrategy(LogContext.PUSH_LOG_SERVICE_CLASS_NAME, data);
                    if (!isDisableToolsProcess()) {
                        notifyOtherProcessToUpdateLogStrategy(LogContext.TOOLS_SERVICE_CLASS_NAME, data);
                    }
                    notifyLiteProcessToUpdateLogStrategy(data);
                } else if (LoggerFactory.getProcessInfo().isPushProcess()) {
                    if (!isDisableToolsProcess()) {
                        notifyOtherProcessToUpdateLogStrategy(LogContext.TOOLS_SERVICE_CLASS_NAME, data);
                    }
                } else if (!LoggerFactory.getProcessInfo().isToolsProcess() && !LoggerFactory.getProcessInfo().isExtProcess()) {
                    LoggerFactory.getTraceLogger().error((String) TAG, "syncRequestLogConfig, error: unknown process " + LoggerFactory.getProcessInfo().getProcessAlias());
                }
            }
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error(TAG, "syncLogConfig", e2);
        }
    }

    private void notifyOtherProcessToUpdateLogStrategy(String serviceClassName, String strategyConfig) {
        if (!TextUtils.isEmpty(serviceClassName) && !TextUtils.isEmpty(strategyConfig)) {
            Intent intent = new Intent();
            intent.setClassName(this.context, serviceClassName);
            intent.setAction(this.context.getPackageName() + ".monitor.action.UPDATE_LOG_STRATEGY");
            intent.putExtra("strategy", strategyConfig);
            try {
                intent.setPackage(this.context.getPackageName());
            } catch (Throwable th) {
            }
            try {
                if (this.context.startService(intent) == null) {
                    LoggerFactory.getTraceLogger().error((String) TAG, (String) "notifyOtherProcessToUpdateLogStrategy: start service occured error");
                }
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error(TAG, "notifyOtherProcessToUpdateLogStrategy", t);
            }
        }
    }

    private synchronized void notifyLiteProcessToUpdateLogStrategy(String strategyConfig) {
        if (!TextUtils.isEmpty(strategyConfig)) {
            Map processMap = new HashMap();
            try {
                for (RunningAppProcessInfo process : ((ActivityManager) this.context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                    processMap.put(process.processName, process.processName);
                }
            } catch (Throwable t) {
                Log.e(TAG, "getProcessIdByName: " + t);
            }
            for (int i = 1; i <= 5; i++) {
                String liteProcessName = "com.alipay.mobile.common.logging.process.LogServiceInlite" + i;
                if (processMap.containsKey(liteProcessName)) {
                    Intent intent = new Intent();
                    intent.setClassName(this.context, liteProcessName);
                    intent.setAction(this.context.getPackageName() + ".monitor.action.UPDATE_LOG_STRATEGY");
                    intent.putExtra("strategy", strategyConfig);
                    try {
                        intent.setPackage(this.context.getPackageName());
                    } catch (Throwable th) {
                    }
                    try {
                        if (this.context.startService(intent) == null) {
                            LoggerFactory.getTraceLogger().error((String) TAG, (String) "notifyLiteProcessToUpdateLogStrategy: start service occured error");
                        }
                    } catch (Throwable t2) {
                        LoggerFactory.getTraceLogger().error(TAG, "notifyLiteProcessToUpdateLogStrategy", t2);
                    }
                }
            }
        }
    }

    public void updateBackgroundTime(long timestamp) {
        readAndParseStrategy();
        SharedPreferences sp = this.context.getSharedPreferences("CrashCountInfo", 4);
        if (timestamp != sp.getLong(KEY_BACKGROUND_TIMESTAMP, -9)) {
            sp.edit().putLong(KEY_BACKGROUND_TIMESTAMP, timestamp).commit();
        }
    }

    public long getBackgroundTime() {
        readAndParseStrategy();
        return this.context.getSharedPreferences("CrashCountInfo", 4).getLong(KEY_BACKGROUND_TIMESTAMP, 0);
    }

    public void updateLogStrategy(String strategyConfig) {
        readAndParseStrategy();
        if (!TextUtils.isEmpty(strategyConfig)) {
            this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).edit().putString(KEY_STRATEG_CONFIG_CONTENT, strategyConfig).apply();
            try {
                parseLogStrategy(strategyConfig);
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) TAG, e);
            }
        }
    }

    private void parseLogStrategy(String strategyConfig) {
        String header;
        LogStrategyInfo strategyInfo;
        if (!TextUtils.isEmpty(strategyConfig)) {
            if (LoggingUtil.isDebuggable(this.context)) {
                LoggerFactory.getTraceLogger().info(TAG, "parseLogStrategy: " + strategyConfig);
            }
            JSONObject datas = new JSONObject(strategyConfig);
            Iterator keyIterator = datas.keys();
            boolean hasPositiveDiagnoseLog = false;
            boolean hasPositiveUpInterval = false;
            boolean hasZipAndSevenZip = false;
            boolean hasDisableToolsProcess = false;
            boolean hasEnableTrafficLimit = false;
            boolean hasDisableNoLockLog = false;
            while (keyIterator.hasNext()) {
                try {
                    String key = keyIterator.next();
                    JSONObject dataItem = null;
                    try {
                        dataItem = datas.getJSONObject(key);
                    } catch (Throwable th) {
                    }
                    if (dataItem != null) {
                        if ("positiveDiagnoseLog".equalsIgnoreCase(key)) {
                            if (dataItem.has("event")) {
                                this.positiveDiagnoseTag = 3;
                                if (dataItem.has(DataflowMonitorModel.METHOD_NAME_SEND)) {
                                    JSONArray networks = dataItem.getJSONArray(DataflowMonitorModel.METHOD_NAME_SEND);
                                    int index = 0;
                                    while (true) {
                                        if (index >= networks.length()) {
                                            break;
                                        }
                                        String network = networks.getString(index);
                                        if (Constants.ANIMATOR_NONE.equalsIgnoreCase(network)) {
                                            this.positiveDiagnoseTag = 1;
                                            break;
                                        }
                                        if ("wifi".equalsIgnoreCase(network)) {
                                            this.positiveDiagnoseTag = 2;
                                        }
                                        index++;
                                    }
                                }
                                try {
                                    if (this.positiveDiagnoseTag != 1 && dataItem.has("upInterval")) {
                                        long j = dataItem.getLong("upInterval") * TimeUnit.MINUTES.toMillis(1);
                                        CURRENT_REQUEST_TIME_SPAN = j;
                                        if (j <= 0) {
                                            CURRENT_REQUEST_TIME_SPAN = DEFAULT_REQUEST_TIME_SPAN;
                                        } else if (CURRENT_REQUEST_TIME_SPAN < MINIMUM_REQUEST_TIME_SPAN) {
                                            CURRENT_REQUEST_TIME_SPAN = MINIMUM_REQUEST_TIME_SPAN;
                                        } else if (CURRENT_REQUEST_TIME_SPAN > MAXIMAL_REQUEST_TIME_SPAN) {
                                            CURRENT_REQUEST_TIME_SPAN = MAXIMAL_REQUEST_TIME_SPAN;
                                        }
                                        hasPositiveUpInterval = true;
                                    }
                                } catch (Throwable e) {
                                    Log.w(TAG, e);
                                }
                            } else {
                                this.positiveDiagnoseTag = 1;
                            }
                            hasPositiveDiagnoseLog = true;
                        } else if ("zipAndSevenZip".equalsIgnoreCase(key)) {
                            this.zipAndSevenZipTag = 2;
                            hasZipAndSevenZip = true;
                        } else if ("disableToolsProcess".equalsIgnoreCase(key)) {
                            this.disableToolsProcessTag = 2;
                            hasDisableToolsProcess = true;
                        } else if ("enableTrafficLimit".equalsIgnoreCase(key)) {
                            this.enableTrafficLimitTag = 1;
                            hasEnableTrafficLimit = true;
                        } else if ("disable_nolock_log".equalsIgnoreCase(key)) {
                            this.disableNoLockLog = 1;
                            hasDisableNoLockLog = true;
                        } else if ("realtimeConfig".equalsIgnoreCase(key)) {
                            try {
                                this.realTimeConfig.setInterval(dataItem.getInt(H5SensorPlugin.PARAM_INTERVAL));
                            } catch (Throwable th2) {
                            }
                            try {
                                String value = dataItem.getString("enable");
                                if (BQCCameraParam.VALUE_NO.equals(value) || "false".equals(value)) {
                                    this.realTimeConfig.setEnable(false);
                                } else {
                                    this.realTimeConfig.setEnable(true);
                                }
                            } catch (Throwable th3) {
                            }
                        } else if ("config".equalsIgnoreCase(key)) {
                            Iterator configKeyIterator = dataItem.keys();
                            while (configKeyIterator.hasNext()) {
                                try {
                                    JSONObject configItem = null;
                                    try {
                                        configItem = dataItem.getJSONObject(configKeyIterator.next());
                                    } catch (Throwable th4) {
                                    }
                                    if (configItem != null) {
                                        Iterator configItemIterator = configItem.keys();
                                        while (configItemIterator.hasNext()) {
                                            try {
                                                header = configItemIterator.next();
                                                JSONObject item = configItem.getJSONObject(header);
                                                strategyInfo = new LogStrategyInfo();
                                                try {
                                                    strategyInfo.isWrite = "yes".equalsIgnoreCase(item.getString("write"));
                                                    try {
                                                        strategyInfo.level = item.getInt(H5PermissionManager.level);
                                                        strategyInfo.realtime = "yes".equalsIgnoreCase(item.getString("realtime"));
                                                        this.realTimeConfig.getRealtimeCategory().put(header, Boolean.valueOf(true));
                                                        try {
                                                            strategyInfo.uploadRate = item.getInt("uploadRate");
                                                        } catch (Throwable th5) {
                                                            strategyInfo.uploadRate = -1;
                                                        }
                                                        try {
                                                            strategyInfo.levelRate1 = item.getInt("levelRate1");
                                                        } catch (Throwable th6) {
                                                            strategyInfo.levelRate1 = -1;
                                                        }
                                                        try {
                                                            strategyInfo.levelRate2 = item.getInt("levelRate2");
                                                        } catch (Throwable th7) {
                                                            strategyInfo.levelRate2 = -1;
                                                        }
                                                        try {
                                                            strategyInfo.levelRate3 = item.getInt("levelRate3");
                                                        } catch (Throwable th8) {
                                                            strategyInfo.levelRate3 = -1;
                                                        }
                                                        try {
                                                            strategyInfo.uploadInterval = item.getInt("uploadInterval");
                                                        } catch (Throwable th9) {
                                                            strategyInfo.uploadInterval = -1;
                                                        }
                                                        try {
                                                            strategyInfo.isEncrypt = "yes".equalsIgnoreCase(item.getString(DriveUtil.SCHEME_PARAM_ENCRYPT));
                                                        } catch (Throwable th10) {
                                                            strategyInfo.isEncrypt = false;
                                                        }
                                                        try {
                                                            JSONArray networks2 = item.getJSONArray(DataflowMonitorModel.METHOD_NAME_SEND);
                                                            boolean hasMobileCondition = false;
                                                            for (int index2 = 0; index2 < networks2.length(); index2++) {
                                                                String network2 = networks2.getString(index2);
                                                                strategyInfo.sendCondition.add(network2);
                                                                if (UtilityImpl.NET_TYPE_2G.equalsIgnoreCase(network2) || UtilityImpl.NET_TYPE_3G.equalsIgnoreCase(network2) || UtilityImpl.NET_TYPE_4G.equalsIgnoreCase(network2)) {
                                                                    hasMobileCondition = true;
                                                                }
                                                            }
                                                            if (hasMobileCondition) {
                                                                strategyInfo.sendCondition.add("mobile");
                                                            }
                                                            strategyInfo.hasSendCondition = true;
                                                        } catch (Throwable th11) {
                                                        }
                                                        try {
                                                            JSONArray events = item.getJSONArray("event");
                                                            for (int index3 = 0; index3 < events.length(); index3++) {
                                                                strategyInfo.uploadEvents.add(events.getString(index3));
                                                            }
                                                        } catch (Throwable th12) {
                                                        }
                                                        try {
                                                            strategyInfo.threshold = item.getInt("maxLogCount");
                                                        } catch (Throwable th13) {
                                                        }
                                                        this.strategyDataMap.put(header, strategyInfo);
                                                    } catch (Throwable th14) {
                                                    }
                                                } catch (Throwable th15) {
                                                }
                                            } catch (Throwable th16) {
                                            }
                                        }
                                    }
                                } catch (Throwable th17) {
                                }
                            }
                        }
                    }
                } catch (Throwable e2) {
                    LoggerFactory.getTraceLogger().error(TAG, "parseLogStrategy", e2);
                }
            }
            if (!hasPositiveDiagnoseLog) {
                this.positiveDiagnoseTag = 1;
            }
            if (this.positiveDiagnoseTag == 1 || !hasPositiveUpInterval) {
                CURRENT_REQUEST_TIME_SPAN = DEFAULT_REQUEST_TIME_SPAN;
            }
            if (!hasZipAndSevenZip) {
                this.zipAndSevenZipTag = 1;
            }
            if (!hasDisableToolsProcess) {
                this.disableToolsProcessTag = 1;
            }
            if (!hasEnableTrafficLimit) {
                this.enableTrafficLimitTag = 2;
            }
            if (!hasDisableNoLockLog) {
                this.disableNoLockLog = 2;
            }
            Editor editor = this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).edit();
            editor.putInt(KEY_POSITIVE_DIAGNOSE, this.positiveDiagnoseTag);
            editor.putLong(KEY_CURRENT_REQUEST_TIMESPAN, CURRENT_REQUEST_TIME_SPAN);
            editor.putInt(KEY_ZIP_AND_SEVENZIP, this.zipAndSevenZipTag);
            editor.putInt(KEY_DISABLE_TOOLS_PROCESS, this.disableToolsProcessTag);
            editor.putInt(KEY_ENABLE_TRAFFIC_LIMIT, this.enableTrafficLimitTag);
            editor.putInt(KEY_ENABLE_NOLOCK_LOG, this.disableNoLockLog);
            editor.apply();
            StringBuilder buf = new StringBuilder("parseLogStrategy");
            buf.append(", positiveDiagnoseTag: ").append(this.positiveDiagnoseTag);
            buf.append(", CURRENT_REQUEST_TIME_SPAN: ").append(CURRENT_REQUEST_TIME_SPAN);
            buf.append(", zipAndSevenZipTag: ").append(this.zipAndSevenZipTag);
            buf.append(", disableToolsProcessTag: ").append(this.disableToolsProcessTag);
            buf.append(", enableTrafficLimitTag: ").append(this.enableTrafficLimitTag);
            buf.append(", disableNoLockLog: ").append(this.disableNoLockLog);
            LoggerFactory.getTraceLogger().info(TAG, buf.toString());
        }
    }

    public boolean isPositiveDiagnose() {
        readAndParseStrategy();
        if (this.positiveDiagnoseTag == 0) {
            this.positiveDiagnoseTag = this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).getInt(KEY_POSITIVE_DIAGNOSE, 1);
        }
        if (this.positiveDiagnoseTag == 2) {
            NetworkInfo networkInfo = NetUtil.getActiveNetworkInfo(this.context);
            if (networkInfo == null || networkInfo.getType() != 1) {
                return false;
            }
            return true;
        } else if (this.positiveDiagnoseTag != 3) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isZipAndSevenZip() {
        readAndParseStrategy();
        if (this.zipAndSevenZipTag == 0) {
            this.zipAndSevenZipTag = this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).getInt(KEY_ZIP_AND_SEVENZIP, 1);
        }
        if (this.zipAndSevenZipTag == 2) {
            return true;
        }
        return false;
    }

    public void setDisableToolsProcess(boolean disable) {
        if (disable) {
            this.disableToolsProcessTag = 2;
        } else {
            this.disableToolsProcessTag = 1;
        }
    }

    public boolean isDisableToolsProcess() {
        readAndParseStrategy();
        if (this.disableToolsProcessTag == 0) {
            this.disableToolsProcessTag = this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).getInt(KEY_DISABLE_TOOLS_PROCESS, 1);
        }
        if (this.disableToolsProcessTag == 2) {
            return true;
        }
        return false;
    }

    public boolean isEnableTrafficLimit() {
        readAndParseStrategy();
        if (this.enableTrafficLimitTag == 0) {
            this.enableTrafficLimitTag = this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).getInt(KEY_ENABLE_TRAFFIC_LIMIT, 2);
        }
        if (this.enableTrafficLimitTag == 1) {
            return true;
        }
        return false;
    }

    public boolean isDisableNoLockLog() {
        if (this.disableNoLockLog == 0) {
            this.disableNoLockLog = this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).getInt(KEY_ENABLE_NOLOCK_LOG, 2);
        }
        if (this.disableNoLockLog != 1) {
            return true;
        }
        return false;
    }

    private void saveRequestTimeAndRevertRequestSpanToNormal() {
        this.context.getSharedPreferences(SP_NAME_LOGSTRATEGY_CONFIG, 4).edit().putLong(KEY_PREVIOUS_REQUEST_TIME, System.currentTimeMillis()).apply();
        revertRequestSpanToNormal();
    }

    private boolean isHitTest(LogStrategyInfo logStrategyInfo, Level level) {
        int logLevel = level.loggerLevel;
        Integer isLevelHit = logStrategyInfo.levelHits.get(String.valueOf(logLevel));
        if (isLevelHit == null || isLevelHit.intValue() == LogStrategyInfo.SIMPLING_STATE_INIT) {
            boolean isHit = SimplingUtils.isHitTest(getUploadRateByLevel(logStrategyInfo, logLevel), LoggerFactory.getLogContext().getDeviceId());
            if (isHit) {
                logStrategyInfo.levelHits.put(String.valueOf(logLevel), Integer.valueOf(LogStrategyInfo.SIMPLING_STATE_HIT));
                return isHit;
            }
            logStrategyInfo.levelHits.put(String.valueOf(logLevel), Integer.valueOf(LogStrategyInfo.SIMPLING_STATE_UNHIT));
            return isHit;
        } else if (isLevelHit.intValue() == LogStrategyInfo.SIMPLING_STATE_HIT || isLevelHit.intValue() != LogStrategyInfo.SIMPLING_STATE_UNHIT) {
            return true;
        } else {
            return false;
        }
    }

    private int getUploadRateByLevel(LogStrategyInfo logStrategyInfo, int level) {
        int targetRate = -1;
        switch (level) {
            case 1:
                targetRate = logStrategyInfo.levelRate1;
                break;
            case 2:
                targetRate = logStrategyInfo.levelRate2;
                break;
            case 3:
                targetRate = logStrategyInfo.levelRate3;
                break;
        }
        if (targetRate == -1) {
            return logStrategyInfo.uploadRate;
        }
        return targetRate;
    }

    public void refreshHitState() {
        if (this.strategyDataMap != null) {
            for (Entry<String, LogStrategyInfo> value : this.strategyDataMap.entrySet()) {
                Object val = value.getValue();
                if (val != null && (val instanceof LogStrategyInfo)) {
                    ((LogStrategyInfo) val).levelHits.clear();
                }
            }
        }
    }

    public String getHitTestRate(String logCategory, int level) {
        if (logCategory == null) {
            return "1000";
        }
        LogStrategyInfo info = this.strategyDataMap.get(logCategory);
        if (info == null) {
            return "1000";
        }
        int rate = getUploadRateByLevel(info, level);
        if (rate < 0) {
            return "1000";
        }
        return String.valueOf(rate);
    }

    public RealTimeConfig getRealTimeConfig() {
        return this.realTimeConfig;
    }

    public void setRealTimeConfig(RealTimeConfig realTimeConfig2) {
        this.realTimeConfig = realTimeConfig2;
    }

    public Map<String, String> getIntervalEventMap() {
        return this.intervalEventMap;
    }

    public void setIntervalEventMap(Map<String, String> intervalEventMap2) {
        this.intervalEventMap = intervalEventMap2;
    }
}
