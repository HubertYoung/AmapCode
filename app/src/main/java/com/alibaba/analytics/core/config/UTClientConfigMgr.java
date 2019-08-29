package com.alibaba.analytics.core.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.alibaba.analytics.core.ClientVariables;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UTClientConfigMgr {
    private static final String TAG = "UTClientConfigMgr";
    private static UTClientConfigMgr mInstance;
    private boolean bInit = false;
    private Map<String, String> mConfigMap = Collections.synchronizedMap(new HashMap());
    private Map<String, List<IConfigChangeListener>> mListeners = Collections.synchronizedMap(new HashMap());

    class ConfigReceiver extends BroadcastReceiver {
        ConfigReceiver() {
        }

        public void onReceive(final Context context, final Intent intent) {
            TaskExecutor.getInstance().submit(new Runnable() {
                public void run() {
                    try {
                        String packageName = context.getPackageName();
                        if (!TextUtils.isEmpty(packageName)) {
                            String str = intent.getPackage();
                            if (!TextUtils.isEmpty(str)) {
                                if (packageName.equalsIgnoreCase(str)) {
                                    UTClientConfigMgr.this.dispatchConfig(intent.getStringExtra("key"), intent.getStringExtra("value"));
                                }
                            }
                        }
                    } catch (Throwable th) {
                        Logger.e(UTClientConfigMgr.TAG, th, new Object[0]);
                    }
                }
            });
        }
    }

    public interface IConfigChangeListener {
        String getKey();

        void onChange(String str);
    }

    private UTClientConfigMgr() {
    }

    public static UTClientConfigMgr getInstance() {
        if (mInstance == null) {
            synchronized (UTClientConfigMgr.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new UTClientConfigMgr();
                    }
                }
            }
        }
        return mInstance;
    }

    public synchronized void init() {
        if (!this.bInit) {
            try {
                ClientVariables.getInstance().getContext().registerReceiver(new ConfigReceiver(), new IntentFilter("com.alibaba.analytics.config.change"));
                this.bInit = true;
                Logger.d((String) TAG, "registerReceiver");
            } catch (Throwable th) {
                Logger.w(TAG, th, new Object[0]);
            }
        }
    }

    public synchronized String get(String str) {
        try {
        }
        return this.mConfigMap.get(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void registerConfigChangeListener(com.alibaba.analytics.core.config.UTClientConfigMgr.IConfigChangeListener r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L_0x004e
            java.lang.String r0 = r4.getKey()     // Catch:{ all -> 0x004b }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x004b }
            if (r0 == 0) goto L_0x000e
            goto L_0x004e
        L_0x000e:
            java.lang.String r0 = r4.getKey()     // Catch:{ all -> 0x004b }
            java.util.Map<java.lang.String, java.lang.String> r1 = r3.mConfigMap     // Catch:{ all -> 0x004b }
            boolean r1 = r1.containsKey(r0)     // Catch:{ all -> 0x004b }
            if (r1 == 0) goto L_0x0025
            java.util.Map<java.lang.String, java.lang.String> r1 = r3.mConfigMap     // Catch:{ all -> 0x004b }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ all -> 0x004b }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x004b }
            r4.onChange(r1)     // Catch:{ all -> 0x004b }
        L_0x0025:
            java.util.Map<java.lang.String, java.util.List<com.alibaba.analytics.core.config.UTClientConfigMgr$IConfigChangeListener>> r1 = r3.mListeners     // Catch:{ all -> 0x004b }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ all -> 0x004b }
            if (r1 != 0) goto L_0x0033
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x004b }
            r1.<init>()     // Catch:{ all -> 0x004b }
            goto L_0x003b
        L_0x0033:
            java.util.Map<java.lang.String, java.util.List<com.alibaba.analytics.core.config.UTClientConfigMgr$IConfigChangeListener>> r1 = r3.mListeners     // Catch:{ all -> 0x004b }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ all -> 0x004b }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x004b }
        L_0x003b:
            boolean r2 = r1.contains(r4)     // Catch:{ all -> 0x004b }
            if (r2 != 0) goto L_0x0044
            r1.add(r4)     // Catch:{ all -> 0x004b }
        L_0x0044:
            java.util.Map<java.lang.String, java.util.List<com.alibaba.analytics.core.config.UTClientConfigMgr$IConfigChangeListener>> r4 = r3.mListeners     // Catch:{ all -> 0x004b }
            r4.put(r0, r1)     // Catch:{ all -> 0x004b }
            monitor-exit(r3)
            return
        L_0x004b:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        L_0x004e:
            monitor-exit(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.config.UTClientConfigMgr.registerConfigChangeListener(com.alibaba.analytics.core.config.UTClientConfigMgr$IConfigChangeListener):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0020, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0025, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void unRegisterConfigChangeListener(com.alibaba.analytics.core.config.UTClientConfigMgr.IConfigChangeListener r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 == 0) goto L_0x0024
            java.lang.String r0 = r3.getKey()     // Catch:{ all -> 0x0021 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x000e
            goto L_0x0024
        L_0x000e:
            java.lang.String r0 = r3.getKey()     // Catch:{ all -> 0x0021 }
            java.util.Map<java.lang.String, java.util.List<com.alibaba.analytics.core.config.UTClientConfigMgr$IConfigChangeListener>> r1 = r2.mListeners     // Catch:{ all -> 0x0021 }
            java.lang.Object r0 = r1.get(r0)     // Catch:{ all -> 0x0021 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x001f
            r0.remove(r3)     // Catch:{ all -> 0x0021 }
        L_0x001f:
            monitor-exit(r2)
            return
        L_0x0021:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        L_0x0024:
            monitor-exit(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.config.UTClientConfigMgr.unRegisterConfigChangeListener(com.alibaba.analytics.core.config.UTClientConfigMgr$IConfigChangeListener):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0044, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void dispatchConfig(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.lang.String r0 = "UTClientConfigMgr"
            r1 = 4
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0045 }
            java.lang.String r2 = "dispatchConfig key"
            r3 = 0
            r1[r3] = r2     // Catch:{ all -> 0x0045 }
            r2 = 1
            r1[r2] = r6     // Catch:{ all -> 0x0045 }
            r2 = 2
            java.lang.String r4 = "value"
            r1[r2] = r4     // Catch:{ all -> 0x0045 }
            r2 = 3
            r1[r2] = r7     // Catch:{ all -> 0x0045 }
            com.alibaba.analytics.utils.Logger.d(r0, r1)     // Catch:{ all -> 0x0045 }
            boolean r0 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0045 }
            if (r0 == 0) goto L_0x0022
            monitor-exit(r5)
            return
        L_0x0022:
            java.util.Map<java.lang.String, java.lang.String> r0 = r5.mConfigMap     // Catch:{ all -> 0x0045 }
            r0.put(r6, r7)     // Catch:{ all -> 0x0045 }
            java.util.Map<java.lang.String, java.util.List<com.alibaba.analytics.core.config.UTClientConfigMgr$IConfigChangeListener>> r0 = r5.mListeners     // Catch:{ all -> 0x0045 }
            java.lang.Object r6 = r0.get(r6)     // Catch:{ all -> 0x0045 }
            java.util.List r6 = (java.util.List) r6     // Catch:{ all -> 0x0045 }
            if (r6 == 0) goto L_0x0043
        L_0x0031:
            int r0 = r6.size()     // Catch:{ all -> 0x0045 }
            if (r3 >= r0) goto L_0x0043
            java.lang.Object r0 = r6.get(r3)     // Catch:{ all -> 0x0045 }
            com.alibaba.analytics.core.config.UTClientConfigMgr$IConfigChangeListener r0 = (com.alibaba.analytics.core.config.UTClientConfigMgr.IConfigChangeListener) r0     // Catch:{ all -> 0x0045 }
            r0.onChange(r7)     // Catch:{ all -> 0x0045 }
            int r3 = r3 + 1
            goto L_0x0031
        L_0x0043:
            monitor-exit(r5)
            return
        L_0x0045:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.config.UTClientConfigMgr.dispatchConfig(java.lang.String, java.lang.String):void");
    }
}
