package com.alipay.mobile.beehive.photo.util;

import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.beehive.util.MicroServiceUtil;

public class CloudConfig {
    private static final String KEY_DISABLE_BEE_PHOTO_STEP_UPDATE = "disable_bee_photo_step_update";
    private static final String KEY_DISABLE_LOCAL_PHOTO_DISK_CACKE = "disable_local_photo_disk_cache";
    private static final String KEY_DISABLE_PHOTO_THUMBNAIL_SCAN = "disable_photo_thumbnail_scan";
    private static final String KEY_DISABLE_Q_COMPACT = "disable_q_compact";
    private static boolean isConfigToDisableBeePhotoStepUpdate = false;
    private static boolean isConfigToDisableImageObserver = false;
    private static boolean isConfigToDisableLocalPhotoDiskCache = false;
    private static boolean isConfigToDisablePhotoThumbnailScan = false;
    private static boolean isConfigToDisableQCompact = false;
    private static boolean isInit = false;

    public static boolean isConfigToDisableBeePhotoStepUpdate() {
        if (!isInit) {
            initConfig();
        }
        return isConfigToDisableBeePhotoStepUpdate;
    }

    public static boolean isIsConfigToDisablePhotoThumbnailScan() {
        if (!isInit) {
            initConfig();
        }
        return isConfigToDisablePhotoThumbnailScan;
    }

    public static boolean isConfigToDisableCheckLocalPhotoDiskCache() {
        if (!isInit) {
            initConfig();
        }
        return isConfigToDisableLocalPhotoDiskCache;
    }

    public static boolean isConfigToDisableQCompact() {
        if (!isInit) {
            initConfig();
        }
        return isConfigToDisableQCompact;
    }

    public static boolean isConfigToDisableImageObserver() {
        if (!isInit) {
            initConfig();
        }
        return isConfigToDisableImageObserver;
    }

    private static void initConfig() {
        ConfigService cs = (ConfigService) MicroServiceUtil.getMicroService(ConfigService.class);
        if (cs != null) {
            isConfigToDisableBeePhotoStepUpdate = TextUtils.equals(cs.getConfig(KEY_DISABLE_BEE_PHOTO_STEP_UPDATE), "true");
            isConfigToDisablePhotoThumbnailScan = TextUtils.equals(cs.getConfig(KEY_DISABLE_PHOTO_THUMBNAIL_SCAN), "true");
            isConfigToDisableLocalPhotoDiskCache = TextUtils.equals(cs.getConfig(KEY_DISABLE_LOCAL_PHOTO_DISK_CACKE), "true");
            isConfigToDisableQCompact = TextUtils.equals(cs.getConfig(KEY_DISABLE_Q_COMPACT), "true");
            isConfigToDisableImageObserver = TextUtils.equals(cs.getConfig("disable_bee_image_observer"), "true");
            isInit = true;
        }
    }
}
