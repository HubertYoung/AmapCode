package com.alipay.mobile.liteprocess.perf;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.webkit.ValueCallback;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.ExecutionAdvice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.MTBizReportName;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.Performance.Builder;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.logging.util.ReflectUtil;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessClientManager;
import com.alipay.mobile.monitor.api.MonitorFactory;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class PerformanceLogger {
    private static Map<String, Boolean> A = new HashMap();
    public static final String TAG = "PerformanceLogger";
    static String a;
    static String b;
    static String c;
    static String d;
    static long e;
    static boolean f = false;
    static long g = 0;
    static long h = 0;
    static long i;
    static String j;
    private static volatile boolean k = false;
    private static Map<TrackType, Long> l = new HashMap();
    private static Boolean m;
    private static Boolean n;
    private static String o;
    private static AppType p = AppType.UNKNOWN;
    private static long q = -1;
    /* access modifiers changed from: private */
    public static boolean r = false;
    private static boolean s = false;
    private static boolean t = false;
    private static boolean u = false;
    private static boolean v = false;
    private static boolean w = false;
    private static HashMap<String, HashMap<String, Long>> x;
    private static String y;
    private static Random z = new Random();

    enum AppType {
        UNKNOWN("-1"),
        TINY_APP("1"),
        H5("2"),
        TINY_APP_RN("3");
        
        String a;

        private AppType(String value) {
            this.a = value;
        }
    }

    enum TrackType {
        STARTUP_OPEN,
        STARTUP_PREPARE,
        STARTUP_BEGIN,
        STARTUP_WINDOW_APPEAR,
        STARTUP_PAGE_START,
        STARTUP_PAGE_FINISH,
        STARTUP_PAGE_RENDER,
        STARTUP_DOM_READY,
        STARTUP_JS_FINISH,
        STARTUP_APP_LOADED,
        STARTUP_PAGE_LOADED,
        STARTUP_WORKER_FRAMEWORK_LOADED,
        STARTUP_PROCESS_LAUNCH_TIME,
        STARTUP_FOREGROUND_START_TIME,
        STARTUP_H5_RPC_TIME,
        PAGE_SWITCH_DOM_READY
    }

    public static void init(String appId) {
        try {
            c(appId);
        } catch (Throwable e2) {
            TraceLogger.e(TAG, "performance logger init error.", e2);
        }
    }

    private static void c(String appId) {
        p = AppType.TINY_APP;
        o = appId;
        l.clear();
        a = null;
        r = false;
        q = -1;
        h = 0;
        y = null;
        if (x != null && LoggerFactory.getProcessInfo().isMainProcess()) {
            x.clear();
        }
        track(TrackType.STARTUP_BEGIN);
        if (!k) {
            FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.BASEFRAGMENTACTIVITY_ONWINDOWFOCUSCHANGED, (Advice) new ExecutionAdvice() {
                public final void onExecutionBefore(String s, Object o, Object[] objects) {
                }

                public final Pair<Boolean, Object> onExecutionAround(String s, Object o, Object[] objects) {
                    if (objects != null && objects.length > 0 && (objects[0] instanceof Boolean)) {
                        Boolean hasFocus = objects[0];
                        Log.i(PerformanceLogger.TAG, "onFocus:" + hasFocus);
                        if (hasFocus.booleanValue() && !PerformanceLogger.r) {
                            PerformanceLogger.track(TrackType.STARTUP_WINDOW_APPEAR);
                            PerformanceLogger.r = true;
                        }
                    }
                    return null;
                }

                public final void onExecutionAfter(String s, Object o, Object[] objects) {
                }
            });
            k = true;
        }
    }

    public static String getCurrentAppId() {
        return o;
    }

    public static void setLocal(boolean local) {
        Log.i(TAG, "setLocal:" + local);
        m = Boolean.valueOf(local);
    }

    public static void onTinyAppProcessEvent(String process, String section) {
        onTinyAppProcessEvent(process, section, SystemClock.elapsedRealtime());
    }

    public static void onTinyAppProcessEvent(String process, String section, long time) {
        try {
            if (!TextUtils.isEmpty(process) && !TextUtils.isEmpty(section) && !TextUtils.isEmpty(o)) {
                Long begin = l.get(TrackType.STARTUP_BEGIN);
                if (begin == null || q != begin.longValue()) {
                    if (h == 0 && "H5PageImpl.loadUrl()".equals(section)) {
                        h = SystemClock.elapsedRealtime();
                    }
                    HashMap sectionList = d(process);
                    if ((!"2018030502317859".equalsIgnoreCase(o) || !"render_setData".equalsIgnoreCase(section) || sectionList.containsKey("mtop.tmall.tac.gateway.execute.end") || sectionList.containsKey("mtop.ju.data.get.end")) && !sectionList.containsKey(section)) {
                        sectionList.put(section, Long.valueOf(time));
                    }
                }
            }
        } catch (Throwable thr) {
            LoggerFactory.getTraceLogger().warn((String) "onTinyAppProcessEvent error!", thr);
        }
    }

    private static HashMap<String, Long> d(String process) {
        if (x == null) {
            x = new HashMap<>();
        }
        HashMap sectionList = x.get(process);
        if (sectionList != null) {
            return sectionList;
        }
        HashMap sectionList2 = new HashMap();
        x.put(process, sectionList2);
        return sectionList2;
    }

    public static void onPackagePrelodResult(boolean isPreloadCompleted, boolean isPreloadOnTarget) {
        u = isPreloadCompleted;
        v = isPreloadOnTarget;
    }

    public static void setH5WebviewVersion(String version) {
        d = version;
    }

    public static void setPreload(boolean preload) {
        n = Boolean.valueOf(preload);
    }

    public static void setPrepareTime(long time) {
        l.put(TrackType.STARTUP_PREPARE, Long.valueOf(time));
        i = SystemClock.elapsedRealtime() - time;
        onTinyAppProcessEvent("main", "LiteProcessServerManager.startApp()", time);
    }

    public static void setUcInitAbandoned() {
        s = true;
    }

    public static void setUcReInitSuccess() {
        t = true;
    }

    public static void setPreloadCompleted() {
        f = true;
        g = SystemClock.elapsedRealtime() - MonitorFactory.getTimestampInfo().getProcessCurrentLaunchElapsedTime();
    }

    public static void setRpcTime(long time) {
        l.put(TrackType.STARTUP_H5_RPC_TIME, Long.valueOf(time));
    }

    public static void setTimeFromLaunch(long time) {
        e = time;
    }

    public static void setIsPreloadWait(boolean isWait) {
        w = isWait;
    }

    public static void setForegroundStartTime(long time) {
        l.put(TrackType.STARTUP_FOREGROUND_START_TIME, Long.valueOf(time));
    }

    public static void setPreloadFrom(String preloadFrom) {
        c = preloadFrom;
    }

    public static void setChInfo(String chInfo) {
        b = chInfo;
    }

    public static void setOpenAppTime(long time) {
        l.put(TrackType.STARTUP_OPEN, Long.valueOf(time));
    }

    public static void setStartupQuery(String query) {
        j = query;
    }

    public static void track(TrackType type) {
        track(type, SystemClock.elapsedRealtime());
    }

    public static void track(TrackType type, long timeStampOrLoadTime) {
        if (type == TrackType.PAGE_SWITCH_DOM_READY || !l.containsKey(type)) {
            Log.i(TAG, String.format("track %s", new Object[]{type}));
            l.put(type, Long.valueOf(timeStampOrLoadTime));
        }
    }

    public static void logStartup(H5Page h5Page) {
        Long begin = l.get(TrackType.STARTUP_BEGIN);
        if (begin == null || q != begin.longValue()) {
            if (h5Page != null) {
                try {
                    if (h5Page.getWebView() != null) {
                        a(h5Page);
                        return;
                    }
                } catch (Throwable thr) {
                    Log.e(TAG, "doLogStartup error!", thr);
                    return;
                }
            }
            e(null);
            return;
        }
        Log.i(TAG, "last start up equal, duplicate.");
    }

    private static void a(H5Page h5Page) {
        try {
            LoggerFactory.getTraceLogger().debug(TAG, "retrieveUcData");
            View contentView = h5Page.getContentView();
            if (contentView.getClass().getName().startsWith("com.alipay.mobile.nebulauc")) {
                Method getExtensionMethod = contentView.getClass().getMethod("getUCExtension", new Class[0]);
                getExtensionMethod.setAccessible(true);
                Object extensions = getExtensionMethod.invoke(contentView, new Object[0]);
                ValueCallback callback = new ValueCallback<String>() {
                    public final void onReceiveValue(String value) {
                        PerformanceLogger.e(value);
                    }
                };
                extensions.getClass().getDeclaredMethod("getStartupPerformanceStatistics", new Class[]{ValueCallback.class}).invoke(extensions, new Object[]{callback});
                return;
            }
            e(null);
        } catch (Throwable thr) {
            e(null);
            LoggerFactory.getTraceLogger().warn(TAG, "retrieveUcData error!", thr);
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void e(String ucData) {
        Long domReady;
        synchronized (PerformanceLogger.class) {
            LoggerFactory.getTraceLogger().debug(TAG, "doLogStartup");
            Long begin = l.get(TrackType.STARTUP_BEGIN);
            if (begin == null || q != begin.longValue()) {
                Long prepareBegin = l.get(TrackType.STARTUP_PREPARE);
                Long appear = l.get(TrackType.STARTUP_WINDOW_APPEAR);
                Long pageStart = l.get(TrackType.STARTUP_PAGE_START);
                Long pageFinish = l.get(TrackType.STARTUP_PAGE_FINISH);
                Long pageRender = l.get(TrackType.STARTUP_PAGE_RENDER);
                Long appLoaded = l.get(TrackType.STARTUP_APP_LOADED);
                Long pageLoaded = l.get(TrackType.STARTUP_PAGE_LOADED);
                Long workerLoaded = l.get(TrackType.STARTUP_WORKER_FRAMEWORK_LOADED);
                Long readyProcessLaunched = l.get(TrackType.STARTUP_PROCESS_LAUNCH_TIME);
                Long foregroundStartTime = l.get(TrackType.STARTUP_FOREGROUND_START_TIME);
                Long h5RpcTime = l.get(TrackType.STARTUP_H5_RPC_TIME);
                if (begin == null || appear == null || pageStart == null || pageFinish == null) {
                    LoggerFactory.getTraceLogger().warn(TAG, "log time is null when logStartup!!begin:" + begin + " appear: " + appear + " pageStart:" + pageStart + " pageFinish:" + pageFinish);
                    Log.w(TAG, String.format("log time is null when logStartup!!, begin=%s, appear=%spageStart=%s, pageFinish=%s, currentPage=%s", new Object[]{begin, appear, pageStart, pageFinish, a}));
                } else {
                    if (p == AppType.TINY_APP && prepareBegin == null) {
                        Log.w(TAG, "prepareBegin time is null in tiny app!!");
                        LoggerFactory.getTraceLogger().info(TAG, "logStartup#prepareBegin time is null in tiny app!");
                    } else {
                        if (p == AppType.H5 && prepareBegin == null) {
                            prepareBegin = begin;
                        }
                        if (pageRender == null) {
                            LoggerFactory.getTraceLogger().warn(TAG, (String) "page Render does not callback!");
                            pageRender = pageFinish;
                        }
                        q = begin.longValue();
                        Long domReady2 = l.get(TrackType.STARTUP_DOM_READY);
                        if (domReady2 != null) {
                            LoggerFactory.getTraceLogger().debug(TAG, "launch cost has domReady info, domReady:" + domReady2 + ", pageRender:" + pageRender);
                            domReady = Long.valueOf(Math.max(domReady2.longValue(), pageRender.longValue()));
                        } else {
                            domReady = pageRender;
                        }
                        Long openAppTime = l.get(TrackType.STARTUP_OPEN);
                        if (openAppTime == null) {
                            openAppTime = prepareBegin;
                        }
                        Map ext = new HashMap();
                        ext.put("app_id", o);
                        ext.put("ch_info", b);
                        ext.put("preload_from", c);
                        r0 = "preload_completed";
                        ext.put("preload_completed", f ? "1" : "0");
                        if (g != 0) {
                            ext.put("preload_cost", String.valueOf(g));
                        }
                        ext.put("h5_webview_version", d);
                        r0 = "ucinit_abandoned";
                        ext.put("ucinit_abandoned", s ? "1" : "0");
                        r0 = "uc_reInit_success";
                        ext.put("uc_reInit_success", t ? "1" : "0");
                        ext.put("prepare_cost", String.valueOf(prepareBegin.longValue() - openAppTime.longValue()));
                        ext.put("load_cost", String.valueOf(pageStart.longValue() - prepareBegin.longValue()));
                        ext.put("appear_cost", String.valueOf(appear.longValue() - openAppTime.longValue()));
                        ext.put("loadcomplete_cost", String.valueOf(pageFinish.longValue() - prepareBegin.longValue()));
                        ext.put("render_cost", String.valueOf(pageRender.longValue() - prepareBegin.longValue()));
                        ext.put("from_app_start", String.valueOf(e));
                        ext.put("launch_cost", String.valueOf(Math.max(Math.max(domReady.longValue(), pageFinish.longValue()), pageLoaded == null ? 0 : pageLoaded.longValue()) - openAppTime.longValue()));
                        ext.put(H5AppUtil.app_type, p.a);
                        String str = (n == null || !n.booleanValue()) ? "0" : "1";
                        r0 = Const.PERF_IS_PRELOAD;
                        ext.put(Const.PERF_IS_PRELOAD, str);
                        r0 = "is_local";
                        ext.put("is_local", (m == null || !m.booleanValue()) ? "0" : "1");
                        r0 = "url";
                        ext.put("url", a == null ? "null" : URLEncoder.encode(a));
                        ext.put("ipc_cost", String.valueOf(i));
                        r0 = "is_preload_wait";
                        ext.put("is_preload_wait", w ? "1" : "0");
                        if (!(appLoaded == null || appLoaded.longValue() == 0)) {
                            ext.put("app_loaded_cost", String.valueOf(appLoaded.longValue() - openAppTime.longValue()));
                        }
                        Long jsFinish = l.get(TrackType.STARTUP_JS_FINISH);
                        if (jsFinish != null) {
                            ext.put("js_launch_cost", String.valueOf(jsFinish.longValue() - openAppTime.longValue()));
                        }
                        if (!(pageLoaded == null || pageLoaded.longValue() == 0)) {
                            ext.put("page_loaded_cost", String.valueOf(pageLoaded.longValue() - openAppTime.longValue()));
                            ext.put("app_first_page", "firstPage");
                        }
                        if (!(workerLoaded == null || workerLoaded.longValue() == 0)) {
                            ext.put("worker_framework_loaded_cost", String.valueOf(workerLoaded));
                        }
                        if (!(readyProcessLaunched == null || readyProcessLaunched.longValue() == 0)) {
                            ext.put("ready_process_launched", String.valueOf(readyProcessLaunched));
                        }
                        if (!(foregroundStartTime == null || foregroundStartTime.longValue() == 0)) {
                            ext.put("foreground_start_time", String.valueOf(foregroundStartTime));
                        }
                        if (h5RpcTime != null) {
                            ext.put("h5_rpc_time", String.valueOf(h5RpcTime));
                        }
                        if (h != 0) {
                            ext.put("before_load_url", String.valueOf(h - openAppTime.longValue()));
                        }
                        if (u) {
                            ext.put("package_preloaded", "1");
                            r0 = "package_on_target";
                            ext.put("package_on_target", v ? "1" : "0");
                        }
                        if (!TextUtils.isEmpty(y)) {
                            try {
                                r0 = "map_type";
                                ext.put("map_type", y);
                                ext.put("map_load_cost", String.valueOf(Math.max(Long.valueOf(d("main").get("map_loaded").longValue()).longValue(), Long.valueOf(d("main").get("map_data_ready").longValue()).longValue()) - pageLoaded.longValue()));
                            } catch (Throwable thr) {
                                LoggerFactory.getTraceLogger().warn(TAG, "process map data error!", thr);
                            }
                        }
                        if (ucData != null) {
                            try {
                                String[] keyValues = ucData.split(";");
                                HashMap dataMap = new HashMap();
                                int length = keyValues.length;
                                for (int i2 = 0; i2 < length; i2++) {
                                    String keyValue = keyValues[i2];
                                    if (!TextUtils.isEmpty(keyValue)) {
                                        String[] keyValueArray = keyValue.split(":");
                                        dataMap.put(keyValueArray[0], keyValueArray[1]);
                                    }
                                }
                                long ucSetupTime = Long.valueOf((String) dataMap.get("as9")).longValue();
                                HashMap<String, Long> d2 = d("main");
                                if (dataMap.containsKey("d1")) {
                                    d2.put("sw.init.start", Long.valueOf(Long.valueOf((String) dataMap.get("d1")).longValue() + ucSetupTime));
                                }
                                if (dataMap.containsKey("d4")) {
                                    d2.put("sw.init.end", Long.valueOf(Long.valueOf((String) dataMap.get("d4")).longValue() + ucSetupTime));
                                }
                                long loadUrlElapsedRealTime = (Long.valueOf((String) dataMap.get("e0")).longValue() - Long.valueOf((String) dataMap.get("as0")).longValue()) + ucSetupTime;
                                if (dataMap.containsKey("e3")) {
                                    d2.put("render.layout", Long.valueOf(Long.valueOf((String) dataMap.get("e3")).longValue() + loadUrlElapsedRealTime));
                                }
                                if (dataMap.containsKey("e2")) {
                                    long firstScreen = loadUrlElapsedRealTime + Long.valueOf((String) dataMap.get("e2")).longValue();
                                    if (!(pageLoaded == null || pageLoaded.longValue() == 0)) {
                                        ext.put("first_screen", String.valueOf(firstScreen - pageLoaded.longValue()));
                                    }
                                    d2.put("render.first.frame", Long.valueOf(firstScreen));
                                }
                                if (dataMap.containsKey("e5")) {
                                    d2.put("render.first.screen", Long.valueOf(Long.valueOf((String) dataMap.get("e5")).longValue() + loadUrlElapsedRealTime));
                                }
                                if (!(pageLoaded == null || pageLoaded.longValue() == 0)) {
                                    d2.put("pageLoaded", pageLoaded);
                                }
                                ext.put("uc_data", ucData);
                            } catch (Throwable throwable) {
                                LoggerFactory.getTraceLogger().warn(TAG, "process uc data error!", throwable);
                            }
                        }
                        if (x != null) {
                            StringBuilder processInfo = new StringBuilder();
                            Set keySet = x.keySet();
                            if (keySet != null) {
                                for (String key : keySet) {
                                    if (processInfo.length() != 0) {
                                        processInfo.append(MergeUtil.SEPARATOR_KV);
                                    }
                                    HashMap sectionList = x.get(key);
                                    processInfo.append(key).append("##");
                                    if (sectionList != null) {
                                        Set<String> childKeySet = sectionList.keySet();
                                        if (childKeySet != null) {
                                            for (String childKey : childKeySet) {
                                                processInfo.append(childKey).append("$").append(sectionList.get(childKey)).append("%");
                                            }
                                            processInfo.deleteCharAt(processInfo.length() - 1);
                                        }
                                    }
                                }
                                x.clear();
                            }
                            ext.put("detail_info", processInfo.toString());
                        }
                        if (j != null) {
                            try {
                                ext.put("launcher_create", String.valueOf(((Long) ReflectUtil.getField("com.alipay.mobile.quinox.LauncherActivity", "sLastCreateTime", null)).longValue()));
                                ext.put("startup_query", URLEncoder.encode(j));
                                ext.put("process_launch", String.valueOf(MonitorFactory.getTimestampInfo().getProcessCurrentLaunchElapsedTime()));
                                ext.put("open_app_time", String.valueOf(openAppTime));
                            } catch (Throwable thr2) {
                                LoggerFactory.getTraceLogger().error(TAG, "get launcher activity create time error!", thr2);
                            }
                        }
                        b("tiny_app_launch", ext);
                    }
                }
            } else {
                Log.i(TAG, "last start up equal, duplicate.");
            }
        }
    }

    public static void logRenderFrameworkLoaded(int loadTime) {
        Map ext = new HashMap();
        ext.put("app_id", o);
        ext.put(H5AppUtil.app_type, p.a);
        ext.put("cost", String.valueOf(loadTime));
        a("tiny_app_render_framework_loaded", ext);
    }

    public static void logSaoyisaoStartAppPerf(String targetApp, long timeCost, boolean isPreloadPerformed) {
        Map ext = new HashMap();
        ext.put("target_app", targetApp);
        ext.put("time_cost", String.valueOf(timeCost));
        ext.put("preload_performed", isPreloadPerformed ? "1" : "0");
        a("saoyisao_start_app_perf", ext);
        LoggerFactory.getTraceLogger().debug(TAG, "logSaoyisaoStartAppPerf targetApp = " + targetApp + ", timeCost  = " + timeCost + ", isPreloadPerformed = " + isPreloadPerformed);
    }

    public static void logH5PreloadException(String exception) {
        Map ext = new HashMap();
        ext.put("exception_type", exception);
        a("h5_preload_exception", ext);
    }

    public static void logPageSwitch(int loadTime, String page, boolean isMainPage) {
        try {
            if (p != AppType.H5 || z.nextDouble() <= 0.01d) {
                H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
                Map ext = new HashMap();
                ext.put("app_id", o);
                ext.put("main_page", isMainPage ? "1" : "0");
                ext.put(H5AppUtil.app_type, p.a);
                ext.put("to_url", URLEncoder.encode(page));
                ext.put("url", h5Service.getTopH5Page().getUrl());
                if (isMainPage) {
                    Long openAppTime = l.get(TrackType.STARTUP_OPEN);
                    if (openAppTime != null) {
                        loadTime = (int) (SystemClock.elapsedRealtime() - openAppTime.longValue());
                    }
                }
                ext.put("cost", String.valueOf(loadTime));
                a("tiny_app_page_switch", ext);
                return;
            }
            LoggerFactory.getTraceLogger().warn(TAG, (String) "logPageSwitch, but do not satisfy the sampling.");
        } catch (Throwable thr) {
            LoggerFactory.getTraceLogger().warn(TAG, "logPageSwitch", thr);
        }
    }

    public static void logMainProcessDied() {
        if (o != null && !ActivityHelper.isBackgroundRunning()) {
            Map ext = new HashMap();
            ext.put("app_id", o);
            ext.put(H5AppUtil.app_type, p.a);
            a("tiny_app_main_process_died", ext);
        }
    }

    public static void logStopShowingLiteProcess() {
    }

    public static Map<String, String> getPerformanceData() {
        HashMap data = new HashMap();
        Long openAppTime = l.get(TrackType.STARTUP_OPEN);
        if (openAppTime != null) {
            data.put("open_app_time", String.valueOf(openAppTime));
        }
        data.put("preload_complete_cost", String.valueOf(g));
        return data;
    }

    public static void logSaoyisaoMonitorResult(int taskNum30, int taskNum100, boolean isMonitorForPreload, boolean isPreloadPerformed, long monitorTime, long processLaunchedTime) {
        Map ext = new HashMap();
        ext.put("tasks_30_to_100", String.valueOf(taskNum30));
        ext.put("tasks_100_to_more", String.valueOf(taskNum100));
        ext.put("monitor_for_preload", isMonitorForPreload ? "1" : "0");
        ext.put("preload_performed", isPreloadPerformed ? "1" : "0");
        ext.put("stay_5s_more", String.valueOf(monitorTime));
        ext.put("process_launched_time", String.valueOf(processLaunchedTime));
        a("saoyisao_monitor_result", ext);
        LoggerFactory.getTraceLogger().info(TAG, "logSaoyisaoMonitorResult taskNum30 = " + taskNum30 + ", taskNum100 = " + taskNum100 + ", isMonitorForPreload = " + isMonitorForPreload + ", isPreloadPerformed = " + isPreloadPerformed + ", monitorTime = " + monitorTime + ", processLaunchedTime = " + processLaunchedTime);
    }

    public static void logAMapPerf(boolean is3d, long mapStart, long mapLoaded, long dataStart, long dataReady) {
        onTinyAppProcessEvent("main", "map_start", mapStart);
        onTinyAppProcessEvent("main", "map_loaded", mapLoaded);
        onTinyAppProcessEvent("main", "map_data_start", dataStart);
        onTinyAppProcessEvent("main", "map_data_ready", dataReady);
        y = is3d ? "3d" : "2d";
        LoggerFactory.getTraceLogger().debug(TAG, "logAMapPerf mapCost = " + (mapLoaded - mapStart) + ", dataCost  = " + (dataReady - dataStart));
    }

    private static void a(String subType, Map<String, String> ext) {
        a(subType, ext, -1);
    }

    private static void b(String subType, Map<String, String> ext) {
        a(subType, ext, 1);
    }

    private static void a(String subType, Map<String, String> extParams, int logLevel) {
        Builder builder = new Builder();
        builder.setSubType(subType);
        if (extParams != null) {
            for (Entry stringStringEntry : extParams.entrySet()) {
                builder.addExtParam((String) stringStringEntry.getKey(), (String) stringStringEntry.getValue());
            }
        }
        builder.addExtParam("running_process", LoggerFactory.getProcessInfo().getProcessAlias());
        Log.i(TAG, String.format("log#%s, %s", new Object[]{subType, extParams}));
        Performance performance = builder.build();
        if (logLevel > 0) {
            performance.setLoggerLevel(logLevel);
        }
        LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_PERFORMANCE, performance);
    }

    public static void recordAppStart(final String appId) {
        if (!A.containsKey(appId) || !A.get(appId).booleanValue()) {
            try {
                track(TrackType.STARTUP_PROCESS_LAUNCH_TIME, SystemClock.elapsedRealtime() - MonitorFactory.getTimestampInfo().getProcessCurrentLaunchElapsedTime());
            } catch (Throwable thr) {
                LoggerFactory.getTraceLogger().warn(TAG, "init ready process launch time error!", thr);
            }
            Handler asyncHandler = LiteProcessClientManager.getAsyncHandler();
            if (asyncHandler != null) {
                asyncHandler.removeCallbacksAndMessages(appId);
                A.put(appId, Boolean.valueOf(false));
                Message msg = Message.obtain(asyncHandler, new Runnable() {
                    public final void run() {
                        try {
                            PerformanceLogger.f(appId);
                        } catch (Throwable t) {
                            LoggerFactory.getTraceLogger().error(PerformanceLogger.TAG, Log.getStackTraceString(t));
                        }
                    }
                });
                msg.obj = appId;
                asyncHandler.sendMessageDelayed(msg, StatisticConfig.MIN_UPLOAD_INTERVAL);
                TraceLogger.i(TAG, "record app start:" + appId);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void f(String appId) {
        if (!A.containsKey(appId)) {
            LoggerFactory.getTraceLogger().info(TAG, "check lite app start fail, but no startup time.");
            return;
        }
        boolean started = A.get(appId).booleanValue();
        A.remove(appId);
        TraceLogger.i(TAG, "check app:" + appId + " started, " + started);
        if (!started) {
            c();
            LoggerFactory.getLogContext().flush("applog", false);
            Map params = new HashMap();
            params.put("appId", appId);
            params.put("running_process", LoggerFactory.getProcessInfo().getProcessAlias());
            H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
            if (h5AppProvider != null) {
                AppInfo appInfo = h5AppProvider.getAppInfo(appId);
                if (appInfo != null) {
                    params.put("appUniqueId", appId + "_" + appInfo.version + "_" + appInfo.release_type);
                }
            } else {
                LoggerFactory.getTraceLogger().warn(TAG, (String) "can not get H5AppProvider when get appUinqueId");
            }
            LoggerFactory.getMonitorLogger().mtBizReport(MTBizReportName.MTBIZ_FRAME, MTBizReportName.FRAME_MICROAPP_STARTUP_FAIL, FrameworkMonitor.MICROAPP_STARTUP_FAIL_TINYAPP_FAIL, params);
        }
    }

    public static void recordAppStarted(String appId) {
        boolean z2 = true;
        LoggerFactory.getTraceLogger().info(TAG, "record app started:" + appId);
        A.put(appId, Boolean.valueOf(true));
        com.alipay.mobile.common.logging.api.trace.TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        String str = TAG;
        StringBuilder sb = new StringBuilder("recordAppStarted success ? ");
        if (o == null || !o.equals(appId)) {
            z2 = false;
        }
        traceLogger.error(str, sb.append(z2).append(" currentAppId = ").append(o).append(", appId = ").append(appId).toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ac A[SYNTHETIC, Splitter:B:29:0x00ac] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b5 A[SYNTHETIC, Splitter:B:34:0x00b5] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:19:0x0081=Splitter:B:19:0x0081, B:36:0x00b8=Splitter:B:36:0x00b8} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void c() {
        /*
            java.lang.Class<com.alipay.mobile.liteprocess.perf.PerformanceLogger> r10 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.class
            monitor-enter(r10)
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00b9 }
            java.lang.String r11 = TAG     // Catch:{ all -> 0x00b9 }
            java.lang.String r12 = "dumpLogAllLines, start logcatDump"
            r9.info(r11, r12)     // Catch:{ all -> 0x00b9 }
            java.lang.String r4 = "dumpLogcatForTinyApp"
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x00b9 }
            com.alipay.mobile.framework.LauncherApplicationAgent r9 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ all -> 0x00b9 }
            android.app.Application r9 = r9.getApplicationContext()     // Catch:{ all -> 0x00b9 }
            java.io.File r9 = r9.getCacheDir()     // Catch:{ all -> 0x00b9 }
            r3.<init>(r9, r4)     // Catch:{ all -> 0x00b9 }
            boolean r9 = r3.exists()     // Catch:{ all -> 0x00b9 }
            if (r9 == 0) goto L_0x002a
            r3.delete()     // Catch:{ all -> 0x00b9 }
        L_0x002a:
            r9 = 3000(0xbb8, float:4.204E-42)
            com.alipay.mobile.common.logging.util.LogcatUtil.dumpLogcat(r3, r9)     // Catch:{ all -> 0x00b9 }
            r0 = 0
            r5 = 0
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x009e }
            java.io.FileReader r9 = new java.io.FileReader     // Catch:{ Throwable -> 0x009e }
            r9.<init>(r3)     // Catch:{ Throwable -> 0x009e }
            r6.<init>(r9)     // Catch:{ Throwable -> 0x009e }
            com.alipay.mobile.common.logging.api.LogEvent r7 = new com.alipay.mobile.common.logging.api.LogEvent     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            java.lang.String r9 = "applog"
            java.lang.String r11 = ""
            com.alipay.mobile.common.logging.api.LogEvent$Level r12 = com.alipay.mobile.common.logging.api.LogEvent.Level.INFO     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            java.lang.String r13 = "dumpLogcatForTinyApp start**********\n"
            r7.<init>(r9, r11, r12, r13)     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            com.alipay.mobile.common.logging.api.LogContext r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            r9.appendLogEvent(r7)     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
        L_0x004f:
            java.lang.String r1 = r6.readLine()     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            if (r1 == 0) goto L_0x007d
            com.alipay.mobile.common.logging.api.LogEvent r2 = new com.alipay.mobile.common.logging.api.LogEvent     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            java.lang.String r9 = "applog"
            java.lang.String r11 = ""
            com.alipay.mobile.common.logging.api.LogEvent$Level r12 = com.alipay.mobile.common.logging.api.LogEvent.Level.INFO     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            r13.<init>()     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            java.lang.StringBuilder r13 = r13.append(r1)     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            java.lang.String r14 = "\n"
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            java.lang.String r13 = r13.toString()     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            r2.<init>(r9, r11, r12, r13)     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            com.alipay.mobile.common.logging.api.LogContext r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            r9.appendLogEvent(r2)     // Catch:{ Throwable -> 0x00c1, all -> 0x00be }
            int r0 = r0 + 1
            goto L_0x004f
        L_0x007d:
            r6.close()     // Catch:{ Throwable -> 0x009b }
            r5 = r6
        L_0x0081:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00b9 }
            java.lang.String r11 = TAG     // Catch:{ all -> 0x00b9 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b9 }
            java.lang.String r13 = "dumpLogcatForTinyApp, end logcatDump, count="
            r12.<init>(r13)     // Catch:{ all -> 0x00b9 }
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ all -> 0x00b9 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x00b9 }
            r9.info(r11, r12)     // Catch:{ all -> 0x00b9 }
            monitor-exit(r10)
            return
        L_0x009b:
            r9 = move-exception
            r5 = r6
            goto L_0x0081
        L_0x009e:
            r8 = move-exception
        L_0x009f:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00b2 }
            java.lang.String r11 = TAG     // Catch:{ all -> 0x00b2 }
            java.lang.String r12 = "dumpLogAllLines"
            r9.error(r11, r12, r8)     // Catch:{ all -> 0x00b2 }
            if (r5 == 0) goto L_0x0081
            r5.close()     // Catch:{ Throwable -> 0x00b0 }
            goto L_0x0081
        L_0x00b0:
            r9 = move-exception
            goto L_0x0081
        L_0x00b2:
            r9 = move-exception
        L_0x00b3:
            if (r5 == 0) goto L_0x00b8
            r5.close()     // Catch:{ Throwable -> 0x00bc }
        L_0x00b8:
            throw r9     // Catch:{ all -> 0x00b9 }
        L_0x00b9:
            r9 = move-exception
            monitor-exit(r10)
            throw r9
        L_0x00bc:
            r11 = move-exception
            goto L_0x00b8
        L_0x00be:
            r9 = move-exception
            r5 = r6
            goto L_0x00b3
        L_0x00c1:
            r8 = move-exception
            r5 = r6
            goto L_0x009f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.liteprocess.perf.PerformanceLogger.c():void");
    }

    public static void test(String targetAppId) {
        Random r2 = new Random();
        Map startupMap = new HashMap();
        startupMap.put("app_id", targetAppId);
        startupMap.put("load_cost", String.valueOf(r2.nextInt(3000)));
        startupMap.put("appear_cost", String.valueOf(r2.nextInt(2000)));
        startupMap.put("launch_cost", String.valueOf(r2.nextInt(2600)));
        startupMap.put(Const.PERF_IS_PRELOAD, r2.nextBoolean() ? "1" : "0");
        startupMap.put("is_local", r2.nextBoolean() ? "1" : "0");
        a("tiny_app_launch", startupMap);
        Map pageSwitchMap = new HashMap();
        pageSwitchMap.put("app_id", targetAppId);
        pageSwitchMap.put("cost", String.valueOf(new Random().nextInt(6000)));
        a("tiny_app_page_switch", pageSwitchMap);
        Map ext = new HashMap();
        ext.put("app_id", targetAppId);
        ext.put("url", URLEncoder.encode(String.format("http://%s.tinyapp.alipay.com", new Object[]{Integer.valueOf(r2.nextInt(20000000))})));
        ext.put("resource_count", String.valueOf(r2.nextInt(10)));
        ext.put("average", String.valueOf(r2.nextInt(500)));
        ext.put("page_load_cost", String.valueOf(r2.nextInt(1000)));
        a("tiny_app_page_resource", ext);
        Map params = new HashMap();
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        params.put("stackFrame", traceElements[r2.nextInt(traceElements.length)].toString());
        params.put("lagActivity", "lagActivity");
        params.put("isTinyApp", r2.nextBoolean() ? "1" : "0");
        params.put("lagAppId", targetAppId);
        params.put("lagTime", String.valueOf(r2.nextInt(6000)));
        LoggerFactory.getMonitorLogger().apm(AjxDebugConfig.PERFORMANCE, "lag", null, params);
        Map fluencyMap = new HashMap();
        fluencyMap.put("TINYAPP_SMOOTHNESS_SCORE", String.valueOf(r2.nextInt(50) + 50));
        fluencyMap.put("TINYAPP_SMOOTHNESS_APPID", targetAppId);
        a("TrafficPowerReport", fluencyMap);
        LoggerFactory.getLogContext().flush(LogCategory.CATEGORY_PERFORMANCE, false);
        LoggerFactory.getLogContext().upload(LogCategory.CATEGORY_PERFORMANCE);
        LoggerFactory.getLogContext().flush(LogCategory.CATEGORY_APM, false);
        LoggerFactory.getLogContext().upload(LogCategory.CATEGORY_APM);
    }
}
