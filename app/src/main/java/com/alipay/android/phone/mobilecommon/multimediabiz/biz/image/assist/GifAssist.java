package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GifAssist {
    private static final HashSet<String> a = new LinkedHashSet();
    private static final ReadWriteLock b = new ReentrantReadWriteLock();

    public static boolean isGif(BitmapCacheKey key) {
        boolean z;
        b.readLock().lock();
        if (key != null) {
            try {
                if (a.contains(key.key)) {
                    z = true;
                    b.readLock().unlock();
                    return z;
                }
            } catch (Throwable th) {
                b.readLock().unlock();
                throw th;
            }
        }
        z = false;
        b.readLock().unlock();
        return z;
    }

    public static void recordGif(BitmapCacheKey key) {
        b.writeLock().lock();
        if (key != null) {
            try {
                if (!a.contains(key.key)) {
                    a.add(key.key);
                    Logger.D("GifAssist", "recordGif path: " + key.key, new Object[0]);
                }
            } catch (Throwable th) {
                b.writeLock().unlock();
                throw th;
            }
        }
        b.writeLock().unlock();
    }
}
