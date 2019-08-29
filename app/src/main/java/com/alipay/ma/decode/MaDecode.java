package com.alipay.ma.decode;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.text.TextUtils;
import com.alipay.ma.common.a.a;
import com.alipay.ma.util.b;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.mascanengine.BuryRecord;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MaDecode {
    public static final String SO_NAME = "alipaydecode100120";
    public static final String TAG = "MaDecode";
    public static final String USE_OLD_ENCODE = "USE_OLD_ENCODE";
    private static boolean a = false;
    public static boolean useOldEncodeGuess = false;

    private static native DecodeResult[] codeDecodeWithBinary(byte[] bArr, int i, int i2, int i3, Rect rect, int i4, int i5, String str, String[] strArr);

    private static native DecodeResult codeDecodeWithQr(byte[] bArr, int i, int i2, int i3, int i4);

    public static native int getLastFrameAvgGray();

    public static native float getMaProportion();

    public static native String getReaderParams();

    public static native int setReaderParams(String str, String str2);

    private static native DecodeResult[] yuvcodeDecode(byte[] bArr, int i, int i2, int i3, Rect rect, int i4, String str, String[] strArr);

    static {
        init();
    }

    public static void init() {
        try {
            System.loadLibrary(SO_NAME);
            a.a(TAG, "Succeed to load alipaydecode100120");
        } catch (UnsatisfiedLinkError e) {
            a.c(TAG, "Failed to load alipaydecode100120, " + e.getMessage());
        }
    }

    public static void recordScanDecodeTrack(String str) {
        BuryRecord.recordScanDecodeTrack(str);
    }

    public static DecodeResult[] yuvcodeDecode(YuvImage yuvImage, Rect rect, int scanMode, String str4GLocalAddr, String[] whitelist) {
        return a(yuvImage.getYuvData(), yuvImage.getWidth(), yuvImage.getHeight(), yuvImage.getStrides()[0], rect, scanMode, str4GLocalAddr, whitelist);
    }

    private static synchronized DecodeResult[] a(byte[] data, int width, int height, int strides, Rect rect, int scanMode, String str4GLocalAddr, String[] whitelist) {
        DecodeResult[] result;
        synchronized (MaDecode.class) {
            if (a) {
                result = null;
            } else {
                a = true;
                if (data == null) {
                    a.b(TAG, "codeDecode data is null");
                    result = null;
                } else {
                    result = null;
                    try {
                        result = yuvcodeDecode(data, width, height, strides, rect, scanMode, str4GLocalAddr, whitelist);
                    } catch (UnsatisfiedLinkError error) {
                        a.c(TAG, "Failed to load alipaydecode100120, " + error.getMessage());
                        init();
                    } catch (Exception e) {
                        a.c(TAG, e.getMessage());
                    }
                    a = false;
                    if (result == null || result.length == 0) {
                        result = null;
                    } else {
                        for (int i = 0; i < result.length; i++) {
                            result[i] = a(result[i]);
                        }
                    }
                }
            }
        }
        return result;
    }

    private static DecodeResult a(DecodeResult result) {
        if (!(result == null || result.bytes == null || result.bytes.length <= 0)) {
            try {
                String encode = b.a(result.bytes, useOldEncodeGuess);
                if (TextUtils.isEmpty(encode)) {
                    result.strCode = new String(result.bytes, "utf-8");
                } else {
                    result.strCode = new String(result.bytes, encode);
                }
                result.bytes = null;
                if (TextUtils.isEmpty(result.strCode)) {
                    return null;
                }
                return result;
            } catch (UnsupportedEncodingException | Exception e) {
            }
        }
        return null;
    }

    public static synchronized DecodeResult codeDecodePictureWithQr(String path, int scanMode) {
        DecodeResult decodeResult = null;
        synchronized (MaDecode.class) {
            try {
                if (!TextUtils.isEmpty(path)) {
                    File srcImageFile = new File(path);
                    if (srcImageFile.exists()) {
                        decodeResult = codeDecodePictureWithQr(com.alipay.ma.util.a.a(srcImageFile), scanMode);
                    }
                }
            } catch (Exception e) {
            }
        }
        return decodeResult;
    }

    public static synchronized DecodeResult codeDecodePictureWithQr(Bitmap bitmap, int scanMode) {
        DecodeResult result;
        synchronized (MaDecode.class) {
            Bitmap b = bitmap;
            result = null;
            try {
                if (b.getConfig() != Config.ARGB_8888) {
                    Bitmap bb = b.copy(Config.ARGB_8888, true);
                    b.recycle();
                    b = bb;
                }
                ByteBuffer buf = ByteBuffer.allocate(b.getHeight() * b.getRowBytes());
                buf.order(ByteOrder.BIG_ENDIAN);
                b.copyPixelsToBuffer(buf);
                try {
                    result = codeDecodeWithQr(buf.array(), b.getWidth(), b.getHeight(), b.getRowBytes(), scanMode);
                } catch (UnsatisfiedLinkError error) {
                    a.c(TAG, "Failed to load alipaydecode100120," + error.getMessage());
                    init();
                } catch (Exception e) {
                    a.c(TAG, e.getMessage());
                }
                result = a(result);
            } catch (OutOfMemoryError e2) {
                a.c(TAG, "codeDecodePictureWithQr error : " + e2.getMessage());
            }
        }
        return result;
    }

    public static synchronized DecodeResult[] codeDecodeBinarizedData(byte[] bitMatrix, int width, int height, int strides, Rect rect, int scanMode, int binarizeMethod, String str4GLocalAddr, String[] whitelist) {
        DecodeResult[] result;
        synchronized (MaDecode.class) {
            if (a) {
                result = null;
            } else {
                a = true;
                if (bitMatrix == null) {
                    a.b(TAG, "codeDecode data is null");
                    result = null;
                } else {
                    result = null;
                    try {
                        result = codeDecodeWithBinary(bitMatrix, width, height, strides, rect, scanMode, binarizeMethod, str4GLocalAddr, whitelist);
                    } catch (UnsatisfiedLinkError error) {
                        a.c(TAG, "Failed to load alipaydecode100120, " + error.getMessage());
                        init();
                    } catch (Exception e) {
                        a.c(TAG, e.getMessage());
                    }
                    a = false;
                    Logger.d(TAG, "result is null:" + (result == null));
                    if (result == null || result.length == 0) {
                        result = null;
                    } else {
                        for (int i = 0; i < result.length; i++) {
                            result[i] = a(result[i]);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static int getLastFrameAverageGray() {
        int avgValue = 0;
        try {
            return getLastFrameAvgGray();
        } catch (UnsatisfiedLinkError error) {
            a.c(TAG, "Failed to load alipaydecode100120, " + error.getMessage());
            init();
            return avgValue;
        } catch (Exception e) {
            a.c(TAG, e.getMessage());
            return avgValue;
        }
    }
}
