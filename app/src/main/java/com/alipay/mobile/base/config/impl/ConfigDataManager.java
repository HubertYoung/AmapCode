package com.alipay.mobile.base.config.impl;

import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.base.config.model.PLData;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;

public class ConfigDataManager {
    private static final String KEY_ALL_LOGIN_USER = "key_all_user";
    public static final String KEY_LAST_NOT_INCREMENT_SUCCESS = "last_not_increment";
    public static final String KEY_LAST_OS_VERSION = "last_not_increment";
    private static final String KEY_PERSIST_KEYS = "persist_keys";
    private static final String KEY_REPLACE = "key_replace";
    public static final String RESERVE_CONFIG_KEY_UPGRADE_LOAD = "upgradeLoadVersion";
    private static final String SP_CONFIG_ASSIST = "CommonConfigAssist";
    public static final String SP_CONFIG_NAME = "CommonConfig_";
    private static final String SP_ENCRYPT_CONFIG_NAME = "CommonConfigEncrypt";
    private static final String TAG = "ConfigDataManager";
    private static ConfigDataManager mConfigDataManager;
    private static ContextWrapper mContextWrapper;
    private SharedPreferences mAssistSP;
    private SPAdapter mCommonConfigAdapter;
    /* access modifiers changed from: private */
    public SharedPreferences mEncryptSP;
    private SharedPreferences mSharedPref;
    private Map<String, SharedPreferences> mUserSps = new HashMap();

    private ConfigDataManager() {
        init();
    }

    public static ConfigDataManager getInstance(ContextWrapper context) {
        if (context != null) {
            mContextWrapper = context;
        }
        if (mConfigDataManager == null) {
            synchronized (ConfigDataManager.class) {
                if (mConfigDataManager == null) {
                    mConfigDataManager = new ConfigDataManager();
                }
            }
        }
        return mConfigDataManager;
    }

    public static void unload() {
        mConfigDataManager = null;
        mContextWrapper = null;
    }

    public SharedPreferences getSpByUser(String userId) {
        if (this.mUserSps.containsKey(userId)) {
            return this.mUserSps.get(userId);
        }
        SharedPreferences sharedPreferences = getSpBySpName(new StringBuilder(SP_CONFIG_NAME).append(userId).toString());
        this.mUserSps.put(userId, sharedPreferences);
        return sharedPreferences;
    }

    public String getString(String key, String def, String userId) {
        String string;
        if (!TextUtils.isEmpty(userId)) {
            return getValue(getSpByUser(userId), key, def);
        }
        synchronized (ConfigService.class) {
            string = this.mCommonConfigAdapter.getString(key, def);
        }
        return string;
    }

    public SharedPreferences getSpBySpName(String spName) {
        return mContextWrapper.getSharedPreferences(spName, 0);
    }

    private void saveValue(SharedPreferences sharedPreferences, String key, String value) {
        if (sharedPreferences != null && !TextUtils.isEmpty(key)) {
            sharedPreferences.edit().putString(key, encrypt(value)).apply();
        }
    }

    private String getValue(SharedPreferences sharedPreferences, String key, String def) {
        if (sharedPreferences == null) {
            return def;
        }
        try {
            if (TextUtils.isEmpty(key)) {
                return def;
            }
            String value = sharedPreferences.getString(key, def);
            if (TextUtils.isEmpty(value) || value == def) {
                return def;
            }
            return decrypt(value);
        } catch (Exception e) {
            logException(e);
            return def;
        }
    }

    /* access modifiers changed from: private */
    public String decrypt(String value) {
        try {
            String decrypt = TaobaoSecurityEncryptor.decrypt(mContextWrapper, value);
            if (!TextUtils.isEmpty(decrypt) || TextUtils.isEmpty(value)) {
                return decrypt;
            }
            LoggerFactory.getTraceLogger().warn((String) TAG, "decrypt value error!! value = " + value);
            return decrypt;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public String encrypt(String value) {
        try {
            String encrypt = TaobaoSecurityEncryptor.encrypt(mContextWrapper, value);
            if (!TextUtils.isEmpty(encrypt) || TextUtils.isEmpty(value)) {
                return encrypt;
            }
            LoggerFactory.getTraceLogger().warn((String) TAG, "encrypt value error!! value = " + value);
            return encrypt;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e);
            return null;
        }
    }

    public void clearData(List<String> withoutCommonKeys) {
        synchronized (ConfigService.class) {
            try {
                addPersistKey(withoutCommonKeys);
                migrateCommonConfigPersistKeys();
                for (Entry<String, SharedPreferences> value : this.mUserSps.entrySet()) {
                    clearSp((SharedPreferences) value.getValue());
                }
                this.mUserSps.clear();
                clearCommonConfig();
                reMigrateCommonConfigPersistKeys();
                clearAssistSp();
                addPersistKey(withoutCommonKeys);
            } catch (Exception e) {
                logException(e);
            }
        }
    }

    private void clearSp(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().clear().apply();
        }
    }

    public void saveMapConfig(final Map<String, String> map, final String userId) {
        if (map != null) {
            try {
                if (!map.isEmpty()) {
                    new Thread(new Runnable() {
                        public void run() {
                            Editor edit = ConfigDataManager.this.getSpByUser(userId).edit();
                            for (String key : map.keySet()) {
                                String value = (String) map.get(key);
                                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                                    edit.putString(key, ConfigDataManager.this.encrypt(value));
                                }
                            }
                            edit.apply();
                        }
                    }).start();
                }
            } catch (Exception e) {
                logException(e);
            }
        }
    }

    private void logException(Exception e) {
        LoggerFactory.getTraceLogger().error((String) TAG, e.getMessage());
    }

    public boolean containsInUserSp(String key, String userId) {
        try {
            return getSpByUser(userId).contains(key);
        } catch (Exception e) {
            logException(e);
            return false;
        }
    }

    public boolean containsInCommonConfig(String key) {
        try {
            return this.mCommonConfigAdapter.contains(key);
        } catch (Exception e) {
            logException(e);
            return false;
        }
    }

    private void init() {
        this.mEncryptSP = mContextWrapper.getSharedPreferences(SP_ENCRYPT_CONFIG_NAME, 0);
        this.mAssistSP = mContextWrapper.getSharedPreferences(SP_CONFIG_ASSIST, 0);
        addPersistKey((String) RESERVE_CONFIG_KEY_UPGRADE_LOAD);
        addPersistKey((String) KEY_ALL_LOGIN_USER);
        if (!this.mEncryptSP.getBoolean(KEY_REPLACE, false)) {
            this.mSharedPref = mContextWrapper.getSharedPreferences(SP_CONFIG_NAME, 0);
        }
        this.mCommonConfigAdapter = new SPAdapter(this.mSharedPref, this.mEncryptSP) {
            public String getStringInOldSp(String key, String value, String def) {
                return value;
            }

            public String getStringInNewSp(String key, String value, String def) {
                String decrypt = ConfigDataManager.this.decrypt(value);
                return !TextUtils.isEmpty(decrypt) ? decrypt : def;
            }

            public String beforePutInNewSp(String key, String value) {
                return ConfigDataManager.this.encrypt(value);
            }

            public void onClear(SharedPreferences oldSp, SharedPreferences newSp) {
                if (ConfigDataManager.this.mEncryptSP != null) {
                    Editor edit = ConfigDataManager.this.mEncryptSP.edit();
                    edit.putBoolean(ConfigDataManager.KEY_REPLACE, true);
                    edit.apply();
                }
            }
        };
    }

    public String getString(String key, String def) {
        String string;
        synchronized (ConfigService.class) {
            string = this.mCommonConfigAdapter.getString(key, def);
        }
        return string;
    }

    public void putString(String key, String value) {
        this.mCommonConfigAdapter.putString(key, value);
    }

    public void putString(String key, String value, String userId) {
        if (!TextUtils.isEmpty(userId)) {
            saveValue(getSpByUser(userId), key, value);
        } else {
            this.mCommonConfigAdapter.putString(key, value);
        }
    }

    public SharedPreferences getAssistSp() {
        return this.mAssistSP;
    }

    public void putMap(Map<String, String> configs) {
        putMap(configs, null);
    }

    public void putMap(Map<String, String> configs, String userId) {
        if (TextUtils.isEmpty(userId)) {
            LoggerFactory.getTraceLogger().info(TAG, "putMap userId is null");
            this.mCommonConfigAdapter.putMap(configs);
            return;
        }
        LoggerFactory.getTraceLogger().info(TAG, "putMap userId is" + userId);
        Editor edit = getSpByUser(userId).edit();
        StringBuffer sb = new StringBuffer();
        sb.append("putMap_");
        for (String key : configs.keySet()) {
            String value = configs.get(key);
            sb.append(key + "=" + value + "#r#");
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                edit.putString(key, encrypt(value));
            }
        }
        edit.apply();
        LoggerFactory.getTraceLogger().info(TAG, sb.toString());
    }

    public void addPersistKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            try {
                List keyList = getPersistKeys();
                if (keyList == null) {
                    keyList = new ArrayList();
                }
                if (!keyList.contains(key)) {
                    keyList.add(key);
                    this.mAssistSP.edit().putString(KEY_PERSIST_KEYS, encrypt(JSON.toJSONString(filterKeys(keyList)))).apply();
                }
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().info(TAG, "addPersistKey Exception : " + e.getMessage());
            }
        }
    }

    public synchronized void addPersistKey(List<String> keys) {
        if (keys != null) {
            try {
                List keyList = getPersistKeys();
                if (keyList == null) {
                    keyList = new ArrayList();
                }
                keyList.addAll(keys);
                this.mAssistSP.edit().putString(KEY_PERSIST_KEYS, encrypt(JSON.toJSONString(filterKeys(keyList)))).apply();
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().info(TAG, "addPersistKey Exception : " + e.getMessage());
            }
        }
        return;
    }

    private List<String> getPersistKeys() {
        try {
            String keys = getValue(this.mAssistSP, KEY_PERSIST_KEYS, null);
            if (!TextUtils.isEmpty(keys)) {
                List ret = new ArrayList();
                JSONArray jsonArray = new JSONArray(keys);
                for (int i = 0; i < jsonArray.length(); i++) {
                    ret.add(jsonArray.getString(i));
                }
                return ret;
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().info(TAG, "getPersistKeys Exception : " + e.getMessage());
        }
        return null;
    }

    private List<String> filterKeys(List<String> keyList) {
        Set sets = new HashSet();
        sets.addAll(keyList);
        keyList.clear();
        keyList.addAll(sets);
        return keyList;
    }

    public void removeKey(String key, String userId) {
        try {
            if (TextUtils.isEmpty(userId)) {
                this.mCommonConfigAdapter.removeKey(key);
            } else {
                getSpByUser(userId).edit().remove(key).apply();
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e.getMessage());
        }
    }

    public void clearAssistSp() {
        this.mAssistSP.edit().clear().apply();
    }

    public void clearCommonConfig() {
        this.mCommonConfigAdapter.clear();
    }

    public void removeKeys(List<String> deleteKeys) {
        this.mCommonConfigAdapter.removeKeys(deleteKeys);
    }

    public void migrateCommonConfigPersistKeys() {
        List persistKeys = getPersistKeys();
        if (persistKeys != null) {
            Editor edit = this.mAssistSP.edit();
            for (String key : persistKeys) {
                String value = this.mCommonConfigAdapter.getString(key, null);
                if (!TextUtils.isEmpty(value)) {
                    edit.putString(key, encrypt(value));
                }
            }
            edit.apply();
        }
    }

    public void reMigrateCommonConfigPersistKeys() {
        List persistKeys = getPersistKeys();
        if (persistKeys != null) {
            Map map = new HashMap();
            Editor edit = this.mAssistSP.edit();
            for (String key : persistKeys) {
                String value = getValue(this.mAssistSP, key, null);
                if (!TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            }
            edit.apply();
            this.mCommonConfigAdapter.putMap(map);
        }
    }

    public boolean saveKeyValueWithTime(String key, String value, long time, boolean onlyUser, String userId) {
        String str;
        boolean z;
        String str2 = null;
        synchronized (ConfigService.class) {
            SharedPreferences assistSp = getAssistSp();
            if (onlyUser) {
                str = userId;
            } else {
                str = null;
            }
            if (time > assistSp.getLong(getKeyWithTime(key, str), -1)) {
                Editor edit = getAssistSp().edit();
                if (onlyUser) {
                    str2 = userId;
                }
                edit.putLong(getKeyWithTime(key, str2), time).apply();
                if (!onlyUser) {
                    putString(key, value);
                    updateAllUserConfig(key, value);
                } else {
                    putString(key, value, userId);
                }
                LoggerFactory.getTraceLogger().info(TAG, "uiniqId = " + key + ",updateTime = " + time + ",onlyUser = " + onlyUser + " userId = " + userId + " ,save");
                z = true;
            } else {
                LoggerFactory.getTraceLogger().info(TAG, "uiniqId = " + key + ",updateTime = " + time + " ,time <= aLong do not save");
                z = false;
            }
        }
        return z;
    }

    public boolean saveKeyValueWithTimeToCommon(String key, String value, long time) {
        boolean z;
        synchronized (ConfigService.class) {
            if (time > getAssistSp().getLong(getKeyWithTime(key, null), -1)) {
                getAssistSp().edit().putLong(getKeyWithTime(key, null), time).apply();
                putString(key, value, null);
                LoggerFactory.getTraceLogger().info(TAG, "uiniqId = " + key + ",updateTime = " + time + " ,save");
                z = true;
            } else {
                LoggerFactory.getTraceLogger().info(TAG, "uiniqId = " + key + ",updateTime = " + time + " ,time <= aLong do not save");
                z = false;
            }
        }
        return z;
    }

    public boolean saveKeyValueToCommon(String key, String value, long time) {
        synchronized (ConfigService.class) {
            getAssistSp().edit().putLong(getKeyWithTime(key, null), time).apply();
            putString(key, value, null);
            LoggerFactory.getTraceLogger().info(TAG, "uiniqId = " + key + ",updateTime = " + time + " ,save");
        }
        return true;
    }

    private void updateAllUserConfig(String key, String value) {
        List<String> allLoginUsers = getAllLoginUsers();
        if (allLoginUsers != null && !allLoginUsers.isEmpty()) {
            for (String uid : allLoginUsers) {
                SharedPreferences spByUser = getSpByUser(uid);
                if (spByUser.contains(key)) {
                    saveValue(spByUser, key, value);
                }
            }
        }
    }

    private String getKeyWithTime(String key, String userId) {
        if (TextUtils.isEmpty(userId)) {
            return key + "_time";
        }
        return key + "_time_" + userId;
    }

    public boolean putSpliteData(PLData plData, String userId) {
        if (TextUtils.isEmpty(userId)) {
            putString(plData.uniqId + plData.updateTime + (plData.num - 1), JSON.toJSON(plData).toString());
            for (int i = 0; i < plData.total; i++) {
                if (!containsInCommonConfig(plData.uniqId + plData.updateTime + i)) {
                    return false;
                }
            }
        } else {
            putString(plData.uniqId + plData.updateTime + (plData.num - 1), JSON.toJSON(plData).toString(), userId);
            for (int i2 = 0; i2 < plData.total; i2++) {
                if (!containsInUserSp(plData.uniqId + plData.updateTime + i2, userId)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<String> getAllLoginUsers() {
        String string = getValue(getAssistSp(), KEY_ALL_LOGIN_USER, null);
        if (TextUtils.isEmpty(string)) {
            return new ArrayList();
        }
        try {
            return JSON.parseArray(string, String.class);
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public void addLoginUser(String userId) {
        try {
            List users = getAllLoginUsers();
            HashSet hashSet = new HashSet();
            hashSet.addAll(users);
            if (!hashSet.contains(userId)) {
                hashSet.add(userId);
                List users2 = new ArrayList();
                users2.addAll(hashSet);
                saveValue(getAssistSp(), KEY_ALL_LOGIN_USER, JSON.toJSON(users2).toString());
            }
        } catch (Exception e) {
            logException(e);
        }
    }
}
