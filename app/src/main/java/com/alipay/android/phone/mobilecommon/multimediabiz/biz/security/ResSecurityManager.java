package com.alipay.android.phone.mobilecommon.multimediabiz.biz.security;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.OriginalBitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemoryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.diskcache.DiskCache;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpResponse;

public class ResSecurityManager {
    public static final int RES_TYPE_FILE = 3;
    public static final int RES_TYPE_IMAGE = 1;
    public static final int RES_TYPE_VIDEO = 2;
    private static final Logger logger = Logger.getLogger((String) "ResSecurityManager");

    public static void handleSyncIllegalRes(String id) {
        logger.d("handleSyncIllegalRes id: " + id, new Object[0]);
        if (!TextUtils.isEmpty(id)) {
            FileCacheModel cacheModel = CacheContext.get().getDiskCache().get(id);
            logger.d("handleSyncIllegalRes cacheModel: " + cacheModel, new Object[0]);
            if (cacheModel == null) {
                handleNoneInfo(id);
            } else if (cacheModel.type == 1) {
                handleIllegalImage(id);
            } else {
                handleIllegalVideo(id);
            }
        }
    }

    private static void handleNoneInfo(String id) {
        try {
            String path = CacheContext.get().getDiskCache().genPathByKey(id);
            if (FileUtils.checkFile(path)) {
                FileUtils.safeCopyToFile(getSourceFile(3), new File(path));
            }
        } catch (IOException e) {
            logger.e(e, "handleNoneInfo id: " + id, new Object[0]);
        }
    }

    public static void interceptBaseDownResp(BaseDownResp rsp, int resType) {
        if (ConfigManager.getInstance().getCommonConfigItem().securityConf.isEnableHttpHandle() && rsp != null && isIllegalCode(rsp.getCode())) {
            logger.d("interceptBaseDownResp rsp: " + rsp + ", resType: " + resType, new Object[0]);
            rsp.setCode(DjangoConstant.DJANGO_OK);
            rsp.setResp(mockHttpResponse(rsp, resType));
        }
    }

    public static void interceptNBnetDownResp(NBNetDownloadResponse rsp, NBNetDownloadRequest request, int resType) {
        if (ConfigManager.getInstance().getCommonConfigItem().securityConf.isEnableNBnetHandle() && rsp != null && isIllegalNBnetCode(rsp.getErrorCode())) {
            logger.d("interceptNBnetDownResp rsp: " + rsp + ", request: " + request + ", resType: " + resType, new Object[0]);
            rsp.setErrorCode(DjangoConstant.DJANGO_OK);
            rsp.setDataLength(replaceIllegalRes(request, resType));
        }
    }

    private static long replaceIllegalRes(NBNetDownloadRequest request, int resType) {
        File targetFile = new File(request.getSavePath());
        if (2 == resType) {
            try {
                FileUtils.safeCopyToFile(getSourceFile(resType), targetFile);
            } catch (IOException e) {
                logger.e(e, "replaceIllegalRes error, request: " + request + ", resType: " + resType, new Object[0]);
            }
        } else {
            FileUtils.safeCopyToFile(getSourceFile(resType), targetFile);
        }
        return targetFile.length();
    }

    private static boolean isIllegalNBnetCode(int code) {
        return ConfigManager.getInstance().getCommonConfigItem().securityConf.illegalNBnetStatusCode == code;
    }

    private static HttpResponse mockHttpResponse(BaseDownResp rsp, int resType) {
        return new LegalHttpResponse(rsp.getResp(), getSourceFile(resType));
    }

    private static boolean isIllegalCode(int code) {
        return ConfigManager.getInstance().getCommonConfigItem().securityConf.illegalStatusCode == code;
    }

    private static void handleIllegalImage(String id) {
        DiskCache diskCache = CacheContext.get().getDiskCache();
        FileCacheModel model = diskCache.get(id);
        logger.d("handleIllegalImage id: " + id + ", cache: " + model, new Object[0]);
        if (model != null) {
            diskCache.remove(id);
            fillDefaultImage(id, model.businessId);
            removeMemCache(id, model.businessId);
        }
    }

    private static void removeMemCache(String id, String businessId) {
        MemoryCache[] memoryCacheArr = {CacheContext.get().getMemCache(businessId), CacheContext.get().getSoftReferenceMemCache(), CacheContext.get().getMemCache()};
        for (int i = 0; i < 3; i++) {
            MemoryCache cache = memoryCacheArr[i];
            if (!(cache == null || cache.keys() == null || cache.keys().isEmpty())) {
                for (String key : cache.keys()) {
                    if (key.startsWith(id)) {
                        cache.remove(key);
                    }
                }
            }
        }
    }

    public static void fillDefaultImage(String id, String businessId) {
        BitmapCacheKey cacheKey = new OriginalBitmapCacheKey(id, false);
        String path = ImageDiskCache.get().genPathByKey(cacheKey.complexCacheKey());
        logger.d("fillDefaultImage id: " + id + ", businessId: " + businessId + ", path: " + path, new Object[0]);
        try {
            FileUtils.safeCopyToFile(getSourceFile(1), new File(path));
            ImageDiskCache.get().savePath(cacheKey, path, 128, businessId);
        } catch (IOException e) {
            logger.e(e, "fillDefaultImage error, id: " + id + ", businessId: " + businessId, new Object[0]);
        }
    }

    private static File getSourceFile(int resType) {
        File file = new File(AppUtils.getApplicationContext().getFilesDir(), "safe.raw");
        if (!file.exists()) {
            InputStream in = null;
            try {
                in = AppUtils.getResources().getAssets().open("security/safe.png");
                FileUtils.safeCopyToFile(in, file);
            } catch (Throwable t) {
                logger.e(t, "getSourceFile error, resType: " + resType, new Object[0]);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        return file;
    }

    private static void handleIllegalVideo(String id) {
        FileCacheModel videoInfo = VideoFileManager.getInstance().getVideoInfo(id);
        logger.d("handleIllegalVideo id: " + videoInfo + ", videoInfo: " + videoInfo, new Object[0]);
        if (videoInfo != null && FileUtils.checkFile(videoInfo.path)) {
            try {
                File dst = new File(videoInfo.path);
                if (FileUtils.safeCopyToFile(getSourceFile(2), dst)) {
                    videoInfo.fileSize = dst.length();
                    videoInfo.tag |= 4096;
                    VideoFileManager.getInstance().getDiskCache().update(videoInfo);
                }
            } catch (IOException e) {
                logger.e(e, "fillDefaultImage error, id: " + id, new Object[0]);
            }
        }
    }
}
