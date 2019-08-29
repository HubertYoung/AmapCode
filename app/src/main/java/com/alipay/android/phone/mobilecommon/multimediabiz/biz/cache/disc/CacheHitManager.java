package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

public class CacheHitManager {
    private static boolean a = false;
    private static int b = 24;
    private static volatile CacheHitManager c;
    /* access modifiers changed from: private */
    public AtomicBoolean d = new AtomicBoolean(false);
    private AtomicInteger e = new AtomicInteger(0);
    private AtomicInteger f = new AtomicInteger(0);
    private AtomicInteger g = new AtomicInteger(0);
    private AtomicInteger h = new AtomicInteger(0);
    private AtomicInteger i = new AtomicInteger(0);
    private AtomicInteger j = new AtomicInteger(0);
    private AtomicInteger k = new AtomicInteger(0);
    private AtomicInteger l = new AtomicInteger(0);

    private static class DataFactory {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private String f;
        private String g;
        private String h;

        private DataFactory() {
        }

        /* synthetic */ DataFactory(byte b2) {
            this();
        }

        public String getAudioCacheMissed() {
            return this.h;
        }

        public void setAudioCacheMissed(String audioCacheMissed) {
            this.h = audioCacheMissed;
        }

        public String getAudioCacheHit() {
            return this.g;
        }

        public void setAudioCacheHit(String audioCacheHit) {
            this.g = audioCacheHit;
        }

        public String getFileCacheHit() {
            return this.a;
        }

        public void setFileCacheHit(String fileCacheHit) {
            this.a = fileCacheHit;
        }

        public String getFileCacheMissed() {
            return this.b;
        }

        public void setFileCacheMissed(String fileCacheMissed) {
            this.b = fileCacheMissed;
        }

        public String getImageCacheHit() {
            return this.c;
        }

        public void setImageCacheHit(String imageCacheHit) {
            this.c = imageCacheHit;
        }

        public String getImageCacheMissed() {
            return this.d;
        }

        public void setImageCacheMissed(String imageCacheMissed) {
            this.d = imageCacheMissed;
        }

        public String getVideoCacheHit() {
            return this.e;
        }

        public void setVideoCacheHit(String videoCacheHit) {
            this.e = videoCacheHit;
        }

        public String getVideoCacheMissed() {
            return this.f;
        }

        public void setVideoCacheMissed(String videoCacheMissed) {
            this.f = videoCacheMissed;
        }
    }

    private CacheHitManager() {
        TaskScheduleManager.get().execute(new Runnable() {
            public void run() {
                try {
                    CacheHitManager.this.a();
                } catch (Throwable t) {
                    LoggerFactory.getTraceLogger().error("CacheHitManager", "CacheHitManager sync config error", t);
                    CacheHitManager.this.d.set(false);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void a() {
        ConfigService mConfigService = AppUtils.getConfigService();
        if (mConfigService == null) {
            this.d.set(false);
            return;
        }
        String config = mConfigService.getConfig("multimedia_cache_statistic_config");
        if (TextUtils.isEmpty(config)) {
            this.d.set(false);
            return;
        }
        JSONObject jsonObject = new JSONObject(config);
        a = jsonObject.optBoolean(FunctionSupportConfiger.SWITCH_TAG, false);
        b = jsonObject.optInt(H5SensorPlugin.PARAM_INTERVAL, 24);
        this.d.set(true);
    }

    public static CacheHitManager getInstance() {
        if (c == null) {
            synchronized (CacheHitManager.class) {
                try {
                    if (c == null) {
                        c = new CacheHitManager();
                    }
                }
            }
        }
        return c;
    }

    public void fileCacheHit() {
        if (this.d.get() && a) {
            this.e.getAndIncrement();
        }
    }

    public void fileCacheMissed() {
        if (this.d.get() && a) {
            this.f.getAndIncrement();
        }
    }

    public void imageCacheHit() {
        if (this.d.get() && a) {
            this.g.getAndIncrement();
        }
    }

    public void imageCacheMissed() {
        if (this.d.get() && a) {
            this.h.getAndIncrement();
        }
    }

    public void videoCacheHit() {
        if (this.d.get() && a) {
            this.i.getAndIncrement();
        }
    }

    public void videoCacheMissed() {
        if (this.d.get() && a) {
            this.j.getAndIncrement();
        }
    }

    public void audioCacheHit() {
        if (this.d.get() && a) {
            this.k.getAndIncrement();
        }
    }

    public void audioCacheMissed() {
        if (this.d.get() && a) {
            this.l.getAndIncrement();
        }
    }

    public void report() {
        if (!this.d.get() || !a) {
            LoggerFactory.getTraceLogger().debug("CacheHitManager", "Cache report stop due to config not synced or switch is false");
            return;
        }
        DataFactory dataFactory = new DataFactory(0);
        SharedPreferences mPreferences = AppUtils.getApplicationContext().getSharedPreferences("cache_hit_statistic_pref", 0);
        if (mPreferences == null) {
            LoggerFactory.getTraceLogger().debug("CacheHitManager", "Cache report error due to getSharedPreferences returns null");
            return;
        }
        long lastReportTime = mPreferences.getLong("last_report_time", -1);
        if (lastReportTime == -1) {
            dataFactory.setFileCacheHit(String.valueOf(this.e.get()));
            dataFactory.setFileCacheMissed(String.valueOf(this.f.get()));
            dataFactory.setImageCacheHit(String.valueOf(this.g.get()));
            dataFactory.setImageCacheMissed(String.valueOf(this.h.get()));
            dataFactory.setVideoCacheHit(String.valueOf(this.i.get()));
            dataFactory.setVideoCacheMissed(String.valueOf(this.j.get()));
            dataFactory.setAudioCacheHit(String.valueOf(this.k.get()));
            dataFactory.setAudioCacheMissed(String.valueOf(this.l.get()));
            a(dataFactory);
            mPreferences.edit().putLong("last_report_time", System.currentTimeMillis()).apply();
            b();
        } else if (System.currentTimeMillis() - lastReportTime < TimeUnit.HOURS.toMillis((long) b)) {
            Editor edit = mPreferences.edit();
            edit.putLong("file_cache_hit", mPreferences.getLong("file_cache_hit", 0) + ((long) this.e.get()));
            edit.putLong("file_cache_missed", mPreferences.getLong("file_cache_missed", 0) + ((long) this.f.get()));
            edit.putLong("image_cache_hit", mPreferences.getLong("image_cache_hit", 0) + ((long) this.g.get()));
            edit.putLong("image_cache_missed", mPreferences.getLong("image_cache_missed", 0) + ((long) this.h.get()));
            edit.putLong("video_cache_hit", mPreferences.getLong("video_cache_hit", 0) + ((long) this.i.get()));
            edit.putLong("video_cache_missed", mPreferences.getLong("video_cache_missed", 0) + ((long) this.j.get()));
            edit.putLong("audio_cache_hit", mPreferences.getLong("audio_cache_hit", 0) + ((long) this.k.get()));
            edit.putLong("audio_cache_missed", mPreferences.getLong("audio_cache_missed", 0) + ((long) this.l.get()));
            edit.apply();
            b();
            LoggerFactory.getTraceLogger().debug("CacheHitManager", "Multimedia Cache statistic save to file");
        } else {
            dataFactory.setFileCacheHit(String.valueOf(mPreferences.getLong("file_cache_hit", 0) + ((long) this.e.get())));
            dataFactory.setFileCacheMissed(String.valueOf(mPreferences.getLong("file_cache_missed", 0) + ((long) this.f.get())));
            dataFactory.setImageCacheHit(String.valueOf(mPreferences.getLong("image_cache_hit", 0) + ((long) this.g.get())));
            dataFactory.setImageCacheMissed(String.valueOf(mPreferences.getLong("image_cache_missed", 0) + ((long) this.h.get())));
            dataFactory.setVideoCacheHit(String.valueOf(mPreferences.getLong("video_cache_hit", 0) + ((long) this.i.get())));
            dataFactory.setVideoCacheMissed(String.valueOf(mPreferences.getLong("video_cache_missed", 0) + ((long) this.j.get())));
            dataFactory.setAudioCacheHit(String.valueOf(mPreferences.getLong("audio_cache_hit", 0) + ((long) this.k.get())));
            dataFactory.setAudioCacheMissed(String.valueOf(mPreferences.getLong("audio_cache_missed", 0) + ((long) this.l.get())));
            a(dataFactory);
            Editor edit2 = mPreferences.edit();
            edit2.clear();
            edit2.putLong("last_report_time", System.currentTimeMillis());
            edit2.apply();
            b();
        }
    }

    private static void a(DataFactory dataFactory) {
        Behavor behavor = new Behavor();
        behavor.setSeedID("MultimediaCacheStatistic");
        behavor.setBehaviourPro("StorageCleanMonitor");
        behavor.addExtParam("fileCacheHit", dataFactory.getFileCacheHit());
        behavor.addExtParam("fileCacheMissed", dataFactory.getFileCacheMissed());
        behavor.addExtParam("imageCacheHit", dataFactory.getImageCacheHit());
        behavor.addExtParam("imageCacheMissed", dataFactory.getImageCacheMissed());
        behavor.addExtParam("videoCacheHit", dataFactory.getVideoCacheHit());
        behavor.addExtParam("videoCacheMissed", dataFactory.getVideoCacheMissed());
        behavor.addExtParam("audioCacheHit", dataFactory.getAudioCacheHit());
        behavor.addExtParam("audioCacheMissed", dataFactory.getAudioCacheMissed());
        LoggerFactory.getTraceLogger().debug("CacheHitManager", "Multimedia Cache statistic upload fileCacheHit =  " + dataFactory.getFileCacheHit() + " fileCacheMissed = " + dataFactory.getFileCacheMissed() + " imageCacheHit = " + dataFactory.getImageCacheHit() + " imageCacheMissed = " + dataFactory.getImageCacheMissed() + " videoCacheHit = " + dataFactory.getVideoCacheHit() + " videoCacheMissed = " + dataFactory.getVideoCacheMissed() + " audioCacheHit = " + dataFactory.getAudioCacheHit() + " audioCacheMissed = " + dataFactory.getAudioCacheMissed());
        LoggerFactory.getBehavorLogger().event(null, behavor);
    }

    private void b() {
        this.e.set(0);
        this.f.set(0);
        this.g.set(0);
        this.h.set(0);
        this.i.set(0);
        this.j.set(0);
        this.k.set(0);
        this.l.set(0);
    }
}
