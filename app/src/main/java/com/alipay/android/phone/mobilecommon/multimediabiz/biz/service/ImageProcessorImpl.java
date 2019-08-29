package com.alipay.android.phone.mobilecommon.multimediabiz.biz.service;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageProcessor;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImageInfo;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImagePlaceHolderOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.APImagePlaceHolderRect;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APCropOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode.APDecodeResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.effect.APCalcColorResult;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeOptions;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.encode.APEncodeResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.FRWBroadcastReceiver;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.ImageProcessorConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.gif.GifProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.processor.CompositeImageProcessor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.CompressImagePerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.ResizeImagePerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AlipayImageParamConverter;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.base.SoLibLoader;
import com.alipay.multimedia.img.decode.CropOptions;
import com.alipay.multimedia.img.decode.DecodeOptions;
import com.alipay.multimedia.img.decode.DecodeResult;
import com.alipay.multimedia.img.decode.GifDecoderWrapper;
import com.alipay.multimedia.img.decode.ImageDecoder;
import com.alipay.multimedia.img.encode.EncodeOptions;
import com.alipay.multimedia.img.encode.EncodeResult;
import com.alipay.multimedia.img.encode.ImageEncoder;
import com.alipay.multimedia.img.utils.ImageAssist;
import com.alipay.streammedia.mmengine.MMNativeEngineApi;
import com.alipay.streammedia.mmengine.MMNativeException;
import com.alipay.streammedia.mmengine.filter.CalcColorResult;
import com.alipay.streammedia.mmengine.picture.gif.GifDecoder;
import java.io.File;
import java.io.InputStream;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class ImageProcessorImpl extends MultimediaImageProcessor {
    private static final String TAG = "ImageProcessorImpl";

    public APEncodeResult compress(File file, APEncodeOptions options) {
        EncodeOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        EncodeResult result = ImageEncoder.compress(file, opts);
        CompressImagePerf.createFrom(start, file, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APEncodeResult compressToTempFile(File file, APEncodeOptions options) {
        if (options != null && TextUtils.isEmpty(options.outputFile)) {
            try {
                options.outputFile = File.createTempFile("mm_img_comp_", ".jpg").getAbsolutePath();
            } catch (Throwable e) {
                Logger.E((String) TAG, e, (String) "calcPictureComplexity exp", new Object[0]);
            }
        }
        EncodeOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        EncodeResult result = ImageEncoder.compress(file, opts);
        CompressImagePerf.createFrom(start, file, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APEncodeResult compress(byte[] bytes, APEncodeOptions options) {
        EncodeOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        EncodeResult result = ImageEncoder.compress(bytes, opts);
        CompressImagePerf.createFrom(start, bytes, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APEncodeResult compress(InputStream inputStream, APEncodeOptions options) {
        EncodeOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        EncodeResult result = ImageEncoder.compress(inputStream, opts);
        CompressImagePerf.createFrom(start, (Bitmap) null, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APEncodeResult compress(Bitmap bitmap, APEncodeOptions options) {
        EncodeOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        EncodeResult result = ImageEncoder.compress(bitmap, opts);
        CompressImagePerf.createFrom(start, bitmap, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APDecodeResult decodeBitmap(File file, APDecodeOptions options) {
        DecodeOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        DecodeResult result = ImageDecoder.decodeBitmap(file, opts);
        ResizeImagePerf.createFrom(start, file, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APDecodeResult decodeBitmap(byte[] bytes, APDecodeOptions options) {
        DecodeOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        DecodeResult result = ImageDecoder.decodeBitmap(bytes, opts);
        ResizeImagePerf.createFrom(start, bytes, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APDecodeResult decodeBitmap(InputStream inputStream, APDecodeOptions options) {
        DecodeOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        DecodeResult result = ImageDecoder.decodeBitmap(inputStream, opts);
        ResizeImagePerf.createFrom(start, (byte[]) null, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APDecodeResult cropBitmap(File file, APCropOptions options) {
        CropOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        DecodeResult result = ImageDecoder.cropBitmap(file, opts);
        ResizeImagePerf.createFrom(start, file, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APDecodeResult cropBitmap(byte[] bytes, APCropOptions options) {
        CropOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        DecodeResult result = ImageDecoder.cropBitmap(bytes, opts);
        ResizeImagePerf.createFrom(start, bytes, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public APDecodeResult cropBitmap(InputStream inputStream, APCropOptions options) {
        CropOptions opts = AlipayImageParamConverter.from(options);
        long start = System.currentTimeMillis();
        DecodeResult result = ImageDecoder.cropBitmap(inputStream, opts);
        ResizeImagePerf.createFrom(start, (byte[]) null, opts, result).submitRemoteAsync();
        return AlipayImageParamConverter.from(result);
    }

    public byte[] pictureOilFilter(Bitmap bitmap) {
        ImageProcessorConf conf = ConfigManager.getInstance().getCommonConfigItem().imageProcessorConf;
        try {
            return MMNativeEngineApi.pictureOilFilter(bitmap, conf.oilPicBrushSize, conf.oilPicCoarseness);
        } catch (MMNativeException e) {
            Logger.E((String) TAG, (Throwable) e, "pictureOilFilter exp code=" + e.getCode(), new Object[0]);
            return null;
        }
    }

    public int calcPictureComplexity(Bitmap bitmap) {
        try {
            return MMNativeEngineApi.calcPictureComplexity(bitmap);
        } catch (MMNativeException e) {
            Logger.E((String) TAG, (Throwable) e, "calcPictureComplexity exp code=" + e.getCode(), new Object[0]);
            return -1;
        }
    }

    public APCalcColorResult calcPictureColor(Bitmap bitmap) {
        CalcColorResult result = null;
        try {
            result = MMNativeEngineApi.calcPictureColor(bitmap);
        } catch (MMNativeException e) {
            Logger.E((String) TAG, (Throwable) e, "calcPictureColor exp code=" + e.getCode(), new Object[0]);
        }
        return AlipayImageParamConverter.from(result);
    }

    public APImagePlaceHolderRect calculateImageRect(APImagePlaceHolderOptions options) {
        return AlipayImageParamConverter.from(ImageAssist.calculateImageRect(AlipayImageParamConverter.from(options)));
    }

    public APImageInfo parseImageInfo(String path) {
        File file = PathUtils.extractFile(PathUtils.extractPath(path));
        if (file == null) {
            return null;
        }
        String filePath = file.getAbsolutePath();
        APImageInfo info = AlipayImageParamConverter.from(ImageInfo.getImageInfo(filePath));
        info.path = filePath;
        return info;
    }

    public Bitmap extractGifFrame(String gifPath, int index) {
        DecodeResult result = null;
        try {
            File gifFile = PathUtils.extractFile(gifPath);
            DecodeOptions options = new DecodeOptions();
            options.frameIndex = index;
            options.frameCheck = false;
            result = GifDecoderWrapper.decode(gifFile, options);
        } catch (Throwable t) {
            Logger.E((String) TAG, t, "extractGifFrame error, path: " + gifPath + ", index: " + index, new Object[0]);
        }
        if (result != null) {
            return result.bitmap;
        }
        return null;
    }

    public APGifInfo compressGif(String path, String business, Bundle extra) {
        return GifProcessor.compressGif(path, business, extra);
    }

    public APImageInfo parseGifInfo(String gifPath) {
        APImageInfo info = new APImageInfo(0, 0, 0);
        GifDecoder decoder = new GifDecoder();
        try {
            decoder.init(gifPath, 4096, 1);
            info.width = decoder.getWidth();
            info.height = decoder.getHeight();
            try {
            } catch (Exception e) {
                Logger.E((String) TAG, (Throwable) e, "parseGifInfo release error, path: " + gifPath, new Object[0]);
            }
        } catch (Exception e2) {
            Logger.E((String) TAG, (Throwable) e2, "parseGifInfo error, path: " + gifPath, new Object[0]);
            try {
            } catch (Exception e3) {
                Logger.E((String) TAG, (Throwable) e3, "parseGifInfo release error, path: " + gifPath, new Object[0]);
            }
        } finally {
            try {
                decoder.release();
            } catch (Exception e4) {
                Logger.E((String) TAG, (Throwable) e4, "parseGifInfo release error, path: " + gifPath, new Object[0]);
            }
        }
        return info;
    }

    public byte[] compositeImage(Bitmap src, Bitmap overlap, Rect rect, Bundle extra) {
        return CompositeImageProcessor.compositeImage(src, overlap, rect, extra);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        IjkMediaPlayer.loadLibrariesOnce(new SoLibLoader());
        FRWBroadcastReceiver.initOnce();
        try {
            GifDecoder.loadLibrariesOnce(new SoLibLoader());
        } catch (MMNativeException e) {
            Logger.E((String) TAG, (Throwable) e, (String) "loadLibrariesOnce error ", new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
