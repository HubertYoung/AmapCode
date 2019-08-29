package com.alipay.mobile.liteprocess.advice;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Printer;
import com.ali.auth.third.login.LoginConstants;
import com.alipay.android.phone.mobilesdk.apm.base.MainLooperLogger;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.liteprocess.Config;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.liteprocess.LiteProcessServerManager;
import com.alipay.mobile.liteprocess.Util;
import com.alipay.mobile.liteprocess.perf.PerformanceLogger;
import com.alipay.mobile.monitor.api.MonitorFactory;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SaoyisaoStartAdvice implements Advice {
    private static SaoyisaoStartAdvice a;
    private static boolean b = false;
    private Timer c;
    private TimerTask d;
    /* access modifiers changed from: private */
    public MonitorPrinter e = new MonitorPrinter();
    /* access modifiers changed from: private */
    public Handler f = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public String h;
    /* access modifiers changed from: private */
    public long i;
    /* access modifiers changed from: private */
    public boolean j;
    /* access modifiers changed from: private */
    public boolean k = false;
    private ActivityLifecycle l;

    class ActivityLifecycle implements ActivityLifecycleCallbacks {
        private boolean b;
        private final int c;
        private int d;

        private ActivityLifecycle() {
            this.c = 4;
        }

        /* synthetic */ ActivityLifecycle(SaoyisaoStartAdvice x0, byte b2) {
            this();
        }

        public boolean isRegistered() {
            return this.b;
        }

        public void register() {
            try {
                LauncherApplicationAgent.getInstance().getApplicationContext().unregisterActivityLifecycleCallbacks(this);
                LauncherApplicationAgent.getInstance().getApplicationContext().registerActivityLifecycleCallbacks(this);
                this.b = true;
            } catch (Throwable thr) {
                LoggerFactory.getTraceLogger().warn(Const.TAG, "SaoyisaoStartAdvice register ActivityLifecycle error!", thr);
            }
        }

        public void unregister() {
            try {
                LauncherApplicationAgent.getInstance().getApplicationContext().unregisterActivityLifecycleCallbacks(this);
                this.b = false;
                this.d = 0;
                SaoyisaoStartAdvice.this.j = false;
            } catch (Throwable thr) {
                LoggerFactory.getTraceLogger().warn(Const.TAG, "SaoyisaoStartAdvice unregister ActivityLifecycle error!", thr);
            }
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
            try {
                this.d++;
                if (this.d >= 4) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice mResumeCount max");
                    unregister();
                    return;
                }
                Intent intent = activity.getIntent();
                if (intent == null) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice intent = null");
                    return;
                }
                if (activity.getClass().toString().endsWith("LocalViewActivity")) {
                    String requestData = intent.getStringExtra("requestData");
                    int timeStartIndex = requestData.indexOf("\"codeScanTime\":\"");
                    String time = requestData.substring(timeStartIndex + 16, requestData.indexOf("\",", timeStartIndex));
                    if (time != null && time.equals(SaoyisaoStartAdvice.this.g)) {
                        PerformanceLogger.logSaoyisaoStartAppPerf("pay", SystemClock.elapsedRealtime() - SaoyisaoStartAdvice.this.i, SaoyisaoStartAdvice.this.j);
                    }
                } else if (activity.getClass().toString().endsWith("H5Activity")) {
                    String url = intent.getExtras().getString("url");
                    if (url != null && url.equals(SaoyisaoStartAdvice.this.h)) {
                        PerformanceLogger.logSaoyisaoStartAppPerf(LoginConstants.H5_LOGIN, SystemClock.elapsedRealtime() - SaoyisaoStartAdvice.this.i, SaoyisaoStartAdvice.this.j);
                    }
                } else {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice activity not match");
                    return;
                }
                unregister();
            } catch (Throwable thr) {
                unregister();
                LoggerFactory.getTraceLogger().warn(Const.TAG, "SaoyisaoStartAdvice onActivityResumed error!", thr);
            }
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }

    class MonitorPrinter implements Printer {
        /* access modifiers changed from: private */
        public long b;
        /* access modifiers changed from: private */
        public long c;
        /* access modifiers changed from: private */
        public int d;
        /* access modifiers changed from: private */
        public int e;
        /* access modifiers changed from: private */
        public LinkedList<Long> f;
        /* access modifiers changed from: private */
        public LinkedList<Long> g;
        /* access modifiers changed from: private */
        public long h;

        MonitorPrinter() {
            this.b = 0;
            this.d = 0;
            this.e = 0;
            this.f = new LinkedList<>();
            this.g = new LinkedList<>();
            this.b = SystemClock.elapsedRealtime();
        }

        public void println(String x) {
            try {
                if (!TextUtils.isEmpty(x)) {
                    if (x.charAt(0) == '>') {
                        this.b = SystemClock.elapsedRealtime();
                    } else if (x.charAt(0) == '<' && this.b != 0) {
                        long timeCost = SystemClock.elapsedRealtime() - this.b;
                        if (timeCost > 30 && timeCost <= 100) {
                            this.f.addFirst(Long.valueOf(SystemClock.elapsedRealtime()));
                            if (SystemClock.elapsedRealtime() - this.c < 5000) {
                                this.d++;
                            }
                        } else if (timeCost > 100) {
                            this.g.addFirst(Long.valueOf(SystemClock.elapsedRealtime()));
                            if (SystemClock.elapsedRealtime() - this.c < 5000) {
                                this.e++;
                            }
                        }
                    }
                }
            } catch (Throwable thr) {
                LoggerFactory.getTraceLogger().warn(Const.TAG, "SaoyisaoStartAdvice MonitorPrinter println error!", thr);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            SaoyisaoStartAdvice.this.f.post(new Runnable() {
                public void run() {
                    try {
                        MonitorPrinter.this.b = 0;
                        MonitorPrinter.this.c = SystemClock.elapsedRealtime();
                        MonitorPrinter.this.d = 0;
                        MonitorPrinter.this.e = 0;
                        MonitorPrinter.this.h = SystemClock.elapsedRealtime() - MonitorFactory.getTimestampInfo().getProcessCurrentLaunchElapsedTime();
                        MainLooperLogger.getInstance().addMessageLogging(SaoyisaoStartAdvice.this.e);
                    } catch (Throwable thr) {
                        LoggerFactory.getTraceLogger().warn(Const.TAG, "SaoyisaoStartAdvice register MonitorPrinter error!", thr);
                    }
                }
            });
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            SaoyisaoStartAdvice.this.f.post(new Runnable() {
                public void run() {
                    try {
                        MonitorPrinter.this.g.clear();
                        MonitorPrinter.this.f.clear();
                        MainLooperLogger.getInstance().removeMessageLogging(SaoyisaoStartAdvice.this.e);
                        PerformanceLogger.logSaoyisaoMonitorResult(MonitorPrinter.this.d, MonitorPrinter.this.e, SaoyisaoStartAdvice.this.k, SaoyisaoStartAdvice.this.j, SystemClock.elapsedRealtime() - MonitorPrinter.this.c, MonitorPrinter.this.h);
                        MonitorPrinter.this.c = 0;
                    } catch (Throwable thr) {
                        LoggerFactory.getTraceLogger().warn(Const.TAG, "SaoyisaoStartAdvice unregister MonitorPrinter error!", thr);
                    }
                }
            });
        }
    }

    class MonitorTask extends TimerTask {
        private MonitorTask() {
        }

        /* synthetic */ MonitorTask(SaoyisaoStartAdvice x0, byte b) {
            this();
        }

        public void run() {
            try {
                if (!H5Utils.SCAN_APP_ID.equals(LauncherApplicationAgent.getInstance().getMicroApplicationContext().findTopRunningApp().getAppId())) {
                    SaoyisaoStartAdvice.this.d();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice task canceled saoyisao exit");
                    return;
                }
                if (LiteProcessServerManager.g().hasPreloadProcess()) {
                    SaoyisaoStartAdvice.this.k = false;
                }
                if (SaoyisaoStartAdvice.this.k && SaoyisaoStartAdvice.this.a()) {
                    LoggerFactory.getTraceLogger().warn((String) Const.TAG, (String) "SaoyisaoStartAdvicestartLiteProcess urgently");
                    SaoyisaoStartAdvice.this.j = true;
                    LiteProcessServerManager.g().startLiteProcessAsync(-1);
                    SaoyisaoStartAdvice.this.d();
                }
                if (!SaoyisaoStartAdvice.this.k && SystemClock.elapsedRealtime() - SaoyisaoStartAdvice.this.e.c >= 5000) {
                    SaoyisaoStartAdvice.this.d();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice task canceled time out");
                }
            } catch (Throwable th) {
            }
        }
    }

    public static void register() {
        try {
            b = Util.getSp().getBoolean("KEY_STARTED_FROM_SAOYISAO", false);
            b();
        } catch (Throwable thr) {
            LoggerFactory.getTraceLogger().warn(Const.TAG, "register SaoyisaoStartAdvice error!", thr);
        }
    }

    private static void b() {
        if (Config.SAOYISAO_MONITOR_CONFIG && a == null) {
            synchronized (SaoyisaoStartAdvice.class) {
                if (a == null) {
                    a = new SaoyisaoStartAdvice();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "doRegister SaoyisaoStartAdvice");
                    FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTAPP, (Advice) a);
                }
            }
        }
    }

    public void onCallBefore(String s, Object o, Object[] objects) {
    }

    public Pair<Boolean, Object> onCallAround(String s, Object o, Object[] objects) {
        return null;
    }

    public void onCallAfter(String s, Object o, Object[] objects) {
    }

    public void onExecutionBefore(String s, Object o, Object[] objects) {
    }

    public Pair<Boolean, Object> onExecutionAround(String s, Object o, Object[] objects) {
        return null;
    }

    public void onExecutionAfter(String pointCut, Object thisPoint, final Object[] args) {
        TaskScheduleService scheduleService = (TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName());
        if (scheduleService != null) {
            scheduleService.schedule(new Runnable() {
                public void run() {
                    SaoyisaoStartAdvice.this.a(args);
                }
            }, "SaoyisaoStartAdvice", 0, TimeUnit.MICROSECONDS);
        }
    }

    /* access modifiers changed from: private */
    public void a(Object[] args) {
        boolean z;
        String targetAppId = "";
        if (args != null) {
            try {
                if (args.length >= 3 && (args[1] instanceof String)) {
                    targetAppId = args[1];
                }
            } catch (Throwable th) {
                return;
            }
        }
        if (H5Utils.SCAN_APP_ID.equals(targetAppId)) {
            if (this.c != null) {
                this.c.cancel();
            }
            this.c = null;
            this.d = null;
            this.j = false;
            e();
            this.e.a();
            this.c.schedule(this.d, 2000, 200);
            if (!Config.NEED_LITE || LiteProcessServerManager.g().hasPreloadProcess() || !Config.PRELOAD_WHEN_START_SAOYISAO || !b) {
                z = false;
            } else {
                z = true;
            }
            this.k = z;
            LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice 10000007 schedule task mIsMonitorForPreload = " + this.k + ", has preloaded lite process = " + LiteProcessServerManager.g().hasPreloadProcess() + ", sHasStartedFromSaoyisao = " + b + ", PRELOAD_WHEN_START_SAOYISAO = " + Config.PRELOAD_WHEN_START_SAOYISAO);
        } else if ("20001001".equals(targetAppId) && (args[2] instanceof Bundle)) {
            String pageData = args[2].getString("pageData");
            if (pageData != null) {
                int timeStartIndex = pageData.indexOf("\"codeScanTime\":\"");
                if (timeStartIndex >= 0) {
                    this.g = pageData.substring(timeStartIndex + 16, pageData.indexOf("\",", timeStartIndex));
                    if (TextUtils.isEmpty(this.g)) {
                        this.g = null;
                        return;
                    }
                    this.i = SystemClock.elapsedRealtime();
                    c();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice 20001001 monitor activity");
                }
            }
        } else if ("20000067".equals(targetAppId) && (args[2] instanceof Bundle)) {
            Bundle params = args[2];
            if (H5Param.SCAN_APP.equals(params.getString(H5Param.LONG_BIZ_SCENARIO))) {
                this.h = params.getString("url");
                if (!TextUtils.isEmpty(this.h)) {
                    this.i = SystemClock.elapsedRealtime();
                    c();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice 20000067 monitor activity");
                }
            }
        } else if ("66666714".equals(targetAppId) && (args[2] instanceof Bundle)) {
            this.h = args[2].getString("url");
            if (!TextUtils.isEmpty(this.h) && this.h.contains("TRANSFER_QRCODE_PAY") && this.h.contains("codeScanTime")) {
                this.i = SystemClock.elapsedRealtime();
                c();
                LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice 66666714 monitor activity");
            }
        }
    }

    private void c() {
        if (this.l == null) {
            synchronized (SaoyisaoStartAdvice.class) {
                if (this.l == null) {
                    this.l = new ActivityLifecycle(this, 0);
                }
            }
        }
        this.l.register();
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
        }
        this.d = null;
        this.e.b();
    }

    private void e() {
        if (this.c == null) {
            this.c = new Timer();
        }
        if (this.d == null) {
            this.d = new MonitorTask(this, 0);
        }
    }

    public static void tinyAppStartedFromSaoyisao() {
        try {
            if (!b) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "SaoyisaoStartAdvice tinyAppStartedFromSaoyisao");
                b = true;
                Util.getSp().edit().putBoolean("KEY_STARTED_FROM_SAOYISAO", true).apply();
            }
        } catch (Throwable thr) {
            LoggerFactory.getTraceLogger().warn(Const.TAG, "tinyAppStartedFromSaoyisao error!", thr);
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean a() {
        int total = 0;
        long currentTime = SystemClock.elapsedRealtime();
        Iterator it = ((LinkedList) this.e.f.clone()).iterator();
        while (it.hasNext() && currentTime - ((Long) it.next()).longValue() < 2000) {
            total++;
        }
        if (total > Config.MAX_TASKS_30_TO_100) {
            return false;
        }
        int total2 = 0;
        Iterator it2 = ((LinkedList) this.e.g.clone()).iterator();
        while (it2.hasNext() && currentTime - ((Long) it2.next()).longValue() < 2000) {
            total2++;
        }
        if (total2 > Config.MAX_TASKS_100_TO_MORE) {
            return false;
        }
        return true;
    }
}
