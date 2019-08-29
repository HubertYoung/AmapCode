package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc;

import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheParams;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.service.CacheServiceMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.DirConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.AutoCleanStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.model.FileCacheModel;
import java.io.File;
import java.util.HashSet;
import java.util.List;

public class OldCacheCleaner {
    private static final OldCacheCleaner a = new OldCacheCleaner();
    private long b = 0;
    private long c = 0;
    private long d = 0;

    public static OldCacheCleaner get() {
        return a;
    }

    public long cleanOldVerCache(boolean bOthers, int timeInterval) {
        long start = System.currentTimeMillis();
        long deleteSize = 0;
        if (timeInterval <= 0 || Math.abs(start - this.b) > ((long) timeInterval) * 3600000) {
            try {
                this.b = start;
                String im = FileUtils.getMediaDir("im");
                String cache = FileUtils.getMediaDir("cache");
                String vcache = FileUtils.getMediaDir("vcache");
                File file = new File(im);
                File cacheDir = new File(cache);
                File file2 = new File(vcache);
                long deleteSize2 = FileUtils.deleteAllFiles(file);
                Logger.D("OldCacheCleaner", "cleanOldVerCache im:" + (System.currentTimeMillis() - start) + ";deleteSize=" + deleteSize2, new Object[0]);
                long deleteSize3 = deleteSize2 + FileUtils.deleteAllFiles(cacheDir);
                Logger.D("OldCacheCleaner", "cleanOldVerCache cache:" + (System.currentTimeMillis() - start) + ";deleteSize=" + deleteSize3, new Object[0]);
                deleteSize = deleteSize3 + FileUtils.deleteAllFiles(file2);
                if (bOthers) {
                    Logger.D("OldCacheCleaner", "cleanOldVerCache vcacheDir:" + (System.currentTimeMillis() - start) + ";deleteSize=" + deleteSize, new Object[0]);
                    long deleteSize4 = deleteSize + FileUtils.deleteAllFiles(new File(DirConstants.getAudioCache()));
                    Logger.D("OldCacheCleaner", "cleanOldVerCache audioDir:" + (System.currentTimeMillis() - start) + ";deleteSize=" + deleteSize4, new Object[0]);
                    long deleteSize5 = deleteSize4 + FileUtils.deleteAllFiles(new File(DirConstants.getFileCache()));
                    Logger.D("OldCacheCleaner", "cleanOldVerCache fileDir:" + (System.currentTimeMillis() - start) + ";deleteSize=" + deleteSize5, new Object[0]);
                    File file3 = new File(DirConstants.getMaterialCache());
                    long deleteSize6 = deleteSize5 + FileUtils.deleteAllFiles(file3);
                    Logger.D("OldCacheCleaner", "cleanOldVerCache meterialDir:" + (System.currentTimeMillis() - start) + ";deleteSize=" + deleteSize6, new Object[0]);
                    deleteSize = deleteSize6 + FileUtils.deleteAllFiles(new File(FileUtils.getMediaDir("django")));
                }
            } catch (Throwable e) {
                Logger.E((String) "OldCacheCleaner", e, (String) "cleanOldVerCache exp", new Object[0]);
            }
            Logger.D("OldCacheCleaner", "cleanOldVerCache coast time=" + (System.currentTimeMillis() - start) + ";deleteSize=" + deleteSize + ";bInterrupt=" + CleanController.get().isInterrupt(), new Object[0]);
            return deleteSize;
        }
        Logger.D("OldCacheCleaner", "cleanOldVerCache return timeInterval=" + timeInterval, new Object[0]);
        return 0;
    }

    public long cleanCacheByTime(long time, int timeInterval) {
        long deleteSize = 0;
        long start = System.currentTimeMillis();
        if (timeInterval <= 0 || Math.abs(start - this.c) > ((long) timeInterval) * 3600000) {
            this.c = start;
            try {
                long j = time;
                deleteSize = FileUtils.deleteAllCacheFiles(CacheServiceMgr.get().getCacheFileDir(), j, ConfigManager.getInstance().diskConf().autoCleanStrategy.ignoreSuffix);
            } catch (Throwable e) {
                Logger.E((String) "OldCacheCleaner", e, (String) "cleanCacheByTime exp", new Object[0]);
            }
            Logger.D("OldCacheCleaner", "cleanCacheByTime deleteSize=" + deleteSize + ";coast=" + (System.currentTimeMillis() - start) + ";time=" + time + ";bInterrupt=" + CleanController.get().isInterrupt(), new Object[0]);
            return deleteSize;
        }
        Logger.D("OldCacheCleaner", "cleanCacheByTime return timeInterval=" + timeInterval, new Object[0]);
        return 0;
    }

    public long cleanZombieCache(long timeInterval, HashSet<String> listData) {
        long deleteSize = 0;
        long start = System.currentTimeMillis();
        if (timeInterval <= 0 || Math.abs(start - this.d) > 3600000 * timeInterval) {
            this.d = start;
            try {
                AutoCleanStrategy config = ConfigManager.getInstance().diskConf().autoCleanStrategy;
                HashSet<String> hashSet = listData;
                deleteSize = FileUtils.deleteAllCacheFilesNotInList(CacheServiceMgr.get().getCacheFileDir(), hashSet, config.ignoreSuffix, System.currentTimeMillis() - (((long) config.zombieExpiredTime) * 86400000));
            } catch (Throwable e) {
                Logger.E((String) "OldCacheCleaner", e, (String) "cleanZombieCache exp", new Object[0]);
            }
            Logger.D("OldCacheCleaner", "cleanZombieCache deleteSize=" + deleteSize + ";coast=" + (System.currentTimeMillis() - start) + ";bInterrupt=" + CleanController.get().isInterrupt(), new Object[0]);
            return deleteSize;
        }
        Logger.D("OldCacheCleaner", "cleanZombieCache return timeInterval=" + timeInterval, new Object[0]);
        return 0;
    }

    public long cleanOldCacheByParams(APCacheParams params) {
        if (params == null || CleanController.get().isInterrupt()) {
            return 0;
        }
        long deleteSize = 0;
        long deleteCount = 0;
        List dataList = CacheServiceMgr.get().queryRemoveCacheList(params);
        if (dataList == null) {
            return 0;
        }
        for (FileCacheModel info : dataList) {
            if (CleanController.get().isInterrupt()) {
                break;
            }
            try {
                FileUtils.checkFile(info.path);
                FileUtils.delete(info.path);
                deleteCount++;
                deleteSize += info.fileSize;
            } catch (Exception e) {
                Logger.D("OldCacheCleaner", "cleanOldCacheByParams del file exp=" + e.toString(), new Object[0]);
            }
        }
        Logger.D("OldCacheCleaner", "cleanOldCacheByParams deleteSize=" + deleteSize + ";deleteCount" + deleteCount + ";param=" + params.toString(), new Object[0]);
        try {
            CacheContext.get().getDiskCache().remove(dataList);
            return deleteSize;
        } catch (Throwable e2) {
            Logger.D("OldCacheCleaner", "cleanOldCacheByParams db remove exp=" + e2.toString(), new Object[0]);
            return deleteSize;
        }
    }
}
