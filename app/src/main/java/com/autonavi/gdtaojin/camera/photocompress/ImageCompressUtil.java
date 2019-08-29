package com.autonavi.gdtaojin.camera.photocompress;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompressUtil {

    public static class Param {
        public int customDegree;
        public String filePath;
        public Config inPreferredConfig = Config.ARGB_8888;
        public int maxSize;
        public int quality = 92;
        public boolean readFileDegree = true;
        public float restrictRatio;
        public boolean updateFile = true;
    }

    private static boolean validParam(Param param) {
        if (param == null || TextUtils.isEmpty(param.filePath) || param.maxSize < 0 || param.customDegree < 0 || param.customDegree >= 360 || param.restrictRatio < 0.0f || param.quality <= 0 || param.quality > 100 || param.inPreferredConfig == null) {
            return false;
        }
        File file = new File(param.filePath);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        return true;
    }

    private static void calculateSampleSize(Param param, Options options) {
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(param.filePath, options);
        if (param.maxSize > 0) {
            int max = Math.max(options.outWidth / param.maxSize, options.outHeight / param.maxSize);
            if (max > 1) {
                options.inSampleSize = max;
            }
        }
    }

    private static Bitmap decodeBitmap(Param param, Options options) {
        options.inJustDecodeBounds = false;
        options.inInputShareable = true;
        options.inPurgeable = true;
        options.inPreferredConfig = param.inPreferredConfig;
        try {
            return BitmapFactory.decodeFile(param.filePath, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            options.inSampleSize *= 2;
            return BitmapFactory.decodeFile(param.filePath, options);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static int readPictureDegree(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt != 8) {
                return 0;
            }
            return 270;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static Bitmap scaleImage(int i, Param param, Matrix matrix, Bitmap bitmap) {
        boolean z;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float max = (float) ((((double) param.maxSize) * 1.0d) / ((double) Math.max(width, height)));
        if (max >= 1.0f || max <= 0.0f) {
            z = false;
        } else {
            matrix.postScale(max, max);
            z = true;
        }
        if (i != 0) {
            z = true;
        }
        return z ? Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true) : bitmap;
    }

    private static Bitmap cropWidthScaleImage(float f, Param param, Matrix matrix, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = (int) (((float) height) * f);
        int i2 = (width - i) / 2;
        float max = (float) ((((double) param.maxSize) * 1.0d) / ((double) Math.max(i, height)));
        if (max < 1.0f && max > 0.0f) {
            matrix.postScale(max, max);
        }
        return Bitmap.createBitmap(bitmap, i2, 0, i, height, matrix, true);
    }

    private static Bitmap cropHeightScaleImage(float f, Param param, Matrix matrix, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int i = (int) (((float) width) / f);
        int height = (bitmap.getHeight() - i) / 2;
        float max = (float) ((((double) param.maxSize) * 1.0d) / ((double) Math.max(width, i)));
        if (max < 1.0f) {
            matrix.postScale(max, max);
        }
        return Bitmap.createBitmap(bitmap, 0, height, width, i, matrix, true);
    }

    private static Bitmap resizeAndRotateImage(int i, Bitmap bitmap, Param param) {
        Bitmap bitmap2;
        if (bitmap == null || param == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        try {
            float f = param.restrictRatio;
            if (f == 0.0f) {
                return scaleImage(i, param, matrix, bitmap);
            }
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width < height) {
                if (f > 1.0f) {
                    f = 1.0f / f;
                }
            } else if (f < 1.0f) {
                f = 1.0f / f;
            }
            float f2 = (float) width;
            float f3 = ((float) height) * f;
            if (f2 < f3) {
                bitmap2 = cropHeightScaleImage(f, param, matrix, bitmap);
            } else if (f2 > f3) {
                bitmap2 = cropWidthScaleImage(f, param, matrix, bitmap);
            } else {
                bitmap2 = scaleImage(i, param, matrix, bitmap);
            }
            if (!(bitmap == null || bitmap == bitmap2)) {
                bitmap.recycle();
            }
            return bitmap2;
        } catch (Throwable th) {
            th.printStackTrace();
            bitmap2 = null;
        }
    }

    private static boolean compressBmpToFile(Bitmap bitmap, String str, int i) {
        ByteArrayOutputStream byteArrayOutputStream;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bitmap.compress(CompressFormat.JPEG, i, byteArrayOutputStream);
                fileOutputStream = new FileOutputStream(str);
            } catch (Throwable th) {
                th = th;
                th.printStackTrace();
                closeIO(fileOutputStream2);
                closeIO(byteArrayOutputStream);
                return false;
            }
            try {
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
                closeIO(fileOutputStream);
                closeIO(byteArrayOutputStream);
                return true;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                th.printStackTrace();
                closeIO(fileOutputStream2);
                closeIO(byteArrayOutputStream);
                return false;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
            th.printStackTrace();
            closeIO(fileOutputStream2);
            closeIO(byteArrayOutputStream);
            return false;
        }
    }

    private static void closeIO(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap handleImage(Param param) {
        int i;
        if (!validParam(param)) {
            return null;
        }
        Options options = new Options();
        calculateSampleSize(param, options);
        Bitmap decodeBitmap = decodeBitmap(param, options);
        if (decodeBitmap == null) {
            return null;
        }
        if (param.readFileDegree) {
            i = readPictureDegree(param.filePath);
        } else {
            i = param.customDegree;
        }
        Bitmap resizeAndRotateImage = resizeAndRotateImage(i, decodeBitmap, param);
        if (resizeAndRotateImage != null && param.updateFile) {
            compressBmpToFile(resizeAndRotateImage, param.filePath, param.quality);
        }
        return resizeAndRotateImage;
    }
}
