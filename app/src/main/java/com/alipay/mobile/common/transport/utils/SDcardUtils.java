package com.alipay.mobile.common.transport.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

public class SDcardUtils {
    public static String TAG = "SDcardUtils";

    public static boolean isUseSdcardPath(String path) {
        String sdcard = Environment.getExternalStorageDirectory().getPath();
        Log.d(TAG, "path = " + path);
        if (!TextUtils.isEmpty(path) && path.length() >= sdcard.length() && path.startsWith(sdcard)) {
            return true;
        }
        return false;
    }

    public static boolean IsCanUseSdCard() {
        try {
            return Environment.getExternalStorageState().equals("mounted");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isSDcardAvailableSpace(long space) {
        if (!IsCanUseSdCard()) {
            return false;
        }
        return isAppAvailableSpace(space, Environment.getExternalStorageDirectory());
    }

    @Deprecated
    public static boolean isAppAvailableSpace(long space) {
        try {
            return isAppAvailableSpace(space, Environment.getDataDirectory());
        } catch (Throwable e) {
            LogCatUtil.error(TAG, "isAppAvailableSpace error", e);
            return true;
        }
    }

    public static boolean isAppAvailableSpace(long space, File file) {
        if (file == null) {
            return false;
        }
        try {
            StatFs statFs = new StatFs(file.getPath());
            long availableSpare = ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
            if (space < availableSpare) {
                return true;
            }
            LogCatUtil.warn(TAG, "isAppAvailableSpace.  (space:" + space + ") < (availableSpare:" + availableSpare + ")  file=" + file.getAbsolutePath());
            return false;
        } catch (Throwable e) {
            LogCatUtil.warn(TAG, "isAppAvailableSpace error.", e);
            return true;
        }
    }

    public static boolean isDataDirAvailableSpace(long space) {
        return isAppAvailableSpace(space);
    }
}
