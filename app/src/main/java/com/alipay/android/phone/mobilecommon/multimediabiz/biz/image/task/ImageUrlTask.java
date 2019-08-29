package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.HttpTransListener;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.download.NetDownloader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.DecryptException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ParamChecker;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class ImageUrlTask extends ImageNetTask {
    private static final Logger a = Logger.getLogger((String) "ImageUrlTask");
    private NetDownloader b;

    public ImageUrlTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
        setTag("ImageUrlTask");
    }

    public Bitmap executeTask() {
        this.loadReq.notifyGifState(3, true, 0);
        this.loadReq.netPerf.id = this.loadReq.path;
        this.loadReq.netPerf.zoom = "url";
        this.loadReq.netPerf.biz = this.loadReq.options.getBizType();
        long start = System.currentTimeMillis();
        a.d("call image url task start, path: " + this.loadReq.path, new Object[0]);
        if (TextUtils.isEmpty(FileUtils.getMediaDir("im"))) {
            notifyError(this.loadReq, RETCODE.DOWNLOAD_FAILED, "base dir is empty", new IllegalStateException("base dir is empty"));
        } else {
            String savePath = genSavePath();
            this.b = createNetDownloader(savePath);
            ThumbnailsDownResp rsp = this.b.download(this.loadReq, (Bundle) null);
            if (this.loadReq.getTransportWay() == 3) {
                this.loadReq.netPerf.netMethod = 3;
            }
            this.loadReq.netPerf.netTime = System.currentTimeMillis() - start;
            this.loadReq.netPerf.retCode = rsp.getCode();
            a.d("call ImageUrlTask download, " + this.loadReq.path + ", rsp: " + rsp + ", used: " + (System.currentTimeMillis() - start), new Object[0]);
            if (rsp.isSuccess()) {
                File saveFile = new File(savePath);
                this.loadReq.netPerf.size = saveFile.length();
                this.loadReq.netPerf.fileType = ImageFileType.detectImageFileType(saveFile);
                this.loadReq.taskModel.setTotalSize(saveFile.length());
                try {
                    a(saveFile, rsp);
                    addImDbRecord(this.loadReq, savePath);
                    QueryCacheManager.getInstance().queryOriginalAndPut(this.loadReq.path);
                } catch (DecryptException e) {
                    notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
                }
            } else if (DjangoConstant.DJANGO_CANCEL == rsp.getCode()) {
                notifyCancel();
            } else if (RETCODE.CURRENT_LIMIT.value() == rsp.getCode()) {
                a.d("ImageUrlTask notifyLimitError rsp=" + rsp, new Object[0]);
                notifyLimitError();
            } else if (RETCODE.TIME_OUT.value() == rsp.getCode()) {
                a.d("ImageUrlTask notifyTimeoutError rsp=" + rsp, new Object[0]);
                notifyTimeoutError(rsp.getMsg());
            } else {
                notifyError(null);
            }
            if (!rsp.isSuccess()) {
                this.loadReq.notifyGifState(3, false, -1);
            }
            a.p("call image url task finish", new Object[0]);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public NetDownloader createNetDownloader(String savePath) {
        return new NetDownloader(this.loadReq, savePath, new HttpTransListener(this.loadReqSet));
    }

    /* access modifiers changed from: protected */
    public String genSavePath() {
        return ImageDiskCache.get().genPathByKey(this.loadReq.cacheKey.key);
    }

    private void a(File saveFile, ThumbnailsDownResp rsp) {
        removeTask();
        ParamChecker.pmdCheck(rsp);
        FalconFacade facade = FalconFacade.get();
        BitmapCacheLoader cacheLoader = getCacheLoader();
        a.d("dealWithResponse loadReqSet.size: " + this.loadReqSet.size(), new Object[0]);
        HashMap hashMap = new HashMap();
        ImageInfo info = ImageInfo.getImageInfo(saveFile.getAbsolutePath());
        Size size = new Size(info.correctWidth, info.correctHeight);
        boolean skipFirst = true;
        for (ImageLoadReq loadReq : this.loadReqSet) {
            loadReq.taskModel.setTotalSize(saveFile.length());
            if (isGifTask(loadReq, saveFile)) {
                loadGif(saveFile, loadReq, null);
                loadReq.notifyGifState(3, false, 0);
            } else {
                loadReq.notifyGifState(3, false, -1);
                DisplayImageOptions options = loadReq.options;
                Integer width = options.getWidth();
                Integer height = options.getHeight();
                int[] fitSize = getFitSize(size, width.intValue(), height.intValue());
                byte[] imageData = skipFirst ? null : (byte[]) hashMap.get(loadReq.cacheKey.complexCacheKey());
                Bitmap bitmap = skipFirst ? null : cacheLoader.getMemCache(loadReq.cacheKey, loadReq.options.getBusinessId());
                skipFirst = false;
                try {
                    if (!ImageUtils.checkBitmap(bitmap)) {
                        if (CutScaleType.SMART_CROP.equals(options.getCutScaleType())) {
                            long startTime = System.currentTimeMillis();
                            bitmap = FalconFactory.INS.getSmartCutProcessor().process(saveFile.getAbsolutePath(), loadReq);
                            loadReq.netPerf.decodeTime = System.currentTimeMillis() - startTime;
                        } else if (CutScaleType.AUTO_CUT_EXACTLY.equals(options.cutScaleType)) {
                            long startTime2 = System.currentTimeMillis();
                            bitmap = facade.cutImage(saveFile, width.intValue(), height.intValue(), ((float) Math.min(width.intValue(), height.intValue())) / ((float) Math.max(width.intValue(), height.intValue())));
                            loadReq.netPerf.decodeTime = System.currentTimeMillis() - startTime2;
                        } else {
                            long startTime3 = System.currentTimeMillis();
                            bitmap = facade.cutImageKeepRatio(saveFile, fitSize[0], fitSize[1]);
                            loadReq.netPerf.decodeTime = System.currentTimeMillis() - startTime3;
                        }
                        if (ImageUtils.checkBitmap(bitmap)) {
                            bitmap = processBitmap(options.getProcessor(), a(loadReq, options, fitSize, bitmap));
                            if (ImageUtils.checkBitmap(bitmap)) {
                                if (loadReq.options.isWithImageDataInCallback() || !loadReq.options.isCacheInMem()) {
                                    imageData = ImageUtils.bitmap2Bytes(bitmap, isForceSaveJpg(saveFile));
                                    if (imageData != null) {
                                        hashMap.put(loadReq.cacheKey.complexCacheKey(), imageData);
                                        cacheLoader.putDiskCache(loadReq.cacheKey, imageData, options.getBusinessId(), null, loadReq.getExpiredTime());
                                    }
                                } else {
                                    cacheLoader.put(loadReq.cacheKey, saveFile.getAbsolutePath(), bitmap, options.getBusinessId(), isForceSaveJpg(saveFile) && !options.shouldProcess(), loadReq.getExpiredTime());
                                }
                            }
                        }
                    } else {
                        a.d("dealWithResponse from cache, bitmap: " + bitmap + ", saveFile: " + saveFile + ", fitSize: " + Arrays.toString(fitSize), new Object[0]);
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
    }

    private Bitmap a(ImageLoadReq loadReq, DisplayImageOptions options, int[] fitSize, Bitmap bitmap) {
        if (isNeedZoom(loadReq) && isNeedZoom(bitmap, options.getWidth(), options.getHeight())) {
            bitmap = ImageUtils.zoomBitmap(bitmap, fitSize[0], fitSize[1]);
        } else if (CutScaleType.SCALE_AUTO_LIMIT.equals(options.getCutScaleType())) {
            a.p("SCALE_AUTO_LIMIT bitmap.w: " + bitmap.getWidth() + ", bitmap.h: " + bitmap.getHeight() + ", ow: " + options.getWidth() + ", oh: " + options.getHeight(), new Object[0]);
            if (isNeedZoom(bitmap, options.getWidth(), options.getHeight())) {
                bitmap = ImageUtils.zoomBitmap(bitmap, options.getWidth().intValue(), options.getHeight().intValue());
            }
        }
        if (!ImageUtils.checkBitmap(bitmap)) {
            a.i("handleZoomBitmap bitmap: " + bitmap + ", req: " + loadReq, new Object[0]);
        }
        return bitmap;
    }

    public void cancel() {
        super.cancel();
        if (this.b != null) {
            this.b.cancel();
        }
    }
}
