package com.alipay.multimedia.img.decode;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.alipay.multimedia.img.ImageInfo;
import com.alipay.multimedia.img.decode.DecodeOptions.MaxLenMode;
import com.alipay.multimedia.img.decode.DecodeOptions.MinLenMode;
import com.alipay.multimedia.img.utils.ImageAssist;
import com.alipay.multimedia.img.utils.LogUtils;
import com.alipay.multimedia.io.FileUtils;
import com.alipay.multimedia.io.IOUtils;
import java.io.File;
import java.io.InputStream;

public class SystemImageDecoder {
    private static final String TAG = "SystemImageDecoder";

    public static DecodeResult decodeBitmap(File file, DecodeOptions options) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        if (FileUtils.checkFile(file)) {
            ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
            result = InnerDecoder.decodeFile(file, options, info);
            doImageDecodeProcess(result, options, info);
            result.srcInfo = info;
        } else {
            result.code = -1;
        }
        LogUtils.d(TAG, "decodeBitmap file: " + file + ", options: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult decodeBitmap(byte[] data, DecodeOptions options) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        if (data == null || data.length <= 0) {
            result.code = -1;
        } else {
            ImageInfo info = ImageInfo.getImageInfo(data);
            result = InnerDecoder.decodeByteArray(data, info, options);
            doImageDecodeProcess(result, options, info);
            result.srcInfo = info;
        }
        LogUtils.d(TAG, "decodeBitmap data: " + data + ", options: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult decodeBitmap(InputStream in, DecodeOptions options) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        if (in != null) {
            result = decodeBitmap(IOUtils.getBytes(in), options);
        } else {
            result.code = -1;
        }
        LogUtils.d(TAG, "decodeBitmap in: " + in + ", options: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult processBitmap(Bitmap bitmap, int rotation, DecodeOptions options) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        result.code = bitmap == null ? -1 : 0;
        result.bitmap = bitmap;
        doImageDecodeProcess(result, options, ImageInfo.getImageInfo(bitmap, rotation));
        LogUtils.d(TAG, "decodeBitmap bitmap: " + bitmap + ", options: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult processBitmap(byte[] yuv, ImageInfo info, DecodeOptions options) {
        return new DecodeResult();
    }

    private static void doImageDecodeProcess(DecodeResult result, DecodeOptions options, ImageInfo info) {
        int rotate;
        LogUtils.d(TAG, "doImageDecodeProcess result: " + result + ", options: " + options + ", info: " + info);
        if (result.isSuccess()) {
            Bitmap bitmap = result.bitmap;
            int rotation = info.rotation;
            if (info.rotation > 0 || !(options.forceRotate == null || options.forceRotate.intValue() == 0)) {
                Matrix matrix = new Matrix();
                if (options.forceRotate != null) {
                    rotate = options.forceRotate.intValue();
                } else {
                    rotate = rotation;
                }
                matrix.postRotate((float) rotate);
                int times = 0;
                while (true) {
                    int times2 = times;
                    times = times2 + 1;
                    if (times2 < 5) {
                        try {
                            Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                            result.code = 0;
                            result.bitmap = b;
                            bitmap.recycle();
                            return;
                        } catch (Throwable t) {
                            LogUtils.d(TAG, "processBitmap process bitmap error, t: " + t);
                            result.code = -1;
                            LogUtils.d(TAG, "processBitmap degrade, scale: 0.5");
                            matrix.postScale(0.5f, 0.5f);
                            float targetScale = (float) Math.pow(0.5d, (double) times);
                            if (((float) bitmap.getWidth()) * targetScale != 0.0f && ((float) bitmap.getHeight()) * targetScale != 0.0f) {
                                LogUtils.d(TAG, "doImageDecodeProcess targetScale: " + targetScale + ", times: " + times);
                            } else if (result.bitmap != null) {
                                return;
                            } else {
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                }
                if (result.bitmap != null && !result.bitmap.isRecycled()) {
                    result.bitmap.recycle();
                    return;
                }
                return;
            }
            result.code = 0;
            result.bitmap = bitmap;
        }
    }

    public static DecodeResult cropBitmap(File file, CropOptions options) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        if (FileUtils.checkFile(file)) {
            ImageInfo info = ImageInfo.getImageInfo(file.getAbsolutePath());
            result = InnerDecoder.decodeFile(file, createDecodeOptsForCrop(options, info), info);
            doImageCropProcess(result, options, info);
        }
        LogUtils.d(TAG, "cropBitmap file: " + file + ", options: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult cropBitmap(byte[] data, CropOptions options) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        if (data != null && data.length > 0) {
            ImageInfo info = ImageInfo.getImageInfo(data);
            result = InnerDecoder.decodeByteArray(data, info, createDecodeOptsForCrop(options, info));
            doImageCropProcess(result, options, info);
        }
        LogUtils.d(TAG, "cropBitmap data: " + data + ", options: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult cropBitmap(InputStream in, CropOptions options) {
        return cropBitmap(in == null ? null : IOUtils.getBytes(in), options);
    }

    public static DecodeResult processBitmap(Bitmap bitmap, int rotation, CropOptions options) {
        long start = System.currentTimeMillis();
        DecodeResult result = new DecodeResult();
        if (bitmap != null && !bitmap.isRecycled()) {
            result.code = 0;
            result.bitmap = bitmap;
            doImageCropProcess(result, options, ImageInfo.getImageInfo(bitmap, rotation));
        }
        LogUtils.d(TAG, "processBitmap bitmap: " + bitmap + ", options: " + options + ", result: " + result + ", cost: " + (System.currentTimeMillis() - start));
        return result;
    }

    public static DecodeResult processBitmap(byte[] yuvData, ImageInfo info, CropOptions options) {
        return new DecodeResult();
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

    private static void doImageCropProcess(DecodeResult result, CropOptions options, ImageInfo info) {
        LogUtils.d(TAG, "doImageCropProcess start result: " + result + ", options: " + options + ", info: " + info);
        if (options == null || options.systemCropNew) {
            if (result.isSuccess()) {
                Matrix m = new Matrix();
                if (options.forceRotate != null) {
                    m.postRotate((float) options.forceRotate.intValue());
                } else if (options.autoRotate) {
                    m.postRotate((float) info.rotation);
                }
                int bmWidth = result.bitmap.getWidth();
                int bmHeight = result.bitmap.getHeight();
                float scale = ImageAssist.getScale(options.cutSize.width, options.cutSize.height, (float) bmWidth, (float) bmHeight);
                m.postScale(scale, scale);
                try {
                    Bitmap b = Bitmap.createBitmap(result.bitmap, 0, 0, bmWidth, bmHeight, m, true);
                    if (Math.abs(1.0f - scale) > 0.01f) {
                        b = bitmapCrop(b, options.cutSize.width, options.cutSize.height, options);
                    }
                    result.code = 0;
                    result.bitmap = b;
                } catch (Throwable th) {
                    result.code = -1;
                }
            }
            LogUtils.d(TAG, "doImageCropProcess end result: " + result + ", options: " + options + ", info: " + info);
            return;
        }
        doImageCropProcessOld(result, options, info);
    }

    private static Bitmap bitmapCrop(Bitmap bitmap, int destWidth, int destHeight, CropOptions opts) {
        int bmWidth = bitmap.getWidth();
        int bmHeight = bitmap.getHeight();
        int cropW = Math.min(bmWidth, destWidth);
        int cropH = Math.min(bmHeight, destHeight);
        if (ImageAssist.isSuperPic(bmWidth, bmHeight)) {
            if (bmWidth > bmHeight) {
                return Bitmap.createBitmap(bitmap, (bmWidth - cropW) / 2, 0, cropW, cropH);
            }
            return Bitmap.createBitmap(bitmap, 0, 0, cropW, cropH);
        } else if (opts.cropMode != 0) {
            return Bitmap.createBitmap(bitmap, (bmWidth - cropW) / 2, (bmHeight - cropH) / 2, cropW, cropH);
        } else {
            return bitmap;
        }
    }

    private static void doImageCropProcessOld(DecodeResult result, CropOptions options, ImageInfo info) {
        LogUtils.d(TAG, "doImageCropProcessOld start result: " + result + ", options: " + options + ", info: " + info);
        if (result.isSuccess()) {
            Matrix m = new Matrix();
            if (options.forceRotate != null) {
                m.postRotate((float) options.forceRotate.intValue());
            } else if (options.autoRotate) {
                m.postRotate((float) info.rotation);
            }
            int startX = 0;
            int startY = 0;
            if (options.startPoint != null) {
                startX = options.startPoint.x;
                startY = options.startPoint.y;
            }
            try {
                Bitmap b = Bitmap.createBitmap(result.bitmap, startX, startY, Math.min(result.bitmap.getWidth(), options.cutSize.width), Math.min(result.bitmap.getHeight(), options.cutSize.height), m, true);
                result.code = 0;
                result.bitmap = b;
            } catch (Throwable th) {
                result.code = -1;
            }
        }
        LogUtils.d(TAG, "doImageCropProcessOld end result: " + result + ", options: " + options + ", info: " + info);
    }
}
