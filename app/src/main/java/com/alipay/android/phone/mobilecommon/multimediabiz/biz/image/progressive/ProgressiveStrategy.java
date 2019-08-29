package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.progressive;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.ProgressiveConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.File;

public class ProgressiveStrategy {
    public static final int MAX_COUNT = 2;
    public static final long PROGRESSIVE_MAX_SIZE = 1048576;
    public static final long PROGRESSIVE_MIN_SIZE = (1024 * ((long) a.progressiveMinSize));
    private static ProgressiveConfig a;
    private static final int b;
    private static final int c = a.progressiveMax;
    private static final int d = a.progressiveMid;
    private static final int e = a.progressiveInterval;
    private static final long f = (500 * ((long) a.timeInterval));
    private long g;
    private int h;
    private int i;
    private Boolean j;
    private Boolean k;

    static {
        ProgressiveConfig progressiveConfig = ConfigManager.getInstance().getCommonConfigItem().progressiveConfig;
        a = progressiveConfig;
        b = progressiveConfig.progressiveMin;
    }

    public ProgressiveStrategy() {
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.g = System.currentTimeMillis();
    }

    public boolean isNeedProgressive(int progress, long curSize, File file, BitmapCacheKey key) {
        a("isNeedProgressive start key=" + key + ";progress=" + progress + ";size=" + curSize + ";strategy=" + toString());
        if (this.i >= 2) {
            a((String) "isNeedProgressive  countCheck= false");
            return false;
        } else if (!a(progress, key)) {
            a((String) "isNeedProgressive  checkProgress= false");
            return false;
        } else if (!a(curSize)) {
            a((String) "isNeedProgressive  checkSize= false");
            return false;
        } else if (!a()) {
            a((String) "isNeedProgressive  checkTimeInterval= false");
            return false;
        } else if (!a(file)) {
            a((String) "isNeedProgressive  checkFileType= false");
            return false;
        } else {
            this.i++;
            this.h = progress;
            this.g = System.currentTimeMillis();
            Logger.D("ProgressiveStrategy", "isNeedProgressive ok progress=" + progress + ";size=" + curSize + ";strategy=" + toString(), new Object[0]);
            return true;
        }
    }

    private boolean a() {
        if (Math.abs(System.currentTimeMillis() - this.g) >= f) {
            return true;
        }
        return false;
    }

    private static boolean a(long size) {
        return size >= PROGRESSIVE_MIN_SIZE && size <= 1048576;
    }

    private boolean a(int progress, BitmapCacheKey key) {
        if (b > progress || progress > c) {
            return false;
        }
        if (this.i > 0 && (progress < d || progress - this.h <= e)) {
            return false;
        }
        if (progress > ProgressiveMgr.getInstance().getProgressiveVal(key.complexCacheKey() + e)) {
            return true;
        }
        Logger.D("ProgressiveStrategy", "checkProgress retry progress false", new Object[0]);
        return false;
    }

    private boolean a(File file) {
        if (this.j == null) {
            this.j = Boolean.valueOf(FileUtils.isJpegFile(file));
        }
        return this.j.booleanValue();
    }

    private void a(String str) {
        if (this.k == null) {
            this.k = Boolean.valueOf(AppUtils.localDebug(new File("/sdcard/pr.o")));
            Logger.P("ProgressiveStrategy", "debug switch is=ProgressiveStrategy", new Object[0]);
        }
        if (this.k.booleanValue()) {
            Logger.P("ProgressiveStrategy", str, new Object[0]);
        }
    }

    public String toString() {
        return "ProgressiveStrategy{count=" + this.i + "lastProg=" + this.h + ", lastTime=" + this.g + '}';
    }
}
