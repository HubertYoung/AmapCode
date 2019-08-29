package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.FalconFacade;
import com.alipay.multimedia.img.encode.EncodeOptions;
import com.alipay.multimedia.img.encode.EncodeResult;
import com.alipay.multimedia.img.encode.ImageEncoder;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtils {
    private static final String a = ImageUtils.class.getSimpleName();

    public static int[] getScaleScreenRect(Context context, float scale) {
        int width = 1280;
        int height = 1280;
        if (context != null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            if (scale < 0.0f) {
                scale = 1.0f;
            }
            width = (int) (((float) metrics.widthPixels) * scale);
            height = (int) (((float) metrics.heightPixels) * scale);
        }
        return new int[]{width, height};
    }

    public static int[] calculateDesWidthHeight(String path) {
        return FalconFacade.get().calculateDesWidthHeight(path);
    }

    public static Bitmap decodeBitmap(InputStream is) {
        return decodeBitmap(is, (Options) null);
    }

    public static Bitmap decodeBitmap(InputStream is, Options options) {
        if (is == null) {
            return null;
        }
        if (options == null) {
            return BitmapFactory.decodeStream(is);
        }
        try {
            return BitmapFactory.decodeStream(is, new Rect(), options);
        } catch (Throwable e) {
            Logger.E(a, e, (String) "decodeBitmap inputStream failed", new Object[0]);
            return null;
        }
    }

    public static Bitmap decodeBitmap(File file, Rect rect) {
        Bitmap bitmap = null;
        if (FileUtils.checkFile(file)) {
            FileInputStream fis = null;
            if (rect == null) {
                try {
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                } catch (Throwable th) {
                    e = th;
                }
            } else {
                FileInputStream fis2 = new FileInputStream(file);
                try {
                    bitmap = BitmapFactory.decodeStream(fis2, rect, null);
                    fis = fis2;
                } catch (Throwable th2) {
                    th = th2;
                    fis = fis2;
                    IOUtils.closeQuietly((InputStream) fis);
                    throw th;
                }
            }
            IOUtils.closeQuietly((InputStream) fis);
        }
        return bitmap;
    }

    public static Drawable makeDrawable(Bitmap bitmap, Rect padding, String srcName) {
        if (!checkBitmap(bitmap)) {
            return null;
        }
        if (!NinePatch.isNinePatchChunk(bitmap.getNinePatchChunk())) {
            return new BitmapDrawable(AppUtils.getResources(), bitmap);
        }
        return new NinePatchDrawable(AppUtils.getResources(), bitmap, bitmap.getNinePatchChunk(), padding, srcName);
    }

    public static Drawable decodeDrawable(File file) {
        Rect padding = new Rect();
        Bitmap bitmap = decodeBitmap(file, padding);
        if (checkBitmap(bitmap)) {
            return makeDrawable(bitmap, padding, null);
        }
        return null;
    }

    public static boolean compressBitmap(Bitmap bitmap, OutputStream os, int quality, boolean forceJPEG) {
        boolean ret = false;
        try {
            if (checkBitmap(bitmap)) {
                if (forceJPEG || !bitmap.hasAlpha()) {
                    ret = bitmap.compress(CompressFormat.JPEG, quality, os);
                } else {
                    ret = bitmap.compress(CompressFormat.PNG, 100, os);
                }
            }
        } catch (Exception e) {
            Logger.E(a, (Throwable) e, (String) "compressBitmap error", new Object[0]);
        } finally {
            IOUtils.closeQuietly(os);
        }
        return ret;
    }

    public static boolean compressBitmap(Bitmap bitmap, OutputStream os) {
        return compressBitmap(bitmap, os, 80, false);
    }

    public static boolean compressJpg(Bitmap bitmap, String outPath) {
        return compressJpg(bitmap, outPath, 100);
    }

    public static boolean compressJpg(Bitmap bitmap, String outPath, int quality) {
        boolean z = false;
        if (bitmap == null || TextUtils.isEmpty(outPath)) {
            return z;
        }
        if (ConfigManager.getInstance().getCommonConfigItem().takePictureUseNativeCompress == 1) {
            EncodeOptions options = new EncodeOptions();
            options.outputFile = outPath;
            options.quality = 2;
            try {
                EncodeResult result = ImageEncoder.compress(bitmap, options);
                Logger.D(a, "compressJpg bitmap: " + bitmap + ", outPath: " + outPath + ", result: " + result, new Object[0]);
                return result.isSuccess();
            } catch (Throwable e) {
                Logger.E(a, e, "compressJpg native error, bitmap: " + bitmap + ", outPath: " + outPath, new Object[z]);
                return z;
            }
        } else {
            FileOutputStream fos = null;
            try {
                FileOutputStream fos2 = new FileOutputStream(outPath);
                if (quality <= 0 || quality >= 100) {
                    quality = 100;
                }
                try {
                    boolean compressBitmap = compressBitmap(bitmap, fos2, quality, true);
                    com.alipay.multimedia.io.IOUtils.closeQuietly(fos2);
                    return compressBitmap;
                } catch (Throwable th) {
                    th = th;
                    fos = fos2;
                    com.alipay.multimedia.io.IOUtils.closeQuietly(fos);
                    throw th;
                }
            } catch (Throwable th2) {
                e = th2;
                Logger.E(a, e, "compressJpg sys error, bitmap: " + bitmap + ", outPath: " + outPath, new Object[0]);
                com.alipay.multimedia.io.IOUtils.closeQuietly(fos);
                return z;
            }
        }
    }

    public static boolean checkBitmap(Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled();
    }

    public static CutScaleType calcCutScaleType(Size size, float scale, int maxLen) {
        if (size != null && maxLen > 0 && size.getWidth() > 0 && size.getHeight() > 0) {
            double delta = ConfigManager.getInstance().getCommonConfigItem().imageProcessorConf.correctCutScaleTypeDelta;
            int[] wh = FalconFacade.get().calculateCutImageRect(size.getWidth(), size.getHeight(), maxLen, scale, null);
            double srcRatio = ((double) size.getWidth()) / ((double) size.getHeight());
            double newRatio = ((double) wh[0]) / ((double) wh[1]);
            if (Math.abs((((double) wh[0]) / ((double) size.getWidth())) - (((double) wh[1]) / ((double) size.getHeight()))) >= delta || Math.abs(srcRatio - newRatio) >= delta) {
                return CutScaleType.CENTER_CROP;
            }
        }
        return CutScaleType.KEEP_RATIO;
    }

    public static Bitmap zoomBitmap(Bitmap src, int targetWidth, int targetHeight) {
        if (src == null || src.isRecycled() || (targetWidth == 0 && targetHeight == 0)) {
            return null;
        }
        float scale = getScale(targetWidth, targetHeight, (float) src.getWidth(), (float) src.getHeight());
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap scaleBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        Logger.D(a, "zoomBitmap newWidth:" + scaleBitmap.getWidth() + ", newHeight:" + scaleBitmap.getHeight(), new Object[0]);
        return scaleBitmap;
    }

    public static float getScale(int targetWidth, int targetHeight, float bitmapWidth, float bitmapHeight) {
        if (targetWidth > 0 && targetHeight > 0) {
            return Math.min(((float) targetWidth) / bitmapWidth, ((float) targetHeight) / bitmapHeight);
        }
        if (targetWidth > 0) {
            return ((float) targetWidth) / bitmapWidth;
        }
        if (targetHeight > 0) {
            return ((float) targetHeight) / bitmapHeight;
        }
        return 1.0f;
    }

    public static boolean isImage(String filePath) {
        long start = System.currentTimeMillis();
        Options option_decodeOptions = new Options();
        option_decodeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, option_decodeOptions);
        Logger.D(a, "isImage outMimeType:" + option_decodeOptions.outMimeType + ", newWidth:" + option_decodeOptions.outWidth + ", newHeight:" + option_decodeOptions.outHeight, new Object[0]);
        if (TextUtils.isEmpty(option_decodeOptions.outMimeType) || option_decodeOptions.outWidth <= 0 || option_decodeOptions.outHeight <= 0) {
            Logger.I(a, "isImage false cost:" + (System.currentTimeMillis() - start), new Object[0]);
            return false;
        }
        Logger.I(a, "isImage yes cost:" + (System.currentTimeMillis() - start), new Object[0]);
        return true;
    }

    public static Bitmap decodeBitmapByFalcon(File file) {
        return decodeBitmapByFalcon(file, 0, 0);
    }

    public static Bitmap decodeBitmapByFalcon(File file, int width, int height) {
        try {
            return FalconFacade.get().cutImageKeepRatio(file, width, height);
        } catch (Throwable e) {
            Logger.E(a, e, (String) "decodeBitmapByFalcon err", new Object[0]);
            return null;
        }
    }

    public static Bitmap decodeBitmapByFalcon(byte[] fileData) {
        try {
            return FalconFacade.get().cutImageKeepRatio(fileData, 0, 0);
        } catch (Throwable e) {
            Logger.E(a, e, (String) "decodeBitmapByFalcon data err", new Object[0]);
            return null;
        }
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, boolean jpg) {
        byte[] imageData = null;
        if (checkBitmap(bitmap)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                compressBitmap(bitmap, baos, 80, jpg);
                imageData = baos.toByteArray();
            } catch (Throwable t) {
                Logger.E(a, t, (String) "bitmap2Bytes err", new Object[0]);
            } finally {
                IOUtils.closeQuietly((OutputStream) baos);
            }
        }
        return imageData;
    }

    public static String getBitmapInfo(Bitmap bitmap) {
        if (bitmap != null) {
            return "[ " + bitmap + ", " + bitmap.getWidth() + DictionaryKeys.CTRLXY_X + bitmap.getHeight() + ", " + bitmap.getConfig() + " ]";
        }
        return "";
    }
}
