package com.alipay.mobile.nebula.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5ImageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ImageProvider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class H5ImageUtil {
    static final String TAG = "ImageUtil";
    public static String gifBase64 = "data:image/gif;base64,";
    public static String iconBase64 = "data:image/x-icon;base64,";
    public static String jpgBae64 = "data:image/jpeg;base64,";
    public static String pngBase64 = "data:image/png;base64,";

    public static Bitmap scaleBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float baseScale = Math.min(((float) w) / ((float) width), ((float) h) / ((float) height));
        Matrix matrix = new Matrix();
        matrix.postScale(baseScale, baseScale);
        boolean z = false;
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (OutOfMemoryError e) {
            H5Log.e(TAG, "OutOfMemoryError", e);
            return z;
        }
    }

    public static Drawable byteToDrawable(String icon) {
        byte[] img = Base64.decode(base64Deal(icon), 0);
        if (img != null) {
            return new BitmapDrawable(BitmapFactory.decodeByteArray(img, 0, img.length));
        }
        return null;
    }

    public static String getImageTempDir(Context context) {
        return context.getFilesDir() + "/h5container/image/temp" + File.separator;
    }

    public static Bitmap imageQuality(Bitmap bitmap, int quality) {
        if (bitmap == null) {
            return null;
        }
        Bitmap qBitmap = null;
        if (quality >= 100) {
            return bitmap;
        }
        ByteArrayOutputStream baos = new PoolingByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, quality, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        try {
            qBitmap = BitmapFactory.decodeStream(bais, null, new Options());
        } catch (OutOfMemoryError e) {
            H5Log.e(TAG, "exception detail", e);
        } finally {
            H5IOUtils.closeQuietly(baos);
            H5IOUtils.closeQuietly(bais);
        }
        return qBitmap;
    }

    public static Bitmap getDiskBitmap(String imagePath, int maxWidth, int maxHeight) {
        int targetWidth;
        int targetHeight;
        if (!H5FileUtil.exists(imagePath)) {
            return null;
        }
        H5Log.debug(TAG, "getDiskBitmap begin");
        Options options = new Options();
        options.inPreferredConfig = Config.ALPHA_8;
        options.inJustDecodeBounds = true;
        options.inDither = false;
        options.inTempStorage = new byte[32768];
        H5Log.debug(TAG, "getDiskBitmap decodeFile begin");
        BitmapFactory.decodeFile(imagePath, options);
        H5Log.debug(TAG, "getDiskBitmap decodeFile after");
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        if ((srcWidth <= maxWidth && srcHeight <= maxHeight) || maxWidth <= 0 || maxHeight <= 0) {
            return BitmapFactory.decodeFile(imagePath);
        }
        float scaleX = ((float) srcWidth) / ((float) maxWidth);
        float scaleY = ((float) srcHeight) / ((float) maxHeight);
        if (scaleX > scaleY) {
            targetWidth = maxWidth;
            targetHeight = (int) (((float) srcHeight) / scaleX);
        } else {
            targetWidth = (int) (((float) srcWidth) / scaleY);
            targetHeight = maxHeight;
        }
        options.inSampleSize = Math.min(srcWidth / targetWidth, srcHeight / targetHeight);
        options.inJustDecodeBounds = false;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(new File(imagePath));
        } catch (FileNotFoundException e) {
            H5Log.e((String) TAG, String.valueOf(e));
        }
        Bitmap newBitmap = null;
        if (fs != null) {
            try {
                H5Log.debug(TAG, "getDiskBitmap decodeFileDescriptor begin");
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, options);
                H5Log.debug(TAG, "getDiskBitmap decodeFileDescriptor after");
                newBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
                if (bitmap != newBitmap) {
                    bitmap.recycle();
                }
                fs.close();
            } catch (IOException e2) {
                H5Log.e("create bitmap exception:" + e2);
            }
        } else {
            H5Log.e((String) TAG, "not file." + imagePath);
        }
        H5Log.debug(TAG, "getDiskBitmap after");
        return newBitmap;
    }

    @SuppressLint({"NewApi"})
    public static Bitmap getDiskBitmap(String imagePath, int maxWidth, int maxHeight, int quality) {
        Bitmap bitmap = getDiskBitmap(imagePath, maxWidth, maxHeight);
        if (bitmap == null) {
            return null;
        }
        if (quality < 50 || quality > 100) {
            H5Log.d(TAG, "set quality as default 75.");
            quality = 75;
        }
        if (quality != 100) {
            ByteArrayOutputStream baos = new PoolingByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, quality, baos);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            bitmap.recycle();
            bitmap = BitmapFactory.decodeStream(bais, null, null);
            H5IOUtils.closeQuietly(baos);
        }
        return bitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0024 A[SYNTHETIC, Splitter:B:16:0x0024] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0034 A[SYNTHETIC, Splitter:B:22:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeImage(android.graphics.Bitmap r6, android.graphics.Bitmap.CompressFormat r7, java.lang.String r8) {
        /*
            if (r6 == 0) goto L_0x0010
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x001a }
            r2.<init>(r8)     // Catch:{ IOException -> 0x001a }
            r3 = 100
            r6.compress(r7, r3, r2)     // Catch:{ IOException -> 0x0044, all -> 0x0041 }
            r2.close()     // Catch:{ IOException -> 0x0011 }
        L_0x0010:
            return
        L_0x0011:
            r0 = move-exception
            java.lang.String r3 = "ImageUtil"
            java.lang.String r4 = "Exception"
            com.alipay.mobile.nebula.util.H5Log.e(r3, r4, r0)
            goto L_0x0010
        L_0x001a:
            r0 = move-exception
        L_0x001b:
            java.lang.String r3 = "ImageUtil"
            java.lang.String r4 = "exception detail."
            com.alipay.mobile.nebula.util.H5Log.e(r3, r4, r0)     // Catch:{ all -> 0x0031 }
            if (r1 == 0) goto L_0x0010
            r1.close()     // Catch:{ IOException -> 0x0028 }
            goto L_0x0010
        L_0x0028:
            r0 = move-exception
            java.lang.String r3 = "ImageUtil"
            java.lang.String r4 = "Exception"
            com.alipay.mobile.nebula.util.H5Log.e(r3, r4, r0)
            goto L_0x0010
        L_0x0031:
            r3 = move-exception
        L_0x0032:
            if (r1 == 0) goto L_0x0037
            r1.close()     // Catch:{ IOException -> 0x0038 }
        L_0x0037:
            throw r3
        L_0x0038:
            r0 = move-exception
            java.lang.String r4 = "ImageUtil"
            java.lang.String r5 = "Exception"
            com.alipay.mobile.nebula.util.H5Log.e(r4, r5, r0)
            goto L_0x0037
        L_0x0041:
            r3 = move-exception
            r1 = r2
            goto L_0x0032
        L_0x0044:
            r0 = move-exception
            r1 = r2
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebula.util.H5ImageUtil.writeImage(android.graphics.Bitmap, android.graphics.Bitmap$CompressFormat, java.lang.String):void");
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, float rotation) {
        if (bitmap == null) {
            return null;
        }
        Bitmap rotatedBitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);
        try {
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            H5Log.e(TAG, "OutOfMemoryError", e);
        }
        if (rotatedBitmap == null) {
            return bitmap;
        }
        return rotatedBitmap;
    }

    public static String bitmapToString(Bitmap bitmap, String format) {
        ByteArrayOutputStream bStream = new PoolingByteArrayOutputStream();
        bitmap.compress(format.equals("jpg") ? CompressFormat.JPEG : CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        H5IOUtils.closeQuietly(bStream);
        return Base64.encodeToString(bytes, 2);
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        try {
            byte[] bytes = Base64.decode(base64Deal(base64Data), 0);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return null;
        }
    }

    @Deprecated
    public static String getMimeType(String base64Data) {
        Options mOptions = new Options();
        byte[] bytes = Base64.decode(base64Deal(base64Data), 0);
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, mOptions);
        H5Log.d(TAG, "--type is " + mOptions.outMimeType);
        return mOptions.outMimeType;
    }

    public static long getBitmapSize(Bitmap bitmap) {
        try {
            if (VERSION.SDK_INT >= 12) {
                return (long) bitmap.getByteCount();
            }
            return (long) (bitmap.getRowBytes() * bitmap.getHeight());
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return 0;
        }
    }

    public static String base64Deal(String base64) {
        if (TextUtils.isEmpty(base64)) {
            return base64;
        }
        if (base64.startsWith(gifBase64)) {
            return base64.replace(gifBase64, "");
        }
        if (base64.startsWith(pngBase64)) {
            return base64.replace(pngBase64, "");
        }
        if (base64.startsWith(jpgBae64)) {
            return base64.replace(jpgBae64, "");
        }
        if (base64.startsWith(iconBase64)) {
            return base64.replace(iconBase64, "");
        }
        return base64;
    }

    public static String getExtensionFromBase64(String base64) {
        String mimeType = getMimeType(base64);
        if (TextUtils.isEmpty(base64)) {
            return "";
        }
        if (mimeType.equals("image/gif")) {
            return "gif";
        }
        if (mimeType.equals("image/png")) {
            return "png";
        }
        if (mimeType.equals("image/jpg")) {
            return "jpg";
        }
        if (mimeType.equals("image/icon")) {
            return H5Param.MENU_ICON;
        }
        return "";
    }

    public static InputStream base64ToInputStream(String base64Data) {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_base64ToInputStream"))) {
            return base64ToInput(base64Data);
        }
        ByteArrayOutputStream baos = null;
        try {
            byte[] bytes = Base64.decode(base64Deal(base64Data), 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ByteArrayOutputStream baos2 = new PoolingByteArrayOutputStream();
            try {
                bitmap.compress(CompressFormat.JPEG, 100, baos2);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(baos2.toByteArray());
                H5IOUtils.closeQuietly(baos2);
                return byteArrayInputStream;
            } catch (Throwable th) {
                th = th;
                baos = baos2;
                H5IOUtils.closeQuietly(baos);
                throw th;
            }
        } catch (Throwable th2) {
            H5Log.d(TAG, "base64ToInputStream, inputStream is null");
            H5IOUtils.closeQuietly(baos);
            return null;
        }
    }

    private static InputStream base64ToInput(String strBase64) {
        try {
            H5Log.d(TAG, "");
            return new ByteArrayInputStream(Base64.decode(base64Deal(strBase64), 0));
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            return null;
        }
    }

    public static void loadImage(String imageUrl, final H5ImageListener h5ImageListener) {
        H5ImageProvider h5ImageProvider = (H5ImageProvider) H5Utils.getProvider(H5ImageProvider.class.getName());
        if (TextUtils.isEmpty(imageUrl)) {
            if (h5ImageListener != null) {
                h5ImageListener.onImage(null);
            }
        } else if (h5ImageProvider != null) {
            h5ImageProvider.loadImage(imageUrl, new H5ImageListener() {
                public final void onImage(Bitmap bitmap) {
                    if (h5ImageListener != null) {
                        h5ImageListener.onImage(bitmap);
                    }
                }
            });
        } else {
            H5Utils.getExecutor("RPC").execute(new H5ImageLoader(imageUrl, new H5ImageListener() {
                public final void onImage(Bitmap bitmap) {
                    if (h5ImageListener != null) {
                        h5ImageListener.onImage(bitmap);
                    }
                }
            }));
        }
    }
}
