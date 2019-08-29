package com.alipay.diskcache.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.alipay.diskcache.DiskCache;
import com.alipay.diskcache.DiskCache.QueryFilter;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.diskcache.model.StatisticInfo;
import com.alipay.diskcache.naming.FileCacheGenerator;
import com.alipay.diskcache.naming.MultiDirFileGenerator;
import com.alipay.diskcache.persistence.FileCachePersistence;
import com.alipay.diskcache.utils.CacheUtils;
import com.alipay.diskcache.utils.FileUtils;
import com.alipay.diskcache.utils.IOUtils;
import com.alipay.diskcache.utils.ImageUtils;
import com.alipay.diskcache.utils.LogHelper;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseDiskCache implements DiskCache {
    private static final int DEFAULT_EXPIRED_COUNT = 10;
    private static final int DEFAULT_REFRESH_INTERVAL = 10000;
    public static final String TAG = "GeneralCache";
    private final int L2CACHE_SIZE;
    private transient Set<String> mAutoExpiredWhiteSet;
    private File mBaseDir;
    protected FileCachePersistence mCachePersistence;
    protected Context mContext;
    protected long mCurrentSize;
    protected int mExpiredCount;
    private FileCacheGenerator mFileNameGenerator;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public L2Cache mL2Cache;
    private long mLastRefreshTime;
    protected long mMaxSize;
    private HandlerThread mThread;
    private Set<FileCacheModel> mToRefreshCacheModels;

    private class L2Cache {
        private LruCache<String, List<FileCacheModel>> mCache;

        public L2Cache(int size) {
            this.mCache = new LruCache<>(size);
        }

        public FileCacheModel get(String key) {
            List list = (List) this.mCache.get(key);
            if (list == null || list.size() <= 0) {
                return null;
            }
            return (FileCacheModel) list.get(0);
        }

        public List<FileCacheModel> getList(String key) {
            return (List) this.mCache.get(key);
        }

        public void put(String key, FileCacheModel value) {
            List models = BaseDiskCache.this.mL2Cache.getList(key);
            if (models == null) {
                models = new ArrayList();
            }
            models.add(value);
            this.mCache.put(key, models);
        }

        public void put(String key, List<FileCacheModel> list) {
            this.mCache.put(key, list);
        }

        public void remove(String key) {
            this.mCache.remove(key);
        }
    }

    private static class RefreshHandler extends Handler {
        private WeakReference<BaseDiskCache> mWeakRef;

        public RefreshHandler(BaseDiskCache cache, Looper looper) {
            super(looper);
            this.mWeakRef = new WeakReference<>(cache);
        }

        public void handleMessage(Message inputMessage) {
            Object obj = inputMessage.obj;
            BaseDiskCache cache = (BaseDiskCache) this.mWeakRef.get();
            if (cache != null) {
                cache.handleUpdateTime((List) obj);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void checkCacheSize(long j);

    public Set<String> getAutoExpiredWhiteSet() {
        return this.mAutoExpiredWhiteSet;
    }

    /* access modifiers changed from: protected */
    public void setAutoExpiredWhiteSet(Set<String> whiteSet) {
        if (whiteSet != null && !whiteSet.isEmpty()) {
            this.mAutoExpiredWhiteSet = new HashSet(whiteSet);
        } else if (this.mAutoExpiredWhiteSet != null) {
            this.mAutoExpiredWhiteSet = null;
        }
    }

    private synchronized Handler getHandler() {
        if (this.mHandler == null) {
            this.mThread = new HandlerThread("disk_cache_accession_update");
            this.mThread.start();
            this.mHandler = new RefreshHandler(this, this.mThread.getLooper());
        }
        return this.mHandler;
    }

    /* access modifiers changed from: private */
    public void handleUpdateTime(List<FileCacheModel> models) {
        LogHelper.d(TAG, "handleUpdateTime() for access, input models:" + models);
        try {
            getCachePersistence().updateAccessTime(models);
        } catch (Exception e) {
            LogHelper.e(TAG, "update cacheModels error", e);
        }
    }

    public BaseDiskCache(Context context, File baseDir, long maxSize, int expiredCount) {
        this.mLastRefreshTime = System.currentTimeMillis();
        this.mToRefreshCacheModels = new HashSet();
        this.L2CACHE_SIZE = 1000;
        this.mCurrentSize = 0;
        this.mContext = context;
        this.mBaseDir = baseDir;
        this.mMaxSize = maxSize;
        this.mExpiredCount = expiredCount;
        this.mFileNameGenerator = new MultiDirFileGenerator();
        this.mL2Cache = new L2Cache(1000);
    }

    public BaseDiskCache(Context context, File baseDir) {
        this(context, baseDir, 104857600, 10);
    }

    public FileCachePersistence getCachePersistence() {
        if (this.mCachePersistence == null) {
            synchronized (this) {
                if (this.mCachePersistence == null) {
                    long start = System.currentTimeMillis();
                    try {
                        this.mCachePersistence = FileCachePersistence.getInstance(this.mContext, this.mBaseDir.getAbsolutePath());
                    } catch (Exception e) {
                        LogHelper.e(TAG, "getCachePersistence error", e);
                    }
                    LogHelper.d(TAG, "FileCachePersistence create cost: " + (System.currentTimeMillis() - start) + RPCDataParser.TIME_MS);
                }
            }
        }
        return this.mCachePersistence;
    }

    public String getPath(String key) {
        LogHelper.d(TAG, "getPath(), input key:" + key);
        FileCacheModel model = queryCacheModel(key);
        if (model != null) {
            return model.path;
        }
        return null;
    }

    public FileCacheModel get(String key) {
        LogHelper.d(TAG, "get(), input key:" + key);
        List list = this.mL2Cache.getList(key);
        if (list != null) {
            LogHelper.d(TAG, "get from l2 cache, key:" + key);
            if (list.size() > 0) {
                return list.get(0);
            }
            return null;
        }
        FileCacheModel model = getCachePersistence().queryByCacheKey(key);
        if (model == null) {
            FileCacheModel model2 = getCachePersistence().queryByAliasKey(key);
            if (model2 != null) {
                if (!new File(model2.path).exists()) {
                    try {
                        getCachePersistence().queryByAliasKey(key);
                    } catch (Exception e) {
                        LogHelper.d(TAG, e.getMessage());
                    }
                    return null;
                }
                this.mL2Cache.put(key, model2);
                updateCacheAccessTime(model2);
            }
            if (model2 != null) {
                return model2;
            }
            this.mL2Cache.put(key, (List<FileCacheModel>) new ArrayList<FileCacheModel>());
            return model2;
        } else if (!new File(model.path).exists()) {
            try {
                getCachePersistence().deleteByCacheKey(key);
            } catch (Exception e2) {
                LogHelper.d(TAG, e2.getMessage());
            }
            return null;
        } else {
            this.mL2Cache.put(key, model);
            updateCacheAccessTime(model);
            return model;
        }
    }

    public FileCacheModel get(String key, QueryFilter filter) {
        LogHelper.d(TAG, "get() with filter, input key:" + key);
        if (filter == null) {
            return null;
        }
        List list = this.mL2Cache.getList(key);
        if (list != null) {
            LogHelper.d(TAG, "get from l2 cache, key:" + key);
            if (list.size() <= 0) {
                return null;
            }
            FileCacheModel model = filter.parse(list);
            updateCacheAccessTime(model);
            return model;
        }
        List models = getCachePersistence().queryByCacheKey2(key);
        LogHelper.d(TAG, "get, queryByCacheKey2, key: " + key + ", models: " + models);
        if (models.isEmpty()) {
            models = getCachePersistence().queryByAliasKey2(key);
            LogHelper.d(TAG, "get, queryByAliasKey2, key: " + key + ", models: " + models);
        }
        FileCacheModel model2 = filter.parse(models);
        if (model2 == null) {
            this.mL2Cache.put(key, models);
            return null;
        } else if (!new File(model2.path).exists()) {
            try {
                getCachePersistence().deleteByPath(model2.path);
            } catch (Exception e) {
                LogHelper.d(TAG, e.getMessage());
            }
            return null;
        } else {
            this.mL2Cache.put(key, models);
            updateCacheAccessTime(model2);
            return model2;
        }
    }

    public List<FileCacheModel> getAlias(int type) {
        return getCachePersistence().queryAlias(type);
    }

    public boolean save(String key, Bitmap value, String businessId) {
        if (!checkParam(key, value)) {
            return false;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageUtils.compressBitmap(value, baos);
        return save(key, baos.toByteArray(), businessId);
    }

    public boolean save(String key, byte[] data, String businessId) {
        if (!checkParam(key, data)) {
            return false;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            return save(key, (InputStream) bais, businessId);
        } finally {
            IOUtils.closeQuietly((InputStream) bais);
        }
    }

    public boolean save(String key, InputStream in, String businessId) {
        invalidateL2Cache(key);
        if (!checkParam(key, in)) {
            return false;
        }
        File dstFile = generateCacheFile(key);
        boolean ret = false;
        try {
            ret = FileUtils.safeCopyToFile(in, dstFile);
        } catch (IOException e) {
            LogHelper.e(TAG, "safeCopyToFile exception:", e);
        }
        LogHelper.d(TAG, "saveFile key-val key: " + key + ", file: " + dstFile + ", len: " + FileUtils.fileSize(dstFile) + ",bussiness id:" + businessId + ", ret: " + ret);
        if (!ret) {
            return ret;
        }
        addDbRecord(key, dstFile, businessId);
        checkCacheSize(dstFile.length());
        return ret;
    }

    public boolean remove(String key) {
        LogHelper.d(TAG, "remove() input key:" + key);
        List models = getCachePersistence().queryByCacheKey2(key);
        if (models == null || models.isEmpty()) {
            models = getCachePersistence().queryByAliasKey2(key);
        }
        return remove(models);
    }

    /* access modifiers changed from: protected */
    public boolean remove(FileCacheModel model, boolean deleteDb) {
        if (model == null) {
            return false;
        }
        LogHelper.d(TAG, "remove() input model:" + model.toString());
        this.mCurrentSize = 0;
        invalidateL2Cache(model.cacheKey);
        invalidateL2Cache(model.aliasKey);
        if (deleteDb) {
            try {
                getCachePersistence().deleteByPath(model.path);
            } catch (Exception e) {
                LogHelper.d(TAG, e.getMessage());
            }
        }
        File file = new File(model.path);
        if (FileUtils.checkFile(file)) {
            return file.delete();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean remove(FileCacheModel model) {
        return remove(model, true);
    }

    public boolean remove(List<FileCacheModel> list) {
        this.mCurrentSize = 0;
        int i = 0;
        while (list != null && i < list.size()) {
            remove(list.get(i), false);
            i++;
        }
        if (!getCachePersistence().delete(list)) {
            return false;
        }
        return true;
    }

    public void close() {
    }

    public void clear() {
    }

    private File generateCacheFile(String key) {
        return this.mFileNameGenerator.generate(this.mBaseDir.getAbsolutePath(), key);
    }

    /* access modifiers changed from: protected */
    public boolean checkParam(String oldKey, String newKey) {
        return !TextUtils.isEmpty(oldKey) && !TextUtils.isEmpty(newKey) && !oldKey.equals(newKey);
    }

    /* access modifiers changed from: protected */
    public boolean checkParam(String key, byte[] data) {
        return (key == null || data == null || data.length <= 0) ? false : true;
    }

    /* access modifiers changed from: protected */
    public boolean checkParam(String key, InputStream in) {
        return (key == null || in == null) ? false : true;
    }

    /* access modifiers changed from: protected */
    public boolean checkParam(String key, Bitmap data) {
        return (key == null || data == null) ? false : true;
    }

    /* access modifiers changed from: protected */
    public boolean checkParam(String key) {
        return key != null;
    }

    /* access modifiers changed from: protected */
    public boolean addDbRecord(String key, File file, String businessId) {
        if (!CacheUtils.checkCacheFile(file)) {
            return false;
        }
        String path = file.getAbsolutePath();
        if (key == null || getCachePersistence() == null) {
            return false;
        }
        FileCacheModel model = getCachePersistence().queryByCacheKey(key);
        if (model == null) {
            model = new FileCacheModel();
            model.cacheKey = key;
            model.path = path;
            model.businessId = businessId;
            model.modifyTime = System.currentTimeMillis();
        }
        model.fileSize = new File(path).length();
        model.accessTime = System.currentTimeMillis();
        try {
            getCachePersistence().save(model);
            return true;
        } catch (Exception e) {
            LogHelper.e(TAG, "addDbRecord exception: model: " + model, e);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public FileCacheModel addDbRecord(String key, String path, int type, int tag, String extra, String bizId) {
        return addDbRecord(key, path, type, tag, extra, bizId, Long.MAX_VALUE);
    }

    /* access modifiers changed from: protected */
    public FileCacheModel addDbRecord(String key, String path, int type, int tag, String extra, String bizId, long expiredTime) {
        invalidateL2Cache(key);
        FileCacheModel model = null;
        File file = !TextUtils.isEmpty(path) ? new File(path) : generateCacheFile(key);
        if (CacheUtils.checkCacheFile(file)) {
            if (getCachePersistence() != null) {
                model = getCachePersistence().queryByCacheKey(key);
                if (model != null && !model.path.equals(file.getAbsolutePath())) {
                    model = null;
                }
                if (model == null) {
                    LogHelper.d(TAG, "queryByCacheKey for addDbRecord(), key:" + key + " return null");
                    model = new FileCacheModel();
                    model.cacheKey = key;
                    model.path = file.getAbsolutePath();
                    model.aliasKey = key;
                    model.businessId = bizId;
                    model.modifyTime = System.currentTimeMillis();
                    model.fileSize = new File(model.path).length();
                }
                model.accessTime = System.currentTimeMillis();
                model.tag = tag;
                model.type = type;
                if (!TextUtils.isEmpty(extra)) {
                    model.extra = extra;
                }
                model.expiredTime = expiredTime;
                try {
                    getCachePersistence().save(model);
                } catch (Exception e) {
                    LogHelper.e(TAG, "addDbRecord exception: model: " + model, e);
                    return null;
                }
            }
            return model;
        }
        LogHelper.d(TAG, "addDbRecord() but file doesn't exist.");
        return null;
    }

    /* access modifiers changed from: private */
    public synchronized void updateCacheAccessTime(FileCacheModel model) {
        if (model != null) {
            if (checkCacheExistence(model) && !TextUtils.isEmpty(model.path)) {
                model.accessTime = System.currentTimeMillis();
                this.mToRefreshCacheModels.add(model);
                if (System.currentTimeMillis() - this.mLastRefreshTime > 10000) {
                    List models = new ArrayList(this.mToRefreshCacheModels);
                    this.mToRefreshCacheModels.clear();
                    this.mLastRefreshTime = System.currentTimeMillis();
                    getHandler().sendMessage(getHandler().obtainMessage(0, models));
                }
            }
        }
    }

    private boolean checkCacheExistence(FileCacheModel model) {
        if (FileUtils.checkFile(new File(model.path))) {
            return true;
        }
        getCachePersistence().deleteByPath(model.path);
        invalidateL2Cache(model.cacheKey);
        return false;
    }

    private FileCacheModel queryCacheModel(final String key) {
        FileCacheModel model;
        if (!checkParam(key)) {
            return null;
        }
        FileCacheModel l2 = this.mL2Cache.get(key);
        if (l2 != null) {
            LogHelper.d(TAG, "get from l2 cache, key:" + key);
            updateCacheAccessTime(l2);
            return l2;
        }
        File file = generateCacheFile(key);
        if (FileUtils.checkFile(file)) {
            model = new FileCacheModel();
            model.cacheKey = key;
            model.path = file.getAbsolutePath();
            final FileCacheModel newModel = model;
            getHandler().post(new Runnable() {
                public void run() {
                    FileCacheModel dbModel = BaseDiskCache.this.getCachePersistence().queryByPath(newModel.path);
                    if (dbModel != null) {
                        BaseDiskCache.this.mL2Cache.put(key, dbModel);
                        BaseDiskCache.this.updateCacheAccessTime(dbModel);
                    }
                }
            });
        } else {
            LogHelper.d(TAG, "queryCacheModel again as alias_key:" + key);
            model = getCachePersistence().queryByAliasKey(key);
            if (model == null) {
                LogHelper.d(TAG, "queryCacheModel again as alias_key return null");
                try {
                    getCachePersistence().deleteByCacheKey(key);
                    getCachePersistence().deleteByPath(file.getAbsolutePath());
                } catch (Exception e) {
                    LogHelper.e(TAG, "delete db record exception:", e);
                }
            } else if (new File(model.path).exists()) {
                this.mL2Cache.put(key, model);
                updateCacheAccessTime(model);
            } else {
                try {
                    getCachePersistence().deleteByPath(model.path);
                } catch (Exception e2) {
                    LogHelper.e(TAG, "deleteByPath exception:", e2);
                }
                model = null;
            }
        }
        return model;
    }

    public long getTotalSize(int type) {
        long size = -1;
        try {
            return getCachePersistence().getCacheSizeByType(type);
        } catch (Exception e) {
            LogHelper.e(TAG, "getCacheSizeByType exp:" + e.getMessage());
            return size;
        }
    }

    public long getTotalSize(String bizId) {
        long size = -1;
        try {
            return getCachePersistence().getCacheSizeByBiz(bizId);
        } catch (Exception e) {
            LogHelper.e(TAG, "getCacheSizeByBiz exp:" + e.getMessage());
            return size;
        }
    }

    public String genPathByKey(String key) {
        return generateCacheFile(key).getAbsolutePath();
    }

    public boolean save(String key, int type, int tag, String businessId, long expiredTime) {
        FileCacheModel model = addDbRecord(key, null, type, tag, null, businessId, expiredTime);
        if (model != null) {
            checkCacheSize(model.fileSize);
        }
        return model != null;
    }

    public boolean save(String key, String path, int type, int tag, String extra, String businessId, long expiredTime) {
        FileCacheModel model = addDbRecord(key, path, type, tag, extra, businessId, expiredTime);
        if (model != null) {
            checkCacheSize(model.fileSize);
        }
        return model != null;
    }

    public boolean update(String key, String aliasKey) {
        return update(key, aliasKey, -1);
    }

    public boolean update(String key, String aliasKey, int tag) {
        invalidateL2Cache(key);
        invalidateL2Cache(aliasKey);
        List models = getCachePersistence().queryByCacheKey2(key);
        if (models == null || models.size() <= 0) {
            return false;
        }
        for (FileCacheModel model : models) {
            model.aliasKey = aliasKey;
            if (TextUtils.isEmpty(aliasKey)) {
                model.multiAliasKeys = "";
            }
            if (tag >= 0) {
                model.tag = tag;
            }
            model.accessTime = System.currentTimeMillis();
        }
        try {
            getCachePersistence().save(models);
            return true;
        } catch (SQLException e) {
            LogHelper.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    public boolean update(String key, int tag) {
        invalidateL2Cache(key);
        FileCacheModel model = getCachePersistence().queryByCacheKey(key);
        if (model == null) {
            FileCacheModel model2 = getCachePersistence().queryByAliasKey(key);
            if (model2 == null) {
                return false;
            }
            if (!new File(model2.path).exists()) {
                try {
                    getCachePersistence().deleteByAliasKey(key);
                    return false;
                } catch (Exception e) {
                    LogHelper.e(TAG, e.getMessage(), e);
                    return false;
                }
            } else {
                model2.tag = tag;
                model2.accessTime = System.currentTimeMillis();
                try {
                    getCachePersistence().save(model2);
                } catch (SQLException e2) {
                    LogHelper.e(TAG, e2.getMessage(), e2);
                    return false;
                }
            }
        } else if (!generateCacheFile(key).exists()) {
            try {
                getCachePersistence().deleteByCacheKey(key);
            } catch (Exception e3) {
                LogHelper.e(TAG, e3.getMessage(), e3);
                return false;
            }
        } else {
            model.tag = tag;
            model.accessTime = System.currentTimeMillis();
            try {
                getCachePersistence().save(model);
            } catch (SQLException e4) {
                LogHelper.e(TAG, e4.getMessage(), e4);
                return false;
            }
        }
        return true;
    }

    public boolean update(FileCacheModel record) {
        if (record == null || record.id <= 0) {
            return false;
        }
        try {
            getCachePersistence().save(record);
            return true;
        } catch (SQLException e) {
            LogHelper.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    public List<StatisticInfo> statisticByGroup(String businessId, int tag, boolean skipLock, long interval) {
        return getCachePersistence().queryByGroup(businessId, tag, skipLock, interval, false);
    }

    public List<FileCacheModel> queryForStatistic(String businessId, int tag, boolean skipLock, long interval) {
        return getCachePersistence().query(businessId, tag, skipLock, interval, false);
    }

    public List<StatisticInfo> statisticByGroup(String businessId, int tag, boolean skipLock, long interval, boolean useAccTime) {
        return getCachePersistence().queryByGroup(businessId, tag, skipLock, interval, useAccTime);
    }

    public List<FileCacheModel> queryForStatistic(String businessId, int tag, boolean skipLock, long interval, boolean useAccTime) {
        return getCachePersistence().query(businessId, tag, skipLock, interval, useAccTime);
    }

    public List<FileCacheModel> queryExpiredRecords(int limit, boolean skipLock) {
        return getCachePersistence().queryExpiredRecords(limit, getAutoExpiredWhiteSet(), skipLock, System.currentTimeMillis());
    }

    public List<FileCacheModel> queryNonWhiteListRecords(Set<String> list, int limit, boolean skipLock) {
        return getCachePersistence().queryExpiredRecords(limit, list, skipLock, Long.MAX_VALUE);
    }

    public List<FileCacheModel> getRecent(long time, int tag) {
        return getCachePersistence().queryCacheModelsByTimeInterval(time, tag, true);
    }

    private void invalidateL2Cache(String key) {
        if (key != null) {
            FileCacheModel l2_model = this.mL2Cache.get(key);
            if (l2_model != null) {
                this.mL2Cache.remove(l2_model.aliasKey);
            }
            this.mL2Cache.remove(key);
        }
    }

    public void setupExpiredWhiteList(Set<String> set) {
        setAutoExpiredWhiteSet(set);
    }

    public List<String> queryAllBusiness() {
        return getCachePersistence().queryAllBusiness();
    }

    public List<FileCacheModel> getMultiAlias(int type) {
        return getCachePersistence().getMultiAlias(type);
    }

    public boolean appendAliasKey(String key, String aliasKey) {
        invalidateL2Cache(key);
        invalidateL2Cache(aliasKey);
        return getCachePersistence().appendAliasKey(key, aliasKey);
    }
}
