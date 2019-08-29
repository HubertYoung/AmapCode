package com.alipay.multimedia.img.decode;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.base.StaticOptions;
import com.alipay.multimedia.img.decode.DecodeOptions.FitRectMode;
import com.alipay.multimedia.img.decode.DecodeOptions.MaxLenMode;
import com.alipay.multimedia.img.decode.DecodeOptions.MinLenMode;
import com.alipay.multimedia.img.utils.ImageFileType;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.multimedia.io.IOUtils;
import java.io.File;

public class InnerDecoder {
    private static final int MAX_LIMIT_LEN = 10000;
    private static final String TAG = "InnerDecoder";

    public static DecodeResult decodeFile(File file, DecodeOptions options, ImageInfo info) {
        DecodeResult result;
        long start = System.currentTimeMillis();
        if (!options.autoUseAshmem || VERSION.SDK_INT >= 21) {
            result = new DecodeResult();
            result.code = -1;
            decodeBitmap(file, null, result, info, calcDecodeOptions(info, options), options != null ? options.canUseJpgThumbnailData : StaticOptions.useThumbnailData);
        } else {
            result = decodeByteArray(IOUtils.getBytes(file), info, options);
        }
        LogUtils.d(TAG, "decodeFile file: " + file + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    private static void decodeBitmap(File file, byte[] data, DecodeResult result, ImageInfo info, Options o, boolean canUseThumbnailData) {
        Bitmap bitmap;
        boolean jpg = data != null ? ImageFileType.isJPEG(data) : ImageFileType.isJPEG(file);
        do {
            if (data != null) {
                try {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, o);
                } catch (OutOfMemoryError e) {
                    if (!reCalcOOMOpts(result, info, o, jpg)) {
                    }
                } catch (Exception e2) {
                    return;
                }
            } else {
                if (jpg && canUseThumbnailData) {
                    if (StaticOptions.useThumbnailData) {
                        Options thumbOptions = calcThumbnailOptions(info, o);
                        if (thumbOptions != null) {
                            byte[] thumbData = info.getThumbnailInfo().data;
                            bitmap = BitmapFactory.decodeByteArray(thumbData, 0, thumbData.length, thumbOptions);
                        }
                    }
                }
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), o);
            }
            result.bitmap = bitmap;
            if (bitmap != null) {
                result.code = 0;
                return;
            }
            return;
        } while (!reCalcOOMOpts(result, info, o, jpg));
    }

    public static Options calcThumbnailOptions(ImageInfo info, Options options) {
        ImageInfo thumbInfo = info.getThumbnailInfo();
        if (thumbInfo != null) {
            int targetWidth = info.correctWidth / options.inSampleSize;
            int targetHeight = info.correctHeight / options.inSampleSize;
            if (targetWidth - StaticOptions.thumbnail_allow_delta < thumbInfo.correctWidth && targetHeight - StaticOptions.thumbnail_allow_delta < thumbInfo.correctHeight) {
                Options thumbOptions = new Options();
                thumbOptions.inDensity = options.inDensity;
                thumbOptions.inTargetDensity = options.inTargetDensity;
                thumbOptions.inScreenDensity = options.inScreenDensity;
                thumbOptions.inMutable = options.inMutable;
                thumbOptions.inDither = options.inDither;
                thumbOptions.inPreferQualityOverSpeed = options.inPreferQualityOverSpeed;
                thumbOptions.inPreferredConfig = options.inPreferredConfig;
                thumbOptions.inSampleSize = calcMaxLenSampleSize(thumbInfo, Integer.valueOf(Math.max(targetWidth, targetHeight)));
                return thumbOptions;
            }
        }
        return null;
    }

    private static boolean reCalcOOMOpts(DecodeResult result, ImageInfo info, Options o, boolean jpg) {
        if (o.inPreferredConfig == Config.ARGB_8888 && info.rotation == 0 && jpg) {
            o.inPreferredConfig = Config.RGB_565;
            result.extra = 1;
            return true;
        }
        int maxSide = Math.max(info.width, info.height);
        if (maxSide / o.inSampleSize <= 10000 || maxSide <= 0) {
            return false;
        }
        do {
            o.inSampleSize <<= 1;
            if (o.inSampleSize == 0) {
                return false;
            }
        } while (maxSide / o.inSampleSize > 10000);
        result.extra |= 2;
        return true;
    }

    public static DecodeResult decodeByteArray(byte[] data, ImageInfo info, DecodeOptions options) {
        DecodeResult result = new DecodeResult();
        result.code = -1;
        long start = System.currentTimeMillis();
        decodeBitmap(null, data, result, info, calcDecodeOptions(info, options), options != null ? options.canUseJpgThumbnailData : StaticOptions.useThumbnailData);
        LogUtils.d(TAG, "decodeByteArray data: " + data + ", opts: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    private static Options calcDecodeOptions(ImageInfo info, DecodeOptions options) {
        int sampleSize = calcSampleSize(info, options);
        Options o = options.baseOptions == null ? new Options() : options.baseOptions;
        o.inSampleSize = sampleSize;
        o.inPreferredConfig = options.inPreferredConfig;
        o.inDither = true;
        o.inMutable = true;
        if (options.autoUseAshmem && VERSION.SDK_INT < 21) {
            o.inPurgeable = true;
            o.inInputShareable = true;
        }
        if (options.inPreferQualityOverSpeed) {
            o.inPreferQualityOverSpeed = true;
        }
        return o;
    }

    private static int calcSampleSize(ImageInfo info, DecodeOptions options) {
        if (info == null || info.width <= 0 || info.height <= 0 || options.mode == null) {
            return 1;
        }
        switch (options.mode.type) {
            case 1:
                return calcMinLenSampleSize(info, ((MinLenMode) options.mode).len);
            case 2:
                return calcFitRectSampleSize(info, (FitRectMode) options.mode);
            default:
                return calcMaxLenSampleSize(info, ((MaxLenMode) options.mode).len);
        }
    }

    private static int calcFitRectSampleSize(ImageInfo info, FitRectMode mode) {
        int sampleSize = 1;
        if (info != null) {
            if ((mode.rectWidth < mode.rectHeight || info.width < info.height) && (mode.rectWidth > mode.rectHeight || info.width > info.height)) {
                sampleSize = calcMinLenSampleSize(info, Integer.valueOf((int) (((float) Math.min(mode.rectWidth, mode.rectHeight)) * (mode.rectWidth > mode.rectHeight ? ((float) mode.rectHeight) / ((float) mode.rectWidth) : ((float) mode.rectWidth) / ((float) mode.rectHeight)))));
            } else {
                sampleSize = calcMaxLenSampleSize(info, Integer.valueOf(Math.max(mode.rectWidth, mode.rectHeight)));
            }
        }
        LogUtils.d(TAG, "calcFitRectSampleSize info: " + info + ", mode: " + mode + ", sampleSize: " + sampleSize);
        return sampleSize;
    }

    private static int calcMinLenSampleSize(ImageInfo info, Integer len) {
        int sampleSize = getSampleSize(len, Math.min(info.width, info.height), len != null && len.intValue() > 10000);
        LogUtils.d(TAG, "calcMinLenSampleSize info: " + info + ", len: " + len + ", sampleSize: " + sampleSize);
        return sampleSize;
    }

    private static int getSampleSize(Integer len, int side, boolean optimize) {
        int sampleSize = 1;
        if (len != null && len.intValue() > 0 && side > len.intValue()) {
            sampleSize = 2;
            int preSampleSize = 1;
            while (sampleSize != 0 && side / sampleSize >= len.intValue()) {
                preSampleSize = sampleSize;
                sampleSize <<= 1;
            }
            if (!optimize) {
                sampleSize = preSampleSize;
            } else if (sampleSize > 0 && Math.abs((side / preSampleSize) - len.intValue()) < Math.abs((side / sampleSize) - len.intValue())) {
                sampleSize = preSampleSize;
            }
            if (sampleSize <= 0) {
                sampleSize = 1;
            }
        }
        LogUtils.d(TAG, "getSampleSize len: " + len + ", side: " + side + ", optimize: " + optimize + ", sampleSize: " + sampleSize);
        return sampleSize;
    }

    private static int calcMaxLenSampleSize(ImageInfo info, Integer len) {
        int sampleSize = getSampleSize(len, Math.max(info.width, info.height), len != null && len.intValue() > 10000);
        LogUtils.d(TAG, "calcMaxLenSampleSize info: " + info + ", len: " + len + ", sampleSize: " + sampleSize);
        return sampleSize;
    }
}
