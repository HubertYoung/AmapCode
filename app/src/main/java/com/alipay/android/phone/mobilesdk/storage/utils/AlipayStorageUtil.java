package com.alipay.android.phone.mobilesdk.storage.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;

public class AlipayStorageUtil {
    private static final String ALIPAY = "alipay";

    public static File getAppExternalFile(Context context) {
        boolean z = false;
        try {
            return context.getExternalFilesDir(null).getParentFile();
        } catch (Exception e) {
            return z;
        }
    }

    public static File getAlipaySDFile(String bundleName) {
        if (TextUtils.isEmpty(bundleName)) {
            throw new RuntimeException("bundleName can not be null");
        } else if (Environment.getExternalStorageState().equals("mounted")) {
            return new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "alipay" + File.separator + bundleName);
        } else {
            return null;
        }
    }
}
