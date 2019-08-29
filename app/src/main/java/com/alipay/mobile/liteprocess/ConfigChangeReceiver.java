package com.alipay.mobile.liteprocess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ConfigChangeReceiver extends BroadcastReceiver {
    private static boolean a = false;

    public static void register() {
        if (!a) {
            LoggerFactory.getTraceLogger().debug("ConfigChangeReceiver", "ConfigChangeReceiver register enter");
            a = true;
            ConfigService configService = (ConfigService) Util.getMicroAppContext().findServiceByInterface(ConfigService.class.getName());
            if (configService != null) {
                if (!String.valueOf(Config.m).equalsIgnoreCase(configService.getConfig("lite_config_load_local_sp"))) {
                    HashMap configs = new HashMap();
                    configs.put("lite_config_load_local_sp", String.valueOf(Config.m));
                    configService.saveConfigs(configs);
                }
            }
            if (Config.m) {
                LoggerFactory.getTraceLogger().debug("ConfigChangeReceiver", "ConfigChangeReceiver do register register");
                LocalBroadcastManager.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).registerReceiver(new ConfigChangeReceiver(), new IntentFilter("com.alipay.mobile.client.CONFIG_CHANGE"));
            }
            LoggerFactory.getTraceLogger().debug("ConfigChangeReceiver", "ConfigChangeReceiver register leave");
        }
    }

    public void onReceive(Context context, final Intent intent) {
        TaskScheduleService scheduleService = (TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName());
        if (scheduleService != null) {
            scheduleService.schedule(new Runnable() {
                public void run() {
                    try {
                        if (intent != null && intent.getExtras() != null) {
                            LiteProcessServerManager.g();
                            LiteProcessServerManager.a(intent.getExtras());
                        }
                    } catch (Throwable thr) {
                        LoggerFactory.getTraceLogger().warn("LiteProcess.ConfigChangeReceiver", "notify config change error!", thr);
                    }
                }
            }, "ConfigChangeReceiver_notifyConfigChanged", 0, TimeUnit.MICROSECONDS);
        }
    }
}
