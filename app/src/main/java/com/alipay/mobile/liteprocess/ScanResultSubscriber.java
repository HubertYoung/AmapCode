package com.alipay.mobile.liteprocess;

import android.text.TextUtils;
import com.alipay.android.phone.scancode.export.ScanOuterNotice;
import com.alipay.android.phone.scancode.export.ScanOuterNotice.Subscriber;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import java.util.concurrent.TimeUnit;

public class ScanResultSubscriber implements Subscriber {
    /* access modifiers changed from: private */
    public static ScanResultSubscriber a;

    public static void register() {
        if (a == null && Config.s) {
            try {
                TaskScheduleService scheduleService = (TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName());
                if (scheduleService != null) {
                    scheduleService.schedule(new Runnable() {
                        public final void run() {
                            try {
                                ScanOuterNotice scanService = (ScanOuterNotice) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ScanOuterNotice.class.getName());
                                if (scanService != null) {
                                    ScanResultSubscriber.a = new ScanResultSubscriber();
                                    scanService.subscribe("tinyapp", ScanResultSubscriber.a);
                                    LoggerFactory.getTraceLogger().debug(Const.TAG, "ScanResultSubscriber registered");
                                }
                            } catch (Throwable thr) {
                                LoggerFactory.getTraceLogger().error(Const.TAG, "ScanOuterNotice register error!", thr);
                            }
                        }
                    }, "SaoyisaoStartAdvice", 0, TimeUnit.MICROSECONDS);
                }
            } catch (Throwable thr) {
                LoggerFactory.getTraceLogger().error(Const.TAG, "ScanOuterNotice register error!!!", thr);
            }
        }
    }

    public void notifyCode(String s) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, "scan notifyCode = " + s);
        if (TextUtils.isEmpty(s)) {
            return;
        }
        if ((s.startsWith("http://c3x.me/") || s.startsWith("http://ofo.so/") || s.contains("ymobile-cn.ankerjiedian.com") || s.contains("ali.imlaidian.com")) && Config.NEED_LITE && !LiteProcessServerManager.g().hasPreloadProcess()) {
            LiteProcessServerManager.g().startLiteProcessAsync(-5);
            LoggerFactory.getTraceLogger().debug(Const.TAG, "scan result recognized as tinyApp, start lite process");
        }
    }
}
