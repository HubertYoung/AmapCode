package com.alipay.mobile.base.config.impl;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.base.config.ConfigService.ConfigChangeListener;
import com.alipay.mobile.base.config.ConfigService.ConfigLoadCallBack;
import com.alipay.mobile.base.config.ConfigService.ConfigSyncReporter;
import com.alipay.mobile.base.config.ConfigService.SyncReceiverListener;
import com.alipay.mobile.base.config.model.PLData;
import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.utils.SharedSwitchUtil;
import com.alipay.mobile.forerunner.runner.Counter;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.quinox.startup.UpgradeHelper;
import com.alipay.mobileappcommon.biz.rpc.clientswitch.ClientSwitchRpcFacade;
import com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb.SwitchInfoEntryPb;
import com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb.SwitchInfoReqPb;
import com.alipay.mobileappcommon.biz.rpc.clientswitch.model.pb.SwitchInfoRespPb;
import com.alipay.mobileappcommon.biz.rpc.sync.MobileSyncDataService;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.BatchSyncDataReq;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.BatchSyncDataResp;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.BatchSyncDataResp.DataMap;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.SyncDataReq;
import com.alipay.mobileappcommon.biz.rpc.sync.model.proto.SyncDataResp;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConfigServiceImpl extends ConfigService {
    private static final String IS_COUNT_KEY = "isCountKey";
    private static final String KEY_INTERVAL_TIME = "Load_Config_Interval";
    private static final String KEY_LAST_LOAD_TIME = "last_load_time";
    private static final String RESERVE_CONFIG_KEY_RESPONSE_TIME = "reserveConfigKeyResponseTime";
    private static final String RESERVE_CONFIG_KEY_USERID = "reserveConfigKeyUserId";
    /* access modifiers changed from: private */
    public static final String TAG = ConfigService.class.getSimpleName();
    private static Boolean isCountKey = null;
    private static List<SoftReference<ConfigChangeListener>> mConfigChangeListeners = Collections.synchronizedList(new ArrayList());
    public static long mLastLoadTime = 0;
    private static ExecutorService mSingleThreadExecutor;
    private static List<SoftReference<SyncReceiverListener>> slmacSyncReceiverListeners = Collections.synchronizedList(new ArrayList());
    private final long TIME_HALF_HOUR = TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL;
    /* access modifiers changed from: private */
    public AtomicBoolean lastRpcSuccess = new AtomicBoolean(true);
    /* access modifiers changed from: private */
    public ConfigDataManager mConfigDataManager;
    private ConfigServiceLite mConfigServiceLite;
    /* access modifiers changed from: private */
    public ContextWrapper mContextWrapper;
    /* access modifiers changed from: private */
    public String mLoginUserId;
    /* access modifiers changed from: private */
    public UpgradeHelper mUpgradeHelper;
    /* access modifiers changed from: private */
    public AtomicBoolean onRpcProcessing = new AtomicBoolean(false);
    private TaskScheduleService taskScheduleService = null;

    class ConfigLoaderTask implements Runnable {
        private long delayTime = 0;
        private String mUserId = null;

        public ConfigLoaderTask(long delay) {
            this.delayTime = delay;
        }

        public ConfigLoaderTask(long delay, String userId) {
            this.delayTime = delay;
            this.mUserId = userId;
        }

        public void run() {
            String resCode;
            String lastReqTime = null;
            try {
                if (!TextUtils.isEmpty(this.mUserId)) {
                    ConfigServiceImpl.this.mConfigDataManager.removeKey(ConfigServiceImpl.RESERVE_CONFIG_KEY_RESPONSE_TIME, null);
                    ConfigServiceImpl.this.mConfigDataManager.putString(ConfigServiceImpl.RESERVE_CONFIG_KEY_USERID, this.mUserId);
                }
                if (this.delayTime != 0) {
                    Thread.sleep(this.delayTime);
                }
                ClientSwitchRpcFacade service = (ClientSwitchRpcFacade) ((RpcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RpcService.class.getName())).getBgRpcProxy(ClientSwitchRpcFacade.class);
                SwitchInfoReqPb req = new SwitchInfoReqPb();
                AppInfo appInfo = AppInfo.createInstance(ConfigServiceImpl.this.mContextWrapper);
                String mobileBrand = DeviceInfo.getInstance().getmMobileBrand();
                String romVersion = LoggerFactory.getDeviceProperty().getRomVersion();
                req.productId = appInfo.getProductID();
                req.systemType = "android";
                req.clientVersion = appInfo.getmProductVersion() == null ? "" : appInfo.getmProductVersion();
                req.mobileBrand = mobileBrand;
                req.mobileModel = DeviceInfo.getInstance().getmMobileModel();
                req.osVersion = DeviceInfo.getInstance().getOsVersion();
                req.romVersion = romVersion;
                String uid = ConfigServiceImpl.this.mConfigDataManager.getString(ConfigServiceImpl.RESERVE_CONFIG_KEY_USERID, null);
                LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "ConfigLoaderTask req userId = " + uid + " version = " + appInfo.getmProductVersion() + " productId = " + appInfo.getProductID() + " romVersion = " + romVersion + " mobileBrand = " + mobileBrand);
                req.userId = uid;
                if (ConfigServiceImpl.this.needPull()) {
                    ConfigServiceImpl.this.mConfigDataManager.getAssistSp().edit().putString("last_not_increment", "0").commit();
                    req.lastResponseTime = null;
                    ConfigServiceImpl.this.mConfigDataManager.addPersistKey((String) ConfigDataManager.RESERVE_CONFIG_KEY_UPGRADE_LOAD);
                    ConfigServiceImpl.this.mConfigDataManager.addPersistKey((String) ConfigServiceImpl.RESERVE_CONFIG_KEY_USERID);
                    ConfigServiceImpl.this.mConfigDataManager.addPersistKey((String) ConfigServiceImpl.KEY_LAST_LOAD_TIME);
                    LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "needPull update = true");
                } else {
                    String respTime = ConfigServiceImpl.this.mConfigDataManager.getString(ConfigServiceImpl.RESERVE_CONFIG_KEY_RESPONSE_TIME, null);
                    req.lastResponseTime = respTime;
                    LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "ConfigLoaderTask req lastResponseTime = " + respTime);
                    lastReqTime = respTime;
                }
                String channelId = appInfo.getmChannels();
                String imei = DeviceInfo.createInstance(ConfigServiceImpl.this.mContextWrapper).getDefImei();
                String utdid = DeviceInfo.createInstance(ConfigServiceImpl.this.mContextWrapper).getmDid();
                req.channelId = channelId;
                req.imei = imei;
                req.utdid = utdid;
                LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "ConfigLoaderTask req channelId = " + channelId + " imei = " + imei + " utdid = " + utdid);
                SwitchInfoRespPb resp = service.getSwitchesPbAfterLogin(req);
                ConfigMonitor.getInstance().logRpcSend(H5PageData.KEY_UC_START, null, req.lastResponseTime);
                if (resp.success.booleanValue()) {
                    ConfigServiceImpl.this.lastRpcSuccess.set(true);
                    ConfigServiceImpl.this.mConfigDataManager.putString(ConfigDataManager.RESERVE_CONFIG_KEY_UPGRADE_LOAD, ConfigServiceImpl.this.mUpgradeHelper.getProductVersion());
                    LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "resp.success");
                    ConfigMonitor.getInstance().logRpcSend("success", null, req.lastResponseTime);
                    if (!resp.increment.booleanValue()) {
                        LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "!resp.increment");
                        ConfigServiceImpl.this.mConfigDataManager.getAssistSp().edit().putString("last_not_increment", "1").commit();
                        synchronized (ConfigService.class) {
                            String userId = ConfigServiceImpl.this.mConfigDataManager.getString(ConfigServiceImpl.RESERVE_CONFIG_KEY_USERID, null);
                            ConfigServiceImpl.this.mConfigDataManager.migrateCommonConfigPersistKeys();
                            ConfigServiceImpl.this.mConfigDataManager.clearCommonConfig();
                            ConfigServiceImpl.this.mConfigDataManager.reMigrateCommonConfigPersistKeys();
                            ConfigServiceImpl.this.mConfigDataManager.putString(ConfigServiceImpl.RESERVE_CONFIG_KEY_USERID, userId);
                            ConfigServiceImpl.this.saveConfigs(resp, false);
                        }
                    } else {
                        ConfigServiceImpl.this.saveConfigs(resp, true);
                    }
                } else {
                    LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "!!!resp.success");
                    ConfigServiceImpl.this.lastRpcSuccess.set(true);
                }
                ConfigServiceImpl.this.onRpcProcessing.set(false);
            } catch (Throwable e) {
                try {
                    ConfigServiceImpl.this.lastRpcSuccess.set(false);
                    LoggerFactory.getTraceLogger().error(ConfigServiceImpl.TAG, e);
                    resCode = null;
                    if (e instanceof RpcException) {
                        resCode = ((RpcException) e).getCode();
                    }
                } catch (Throwable th) {
                    LoggerFactory.getTraceLogger().error(ConfigServiceImpl.TAG, e);
                }
                ConfigMonitor.getInstance().logRpcSend(LogCategory.CATEGORY_EXCEPTION, resCode, lastReqTime);
                ConfigServiceImpl.this.onRpcProcessing.set(false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite = new ConfigServiceLite();
            this.mConfigServiceLite.setContext(getMicroApplicationContext().getApplicationContext());
            this.mConfigServiceLite.onCreate(bundle);
            Log.i("mytest", "ConfigService onCreate step 2");
            return;
        }
        mSingleThreadExecutor = Executors.newFixedThreadPool(1);
        this.mContextWrapper = getMicroApplicationContext().getApplicationContext();
        this.mConfigDataManager = ConfigDataManager.getInstance(this.mContextWrapper);
        this.mLoginUserId = this.mConfigDataManager.getString(RESERVE_CONFIG_KEY_USERID, null);
        this.mUpgradeHelper = UpgradeHelper.getInstance(getMicroApplicationContext().getApplicationContext());
        this.taskScheduleService = (TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName());
        initOSVersion();
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle arg0) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.onDestroy(arg0);
        }
    }

    public String getConfig(String key) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            return this.mConfigServiceLite.getConfig(key);
        }
        if ("SOCIAL_CONTACT".equals(key)) {
            ConfigMonitor.getInstance().reportBizRequest("ConfigArrivalCount2");
        }
        if ("ONSITEPAY_REALNAME_ALERT_SWITCH".equals(key)) {
            ConfigMonitor.getInstance().reportBizRequest("ConfigArrivalCount3");
            ConfigMonitor.getInstance().reportBizRequest("ConfigArrivalCount4");
        }
        return getConfigFromUserConfigAndDefConfig(key);
    }

    /* access modifiers changed from: private */
    public String getConfigFromUserConfigAndDefConfig(String key) {
        String stringOnlyUser = this.mConfigDataManager.getString(key, null, this.mLoginUserId);
        logCountKey(key);
        if (!TextUtils.isEmpty(stringOnlyUser)) {
            LoggerFactory.getTraceLogger().info(TAG, "getConfig key = " + key + " stringOnlyUser = " + stringOnlyUser);
            return stringOnlyUser;
        }
        String strInCommon = this.mConfigDataManager.getString(key, null);
        LoggerFactory.getTraceLogger().info(TAG, "getConfig key = " + key + " strInCommon " + strInCommon);
        return strInCommon;
    }

    private void logCountKey(String key) {
        if (!IS_COUNT_KEY.equals(key)) {
            if (isCountKey == null) {
                refreashIsCount();
            }
            if (isCountKey != null && isCountKey.booleanValue()) {
                HashMap params = new HashMap();
                params.put("configKey", key);
                Counter.count("config-00001", params);
            }
        }
    }

    public synchronized void loadConfig() {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.loadConfig();
        } else {
            LoggerFactory.getTraceLogger().debug(TAG, "public synchronized void loadConfig ");
            long nowTime = System.currentTimeMillis();
            if (mLastLoadTime == 0) {
                try {
                    mLastLoadTime = Long.parseLong(this.mConfigDataManager.getString(KEY_LAST_LOAD_TIME, "0"));
                } catch (Exception e) {
                    mLastLoadTime = 0;
                }
            }
            if (!this.onRpcProcessing.get() && (Math.abs(nowTime - mLastLoadTime) > getIntervalTime() || !this.lastRpcSuccess.get() || needPull())) {
                LoggerFactory.getTraceLogger().info(TAG, "mLastLoadTime = " + mLastLoadTime + " nowTime = " + nowTime);
                mLastLoadTime = nowTime;
                this.onRpcProcessing.set(true);
                mSingleThreadExecutor.execute(new ConfigLoaderTask(0));
                this.mConfigDataManager.putString(KEY_LAST_LOAD_TIME, mLastLoadTime);
            }
        }
        return;
    }

    private long getIntervalTime() {
        try {
            return Long.parseLong(this.mConfigDataManager.getString(KEY_INTERVAL_TIME, "30")) * 60 * 1000;
        } catch (Exception e) {
            return TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL;
        }
    }

    public synchronized void loadConfigImmediately(long delay) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.loadConfigImmediately(delay);
        } else {
            LoggerFactory.getTraceLogger().info(TAG, "loadConfigImmediately delay = " + delay);
            mSingleThreadExecutor.execute(new ConfigLoaderTask(delay));
        }
    }

    public synchronized void refreshAfterLogin(String userId) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.refreshAfterLogin(userId);
        } else {
            LoggerFactory.getTraceLogger().debug(TAG, "refreshAfterLogin userId : " + userId + ", preUser : " + this.mLoginUserId);
            if (!TextUtils.isEmpty(userId) && !userId.equals(this.mLoginUserId)) {
                this.mConfigDataManager.addLoginUser(userId);
                mSingleThreadExecutor.execute(new ConfigLoaderTask(0, userId));
            }
            if (TextUtils.isEmpty(userId)) {
                userId = this.mLoginUserId;
            }
            this.mLoginUserId = userId;
        }
    }

    public synchronized void refreshAfterLogout() {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.refreshAfterLogout();
        } else {
            LoggerFactory.getTraceLogger().debug(TAG, "refreshAfterLogout");
            if (!TextUtils.isEmpty(this.mLoginUserId)) {
                this.mConfigDataManager.removeKey(RESERVE_CONFIG_KEY_RESPONSE_TIME, null);
                this.mConfigDataManager.removeKey(RESERVE_CONFIG_KEY_USERID, null);
                mSingleThreadExecutor.execute(new ConfigLoaderTask(0));
                this.mLoginUserId = "";
            }
        }
    }

    public void saveConfigs(Map<String, String> configs) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.saveConfigs(configs);
        } else if (configs != null) {
            this.mConfigDataManager.putMap(configs);
            for (String key : configs.keySet()) {
                notifySyncArrived(key, configs.get(key));
                notifyConfigChange(key, configs.get(key));
                if (ConfigMonitor.MONITOR_KEY.equals(key)) {
                    ConfigMonitor.getInstance().logKeyArrived(configs.get(key), "sync");
                }
            }
            if (this.mContextWrapper == null) {
                LoggerFactory.getTraceLogger().info(TAG, "mContextWrapper is null");
                return;
            }
            try {
                LocalBroadcastManager.getInstance(this.mContextWrapper).sendBroadcast(generateConfigChangeBroadcastIntent(configs));
                refreashIsCount();
                LoggerFactory.getTraceLogger().info(TAG, "send CONFIG_CHANGE");
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error(TAG, "send CONFIG_CHANGE broadcast error!", e);
            }
        }
    }

    private Intent generateConfigChangeBroadcastIntent(Map<String, String> configs) {
        Intent intent = new Intent("com.alipay.mobile.client.CONFIG_CHANGE");
        if (configs != null) {
            Bundle extra = new Bundle();
            String changedConfigs = JSON.toJSONString(configs);
            if (changedConfigs.getBytes().length > 491520) {
                extra.putBoolean("data_overflow", true);
            } else {
                extra.putString("changed_configs", changedConfigs);
                extra.putBoolean("data_overflow", false);
            }
            intent.putExtras(extra);
        }
        return intent;
    }

    private void refreashIsCount() {
        if ("yes".equals(getConfig(IS_COUNT_KEY))) {
            isCountKey = Boolean.valueOf(true);
        } else {
            isCountKey = Boolean.valueOf(false);
        }
    }

    /* access modifiers changed from: private */
    public boolean needPull() {
        return (this.mUpgradeHelper.getProductVersion() != null && !this.mUpgradeHelper.getProductVersion().equals(this.mConfigDataManager.getString(ConfigDataManager.RESERVE_CONFIG_KEY_UPGRADE_LOAD, null))) || "0".equals(this.mConfigDataManager.getAssistSp().getString("last_not_increment", "0")) || isChangeOSVersion();
    }

    private void initOSVersion() {
        String osVersion = this.mConfigDataManager.getAssistSp().getString("last_not_increment", "0");
        LoggerFactory.getTraceLogger().info(TAG, "initOSVersion osVersion = " + osVersion);
        if (TextUtils.isEmpty(osVersion) || "0".equals(osVersion)) {
            this.mConfigDataManager.getAssistSp().edit().putString("last_not_increment", VERSION.RELEASE).commit();
        }
    }

    private boolean isChangeOSVersion() {
        try {
            if (!this.mConfigDataManager.getAssistSp().getString("last_not_increment", "0").equals(VERSION.RELEASE)) {
                return false;
            }
            LoggerFactory.getTraceLogger().info(TAG, "isChangeOSVersion = true");
            return true;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error(TAG, e);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void saveConfigs(SwitchInfoRespPb resp, boolean increment) {
        this.mConfigDataManager.putString(RESERVE_CONFIG_KEY_RESPONSE_TIME, resp.responseTime);
        LoggerFactory.getTraceLogger().info(TAG, "resp.responseTime = " + resp.responseTime + " increment = " + increment);
        Map switchMap = null;
        if (resp.switches == null || resp.switches.size() <= 0) {
            LoggerFactory.getTraceLogger().info(TAG, "resp.switches is empty");
        } else {
            LoggerFactory.getTraceLogger().info(TAG, "resp.switches.size() = " + resp.switches.size());
            switchMap = toMap(resp.switches);
            this.mConfigDataManager.putMap(switchMap);
            notifyRpcRes(switchMap);
            if (switchMap != null && !TextUtils.isEmpty(switchMap.get(ConfigMonitor.MONITOR_KEY))) {
                ConfigMonitor.getInstance().logKeyArrived(switchMap.get(ConfigMonitor.MONITOR_KEY), "rpc");
            }
        }
        if (resp.deleteKeys != null && resp.deleteKeys.size() > 0) {
            this.mConfigDataManager.removeKeys(resp.deleteKeys);
        }
        doRefreshSharedSwitch(this.mContextWrapper, toMap(resp.switches));
        try {
            LocalBroadcastManager.getInstance(this.mContextWrapper).sendBroadcast(generateConfigChangeBroadcastIntent(switchMap));
        } catch (Throwable throwable) {
            LoggerFactory.getTraceLogger().error(TAG, "saveConfigs SwitchInfoRespPb sendBroadcast error!", throwable);
        }
        refreashIsCount();
    }

    private void notifyRpcRes(Map<String, String> switchMap) {
        if (switchMap == null) {
            LoggerFactory.getTraceLogger().info(TAG, "switchMap is null");
            return;
        }
        for (String key : switchMap.keySet()) {
            String value = switchMap.get(key);
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                LoggerFactory.getTraceLogger().warn(TAG, "key = " + key + " value = " + key);
            } else {
                notifyConfigChange(key, value);
            }
        }
    }

    private Map<String, String> toMap(List<SwitchInfoEntryPb> switches) {
        if (switches == null) {
            LoggerFactory.getTraceLogger().info(TAG, "get Rpc switches is null");
            return null;
        }
        Map switchMap = new HashMap();
        for (SwitchInfoEntryPb switchInfoEntryPb : switches) {
            switchMap.put(switchInfoEntryPb.key, switchInfoEntryPb.value);
        }
        LoggerFactory.getTraceLogger().info(TAG, "switchMap.size() = " + switchMap.size());
        return switchMap;
    }

    public void getConfig(final String key, final ConfigLoadCallBack configLoadCallBack) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.getConfig(key, configLoadCallBack);
        } else {
            new Thread(new Runnable() {
                public void run() {
                    String configFromUserConfigAndDefConfig = ConfigServiceImpl.this.getConfigFromUserConfigAndDefConfig(key);
                    if (TextUtils.isEmpty(configFromUserConfigAndDefConfig)) {
                        try {
                            SyncDataReq syncDataReq = new SyncDataReq();
                            syncDataReq.uniqId = key;
                            SyncDataResp syncDataResp = ((MobileSyncDataService) ((RpcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RpcService.class.getName())).getBgRpcProxy(MobileSyncDataService.class)).getSyncData(syncDataReq);
                            if (syncDataResp == null) {
                                LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "getLmacConfig from rpc get " + syncDataResp);
                            } else {
                                LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "getLmacConfig from rpc get syncDataResp.success = " + syncDataResp.success + " syncDataResp uniqId = " + syncDataResp.uniqId + " data = " + syncDataResp.data);
                            }
                            if (syncDataResp.success.booleanValue()) {
                                ConfigServiceImpl.this.mConfigDataManager.putString(syncDataResp.uniqId, syncDataResp.data);
                                ConfigServiceImpl.this.mConfigDataManager.putString(syncDataResp.uniqId, syncDataResp.data, ConfigServiceImpl.this.mLoginUserId);
                                if (configLoadCallBack != null) {
                                    configLoadCallBack.onLoaded(key, syncDataResp.data);
                                    return;
                                }
                                return;
                            }
                            LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "rpc syncDataResp.success : false");
                        } catch (Exception e) {
                            LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "configLoadCallBack.onLoaded(key, null);  rpc error or  configLoadCallBack.onLoaded error : " + e.getMessage());
                        }
                    } else if (configLoadCallBack != null) {
                        configLoadCallBack.onLoaded(key, configFromUserConfigAndDefConfig);
                    }
                }
            }).start();
        }
    }

    public void registerSyncReceiverListener(SyncReceiverListener syncReceiverListener) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.registerSyncReceiverListener(syncReceiverListener);
        } else if (syncReceiverListener != null) {
            synchronized (slmacSyncReceiverListeners) {
                slmacSyncReceiverListeners.add(new SoftReference(syncReceiverListener));
            }
        }
    }

    public boolean addConfigChangeListener(ConfigChangeListener configChangeListener) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            return this.mConfigServiceLite.addConfigChangeListener(configChangeListener);
        }
        if (configChangeListener == null) {
            LoggerFactory.getTraceLogger().warn(TAG, (String) "configChangeListener is null");
            return false;
        }
        synchronized (mConfigChangeListeners) {
            if (isAddConfigChangeListener(configChangeListener)) {
                LoggerFactory.getTraceLogger().warn(TAG, configChangeListener.getClass().getName() + " configChangeListener is alreadly is add");
                return false;
            }
            mConfigChangeListeners.add(new SoftReference(configChangeListener));
            return true;
        }
    }

    private boolean isAddConfigChangeListener(ConfigChangeListener configChangeListener) {
        for (SoftReference softReference : mConfigChangeListeners) {
            if (softReference != null) {
                ConfigChangeListener tmp = (ConfigChangeListener) softReference.get();
                if (tmp != null && tmp == configChangeListener) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isRegistered(SyncReceiverListener syncReceiverListener) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            return this.mConfigServiceLite.isRegistered(syncReceiverListener);
        }
        synchronized (slmacSyncReceiverListeners) {
            for (SoftReference softReference : slmacSyncReceiverListeners) {
                if (softReference != null) {
                    SyncReceiverListener tmp = (SyncReceiverListener) softReference.get();
                    if (tmp != null && tmp == syncReceiverListener) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void unregisterSyncReceiverListener(SyncReceiverListener syncReceiverListener) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.unregisterSyncReceiverListener(syncReceiverListener);
        } else if (syncReceiverListener != null) {
            try {
                synchronized (slmacSyncReceiverListeners) {
                    SoftReference target = null;
                    Iterator<SoftReference<SyncReceiverListener>> it = slmacSyncReceiverListeners.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        SoftReference softReference = it.next();
                        if (softReference != null) {
                            SyncReceiverListener tmp = (SyncReceiverListener) softReference.get();
                            if (tmp != null && tmp == syncReceiverListener) {
                                target = softReference;
                                break;
                            }
                        }
                    }
                    if (target != null) {
                        slmacSyncReceiverListeners.remove(target);
                    }
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error(TAG, e);
            }
        }
    }

    public void removeConfigChangeListener(ConfigChangeListener configChangeListener) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.removeConfigChangeListener(configChangeListener);
        } else if (configChangeListener != null) {
            try {
                synchronized (mConfigChangeListeners) {
                    SoftReference target = null;
                    Iterator<SoftReference<ConfigChangeListener>> it = mConfigChangeListeners.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        SoftReference softReference = it.next();
                        if (softReference != null) {
                            ConfigChangeListener tmp = (ConfigChangeListener) softReference.get();
                            if (tmp != null && tmp == configChangeListener) {
                                target = softReference;
                                break;
                            }
                        }
                    }
                    if (target != null) {
                        mConfigChangeListeners.remove(target);
                    }
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error(TAG, e);
            }
        }
    }

    public ConfigSyncReporter getConfigSyncReporter() {
        return ConfigMonitor.getInstance().getConfigSyncReporter();
    }

    public void setConfigSyncReporter(ConfigSyncReporter reporter) {
        ConfigMonitor.getInstance().setConfigSyncReporter(reporter);
    }

    public void preloadKeys(final List<String> keys) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            this.mConfigServiceLite.preloadKeys(keys);
        } else {
            new Thread(new Runnable() {
                public void run() {
                    if (keys == null) {
                        LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "keys==null");
                        return;
                    }
                    List<String> rpcKeys = new ArrayList<>();
                    for (String key : keys) {
                        if (!TextUtils.isEmpty(key) && !ConfigServiceImpl.this.mConfigDataManager.containsInCommonConfig(key)) {
                            rpcKeys.add(key);
                        }
                    }
                    if (!rpcKeys.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String rpcKey : rpcKeys) {
                            stringBuilder.append("rpc key = " + rpcKey + " ,");
                        }
                        LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "rpckeys is : " + stringBuilder.toString());
                        try {
                            Collections.sort(rpcKeys);
                            BatchSyncDataReq batchSyncDataReq = new BatchSyncDataReq();
                            batchSyncDataReq.uniqIds = rpcKeys;
                            BatchSyncDataResp batchSyncDataResp = ((MobileSyncDataService) ((RpcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RpcService.class.getName())).getBgRpcProxy(MobileSyncDataService.class)).getBatchSyncData(batchSyncDataReq);
                            ArrayList persistKeys = new ArrayList();
                            if (batchSyncDataResp.success.booleanValue()) {
                                List dataMap = batchSyncDataResp.dataMap;
                                if (dataMap == null || dataMap.isEmpty()) {
                                    LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "dataMap == null or  dataMap.isEmpty() ");
                                } else {
                                    for (DataMap data : dataMap) {
                                        try {
                                            persistKeys.add(data.key);
                                            boolean saved = ConfigServiceImpl.this.mConfigDataManager.saveKeyValueWithTimeToCommon(data.key, data.value.data, Long.parseLong(data.value.updateTime));
                                            LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "from preload rpc key = " + data.key + ",value = " + data.value.data);
                                            if (saved) {
                                                ConfigServiceImpl.this.notifySyncArrived(data.key, data.value.data);
                                                ConfigServiceImpl.this.notifyConfigChange(data.key, data.value.data);
                                            } else if (!ConfigServiceImpl.this.mConfigDataManager.containsInCommonConfig(data.key)) {
                                                ConfigServiceImpl.this.mConfigDataManager.saveKeyValueToCommon(data.key, data.value.data, Long.parseLong(data.value.updateTime));
                                                ConfigServiceImpl.this.notifySyncArrived(data.key, data.value.data);
                                                ConfigServiceImpl.this.notifyConfigChange(data.key, data.value.data);
                                            } else {
                                                LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "不应该走到这里的");
                                            }
                                        } catch (Exception e) {
                                            LoggerFactory.getTraceLogger().error(ConfigServiceImpl.TAG, (Throwable) e);
                                        }
                                    }
                                }
                            } else {
                                LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "preloadkeys batchSyncDataResp false ");
                            }
                            ConfigServiceImpl.this.mConfigDataManager.addPersistKey((List<String>) persistKeys);
                        } catch (Exception e2) {
                            LoggerFactory.getTraceLogger().error(ConfigServiceImpl.TAG, (Throwable) e2);
                        }
                    } else {
                        LoggerFactory.getTraceLogger().info(ConfigServiceImpl.TAG, "rpc keys==null");
                    }
                }
            }).start();
        }
    }

    public String saveConfig(PLData plData) {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            return this.mConfigServiceLite.saveConfig(plData);
        }
        return saveConfig(plData, false, false);
    }

    private void footPrint(String msg) {
        Performance performance = new Performance();
        performance.setParam1(msg);
        LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_FOOTPRINT, performance);
    }

    public String saveConfig(PLData plData, boolean persist, boolean onlyUser) {
        String saveSplitedData;
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            return this.mConfigServiceLite.saveConfig(plData, persist, onlyUser);
        }
        footPrint("uiniqId = " + plData.uniqId + ",updateTime = " + plData.updateTime + ", onlyUser = " + onlyUser);
        LoggerFactory.getTraceLogger().info(TAG, "saveConfig plData【type = " + plData.type + ", data = " + plData.data + ", uniqId = " + plData.uniqId + ", updateTime = " + plData.updateTime + ", onlyUser = " + onlyUser + "】");
        if (plData == null) {
            return null;
        }
        if (persist) {
            this.mConfigDataManager.addPersistKey(plData.uniqId);
        }
        if (plData.total >= 2) {
            try {
                synchronized (ConfigService.class) {
                    saveSplitedData = saveSplitedData(plData, onlyUser);
                }
                return saveSplitedData;
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().info(TAG, "saveConfig plData.total >= 2 Exception : " + e.getMessage() + "  plData【type = " + plData.type + ", data = " + plData.data + ", uniqId = " + plData.uniqId + "】");
            }
        } else {
            try {
                if (this.mConfigDataManager.saveKeyValueWithTime(plData.uniqId, plData.data, plData.updateTime, onlyUser, this.mLoginUserId)) {
                    notifySyncArrived(plData.uniqId, plData.data);
                    notifyConfigChange(plData.uniqId, plData.data);
                    return plData.data;
                }
            } catch (Exception e2) {
                LoggerFactory.getTraceLogger().info(TAG, "saveConfig plData.total ==1  Exception : " + e2.getMessage() + "  plData【type = " + plData.type + ", data = " + plData.data + ", uniqId = " + plData.uniqId + "】");
            }
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0055, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0067, code lost:
        r3 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String saveSplitedData(com.alipay.mobile.base.config.model.PLData r17, boolean r18) {
        /*
            r16 = this;
            java.lang.Class<com.alipay.mobile.base.config.ConfigService> r15 = com.alipay.mobile.base.config.ConfigService.class
            monitor-enter(r15)
            r0 = r17
            int r14 = r0.total     // Catch:{ all -> 0x0058 }
            r0 = r16
            com.alipay.mobile.base.config.impl.ConfigDataManager r2 = r0.mConfigDataManager     // Catch:{ all -> 0x0058 }
            if (r18 == 0) goto L_0x001c
            r0 = r16
            java.lang.String r1 = r0.mLoginUserId     // Catch:{ all -> 0x0058 }
        L_0x0011:
            r0 = r17
            boolean r1 = r2.putSpliteData(r0, r1)     // Catch:{ all -> 0x0058 }
            if (r1 != 0) goto L_0x001e
            r3 = 0
            monitor-exit(r15)     // Catch:{ all -> 0x0058 }
        L_0x001b:
            return r3
        L_0x001c:
            r1 = 0
            goto L_0x0011
        L_0x001e:
            com.alipay.mobile.base.config.model.PLData[] r10 = new com.alipay.mobile.base.config.model.PLData[r14]     // Catch:{ all -> 0x0058 }
            r8 = 0
        L_0x0021:
            if (r8 >= r14) goto L_0x0073
            r0 = r16
            com.alipay.mobile.base.config.impl.ConfigDataManager r2 = r0.mConfigDataManager     // Catch:{ Exception -> 0x006f }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006f }
            r1.<init>()     // Catch:{ Exception -> 0x006f }
            r0 = r17
            java.lang.String r4 = r0.uniqId     // Catch:{ Exception -> 0x006f }
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x006f }
            r0 = r17
            long r4 = r0.updateTime     // Catch:{ Exception -> 0x006f }
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x006f }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Exception -> 0x006f }
            java.lang.String r4 = r1.toString()     // Catch:{ Exception -> 0x006f }
            r5 = 0
            if (r18 == 0) goto L_0x005b
            r0 = r16
            java.lang.String r1 = r0.mLoginUserId     // Catch:{ Exception -> 0x006f }
        L_0x004b:
            java.lang.String r13 = r2.getString(r4, r5, r1)     // Catch:{ Exception -> 0x006f }
            boolean r1 = android.text.TextUtils.isEmpty(r13)     // Catch:{ Exception -> 0x006f }
            if (r1 == 0) goto L_0x005d
            r3 = 0
            monitor-exit(r15)     // Catch:{ all -> 0x0058 }
            goto L_0x001b
        L_0x0058:
            r1 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x0058 }
            throw r1
        L_0x005b:
            r1 = 0
            goto L_0x004b
        L_0x005d:
            java.lang.Class<com.alipay.mobile.base.config.model.PLData> r1 = com.alipay.mobile.base.config.model.PLData.class
            java.lang.Object r9 = com.alibaba.fastjson.JSON.parseObject(r13, r1)     // Catch:{ Exception -> 0x006f }
            com.alipay.mobile.base.config.model.PLData r9 = (com.alipay.mobile.base.config.model.PLData) r9     // Catch:{ Exception -> 0x006f }
            if (r9 != 0) goto L_0x006a
            r3 = 0
            monitor-exit(r15)     // Catch:{ all -> 0x0058 }
            goto L_0x001b
        L_0x006a:
            r10[r8] = r9     // Catch:{ Exception -> 0x006f }
            int r8 = r8 + 1
            goto L_0x0021
        L_0x006f:
            r1 = move-exception
            r3 = 0
            monitor-exit(r15)     // Catch:{ all -> 0x0058 }
            goto L_0x001b
        L_0x0073:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            r12.<init>()     // Catch:{ all -> 0x0058 }
            r8 = 0
        L_0x0079:
            if (r8 >= r14) goto L_0x0085
            r1 = r10[r8]     // Catch:{ all -> 0x0058 }
            java.lang.String r1 = r1.data     // Catch:{ all -> 0x0058 }
            r12.append(r1)     // Catch:{ all -> 0x0058 }
            int r8 = r8 + 1
            goto L_0x0079
        L_0x0085:
            java.lang.String r3 = r12.toString()     // Catch:{ all -> 0x0058 }
            r0 = r16
            com.alipay.mobile.base.config.impl.ConfigDataManager r1 = r0.mConfigDataManager     // Catch:{ all -> 0x0058 }
            r0 = r17
            java.lang.String r2 = r0.uniqId     // Catch:{ all -> 0x0058 }
            r0 = r17
            long r4 = r0.updateTime     // Catch:{ all -> 0x0058 }
            r0 = r16
            java.lang.String r7 = r0.mLoginUserId     // Catch:{ all -> 0x0058 }
            r6 = r18
            boolean r11 = r1.saveKeyValueWithTime(r2, r3, r4, r6, r7)     // Catch:{ all -> 0x0058 }
            r16.removeTempKeys(r17, r18)     // Catch:{ all -> 0x0058 }
            if (r11 == 0) goto L_0x00b9
            r0 = r17
            java.lang.String r1 = r0.uniqId     // Catch:{ all -> 0x0058 }
            r0 = r16
            r0.notifySyncArrived(r1, r3)     // Catch:{ all -> 0x0058 }
            r0 = r17
            java.lang.String r1 = r0.uniqId     // Catch:{ all -> 0x0058 }
            r0 = r16
            r0.notifyConfigChange(r1, r3)     // Catch:{ all -> 0x0058 }
            monitor-exit(r15)     // Catch:{ all -> 0x0058 }
            goto L_0x001b
        L_0x00b9:
            r3 = 0
            monitor-exit(r15)     // Catch:{ all -> 0x0058 }
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.base.config.impl.ConfigServiceImpl.saveSplitedData(com.alipay.mobile.base.config.model.PLData, boolean):java.lang.String");
    }

    private void removeTempKeys(PLData plDataTime, boolean onlyUser) {
        int size = plDataTime.total;
        for (int i = 0; i < size; i++) {
            this.mConfigDataManager.removeKey(plDataTime.uniqId + plDataTime.updateTime + i, onlyUser ? this.mLoginUserId : null);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyConfigChange(java.lang.String r11, java.lang.String r12) {
        /*
            r10 = this;
            boolean r4 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x008c }
            if (r4 == 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x008c }
            java.lang.String r5 = TAG     // Catch:{ Throwable -> 0x008c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008c }
            java.lang.String r7 = "notifyConfigChange start key is "
            r6.<init>(r7)     // Catch:{ Throwable -> 0x008c }
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r7 = " , value is "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x008c }
            java.lang.StringBuilder r6 = r6.append(r12)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x008c }
            r4.info(r5, r6)     // Catch:{ Throwable -> 0x008c }
            r10.notifySDK(r11, r12)     // Catch:{ Throwable -> 0x008c }
            java.util.List<java.lang.ref.SoftReference<com.alipay.mobile.base.config.ConfigService$ConfigChangeListener>> r5 = mConfigChangeListeners     // Catch:{ Throwable -> 0x008c }
            monitor-enter(r5)     // Catch:{ Throwable -> 0x008c }
            java.util.List<java.lang.ref.SoftReference<com.alipay.mobile.base.config.ConfigService$ConfigChangeListener>> r4 = mConfigChangeListeners     // Catch:{ all -> 0x0089 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x0089 }
        L_0x0035:
            boolean r6 = r4.hasNext()     // Catch:{ all -> 0x0089 }
            if (r6 == 0) goto L_0x0098
            java.lang.Object r2 = r4.next()     // Catch:{ all -> 0x0089 }
            java.lang.ref.SoftReference r2 = (java.lang.ref.SoftReference) r2     // Catch:{ all -> 0x0089 }
            if (r2 == 0) goto L_0x0035
            java.lang.Object r3 = r2.get()     // Catch:{ all -> 0x0089 }
            com.alipay.mobile.base.config.ConfigService$ConfigChangeListener r3 = (com.alipay.mobile.base.config.ConfigService.ConfigChangeListener) r3     // Catch:{ all -> 0x0089 }
            if (r3 == 0) goto L_0x0035
            java.util.List r1 = r3.getKeys()     // Catch:{ all -> 0x0089 }
            if (r1 == 0) goto L_0x0035
            boolean r6 = r1.isEmpty()     // Catch:{ all -> 0x0089 }
            if (r6 != 0) goto L_0x0035
            boolean r6 = r1.contains(r11)     // Catch:{ all -> 0x0089 }
            if (r6 == 0) goto L_0x0035
            r3.onConfigChange(r11, r12)     // Catch:{ Exception -> 0x0061 }
            goto L_0x0035
        L_0x0061:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0089 }
            java.lang.String r7 = TAG     // Catch:{ all -> 0x0089 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0089 }
            java.lang.String r9 = "notifyConfigChange Exception :  tmpListener.onSyncReceiver  "
            r8.<init>(r9)     // Catch:{ all -> 0x0089 }
            java.lang.String r9 = r0.getMessage()     // Catch:{ all -> 0x0089 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0089 }
            java.lang.String r9 = " error listener is : "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0089 }
            java.lang.StringBuilder r8 = r8.append(r3)     // Catch:{ all -> 0x0089 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0089 }
            r6.info(r7, r8)     // Catch:{ all -> 0x0089 }
            goto L_0x0035
        L_0x0089:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0089 }
            throw r4     // Catch:{ Throwable -> 0x008c }
        L_0x008c:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = TAG
            r4.error(r5, r0)
            goto L_0x0006
        L_0x0098:
            monitor-exit(r5)     // Catch:{ all -> 0x0089 }
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.base.config.impl.ConfigServiceImpl.notifyConfigChange(java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifySyncArrived(java.lang.String r11, java.lang.String r12) {
        /*
            r10 = this;
            boolean r4 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x0089 }
            if (r4 == 0) goto L_0x0007
        L_0x0006:
            return
        L_0x0007:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0089 }
            java.lang.String r5 = TAG     // Catch:{ Throwable -> 0x0089 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0089 }
            java.lang.String r7 = "notifySyncArrived start key is "
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0089 }
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ Throwable -> 0x0089 }
            java.lang.String r7 = " , value is "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x0089 }
            java.lang.StringBuilder r6 = r6.append(r12)     // Catch:{ Throwable -> 0x0089 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0089 }
            r4.info(r5, r6)     // Catch:{ Throwable -> 0x0089 }
            java.util.List<java.lang.ref.SoftReference<com.alipay.mobile.base.config.ConfigService$SyncReceiverListener>> r5 = slmacSyncReceiverListeners     // Catch:{ Throwable -> 0x0089 }
            monitor-enter(r5)     // Catch:{ Throwable -> 0x0089 }
            java.util.List<java.lang.ref.SoftReference<com.alipay.mobile.base.config.ConfigService$SyncReceiverListener>> r4 = slmacSyncReceiverListeners     // Catch:{ all -> 0x0086 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ all -> 0x0086 }
        L_0x0032:
            boolean r6 = r4.hasNext()     // Catch:{ all -> 0x0086 }
            if (r6 == 0) goto L_0x0095
            java.lang.Object r2 = r4.next()     // Catch:{ all -> 0x0086 }
            java.lang.ref.SoftReference r2 = (java.lang.ref.SoftReference) r2     // Catch:{ all -> 0x0086 }
            if (r2 == 0) goto L_0x0032
            java.lang.Object r3 = r2.get()     // Catch:{ all -> 0x0086 }
            com.alipay.mobile.base.config.ConfigService$SyncReceiverListener r3 = (com.alipay.mobile.base.config.ConfigService.SyncReceiverListener) r3     // Catch:{ all -> 0x0086 }
            if (r3 == 0) goto L_0x0032
            java.util.List r1 = r3.getKeys()     // Catch:{ all -> 0x0086 }
            if (r1 == 0) goto L_0x0032
            boolean r6 = r1.isEmpty()     // Catch:{ all -> 0x0086 }
            if (r6 != 0) goto L_0x0032
            boolean r6 = r1.contains(r11)     // Catch:{ all -> 0x0086 }
            if (r6 == 0) goto L_0x0032
            r3.onSyncReceiver(r11, r12)     // Catch:{ Exception -> 0x005e }
            goto L_0x0032
        L_0x005e:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r6 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0086 }
            java.lang.String r7 = TAG     // Catch:{ all -> 0x0086 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0086 }
            java.lang.String r9 = "notifySyncArrived Exception :  tmpListener.onSyncReceiver  "
            r8.<init>(r9)     // Catch:{ all -> 0x0086 }
            java.lang.String r9 = r0.getMessage()     // Catch:{ all -> 0x0086 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0086 }
            java.lang.String r9 = " error listener is : "
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0086 }
            java.lang.StringBuilder r8 = r8.append(r3)     // Catch:{ all -> 0x0086 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0086 }
            r6.info(r7, r8)     // Catch:{ all -> 0x0086 }
            goto L_0x0032
        L_0x0086:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0086 }
            throw r4     // Catch:{ Throwable -> 0x0089 }
        L_0x0089:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = TAG
            r4.error(r5, r0)
            goto L_0x0006
        L_0x0095:
            monitor-exit(r5)     // Catch:{ all -> 0x0086 }
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.base.config.impl.ConfigServiceImpl.notifySyncArrived(java.lang.String, java.lang.String):void");
    }

    private void notifySDK(String key, String value) {
        try {
            Map map = new HashMap();
            map.put(key, value);
            doRefreshSharedSwitch(this.mContextWrapper, map);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(TAG, (Throwable) e);
        }
    }

    public long getResponseTime() {
        if (LoggerFactory.getProcessInfo().isLiteProcess()) {
            return this.mConfigServiceLite.getResponseTime();
        }
        try {
            return Long.parseLong(this.mConfigDataManager.getString(RESERVE_CONFIG_KEY_RESPONSE_TIME, null));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error(TAG, e);
            return 0;
        }
    }

    private void doRefreshSharedSwitch(final ContextWrapper contextWrapper, final Map<String, String> map) {
        if (this.taskScheduleService != null) {
            this.taskScheduleService.acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
                public void run() {
                    SharedSwitchUtil.refreshSharedSwitch(contextWrapper, map);
                }
            });
        } else {
            SharedSwitchUtil.refreshSharedSwitch(contextWrapper, map);
        }
    }
}
