package com.alipay.diskcache.naming;

import android.os.Looper;
import android.text.TextUtils;
import com.alipay.diskcache.utils.LogHelper;
import com.alipay.diskcache.utils.StaticResources;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MultiDirFileGenerator implements FileCacheGenerator {
    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 36;
    private static final String TAG = "MultiDirFileGenerator";
    public static long asyncTimeout = 500;
    public static boolean enableLockSync = false;
    public static boolean useAsyncCheck = true;
    private final Random random = new Random();

    public File generate(String dir, String key) {
        boolean isMainThread = true;
        String hex = Integer.toHexString(randomInt((long) key.hashCode()));
        if (hex.length() < 2) {
            StringBuffer sb = new StringBuffer();
            sb.append("0").append(hex);
            hex = sb.toString();
        }
        final File rootDir = new File(dir + File.separator + hex);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            isMainThread = false;
        }
        if (!useAsyncCheck || !enableLockSync || !isMainThread || StaticResources.sExecutors == null) {
            try {
                Callable callable = new Callable<Void>() {
                    public Void call() {
                        if (!rootDir.exists()) {
                            synchronized (MultiDirFileGenerator.class) {
                                if (!rootDir.exists() && !rootDir.mkdirs()) {
                                    LogHelper.e("FileCacheGenerator", "mkdirs failure!");
                                }
                            }
                        }
                        return null;
                    }
                };
                if (!useAsyncCheck || !isMainThread || StaticResources.sExecutors == null) {
                    callable.call();
                    return new File(rootDir, getCacheFileNameByKey(key));
                }
                StaticResources.sExecutors.submit(callable).get(asyncTimeout, TimeUnit.MILLISECONDS);
                return new File(rootDir, getCacheFileNameByKey(key));
            } catch (Throwable t) {
                LogHelper.w(TAG, "warning maybe anr occur~~~" + t);
            }
        } else {
            final CountDownLatch waitLatch = new CountDownLatch(1);
            StaticResources.sExecutors.submit(new Callable<Void>() {
                public Void call() {
                    if (!rootDir.exists()) {
                        synchronized (MultiDirFileGenerator.class) {
                            if (!rootDir.exists() && !rootDir.mkdirs()) {
                                LogHelper.e("FileCacheGenerator", "mkdirs failure!");
                            }
                        }
                    }
                    waitLatch.countDown();
                    return null;
                }
            });
            waitLatch.await(asyncTimeout, TimeUnit.MILLISECONDS);
            return new File(rootDir, getCacheFileNameByKey(key));
        }
    }

    public int randomInt(long seed) {
        int abs;
        synchronized (this.random) {
            this.random.setSeed(seed);
            abs = Math.abs(this.random.nextInt() % 255);
        }
        return abs;
    }

    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(data);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            LogHelper.e(TAG, "NoSuchAlgorithmException", e);
            return hash;
        }
    }

    public static String getCacheFileNameByKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            return new BigInteger(getMD5(key.getBytes())).abs().toString(36);
        }
        return null;
    }
}
