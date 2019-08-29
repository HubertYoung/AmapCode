package com.alipay.streammedia.mmengine;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import com.alipay.streammedia.mmengine.MMNativeException.NativeExceptionCode;
import com.alipay.streammedia.mmengine.audio.AudioBaseResult;
import com.alipay.streammedia.mmengine.filter.CalcColorResult;
import com.alipay.streammedia.mmengine.filter.CompositeConfig;
import com.alipay.streammedia.mmengine.filter.WatermarkConfig;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureBaseConfig;
import com.alipay.streammedia.mmengine.picture.jpg.BitmapPictureCompressConfig;
import com.alipay.streammedia.mmengine.picture.jpg.JpgFilePictureCompressConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureBaseResult;
import com.alipay.streammedia.mmengine.picture.jpg.PictureCompressResult;
import com.alipay.streammedia.mmengine.picture.jpg.PictureFileConfig;
import com.alipay.streammedia.mmengine.picture.jpg.PictureHevcFileInfo;
import com.alipay.streammedia.mmengine.picture.jpg.PictureJpegProgAttribute.ProgLayers;
import com.alipay.streammedia.mmengine.picture.scale.ScaleConfig;
import com.alipay.streammedia.mmengine.picture.scale.ScaleFilter;
import com.alipay.streammedia.mmengine.video.VideoCompressConfig;
import com.alipay.streammedia.mmengine.video.VideoInfo;
import com.alipay.streammedia.utils.SoLoadLock;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import tv.danmaku.ijk.media.player.IjkLibLoader;

public class MMNativeEngineApi extends MMNativeEngineNativeWrapper {
    private static final int AAC = 86018;
    private static final int H264 = 28;
    public static final int MAXDIMENSION = 1280;
    private static final String TAG = "NativeEngine";
    private static final int YUV420P = 0;
    private static final int YUVJ420P = 12;
    private static volatile boolean mIsLibLoaded = false;
    public static final SparseArray<Float> sFilterScaleMap;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public final void loadLibrary(String libName) {
            System.loadLibrary(libName);
        }
    };

    static {
        SparseArray<Float> sparseArray = new SparseArray<>();
        sFilterScaleMap = sparseArray;
        sparseArray.put(ScaleFilter.UPSCALE_1_5X.ordinal(), Float.valueOf(1.5f));
        sFilterScaleMap.put(ScaleFilter.UPSCALE_2X.ordinal(), Float.valueOf(2.0f));
        sFilterScaleMap.put(ScaleFilter.UPSCALE_4X.ordinal(), Float.valueOf(4.0f));
    }

    public static void loadLibrariesOnce(IjkLibLoader libLoader) {
        synchronized (SoLoadLock.class) {
            if (!mIsLibLoaded) {
                if (libLoader == null) {
                    libLoader = sLocalLibLoader;
                }
                try {
                    libLoader.loadLibrary("ijkmmengine");
                    mIsLibLoaded = true;
                } catch (Error e) {
                    throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
    }

    public static Bitmap decompressImage(byte[] jpg420pData, PictureBaseConfig cfg) {
        cfg.srcWidth = 0;
        cfg.srcHeight = 0;
        if (cfg.dstWidth == 0 && cfg.dstHeight == 0 && cfg.maxDimension == 0 && cfg.minDimension == 0) {
            Log.d(TAG, "decompressImage not specifies the width/height of the target!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        } else if (cfg.maxDimension == 0 || cfg.minDimension == 0) {
            try {
                PictureBaseResult ret = decompressByByteArray(cfg, jpg420pData, jpg420pData.length);
                checkRet(ret);
                return ret.pic;
            } catch (Error e) {
                throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        } else {
            Log.d(TAG, "decompressImage specifies the maxDimension minDimension conflict!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        }
    }

    public static Bitmap decompressHevcImage(byte[] jpg420pData, PictureBaseConfig cfg) {
        cfg.srcWidth = 0;
        cfg.srcHeight = 0;
        if (cfg.dstWidth == 0 && cfg.dstHeight == 0 && cfg.maxDimension == 0 && cfg.minDimension == 0) {
            Log.d(TAG, "decompressImage not specifies the width/height of the target!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        } else if (cfg.maxDimension == 0 || cfg.minDimension == 0) {
            try {
                PictureBaseResult ret = hevcDecompressByByteArray(cfg, jpg420pData, jpg420pData.length);
                checkRet(ret);
                return ret.pic;
            } catch (Error e) {
                throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        } else {
            Log.d(TAG, "decompressImage specifies the maxDimension minDimension conflict!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        }
    }

    public static Bitmap decompressHevcFile(PictureFileConfig cfg) {
        cfg.srcWidth = 0;
        cfg.srcHeight = 0;
        if (cfg.dstWidth == 0 && cfg.dstHeight == 0 && cfg.maxDimension == 0 && cfg.minDimension == 0) {
            Log.d(TAG, "decompressImage not specifies the width/height of the target!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        } else if (cfg.maxDimension == 0 || cfg.minDimension == 0) {
            try {
                PictureBaseResult ret = hevcFileDecompress(cfg);
                checkRet(ret);
                return ret.pic;
            } catch (Error e) {
                throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        } else {
            Log.d(TAG, "decompressImage specifies the maxDimension minDimension conflict!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        }
    }

    public static Bitmap decompressImage(PictureFileConfig cfg) {
        cfg.srcWidth = 0;
        cfg.srcHeight = 0;
        if (cfg.dstWidth == 0 && cfg.dstHeight == 0 && cfg.maxDimension == 0 && cfg.minDimension == 0) {
            Log.d(TAG, "decompressImage not specifies the width/height of the target!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        } else if (cfg.maxDimension == 0 || cfg.minDimension == 0) {
            try {
                PictureBaseResult ret = jpgFileDecompress(cfg);
                checkRet(ret);
                return ret.pic;
            } catch (Error e) {
                throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        } else {
            Log.d(TAG, "decompressImage specifies the maxDimension minDimension conflict!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        }
    }

    public static Bitmap decompressImageByFfmpeg(PictureFileConfig cfg) {
        cfg.srcWidth = 0;
        cfg.srcHeight = 0;
        if (cfg.dstWidth == 0 && cfg.dstHeight == 0 && cfg.maxDimension == 0 && cfg.minDimension == 0) {
            Log.d(TAG, "decompressImage not specifies the width/height of the target!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        } else if (cfg.maxDimension == 0 || cfg.minDimension == 0) {
            try {
                PictureBaseResult ret = PictureFileDecompress(cfg);
                checkRet(ret);
                return ret.pic;
            } catch (Error e) {
                throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        } else {
            Log.d(TAG, "decompressImage specifies the maxDimension minDimension conflict!\n");
            throw new MMNativeException(NativeExceptionCode.PIXELS_ERROR);
        }
    }

    public static PictureCompressResult decompressImageToYuv420(PictureFileConfig cfg) {
        try {
            PictureCompressResult ret = jpgFileDecompressToYuv420(cfg);
            checkRet(ret);
            return ret;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static Bitmap cropImage(PictureFileConfig cfg) {
        return decompressImage(cfg);
    }

    public static Bitmap cropHevcImage(PictureFileConfig cfg) {
        return decompressHevcFile(cfg);
    }

    public static Bitmap cropImage(Bitmap data, BitmapPictureBaseConfig cfg) {
        try {
            PictureBaseResult ret = bitmapCrop(cfg, data);
            data.recycle();
            checkRet(ret);
            return ret.pic;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static Bitmap cropImage(Bitmap data, BitmapPictureBaseConfig cfg, boolean isBitmap) {
        try {
            ByteBuffer mImageBuffer = ByteBuffer.allocate(data.getHeight() * data.getWidth() * 4);
            data.copyPixelsToBuffer(mImageBuffer);
            data.recycle();
            PictureBaseResult ret = byteArrayCrop(cfg, mImageBuffer.array(), mImageBuffer.capacity());
            mImageBuffer.clear();
            checkRet(ret);
            return ret.pic;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static Bitmap cropImage(byte[] data, int data_size, BitmapPictureBaseConfig cfg) {
        try {
            PictureBaseResult ret = byteArrayCrop(cfg, data, data_size);
            checkRet(ret);
            return ret.pic;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static int CompressImage(JpgFilePictureCompressConfig cfg, String outFile) {
        try {
            int retCode = jpgFileCompressToFile(cfg, outFile);
            if (retCode >= 0) {
                return retCode;
            }
            throw new MMNativeException(retCode);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] CompressImage(JpgFilePictureCompressConfig cfg) {
        try {
            PictureCompressResult ret = jpgFileCompressToByte(cfg);
            checkRet(ret);
            return ret.data;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] CompressImageByProgress(JpgFilePictureCompressConfig cfg, ProgLayers layers) {
        try {
            PictureCompressResult ret = jpgFileCompressToByteByProg(cfg, layers.getIndex());
            checkRet(ret);
            return ret.data;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] CompressImage(Bitmap data, BitmapPictureCompressConfig cfg) {
        try {
            PictureCompressResult ret = bitmapCompressToByte(cfg, data);
            checkRet(ret);
            return ret.data;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] CompressImagebyProgress(Bitmap data, BitmapPictureCompressConfig cfg, ProgLayers layers) {
        try {
            PictureCompressResult ret = bitmapCompressToByteByProg(cfg, data, layers.getIndex());
            checkRet(ret);
            return ret.data;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] CompressImageNoChangePixel(Bitmap data, int qualityLevel) {
        try {
            BitmapPictureCompressConfig cfg = BitmapPictureCompressConfig.createDefault(data);
            cfg.dstWidth = data.getWidth();
            cfg.dstHeight = data.getHeight();
            cfg.qualityLevel = qualityLevel;
            PictureCompressResult ret = bitmapCompressToByteNoScale(cfg, data);
            checkRet(ret);
            return ret.data;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] CompressImageNoChangePixel(JpgFilePictureCompressConfig cfg) {
        try {
            PictureCompressResult ret = jpgFileCompressToByteNoScale(cfg);
            checkRet(ret);
            return ret.data;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] CompressImageNoChangePixelByProgress(Bitmap data, int qualityLevel, ProgLayers layers) {
        try {
            BitmapPictureCompressConfig cfg = BitmapPictureCompressConfig.createDefault(data);
            cfg.dstWidth = data.getWidth();
            cfg.dstHeight = data.getHeight();
            cfg.qualityLevel = qualityLevel;
            PictureCompressResult ret = bitmapCompressToByteNoScaleByProg(cfg, data, layers.getIndex());
            checkRet(ret);
            return ret.data;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] CompressImageNoChangePixelByProgress(JpgFilePictureCompressConfig cfg, ProgLayers layers) {
        try {
            PictureCompressResult ret = jpgFileCompressToByteNoScaleByProg(cfg, layers.getIndex());
            checkRet(ret);
            return ret.data;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static PictureBaseResult calculateImageRect(PictureFileConfig cfg) {
        try {
            return getBestPixelInfo(cfg);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static PictureBaseResult calculateImageRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, int rotate) {
        try {
            PictureFileConfig cfg = PictureFileConfig.createDefault();
            cfg.srcWidth = srcWidth;
            cfg.srcHeight = srcHeight;
            cfg.dstWidth = dstWidth;
            cfg.dstHeight = dstHeight;
            cfg.rotate = rotate;
            return getBestPixelInfo(cfg);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static PictureHevcFileInfo getHevcFileInfo(PictureFileConfig cfg) {
        try {
            return hevcFileGetInfo(cfg);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static int getHevcDecoderVersion() {
        try {
            return hevcDecoderVersion();
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static int audioNsAgcProcessInit(int sampleRate, int frameSize, int channel) {
        try {
            return audioProcessInit(sampleRate, frameSize, channel);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] audioNsAgcProcess(byte[] sampleData) {
        try {
            AudioBaseResult ret = audioProcess(sampleData, sampleData.length);
            if (ret.retCode >= 0) {
                return ret.data;
            }
            throw new MMNativeException(ret.retCode);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static void audioNsAgcProcessDestory() {
        try {
            audioProcessUninit();
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static int audioOpusEncoderInit(int sampleRate, int frameSize, int channel, boolean needNsAgc) {
        try {
            return audioOpusEncInit(sampleRate, frameSize, channel, needNsAgc);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] audioOpusEncoder(byte[] sampleData) {
        try {
            AudioBaseResult ret = audioOpusEncoder(sampleData, sampleData.length);
            if (ret.retCode >= 0) {
                return ret.data;
            }
            throw new MMNativeException(ret.retCode);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static void audioOpusEncoderDestory() {
        try {
            audioOpusEncUninit();
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static int audioOpusDecoderInit(int sampleRate, int frameSize, int channel) {
        try {
            return audioOpusDecInit(sampleRate, frameSize, channel);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] audioOpusDecoder(byte[] sampleData) {
        try {
            AudioBaseResult ret = audioOpusDecoder(sampleData, sampleData.length);
            if (ret.retCode >= 0) {
                return ret.data;
            }
            throw new MMNativeException(ret.retCode);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static void audioOpusDecoderDestory() {
        try {
            audioOpusDecUninit();
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static byte[] pictureOilFilter(Bitmap data, int brushSize, int coarseness) {
        try {
            byte[] ret = pictureOil(data, brushSize, coarseness);
            data.recycle();
            return ret;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static int calcPictureComplexity(Bitmap data) {
        try {
            int ret = pictureCalcComplexity(data);
            data.recycle();
            return ret;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static CalcColorResult calcPictureColor(Bitmap data) {
        try {
            CalcColorResult ret = pictureCalcColor(data);
            data.recycle();
            return ret;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static int videoCompress(VideoCompressConfig cfg) {
        try {
            return videoCompressByCfg(cfg);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static long getCurCompressPts(long videoId) {
        try {
            return getCurCompressPtsNative(videoId);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static VideoInfo getVideoInfo(String videoFile) {
        try {
            VideoInfo info = dumpVideoInfo(videoFile);
            info.H264 = 28;
            info.AAC = AAC;
            return info;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static void getVideoDumpPts(String videoFile) {
        try {
            videoDumpPts(videoFile);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static List<Integer> getSupportPixList() {
        ArrayList supportPix = new ArrayList();
        supportPix.add(Integer.valueOf(0));
        supportPix.add(Integer.valueOf(12));
        return supportPix;
    }

    public int getH264Code() {
        return 28;
    }

    public int getAACCode() {
        return AAC;
    }

    private static void checkRet(PictureBaseResult ret) {
        if (ret == null) {
            throw new MMNativeException(NativeExceptionCode.MALLOC_ERROR.getIndex());
        } else if (ret.retCode < 0) {
            throw new MMNativeException(ret.retCode);
        }
    }

    private static void checkRet(PictureCompressResult ret) {
        if (ret == null) {
            throw new MMNativeException(NativeExceptionCode.MALLOC_ERROR.getIndex());
        } else if (ret.retCode < 0) {
            throw new MMNativeException(ret.retCode);
        }
    }

    public static void setOptionFlags(long flags) {
        try {
            setOptionFlagsNative(flags);
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static long getOptionFlags() {
        try {
            return getOptionFlagsNative();
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public static void composite(CompositeConfig config) {
        checkCompositeParams(config);
        try {
            int ret = compositeNative(config.getSrc(), config.getSuperimosed(), config.getX(), config.getY(), config.getSuperimosed_w(), config.getSuperimosed_h());
            if (ret != 0) {
                throw new MMNativeException(ret);
            }
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    private static void checkCompositeParams(CompositeConfig c) {
        if (c == null || c.getSrc() == null || c.getSuperimosed() == null || c.getX() < 0 || c.getY() < 0 || c.getSuperimosed_w() < 0 || c.getSuperimosed_h() < 0 || c.getX() + c.getSuperimosed().getWidth() > c.getSrc().getWidth() || c.getY() + c.getSuperimosed().getHeight() > c.getSrc().getHeight() || c.getSuperimosed().getWidth() / c.getSuperimosed().getHeight() != c.getSuperimosed_w() / c.getSuperimosed_h()) {
            throw new MMNativeException(NativeExceptionCode.PARAM_ERROR);
        }
    }

    private static void checkWatermarkParams(WatermarkConfig c) {
        if (c == null || c.getSrc() == null || c.getWatermark() == null || c.getX() < 0 || c.getY() < 0 || c.getWatermarkIntervalWidth() < 0 || c.getWatermarkIntervalHeight() < 0 || c.getAlpha() < 0 || c.getAlpha() > 255) {
            throw new MMNativeException(NativeExceptionCode.PARAM_ERROR);
        }
    }

    public static Bitmap scaleImage(Bitmap srcImg, ScaleFilter scaleFilter) {
        try {
            return scaleImageByNative(ScaleConfig.createDefault(srcImg, (double) sFilterScaleMap.get(scaleFilter.ordinal()).floatValue()), srcImg).pic;
        } catch (Error e) {
            throw new MMNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }
}
