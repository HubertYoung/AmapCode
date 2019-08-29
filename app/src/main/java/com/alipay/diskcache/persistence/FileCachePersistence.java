package com.alipay.diskcache.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.diskcache.model.StatisticInfo;
import com.alipay.diskcache.utils.LogHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class FileCachePersistence {
    private static final String TAG = "GeneralCache";
    private static FileCachePersistence mInstance = null;
    protected DatabaseHelper mDbHelper;
    private Semaphore mMutex = new Semaphore(1);

    public static FileCachePersistence getInstance(Context context, String dir) {
        if (mInstance == null) {
            synchronized (FileCachePersistence.class) {
                if (mInstance == null) {
                    try {
                        mInstance = new FileCachePersistence(context, dir);
                    } catch (SQLException ex) {
                        LogHelper.e("GeneralCache", "getInstance error", ex);
                    }
                }
            }
        }
        return mInstance;
    }

    private FileCachePersistence(Context context, String dir) {
        this.mDbHelper = new DatabaseHelper(context, dir);
    }

    public void deleteByCacheKey(String cacheKey) {
        deleteForEq("key", cacheKey);
    }

    public void deleteByAliasKey(String aliaskey) {
        deleteForEq(FileCacheModel.F_ALIAS_KEY, aliaskey);
    }

    public void deleteByPath(String path) {
        deleteForEq("path", path);
    }

    public boolean delete(List<FileCacheModel> list) {
        if (list == null || list.size() <= 0) {
            return false;
        }
        SQLiteDatabase db = this.mDbHelper.openDatabase();
        try {
            beginTransaction(db);
            for (FileCacheModel model : list) {
                db.execSQL("delete from " + getTableName() + " where id=" + model.id);
            }
            db.setTransactionSuccessful();
            endTransaction(db);
            this.mDbHelper.closeDatabase();
            return true;
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "delete exception:", e);
            endTransaction(db);
            this.mDbHelper.closeDatabase();
            return false;
        } catch (Throwable th) {
            endTransaction(db);
            this.mDbHelper.closeDatabase();
            throw th;
        }
    }

    public FileCacheModel queryByCacheKey(String cacheKey) {
        List list = queryForEq("key", cacheKey);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public List<FileCacheModel> queryByCacheKey2(String cacheKey) {
        return queryForEq("key", cacheKey);
    }

    public FileCacheModel queryByAliasKey(String aliasKey) {
        List list = queryForEq(FileCacheModel.F_ALIAS_KEY, aliasKey);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public List<FileCacheModel> queryByAliasKey2(String aliasKey) {
        return queryForEq(FileCacheModel.F_ALIAS_KEY, aliasKey);
    }

    public FileCacheModel queryByPath(String path) {
        List list = queryForEq("path", path);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public long getCacheSizeByType(int type) {
        long cacheSize = 0;
        Cursor cursor = null;
        try {
            cursor = this.mDbHelper.openDatabase().rawQuery(String.format("SELECT SUM(%s) FROM %s where type = %d", new Object[]{"file_size", getTableName(), Integer.valueOf(type)}), null);
            cursor.moveToFirst();
            cacheSize = (long) cursor.getInt(0);
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "getCacheSizeByType error", e);
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return cacheSize;
    }

    public long getCacheSizeByBiz(String bizId) {
        long cacheSize = 0;
        Cursor cursor = null;
        try {
            cursor = this.mDbHelper.openDatabase().rawQuery(String.format("SELECT SUM(%s) FROM %s where business_id = ?", new Object[]{"file_size", getTableName()}), new String[]{bizId});
            cursor.moveToFirst();
            cacheSize = (long) cursor.getInt(0);
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "getCacheSizeByBiz error", e);
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return cacheSize;
    }

    public long getCacheTotalSize() {
        long cacheSize = 0;
        Cursor cursor = null;
        try {
            cursor = this.mDbHelper.openDatabase().rawQuery(String.format("SELECT SUM(%s) FROM %s", new Object[]{"file_size", getTableName()}), null);
            cursor.moveToFirst();
            cacheSize = (long) cursor.getInt(0);
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "getCacheTotalSize error", e);
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            this.mDbHelper.closeDatabase();
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return cacheSize;
    }

    public List<FileCacheModel> queryWillExpireCacheModel(long limit, Set<String> whiteSet) {
        Cursor cursor = null;
        List list = new ArrayList();
        try {
            SQLiteDatabase db = this.mDbHelper.openDatabase();
            String whiteListSql = genWhiteListSql(whiteSet);
            Cursor cursor2 = db.rawQuery(String.format("select * from %s where %s&%d = 0%s order by access_time asc limit " + limit, new Object[]{getTableName(), "tag", Integer.valueOf(16), whiteListSql}), null);
            while (cursor2.moveToNext()) {
                list.add(parseFileCacheModel(cursor2));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "queryWillExpireCacheModel exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        return list;
    }

    private String genWhiteListSql(Set<String> set) {
        if (set == null || set.isEmpty()) {
            return "";
        }
        Iterator iterator = set.iterator();
        String sql = " AND business_id NOT IN (";
        while (true) {
            String sql2 = sql + "'" + iterator.next() + "'";
            if (!iterator.hasNext()) {
                return sql2 + ")";
            }
            sql = sql2 + ", ";
        }
    }

    /* JADX INFO: finally extract failed */
    public FileCacheModel save(FileCacheModel model) {
        try {
            save(this.mDbHelper.openDatabase(), model);
            this.mDbHelper.closeDatabase();
            return model;
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "save exception", e);
            this.mDbHelper.closeDatabase();
            return null;
        } catch (Throwable th) {
            this.mDbHelper.closeDatabase();
            throw th;
        }
    }

    public List<FileCacheModel> save(List<FileCacheModel> models) {
        SQLiteDatabase db = this.mDbHelper.openDatabase();
        if (db != null) {
            beginTransaction(db);
            try {
                for (FileCacheModel model : models) {
                    save(db, model);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogHelper.e("GeneralCache", "save exception", e);
            } finally {
                endTransaction(db);
            }
        }
        this.mDbHelper.closeDatabase();
        return models;
    }

    private void save(SQLiteDatabase db, FileCacheModel model) {
        LogHelper.d("GeneralCache", "save(), input model:" + model);
        String cmd = model.id <= 0 ? "insert" : "replace";
        String value_id = "," + model.id;
        String sql = cmd + " into " + getTableName() + "(access_time,path,alias_key,multi_alias_key,business_id,extra,file_size,key,modify_time,expiredTime,tag,type) values (" + model.accessTime + ",'" + model.path + "','" + model.aliasKey + "','" + model.multiAliasKeys + "','" + model.businessId + "','" + model.extra + "'," + model.fileSize + ",'" + model.cacheKey + "'," + model.modifyTime + "," + model.expiredTime + "," + model.tag + "," + model.type + ")";
        if (model.id > 0) {
            StringBuilder stringBuilder = new StringBuilder(sql);
            stringBuilder.insert(stringBuilder.indexOf(")"), ",id");
            stringBuilder.insert(stringBuilder.lastIndexOf(")"), value_id);
            sql = stringBuilder.toString();
        }
        LogHelper.d("GeneralCache", "save sql: " + sql);
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "save exception", e);
        }
    }

    private void update(SQLiteDatabase db, String field, long value, String selection_field, String selection_value) {
        LogHelper.d("GeneralCache", "update(), field:" + field + ", value:" + value);
        try {
            db.execSQL("update " + getTableName() + " set " + field + " = " + value + " where " + selection_field + " = '" + selection_value + "'");
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "update exception", e);
        }
    }

    public List<FileCacheModel> updateAccessTime(List<FileCacheModel> models) {
        SQLiteDatabase db = this.mDbHelper.openDatabase();
        if (db != null) {
            beginTransaction(db);
            try {
                for (FileCacheModel model : models) {
                    update(db, FileCacheModel.F_CACHE_ACCESS_TIME, model.accessTime, "key", model.cacheKey);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogHelper.e("GeneralCache", "updateAccessTime exception", e);
            } finally {
                endTransaction(db);
            }
        }
        this.mDbHelper.closeDatabase();
        return models;
    }

    /* JADX INFO: finally extract failed */
    public FileCacheModel updateAccessTime(FileCacheModel model) {
        try {
            update(this.mDbHelper.openDatabase(), FileCacheModel.F_CACHE_ACCESS_TIME, model.accessTime, "key", model.cacheKey);
            this.mDbHelper.closeDatabase();
            return model;
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "updateAccessTime exception", e);
            this.mDbHelper.closeDatabase();
            return null;
        } catch (Throwable th) {
            this.mDbHelper.closeDatabase();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    private boolean deleteForEq(String fieldName, String value) {
        LogHelper.d("GeneralCache", "deleteForEq(), fieldName: " + fieldName + ", value:" + value);
        try {
            this.mDbHelper.openDatabase().execSQL(String.format("delete from " + getTableName() + " where " + fieldName + " = '%s'", new Object[]{value}));
            this.mDbHelper.closeDatabase();
            return true;
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "deleteForEq exception", e);
            this.mDbHelper.closeDatabase();
            return false;
        } catch (Throwable th) {
            this.mDbHelper.closeDatabase();
            throw th;
        }
    }

    private List<FileCacheModel> queryForEq(String fieldName, String value) {
        Cursor cursor = null;
        List list = new ArrayList();
        try {
            Cursor cursor2 = this.mDbHelper.openDatabase().rawQuery("select * from " + getTableName() + " where " + fieldName + " = ?", new String[]{value});
            while (cursor2.moveToNext()) {
                list.add(parseFileCacheModel(cursor2));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "queryForEq exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        return list;
    }

    public List<FileCacheModel> queryAlias(int type) {
        List list = new ArrayList();
        Cursor cursor = null;
        try {
            Cursor cursor2 = this.mDbHelper.openDatabase().rawQuery(String.format("select * from " + getTableName() + " where %s = %d and %s != 'null' and %s != '' and %s != %s", new Object[]{"type", Integer.valueOf(type), FileCacheModel.F_ALIAS_KEY, FileCacheModel.F_ALIAS_KEY, FileCacheModel.F_ALIAS_KEY, "key"}), null);
            while (cursor2.moveToNext()) {
                list.add(parseFileCacheModel(cursor2));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "queryCacheModelsByTimeInterval exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        return list;
    }

    public List<FileCacheModel> queryCacheModelsByTimeInterval(long interval, int tag, boolean descOrder) {
        String sql;
        List list = new ArrayList();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.mDbHelper.openDatabase();
            String sql2 = String.format("select * from " + getTableName() + " where %s&%d != 0 and %s>%d order by %s", new Object[]{"tag", Integer.valueOf(tag), FileCacheModel.F_CACHE_MODIFY_TIME, Long.valueOf(System.currentTimeMillis() - interval), FileCacheModel.F_CACHE_MODIFY_TIME});
            if (descOrder) {
                sql = sql2 + " desc";
            } else {
                sql = sql2 + " asc";
            }
            Cursor cursor2 = db.rawQuery(sql, null);
            while (cursor2.moveToNext()) {
                list.add(parseFileCacheModel(cursor2));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "queryCacheModelsByTimeInterval exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        return list;
    }

    public List<FileCacheModel> query(String bizId, int tag, boolean skipLock, long interval, boolean useAcc) {
        Cursor cursor = null;
        List list = new ArrayList();
        if (TextUtils.isEmpty(bizId)) {
            bizId = "";
        }
        String time_field = useAcc ? FileCacheModel.F_CACHE_ACCESS_TIME : FileCacheModel.F_CACHE_MODIFY_TIME;
        try {
            SQLiteDatabase db = this.mDbHelper.openDatabase();
            long ts = System.currentTimeMillis() - interval;
            Object[] objArr = new Object[8];
            objArr[0] = getTableName();
            objArr[1] = FileCacheModel.F_CACHE_BUSINESS_ID;
            objArr[2] = "tag";
            objArr[3] = Integer.valueOf(tag);
            objArr[4] = "tag";
            objArr[5] = Integer.valueOf(skipLock ? 16 : 0);
            objArr[6] = time_field;
            if (interval <= 0) {
                ts = Long.MAX_VALUE;
            }
            objArr[7] = Long.valueOf(ts);
            Cursor cursor2 = db.rawQuery(String.format("select * from %s where %s like ? and %s&%d != 0 and %s&%d = 0 and %s <= %d", objArr), new String[]{bizId + "%"});
            while (cursor2.moveToNext()) {
                list.add(parseFileCacheModel(cursor2));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "query exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        return list;
    }

    public List<FileCacheModel> queryExpiredRecords(int limit, Set<String> whiteSet, boolean skipLock, long ts) {
        Cursor cursor = null;
        List list = new ArrayList();
        try {
            SQLiteDatabase db = this.mDbHelper.openDatabase();
            Object[] objArr = new Object[9];
            objArr[0] = getTableName();
            objArr[1] = FileCacheModel.F_CACHE_EXPIRED_TIME;
            objArr[2] = FileCacheModel.F_CACHE_EXPIRED_TIME;
            objArr[3] = "tag";
            objArr[4] = Integer.valueOf(skipLock ? 16 : 0);
            objArr[5] = FileCacheModel.F_CACHE_EXPIRED_TIME;
            objArr[6] = Long.valueOf(ts);
            objArr[7] = genWhiteListSql(whiteSet);
            objArr[8] = Integer.valueOf(limit);
            String sql = String.format("select * from %s where %s is not null and %s > 0 and  %s&%d = 0 and %s <= %d %s limit %d", objArr);
            LogHelper.i("GeneralCache", "queryExpiredRecord sql:" + sql);
            Cursor cursor2 = db.rawQuery(sql, null);
            while (cursor2.moveToNext()) {
                list.add(parseFileCacheModel(cursor2));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "query exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        return list;
    }

    public List<StatisticInfo> queryByGroup(String bizId, int tag, boolean skipLock, long interval, boolean useAcc) {
        Cursor cursor = null;
        List list = new ArrayList();
        if (TextUtils.isEmpty(bizId)) {
            bizId = "";
        }
        String time_field = useAcc ? FileCacheModel.F_CACHE_ACCESS_TIME : FileCacheModel.F_CACHE_MODIFY_TIME;
        try {
            SQLiteDatabase db = this.mDbHelper.openDatabase();
            long ts = System.currentTimeMillis() - interval;
            String[] columns = {FileCacheModel.F_CACHE_BUSINESS_ID, "count(business_id)", "sum(file_size)"};
            Object[] objArr = new Object[12];
            objArr[0] = columns[0];
            objArr[1] = columns[1];
            objArr[2] = columns[2];
            objArr[3] = getTableName();
            objArr[4] = FileCacheModel.F_CACHE_BUSINESS_ID;
            objArr[5] = "tag";
            objArr[6] = Integer.valueOf(tag);
            objArr[7] = "tag";
            objArr[8] = Integer.valueOf(skipLock ? 16 : 0);
            objArr[9] = time_field;
            if (interval <= 0) {
                ts = Long.MAX_VALUE;
            }
            objArr[10] = Long.valueOf(ts);
            objArr[11] = FileCacheModel.F_CACHE_BUSINESS_ID;
            Cursor cursor2 = db.rawQuery(String.format("select %s, %s, %s from %s where %s like ? and %s&%d != 0 and %s&%d = 0 and %s <= %d group by %s", objArr), new String[]{bizId + "%"});
            while (cursor2.moveToNext()) {
                StatisticInfo info = new StatisticInfo();
                info.mBusinessId = cursor2.getString(cursor2.getColumnIndex(columns[0]));
                info.mCount = cursor2.getInt(cursor2.getColumnIndex(columns[1]));
                info.mTotalSize = cursor2.getLong(cursor2.getColumnIndex(columns[2]));
                list.add(info);
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "queryByGroup exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        return list;
    }

    private String getTableName() {
        return FileCacheModel.TABLE_FILE_CACHE;
    }

    public List<String> queryAllBusiness() {
        String sql = String.format("SELECT DISTINCT %s FROM %s GROUP BY %s", new Object[]{FileCacheModel.F_CACHE_BUSINESS_ID, getTableName(), FileCacheModel.F_CACHE_BUSINESS_ID});
        Cursor cursor = null;
        List list = new ArrayList();
        try {
            Cursor cursor2 = this.mDbHelper.openDatabase().rawQuery(sql, null);
            while (cursor2.moveToNext()) {
                list.add(cursor2.getString(0));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "queryByGroup exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        LogHelper.d("GeneralCache", "queryAllBusiness result: " + list);
        return list;
    }

    public List<FileCacheModel> getMultiAlias(int type) {
        List list = new ArrayList();
        Cursor cursor = null;
        try {
            Cursor cursor2 = this.mDbHelper.openDatabase().rawQuery(String.format("select * from " + getTableName() + " where %s = %d and %s != 'null' and %s != '' and %s != %s", new Object[]{"type", Integer.valueOf(type), FileCacheModel.F_MULTI_ALIAS_KEY, FileCacheModel.F_MULTI_ALIAS_KEY, FileCacheModel.F_MULTI_ALIAS_KEY, "key"}), null);
            while (cursor2.moveToNext()) {
                list.add(parseFileCacheModel(cursor2));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "queryCacheModelsByTimeInterval exception", e);
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            this.mDbHelper.closeDatabase();
            throw th;
        }
        return list;
    }

    public boolean appendAliasKey(String key, String aliasKey) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(aliasKey)) {
            return false;
        }
        List models = queryForEq("key", key);
        for (FileCacheModel model : models) {
            String str = (TextUtils.isEmpty(model.multiAliasKeys) || "null".equalsIgnoreCase(model.multiAliasKeys)) ? aliasKey : Arrays.asList(model.splitMultiAliasKeys()).contains(aliasKey) ? model.multiAliasKeys : model.multiAliasKeys + ";" + aliasKey;
            model.multiAliasKeys = str;
            if (TextUtils.isEmpty(model.aliasKey) || "null".equalsIgnoreCase(model.aliasKey) || key.equalsIgnoreCase(model.aliasKey)) {
                model.aliasKey = aliasKey;
            }
        }
        try {
            save(models);
            return true;
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "appendAliasKey key: " + key + ", aliasKey: " + aliasKey, e);
            return false;
        }
    }

    private FileCacheModel parseFileCacheModel(Cursor cursor) {
        FileCacheModel model = new FileCacheModel();
        model.cacheKey = cursor.getString(cursor.getColumnIndex("key"));
        model.extra = cursor.getString(cursor.getColumnIndex("extra"));
        model.aliasKey = cursor.getString(cursor.getColumnIndex(FileCacheModel.F_ALIAS_KEY));
        model.multiAliasKeys = cursor.getString(cursor.getColumnIndex(FileCacheModel.F_MULTI_ALIAS_KEY));
        model.accessTime = cursor.getLong(cursor.getColumnIndex(FileCacheModel.F_CACHE_ACCESS_TIME));
        model.businessId = cursor.getString(cursor.getColumnIndex(FileCacheModel.F_CACHE_BUSINESS_ID));
        model.fileSize = cursor.getLong(cursor.getColumnIndex("file_size"));
        model.modifyTime = cursor.getLong(cursor.getColumnIndex(FileCacheModel.F_CACHE_MODIFY_TIME));
        model.expiredTime = cursor.getLong(cursor.getColumnIndex(FileCacheModel.F_CACHE_EXPIRED_TIME));
        model.path = cursor.getString(cursor.getColumnIndex("path"));
        model.tag = cursor.getInt(cursor.getColumnIndex("tag"));
        model.type = cursor.getInt(cursor.getColumnIndex("type"));
        model.id = cursor.getInt(cursor.getColumnIndex("id"));
        if (model.expiredTime <= 0) {
            model.expiredTime = Long.MAX_VALUE;
        }
        return model;
    }

    public boolean delete(int id) {
        try {
            this.mDbHelper.openDatabase().execSQL("DELETE FROM " + getTableName() + " WHERE id = " + id);
            return true;
        } catch (Exception e) {
            LogHelper.e("GeneralCache", "queryCacheModelsByTimeInterval exception", e);
            return false;
        } finally {
            this.mDbHelper.closeDatabase();
        }
    }

    private void beginTransaction(SQLiteDatabase db) {
        if (db != null) {
            LogHelper.i("GeneralCache", "mutex acquire begin, count:" + this.mMutex.availablePermits());
            try {
                this.mMutex.acquire();
            } catch (InterruptedException e) {
                LogHelper.e("GeneralCache", "mutex acquire exp:", e);
            }
            LogHelper.i("GeneralCache", "mutex acquire success");
            db.beginTransaction();
        }
    }

    private void endTransaction(SQLiteDatabase db) {
        if (db != null) {
            try {
                db.endTransaction();
            } catch (Exception e) {
                LogHelper.e("GeneralCache", "mutex acquire success", e);
                this.mMutex.release();
                LogHelper.i("GeneralCache", "mutex release");
                return;
            } catch (Throwable th) {
                this.mMutex.release();
                LogHelper.i("GeneralCache", "mutex release");
                throw th;
            }
        }
        this.mMutex.release();
        LogHelper.i("GeneralCache", "mutex release");
    }
}
