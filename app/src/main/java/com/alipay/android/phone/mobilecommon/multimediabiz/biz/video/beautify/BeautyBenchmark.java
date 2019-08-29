package com.alipay.android.phone.mobilecommon.multimediabiz.biz.video.beautify;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;

public class BeautyBenchmark {
    public static final int THRESHOLD = 10000;
    private static BeautyBenchmark a = null;
    private long b;
    private long c;
    private int d = 0;
    private long e;
    private long f = -1;
    private int g;
    private int h;
    private int i;

    private BeautyBenchmark(int on, int type, int beautyValue) {
        this.g = on;
        this.h = type;
        this.i = beautyValue;
    }

    public static BeautyBenchmark getInstance(int on, int type, int beautyValue) {
        if (a == null) {
            synchronized (BeautyBenchmark.class) {
                if (a == null) {
                    a = new BeautyBenchmark(on, type, beautyValue);
                }
            }
        }
        return a;
    }

    public void begin() {
        if (this.d < 20) {
            this.b = System.nanoTime();
        }
    }

    public void end() {
        if (this.d < 20) {
            this.c = System.nanoTime();
            this.e += this.c - this.b;
            this.d++;
        } else if (this.f == -1) {
            this.f = (this.e / ((long) this.d)) / 1000;
            Logger.D("BeautyBenchmark", "mAverageTime = " + this.f, new Object[0]);
            UCLogUtil.UC_MM_C22(((float) this.f) / 1000.0f, this.g, this.h, 1, this.i);
        }
    }

    public long getAverageTime() {
        return this.f;
    }
}
