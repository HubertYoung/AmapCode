package com.alipay.android.phone.mobilesdk.storage.database.tinyapp;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.utils.MD5Util;
import java.util.concurrent.ConcurrentHashMap;

public class SqliteOpenHelperManager {
    private static final String TAG = "TinyAppStoragePlugin";
    private static volatile SqliteOpenHelperManager mInstance;
    private TinyAppContext mContext;
    private ConcurrentHashMap<String, EncryptOrmliteSqliteOpenHelper> mHelperCache;

    private SqliteOpenHelperManager(Context context) {
        this.mContext = new TinyAppContext(context);
    }

    public static SqliteOpenHelperManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SqliteOpenHelperManager.class) {
                if (mInstance == null) {
                    mInstance = new SqliteOpenHelperManager(context);
                }
            }
        }
        return mInstance;
    }

    public EncryptOrmliteSqliteOpenHelper getSqliteOpenHelper(String appId, String userId) {
        if (this.mContext == null) {
            LoggerFactory.getTraceLogger().error((String) "TinyAppStoragePlugin", (String) "context is null");
            return null;
        } else if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(userId)) {
            LoggerFactory.getTraceLogger().error((String) "TinyAppStoragePlugin", "required parameters is null, appId = " + appId + " userId = " + userId);
            return null;
        } else {
            String databaseName = MD5Util.getMD5String(appId + userId);
            if (this.mHelperCache != null) {
                EncryptOrmliteSqliteOpenHelper cachedHelper = this.mHelperCache.get(databaseName);
                if (cachedHelper == null) {
                    EncryptOrmliteSqliteOpenHelper cachedHelper2 = EncryptOrmliteSqliteOpenHelper.getInstance(this.mContext, databaseName);
                    this.mHelperCache.put(databaseName, cachedHelper2);
                    LoggerFactory.getTraceLogger().debug("TinyAppStoragePlugin", "database = " + databaseName + " no cache! create new sqliteOpenHelper = " + cachedHelper2);
                    return cachedHelper2;
                }
                LoggerFactory.getTraceLogger().debug("TinyAppStoragePlugin", "database = " + databaseName + " cache hit! sqliteOpenHelper = " + cachedHelper);
                return cachedHelper;
            }
            this.mHelperCache = new ConcurrentHashMap<>();
            EncryptOrmliteSqliteOpenHelper helper = EncryptOrmliteSqliteOpenHelper.getInstance(this.mContext, databaseName);
            this.mHelperCache.put(databaseName, helper);
            LoggerFactory.getTraceLogger().debug("TinyAppStoragePlugin", "database = " + databaseName + " create new sqliteOpenHelper = " + helper);
            return helper;
        }
    }
}
