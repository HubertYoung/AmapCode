package com.alibaba.analytics.core.config;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.utils.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class SystemConfigMgr extends UTOrangeConfBiz {
    private static final String DELAY = "delay";
    private static SystemConfigMgr mInstance;
    private final Map<String, String> mKVStore = Collections.synchronizedMap(new HashMap());
    private final Map<String, List<IKVChangeListener>> mListeners = Collections.synchronizedMap(new HashMap());
    private final Map<String, UTSystemDelayItem> mSystemDelayItemMap = new HashMap();
    private final String[] namespace = {"utap_system"};

    public interface IKVChangeListener {
        void onChange(String str, String str2);
    }

    static class UTSystemDelayItem {
        private static final String KEY_ALL_D = "all_d";
        private static final String KEY_ARG1 = "arg1";
        private int mAllDelay = -1;
        private List<String> mArg1List = new ArrayList();

        private UTSystemDelayItem() {
        }

        public static UTSystemDelayItem parseJson(String str) {
            try {
                UTSystemDelayItem uTSystemDelayItem = new UTSystemDelayItem();
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(KEY_ALL_D)) {
                    uTSystemDelayItem.mAllDelay = jSONObject.optInt(KEY_ALL_D, -1);
                }
                if (jSONObject.has(KEY_ARG1)) {
                    ArrayList arrayList = new ArrayList();
                    JSONArray jSONArray = jSONObject.getJSONArray(KEY_ARG1);
                    if (jSONArray != null) {
                        for (int i = 0; i < jSONArray.length(); i++) {
                            arrayList.add(jSONArray.getString(i));
                        }
                    }
                    uTSystemDelayItem.mArg1List = arrayList;
                }
                return uTSystemDelayItem;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public boolean checkDelay(String str) {
            if (this.mAllDelay == 0) {
                return matchArg1Name(str);
            }
            return 1 == this.mAllDelay && !matchArg1Name(str);
        }

        private boolean matchArg1Name(String str) {
            if (!TextUtils.isEmpty(str) && this.mArg1List != null) {
                for (int i = 0; i < this.mArg1List.size(); i++) {
                    String str2 = this.mArg1List.get(i);
                    if (!TextUtils.isEmpty(str2)) {
                        if (str2.length() <= 2 || !str2.startsWith("%") || !str2.endsWith("%")) {
                            if (str.equals(str2)) {
                                return true;
                            }
                        } else if (str.contains(str2.substring(1, str2.length() - 1))) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    private SystemConfigMgr() {
        try {
            if (Variables.getInstance().getDbMgr() != null) {
                List<? extends Entity> find = Variables.getInstance().getDbMgr().find(SystemConfig.class, null, null, -1);
                if (find.size() > 0) {
                    Map synchronizedMap = Collections.synchronizedMap(new HashMap(find.size()));
                    for (int i = 0; i < find.size(); i++) {
                        synchronizedMap.put(((SystemConfig) find.get(i)).key, ((SystemConfig) find.get(i)).value);
                    }
                    updateConfig(synchronizedMap);
                }
            }
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    public static synchronized SystemConfigMgr getInstance() {
        SystemConfigMgr systemConfigMgr;
        synchronized (SystemConfigMgr.class) {
            try {
                if (mInstance == null) {
                    mInstance = new SystemConfigMgr();
                }
                systemConfigMgr = mInstance;
            }
        }
        return systemConfigMgr;
    }

    public String get(String str) {
        return this.mKVStore.get(str);
    }

    public void register(String str, IKVChangeListener iKVChangeListener) {
        List list;
        synchronized (this.mListeners) {
            if (this.mListeners.get(str) == null) {
                list = new ArrayList();
            } else {
                list = this.mListeners.get(str);
            }
            list.add(iKVChangeListener);
            this.mListeners.put(str, list);
        }
    }

    public void unRegister(String str, IKVChangeListener iKVChangeListener) {
        List list = this.mListeners.get(str);
        if (list != null) {
            list.remove(iKVChangeListener);
        }
        if (list == null || list.size() == 0) {
            this.mKVStore.remove(str);
        }
    }

    public String[] getOrangeGroupnames() {
        return this.namespace;
    }

    public void onOrangeConfigurationArrive(String str, Map<String, String> map) {
        if ("utap_system".equalsIgnoreCase(str)) {
            updateConfig(map);
            Variables.getInstance().getDbMgr().clear(SystemConfig.class);
            Variables.getInstance().getDbMgr().insert(mapToList(this.mKVStore));
        }
    }

    private void updateConfig(Map<String, String> map) {
        updateSystemDelayItemMap(map);
        HashMap hashMap = new HashMap(this.mKVStore.size());
        hashMap.putAll(this.mKVStore);
        this.mKVStore.clear();
        this.mKVStore.putAll(map);
        for (String next : this.mKVStore.keySet()) {
            if ((this.mKVStore.get(next) == null && hashMap.get(next) != null) || (this.mKVStore.get(next) != null && !this.mKVStore.get(next).equalsIgnoreCase((String) hashMap.get(next)))) {
                dispatch(next, this.mKVStore.get(next));
            }
            hashMap.remove(next);
        }
        for (String str : hashMap.keySet()) {
            dispatch(str, this.mKVStore.get(str));
        }
    }

    private void dispatch(String str, String str2) {
        List list = this.mListeners.get(str);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ((IKVChangeListener) list.get(i)).onChange(str, str2);
            }
        }
        UTConfigMgr.postServerConfig(str, str2);
    }

    private List<Entity> mapToList(Map<String, String> map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (String next : map.keySet()) {
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.key = next;
            systemConfig.value = map.get(next);
            arrayList.add(systemConfig);
        }
        return arrayList;
    }

    public int getInt(String str) {
        String str2 = get(str);
        if (TextUtils.isEmpty(str2)) {
            return 0;
        }
        try {
            return Integer.valueOf(str2).intValue();
        } catch (Exception unused) {
            return 0;
        }
    }

    private synchronized void updateSystemDelayItemMap(Map<String, String> map) {
        if (map != null) {
            try {
                if (map.containsKey(DELAY)) {
                    if ((this.mKVStore.get(DELAY) == null || !map.get(DELAY).equals(this.mKVStore.get(DELAY))) && this.mSystemDelayItemMap != null) {
                        this.mSystemDelayItemMap.clear();
                        JSONObject jSONObject = new JSONObject(map.get(DELAY));
                        Iterator<String> keys = jSONObject.keys();
                        if (keys != null) {
                            while (keys.hasNext()) {
                                String next = keys.next();
                                String string = jSONObject.getString(next);
                                if (!TextUtils.isEmpty(string)) {
                                    UTSystemDelayItem parseJson = UTSystemDelayItem.parseJson(string);
                                    if (parseJson != null) {
                                        this.mSystemDelayItemMap.put(next, parseJson);
                                    }
                                }
                            }
                            return;
                        }
                        return;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
        if (this.mSystemDelayItemMap != null) {
            this.mSystemDelayItemMap.clear();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0097, code lost:
        return false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003f A[Catch:{ Exception -> 0x002e }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045 A[SYNTHETIC, Splitter:B:21:0x0045] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean checkDelayLog(java.util.Map<java.lang.String, java.lang.String> r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.util.Map<java.lang.String, com.alibaba.analytics.core.config.SystemConfigMgr$UTSystemDelayItem> r0 = r5.mSystemDelayItemMap     // Catch:{ all -> 0x0098 }
            r1 = 0
            if (r0 == 0) goto L_0x0096
            java.util.Map<java.lang.String, com.alibaba.analytics.core.config.SystemConfigMgr$UTSystemDelayItem> r0 = r5.mSystemDelayItemMap     // Catch:{ all -> 0x0098 }
            int r0 = r0.size()     // Catch:{ all -> 0x0098 }
            if (r0 > 0) goto L_0x0010
            goto L_0x0096
        L_0x0010:
            com.alibaba.analytics.core.model.LogField r0 = com.alibaba.analytics.core.model.LogField.EVENTID     // Catch:{ all -> 0x0098 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0098 }
            boolean r0 = r6.containsKey(r0)     // Catch:{ all -> 0x0098 }
            r2 = -1
            if (r0 == 0) goto L_0x0032
            com.alibaba.analytics.core.model.LogField r0 = com.alibaba.analytics.core.model.LogField.EVENTID     // Catch:{ Exception -> 0x002e }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x002e }
            java.lang.Object r0 = r6.get(r0)     // Catch:{ Exception -> 0x002e }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x002e }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x002e }
            goto L_0x0033
        L_0x002e:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0098 }
        L_0x0032:
            r0 = -1
        L_0x0033:
            java.util.Map<java.lang.String, com.alibaba.analytics.core.config.SystemConfigMgr$UTSystemDelayItem> r3 = r5.mSystemDelayItemMap     // Catch:{ all -> 0x0098 }
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0098 }
            boolean r3 = r3.containsKey(r4)     // Catch:{ all -> 0x0098 }
            if (r3 == 0) goto L_0x0045
            boolean r6 = r5.checkDelay(r6, r0)     // Catch:{ all -> 0x0098 }
            monitor-exit(r5)
            return r6
        L_0x0045:
            int r3 = r0 % 10
            int r0 = r0 - r3
            java.util.Map<java.lang.String, com.alibaba.analytics.core.config.SystemConfigMgr$UTSystemDelayItem> r3 = r5.mSystemDelayItemMap     // Catch:{ all -> 0x0098 }
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0098 }
            boolean r3 = r3.containsKey(r4)     // Catch:{ all -> 0x0098 }
            if (r3 == 0) goto L_0x005a
            boolean r6 = r5.checkDelay(r6, r0)     // Catch:{ all -> 0x0098 }
            monitor-exit(r5)
            return r6
        L_0x005a:
            int r3 = r0 % 100
            int r0 = r0 - r3
            java.util.Map<java.lang.String, com.alibaba.analytics.core.config.SystemConfigMgr$UTSystemDelayItem> r3 = r5.mSystemDelayItemMap     // Catch:{ all -> 0x0098 }
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0098 }
            boolean r3 = r3.containsKey(r4)     // Catch:{ all -> 0x0098 }
            if (r3 == 0) goto L_0x006f
            boolean r6 = r5.checkDelay(r6, r0)     // Catch:{ all -> 0x0098 }
            monitor-exit(r5)
            return r6
        L_0x006f:
            int r3 = r0 % 1000
            int r0 = r0 - r3
            java.util.Map<java.lang.String, com.alibaba.analytics.core.config.SystemConfigMgr$UTSystemDelayItem> r3 = r5.mSystemDelayItemMap     // Catch:{ all -> 0x0098 }
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0098 }
            boolean r3 = r3.containsKey(r4)     // Catch:{ all -> 0x0098 }
            if (r3 == 0) goto L_0x0084
            boolean r6 = r5.checkDelay(r6, r0)     // Catch:{ all -> 0x0098 }
            monitor-exit(r5)
            return r6
        L_0x0084:
            java.util.Map<java.lang.String, com.alibaba.analytics.core.config.SystemConfigMgr$UTSystemDelayItem> r0 = r5.mSystemDelayItemMap     // Catch:{ all -> 0x0098 }
            java.lang.String r3 = "-1"
            boolean r0 = r0.containsKey(r3)     // Catch:{ all -> 0x0098 }
            if (r0 == 0) goto L_0x0094
            boolean r6 = r5.checkDelay(r6, r2)     // Catch:{ all -> 0x0098 }
            monitor-exit(r5)
            return r6
        L_0x0094:
            monitor-exit(r5)
            return r1
        L_0x0096:
            monitor-exit(r5)
            return r1
        L_0x0098:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.analytics.core.config.SystemConfigMgr.checkDelayLog(java.util.Map):boolean");
    }

    private boolean checkDelay(Map<String, String> map, int i) {
        UTSystemDelayItem uTSystemDelayItem = this.mSystemDelayItemMap.get(String.valueOf(i));
        if (uTSystemDelayItem == null) {
            return false;
        }
        String str = null;
        if (map.containsKey(LogField.ARG1.toString())) {
            str = map.get(LogField.ARG1.toString());
        }
        return uTSystemDelayItem.checkDelay(str);
    }
}
