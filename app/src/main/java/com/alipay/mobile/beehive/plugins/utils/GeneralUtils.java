package com.alipay.mobile.beehive.plugins.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.io.File;
import java.util.concurrent.Executor;

public class GeneralUtils {
    private static final String FILE_PREFIX = "file://";
    private static final String TAG = "GeneralUtils";

    public static MicroApplication getTopApplication() {
        MicroApplication ret = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication();
        if (ret == null) {
            BeeH5PluginLogger.debug(TAG, "getTopApplication return null.");
        }
        return ret;
    }

    public static Executor getExecutor(ScheduleType scheduleType) {
        return ((TaskScheduleService) MicroServiceUtil.getMicroService(TaskScheduleService.class)).acquireExecutor(scheduleType);
    }

    public static void deletePhoto(String path) {
        if (!TextUtils.isEmpty(path)) {
            String path2 = removeFilePrefix(path);
            BeeH5PluginLogger.debug(TAG, "Deleting file at path: " + path2);
            File file = new File(path2);
            if (file.exists()) {
                file.delete();
                scanMedia(file);
                BeeH5PluginLogger.debug(TAG, "Delete success!");
                return;
            }
            BeeH5PluginLogger.debug(TAG, "Delete failed,file not existed.");
        }
    }

    public static void scanMedia(File mediaFile) {
        Intent scanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        scanIntent.setData(Uri.fromFile(mediaFile));
        LauncherApplicationAgent.getInstance().getApplicationContext().sendBroadcast(scanIntent);
    }

    public static String removeFilePrefix(String url) {
        String ret = url;
        if (TextUtils.isEmpty(url) || !url.startsWith("file://")) {
            return ret;
        }
        return url.replace("file://", "");
    }

    public static Resources getBundleResources(String bundleName) {
        return LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle(bundleName);
    }

    public static Resources getBeehiveBundleResources() {
        return getBundleResources("android-phone-wallet-beehive");
    }

    public static boolean isLocalFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        if (path.startsWith("file://") || path.startsWith(File.separator)) {
            return true;
        }
        return false;
    }

    public static String covertPathToLocalId(String path) {
        APMToolService apmToolService = (APMToolService) MicroServiceUtil.getMicroService(APMToolService.class);
        if (apmToolService != null) {
            return apmToolService.encodeToLocalId(path);
        }
        return null;
    }

    public static String convertLocalIdToPath(String localId) {
        APMToolService apmToolService = (APMToolService) MicroServiceUtil.getMicroService(APMToolService.class);
        if (apmToolService != null) {
            return apmToolService.decodeToPath(localId);
        }
        return null;
    }
}
