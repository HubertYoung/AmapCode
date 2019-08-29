package com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheParams;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APStorageCacheInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.Persistence;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ParamChecker;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ReflectUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public abstract class DbPersistence<T> implements Persistence<T> {
    private static final Logger logger = Logger.getLogger((String) "DbPersistence");
    private Class<T> clazz;
    private Dao<T, String> mDao;
    protected DbHelper mDbHelper;

    protected DbPersistence() {
    }

    public DbPersistence(Context context, DbHelper dbHelper, Class<T> clazz2) {
        this.mDbHelper = dbHelper;
        this.clazz = clazz2;
        ParamChecker.pmdCheck(context);
    }

    public Dao<T, String> getDao() {
        if (this.mDao == null) {
            try {
                this.mDao = this.mDbHelper.getDao(this.clazz);
            } catch (Throwable e) {
                logger.e(e, "getDao error", new Object[0]);
            }
        }
        return this.mDao;
    }

    /* access modifiers changed from: private */
    public void updateCacheInfo(T obj) {
        if (obj instanceof APStorageCacheInfo) {
            ((APStorageCacheInfo) obj).updateStorageCacheInfo();
        }
    }

    public T save(T obj) {
        updateCacheInfo(obj);
        getDao().createOrUpdate(obj);
        return obj;
    }

    public List<T> save(final List<T> objects) {
        TransactionManager.callInTransaction(this.mDbHelper.getConnectionSource(), new Callable<List<T>>() {
            public List<T> call() {
                for (Object obj : objects) {
                    DbPersistence.this.updateCacheInfo(obj);
                    DbPersistence.this.getDao().createOrUpdate(obj);
                }
                return objects;
            }
        });
        return objects;
    }

    public T[] save(final T... objects) {
        TransactionManager.callInTransaction(this.mDbHelper.getConnectionSource(), new Callable<T[]>() {
            public T[] call() {
                Object[] objArr;
                for (Object obj : objects) {
                    DbPersistence.this.updateCacheInfo(obj);
                    DbPersistence.this.getDao().createOrUpdate(obj);
                }
                return objects;
            }
        });
        return objects;
    }

    public T add(T obj) {
        updateCacheInfo(obj);
        getDao().create(obj);
        return obj;
    }

    public List<T> add(final List<T> objects) {
        TransactionManager.callInTransaction(this.mDbHelper.getConnectionSource(), new Callable<List<T>>() {
            public List<T> call() {
                for (Object obj : objects) {
                    DbPersistence.this.updateCacheInfo(obj);
                    DbPersistence.this.getDao().create(obj);
                }
                return objects;
            }
        });
        return objects;
    }

    public T[] add(final T... objects) {
        TransactionManager.callInTransaction(this.mDbHelper.getConnectionSource(), new Callable<T[]>() {
            public T[] call() {
                Object[] objArr;
                for (Object obj : objects) {
                    DbPersistence.this.updateCacheInfo(obj);
                    DbPersistence.this.getDao().create(obj);
                }
                return objects;
            }
        });
        return objects;
    }

    public T update(T obj, String... fields) {
        updateCacheInfo(obj);
        getDao().update(obj);
        return obj;
    }

    public List<T> update(final List<T> objects, String... fields) {
        TransactionManager.callInTransaction(this.mDbHelper.getConnectionSource(), new Callable<List<T>>() {
            public List<T> call() {
                for (Object obj : objects) {
                    DbPersistence.this.updateCacheInfo(obj);
                    DbPersistence.this.getDao().update(obj);
                }
                return objects;
            }
        });
        return objects;
    }

    public void deleteById(Object id) {
        this.mDbHelper.getDao(ReflectUtils.getClassGenericType(getClass())).deleteById(id);
    }

    public void deleteByCacheId(String cacheId) {
        if (!TextUtils.isEmpty(cacheId)) {
            String baseSql = String.format("DELETE FROM %s WHERE %s = ?", new Object[]{getTableName(), APStorageCacheInfo.F_CACHE_ID});
            getDao().executeRaw(baseSql, cacheId);
        }
    }

    public T delete(Class<T> clazz2, String id) {
        Object obj = query(clazz2, id);
        if (obj != null) {
            getDao().deleteById(id);
        }
        return obj;
    }

    public T delete(T obj) {
        getDao().delete(obj);
        return obj;
    }

    public T[] delete(final T... objects) {
        TransactionManager.callInTransaction(this.mDbHelper.getConnectionSource(), new Callable<T[]>() {
            public T[] call() {
                for (Object obj : objects) {
                    DbPersistence.this.getDao().delete(obj);
                }
                return objects;
            }
        });
        return objects;
    }

    public List<T> delete(final List<T> objects) {
        TransactionManager.callInTransaction(this.mDbHelper.getConnectionSource(), new Callable<List<T>>() {
            public List<T> call() {
                for (Object obj : objects) {
                    DbPersistence.this.getDao().delete(obj);
                }
                return objects;
            }
        });
        return objects;
    }

    public T query(Class<T> clazz2, String id) {
        return getDao().queryForId(id);
    }

    public List<T> queryForEq(Class<T> clazz2, String fieldName, String value) {
        return getDao().queryForEq(fieldName, new SelectArg((Object) value));
    }

    public List<T> queryAll(Class<T> clazz2) {
        return getDao().queryForAll();
    }

    public QueryBuilder<T, String> queryBuilder() {
        return getDao().queryBuilder();
    }

    public UpdateBuilder<T, String> updateBuilder() {
        return getDao().updateBuilder();
    }

    public DeleteBuilder<T, String> deleteBuilder() {
        return getDao().deleteBuilder();
    }

    public long countOf() {
        return getDao().countOf();
    }

    public List<T> queryCacheRecordsByBusinessId(APCacheParams params) {
        try {
            QueryBuilder queryBuilder = getDao().queryBuilder();
            fillQueryBuilder(params, queryBuilder);
            List list = queryBuilder.query();
            logger.p("queryCacheRecordsByBusinessId sql: " + queryBuilder.prepareStatementString(), new Object[0]);
            return list;
        } catch (Exception e) {
            logger.w("queryCacheRecordByBusinessId params: " + params + ", e: " + e, new Object[0]);
            return new ArrayList();
        }
    }

    public List<String> queryAllBusinessId() {
        List list = new ArrayList();
        try {
            GenericRawResults results = getDao().queryBuilder().distinct().selectColumns(APStorageCacheInfo.F_CACHE_BUSINESS_ID).queryRaw();
            if (results != null) {
                List<String[]> resultList = results.getResults();
                if (resultList != null && !resultList.isEmpty()) {
                    for (String[] item : resultList) {
                        list.add(item[0]);
                    }
                }
            }
        } catch (Exception e) {
            logger.w("queryAllBusinessId error: " + e, new Object[0]);
        }
        return list;
    }

    public Map<String, APCacheResult> sumBusinessSize(APCacheParams params) {
        Map map = new HashMap();
        QueryBuilder queryBuilder = getDao().queryBuilder();
        queryBuilder.selectRaw(sumBusinessSizeColumns());
        Cursor cursor = null;
        try {
            fillQueryBuilder(params, queryBuilder);
            queryBuilder.groupBy(APStorageCacheInfo.F_CACHE_BUSINESS_ID);
            String sql = queryBuilder.prepareStatementString();
            logger.p("sumBusinessSize sql: " + sql, new Object[0]);
            Cursor cursor2 = this.mDbHelper.getReadableDatabase().rawQuery(sql, null);
            while (cursor2.moveToNext()) {
                APCacheResult r = new APCacheResult();
                r.businessId = cursor2.getString(0);
                r.totalFileSize = cursor2.getLong(1);
                r.fileCount = cursor2.getInt(2);
                map.put(r.businessId, r);
            }
            if (cursor2 != null) {
                cursor2.close();
            }
        } catch (Exception e) {
            logger.w("sumEachBusinessSize params: " + params + ", err: " + e, new Object[0]);
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return map;
    }

    /* access modifiers changed from: protected */
    public Where<T, String> fillQueryBuilder(APCacheParams params, QueryBuilder<T, String> queryBuilder) {
        Where where = queryBuilder.where().raw("1=1", new ArgumentHolder[0]);
        if (!TextUtils.isEmpty(params.businessId)) {
            where = where.and().eq(APStorageCacheInfo.F_CACHE_BUSINESS_ID, params.businessId);
        } else if (!TextUtils.isEmpty(params.businessIdPrefix)) {
            where = where.and().like(APStorageCacheInfo.F_CACHE_BUSINESS_ID, params.businessIdPrefix + '%');
        }
        if (params.oldInterval > 0) {
            where = where.and().lt(APStorageCacheInfo.F_CACHE_LAST_MODIFIED_TIME, Long.valueOf(System.currentTimeMillis() - params.oldInterval));
        }
        if (params.skipLock) {
            where.and().ne(APStorageCacheInfo.F_CACHE_LOCK, Boolean.valueOf(true));
        }
        return where;
    }

    /* access modifiers changed from: protected */
    public String sumBusinessSizeColumns() {
        return String.format("%s, SUM(%s), COUNT(%s)", new Object[]{APStorageCacheInfo.F_CACHE_BUSINESS_ID, APStorageCacheInfo.F_CACHE_FILE_SIZE, APStorageCacheInfo.F_CACHE_BUSINESS_ID});
    }
}
