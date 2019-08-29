package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.OriginalBitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.DjangoOriginalDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.NBNetDjangoOriginalDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.DecryptException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CodeConverter;
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
import java.util.concurrent.CountDownLatch;

public class ImageDjangoOriginalTask extends ImageNetTask {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "ImgDjgOriTask");
    private DjangoOriginalDownloader b;
    private NBNetDjangoOriginalDownloader c;
    /* access modifiers changed from: private */
    public int d = -1;
    /* access modifiers changed from: private */
    public CountDownLatch e;

    public ImageDjangoOriginalTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
        setTag("ImgDjgOriTask");
    }

    public Bitmap executeTask() {
        String imagePath;
        a.d("executeTask req: " + this.loadReq, new Object[0]);
        this.loadReq.notifyGifState(3, true, 0);
        String savePath = ImageDiskCache.get().genPathByKey(this.loadReq.cacheKey.key);
        long netStart = System.currentTimeMillis();
        if (this.loadReq.isEncryptRequest() || this.loadReq.getTransportWay() == 2 || PathUtils.checkAftId(this.loadReq.path)) {
            imagePath = a(savePath);
        } else {
            imagePath = b(savePath);
        }
        this.loadReq.netPerf.netTime = System.currentTimeMillis() - netStart;
        this.loadReq.netPerf.retCode = this.d;
        if (this.d != 0 || !FileUtils.checkFile(imagePath)) {
            this.loadReq.notifyGifState(3, false, -1);
            notifyError(new Exception("Download fail, " + this.loadReq.path));
        } else {
            try {
                if (c(imagePath)) {
                    addImDbRecord(this.loadReq, imagePath);
                    QueryCacheManager.getInstance().queryOriginalAndPut(this.loadReq.path);
                } else {
                    this.loadReq.notifyGifState(3, false, -1);
                }
            } catch (DecryptException e2) {
                notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e2);
            }
        }
        return null;
    }

    private String a(String savePath) {
        this.loadReq.netPerf.netMethod = 2;
        this.c = new NBNetDjangoOriginalDownloader(this.loadReq, savePath, new NBNetDownloadCallback() {
            public void onCancled(NBNetDownloadRequest request) {
                ImageDjangoOriginalTask.a.d("downloadOriginalFromNBNet onCancled id=: " + request.getFileId(), new Object[0]);
                ImageDjangoOriginalTask.this.d = 5;
                ImageDjangoOriginalTask.this.loadReq.netPerf.retCode = ImageDjangoOriginalTask.this.d;
            }

            public void onDownloadStart(NBNetDownloadRequest request) {
                ImageDjangoOriginalTask.a.d("NBNetDownloadCallback onDownloadStart id: " + ImageDjangoOriginalTask.this.loadReq.path, new Object[0]);
            }

            public void onDownloadProgress(NBNetDownloadRequest request, int progress, long received, long total) {
                ImageDjangoOriginalTask.this.a(progress, received, total);
            }

            public void onDownloadProgress(NBNetDownloadRequest request, int progress, long received, long total, File cacheFile) {
                ImageDjangoOriginalTask.this.a(progress, received, total);
            }

            public void onDownloadError(NBNetDownloadRequest request, NBNetDownloadResponse response) {
                ImageDjangoOriginalTask.this.a(response.getErrorCode(), response.getErrorMsg());
            }

            public void onDownloadFinished(NBNetDownloadRequest request, NBNetDownloadResponse response) {
                ImageDjangoOriginalTask.this.d = 0;
                ImageDjangoOriginalTask.a.d("onDownloadFinished taskState: " + ImageDjangoOriginalTask.this.d, new Object[0]);
            }
        });
        return this.c.download(this.loadReq, (Bundle) null);
    }

    private String b(String savePath) {
        int i;
        LoadImageFromNetworkPerf loadImageFromNetworkPerf = this.loadReq.netPerf;
        if (b()) {
            i = 3;
        } else {
            i = 1;
        }
        loadImageFromNetworkPerf.netMethod = i;
        this.e = new CountDownLatch(1);
        this.b = new DjangoOriginalDownloader(this.loadReq, savePath, new APFileDownCallback() {
            public void onDownloadStart(APMultimediaTaskModel taskInfo) {
                ImageDjangoOriginalTask.a.d("onDownloadStart id: " + ImageDjangoOriginalTask.this.loadReq.path, new Object[0]);
            }

            public void onDownloadProgress(APMultimediaTaskModel taskInfo, int progress, long hasDownSize, long total) {
                ImageDjangoOriginalTask.this.a(progress, hasDownSize, total);
            }

            public void onDownloadBatchProgress(APMultimediaTaskModel taskInfo, int progress, int curIndex, long hasDownSize, long total) {
                ImageDjangoOriginalTask.a.p("APFileDownCallback onDownloadBatchProgress", new Object[0]);
            }

            public void onDownloadFinished(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
                ImageDjangoOriginalTask.this.d = 0;
                ImageDjangoOriginalTask.this.e.countDown();
                ImageDjangoOriginalTask.a.d("APFileDownCallback onDownloadFinished taskState: " + ImageDjangoOriginalTask.this.d, new Object[0]);
            }

            public void onDownloadError(APMultimediaTaskModel taskInfo, APFileDownloadRsp rsp) {
                if (rsp.getRetCode() == 14) {
                    ImageDjangoOriginalTask.this.a(RETCODE.TIME_OUT.value(), rsp.getMsg());
                } else {
                    ImageDjangoOriginalTask.this.a(rsp.getRetCode(), rsp.getMsg());
                }
            }
        });
        String imagePath = this.b.download(this.loadReq, (Bundle) null);
        this.e.await();
        a.d("call taskState: " + this.d + ", imagePath: " + imagePath, new Object[0]);
        return imagePath;
    }

    /* access modifiers changed from: private */
    public void a(int code, String msg) {
        removeTask();
        if (this.e != null) {
            this.e.countDown();
        }
        this.d = code;
        Exception e2 = new Exception("download failed");
        RETCODE retCode = CodeConverter.toImageRetCode(code);
        if (TextUtils.isEmpty(msg)) {
            msg = "download original image failed";
        }
        if (this.d == RETCODE.CURRENT_LIMIT.value()) {
            retCode = RETCODE.CURRENT_LIMIT;
            msg = ConfigConstants.MULTIMEDIA_NET_LIMIT_MSG;
            a.d("ImageDjangoOriginalTask APFileDownCallback onDownloadError " + msg, new Object[0]);
        }
        for (ImageLoadReq req : this.loadReqSet) {
            if (req.downLoadCallback != null) {
                notifyError(req, retCode, msg, e2);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(int progress, long hasDownSize, long total) {
        for (ImageLoadReq req : this.loadReqSet) {
            if (req.downLoadCallback != null) {
                req.downLoadCallback.onProcess(req.source, progress);
            }
        }
        if (progress < 5 || progress > 95) {
            a.d("onDownloadProgress progress: " + progress + ", hasDownSize: " + hasDownSize + ", total: " + total, new Object[0]);
        } else {
            a.p("onDownloadProgress progress: " + progress + ", hasDownSize: " + hasDownSize + ", total: " + total, new Object[0]);
        }
    }

    private boolean c(String imagePath) {
        File file = new File(imagePath);
        byte[] fileData = null;
        if (this.loadReq.isEncryptRequest()) {
            long start = System.currentTimeMillis();
            fileData = AESUtils.decryptFile(this.loadReq.options.fileKey, file);
            this.loadReq.netPerf.decryptTime = System.currentTimeMillis() - start;
        }
        boolean isEncrypt = this.loadReq.isEncryptRequest() && fileData != null;
        this.loadReq.netPerf.size = file.length();
        this.loadReq.netPerf.fileType = isEncrypt ? ImageFileType.detectImageDataType(fileData) : ImageFileType.detectImageFileType(file);
        this.loadReq.taskModel.setTotalSize(file.length());
        removeTask();
        if (isOnlyWithData()) {
            a.d("dealWithDownloadSuccess onlyWithData, size: " + this.loadReqSet.size(), new Object[0]);
            for (ImageLoadReq req : this.loadReqSet) {
                req.taskModel.setTotalSize(file.length());
                this.loadReq.downloadRsp.loadFrom = 3;
                if (req.options.isWithImageDataInCallback()) {
                    notifySuccessWithImageData(req, this.loadReq.isEncryptRequest() ? fileData : FileUtils.file2Bytes(imagePath));
                }
            }
            return true;
        }
        a.d("dealWithDownloadSuccess notOnlyWithData, size: " + this.loadReqSet.size(), new Object[0]);
        ImageInfo info = isEncrypt ? ImageInfo.getImageInfo(fileData) : ImageInfo.getImageInfo(imagePath);
        int[] fitSize = getFitSize(new Size(info.correctWidth, info.correctHeight), Integer.MAX_VALUE, Integer.MAX_VALUE);
        a.d("dealWithDownloadSuccess fitSize: " + Arrays.toString(fitSize), new Object[0]);
        try {
            long decodeStart = System.currentTimeMillis();
            Bitmap bitmap = isEncrypt ? FalconFacade.get().cutImageKeepRatio(fileData, fitSize[0], fitSize[1]) : FalconFacade.get().cutImageKeepRatio(new File(imagePath), fitSize[0], fitSize[1]);
            this.loadReq.netPerf.decodeTime = System.currentTimeMillis() - decodeStart;
            if (!ImageUtils.checkBitmap(bitmap)) {
                return false;
            }
            for (ImageLoadReq req2 : this.loadReqSet) {
                req2.taskModel.setTotalSize(file.length());
                this.loadReq.downloadRsp.loadFrom = 3;
                if (!(isEncrypt ? isGifTask(this.loadReq, fileData) : isGifTask(this.loadReq, file)) || isEncrypt) {
                    this.loadReq.notifyGifState(3, false, -1);
                    if (req2.options.isWithImageDataInCallback()) {
                        notifySuccessWithImageData(req2, isEncrypt ? fileData : FileUtils.file2Bytes(imagePath));
                    } else {
                        display(bitmap, req2, (ViewWrapper) null);
                    }
                } else {
                    loadGif(file, this.loadReq, null);
                    this.loadReq.notifyGifState(3, false, 0);
                }
            }
            OriginalBitmapCacheKey cacheKey = new OriginalBitmapCacheKey(this.loadReq.path);
            int fileType = isEncrypt ? ImageFileType.detectImageDataType(fileData) : ImageFileType.detectImageFileType(new File(imagePath));
            boolean isJpg = isEncrypt ? ImageFileType.isJPEG(fileData) : ImageFileType.isJPEG(new File(imagePath));
            if (this.loadReq.options.isCacheInMem()) {
                getCacheLoader().put(cacheKey, imagePath, bitmap, this.options.getBusinessId(), isJpg, this.loadReq.options.fileKey, fileType, info, this.loadReq.getExpiredTime());
            } else {
                getCacheLoader().putDiskCache(cacheKey, imagePath, bitmap, this.options.getBusinessId(), isJpg, this.loadReq.options.fileKey, fileType, info, this.loadReq.getExpiredTime());
            }
            checkAndAddResIndexUniqueKey();
            return true;
        } catch (Exception e2) {
            a.e(e2, "dealWithDownloadSuccess but occur exception", new Object[0]);
            notifyError(new Exception("process error, " + this.loadReq.path));
            return false;
        }
    }

    public void cancel() {
        super.cancel();
        if (this.b != null) {
            this.b.cancel();
        }
        if (this.c != null) {
            this.c.cancel();
        }
    }

    private boolean b() {
        return this.loadReq != null && this.loadReq.getTransportWay() == 3;
    }
}
