package com.alipay.security.mobile.module.localstorage;

import android.os.Environment;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.io.File;

public class SDCardStorage {
    public static void writeDataToSDCard(String str, String str2) {
        try {
            if (isSdCardAvailable()) {
                File file = new File(CommonUtils.getExternalDirectory(), str);
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception unused) {
        }
    }

    public static String readDataFromSDCard(String str) {
        try {
            if (isSdCardAvailable()) {
                String absolutePath = CommonUtils.getExternalDirectory().getAbsolutePath();
                if (new File(absolutePath, str).exists()) {
                    new File(absolutePath, str).delete();
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public static boolean isSdCardAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        return externalStorageState != null && externalStorageState.length() > 0 && (externalStorageState.equals("mounted") || externalStorageState.equals("mounted_ro")) && CommonUtils.getExternalDirectory() != null;
    }
}
