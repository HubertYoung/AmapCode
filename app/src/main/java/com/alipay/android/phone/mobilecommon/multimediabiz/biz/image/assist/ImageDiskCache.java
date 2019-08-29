package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.OriginalBitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.diskcache.DiskCache;
import com.alipay.diskcache.DiskCache.QueryFilter;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageDiskCache {
    public static final int TAG_BIG_PICTURE = 512;
    public static final int TAG_ORIGINAL_PICTURE = 128;
    public static final int TAG_THUMB_PICTURE = 256;
    private static ImageDiskCache b;
    private Logger a = Logger.getLogger((String) "ImageDiskCache");
    private final Map<String, String> c = new HashMap();
    private final AtomicBoolean d = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public DiskCache e = CacheContext.get().getDiskCache();

    private ImageDiskCache() {
    }

    public static ImageDiskCache get() {
        if (b == null) {
            synchronized (ImageDiskCache.class) {
                try {
                    if (b == null) {
                        b = new ImageDiskCache();
                    }
                }
            }
        }
        return b;
    }

    public String getPath(String key) {
        String path = this.e.genPathByKey(key);
        if (!FileUtils.checkFile(path)) {
            String aliasKey = CacheContext.get().queryAliasKey(key);
            if (!TextUtils.isEmpty(aliasKey)) {
                path = this.e.genPathByKey(aliasKey);
                key = aliasKey;
            }
        }
        if (FileUtils.checkFile(path)) {
            return a(key, this.e.getPath(key));
        }
        return null;
    }

    @Nullable
    private String a(String key, String path) {
        if (path != null && CacheUtils.isDiskCacheExpired(key, new File(path))) {
            FileUtils.delete(path);
            path = null;
            String pathKey = BitmapCacheKey.extractPath(key);
            if (PathUtils.isLocalFile(pathKey)) {
                update(pathKey, null);
            }
        }
        return path;
    }

    public FileCacheModel get(String key, QueryFilter queryFilter) {
        return this.e.get(key, queryFilter);
    }

    private boolean a(String key, String path, int tag, String extra, String biz, long expiredTime) {
        if (!SimpleConfigProvider.get().getIOConfig().isEnableAsyncSaveDB()) {
            return this.e.save(key, path, 1, tag, extra, biz, expiredTime);
        }
        final String str = key;
        final String str2 = path;
        final int i = tag;
        final String str3 = extra;
        final String str4 = biz;
        final long j = expiredTime;
        TaskScheduleManager.get().commonExecutor().execute(new Runnable() {
            final /* synthetic */ int c = 1;

            public void run() {
                ImageDiskCache.this.e.save(str, str2, this.c, i, str3, str4, j);
            }
        });
        return true;
    }

    public boolean update(String key, String aliasKey) {
        this.a.d("update key: " + key + ", aliasKey: " + aliasKey, new Object[0]);
        if (key == null) {
            return false;
        }
        if (!TextUtils.isEmpty(aliasKey)) {
            String pre = this.c.remove(key);
            if (pre != null) {
                this.c.remove(pre);
            }
            String pre2 = this.c.remove(aliasKey);
            if (pre2 != null) {
                this.c.remove(pre2);
            }
            this.c.put(key, aliasKey);
            this.c.put(aliasKey, key);
        } else {
            String pre3 = this.c.remove(key);
            if (pre3 != null) {
                this.c.remove(pre3);
            }
        }
        return this.e.update(key, aliasKey);
    }

    public boolean appendAliasKey(String key, String aliasKey) {
        this.a.d("appendAliasKey key: " + key + ", aliasKey: " + aliasKey, new Object[0]);
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(aliasKey)) {
            return false;
        }
        this.c.put(aliasKey, key);
        return this.e.appendAliasKey(key, aliasKey);
    }

    public boolean remove(String key) {
        return this.e.remove(key);
    }

    public String genPathByKey(String uniKey) {
        return this.e.genPathByKey(uniKey);
    }

    public String queryAliasKey(String key) {
        String aliasKey;
        if (key == null) {
            return null;
        }
        String aliasKey2 = this.c.get(key);
        if (aliasKey2 != null || AppUtils.inMainLooper()) {
            return aliasKey2;
        }
        synchronized (this.d) {
            try {
                if (!this.d.get()) {
                    this.d.set(true);
                    initAliasKeyCache();
                }
                aliasKey = this.c.get(key);
            }
        }
        return aliasKey;
    }

    /* access modifiers changed from: protected */
    public void initAliasKeyCache() {
        String[] splitMultiAliasKeys;
        long start = System.currentTimeMillis();
        List models = this.e.getAlias(1);
        this.a.d("init aliasKey cost: " + (System.currentTimeMillis() - start) + ", size: " + models.size(), new Object[0]);
        for (FileCacheModel model : models) {
            if (!(model == null || model.aliasKey == null || model.aliasKey.equals(model.cacheKey))) {
                this.c.put(model.cacheKey, model.aliasKey);
                this.c.put(model.aliasKey, model.cacheKey);
            }
        }
        long start2 = System.currentTimeMillis();
        List more = this.e.getMultiAlias(1);
        this.a.d("init multiAliasKey cost: " + (System.currentTimeMillis() - start2) + ", size: " + models.size(), new Object[0]);
        for (FileCacheModel model2 : more) {
            for (String key : model2.splitMultiAliasKeys()) {
                if (!TextUtils.isEmpty(key) && !"null".equalsIgnoreCase(key)) {
                    this.c.put(key, model2.cacheKey);
                }
            }
        }
    }

    public Bitmap getBitmap(BitmapCacheKey key) {
        Bitmap bitmap = null;
        try {
            return getBitmap(key, null);
        } catch (Exception e2) {
            this.a.d("getBitmap exception...", new Object[0]);
            return bitmap;
        }
    }

    public Bitmap getBitmap(BitmapCacheKey key, String encryptKey) {
        File cacheFile = getCacheFile(key);
        if (!TextUtils.isEmpty(encryptKey)) {
            if (cacheFile != null) {
                return ImageUtils.decodeBitmapByFalcon(AESUtils.decryptFile(encryptKey, cacheFile));
            }
            return null;
        } else if (cacheFile != null) {
            return ImageUtils.decodeBitmapByFalcon(cacheFile);
        } else {
            return null;
        }
    }

    public File getCacheFile(BitmapCacheKey key) {
        String path = b(key);
        File cacheFile = null;
        if (FileUtils.checkFile(path)) {
            this.a.d("getCacheFile fast: " + path + ", key: " + key.complexCacheKey(), new Object[0]);
            cacheFile = new File(path);
        } else {
            FileCacheModel cacheModel = a(key);
            if (cacheModel != null && FileUtils.checkFile(cacheModel.path)) {
                cacheFile = new File(cacheModel.path);
            }
        }
        this.a.d("getCacheFile, key: " + key + ", cacheFile: " + cacheFile, new Object[0]);
        return cacheFile;
    }

    @Nullable
    private FileCacheModel a(final BitmapCacheKey key) {
        if (key.aliasKey != null) {
            return this.e.get(key.aliasKey, new QueryFilter() {
                public FileCacheModel parse(List<FileCacheModel> list) {
                    for (FileCacheModel m : list) {
                        if (!TextUtils.isEmpty(m.extra)) {
                            BitmapCacheKey k = BitmapCacheKey.create(m.extra, key.aliasKey);
                            if (k != null && (k.complexCacheKey().equals(key.complexCacheKey()) || k.complexCacheKey().equals(key.aliasComplexKey()))) {
                                return m;
                            }
                        }
                    }
                    return null;
                }
            });
        }
        return null;
    }

    private String b(BitmapCacheKey key) {
        String path = getPath(key.complexCacheKey());
        if (FileUtils.checkFile(path) || key.aliasComplexKey() == null) {
            return path;
        }
        return getPath(BitmapCacheKey.create(key, queryAliasKey(key.aliasKey)).complexCacheKey());
    }

    public boolean saveBitmap(BitmapCacheKey key, Bitmap bitmap, boolean jpg, String biz, String encryptKey, long expiredTime) {
        boolean ret;
        byte[] data = ImageUtils.bitmap2Bytes(bitmap, jpg);
        if (TextUtils.isEmpty(encryptKey)) {
            return saveData(key, data, biz, expiredTime, null);
        }
        String path = this.e.genPathByKey(key.complexCacheKey());
        try {
            ret = AESUtils.encryptFile(encryptKey, data, path);
            if (ret) {
                ret = a(key.key, path, key.tag, key.genJsonExtra(), biz, expiredTime);
            }
        } catch (Exception e2) {
            ret = false;
        }
        this.a.d("saveData, path: " + path + ", ret: " + ret + ", key: " + key + ", biz: " + biz, new Object[0]);
        return ret;
    }

    public boolean saveBitmap(BitmapCacheKey key, Bitmap bitmap, boolean jpg, String biz, long expiredTime) {
        return saveData(key, ImageUtils.bitmap2Bytes(bitmap, jpg), biz, expiredTime, null);
    }

    public boolean saveData(BitmapCacheKey key, byte[] data, String biz) {
        return saveData(key, data, biz, Long.MAX_VALUE, null);
    }

    public boolean saveData(BitmapCacheKey key, byte[] data, String biz, long expiredTime, String encryptKey) {
        boolean ret;
        String path = this.e.genPathByKey(key.complexCacheKey());
        try {
            if (TextUtils.isEmpty(encryptKey)) {
                ret = FileUtils.safeCopyToFile(data, new File(path));
            } else {
                ret = AESUtils.encryptFile(encryptKey, data, path);
            }
            if (ret) {
                ret = a(key.key, path, key.tag, key.genJsonExtra(), biz, expiredTime);
            }
        } catch (Exception e2) {
            ret = false;
        }
        this.a.d("saveData, path: " + path + ", ret: " + ret + ", key: " + key + ", biz: " + biz, new Object[0]);
        return ret;
    }

    public boolean savePath(BitmapCacheKey key, String path, int tag, String biz) {
        return savePath(key, path, tag, biz, Long.MAX_VALUE);
    }

    public boolean savePath(BitmapCacheKey key, String path, int tag, String biz, long expiredTime) {
        boolean ret = a(key.key, path, tag, key.genJsonExtra(), biz, expiredTime);
        this.a.d("savePath, path: " + path + ", ret: " + ret + ", key: " + key + ", tag: " + tag + ", biz: " + biz + ", expiredTime: " + expiredTime, new Object[0]);
        return ret;
    }

    public String saveIntoCache(byte[] bytes, String business) {
        this.a.d("saveIntoCache bytes: " + bytes + ", business: " + business, new Object[0]);
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String localId = "mm:" + MD5Util.getMD5String(bytes);
        OriginalBitmapCacheKey key = new OriginalBitmapCacheKey(localId, false);
        String path = genPathByKey(localId);
        try {
            FileUtils.safeCopyToFile(bytes, new File(path));
            if (!savePath(key, path, key.tag, business, Long.MAX_VALUE)) {
                return null;
            }
            return localId;
        } catch (IOException e2) {
            return null;
        }
    }

    public static boolean couldSaveIntoCacheDirectly(String path, boolean hasProcessor, boolean needGif) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        int formatType = ImageFileType.detectImageFileType(path);
        if (hasProcessor) {
            return false;
        }
        if (formatType == 0 || formatType == 1 || (needGif && formatType == 2)) {
            return true;
        }
        return false;
    }
}
