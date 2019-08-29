package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheParams;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.service.CacheServiceMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.AutoCleanStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.BusinessCleanStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DiskConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.SingleCleanItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.DiskCache;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.common.utils.MD5Util;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AutoStorageCleaner {
    private static final Logger a = Logger.getLogger((String) "AutoStorageCleaner");
    private static final AutoStorageCleaner b = new AutoStorageCleaner();
    private long c = 0;
    private final ConcurrentHashMap<String, SingleCleanItem> d = new ConcurrentHashMap<>();
    private final Set<String> e = new HashSet();
    private SharedPreferences f;

    public static AutoStorageCleaner get() {
        return b;
    }

    public void doClean() {
        if (Math.abs(System.currentTimeMillis() - this.c) > ConfigManager.getInstance().diskConf().cleanInterval * 60000) {
            try {
                CleanController.get().start();
                doAutoCleanExpiredCache();
                doAutoClean();
                doBusinessClean();
            } catch (Throwable e2) {
                a.e(e2, "doClean", new Object[0]);
            } finally {
                CleanController.get().stop();
                this.c = System.currentTimeMillis();
            }
        }
    }

    public synchronized long doAutoCleanExpiredCache() {
        long totalClean;
        totalClean = 0;
        AutoCleanStrategy strategy = ConfigManager.getInstance().diskConf().autoCleanStrategy;
        CacheServiceMgr cacheServiceMgr = CacheServiceMgr.get();
        if (strategy.expiredCacheAutoCleanSwitch == 1) {
            totalClean = cacheServiceMgr.cleanExpiredTimeCache(strategy.expiredCleanLimit);
        }
        return totalClean;
    }

    public synchronized long doAutoClean() {
        long j;
        long totalClean = 0;
        AutoCleanStrategy strategy = ConfigManager.getInstance().diskConf().autoCleanStrategy;
        if (strategy.autoCleanSwitch == 0) {
            j = 0;
        } else {
            a.d("doAutoClean start strategy: " + strategy, new Object[0]);
            CacheServiceMgr cacheServiceMgr = CacheServiceMgr.get();
            if (strategy.cleanOldVersionSwitch == 1) {
                totalClean = 0 + OldCacheCleaner.get().cleanOldVerCache(false, strategy.cleanOldPeriod);
            }
            APCacheParams expiredParams = new APCacheParams();
            expiredParams.skipLock = true;
            expiredParams.oldInterval = strategy.expiredCacheTime * 86400000;
            if (strategy.accessTimeAutoCleanSwitch == 1) {
                expiredParams.bUseAccessTime = true;
            }
            long totalClean2 = totalClean + cacheServiceMgr.deleteCache(expiredParams, null);
            a.d("doAutoClean deleteExpiredCache totalClean: " + totalClean2, new Object[0]);
            if (strategy.cleanOldTimeCacheSwitch == 1) {
                totalClean2 += OldCacheCleaner.get().cleanCacheByTime(expiredParams.oldInterval, strategy.cleanOldPeriod);
            }
            if (strategy.cleanBizCacheSwitch == 1 && !TextUtils.isEmpty(strategy.cleanBizs)) {
                for (String biz : strategy.getCleanBizs()) {
                    APCacheParams params = new APCacheParams();
                    params.businessId = biz;
                    params.bUseAccessTime = true;
                    params.skipLock = true;
                    params.oldInterval = ((long) strategy.cleanBizCacheTime) * 86400000;
                    totalClean2 += OldCacheCleaner.get().cleanOldCacheByParams(params);
                }
            }
            if (strategy.cleanTypeCacheSwitch == 1 && strategy.cleanTypes != null) {
                for (int type : strategy.cleanTypes) {
                    APCacheParams params2 = new APCacheParams();
                    params2.cleanTypes = type;
                    params2.bUseAccessTime = true;
                    params2.skipLock = true;
                    params2.oldInterval = ((long) strategy.cleanTypeCacheTime) * 86400000;
                    totalClean2 += OldCacheCleaner.get().cleanOldCacheByParams(params2);
                }
            }
            if (strategy.autoCleanZombieCacheSwitch == 1 && strategy.cleanZombieCacheSwitch == 1) {
                APCacheParams allParams = new APCacheParams();
                if (strategy.accessTimeAutoCleanSwitch == 1) {
                    allParams.bUseAccessTime = true;
                }
                long cleanSize = OldCacheCleaner.get().cleanZombieCache((long) strategy.cleanOldPeriod, cacheServiceMgr.convertListToHashSet(cacheServiceMgr.queryAllStorageInfo(allParams)));
                totalClean2 += cleanSize;
                a.d("doAutoClean clean zombie cache size: " + cleanSize, new Object[0]);
            }
            long currentCacheSize = cacheServiceMgr.getTotalCacheSize();
            a.d("doAutoClean current: " + currentCacheSize, new Object[0]);
            if (currentCacheSize < strategy.maxCacheSize * 1048576) {
                a.d("doAutoClean not reach the max cache size, max: " + strategy.maxCacheSize + ", current: " + currentCacheSize + ", totalClean: " + totalClean2, new Object[0]);
                j = totalClean2;
            } else {
                long totalClean3 = totalClean2 + a(totalClean2, strategy, cacheServiceMgr, currentCacheSize);
                a.d("doAutoClean finished!!! totalClean: " + totalClean3, new Object[0]);
                j = totalClean3;
            }
        }
        return j;
    }

    private long a(long totalClean, AutoCleanStrategy strategy, CacheServiceMgr cacheServiceMgr, long currentCacheSize) {
        APCacheParams allParams = new APCacheParams();
        if (strategy.accessTimeAutoCleanSwitch == 1) {
            allParams.bUseAccessTime = true;
        }
        List cacheInfos = cacheServiceMgr.queryAllStorageInfo(allParams);
        a(cacheInfos);
        long shouldCleanSize = currentCacheSize - ((strategy.maxCacheSize - strategy.cleanSize) * 1048576);
        a.d("cleanCacheContinue shouldCleanSize: " + shouldCleanSize + ", totalCacheInfo.size: " + cacheInfos.size(), new Object[0]);
        DiskConf conf = ConfigManager.getInstance().getCommonConfigItem().diskConf;
        Set whiteSet = new HashSet();
        if (!TextUtils.isEmpty(conf.expiredWhiteList)) {
            whiteSet.addAll(Arrays.asList(conf.expiredWhiteList.split(",")));
        }
        Set preWhiteSet = new HashSet();
        if (!TextUtils.isEmpty(conf.expiredPrefixWhiteList)) {
            preWhiteSet.addAll(Arrays.asList(conf.expiredPrefixWhiteList.split(",")));
        }
        List toRemoveList = new ArrayList();
        int i = cacheInfos.size() - 1;
        while (!CleanController.get().isInterrupt() && i >= 0 && shouldCleanSize >= 0) {
            FileCacheModel cacheInfo = cacheInfos.remove(i);
            if (!whiteSet.contains(cacheInfo.businessId)) {
                boolean whiteFlag = false;
                Iterator it = preWhiteSet.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (cacheInfo.businessId.startsWith((String) it.next())) {
                        whiteFlag = true;
                        break;
                    }
                }
                if (!whiteFlag) {
                    shouldCleanSize -= cacheInfo.fileSize;
                    totalClean += cacheInfo.fileSize;
                    toRemoveList.add(cacheInfo);
                }
            }
            i--;
        }
        CacheContext.get().getDiskCache().remove(toRemoveList);
        return totalClean;
    }

    public synchronized long doBusinessClean() {
        long totalClean;
        DiskConf diskConf = ConfigManager.getInstance().diskConf();
        if (diskConf.businessCleanSwitch != 0) {
            List businessCleanStrategies = diskConf.businessCleanStrategies;
            if (businessCleanStrategies != null && !businessCleanStrategies.isEmpty()) {
                CacheServiceMgr cacheServiceMgr = CacheServiceMgr.get();
                totalClean = 0;
                a.d("doBusinessClean cleanStrategy.size: " + businessCleanStrategies.size(), new Object[0]);
                for (BusinessCleanStrategy strategy : businessCleanStrategies) {
                    a.d("doBusinessClean strategy: " + strategy + ", interruptClean: " + CleanController.get().isInterrupt(), new Object[0]);
                    if (CleanController.get().isInterrupt()) {
                        break;
                    } else if (strategy.endTime >= System.currentTimeMillis()) {
                        try {
                            APCacheParams params = new APCacheParams();
                            params.businessIdPrefix = strategy.prefixBusinessId;
                            params.businessId = strategy.businessId;
                            params.cleanTypes = strategy.cleanTypes;
                            params.skipLock = strategy.skipLock;
                            params.oldInterval = strategy.cacheExpiredTime < 0 ? -1 : System.currentTimeMillis() - strategy.cacheExpiredTime;
                            totalClean += cacheServiceMgr.deleteCache(params, null);
                        } catch (Exception e2) {
                            a.e(e2, "doBusinessClean strategy: " + strategy, new Object[0]);
                        }
                        a.d("doBusinessClean strategy: " + strategy + ", totalClean: " + totalClean, new Object[0]);
                    }
                }
            } else {
                totalClean = 0;
            }
        } else {
            totalClean = 0;
        }
        return totalClean;
    }

    public void reset() {
        a.d("reset", new Object[0]);
        this.c = 0;
        CleanController.get().reset();
    }

    public void interruptClean() {
        a.d("interruptClean", new Object[0]);
        CleanController.get().stop();
    }

    public long doCleanExpiredCache() {
        return 0;
    }

    private void a(List<FileCacheModel> sortedFileItems) {
        try {
            Collections.sort(sortedFileItems, new Comparator<FileCacheModel>() {
                public int compare(FileCacheModel lhs, FileCacheModel rhs) {
                    return Double.compare((double) rhs.modifyTime, (double) lhs.modifyTime);
                }
            });
        } catch (Throwable t) {
            a.w("sortStorageCacheInfos error: " + t + ", use quick sort", new Object[0]);
            FileCacheModel[] files = new FileCacheModel[sortedFileItems.size()];
            sortedFileItems.toArray(files);
            quickSort(files);
            sortedFileItems.clear();
            Collections.addAll(sortedFileItems, files);
        }
    }

    public static void quickSort(FileCacheModel[] arr) {
        a(arr, 0, arr.length - 1);
    }

    private static void a(FileCacheModel[] arr, int low, int high) {
        if (low < high) {
            int pivot = b(arr, low, high);
            a(arr, low, pivot - 1);
            a(arr, pivot + 1, high);
        }
    }

    private static int b(FileCacheModel[] arr, int low, int high) {
        FileCacheModel pivot = arr[low];
        while (low < high) {
            while (low < high && arr[high].modifyTime <= pivot.modifyTime) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low].modifyTime >= pivot.modifyTime) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }

    public void submitCleanItems(final SingleCleanItem[] items) {
        if (ConfigManager.getInstance().getCommonConfigItem().diskConf.singleCleanSwitch != 0 && items != null && items.length > 0) {
            TaskScheduleManager.get().schedule(new Runnable() {
                public void run() {
                    AutoStorageCleaner.this.cleanItems(items);
                }
            }, 10000);
        }
    }

    /* access modifiers changed from: protected */
    public void cleanItems(SingleCleanItem[] items) {
        synchronized (this.d) {
            long start = System.currentTimeMillis();
            if (!a(items)) {
                a.d("cleanSingleItem changedCount: " + this.d.size(), new Object[0]);
                DiskCache diskCache = CacheContext.get().getDiskCache();
                for (SingleCleanItem item : this.d.values()) {
                    if (!this.f.getBoolean(item.key(), false)) {
                        a.d("cleanSingleItem item: " + item + ", enter..", new Object[0]);
                        boolean removed = false;
                        if (-1 == item.endTime || System.currentTimeMillis() < item.endTime) {
                            if (TextUtils.isEmpty(item.md5)) {
                                removed = diskCache.remove(item.id);
                            } else {
                                removed = a(diskCache, item);
                            }
                        }
                        this.f.edit().putBoolean(item.key(), true).apply();
                        a.d("cleanSingleItem item: " + item + ", removed: " + removed + ", finished!!", new Object[0]);
                    }
                }
                this.e.clear();
                this.e.addAll(this.d.keySet());
                a.d("cleanSingleItem end, totalCost: " + (System.currentTimeMillis() - start), new Object[0]);
            }
        }
    }

    private static boolean a(DiskCache diskCache, SingleCleanItem item) {
        String path = diskCache.genPathByKey(item.id);
        if (!FileUtils.checkFile(path)) {
            return false;
        }
        try {
            String md5 = MD5Util.getFileMD5String(new File(path));
            if (item.md5.equalsIgnoreCase(md5)) {
                return false;
            }
            a.d("cleanSingleItem item: " + item + ", md5 does not match! now md5: " + md5 + ", path: " + path, new Object[0]);
            return diskCache.remove(item.id);
        } catch (Exception e2) {
            a.e(e2, "check md5 error", new Object[0]);
            return false;
        }
    }

    private boolean a(SingleCleanItem[] items) {
        if (this.f == null) {
            this.f = AppUtils.getApplicationContext().getSharedPreferences("pref_clean", 0);
        }
        this.d.clear();
        for (SingleCleanItem item : items) {
            if (!TextUtils.isEmpty(item.id)) {
                this.d.putIfAbsent(item.key(), item);
            }
        }
        if (this.e.containsAll(this.d.keySet())) {
            return true;
        }
        return false;
    }
}
