package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.service;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheDeleteCallback;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheParams;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheQueryCallback;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheResult;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.AutoStorageCleaner;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CleanController;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.OldCacheCleaner;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.AutoCleanStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DiskConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.diskcache.model.StatisticInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CacheServiceMgr {
    private static final Logger a = Logger.getLogger((String) "CacheServiceMgr");
    private static final CacheServiceMgr b = new CacheServiceMgr();
    public static Boolean isAlipayWallet = null;
    private File c;
    private String d;
    private int[] e = {1024, 512, 128, 256, 1, 4, 2, 8, 2048};

    public static CacheServiceMgr get() {
        return b;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long cleanExpiredTimeCache(int r14) {
        /*
            r13 = this;
            r12 = 0
            r0 = 0
            r2 = 0
            long r6 = java.lang.System.currentTimeMillis()
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext.get()     // Catch:{ Throwable -> 0x0081 }
            com.alipay.diskcache.DiskCache r8 = r8.getDiskCache()     // Catch:{ Throwable -> 0x0081 }
            r9 = 1
            java.util.List r5 = r8.queryExpiredRecords(r14, r9)     // Catch:{ Throwable -> 0x0081 }
            if (r5 == 0) goto L_0x001d
            int r8 = r5.size()     // Catch:{ Throwable -> 0x0081 }
            if (r8 > 0) goto L_0x003c
        L_0x001d:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r8 = a     // Catch:{ Throwable -> 0x0081 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r10 = "cleanExpiredTimeCache size is 0 ,coastTime="
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0081 }
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0081 }
            long r10 = r10 - r6
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0081 }
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0081 }
            r8.d(r9, r10)     // Catch:{ Throwable -> 0x0081 }
            r8 = 0
        L_0x003b:
            return r8
        L_0x003c:
            java.util.Iterator r8 = r5.iterator()     // Catch:{ Throwable -> 0x0081 }
        L_0x0040:
            boolean r9 = r8.hasNext()     // Catch:{ Throwable -> 0x0081 }
            if (r9 == 0) goto L_0x00ee
            java.lang.Object r4 = r8.next()     // Catch:{ Throwable -> 0x0081 }
            com.alipay.diskcache.model.FileCacheModel r4 = (com.alipay.diskcache.model.FileCacheModel) r4     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r9 = r4.path     // Catch:{ Exception -> 0x00ca }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.checkFile(r9)     // Catch:{ Exception -> 0x00ca }
            java.lang.String r9 = r4.path     // Catch:{ Exception -> 0x00ca }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils.delete(r9)     // Catch:{ Exception -> 0x00ca }
            int r0 = r0 + 1
            long r10 = r4.fileSize     // Catch:{ Exception -> 0x00ca }
            long r2 = r2 + r10
        L_0x005b:
            int r9 = r0 % 10
            if (r9 != 0) goto L_0x0040
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = a     // Catch:{ Throwable -> 0x0081 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r11 = "cleanExpiredTimeCache onProgress deleteFileCount: "
            r10.<init>(r11)     // Catch:{ Throwable -> 0x0081 }
            java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r11 = ", deleteFileSize: "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Throwable -> 0x0081 }
            java.lang.StringBuilder r10 = r10.append(r2)     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0081 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x0081 }
            r9.d(r10, r11)     // Catch:{ Throwable -> 0x0081 }
            goto L_0x0040
        L_0x0081:
            r1 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r8 = a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "cleanExpiredTimeCache error: "
            r9.<init>(r10)
            java.lang.StringBuilder r9 = r9.append(r1)
            java.lang.String r9 = r9.toString()
            java.lang.Object[] r10 = new java.lang.Object[r12]
            r8.w(r9, r10)
        L_0x0098:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r8 = a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "cleanExpiredTimeCache finish, deleteFileCount: "
            r9.<init>(r10)
            java.lang.StringBuilder r9 = r9.append(r0)
            java.lang.String r10 = ", deleteFileSize: "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r2)
            java.lang.String r10 = ", coastTime: "
            java.lang.StringBuilder r9 = r9.append(r10)
            long r10 = java.lang.System.currentTimeMillis()
            long r10 = r10 - r6
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            java.lang.Object[] r10 = new java.lang.Object[r12]
            r8.d(r9, r10)
            r8 = r2
            goto L_0x003b
        L_0x00ca:
            r1 = move-exception
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r9 = a     // Catch:{ Throwable -> 0x0081 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r11 = "cleanExpiredTimeCache info: "
            r10.<init>(r11)     // Catch:{ Throwable -> 0x0081 }
            java.lang.StringBuilder r10 = r10.append(r4)     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r11 = ", error: "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Throwable -> 0x0081 }
            java.lang.StringBuilder r10 = r10.append(r1)     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0081 }
            r11 = 0
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x0081 }
            r9.w(r10, r11)     // Catch:{ Throwable -> 0x0081 }
            goto L_0x005b
        L_0x00ee:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext r8 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext.get()     // Catch:{ Throwable -> 0x0081 }
            com.alipay.diskcache.DiskCache r8 = r8.getDiskCache()     // Catch:{ Throwable -> 0x0081 }
            r8.remove(r5)     // Catch:{ Throwable -> 0x0081 }
            goto L_0x0098
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.service.CacheServiceMgr.cleanExpiredTimeCache(int):long");
    }

    public long deleteCache(APCacheParams params, APCacheDeleteCallback callback) {
        params.validParams();
        a.d("deleteCache params: " + params + ", callback: " + callback, new Object[0]);
        int totalFileCount = 0;
        long totalFileSize = 0;
        int deletedFileCount = 0;
        long deletedFileSize = 0;
        for (APCacheResult r : queryCacheInfos(params, null).values()) {
            totalFileCount += r.fileCount;
            totalFileSize += r.totalFileSize;
        }
        a.d("deleteCache totalFileCount: " + totalFileCount + ", totalFileSize: " + totalFileSize, new Object[0]);
        if (callback != null) {
            callback.onStart(totalFileCount, totalFileSize);
        }
        List list = queryRemoveCacheList(params);
        a.d("querySize: " + list.size(), new Object[0]);
        for (FileCacheModel info : list) {
            try {
                FileUtils.checkFile(info.path);
                FileUtils.delete(info.path);
                deletedFileCount++;
                deletedFileSize += info.fileSize;
            } catch (Exception e2) {
                a.w("deleteCache info: " + info + ", error: " + e2, new Object[0]);
            }
            if (deletedFileCount % 10 == 0) {
                a.d("deleteCache onProgress deleteFileCount: " + deletedFileCount + ", totalFileCount: " + totalFileCount + ", deleteFileSize: " + deletedFileSize + ", totalFileSize: " + totalFileSize, new Object[0]);
            }
            if (callback != null) {
                callback.onProgress(deletedFileCount, totalFileCount, deletedFileSize, totalFileSize);
            }
        }
        CacheContext.get().getDiskCache().remove(list);
        a.d("deleteCache finish, deleteFileCount: " + deletedFileCount + ", totalFileCount: " + totalFileCount + ", deleteFileSize: " + deletedFileSize + ", totalFileSize: " + totalFileSize, new Object[0]);
        if (callback != null) {
            callback.onFinish(deletedFileCount, totalFileCount, deletedFileSize, totalFileSize);
        }
        return deletedFileSize;
    }

    public List<FileCacheModel> queryRemoveCacheList(APCacheParams params) {
        String biz = !TextUtils.isEmpty(params.businessIdPrefix) ? params.businessIdPrefix : params.businessId;
        List list = CacheContext.get().getDiskCache().queryForStatistic(biz, a(params.cleanTypes), params.skipLock, params.oldInterval, params.bUseAccessTime);
        if (!TextUtils.isEmpty(biz)) {
            return list;
        }
        DiskConf conf = ConfigManager.getInstance().getCommonConfigItem().diskConf;
        HashSet hashSet = new HashSet();
        if (!TextUtils.isEmpty(conf.expiredWhiteList)) {
            hashSet.addAll(Arrays.asList(conf.expiredWhiteList.split(",")));
        }
        Set preWhiteSet = new HashSet();
        if (!TextUtils.isEmpty(conf.expiredPrefixWhiteList)) {
            preWhiteSet.addAll(Arrays.asList(conf.expiredPrefixWhiteList.split(",")));
        }
        List list2 = new ArrayList();
        for (int i = list.size() - 1; i >= 0; i--) {
            FileCacheModel cacheInfo = list.remove(i);
            if (!hashSet.contains(cacheInfo.businessId)) {
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
                    list2.add(cacheInfo);
                }
            }
        }
        Logger.D("CacheServiceMgr", "queryRemoveCacheList all size = " + list.size() + ";remove size=" + list2.size() + ";param=" + params.toString(), new Object[0]);
        return list2;
    }

    public long deleteCache(Set<String> whiteList, int limit, String bizType) {
        int totalFileCount = 0;
        long totalFileSize = 0;
        int deletedFileCount = 0;
        long deletedFileSize = 0;
        long oldVerCacheSize = 0;
        long oldTimeCacheSize = 0;
        long zombieCacheSize = 0;
        long bizCacheSize = 0;
        long typeCacheSize = 0;
        long startTime = System.currentTimeMillis();
        try {
            CleanController.get().start();
            a.d("deleteCache limit=" + limit + ";bizType=" + bizType + ";whiteList=" + whiteList, new Object[0]);
            AutoCleanStrategy cleanStrategy = ConfigManager.getInstance().diskConf().autoCleanStrategy;
            if (cleanStrategy.cleanOldVersionSwitch == 1) {
                oldVerCacheSize = OldCacheCleaner.get().cleanOldVerCache(true, 0);
                deletedFileSize = oldVerCacheSize;
            }
            List list = CacheContext.get().getDiskCache().queryNonWhiteListRecords(whiteList, limit, true);
            if (list != null) {
                a.d("deleteCache size=" + list.size() + " ,coastTime=" + (System.currentTimeMillis() - startTime), new Object[0]);
                totalFileCount = list.size();
                for (FileCacheModel info : list) {
                    if (CleanController.get().isInterrupt()) {
                        break;
                    }
                    try {
                        FileUtils.checkFile(info.path);
                        FileUtils.delete(info.path);
                        deletedFileCount++;
                        deletedFileSize += info.fileSize;
                    } catch (Exception e2) {
                        a.w("deleteCache info: " + info + ", error: " + e2, new Object[0]);
                    }
                    if (deletedFileCount % 20 == 0) {
                        a.d("deleteCache onProgress deleteFileCount: " + deletedFileCount + ", deleteFileSize: " + deletedFileSize, new Object[0]);
                    }
                }
                totalFileSize = deletedFileSize;
                CacheContext.get().getDiskCache().remove(list);
            }
            if (cleanStrategy.cleanOldTimeCacheSwitch == 1) {
                oldTimeCacheSize = OldCacheCleaner.get().cleanCacheByTime(cleanStrategy.expiredCacheTime * 86400000, 0);
                deletedFileSize += oldTimeCacheSize;
            }
            if (cleanStrategy.cleanZombieCacheSwitch == 1) {
                APCacheParams allParams = new APCacheParams();
                if (cleanStrategy.accessTimeAutoCleanSwitch == 1) {
                    allParams.bUseAccessTime = true;
                }
                zombieCacheSize = OldCacheCleaner.get().cleanZombieCache(0, convertListToHashSet(queryAllStorageInfo(allParams)));
                deletedFileSize += zombieCacheSize;
            }
            if (cleanStrategy.cleanBizCacheSwitch == 1 && !TextUtils.isEmpty(cleanStrategy.cleanBizs)) {
                String[] cleanBizs = cleanStrategy.getCleanBizs();
                int length = cleanBizs.length;
                for (int i = 0; i < length; i++) {
                    String biz = cleanBizs[i];
                    APCacheParams params = new APCacheParams();
                    params.businessId = biz;
                    params.bUseAccessTime = true;
                    params.skipLock = true;
                    params.oldInterval = ((long) cleanStrategy.cleanBizCacheTime) * 86400000;
                    bizCacheSize += OldCacheCleaner.get().cleanOldCacheByParams(params);
                }
                deletedFileSize += bizCacheSize;
            }
            if (cleanStrategy.cleanTypeCacheSwitch == 1 && cleanStrategy.cleanTypes != null) {
                int[] iArr = cleanStrategy.cleanTypes;
                int length2 = iArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    int type = iArr[i2];
                    APCacheParams params2 = new APCacheParams();
                    params2.cleanTypes = type;
                    params2.bUseAccessTime = true;
                    params2.skipLock = true;
                    params2.oldInterval = ((long) cleanStrategy.cleanTypeCacheTime) * 86400000;
                    typeCacheSize += OldCacheCleaner.get().cleanOldCacheByParams(params2);
                }
                deletedFileSize += typeCacheSize;
            }
        } catch (Throwable e3) {
            a.w("deleteCache error: " + e3, new Object[0]);
        } finally {
            CleanController.get().stop();
        }
        a.d("deleteCache finish, deleteFileCount: " + deletedFileCount + ", totalFileCount: " + totalFileCount + ", deleteFileSize: " + deletedFileSize + ", totalFileSize: " + totalFileSize + ", oldVerCacheSize: " + oldVerCacheSize + ", oldTimeCacheSize: " + oldTimeCacheSize + ";zombieCacheSize=" + zombieCacheSize + ";useTime=" + (System.currentTimeMillis() - startTime), new Object[0]);
        return deletedFileSize;
    }

    public Map<String, APCacheResult> queryCacheInfos(APCacheParams params, APCacheQueryCallback callback) {
        params.validParams();
        String biz = !TextUtils.isEmpty(params.businessIdPrefix) ? params.businessIdPrefix : params.businessId;
        a.d("queryCacheInfos params: " + params + ", callback: " + callback, new Object[0]);
        Map map = new HashMap();
        if (callback != null) {
            callback.onStartQuery();
        }
        List list = CacheContext.get().getDiskCache().statisticByGroup(biz, a(params.cleanTypes), params.skipLock, params.oldInterval, params.bUseAccessTime);
        if (callback != null) {
            callback.onQueryProgress(99);
        }
        for (StatisticInfo info : list) {
            APCacheResult result = new APCacheResult();
            result.businessId = info.mBusinessId;
            result.fileCount = info.mCount;
            result.totalFileSize = info.mTotalSize;
            map.put(info.mBusinessId, result);
        }
        if (callback != null) {
            callback.onQueryProgress(100);
            callback.onQueryFinish(map);
        }
        a.d("queryCacheInfos params: " + params + ", map: " + map, new Object[0]);
        return map;
    }

    public long deleteExpiredCache() {
        return AutoStorageCleaner.get().doCleanExpiredCache();
    }

    public long getTotalCacheSize() {
        long total = 0;
        for (Entry<String, APCacheResult> entry : queryCacheInfos(new APCacheParams(), null).entrySet()) {
            total += ((APCacheResult) entry.getValue()).totalFileSize;
        }
        return total;
    }

    public List<FileCacheModel> queryAllStorageInfo(APCacheParams params) {
        return CacheContext.get().getDiskCache().queryForStatistic(params.businessId, a(params.cleanTypes), params.skipLock, params.oldInterval, params.bUseAccessTime);
    }

    private int a(int type) {
        int result = 0;
        int i = 0;
        while (type != 0 && i < this.e.length) {
            if ((type & 1) != 0) {
                result |= this.e[i];
            }
            type >>= 1;
            i++;
        }
        return result;
    }

    public File getCacheFileDir() {
        if (this.c == null) {
            String pkgName = AppUtils.getApplicationContext().getPackageName();
            Logger.I("CacheServiceMgr", "pkgname:" + pkgName, new Object[0]);
            String cacheRoot = CacheConfig.getCacheDirNew().getAbsolutePath();
            if (TextUtils.isEmpty(pkgName) || pkgName.equals("com.eg.android.AlipayGphone")) {
                isAlipayWallet = Boolean.valueOf(true);
            } else {
                cacheRoot = cacheRoot + File.separator + pkgName;
                isAlipayWallet = Boolean.valueOf(false);
            }
            Logger.I("CacheServiceMgr", "getCacheDirNew:" + cacheRoot, new Object[0]);
            this.c = new File(cacheRoot);
        }
        return this.c;
    }

    public boolean checkMultimediaCacheDir(String path, String bizType) {
        if (TextUtils.isEmpty(path)) {
            Logger.P("CacheServiceMgr", "checkMultimediaCacheDir path is null and bizType=" + bizType, new Object[0]);
            return false;
        }
        if (this.d == null) {
            this.d = getCacheFileDir().getAbsolutePath();
        }
        if (!isAlipayWallet.booleanValue()) {
            return path.startsWith(this.d);
        }
        if (path.startsWith(this.d)) {
            File file = new File(path);
            if (!file.isDirectory()) {
                return false;
            }
            if (file.getName().length() == 2 || "com.eg.android.AlipayGphone".equalsIgnoreCase(file.getName()) || this.d.equalsIgnoreCase(path)) {
                return true;
            }
            return false;
        } else if (path.startsWith(this.c.getParent())) {
            return true;
        } else {
            return false;
        }
    }

    public HashSet<String> convertListToHashSet(List<FileCacheModel> fileList) {
        HashSet dataSet = null;
        if (fileList != null && !fileList.isEmpty()) {
            Logger.D("CacheServiceMgr", "convertListToHashSet fileList size=" + fileList.size(), new Object[0]);
            dataSet = new HashSet();
            for (int i = 0; i < fileList.size(); i++) {
                dataSet.add(fileList.get(i).path);
            }
        }
        return dataSet;
    }
}
