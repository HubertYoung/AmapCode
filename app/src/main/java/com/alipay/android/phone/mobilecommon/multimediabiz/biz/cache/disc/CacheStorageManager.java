package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheInfo;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheSource;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.OriginalBitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.diskcache.DiskCache;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.multimedia.img.ImageInfo;
import java.io.File;

public class CacheStorageManager {
    private static final Logger a = Logger.getLogger((String) "CacheStorage");

    public static APCacheInfo saveIntoCache(APCacheSource source) {
        APCacheInfo info;
        a.d("saveIntoCache enter source: " + source, new Object[0]);
        if (source.rawData == null) {
            throw new IllegalArgumentException("source.rawData is null");
        }
        switch (source.type) {
            case 0:
                info = a(source);
                break;
            case 1:
                info = b(source);
                break;
            default:
                throw new IllegalArgumentException("unknown type: " + source.type);
        }
        a.d("saveIntoCache leave source: " + source + ", info: " + info, new Object[0]);
        return info;
    }

    public static String queryCacheInfoPath(String identifier) {
        String result = identifier;
        if (!TextUtils.isEmpty(identifier) && identifier.startsWith("mm:")) {
            result = CacheContext.get().getDiskCache().genPathByKey(identifier);
        }
        a.d("queryCacheInfoPath identifier: " + identifier + ", result: " + result, new Object[0]);
        return result;
    }

    private static APCacheInfo a(APCacheSource source) {
        DiskCache cache = CacheContext.get().getDiskCache();
        String key = "mm:" + MD5Util.getMD5String(source.rawData);
        String path = TextUtils.isEmpty(source.savePath) ? cache.genPathByKey(key) : source.savePath;
        if ((!a(source.rawData, path) || !TextUtils.isEmpty(source.savePath)) && !FileUtils.safeCopyToFile(source.rawData, new File(path))) {
            return null;
        }
        cache.save(key, 4, 2048, source.businessId, Long.MAX_VALUE);
        APCacheInfo info = new APCacheInfo();
        info.length = (long) source.rawData.length;
        info.localId = key;
        info.path = path;
        info.extra = new Bundle();
        info.extra.putInt("type", source.type);
        return info;
    }

    private static APCacheInfo b(APCacheSource source) {
        ImageDiskCache cache = ImageDiskCache.get();
        String key = "mm:" + MD5Util.getMD5String(source.rawData);
        String path = TextUtils.isEmpty(source.savePath) ? cache.genPathByKey(key) : source.savePath;
        if ((!a(source.rawData, path) || !TextUtils.isEmpty(source.savePath)) && !FileUtils.safeCopyToFile(source.rawData, new File(path))) {
            return null;
        }
        cache.savePath(new OriginalBitmapCacheKey(key, false), path, 128, source.businessId);
        APCacheInfo info = new APCacheInfo();
        info.length = (long) source.rawData.length;
        info.localId = key;
        info.path = path;
        ImageInfo imageInfo = ImageInfo.getImageInfo(source.rawData);
        info.extra = new Bundle();
        info.extra.putInt("width", imageInfo.width);
        info.extra.putInt("height", imageInfo.height);
        info.extra.putInt("type", source.type);
        info.extra.putInt(APCacheInfo.EXTRA_ROTATION, imageInfo.rotation);
        return info;
    }

    private static boolean a(byte[] data, String path) {
        boolean match = false;
        boolean exists = FileUtils.checkFile(path);
        if (exists) {
            try {
                String dataMd5 = MD5Util.getMD5String(data);
                match = !TextUtils.isEmpty(dataMd5) && dataMd5.equalsIgnoreCase(MD5Util.getFileMD5String(new File(path)));
            } catch (Exception e) {
                a.w("checkExistsFile error, data: " + data + ", path: " + path, new Object[0]);
            }
        }
        a.d("checkExistsFile file exists:" + exists + " and md5 match: " + match, new Object[0]);
        return match;
    }
}
