package com.alipay.mobile.liteprocess;

import android.content.Context;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.ReflectUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.advice.ActivityBackAdvice;
import com.alipay.mobile.liteprocess.advice.ActivityCreateAdvice;
import com.alipay.mobile.liteprocess.advice.ActivityKeyEventAdvice;
import com.alipay.mobile.liteprocess.advice.ActivityResumeAdvice;
import com.alipay.mobile.liteprocess.advice.PhoneCashierStartActivityAdvice;
import com.alipay.mobile.liteprocess.advice.SaoyisaoStartAdvice;
import com.alipay.mobile.liteprocess.advice.StartActivityAdvice;
import com.alipay.mobile.liteprocess.advice.StartAppAdvice;
import com.alipay.mobile.liteprocess.advice.VerifyIdentityStartActivityAdvice;
import com.alipay.mobile.logging.TinyLoggingConfigPlugin;
import com.alipay.mobile.nebula.config.H5PluginConfig;
import java.util.concurrent.TimeUnit;

public class LiteProcessPipeline implements Runnable {
    static boolean a = false;
    private static boolean b = false;

    public void run() {
        if (Util.needSupportLiteProcess()) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessPipeline run at " + Util.getCurrentProcessName());
            Config.syncConfig();
            if (!Util.isMainProcess() || Config.NEED_LITE) {
                ScanResultSubscriber.register();
                a();
                a = true;
                if (Util.isMainProcess()) {
                    LiteProcessServerManager.g().c();
                }
            }
        }
    }

    private static void a() {
        if (!Config.y) {
            try {
                ReflectUtil.invokeMethod("com.alipay.mobile.quinox.startup.StartupSafeguard", "setConservativeStartupLite", null, ReflectUtil.invokeMethod("com.alipay.mobile.quinox.startup.StartupSafeguard", "getInstance", null, null, null), null);
            } catch (Throwable thr) {
                LoggerFactory.getTraceLogger().error(Const.TAG, "disable hook dvm error!", thr);
            }
        }
    }

    private static void b() {
        if (Util.isLiteProcess()) {
            LiteProcessClientManager.getAsyncHandler().postDelayed(new Runnable() {
                public final void run() {
                    try {
                        ReflectUtil.invokeMethod("com.alipay.mobile.quinox.LauncherApplication", "stopHookDvm", new Class[]{Boolean.TYPE}, LauncherApplicationAgent.getInstance().getApplicationContext(), new Object[]{Boolean.valueOf(false)});
                    } catch (Throwable thr) {
                        LoggerFactory.getTraceLogger().error(Const.TAG, "LiteProcessPipeline stopHookDvm error!", thr);
                    }
                }
            }, 5000);
        }
    }

    public static synchronized void litePipelineRun(Context context) {
        synchronized (LiteProcessPipeline.class) {
            if (Util.isMainProcess() || Util.isLiteProcess()) {
                if (a) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "litePipelineRun already");
                } else {
                    Util.setContext(context.getApplicationContext());
                    LiteProcessClientManager.a();
                    Config.syncConfig();
                    registerAdvice(context);
                    c();
                    d();
                    a = true;
                    synchronized (LiteProcessPipeline.class) {
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "LiteProcessPipeline notifyAll");
                        LiteProcessPipeline.class.notifyAll();
                    }
                    b();
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "litePipelineRun over");
                }
            }
        }
    }

    public static void registerAdvice(Context context) {
        Util.setContext(context.getApplicationContext());
        if (!b && Config.NEED_LITE) {
            try {
                StartAppAdvice.register();
                if (Util.needSupportLiteProcess()) {
                    if (Util.isMainProcess()) {
                        SaoyisaoStartAdvice.register();
                        StartActivityAdvice.register();
                        VerifyIdentityStartActivityAdvice.register();
                        PhoneCashierStartActivityAdvice.register();
                    } else if (Util.isLiteProcess()) {
                        LiteProcessClientManager.a();
                        ActivityCreateAdvice.register();
                        ActivityBackAdvice.register();
                        ActivityKeyEventAdvice.register();
                        ActivityResumeAdvice.register();
                    }
                }
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
            }
            b = true;
            LoggerFactory.getTraceLogger().debug(Const.TAG, "registerAdvice@" + Util.getCurrentProcessName());
        }
    }

    private static void c() {
        try {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "registerTinyLog begin @" + Util.getCurrentProcessName());
            H5PluginConfig tinyLoggingConfigPlugin = new H5PluginConfig();
            tinyLoggingConfigPlugin.bundleName = "android-phone-businesscommon-commonbiz";
            tinyLoggingConfigPlugin.className = "com.alipay.mobile.logging.TinyLoggingConfigPlugin";
            tinyLoggingConfigPlugin.scope = "page";
            tinyLoggingConfigPlugin.setEvents(TinyLoggingConfigPlugin.ACTION_TRACKER_CONFIG);
            H5Service h5Service = (H5Service) Util.getMicroAppContext().getExtServiceByInterface(H5Service.class.getName());
            if (h5Service != null) {
                h5Service.addPluginConfig(tinyLoggingConfigPlugin);
                LoggerFactory.getTraceLogger().info("ConfigPluginValve", "h5Service addPluginConfig");
            }
            LoggerFactory.getTraceLogger().debug(Const.TAG, "registerTinyLog end @" + Util.getCurrentProcessName());
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
        }
    }

    private static void d() {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "otherPipelineRun begin");
        TaskScheduleService taskService = (TaskScheduleService) Util.getMicroAppContext().findServiceByInterface(TaskScheduleService.class.getName());
        if (taskService == null) {
            LoggerFactory.getTraceLogger().error((String) Const.TAG, (String) "TaskScheduleService is NULL");
            return;
        }
        for (final String otherPipeline : Config.a) {
            taskService.schedule(new Runnable() {
                public final void run() {
                    try {
                        Object object = Class.forName(otherPipeline).newInstance();
                        if (!(object instanceof Runnable)) {
                            LoggerFactory.getTraceLogger().debug(Const.TAG, "otherPipelineRun is not runnable " + otherPipeline);
                            return;
                        }
                        ((Runnable) object).run();
                        LoggerFactory.getTraceLogger().debug(Const.TAG, "otherPipelineRun run " + otherPipeline);
                    } catch (Throwable t) {
                        LoggerFactory.getTraceLogger().error((String) Const.TAG, Log.getStackTraceString(t));
                    }
                }
            }, "otherPipelineRun", 0, TimeUnit.MILLISECONDS);
        }
        LoggerFactory.getTraceLogger().debug(Const.TAG, "otherPipelineRun end");
    }
}
