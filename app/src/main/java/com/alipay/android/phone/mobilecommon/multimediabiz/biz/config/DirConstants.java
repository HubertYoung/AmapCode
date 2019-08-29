package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.AudioFileManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class DirConstants {
    private static String AUDIO_CACHE = null;
    private static String DISK_CACHE = null;
    private static String FILE_CACHE = null;
    private static String MATERIAL_CACHE = null;
    public static String OLD_VIDEO_CACHE = null;
    private static final String TAG = "DirConstants";

    public static String getAudioCache() {
        if (AUDIO_CACHE == null) {
            try {
                AUDIO_CACHE = AudioFileManager.getInstance(AppUtils.getApplicationContext()).getBaseDir();
            } catch (Throwable t) {
                Logger.W(TAG, "getAudioCache error: " + t, new Object[0]);
            }
        }
        return AUDIO_CACHE;
    }

    public static String getFileCache() {
        if (FILE_CACHE == null) {
            try {
                FILE_CACHE = DjangoUtils.getMediaDir(AppUtils.getApplicationContext(), DjangoConstant.FILE_PATH);
            } catch (Throwable t) {
                Logger.W(TAG, "getFileCache error: " + t, new Object[0]);
            }
        }
        return FILE_CACHE;
    }

    public static String getMaterialCache() {
        if (MATERIAL_CACHE == null) {
            try {
                MATERIAL_CACHE = FileUtils.getMediaDir("material", false);
            } catch (Throwable t) {
                Logger.W(TAG, "getMaterialCache error: " + t, new Object[0]);
            }
        }
        return MATERIAL_CACHE;
    }

    public static String getDiskCacheDir() {
        if (DISK_CACHE == null) {
            try {
                DISK_CACHE = CacheConfig.getCacheDirNew().getAbsolutePath();
            } catch (Throwable t) {
                Logger.W(TAG, "getDiskCacheDir error: " + t, new Object[0]);
            }
        }
        return DISK_CACHE;
    }
}
