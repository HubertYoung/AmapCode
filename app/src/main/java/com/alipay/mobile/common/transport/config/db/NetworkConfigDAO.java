package com.alipay.mobile.common.transport.config.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.alipay.mobile.common.nbnet.biz.db.UploadRecordDo;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import java.lang.ref.SoftReference;

public class NetworkConfigDAO {
    static SoftReference<NetworkConfigDAO> networkConfigDAORef;

    public static final NetworkConfigDAO getInstance() {
        if (networkConfigDAORef != null && networkConfigDAORef.get() != null) {
            return networkConfigDAORef.get();
        }
        synchronized (NetworkConfigDAO.class) {
            if (networkConfigDAORef != null && networkConfigDAORef.get() != null) {
                return networkConfigDAORef.get();
            }
            NetworkConfigDAO networkConfigDAO = new NetworkConfigDAO();
            networkConfigDAORef = new SoftReference<>(networkConfigDAO);
            return networkConfigDAO;
        }
    }

    public synchronized String getConfig(String key) {
        String value;
        Cursor cursor = null;
        try {
            cursor = NetworkConfigDBHelper.getInstance(TransportEnvUtil.getContext()).getReadableDatabase().rawQuery(SqlConstants.QUERY_BY_KEY_SQL, new String[]{key});
            if (cursor.getCount() <= 0) {
                LogCatUtil.info("NetworkConfigDAO", "getConfig. " + key + " value is null.");
                value = "";
                if (cursor != null) {
                    if (!cursor.isClosed()) {
                        close(cursor);
                    }
                }
            } else if (cursor.moveToNext()) {
                value = cursor.getString(cursor.getColumnIndex("value"));
                LogCatUtil.info("NetworkConfigDAO", "getConfig. " + key + ":" + value);
                if (cursor != null) {
                    if (!cursor.isClosed()) {
                        close(cursor);
                    }
                }
            } else {
                value = "";
                if (cursor != null) {
                    if (!cursor.isClosed()) {
                        close(cursor);
                    }
                }
            }
        } catch (Throwable th) {
            if (cursor != null && !cursor.isClosed()) {
                close(cursor);
            }
            throw th;
        }
        return value;
    }

    public synchronized boolean saveOrUpdateConfig(String key, String value) {
        boolean insertConfigByKey;
        if (isExistsConfig(key)) {
            insertConfigByKey = updateConfigByKey(key, value);
        } else {
            insertConfigByKey = insertConfigByKey(key, value);
        }
        return insertConfigByKey;
    }

    public synchronized boolean insertConfigByKey(String key, String value) {
        boolean z;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = NetworkConfigDBHelper.getInstance(TransportEnvUtil.getContext()).getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("switch_key", key);
            contentValues.put("value", value);
            long gmt = System.currentTimeMillis();
            contentValues.put("gmt_modified", Long.valueOf(gmt));
            contentValues.put(UploadRecordDo.GMT_CREATED_FIELD, Long.valueOf(gmt));
            writableDatabase.insert(NetworkConfigDBHelper.TB_NAME, null, contentValues);
            LogCatUtil.info("NetworkConfigDAO", "insertConfigByKey finish. key:" + key + ", value:" + value);
            if (writableDatabase != null) {
                close(writableDatabase);
            }
            z = true;
        } catch (Throwable th) {
            if (writableDatabase != null) {
                close(writableDatabase);
            }
            throw th;
        }
        return z;
    }

    public synchronized boolean updateConfigByKey(String key, String value) {
        boolean z = true;
        synchronized (this) {
            SQLiteDatabase writableDatabase = null;
            try {
                writableDatabase = NetworkConfigDBHelper.getInstance(TransportEnvUtil.getContext()).getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("value", value);
                contentValues.put("gmt_modified", Long.valueOf(System.currentTimeMillis()));
                int rows = writableDatabase.update(NetworkConfigDBHelper.TB_NAME, contentValues, "switch_key = ?", new String[]{key});
                LogCatUtil.info("NetworkConfigDAO", "updateConfigByKey. update finish. rows: " + rows + ", key:" + key + ", value:" + value);
                if (rows <= 0) {
                    z = false;
                }
                if (writableDatabase != null) {
                    close(writableDatabase);
                }
            } catch (Throwable th) {
                if (writableDatabase != null) {
                    close(writableDatabase);
                }
                throw th;
            }
        }
        return z;
    }

    public boolean isExistsConfig(String key) {
        return countConfigByKey(key) > 0;
    }

    public synchronized int countConfigByKey(String key) {
        int count;
        Cursor countCursor = null;
        try {
            countCursor = NetworkConfigDBHelper.getInstance(TransportEnvUtil.getContext()).getReadableDatabase().rawQuery(SqlConstants.COUNT_BY_KEY_SQL, new String[]{key});
            count = countCursor.getCount();
            LogCatUtil.info("NetworkConfigDAO", "countConfigByKey. key:" + key + " count:" + count);
            if (countCursor != null) {
                if (!countCursor.isClosed()) {
                    close(countCursor);
                }
            }
        } catch (Throwable th) {
            if (countCursor != null && !countCursor.isClosed()) {
                close(countCursor);
            }
            throw th;
        }
        return count;
    }

    public synchronized int deleteConfig(String key) {
        int delete;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = NetworkConfigDBHelper.getInstance(TransportEnvUtil.getContext()).getWritableDatabase();
            delete = writableDatabase.delete(NetworkConfigDBHelper.TB_NAME, " switch_key = ? ", new String[]{key});
            LogCatUtil.info("NetworkConfigDAO", "deleteConfig. update finish. rows: " + delete + ", key:" + key);
            if (writableDatabase != null) {
                close(writableDatabase);
            }
        } catch (Throwable th) {
            if (writableDatabase != null) {
                close(writableDatabase);
            }
            throw th;
        }
        return delete;
    }

    public synchronized void close(SQLiteDatabase sqlLiteDatabase) {
        if (sqlLiteDatabase != null) {
            try {
                sqlLiteDatabase.close();
            } catch (Throwable e) {
                MonitorErrorLogHelper.log("NetworkConfigDAO", e);
            }
        }
        return;
    }

    public synchronized void close(Cursor closeable) {
        if (closeable != null) {
            if (!closeable.isClosed()) {
                try {
                    closeable.close();
                } catch (Throwable e) {
                    MonitorErrorLogHelper.log("NetworkConfigDAO", e);
                }
            }
        }
        return;
    }
}
