package com.alipay.mobile.common.transport.config;

import android.content.Context;
import android.os.FileObserver;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.ABTestHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.transport.zfeatures.LoginRefreshManager;
import com.alipay.mobile.common.utils.config.ConfigureCtrlManagerImpl;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.autonavi.common.SuperId;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TransportConfigureManager extends ConfigureCtrlManagerImpl {
    public static final String SDCARD_CONFIG_FILE = "transport_config.json";
    public static final String SDCARD_SRV_CONFIG_FILE = "srv_transport_config.json";
    public static TransportConfigureManager transportConfigureManager;
    private int a = 0;
    private boolean b = false;
    private ConfigFileListener c;
    /* access modifiers changed from: private */
    public String d;

    class ConfigFileListener extends FileObserver {
        static byte STATE_BUSY = 2;
        static byte STATE_IDLE = 1;
        /* access modifiers changed from: private */
        public byte a = STATE_IDLE;

        public ConfigFileListener(String path) {
            super(path);
        }

        public void onEvent(int event, String path) {
            if (!TextUtils.isEmpty(path) && path.equals("sdkSharedSwitch.xml") && event == 2) {
                LogCatUtil.info("Conf_TransportConfigureManager", "ConfigFileListener. path=[" + path + "]  event=[" + event + "] ");
                if (this.a == STATE_BUSY) {
                    LogCatUtil.info("Conf_TransportConfigureManager", "ConfigFileListener. state is STATE_BUSY, return.");
                    return;
                }
                this.a = STATE_BUSY;
                try {
                    NetworkAsyncTaskExecutor.executeSerial(new Runnable() {
                        public void run() {
                            try {
                                TransportConfigureManager.getInstance().updateConfig(TransportEnvUtil.getContext());
                            } catch (Throwable e) {
                                LogCatUtil.error("Conf_TransportConfigureManager", "ConfigFileListener. updateConfig exception.", e);
                            } finally {
                                ConfigFileListener.this.a = ConfigFileListener.STATE_IDLE;
                            }
                        }
                    });
                } catch (Throwable e) {
                    LogCatUtil.error("Conf_TransportConfigureManager", "ConfigFileListener. executeSerial exception.", e);
                    this.a = STATE_IDLE;
                }
            }
        }
    }

    public static final TransportConfigureManager getInstance() {
        if (transportConfigureManager != null) {
            return transportConfigureManager;
        }
        synchronized (TransportConfigureManager.class) {
            try {
                if (transportConfigureManager != null) {
                    TransportConfigureManager transportConfigureManager2 = transportConfigureManager;
                    return transportConfigureManager2;
                }
                transportConfigureManager = new TransportConfigureManager();
                return transportConfigureManager;
            }
        }
    }

    private TransportConfigureManager() {
        a();
    }

    public boolean resetConfig(Context context) {
        LogCatUtil.info("Conf_TransportConfigureManager", "resetConfig");
        this.a = 0;
        return updateConfig(context);
    }

    public boolean firstUpdateConfig(Context context) {
        if (this.b) {
            return true;
        }
        this.b = true;
        LogCatUtil.info("Conf_TransportConfigureManager", "=====> firstUpdateConfig <=====");
        return updateConfig(context);
    }

    /* JADX INFO: finally extract failed */
    public boolean updateConfig(Context context) {
        LogCatUtil.info("Conf_TransportConfigureManager", "updateConfig");
        try {
            d(context);
            super.clearConfig();
            super.updateFromSharedPref(context, "sdkSharedSwitch", "netsdk_normal_switch");
            super.updateFromSharedPref(context, "sdkSharedSwitch", "android_network_core");
            b(context);
            boolean isLoadedConfig = super.isLoadedConfig();
            notifyConfigureChangedEvent();
            return isLoadedConfig;
        } catch (Exception e) {
            LogCatUtil.error((String) "Conf_TransportConfigureManager", (Throwable) e);
            notifyConfigureChangedEvent();
            return false;
        } catch (Throwable th) {
            notifyConfigureChangedEvent();
            throw th;
        }
    }

    public boolean updateConfig(Context context, String json, boolean clear) {
        LogCatUtil.info("Conf_TransportConfigureManager", "updateConfig json: " + json);
        if (clear) {
            try {
                super.clearConfig();
            } catch (Exception e) {
                LogCatUtil.error((String) "Conf_TransportConfigureManager", (Throwable) e);
                delayNotifyConfigureChangedEvent();
                return false;
            } catch (Throwable th) {
                delayNotifyConfigureChangedEvent();
                throw th;
            }
        }
        d(context);
        super.updateFromSharedPref(context, "sdkSharedSwitch", "netsdk_normal_switch");
        super.updateFromJsonStrAndSave(context, json, "sdkSharedSwitch", "android_network_core");
        b(context);
        boolean isLoadedConfig = super.isLoadedConfig();
        delayNotifyConfigureChangedEvent();
        return isLoadedConfig;
    }

    /* JADX INFO: finally extract failed */
    public boolean updateConfig(Context context, Map<String, String> config, String switchKey) {
        LogCatUtil.info("Conf_TransportConfigureManager", "updateConfig map config: " + config.toString());
        try {
            d(context);
            super.partialUpdateFromMapAndSave(context, config, "sdkSharedSwitch", switchKey);
            boolean isLoadedConfig = super.isLoadedConfig();
            delayNotifyConfigureChangedEvent();
            return isLoadedConfig;
        } catch (Exception e) {
            LogCatUtil.error((String) "Conf_TransportConfigureManager", (Throwable) e);
            delayNotifyConfigureChangedEvent();
            return false;
        } catch (Throwable th) {
            delayNotifyConfigureChangedEvent();
            throw th;
        }
    }

    private static void a(Context context) {
        try {
            String gwfurl = ReadSettingServerUrl.getInstance().getGWFURL(context);
            URL url = new URL(gwfurl);
            LogCatUtil.info("Conf_TransportConfigureManager", "initConfigItemsByEnv. gw url: " + gwfurl);
            if (MiscUtils.isOnlineUrl(url)) {
                TransportConfigureItem.SPDY_SWITCH.setValue("T");
                LogCatUtil.info("Conf_TransportConfigureManager", "initConfigItemsByEnv. Open online env spdy finish.");
            } else if (MiscUtils.isTestUrl(url)) {
                TransportConfigureItem.SPDY_SWITCH.setValue("T");
                TransportConfigureItem.SPDY_URL.setValue(MiscUtils.getTestGwUrl());
                LogCatUtil.info("Conf_TransportConfigureManager", "initConfigItemsByEnv. Open test env spdy finish.");
            } else if (MiscUtils.isSandboxUrl(url)) {
                TransportConfigureItem.SPDY_SWITCH.setValue("F");
                LogCatUtil.info("Conf_TransportConfigureManager", "initConfigItemsByEnv. Close sandbox env spdy finish.");
            } else {
                LogCatUtil.info("Conf_TransportConfigureManager", "initConfigItemsByEnv. Close unknow env amnet and spdy finish.");
                TransportConfigureItem.AMNET_SWITCH.setValue("0,0");
                TransportConfigureItem.SPDY_SWITCH.setValue("F");
            }
            if (MiscUtils.isDebugger(context)) {
                TransportConfigureItem.DJG_SWITCH.setValue(SuperId.BIT_2_REALTIMEBUS_BUSSTATION);
                LogCatUtil.printInfo("Conf_TransportConfigureManager", "initConfigItemsByEnv. Debug env, default open django switch.");
                TransportConfigureItem.IPRANK_AB_SWITCH.setValue(SuperId.BIT_2_REALTIMEBUS_BUSSTATION);
                TransportConfigureItem.EASTEREGGS.setValue("T");
                TransportConfigureItem.LOGIN_REFRESH_SWITCH.setValue(SuperId.BIT_2_REALTIMEBUS_BUSSTATION);
                LoginRefreshManager.getInstance().enableRefresh();
                TransportConfigureItem.GO_URLCONNECTION_SWITCH.setValue(SuperId.BIT_2_REALTIMEBUS_BUSSTATION);
            }
        } catch (Throwable e) {
            LogCatUtil.error((String) "Conf_TransportConfigureManager", e);
        }
    }

    public int getLatestVersion() {
        try {
            return Integer.parseInt(getConfgureVersion()) + getVersionSecond();
        } catch (Throwable e) {
            LogCatUtil.warn((String) "Conf_TransportConfigureManager", "getLatestVersion exception : " + e.toString());
            return 0;
        }
    }

    public String getConfgureVersion() {
        return getStringValue((ConfigureItem) TransportConfigureItem.VERSION);
    }

    public int getVersionSecond() {
        return getIntValue(TransportConfigureItem.VERSION2);
    }

    public void notifyConfigureChangedEvent() {
        LogCatUtil.info("Conf_TransportConfigureManager", "notifyConfigureChangedEvent. currentVersion=[" + this.a + "], latestVersion=[" + getLatestVersion() + "] ");
        super.notifyConfigureChangedEvent();
        getInstance().b();
    }

    private void b(Context context) {
        loadConfigFromSettingTool(context);
        c(context);
    }

    private void c(Context context) {
        try {
            if (MiscUtils.isDebugger(context)) {
                String configFromSdcard = MiscUtils.getConfigFromSdcard(SDCARD_CONFIG_FILE);
                if (!TextUtils.isEmpty(configFromSdcard)) {
                    LogCatUtil.info("Conf_TransportConfigureManager", "loadConfigFromSdcard: " + configFromSdcard);
                    mergeConfig(super.parseObject(configFromSdcard));
                }
            }
        } catch (Exception e) {
            LogCatUtil.error((String) "Conf_TransportConfigureManager", (Throwable) e);
        }
    }

    private static void d(Context context) {
        a(context);
    }

    /* access modifiers changed from: protected */
    public void delayNotifyConfigureChangedEvent() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            LogCatUtil.warn((String) "Conf_TransportConfigureManager", "delayNotifyConfigureChangedEvent sleep exception : " + e.toString());
        }
        notifyConfigureChangedEvent();
    }

    private void a() {
        Context context = TransportEnvUtil.getContext();
        if (context == null) {
            LogCatUtil.error((String) "Conf_TransportConfigureManager", (String) "specialHandle.  context is null. ");
        } else if (MiscUtils.isInAlipayClient(context) && MiscUtils.isPushProcess(context) && !MiscUtils.isRealPushProcess(context) && this.c == null) {
            this.c = new ConfigFileListener(MiscUtils.getSharedPrefsDir(TransportEnvUtil.getContext()));
            this.c.startWatching();
            LogCatUtil.info("Conf_TransportConfigureManager", "configFileListener startWatching");
        }
    }

    private void b() {
        NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
            public void run() {
                LogCatUtil.debug("Conf_TransportConfigureManager", "config change,generate new clientABTagValues");
                TransportConfigureManager.this.d = ABTestHelper.generateClientABTagValues();
            }
        }, 1, TimeUnit.SECONDS);
    }

    public synchronized String getClientABTag() {
        if (TextUtils.isEmpty(this.d)) {
            this.d = ABTestHelper.generateClientABTagValues();
        }
        return this.d;
    }

    public boolean loadConfigFromSettingTool(Context context) {
        try {
            if (!MiscUtils.isDebugger(context)) {
                return false;
            }
            ReadSettingServerUrl settingServerUrl = ReadSettingServerUrl.getInstance();
            Boolean enableAmnetSetting = settingServerUrl.isEnableAmnetSetting(context);
            Boolean enableSpdySetting = settingServerUrl.isEnableSpdySetting(context);
            LogCatUtil.debug("Conf_TransportConfigureManager", "enableAmnetSetting=[" + enableAmnetSetting + "]");
            LogCatUtil.debug("Conf_TransportConfigureManager", "enableSpdySetting=[" + enableSpdySetting + "]");
            if (enableSpdySetting == null && enableAmnetSetting == null) {
                return false;
            }
            String gwUrlStr = settingServerUrl.getGWFURL(context);
            URL gwUrl = new URL(gwUrlStr);
            if (enableAmnetSetting != null && enableAmnetSetting == Boolean.TRUE) {
                enableAmnetSetting(context, gwUrlStr, gwUrl);
            } else if (enableSpdySetting == null || enableSpdySetting != Boolean.TRUE) {
                TransportConfigureManager configureManager = getInstance();
                configureManager.setValue(TransportConfigureItem.SPDY_SWITCH, "F");
                configureManager.setValue(TransportConfigureItem.AMNET_SWITCH, "0,0,0");
                configureManager.setValue(TransportConfigureItem.NO_DOWN_HTTPS, "0-1");
                configureManager.setValue(TransportConfigureItem.RPCV2_SWITCH, "T");
                LogCatUtil.info("Conf_TransportConfigureManager", "Disable ext transport!");
            } else {
                enableSpdySetting(gwUrl);
            }
            LogCatUtil.debug("Conf_TransportConfigureManager", "settings config load finish!");
            return true;
        } catch (Exception e) {
            LogCatUtil.warn((String) "Conf_TransportConfigureManager", e.toString());
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void enableSpdySetting(URL gwUrl) {
        TransportConfigureManager configureManager = getInstance();
        configureManager.setValue(TransportConfigureItem.AMNET_SWITCH, "0,0,0");
        if (MiscUtils.isOnlineUrl(gwUrl) || MiscUtils.isTestUrl(gwUrl)) {
            configureManager.setValue(TransportConfigureItem.SPDY_SWITCH, "T");
            configureManager.setValue(TransportConfigureItem.SPDY_BLACK_LIST_PHONE_BRAND, "");
            configureManager.setValue(TransportConfigureItem.SPDY_BLACK_LIST_PHONE_MODEL, "");
            configureManager.setValue(TransportConfigureItem.SPDY_BLACK_LIST_CPU_MODEL, "");
            configureManager.setValue(TransportConfigureItem.SPDY_DISABLED_NET_KEY, "");
            configureManager.setValue(TransportConfigureItem.SPDY_DISABLED_SDK_VERSION, "");
            LogCatUtil.info("Conf_TransportConfigureManager", "enableSpdySetting done");
            return;
        }
        configureManager.setValue(TransportConfigureItem.SPDY_SWITCH, "F");
        LogCatUtil.info("Conf_TransportConfigureManager", "enableSpdySetting. Server does not support spdy, server url = " + gwUrl);
    }

    /* access modifiers changed from: protected */
    public void enableAmnetSetting(Context context, String gwUrlStr, URL gwUrl) {
        TransportConfigureManager configureManager = getInstance();
        configureManager.setValue(TransportConfigureItem.AMNET_SWITCH, SuperId.BIT_2_REALTIMEBUS_BUSSTATION);
        configureManager.setValue(TransportConfigureItem.NO_DOWN_HTTPS, "1-1");
        configureManager.setValue(TransportConfigureItem.RPCV2_SWITCH, "T");
        configureManager.setValue(TransportConfigureItem.AMNET_BLACK_LIST_PHONE_BRAND, "");
        configureManager.setValue(TransportConfigureItem.AMNET_BLACK_LIST_PHONE_MODEL, "");
        configureManager.setValue(TransportConfigureItem.AMNET_BLACK_LIST_CPU_MODEL, "");
        configureManager.setValue(TransportConfigureItem.AMNET_DISABLED_NET_KEY, "");
        configureManager.setValue(TransportConfigureItem.AMNET_DISABLED_SDK_VERSION, "");
        if (!setAmnetConfigByDns(context)) {
            setAmnetConfigByGwUrl(context, gwUrlStr, gwUrl, configureManager);
        }
        LogCatUtil.info("Conf_TransportConfigureManager", "enableAmnetSetting done");
    }

    /* access modifiers changed from: protected */
    public boolean setAmnetConfigByDns(Context context) {
        TransportConfigureManager configureManager = getInstance();
        String amnetDnsSetting = ReadSettingServerUrl.getInstance().getAmnetDnsSetting(context);
        if (TextUtils.isEmpty(amnetDnsSetting)) {
            return false;
        }
        String amnetDnsSetting2 = amnetDnsSetting.trim();
        if (TextUtils.isEmpty(amnetDnsSetting2)) {
            return false;
        }
        configureManager.setValue(TransportConfigureItem.MMTP_URL, amnetDnsSetting2);
        if (amnetDnsSetting2.endsWith(":8903")) {
            LogCatUtil.info("Conf_TransportConfigureManager", "setAmnetConfigByDns port is 8903, don't need to shake hands");
            configureManager.setValue(TransportConfigureItem.AMNET_HS, "F");
        } else {
            configureManager.setValue(TransportConfigureItem.AMNET_HS, "T");
        }
        LogCatUtil.info("Conf_TransportConfigureManager", "setAmnetConfigByDns finish,  amnetDnsSetting=[" + amnetDnsSetting2 + "]");
        return true;
    }

    /* access modifiers changed from: protected */
    public void setAmnetConfigByGwUrl(Context context, String gwUrlStr, URL gwUrl, TransportConfigureManager configureManager) {
        if (MiscUtils.isOnlineUrl(gwUrl)) {
            if (MiscUtils.isRCVersion(context)) {
                configureManager.setValue(TransportConfigureItem.MMTP_URL, "mygwrc.alipay.com:443");
            } else {
                configureManager.setValue(TransportConfigureItem.MMTP_URL, "mygw.alipay.com:443");
            }
        } else if (MiscUtils.isPreUrl(gwUrl)) {
            configureManager.setValue(TransportConfigureItem.MMTP_URL, "mygwpre.alipay.com:443");
        } else if (gwUrl.getPort() != -1) {
            configureManager.setValue(TransportConfigureItem.MMTP_URL, gwUrl.getHost() + ":" + gwUrl.getPort());
            if (gwUrl.getPort() == 8903) {
                configureManager.setValue(TransportConfigureItem.AMNET_HS, "F");
            } else {
                configureManager.setValue(TransportConfigureItem.AMNET_HS, "T");
            }
        } else {
            configureManager.setValue(TransportConfigureItem.MMTP_URL, gwUrl.getHost() + ":443");
            configureManager.setValue(TransportConfigureItem.AMNET_HS, "T");
        }
        LogCatUtil.debug("Conf_TransportConfigureManager", "enableAmnetSetting gw_url=[" + gwUrlStr + "] mapping to amnet_url=" + configureManager.getStringValue((ConfigureItem) TransportConfigureItem.MMTP_URL));
    }
}
