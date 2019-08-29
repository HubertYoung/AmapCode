package com.alipay.diskcache.impl;

import android.content.Context;
import com.alipay.diskcache.StrategyConfig;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.diskcache.utils.FileUtils;
import com.alipay.diskcache.utils.LogHelper;
import java.io.File;
import java.util.List;

public class LruDiskCache extends BaseDiskCache {
    private long mCheckSizeTime = 0;
    private StrategyConfig mConfig = new StrategyConfig();
    private long mSpaceLeft = -1;
    private long mTime = System.currentTimeMillis();

    public LruDiskCache(Context context, File cacheDir) {
        super(context, cacheDir);
    }

    public LruDiskCache(Context context, String cacheDir) {
        super(context, new File(cacheDir));
    }

    public LruDiskCache(Context context, File baseDir, long maxSize, int expiredCount) {
        super(context, baseDir, maxSize, expiredCount);
    }

    public LruDiskCache(Context context, String cacheDir, long maxSize, int expiredCount) {
        super(context, new File(cacheDir), maxSize, expiredCount);
    }

    /* access modifiers changed from: protected */
    public synchronized void checkCacheSize(long increment) {
        if (this.mCurrentSize == 0) {
            LogHelper.d(BaseDiskCache.TAG, "getCacheTotalSize from db");
            this.mCurrentSize = getCachePersistence().getCacheTotalSize();
        } else {
            this.mCurrentSize += increment;
        }
        LogHelper.d(BaseDiskCache.TAG, "***checkCacheSize cachedSize: " + this.mCurrentSize);
        if (this.mCurrentSize > this.mMaxSize && this.mConfig.mLruSwitch == 1 && checkTimeInterval()) {
            this.mCheckSizeTime = System.currentTimeMillis();
            List<FileCacheModel> models = getCachePersistence().queryWillExpireCacheModel((long) this.mExpiredCount, getAutoExpiredWhiteSet());
            LogHelper.d(BaseDiskCache.TAG, "trigger elimination, cachedSize: " + this.mCurrentSize + ", maxSize: " + this.mMaxSize + ",\n models: " + models);
            for (FileCacheModel model : models) {
                remove(model);
            }
        }
    }

    public void trim() {
        if (Math.abs(System.currentTimeMillis() - this.mTime) > this.mConfig.mSpaceCheckInterval || this.mSpaceLeft < 0) {
            this.mTime = System.currentTimeMillis();
            this.mSpaceLeft = FileUtils.getSdAvailableSizeBytes(this.mContext);
        }
        if (this.mSpaceLeft <= this.mConfig.mLowSpaceThreshold) {
            if (this.mCurrentSize == 0) {
                LogHelper.d(BaseDiskCache.TAG, "getCacheTotalSize from db");
                this.mCurrentSize = getCachePersistence().getCacheTotalSize();
            }
            if (this.mCurrentSize > this.mConfig.mClearThreshold) {
                List list = getCachePersistence().queryWillExpireCacheModel((long) this.mExpiredCount, getAutoExpiredWhiteSet());
                long delete_size = 0;
                int i = 0;
                while (list != null && i < list.size() && delete_size < this.mConfig.mClearSize) {
                    remove(list.get(i));
                    delete_size += list.get(i).fileSize;
                    i++;
                }
            }
        }
    }

    public void updateConfig(StrategyConfig config) {
        if (config == null) {
            config = new StrategyConfig();
        }
        LogHelper.d(BaseDiskCache.TAG, "updateConfig, StrategyConfig:" + config.toString());
        this.mConfig = config;
        this.mMaxSize = config.mMaxCacheSize;
        this.mExpiredCount = config.mClearFileCount;
    }

    private boolean checkTimeInterval() {
        return this.mConfig != null && Math.abs(System.currentTimeMillis() - this.mCheckSizeTime) > this.mConfig.mSpaceCheckInterval;
    }
}
