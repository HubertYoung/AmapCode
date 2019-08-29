package com.alipay.mobile.liteprocess;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.liteprocess.advice.SaoyisaoStartAdvice;
import com.alipay.mobile.liteprocess.ipc.IClientService.Stub;
import com.alipay.mobile.liteprocess.ipc.IpcCallServer;
import com.alipay.mobile.liteprocess.ipc.IpcMsgServer;
import com.alipay.mobile.liteprocess.perf.PerformanceLogger;
import com.alipay.mobile.liteprocess.rpc.RpcCall;
import com.alipay.mobile.liteprocess.rpc.RpcCallServerImpl;
import com.alipay.mobile.monitor.api.MonitorFactory;
import com.alipay.mobile.monitor.fulllink.FullLinkApi;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

public class LiteProcessServerManager {
    static boolean a = false;
    private static LinkedList<LiteProcess> b = new LinkedList<>();
    private static LiteProcess c;
    private static SparseArray<Class> d = new SparseArray<>();
    private static SparseArray<Class> e = new SparseArray<>();
    private static SparseArray<Runnable> f = new SparseArray<>();
    private static Handler g;
    private static boolean h = true;
    private static boolean i = false;
    /* access modifiers changed from: private */
    public static boolean j = false;
    private Set<String> k;
    private int l;
    private Runnable m;

    class LiteProcessServerHandler extends Handler {
        LiteProcessServerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    LiteProcessServerManager.this.a(msg.arg1, msg.arg2);
                    return;
                case 12:
                    LiteProcessServerManager.this.b(msg.arg1, msg.arg2);
                    return;
                case 15:
                    LiteProcessServerManager.this.a(msg.arg1);
                    return;
                case 18:
                    LiteProcessServerManager.this.c(msg.arg1, msg.arg2);
                    return;
                case 21:
                    LiteProcessServerManager.this.b(msg.getData());
                    return;
                default:
                    return;
            }
        }
    }

    class SingletonHolder {
        /* access modifiers changed from: private */
        public static LiteProcessServerManager a = new LiteProcessServerManager(0);

        private SingletonHolder() {
        }
    }

    /* synthetic */ LiteProcessServerManager(byte b2) {
        this();
    }

    private LiteProcessServerManager() {
        this.k = new ConcurrentSkipListSet();
        this.l = 0;
        this.m = null;
        h();
    }

    public static final LiteProcessServerManager g() {
        try {
            return SingletonHolder.a;
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
            SingletonHolder.a = new LiteProcessServerManager();
            return SingletonHolder.a;
        }
    }

    private synchronized void h() {
        if (Util.needSupportLiteProcess()) {
            if (!Util.isMainProcess()) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "LiteProcessServerManager must be in main process. " + Log.getStackTraceString(new Throwable()));
            } else {
                for (int i2 = 1; i2 <= Config.b; i2++) {
                    LiteProcess liteProcess = new LiteProcess();
                    liteProcess.a();
                    liteProcess.b = i2;
                    b.add(liteProcess);
                    d.put(i2, LiteProcessService.a[i2 - 1]);
                    e.put(i2, LiteProcessActivity.ACTIVITY_CLASSES[i2 - 1]);
                    a(LiteProcessActivity.ACTIVITY_CLASSES[i2 - 1], i2);
                }
                HandlerThread handlerThread = new HandlerThread("LiteProcessServerManager");
                handlerThread.start();
                g = new LiteProcessServerHandler(handlerThread.getLooper());
                IpcMsgServer.registerReqBizHandler(Const.TAG, g);
                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager init");
            }
        }
    }

    public synchronized void startAppAsync(final String sourceAppId, final String targetAppId, final Bundle params) {
        LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager startAppAsync " + targetAppId);
        g.post(new Runnable() {
            public void run() {
                LiteProcess liteProcess = LiteProcessServerManager.this.b(targetAppId);
                if (Config.o && liteProcess != null && !TextUtils.isEmpty(liteProcess.q) && !liteProcess.q.equals(params.getString(Const.APP_TYPE))) {
                    LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "App Type change and stop first!");
                    LiteProcessServerManager.this.b(liteProcess);
                }
                LiteProcessServerManager.this.a(sourceAppId, targetAppId, params);
            }
        });
    }

    /* access modifiers changed from: private */
    public synchronized void a(String sourceAppId, String targetAppId, Bundle params) {
        if (this.k.contains(targetAppId)) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "STARTING_APPID contains " + targetAppId);
        } else {
            boolean updateFromActivity = true;
            boolean forceStartFromMainUi = params.getBoolean("FORCE_START_LITE_APP_FROM_MAIN_UI", false);
            String fromTinyAppid = params.getString("FROM_TINY_APP_ID", "");
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, "startApp forceStartFromMainUi: " + forceStartFromMainUi + " FROM_TINY_APP_ID: " + fromTinyAppid + " CAN_FORCE_START_FROM_MAINUI: " + Config.q);
            boolean forceStartFromMainUi2 = Config.q && forceStartFromMainUi;
            LiteProcess fromLiteProcess = forceStartFromMainUi2 ? null : !TextUtils.isEmpty(fromTinyAppid) ? b(fromTinyAppid) : k();
            if (fromLiteProcess != null) {
                if (targetAppId.equals(fromLiteProcess.e)) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, "startApp self cycle fromLiteProcess: " + fromLiteProcess + " targetAppId: " + targetAppId);
                }
                if (targetAppId.equals(fromLiteProcess.l)) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "startApp fromLiteProcess cycle fromLiteProcess: " + fromLiteProcess + " targetAppId: " + targetAppId);
                    updateFromActivity = false;
                } else {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "startApp fromLiteProcess.canStop = false fromLiteProcess: " + fromLiteProcess);
                    fromLiteProcess.k = false;
                    fromLiteProcess.n = false;
                }
            } else {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "startApp fromLiteProcess is null");
            }
            if (!a(sourceAppId, targetAppId, params, updateFromActivity, forceStartFromMainUi2, fromLiteProcess)) {
                params.putBoolean(Const.PERF_IS_PRELOAD, c != null);
                params.putLong("perf_prepare_time", SystemClock.elapsedRealtime());
                params.putLong("perf_foreground_start_time", SystemClock.elapsedRealtime() - HostInfoReceiver.a());
                params.putLong("time_from_launch", SystemClock.elapsedRealtime() - MonitorFactory.getTimestampInfo().getProcessCurrentLaunchElapsedTime());
                params.putBoolean("perf_preload_wait", Config.B);
                if (c == null) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "readyProcess is null and startLiteProcess");
                    a(false);
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "STARTING_APPID add " + targetAppId);
                    this.k.add(targetAppId);
                }
                if (c == null) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, (String) "ready doStartApp but readyProcess is null");
                } else {
                    c.e = targetAppId;
                    if (fromLiteProcess != null) {
                        if (a(fromLiteProcess, c)) {
                            b(c, fromLiteProcess);
                            updateFromActivity = true;
                            forceStartFromMainUi2 = true;
                            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager startApp cycle recover bind2Lite");
                        } else {
                            b(fromLiteProcess, c);
                            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager startApp bind2Lite");
                        }
                    }
                    a(sourceAppId, targetAppId, params, c, false, updateFromActivity, forceStartFromMainUi2);
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager startApp " + c + " params = " + params);
                    c = null;
                    SaoyisaoStartAdvice.tinyAppStartedFromSaoyisao();
                    c(targetAppId);
                    startLiteProcessAsync(10);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void b(Bundle params) {
        String sourceId = params.getString("SOURCEAPPID_IN_LITE", "");
        String targetId = params.getString("TARGETAPPID_IN_LITE", "");
        if (TextUtils.isEmpty(targetId)) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG_SAIL, "startAppInLiteStep2@Main but targetId is null and return" + params);
        } else {
            Util.getMicroAppContext().startApp(sourceId, targetId, params);
            LoggerFactory.getTraceLogger().info(Const.TAG_SAIL, "startAppInLiteStep2@Main finish " + params);
        }
    }

    public synchronized void startAppInLiteStep3(Bundle params) {
        Message msg = Message.obtain();
        msg.what = 21;
        Bundle bundle = new Bundle(params);
        bundle.remove(Const.START_APP_IN_LITE);
        bundle.putString(Const.APP_TYPE, Util.getMicroAppContext().findDescriptionByAppId(params.getString("TARGETAPPID_IN_LITE", "")).getEngineType());
        msg.setData(bundle);
        int pid = bundle.getInt("PID");
        if (pid == 0) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG_SAIL, (String) "startAppInLiteStep3@Main but pid is 0 and return");
        } else {
            LiteProcess liteProcess = findProcessByPid(pid);
            if (liteProcess == null) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG_SAIL, (String) "startAppInLiteStep3@Main but liteProcess is null and return");
            } else {
                IpcMsgServer.reply(liteProcess.h, Const.TAG, msg);
                LoggerFactory.getTraceLogger().info(Const.TAG_SAIL, "startAppInLiteStep3@Main finish " + bundle);
            }
        }
    }

    private static void c(String targetAppId) {
        try {
            String recentApps = Util.getSp().getString("recent_tiny_apps", null);
            switch (Config.l) {
                case 0:
                    if (!TextUtils.isEmpty(recentApps)) {
                        Util.getSp().edit().putString("recent_tiny_apps", "").commit();
                        return;
                    }
                    return;
                case 1:
                    if ("2017050407110255|2017041206668232".contains(targetAppId) && !(targetAppId + ";").equals(recentApps)) {
                        Util.getSp().edit().putString("recent_tiny_apps", targetAppId + ";").commit();
                        return;
                    }
                    return;
                case 2:
                    if (!"2017050407110255|2017041206668232".contains(targetAppId)) {
                        return;
                    }
                    if (TextUtils.isEmpty(recentApps)) {
                        Util.getSp().edit().putString("recent_tiny_apps", targetAppId + ";").commit();
                        return;
                    } else if (!recentApps.contains(targetAppId)) {
                        Util.getSp().edit().putString("recent_tiny_apps", recentApps + targetAppId + ";").commit();
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        } catch (Throwable th) {
            return;
        }
        LoggerFactory.getTraceLogger().error(Const.TAG, "onTinyAppStart record app id error!", thr);
        Util.getSp().edit().putString("recent_tiny_apps", "").commit();
    }

    private void a(String sourceAppId, String targetAppId, Bundle params, LiteProcess liteProcess, boolean forceStart, boolean updateFromActivity, boolean forceStartFromMainUi) {
        TraceLogger traceLogger;
        String str;
        StringBuilder append;
        String str2;
        if (params == null) {
            params = new Bundle();
        }
        params.putInt(Const.KEY_LITE_PROCESS_ID, liteProcess.b);
        params.putString("PRELOAD_FROM", liteProcess.o);
        ComponentName topTaskBaseActivity = Util.b();
        ComponentName fromBaseActivity = topTaskBaseActivity;
        if (!TextUtils.isEmpty(liteProcess.l)) {
            LiteProcess fromLiteprocess = b(liteProcess.l);
            if (fromLiteprocess != null) {
                ComponentName foundFromBaseActivity = Util.b(fromLiteprocess.g);
                if (foundFromBaseActivity != null) {
                    fromBaseActivity = foundFromBaseActivity;
                }
            }
        }
        String fromBaseActivityName = a(fromBaseActivity);
        if (topTaskBaseActivity == null || d(topTaskBaseActivity.getClassName()) != null || !Config.e || liteProcess.d != 2 || liteProcess.h == null) {
            Context context = Util.getContext();
            Intent intent = new Intent();
            intent.setAction("START_APP");
            intent.putExtra("SOURCEAPPID", sourceAppId);
            intent.putExtra("TARGETAPPID", targetAppId);
            intent.putExtra("PARAMS", params);
            intent.putExtra("FORCE_START", forceStart);
            intent.putExtra("UID", Util.c());
            if (updateFromActivity) {
                if (forceStartFromMainUi) {
                    intent.putExtra("FROM_BASE_ACTIVITY", Const.a);
                } else {
                    intent.putExtra("FROM_BASE_ACTIVITY", fromBaseActivityName);
                }
            }
            intent.setClass(context, e.get(liteProcess.b));
            intent.setFlags(268435456);
            context.startActivity(intent);
            traceLogger = LoggerFactory.getTraceLogger();
            str = Const.TAG;
            append = new StringBuilder("LiteProcessServerManager not fast doStartApp ").append(liteProcess).append(" fromBaseActivity = ").append(fromBaseActivityName).append(" topTaskBaseActivity = ");
            str2 = topTaskBaseActivity != null ? topTaskBaseActivity.getClassName() : null;
        } else {
            Message msg = Message.obtain();
            msg.what = 16;
            Bundle extras = new Bundle();
            extras.putString("SOURCEAPPID", sourceAppId);
            extras.putString("TARGETAPPID", targetAppId);
            if (updateFromActivity) {
                if (forceStartFromMainUi) {
                    extras.putString("FROM_BASE_ACTIVITY", Const.a);
                } else {
                    extras.putString("FROM_BASE_ACTIVITY", fromBaseActivityName);
                }
            }
            extras.putBoolean("FORCE_START", forceStart);
            extras.putString("UID", Util.c());
            params.putBundle("PARAMS", extras);
            msg.setData(params);
            IpcMsgServer.reply(liteProcess.h, Const.TAG, msg);
            traceLogger = LoggerFactory.getTraceLogger();
            str = Const.TAG;
            append = new StringBuilder("LiteProcessServerManager fast doStartApp ").append(liteProcess).append(" fromBaseActivity = ").append(fromBaseActivityName).append(" topTaskBaseActivity =  ");
            str2 = topTaskBaseActivity.getClassName();
        }
        traceLogger.debug(str, append.append(str2).toString());
        liteProcess.d = 2;
        liteProcess.e = targetAppId;
        liteProcess.q = params.getString(Const.APP_TYPE);
        final LiteProcess liteProcess2 = liteProcess;
        g.postDelayed(new Runnable() {
            public void run() {
                LiteProcessServerManager.this.a(liteProcess2);
            }
        }, 500);
    }

    private synchronized boolean a(String sourceAppId, String targetAppId, Bundle params, boolean updateFromActivity, boolean forceStartFromMainUi, LiteProcess fromLiteprocess) {
        boolean z;
        LiteProcess liteProcess = b(targetAppId);
        if (liteProcess == null || liteProcess.d != 2) {
            z = false;
        } else {
            ComponentName topTaskBaseActivity = Util.b();
            if (Config.r && topTaskBaseActivity != null && liteProcess.g.contains(topTaskBaseActivity.getClassName())) {
                params.putString("CLEAR_TOP_APP_WHEN_RESTART", "true");
            }
            LiteProcess origFromLiteprocess = b(liteProcess.l);
            if (origFromLiteprocess != null && topTaskBaseActivity != null && topTaskBaseActivity.getClassName().equals(Const.a)) {
                c(origFromLiteprocess, liteProcess);
                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager restartApp unbind2Lite");
            } else if (!a(fromLiteprocess, liteProcess)) {
                b(fromLiteprocess, liteProcess);
                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager restartApp bind2Lite");
            }
            if (a(sourceAppId, targetAppId, params, liteProcess, updateFromActivity, forceStartFromMainUi)) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "restartInTask success in process " + liteProcess);
            } else {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "restartInTask failed and will restart in process " + liteProcess);
                a(sourceAppId, targetAppId, params, liteProcess, true, updateFromActivity, forceStartFromMainUi);
            }
            z = true;
        }
        return z;
    }

    private boolean a(String sourceAppId, String targetAppId, Bundle params, LiteProcess toLiteProcess, boolean updateFromActivity, boolean forceStartFromMainUi) {
        final ActivityManager activityManager = (ActivityManager) Util.getContext().getSystemService(WidgetType.ACTIVITY);
        RunningTaskInfo toRunningTaskInfo = null;
        Iterator<RunningTaskInfo> it = activityManager.getRunningTasks(Integer.MAX_VALUE).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RunningTaskInfo runningTaskInfo = it.next();
            if (runningTaskInfo.baseActivity != null && toLiteProcess.g.contains(runningTaskInfo.baseActivity.getClassName())) {
                toRunningTaskInfo = runningTaskInfo;
                break;
            }
        }
        if (toRunningTaskInfo == null) {
            return false;
        }
        ComponentName fromActivity = Util.b();
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager restartAppIfNeeded sourceAppId = " + sourceAppId + " targetAppId = " + targetAppId + " toRunningTaskInfo.baseActivity = " + toRunningTaskInfo.baseActivity + " updateFromActivity  = " + updateFromActivity + " fromActivity = " + fromActivity + " params = " + params);
        boolean fromForeground = false;
        if (fromActivity != null) {
            if (updateFromActivity) {
                Message msg = Message.obtain();
                msg.what = 14;
                Bundle data = new Bundle();
                if (forceStartFromMainUi) {
                    data.putString("FROM_BASE_ACTIVITY", Const.a);
                } else {
                    data.putString("FROM_BASE_ACTIVITY", fromActivity.getClassName());
                }
                msg.setData(data);
                IpcMsgServer.reply(toLiteProcess.h, Const.TAG, msg);
            }
            if (fromActivity.getPackageName().equals(Util.getContext().getPackageName())) {
                fromForeground = true;
            }
        }
        LiteProcess fromLiteProcess = null;
        if (fromActivity != null) {
            fromLiteProcess = d(fromActivity.getClassName());
        }
        Handler handler = new Handler(Looper.getMainLooper());
        final RunningTaskInfo finalToRunningTaskInfo = toRunningTaskInfo;
        final boolean finalFromForeground = fromForeground;
        if (fromLiteProcess == null) {
            final Bundle bundle = params;
            handler.post(new Runnable() {
                public void run() {
                    Util.moveTaskToFront(activityManager, (Activity) Util.getMicroAppContext().getTopActivity().get(), finalToRunningTaskInfo, true, finalFromForeground, bundle, false);
                }
            });
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager doRestartAppInTask finish1.");
        } else if (fromLiteProcess.j == null) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, "LiteProcessServerManager doRestartAppInTask fail and stop start.  fromLiteProcess = " + fromLiteProcess + " fromLiteProcess.clientService = " + fromLiteProcess.j);
            final Bundle bundle2 = params;
            handler.post(new Runnable() {
                public void run() {
                    Util.moveTaskToFront(activityManager, (Activity) null, finalToRunningTaskInfo, true, finalFromForeground, bundle2, false);
                }
            });
        } else {
            try {
                fromLiteProcess.j.moveTaskToFront(toRunningTaskInfo.id, true, fromForeground, params);
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
            }
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager doRestartAppInTask finish2.  fromLiteProcess = " + fromLiteProcess + " fromLiteProcess.clientService = " + fromLiteProcess.j);
        }
        return true;
    }

    private synchronized boolean a(LiteProcess src, LiteProcess dst) {
        boolean z = false;
        synchronized (this) {
            if (!(src == null || dst == null)) {
                if (!TextUtils.isEmpty(src.l) && src.l.equals(dst.e)) {
                    z = true;
                }
            }
        }
        return z;
    }

    private synchronized void b(LiteProcess src, LiteProcess dst) {
        if (!(src == null || dst == null)) {
            if (TextUtils.isEmpty(src.e) || !src.e.equals(dst.e)) {
                dst.l = src.e;
                src.m.add(dst.e);
                src.k = false;
                LoggerFactory.getTraceLogger().warn((String) Const.TAG, "bind2Lite successful src: " + src + " dst: " + dst);
            } else {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, "bind2Lite failed src == dst. src: " + src + " dst: " + dst);
            }
        }
    }

    private synchronized void c(LiteProcess src, LiteProcess dst) {
        if (!(src == null || dst == null)) {
            dst.l = null;
            src.m.remove(dst.e);
            if (src.m.size() == 0) {
                src.k = true;
            }
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, "unbind2Lite successful src: " + src + " dst: " + dst);
        }
    }

    public synchronized void startLiteProcessAsync(int delaySeconds) {
        if (Util.needSupportLiteProcess()) {
            if (!h || !Config.c || (j && delaySeconds > 0)) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager startLiteProcessAsync return  needPreloadLocal: " + h + " NEED_PRELOAD: " + Config.c + " IS_START_PROCESS_ASYNC_ING: " + j);
            } else if (!Config.f || !a || !isAllLiteProcessHide()) {
                TaskScheduleService scheduleService = (TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName());
                if (scheduleService == null) {
                    a(true);
                } else {
                    String preloadFrom = "default";
                    if (delaySeconds == -1) {
                        preloadFrom = "saoyisao";
                    } else if (delaySeconds == -2) {
                        preloadFrom = "bike";
                    } else if (delaySeconds == -3) {
                        preloadFrom = "tinyapp";
                    } else if (delaySeconds == -4) {
                        preloadFrom = H5SearchType.SEARCH;
                    } else if (delaySeconds == -5) {
                        preloadFrom = "scan";
                    } else if (delaySeconds == -6) {
                        preloadFrom = "scheme";
                    } else if (delaySeconds == -7) {
                        preloadFrom = "favorite";
                    }
                    if (delaySeconds < 0) {
                        delaySeconds = 0;
                    }
                    final String preload = preloadFrom;
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager startLiteProcessAsync");
                    j = true;
                    final int preloadDelay = delaySeconds;
                    scheduleService.schedule(new Runnable() {
                        public void run() {
                            if (Config.f && LiteProcessServerManager.a && LiteProcessServerManager.this.isAllLiteProcessHide()) {
                                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager startLiteProcessAsync cancel2  mainAtBackground: " + LiteProcessServerManager.a + " isAllLiteProcessHide " + LiteProcessServerManager.this.isAllLiteProcessHide());
                                LiteProcessServerManager.j = false;
                            } else if (!Config.B || LiteProcessServerManager.a || preloadDelay == 0 || !LiteProcessServerManager.this.i()) {
                                LiteProcessServerManager.this.a(true, preload);
                                LiteProcessServerManager.j = false;
                            } else {
                                LoggerFactory.getTraceLogger().debug(Const.TAG, "checkPreloadTiming failed, delaying");
                            }
                        }
                    }, "LiteProcessServerManager", (long) delaySeconds, TimeUnit.SECONDS);
                }
            } else {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager startLiteProcessAsync cancel1  mainAtBackground: " + a + " isAllLiteProcessHide " + isAllLiteProcessHide());
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean i() {
        try {
            Activity activity = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
            if (activity != null && !activity.getClass().getName().endsWith("AlipayLogin")) {
                MemoryInfo memoryInfo = new MemoryInfo();
                ((ActivityManager) Util.getContext().getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memoryInfo);
                if (memoryInfo.availMem < ((long) (Config.C * 1000 * 1000))) {
                    int i2 = this.l + 1;
                    this.l = i2;
                    if (i2 >= 30) {
                        this.l = 0;
                        return false;
                    }
                    j = false;
                    startLiteProcessAsync(2);
                    return true;
                }
            }
        } catch (Throwable thr) {
            LoggerFactory.getTraceLogger().warn(Const.TAG, "checkPreloadTiming error", thr);
        }
        this.l = 0;
        return false;
    }

    private void a(boolean needStartService) {
        a(needStartService, (String) "default");
    }

    /* access modifiers changed from: private */
    public synchronized void a(boolean needStartService, String preloadFrom) {
        if (preloadFrom == "default") {
            FullLinkApi.getInstance().putInChain("__liteprocess_start__", String.valueOf(SystemClock.elapsedRealtime()));
        }
        if (Util.needSupportLiteProcess()) {
            if (c == null) {
                LiteProcess liteProcess = l();
                if (liteProcess == null) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, (String) "startLiteProcess but no can start!!! ");
                } else {
                    b.remove(liteProcess);
                    if (liteProcess.d == 2) {
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "is running and stop " + liteProcess);
                        b(liteProcess);
                    }
                    b.addLast(liteProcess);
                    liteProcess.o = preloadFrom;
                    c = liteProcess;
                    liteProcess.d = 1;
                    if (needStartService) {
                        a(c);
                    }
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager startLiteProcess " + c);
                    ConfigChangeReceiver.register();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(final LiteProcess liteProcess) {
        if (liteProcess != null && liteProcess.i == null) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "bindClientService " + liteProcess);
            Context context = Util.getContext();
            Intent intent = new Intent(context, d.get(liteProcess.b));
            try {
                liteProcess.i = new ServiceConnection() {
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        try {
                            LoggerFactory.getTraceLogger().debug(Const.TAG, "clientService onServiceConnected " + name + Token.SEPARATOR + service);
                            liteProcess.j = Stub.asInterface(service);
                        } catch (Throwable t) {
                            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                        }
                    }

                    public void onServiceDisconnected(ComponentName name) {
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "clientService onServiceDisconnected " + name);
                    }
                };
                intent.putExtra("UID", Util.c());
                LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager start service begin!");
                context.startService(intent);
                LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager start service end!");
            } catch (IllegalStateException iex) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, (String) "LiteProcessServerManager start service failed!");
                LoggerFactory.getTraceLogger().error((String) Const.TAG, (Throwable) iex);
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                return;
            }
            context.bindService(intent, liteProcess.i, 0);
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(String appId) {
        b(b(appId));
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(int lpid) {
        b(findProcessByLpid(lpid));
    }

    /* access modifiers changed from: private */
    public synchronized void b(LiteProcess liteProcess) {
        if (liteProcess != null) {
            if (liteProcess.d != 0) {
                if (liteProcess.isShow()) {
                    LoggerFactory.getTraceLogger().warn(Const.TAG, "LiteProcessServerManager stopLiteProcess print stack trace, not exception:", new Throwable());
                    PerformanceLogger.logStopShowingLiteProcess();
                }
                LoggerFactory.getTraceLogger().warn((String) Const.TAG, "LiteProcessServerManager stopLiteProcess " + liteProcess);
                e(liteProcess);
                LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager destroyClient " + liteProcess);
                if (liteProcess.j != null) {
                    try {
                        liteProcess.j.destoryClient();
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                    }
                }
                LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager stopService " + liteProcess);
                try {
                    Context context = Util.getContext();
                    Intent intent = new Intent(context, d.get(liteProcess.b));
                    if (liteProcess.i != null) {
                        context.unbindService(liteProcess.i);
                    }
                    context.stopService(intent);
                } catch (Throwable t2) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t2));
                }
                Util.a(liteProcess.g);
                d(liteProcess);
                LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager killProcess " + liteProcess);
                Process.killProcess(liteProcess.c);
                c(liteProcess);
                liteProcess.a();
            }
        }
    }

    private synchronized void c(LiteProcess liteProcess) {
        if (!TextUtils.isEmpty(liteProcess.l)) {
            final LiteProcess fromLiteProcess = b(liteProcess.l);
            if (fromLiteProcess != null) {
                LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager stopFromLiteProcessIfNeeded liteProcess: " + liteProcess + " from: " + fromLiteProcess);
                c(fromLiteProcess, liteProcess);
                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager stopLite unbind2Lite");
                if (fromLiteProcess.k && !fromLiteProcess.f) {
                    TaskScheduleService scheduleService = (TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName());
                    if (scheduleService != null) {
                        scheduleService.schedule(new Runnable() {
                            public void run() {
                                if (fromLiteProcess.k && !fromLiteProcess.f) {
                                    LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager stopFromLiteProcessIfNeeded doStop from " + fromLiteProcess);
                                    LiteProcessServerManager.this.b(fromLiteProcess);
                                }
                            }
                        }, "LiteProcessServerManager", Config.z, TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a() {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager stopAllLiteProcess");
        Iterator it = b.iterator();
        while (it.hasNext()) {
            b((LiteProcess) it.next());
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void b() {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager notifyALlLiteProcessLogout");
        Iterator it = b.iterator();
        while (it.hasNext()) {
            LiteProcess liteProcess = (LiteProcess) it.next();
            if (!(liteProcess == null || liteProcess.d == 0)) {
                LoggerFactory.getTraceLogger().info(Const.TAG, "LiteProcessServerManager notifyLogout " + liteProcess);
                if (liteProcess.j != null) {
                    try {
                        liteProcess.j.notifyLogout();
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                    }
                } else {
                    continue;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void c() {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager notifySrvReady");
        Iterator it = b.iterator();
        while (it.hasNext()) {
            LiteProcess liteProcess = (LiteProcess) it.next();
            if (!(liteProcess.h == null || liteProcess.d == 0)) {
                Message msg = Message.obtain();
                msg.what = 13;
                IpcMsgServer.reply(liteProcess.h, Const.TAG, msg);
            }
        }
    }

    private static void j() {
        if (!Config.v) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "no need notifySrvShow");
            return;
        }
        Iterator it = b.iterator();
        while (it.hasNext()) {
            LiteProcess liteProcess = (LiteProcess) it.next();
            if (!(liteProcess == null || liteProcess.d != 2 || liteProcess.h == null)) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "notifySrvShow " + liteProcess);
                Message msg = Message.obtain();
                msg.what = 22;
                msg.arg1 = Config.w;
                IpcMsgServer.reply(liteProcess.h, Const.TAG, msg);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0032 A[SYNTHETIC, Splitter:B:9:0x0032] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onProcessAdd(int r8, int r9, java.lang.String r10, java.lang.String r11, android.os.Messenger r12) {
        /*
            r7 = this;
            monitor-enter(r7)
            com.alipay.mobile.liteprocess.LiteProcess r0 = r7.findProcessByLpid(r9)     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x003f
            java.lang.String r3 = r0.e     // Catch:{ Throwable -> 0x0063 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0063 }
            if (r3 != 0) goto L_0x003f
            java.util.Set<java.lang.String> r3 = r7.k     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r4 = r0.e     // Catch:{ Throwable -> 0x0063 }
            r3.remove(r4)     // Catch:{ Throwable -> 0x0063 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r4 = "LiteProcess"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r6 = "STARTING_APPID remove liteProcess.appId "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r6 = r0.e     // Catch:{ Throwable -> 0x0063 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0063 }
            r3.debug(r4, r5)     // Catch:{ Throwable -> 0x0063 }
        L_0x0030:
            if (r0 != 0) goto L_0x0075
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0072 }
            java.lang.String r4 = "LiteProcess"
            java.lang.String r5 = "LiteProcessServerManager onProcessAdd liteProcess = null"
            r3.debug(r4, r5)     // Catch:{ all -> 0x0072 }
        L_0x003d:
            monitor-exit(r7)
            return
        L_0x003f:
            boolean r3 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x0063 }
            if (r3 != 0) goto L_0x0030
            java.util.Set<java.lang.String> r3 = r7.k     // Catch:{ Throwable -> 0x0063 }
            r3.remove(r10)     // Catch:{ Throwable -> 0x0063 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r4 = "LiteProcess"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r6 = "STARTING_APPID remove appId "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0063 }
            java.lang.StringBuilder r5 = r5.append(r10)     // Catch:{ Throwable -> 0x0063 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0063 }
            r3.debug(r4, r5)     // Catch:{ Throwable -> 0x0063 }
            goto L_0x0030
        L_0x0063:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0072 }
            java.lang.String r4 = "LiteProcess"
            java.lang.String r5 = android.util.Log.getStackTraceString(r2)     // Catch:{ all -> 0x0072 }
            r3.error(r4, r5)     // Catch:{ all -> 0x0072 }
            goto L_0x0030
        L_0x0072:
            r3 = move-exception
            monitor-exit(r7)
            throw r3
        L_0x0075:
            r3 = 2
            r0.d = r3     // Catch:{ all -> 0x0072 }
            r0.c = r8     // Catch:{ all -> 0x0072 }
            r0.a = r11     // Catch:{ all -> 0x0072 }
            r0.h = r12     // Catch:{ all -> 0x0072 }
            com.alipay.mobile.liteprocess.LiteProcess r3 = c     // Catch:{ all -> 0x0072 }
            if (r3 == r0) goto L_0x008c
            java.lang.String r3 = r0.e     // Catch:{ all -> 0x0072 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0072 }
            if (r3 == 0) goto L_0x008c
            r0.e = r10     // Catch:{ all -> 0x0072 }
        L_0x008c:
            java.util.LinkedList<com.alipay.mobile.liteprocess.LiteProcess> r3 = b     // Catch:{ all -> 0x0072 }
            r3.remove(r0)     // Catch:{ all -> 0x0072 }
            java.util.LinkedList<com.alipay.mobile.liteprocess.LiteProcess> r3 = b     // Catch:{ all -> 0x0072 }
            r3.addLast(r0)     // Catch:{ all -> 0x0072 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0072 }
            java.lang.String r4 = "LiteProcess"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0072 }
            java.lang.String r6 = "LiteProcessServerManager onProcessAdd "
            r5.<init>(r6)     // Catch:{ all -> 0x0072 }
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ all -> 0x0072 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0072 }
            r3.debug(r4, r5)     // Catch:{ all -> 0x0072 }
            boolean r3 = com.alipay.mobile.liteprocess.LiteProcessPipeline.a     // Catch:{ all -> 0x0072 }
            if (r3 == 0) goto L_0x003d
            android.os.Message r1 = android.os.Message.obtain()     // Catch:{ all -> 0x0072 }
            r3 = 13
            r1.what = r3     // Catch:{ all -> 0x0072 }
            java.lang.String r3 = "LiteProcess"
            com.alipay.mobile.liteprocess.ipc.IpcMsgServer.reply(r12, r3, r1)     // Catch:{ all -> 0x0072 }
            goto L_0x003d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.liteprocess.LiteProcessServerManager.onProcessAdd(int, int, java.lang.String, java.lang.String, android.os.Messenger):void");
    }

    public synchronized void onProcessRemove(int pid, int lpid) {
        if (c != null && c.c == pid) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager readyProcess onProcessRemove " + c);
            c = null;
        }
        LiteProcess liteProcess = findProcessByLpid(lpid);
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager onProcessRemove " + liteProcess);
        if (liteProcess != null && liteProcess.c == pid) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "onProcessRemove and reset");
            liteProcess.a();
            b.remove(liteProcess);
            b.addFirst(liteProcess);
        }
        if (c == null) {
            startLiteProcessAsync(1);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(int pid, int lpid) {
        LiteProcess liteProcess = findProcessByLpid(lpid);
        if (liteProcess != null && liteProcess.c == pid) {
            liteProcess.f = false;
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager onProcessHide " + liteProcess);
            d(liteProcess);
            if (liteProcess.k) {
                a(pid, liteProcess);
            } else {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager can not Stop " + liteProcess);
            }
            if (liteProcess.n) {
                Message msg = Message.obtain();
                msg.what = 17;
                IpcMsgServer.reply(liteProcess.h, Const.TAG, msg);
            } else {
                liteProcess.n = true;
            }
            d();
            ComponentName topTaskBaseActivity = Util.b();
            if (topTaskBaseActivity != null && topTaskBaseActivity.getClassName().equals(Const.a) && isAllLiteProcessHide()) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager onProcessHide and notifySrvShow");
                j();
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void b(int pid, int lpid) {
        LiteProcess liteProcess = findProcessByLpid(lpid);
        if (liteProcess != null) {
            liteProcess.c = pid;
            liteProcess.f = true;
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager onProcessShow " + liteProcess);
            Iterator it = b.iterator();
            while (it.hasNext()) {
                LiteProcess lite = (LiteProcess) it.next();
                if (lite.c != pid && lite.f) {
                    lite.f = false;
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager onProcessShow fix isShow" + lite);
                }
            }
            d(liteProcess);
            e();
        }
    }

    /* access modifiers changed from: private */
    public synchronized void c(int pid, int lpid) {
        LoggerFactory.getTraceLogger().info(Const.TAG, "onAppxLoaded pid = " + pid + " lpid = " + lpid);
        LiteProcess liteProcess = findProcessByLpid(lpid);
        if (liteProcess != null) {
            liteProcess.p = true;
        }
    }

    private void a(final int pid, final LiteProcess liteProcess) {
        Runnable runnable = new Runnable() {
            public void run() {
                if (liteProcess.c == pid && !liteProcess.f) {
                    LoggerFactory.getTraceLogger().warn((String) Const.TAG, "canKill process after CAN_STOP_DURATION = " + Config.d + Token.SEPARATOR + liteProcess);
                    LiteProcessServerManager.this.b(liteProcess);
                }
            }
        };
        g.postDelayed(runnable, Config.d);
        f.put(pid, runnable);
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager addStopProcessRunnable " + liteProcess);
    }

    private static void d(LiteProcess liteProcess) {
        Runnable runnable = f.get(liteProcess.c);
        if (runnable != null) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager removeStopProcessRunnable " + liteProcess);
            g.removeCallbacks(runnable);
            f.remove(liteProcess.c);
        }
    }

    @Nullable
    public synchronized LiteProcess findProcessByLpid(int lpid) {
        LiteProcess liteProcess;
        Iterator it = b.iterator();
        while (true) {
            if (!it.hasNext()) {
                liteProcess = null;
                break;
            }
            liteProcess = (LiteProcess) it.next();
            if (liteProcess.b == lpid) {
                break;
            }
        }
        return liteProcess;
    }

    @Nullable
    public synchronized LiteProcess findProcessByPid(int pid) {
        LiteProcess liteProcess;
        try {
            Iterator it = b.iterator();
            while (true) {
                if (!it.hasNext()) {
                    liteProcess = null;
                    break;
                }
                liteProcess = (LiteProcess) it.next();
                if (liteProcess.c == pid) {
                    break;
                }
            }
        }
        return liteProcess;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final synchronized LiteProcess b(String appId) {
        LiteProcess liteProcess;
        if (!TextUtils.isEmpty(appId)) {
            Iterator it = b.iterator();
            while (true) {
                if (!it.hasNext()) {
                    liteProcess = null;
                    break;
                }
                liteProcess = (LiteProcess) it.next();
                if (appId.equals(liteProcess.e)) {
                    break;
                }
            }
        } else {
            liteProcess = null;
        }
        return liteProcess;
    }

    public synchronized List<LiteProcess> getAllAliveProcess() {
        List aliveProcess;
        aliveProcess = new ArrayList();
        Iterator it = b.iterator();
        while (it.hasNext()) {
            LiteProcess liteProcess = (LiteProcess) it.next();
            if (liteProcess.d != 0) {
                aliveProcess.add(liteProcess);
            }
        }
        return aliveProcess;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(Class clazz, int lpid) {
        LiteProcess liteProcess = findProcessByLpid(lpid);
        if (liteProcess != null) {
            liteProcess.g.add(clazz.getName());
        }
    }

    public synchronized boolean startActivityFromLiteProcessIfNeeded(MicroApplication microApplication, Intent intent) {
        boolean z = false;
        synchronized (this) {
            ComponentName baseActivity = Util.b();
            if (baseActivity != null && !TextUtils.isEmpty(baseActivity.getClassName())) {
                String baseActivityClassName = baseActivity.getClassName();
                LoggerFactory.getTraceLogger().debug(Const.TAG, "startActivityFromLiteProcessIfNeeded baseActivityClassName = " + baseActivityClassName);
                LiteProcess liteProcess = d(baseActivityClassName);
                if (!(liteProcess == null || liteProcess.h == null)) {
                    a(liteProcess, microApplication, intent);
                    z = true;
                }
            }
        }
        return z;
    }

    private synchronized void a(LiteProcess liteProcess, MicroApplication microApplication, Intent intent) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessServerManager doStartActivityFromLiteProcess intent = " + intent.toUri(1));
        Message msg = Message.obtain();
        msg.what = 10;
        Bundle bundle = new Bundle();
        intent.setFlags(262144);
        if (intent.getExtras() != null) {
            intent.putExtra("mExtras", intent.getExtras());
        }
        if (microApplication != null) {
            intent.putExtra("app_id", microApplication.getAppId());
        }
        bundle.putParcelable("intent", intent);
        msg.setData(bundle);
        IpcMsgServer.reply(liteProcess.h, Const.TAG, msg);
    }

    public void registerCallServerBean() {
        IpcCallServer.registerServiceBean(RpcCall.class.getName(), new RpcCallServerImpl());
        LoggerFactory.getTraceLogger().debug(Const.TAG, "registerCallServerBean");
    }

    public Looper getLiteProcessLooper() {
        return g.getLooper();
    }

    private static void e(LiteProcess liteProcess) {
        try {
            Object tinyLoggingConfigManager = ReflectUtil.invokeMethod((String) "com.alipay.mobile.logging.TinyLoggingConfigManager", (String) "getInstance");
            if (tinyLoggingConfigManager != null) {
                ReflectUtil.invokeMethod(tinyLoggingConfigManager, (String) "triggerUpload", new Class[]{String.class, String.class}, (Object[]) new String[]{liteProcess.e, null});
            }
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().info(Const.TAG, Log.getStackTraceString(e2));
        }
    }

    public synchronized boolean isAllLiteProcessHide() {
        boolean z;
        Iterator it = b.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            LiteProcess liteProcess = (LiteProcess) it.next();
            if (liteProcess != null && liteProcess.f) {
                z = false;
                break;
            }
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().debug(com.alipay.mobile.liteprocess.Const.TAG, "findProcessOnShow return null " + r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005d, code lost:
        r0 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.alipay.mobile.liteprocess.LiteProcess k() {
        /*
            r6 = this;
            monitor-enter(r6)
            android.content.ComponentName r1 = com.alipay.mobile.liteprocess.Util.b()     // Catch:{ all -> 0x005f }
            java.util.LinkedList<com.alipay.mobile.liteprocess.LiteProcess> r2 = b     // Catch:{ all -> 0x005f }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x005f }
        L_0x000b:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x005f }
            if (r3 == 0) goto L_0x0045
            java.lang.Object r0 = r2.next()     // Catch:{ all -> 0x005f }
            com.alipay.mobile.liteprocess.LiteProcess r0 = (com.alipay.mobile.liteprocess.LiteProcess) r0     // Catch:{ all -> 0x005f }
            if (r0 == 0) goto L_0x000b
            boolean r3 = r0.f     // Catch:{ all -> 0x005f }
            if (r3 == 0) goto L_0x000b
            if (r1 == 0) goto L_0x000b
            java.util.Set<java.lang.String> r3 = r0.g     // Catch:{ all -> 0x005f }
            java.lang.String r4 = r1.getClassName()     // Catch:{ all -> 0x005f }
            boolean r3 = r3.contains(r4)     // Catch:{ all -> 0x005f }
            if (r3 == 0) goto L_0x000b
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x005f }
            java.lang.String r3 = "LiteProcess"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x005f }
            java.lang.String r5 = "findProcessOnShow "
            r4.<init>(r5)     // Catch:{ all -> 0x005f }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ all -> 0x005f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x005f }
            r2.debug(r3, r4)     // Catch:{ all -> 0x005f }
        L_0x0043:
            monitor-exit(r6)
            return r0
        L_0x0045:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x005f }
            java.lang.String r3 = "LiteProcess"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x005f }
            java.lang.String r5 = "findProcessOnShow return null "
            r4.<init>(r5)     // Catch:{ all -> 0x005f }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ all -> 0x005f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x005f }
            r2.debug(r3, r4)     // Catch:{ all -> 0x005f }
            r0 = 0
            goto L_0x0043
        L_0x005f:
            r2 = move-exception
            monitor-exit(r6)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.liteprocess.LiteProcessServerManager.k():com.alipay.mobile.liteprocess.LiteProcess");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r1 = b.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003d, code lost:
        if (r1.hasNext() == false) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003f, code lost:
        r0 = (com.alipay.mobile.liteprocess.LiteProcess) r1.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0045, code lost:
        if (r0 == null) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0049, code lost:
        if (r0.f != false) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004d, code lost:
        if (r0.k == false) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004f, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().debug(com.alipay.mobile.liteprocess.Const.TAG, "findProcesCanStart canStop " + r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006b, code lost:
        r0 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.alipay.mobile.liteprocess.LiteProcess l() {
        /*
            r5 = this;
            monitor-enter(r5)
            java.util.LinkedList<com.alipay.mobile.liteprocess.LiteProcess> r1 = b     // Catch:{ all -> 0x0068 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0068 }
        L_0x0007:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0068 }
            if (r2 == 0) goto L_0x0033
            java.lang.Object r0 = r1.next()     // Catch:{ all -> 0x0068 }
            com.alipay.mobile.liteprocess.LiteProcess r0 = (com.alipay.mobile.liteprocess.LiteProcess) r0     // Catch:{ all -> 0x0068 }
            if (r0 == 0) goto L_0x0007
            int r2 = r0.d     // Catch:{ all -> 0x0068 }
            if (r2 != 0) goto L_0x0007
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0068 }
            java.lang.String r2 = "LiteProcess"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0068 }
            java.lang.String r4 = "findProcesCanStart TERMINATED "
            r3.<init>(r4)     // Catch:{ all -> 0x0068 }
            java.lang.StringBuilder r3 = r3.append(r0)     // Catch:{ all -> 0x0068 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0068 }
            r1.debug(r2, r3)     // Catch:{ all -> 0x0068 }
        L_0x0031:
            monitor-exit(r5)
            return r0
        L_0x0033:
            java.util.LinkedList<com.alipay.mobile.liteprocess.LiteProcess> r1 = b     // Catch:{ all -> 0x0068 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0068 }
        L_0x0039:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0068 }
            if (r2 == 0) goto L_0x006b
            java.lang.Object r0 = r1.next()     // Catch:{ all -> 0x0068 }
            com.alipay.mobile.liteprocess.LiteProcess r0 = (com.alipay.mobile.liteprocess.LiteProcess) r0     // Catch:{ all -> 0x0068 }
            if (r0 == 0) goto L_0x0039
            boolean r2 = r0.f     // Catch:{ all -> 0x0068 }
            if (r2 != 0) goto L_0x0039
            boolean r2 = r0.k     // Catch:{ all -> 0x0068 }
            if (r2 == 0) goto L_0x0039
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0068 }
            java.lang.String r2 = "LiteProcess"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0068 }
            java.lang.String r4 = "findProcesCanStart canStop "
            r3.<init>(r4)     // Catch:{ all -> 0x0068 }
            java.lang.StringBuilder r3 = r3.append(r0)     // Catch:{ all -> 0x0068 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0068 }
            r1.debug(r2, r3)     // Catch:{ all -> 0x0068 }
            goto L_0x0031
        L_0x0068:
            r1 = move-exception
            monitor-exit(r5)
            throw r1
        L_0x006b:
            r0 = 0
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.liteprocess.LiteProcessServerManager.l():com.alipay.mobile.liteprocess.LiteProcess");
    }

    private synchronized LiteProcess d(String className) {
        LiteProcess liteProcess;
        Iterator it = b.iterator();
        while (true) {
            if (!it.hasNext()) {
                liteProcess = null;
                break;
            }
            liteProcess = (LiteProcess) it.next();
            if (liteProcess != null && liteProcess.g.contains(className)) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "findProcessByClassName " + liteProcess);
                break;
            }
        }
        return liteProcess;
    }

    private synchronized String a(ComponentName fromBaseActivity) {
        String fromBaseActivityName;
        if (!Config.u) {
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, "no need safeGetFromBaseActivityName " + fromBaseActivity);
            fromBaseActivityName = fromBaseActivity != null ? fromBaseActivity.getClassName() : null;
        } else {
            LoggerFactory.getTraceLogger().warn((String) Const.TAG, "safeGetFromBaseActivityName " + fromBaseActivity);
            fromBaseActivityName = null;
            if (fromBaseActivity == null) {
                LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "checkFrom fail. fromBaseActivity is null");
            } else if (Const.b.equals(fromBaseActivity.getClassName()) || Const.c.equals(fromBaseActivity.getClassName())) {
                fromBaseActivityName = fromBaseActivity.getClassName();
                LoggerFactory.getTraceLogger().warn((String) Const.TAG, "checkFrom success with main proc activity = " + fromBaseActivity);
            } else if (!fromBaseActivity.getClassName().equals(Const.a)) {
                LiteProcess checkFrom = d(fromBaseActivity.getClassName());
                if (checkFrom == null) {
                    LoggerFactory.getTraceLogger().warn((String) Const.TAG, "checkFrom fail. checkFrom is null. fromBaseActivity =  " + fromBaseActivity);
                } else if (checkFrom.d == 2) {
                    LoggerFactory.getTraceLogger().warn((String) Const.TAG, "checkFrom success. checkFrom = " + checkFrom + " fromBaseActivity = " + fromBaseActivity);
                    fromBaseActivityName = fromBaseActivity.getClassName();
                } else {
                    LoggerFactory.getTraceLogger().warn((String) Const.TAG, "checkFrom fail and fix. checkFrom not running = " + checkFrom + " fromBaseActivity = " + fromBaseActivity);
                    fromBaseActivityName = Const.a;
                }
            } else {
                LoggerFactory.getTraceLogger().warn((String) Const.TAG, "checkFrom no need. fromBaseActivity is MAIN_UI. fromBaseActivity = " + fromBaseActivity);
                fromBaseActivityName = Const.a;
            }
        }
        return fromBaseActivityName;
    }

    public synchronized boolean hasPreloadCompletedProcess() {
        return c != null && c.p;
    }

    public synchronized boolean hasPreloadProcess() {
        return c != null;
    }

    public LiteProcess getReadyLiteProcess() {
        return c;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void d() {
        if (Config.f) {
            if (!a || !isAllLiteProcessHide()) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "addStopReadyProcessRunnable return mainAtBackground: " + a + " isAllLiteProcessHide " + isAllLiteProcessHide());
            } else {
                if (this.m == null) {
                    this.m = new Runnable() {
                        public void run() {
                            LiteProcessServerManager.this.m();
                        }
                    };
                }
                g.removeCallbacks(this.m);
                g.postDelayed(this.m, Config.g);
                LoggerFactory.getTraceLogger().debug(Const.TAG, "addStopReadyProcessRunnable");
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void m() {
        if (Config.f) {
            if (c == null || !a || !isAllLiteProcessHide()) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "stopReadyProcess cancel readyProcess: " + c + " mainAtBackground: " + a + " isAllLiteProcessHide: " + isAllLiteProcessHide());
            } else {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "stopReadyProcess");
                b(c);
                c = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void e() {
        boolean z = true;
        synchronized (this) {
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder append = new StringBuilder("removeStopReadyProcessRunnable runnable == null ? ").append(this.m == null).append(", readyProcess == null ? ");
            if (c != null) {
                z = false;
            }
            traceLogger.debug(Const.TAG, append.append(z).append(", pipeline over ? ").append(LiteProcessPipeline2.a).append(", starting ? ").append(j).toString());
            if (Config.f) {
                if (this.m != null) {
                    g.removeCallbacks(this.m);
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "removeStopReadyProcessRunnable");
                }
                if (c == null && LiteProcessPipeline2.a && !j) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "removeStopReadyProcessRunnable and startAsync");
                    startLiteProcessAsync(Config.i);
                }
            }
        }
    }

    static void a(Bundle extra) {
        Iterator it = b.iterator();
        while (it.hasNext()) {
            LiteProcess liteProcess = (LiteProcess) it.next();
            if (!(liteProcess == null || liteProcess.d != 2 || liteProcess.h == null)) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "notifyConfigChanged " + liteProcess);
                Message msg = Message.obtain();
                msg.what = 20;
                msg.setData(extra);
                IpcMsgServer.reply(liteProcess.h, Const.TAG, msg);
            }
        }
    }
}
