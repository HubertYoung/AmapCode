package com.alipay.mobile.nebula.filecache;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import java.io.File;

public class DiskUtil {
    public static final String TAG = "DiskUtil";

    public static String getAppDir(Context context) {
        File fileDir = context.getFilesDir();
        if (H5FileUtil.exists(fileDir)) {
            return fileDir.getParent();
        }
        return null;
    }

    public static String getSubDir(Context context, String subDir) {
        String appDir = getAppDir(context);
        if (TextUtils.isEmpty(appDir)) {
            return null;
        }
        return appDir + "/" + subDir;
    }

    public static boolean mediaMounted() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            return false;
        }
    }
}
