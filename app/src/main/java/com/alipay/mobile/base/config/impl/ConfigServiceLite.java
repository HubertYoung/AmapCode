package com.alipay.mobile.base.config.impl;

import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.base.config.ConfigService.ConfigChangeListener;
import com.alipay.mobile.base.config.ConfigService.ConfigLoadCallBack;
import com.alipay.mobile.base.config.ConfigService.ConfigSyncReporter;
import com.alipay.mobile.base.config.ConfigService.SyncReceiverListener;
import com.alipay.mobile.base.config.model.PLData;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConfigServiceLite extends ConfigService {
    private static final String RESERVE_CONFIG_KEY_USERID = "reserveConfigKeyUserId";
    private final String TAG = "ConfigServiceLite";
    private LinkedList<HashMap<String, String>> mChangedConfigs;
    /* access modifiers changed from: private */
    public ConfigDataManager mConfigDataManager;
    /* access modifiers changed from: private */
    public Uri mContentUri;
    /* access modifiers changed from: private */
    public ContextWrapper mContext;
    private long mFirstForegroundTime = -1;
    /* access modifiers changed from: private */
    public boolean mLoadLocalSp = false;
    /* access modifiers changed from: private */
    public String mLoginUserId;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.mContentUri = Uri.parse("content://" + this.mContext.getPackageName() + ".configprovider/config");
        checkAndInitConfigDataManager(true);
    }

    private void checkAndInitConfigDataManager(final boolean needCheckConfig) {
        TaskScheduleService scheduleService = (TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName());
        if (scheduleService != null) {
            scheduleService.schedule(new Runnable() {
                /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r7 = this;
                        boolean r2 = r7     // Catch:{ Throwable -> 0x0042 }
                        if (r2 == 0) goto L_0x0055
                        com.alipay.mobile.base.config.impl.ConfigServiceLite r2 = com.alipay.mobile.base.config.impl.ConfigServiceLite.this     // Catch:{ Throwable -> 0x0042 }
                        java.lang.String r3 = "reserveConfigKeyUserId"
                        java.lang.String r2 = r2.getConfigIpc(r3)     // Catch:{ Throwable -> 0x0042 }
                        boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x0042 }
                        if (r2 == 0) goto L_0x001e
                        com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0042 }
                        java.lang.String r3 = "ConfigServiceLite"
                        java.lang.String r4 = "user not login"
                        r2.debug(r3, r4)     // Catch:{ Throwable -> 0x0042 }
                    L_0x001d:
                        return
                    L_0x001e:
                        com.alipay.mobile.base.config.impl.ConfigServiceLite r2 = com.alipay.mobile.base.config.impl.ConfigServiceLite.this     // Catch:{ Throwable -> 0x0042 }
                        java.lang.String r3 = "lite_config_load_local_sp"
                        java.lang.String r0 = r2.getConfigIpc(r3)     // Catch:{ Throwable -> 0x0042 }
                        boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0042 }
                        if (r2 != 0) goto L_0x0036
                        java.lang.Boolean r2 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x0042 }
                        boolean r2 = r2.booleanValue()     // Catch:{ Throwable -> 0x0042 }
                        if (r2 != 0) goto L_0x004f
                    L_0x0036:
                        com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0042 }
                        java.lang.String r3 = "ConfigServiceLite"
                        java.lang.String r4 = "config is off, abandon load sp"
                        r2.debug(r3, r4)     // Catch:{ Throwable -> 0x0042 }
                        goto L_0x001d
                    L_0x0042:
                        r1 = move-exception
                        com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
                        java.lang.String r3 = "ConfigServiceLite"
                        java.lang.String r4 = "checkAndInitConfigDataManager error!"
                        r2.warn(r3, r4, r1)
                        goto L_0x001d
                    L_0x004f:
                        com.alipay.mobile.base.config.impl.ConfigServiceLite r2 = com.alipay.mobile.base.config.impl.ConfigServiceLite.this     // Catch:{ Throwable -> 0x0042 }
                        r3 = 1
                        r2.mLoadLocalSp = r3     // Catch:{ Throwable -> 0x0042 }
                    L_0x0055:
                        java.lang.Class<com.alipay.mobile.base.config.impl.ConfigServiceLite> r3 = com.alipay.mobile.base.config.impl.ConfigServiceLite.class
                        monitor-enter(r3)     // Catch:{ Throwable -> 0x0042 }
                        com.alipay.mobile.base.config.impl.ConfigServiceLite r2 = com.alipay.mobile.base.config.impl.ConfigServiceLite.this     // Catch:{ all -> 0x0062 }
                        com.alipay.mobile.base.config.impl.ConfigDataManager r2 = r2.mConfigDataManager     // Catch:{ all -> 0x0062 }
                        if (r2 == 0) goto L_0x0065
                        monitor-exit(r3)     // Catch:{ all -> 0x0062 }
                        goto L_0x001d
                    L_0x0062:
                        r2 = move-exception
                        monitor-exit(r3)     // Catch:{ all -> 0x0062 }
                        throw r2     // Catch:{ Throwable -> 0x0042 }
                    L_0x0065:
                        com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0062 }
                        java.lang.String r4 = "ConfigServiceLite"
                        java.lang.String r5 = "init config data manager begin"
                        r2.debug(r4, r5)     // Catch:{ all -> 0x0062 }
                        com.alipay.mobile.base.config.impl.ConfigServiceLite r2 = com.alipay.mobile.base.config.impl.ConfigServiceLite.this     // Catch:{ all -> 0x0062 }
                        com.alipay.mobile.base.config.impl.ConfigServiceLite r4 = com.alipay.mobile.base.config.impl.ConfigServiceLite.this     // Catch:{ all -> 0x0062 }
                        android.content.ContextWrapper r4 = r4.mContext     // Catch:{ all -> 0x0062 }
                        com.alipay.mobile.base.config.impl.ConfigDataManager r4 = com.alipay.mobile.base.config.impl.ConfigDataManager.getInstance(r4)     // Catch:{ all -> 0x0062 }
                        r2.mConfigDataManager = r4     // Catch:{ all -> 0x0062 }
                        com.alipay.mobile.base.config.impl.ConfigServiceLite r2 = com.alipay.mobile.base.config.impl.ConfigServiceLite.this     // Catch:{ all -> 0x0062 }
                        com.alipay.mobile.base.config.impl.ConfigServiceLite r4 = com.alipay.mobile.base.config.impl.ConfigServiceLite.this     // Catch:{ all -> 0x0062 }
                        com.alipay.mobile.base.config.impl.ConfigDataManager r4 = r4.mConfigDataManager     // Catch:{ all -> 0x0062 }
                        java.lang.String r5 = "reserveConfigKeyUserId"
                        r6 = 0
                        java.lang.String r4 = r4.getString(r5, r6)     // Catch:{ all -> 0x0062 }
                        r2.mLoginUserId = r4     // Catch:{ all -> 0x0062 }
                        com.alipay.mobile.common.logging.api.trace.TraceLogger r2 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0062 }
                        java.lang.String r4 = "ConfigServiceLite"
                        java.lang.String r5 = "init config data manager end"
                        r2.debug(r4, r5)     // Catch:{ all -> 0x0062 }
                        monitor-exit(r3)     // Catch:{ all -> 0x0062 }
                        goto L_0x001d
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.base.config.impl.ConfigServiceLite.AnonymousClass1.run():void");
                }
            }, "checkAndInitConfigDataManager", 0, TimeUnit.MICROSECONDS);
        }
    }

    public void setContext(ContextWrapper context) {
        this.mContext = context;
    }

    public String getConfig(String key) {
        if (getConfigDataManager() == null) {
            return getConfigIpc(key);
        }
        return getConfigFromSp(key);
    }

    /* access modifiers changed from: private */
    public ConfigDataManager getConfigDataManager() {
        if (this.mFirstForegroundTime == -1 && !ActivityHelper.isBackgroundRunning() && !this.mLoadLocalSp) {
            this.mFirstForegroundTime = System.currentTimeMillis();
            checkAndInitConfigDataManager(false);
        } else if (this.mFirstForegroundTime != -1 && System.currentTimeMillis() - this.mFirstForegroundTime > 10000 && this.mConfigDataManager != null && !this.mLoadLocalSp) {
            ConfigDataManager.unload();
            this.mConfigDataManager = null;
        }
        return this.mConfigDataManager;
    }

    public String getConfigIpc(String key) {
        try {
            Cursor cursor = this.mContext.getContentResolver().query(this.mContentUri, new String[]{key}, null, null, "");
            cursor.moveToFirst();
            String ret = cursor.getString(0);
            cursor.close();
            return ret;
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().warn("ConfigServiceLite", "getConfig error", t);
            return "";
        }
    }

    /* access modifiers changed from: private */
    public String getConfigFromSp(String key) {
        String stringOnlyUser = this.mConfigDataManager.getString(key, null, this.mLoginUserId);
        if (!TextUtils.isEmpty(stringOnlyUser)) {
            return stringOnlyUser;
        }
        if (this.mChangedConfigs != null && this.mChangedConfigs.size() > 0) {
            synchronized (this.mChangedConfigs) {
                Iterator it = this.mChangedConfigs.iterator();
                while (it.hasNext()) {
                    HashMap configs = (HashMap) it.next();
                    if (configs.get(key) != null) {
                        String stringOnlyUser2 = (String) configs.get(key);
                        return stringOnlyUser2;
                    }
                }
            }
        }
        return this.mConfigDataManager.getString(key, null);
    }

    public void loadConfig() {
    }

    public void loadConfigImmediately(long delay) {
    }

    public void refreshAfterLogin(String userId) {
    }

    public void refreshAfterLogout() {
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void saveConfigs(java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
            r7 = this;
            r4 = 0
            boolean r3 = r7.mLoadLocalSp     // Catch:{ Throwable -> 0x003c }
            if (r3 != 0) goto L_0x0006
        L_0x0005:
            return
        L_0x0006:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x003c }
            java.lang.String r5 = "ConfigServiceLite"
            java.lang.String r6 = "receive config change broadcast"
            r3.debug(r5, r6)     // Catch:{ Throwable -> 0x003c }
            java.lang.String r3 = "data_overflow"
            java.lang.Object r3 = r8.get(r3)     // Catch:{ Throwable -> 0x003c }
            if (r3 == 0) goto L_0x0058
            java.lang.String r3 = "data_overflow"
            java.lang.Object r3 = r8.get(r3)     // Catch:{ Throwable -> 0x003c }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x003c }
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ Throwable -> 0x003c }
            boolean r3 = r3.booleanValue()     // Catch:{ Throwable -> 0x003c }
            if (r3 == 0) goto L_0x0058
            r3 = 1
        L_0x002c:
            if (r3 == 0) goto L_0x005a
            com.alipay.mobile.base.config.impl.ConfigDataManager r3 = r7.mConfigDataManager     // Catch:{ Throwable -> 0x003c }
            if (r3 == 0) goto L_0x0005
            com.alipay.mobile.base.config.impl.ConfigDataManager.unload()     // Catch:{ Throwable -> 0x003c }
            r3 = 0
            r7.mConfigDataManager = r3     // Catch:{ Throwable -> 0x003c }
            r3 = 0
            r7.mChangedConfigs = r3     // Catch:{ Throwable -> 0x003c }
            goto L_0x0005
        L_0x003c:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "ConfigServiceLite"
            java.lang.String r5 = "handle config change error!"
            r3.warn(r4, r5, r2)
            com.alipay.mobile.base.config.impl.ConfigDataManager r3 = r7.mConfigDataManager     // Catch:{ Throwable -> 0x0056 }
            if (r3 == 0) goto L_0x0005
            com.alipay.mobile.base.config.impl.ConfigDataManager.unload()     // Catch:{ Throwable -> 0x0056 }
            r3 = 0
            r7.mConfigDataManager = r3     // Catch:{ Throwable -> 0x0056 }
            r3 = 0
            r7.mChangedConfigs = r3     // Catch:{ Throwable -> 0x0056 }
            goto L_0x0005
        L_0x0056:
            r3 = move-exception
            goto L_0x0005
        L_0x0058:
            r3 = r4
            goto L_0x002c
        L_0x005a:
            com.alipay.mobile.base.config.impl.ConfigDataManager r3 = r7.mConfigDataManager     // Catch:{ Throwable -> 0x003c }
            if (r3 == 0) goto L_0x0005
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x003c }
            java.lang.String r4 = "ConfigServiceLite"
            java.lang.String r5 = "receive config change broadcast, save changed configs"
            r3.debug(r4, r5)     // Catch:{ Throwable -> 0x003c }
            java.lang.String r3 = "changed_configs"
            java.lang.Object r1 = r8.get(r3)     // Catch:{ Throwable -> 0x003c }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x003c }
            if (r1 == 0) goto L_0x009c
            com.alipay.mobile.base.config.impl.ConfigServiceLite$2 r3 = new com.alipay.mobile.base.config.impl.ConfigServiceLite$2     // Catch:{ Throwable -> 0x003c }
            r3.<init>()     // Catch:{ Throwable -> 0x003c }
            r4 = 0
            com.alibaba.fastjson.parser.Feature[] r4 = new com.alibaba.fastjson.parser.Feature[r4]     // Catch:{ Throwable -> 0x003c }
            java.lang.Object r0 = com.alibaba.fastjson.JSON.parseObject(r1, r3, r4)     // Catch:{ Throwable -> 0x003c }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x003c }
            java.util.LinkedList<java.util.HashMap<java.lang.String, java.lang.String>> r3 = r7.mChangedConfigs     // Catch:{ Throwable -> 0x003c }
            if (r3 != 0) goto L_0x008c
            java.util.LinkedList r3 = new java.util.LinkedList     // Catch:{ Throwable -> 0x003c }
            r3.<init>()     // Catch:{ Throwable -> 0x003c }
            r7.mChangedConfigs = r3     // Catch:{ Throwable -> 0x003c }
        L_0x008c:
            if (r0 == 0) goto L_0x0005
            java.util.LinkedList<java.util.HashMap<java.lang.String, java.lang.String>> r4 = r7.mChangedConfigs     // Catch:{ Throwable -> 0x003c }
            monitor-enter(r4)     // Catch:{ Throwable -> 0x003c }
            java.util.LinkedList<java.util.HashMap<java.lang.String, java.lang.String>> r3 = r7.mChangedConfigs     // Catch:{ all -> 0x0099 }
            r3.addFirst(r0)     // Catch:{ all -> 0x0099 }
            monitor-exit(r4)     // Catch:{ all -> 0x0099 }
            goto L_0x0005
        L_0x0099:
            r3 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0099 }
            throw r3     // Catch:{ Throwable -> 0x003c }
        L_0x009c:
            java.util.LinkedList<java.util.HashMap<java.lang.String, java.lang.String>> r3 = r7.mChangedConfigs     // Catch:{ Throwable -> 0x003c }
            if (r3 != 0) goto L_0x00a7
            java.util.LinkedList r3 = new java.util.LinkedList     // Catch:{ Throwable -> 0x003c }
            r3.<init>()     // Catch:{ Throwable -> 0x003c }
            r7.mChangedConfigs = r3     // Catch:{ Throwable -> 0x003c }
        L_0x00a7:
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x003c }
            r0.<init>()     // Catch:{ Throwable -> 0x003c }
            r0.putAll(r8)     // Catch:{ Throwable -> 0x003c }
            java.util.LinkedList<java.util.HashMap<java.lang.String, java.lang.String>> r4 = r7.mChangedConfigs     // Catch:{ Throwable -> 0x003c }
            monitor-enter(r4)     // Catch:{ Throwable -> 0x003c }
            java.util.LinkedList<java.util.HashMap<java.lang.String, java.lang.String>> r3 = r7.mChangedConfigs     // Catch:{ all -> 0x00ba }
            r3.addFirst(r0)     // Catch:{ all -> 0x00ba }
            monitor-exit(r4)     // Catch:{ all -> 0x00ba }
            goto L_0x0005
        L_0x00ba:
            r3 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00ba }
            throw r3     // Catch:{ Throwable -> 0x003c }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.base.config.impl.ConfigServiceLite.saveConfigs(java.util.Map):void");
    }

    public void getConfig(final String key, final ConfigLoadCallBack configLoadCallBack) {
        new Thread(new Runnable() {
            public void run() {
                String config = null;
                if (ConfigServiceLite.this.getConfigDataManager() != null) {
                    config = ConfigServiceLite.this.getConfigFromSp(key);
                }
                if (TextUtils.isEmpty(config)) {
                    Cursor cursor = ConfigServiceLite.this.mContext.getContentResolver().query(ConfigServiceLite.this.mContentUri, new String[]{key}, null, null, "");
                    cursor.moveToFirst();
                    String ret = cursor.getString(0);
                    cursor.close();
                    String config2 = ret;
                    if (!TextUtils.isEmpty(config2) && configLoadCallBack != null) {
                        configLoadCallBack.onLoaded(key, config2);
                    }
                } else if (configLoadCallBack != null) {
                    configLoadCallBack.onLoaded(key, config);
                }
            }
        }).start();
    }

    public void registerSyncReceiverListener(SyncReceiverListener syncReceiverListener) {
    }

    public void unregisterSyncReceiverListener(SyncReceiverListener syncReceiverListener) {
    }

    public boolean isRegistered(SyncReceiverListener syncReceiverListener) {
        return false;
    }

    public void preloadKeys(List<String> keys) {
    }

    public String saveConfig(PLData plData) {
        return null;
    }

    public String saveConfig(PLData plData, boolean persist, boolean onlyUser) {
        return null;
    }

    public long getResponseTime() {
        return 0;
    }

    public boolean addConfigChangeListener(ConfigChangeListener configChangeListener) {
        return false;
    }

    public void removeConfigChangeListener(ConfigChangeListener configChangeListener) {
    }

    public ConfigSyncReporter getConfigSyncReporter() {
        return null;
    }

    public void setConfigSyncReporter(ConfigSyncReporter reporter) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
