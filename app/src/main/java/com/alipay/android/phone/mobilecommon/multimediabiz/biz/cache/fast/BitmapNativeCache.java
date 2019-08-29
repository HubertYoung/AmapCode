package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.fast;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.BitmapReference;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.CacheProxy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.LruCacheProxy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.TQCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.util.TQCacheProxy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DeviceWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class BitmapNativeCache {
    private static final int MAX_LOCKS = 64;
    private static final String TAG = "BitmapNativeCache";
    private static final Logger logger = Logger.getLogger((String) TAG);
    private static boolean mUploadUnavailableReport = false;
    private ReentrantLock[] locks;
    private CacheProxy<String, BitmapInfo> mCache;
    private Set<BitmapReference> reusableSet = Collections.synchronizedSet(new HashSet());

    private class BitmapInfo {
        /* access modifiers changed from: private */
        public long b;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public int d;
        /* access modifiers changed from: private */
        public Config e;
        private int f;
        /* access modifiers changed from: private */
        public int g;
        public long lastAccessTime = System.currentTimeMillis();

        public BitmapInfo(long pointer, Bitmap bitmap) {
            this.b = pointer;
            this.c = bitmap.getWidth();
            this.d = bitmap.getHeight();
            this.e = bitmap.getConfig();
            this.f = bitmap.hashCode();
            this.g = bitmap.getRowBytes() * bitmap.getHeight();
        }

        public boolean exist(Bitmap bitmap) {
            return this.c == bitmap.getWidth() && this.d == bitmap.getHeight() && this.e == bitmap.getConfig() && this.f == bitmap.hashCode();
        }

        public boolean valid() {
            return this.c > 0 && this.d > 0 && this.e != null && this.b != 0;
        }

        public String toString() {
            return "BitmapInfo{pointer=" + this.b + ", width=" + this.c + ", height=" + this.d + ", config=" + this.e + ", needBytes=" + (this.c * this.d * BitmapNativeCache.this.sizeOfPixel(this.e)) + ", lastAccessTime=" + this.lastAccessTime + '}';
        }
    }

    private native void free(long j);

    private native void getBitmapData(long j, Bitmap bitmap);

    private native int getMemFree();

    private native int getMemTotal();

    private native long setBitmapData(Bitmap bitmap);

    public static BitmapNativeCache open(int byteCount, boolean useTQ) {
        if (byteCount > 0) {
            return new BitmapNativeCache(byteCount, useTQ);
        }
        throw new IllegalArgumentException("byteCount <= 0");
    }

    public void close() {
        cleanup();
    }

    private BitmapNativeCache(int byteCount, boolean useTQ) {
        AppUtils.loadLibraryOnce("AlipayBitmapNative");
        if (!useTQ) {
            this.mCache = createLruCache(byteCount);
        } else {
            this.mCache = createTQCache(byteCount);
        }
        this.locks = new ReentrantLock[64];
        for (int i = 0; i < 64; i++) {
            this.locks[i] = new ReentrantLock();
        }
    }

    private CacheProxy<String, BitmapInfo> createTQCache(int byteCount) {
        return new TQCacheProxy(new TQCache<String, BitmapInfo>(byteCount) {
            /* access modifiers changed from: protected */
            public int sizeOf(String key, BitmapInfo value) {
                return value.g;
            }

            /* access modifiers changed from: protected */
            public void entryRemoved(int queueIndex, boolean evicted, String key, BitmapInfo oldValue, BitmapInfo newValue) {
                if (newValue == null || !(oldValue == null || oldValue.b == newValue.b)) {
                    BitmapNativeCache.this.remove(oldValue);
                }
            }
        });
    }

    private CacheProxy<String, BitmapInfo> createLruCache(int byteCount) {
        return new LruCacheProxy(new LruCache<String, BitmapInfo>(byteCount) {
            /* access modifiers changed from: protected */
            public int sizeOf(String key, BitmapInfo value) {
                return value.g;
            }

            /* access modifiers changed from: protected */
            public void entryRemoved(boolean evicted, String key, BitmapInfo oldValue, BitmapInfo newValue) {
                if (newValue == null || !(oldValue == null || oldValue.b == newValue.b)) {
                    BitmapNativeCache.this.remove(oldValue);
                }
            }
        });
    }

    public Bitmap getBitmap(String key) {
        return getBitmap(key, null);
    }

    @TargetApi(19)
    public Bitmap getBitmap(String key, Bitmap reuse) {
        BitmapInfo info;
        if (key == null) {
            return null;
        }
        ReentrantLock lock = getLock(key);
        if (lock == null) {
            return null;
        }
        lock.lock();
        Bitmap bitmap = null;
        try {
            info = (BitmapInfo) this.mCache.get(key);
            if (info != null && info.valid()) {
                info.lastAccessTime = System.currentTimeMillis();
                Bitmap bm = getReusableBitmap(reuse, info.c, info.d, info.e);
                if (bm == null) {
                    bm = Bitmap.createBitmap(info.c, info.d, info.e);
                }
                if (VERSION.SDK_INT >= 19) {
                    bm.reconfigure(info.c, info.d, info.e);
                }
                if (bm.isMutable() && (Config.ARGB_8888 == info.e || Config.ARGB_4444 == info.e)) {
                    if (VERSION.SDK_INT >= 12) {
                        bm.setHasAlpha(true);
                    }
                    bm.eraseColor(0);
                }
                if (info.b != 0 && safeGetBitmapData(info, bm)) {
                    bitmap = bm;
                }
            }
            lock.unlock();
            return bitmap;
        } catch (Throwable throwable) {
            try {
                logger.e(throwable, "getBitmap error", new Object[0]);
                return null;
            } finally {
                lock.unlock();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0024, code lost:
        if (mUploadUnavailableReport != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0026, code lost:
        mUploadUnavailableReport = true;
        r8 = new java.io.File(new java.io.File(com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils.getApplicationContext().getCacheDir().getParentFile(), "lib"), "libAlipayBitmapNative.so");
        com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_BIZ_UNAVAILBLE(com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem.SUB_SO_FAIL, "-1", "loadSo", "-1", java.lang.String.valueOf(r8.length()), "", r8.getAbsolutePath());
        logger.d("uploadUnavailableReport loadSo fail, size: " + r8.length() + ", path: " + r8.getAbsolutePath(), new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean safeGetBitmapData(com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.fast.BitmapNativeCache.BitmapInfo r14, android.graphics.Bitmap r15) {
        /*
            r13 = this;
            r12 = 0
            r9 = 0
            long r0 = r14.b     // Catch:{ Throwable -> 0x000b }
            r13.getBitmapData(r0, r15)     // Catch:{ Throwable -> 0x000b }
            r9 = 1
        L_0x000a:
            return r9
        L_0x000b:
            r10 = move-exception
            boolean r0 = mUploadUnavailableReport
            if (r0 != 0) goto L_0x0019
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r0 = logger
            java.lang.String r1 = "safeGetBitmapData error"
            java.lang.Object[] r2 = new java.lang.Object[r12]
            r0.e(r10, r1, r2)
        L_0x0019:
            boolean r0 = r10 instanceof java.lang.UnsatisfiedLinkError
            if (r0 == 0) goto L_0x0022
            java.lang.String r0 = "AlipayBitmapNative"
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils.loadLibrary(r0)     // Catch:{ Throwable -> 0x0085 }
        L_0x0022:
            boolean r0 = mUploadUnavailableReport
            if (r0 != 0) goto L_0x000a
            r0 = 1
            mUploadUnavailableReport = r0
            java.io.File r7 = new java.io.File
            android.content.Context r0 = com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils.getApplicationContext()
            java.io.File r0 = r0.getCacheDir()
            java.io.File r0 = r0.getParentFile()
            java.lang.String r1 = "lib"
            r7.<init>(r0, r1)
            java.io.File r8 = new java.io.File
            java.lang.String r0 = "libAlipayBitmapNative.so"
            r8.<init>(r7, r0)
            java.lang.String r0 = "10_0"
            java.lang.String r1 = "-1"
            java.lang.String r2 = "loadSo"
            java.lang.String r3 = "-1"
            long r4 = r8.length()
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r5 = ""
            java.lang.String r6 = r8.getAbsolutePath()
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil.UC_MM_BIZ_UNAVAILBLE(r0, r1, r2, r3, r4, r5, r6)
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r0 = logger
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "uploadUnavailableReport loadSo fail, size: "
            r1.<init>(r2)
            long r2 = r8.length()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ", path: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r8.getAbsolutePath()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.Object[] r2 = new java.lang.Object[r12]
            r0.d(r1, r2)
            goto L_0x000a
        L_0x0085:
            r11 = move-exception
            boolean r0 = mUploadUnavailableReport
            if (r0 != 0) goto L_0x0022
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger r0 = logger
            java.lang.String r1 = "safeGetBitmapData try to loadLibrary error"
            java.lang.Object[] r2 = new java.lang.Object[r12]
            r0.e(r11, r1, r2)
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.fast.BitmapNativeCache.safeGetBitmapData(com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.fast.BitmapNativeCache$BitmapInfo, android.graphics.Bitmap):boolean");
    }

    public void putBitmap(String key, Bitmap bitmap) {
        if (key != null && bitmap != null) {
            ReentrantLock lock = getLock(key);
            lock.lock();
            try {
                BitmapInfo info = (BitmapInfo) this.mCache.get(key);
                if (info == null || !info.exist(bitmap)) {
                    long pointer = 0;
                    if (!bitmap.isRecycled()) {
                        pointer = setBitmapData(bitmap);
                    }
                    if (pointer != 0) {
                        this.mCache.put(key, new BitmapInfo(pointer, bitmap));
                    }
                    lock.unlock();
                    return;
                }
                lock.unlock();
            } catch (Exception e) {
                logger.e(e, "setBitmapData exception", new Object[0]);
            } catch (Throwable th) {
                lock.unlock();
                throw th;
            }
        }
    }

    public int getTotalByteCount() {
        return this.mCache.size();
    }

    public void cleanup() {
        this.mCache.evictAll();
    }

    public void trimToSize(int size) {
        if (this.mCache != null) {
            this.mCache.trimToSize(size);
            logger.d("trimToSize: " + size + ", pre: " + ((long) this.mCache.size()) + ", cur: " + this.mCache.size(), new Object[0]);
        }
    }

    public void knockOutExpired(long aliveTime) {
        logger.d("knockOutExpired aliveTime: " + aliveTime, new Object[0]);
        Map snapshot = this.mCache.snapshot();
        if (snapshot != null) {
            for (Entry entry : snapshot.entrySet()) {
                BitmapInfo info = (BitmapInfo) entry.getValue();
                if (info == null || System.currentTimeMillis() - info.lastAccessTime > aliveTime) {
                    logger.d("knockOutExpired key: " + ((String) entry.getKey()) + ", info: " + info, new Object[0]);
                    this.mCache.remove(entry.getKey());
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void remove(BitmapInfo data) {
        free(data.b);
        data.b = 0;
    }

    public void remove(String key) {
        if (!TextUtils.isEmpty(key)) {
            try {
                BitmapInfo data = (BitmapInfo) this.mCache.remove(key);
                if (data != null && data.b > 0) {
                    remove(data);
                }
            } catch (Exception e) {
                logger.d("remove exption key=" + key, new Object[0]);
            }
        }
    }

    private Bitmap getReusableBitmap(Bitmap old, int width, int height, Config config) {
        if (!DeviceWrapper.hasBitmapReuseablity()) {
            return null;
        }
        pushToPool(old);
        return popFromPool(width, height, config);
    }

    private boolean isReusableBitmap(Bitmap bitmap, int width, int height, Config config) {
        if (bitmap == null || !bitmap.isMutable()) {
            return false;
        }
        if (bitmap.getWidth() == width && bitmap.getHeight() == height && bitmap.getConfig() == config) {
            return true;
        }
        if (VERSION.SDK_INT >= 19) {
            return isReUsed(bitmap, width, height, config);
        }
        return false;
    }

    @TargetApi(19)
    private boolean isReUsed(Bitmap bitmap, int width, int height, Config config) {
        int byteNeed = width * height * sizeOfPixel(config);
        if (VERSION.SDK_INT < 19 || bitmap.getAllocationByteCount() < byteNeed) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public int sizeOfPixel(Config config) {
        if (config == Config.ARGB_8888) {
            return 4;
        }
        if (config == Config.RGB_565 || config == Config.ARGB_4444) {
            return 2;
        }
        if (config == Config.ALPHA_8) {
        }
        return 1;
    }

    private ReentrantLock getLock(String s) {
        return this.locks[Math.abs(s.hashCode()) % 64];
    }

    private void pushToPool(Bitmap bitmap) {
        synchronized (this) {
            if (bitmap != null) {
                if (bitmap.isMutable()) {
                    this.reusableSet.add(new BitmapReference(bitmap));
                }
            }
        }
    }

    private synchronized Bitmap popFromPool(int width, int height, Config config) {
        Bitmap bitmap;
        bitmap = null;
        Iterator iterator = this.reusableSet.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                break;
            }
            Bitmap item = iterator.next().get();
            if (item == null) {
                iterator.remove();
            } else if (isReusableBitmap(item, width, height, config)) {
                bitmap = item;
                iterator.remove();
                break;
            }
        }
        return bitmap;
    }

    public void debugInfo() {
        this.mCache.debugInfo();
    }

    public Collection<String> keys() {
        if (this.mCache != null) {
            return this.mCache.snapshot().keySet();
        }
        return null;
    }
}
