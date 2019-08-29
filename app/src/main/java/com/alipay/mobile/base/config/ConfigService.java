package com.alipay.mobile.base.config;

import com.alipay.mobile.base.config.model.PLData;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.List;
import java.util.Map;

public abstract class ConfigService extends ExternalService {

    public interface ConfigChangeListener {
        List<String> getKeys();

        void onConfigChange(String str, String str2);
    }

    public interface ConfigLoadCallBack {
        void onLoaded(String str, String str2);
    }

    public interface ConfigSyncReporter {
        void reportBizRequest(String str, String str2, String str3, Long l);
    }

    public interface SyncReceiverListener {
        List<String> getKeys();

        void onSyncReceiver(String str, String str2);
    }

    public abstract boolean addConfigChangeListener(ConfigChangeListener configChangeListener);

    public abstract String getConfig(String str);

    public abstract void getConfig(String str, ConfigLoadCallBack configLoadCallBack);

    public abstract ConfigSyncReporter getConfigSyncReporter();

    public abstract long getResponseTime();

    public abstract boolean isRegistered(SyncReceiverListener syncReceiverListener);

    public abstract void loadConfig();

    public abstract void loadConfigImmediately(long j);

    public abstract void preloadKeys(List<String> list);

    public abstract void refreshAfterLogin(String str);

    public abstract void refreshAfterLogout();

    public abstract void registerSyncReceiverListener(SyncReceiverListener syncReceiverListener);

    public abstract void removeConfigChangeListener(ConfigChangeListener configChangeListener);

    public abstract String saveConfig(PLData pLData);

    public abstract String saveConfig(PLData pLData, boolean z, boolean z2);

    public abstract void saveConfigs(Map<String, String> map);

    public abstract void setConfigSyncReporter(ConfigSyncReporter configSyncReporter);

    public abstract void unregisterSyncReceiverListener(SyncReceiverListener syncReceiverListener);
}
