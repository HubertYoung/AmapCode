package com.alipay.mobile.liteprocess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.LiteProcessApi.LiteClient;
import com.alipay.mobile.liteprocess.ipc.IpcCallClient;
import com.alipay.mobile.liteprocess.ipc.IpcMsgClient;
import com.alipay.mobile.liteprocess.perf.PerformanceLogger;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class LiteProcessClientManager {
    private static LiteProcessClientHandler a;
    private static Set<Class> b = new HashSet();
    /* access modifiers changed from: private */
    public static Set<String> c = new ConcurrentSkipListSet();
    private static boolean d = false;
    private static boolean e = false;
    private static Handler f;
    private static Set<LiteClient> g = new HashSet();
    public static boolean hasStartApp = false;

    class LiteProcessClientHandler extends Handler {
        LiteProcessClientHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    LiteProcessClientManager.f(msg.getData());
                    return;
                case 13:
                    LiteProcessClientManager.i();
                    return;
                case 14:
                    LiteProcessActivity.fromBaseActivity = msg.getData().getString("FROM_BASE_ACTIVITY");
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "WHAT_RESTART_FROM fromBaseActivity = " + LiteProcessActivity.fromBaseActivity);
                    return;
                case 16:
                    PerformanceLogger.onTinyAppProcessEvent("main", "ipc_start_tinyapp");
                    Bundle extras = msg.getData().getBundle("PARAMS");
                    if (extras != null) {
                        msg.getData().remove("PARAMS");
                        LiteProcessActivity.fromBaseActivity = extras.getString("FROM_BASE_ACTIVITY");
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "WHAT_START_APP fromBaseActivity = " + LiteProcessActivity.fromBaseActivity);
                        Intent intent = new Intent("START_APP");
                        intent.putExtra("SOURCEAPPID", extras.getString("SOURCEAPPID"));
                        intent.putExtra("TARGETAPPID", extras.getString("TARGETAPPID"));
                        intent.putExtra("FORCE_START", extras.getBoolean("FORCE_START", false));
                        intent.putExtra("PARAMS", msg.getData());
                        if (!TextUtils.isEmpty(LiteProcessActivity.fromBaseActivity)) {
                            intent.putExtra("FROM_BASE_ACTIVITY", LiteProcessActivity.fromBaseActivity);
                        }
                        Util.b(extras.getString("UID"));
                        LiteProcessClientManager.c(intent);
                        return;
                    }
                    return;
                case 17:
                    LiteProcessActivity.fromBaseActivity = null;
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "WHAT_RESET_FROM_ACTIVITY fromBaseActivity = " + LiteProcessActivity.fromBaseActivity);
                    return;
                case 19:
                    LiteProcessClientManager.h();
                    return;
                case 20:
                    LiteProcessClientManager.d(msg.getData());
                    return;
                case 21:
                    LiteProcessClientManager.e(msg.getData());
                    return;
                case 22:
                    LiteProcessClientManager.b(msg);
                    return;
                default:
                    return;
            }
        }
    }

    static {
        try {
            HandlerThread handlerThread = new HandlerThread("LiteProcessClientManager");
            handlerThread.start();
            f = new Handler(handlerThread.getLooper());
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
        }
    }

    static synchronized void a() {
        synchronized (LiteProcessClientManager.class) {
            if (Util.needSupportLiteProcess()) {
                if (!Util.isLiteProcess()) {
                    LoggerFactory.getTraceLogger().error((String) Const.TAG, "LiteProcessClientManager must be in lite process. " + Log.getStackTraceString(new Throwable()));
                } else if (!d) {
                    d = true;
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessClientManager prepare");
                    if (a == null) {
                        a = new LiteProcessClientHandler(Looper.getMainLooper());
                        IpcMsgClient.registerRspBizHandler(Const.TAG, a);
                    }
                    c.add(LiteProcessActivity.ACTIVITY_CLASSES[Util.getLpid() - 1].getName());
                    IpcMsgClient.prepare();
                    IpcCallClient.prepare();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void d(Bundle extra) {
        try {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "onConfigChanged enter");
            ConfigService configService = (ConfigService) Util.getMicroAppContext().findServiceByInterface(ConfigService.class.getName());
            if (configService != null) {
                HashMap configs = new HashMap();
                configs.put("data_overflow", String.valueOf(extra.getBoolean("data_overflow", false)));
                configs.put("changed_configs", extra.getString("changed_configs"));
                configService.saveConfigs(configs);
            }
        } catch (Throwable throwable) {
            LoggerFactory.getTraceLogger().error(Const.TAG, "onConfigChanged error!", throwable);
        }
    }

    /* access modifiers changed from: private */
    public static void h() {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "reInitUc");
        LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).sendBroadcast(new Intent("uc_init_success_in_main"));
    }

    /* access modifiers changed from: private */
    public static void i() {
        synchronized (LiteProcessClientManager.class) {
            e = true;
            LoggerFactory.getTraceLogger().debug(Const.TAG, "onSrvReady notifyAll");
            LiteProcessClientManager.class.notifyAll();
        }
    }

    /* access modifiers changed from: private */
    public static void b(Message msg) {
        if (msg != null) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "onSrvShow delay: " + msg.arg1);
            Util.a((long) msg.arg1);
        }
    }

    public static Handler getAsyncHandler() {
        return f;
    }

    /* access modifiers changed from: private */
    public static void c(final Intent intent) {
        f.post(new Runnable() {
            public final void run() {
                LiteProcessClientManager.a(intent);
            }
        });
    }

    static synchronized void a(Intent intent) {
        final String sourceAppId;
        final String targetAppId;
        Bundle params;
        boolean forceStart;
        Bundle bundle;
        synchronized (LiteProcessClientManager.class) {
            PerformanceLogger.onTinyAppProcessEvent("main", "LiteProcessClientManager.startApp()");
            try {
                Log.i("mytest", "geth5service: " + Util.getMicroAppContext().findServiceByInterface(H5Service.class.getName()));
                Util.getMicroAppContext().findServiceByInterface("com.alipay.tinybootloader.TinyBootloadService");
                Util.getMicroAppContext().findServiceByInterface(Const.TINY_SERVICE);
            } catch (Throwable t) {
                Log.i("mytest", "register engine step error", t);
            }
            new Bundle();
            if (intent != null) {
                sourceAppId = intent.getStringExtra("SOURCEAPPID");
                targetAppId = intent.getStringExtra("TARGETAPPID");
                params = intent.getBundleExtra("PARAMS");
                if (!hasStartApp) {
                    SharedPreferences sp = Util.getSp();
                    sp.edit().putString("SOURCEAPPID", sourceAppId).apply();
                    sp.edit().putString("TARGETAPPID", targetAppId).apply();
                    sp.edit().putString("PARAMS", Util.a(params)).apply();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessClientManager sp put TARGETAPPID " + targetAppId);
                }
                forceStart = intent.getBooleanExtra("FORCE_START", false);
            } else {
                SharedPreferences sp2 = Util.getSp();
                sourceAppId = sp2.getString("SOURCEAPPID", "");
                targetAppId = sp2.getString("TARGETAPPID", "");
                params = Util.a(sp2.getString("PARAMS", ""));
                params.putInt(Const.KEY_LITE_PROCESS_ID, Util.getLpid());
                forceStart = true;
            }
            if (params == null) {
                params = new Bundle();
                params.putString("appId", targetAppId);
                params.putString(Const.APP_TYPE, intent.getStringExtra(Const.APP_TYPE));
                params.putBoolean(PointCutConstants.REALLY_STARTAPP, intent.getBooleanExtra(PointCutConstants.REALLY_STARTAPP, true));
                params.putString(H5AppHandler.CHECK_KEY, intent.getStringExtra(H5AppHandler.CHECK_KEY));
                params.putBoolean(PointCutConstants.REALLY_DOSTARTAPP, intent.getBooleanExtra(PointCutConstants.REALLY_DOSTARTAPP, false));
            }
            hasStartApp = true;
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessClientManager begin startApp sourceAppId = " + sourceAppId + " targetAppId = " + targetAppId + " params = " + params + " intent = " + intent);
            ApplicationDescription appDescription = new ApplicationDescription();
            appDescription.setAppId(targetAppId);
            appDescription.setEngineType(params.getString(Const.APP_TYPE));
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().addDescription(appDescription);
            if (forceStart) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "forceStart and finish first.");
                Util.getMicroAppContext().finishApp(sourceAppId, targetAppId, params);
            }
            Handler handler = new Handler(Looper.getMainLooper());
            try {
                bundle = new Bundle(params);
            } catch (Throwable t2) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t2));
                bundle = new Bundle();
            }
            final Bundle finalParams = bundle;
            final Intent intent2 = intent;
            handler.post(new Runnable() {
                public final void run() {
                    Util.getMicroAppContext().startApp(sourceAppId, targetAppId, finalParams);
                    PerformanceLogger.init(targetAppId);
                    PerformanceLogger.setPreload(finalParams.getBoolean(Const.PERF_IS_PRELOAD, false));
                    PerformanceLogger.setLocal(finalParams.getBoolean("is_local", true));
                    PerformanceLogger.setPrepareTime(finalParams.getLong("perf_prepare_time", -1));
                    PerformanceLogger.setOpenAppTime(finalParams.getLong("perf_open_app_time", -1));
                    PerformanceLogger.setChInfo(finalParams.getString("chInfo"));
                    PerformanceLogger.setPreloadFrom(finalParams.getString("PRELOAD_FROM"));
                    if (finalParams.getString("chInfo") == null) {
                        if (finalParams.getString(Constants.NORMAL_MA_TYPE_QR) != null) {
                            PerformanceLogger.setChInfo("ch_scan");
                        } else if (finalParams.getString(H5Param.CUSTOM_PARAMS) != null && finalParams.getString(H5Param.CUSTOM_PARAMS).contains("chInfo=app_desktop")) {
                            PerformanceLogger.setChInfo("app_desktop");
                        } else if (finalParams.getString(MicroApplication.KEY_APP_SCENE_ID) != null) {
                            PerformanceLogger.setChInfo("sceneId_" + finalParams.getString(MicroApplication.KEY_APP_SCENE_ID));
                        }
                    }
                    PerformanceLogger.setForegroundStartTime(finalParams.getLong("perf_foreground_start_time", 0));
                    PerformanceLogger.setRpcTime(finalParams.getLong("perf_rpc_time", 0));
                    PerformanceLogger.setTimeFromLaunch(finalParams.getLong("time_from_launch", 0));
                    PerformanceLogger.setIsPreloadWait(finalParams.getBoolean("perf_preload_wait", false));
                    PerformanceLogger.recordAppStart(targetAppId);
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessClientManager end startApp sourceAppId = " + sourceAppId + " targetAppId = " + targetAppId + " params = " + finalParams + " intent = " + intent2);
                }
            });
        }
        return;
    }

    public static synchronized void startAppInLiteStep1(String sourceAppId, String targetAppId, Bundle params) {
        synchronized (LiteProcessClientManager.class) {
            Message msg = Message.obtain();
            msg.what = 21;
            Bundle bundle = new Bundle(params);
            bundle.remove(Const.START_APP_IN_CURRENT_PROCESS);
            bundle.putBoolean(Const.START_APP_IN_LITE, true);
            bundle.putInt("LPID", Util.getLpid());
            bundle.putInt("PID", Process.myPid());
            bundle.putString("SOURCEAPPID_IN_LITE", sourceAppId);
            bundle.putString("TARGETAPPID_IN_LITE", targetAppId);
            if (!TextUtils.isEmpty(LiteProcessActivity.fromBaseActivity)) {
                bundle.putString("FROM_BASE_ACTIVITY_IN_LITE", LiteProcessActivity.fromBaseActivity);
            }
            msg.setData(bundle);
            IpcMsgClient.send(Const.TAG, msg);
            LoggerFactory.getTraceLogger().info(Const.TAG_SAIL, "startAppInLiteStep1@" + Util.getCurrentProcessName() + " finish " + bundle);
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void e(Bundle params) {
        synchronized (LiteProcessClientManager.class) {
            Bundle bundle = new Bundle(params);
            bundle.remove(Const.START_APP_IN_CURRENT_PROCESS);
            bundle.remove(Const.START_APP_IN_LITE);
            String sourceAppId = bundle.getString("SOURCEAPPID_IN_LITE");
            String targetAppId = bundle.getString("TARGETAPPID_IN_LITE");
            if (TextUtils.isEmpty(LiteProcessActivity.fromBaseActivity) && !TextUtils.isEmpty(bundle.getString("FROM_BASE_ACTIVITY_IN_LITE"))) {
                LiteProcessActivity.fromBaseActivity = bundle.getString("FROM_BASE_ACTIVITY_IN_LITE");
            }
            ApplicationDescription appDescription = new ApplicationDescription();
            appDescription.setAppId(targetAppId);
            appDescription.setEngineType(bundle.getString(Const.APP_TYPE));
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().addDescription(appDescription);
            Util.getMicroAppContext().startApp(sourceAppId, targetAppId, bundle);
            LoggerFactory.getTraceLogger().info(Const.TAG_SAIL, "startAppInLiteStep4@" + Util.getCurrentProcessName() + " finish fromBaseActivity " + LiteProcessActivity.fromBaseActivity + Token.SEPARATOR + bundle);
        }
    }

    /* access modifiers changed from: private */
    public static void f(Bundle bundle) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessClientManager startActivity");
        Intent intent = (Intent) bundle.getParcelable("intent");
        if (intent == null) {
            intent = new Intent();
        }
        Context launchContext = (Context) Util.getMicroAppContext().getTopActivity().get();
        if (launchContext == null) {
            launchContext = Util.getContext();
            intent.setFlags(268435456);
        }
        launchContext.startActivity(intent);
    }

    public static void waitIfNeeded() {
        j();
        k();
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void j() {
        /*
            boolean r1 = com.alipay.mobile.liteprocess.LiteProcessPipeline.a
            if (r1 != 0) goto L_0x0027
            java.lang.Class<com.alipay.mobile.liteprocess.LiteProcessPipeline> r2 = com.alipay.mobile.liteprocess.LiteProcessPipeline.class
            monitor-enter(r2)
            boolean r1 = com.alipay.mobile.liteprocess.LiteProcessPipeline.a     // Catch:{ all -> 0x0046 }
            if (r1 != 0) goto L_0x0026
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Exception -> 0x0028 }
            java.lang.String r3 = "LiteProcess"
            java.lang.String r4 = "LiteProcessClientManager begin wait pipeline"
            r1.debug(r3, r4)     // Catch:{ Exception -> 0x0028 }
            java.lang.Class<com.alipay.mobile.liteprocess.LiteProcessPipeline> r1 = com.alipay.mobile.liteprocess.LiteProcessPipeline.class
            r1.wait()     // Catch:{ Exception -> 0x0028 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Exception -> 0x0028 }
            java.lang.String r3 = "LiteProcess"
            java.lang.String r4 = "LiteProcessClientManager end wait pipeline"
            r1.debug(r3, r4)     // Catch:{ Exception -> 0x0028 }
        L_0x0026:
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
        L_0x0027:
            return
        L_0x0028:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0046 }
            java.lang.String r3 = "LiteProcess"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0046 }
            java.lang.String r5 = "LiteProcessClientManager wait pipeline error "
            r4.<init>(r5)     // Catch:{ all -> 0x0046 }
            java.lang.String r5 = android.util.Log.getStackTraceString(r0)     // Catch:{ all -> 0x0046 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0046 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0046 }
            r1.error(r3, r4)     // Catch:{ all -> 0x0046 }
            goto L_0x0026
        L_0x0046:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.liteprocess.LiteProcessClientManager.j():void");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void k() {
        /*
            boolean r1 = e
            if (r1 != 0) goto L_0x0027
            java.lang.Class<com.alipay.mobile.liteprocess.LiteProcessClientManager> r2 = com.alipay.mobile.liteprocess.LiteProcessClientManager.class
            monitor-enter(r2)
            boolean r1 = e     // Catch:{ all -> 0x0046 }
            if (r1 != 0) goto L_0x0026
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Exception -> 0x0028 }
            java.lang.String r3 = "LiteProcess"
            java.lang.String r4 = "LiteProcessClientManager begin wait srv"
            r1.debug(r3, r4)     // Catch:{ Exception -> 0x0028 }
            java.lang.Class<com.alipay.mobile.liteprocess.LiteProcessClientManager> r1 = com.alipay.mobile.liteprocess.LiteProcessClientManager.class
            r1.wait()     // Catch:{ Exception -> 0x0028 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Exception -> 0x0028 }
            java.lang.String r3 = "LiteProcess"
            java.lang.String r4 = "LiteProcessClientManager end wait srv"
            r1.debug(r3, r4)     // Catch:{ Exception -> 0x0028 }
        L_0x0026:
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
        L_0x0027:
            return
        L_0x0028:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0046 }
            java.lang.String r3 = "LiteProcess"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0046 }
            java.lang.String r5 = "LiteProcessClientManager wait srv error "
            r4.<init>(r5)     // Catch:{ all -> 0x0046 }
            java.lang.String r5 = android.util.Log.getStackTraceString(r0)     // Catch:{ all -> 0x0046 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0046 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0046 }
            r1.error(r3, r4)     // Catch:{ all -> 0x0046 }
            goto L_0x0026
        L_0x0046:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0046 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.liteprocess.LiteProcessClientManager.k():void");
    }

    static void a(Class clazz) {
        b.add(clazz);
    }

    static void a(Class clazz, int lpid) {
        if (lpid == Util.getLpid()) {
            c.add(clazz.getName());
        }
    }

    static synchronized void a(LiteClient liteClient) {
        synchronized (LiteProcessClientManager.class) {
            g.add(liteClient);
        }
    }

    public static Set<Class> getHookBackKeyBlackList() {
        return b;
    }

    public static void stopSelfByClient() {
        LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "LiteProcessClientManager stopSelfByClient");
        f.postDelayed(new Runnable() {
            public final void run() {
                Util.a(LiteProcessClientManager.c);
                LiteProcessClientManager.c();
                LoggerFactory.getLogContext().flush("applog", true);
                Process.killProcess(Process.myPid());
            }
        }, 500);
    }

    static void b() {
        LoggerFactory.getTraceLogger().warn(Const.TAG, "LiteProcessClientManager stopSelfByServer, print stack trace, not exception:", new Throwable());
        f.post(new Runnable() {
            public final void run() {
                Message msg = Message.obtain();
                msg.what = 15;
                msg.arg1 = Util.getLpid();
                msg.arg2 = Process.myPid();
                IpcMsgClient.send(Const.TAG, msg);
                LoggerFactory.getLogContext().flush("applog", true);
            }
        });
    }

    static synchronized void c() {
        synchronized (LiteProcessClientManager.class) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessClientManager destoryClient before");
            for (LiteClient onClientDestory : g) {
                onClientDestory.onClientDestory();
            }
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessClientManager destoryClient");
        }
    }

    static void d() {
        try {
            Log.i("mytest", "preload step 1");
            Object obj = Util.getMicroAppContext().findServiceByInterface("com.alipay.mobile.framework.service.ext.openplatform.service.AppManageService");
            Log.i("mytest", "preload step 2: " + obj.getClass().getDeclaredMethod("getAppById", new Class[]{String.class}).invoke(obj, new Object[]{"1"}));
            ((H5Service) Util.getMicroAppContext().findServiceByInterface(H5Service.class.getName())).preLoadInTinyProcess();
            if (Config.k) {
                Util.getMicroAppContext().findServiceByInterface("com.alipay.tinybootloader.TinyBootloadService");
                Object tinyService = Util.getMicroAppContext().findServiceByInterface(Const.TINY_SERVICE);
                tinyService.getClass().getDeclaredMethod("preload", new Class[0]).invoke(tinyService, new Object[0]);
            }
            Log.i("mytest", "preload step 3");
        } catch (Throwable t) {
            Log.i("mytest", "preload error", t);
        }
    }

    public static void onLiteProcessPreloadComplete() {
        Message msg = Message.obtain();
        msg.what = 18;
        msg.arg1 = Process.myPid();
        msg.arg2 = Util.getLpid();
        IpcMsgClient.send(Const.TAG, msg);
        PerformanceLogger.setPreloadCompleted();
        TinyAppClassPreloader.preloadClasses();
    }

    public static Set<String> getBaseClassNames() {
        return c;
    }
}
