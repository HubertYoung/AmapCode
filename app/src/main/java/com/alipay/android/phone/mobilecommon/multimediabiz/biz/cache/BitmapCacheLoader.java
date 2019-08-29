package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemoryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.utils.AshmemLocalMonitor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.BitmapParcel;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.GifAssist;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.ImageCachePerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class BitmapCacheLoader {
    public static final int LARGE_CACHE_MEM_SIZE = a();
    protected static final Logger logger = Logger.getLogger((String) "BitmapCacheLoader");
    private CacheContext a;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, String> b;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<String, String> c;
    private MemoryCache d;
    protected Handler mAsyncHandler;

    public BitmapCacheLoader(CacheContext cacheContext) {
        this.b = new ConcurrentHashMap<>();
        this.c = new ConcurrentHashMap<>();
        this.a = cacheContext;
        this.d = cacheContext.getSoftReferenceMemCache();
        this.mAsyncHandler = TaskScheduleManager.get().commonHandler();
    }

    public BitmapCacheLoader() {
        this(CacheContext.get());
    }

    public boolean putMemCache(BitmapCacheKey key, Bitmap value, String businessId) {
        if (!ImageUtils.checkBitmap(value)) {
            return false;
        }
        if (isLargeBitmap(value)) {
            return this.d.put(key.complexCacheKey(), value);
        }
        if (b(value) >= LARGE_CACHE_MEM_SIZE || a(businessId) == null) {
            return false;
        }
        a(businessId).put(key.complexCacheKey(), value);
        return true;
    }

    private MemoryCache a(String business) {
        return this.a.getMemCache(business);
    }

    private Bitmap a(BitmapCacheKey key, String businessId) {
        Bitmap val = a(businessId).get(key.complexCacheKey());
        if (!ImageUtils.checkBitmap(val)) {
            val = this.d.get(key.complexCacheKey());
        }
        if (!ImageUtils.checkBitmap(val)) {
            return a(key);
        }
        return val;
    }

    public Bitmap get(BitmapCacheKey key, String businessId) {
        if (key.complexCacheKey().contains("##q")) {
            Bitmap val = a(BitmapCacheKey.create(key).updateQuality(-1), businessId);
            if (ImageUtils.checkBitmap(val)) {
                return val;
            }
        }
        return a(key, businessId);
    }

    private Bitmap b(BitmapCacheKey key, String business) {
        Bitmap val = a(business).get(key.complexCacheKey());
        if (val == null) {
            return this.d.get(key.complexCacheKey());
        }
        return val;
    }

    public Bitmap getMemCache(BitmapCacheKey key, String businessId) {
        if (key.complexCacheKey().contains("##q")) {
            Bitmap val = b(BitmapCacheKey.create(key).updateQuality(-1), businessId);
            if (ImageUtils.checkBitmap(val)) {
                return val;
            }
        }
        return b(key, businessId);
    }

    private Bitmap a(BitmapCacheKey key, Bitmap pre, String businessId) {
        if (pre != null && isLargeBitmap(pre)) {
            pre = null;
        }
        Bitmap val = a(businessId).get(key.complexCacheKey(), pre);
        if (val == null && key.aliasComplexKey() != null) {
            val = a(businessId).get(key.aliasComplexKey(), pre);
        }
        if (val == null) {
            val = this.d.get(key.complexCacheKey(), pre);
        }
        if (val != null || key.aliasComplexKey() == null) {
            return val;
        }
        return this.d.get(key.aliasComplexKey(), pre);
    }

    public Bitmap getMemCache(BitmapCacheKey key, Bitmap pre, String businessId) {
        if (key.complexCacheKey().contains("##q")) {
            Bitmap val = a(BitmapCacheKey.create(key).updateQuality(-1), pre, businessId);
            if (ImageUtils.checkBitmap(val)) {
                return val;
            }
        }
        return a(key, pre, businessId);
    }

    public boolean putDiskCache(BitmapCacheKey key, String source, Bitmap value, String businessId, boolean jpg, long expiredTime) {
        boolean ret = false;
        if (value != null && !value.isRecycled()) {
            String keyStr = key.complexCacheKey();
            if (this.c.putIfAbsent(keyStr, "0") != null) {
                logger.p("putDiskCache inQueue: " + key, new Object[0]);
                return false;
            }
            ret = AshmemLocalMonitor.get().isUseAshmem() ? a(key, source, value, jpg, businessId, keyStr, (String) null, 0, (ImageInfo) null, expiredTime) : a(key, source, value, businessId, jpg, keyStr, (String) null, 0, (ImageInfo) null, expiredTime);
        }
        return ret;
    }

    public boolean putDiskCache(BitmapCacheKey key, String source, Bitmap value, String businessId, boolean jpg, String encryptKey, int fileType, ImageInfo imageInfo, long expiredTime) {
        boolean ret = false;
        if (value != null && !value.isRecycled()) {
            String keyStr = key.complexCacheKey();
            if (this.c.putIfAbsent(keyStr, "0") != null) {
                logger.p("putDiskCache inQueue: " + key, new Object[0]);
                return false;
            }
            ret = AshmemLocalMonitor.get().isUseAshmem() ? a(key, source, value, jpg, businessId, keyStr, encryptKey, fileType, imageInfo, expiredTime) : a(key, source, value, businessId, jpg, keyStr, encryptKey, fileType, imageInfo, expiredTime);
        }
        return ret;
    }

    public boolean putDiskCacheByPath(BitmapCacheKey key, String path, int tag, String biz, long expiredTime) {
        return getDiskCache().savePath(key, path, tag, biz, expiredTime);
    }

    private boolean a(BitmapCacheKey key, String source, Bitmap bitmap, boolean jpg, String businessId, String keyStr, String encryptKey, int fileType, ImageInfo imageInfo, long expiredTime) {
        final String str = encryptKey;
        final BitmapCacheKey bitmapCacheKey = key;
        final String str2 = source;
        final Bitmap bitmap2 = bitmap;
        final boolean z = jpg;
        final String str3 = businessId;
        final long j = expiredTime;
        final int i = fileType;
        final ImageInfo imageInfo2 = imageInfo;
        final String str4 = keyStr;
        TaskScheduleManager.get().execute(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                try {
                    if (TextUtils.isEmpty(str)) {
                        BitmapCacheLoader.this.getDiskCache().saveData(bitmapCacheKey, BitmapCacheLoader.b(bitmapCacheKey, str2, bitmap2, z), str3, j, str);
                    } else {
                        BitmapCacheLoader.this.a(bitmapCacheKey, str2, bitmap2, z, str3, str, i, imageInfo2, j);
                    }
                } catch (Exception e2) {
                    BitmapCacheLoader.b(e2, str4, bitmap2 == null ? 0 : (long) BitmapCacheLoader.b(bitmap2), bitmapCacheKey.key, str3, "putDiskCache fail");
                    BitmapCacheLoader.logger.e(e2, "putDiskCacheAshmem exception, key: " + bitmapCacheKey, new Object[0]);
                } finally {
                    BitmapCacheLoader.this.c.remove(bitmapCacheKey);
                    Logger logger = BitmapCacheLoader.logger;
                    r7 = "putDiskCacheAshmem key: ";
                    StringBuilder append = new StringBuilder(r7).append(bitmapCacheKey);
                    r7 = ", cost: ";
                    logger.p(append.append(r7).append(System.currentTimeMillis() - start).toString(), new Object[0]);
                }
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public static byte[] b(BitmapCacheKey key, String source, Bitmap bitmap, boolean jpg) {
        byte[] data = null;
        if (key.resType != 1 && ImageFileType.isGif(new File(source))) {
            GifAssist.recordGif(key);
            data = FileUtils.file2Bytes(source);
        } else if (key.scaleType == CutScaleType.KEEP_RATIO.getValue() || key.scaleType == CutScaleType.SCALE_AUTO_LIMIT.getValue()) {
            if (CompareUtils.in(Integer.valueOf(ImageFileType.detectImageFileType(source)), Integer.valueOf(0), Integer.valueOf(1)) && TextUtils.isEmpty(key.pluginKey) && TextUtils.isEmpty(key.waterMarkKey)) {
                ImageInfo info = ImageInfo.getImageInfo(source);
                if (info.correctWidth <= bitmap.getWidth() && info.correctHeight <= bitmap.getHeight()) {
                    data = FileUtils.file2Bytes(source);
                }
            }
        }
        if (data == null) {
            return ImageUtils.bitmap2Bytes(bitmap, jpg);
        }
        return data;
    }

    /* access modifiers changed from: private */
    public void a(BitmapCacheKey key, String source, Bitmap bitmap, boolean jpg, String businessId, String encryptKey, int fileType, ImageInfo info, long expiredTime) {
        byte[] data = null;
        boolean hasSaved = false;
        if (key.resType != 1) {
            if (CompareUtils.in(Integer.valueOf(fileType), Integer.valueOf(2))) {
                GifAssist.recordGif(key);
                data = FileUtils.file2Bytes(source);
                if (data != null) {
                    hasSaved = true;
                    getDiskCache().saveData(key, data, businessId, expiredTime, encryptKey);
                }
                if (!hasSaved && data == null) {
                    getDiskCache().saveData(key, ImageUtils.bitmap2Bytes(bitmap, jpg), businessId, expiredTime, encryptKey);
                    return;
                }
            }
        }
        if (key.scaleType == CutScaleType.KEEP_RATIO.getValue() || key.scaleType == CutScaleType.SCALE_AUTO_LIMIT.getValue()) {
            if (CompareUtils.in(Integer.valueOf(fileType), Integer.valueOf(0), Integer.valueOf(1)) && TextUtils.isEmpty(key.pluginKey) && TextUtils.isEmpty(key.waterMarkKey) && info != null && info.correctWidth <= bitmap.getWidth() && info.correctHeight <= bitmap.getHeight()) {
                getDiskCache().savePath(key, source, key.tag, businessId, expiredTime);
                hasSaved = true;
            }
        }
        if (!hasSaved) {
        }
    }

    private boolean a(BitmapCacheKey key, String source, Bitmap value, String businessId, boolean jpg, String keyStr, String encryptKey, int fileType, ImageInfo imageInfo, long expiredTime) {
        final BitmapParcel parcel = BitmapParcel.create(value);
        if (parcel != null) {
            final String str = encryptKey;
            final BitmapCacheKey bitmapCacheKey = key;
            final String str2 = source;
            final boolean z = jpg;
            final String str3 = businessId;
            final long j = expiredTime;
            final int i = fileType;
            final ImageInfo imageInfo2 = imageInfo;
            final String str4 = keyStr;
            TaskScheduleManager.get().execute(new Runnable() {
                public void run() {
                    long start = System.currentTimeMillis();
                    Bitmap bitmap = null;
                    try {
                        bitmap = parcel.getBitmap();
                        if (TextUtils.isEmpty(str)) {
                            BitmapCacheLoader.this.getDiskCache().saveData(bitmapCacheKey, BitmapCacheLoader.b(bitmapCacheKey, str2, bitmap, z), str3, j, str);
                        } else {
                            BitmapCacheLoader.this.a(bitmapCacheKey, str2, bitmap, z, str3, str, i, imageInfo2, j);
                        }
                        BitmapCacheLoader.this.c.remove(bitmapCacheKey);
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        BitmapCacheLoader.logger.p("putDiskCacheArt key: " + bitmapCacheKey + ", cost: " + (System.currentTimeMillis() - start), new Object[0]);
                        parcel.recycle();
                    } catch (Exception e2) {
                        BitmapCacheLoader.b(e2, str4, bitmap == null ? 0 : (long) BitmapCacheLoader.b(bitmap), bitmapCacheKey.key, str3, "putDiskCache fail");
                        BitmapCacheLoader.logger.e(e2, "putDiskCacheArt exception, key: " + bitmapCacheKey, new Object[0]);
                        BitmapCacheLoader.this.c.remove(bitmapCacheKey);
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        BitmapCacheLoader.logger.p("putDiskCacheArt key: " + bitmapCacheKey + ", cost: " + (System.currentTimeMillis() - start), new Object[0]);
                        parcel.recycle();
                    } catch (Throwable th) {
                        BitmapCacheLoader.this.c.remove(bitmapCacheKey);
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                        BitmapCacheLoader.logger.p("putDiskCacheArt key: " + bitmapCacheKey + ", cost: " + (System.currentTimeMillis() - start), new Object[0]);
                        parcel.recycle();
                        throw th;
                    }
                }
            });
            return true;
        }
        logger.d("putDiskCacheArt parcel way is full, use normal way", new Object[0]);
        try {
            putDiskCache(key, ImageUtils.bitmap2Bytes(value, jpg), businessId, encryptKey, expiredTime);
            return true;
        } catch (Exception e2) {
            logger.e(e2, "putDiskCacheArt putDiskCache normal way exception", new Object[0]);
            return false;
        } finally {
            this.c.remove(key);
        }
    }

    /* access modifiers changed from: private */
    public static int b(Bitmap value) {
        if (value != null) {
            return value.getRowBytes() * value.getHeight();
        }
        return Integer.MAX_VALUE;
    }

    public static boolean isLargeBitmap(Bitmap value) {
        int size = b(value);
        return size >= LARGE_CACHE_MEM_SIZE && size < 104857600;
    }

    public void releaseImageMem(long size, boolean bForce) {
    }

    public void trimToSize(int size) {
        if (a(CacheContext.COMMON_NONE_BUSINESS) != null) {
            a(CacheContext.COMMON_NONE_BUSINESS).trimToSize(size);
        }
    }

    public Bitmap loadCacheBitmapInner(BitmapCacheKey cacheKey, boolean loadFromDiskCache) {
        boolean z = true;
        long start = System.currentTimeMillis();
        Bitmap bitmap = this.a.getCommonMemCache().get(cacheKey.complexCacheKey());
        if (bitmap == null) {
            bitmap = b(cacheKey, CacheContext.COMMON_NONE_BUSINESS);
        }
        ImageCachePerf.getCurrent().addMemHitTime(bitmap != null, System.currentTimeMillis() - start);
        if (!ImageUtils.checkBitmap(bitmap) && loadFromDiskCache) {
            long start2 = System.currentTimeMillis();
            bitmap = a(cacheKey);
            ImageCachePerf current = ImageCachePerf.getCurrent();
            if (bitmap == null) {
                z = false;
            }
            current.addDiskHitTime(z, System.currentTimeMillis() - start2);
        }
        return bitmap;
    }

    public Bitmap loadCacheBitmap(BitmapCacheKey cacheKey, boolean loadFromDiskCache) {
        if (cacheKey.complexCacheKey().contains("##q")) {
            Bitmap val = loadCacheBitmapInner(BitmapCacheKey.create(cacheKey).updateQuality(-1), loadFromDiskCache);
            if (ImageUtils.checkBitmap(val)) {
                return val;
            }
        }
        return loadCacheBitmapInner(cacheKey, loadFromDiskCache);
    }

    private static int a() {
        int size = 1536000;
        Resources res = AppUtils.getResources();
        if (res != null) {
            int size2 = (int) (1536000.0f * res.getDisplayMetrics().density);
            if (ConfigManager.getInstance().getAshmemConfSwitch()) {
                size2 *= 3;
            }
            size = Math.min(size2, ConfigManager.getInstance().getCommonConfigItem().cache.largeMemSize);
        }
        Logger.P("BitmapCacheLoader", "calcLargeCacheMemorySize size: " + size, new Object[0]);
        return size;
    }

    private Bitmap a(BitmapCacheKey key) {
        if (getDiskCache() != null) {
            return getDiskCache().getBitmap(key);
        }
        return null;
    }

    public Bitmap getDiskCache(BitmapCacheKey key) {
        if (key.complexCacheKey().contains("##q")) {
            Bitmap val = a(BitmapCacheKey.create(key).updateQuality(-1));
            if (ImageUtils.checkBitmap(val)) {
                return val;
            }
        }
        return a(key);
    }

    public boolean put(BitmapCacheKey key, String source, Bitmap value, String biz, long expiredTime) {
        return put(key, source, value, biz, false, expiredTime);
    }

    public boolean put(BitmapCacheKey key, String source, Bitmap value, String businessId, boolean jpg, long expiredTime) {
        return putDiskCache(key, source, value, businessId, jpg, expiredTime) | putMemCache(key, value, businessId);
    }

    public boolean put(BitmapCacheKey key, String source, Bitmap value, String businessId, boolean jpg, String encryptKey, int fileType, ImageInfo info, long expiredTime) {
        return putDiskCache(key, source, value, businessId, jpg, encryptKey, fileType, info, expiredTime) | putMemCache(key, value, businessId);
    }

    public boolean putDiskCache(BitmapCacheKey key, byte[] value, String businessId) {
        return putDiskCache(key, value, businessId, null, Long.MAX_VALUE);
    }

    public boolean putDiskCache(BitmapCacheKey key, byte[] value, String businessId, String encryptKey, long expiredTime) {
        if (getDiskCache() == null) {
            return false;
        }
        if (!TextUtils.isEmpty(this.b.putIfAbsent(key.complexCacheKey(), "0"))) {
            return false;
        }
        final BitmapCacheKey bitmapCacheKey = key;
        final byte[] bArr = value;
        final String str = businessId;
        final long j = expiredTime;
        final String str2 = encryptKey;
        this.mAsyncHandler.post(new Runnable() {
            public void run() {
                try {
                    BitmapCacheLoader.this.getDiskCache().saveData(bitmapCacheKey, bArr, str, j, str2);
                } catch (Exception e2) {
                    BitmapCacheLoader.logger.e(e2, "Key: %s, value: %s, save error", bitmapCacheKey, bArr);
                } finally {
                    BitmapCacheLoader.this.b.remove(bitmapCacheKey);
                }
            }
        });
        return true;
    }

    public ImageDiskCache getDiskCache() {
        return ImageDiskCache.get();
    }

    public MemoryCache getMemoryCache(String businessId) {
        return a(businessId);
    }

    /* access modifiers changed from: protected */
    public boolean isInQueue(String key) {
        return this.b.containsKey(key);
    }

    public void removeMemCache(BitmapCacheKey cacheKey, String businessId) {
        a(businessId).remove(cacheKey.complexCacheKey());
    }

    /* access modifiers changed from: private */
    public static void b(Exception e, String key, long size, String id, String biz, String exp) {
        DiskExpUtils.UC_MM_47(DiskExpUtils.parseException(e), key, true, size, id, biz, exp);
    }
}
