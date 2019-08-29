package com.alipay.android.phone.mobilecommon.multimediabiz.biz.common;

import android.content.SharedPreferences;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;

public class CommonSharedPreference {
    private static CommonSharedPreference b;
    private SharedPreferences a = AppUtils.getApplicationContext().getSharedPreferences("pref_common_multimedia", 0);

    private CommonSharedPreference() {
    }

    public static CommonSharedPreference get() {
        if (b == null) {
            synchronized (CommonSharedPreference.class) {
                try {
                    if (b == null) {
                        b = new CommonSharedPreference();
                    }
                }
            }
        }
        return b;
    }

    public void release() {
        b = null;
    }

    public void setMultimediaDbCreateTime(long time) {
        this.a.edit().putLong("key_db_multimedia", time).apply();
    }

    public long getMultimediaDbCreateTime() {
        return this.a.getLong("key_db_multimedia", -1);
    }

    public void setMultimediaTaskDbCreateTime(long time) {
        this.a.edit().putLong("key_db_multimedia_task", time).apply();
    }

    public long getMultimediaTaskDbCreateTime() {
        return this.a.getLong("key_db_multimedia_task", -1);
    }

    public void setImageCacheDirCreateTime(long time) {
        this.a.edit().putLong("key_dir_time_image_cache", time).apply();
    }

    public long getImageCacheDirCreateTime() {
        return this.a.getLong("key_dir_time_image_cache", -1);
    }

    public void setIMCacheDirCreateTime(long time) {
        this.a.edit().putLong("key_dir_time_im_cache", time).apply();
    }

    public long getIMCacheDirCreateTime() {
        return this.a.getLong("key_dir_time_im_cache", -1);
    }

    public void setAudioCacheDirCreateTime(long time) {
        this.a.edit().putLong("key_dir_time_audio_cache", time).apply();
    }

    public long getAudioCacheDirCreateTime() {
        return this.a.getLong("key_dir_time_audio_cache", -1);
    }

    public void setVideoCacheDirCreateTime(long time) {
        this.a.edit().putLong("key_dir_time_video_cache", time).apply();
    }

    public long getVideoCacheDirCreateTime() {
        return this.a.getLong("key_dir_time_video_cache", -1);
    }

    public void setCleanedExpiredCache(boolean cleaned) {
        this.a.edit().putBoolean("key_cleaned_expired_cache", cleaned).apply();
    }

    public boolean isCleanedExpiredCache() {
        return this.a.getBoolean("key_cleaned_expired_cache", false);
    }
}
