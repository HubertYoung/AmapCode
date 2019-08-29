package com.alipay.mobile.common.logging.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class LoggingSPCache {
    public static final String CACHE_FILE_NAME = "LoggingCache";
    public static final String KEY_CUR_UPLOAD_DAY = "curUploadDay";
    public static final String KEY_CUR_UPLOAD_TRAFIC = "curUploadTrafic";
    public static final String LOGGING_CACHE_KEY_INDEX = "behavorLoggingIndex";
    public static final String LOGGING_CACHE_KEY_LOG_DUMP_TAG = "LogDumpTag";
    public static final String STORAGE_BIRDNESTVERSION = "birdNestVersion";
    public static final String STORAGE_BUNDLEVERSION = "bundleVersion";
    public static final String STORAGE_CHANNELID = "channelId";
    public static final String STORAGE_CLIENTID = "clientID";
    public static final String STORAGE_CLIENTIMEI = "clientIMEI";
    public static final String STORAGE_DEVICEID = "utdid";
    public static final String STORAGE_HOTPATCHVERSION = "hotpatchVersion";
    public static final String STORAGE_LANGUAGE = "language";
    public static final String STORAGE_LOGHOST = "logHost";
    public static final String STORAGE_PACKAGEID = "packageId";
    public static final String STORAGE_PRODUCTID = "productID";
    public static final String STORAGE_PRODUCTVERSION = "productVersion";
    public static final String STORAGE_RELEASECODE = "releaseCode";
    public static final String STORAGE_RELEASETYPE = "releaseType";
    public static final String STORAGE_USERID = "userID";
    public static final String STORAGE_USERSESSIONID = "userSessionId";
    private static LoggingSPCache a;
    private Context b;
    private SharedPreferences c;

    public static synchronized LoggingSPCache createInstance(Context context) {
        LoggingSPCache loggingSPCache;
        synchronized (LoggingSPCache.class) {
            if (a == null) {
                a = new LoggingSPCache(context);
            }
            loggingSPCache = a;
        }
        return loggingSPCache;
    }

    public static LoggingSPCache getInstance() {
        if (a == null) {
            throw new IllegalStateException("need createInstance befor use");
        }
        a.a();
        return a;
    }

    private LoggingSPCache(Context context) {
        this.b = context;
    }

    private void a() {
        this.c = this.b.getSharedPreferences(LoggerFactory.getProcessInfo().getProcessTag() + DjangoUtils.EXTENSION_SEPARATOR + CACHE_FILE_NAME, 0);
    }

    public SharedPreferences getEntity() {
        return this.c;
    }

    public Editor getEditor() {
        return this.c.edit();
    }

    public void removeCommit(String key) {
        this.c.edit().remove(key).commit();
    }

    public void removeApply(String key) {
        this.c.edit().remove(key).apply();
    }

    public void putStringCommit(String key, String value) {
        String origin = this.c.getString(key, null);
        if (!this.c.contains(key) || !TextUtils.equals(origin, value)) {
            this.c.edit().putString(key, value).commit();
        }
    }

    public void putStringApply(String key, String value) {
        String origin = this.c.getString(key, null);
        if (!this.c.contains(key) || !TextUtils.equals(origin, value)) {
            this.c.edit().putString(key, value).apply();
        }
    }

    public String getString(String key, String defaultValue) {
        return this.c.getString(key, defaultValue);
    }

    public void putIntCommit(String key, int value) {
        this.c.edit().putInt(key, value).commit();
    }

    public void putIntApply(String key, int value) {
        this.c.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defaultValue) {
        return this.c.getInt(key, defaultValue);
    }

    public void putLongCommit(String key, long value) {
        this.c.edit().putLong(key, value).commit();
    }

    public void putLongApply(String key, long value) {
        this.c.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defaultValue) {
        return this.c.getLong(key, defaultValue);
    }

    public void putBooleanCommit(String key, boolean value) {
        this.c.edit().putBoolean(key, value).commit();
    }

    public void putBooleanApply(String key, boolean value) {
        this.c.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.c.getBoolean(key, defaultValue);
    }
}
