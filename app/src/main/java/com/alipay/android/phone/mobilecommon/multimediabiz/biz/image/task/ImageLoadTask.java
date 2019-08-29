package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.annotation.Nullable;
import android.view.View;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.MCaller;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.ReusableBitmapDrawable;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.GifDrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDisplayUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableOwner;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.processor.RegionCropProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.progressive.ProgressiveMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.unilink.ImageUniLinkLocalSmartCutTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.unilink.ImageUniLinkLocalTask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.DecryptException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeOptions.MaxLenMode;
import com.alipay.multimedia.img.decode.DecodeResult;
import com.alipay.multimedia.img.decode.ImageDecoder;
import com.alipay.multimedia.img.utils.Exif;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

public class ImageLoadTask extends ImageTask {
    private static final Logger a = Logger.getLogger((String) "ImageLoadTask");

    public ImageLoadTask(ImageLoadReq loadReq) {
        super(loadReq, null);
        viewAssistant.setViewTag(loadReq.getTargetImageView(), loadReq.cacheKey);
        if (ProgressiveMgr.getInstance().isExistKey(loadReq.cacheKey.complexCacheKey())) {
            a.p("ImageLoadTask from memcache key=" + loadReq.cacheKey.complexCacheKey(), new Object[0]);
        } else {
            displayDefaultDrawable();
        }
    }

    public Object call() {
        long start = System.currentTimeMillis();
        a.d("load start...." + this.loadReq.cacheKey + ", view: " + this.loadReq.getTargetView(), new Object[0]);
        this.loadReq.notifyGifState(1, true, 0);
        if (checkTask()) {
            this.loadReq.notifyGifState(1, false, -1);
            return null;
        }
        Logger.TIME("ImageLoadTask call checkTask costTime: " + (System.currentTimeMillis() - start) + ", " + this.loadReq.path, System.currentTimeMillis() - start, new Object[0]);
        BitmapCacheLoader cacheLoader = getCacheLoader();
        long start2 = System.currentTimeMillis();
        Bitmap bitmap = null;
        byte[] imageData = null;
        boolean isEncrypt = this.loadReq.isEncryptRequest();
        if (this.loadReq.data != null && this.loadReq.data.length > 0) {
            ByteArrayInputStream in = new ByteArrayInputStream(this.loadReq.data);
            bitmap = ImageUtils.decodeBitmap(in);
            IOUtils.closeQuietly((InputStream) in);
        } else if (this.loadReq.options.isWithImageDataInCallback()) {
            File cacheFile = ImageDiskCache.get().getCacheFile(this.loadReq.cacheKey);
            if (isEncrypt) {
                try {
                    imageData = AESUtils.decryptFile(this.loadReq.options.fileKey, cacheFile);
                } catch (DecryptException e) {
                    notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
                    return null;
                }
            } else {
                imageData = FileUtils.file2Bytes(cacheFile);
            }
        } else if (!isEncrypt && isGifTask(this.loadReq, ImageDiskCache.get().getCacheFile(this.loadReq.cacheKey))) {
            a(ImageDiskCache.get().getCacheFile(this.loadReq.cacheKey));
            this.loadReq.notifyGifState(1, false, 0);
            return null;
        } else if (isGifTask(this.loadReq, ImageDiskCache.get().genPathByKey(this.loadReq.path))) {
            a(new File(ImageDiskCache.get().genPathByKey(this.loadReq.path)));
            this.loadReq.notifyGifState(1, false, 0);
            return null;
        } else if (a()) {
            bitmap = a(PathUtils.extractPath(this.loadReq.thumbPath));
            if (this.loadReq.options.isCacheInMem()) {
                cacheLoader.putMemCache(this.loadReq.cacheKey, bitmap, this.loadReq.options.getBusinessId());
            }
        } else if (b()) {
            try {
                bitmap = a(this.loadReq.imageId);
                if (this.loadReq.options.isCacheInMem()) {
                    cacheLoader.putMemCache(this.loadReq.cacheKey, bitmap, this.loadReq.options.getBusinessId());
                }
            } catch (Throwable e2) {
                a.d("decodeImageIdThumbnail exp: " + e2.toString(), new Object[0]);
            }
        } else {
            try {
                bitmap = ImageDiskCache.get().getBitmap(this.loadReq.cacheKey, this.loadReq.options.fileKey);
                if (this.loadReq.options.isCacheInMem()) {
                    cacheLoader.putMemCache(this.loadReq.cacheKey, bitmap, this.loadReq.options.getBusinessId());
                }
            } catch (DecryptException e3) {
                notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e3);
                return null;
            }
        }
        Logger.TIME("ImageLoadTask call getDiskCache costTime: " + (System.currentTimeMillis() - start2) + ", " + this.loadReq.path, System.currentTimeMillis() - start2, new Object[0]);
        long start3 = System.currentTimeMillis();
        a.d("check disk cache bitmap: " + bitmap + ", cacheKey: " + this.loadReq.cacheKey, new Object[0]);
        if (a(imageData) || displayBitmap(start3, bitmap)) {
            if (PathUtils.isPreloadNeedReport(this.loadReq.options.businessId, this.loadReq.path)) {
                UCLogUtil.UC_MM_48("0", this.loadReq.path, "im");
            }
            if (!PathUtils.checkIsResourcePreDownload(this.loadReq.options.businessId)) {
                CacheHitManager.getInstance().imageCacheHit();
            }
            return null;
        } else if (checkImageViewReused()) {
            notifyReuse();
            return null;
        } else {
            checkAndStartLocalTask(bitmap);
            Logger.TIME("ImageLoadTask call ImageLocalTask costTime: " + (System.currentTimeMillis() - start3) + ", " + this.loadReq.path, System.currentTimeMillis() - start3, new Object[0]);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void checkAndStartLocalTask(Bitmap bitmap) {
        if (!ImageUtils.checkBitmap(bitmap)) {
            startLocalTask();
        }
    }

    /* access modifiers changed from: protected */
    public void startLocalTask() {
        if (this.loadReq.options.getCutScaleType().isSmartCrop() && ConfigManager.getInstance().supportLocalSmartCrop()) {
            ImageTaskEngine.get().submit((ImageLocalSmartCutTask) new ImageUniLinkLocalSmartCutTask(this.loadReq, this.viewWrapper));
        } else if (this.loadReq.sourceType == 3) {
            ImageTaskEngine.get().submit(new ImageBase64Task(this.loadReq, this.viewWrapper));
        } else {
            ImageTaskEngine.get().submit((ImageLocalTask) new ImageUniLinkLocalTask(this.loadReq, this.viewWrapper));
        }
        this.loadReq.notifyGifState(1, false, -1);
    }

    private boolean a(byte[] imageData) {
        if (imageData == null) {
            return false;
        }
        this.loadReq.downloadRsp.loadFrom = 1;
        notifySuccessWithImageData(this.loadReq, imageData);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean displayBitmap(long start, Bitmap bitmap) {
        a.p("displayBitmap  bitmap=" + bitmap + " cacheKey: " + this.loadReq.cacheKey, new Object[0]);
        if (!ImageUtils.checkBitmap(bitmap)) {
            return false;
        }
        this.loadReq.downloadRsp.loadFrom = 1;
        display(bitmap, this.loadReq, this.viewWrapper);
        Logger.TIME("ImageLoadTask call display costTime: " + (System.currentTimeMillis() - start) + ", " + this.loadReq.path, System.currentTimeMillis() - start, new Object[0]);
        a.p("loadFrom diskCache " + this.loadReq.path + " cacheKey: " + this.loadReq.cacheKey, new Object[0]);
        return true;
    }

    /* access modifiers changed from: protected */
    public void displayDefaultDrawable() {
        Drawable d = this.options.getImageOnLoading();
        if (d instanceof ReusableBitmapDrawable) {
            d = new BitmapDrawable(AppUtils.getResources(), ((ReusableBitmapDrawable) d).getBitmap());
        }
        if (d != null) {
            if (!(d instanceof BitmapDrawable)) {
                ConstantState state = d.getConstantState();
                if (state != null) {
                    d = state.newDrawable();
                }
            }
            if (this.options.getDisplayer() == null || !MCaller.IMAGE_WORKER.equalsIgnoreCase(this.options.getCaller())) {
                new ImageDisplaySilentTask(d, this.loadReq, this.viewWrapper).syncRunTask();
            }
        } else if (this.options.isSetNullDefaultDrawable()) {
            ImageDisplayUtils.display(d, this.viewWrapper);
        }
    }

    private void a(File gifFile) {
        boolean ignoreAutoCheck;
        View view = this.viewWrapper.getTargetView();
        if (this.loadReq.getLoadOptions() == null || !this.loadReq.getLoadOptions().ignoreGifAutoStart) {
            ignoreAutoCheck = false;
        } else {
            ignoreAutoCheck = true;
        }
        if (view == null && this.loadReq.getTargetView() == null && !ignoreAutoCheck) {
            notifySuccess();
            return;
        }
        String cacheKey = getGifCacheKey(view);
        GifDrawableImpl gifDrawable = (GifDrawableImpl) GifDrawableCache.getInstance().get(cacheKey);
        if (gifDrawable == null) {
            ImageInfo info = ImageInfo.getImageInfo(gifFile.getAbsolutePath());
            if (ignoreAutoCheck) {
                gifDrawable = new GifDrawableOwner(this.mContext, gifFile.getAbsolutePath(), info.correctWidth, info.correctHeight, null);
            } else {
                gifDrawable = new GifDrawableImpl(this.mContext, gifFile.getAbsolutePath(), info.correctWidth, info.correctHeight, null);
            }
            GifDrawableCache.getInstance().put(cacheKey, gifDrawable);
        } else {
            a.d("loadtask loadGif from memcache path=" + this.loadReq.path, new Object[0]);
        }
        gifDrawable.bindView(this.viewWrapper.getTargetView());
        display((Drawable) gifDrawable, this.loadReq, this.viewWrapper);
        this.loadReq.notifyGifController(gifDrawable);
    }

    private boolean a() {
        return ConfigManager.getInstance().getCommonConfigItem().useThumbnai() && FileUtils.checkFile(PathUtils.extractPath(this.loadReq.thumbPath));
    }

    private boolean b() {
        return ConfigManager.getInstance().getCommonConfigItem().useThumbnaiId() && this.loadReq.imageId > 0;
    }

    private Bitmap a(String thumbPath) {
        Bitmap bitmap = null;
        Integer width = this.options.getWidth();
        Integer height = this.options.getHeight();
        long start = System.currentTimeMillis();
        ImageInfo imageInfo = ImageInfo.getImageInfo(thumbPath);
        int[] fitSize = getFitSize(new Size(imageInfo.correctWidth, imageInfo.correctHeight), width.intValue(), height.intValue());
        a.d("decodeImageThumbnail fitSize: " + Arrays.toString(fitSize) + ";path=" + this.loadReq.path + ";thumbpath=" + thumbPath, new Object[0]);
        FalconFacade facade = FalconFacade.get();
        File sourceFile = new File(thumbPath);
        if (CutScaleType.CENTER_CROP.equals(this.options.getCutScaleType())) {
            try {
                bitmap = facade.cutImage(sourceFile, width.intValue(), height.intValue(), this.options.getScale().floatValue());
                a.p("decodeImageThumbnail cutImage, width: " + bitmap.getWidth() + ", height: " + bitmap.getHeight() + ", req: " + this.loadReq.cacheKey, new Object[0]);
            } catch (Exception e) {
                a.e(e, "decodeImageThumbnail err, info: " + this.loadReq, new Object[0]);
            }
        } else if (CutScaleType.AUTO_CUT_EXACTLY.equals(this.options.getCutScaleType())) {
            try {
                int maxLen = Math.max(width.intValue(), height.intValue());
                int minLen = Math.min(width.intValue(), height.intValue());
                bitmap = facade.cutImage(sourceFile, maxLen, minLen, ((float) minLen) / ((float) maxLen));
                a.p("decodeImageThumbnail cutImage exactly, width: " + bitmap.getWidth() + ", height: " + bitmap.getHeight() + ", req: " + this.loadReq.cacheKey, new Object[0]);
            } catch (Exception e2) {
                a.e(e2, "decodeImageThumbnail err, info: " + this.loadReq, new Object[0]);
            }
        } else if (this.options.cutScaleType.isRegionCrop() || this.options.cutScaleType.isSmartCrop()) {
            bitmap = RegionCropProcessor.processRegionCrop(this.loadReq, sourceFile, this.options.getCutScaleType());
        } else {
            try {
                long start1 = System.currentTimeMillis();
                bitmap = facade.cutImageKeepRatio(sourceFile, fitSize[0], fitSize[1]);
                if (System.currentTimeMillis() - start1 > 300) {
                    Logger.TIME("decodeImageThumbnail cutImageKeepRatio costTime: " + (System.currentTimeMillis() - start1) + ", " + thumbPath, System.currentTimeMillis() - start, new Object[0]);
                } else {
                    Logger.P("CostTime", "decodeImageThumbnail cutImageKeepRatio costTime: " + (System.currentTimeMillis() - start1) + ", " + thumbPath, new Object[0]);
                }
            } catch (Exception e3) {
                a.e(e3, "fromLocal err, info: " + this.loadReq, new Object[0]);
            }
        }
        return a(bitmap, 1);
    }

    private Bitmap a(int imageId) {
        return a(Thumbnails.getThumbnail(AppUtils.getApplicationContext().getContentResolver(), (long) imageId, 3, null), 2);
    }

    @Nullable
    private Bitmap a(Bitmap bitmap, int type) {
        if (!ConfigManager.getInstance().getCommonConfigItem().inDecodeDirectionOpTypes(type)) {
            a.p("processImageDirection don't handle image's direction, type=" + type, new Object[0]);
            return bitmap;
        }
        if (ImageUtils.checkBitmap(bitmap)) {
            try {
                int rotation = ImageInfo.getImageRotation(Exif.getOrientation(this.loadReq.path));
                a.p("decodeImageIdThumbnail rotation: " + rotation + ";path=" + this.loadReq.path, new Object[0]);
                if (rotation > 0) {
                    DecodeOptions options = new DecodeOptions();
                    options.mode = new MaxLenMode(Integer.valueOf(Math.max(bitmap.getWidth(), bitmap.getHeight())));
                    DecodeResult result = ImageDecoder.processBitmap(bitmap, rotation, options);
                    if (result != null && result.isSuccess()) {
                        if (bitmap != result.bitmap) {
                            a.p("recycle bitmap: " + bitmap, new Object[0]);
                            bitmap.recycle();
                        }
                        return result.bitmap;
                    }
                }
            } catch (Throwable e) {
                a.e(e, "decodeImageIdThumbnail err, info: " + this.loadReq, new Object[0]);
            }
        }
        return bitmap;
    }
}
