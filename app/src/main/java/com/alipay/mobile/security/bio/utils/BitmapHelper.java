package com.alipay.mobile.security.bio.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BitmapHelper {
    public static final int FRAME_MODE_BGR = 2;
    public static final int FRAME_MODE_BGRA = 1;
    public static final int FRAME_MODE_NV21 = 0;
    public static final int FRAME_MODE_RGB = 4;
    public static final int FRAME_MODE_RGBA = 3;

    public static Bitmap bytes2Bitmap(byte[] bArr, int i, int i2, int i3) {
        switch (i3) {
            case 0:
                return bytes2Bitmap(bArr);
            case 3:
                return RGBABytes2Bitmap(bArr, i, i2);
            case 4:
                return RGBBytes2Bitmap(bArr, i, i2);
            default:
                return null;
        }
    }

    public static Bitmap bytes2Bitmap(byte[] bArr) {
        if (bArr.length != 0) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        return null;
    }

    public static Bitmap RGBABytes2Bitmap(byte[] bArr, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(bArr));
        return createBitmap;
    }

    public static Bitmap RGBBytes2Bitmap(byte[] bArr, int i, int i2) {
        int[] convertBytesToInts = convertBytesToInts(bArr);
        if (convertBytesToInts == null) {
            return null;
        }
        return Bitmap.createBitmap(convertBytesToInts, 0, i, i, i2, Config.ARGB_8888);
    }

    public static int convertByteToInt(byte b) {
        return (((b >> 4) & 15) * 16) + (b & 15);
    }

    public static int[] convertBytesToInts(byte[] bArr) {
        int i;
        int i2 = 0;
        int length = bArr.length;
        if (length == 0) {
            return null;
        }
        if (length % 3 != 0) {
            i = 1;
        } else {
            i = 0;
        }
        int[] iArr = new int[((length / 3) + i)];
        int length2 = iArr.length;
        if (i == 0) {
            while (i2 < length2) {
                iArr[i2] = (convertByteToInt(bArr[i2 * 3]) << 16) | (convertByteToInt(bArr[(i2 * 3) + 1]) << 8) | convertByteToInt(bArr[(i2 * 3) + 2]) | -16777216;
                i2++;
            }
        } else {
            while (i2 < length2 - 1) {
                iArr[i2] = (convertByteToInt(bArr[i2 * 3]) << 16) | (convertByteToInt(bArr[(i2 * 3) + 1]) << 8) | convertByteToInt(bArr[(i2 * 3) + 2]) | -16777216;
                i2++;
            }
            iArr[length2 - 1] = -16777216;
        }
        return iArr;
    }

    public static Bitmap readBitMap(Context context, int i) {
        Options options = new Options();
        options.inPreferredConfig = Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(i), null, options);
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] Bitmap2BytesEx(Bitmap bitmap) {
        int i = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int length = iArr.length;
        byte[] bArr = new byte[(length * 4)];
        for (int i2 = length - 1; i2 >= width; i2 -= width) {
            for (int i3 = (i2 - width) + 1; i3 <= i2; i3++) {
                bArr[i] = (byte) (iArr[i3] >> 0);
                bArr[i + 1] = (byte) (iArr[i3] >> 8);
                bArr[i + 2] = (byte) (iArr[i3] >> 16);
                bArr[i + 3] = (byte) (iArr[i3] >> 24);
                i += 4;
            }
        }
        return bArr;
    }

    public static Bitmap compress(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap compress(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = ((float) i) / ((float) width);
        Matrix matrix = new Matrix();
        matrix.postScale(f, f);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static void recycle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            BioLog.i("recycled");
        }
    }

    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v0, types: [byte, int] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r2v0, types: [byte, int] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap getBitmap(byte[] r6, int r7, int r8) {
        /*
            if (r6 != 0) goto L_0x0004
            r0 = 0
        L_0x0003:
            return r0
        L_0x0004:
            int r0 = r6.length
            int r4 = r0 / 3
            int[] r5 = new int[r4]
            r0 = 0
            r3 = r0
        L_0x000b:
            if (r3 >= r4) goto L_0x0033
            int r0 = r3 * 3
            byte r2 = r6[r0]
            int r0 = r3 * 3
            int r0 = r0 + 1
            byte r1 = r6[r0]
            int r0 = r3 * 3
            int r0 = r0 + 2
            byte r0 = r6[r0]
            if (r2 >= 0) goto L_0x0021
            int r2 = r2 + 256
        L_0x0021:
            if (r1 >= 0) goto L_0x0025
            int r1 = r1 + 256
        L_0x0025:
            if (r0 >= 0) goto L_0x0029
            int r0 = r0 + 256
        L_0x0029:
            int r0 = android.graphics.Color.rgb(r2, r1, r0)
            r5[r3] = r0
            int r0 = r3 + 1
            r3 = r0
            goto L_0x000b
        L_0x0033:
            android.graphics.Bitmap$Config r0 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r5, r7, r8, r0)
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.utils.BitmapHelper.getBitmap(byte[], int, int):android.graphics.Bitmap");
    }

    public static Bitmap getYUVBitmap(byte[] bArr, int i, int i2) {
        int[] iArr = null;
        YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, iArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, i, i2), 100, byteArrayOutputStream);
        try {
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } catch (OutOfMemoryError e) {
            BioLog.e(e.toString());
            return iArr;
        } catch (Error e2) {
            BioLog.e(e2.toString());
            return iArr;
        }
    }

    public static Bitmap getYUVBitmap(byte[] bArr, int i, int i2, int i3) {
        int[] iArr = null;
        YuvImage yuvImage = new YuvImage(bArr, 17, i, i2, iArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, i, i2), i3, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } catch (OutOfMemoryError e) {
            BioLog.e(e.toString());
            return iArr;
        }
    }

    public static byte[] processImage(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        switch (i) {
            case 0:
                return rotateYUVImage(bArr, i2, i3, i4, i5);
            case 3:
                return compressRGBAImage(bArr, i2, i3, i4, i5);
            case 4:
                return compressRGBImage(bArr, i2, i3, i4, i5);
            default:
                return null;
        }
    }

    public static byte[] compressRGBImage(byte[] bArr, int i, int i2, int i3, int i4) {
        byte[] bArr2;
        Bitmap RGBBytes2Bitmap = RGBBytes2Bitmap(bArr, i, i2);
        if (i3 == 90 || i3 == 270) {
            Matrix matrix = new Matrix();
            matrix.setRotate((float) i3);
            Bitmap createBitmap = Bitmap.createBitmap(RGBBytes2Bitmap, 0, 0, RGBBytes2Bitmap.getWidth(), RGBBytes2Bitmap.getHeight(), matrix, true);
            bArr2 = FileUtil.bitmapToByteArray(createBitmap, i4);
            createBitmap.recycle();
        } else {
            bArr2 = FileUtil.bitmapToByteArray(RGBBytes2Bitmap, i4);
        }
        RGBBytes2Bitmap.recycle();
        return bArr2;
    }

    public static byte[] compressRGBAImage(byte[] bArr, int i, int i2, int i3, int i4) {
        byte[] bArr2;
        Bitmap RGBABytes2Bitmap = RGBABytes2Bitmap(bArr, i, i2);
        if (i3 == 90 || i3 == 270) {
            Matrix matrix = new Matrix();
            matrix.setRotate((float) i3);
            Bitmap createBitmap = Bitmap.createBitmap(RGBABytes2Bitmap, 0, 0, RGBABytes2Bitmap.getWidth(), RGBABytes2Bitmap.getHeight(), matrix, true);
            bArr2 = FileUtil.bitmapToByteArray(createBitmap, i4);
            createBitmap.recycle();
        } else {
            bArr2 = FileUtil.bitmapToByteArray(RGBABytes2Bitmap, i4);
        }
        RGBABytes2Bitmap.recycle();
        return bArr2;
    }

    public static byte[] rotateYUVImage(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        byte[] bArr2;
        if (i3 == 90) {
            bArr2 = rotateYUV420Degree270(bArr, i, i2);
            i5 = i2;
            i6 = i;
        } else if (i3 == 270) {
            bArr2 = a(bArr, i, i2);
            i5 = i2;
            i6 = i;
        } else {
            i5 = i;
            i6 = i2;
            bArr2 = bArr;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bArr2 != null) {
            new YuvImage(bArr2, 17, i5, i6, null).compressToJpeg(new Rect(0, 0, i5, i6), i4, byteArrayOutputStream);
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            BioLog.e(e.toString());
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[(((i * i2) * 3) / 2)];
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            for (int i5 = i2 - 1; i5 >= 0; i5--) {
                bArr2[i3] = bArr[(i5 * i) + i4];
                i3++;
            }
        }
        int i6 = i - 1;
        int i7 = (((i * i2) * 3) / 2) - 1;
        while (i6 > 0) {
            int i8 = i7;
            for (int i9 = 0; i9 < i2 / 2; i9++) {
                bArr2[i8] = bArr[(i * i2) + (i9 * i) + i6];
                int i10 = i8 - 1;
                bArr2[i10] = bArr[(i * i2) + (i9 * i) + (i6 - 1)];
                i8 = i10 - 1;
            }
            i6 -= 2;
            i7 = i8;
        }
        return bArr2;
    }

    public static byte[] rotateYUV420Degree270(byte[] bArr, int i, int i2) {
        byte[] a = a(bArr, i, i2);
        byte[] bArr2 = new byte[(((i * i2) * 3) / 2)];
        int i3 = 0;
        for (int i4 = (i * i2) - 1; i4 >= 0; i4--) {
            bArr2[i3] = a[i4];
            i3++;
        }
        for (int i5 = (((i * i2) * 3) / 2) - 1; i5 >= i * i2; i5 -= 2) {
            int i6 = i3 + 1;
            bArr2[i3] = a[i5 - 1];
            i3 = i6 + 1;
            bArr2[i6] = a[i5];
        }
        return bArr2;
    }

    public static Bitmap reverseBitmap(Bitmap bitmap, int i) {
        float[] fArr;
        switch (i) {
            case 0:
                fArr = new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
                break;
            default:
                fArr = new float[]{1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
                break;
        }
        Matrix matrix = new Matrix();
        matrix.setValues(fArr);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
