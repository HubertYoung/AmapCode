package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageOriginalQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageRetMsg.RETCODE;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageSourceCutQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.BaseReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.GifDrawableCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.CacheHitManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.ImageLoadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ResourcesHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ViewWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable.GifDrawableOwner;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.processor.RegionCropProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageFromNetworkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.LoadImageMarkPerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AESUtils.DecryptException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.VideoFileManager;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeResult;
import com.alipay.multimedia.img.decode.ImageDecoder;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.autonavi.bundle.uitemplate.util.LottieDownloadUtil.LottieProperty;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class ImageLocalTask extends ImageTask {
    private static final Logger a = Logger.getLogger((String) "ImageLocalTask");
    private File b = null;
    private AtomicInteger c = new AtomicInteger(0);
    private ImageInfo d;

    public ImageLocalTask(ImageLoadReq loadReq, ViewWrapper wrapper) {
        super(loadReq, wrapper);
    }

    public Object call() {
        long start = System.currentTimeMillis();
        this.loadReq.notifyGifState(2, true, 0);
        if (!a()) {
            notifyError(RETCODE.STREAMERROR, "file is too large", null);
            this.loadReq.notifyGifState(2, false, -1);
            return null;
        }
        Bitmap bitmap = null;
        BitmapCacheLoader cacheLoader = getCacheLoader();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicBoolean atomicBoolean2 = new AtomicBoolean(true);
        boolean isEncrypt = this.loadReq.isEncryptRequest();
        if (b()) {
            bitmap = f();
            if (bitmap == null) {
                return null;
            }
        } else if (e()) {
            bitmap = g();
        } else if (c()) {
            bitmap = h();
        } else if (d()) {
            bitmap = i();
            atomicBoolean2.set(ConfigManager.getInstance().getCommonConfigItem().saveVideoThumb());
        } else if (PathUtils.isVideoFileUri(a(this.loadReq.source))) {
            bitmap = b(this.loadReq.path);
            atomicBoolean2.set(ConfigManager.getInstance().getCommonConfigItem().saveVideoThumb());
        } else {
            if (!isEncrypt) {
                if (isGifTask(this.loadReq, this.loadReq.path)) {
                    c(this.loadReq.path);
                    this.loadReq.notifyGifState(2, false, 0);
                    return null;
                }
            }
            if (this.options.getImageMarkRequest() == null) {
                try {
                    String aliasPath = ImageDiskCache.get().queryAliasKey(this.loadReq.cacheKey.key);
                    if (!isEncrypt) {
                        if (isGifTask(this.loadReq, aliasPath)) {
                            c(aliasPath);
                            this.loadReq.notifyGifState(2, false, 0);
                            return null;
                        }
                    }
                    atomicBoolean2.set(l() || isEncrypt);
                    bitmap = a(atomicBoolean, aliasPath);
                } catch (DecryptException e) {
                    notifyError(RETCODE.DECRYPT_FAILED, "decrypt file fail", e);
                    return null;
                }
            }
        }
        Bitmap bitmap2 = a(bitmap);
        if (ImageUtils.checkBitmap(bitmap2)) {
            long start1 = System.currentTimeMillis();
            this.loadReq.downloadRsp.loadFrom = 2;
            if (this.loadReq.options.isWithImageDataInCallback()) {
                byte[] imageData = ImageUtils.bitmap2Bytes(bitmap2, atomicBoolean.get());
                cacheLoader.putDiskCache(this.loadReq.cacheKey, imageData, this.options.getBusinessId(), this.options.fileKey, this.options.getExpiredTime());
                notifySuccessWithImageData(this.loadReq, imageData);
            } else {
                display(bitmap2, this.loadReq, this.viewWrapper);
                if (!e()) {
                    if (this.loadReq.isEncryptRequest()) {
                        cacheLoader.put(this.loadReq.cacheKey, this.b != null ? this.b.getAbsolutePath() : "", bitmap2, this.options.getBusinessId(), atomicBoolean.get() && !this.options.shouldProcess(), this.loadReq.options.fileKey, this.c.get(), this.d, this.options.getExpiredTime());
                    } else if ((this.loadReq.options.baseOptions == null || this.loadReq.options.baseOptions.saveToDiskCache) && atomicBoolean2.get()) {
                        cacheLoader.put(this.loadReq.cacheKey, this.b != null ? this.b.getAbsolutePath() : "", bitmap2, this.options.getBusinessId(), atomicBoolean.get() && !this.options.shouldProcess(), this.options.getExpiredTime());
                    } else {
                        cacheLoader.putMemCache(this.loadReq.cacheKey, bitmap2, this.options.getBusinessId());
                        a.p("ImageLocalTask only put memcache" + this.loadReq.cacheKey, new Object[0]);
                    }
                }
            }
            Logger.TIME("ImageLocalTask call cacheLoader.put costTime: " + (System.currentTimeMillis() - start1) + ", " + this.loadReq.path, System.currentTimeMillis() - start1, new Object[0]);
            a.d("loadFrom local " + this.loadReq.cacheKey, new Object[0]);
        } else if (checkAndStartNetTask()) {
            this.loadReq.notifyGifState(2, false, -1);
            return null;
        }
        this.loadReq.notifyGifState(2, false, -1);
        Logger.TIME("ImageLocalTask call costTime: " + (System.currentTimeMillis() - start) + ", " + this.loadReq.path, System.currentTimeMillis() - start, new Object[0]);
        return null;
    }

    private boolean a() {
        boolean match;
        if (PathUtils.isLocalFile(this.loadReq.path)) {
            File file = PathUtils.extractFile(this.loadReq.path);
            if (file != null) {
                long limitSize = ConfigManager.getInstance().getCommonConfigItem().diskConf.largeImageSize;
                Pattern pattern = ConfigManager.getInstance().getCommonConfigItem().diskConf.getLargeImageExcludeSuffixPattern();
                String suffix = FileUtils.getSuffix(file.getName(), false);
                if (pattern == null || suffix == null || !pattern.matcher(suffix).matches()) {
                    match = false;
                } else {
                    match = true;
                }
                if (file.length() > limitSize && !match) {
                    a.e("preCheckLargeImageFile file: " + file + ", length: " + file.length() + ", limit: " + limitSize, new Object[0]);
                    return false;
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean checkAndStartNetTask() {
        boolean hasNetwork;
        if (this.loadReq.getLoadOptions() != null && this.loadReq.getLoadOptions().ignoreNetTask) {
            a.d("checkAndStartNetTask end bey ignoreNetTask", new Object[0]);
            notifyError(RETCODE.FILE_NOT_EXIST, "file may not exist and ignore net download", null);
            return false;
        } else if (checkImageViewReused()) {
            notifyReuse();
            return true;
        } else {
            long prepareStart = System.currentTimeMillis();
            boolean isSpaceAvailable = CommonUtils.isSatisfyDownloadSpace(this.loadReq.options.getBizType());
            boolean checkNet = CommonUtils.isNeedCheckActiveNet(true);
            if (this.loadReq.options.bundle != null) {
                checkNet = this.loadReq.options.bundle.getBoolean(BaseReq.KEY_NETCHECK, checkNet);
            }
            if (checkNet || CommonUtils.isActiveNetwork(this.mContext)) {
                hasNetwork = true;
            } else {
                hasNetwork = false;
            }
            if (a(isSpaceAvailable, hasNetwork)) {
                a(prepareStart);
                if (startNetTask()) {
                    return true;
                }
                return false;
            }
            a.d("load error, notify biz, req: " + this.loadReq + ", space: " + isSpaceAvailable + ", hasNetwork: " + hasNetwork, new Object[0]);
            b(isSpaceAvailable, hasNetwork);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean startNetTask() {
        ImageNetTask netTask;
        if (PathUtils.isHttp(Uri.parse(this.loadReq.source))) {
            String domain = PathUtils.extractDomain(this.loadReq.path);
            if (TextUtils.isEmpty(domain) || !ConfigManager.getInstance().isConvergenceDomain(domain)) {
                netTask = new ImageUrlTask(this.loadReq, this.viewWrapper);
            } else if (this.loadReq.adjustToDjangoReq()) {
                netTask = new ImageDjangoTask(this.loadReq, this.viewWrapper);
                this.loadReq.netPerf.downloadType = 2;
                this.loadReq.netPerf.originalUrl = this.loadReq.path;
            } else {
                netTask = new ImageUrlTask(this.loadReq, this.viewWrapper);
            }
        } else if (checkDjgIdAndCurrentLimit()) {
            return true;
        } else {
            if (CutScaleType.NONE.equals(this.options.getCutScaleType())) {
                netTask = new ImageDjangoOriginalTask(this.loadReq, this.viewWrapper);
            } else {
                netTask = new ImageDjangoTask(this.loadReq, this.viewWrapper);
            }
        }
        addNetTask(netTask);
        a.d("loadFrom network " + this.loadReq.cacheKey, new Object[0]);
        return false;
    }

    private static void a(Bitmap bitmap, String sourcePath, String path, byte[] fileData) {
        if (ConfigManager.getInstance().getCommonConfigItem().cleanInvalidImageFile != 0 && bitmap == null && FileUtils.checkFile(sourcePath) && !PathUtils.isLocalFile(path)) {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            if (fileData != null) {
                try {
                    BitmapFactory.decodeByteArray(fileData, 0, fileData.length, options);
                } catch (Throwable th) {
                    if (options.outWidth < 0 || options.outHeight < 0) {
                        FileUtils.delete(sourcePath);
                        a.d("cleanInvalidImageFile src: " + sourcePath + " is invalid image file! req.path: " + path, new Object[0]);
                    }
                    throw th;
                }
            } else {
                BitmapFactory.decodeFile(sourcePath, options);
            }
            if (options.outWidth < 0 || options.outHeight < 0) {
                FileUtils.delete(sourcePath);
                a.d("cleanInvalidImageFile src: " + sourcePath + " is invalid image file! req.path: " + path, new Object[0]);
            }
        }
    }

    private void a(long prepareStart) {
        this.loadReq.netPerf = this.loadReq.options.getImageMarkRequest() == null ? new LoadImageFromNetworkPerf(this.loadReq) : new LoadImageMarkPerf(this.loadReq);
        this.loadReq.netPerf.prepareTime = System.currentTimeMillis() - prepareStart;
        this.loadReq.netPerf.beginWait();
    }

    /* access modifiers changed from: protected */
    public boolean checkDjgIdAndCurrentLimit() {
        if (!PathUtils.checkDjangoId(this.loadReq.path)) {
            notifyError(this.loadReq, RETCODE.PARAM_ERROR, this.loadReq.source + " is a invalid Django id", null);
            return true;
        } else if (!j()) {
            return false;
        } else {
            notifyError(this.loadReq, RETCODE.CURRENT_LIMIT, this.loadReq.source + " download fail for limited current", null);
            a.p("loadFrom network fail by net limit" + this.loadReq.path, new Object[0]);
            return true;
        }
    }

    private boolean a(boolean isSpaceAvailable, boolean hasNetwork) {
        return isSpaceAvailable && hasNetwork && !b() && !e() && !PathUtils.isLocalFile(this.loadReq.source);
    }

    private void b(boolean isSpaceAvailable, boolean hasNetwork) {
        if (!isSpaceAvailable) {
            a(this.loadReq.cacheKey, this.loadReq.path, this.loadReq.options.businessId, (String) "space not enough");
            notifyError(this.loadReq, RETCODE.SPACE_NOT_ENOUGH, this.loadReq.cacheKey + ", space: " + FileUtils.getSdAvailableSize(), null);
        } else if (hasNetwork) {
            notifyError(this.loadReq, RETCODE.FILE_NOT_EXIST, this.loadReq.source + " maybe not exist", null);
        } else {
            notifyError(this.loadReq, RETCODE.INVALID_NETWORK, this.loadReq.source + " invalid network", null);
        }
    }

    private static String a(String videoPath) {
        return videoPath.endsWith(VideoFileManager.VIDEO_EXT) ? videoPath.substring(0, videoPath.lastIndexOf(VideoFileManager.VIDEO_EXT)) + PhotoParam.VIDEO_SUFFIX : videoPath;
    }

    private boolean b() {
        return PathUtils.isAlipayAssetsFile(this.loadReq.source);
    }

    private boolean c() {
        return !this.loadReq.options.isDetectedGif() && PathUtils.isContentFile(this.loadReq.source) && this.loadReq.source.contains(LottieProperty.LOTTIE_IMAGE_PATH_NAME);
    }

    private boolean d() {
        return PathUtils.isContentFile(this.loadReq.source) && this.loadReq.source.contains("/video");
    }

    private boolean e() {
        return PathUtils.isResFile(this.loadReq.source);
    }

    private Bitmap f() {
        AssetManager am = this.mContext.getAssets();
        if (am != null) {
            if (this.loadReq.options.isDetectedGif() && (this.loadReq.path.endsWith(".gif") || this.loadReq.path.endsWith(".GIF"))) {
                return b(am);
            }
            Bitmap in = a(am);
            if (in != null) {
                return in;
            }
        }
        return null;
    }

    /* JADX INFO: finally extract failed */
    @Nullable
    private Bitmap a(AssetManager am) {
        InputStream in = null;
        try {
            in = am.open(this.loadReq.path);
            Bitmap decodeBitmap = ImageUtils.decodeBitmap(in);
            IOUtils.closeQuietly(in);
            return decodeBitmap;
        } catch (IOException e) {
            a.e(e, "fromAssets error", new Object[0]);
            IOUtils.closeQuietly(in);
            return null;
        } catch (Throwable th) {
            IOUtils.closeQuietly(in);
            throw th;
        }
    }

    @Nullable
    private Bitmap b(AssetManager am) {
        File cacheFile = new File(this.mContext.getCacheDir(), new File(this.loadReq.path).getName());
        if (!cacheFile.exists() || !cacheFile.isFile()) {
            InputStream in = null;
            try {
                in = am.open(this.loadReq.path);
                FileUtils.safeCopyToFile(in, cacheFile);
            } catch (IOException e) {
                a.e(e, "fromAssets error", new Object[0]);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        if (!ImageFileType.isGif(cacheFile)) {
            return ImageUtils.decodeBitmapByFalcon(cacheFile);
        }
        c(cacheFile.getAbsolutePath());
        return null;
    }

    private Bitmap g() {
        return ResourcesHelper.getBitmap(this.options.getContext(), this.loadReq.source, this.loadReq);
    }

    private Bitmap h() {
        InputStream inputStream = null;
        try {
            inputStream = SandboxWrapper.openContentResolverInputStream(Uri.parse(this.loadReq.source));
            DecodeResult result = ImageDecoder.decodeBitmap(inputStream, new DecodeOptions());
            if (result != null && result.isSuccess()) {
                return result.bitmap;
            }
            IOUtils.closeQuietly(inputStream);
            return null;
        } catch (Throwable th) {
            a.d("fromContentUri exp path: " + this.loadReq.source, new Object[0]);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    private Bitmap i() {
        return a(Uri.parse(this.loadReq.source));
    }

    private Bitmap a(AtomicBoolean outJpg, String aliasPath) {
        Bitmap bitmap;
        Bitmap bitmap2;
        APImageQueryResult result;
        long start = System.currentTimeMillis();
        String sourcePath = aliasPath;
        if (TextUtils.isEmpty(aliasPath)) {
            sourcePath = this.loadReq.path;
        }
        a.d("from local start... sourcePath: " + sourcePath + ", req: " + this.loadReq, new Object[0]);
        Integer width = this.options.getWidth();
        Integer height = this.options.getHeight();
        if (!FileUtils.checkFile(sourcePath) && !PathUtils.isLocalFile(this.loadReq.source)) {
            boolean shouldCutForEncrypt = true;
            if (this.loadReq.isEncryptRequest() && !this.options.cutScaleType.equals(CutScaleType.NONE) && ConfigManager.getInstance().getCommonConfigItem().enableCutForEncrypt == 0) {
                shouldCutForEncrypt = false;
                a.d("from local shouldCutForEncrypt = false", new Object[0]);
            }
            if (!shouldCutForEncrypt || this.options.getImageMarkRequest() != null) {
                result = new APImageQueryResult();
                result.success = false;
            } else if ((this.options.cutScaleType.equals(CutScaleType.KEEP_RATIO) || this.options.cutScaleType.equals(CutScaleType.SCALE_AUTO_LIMIT)) && !k()) {
                result = querySourceCut(this.loadReq.path, sourcePath, width, height);
            } else {
                result = queryOriginal(this.loadReq.path, sourcePath);
            }
            if (result.success) {
                sourcePath = result.path;
            }
            if (PathUtils.isPreloadNeedReport(this.loadReq.options.businessId, this.loadReq.path)) {
                UCLogUtil.UC_MM_48(result.success ? "0" : "1", this.loadReq.path, "im");
            }
            if (!PathUtils.checkIsResourcePreDownload(this.loadReq.options.businessId)) {
                if (result.success) {
                    CacheHitManager.getInstance().imageCacheHit();
                } else {
                    CacheHitManager.getInstance().imageCacheMissed();
                }
            }
        } else if (!FileUtils.checkFile(sourcePath)) {
            sourcePath = this.loadReq.path;
        }
        Logger.TIME("fromLocal queryImageFor costTime: " + (System.currentTimeMillis() - start) + ", " + this.loadReq.path, System.currentTimeMillis() - start, new Object[0]);
        long start2 = System.currentTimeMillis();
        Bitmap bitmap3 = null;
        if (FileUtils.checkFile(sourcePath)) {
            byte[] fileData = null;
            boolean isEncrypt = this.loadReq.isEncryptRequest();
            if (isEncrypt) {
                String str = this.loadReq.options.fileKey;
                File file = new File(sourcePath);
                fileData = AESUtils.decryptFile(str, file);
            }
            this.d = isEncrypt ? ImageInfo.getImageInfo(fileData) : ImageInfo.getImageInfo(sourcePath);
            this.c.set(isEncrypt ? ImageFileType.detectImageDataType(fileData) : ImageFileType.detectImageFileType(sourcePath));
            outJpg.set(this.c.get() == 0);
            int[] fitSize = getFitSize(new Size(this.d.correctWidth, this.d.correctHeight), width.intValue(), height.intValue());
            a.d("from local fitSize: " + Arrays.toString(fitSize) + ";fileType=" + this.c, new Object[0]);
            FalconFacade facade = FalconFacade.get();
            File file2 = new File(sourcePath);
            this.b = file2;
            if (CutScaleType.CENTER_CROP.equals(this.options.getCutScaleType())) {
                if (isEncrypt) {
                    try {
                        bitmap3 = facade.cutImage(fileData, width.intValue(), height.intValue(), this.options.getScale().floatValue());
                    } catch (Exception e) {
                        a.e(e, "fromLocal err, info: " + this.loadReq, new Object[0]);
                    }
                } else {
                    bitmap3 = facade.cutImage(this.b, width.intValue(), height.intValue(), this.options.getScale().floatValue());
                }
                a.p("fromLocal cutImage, width: " + bitmap3.getWidth() + ", height: " + bitmap3.getHeight() + ", req: " + this.loadReq.cacheKey, new Object[0]);
            } else if (CutScaleType.AUTO_CUT_EXACTLY.equals(this.options.getCutScaleType())) {
                try {
                    int maxLen = Math.max(width.intValue(), height.intValue());
                    int minLen = Math.min(width.intValue(), height.intValue());
                    float scale = ((float) minLen) / ((float) maxLen);
                    if (isEncrypt) {
                        bitmap2 = facade.cutImage(fileData, maxLen, minLen, scale);
                    } else {
                        bitmap2 = facade.cutImage(this.b, maxLen, minLen, scale);
                    }
                    a.p("fromLocal cutImage exactly, width: " + bitmap3.getWidth() + ", height: " + bitmap3.getHeight() + ", req: " + this.loadReq.cacheKey, new Object[0]);
                } catch (Exception e2) {
                    a.e(e2, "fromLocal err, info: " + this.loadReq, new Object[0]);
                }
            } else if (this.options.cutScaleType.isRegionCrop() || this.options.cutScaleType.isSmartCrop()) {
                bitmap3 = isEncrypt ? RegionCropProcessor.processRegionCrop(this.loadReq, fileData, this.options.getCutScaleType()) : RegionCropProcessor.processRegionCrop(this.loadReq, this.b, this.options.getCutScaleType());
            } else {
                try {
                    long start1 = System.currentTimeMillis();
                    if (isEncrypt) {
                        bitmap = facade.cutImageKeepRatio(fileData, fitSize[0], fitSize[1]);
                    } else {
                        bitmap = facade.cutImageKeepRatio(this.b, fitSize[0], fitSize[1]);
                    }
                    if (System.currentTimeMillis() - start1 > 300) {
                        Logger.TIME("fromLocal cutImageKeepRatio costTime: " + (System.currentTimeMillis() - start1) + ", " + this.loadReq.path, System.currentTimeMillis() - start2, new Object[0]);
                    } else {
                        Logger.P("CostTime", "fromLocal cutImageKeepRatio costTime: " + (System.currentTimeMillis() - start1) + ", " + this.loadReq.path, new Object[0]);
                    }
                } catch (Exception e3) {
                    a.e(e3, "fromLocal err, info: " + this.loadReq, new Object[0]);
                }
            }
            if (isEncrypt) {
                a(bitmap3, sourcePath, this.loadReq.path, fileData);
            }
        }
        Logger.TIME("fromLocal cutImage costTime: " + (System.currentTimeMillis() - start2) + ", " + this.loadReq.path, System.currentTimeMillis() - start2, new Object[0]);
        if (!this.loadReq.isEncryptRequest()) {
            a(bitmap3, sourcePath, this.loadReq.path, (byte[]) null);
        }
        a.d("handle fromLocal, path: " + this.loadReq.cacheKey + ", bitmap: " + bitmap3, new Object[0]);
        return bitmap3;
    }

    /* access modifiers changed from: protected */
    public APImageQueryResult queryOriginal(String originalPath, String aliasPath) {
        List<String> queryList = new ArrayList<>();
        if (!TextUtils.isEmpty(originalPath)) {
            queryList.add(originalPath);
        }
        if (!TextUtils.isEmpty(aliasPath) && !aliasPath.equals(originalPath)) {
            queryList.add(aliasPath);
        }
        APImageQueryResult result = null;
        for (String path : queryList) {
            APImageOriginalQuery query = new APImageOriginalQuery(path);
            query.requireImageInfo = false;
            query.businessId = this.loadReq.options.getBusinessId();
            result = getCacheManager().queryImageFor(query);
            if (result.success) {
                break;
            }
        }
        a.d("queryOriginal queryList: " + queryList + ", result: " + result, new Object[0]);
        return result == null ? new APImageQueryResult() : result;
    }

    /* access modifiers changed from: protected */
    public APImageQueryResult querySourceCut(String originalPath, String aliasPath, Integer width, Integer height) {
        List<String> queryList = new ArrayList<>();
        if (!TextUtils.isEmpty(originalPath)) {
            queryList.add(originalPath);
        }
        if (!TextUtils.isEmpty(aliasPath) && !aliasPath.equals(originalPath)) {
            queryList.add(aliasPath);
        }
        APImageQueryResult result = null;
        for (String path : queryList) {
            APImageSourceCutQuery query = new APImageSourceCutQuery(path, this.options.getCutScaleType(), width, height);
            query.businessId = this.loadReq.options.getBusinessId();
            query.setQuality(this.loadReq.options.getQuality());
            result = getCacheManager().queryImageFor(query);
            if (result.success) {
                break;
            }
        }
        a.d("querySourceCut queryList: " + queryList + ", width: " + width + ", height: " + height + ", result: " + result, new Object[0]);
        return result == null ? new APImageQueryResult() : result;
    }

    @TargetApi(8)
    private Bitmap b(String filePath) {
        if (VERSION.SDK_INT >= 8) {
            try {
                this.b = new File(filePath);
                return VideoUtils.getVideoFrame(filePath, 0);
            } catch (Throwable e) {
                a.e(e, "createVideoThumbnail exception " + filePath, new Object[0]);
            }
        }
        return null;
    }

    private static Bitmap a(Uri uri) {
        try {
            return VideoUtils.getVideoFrameByUri(uri, 0);
        } catch (Exception e) {
            a.e(e, "getVideoThumbnailStream exp=" + e.toString(), new Object[0]);
            return null;
        }
    }

    private Bitmap a(Bitmap bitmap) {
        if (!ImageUtils.checkBitmap(bitmap)) {
            return bitmap;
        }
        Bitmap bitmap2 = processBitmap(bitmap);
        if (this.options.shouldProcess() || !isNeedZoom(this.loadReq) || !ImageUtils.checkBitmap(bitmap2)) {
            return bitmap2;
        }
        int[] fitSize = getFitSize(new Size(bitmap2.getWidth(), bitmap2.getHeight()), this.options.getWidth().intValue(), this.options.getHeight().intValue());
        a.p("processOrZoom fitSize: " + Arrays.toString(fitSize) + ", req: " + this.loadReq, new Object[0]);
        return ImageUtils.zoomBitmap(bitmap2, fitSize[0], fitSize[1]);
    }

    private boolean j() {
        boolean ret;
        boolean ret2 = false;
        int val = ConfigManager.getInstance().getIntValue(ConfigConstants.MULTIMEDIA_CURRENT_LIMIT, 0);
        if (val > 0) {
            ret2 = true;
        }
        if (!ret2 || val == 3) {
            return ret2;
        }
        if (CutScaleType.NONE.equals(this.options.getCutScaleType())) {
            ret = true;
        } else {
            ret = a(this.options.getWidth().intValue(), this.options.getHeight().intValue());
        }
        return ret;
    }

    private static boolean a(int width, int height) {
        return (width == 0 && height == 0) || (width >= 1280 && height >= 1280);
    }

    private static void a(BitmapCacheKey key, String id, String biz, String exp) {
        DiskExpUtils.UC_MM_47(DiskExpUtils.DISK_NO_SPACE, key.complexCacheKey(), false, 0, id, biz, exp);
    }

    private void c(String gif) {
        boolean ignoreAutoCheck;
        ImageInfo info;
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
            if (SandboxWrapper.isContentUriPath(gif)) {
                info = ImageInfo.getImageInfo(SandboxWrapper.openFileDescriptor(Uri.parse(gif)));
            } else {
                info = ImageInfo.getImageInfo(gif);
            }
            if (ignoreAutoCheck) {
                gifDrawable = new GifDrawableOwner(this.mContext, gif, info.correctWidth, info.correctHeight, null);
            } else {
                gifDrawable = new GifDrawableImpl(this.mContext, gif, info.correctWidth, info.correctHeight, null);
            }
            GifDrawableCache.getInstance().put(cacheKey, gifDrawable);
        } else {
            a.d("loadGif from memcache", new Object[0]);
        }
        gifDrawable.bindView(this.viewWrapper.getTargetView());
        display((Drawable) gifDrawable, this.loadReq, this.viewWrapper);
        this.loadReq.notifyGifController(gifDrawable);
    }

    private boolean k() {
        return this.loadReq.options.isDetectedGif() && this.loadReq.getGifController() != null;
    }

    private boolean l() {
        Bitmap bitmap = null;
        if (this.d == null || ConfigManager.getInstance().getCommonConfigItem().diskConf.saveLocalToDiskCache()) {
            return true;
        }
        if (this.d.getFormat() == 2) {
            return false;
        }
        if (this.d.getFormat() != 1 || !ImageUtils.checkBitmap(bitmap)) {
            return true;
        }
        if (this.d.correctWidth < bitmap.getWidth() || this.d.correctHeight < bitmap.getHeight()) {
            return false;
        }
        return true;
    }
}
