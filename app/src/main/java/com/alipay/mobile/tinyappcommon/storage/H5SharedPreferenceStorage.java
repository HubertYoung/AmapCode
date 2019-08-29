package com.alipay.mobile.tinyappcommon.storage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.StorageInterface;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class H5SharedPreferenceStorage implements StorageInterface {
    private static final String FILE_NAME = "tiny_app_common_storage";
    private static final String PERFORMANCE_KEY = "performance_key";
    private static final String TAG = H5SharedPreferenceStorage.class.getSimpleName();
    private static final String VCONSOLE_KEY = "vconsole_key";
    private Bundle mCacheDataBundle;
    private ConcurrentHashMap<String, Integer> mIntStorageCache;
    private MainProcProxyListener mMainProcProxyListener;
    private SharedPreferences mSharedPreferences;
    private ConcurrentHashMap<String, String> mStorageCache;

    private static class H5SharedPreferenceStorageInner {
        public static H5SharedPreferenceStorage INSTANCE = new H5SharedPreferenceStorage();

        private H5SharedPreferenceStorageInner() {
        }
    }

    public interface MainProcProxyListener {
        boolean getAssistantPanelSwitch();

        int getDefaultCurrentStorageSize(String str);

        void putInt(String str, int i, boolean z);

        void remove(String str);

        void setPerformancePanelVisible(String str, boolean z);

        void setVConsoleVisible(String str, boolean z);
    }

    private H5SharedPreferenceStorage() {
        this.mStorageCache = new ConcurrentHashMap<>();
        this.mIntStorageCache = new ConcurrentHashMap<>();
        this.mSharedPreferences = H5Utils.getContext().getSharedPreferences(FILE_NAME, 4);
    }

    public static H5SharedPreferenceStorage getInstance() {
        return H5SharedPreferenceStorageInner.INSTANCE;
    }

    public void registerMainProcProxyListener(MainProcProxyListener listener) {
        this.mMainProcProxyListener = listener;
    }

    public synchronized void initLoadStorage() {
        try {
            H5Log.d(TAG, "initLoadStorage...");
            if (this.mMainProcProxyListener == null || TinyAppService.get().getMixActionService() == null) {
                resetCache();
                Map allMap = this.mSharedPreferences.getAll();
                if (allMap != null && !allMap.isEmpty()) {
                    Bundle tempData = new Bundle();
                    for (Entry entry : allMap.entrySet()) {
                        String key = (String) entry.getKey();
                        Object value = entry.getValue();
                        updateCacheData(key, value);
                        if (value instanceof String) {
                            tempData.putString(key, (String) value);
                        } else if (value instanceof Integer) {
                            tempData.putInt(key, ((Integer) value).intValue());
                        }
                    }
                    synchronized (this) {
                        if (!tempData.isEmpty()) {
                            this.mCacheDataBundle = tempData;
                        }
                    }
                }
            } else {
                TinyAppService.get().getMixActionService().initLoadStorage();
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "initLoadStorage...e=" + e);
        }
        return;
    }

    public synchronized Bundle getAllCacheData() {
        Bundle bundle;
        if (this.mCacheDataBundle == null) {
            bundle = null;
        } else {
            bundle = new Bundle(this.mCacheDataBundle);
        }
        return bundle;
    }

    public void resetCache() {
        this.mStorageCache.clear();
        this.mIntStorageCache.clear();
    }

    public <T> void updateCacheData(String key, T data) {
        if (!TextUtils.isEmpty(key)) {
            H5Log.d(TAG, "updateCacheData...key=" + key + ", data=" + data);
            if (data instanceof String) {
                this.mStorageCache.put(key, (String) data);
            } else if (data instanceof Integer) {
                this.mIntStorageCache.put(key, (Integer) data);
            }
        }
    }

    public void removeCacheData(String key) {
        if (!TextUtils.isEmpty(key)) {
            this.mIntStorageCache.remove(key);
            this.mStorageCache.remove(key);
        }
    }

    public void putString(String appId, String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
            if (!isInLiteProcess() || mixActionService == null) {
                try {
                    this.mStorageCache.put(key, value);
                    this.mSharedPreferences.edit().putString(key, value).apply();
                } catch (Throwable e) {
                    H5Log.e(TAG, "putString...e=" + e);
                }
            } else {
                this.mStorageCache.put(key, value);
                mixActionService.putString(appId, key, value);
            }
        }
    }

    private boolean isInLiteProcess() {
        return this.mMainProcProxyListener != null;
    }

    public void putInt(String key, int value, boolean need2SyncAllLite) {
        if (!TextUtils.isEmpty(key)) {
            if (this.mMainProcProxyListener != null) {
                this.mIntStorageCache.put(key, Integer.valueOf(value));
                this.mMainProcProxyListener.putInt(key, value, need2SyncAllLite);
                return;
            }
            try {
                this.mIntStorageCache.put(key, Integer.valueOf(value));
                this.mSharedPreferences.edit().putInt(key, value).apply();
            } catch (Throwable e) {
                H5Log.e(TAG, "putInt...e=" + e);
            }
        }
    }

    public int getInt(String key) {
        if (TextUtils.isEmpty(key)) {
            return 0;
        }
        Integer value = this.mIntStorageCache.get(key);
        if (value != null) {
            return value.intValue();
        }
        return 0;
    }

    public void remove(String key) {
        if (!TextUtils.isEmpty(key)) {
            if (this.mMainProcProxyListener != null) {
                this.mStorageCache.remove(key);
                this.mMainProcProxyListener.remove(key);
            }
            this.mStorageCache.remove(key);
            this.mSharedPreferences.edit().remove(key).apply();
        }
    }

    public String getString(String key) {
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        String value = this.mStorageCache.get(key);
        return value == null ? "" : value;
    }

    public boolean getVConsoleVisible(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        return "1".equals(this.mStorageCache.get(buildVConsoleStorageKey(appId)));
    }

    public void setVConsoleVisible(String appId, boolean visible) {
        if (!TextUtils.isEmpty(appId)) {
            String key = buildVConsoleStorageKey(appId);
            if (this.mMainProcProxyListener != null) {
                this.mStorageCache.put(key, visible ? "1" : "0");
                this.mMainProcProxyListener.setVConsoleVisible(appId, visible);
                return;
            }
            String value = visible ? "1" : "0";
            this.mStorageCache.put(key, value);
            putString(appId, key, value);
        }
    }

    private String buildVConsoleStorageKey(String appId) {
        return appId + "_vconsole_key";
    }

    public boolean getPerformancePanelVisible(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return true;
        }
        return "1".equals(this.mStorageCache.get(buildPerformanceStorageKey(appId)));
    }

    public void setPerformancePanelVisible(String appId, boolean visible) {
        if (!TextUtils.isEmpty(appId)) {
            String key = buildPerformanceStorageKey(appId);
            if (this.mMainProcProxyListener != null) {
                this.mStorageCache.put(key, visible ? "1" : "0");
                this.mMainProcProxyListener.setPerformancePanelVisible(appId, visible);
                return;
            }
            String value = visible ? "1" : "0";
            this.mStorageCache.put(key, value);
            putString(appId, key, value);
        }
    }

    private String buildPerformanceStorageKey(String appId) {
        return appId + "_performance_key";
    }

    public int getCurrentStorageSize() {
        if (this.mMainProcProxyListener != null) {
            return this.mMainProcProxyListener.getDefaultCurrentStorageSize(TinyAppStorage.getInstance().getCurrentAppId());
        }
        return getDefaultCurrentStorageSize(null, null);
    }

    public int getDefaultCurrentStorageSize(String appId, String userId) {
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(userId)) {
            return TinyAppStoragePlugin.getCurrentStorageSize(appId, userId);
        }
        H5Log.d(TAG, "getDefaultCurrentStorageSize...no reality");
        return -1;
    }

    public boolean getAssistantPanelSwitch() {
        if (this.mMainProcProxyListener != null) {
            return this.mMainProcProxyListener.getAssistantPanelSwitch();
        }
        return true;
    }
}
