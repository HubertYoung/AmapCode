package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc;

import android.content.Context;
import android.content.SharedPreferences;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;

public class CacheStatistics {
    private static CacheStatistics c;
    private static final Logger e = Logger.getLogger((String) "CacheStatistics");
    private Context a = AppUtils.getApplicationContext();
    private SharedPreferences b = this.a.getSharedPreferences("CacheStatistics", 0);
    private boolean d = false;

    private CacheStatistics() {
    }

    public static CacheStatistics get() {
        synchronized (CacheStatistics.class) {
            if (c == null) {
                c = new CacheStatistics();
            }
        }
        return c;
    }

    public long audioSize() {
        return CacheContext.get().getDiskCache().getTotalSize(3);
    }

    public long videoSize() {
        return CacheContext.get().getDiskCache().getTotalSize(2);
    }

    public long fileSize() {
        return CacheContext.get().getDiskCache().getTotalSize(4);
    }

    public long imageSize() {
        return CacheContext.get().getDiskCache().getTotalSize(1);
    }

    public void uploadCacheInfo() {
        long start = System.currentTimeMillis();
        e.p("uploadCacheInfo mUploading: " + this.d + ", shouldUploadInDeltaTime: " + b(), new Object[0]);
        if (a() || (!this.d && b())) {
            this.d = true;
            UCLogUtil.UC_MM_C19(fileSize(), imageSize(), audioSize(), videoSize(), FileUtils.getSdTotalSizeBytes() >> 20, FileUtils.getSdAvailableSizeBytes() >> 20, FileUtils.getPhoneTotalSizeBytes() >> 20, FileUtils.getPhoneAvailableSizeBytes() >> 20);
            this.d = false;
            this.b.edit().putLong("last_upload_time", System.currentTimeMillis()).apply();
            e.p("uploadCacheInfo finish, cost: " + (System.currentTimeMillis() - start), new Object[0]);
        }
    }

    private static boolean a() {
        return ConfigManager.getInstance().enableUploadCacheInfo();
    }

    private boolean b() {
        return Math.abs(System.currentTimeMillis() - this.b.getLong("last_upload_time", 0)) > c();
    }

    private static long c() {
        return ConfigManager.getInstance().getUploadTimeInterval(86400000);
    }

    public void uploadCacheInfoAsync() {
        e.p("uploadCacheInfoAsync...", new Object[0]);
        TaskScheduleManager.get().execute(new Runnable() {
            public void run() {
                CacheStatistics.this.uploadCacheInfo();
            }
        });
    }
}
