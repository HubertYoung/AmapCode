package com.alipay.multimedia.img.encode;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.base.SoLoader;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeResult;
import com.alipay.multimedia.img.decode.GifDecoderWrapper;
import com.alipay.multimedia.img.decode.InnerDecoder;
import com.alipay.multimedia.img.encode.mode.CenterCropMode;
import com.alipay.multimedia.img.encode.mode.MaxLenMode;
import com.alipay.multimedia.img.encode.mode.MinLenMode;
import com.alipay.multimedia.img.encode.mode.SpecificCropMode;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.multimedia.img.utils.Preconditions;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.multimedia.io.IOUtils;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureBaseConfig.ImageType;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureCompressConfig;
import com.alipay.streammedia.mmengine.picture.jpg.JpgFilePictureCompressConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig.CropMode;
import java.io.File;
import java.io.InputStream;

public class NeonImageEncoder {
    public static final int DEFAULT_COMPRESS_DECODE_MAX_LEN = 50000;
    public static final int NATIVE_ERROR_MALLOC_FAILED = NativeExceptionCode.MALLOC_ERROR.getIndex();
    private static final String TAG = "NeonImageEncoder";

    static {
        SoLoader.loadLibraryOnce();
    }

    public static EncodeResult compress(File file, EncodeOptions options) {
        checkFile(file);
        checkEncodeOptions(options);
        EncodeResult result = new EncodeResult();
        long start = System.currentTimeMillis();
        if (ImageFileType.isJPEG(file)) {
            doJpegCompressProcess(result, file, options);
        }
        if (!result.isSuccess()) {
            DecodeResult dr = getDecodeResult(file, options, result.code == NATIVE_ERROR_MALLOC_FAILED);
            if (dr.code == 0) {
                doBitmapCompressProcess(result, dr.bitmap, options, dr.srcInfo);
            }
        }
        LogUtils.d(TAG, "compress file: " + file + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static EncodeResult compress(byte[] data, EncodeOptions options) {
        checkData(data);
        checkEncodeOptions(options);
        EncodeResult result = new EncodeResult();
        long start = System.currentTimeMillis();
        DecodeResult dr = getDecodeResult(data, options);
        if (dr.code == 0) {
            doBitmapCompressProcess(result, dr.bitmap, options, dr.srcInfo);
        }
        LogUtils.d(TAG, "compress data: " + data + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static EncodeResult compress(InputStream in, EncodeOptions options) {
        checkInputStream(in);
        checkEncodeOptions(options);
        EncodeResult result = new EncodeResult();
        long start = System.currentTimeMillis();
        DecodeResult dr = getDecodeResult(in, options);
        if (dr.code == 0) {
            doBitmapCompressProcess(result, dr.bitmap, options, dr.srcInfo);
        }
        LogUtils.d(TAG, "compress in: " + in + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static EncodeResult compress(Bitmap bitmap, EncodeOptions options) {
        checkBitmap(bitmap);
        checkEncodeOptions(options);
        long start = System.currentTimeMillis();
        EncodeResult result = new EncodeResult();
        ImageInfo info = new ImageInfo();
        info.width = bitmap.getWidth();
        info.height = bitmap.getHeight();
        doBitmapCompressProcess(result, bitmap, options, info);
        LogUtils.d(TAG, "compress bitmap: " + bitmap + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    private static void doJpegCompressProcess(EncodeResult result, File file, EncodeOptions options) {
        ImageInfo imageInfo;
        long start = System.currentTimeMillis();
        ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
        JpgFilePictureCompressConfig cfg = JpgFilePictureCompressConfig.createDefault();
        cfg.debugLog = StaticOptions.jniDebug;
        cfg.srcFile = file.getAbsolutePath();
        cfg.qualityLevel = convertQuality(options.quality);
        setupRotation(cfg, options, info);
        setupCropOrScaleInfo(cfg, options, info, null);
        try {
            if (!TextUtils.isEmpty(options.outputFile)) {
                printCfg(cfg, "src: " + file + ", out: " + options.outputFile);
                if (MMNativeEngineApi.CompressImage(cfg, options.outputFile) == 0) {
                    result.code = 0;
                    result.encodeFilePath = options.outputFile;
                }
            } else {
                printCfg(cfg, "src: " + file);
                byte[] data = MMNativeEngineApi.CompressImage(cfg);
                if (data != null) {
                    result.code = 0;
                    result.encodeData = data;
                }
            }
            if (result.code == 0 && options.requireOutputInfo) {
                if (result.encodeData == null) {
                    imageInfo = ImageInfo.getImageInfo(result.encodeFilePath);
                } else {
                    imageInfo = ImageInfo.getImageInfo(result.encodeData);
                }
                result.imageInfo = imageInfo;
            }
            LogUtils.d(TAG, "doJpegCompressProcess file: " + file + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        } catch (MMNativeException e) {
            result.code = e.getCode();
            LogUtils.e(TAG, "doJpegImageDecodeProcess file: " + file + ", opts: " + options + ", info: " + info + ", errCode: " + e.getCode() + ", msg: " + e.getMessage(), e);
        }
    }

    private static void doBitmapCompressProcess(EncodeResult result, Bitmap bitmap, EncodeOptions options, ImageInfo info) {
        long start = System.currentTimeMillis();
        BitmapPictureCompressConfig cfg = createSimpleBitmapPictureCompressConfig(bitmap);
        cfg.debugLog = StaticOptions.jniDebug;
        cfg.qualityLevel = convertQuality(options.quality);
        setupRotation(cfg, options, info);
        if (bitmap.getConfig() == null) {
            cfg.pixfmtConfig = ImageType.ARGB_8888.getIndex();
        }
        setupCropOrScaleInfo(cfg, options, info, bitmap);
        printCfg(cfg, "");
        byte[] data = null;
        try {
            data = 2 == info.getFormat() ? MMNativeEngineApi.CompressImage(Bitmap.createBitmap(bitmap), cfg) : MMNativeEngineApi.CompressImage(bitmap, cfg);
        } catch (MMNativeException e) {
            LogUtils.e(TAG, "CompressImage exp code=" + e.getCode(), e);
        } catch (Exception e2) {
            LogUtils.e(TAG, "CompressImage exp =", e2);
        }
        if (data != null) {
            result.code = 0;
            result.encodeData = data;
            if (!TextUtils.isEmpty(options.outputFile)) {
                if (FileUtils.safeCopyToFile(result.encodeData, new File(options.outputFile))) {
                    result.encodeFilePath = options.outputFile;
                } else {
                    result.code = -1;
                }
            }
        }
        if (result.code == 0 && options.requireOutputInfo) {
            result.imageInfo = ImageInfo.getImageInfo(result.encodeData);
        }
        LogUtils.d(TAG, "doBitmapCompressProcess bitmap: " + bitmap + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
    }

    private static BitmapPictureCompressConfig createSimpleBitmapPictureCompressConfig(Bitmap bitmap) {
        BitmapPictureCompressConfig cfg = BitmapPictureCompressConfig.createDefault(bitmap);
        if (cfg == null) {
            cfg = new BitmapPictureCompressConfig();
            if (bitmap != null) {
                cfg.srcWidth = bitmap.getWidth();
                cfg.srcHeight = bitmap.getHeight();
            }
            cfg.pixfmtConfig = ImageType.ARGB_8888.getIndex();
            cfg.dstWidth = 0;
            cfg.dstHeight = 0;
            cfg.maxDimension = 0;
            cfg.minDimension = 0;
            cfg.qualityLevel = 1;
            cfg.cropX = 0;
            cfg.cropY = 0;
            cfg.rotate = 0;
            cfg.cropMode = CropMode.MaxVisibility.getIndex();
            cfg.needMirror = false;
        }
        return cfg;
    }

    private static int convertQuality(int quality) {
        switch (quality) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    private static void setupRotation(PictureBaseConfig cfg, EncodeOptions options, ImageInfo info) {
        if (options.forceRotate != null) {
            cfg.rotate = options.forceRotate.intValue();
        } else if (options.autoRotate && info != null) {
            cfg.rotate = info.rotation;
        }
    }

    private static void setupCropOrScaleInfo(PictureBaseConfig cfg, EncodeOptions options, ImageInfo info, Bitmap bitmap) {
        switch (options.mode.type) {
            case 0:
                cfg.maxDimension = ((MaxLenMode) options.mode).len;
                cfg.minDimension = 0;
                return;
            case 1:
                cfg.maxDimension = 0;
                cfg.minDimension = ((MinLenMode) options.mode).len;
                return;
            case 2:
                CenterCropMode centerCropMode = (CenterCropMode) options.mode;
                cfg.dstWidth = centerCropMode.width;
                cfg.dstHeight = centerCropMode.height;
                return;
            case 3:
                SpecificCropMode specificCropMode = (SpecificCropMode) options.mode;
                cfg.cropX = specificCropMode.x;
                cfg.cropY = specificCropMode.y;
                cfg.dstWidth = specificCropMode.width;
                cfg.dstHeight = specificCropMode.height;
                return;
            case 4:
                int maxDimemsion = 50000;
                if (bitmap != null) {
                    maxDimemsion = Math.max(bitmap.getWidth(), bitmap.getHeight());
                } else if (info != null && info.width > 0 && info.height > 0) {
                    maxDimemsion = Math.max(info.width, info.height);
                }
                cfg.maxDimension = maxDimemsion;
                cfg.minDimension = 0;
                return;
            default:
                return;
        }
    }

    private static DecodeResult getDecodeResult(InputStream in, EncodeOptions options) {
        return getDecodeResult(IOUtils.getBytes(in), options);
    }

    private static DecodeResult getDecodeResult(byte[] data, EncodeOptions options) {
        DecodeResult result;
        ImageInfo info = ImageInfo.getImageInfo(data);
        DecodeOptions decodeOptions = makeDecodeOptions(options, info, false);
        if (ImageFileType.isGif(data)) {
            result = GifDecoderWrapper.decode(data, decodeOptions);
        } else {
            result = InnerDecoder.decodeByteArray(data, info, decodeOptions);
        }
        result.srcInfo = info;
        return result;
    }

    private static DecodeResult getDecodeResult(File file, EncodeOptions options, boolean memLimit) {
        DecodeResult result;
        ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
        DecodeOptions decodeOptions = makeDecodeOptions(options, info, memLimit);
        if (ImageFileType.isGif(file)) {
            result = GifDecoderWrapper.decode(file, decodeOptions);
        } else {
            result = InnerDecoder.decodeFile(file, decodeOptions, info);
        }
        result.srcInfo = info;
        return result;
    }

    private static DecodeOptions makeDecodeOptions(EncodeOptions options, ImageInfo info, boolean memLimit) {
        DecodeOptions opts = new DecodeOptions();
        if (!memLimit) {
            opts.mode = new DecodeOptions.MaxLenMode(Integer.valueOf(0));
        } else if (options.mode instanceof MaxLenMode) {
            opts.mode = new DecodeOptions.MaxLenMode(Integer.valueOf(((MaxLenMode) options.mode).len));
        } else if (options.mode instanceof MinLenMode) {
            opts.mode = new DecodeOptions.MinLenMode(Integer.valueOf(((MinLenMode) options.mode).len));
        } else if (options.mode instanceof CenterCropMode) {
            CenterCropMode mode = (CenterCropMode) options.mode;
            opts.mode = new DecodeOptions.MinLenMode(Integer.valueOf(Math.min(mode.width, mode.height)));
        } else {
            opts.mode = new DecodeOptions.MaxLenMode(Integer.valueOf(1280));
        }
        LogUtils.d(TAG, "makeDecodeOptions inOpts: " + options + ", info: " + info + ", memLimit: " + memLimit + ", outOpts: " + opts);
        return opts;
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

    private static void checkEncodeOptions(EncodeOptions opts) {
        boolean z;
        boolean z2 = true;
        if (opts != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "EncodeOptions must not be null");
        if (opts.mode == null) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "EncodeOptions.mode must not be null");
    }

    private static void checkBitmap(Bitmap bitmap) {
        boolean z;
        boolean z2 = true;
        if (bitmap != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "bitmap must not be null!!");
        if (bitmap.isRecycled()) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "bitmap has been recycled!!");
    }

    private static void printCfg(PictureBaseConfig cfg, String extra) {
        LogUtils.printCfg(TAG, cfg, extra);
    }
}
