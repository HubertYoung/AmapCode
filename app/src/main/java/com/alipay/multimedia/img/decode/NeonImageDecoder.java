package com.alipay.multimedia.img.decode;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import com.alipay.multimedia.img.Format;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.base.SoLoader;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.img.decode.DecodeOptions.FitRectMode;
import com.alipay.multimedia.img.decode.DecodeOptions.MaxLenMode;
import com.alipay.multimedia.img.decode.DecodeOptions.MinLenMode;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.multimedia.img.utils.Preconditions;
import com.alipay.multimedia.io.IOUtils;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureBaseConfig;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureBaseConfig.ImageType;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig.CropMode;
import com.alipay.streammedia.mmengine.picture.jpg.PictureCompressResult;
import com.alipay.streammedia.mmengine.picture.jpg.PictureFileConfig;
import java.io.File;
import java.io.InputStream;

public class NeonImageDecoder implements Format {
    private static final String TAG = "NeonImageDecoder";

    static {
        SoLoader.loadLibraryOnce();
    }

    public static DecodeResult decodeBitmap(File file, DecodeOptions options) {
        DecodeResult result;
        checkYUVOptions(file, options);
        if (options == null) {
            options = new DecodeOptions();
        }
        DecodeResult result2 = null;
        long start = System.currentTimeMillis();
        ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
        if ((info.matchFormat(0) && !canUseThumbnailData4Decode(info, options)) || info.matchFormat(5)) {
            result2 = doNativeImageDecodeProcess(file, options, info);
        }
        if (result == null || !result.isSuccess()) {
            if (ImageFileType.isGif(file)) {
                result = GifDecoderWrapper.decode(file, options);
            } else {
                result = InnerDecoder.decodeFile(file, options, info);
            }
            doImageProcess(result, options, info);
        }
        LogUtils.d(TAG, "decodeBitmap file: " + file + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult decodeBitmap(byte[] data, DecodeOptions options) {
        DecodeResult result;
        checkData(data);
        checkYUVOptions(options);
        long start = System.currentTimeMillis();
        if (options == null) {
            options = new DecodeOptions();
        }
        ImageInfo info = ImageInfo.getImageInfo(data);
        if (info.matchFormat(2)) {
            result = GifDecoderWrapper.decode(data, options);
        } else if (info.matchFormat(5)) {
            result = doNativeImageDecodeProcess(data, options, info);
        } else {
            result = InnerDecoder.decodeByteArray(data, info, options);
        }
        doImageProcess(result, options, info);
        LogUtils.d(TAG, "decodeBitmap data: " + data + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult decodeBitmap(InputStream in, DecodeOptions options) {
        checkInputStream(in);
        checkYUVOptions(options);
        return decodeBitmap(IOUtils.getBytes(in), options);
    }

    public static DecodeResult processBitmap(Bitmap bitmap, int rotation, DecodeOptions options) {
        checkBitmap(bitmap);
        checkYUVOptions(options);
        DecodeResult result = new DecodeResult();
        result.bitmap = bitmap;
        result.code = 0;
        doImageProcess(result, options, ImageInfo.getImageInfo(bitmap, rotation));
        return result;
    }

    public static DecodeResult processBitmap(byte[] yuv, ImageInfo info, DecodeOptions options) {
        checkData(yuv);
        DecodeResult result = new DecodeResult();
        result.yuvData = yuv;
        result.code = 0;
        result.yuvInfo = info;
        doYUVProcess(result, options, info);
        return result;
    }

    private static boolean canUseThumbnailData4Decode(ImageInfo info, DecodeOptions options) {
        boolean can = false;
        ImageInfo thumbInfo = null;
        if (StaticOptions.useThumbnailData) {
            thumbInfo = info.getThumbnailInfo();
            if (thumbInfo != null) {
                if (options.mode instanceof MaxLenMode) {
                    int maxSide = Math.max(thumbInfo.width, thumbInfo.height);
                    Integer len = ((MaxLenMode) options.mode).len;
                    can = len.intValue() > 0 && (maxSide > len.intValue() || len.intValue() - maxSide <= StaticOptions.thumbnail_allow_delta);
                } else if (options.mode instanceof MinLenMode) {
                    int minSide = Math.max(thumbInfo.width, thumbInfo.height);
                    Integer len2 = ((MinLenMode) options.mode).len;
                    can = len2.intValue() > 0 && (minSide > len2.intValue() || len2.intValue() - minSide <= StaticOptions.thumbnail_allow_delta);
                }
            }
        }
        LogUtils.d(TAG, "canUseThumbnailData4Decode info: " + info + ", options: " + options + ", thumbInfo: " + thumbInfo + ", can: " + can);
        return can;
    }

    public static DecodeResult cropBitmap(File file, CropOptions options) {
        DecodeResult result;
        checkFile(file);
        checkCutOptions(options);
        DecodeResult result2 = null;
        long start = System.currentTimeMillis();
        ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
        if ((info.matchFormat(0) && !isKeepMinScale(options) && !canUseThumbnailData4Crop(info, options)) || info.matchFormat(5)) {
            result2 = doNativeImageCropProcess(file, options, info);
        }
        if (result == null || !result.isSuccess()) {
            DecodeOptions opts = createDecodeOptsForCrop(options, info);
            if (info.matchFormat(2)) {
                result = GifDecoderWrapper.decode(file, opts);
            } else {
                result = InnerDecoder.decodeFile(file, opts, info);
            }
            doImageCropProcess(result, options, info);
        }
        LogUtils.d(TAG, "cropBitmap file: " + file + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult cropBitmap(byte[] data, CropOptions options) {
        DecodeResult result;
        checkData(data);
        checkCutOptions(options);
        long start = System.currentTimeMillis();
        ImageInfo info = ImageInfo.getImageInfo(data);
        DecodeOptions opts = createDecodeOptsForCrop(options, info);
        if (info.matchFormat(2)) {
            result = GifDecoderWrapper.decode(data, opts);
        } else if (info.matchFormat(5)) {
            result = doNativeImageDecodeProcess(data, opts, info);
        } else {
            result = InnerDecoder.decodeByteArray(data, info, opts);
        }
        doImageCropProcess(result, options, info);
        LogUtils.d(TAG, "cropBitmap data: " + data + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult cropBitmap(InputStream in, CropOptions options) {
        checkInputStream(in);
        checkCutOptions(options);
        return cropBitmap(IOUtils.getBytes(in), options);
    }

    public static DecodeResult processBitmap(Bitmap bitmap, int rotation, CropOptions options) {
        checkBitmap(bitmap);
        checkYUVOptions(options);
        DecodeResult result = new DecodeResult();
        result.bitmap = bitmap;
        result.code = 0;
        doImageCropProcess(result, options, ImageInfo.getImageInfo(bitmap, rotation));
        return result;
    }

    public static DecodeResult processBitmap(byte[] yuvData, ImageInfo info, CropOptions options) {
        checkData(yuvData);
        checkYUVOptions(options);
        DecodeResult result = new DecodeResult();
        result.yuvData = yuvData;
        result.yuvInfo = info;
        result.code = 0;
        doYUVCropProcess(result, options, info);
        return result;
    }

    private static boolean canUseThumbnailData4Crop(ImageInfo info, CropOptions options) {
        boolean can = false;
        ImageInfo thumbInfo = null;
        if (StaticOptions.useThumbnailData) {
            thumbInfo = info.getThumbnailInfo();
            if (thumbInfo != null) {
                int minSide = Math.min(thumbInfo.width, thumbInfo.height);
                int maxCropSide = Math.max(options.cutSize.width, options.cutSize.height);
                can = minSide > maxCropSide || maxCropSide - minSide <= StaticOptions.thumbnail_allow_delta;
            }
        }
        LogUtils.d(TAG, "canUseThumbnailData4Crop info: " + info + ", options: " + options + ", thumbInfo: " + thumbInfo + ", can: " + can);
        return can;
    }

    private static DecodeResult doNativeImageDecodeProcess(File image, DecodeOptions options, ImageInfo info) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        PictureFileConfig cfg = PictureFileConfig.createDefault();
        setupBaseDecodeOptions(options, cfg);
        cfg.srcFile = image.getAbsolutePath();
        setupDecodeRotate(cfg, options, info);
        setupDecodeScaleCfg(cfg, options, info);
        printCfg(cfg, "srf: " + image);
        if (options.resultFormat == 0) {
            Bitmap bitmap = null;
            try {
                if (info.getFormat() == 5) {
                    bitmap = MMNativeEngineApi.decompressHevcFile(cfg);
                } else {
                    bitmap = MMNativeEngineApi.decompressImage(cfg);
                }
            } catch (MMNativeException e) {
                result.extra = e.getCode();
                LogUtils.e(TAG, "doNativeImageDecodeProcess file: " + image + ", opts: " + options + ", info: " + info + ", errCode: " + e.getCode() + ", msg: " + e.getMessage());
            }
            if (bitmap != null) {
                result.code = 0;
                result.bitmap = bitmap;
            }
        } else if (options.resultFormat == 1) {
            try {
                PictureCompressResult r = MMNativeEngineApi.decompressImageToYuv420(cfg);
                if (!(r == null || r.retCode != 0 || r.data == null)) {
                    result.code = 0;
                    result.yuvData = r.data;
                    result.yuvInfo = ImageInfo.getImageInfo(r.dstWidth, r.dstHeight);
                }
            } catch (MMNativeException e2) {
                result.extra = e2.getCode();
                LogUtils.e(TAG, "doNativeImageDecodeProcess file: " + image + ", opts: " + options + ", info: " + info + ", errCode: " + e2.getCode() + ", msg: " + e2.getMessage());
            }
        }
        LogUtils.d(TAG, "doNativeImageDecodeProcess cost: " + (System.currentTimeMillis() - start) + ", file: " + image + ", opts: " + options + ", info: " + info);
        return result;
    }

    private static DecodeResult doNativeImageDecodeProcess(byte[] image, DecodeOptions options, ImageInfo info) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        PictureBaseConfig cfg = PictureBaseConfig.createDefault();
        setupBaseDecodeOptions(options, cfg);
        setupDecodeRotate(cfg, options, info);
        setupDecodeScaleCfg(cfg, options, info);
        Bitmap bitmap = null;
        try {
            bitmap = MMNativeEngineApi.decompressHevcImage(image, cfg);
        } catch (MMNativeException e) {
            result.extra = e.getCode();
            LogUtils.e(TAG, "doNativeImageDecodeProcess data: " + image + ", opts: " + options + ", info: " + info + ", errCode: " + e.getCode() + ", msg: " + e.getMessage());
        }
        if (bitmap != null) {
            result.code = 0;
            result.bitmap = bitmap;
        }
        LogUtils.d(TAG, "doNativeImageDecodeProcess cost: " + (System.currentTimeMillis() - start) + ", data: " + image + ", opts: " + options + ", info: " + info);
        return result;
    }

    private static void doImageProcess(DecodeResult result, DecodeOptions options, ImageInfo info) {
        long start = System.currentTimeMillis();
        if (result.code == 0 && checkNeedNativeProcess(result, options, info)) {
            BitmapPictureBaseConfig cfg = createSimpleBitmapConfig(result.bitmap);
            setupBaseDecodeOptions(options, cfg);
            setupDecodeRotate(cfg, options, info);
            setupDecodeScaleCfg(cfg, options, info);
            printCfg(cfg, "");
            Bitmap bitmap = null;
            try {
                cfg.debugLog = true;
                cfg.perfLog = true;
                bitmap = MMNativeEngineApi.cropImage(result.bitmap, cfg);
            } catch (MMNativeException e) {
                LogUtils.e(TAG, "cropImage exp code=" + e.getCode(), e);
            }
            if (bitmap != null) {
                result.code = 0;
                result.bitmap = bitmap;
            } else {
                result.code = -1;
                result.bitmap = null;
            }
        }
        LogUtils.d(TAG, "doImageProcess cost: " + (System.currentTimeMillis() - start) + ", opts: " + options + ", info: " + info);
    }

    private static void doYUVProcess(DecodeResult result, DecodeOptions options, ImageInfo info) {
        long start = System.currentTimeMillis();
        if (result.code == 0 && checkNeedNativeProcess(result, options, info)) {
            BitmapPictureBaseConfig cfg = createSimpleBitmapConfig(result.yuvInfo);
            setupBaseDecodeOptions(options, cfg);
            setupDecodeRotate(cfg, options, info);
            setupDecodeScaleCfg(cfg, options, info);
            printCfg(cfg, "");
            Bitmap bitmap = null;
            try {
                cfg.debugLog = true;
                cfg.perfLog = true;
                bitmap = MMNativeEngineApi.cropImage(result.yuvData, result.yuvData.length, cfg);
            } catch (MMNativeException e) {
                LogUtils.e(TAG, "cropImage exp code=" + e.getCode(), e);
            }
            if (bitmap != null) {
                result.code = 0;
                result.bitmap = bitmap;
            } else {
                result.code = -1;
                result.bitmap = null;
            }
        }
        LogUtils.d(TAG, "doYUVProcess cost: " + (System.currentTimeMillis() - start) + ", opts: " + options + ", info: " + info);
    }

    private static void setupBaseDecodeOptions(BaseDecodeOptions options, PictureBaseConfig cfg) {
        cfg.debugLog = StaticOptions.jniDebug;
        cfg.useAshMem = VERSION.SDK_INT < 21 && options.autoUseAshmem;
    }

    private static void setupDecodeRotate(PictureBaseConfig cfg, DecodeOptions options, ImageInfo info) {
        if (options.forceRotate != null) {
            cfg.rotate = options.forceRotate.intValue();
        } else if (options.autoRotate && info != null) {
            cfg.rotate = info.rotation;
            cfg.needMirror = info.orientation == 2;
        }
    }

    private static void setupDecodeScaleCfg(PictureBaseConfig cfg, DecodeOptions options, ImageInfo info) {
        float ratio;
        if (options.mode != null && info != null) {
            switch (options.mode.type) {
                case 0:
                    MaxLenMode maxLenMode = (MaxLenMode) options.mode;
                    cfg.maxDimension = (maxLenMode.len == null || maxLenMode.len.intValue() <= 0) ? Math.max(info.width, info.height) : maxLenMode.len.intValue();
                    break;
                case 1:
                    MinLenMode minLenMode = (MinLenMode) options.mode;
                    cfg.minDimension = (minLenMode.len == null || minLenMode.len.intValue() <= 0) ? Math.min(info.width, info.height) : minLenMode.len.intValue();
                    break;
                case 2:
                    FitRectMode fitRectMode = (FitRectMode) options.mode;
                    if ((fitRectMode.rectWidth >= fitRectMode.rectHeight && info.correctWidth >= info.correctHeight) || (fitRectMode.rectWidth <= fitRectMode.rectHeight && info.correctWidth <= info.correctHeight)) {
                        cfg.maxDimension = Math.max(fitRectMode.rectWidth, fitRectMode.rectHeight);
                        break;
                    } else {
                        if (fitRectMode.rectWidth > fitRectMode.rectHeight) {
                            ratio = ((float) fitRectMode.rectHeight) / ((float) fitRectMode.rectWidth);
                        } else {
                            ratio = ((float) fitRectMode.rectWidth) / ((float) fitRectMode.rectHeight);
                        }
                        cfg.minDimension = (int) (((float) Math.min(fitRectMode.rectWidth, fitRectMode.rectHeight)) * ratio);
                        break;
                    }
                    break;
            }
            if (cfg.maxDimension < 0) {
                cfg.maxDimension = Integer.MAX_VALUE;
            } else if (cfg.minDimension < 0) {
                cfg.minDimension = Integer.MAX_VALUE;
            }
        }
    }

    private static boolean checkNeedNativeProcess(DecodeResult result, DecodeOptions options, ImageInfo info) {
        boolean need;
        if (info.rotation != 0) {
            need = true;
        } else {
            need = false;
        }
        if (!result.isSuccess() || !Config.ARGB_8888.equals(result.bitmap.getConfig())) {
            return false;
        }
        if (!need) {
            int max = Math.max(result.bitmap.getWidth(), result.bitmap.getHeight());
            int min = Math.min(result.bitmap.getWidth(), result.bitmap.getHeight());
            switch (options.mode.type) {
                case 0:
                    MaxLenMode maxLenMode = (MaxLenMode) options.mode;
                    if (maxLenMode.len != null && maxLenMode.len.intValue() > 0 && max > maxLenMode.len.intValue()) {
                        need = true;
                        break;
                    } else {
                        need = false;
                        break;
                    }
                    break;
                case 1:
                    MinLenMode minLenMode = (MinLenMode) options.mode;
                    if (minLenMode.len != null && minLenMode.len.intValue() > 0 && min > minLenMode.len.intValue()) {
                        need = true;
                        break;
                    } else {
                        need = false;
                        break;
                    }
                    break;
                case 2:
                    FitRectMode fitRectMode = (FitRectMode) options.mode;
                    if (result.bitmap.getWidth() <= fitRectMode.rectWidth && result.bitmap.getHeight() <= fitRectMode.rectHeight) {
                        need = false;
                        break;
                    } else {
                        need = true;
                        break;
                    }
            }
        }
        if (need) {
            LogUtils.d(TAG, "checkNeedNativeProcess w: " + result.bitmap.getWidth() + ", h: " + result.bitmap.getHeight() + ", mode: " + options.mode);
        }
        return need;
    }

    private static DecodeOptions createDecodeOptsForCrop(CropOptions options, ImageInfo info) {
        DecodeOptions opts = new DecodeOptions();
        if (options.inPerformance) {
            opts.mode = new MinLenMode(Integer.valueOf(Math.max(options.cutSize.width, options.cutSize.height)));
        } else {
            opts.mode = new MaxLenMode(Integer.valueOf(0));
        }
        opts.autoRotate = options.autoRotate;
        opts.autoUseAshmem = options.autoUseAshmem;
        opts.inPreferQualityOverSpeed = options.inPreferQualityOverSpeed;
        opts.inPreferredConfig = options.inPreferredConfig;
        opts.canUseJpgThumbnailData = options.canUseJpgThumbnailData;
        LogUtils.d(TAG, "createDecodeOptsForCrop cropOpts: " + options + ", decodeOpts: " + opts + ", info: " + info);
        return opts;
    }

    private static DecodeResult doNativeImageCropProcess(File image, CropOptions options, ImageInfo info) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        PictureFileConfig cfg = PictureFileConfig.createDefault();
        setupBaseDecodeOptions(options, cfg);
        cfg.srcFile = image.getAbsolutePath();
        setupCropRotate(cfg, options, info);
        setupCropRegion(cfg, options);
        Bitmap bitmap = null;
        try {
            cfg.debugLog = true;
            cfg.perfLog = true;
            printCfg(cfg, "src: " + image);
            if (info.getFormat() == 5) {
                bitmap = MMNativeEngineApi.cropHevcImage(cfg);
            } else {
                bitmap = MMNativeEngineApi.cropImage(cfg);
            }
        } catch (MMNativeException e) {
            LogUtils.e(TAG, "doNativeImageCropProcess file: " + image + ", opts: " + options + ", info: " + info + ", errCode: " + e.getCode() + ", msg: " + e.getMessage());
        }
        if (bitmap != null) {
            result.code = 0;
            result.bitmap = bitmap;
        }
        LogUtils.d(TAG, "doNativeImageCropProcess cost: " + (System.currentTimeMillis() - start) + ", file: " + image + ", opts: " + options + ", info: " + info);
        return result;
    }

    private static void doImageCropProcess(DecodeResult result, CropOptions options, ImageInfo info) {
        long start = System.currentTimeMillis();
        if (result.code == 0 && Config.ARGB_8888.equals(result.bitmap.getConfig())) {
            BitmapPictureBaseConfig cfg = createSimpleBitmapConfig(result.bitmap);
            setupBaseDecodeOptions(options, cfg);
            setupCropRotate(cfg, options, info);
            setupCropRegion(cfg, options);
            keepMinSideProcess(result, options, info, cfg);
            printCfg(cfg, "");
            Bitmap bitmap = null;
            try {
                cfg.debugLog = true;
                cfg.perfLog = true;
                bitmap = MMNativeEngineApi.cropImage(result.bitmap, cfg);
            } catch (MMNativeException e) {
                LogUtils.e(TAG, "cropImage exp code=" + e.getCode(), e);
            }
            if (bitmap != null) {
                result.code = 0;
                result.bitmap = bitmap;
            } else {
                result.code = -1;
                result.bitmap = null;
            }
        }
        LogUtils.d(TAG, "doCutImageProcess cost: " + (System.currentTimeMillis() - start) + ", opts: " + options + ", info: " + info);
    }

    private static void doYUVCropProcess(DecodeResult result, CropOptions options, ImageInfo info) {
        long start = System.currentTimeMillis();
        if (result.code == 0) {
            BitmapPictureBaseConfig cfg = createSimpleBitmapConfig(result.yuvInfo);
            setupBaseDecodeOptions(options, cfg);
            setupCropRotate(cfg, options, info);
            setupCropRegion(cfg, options);
            keepMinSideProcess(result, options, info, cfg);
            printCfg(cfg, "");
            Bitmap bitmap = null;
            try {
                cfg.debugLog = true;
                cfg.perfLog = true;
                bitmap = MMNativeEngineApi.cropImage(result.yuvData, result.yuvData.length, cfg);
            } catch (MMNativeException e) {
                LogUtils.e(TAG, "cropImage exp code=" + e.getCode(), e);
            }
            if (bitmap != null) {
                result.code = 0;
                result.bitmap = bitmap;
            } else {
                result.code = -1;
                result.bitmap = null;
            }
        }
        LogUtils.d(TAG, "doYUVCropProcess cost: " + (System.currentTimeMillis() - start) + ", opts: " + options + ", info: " + info);
    }

    private static BitmapPictureBaseConfig createSimpleBitmapConfig(Bitmap bitmap) {
        BitmapPictureBaseConfig cfg = BitmapPictureBaseConfig.createDefault(bitmap);
        if (cfg == null) {
            cfg = new BitmapPictureBaseConfig();
            if (bitmap != null) {
                cfg.srcWidth = bitmap.getWidth();
                cfg.srcHeight = bitmap.getHeight();
            }
            cfg.dstWidth = 0;
            cfg.dstHeight = 0;
            cfg.maxDimension = 0;
            cfg.minDimension = 0;
            cfg.pixfmtConfig = ImageType.ARGB_8888.getIndex();
            cfg.cropX = 0;
            cfg.cropY = 0;
            cfg.cropMode = CropMode.MaxVisibility.getIndex();
            cfg.needMirror = false;
            cfg.rotate = 0;
        }
        return cfg;
    }

    private static BitmapPictureBaseConfig createSimpleBitmapConfig(ImageInfo info) {
        BitmapPictureBaseConfig cfg = new BitmapPictureBaseConfig();
        cfg.srcWidth = info.width;
        cfg.srcHeight = info.height;
        cfg.dstWidth = 0;
        cfg.dstHeight = 0;
        cfg.maxDimension = 0;
        cfg.minDimension = 0;
        cfg.pixfmtConfig = ImageType.YUV_420.getIndex();
        cfg.cropX = 0;
        cfg.cropY = 0;
        cfg.cropMode = CropMode.MaxVisibility.getIndex();
        cfg.needMirror = false;
        cfg.rotate = info.rotation;
        return cfg;
    }

    private static void keepMinSideProcess(DecodeResult result, CropOptions options, ImageInfo info, BitmapPictureBaseConfig cfg) {
        float ratioW;
        float ratioH;
        if (isKeepMinScale(options)) {
            int width = result.bitmap.getWidth();
            int height = result.bitmap.getHeight();
            if (info.correctWidth == info.width) {
                ratioW = ((float) width) / ((float) options.cutSize.width);
                ratioH = ((float) height) / ((float) options.cutSize.height);
            } else {
                ratioW = ((float) height) / ((float) options.cutSize.width);
                ratioH = ((float) width) / ((float) options.cutSize.height);
            }
            float ratio = Math.min(ratioH, ratioW);
            if (ratio < 1.0f) {
                float ratio2 = 1.0f / ratio;
                result.bitmap = Bitmap.createScaledBitmap(result.bitmap, (int) (((float) width) * ratio2), (int) (((float) height) * ratio2), true);
                cfg.srcWidth = result.bitmap.getWidth();
                cfg.srcHeight = result.bitmap.getHeight();
            }
        }
    }

    private static boolean isKeepMinScale(CropOptions options) {
        return 1 == options.scaleType;
    }

    private static void setupCropRotate(PictureBaseConfig cfg, CropOptions options, ImageInfo info) {
        if (options.forceRotate != null) {
            cfg.rotate = options.forceRotate.intValue();
        } else if (options.autoRotate && info != null) {
            cfg.rotate = info.rotation;
            cfg.needMirror = info.orientation == 2;
        }
    }

    private static void setupCropRegion(PictureBaseConfig cfg, CropOptions options) {
        if (options.startPoint != null) {
            cfg.cropX = options.startPoint.x;
            cfg.cropY = options.startPoint.y;
        }
        cfg.dstWidth = options.cutSize.width;
        cfg.dstHeight = options.cutSize.height;
        cfg.cropMode = options.cropMode;
    }

    private static void checkFile(File file) {
        boolean z;
        boolean z2 = true;
        if (file != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "input file must not be null");
        if (file.length() <= 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "input file must not be empty file");
    }

    private static void checkData(byte[] data) {
        boolean z;
        boolean z2 = true;
        if (data != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "input data must not be null");
        if (data.length <= 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "input data must not be empty data");
    }

    private static void checkInputStream(InputStream in) {
        Preconditions.checkArgument(in != null, "inputStream must not be null");
    }

    private static void checkBitmap(Bitmap bitmap) {
        boolean z;
        boolean z2 = true;
        if (bitmap != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "input bitmap must not be null");
        if (bitmap.isRecycled()) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "input bitmap must not be recycled");
    }

    private static void checkCutOptions(CropOptions opts) {
        boolean z;
        boolean z2 = true;
        if (opts != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "CropOptions must not be null");
        if (opts.cutSize == null) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "CropOptions.cutSize must not be null");
    }

    private static void checkYUVOptions(File file, BaseDecodeOptions opts) {
        boolean z = true;
        checkFile(file);
        if (opts.resultFormat == 1 && !ImageFileType.isJPEG(file)) {
            z = false;
        }
        Preconditions.checkArgument(z, "Only jpg file support yuv output");
    }

    private static void checkYUVOptions(BaseDecodeOptions opts) {
        boolean z = true;
        if (opts.resultFormat == 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "Only jpg file support yuv output");
    }

    private static void printCfg(PictureBaseConfig cfg, String extra) {
        LogUtils.printCfg(TAG, cfg, extra);
    }
}
