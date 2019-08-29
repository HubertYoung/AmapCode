package com.alipay.mobile.liteprocess;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.monitor.api.MonitorFactory;
import java.util.HashSet;
import java.util.Set;

public class Config {
    static boolean A;
    static boolean B;
    static int C;
    public static int MAX_RETRY_BIND_TIMES;
    public static int MAX_TASKS_100_TO_MORE;
    public static int MAX_TASKS_30_TO_100;
    public static boolean NEED_LITE;
    public static boolean PRELOAD_WHEN_START_SAOYISAO;
    public static boolean SAOYISAO_MONITOR_CONFIG;
    public static boolean TINYAPP_PAGE_FLUENCY;
    public static String TINY_APP_LIST_MAIN_PROCESS;
    public static boolean URGENT_PRELOAD;
    static Set<String> a;
    static int b;
    static boolean c;
    static long d;
    static boolean e;
    static boolean f;
    static long g;
    static int h;
    static int i;
    static boolean j;
    static boolean k;
    static int l;
    static boolean m;
    static boolean n;
    static boolean o;
    static boolean p;
    static boolean q;
    static boolean r;
    static boolean s;
    static boolean t;
    static boolean u;
    static boolean v;
    static int w;
    static boolean x;
    static boolean y;
    static long z;

    static {
        a();
    }

    private static void a() {
        URGENT_PRELOAD = true;
        NEED_LITE = true;
        c = true;
        e = true;
        b = 5;
        d = 300000;
        HashSet hashSet = new HashSet();
        a = hashSet;
        hashSet.add("com.alipay.mobile.beehive.compositeui.app.InitTask");
        a.add("com.alipay.mobile.security.msgcenter.ScreenShotFeedbackSecondaryStarter");
        a.add("com.alipay.mobile.apiexecutor.app.RegisterBeehiveServicePipeTask");
        MAX_RETRY_BIND_TIMES = 0;
        f = true;
        g = 300000;
        h = 10;
        i = 5;
        PRELOAD_WHEN_START_SAOYISAO = false;
        MAX_TASKS_30_TO_100 = 0;
        MAX_TASKS_100_TO_MORE = 0;
        SAOYISAO_MONITOR_CONFIG = false;
        TINY_APP_LIST_MAIN_PROCESS = null;
        j = true;
        k = true;
        l = 1;
        m = true;
        n = true;
        o = true;
        p = true;
        q = true;
        s = true;
        r = true;
        t = true;
        u = true;
        TINYAPP_PAGE_FLUENCY = true;
        v = true;
        w = 300;
        x = false;
        y = true;
        z = 60000;
        A = true;
        B = true;
        C = 1000;
    }

    public static void syncConfig() {
        try {
            ConfigService configService = (ConfigService) Util.getMicroAppContext().findServiceByInterface(ConfigService.class.getName());
            String tinyAppPageFluency = configService.getConfig("KEY_TINYAPP_PAGE_FLUENCY");
            if (!TextUtils.isEmpty(tinyAppPageFluency)) {
                TINYAPP_PAGE_FLUENCY = Boolean.valueOf(tinyAppPageFluency).booleanValue();
            }
            MonitorFactory.getMonitorContext().setTinyAppPageFluencyEnable(TINYAPP_PAGE_FLUENCY);
            if (Util.isMainProcess()) {
                String needLite = configService.getConfig("KEY_NEED_LITE");
                if (!TextUtils.isEmpty(needLite)) {
                    NEED_LITE = Boolean.valueOf(needLite).booleanValue();
                }
                String needPreload = configService.getConfig("KEY_NEED_LITE_PRELOAD");
                if (!TextUtils.isEmpty(needPreload)) {
                    c = Boolean.valueOf(needPreload).booleanValue();
                }
                String maxProcessNum = configService.getConfig("KEY_MAX_LITE_PROCESS_NUM");
                if (!TextUtils.isEmpty(maxProcessNum)) {
                    b = Integer.valueOf(maxProcessNum).intValue();
                }
                String canStopDuration = configService.getConfig("KEY_CAN_LITE_STOP_DURATION");
                if (!TextUtils.isEmpty(canStopDuration)) {
                    d = (long) Integer.valueOf(canStopDuration).intValue();
                }
                String fastStart = configService.getConfig("KEY_TINY_APP_FAST_START");
                if (!TextUtils.isEmpty(fastStart)) {
                    e = Boolean.valueOf(fastStart).booleanValue();
                }
                String urgentPreload = configService.getConfig("KEY_URGENT_PRELOAD_IN_ADVICE");
                if (!TextUtils.isEmpty(urgentPreload)) {
                    URGENT_PRELOAD = Boolean.valueOf(urgentPreload).booleanValue();
                }
                String maxRetryBindTimes = configService.getConfig("KEY_LITE_MAX_RETRY_BIND_TIMES");
                if (!TextUtils.isEmpty(maxRetryBindTimes)) {
                    MAX_RETRY_BIND_TIMES = Integer.valueOf(maxRetryBindTimes).intValue();
                }
                String needStopReady = configService.getConfig("KEY_LITE_NEED_STOP_READY");
                if (!TextUtils.isEmpty(needStopReady)) {
                    f = Boolean.valueOf(needStopReady).booleanValue();
                }
                String canStopReadyDuration = configService.getConfig("KEY_CAN_LITE_STOP_READY_DURATION ");
                if (!TextUtils.isEmpty(canStopReadyDuration)) {
                    g = (long) Integer.valueOf(canStopReadyDuration).intValue();
                }
                String startAppDelayStarted = configService.getConfig("KEY_START_PROCESS_DELAY_STARTED");
                if (!TextUtils.isEmpty(startAppDelayStarted)) {
                    h = Integer.valueOf(startAppDelayStarted).intValue();
                }
                String startAppDelayForeground = configService.getConfig("KEY_START_PROCESS_DELAY_FOREGROUND");
                if (!TextUtils.isEmpty(startAppDelayForeground)) {
                    i = Integer.valueOf(startAppDelayForeground).intValue();
                }
                String preloadWhenStartSaoyisao = configService.getConfig("KEY_PRELOAD_WHEN_START_SAOYISAO");
                if (!TextUtils.isEmpty(preloadWhenStartSaoyisao)) {
                    PRELOAD_WHEN_START_SAOYISAO = Boolean.valueOf(preloadWhenStartSaoyisao).booleanValue();
                }
                String maxTask30to100 = configService.getConfig("KEY_MAX_TASKS_30_TO_100");
                if (!TextUtils.isEmpty(maxTask30to100)) {
                    MAX_TASKS_30_TO_100 = Integer.valueOf(maxTask30to100).intValue();
                }
                String maxTask100ToMore = configService.getConfig("KEY_MAX_TASKS_100_TO_MORE");
                if (!TextUtils.isEmpty(maxTask100ToMore)) {
                    MAX_TASKS_100_TO_MORE = Integer.valueOf(maxTask100ToMore).intValue();
                }
                String saoyisaoMonitorConfig = configService.getConfig("KEY_SAOYISAO_MONITOR_CONFIG");
                if (!TextUtils.isEmpty(saoyisaoMonitorConfig)) {
                    SAOYISAO_MONITOR_CONFIG = Boolean.valueOf(saoyisaoMonitorConfig).booleanValue();
                }
                String tinyAppMainProcess = configService.getConfig("KEY_TINY_APP_LIST_MAIN_PROCESS");
                if (!TextUtils.isEmpty(tinyAppMainProcess)) {
                    TINY_APP_LIST_MAIN_PROCESS = tinyAppMainProcess;
                }
                String quickPreloadWhenHotStartup = configService.getConfig("KEY_QUICK_PRELOAD_WHEN_HOT_STARTUP");
                if (!TextUtils.isEmpty(quickPreloadWhenHotStartup)) {
                    j = Boolean.valueOf(quickPreloadWhenHotStartup).booleanValue();
                }
                String maxRecordIdCount = configService.getConfig("KEY_MAX_RECORD_APP_ID_NUM");
                if (!TextUtils.isEmpty(maxRecordIdCount)) {
                    l = Integer.valueOf(maxRecordIdCount).intValue();
                }
                String loadLocalSp = configService.getConfig("KEY_LITE_CONFIG_LOAD_LOCAL_SP");
                if (!TextUtils.isEmpty(loadLocalSp)) {
                    m = Boolean.valueOf(loadLocalSp).booleanValue();
                }
                String stopProcessWhenAppTypeChange = configService.getConfig("KEY_STOP_PROCESS_WHEN_APP_TYPE_CHANGE");
                if (!TextUtils.isEmpty(stopProcessWhenAppTypeChange)) {
                    o = Boolean.valueOf(stopProcessWhenAppTypeChange).booleanValue();
                }
                String firstForegroundQuickPreload = configService.getConfig("KEY_FIRST_FOREGROUND_QUICK_PRELOAD");
                if (!TextUtils.isEmpty(firstForegroundQuickPreload)) {
                    p = Boolean.valueOf(firstForegroundQuickPreload).booleanValue();
                }
                String canForceStartFromMainUi = configService.getConfig("KEY_CAN_FORCE_START_FROM_MAINUI");
                if (!TextUtils.isEmpty(canForceStartFromMainUi)) {
                    q = Boolean.valueOf(canForceStartFromMainUi).booleanValue();
                }
                String canClearTopAppWhenRestart = configService.getConfig("KEY_CAN_CLEAR_TOP_APP_WHEN_RESTART");
                if (!TextUtils.isEmpty(canClearTopAppWhenRestart)) {
                    r = Boolean.valueOf(canClearTopAppWhenRestart).booleanValue();
                }
                String scanResultSubscribe = configService.getConfig("KEY_SCAN_RESULT_SUBSCRIBE_CONFIG");
                if (!TextUtils.isEmpty(scanResultSubscribe)) {
                    s = Boolean.valueOf(scanResultSubscribe).booleanValue();
                }
                String preloadWhenSchemeStart = configService.getConfig("KEY_PRELOAD_WHEN_SCHEME_START");
                if (!TextUtils.isEmpty(preloadWhenSchemeStart)) {
                    t = Boolean.valueOf(preloadWhenSchemeStart).booleanValue();
                }
                String needSafeGetFromBaseActivity = configService.getConfig("KEY_NEED_SAFE_GETFROMBASEACTIVITY");
                if (!TextUtils.isEmpty(needSafeGetFromBaseActivity)) {
                    u = Boolean.valueOf(needSafeGetFromBaseActivity).booleanValue();
                }
                String needNotifySrvShow = configService.getConfig("KEY_LITE_NEED_NOTIFY_SRV_SHOW");
                if (!TextUtils.isEmpty(needNotifySrvShow)) {
                    v = Boolean.valueOf(needNotifySrvShow).booleanValue();
                }
                String cliOnSrvShowDelay = configService.getConfig("KEY_LITE_CLI_ON_SRV_SHOW_DELAY");
                if (!TextUtils.isEmpty(cliOnSrvShowDelay)) {
                    w = Integer.valueOf(cliOnSrvShowDelay).intValue();
                }
                String tinyAppNotifyLogout = configService.getConfig("KEY_TINY_APP_NOTIFY_LOGOUT");
                if (!TextUtils.isEmpty(tinyAppNotifyLogout)) {
                    x = Boolean.valueOf(tinyAppNotifyLogout).booleanValue();
                }
                String hookLiteDvm = configService.getConfig("KEY_HOOK_LITE_DVM");
                if (!TextUtils.isEmpty(hookLiteDvm)) {
                    y = Boolean.valueOf(hookLiteDvm).booleanValue();
                }
                String addIsLiteMoveTask = configService.getConfig("KEY_ADD_IS_LITE_MOVE_TASK");
                if (!TextUtils.isEmpty(addIsLiteMoveTask)) {
                    A = Boolean.valueOf(addIsLiteMoveTask).booleanValue();
                }
                String waitPreload = configService.getConfig("KEY_PRELOAD_LITEPROCESS_WAIT");
                if (!TextUtils.isEmpty(waitPreload)) {
                    B = Boolean.valueOf(waitPreload).booleanValue();
                }
                String memoryThreshold = configService.getConfig("KEY_PRELOAD_MEMORY_THRESHOLD");
                if (!TextUtils.isEmpty(memoryThreshold)) {
                    C = Integer.valueOf(memoryThreshold).intValue();
                }
            } else {
                String litePipeline = configService.getConfig("KEY_LITE_PIPELINE");
                if (!TextUtils.isEmpty(litePipeline)) {
                    String[] split = litePipeline.split(",");
                    int length = split.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        a.add(split[i2].trim());
                    }
                }
                if ("test".equalsIgnoreCase(LoggerFactory.getLogContext().getReleaseType())) {
                    LoggerFactory.getTraceLogger().info(Const.TAG, "OTHER_PIPELINES add AutoTrackStartPipeline");
                    a.add("com.alipay.android.phone.performance.AutoTrackStartPipeline");
                }
                String needPreloadTinyService = configService.getConfig("KEY_NEED_PRELOAD_TINY_SERVICE");
                if (!TextUtils.isEmpty(needPreloadTinyService)) {
                    k = Boolean.valueOf(needPreloadTinyService).booleanValue();
                }
                String preloadTinyAppClass = configService.getConfig("KEY_PRELOAD_TINY_APP_CLASS");
                if (!TextUtils.isEmpty(preloadTinyAppClass)) {
                    n = Boolean.valueOf(preloadTinyAppClass).booleanValue();
                }
                String checkStopFromDelay = configService.getConfig("KEY_CHECK_STOP_FROM_DELAY");
                if (!TextUtils.isEmpty(checkStopFromDelay)) {
                    z = (long) Integer.valueOf(checkStopFromDelay).intValue();
                }
                String addIsLiteMoveTask2 = configService.getConfig("KEY_ADD_IS_LITE_MOVE_TASK");
                if (!TextUtils.isEmpty(addIsLiteMoveTask2)) {
                    A = Boolean.valueOf(addIsLiteMoveTask2).booleanValue();
                }
            }
        } catch (Throwable t2) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t2));
            a();
        }
        LoggerFactory.getTraceLogger().debug(Const.TAG, "NEED_LITE " + NEED_LITE + " NEED_PRELOAD = " + c + " MAX_PROCESS_NUM = " + b + " CAN_STOP_DURATION = " + d + " OTHER_PIPELINES = " + a + " TINY_APP_FAST_START = " + e + " MAX_RETRY_BIND_TIMES = " + MAX_RETRY_BIND_TIMES + " NEED_STOP_READY = " + f + " CAN_STOP_READY_DURATION = " + g + " NEED_PRELOAD_TINY_SERVICE = " + k + " STOP_PROCESS_WHEN_APP_TYPE_CHANGE = " + o + " FIRST_FOREGROUND_QUICK_PRELOAD = " + p + " CAN_FORCE_START_FROM_MAINUI = " + q + " CAN_CLEAR_TOP_APP_WHEN_RESTART = " + r + " NEED_SAFE_GETFROMBASEACTIVITY = " + u + " NEED_NOTIFY_SRV_SHOW = " + v + " CLI_ON_SRV_SHOW_DELAY = " + w + " NOTIFY_LOGOUT = " + x + " checkStopFromDelay = " + z + " addIsLiteMoveTask = " + A);
    }

    public static boolean needHookVerifyIdentity() {
        return a("KEY_HOOK_LITE_VERIFYIDENTITY");
    }

    public static boolean needHookPhoneCashier() {
        return a("KEY_HOOK_PHONECASHIER");
    }

    private static boolean a(String key) {
        try {
            String needHookKey = ((ConfigService) Util.getMicroAppContext().findServiceByInterface(ConfigService.class.getName())).getConfig(key);
            if (!TextUtils.isEmpty(needHookKey)) {
                return Boolean.valueOf(needHookKey).booleanValue();
            }
            return true;
        } catch (Throwable t2) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t2));
            return true;
        }
    }
}
