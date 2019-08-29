package com.sina.weibo.sdk.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    private static void revitionImageSizeHD(String str, int i, int i2) throws IOException {
        Bitmap createBitmap;
        if (i <= 0) {
            throw new IllegalArgumentException("size must be greater than 0!");
        } else if (!isFileExisted(str)) {
            if (str == null) {
                str = "null";
            }
            throw new FileNotFoundException(str);
        } else if (!BitmapHelper.verifyBitmap(str)) {
            throw new IOException("");
        } else {
            int i3 = i * 2;
            FileInputStream fileInputStream = new FileInputStream(str);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(fileInputStream, null, options);
            try {
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int i4 = 0;
            while (true) {
                if ((options.outWidth >> i4) <= i3 && (options.outHeight >> i4) <= i3) {
                    break;
                }
                i4++;
            }
            options.inSampleSize = (int) Math.pow(2.0d, (double) i4);
            options.inJustDecodeBounds = false;
            Bitmap safeDecodeBimtapFile = safeDecodeBimtapFile(str, options);
            if (safeDecodeBimtapFile == null) {
                throw new IOException("Bitmap decode error!");
            }
            deleteDependon(str);
            makesureFileExist(str);
            float width = ((float) i) / ((float) (safeDecodeBimtapFile.getWidth() > safeDecodeBimtapFile.getHeight() ? safeDecodeBimtapFile.getWidth() : safeDecodeBimtapFile.getHeight()));
            if (width < 1.0f) {
                while (true) {
                    try {
                        createBitmap = Bitmap.createBitmap((int) (((float) safeDecodeBimtapFile.getWidth()) * width), (int) (((float) safeDecodeBimtapFile.getHeight()) * width), Config.ARGB_8888);
                        break;
                    } catch (OutOfMemoryError unused) {
                        System.gc();
                        width = (float) (((double) width) * 0.8d);
                    }
                }
                if (createBitmap == null) {
                    safeDecodeBimtapFile.recycle();
                }
                Canvas canvas = new Canvas(createBitmap);
                Matrix matrix = new Matrix();
                matrix.setScale(width, width);
                canvas.drawBitmap(safeDecodeBimtapFile, matrix, new Paint());
                safeDecodeBimtapFile.recycle();
                safeDecodeBimtapFile = createBitmap;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            if (options.outMimeType == null || !options.outMimeType.contains("png")) {
                safeDecodeBimtapFile.compress(CompressFormat.JPEG, i2, fileOutputStream);
            } else {
                safeDecodeBimtapFile.compress(CompressFormat.PNG, i2, fileOutputStream);
            }
            try {
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            safeDecodeBimtapFile.recycle();
        }
    }

    private static void revitionImageSize(String str, int i, int i2) throws IOException {
        if (i <= 0) {
            throw new IllegalArgumentException("size must be greater than 0!");
        } else if (!isFileExisted(str)) {
            if (str == null) {
                str = "null";
            }
            throw new FileNotFoundException(str);
        } else if (!BitmapHelper.verifyBitmap(str)) {
            throw new IOException("");
        } else {
            FileInputStream fileInputStream = new FileInputStream(str);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(fileInputStream, null, options);
            try {
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int i3 = 0;
            while (true) {
                if ((options.outWidth >> i3) <= i && (options.outHeight >> i3) <= i) {
                    break;
                }
                i3++;
            }
            options.inSampleSize = (int) Math.pow(2.0d, (double) i3);
            options.inJustDecodeBounds = false;
            Bitmap safeDecodeBimtapFile = safeDecodeBimtapFile(str, options);
            if (safeDecodeBimtapFile == null) {
                throw new IOException("Bitmap decode error!");
            }
            deleteDependon(str);
            makesureFileExist(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            if (options.outMimeType == null || !options.outMimeType.contains("png")) {
                safeDecodeBimtapFile.compress(CompressFormat.JPEG, i2, fileOutputStream);
            } else {
                safeDecodeBimtapFile.compress(CompressFormat.PNG, i2, fileOutputStream);
            }
            try {
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            safeDecodeBimtapFile.recycle();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0031, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0032, code lost:
        r6 = r5;
        r5 = r4;
        r4 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        return r4;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A[ExcHandler: FileNotFoundException (unused java.io.FileNotFoundException), SYNTHETIC, Splitter:B:6:0x0013] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.Bitmap safeDecodeBimtapFile(java.lang.String r7, android.graphics.BitmapFactory.Options r8) {
        /*
            if (r8 != 0) goto L_0x000b
            android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
            r0.<init>()
            r1 = 1
            r0.inSampleSize = r1
            goto L_0x000c
        L_0x000b:
            r0 = r8
        L_0x000c:
            r1 = 0
            r2 = 0
            r3 = r2
            r4 = r3
        L_0x0010:
            r5 = 5
            if (r1 >= r5) goto L_0x004a
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ OutOfMemoryError -> 0x0031, FileNotFoundException -> 0x004a }
            r5.<init>(r7)     // Catch:{ OutOfMemoryError -> 0x0031, FileNotFoundException -> 0x004a }
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeStream(r5, r2, r8)     // Catch:{ OutOfMemoryError -> 0x002b, FileNotFoundException -> 0x004a }
            r5.close()     // Catch:{ IOException -> 0x0025 }
            goto L_0x0029
        L_0x0020:
            r4 = move-exception
            r6 = r5
            r5 = r3
            r3 = r6
            goto L_0x0035
        L_0x0025:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ OutOfMemoryError -> 0x0020, FileNotFoundException -> 0x0029 }
        L_0x0029:
            r4 = r3
            goto L_0x004a
        L_0x002b:
            r3 = move-exception
            r6 = r4
            r4 = r3
            r3 = r5
            r5 = r6
            goto L_0x0035
        L_0x0031:
            r5 = move-exception
            r6 = r5
            r5 = r4
            r4 = r6
        L_0x0035:
            r4.printStackTrace()
            int r4 = r0.inSampleSize
            int r4 = r4 * 2
            r0.inSampleSize = r4
            r3.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0046
        L_0x0042:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0046:
            int r1 = r1 + 1
            r4 = r5
            goto L_0x0010
        L_0x004a:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.utils.ImageUtils.safeDecodeBimtapFile(java.lang.String, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
    }

    private static void delete(File file) {
        if (file != null && file.exists() && !file.delete()) {
            StringBuilder sb = new StringBuilder();
            sb.append(file.getAbsolutePath());
            sb.append(" doesn't be deleted!");
            throw new RuntimeException(sb.toString());
        }
    }

    private static boolean deleteDependon(String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        int i = 1;
        while (!z && i <= 5 && file.isFile() && file.exists()) {
            z = file.delete();
            if (!z) {
                i++;
            }
        }
        return z;
    }

    private static boolean isFileExisted(String str) {
        if (!TextUtils.isEmpty(str) && new File(str).exists()) {
            return true;
        }
        return false;
    }

    private static boolean isParentExist(File file) {
        if (file == null) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null || parentFile.exists()) {
            return false;
        }
        if (file.exists() || file.mkdirs()) {
            return true;
        }
        return false;
    }

    @TargetApi(10)
    public static long getVideoDuring(String str) {
        if (!new File(str).exists()) {
            return 0;
        }
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(str);
            return Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
        } catch (Exception unused) {
            return 0;
        }
    }

    private static void makesureFileExist(String str) {
        if (str != null) {
            File file = new File(str);
            if (!file.exists() && isParentExist(file)) {
                if (file.exists()) {
                    delete(file);
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isWifi(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }
}
