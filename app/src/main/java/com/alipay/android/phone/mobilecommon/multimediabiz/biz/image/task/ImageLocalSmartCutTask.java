package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.content.Context;
import android.graphics.Bitmap;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageSourceCutQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.BaseReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.interf.ISmartCutProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.DecryptException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.utils.ImageFileType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageLocalSmartCutTask extends ImageTask {
    private static final Logger a = Logger.getLogger((String) "ImageLocalSmart");

    public ImageLocalSmartCutTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
    }

    public Object call() {
        Bitmap smartCutBitmap;
        String path = a();
        ISmartCutProcessor processor = FalconFactory.INS.getSmartCutProcessor();
        byte[] fileData = null;
        File file = null;
        try {
            if (TextUtils.isEmpty(path) || !this.loadReq.isEncryptRequest()) {
                smartCutBitmap = processor.process(path, this.loadReq);
            } else {
                File file2 = new File(path);
                try {
                    fileData = AESUtils.decryptFile(this.loadReq.options.fileKey, file2);
                    smartCutBitmap = processor.process(fileData, this.loadReq);
                    file = file2;
                } catch (DecryptException e) {
                    e = e;
                    File file3 = file2;
                    notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
                    return null;
                }
            }
            Bitmap bitmap = processBitmap(smartCutBitmap);
            if (!(smartCutBitmap == null || smartCutBitmap == bitmap)) {
                smartCutBitmap.recycle();
            }
            if (bitmap != null) {
                if (this.loadReq.options.isWithImageDataInCallback()) {
                    if (file == null) {
                        file = new File(path);
                    }
                    byte[] data = ImageUtils.bitmap2Bytes(bitmap, ImageFileType.isJPEG(file));
                    notifySuccessWithImageData(this.loadReq, data);
                    getCacheLoader().putDiskCache(this.loadReq.cacheKey, data, this.loadReq.options.getBusinessId(), this.loadReq.options.fileKey, this.loadReq.getExpiredTime());
                    return bitmap;
                } else if (this.loadReq.isEncryptRequest() && fileData != null) {
                    display(bitmap, this.loadReq, this.viewWrapper);
                    ImageInfo info = ImageInfo.getImageInfo(fileData);
                    getCacheLoader().put(this.loadReq.cacheKey, path, bitmap, this.loadReq.options.getBusinessId(), false, this.loadReq.options.fileKey, ImageFileType.detectImageDataType(fileData), info, this.loadReq.getExpiredTime());
                    return bitmap;
                } else if (this.loadReq.options.baseOptions == null || this.loadReq.options.baseOptions.saveToDiskCache) {
                    getCacheLoader().put(this.loadReq.cacheKey, path, bitmap, this.loadReq.options.getBusinessId(), false, this.loadReq.getExpiredTime());
                    display(bitmap, this.loadReq, this.viewWrapper);
                    return bitmap;
                } else {
                    getCacheLoader().putMemCache(this.loadReq.cacheKey, bitmap, this.options.getBusinessId());
                    a.p("ImageLocalTask only put memcache" + this.loadReq.cacheKey, new Object[0]);
                    return bitmap;
                }
            } else if (!b()) {
                return bitmap;
            } else {
                addNetTask(getImageMMTask());
                return bitmap;
            }
        } catch (DecryptException e2) {
            e = e2;
        }
    }

    /* access modifiers changed from: protected */
    public ImageMMTask getImageMMTask() {
        if (!PathUtils.isHttp(this.loadReq.path)) {
            return new ImageDjangoTask(this.loadReq, this.viewWrapper);
        }
        String domain = PathUtils.extractDomain(this.loadReq.path);
        if (TextUtils.isEmpty(domain) || !ConfigManager.getInstance().isConvergenceDomain(domain)) {
            return new ImageUrlTask(this.loadReq, this.viewWrapper);
        }
        if (!this.loadReq.adjustToDjangoReq()) {
            return new ImageUrlTask(this.loadReq, this.viewWrapper);
        }
        ImageMMTask task = new ImageDjangoTask(this.loadReq, this.viewWrapper);
        this.loadReq.netPerf.downloadType = 2;
        this.loadReq.netPerf.originalUrl = this.loadReq.path;
        return task;
    }

    private String a() {
        String path = null;
        if (PathUtils.isLocalFile(this.loadReq.source)) {
            String path2 = this.loadReq.path;
            if (PathUtils.isAlipayAssetsFile(this.loadReq.source)) {
                return a(path2);
            }
            return path2;
        } else if (!this.loadReq.isEncryptRequest() || this.options.cutScaleType.equals(CutScaleType.NONE) || ConfigManager.getInstance().getCommonConfigItem().enableCutForEncrypt != 0) {
            APImageQueryResult result = getCacheManager().queryImageFor(new APImageSourceCutQuery(this.loadReq.path));
            if (result.success) {
                path = result.path;
            }
            if (PathUtils.isPreloadNeedReport(this.loadReq.options.businessId, this.loadReq.path)) {
                UCLogUtil.UC_MM_48(result.success ? "0" : "1", this.loadReq.path, "im");
            }
            if (PathUtils.checkIsResourcePreDownload(this.loadReq.options.businessId)) {
                return path;
            }
            if (result.success) {
                CacheHitManager.getInstance().imageCacheHit();
                return path;
            }
            CacheHitManager.getInstance().imageCacheMissed();
            return path;
        } else {
            a.d("findResource encrypt request not original local cut not enabled...", new Object[0]);
            return null;
        }
    }

    private String a(String path) {
        Context context = this.options.getContext() == null ? this.mContext : this.options.getContext();
        File cacheFile = new File(context.getCacheDir(), new File(path).getName());
        if (!cacheFile.exists() || !cacheFile.isFile()) {
            InputStream in = null;
            try {
                in = context.getAssets().open(this.loadReq.path);
                FileUtils.safeCopyToFile(in, cacheFile);
            } catch (IOException e) {
                a.e(e, "fromAssets error", new Object[0]);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        return cacheFile.getAbsolutePath();
    }

    private boolean b() {
        boolean hasNetwork = true;
        if (checkImageViewReused()) {
            notifyReuse();
            return false;
        }
        this.loadReq.netPerf = new LoadImageFromNetworkPerf(this.loadReq);
        long prepareStart = System.currentTimeMillis();
        boolean isSpaceAvaliable = CommonUtils.isSatisfyDownloadSpace(this.loadReq.options.getBizType());
        boolean checkNet = CommonUtils.isNeedCheckActiveNet(true);
        if (this.loadReq.options.bundle != null) {
            checkNet = this.loadReq.options.bundle.getBoolean(BaseReq.KEY_NETCHECK, checkNet);
        }
        if (!checkNet && !CommonUtils.isActiveNetwork(this.mContext)) {
            hasNetwork = false;
        }
        boolean allowDownload = true;
        if (PathUtils.isLocalFile(this.loadReq.source)) {
            notifyError(this.loadReq, RETCODE.FILE_NOT_EXIST, this.loadReq.source + " maybe not exist", null);
            allowDownload = false;
        } else if (!PathUtils.isNetResource(this.loadReq.path)) {
            notifyError(this.loadReq, RETCODE.PARAM_ERROR, this.loadReq.source + " is a invalid net resource id", null);
            allowDownload = false;
        } else if (c()) {
            notifyError(this.loadReq, RETCODE.CURRENT_LIMIT, this.loadReq.source + " download fail for limited current", null);
            a.p("loadFrom network fail by net limit" + this.loadReq.path, new Object[0]);
            allowDownload = false;
        } else if (!isSpaceAvaliable) {
            a(this.loadReq.cacheKey, this.loadReq.path, this.loadReq.options.businessId, "space not enough");
            notifyError(this.loadReq, RETCODE.SPACE_NOT_ENOUGH, this.loadReq.cacheKey + ", space: " + FileUtils.getSdAvailableSize(), null);
            allowDownload = false;
        } else if (!hasNetwork) {
            notifyError(this.loadReq, RETCODE.INVALID_NETWORK, this.loadReq.source + " invalid network", null);
            allowDownload = false;
        }
        this.loadReq.netPerf.prepareTime = System.currentTimeMillis() - prepareStart;
        return allowDownload;
    }

    private boolean c() {
        boolean ret = false;
        int val = ConfigManager.getInstance().getIntValue(ConfigConstants.MULTIMEDIA_CURRENT_LIMIT, 0);
        if (val > 0) {
            ret = true;
        }
        if (!ret || val == 3) {
            return ret;
        }
        return a(this.options.getWidth().intValue(), this.options.getHeight().intValue());
    }

    private static boolean a(int width, int height) {
        return (width == 0 && height == 0) || (width >= 1280 && height >= 1280);
    }

    private static void a(BitmapCacheKey key, String id, String biz, String exp) {
        DiskExpUtils.UC_MM_47(DiskExpUtils.DISK_NO_SPACE, key.complexCacheKey(), false, 0, id, biz, exp);
    }
}
