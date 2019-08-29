package com.alipay.mobile.liteprocess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogEvent;
import com.alipay.mobile.common.logging.api.LogEvent.Level;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.msg.MsgCodeConstants;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.liteprocess.ipc.IpcMsgClient;
import com.alipay.mobile.monitor.api.MonitorFactory;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.concurrent.TimeUnit;

public class HostInfoReceiver extends BroadcastReceiver {
    private static String a = "com.alipay.security.login";
    private static String b = "com.alipay.security.logout";
    private static String c = "switchaccount";
    private static long d = 0;
    private static long e;

    public void onReceive(Context context, final Intent intent) {
        Util.setContext(context.getApplicationContext());
        if (Util.needSupportLiteProcess()) {
            try {
                ((TaskScheduleService) Util.getMicroAppContext().findServiceByInterface(TaskScheduleService.class.getName())).schedule(new Runnable() {
                    public void run() {
                        HostInfoReceiver.b(intent);
                    }
                }, "HostInfoReceiver", 0, TimeUnit.SECONDS);
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().warn((String) Const.TAG, t);
                b(intent);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(Intent intent) {
        if (Util.isMainProcess()) {
            c(intent);
        } else if (Util.isLiteProcess()) {
            d(intent);
        }
    }

    private static void c(Intent intent) {
        String enventString = intent.getAction();
        if (b.equalsIgnoreCase(enventString) || (a.equalsIgnoreCase(enventString) && intent.getBooleanExtra(c, false))) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "HostInfoReceiver " + enventString + Token.SEPARATOR + Util.getCurrentProcessName());
            if (Config.x && b.equalsIgnoreCase(enventString)) {
                LiteProcessServerManager.g().b();
            }
            if (Util.getSp().getBoolean("FIRST_LOGIN", true)) {
                Util.getSp().edit().putBoolean("FIRST_LOGIN", false).apply();
                LoggerFactory.getTraceLogger().debug(Const.TAG, "FIRST_LOGIN and not stop all");
                return;
            }
            LoggerFactory.getTraceLogger().debug(Const.TAG, "Not FIRST_LOGIN and stop all");
            LiteProcessServerManager.g().a();
        } else if ("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND".equalsIgnoreCase(enventString) || (MsgCodeConstants.FRAMEWORK_ACTIVITY_RESUME.equalsIgnoreCase(enventString) && LiteProcessServerManager.a)) {
            if (LiteProcessServerManager.a) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "HostInfoReceiver FRAMEWORK_BROUGHT_TO_FOREGROUND " + Util.getCurrentProcessName());
                if (!LiteProcessPipeline2.a && Config.j && SystemClock.elapsedRealtime() - MonitorFactory.getTimestampInfo().getProcessCurrentLaunchElapsedTime() > 10000) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "hot startup make pipelineOver true");
                    LiteProcessPipeline2.a = true;
                } else if (!LiteProcessPipeline2.a && Config.p) {
                    LoggerFactory.getTraceLogger().debug(Const.TAG, "FIRST_FOREGROUND_QUICK_PRELOAD");
                    LiteProcessPipeline2.a();
                }
                e = SystemClock.elapsedRealtime();
                LiteProcessServerManager.a = false;
                LiteProcessServerManager.g().e();
            }
        } else if ("com.alipay.mobile.framework.USERLEAVEHINT".equalsIgnoreCase(enventString) || MsgCodeConstants.FRAMEWORK_ACTIVITY_ALL_STOPPED.equalsIgnoreCase(enventString)) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "HostInfoReceiver FRAMEWORK_ACTIVITY_USERLEAVEHINT " + Util.getCurrentProcessName());
            if (!LiteProcessServerManager.a) {
                LiteProcessServerManager.a = true;
                LiteProcessServerManager.g().d();
            }
        }
    }

    private static void d(Intent intent) {
        String enventString = intent.getAction();
        if ("com.alipay.mobile.framework.BROUGHT_TO_FOREGROUND".equalsIgnoreCase(enventString)) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "HostInfoReceiver FRAMEWORK_BROUGHT_TO_FOREGROUND " + Util.getCurrentProcessName());
            Message msg = Message.obtain();
            msg.what = 12;
            msg.arg1 = Process.myPid();
            msg.arg2 = Util.getLpid();
            IpcMsgClient.send(Const.TAG, msg);
            if (System.currentTimeMillis() - d > TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL) {
                LoggerFactory.getTraceLogger().debug(Const.TAG, "refresh log session");
                LoggerFactory.getLogContext().appendLogEvent(new LogEvent("refreshSession", null, Level.ERROR, null));
                d = System.currentTimeMillis();
            }
        } else if ("com.alipay.mobile.framework.USERLEAVEHINT".equalsIgnoreCase(enventString)) {
            LoggerFactory.getTraceLogger().debug(Const.TAG, "HostInfoReceiver FRAMEWORK_ACTIVITY_USERLEAVEHINT " + Util.getCurrentProcessName());
            Message msg2 = Message.obtain();
            msg2.what = 11;
            msg2.arg1 = Process.myPid();
            msg2.arg2 = Util.getLpid();
            IpcMsgClient.send(Const.TAG, msg2);
            LoggerFactory.getLogContext().notifyClientEvent(LogContext.ENVENT_GOTOBACKGROUND, null);
        }
    }

    static long a() {
        return e;
    }
}
