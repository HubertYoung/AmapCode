package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.utils.MD5Util;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class CacheConfig {
    private static File a = null;
    private static AtomicBoolean b = new AtomicBoolean(false);

    public static File getCacheDirNew() {
        synchronized (CacheConfig.class) {
            try {
                if (a == null) {
                    Context context = AppUtils.getApplicationContext();
                    if (!PermissionHelper.hasPermission("android.permission.WRITE_EXTERNAL_STORAGE") || !"mounted".equals(Environment.getExternalStorageState())) {
                        a = new File(context.getFilesDir(), "multimedia");
                    } else {
                        a = new File(FileUtils.getMediaDir(MD5Util.getMD5String(Build.MANUFACTURER)));
                    }
                    Logger.P("CacheConfig", "cachePath: " + a, new Object[0]);
                    if (b.compareAndSet(false, true)) {
                        FileUtils.mkdirs(a);
                    }
                }
            }
        }
        return a;
    }
}
