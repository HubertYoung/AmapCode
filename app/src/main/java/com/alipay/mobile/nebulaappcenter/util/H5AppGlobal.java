package com.alipay.mobile.nebulaappcenter.util;

import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappcenter.app.H5App;
import com.alipay.mobile.nebulaappcenter.dbdao.H5NebulaAppDao;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class H5AppGlobal {
    private static String a = "H5AppGlobal";

    public static String a(String file) {
        try {
            return H5Utils.getContext().getFilesDir().getAbsolutePath() + file;
        } catch (Throwable throwable) {
            H5Log.e(a, throwable);
            return null;
        }
    }

    public static boolean a(String appId, String version, String installPath) {
        if (!TextUtils.isEmpty(installPath)) {
            return H5FileUtil.exists(installPath);
        }
        H5App h5App = new H5App();
        h5App.setAppInfo(H5NebulaAppDao.c().a(appId, version));
        return h5App.isInstalled();
    }

    public static String a(InputStream inputStream) {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder(1000);
            while (true) {
                String line = bufReader.readLine();
                if (line == null) {
                    return sb.toString();
                }
                sb.append(line);
            }
        } catch (Exception e) {
            H5Log.e(a, (Throwable) e);
            return "";
        }
    }
}
