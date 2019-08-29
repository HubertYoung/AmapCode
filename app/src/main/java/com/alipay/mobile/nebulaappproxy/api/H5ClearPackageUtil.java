package com.alipay.mobile.nebulaappproxy.api;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.service.H5AppCenterService;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.concurrent.TimeUnit;

public class H5ClearPackageUtil {
    private static boolean a = false;

    public static void clearUnusedAppPackage() {
        if (a) {
            H5Log.d("H5ClearPackageUtil", "not clear because sClearing.");
            return;
        }
        try {
            a = true;
            a();
        } finally {
            a = false;
        }
    }

    private static void a() {
        H5AppCenterService appCenterService = (H5AppCenterService) H5Utils.findServiceByInterface(H5AppCenterService.class.getName());
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (appCenterService != null && h5ConfigProvider != null) {
            String clearConfig = h5ConfigProvider.getConfig("h5_enableClearAppPkg");
            boolean clearAmr = false;
            boolean clearUnzip = false;
            long clearUnzipTime = TimeUnit.DAYS.toMillis(50);
            long checkTime = TimeUnit.DAYS.toMillis(7);
            if (!TextUtils.isEmpty(clearConfig)) {
                JSONObject configObj = H5Utils.parseObject(clearConfig);
                if (configObj != null) {
                    clearAmr = "YES".equalsIgnoreCase(H5Utils.getString(configObj, (String) "amr"));
                    clearUnzip = "YES".equalsIgnoreCase(H5Utils.getString(configObj, (String) "unzip"));
                    try {
                        int unzipT = Integer.parseInt(H5Utils.getString(configObj, (String) "unzipT"));
                        if (unzipT > 0) {
                            clearUnzipTime = TimeUnit.SECONDS.toMillis((long) unzipT);
                        }
                        int checkT = Integer.parseInt(H5Utils.getString(configObj, (String) "checkT"));
                        if (checkT > 0) {
                            checkTime = TimeUnit.SECONDS.toMillis((long) checkT);
                        }
                    } catch (Throwable throwable) {
                        H5Log.e((String) "H5ClearPackageUtil", throwable);
                    }
                }
            }
            boolean enableClear = a(checkTime);
            H5Log.d("H5ClearPackageUtil", "clearAmr : " + clearAmr + " clearUnzipTime : " + clearUnzipTime + " checkTime : " + checkTime + " enableClear : " + enableClear);
            if (clearAmr && enableClear) {
                appCenterService.clearAppAmrPackage();
            }
            if (clearUnzip && enableClear) {
                appCenterService.clearAppUnzipPackage(clearUnzipTime);
            }
            if (!enableClear) {
                return;
            }
            if (clearAmr || clearUnzip) {
                b();
            }
        }
    }

    private static boolean a(long checkTime) {
        String lastClearStr = H5DevConfig.getStringConfig(H5DevConfig.H5_DELETE_UNUSED_APP_PACKAGE, null);
        if (!TextUtils.isEmpty(lastClearStr)) {
            long lastTime = 0;
            try {
                lastTime = Long.parseLong(lastClearStr);
            } catch (Throwable throwable) {
                H5Log.e((String) "H5ClearPackageUtil", throwable);
            }
            long currentTime = System.currentTimeMillis();
            H5Log.d("H5ClearPackageUtil", "currentTime : " + currentTime + " lastTime : " + lastTime);
            if (lastTime > 0 && currentTime - lastTime > checkTime) {
                return true;
            }
        } else {
            H5DevConfig.setStringConfig(H5DevConfig.H5_DELETE_UNUSED_APP_PACKAGE, String.valueOf(System.currentTimeMillis()));
        }
        return false;
    }

    private static void b() {
        long clearPkgTime = System.currentTimeMillis();
        H5Log.d("H5ClearPackageUtil", "save clearPkgTime : " + clearPkgTime);
        H5DevConfig.setStringConfig(H5DevConfig.H5_DELETE_UNUSED_APP_PACKAGE, String.valueOf(clearPkgTime));
    }
}
