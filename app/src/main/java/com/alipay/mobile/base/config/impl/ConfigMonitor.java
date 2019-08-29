package com.alipay.mobile.base.config.impl;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.base.config.ConfigService.ConfigSyncReporter;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class ConfigMonitor {
    public static final String CONFIGSDK_NOTIFY = "CONFIGSDK-NOTIFY";
    private static ConfigMonitor INSTANCE = null;
    public static final String MONITOR_KEY = "apconfigDrill";
    public static final String SEEDID = "APConfig_SeedId";
    private static final String SP_NAME = "ConfigMonitor";
    private static final String TAG = "ConfigMonitor";
    public static final String UCID = "APConfig_UC";
    private ConfigService configService = null;
    private ConfigSyncReporter mConfigSyncReporter;
    private Editor mEditor = this.mSharedPreferences.edit();
    private SharedPreferences mSharedPreferences = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext().getSharedPreferences("ConfigMonitor", 0);

    public static synchronized ConfigMonitor getInstance() {
        ConfigMonitor configMonitor;
        synchronized (ConfigMonitor.class) {
            if (INSTANCE == null) {
                INSTANCE = new ConfigMonitor();
            }
            configMonitor = INSTANCE;
        }
        return configMonitor;
    }

    private ConfigMonitor() {
    }

    public void reportBizRequest(String key) {
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info("ConfigMonitor", "reportBizRequest key is null");
            return;
        }
        try {
            if (this.mConfigSyncReporter == null) {
                LoggerFactory.getTraceLogger().warn((String) "ConfigMonitor", (String) "mConfigSyncReporter is null");
                return;
            }
            if (this.configService == null) {
                this.configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName());
            }
            if (this.configService == null) {
                LoggerFactory.getTraceLogger().warn((String) "ConfigMonitor", (String) "configService is null");
                return;
            }
            String value = this.configService.getConfig(key);
            LoggerFactory.getTraceLogger().warn((String) "ConfigMonitor", "mConfigSyncReporter reportBizRequest key = " + key + " value = " + value);
            this.mConfigSyncReporter.reportBizRequest(CONFIGSDK_NOTIFY, key, value, Long.valueOf(System.currentTimeMillis()));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "ConfigMonitor", e);
        }
    }

    public void logRpcSend(String status, String resCode, String lastResponseTime) {
        Behavor behavor = new Behavor();
        behavor.setUserCaseID(UCID);
        behavor.setSeedID(SEEDID);
        behavor.setParam1("rpc");
        behavor.setParam2(status);
        behavor.setParam3(resCode);
        behavor.addExtParam("lastResponseTime", lastResponseTime);
        LoggerFactory.getBehavorLogger().event(null, behavor);
    }

    public void logKeyArrived(String value, String source) {
        if (TextUtils.isEmpty(value)) {
            LoggerFactory.getTraceLogger().info("ConfigMonitor", "logKeyArrived value =  null");
            return;
        }
        String key = "apconfigDrill_" + value;
        if (getKey(key)) {
            LoggerFactory.getTraceLogger().info("ConfigMonitor", "is loged key = " + key);
            return;
        }
        Behavor behavor = new Behavor();
        behavor.setUserCaseID(UCID);
        behavor.setSeedID(SEEDID);
        behavor.setParam1(MONITOR_KEY);
        behavor.setParam2(value);
        behavor.setParam3(source);
        LoggerFactory.getBehavorLogger().event(null, behavor);
        LoggerFactory.getTraceLogger().info("ConfigMonitor", "log key = apconfigDrill value = " + value);
        putKey(key, true);
    }

    private boolean getKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            return this.mSharedPreferences.getBoolean(key, false);
        }
        LoggerFactory.getTraceLogger().info("ConfigMonitor", "getKey,key is null");
        return false;
    }

    private void putKey(String key, boolean value) {
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info("ConfigMonitor", "putKey,key is null");
        } else if (this.mEditor != null) {
            this.mEditor.putBoolean(key, value);
            this.mEditor.apply();
        } else {
            LoggerFactory.getTraceLogger().info("ConfigMonitor", "mEditor is null");
        }
    }

    public ConfigSyncReporter getConfigSyncReporter() {
        return this.mConfigSyncReporter;
    }

    public void setConfigSyncReporter(ConfigSyncReporter configSyncReporter) {
        this.mConfigSyncReporter = configSyncReporter;
    }
}
