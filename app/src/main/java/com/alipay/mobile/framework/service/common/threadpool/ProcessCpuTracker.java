package com.alipay.mobile.framework.service.common.threadpool;

import android.os.Process;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class ProcessCpuTracker {
    private static final String a = ProcessCpuTracker.class.getSimpleName();
    private static final int[] b = {288, 8224, 8224, 8224, 8224, 8224, 8224, 8224};
    private long[] c = new long[7];
    private long d;
    private long e;
    private long f;
    private long g;
    private long h;
    private long i;
    private long j;
    private long k;

    public ProcessCpuTracker() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public ProcessCpuTracker update() {
        return a(null);
    }

    public ProcessCpuTracker update4Process(String pid) {
        return a(pid);
    }

    @NonNull
    private ProcessCpuTracker a(String pid) {
        boolean isSuccess = false;
        try {
            isSuccess = Process.readProcFile(TextUtils.isEmpty(pid) ? "/proc/stat" : "/proc/" + pid + "/stat", b, null, this.c, null);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().warn(a, t);
        }
        if (!isSuccess) {
            LoggerFactory.getTraceLogger().error(a, (String) "fail to compute");
        } else {
            long userTime = this.c[0] + this.c[1];
            long systemTime = this.c[2];
            long idleTime = this.c[3];
            long irqTime = this.c[5];
            this.h = userTime - this.d;
            this.i = systemTime - this.e;
            this.j = irqTime - this.f;
            this.k = idleTime - this.g;
            this.d = userTime;
            this.e = systemTime;
            this.f = irqTime;
            this.g = idleTime;
        }
        return this;
    }

    public float getCpuIdlePercent() {
        long denom = this.h + this.i + this.j + this.k;
        if (denom > 0) {
            return (((float) this.k) * 100.0f) / ((float) denom);
        }
        return -1.0f;
    }
}
