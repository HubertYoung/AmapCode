package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ZoomHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.DjangoDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.DjangoDownloader.DownloadListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.HttpTransListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.NBNetDjangoDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.NetDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.FalconUtilsBridge;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.processor.RegionCropProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.progressive.ProgressiveStrategy;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APMultimediaTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.DecryptException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadCallback;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadRequest;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadResponse;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ImageDjangoTask extends ImageNetTask {
    private static final Logger a = Logger.getLogger((String) "ImageDjangoTask");
    private NetDownloader b;

    public ImageDjangoTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
        setTag("ImageDjangoTask");
    }

    public Bitmap executeTask() {
        a.d("executeTask req: " + this.loadReq, new Object[0]);
        return a(exeuteInit());
    }

    @Nullable
    private Bitmap a(String savePath) {
        if (this.loadReq.isEncryptRequest() || this.loadReq.getTransportWay() == 2 || PathUtils.checkAftId(this.loadReq.path)) {
            try {
                downloadFromNBNet(savePath);
            } catch (DecryptException e) {
                notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
            }
        } else if (c()) {
            c(savePath);
        } else {
            b(savePath);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public String exeuteInit() {
        this.loadReq.notifyGifState(2, true, 0);
        String savePath = ImageDiskCache.get().genPathByKey(this.loadReq.cacheKey.complexCacheKey());
        this.bProgressive = b();
        return savePath;
    }

    private void a() {
        if (this.progressiveStrategy == null) {
            this.progressiveStrategy = new ProgressiveStrategy();
        }
    }

    /* access modifiers changed from: protected */
    public void downloadFromNBNet(String savePath) {
        this.loadReq.netPerf.netMethod = 2;
        final long netStart = System.currentTimeMillis();
        NBNetDjangoDownloader downloader = new NBNetDjangoDownloader(savePath, this.loadReq, new NBNetDownloadCallback() {
            public void onCancled(NBNetDownloadRequest request) {
            }

            public void onDownloadStart(NBNetDownloadRequest request) {
            }

            public void onDownloadProgress(NBNetDownloadRequest request, int progress, long received, long total) {
                ImageDjangoTask.this.a(received, total, progress);
            }

            public void onDownloadProgress(NBNetDownloadRequest request, int progress, long received, long total, File cacheFile) {
                if (!ImageDjangoTask.this.loadReq.isEncryptRequest()) {
                    ImageDjangoTask.this.progressiveDisplay(progress, received, total, cacheFile, netStart);
                }
                ImageDjangoTask.this.a(received, total, progress);
            }

            public void onDownloadError(NBNetDownloadRequest request, NBNetDownloadResponse response) {
            }

            public void onDownloadFinished(NBNetDownloadRequest request, NBNetDownloadResponse response) {
            }
        });
        a();
        ThumbnailsDownResp rsp = downloader.download(this.loadReq, (Bundle) null);
        this.loadReq.netPerf.retCode = rsp.getCode();
        if (rsp.isSuccess()) {
            dealWithResponse(new File(rsp.getSavePath()), rsp);
        } else if (rsp.getCode() == RETCODE.CURRENT_LIMIT.value()) {
            a.d("downloadFromNBNet notifyLimitError rsp=" + rsp, new Object[0]);
            notifyLimitError();
        } else if (rsp.getCode() == RETCODE.TIME_OUT.value()) {
            a.d("downloadFromNBNet notifyTimeoutError rsp=" + rsp, new Object[0]);
            notifyTimeoutError(rsp.getMsg());
        } else {
            a.d("downloadFromNBNet notifyError rsp=" + rsp, new Object[0]);
            notifyError(null);
        }
        if (!rsp.isSuccess()) {
            this.loadReq.notifyGifState(3, false, 0);
        }
    }

    /* access modifiers changed from: private */
    public void a(long cSize, long tSize, int progress) {
        this.loadReq.taskModel.setTotalSize(tSize);
        this.loadReq.taskModel.setCurrentSize(cSize);
        APMultimediaTaskManager.getInstance(this.mContext).updateTaskRecord(this.loadReq.taskModel);
        notifyProgress(progress);
    }

    private void b(String savePath) {
        this.loadReq.netPerf.netMethod = 1;
        final long netStart = System.currentTimeMillis();
        DjangoDownloader downloader = new DjangoDownloader(savePath, this.loadReq, new DownloadListener() {
            public void onDownloadStart(DjangoDownloader downloader, ImageLoadReq req) {
            }

            public void onDownloadProgress(DjangoDownloader downloader, ImageLoadReq req, long read, long total, int percent, File cacheFile) {
                if (!ImageDjangoTask.this.loadReq.isEncryptRequest()) {
                    ImageDjangoTask.this.progressiveDisplay(percent, read, total, cacheFile, netStart);
                }
                ImageDjangoTask.this.notifyProgress(percent);
            }

            public void onDownloadFinish(DjangoDownloader downloader, ImageLoadReq req, ThumbnailsDownResp rsp) {
                ImageDjangoTask.this.notifySuccess();
            }

            public void onDownloadError(DjangoDownloader downloader, ImageLoadReq req, Exception e) {
            }
        });
        a();
        ThumbnailsDownResp rsp = getDownloadRsp(downloader);
        this.loadReq.netPerf.retCode = rsp.getCode();
        if (rsp.isSuccess()) {
            try {
                dealWithResponse(new File(rsp.getSavePath()), rsp);
            } catch (DecryptException e) {
                notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
            }
        } else if (rsp.getCode() == RETCODE.CURRENT_LIMIT.value()) {
            a.d("downloadFromDefault notifyLimitError rsp=" + rsp, new Object[0]);
            notifyLimitError();
        } else if (rsp.getCode() == RETCODE.TIME_OUT.value()) {
            a.d("downloadFromDefault notifyTimeoutError rsp=" + rsp, new Object[0]);
            notifyTimeoutError(rsp.getMsg());
        } else {
            a.d("downloadFromDefault notifyError rsp=" + rsp, new Object[0]);
            notifyError(null);
        }
    }

    /* access modifiers changed from: protected */
    public ThumbnailsDownResp getDownloadRsp(DjangoDownloader downloader) {
        return downloader.download(this.loadReq, (Bundle) null);
    }

    /* access modifiers changed from: protected */
    public void dealWithResponse(File saveFile, ThumbnailsDownResp rsp) {
        ImageInfo info;
        byte[] fileData = null;
        if (this.loadReq.isEncryptRequest()) {
            long start = System.currentTimeMillis();
            fileData = AESUtils.decryptFile(this.loadReq.options.fileKey, saveFile);
            this.loadReq.netPerf.decryptTime = System.currentTimeMillis() - start;
        }
        this.loadReq.netPerf.size = saveFile.length();
        this.loadReq.taskModel.setTotalSize(saveFile.length());
        removeTask();
        FalconFacade facade = FalconFacade.get();
        BitmapCacheLoader cacheLoader = getCacheLoader();
        if (!this.loadReq.isEncryptRequest() || fileData == null) {
            this.loadReq.netPerf.fileType = ImageFileType.detectImageFileType(saveFile);
            info = ImageInfo.getImageInfo(saveFile.getAbsolutePath());
        } else {
            this.loadReq.netPerf.fileType = ImageFileType.detectImageDataType(fileData);
            info = ImageInfo.getImageInfo(fileData);
        }
        int fileType = this.loadReq.netPerf.fileType;
        Size size = new Size(info.correctWidth, info.correctHeight);
        Map tempBuffer = new HashMap();
        boolean skipFirst = true;
        boolean isNeedSaveGifCache = false;
        for (ImageLoadReq loadReq : this.loadReqSet) {
            loadReq.taskModel.setTotalSize(saveFile.length());
            if ((!loadReq.isEncryptRequest() || fileData == null) ? isGifTask(loadReq, saveFile) : isGifTask(loadReq, fileData)) {
                isNeedSaveGifCache = true;
                loadGif(saveFile, loadReq, null);
                loadReq.notifyGifState(3, false, 0);
            } else {
                loadReq.notifyGifState(3, false, -1);
                DisplayImageOptions options = loadReq.options;
                int[] fitSize = getFitSize(size, options.getWidth().intValue(), options.getHeight().intValue());
                a.d("dealWithResponse fitSize: " + Arrays.toString(fitSize) + ", cacheKey: " + loadReq.cacheKey + ";progressive=" + loadReq.bProgressive, new Object[0]);
                byte[] imageData = skipFirst ? null : (byte[]) tempBuffer.get(loadReq.cacheKey.complexCacheKey());
                Bitmap bitmap = skipFirst ? null : cacheLoader.getMemCache(loadReq.cacheKey, options.getBusinessId());
                skipFirst = false;
                try {
                    if (!ImageUtils.checkBitmap(bitmap) || loadReq.bProgressive) {
                        long decodeStartTime = System.currentTimeMillis();
                        Object[] results = a(facade, loadReq, fitSize, saveFile, fileData, info);
                        bitmap = (Bitmap) results[0];
                        loadReq.netPerf.decodeTime = System.currentTimeMillis() - decodeStartTime;
                        if (options.getProcessor() != null) {
                            bitmap = options.getProcessor().process(loadReq.taskModel, bitmap);
                        } else if (isNeedZoom(loadReq)) {
                            if (isNeedZoom(bitmap, options.getWidth(), options.getHeight())) {
                                bitmap = ImageUtils.zoomBitmap(bitmap, fitSize[0], fitSize[1]);
                            }
                        }
                        if (ImageUtils.checkBitmap(bitmap)) {
                            boolean hasSavedDiskCache = rsp.getExtra() != null && rsp.getExtra().getBoolean("saveDisk", false) && !((Boolean) results[1]).booleanValue();
                            if (loadReq.options.isWithImageDataInCallback()) {
                                imageData = a(hasSavedDiskCache, bitmap, saveFile, fileData, tempBuffer);
                            } else {
                                loadReq.bProgressive = false;
                                removeProgressiveVal(loadReq.cacheKey);
                                if (loadReq.options.isCacheInMem()) {
                                    cacheLoader.putMemCache(loadReq.cacheKey, bitmap, options.getBusinessId());
                                }
                            }
                            if (!hasSavedDiskCache) {
                                a.d("dealWithResponse saveDisk: false, cacheKey: " + loadReq.cacheKey, new Object[0]);
                                if (imageData != null) {
                                    cacheLoader.putDiskCache(loadReq.cacheKey, imageData, options.getBusinessId(), loadReq.options.fileKey, loadReq.getExpiredTime());
                                } else {
                                    cacheLoader.putDiskCache(loadReq.cacheKey, saveFile.getAbsolutePath(), bitmap, options.getBusinessId(), !options.shouldProcess() && ((!loadReq.isEncryptRequest() || fileData == null) ? isForceSaveJpg(saveFile) : isForceSaveJpg(fileData)), loadReq.options.fileKey, fileType, info, loadReq.getExpiredTime());
                                }
                                FileUtils.delete(saveFile);
                            }
                        }
                    }
                    if (bitmap != null) {
                        a.d("dealWithResponse bitmap[w: " + bitmap.getWidth() + ", h: " + bitmap.getHeight() + "], key: " + loadReq.cacheKey + ";progressive=" + loadReq.bProgressive, new Object[0]);
                    }
                    loadReq.downloadRsp.loadFrom = 3;
                    if (imageData == null || !loadReq.options.isWithImageDataInCallback()) {
                        display(bitmap, loadReq, (ViewWrapper) null);
                    } else {
                        notifySuccessWithImageData(loadReq, imageData);
                    }
                    checkAndAddResIndexUniqueKey();
                } catch (Exception e) {
                    notifyError(loadReq, RETCODE.DOWNLOAD_FAILED, getExceptionInfo(e), e);
                }
            }
        }
        if (isNeedSaveGifCache && c()) {
            cacheLoader.putDiskCacheByPath(this.loadReq.cacheKey, saveFile.getAbsolutePath(), this.loadReq.cacheKey.tag, this.options.getBusinessId(), this.loadReq.getExpiredTime());
        }
    }

    private byte[] a(boolean hasSavedDiskCache, Bitmap bitmap, File saveFile, byte[] fileData, Map<String, byte[]> tempBuffer) {
        byte[] imageData;
        if (!this.loadReq.isEncryptRequest() || fileData == null) {
            if (hasSavedDiskCache) {
                imageData = FileUtils.file2Bytes(saveFile);
            } else {
                imageData = ImageUtils.bitmap2Bytes(bitmap, isForceSaveJpg(saveFile));
            }
        } else if (hasSavedDiskCache) {
            imageData = fileData;
        } else {
            imageData = ImageUtils.bitmap2Bytes(bitmap, isForceSaveJpg(fileData));
        }
        if (imageData != null) {
            tempBuffer.put(this.loadReq.cacheKey.complexCacheKey(), imageData);
        }
        return imageData;
    }

    private boolean b() {
        return ZoomHelper.imageProgressiveSupport(this.loadReq.options);
    }

    private static Object[] a(FalconFacade facade, ImageLoadReq req, int[] fitSize, File imageFile, byte[] imageData, ImageInfo info) {
        Bitmap bitmap;
        Bitmap bitmap2;
        CutScaleType scaleType = req.options.getCutScaleType();
        boolean degrade = false;
        boolean needCrop = scaleType.isRegionCrop() || scaleType.isSmartCrop() || scaleType == CutScaleType.AUTO_CUT_EXACTLY || scaleType == CutScaleType.CENTER_CROP;
        int[] targetSize = new int[2];
        Integer width = req.options.getWidth();
        Integer height = req.options.getHeight();
        if (req.options.srcSize == null || req.isEncryptRequest()) {
            targetSize[0] = width.intValue();
            targetSize[1] = height.intValue();
        } else if (needCrop) {
            FalconUtilsBridge.calcultDesWidthHeight(req.options.srcSize.getWidth(), req.options.srcSize.getHeight(), Math.max(width.intValue(), height.intValue()), targetSize);
        }
        a.d("decodeBitmap calc targetSize: " + Arrays.toString(targetSize) + ", from " + req.options.srcSize + ", width: " + width + ", height: " + height, new Object[0]);
        if (!req.isEncryptRequest() || imageData == null) {
            if (!needCrop || a(targetSize, info)) {
                bitmap = facade.cutImageKeepRatio(imageFile, fitSize[0], fitSize[1]);
            } else {
                degrade = true;
                if (scaleType.isSmartCrop()) {
                    bitmap2 = FalconFactory.INS.getSmartCutProcessor().process(imageFile.getAbsolutePath(), req);
                } else {
                    bitmap2 = RegionCropProcessor.processRegionCrop(req, imageFile, scaleType);
                }
                a.d("decodeBitmap degrade to use local crop, fitSize: " + Arrays.toString(fitSize) + "，src: " + info + ", file: " + imageFile + ", req: " + req.cacheKey + "， targetBitmap: " + ImageInfo.getImageInfo(bitmap, 0), new Object[0]);
            }
        } else if (!needCrop || a(targetSize, info)) {
            bitmap = facade.cutImageKeepRatio(imageData, fitSize[0], fitSize[1]);
        } else {
            degrade = true;
            if (scaleType.isSmartCrop()) {
                bitmap = FalconFactory.INS.getSmartCutProcessor().process(imageData, req);
            } else {
                bitmap = RegionCropProcessor.processRegionCrop(req, imageData, scaleType);
            }
            a.d("decodeBitmap degrade to use local crop, fitSize: " + Arrays.toString(fitSize) + "，src: " + info + ", imageData: " + imageData + ", req: " + req.cacheKey + "， targetBitmap: " + ImageInfo.getImageInfo(bitmap, 0), new Object[0]);
        }
        return new Object[]{bitmap, Boolean.valueOf(degrade)};
    }

    private static boolean a(int[] target, ImageInfo info) {
        DjangoConf conf = ConfigManager.getInstance().getCommonConfigItem().djangoConf;
        if (target[0] > conf.maxOssRequestWidth || target[1] > conf.maxOssRequestHeight || Math.max(info.width, info.height) > conf.maxOssTargetSide || info.width * info.height > conf.maxOssTargetArea) {
            return false;
        }
        if (Math.abs(target[0] - info.correctWidth) < 10 && Math.abs(target[1] - info.correctHeight) < 10) {
            return true;
        }
        if (Math.abs(((((float) target[0]) * 1.0f) / ((float) target[1])) - ((((float) info.correctWidth) * 1.0f) / ((float) info.correctHeight))) >= 0.1f || info.correctWidth >= target[0] || info.correctHeight >= target[1]) {
            return false;
        }
        return true;
    }

    public void cancel() {
        super.cancel();
        if (this.b != null) {
            this.b.cancel();
        }
    }

    private void c(String savePath) {
        this.loadReq.netPerf.netMethod = 3;
        long start = System.currentTimeMillis();
        this.b = new NetDownloader(this.loadReq, savePath, new HttpTransListener(this.loadReqSet));
        ThumbnailsDownResp rsp = this.b.download(this.loadReq, (Bundle) null);
        this.loadReq.netPerf.netTime = System.currentTimeMillis() - start;
        if (rsp.isSuccess()) {
            try {
                dealWithResponse(new File(savePath), rsp);
            } catch (DecryptException e) {
                a.d("ImageDjangoTask", "downloadFromMdn DecryptException error");
                notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
            }
        } else if (rsp.getCode() == RETCODE.CURRENT_LIMIT.value()) {
            a.d("downloadFromMdn notifyLimitError rsp=" + rsp, new Object[0]);
            notifyLimitError();
        } else if (rsp.getCode() == RETCODE.TIME_OUT.value()) {
            a.d("downloadFromMdn notifyTimeoutError rsp=" + rsp, new Object[0]);
            notifyTimeoutError(rsp.getMsg());
        } else {
            a.d("downloadFromMdn notifyError rsp=" + rsp, new Object[0]);
            notifyError(null);
        }
        this.loadReq.netPerf.retCode = rsp == null ? -1 : rsp.getCode();
        a.d("downloadFromMdn notifyError rsp=" + rsp, new Object[0]);
    }

    private boolean c() {
        return this.loadReq != null && this.loadReq.getTransportWay() == 3;
    }
}
