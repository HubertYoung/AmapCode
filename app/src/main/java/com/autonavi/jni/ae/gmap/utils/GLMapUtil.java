package com.autonavi.jni.ae.gmap.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.TypedValue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GLMapUtil {
    private static final int AMAP_ENGINE_TYPE_DISPLAY_EXTERNAL_1 = 2;
    private static final int AMAP_ENGINE_TYPE_DISPLAY_EXTERNAL_1_EAGLE_EYE = 3;
    private static final int AMAP_ENGINE_TYPE_DISPLAY_EXTERNAL_2 = 4;
    private static final int AMAP_ENGINE_TYPE_DISPLAY_EXTERNAL_2_EAGLE_EYE = 5;
    private static final int AMAP_ENGINE_TYPE_DISPLAY_EXTERNAL_3 = 6;
    private static final int AMAP_ENGINE_TYPE_DISPLAY_EXTERNAL_3_EAGLE_EYE = 7;
    private static final int AMAP_ENGINE_TYPE_EAGLEEYE = 1;
    private static final int AMAP_ENGINE_TYPE_MAIN = 0;
    private static final int AN_ENGINE_ID_DISPLAY_EXTERNAL_1 = 3;
    private static final int AN_ENGINE_ID_DISPLAY_EXTERNAL_1_EAGLE_EYE = 4;
    private static final int AN_ENGINE_ID_DISPLAY_EXTERNAL_2 = 5;
    private static final int AN_ENGINE_ID_DISPLAY_EXTERNAL_2_EAGLE_EYE = 6;
    private static final int AN_ENGINE_ID_DISPLAY_EXTERNAL_3 = 7;
    private static final int AN_ENGINE_ID_DISPLAY_EXTERNAL_3_EAGLE_EYE = 8;
    private static final int AN_ENGINE_ID_EAGLE_EYE = 2;
    public static final int AN_ENGINE_ID_INVALID = -1;
    private static final int AN_ENGINE_ID_MAIN = 1;
    public static final String CONFIG_TABLE_NAME_EAGLE_EYE = "config_2";
    public static final String CONFIG_TABLE_NAME_MAIN = "config_1";
    public static final String MAPPROFILE_TABLE_NAME_MAIN = "mapprofile_1";
    public static final String MAPPROFILE_TABLE_NAME_MINI = "mapprofile_2";

    private static native int[] nativeLonLatToPixel(double d, double d2, int i);

    public static native int nativeMeterToP20(int i, int i2, float f);

    private static native double[] nativePixelToLonLat(int i, int i2, int i3);

    public static byte[] decodeAssetResData(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read >= 0) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    open.close();
                    return byteArray;
                }
            }
        } catch (IOException unused) {
            return null;
        } catch (OutOfMemoryError unused2) {
            return null;
        }
    }

    public static int dipToPixel(Context context, int i) {
        if (context == null) {
            return i;
        }
        try {
            return (int) TypedValue.applyDimension(1, (float) i, context.getResources().getDisplayMetrics());
        } catch (Exception unused) {
            return i;
        }
    }

    public static int spToPixel(Context context, int i) {
        return (int) TypedValue.applyDimension(2, (float) i, context.getResources().getDisplayMetrics());
    }

    public static String getString(Context context, int i) {
        return context.getResources().getString(i);
    }

    public static boolean isAssic(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] >= 256 || charArray[i] <= 0) {
                return false;
            }
        }
        return true;
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException unused) {
            return "";
        }
    }

    public static int meterToP20(int i, int i2, float f) {
        return nativeMeterToP20(i, i2, f);
    }

    public static int[] lonLatToPixel(double d, double d2, int i) {
        return nativeLonLatToPixel(d, d2, i);
    }

    public static double[] pixelToLonLat(int i, int i2, int i3) {
        return nativePixelToLonLat(i, i2, i3);
    }
}
